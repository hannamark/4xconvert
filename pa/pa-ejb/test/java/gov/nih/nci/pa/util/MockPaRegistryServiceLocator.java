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
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.RegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceRemote;
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
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusBeanLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderServiceBean;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;


/**
 * @author vrushali
 *
 */
public class MockPaRegistryServiceLocator implements ServiceLocator  {

    private final LookUpTableServiceRemote lookUpService = new MockLookUpTableServiceBean();
    StudyProtocolServiceLocal studyProtocolService = new StudyProtocolBeanLocal();
    StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal();
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    OrganizationCorrelationServiceRemote ocsr = new OrganizationCorrelationServiceBean();
    PAHealthCareProviderRemote paHealthCareProvider = new PAHealthCareProviderServiceBean();

    /**
     * {@inheritDoc}
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * @return StudySiteAccrualStatusServiceRemote
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * {@inheritDoc}
     */
    public InterventionServiceLocal getInterventionService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOnholdServiceLocal getStudyOnholdService() {
      return null;
    }

    /**
     * {@inheritDoc}
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
   public PAPersonServiceRemote getPAPersonService() {
       return null;
   }

   /**
    * {@inheritDoc}
    */
   public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return null;
    }

   /**
    * {@inheritDoc}
    */
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public  OrganizationEntityServiceRemote getPoOrganizationEntityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpService;
    }
    /**
     * {@inheritDoc}
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentServiceLocal getDocumentService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteContactServiceLocal getStudySiteContactService() {
        return studySiteContactService;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPoPersonEntityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return paHealthCareProvider;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StratumGroupServiceRemote getSubGroupsService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasurService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ArmServiceLocal getArmService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    public AbstractionCompletionServiceRemote getAbstractionCompletionService()
        throws PAException {
       return null;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public DiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public DiseaseParentServiceRemote getDiseaseParentService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    public DiseaseServiceLocal getDiseaseService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyContactServiceLocal getStudyContactService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService()
        throws PAException {
       return null;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public MailManagerServiceLocal getMailManagerService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PAHealthCareProviderRemote getHealthCareProviderRemote() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthorityServiceLocal getRegulatoryAuthorityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StratumGroupServiceLocal getStratumGroupService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return ocsr;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyInboxServiceLocal getStudyInboxService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return null;
    }

    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public GridAccountServiceRemote getGridAccountService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return null;
    }
}
