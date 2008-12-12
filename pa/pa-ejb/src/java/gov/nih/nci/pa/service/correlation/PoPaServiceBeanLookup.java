package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public class PoPaServiceBeanLookup  {

    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    public static PersonEntityServiceRemote getPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }    
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OversightCommitteeCorrelationServiceRemote
        getOversightCommitteeCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OversightCommitteeCorrelationServiceBean/remote";
        return (OversightCommitteeCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException e
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()  
    throws PAException { 
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error
     */
    public static HealthCareProviderCorrelationServiceRemote 
        getHealthCareProviderCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static HealthCareFacilityCorrelationServiceRemote
        getHealthCareFacilityCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static ResearchOrganizationCorrelationServiceRemote
        getResearchOrganizationCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/ResearchOrganizationCorrelationServiceBean/remote";
        return (ResearchOrganizationCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationalContactCorrelationServiceRemote
        getOrganizationalContactCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OrganizationalContactCorrelationServiceBean/remote";
        return (OrganizationalContactCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyProtocolServiceRemote
        getStudyProtocolService() throws PAException {
        return (StudyProtocolServiceRemote) JNDIUtil.lookup("/pa/StudyProtocolServiceBean/remote");
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyParticipationServiceRemote
        getStudyParticipationService() throws PAException {
        return (StudyParticipationServiceRemote) JNDIUtil.lookup("/pa/StudyParticipationServiceBean/remote");
    } 

    /**
     * @return StudyParticipationContactRemote
     * @throws PAException on error
     */
    public static StudyParticipationContactServiceRemote
        getStudyParticipationContactService() throws PAException {
        return (StudyParticipationContactServiceRemote) 
            JNDIUtil.lookup("/pa/StudyParticipationContactServiceBean/remote");
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyContactServiceRemote
        getStudyContactService() throws PAException {
        return (StudyContactServiceRemote) JNDIUtil.lookup("/pa/StudyContactServiceBean/remote");
    } 

    /**
     * @return StudyResourcingServiceRemote
     * @throws PAException on error
     */
    public static StudyResourcingServiceRemote
        getStudyResourcingService() throws PAException {
        return (StudyResourcingServiceRemote) JNDIUtil.lookup("/pa/StudyResourcingServiceBean/remote");
    } 
    
    /**
     * @return StudyOutcomeMeasureServiceRemote
     * @throws PAException on error
     */
    public static StudyOutcomeMeasureServiceRemote
        getStudyOutcomeMeasureService() throws PAException {
        return (StudyOutcomeMeasureServiceRemote) JNDIUtil.lookup("/pa/StudyOutcomeMeasureServiceBean/remote");
    } 
    /**
     * @return ProtocolQueryServiceRemote
     * @throws PAException on error
     */
    public static ProtocolQueryServiceLocal
        getProtocolQueryService() throws PAException {
        return (ProtocolQueryServiceLocal) JNDIUtil.lookup("/pa/ProtocolQueryServiceBean/local");
    } 
    /**
     * @return ArmServiceRemote
     * @throws PAException on error
     */
    public static ArmServiceRemote
        getArmService() throws PAException {
        return (ArmServiceRemote) JNDIUtil.lookup("/pa/ArmServiceBean/remote");
    } 
    
    /**
     * @return PlannedActivityServiceRemote
     * @throws PAException on error
     */
    public static PlannedActivityServiceRemote
        getPlannedActivityService() throws PAException {
        return (PlannedActivityServiceRemote) JNDIUtil.lookup("/pa/PlannedActivityServiceBean/remote");
    } 
    
    /**
     * @return StudySiteAccrualStatusServiceRemote
     * @throws PAException on error
     */
    public static StudySiteAccrualStatusServiceRemote
        getStudySiteAccrualStatusService() throws PAException {
        return (StudySiteAccrualStatusServiceRemote) JNDIUtil.lookup("/pa/StudySiteAccrualStatusServiceBean/remote");
    } 

    /**
     * @return StudyIndldeServiceRemote
     * @throws PAException on error
     */
    public static StudyIndldeServiceRemote
        getStudyIndldeService() throws PAException {
        return (StudyIndldeServiceRemote) JNDIUtil.lookup("/pa/StudyIndldeServiceBean/remote");
    } 

    /**
     * @return StudyRegulatoryAuthorityServiceRemote
     * @throws PAException on error
     */
    public static StudyRegulatoryAuthorityServiceRemote
        getStudyRegulatoryAuthorityService() throws PAException {
        return (StudyRegulatoryAuthorityServiceRemote) 
            JNDIUtil.lookup("/pa/StudyRegulatoryAuthorityServiceBean/remote");
    } 

    /**
     * @return RegulatoryInformationServiceRemote
     * @throws PAException on error
     */
    public static RegulatoryInformationServiceRemote
        getRegulatoryInformationService() throws PAException {
        return (RegulatoryInformationServiceRemote) JNDIUtil.lookup("/pa/RegulatoryInformationBean/remote");
    } 

    /**
     * @return StudyOverallStatusServiceRemote
     * @throws PAException on error
     */
    public static StudyOverallStatusServiceRemote
        getStudyOverallStatusService() throws PAException {
        return (StudyOverallStatusServiceRemote) JNDIUtil.lookup("/pa/StudyOverallStatusServiceBean/remote");
    } 

    /**
     * @return InterventionServiceRemote
     * @throws PAException on error
     */
    public static InterventionServiceRemote
        getInterventionService() throws PAException {
        return (InterventionServiceRemote) JNDIUtil.lookup("/pa/InterventionServiceBean/remote");
    } 
    
    /**
     * @return DocumentServiceRemote
     * @throws PAException on error
     */
    public static DocumentServiceRemote
        getDocumentService() throws PAException {
        return (DocumentServiceRemote) JNDIUtil.lookup("/pa/DocumentServiceBean/remote");
    } 


    /**
     * @return OrganizationSynchronizationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationSynchronizationServiceRemote
        getOrganizationSynchronizationService() throws PAException {
        return (OrganizationSynchronizationServiceRemote) 
            JNDIUtil.lookup("/pa/OrganizationSynchronizationServiceBean/remote");
    } 
    
    /**
     * @return PersonSynchronizationServiceRemote
     * @throws PAException on error
     */
    public static PersonSynchronizationServiceRemote
        getPersonSynchronizationService() throws PAException {
        return (PersonSynchronizationServiceRemote) 
            JNDIUtil.lookup("/pa/PersonSynchronizationServiceBean/remote");
    } 

    /**
     * @return InterventionAlternateNameServiceRemote
     * @throws PAException on error
     */
    public static InterventionAlternateNameServiceRemote getInterventionAlternateNameService() throws PAException  {
        return (InterventionAlternateNameServiceRemote) 
        JNDIUtil.lookup("/pa/InterventionAlternateNameServiceBean/remote");
        
    }

    /**
     * @return RegistryUserServiceRemote
     * @throws PAException on error
     */
    public static RegistryUserServiceRemote getRegistryUserService() throws PAException  {
        return (RegistryUserServiceRemote) 
        JNDIUtil.lookup("/pa/RegistryUserServiceBean/remote");
        
    }
}
