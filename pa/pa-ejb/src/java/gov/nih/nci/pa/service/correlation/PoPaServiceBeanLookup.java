package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
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
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyProtocolServiceBean/remote";
        return (StudyProtocolServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyParticipationServiceRemote
        getStudyParticipationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyParticipationServiceBean/remote";
        return (StudyParticipationServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyParticipationContactRemote
     * @throws PAException on error
     */
    public static StudyParticipationContactServiceRemote
        getStudyParticipationContactService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyParticipationContactServiceBean/remote";
        return (StudyParticipationContactServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyContactServiceRemote
        getStudyContactService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyContactServiceBean/remote";
        return (StudyContactServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyResourcingServiceRemote
     * @throws PAException on error
     */
    public static StudyResourcingServiceRemote
        getStudyResourcingService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyResourcingServiceBean/remote";
        return (StudyResourcingServiceRemote) JNDIUtil.lookup(serverInfo);
    } 
    
    /**
     * @return StudyOutcomeMeasureServiceRemote
     * @throws PAException on error
     */
    public static StudyOutcomeMeasureServiceRemote
        getStudyOutcomeMeasureService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyOutcomeMeasureServiceBean/remote";
        return (StudyOutcomeMeasureServiceRemote) JNDIUtil.lookup(serverInfo);
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
     * @return ProtocolQueryServiceRemote
     * @throws PAException on error
     */
    public static ProtocolQueryServiceLocal
        getProtocolQueryService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/ProtocolQueryServiceBean/local";
        return (ProtocolQueryServiceLocal) JNDIUtil.lookup(serverInfo);
    } 
    /**
     * @return ArmServiceRemote
     * @throws PAException on error
     */
    public static ArmServiceRemote
        getArmService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/ArmServiceBean/remote";
        return (ArmServiceRemote) JNDIUtil.lookup(serverInfo);
    } 
    
    /**
     * @return PlannedActivityServiceRemote
     * @throws PAException on error
     */
    public static PlannedActivityServiceRemote
        getPlannedActivityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/PlannedActivityServiceBean/remote";
        return (PlannedActivityServiceRemote) JNDIUtil.lookup(serverInfo);
    } 
    
    /**
     * @return StudySiteAccrualStatusServiceRemote
     * @throws PAException on error
     */
    public static StudySiteAccrualStatusServiceRemote
        getStudySiteAccrualStatusService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudySiteAccrualStatusServiceBean/remote";
        return (StudySiteAccrualStatusServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyIndldeServiceRemote
     * @throws PAException on error
     */
    public static StudyIndldeServiceRemote
        getStudyIndldeService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyIndldeServiceBean/remote";
        return (StudyIndldeServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyRegulatoryAuthorityServiceRemote
     * @throws PAException on error
     */
    public static StudyRegulatoryAuthorityServiceRemote
        getStudyRegulatoryAuthorityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyRegulatoryAuthorityServiceBean/remote";
        return (StudyRegulatoryAuthorityServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return RegulatoryInformationServiceRemote
     * @throws PAException on error
     */
    public static RegulatoryInformationServiceRemote
        getRegulatoryInformationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/RegulatoryInformationBean/remote";
        return (RegulatoryInformationServiceRemote) JNDIUtil.lookup(serverInfo);
    } 
    
}
