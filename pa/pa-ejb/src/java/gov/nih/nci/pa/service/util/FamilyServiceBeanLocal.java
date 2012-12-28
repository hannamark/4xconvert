package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
 * @since Nov 15, 2012
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FamilyServiceBeanLocal implements FamilyServiceLocal {

    private static final String ORG_IDS = "orgIds";

    @EJB
    private StudySiteAccrualAccessServiceLocal studySiteAccess;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private DocumentWorkflowStatusServiceLocal dwsService;
    @EJB
    private ParticipatingOrgServiceLocal participatingOrgService;

    static final String HQL_COMPLETE = "SELECT sp.id "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.researchOrganization ro "
            + "INNER JOIN ro.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sp.proprietaryTrialIndicator = false "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :siteCode "
            + "  AND org.identifier IN (:orgIds) ";

    static final String HQL_ABBR = "SELECT sp.id "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.healthCareFacility hf "
            + "INNER JOIN hf.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sp.proprietaryTrialIndicator = true "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :siteCode "
            + "  AND org.identifier IN (:orgIds) ";

    private static final String HQL_LEAD_ORG = "SELECT org.identifier "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.researchOrganization ro "
            + "INNER JOIN ro.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :siteCode "
            + "  AND sp.id = :trialId ";

    private static final String HQL_SITE_TRIAL_SUBMITTERS = "FROM RegistryUser "
            + "WHERE (affiliatedOrganizationId IN (:orgIds) AND siteAccrualSubmitter = true) ";

    private static final String HQL_FAMILY_TRIAL_SUBMITTERS = "FROM RegistryUser "
            + "WHERE (affiliatedOrganizationId IN (:orgIds) AND familyAccrualSubmitter = true) ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignFamilyAccrualAccess(RegistryUser user, RegistryUser creator, String comment) throws PAException {
        if (user == null) {
            return;
        }
        List<Long> poOrgIds =  FamilyHelper.getAllRelatedOrgs(user.getAffiliatedOrganizationId());
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            Set<Long> trialIds = getSiteAccrualTrials(poOrgIds);
            assignFamilyAccess(trialIds, user, creator, comment);
        }
        user.setFamilyAccrualSubmitter(true);
        registryUserService.updateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Long> getSiteAccrualTrials(Long poOrgId) throws PAException {
        List<Long> poOrgList = poOrgId == null ? new ArrayList<Long>() : Arrays.asList(poOrgId);
        return getSiteAccrualTrials(poOrgList);
    }

    @SuppressWarnings("unchecked")
    private Set<Long> getSiteAccrualTrials(List<Long> poOrgIds) throws PAException {
        Set<Long> result = new HashSet<Long>();
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query  query = session.createQuery(HQL_COMPLETE);
            query.setParameter("statusCode", ActStatusCode.ACTIVE);
            query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
            query.setParameter("siteCode", StudySiteFunctionalCode.LEAD_ORGANIZATION);
            query.setParameterList(ORG_IDS, convertPoOrgIdsToStrings(poOrgIds));
            List<Long> queryList = query.list();
            for (Long trialId : queryList) {
                result.add(trialId);
            }
            query = session.createQuery(HQL_ABBR);
            query.setParameter("statusCode", ActStatusCode.ACTIVE);
            query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
            query.setParameter("siteCode", StudySiteFunctionalCode.TREATING_SITE);
            query.setParameterList(ORG_IDS, convertPoOrgIdsToStrings(poOrgIds));
            queryList = query.list();
            for (Long trialId : queryList) {
                result.add(trialId);
            }
        }
        return result;
    }

    private void assignFamilyAccess(Set<Long> trialIds, RegistryUser user, RegistryUser creator, String comment)
            throws PAException {
        Set<Long> idsForAccess = new HashSet<Long>();
        for (Long trialId : trialIds) {
            if (isEligibleForAccrual(trialId)) {
                idsForAccess.add(trialId);
            }
        }
        studySiteAccess.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE,
                idsForAccess, comment, creator);
    }

    private boolean isEligibleForAccrual(Long trialId) throws PAException {
        boolean result = false;
        DocumentWorkflowStatusDTO dws = dwsService.getCurrentByStudyProtocol(
                IiConverter.convertToStudyProtocolIi(trialId));
        if (dws != null) {
            DocumentWorkflowStatusCode code = CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class, 
                    dws.getStatusCode());
            result = code.isEligibleForAccrual();
        }
        return result;
    }

    static Set<String> convertPoOrgIdsToStrings(List<Long> poOrgIds) throws PAException {
        Set<String> orgIdsString = new HashSet<String>();
        for (Long id : poOrgIds) {
            orgIdsString.add(id.toString());
        }
        return orgIdsString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAllAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException {
        if (user == null) {
            return;
        }
        if (creator == null) {
            throw new PAException("Calling FamilyServiceBeanLocal.unassignFamilyAccrualAccess with creator == null.");
        }
        List<Long> trialIds = studySiteAccess.getActiveTrialLevelAccrualAccess(user);
        studySiteAccess.unassignTrialLevelAccrualAccess(user, 
                AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, trialIds, null, creator);
        List<StudySiteAccrualAccessDTO> list = studySiteAccess.getActiveByUser(user.getId());
        studySiteAccess.removeStudySiteAccrualAccess(user, list, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
        user.setFamilyAccrualSubmitter(false);
        user.setSiteAccrualSubmitter(false);
        registryUserService.updateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSiteAndFamilyPermissions(Long trialId) throws PAException {
        if (trialId == null) {
            return;
        }
        Map<RegistryUser, AccrualAccessSourceCode> users;
        if (isIndustrialTrial(trialId)) {
            Set<Long> partSites = getParticipatingSiteOrgIds(trialId);
            users = getUsersForAccess(partSites);
        } else {
            Set<Long> leadOrgPoId = getLeadOrgId(trialId);
            users = getUsersForAccess(leadOrgPoId);
        }
        for (Map.Entry<RegistryUser, AccrualAccessSourceCode> user : users.entrySet()) {
            studySiteAccess.assignTrialLevelAccrualAccess(user.getKey(), user.getValue(),
                    Arrays.asList(trialId), null, null);
        }
    }

    @SuppressWarnings("unchecked")
    private Set<Long> getLeadOrgId(Long trialId) {
        Set<Long> result = new HashSet<Long>();
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(HQL_LEAD_ORG);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
        query.setParameter("siteCode", StudySiteFunctionalCode.LEAD_ORGANIZATION);
        query.setParameter("trialId", trialId);
        List<String> queryList = query.list();
        if (CollectionUtils.isNotEmpty(queryList)) {
            result.add(Long.valueOf(queryList.get(0)));
        }
        return result;
    }

    private Set<Long> getParticipatingSiteOrgIds(Long trialId) throws PAException {
        Set<Long> result = new HashSet<Long>();
        List<ParticipatingOrgDTO> sites = participatingOrgService.getTreatingSites(trialId);
        for (ParticipatingOrgDTO site : sites) {
            result.add(Long.valueOf(site.getPoId()));
        }
        return result;
    }

    private boolean isIndustrialTrial(Long trialID) {
        Session session = PaHibernateUtil.getCurrentSession();
        return (Boolean) session.createQuery(
                "select sp.proprietaryTrialIndicator from StudyProtocol sp where sp.id=" + trialID).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    private Map<RegistryUser, AccrualAccessSourceCode> getUsersForAccess(Collection<Long> orgIds) throws PAException {
        Map<RegistryUser, AccrualAccessSourceCode> result = new HashMap<RegistryUser, AccrualAccessSourceCode>();
        if (CollectionUtils.isNotEmpty(orgIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = session.createQuery(HQL_SITE_TRIAL_SUBMITTERS);
            query.setParameterList(ORG_IDS, orgIds);
            List<RegistryUser> queryList = query.list();
            for (RegistryUser user : queryList) {
                result.put(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE);
            }
            Collection<Long> relatedOrgs = FamilyHelper.getAllRelatedOrgs(orgIds);
            if (CollectionUtils.isNotEmpty(relatedOrgs)) {
                query = session.createQuery(HQL_FAMILY_TRIAL_SUBMITTERS);
                query.setParameterList(ORG_IDS, relatedOrgs);
                queryList = query.list();
                for (RegistryUser user : queryList) {
                    result.put(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
                }
            }
        }
        return result;
    }
    /**
     * @param studySiteAccess the studySiteAccess to set
     */
    public void setStudySiteAccess(StudySiteAccrualAccessServiceLocal studySiteAccess) {
        this.studySiteAccess = studySiteAccess;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param dwsService the dwsService to set
     */
    public void setDwsService(DocumentWorkflowStatusServiceLocal dwsService) {
        this.dwsService = dwsService;
    }

    /**
     * @param participatingOrgService the participatingOrgService to set
     */
    public void setParticipatingOrgService(ParticipatingOrgServiceLocal participatingOrgService) {
        this.participatingOrgService = participatingOrgService;
    }
}
