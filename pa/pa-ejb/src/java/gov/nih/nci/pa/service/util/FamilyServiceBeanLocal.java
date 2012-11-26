package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Hugh Reinhart
 * @since Nov 15, 2012
 */
@Stateless
@SuppressWarnings("unchecked")
public class FamilyServiceBeanLocal implements FamilyServiceLocal {

    @EJB
    private StudySiteAccrualAccessServiceLocal studySiteAccess;
    @EJB
    private RegistryUserServiceLocal registryUserService;

    private static final String HQL_LEAD = "SELECT sp.id "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.researchOrganization ro "
            + "INNER JOIN ro.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :leadOrgCode "
            + "  AND org.identifier IN (:orgIds) ";

    private static final String HQL_TREATING = "SELECT ss.id "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.healthCareFacility hf "
            + "INNER JOIN hf.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sp.proprietaryTrialIndicator = true "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :treatingCode "
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
            + "  AND ss.functionalCode = :leadOrgCode "
            + "  AND sp.id = :trialId ";

    private static final String HQL_SITE_TRIAL_SUBMITTERS = "FROM RegistryUser "
            + "WHERE (affiliatedOrganizationId = :leadOrgId AND siteAccrualSubmitter = true) ";

    private static final String HQL_FAMILY_TRIAL_SUBMITTERS = "FROM RegistryUser "
            + "WHERE (affiliatedOrganizationId IN (:familyOrgIds) AND familyAccrualSubmitter = true) ";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrgFamilyDTO> getByOrgId(Long orgId) throws PAException {
        List<OrgFamilyDTO> result = new ArrayList<OrgFamilyDTO>();
        if (orgId == null) {
            return result;
        }
        OrganizationDTO orgCriteria = new OrganizationDTO();
        orgCriteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            List<OrganizationDTO> orgList = PoRegistry.getOrganizationEntityService().search(orgCriteria, limit);
            if (CollectionUtils.isEmpty(orgList)) {
                return result;
            }
            OrganizationDTO org = orgList.get(0);
            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            if (CollectionUtils.isNotEmpty(org.getFamilyOrganizationRelationships().getItem())) {
                famOrgRelIiList.addAll(org.getFamilyOrganizationRelationships().getItem());
            }
            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
            for (FamilyDTO dto : familyMap.values()) {
                OrgFamilyDTO family = new OrgFamilyDTO();
                family.setId(IiConverter.convertToLong(dto.getIdentifier()));
                family.setName(EnOnConverter.convertEnOnToString(dto.getName()));
                result.add(family);
            }
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getAllRelatedOrgs(Long orgId) throws PAException {
        List<OrgFamilyDTO> families = getByOrgId(orgId);
        Set<Long> result = new HashSet<Long>();
        for (OrgFamilyDTO family : families) {
            List<FamilyOrganizationRelationshipDTO> rList = PoRegistry.getFamilyService()
                    .getActiveRelationships(family.getId());
            for (FamilyOrganizationRelationshipDTO r : rList) {
                result.add(IiConverter.convertToLong(r.getOrgIdentifier()));
            }
        }
        return new ArrayList<Long>(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignFamilyAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException {
        if (user == null) {
            return;
        }
        if (creator == null) {
            throw new PAException("Calling FamilyServiceBeanLocal.assignFamilyAccrualAccess with creator == null.");
        }
        List<Long> poOrgIds =  getAllRelatedOrgs(user.getAffiliatedOrganizationId());
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            assignTrialLevelAccessWhereLeadOrg(poOrgIds, user, creator);
            assignSiteLevelAccessWhereTreatingSiteAndIndustrial(poOrgIds, user, 
                    AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
        }
        user.setFamilyAccrualSubmitter(true);
        registryUserService.updateUser(user);
    }

    private void assignTrialLevelAccessWhereLeadOrg(List<Long> poOrgIds, RegistryUser user, RegistryUser creator)
            throws PAException {
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query  query = session.createQuery(HQL_LEAD);
            query.setParameter("statusCode", ActStatusCode.ACTIVE);
            query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
            query.setParameter("leadOrgCode", StudySiteFunctionalCode.LEAD_ORGANIZATION);
            query.setParameterList("orgIds", convertPoOrgIdsToStrings(poOrgIds));
            List<Long> queryList = query.list();
            if (CollectionUtils.isNotEmpty(queryList)) {
                Set<Long> trialIds = new HashSet<Long>();
                trialIds.addAll(queryList);
                studySiteAccess.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE,
                        trialIds, null, creator);
            }
        }
    }

    private void assignSiteLevelAccessWhereTreatingSiteAndIndustrial(List<Long> poOrgIds, 
            RegistryUser user, AccrualAccessSourceCode source) throws PAException {
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = session.createQuery(HQL_TREATING);
            query.setParameter("statusCode", ActStatusCode.ACTIVE);
            query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
            query.setParameter("treatingCode", StudySiteFunctionalCode.TREATING_SITE);
            query.setParameterList("orgIds", convertPoOrgIdsToStrings(poOrgIds));
            List<Long> queryList = query.list();
            Set<Long> siteIds = new HashSet<Long>();
            siteIds.addAll(queryList);
            for (Long siteId : siteIds) {
                studySiteAccess.createStudySiteAccrualAccess(user.getId(), siteId, source); 
            }
        }
    }

    private Set<String> convertPoOrgIdsToStrings(List<Long> poOrgIds) throws PAException {
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
    public void unassignFamilyAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException {
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
        Long leadOrgPoId = getLeadOrgId(trialId);
        if (leadOrgPoId != null) {
            trialAccessToUsers(trialId, leadOrgPoId);
            if (isIndustrialTrial(trialId)) {
                siteAccessToUsers(trialId);
            }
        }
    }
    
    private Long getLeadOrgId(Long trialId) {
        if (trialId == null) {
            return null;
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(HQL_LEAD_ORG);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
        query.setParameter("leadOrgCode", StudySiteFunctionalCode.LEAD_ORGANIZATION);
        query.setParameter("trialId", trialId);
        List<String> queryList = query.list();
        if (CollectionUtils.isEmpty(queryList)) {
            return null;
        }
        return Long.valueOf(queryList.get(0));
    }

    private boolean isIndustrialTrial(Long trialID) {
        Session session = PaHibernateUtil.getCurrentSession();
        return (Boolean) session.createQuery(
                "select sp.proprietaryTrialIndicator from StudyProtocol sp where sp.id=" + trialID).uniqueResult();
    }

    private void trialAccessToUsers(Long trialId, Long poOrgId) throws PAException {
        RegistryUser creator = new RegistryUser();
        creator.setCsmUser(CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser()));
        Map<RegistryUser, AccrualAccessSourceCode> users = getUsersForAccess(poOrgId);
        for (Map.Entry<RegistryUser, AccrualAccessSourceCode> user : users.entrySet()) {
            studySiteAccess.assignTrialLevelAccrualAccess(user.getKey(), user.getValue(),
                    Arrays.asList(trialId), null, creator);
        }
    }

    private void siteAccessToUsers(Long trialId) throws PAException {
        Map<Long, Organization> sites = studySiteAccess.getTreatingOrganizations(trialId);
        for (Long siteId : sites.keySet()) {
            Map<RegistryUser, AccrualAccessSourceCode> users = getUsersForAccess(siteId);
            for (Map.Entry<RegistryUser, AccrualAccessSourceCode> user : users.entrySet()) {
                studySiteAccess.createStudySiteAccrualAccess(user.getKey().getId(), siteId, user.getValue());
            }
        }

    }

    private Map<RegistryUser, AccrualAccessSourceCode> getUsersForAccess(Long poOrgId) throws PAException {
        Map<RegistryUser, AccrualAccessSourceCode> result = new HashMap<RegistryUser, AccrualAccessSourceCode>();
        List<Long> relatedOrgs = getAllRelatedOrgs(poOrgId);
        if (CollectionUtils.isNotEmpty(relatedOrgs)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = session.createQuery(HQL_FAMILY_TRIAL_SUBMITTERS);
            query.setParameterList("familyOrgIds", relatedOrgs);
            List<RegistryUser> queryList = query.list();
            for (RegistryUser user : queryList) {
                result.put(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
            }
            query = session.createQuery(HQL_SITE_TRIAL_SUBMITTERS);
            query.setParameter("leadOrgId", poOrgId);
            queryList = query.list();
            for (RegistryUser user : queryList) {
                result.put(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE);
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
}
