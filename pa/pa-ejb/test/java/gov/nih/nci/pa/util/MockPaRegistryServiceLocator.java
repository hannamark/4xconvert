/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;


/**
 * @author vrushali
 *
 */
public class MockPaRegistryServiceLocator implements ServiceLocator  {

    private final LookUpTableServiceRemote lookUpService = new MockLookUpTableServiceBean();
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getAbstractionCompletionService()
     */
    public AbstractionCompletionServiceRemote getAbstractionCompletionService()
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getArmService()
     */
    public ArmServiceLocal getArmService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getCTGovXmlGeneratorService()
     */
    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService()
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getDiseaseAlternameService()
     */
    public DiseaseAlternameServiceLocal getDiseaseAlternameService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getDiseaseParentService()
     */
    public DiseaseParentServiceRemote getDiseaseParentService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getDiseaseService()
     */
    public DiseaseServiceLocal getDiseaseService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getDocumentService()
     */
    public DocumentServiceLocal getDocumentService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getDocumentWorkflowStatusService()
     */
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getGridAccountService()
     */
    public GridAccountServiceRemote getGridAccountService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getInterventionAlternateNameService()
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getInterventionService()
     */
    public InterventionServiceLocal getInterventionService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getLookUpTableService()
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        // TODO Auto-generated method stub
        return lookUpService;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getMailManagerService()
     */
    public MailManagerServiceLocal getMailManagerService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getOrganizationCorrelationService()
     */
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getOutcomeMeasurService()
     */
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasurService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPAHealthCareProviderService()
     */
    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPAOrganizationService()
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPAPersonService()
     */
    public PAPersonServiceRemote getPAPersonService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPlannedActivityService()
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPlannedSubstanceAdministrationService()
     */
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getProprietaryTrialService()
     */
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getProtocolQueryService()
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getRegisterUserService()
     */
    public RegistryUserServiceRemote getRegisterUserService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getRegulatoryInformationService()
     */
    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStratumGroupService()
     */
    public StratumGroupServiceLocal getStratumGroupService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyCheckoutService()
     */
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyContactService()
     */
    public StudyContactServiceLocal getStudyContactService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyDiseaseService()
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyInboxService()
     */
    public StudyInboxServiceLocal getStudyInboxService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyIndldeService()
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyMilestoneService()
     */
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyMilestoneTasksService()
     */
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyObjectiveService()
     */
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyOnholdService()
     */
    public StudyOnholdServiceLocal getStudyOnholdService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyOverallStatusService()
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyProtocolService()
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyProtocolStageService()
     */
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyRecruitmentStatusService()
     */
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyRegulatoryAuthorityService()
     */
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyRelationshipService()
     */
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyResoucringService()
     */
    public StudyResourcingServiceLocal getStudyResoucringService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudySiteAccrualAccessService()
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudySiteAccrualStatusService()
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudySiteContactService()
     */
    public StudySiteContactServiceLocal getStudySiteContactService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudySiteOverallStatusService()
     */
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudySiteService()
     */
    public StudySiteServiceLocal getStudySiteService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getTSRReportGeneratorService()
     */
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService()
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getTrialRegistrationService()
     */
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        // TODO Auto-generated method stub
        return null;
    }

}
