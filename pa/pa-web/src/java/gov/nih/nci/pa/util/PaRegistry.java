package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.DiseaseAlternameServiceRemote;
import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceRemote;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
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
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
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
public final class PaRegistry {
    /**
     * Number of records to display by default in display tag controlled tables.
     */
    public static final int DEFAULT_RECORDS_PER_PAGE = 20;
    private static final PaRegistry PA_REGISTRY = new PaRegistry();
    private ServiceLocator serviceLocator;

    /**
     * Constructor for the singleton instance.
     */
    private PaRegistry() {
        this.serviceLocator = new JndiServiceLocator();
    }

    /**
     * @return the PO_REGISTRY
     */
    public static PaRegistry getInstance() {
        return PA_REGISTRY;
    }

    /**
     * 
     * @return diseaseCondServiceRemote DiseaseCondServiceRemote
     */
    public static DiseaseCondServiceRemote getDiseaseCondService() {
        return getInstance().getServiceLocator().getDiseaseConditionService();
    }

    /**
     * Gets the org service from the service locator.
     * 
     * @return the service.
     */
    public static StudyProtocolServiceRemote getStudyProtocolService() {
        return getInstance().getServiceLocator().getStudyProtocolService();
    }

    /**
     * Gets the org service from the service locator.
     * 
     * @return PAOrganizationServiceRemote
     */
    public static PAOrganizationServiceRemote getPAOrganizationService() {
        return getInstance().getServiceLocator().getPAOrganizationService();
    }

    /**
     * @return the serviceLocator
     */
    public ServiceLocator getServiceLocator() {
        return this.serviceLocator;
    }

    /**
     * @param serviceLocator the serviceLocator to set
     */
    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    /**
     * @return PAPerson Service.
     */
    public static PAPersonServiceRemote getPAPersonService() {
        return getInstance().getServiceLocator().getPAPersonService();
    }

    /**
     * 
     * @return RegulatoryInformationServiceRemote
     */
    public static RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return getInstance().getServiceLocator().getRegulatoryInformationService();
    }

    /**
     * 
     * @return StudyOverallStatusServiceRemote
     */
    public static StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return getInstance().getServiceLocator().getStudyOverallStatusService();
    }
    
    /**
     * 
     * @return StudyIndldeServiceRemote
     */
    public static StudyIndldeServiceRemote getStudyIndldeService() {
        return getInstance().getServiceLocator().getStudyIndldeService();
    }

    /**
     * 
     * @return StudyResourcingServiceRemote
     */
    public static StudyResourcingServiceRemote getStudyResourcingService() {
        return getInstance().getServiceLocator().getStudyResoucringService();
    }

    /**
     * 
     * @return StudyRegulatoryAuthorityServiceRemote
     */
    public static StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() {
        return getInstance().getServiceLocator().getStudyRegulatoryAuthorityService();
    }

    /**
     * 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public static OrganizationEntityServiceRemote getPoOrganizationEntityService() throws PAException {
        return getInstance().getServiceLocator().getPoOrganizationEntityService();
    }

    /**
     * 
     * @return LookUpTableServiceRemote
     */
    public static LookUpTableServiceRemote getLookUpTableService() {
        return getInstance().getServiceLocator().getLookUpTableService();
    }

    /**
     * 
     * @return ProtocolQueryServiceRemote
     */
    public static ProtocolQueryServiceLocal getProtocolQueryService() {
        return getInstance().getServiceLocator().getProtocolQueryService();
    }

    /**
     * 
     * @return HealthCareFacilityServiceRemote
     */
    public static PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService() {
        return getInstance().getServiceLocator().getPAHealthCareFacilityService();
    }
    /**
     * 
     * @return ResearchOrganizationServiceRemote
     */
    public static PAResearchOrganizationServiceRemote getPAResearchOrganizationService() {
        return getInstance().getServiceLocator().getPAResearchOrganizationService();
    }

    /**
     * 
     * @return StudyParticipationService
     */
    public static StudyParticipationServiceRemote getStudyParticipationService() {
        return getInstance().getServiceLocator().getStudyParticipationService();
    }

    /**
     * 
     * @return StudySiteAccrualStatusService
     */
    public static StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
        return getInstance().getServiceLocator().getStudySiteAccrualStatusService();
    }

    /**
     * 
     * @return DocumentServiceRemote
     */
    public static DocumentServiceRemote getDocumentService() {
        return getInstance().getServiceLocator().getDocumentService();
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error 
     */
    public static HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() 
        throws PAException {
        return getInstance().getServiceLocator().getPoHealthCareProverService();
    }

    /**
     * 
     * @return SubGroupsServiceRemote
     */
    public static SubGroupsServiceRemote getSubGroupsService() {
        return getInstance().getServiceLocator().getSubGroupsService();
    }

    /**
     * 
     * @return StudyParticipationService
     */
    public static StudyParticipationContactServiceRemote getStudyParticipationContactService() {
        return getInstance().getServiceLocator().getStudyParticipationContactService();
    }

    /**
     * 
     * @return PersonEntityServiceRemote
     * @throws PAException on error 
     */
    public static PersonEntityServiceRemote getPoPersonEntityService() throws PAException {
        return getInstance().getServiceLocator().getPoPersonEntityService();
    }

    /**
     * 
     * @return PAHealthCareProviderRemote
     */
    public static PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return getInstance().getServiceLocator().getPAHealthCareProviderService();
    }
    
    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error 
     */
    public static HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() 
        throws PAException {
        return getInstance().getServiceLocator().getPoPersonCorrelationService();
    }

    /**
     * @return PlannedActivityServiceRemote
     */
    public static PlannedActivityServiceRemote getPlannedActivityService() {
        return getInstance().getServiceLocator().getPlannedActivityService();
    }
    
    /**
     * @return InterventionServiceRemote
     */
    public static InterventionServiceRemote getInterventionService() {
        return getInstance().getServiceLocator().getInterventionService();
    }
    
    /**
     * @return InterventionAlternateNameServiceRemote
     */
    public static InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        return getInstance().getServiceLocator().getInterventionAlternateNameService();
    }
    /**
     * @return OutcomeMeasureServiceRemote
     */
    public static StudyOutcomeMeasureServiceRemote getOutcomeMeasurService() {
        return getInstance().getServiceLocator().getOutcomeMeasurService();
    }
    /**
     * @return OutcomeMeasureServiceRemote
     */
    public static ArmServiceRemote getArmService() {
        return getInstance().getServiceLocator().getArmService();
    }
    /**
     * 
     * @return ClinicalResearchStaffCorrelationServiceRemote
     * @throws PAException on error 
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService() 
        throws PAException {
        return getInstance().getServiceLocator().getPoClinicalResearchStaffCorrelationService();
    }

    /**
     * 
     * @return CTGovXmlGeneratorServiceRemote
     * @throws PAException on error 
     */
    public static CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() 
        throws PAException {
        return getInstance().getServiceLocator().getCTGovXmlGeneratorService();
    }
    
    /**
     * 
     * @return AbstractionCompletionServiceRemote
     * @throws PAException on error 
     */
    public static AbstractionCompletionServiceRemote getAbstractionCompletionService() 
        throws PAException {
        return getInstance().getServiceLocator().getAbstractionCompletionService();
    }
    
    //DocumentWorkflowStatusServiceRemote
    /**
     * 
     * @return DocumentWorkflowStatusServiceRemote
     * @throws PAException on error 
     */
    public static DocumentWorkflowStatusServiceRemote getDocumentWorkflowStatusService() 
        throws PAException {
        return getInstance().getServiceLocator().getDocumentWorkflowStatusService();
    }
    
    /**
     * @return DiseaseService
     */
    public static DiseaseServiceRemote getDiseaseService() {
        return getInstance().getServiceLocator().getDiseaseService();
    }
    
    /**
     * @return DiseaseAlternameService
     */
    public static DiseaseAlternameServiceRemote getDiseaseAlternameService() {
        return getInstance().getServiceLocator().getDiseaseAlternameService();
    }
    
    /**
     * @return DiseaseParentService
     */
    public static DiseaseParentServiceRemote getDiseaseParentService() {
        return getInstance().getServiceLocator().getDiseaseParentService();
    }
    
    /**
     * @return StudyDiseaseService
     */
    public static StudyDiseaseServiceRemote getStudyDiseaseService() {
        return getInstance().getServiceLocator().getStudyDiseaseService();
    }

    /**
     * @return StudyContactService
     */
    public static StudyContactServiceRemote getStudyContactService() {
        return getInstance().getServiceLocator().getStudyContactService();
    }
}

