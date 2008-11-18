package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * .
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", 
    "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
public class OrganizationCorrelationServiceBean {
    
    private static final Logger LOG  = Logger.getLogger(OrganizationCorrelationServiceBean.class);
    private static final String CANCER_CENTER_CODE = "CCR";
    
    /**
     * 
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createHealthCareFacilityCorrelations(String orgPoIdentifier) throws PAException { 
        LOG.debug("Entering createHealthCareFacilityCorrelations");

        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().
                getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
           LOG.error("This Organization is no longer available instead use ");
           throw new PAException("This Organization is no longer available instead use ", e);
        }

        // Step 2 : check if PO has hcf correlation if not create one 
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        List<HealthCareFacilityDTO> hcfDTOs = null;
        hcfDTO.setPlayerIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
     
//        try {
            hcfDTOs = PoPaServiceBeanLookup.getHealthCareFacilityCorrelationService().search(hcfDTO);
//        } catch (NullifiedRoleException e) {
//            LOG.error("check with scoot", e);
//            // @todo: this should not happen, check with 
//        }
        if (hcfDTOs != null && hcfDTOs.size() > 1) {
            throw new PAException("PO hcfDTOs Correlation should not have more than 1  ");
        }
        if (hcfDTOs == null || hcfDTOs.isEmpty()) {
            try {
                Ii ii = PoPaServiceBeanLookup.getHealthCareFacilityCorrelationService().createCorrelation(hcfDTO);
                hcfDTO = PoPaServiceBeanLookup.getHealthCareFacilityCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                LOG.error("Validation exception during get ClinicalResearchStaff " , e);
                throw new PAException("Validation exception during get ClinicalResearchStaff " , e);
            } catch (EntityValidationException e) {
                LOG.error("Validation exception during create ClinicalResearchStaff " , e);
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
            } 
        } else {
            hcfDTO = hcfDTOs.get(0);
        }

        
        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIndetifers(null , orgPoIdentifier);
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }

        // Step 4 : Check of PA has hcf , if not create one
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setIdentifier(hcfDTO.getIdentifier().getExtension());
        hcf = getPAHealthCareFacility(hcf);
        if (hcf == null) {
            // create a new crs
            hcf = new HealthCareFacility();
            hcf.setOrganization(paOrg);
            hcf.setIdentifier(hcfDTO.getIdentifier().getExtension());
            hcf.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(hcfDTO.getStatus()));
            createPAHealthCareFacility(hcf);
        }
        LOG.debug("Leaving createClinicalResearchStaffCorrelation");
        return hcf.getId();
        
    }
    

    /**
     * 
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createResearchOrganizationCorrelations(String orgPoIdentifier) throws PAException { 
        LOG.debug("Entering createResearchOrganizationCorrelations");

        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().
                getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
           LOG.error("This Organization is no longer available instead use ");
           throw new PAException("This Organization is no longer available instead use ", e);
        }

        // Step 2 : check if PO has hcf correlation if not create one 
        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        List<ResearchOrganizationDTO> roDTOs = null;
        roDTO.setPlayerIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        Cd cd = new Cd();
        cd.setCode(CANCER_CENTER_CODE);
//        roDTO.setType(cd);
        roDTO.setTypeCode(cd);
        roDTO.setFundingMechanism(StConverter.convertToSt("foo"));
//        try {
            roDTOs = PoPaServiceBeanLookup.getResearchOrganizationCorrelationService().search(roDTO);
//        } catch (NullifiedRoleException e) {
//            LOG.error("check with scoot", e);
//            // @todo: this should not happen, check with 
//        }
        if (roDTOs != null && roDTOs.size() > 1) {
            throw new PAException("PO ResearchOrganizationDTOs Correlation should not have more than 1  ");
        }
        if (roDTOs == null || roDTOs.isEmpty()) {
            try {
                Ii ii = PoPaServiceBeanLookup.getResearchOrganizationCorrelationService().createCorrelation(roDTO);
                roDTO = PoPaServiceBeanLookup.getResearchOrganizationCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                LOG.error("Validation exception during get ClinicalResearchStaff " , e);
                throw new PAException("Validation exception during get ClinicalResearchStaff " , e);
            } catch (EntityValidationException e) {
                LOG.error("Validation exception during create ClinicalResearchStaff " , e);
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
            } 
        } else {
            roDTO = roDTOs.get(0);
        }

        
        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIndetifers(null , orgPoIdentifier);
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }

        // Step 4 : Check of PA has hcf , if not create one
        ResearchOrganization ro = new ResearchOrganization();
        ro.setIdentifier(roDTO.getIdentifier().getExtension());
        ro = getPAResearchOrganization(ro);
        if (ro == null) {
            // create a new crs
            ro = new ResearchOrganization();
            ro.setOrganization(paOrg);
            ro.setIdentifier(roDTO.getIdentifier().getExtension());
            ro.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(roDTO.getStatus()));
            corrUtils.createPADomain(ro);
        }
        LOG.debug("Leaving createResearchOrganizationCorrelation");
        return ro.getId();
        
    }
    
    /**
     * 
     * @param crs crs
     * @return crs
     * @throws PAException
     */
    private HealthCareFacility getPAHealthCareFacility(HealthCareFacility hcf) 
    throws PAException {
        if (hcf == null) {
            LOG.error("HealthCareFacility Staff cannot be null");
            throw new PAException("HealthCareFacility Staff cannot be null");
        }
        HealthCareFacility hcfOut = null;
        Session session = null;
        List<HealthCareFacility> queryList = new ArrayList<HealthCareFacility>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select hcf from HealthCareFacility hcf  " 
                + "join hcf.organization as org where 1 = 1 ");
        if (hcf.getId() != null) {
            hql.append(" and hcf.id = ").append(hcf.getId());
        }
        if (hcf.getOrganization() != null && hcf.getOrganization().getId() != null) {
            hql.append(" and org.id = ").append(hcf.getOrganization().getId());
        }
        if (hcf.getIdentifier() != null) {
            hql.append(" and hcf.identifier = '").append(hcf.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
        
        query = session.createQuery(hql.toString());
        queryList = query.list();
        
        if (queryList.size() > 1) {
            LOG.error(" HealthCareFacility should be more than 1 for any given criteria");
            throw new PAException(" HealthCareFacility should be more than 1 for any given criteria");
            
        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving HealthCareFacility" , hbe);
        throw new PAException(" Error while retrieving Clinicial Research Staff" , hbe);
    } finally {
        session.flush();
    }
    
    if (!queryList.isEmpty()) {
        hcfOut = queryList.get(0);
    }
    return hcfOut;
    }
    

    /**
     * 
     * @param crs crs
     * @return crs
     * @throws PAException
     */
    private ResearchOrganization getPAResearchOrganization(ResearchOrganization ro) 
    throws PAException {
        if (ro == null) {
            LOG.error("ResearchOrganization Staff cannot be null");
            throw new PAException("ResearchOrganization Staff cannot be null");
        }
        ResearchOrganization roOut = null;
        Session session = null;
        List<ResearchOrganization> queryList = new ArrayList<ResearchOrganization>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select ro from ResearchOrganization ro  " 
                + "join ro.organization as org where 1 = 1 ");
        if (ro.getId() != null) {
            hql.append(" and ro.id = ").append(ro.getId());
        }
        if (ro.getOrganization() != null && ro.getOrganization().getId() != null) {
            hql.append(" and org.id = ").append(ro.getOrganization().getId());
        }
        if (ro.getIdentifier() != null) {
            hql.append(" and ro.identifier = '").append(ro.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
        
        query = session.createQuery(hql.toString());
        queryList = query.list();
        
        if (queryList.size() > 1) {
            LOG.error(" ResearchOrganization should be more than 1 for any given criteria");
            throw new PAException(" ResearchOrganization should be more than 1 for any given criteria");
            
        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving ResearchOrganization" , hbe);
        throw new PAException(" Error while retrieving ResearchOrganization" , hbe);
    } finally {
        session.flush();
    }
    
    if (!queryList.isEmpty()) {
        roOut = queryList.get(0);
    }
    return roOut;
    }
    
    /**
     * 
     * @param crs ClinicalResearchStaff 
     * @return ClinicalResearchStaff
     * @throws PAException PAException
     */
    private HealthCareFacility createPAHealthCareFacility(HealthCareFacility hcf) throws PAException {
        if (hcf == null) {
            LOG.error(" HealthCareFacility should not be null ");
            throw new PAException(" HealthCareFacility should not be null ");
        }     
        LOG.debug("Entering createPAHealthCareFacility ");
        Session session = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(hcf);
        } catch (HibernateException hbe) {
            
            LOG.error(" Hibernate exception while createPAHealthCareFacility " , hbe);
            throw new PAException(" Hibernate exception while create createPAHealthCareFacility" , hbe);
        } finally {
            session.flush();
        }
        
        LOG.debug("Leaving create HealthCareFacility ");
        return hcf;
    }
    
    
    
    /***
     * 
     * @param studyProtocolId sp id
     * @param functionalCode functional code
     * @return List org
     * @throws PAException e
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    public List<Organization> getOrganizationByStudyParticipation(Long studyProtocolId , 
            StudyParticipationFunctionalCode functionalCode) throws PAException {

        Session session  = HibernateUtil.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select org from Organization as org ");
        if (StudyParticipationFunctionalCode.TREATING_SITE.equals(functionalCode)) {
            sb.append(" join org.healthCareFacilities as orgRole  ");
        } else if (StudyParticipationFunctionalCode.COLLABORATORS.equals(functionalCode)) {
            sb.append(" join org.researchOrganizations as orgRole  ");
        } else if (StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.equals(functionalCode)) {
            sb.append(" join org.healthCareFacilities as orgRole  ");
        }
        
        sb.append(" join org.researchOrganizations as orgRole  "
                + " join orgRole.studyParticipations as sps "
                + " join sps.studyProtocol as sp "
                + " where 1 = 1 and sp.id = " + studyProtocolId);
        if (StudyParticipationFunctionalCode.TREATING_SITE.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('" + StudyParticipationFunctionalCode.TREATING_SITE + "')");
        } else if (StudyParticipationFunctionalCode.COLLABORATORS.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in (" 
                    + "'" + StudyParticipationFunctionalCode.FUNDING_SOURCE + "',"
                    + "'" + StudyParticipationFunctionalCode.LABORATORY + "',"
                    + "'" + StudyParticipationFunctionalCode.AGENT_SOURCE + "')");
        } else if (StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('" 
                    + StudyParticipationFunctionalCode.LEAD_ORAGANIZATION + "')");
        }
        List<Organization> queryList = new ArrayList<Organization>();
        try {
            Query query = null;
            query = session.createQuery(sb.toString());
            //query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving StudyProtocol for id = " + studyProtocolId , hbe);
            throw new PAException(" Hibernate exception while retrieving "
                    + "StudyProtocol for id = " + studyProtocolId , hbe);
            
        }

        return queryList; 
        
    }
    
    /**
     * 
     * @param poOrg po
     * @return Organization o
     * @throws PAException pe
     */
    public Organization createPAOrganizationUsingPO(OrganizationDTO poOrg) throws PAException {
        return new CorrelationUtils().createPAOrganization(poOrg);
    }
    

}
