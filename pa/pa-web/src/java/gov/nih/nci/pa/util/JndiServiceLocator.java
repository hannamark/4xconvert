package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.SubGroupsServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PAResearchOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class JndiServiceLocator implements ServiceLocator {
    /**
     * @return protocol service
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return (StudyProtocolServiceRemote) JNDIUtil.lookup("pa/StudyProtocolServiceBean/remote");
    }

    /**
     * @return PAOrganizationServiceRemote remote interface
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return (PAOrganizationServiceRemote) JNDIUtil.lookup("pa/PAOrganizationServiceBean/remote");
    }

    /**
     * @return DiseaseConditionService
     */
    public DiseaseCondServiceRemote getDiseaseConditionService() {
        return (DiseaseCondServiceRemote) JNDIUtil.lookup("pa/DiseaseCondServiceBean/remote");
    }

    /**
     * @return PAPersonService
     */
    public PAPersonServiceRemote getPAPersonService() {
        return (PAPersonServiceRemote) JNDIUtil.lookup("pa/PAPersonServiceBean/remote");
    }

    /**
     * @return RegulatoryInformationServiceRemote
     */
    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return (RegulatoryInformationServiceRemote) JNDIUtil.lookup("pa/RegulatoryInformationBean/remote");
    }

    /**
     * @return StudyIndldeServiceRemote
     *
     */
    public StudyIndldeServiceRemote getStudyIndldeService() {
        return (StudyIndldeServiceRemote) JNDIUtil.lookup("pa/StudyIndldeServiceBean/remote");
    }
    
    /**
     * @return StudyOverallStatusServiceRemote
     */
    public StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return (StudyOverallStatusServiceRemote) JNDIUtil.lookup("pa/StudyOverallStatusServiceBean/remote");
    }

    /**
     * @return StudyResourcingServiceRemote
     */
    public StudyResourcingServiceRemote getStudyResoucringService() {
        return (StudyResourcingServiceRemote) JNDIUtil.lookup("pa/StudyResourcingServiceBean/remote");
    }

    /**
     * @return StudyResourcingServiceRemote
     */
    public StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() {
        return (StudyRegulatoryAuthorityServiceRemote) JNDIUtil.lookup("pa/StudyRegulatoryAuthorityServiceBean/remote");
    }

    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public OrganizationEntityServiceRemote getPoOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return LookUpTableServiceRemote
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        return (LookUpTableServiceRemote) JNDIUtil.lookup("pa/LookUpTableServiceBean/remote");
    }
    
    /**
     * @return ProtocolQueryServiceRemote
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return (ProtocolQueryServiceLocal) JNDIUtil.lookup("pa/ProtocolQueryServiceBean/local");
    }

    /**
     * @return HealthCareFacilityService
     */
    public PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService() {
        return (PAHealthCareFacilityServiceRemote) JNDIUtil.lookup("pa/PAHealthCareFacilityServiceBean/remote");
    }
    /**
     * @return ResearchOrganizationService
     */
    public PAResearchOrganizationServiceRemote getPAResearchOrganizationService() {
        return (PAResearchOrganizationServiceRemote) JNDIUtil.lookup("pa/PAResearchOrganizationServiceBean/remote");
    }
    /**
     * @return StudyParticipationService
     */
    public StudyParticipationServiceRemote getStudyParticipationService() {
        return (StudyParticipationServiceRemote) JNDIUtil.lookup("pa/StudyParticipationServiceBean/remote");
    }

    /**
     * @return StudySiteAccrualStatusService
     */
    public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
        return (StudySiteAccrualStatusServiceRemote) JNDIUtil.lookup("pa/StudySiteAccrualStatusServiceBean/remote");
    }
    /**
     * @return DocumentService
     */
    public DocumentServiceRemote getDocumentService() {
        return (DocumentServiceRemote) JNDIUtil.lookup("pa/DocumentServiceBean/remote");
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * @return SubGroupsService
     */
    public SubGroupsServiceRemote getSubGroupsService() {
        return (SubGroupsServiceRemote) JNDIUtil.lookup("pa/SubGroupsServiceBean/remote");
    }
    
    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    public PersonEntityServiceRemote getPoPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }    
    
    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error
     */
    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return PAHealthCareProviderRemote
     */
    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return (PAHealthCareProviderRemote) JNDIUtil.lookup(
                                            "pa/PAHealthCareProviderServiceBean/remote");
    }
    
    /**
     * @return StudyParticipationService
     */
    public StudyParticipationContactServiceRemote getStudyParticipationContactService() {
        return (StudyParticipationContactServiceRemote) JNDIUtil.lookup(
                                            "pa/StudyParticipationContactServiceBean/remote");
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        return (InterventionAlternateNameServiceRemote) JNDIUtil.lookup(
                    "pa/InterventionAlternateNameServiceBean/remote");
    }

    /**
     * @return InterventionServiceRemote
     */
    public InterventionServiceRemote getInterventionService() {
        return (InterventionServiceRemote) JNDIUtil.lookup("pa/InterventionServiceBean/remote");
    }

    /**
     * @return PlannedActivityServiceRemote
     */
    public PlannedActivityServiceRemote getPlannedActivityService() {
        return (PlannedActivityServiceRemote) JNDIUtil.lookup("pa/PlannedActivityServiceBean/remote");
    }
    /**
     * @return OutcomeMeasureServiceRemote
     */
    public StudyOutcomeMeasureServiceRemote getOutcomeMeasurService() {
        return (StudyOutcomeMeasureServiceRemote) JNDIUtil.lookup("pa/StudyOutcomeMeasureServiceBean/remote");
    }
    /**
     * @return ArmServcieRemote
     */
    public ArmServiceRemote getArmService() {
        return (ArmServiceRemote) JNDIUtil.lookup("pa/ArmServiceBean/remote");
    }
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException e
     */
    public ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService()  
    throws PAException { 
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }
    /**
     * @return CTGovXmlGeneratorServiceRemote
     * @throws PAException e
     */
    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService()  
    throws PAException { 
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/pa/CTGovXmlGeneratorServiceBean/remote";
        return (CTGovXmlGeneratorServiceRemote) JNDIUtil.lookup(serverInfo);
    }


}
