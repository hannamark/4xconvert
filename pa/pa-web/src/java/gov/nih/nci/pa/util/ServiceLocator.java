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
 * Interface used to locate the services used by pa-web.
 * 
 * @author Harsha
 *
 */
public interface ServiceLocator {

    /**
     *
     * @return StudyProtocolServiceRemote studyProtocolServiceRemote
     */
    StudyProtocolServiceRemote getStudyProtocolService();


    /**
     * @return ProtocolOrganizationServiceRemote
     */
    PAOrganizationServiceRemote getPAOrganizationService();


    /**
     *
     * @return DiseaseCondServiceRemote
     */
    DiseaseCondServiceRemote getDiseaseConditionService();
    
    /**
     *
     * @return PAPersonServiceRemote
     */
    PAPersonServiceRemote getPAPersonService();
    
    /**
     * 
     * @return RegulatoryInformationServiceRemote
     */
    RegulatoryInformationServiceRemote getRegulatoryInformationService();
    
    /**
     * 
     * @return StudyIndldeServiceRemote
     *  
     */
    StudyIndldeServiceRemote getStudyIndldeService(); 
    
    
    /**
     * 
     * @return StudyOverallStatusServiceRemote
     */
    StudyOverallStatusServiceRemote getStudyOverallStatusService();
    
    /**
     * 
     * @return StudyResourcingServiceRemote
     */
    StudyResourcingServiceRemote getStudyResoucringService();
    
    /**
     * 
     * @return StudyRegulatoryAuthorityServiceRemote
     */
    StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService();    

    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    OrganizationEntityServiceRemote getPoOrganizationEntityService() throws PAException;
    
    /**
    *
    * @return LookUpTableServiceRemote
    */
    LookUpTableServiceRemote getLookUpTableService();

    /**
     * 
     * @return ProtocolQueryServiceRemote
     */
    ProtocolQueryServiceLocal getProtocolQueryService();
    
    /**
     * @return StudyParticipationServiceRemote
     */
    StudyParticipationServiceRemote getStudyParticipationService();
    
    /**
     * @return PAHealthCareFacilityServiceRemote
     */
    PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService();
    /**
     * @return PAHealthCareProviderRemote
     */    
    PAResearchOrganizationServiceRemote getPAResearchOrganizationService();

    /**
     * @return StudySiteAccrualStatusServiceRemote
     */
    StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService();
    /**
     * 
     * @return DocumentServiceRemote
     */
    DocumentServiceRemote getDocumentService();
    /**
    *
    * @return HealthCareFacilityCorrelationServiceRemote
    * @throws PAException on error  
    */
    HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() throws PAException;
//    /**
//    *
//    * @return HealthCareFacilityCorrelationServiceRemote
//    */
//    ResearchOrganizationCorrelationServiceRemote getPoResearchOrganizationService();
    /**
     * 
     * @return SubGroupsServiceRemote
     */
    SubGroupsServiceRemote getSubGroupsService();
    
    /**
    *
    * @return StudyParticipationContactServiceRemote
    */    
    StudyParticipationContactServiceRemote getStudyParticipationContactService();
    
    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    PersonEntityServiceRemote getPoPersonEntityService() throws PAException;

    /**
     * @return PAHealthCareProviderRemote
     */    
    PAHealthCareProviderRemote getPAHealthCareProviderService();

    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error 
     */ 
    HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() throws PAException;
    
    /**
     * @return PlannedActivityServiceRemote
     */
    PlannedActivityServiceRemote getPlannedActivityService();
    
    /**
     * @return InterventionServiceRemote
     */
    InterventionServiceRemote getInterventionService();
    
    /**
     * @return InterventionAlternateNameServiceRemote
     */
    InterventionAlternateNameServiceRemote getInterventionAlternateNameService();
    
    /**
     * @return OutcomeMeasureServiceRemote
     */
    StudyOutcomeMeasureServiceRemote getOutcomeMeasurService();

    /**
     * @return ArmServiceRemote
     */
    ArmServiceRemote getArmService();

    /**
     * @return getPoClinicalResearchStaffCorrelationService
     * @throws PAException on error 
     */
    ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService() throws PAException;

    /**
     * @return CTGovXmlGeneratorService
     * @throws PAException on error 
     */
    CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() throws PAException;
    
    /**
     * @return AbstractionCompletionService
     * @throws PAException on error 
     */
    AbstractionCompletionServiceRemote getAbstractionCompletionService() throws PAException;
    
    /**
     * 
     * @return StudyIndldeServiceRemote
     *  
     */
    DocumentWorkflowStatusServiceRemote getDocumentWorkflowStatusService(); 
    
    /**
     * @return StudyDiseaseService
     */
    StudyDiseaseServiceRemote getStudyDiseaseService();
    
    /**
     * @return DiseaseService
     */
    DiseaseServiceRemote getDiseaseService();
    
    /**
     * @return DiseaseAlternameService
     */
    DiseaseAlternameServiceRemote getDiseaseAlternameService();
    
    /**
     * @return DiseaseParentService
     */
    DiseaseParentServiceRemote getDiseaseParentService();

    /**
     * @return StudyContactService
     */
    StudyContactServiceRemote getStudyContactService();
}

