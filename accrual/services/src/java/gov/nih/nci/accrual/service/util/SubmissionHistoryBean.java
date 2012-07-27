package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.service.SubjectAccrualBeanLocal;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    @EJB
    private SearchTrialService searchTrialSvc;
    
    private static final int DATE_COL = 3;
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final String BATCH_HQL = "from AccrualCollections ac "
            + "join fetch ac.batchFile bf "
            + "join fetch bf.userLastCreated us "
            + "where (ac.nciNumber in (:nciNumbers) "
            + "       or us.userId = :userId) ";
    private static final String GUI_COMPLETE_SQL = 
            "select identifier, study_protocol_identifier, user_last_updated_id, date_last_updated "
            + "from study_subject "
            + "where submission_type = '" + AccrualSubmissionTypeCode.UI.getName() + "' "
            + "and status_code = '" + FunctionalRoleStatusCode.ACTIVE.getName() + "' "
            + "and study_protocol_identifier in (:studyProtocolIdentifiers) ";
    private static final String GUI_ABBREVIATED_SQL =
            "select identifier, study_protocol_identifier, user_last_updated_id, date_last_updated "
            + "from study_site_subject_accrual_count "
            + "where submission_type = '" + AccrualSubmissionTypeCode.UI.getName() + "' "
            + "and study_protocol_identifier in (:studyProtocolIdentifiers) ";


    /**
     * {@inheritDoc}
     */
    @Override
    public List<HistoricalSubmissionDto> search(Timestamp from, Timestamp to, RegistryUser ru) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Map<Long, String> trials = searchTrialSvc.getAuthorizedTrialMap(ru == null ? null : ru.getId());
        Map<Long, String> usernames = new HashMap<Long, String>();
        if (!trials.isEmpty()) {
            result.addAll(searchGuiCompleteSubmissions(from, to, trials, usernames));
            result.addAll(searchGuiAbbreviatedSubmissions(from, to, trials, usernames));
            result.addAll(searchBatchSubmissions(from, to, trials, usernames));
            Collections.sort(result);
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchBatchSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, String> usernames) throws PAException {
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
            row.setFileHtml("<a href='/accrual/protected/priorSubmissionsviewDoc.action?batchFileId=" + bf.getId()
                    + "'>" + AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()) + "</a>");
            row.setNciNumber(ac.getNciNumber());
            row.setResult(getBatchResult(ac));
            row.setSubmissionType(bf.getSubmissionTypeCode());
            row.setUsername(getRegistryUsername(usernames, bf.getUserLastCreated() == null 
                    ? null : bf.getUserLastCreated().getUserId()));
            result.add(row);
        }
        return result;
    }

    String getBatchResult(AccrualCollections ac) {
        String result = null;
        if (!ac.isPassedValidation()) {
            result = "Fail";
        }
        if (ac.getTotalImports() != null) {
            result = "Pass";
        }
        if (result == null) {
            Date td = new Date(new Date().getTime() 
                    - SubjectAccrualBeanLocal.BATCH_PROCESSING_THREAD_TIMEOUT_HOURS * ONE_HOUR);
            if (td.after(ac.getDateLastCreated())) {
                result = "Fail";
            }
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchGuiCompleteSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, String> usernames) throws PAException {
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
            row.setFileHtml("<a href='/accrual/protected/patients.action?studyProtocolId=" + trialId
                    + "'>Trial subjects</a>");
            row.setNciNumber(trials.get(trialId));
            row.setResult("Pass");
            row.setSubmissionType(AccrualSubmissionTypeCode.UI);
            row.setUsername(getRegistryUsername(usernames, subm[2] == null ? null : ((Integer) subm[2]).longValue()));
            result.add(row);
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchGuiAbbreviatedSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, String> usernames) throws PAException {
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
            row.setFileHtml("<a href='/accrual/protected/industrialPatients.action?studyProtocolId=" + trialId
                    + "'>Trial counts</a>");
            row.setNciNumber(trials.get(trialId));
            row.setResult("Pass");
            row.setSubmissionType(AccrualSubmissionTypeCode.UI);
            row.setUsername(getRegistryUsername(usernames, subm[2] == null ? null : ((Integer) subm[2]).longValue()));
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

    private String getRegistryUsername(Map<Long, String> usernames, Long ruId) {
        if (ruId == null) {
            return null;
        }
        if (usernames.get(ruId) ==  null) {
            Session session = PaHibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer(
                    "from RegistryUser ru join fetch ru.csmUser cu where cu.id = :identifier");
            Query query = session.createQuery(hql.toString());
            query.setParameter("identifier", ruId);
            List<Object> userList = query.list();
            if (CollectionUtils.isNotEmpty(userList)) {
                RegistryUser registryUser = (RegistryUser) userList.get(0);
                usernames.put(ruId, AccrualUtil.getDisplayName(registryUser));
            }
        }
        return usernames.get(ruId);
    }

    /**
     * @param searchTrialSvc the searchTrialSvc to set
     */
    public void setSearchTrialSvc(SearchTrialService searchTrialSvc) {
        this.searchTrialSvc = searchTrialSvc;
    }
}
