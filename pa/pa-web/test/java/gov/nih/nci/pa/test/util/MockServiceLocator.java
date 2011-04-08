/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.test.util;

import static org.mockito.Mockito.mock;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceBean;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.RegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.RegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StratumGroupBeanLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceRemote;
import gov.nih.nci.pa.service.StudyCheckoutServiceBean;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceBean;
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
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceBean;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockArmService;
import gov.nih.nci.service.MockDiseaseAlternateService;
import gov.nih.nci.service.MockDiseaseParentService;
import gov.nih.nci.service.MockDiseaseService;
import gov.nih.nci.service.MockDocumentService;
import gov.nih.nci.service.MockGridAccountService;
import gov.nih.nci.service.MockInterventionAlternateNameService;
import gov.nih.nci.service.MockInterventionService;
import gov.nih.nci.service.MockMailManagerService;
import gov.nih.nci.service.MockOrganizationCorrelationService;
import gov.nih.nci.service.MockPAHealthCareProviderService;
import gov.nih.nci.service.MockPlannedActivityService;
import gov.nih.nci.service.MockPlannedMarkerService;
import gov.nih.nci.service.MockProtocolQueryService;
import gov.nih.nci.service.MockRegistryUserService;
import gov.nih.nci.service.MockRegulatoryInformationService;
import gov.nih.nci.service.MockStudyContactService;
import gov.nih.nci.service.MockStudyDiseaseService;
import gov.nih.nci.service.MockStudyIndIdeService;
import gov.nih.nci.service.MockStudyMilestoneService;
import gov.nih.nci.service.MockStudyObjectiveService;
import gov.nih.nci.service.MockStudyOnholdService;
import gov.nih.nci.service.MockStudyOutcomeMeasureService;
import gov.nih.nci.service.MockStudyOverallStatusService;
import gov.nih.nci.service.MockStudyProtocolService;
import gov.nih.nci.service.MockStudyResourcingService;
import gov.nih.nci.service.MockStudySiteAccrualStatusService;
import gov.nih.nci.service.MockStudySiteService;
import gov.nih.nci.service.util.MockAbstractionCompletionService;
import gov.nih.nci.service.util.MockCTGovXMLGeneratorService;
import gov.nih.nci.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.service.util.MockStudySiteAccrualAccessService;
import gov.nih.nci.service.util.MockTSRReportGeneratorService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;


/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator {

    private final StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();
    private final StudyOverallStatusServiceLocal studyOverallStatusService = new MockStudyOverallStatusService();
    private final StudySiteServiceLocal studySiteService = new MockStudySiteService();
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new MockStudySiteAccrualStatusService();
    private final InterventionServiceLocal interventionService = new MockInterventionService();
    private final InterventionAlternateNameServiceRemote interventionAlternateNameService = new MockInterventionAlternateNameService();
    private final PlannedActivityServiceLocal plannedActivityService = new MockPlannedActivityService();
    private final StudyOnholdServiceLocal studyOnholdService = new MockStudyOnholdService();
    private final PAHealthCareProviderRemote healthCareProviderRemote = new MockPAHealthCareProviderService();
    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final StudyDiseaseServiceLocal studyDiseaseService = new MockStudyDiseaseService();
    private final PDQDiseaseServiceLocal diseaseService = new MockDiseaseService();
    private final PDQDiseaseParentServiceRemote diseaseParentService = new MockDiseaseParentService();
    private final PDQDiseaseAlternameServiceLocal diseaseAlternateService = new MockDiseaseAlternateService();
    private final StudyMilestoneServicelocal studyMilestoneService = new MockStudyMilestoneService();
    private final StudyResourcingServiceLocal studyResourcingService = new MockStudyResourcingService();
    private final RegulatoryInformationServiceRemote regulatoryInformationService = new MockRegulatoryInformationService();
    private final RegistryUserServiceLocal registryUserService = new MockRegistryUserService();
    private final StudyOutcomeMeasureServiceLocal studyOutcomeMService = new MockStudyOutcomeMeasureService();
    private final StudyObjectiveServiceLocal studyObjectiveService = new MockStudyObjectiveService();
    private final RegulatoryAuthorityServiceLocal regulatoryAuthorityService = new RegulatoryAuthorityBeanLocal();
    private final StratumGroupServiceLocal stratumGroupService = new StratumGroupBeanLocal();
    private final StudyMilestoneTasksServiceLocal studyMilestoneTasksService = new StudyMilestoneTasksServiceBean();
    private final StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService = new MockStudySiteAccrualAccessService();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final StudyInboxServiceLocal studyInboxService = new StudyInboxServiceBean();
    private final StudyCheckoutServiceLocal studyCheckoutService = new StudyCheckoutServiceBean();
    private final PlannedSubstanceAdministrationServiceRemote plannedSAService = new PlannedSubstanceAdministrationServiceBean();
    private final LookUpTableServiceRemote lookUpService = new MockLookUpTableServiceBean();
    private final ArmServiceLocal armService = new MockArmService();
    private final StudyIndldeServiceLocal studyIndIdeService = new MockStudyIndIdeService();
    private final AbstractionCompletionServiceRemote abstractionCompletionService = new MockAbstractionCompletionService();
    private final CTGovXmlGeneratorServiceRemote ctGovXmlGeneratorService = new MockCTGovXMLGeneratorService();
    private final TSRReportGeneratorServiceRemote tsrReportGeneratorService = new MockTSRReportGeneratorService();
    private final StudyContactServiceLocal studyContactService = new MockStudyContactService();
    private final DocumentServiceLocal documentService = new MockDocumentService();
    private final MailManagerServiceLocal mailManagerService = new MockMailManagerService();
    private final GridAccountServiceRemote gridAccountService = new MockGridAccountService();
    private final PlannedMarkerServiceLocal plannedMarkerService = new MockPlannedMarkerService();

    /**
     * @return mock service
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @return StudyOverallStatusServiceRemote
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    /**
     * @return StudySiteServiceRemote
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
     * @return InterventionServiceLocal
     */
    public InterventionServiceLocal getInterventionService() {
        return interventionService;
    }

    /**
     * @return PlannedActivityServiceRemote
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return plannedActivityService;
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        return interventionAlternateNameService;
    }


    /**
     * @return StudyOnholdServiceRemote
     */
    public StudyOnholdServiceLocal getStudyOnholdService() {
      return studyOnholdService;
    }

    /**
     * @return null
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return null;
    }

   /**
   *
   * @return PAPersonServiceRemote
   */
   public PAPersonServiceRemote getPAPersonService() {
       return null;
   }

   /**
    * @return RegulatoryInformationServiceRemote
    */
   public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
		return regulatoryInformationService;
	}

    /**
     * return StudyResourcingServiceRemote
     */
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return studyResourcingService;
    }

    /**
     * return StudyRegulatoryAuthorityServiceRemote
     */
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
    *
    * @return OrganizationEntityServiceRemote
    */
    public  OrganizationEntityServiceRemote getPoOrganizationEntityService() {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getLookUpTableService()
     */
    public LookUpTableServiceRemote getLookUpTableService() {
    	return lookUpService;
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getProtocolQueryService()
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }


    /**
     * return DocumentServiceRemote
     */
    public DocumentServiceLocal getDocumentService() {
        return documentService;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPoHealthCareProverService()
     */
    public HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() {
        // TODO Auto-generated method stub
        return null;
    }


    public StudySiteContactServiceLocal getStudySiteContactService() {
        // TODO Auto-generated method stub
        return null;
    }

    public PersonEntityServiceRemote getPoPersonEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return healthCareProviderRemote;
    }

    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * return SubGroupsServiceRemote
     */
    public StratumGroupServiceRemote getSubGroupsService() {
        return null;
    }


    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        return studyOutcomeMService;
    }
    public ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService() {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getArmService()
     */
    public ArmServiceLocal getArmService() {
        return armService;
    }

    public StudyIndldeServiceLocal getStudyIndldeService() {
        // TODO Auto-generated method stub
        return studyIndIdeService;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getCTGovXmlGeneratorService()
     */
    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() {
         return ctGovXmlGeneratorService;
    }

    public AbstractionCompletionServiceRemote getAbstractionCompletionService() {
       return abstractionCompletionService;
    }

    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return diseaseAlternateService;
    }

    /**
     * @return
     */
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
         return diseaseParentService;
    }

    /**
     * @return
     */
    public PDQDiseaseServiceLocal getDiseaseService() {
         return diseaseService;
    }

    /**
     * @return
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
         return studyDiseaseService;
    }

    /**
     * @return StudyContact
     */
    public StudyContactServiceLocal getStudyContactService() {
         return studyContactService;
    }

    public OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return studyMilestoneService;
    }

    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
       return tsrReportGeneratorService;
    }

    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    public MailManagerServiceLocal getMailManagerService() {
        return mailManagerService;
    }

    /**
     * @return the healthCareProviderRemote
     */
    public PAHealthCareProviderRemote getHealthCareProviderRemote() {
        return healthCareProviderRemote;
    }

    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return studyObjectiveService;
    }

    /**
     * @return the regulatoryAuthorityService
     */
    public RegulatoryAuthorityServiceLocal getRegulatoryAuthorityService() {
        return regulatoryAuthorityService;
    }

    /**
     * @return the stratumGroupService
     */
    public StratumGroupServiceLocal getStratumGroupService() {
        return stratumGroupService;
    }

    /**
     * @return the studyMilestoneTasksService
     */
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return studyMilestoneTasksService;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return studySiteAccrualAccessService;
    }

    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return organizationCorrelationService;
    }

    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        // TODO Auto-generated method stub
        return null;
    }
	/**
	 * @return the studyInboxService
	 */
	public StudyInboxServiceLocal getStudyInboxService() {
		return studyInboxService;
	}

    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        // TODO Auto-generated method stub
        return mock(TrialRegistrationServiceLocal.class);
    }

    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return studyCheckoutService;
    }

    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return plannedSAService;
    }

    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public GridAccountServiceRemote getGridAccountService() {
        return gridAccountService;
    }

    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return mock(ParticipatingSiteServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
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
        return plannedMarkerService;
    }

}
