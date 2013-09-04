package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.service.SubjectAccrualBeanLocal;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Jul 23, 2012
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings("unchecked")
public class SubmissionHistoryBean implements SubmissionHistoryService {
    private static final String YES = "Yes";
    private static final String NO = "No";
    
    @EJB
    private SearchTrialService searchTrialSvc;
    
    private static final int DATE_COL = 3;
    private static final int TYPE_COL = 4;
    private static final int AI_COL = 5;
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final String BATCH_HQL = "from AccrualCollections ac "
            + "join fetch ac.batchFile bf "
            + "join fetch bf.userLastCreated us "
            + "where (ac.nciNumber in (:nciNumbers) "
            + "       or us.userId = :userId) ";
    private static final String GUI_COMPLETE_SQL = 
            "select identifier, study_protocol_identifier,  "
            + "     case when date_last_updated is null then user_last_created_id else user_last_updated_id end, "
            + "     case when date_last_updated is null then date_last_created else date_last_updated end, "
            + "     submission_type, assigned_identifier "
            + "from study_subject "
            + "where submission_type in ('" 
            + AccrualSubmissionTypeCode.UI.getName() + "','" + AccrualSubmissionTypeCode.SERVICE_MSA.getName() + "') "
            + "and status_code = '" + FunctionalRoleStatusCode.ACTIVE.getName() + "' "
            + "and study_protocol_identifier in (:studyProtocolIdentifiers) ";
    private static final String GUI_ABBREVIATED_SQL =
            "select identifier, study_protocol_identifier, user_last_updated_id, date_last_updated "
            + "from study_site_subject_accrual_count "
            + "where submission_type = '" + AccrualSubmissionTypeCode.UI.getName() + "' "
            + "and study_protocol_identifier in (:studyProtocolIdentifiers) ";

    /** Used to store data on submitters. Avoid redundant db queries. */
    private class UserData {
        private final String name;
        private final Long organization;
        public UserData(String name, Long organization) {
            this.name = name;
            this.organization = organization;
        }
    }

    /** Used to store data on submitters. Avoid redundant db queries. */
    private class TrialData {
        private final boolean industrial;
        private final boolean affiliatedWithLead;
        private final boolean affiliatedWithSite;
        private final Set<Long> siteAccess;
        public TrialData(boolean industrial, boolean affiliatedWithLead, boolean affiliatedWithSite, 
                Set<Long> siteAccess) {
            this.industrial = industrial;
            this.affiliatedWithLead = affiliatedWithLead;
            this.affiliatedWithSite = affiliatedWithSite;
            this.siteAccess = siteAccess;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HistoricalSubmissionDto> search(Timestamp from, Timestamp to, RegistryUser ru) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        if (ru == null) {
            return result;
        }
        Map<Long, String> trials = searchTrialSvc.getAuthorizedTrialMap(ru.getId());
        Map<Long, UserData> users = new HashMap<Long, UserData>();
        if (!trials.isEmpty()) {
            result.addAll(searchGuiCompleteSubmissions(from, to, trials, users));
            result.addAll(searchGuiAbbreviatedSubmissions(from, to, trials, users));
            result.addAll(searchBatchSubmissions(from, to, trials, users));
            Collections.sort(result);
        }
        return po6304Filter(result, ru, trials);
    }

    private List<HistoricalSubmissionDto> po6304Filter(List<HistoricalSubmissionDto> sList, RegistryUser ru,
            Map<Long, String> trials) throws PAException {
        Map<String, TrialData> trialData = new HashMap<String, TrialData>();
        List<Long> familyOrgIds = FamilyHelper.getAllRelatedOrgs(ru.getAffiliatedOrganizationId());
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        for (HistoricalSubmissionDto s : sList) {
            if (s.getRegistryUserId() == null) {
                continue;
            }

            // always show if user is submitter
            if (s.getRegistryUserId().equals(ru.getCsmUser().getUserId())) {
                result.add(s);
                continue;
            }

            // otherwise show only under certain conditions
            if (trials.containsValue(s.getNciNumber()) 
                    && po6304Show(s, ru, trialData, familyOrgIds)) {
                result.add(s);
            }
        }
        return result;
    }

    private boolean po6304Show(HistoricalSubmissionDto s, RegistryUser ru, Map<String, TrialData> trialData, 
            List<Long> familyOrgIds) throws PAException {
        TrialData d = getTrialData(s.getNciNumber(), ru, trialData);
        return !d.industrial && d.affiliatedWithLead
                || d.affiliatedWithSite  
                   && d.siteAccess.contains(s.getUserAffiliatedOrgId())
                   && (familyOrgIds.contains(s.getUserAffiliatedOrgId()) 
                       // below required for case where affiliated org is not part of a family
                       || ru.getAffiliatedOrganizationId().equals(s.getUserAffiliatedOrgId()));
    }

    private TrialData getTrialData(String nciId, RegistryUser user, Map<String, TrialData> trialData)
            throws PAException {
        TrialData result = trialData.get(nciId);
        if (result == null) {
            Ii ii = IiConverter.convertToStudyProtocolIi(0L);
            ii.setExtension(nciId);
            StudyProtocolDTO sp = PaServiceLocator.getInstance().getStudyProtocolService().getStudyProtocol(ii);
            Long spId = IiConverter.convertToLong(sp.getIdentifier());
            boolean industrial = BlConverter.convertToBool(sp.getProprietaryTrialIndicator());
            boolean lead = PaRegistry.getOrganizationCorrelationService().isAffiliatedWithTrial(spId, 
                    user.getAffiliatedOrganizationId(), StudySiteFunctionalCode.LEAD_ORGANIZATION);
            boolean site =  PaRegistry.getOrganizationCorrelationService().isAffiliatedWithTrial(spId, 
                    user.getAffiliatedOrganizationId(), StudySiteFunctionalCode.TREATING_SITE);
            Set<Long> siteAccess = new HashSet<Long>();
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select ssaa "
                + "from StudyProtocol sp "
                + "join sp.studySites ss "
                + "join ss.studySiteAccrualAccess ssaa "
                + "where sp.id = :spId "
                + "  and ssaa.statusCode = 'ACTIVE' "
                + "  and ssaa.registryUser.id = :ruId";
            query = session.createQuery(hql);
            query.setParameter("spId", spId);
            query.setParameter("ruId", user.getId());
            List<StudySiteAccrualAccess> queryList = query.list();
            for (StudySiteAccrualAccess obj : queryList) {
                siteAccess.add(Long.valueOf(obj.getStudySite().getHealthCareFacility().
                        getOrganization().getIdentifier()));
            }
            result = new TrialData(industrial, lead, site, siteAccess);
            trialData.put(nciId, result);
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchBatchSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, UserData> users) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer(BATCH_HQL);
        hql.append(getHqlDateCriteria(from, to));
        Query query = session.createQuery(hql.toString());
        query.setParameterList("nciNumbers", trials.values());
        User user = AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser());
        query.setLong("userId", user.getUserId());
        setDateProperties(query, from, to);
        List<Object> submList = query.list();
        for (Object subm : submList) {
            AccrualCollections ac = (AccrualCollections) subm;
            BatchFile bf = ac.getBatchFile();
            HistoricalSubmissionDto row = new HistoricalSubmissionDto();
            row.setBatchFileIdentifier(bf.getId());
            row.setDate(bf.getDateLastCreated() == null ? null : new Timestamp(bf.getDateLastCreated().getTime()));
            File file = new File(bf.getFileLocation());
            row.setFileName(AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()));
            row.setNciNumber(ac.getNciNumber());
            row.setResult(getBatchResult(ac));
            row.setSubmissionType(bf.getSubmissionTypeCode());
            row.setRegistryUserId(bf.getUserLastCreated() == null ? null : bf.getUserLastCreated().getUserId());
            UserData userData = getRegistryUserData(users, row.getRegistryUserId());
            row.setUsername(userData.name);
            row.setUserAffiliatedOrgId(userData.organization);
            result.add(row);
        }
        return result;
    }

    String getBatchResult(AccrualCollections ac) {
        String result = null;
        if (!ac.isPassedValidation()) {
            result = NO;
        }
        if (ac.getTotalImports() != null) {
            result = YES;
        }
        if (result == null) {
            Date td = new Date(new Date().getTime() 
                    - SubjectAccrualBeanLocal.BATCH_PROCESSING_THREAD_TIMEOUT_HOURS * ONE_HOUR);
            if (td.after(ac.getDateLastCreated())) {
                result = NO;
            }
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchGuiCompleteSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, UserData> users) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer(GUI_COMPLETE_SQL);
        sql.append(getSqlDateCriteria(from, to));
        Query query = session.createSQLQuery(sql.toString());
        query.setParameterList("studyProtocolIdentifiers", trials.keySet());
        setDateProperties(query, from, to);
        List<Object[]> submList = query.list();
        for (Object[] subm : submList) {
            HistoricalSubmissionDto row = new HistoricalSubmissionDto();
            row.setDate((Timestamp) subm[DATE_COL]);
            Long trialId = ((BigInteger) subm[1]).longValue();
            row.setCompleteTrialId(trialId);
            row.setNciNumber(trials.get(trialId));
            row.setResult(YES);
            row.setSubmissionType(AccrualSubmissionTypeCode.valueOf((String) subm[TYPE_COL]));
            row.setRegistryUserId(((Number) subm[2]).longValue());
            UserData userData = getRegistryUserData(users, row.getRegistryUserId());
            row.setUsername(userData.name);
            row.setUserAffiliatedOrgId(userData.organization);
            row.setAssignedIdentifier((String) subm[AI_COL]);
            row.setStudySubjectId(((Number) subm[0]).longValue());
            result.add(row);
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchGuiAbbreviatedSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, UserData> users) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer(GUI_ABBREVIATED_SQL);
        sql.append(getSqlDateCriteria(from, to));
        Query query = session.createSQLQuery(sql.toString());
        query.setParameterList("studyProtocolIdentifiers", trials.keySet());
        setDateProperties(query, from, to);
        List<Object[]> submList = query.list();
        for (Object[] subm : submList) {
            HistoricalSubmissionDto row = new HistoricalSubmissionDto();
            row.setDate((Timestamp) subm[DATE_COL]);
            Long trialId = ((BigInteger) subm[1]).longValue();
            row.setAbbreviatedTrialId(trialId);
            row.setNciNumber(trials.get(trialId));
            row.setResult(YES);
            row.setSubmissionType(AccrualSubmissionTypeCode.UI);
            row.setRegistryUserId(((Number) subm[2]).longValue());
            UserData userData = getRegistryUserData(users, row.getRegistryUserId());
            row.setUsername(userData.name);
            row.setUserAffiliatedOrgId(userData.organization);
            result.add(row);
        }
        return result;
    }

    private String getHqlDateCriteria(Timestamp from, Timestamp to) {
        StringBuffer result = new StringBuffer();
        if (from != null) {
            result.append("and bf.dateLastCreated >= :from ");
        }
        if (to != null) {
            result.append("and bf.dateLastCreated < :to ");
        }
        return result.toString();
    }

    private String getSqlDateCriteria(Timestamp from, Timestamp to) {
        StringBuffer result = new StringBuffer();
        if (from != null) {
            result.append("and date_last_updated >= :from ");
        }
        if (to != null) {
            result.append("and date_last_updated < :to ");
        }
        return result.toString();
    }

    private void setDateProperties(Query query, Timestamp from, Timestamp to) {
        if (from != null) {
            query.setParameter("from", from);
        }
        if (to != null) {
            Timestamp toParam = new Timestamp(to.getTime() + ONE_DAY);
            query.setParameter("to", toParam);
        }
    }

    private UserData getRegistryUserData(Map<Long, UserData> users, Long ruId) {
        if (ruId == null) {
            return new UserData(null, null);
        }
        if (users.get(ruId) ==  null) {
            Session session = PaHibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer(
                    "from RegistryUser ru join fetch ru.csmUser cu where cu.id = :identifier");
            Query query = session.createQuery(hql.toString());
            query.setParameter("identifier", ruId);
            List<Object> userList = query.list();
            if (CollectionUtils.isNotEmpty(userList)) {
                RegistryUser registryUser = (RegistryUser) userList.get(0);
                users.put(ruId, new UserData(AccrualUtil.getDisplayName(registryUser), 
                        registryUser.getAffiliatedOrganizationId()));
            }
        }
        return users.get(ruId);
    }

    /**
     * @param searchTrialSvc the searchTrialSvc to set
     */
    public void setSearchTrialSvc(SearchTrialService searchTrialSvc) {
        this.searchTrialSvc = searchTrialSvc;
    }
}
