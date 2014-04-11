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
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIdentifiersService;
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
import gov.nih.nci.pa.service.TrialDataVerificationServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovSyncNightlyServiceLocal;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.CTGovUploadServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.FamilyServiceLocal;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialUploadService;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.PendingPatientAccrualsServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;


/**
 *
 * @author Harsha
 *
 */
public class JndiServiceLocator implements ServiceLocator {

    /**
     * @return pdq trial registration bean.
     */
    @Override
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        return (PDQTrialRegistrationServiceBeanRemote) JNDIUtil.lookupPa("/pa/PDQTrialRegistrationServiceBean/remote");
    }

    /**
     * @return pdq trial abstraction bean.
     */
    @Override
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return (PDQTrialAbstractionServiceBeanRemote) JNDIUtil.lookupPa("/pa/PDQTrialAbstractionServiceBean/remote");
    }

    /**
     * @return protocol service
     */
    @Override
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return (StudyProtocolServiceLocal) JNDIUtil.lookupPa("/pa/StudyProtocolBeanLocal/local");
    }

    /**
     * @return PAOrganizationServiceRemote remote interface
     */
    @Override
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return (PAOrganizationServiceRemote) JNDIUtil.lookupPa("/pa/PAOrganizationServiceBean/remote");
    }

    /**
     * @return PAPersonService
     */
    @Override
    public PAPersonServiceRemote getPAPersonService() {
        return (PAPersonServiceRemote) JNDIUtil.lookupPa("/pa/PAPersonServiceBean/remote");
    }

    /**
     * @return RegulatoryInformationServiceRemote
     */
    @Override
    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return (RegulatoryInformationServiceRemote) JNDIUtil.lookupPa("/pa/RegulatoryInformationBean/remote");
    }

    /**
     * @return StudyIndldeServiceLocal
     *
     */
    @Override
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return (StudyIndldeServiceLocal) JNDIUtil.lookupPa("/pa/StudyIndldeBeanLocal/local");
    }

    /**
     * @return DocumentWorkflowStatusServiceLocal
     *
     */
    @Override
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return (DocumentWorkflowStatusServiceLocal) JNDIUtil.lookupPa("/pa/DocumentWorkflowStatusBeanLocal/local");
    }

    /**
     * @return StudyOverallStatusServiceLocal
     */
    @Override
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return (StudyOverallStatusServiceLocal) JNDIUtil.lookupPa("/pa/StudyOverallStatusBeanLocal/local");
    }

    /**
     * @return StudyResourcingServiceLocal
     */
    @Override
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return (StudyResourcingServiceLocal) JNDIUtil.lookupPa("/pa/StudyResourcingBeanLocal/local");
    }

    /**
     * @return StudyResourcingServiceLocal
     */
    @Override
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return (StudyRegulatoryAuthorityServiceLocal) JNDIUtil.lookupPa("/pa/StudyRegulatoryAuthorityBeanLocal/local");
    }

    /**
     * @return LookUpTableServiceRemote
     */
    @Override
    public LookUpTableServiceRemote getLookUpTableService() {
        return (LookUpTableServiceRemote) JNDIUtil.lookupPa("/pa/LookUpTableServiceBean/remote");
    }

    /**
     * @return ProtocolQueryServiceRemote
     */
    @Override
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return (ProtocolQueryServiceLocal) JNDIUtil.lookupPa("/pa/ProtocolQueryServiceBean/local");
    }

    /**
     * @return StudySiteServiceLocal
     */
    @Override
    public StudySiteServiceLocal getStudySiteService() {
        return (StudySiteServiceLocal) JNDIUtil.lookupPa("/pa/StudySiteBeanLocal/local");
    }

    /**
     * @return StudySiteAccrualStatusServiceLocal
     */
    @Override
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return (StudySiteAccrualStatusServiceLocal) JNDIUtil.lookupPa("/pa/StudySiteAccrualStatusBeanLocal/local");
    }

    /**
     * @return DocumentServiceLocal
     */
    @Override
    public DocumentServiceLocal getDocumentService() {
        return (DocumentServiceLocal) JNDIUtil.lookupPa("/pa/DocumentBeanLocal/local");
    }

    /**
     * @return StratumGroupServiceLocal
     */
    @Override
    public StratumGroupServiceLocal getStratumGroupService() {
        return (StratumGroupServiceLocal) JNDIUtil.lookupPa("/pa/StratumGroupBeanLocal/local");
    }

    /**
     * @return PAHealthCareProviderRemote
     */
    @Override
    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return (PAHealthCareProviderRemote) JNDIUtil.lookupPa("/pa/PAHealthCareProviderServiceBean/remote");
    }

    /**
     * @return StudySiteService
     */
    @Override
    public StudySiteContactServiceLocal getStudySiteContactService() {
        return (StudySiteContactServiceLocal) JNDIUtil.lookupPa("/pa/StudySiteContactBeanLocal/local");
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    @Override
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        return (InterventionAlternateNameServiceRemote) JNDIUtil
            .lookupPa("/pa/InterventionAlternateNameServiceBean/remote");
    }

    /**
     * @return InterventionServiceLocal
     */
    @Override
    public InterventionServiceLocal getInterventionService() {
        return (InterventionServiceLocal) JNDIUtil.lookupPa("/pa/InterventionBeanLocal/local");
    }

    /**
     * @return PlannedActivityServiceLocal
     */
    @Override
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return (PlannedActivityServiceLocal) JNDIUtil.lookupPa("/pa/PlannedActivityBeanLocal/local");
    }

    /**
     * @return OutcomeMeasureServiceLocal
     */
    @Override
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        return (StudyOutcomeMeasureServiceLocal) JNDIUtil.lookupPa("/pa/StudyOutcomeMeasureBeanLocal/local");
    }

    /**
     * @return ArmServcieLocal
     */
    @Override
    public ArmServiceLocal getArmService() {
        return (ArmServiceLocal) JNDIUtil.lookupPa("/pa/ArmBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService() {
        return (CTGovXmlGeneratorServiceLocal) JNDIUtil.lookupPa("/pa/CTGovXmlGeneratorServiceBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return (PDQXmlGeneratorServiceRemote) JNDIUtil.lookupPa("/pa/PDQXmlGeneratorServiceBean/remote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractionCompletionServiceRemote getAbstractionCompletionService() {
        return (AbstractionCompletionServiceRemote) JNDIUtil.lookupPa("/pa/AbstractionCompletionServiceBean/remote");
    }

    /**
     * @return PDQDiseaseAlternameService
     */
    @Override
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return (PDQDiseaseAlternameServiceLocal) JNDIUtil.lookupPa("/pa/PDQDiseaseAlternameBeanLocal/local");
    }

    /**
     * @return PDQDiseaseParentService
     */
    @Override
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
        return (PDQDiseaseParentServiceRemote) JNDIUtil.lookupPa("/pa/PDQDiseaseParentServiceBean/remote");
    }

    /**
     * @return PDQDiseaseService
     */
    @Override
    public PDQDiseaseServiceLocal getDiseaseService() {
        return (PDQDiseaseServiceLocal) JNDIUtil.lookupPa("/pa/PDQDiseaseBeanLocal/local");
    }

    /**
     * @return StudyDiseaseServiceLocal
     */
    @Override
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return (StudyDiseaseServiceLocal) JNDIUtil.lookupPa("/pa/StudyDiseaseBeanLocal/local");
    }

    /**
     * @return StudyContactServiceLocal
     */
    @Override
    public StudyContactServiceLocal getStudyContactService() {
        return (StudyContactServiceLocal) JNDIUtil.lookupPa("/pa/StudyContactBeanLocal/local");
    }

    /**
     * @return StudyMilestoneServiceLocal
     */
    @Override
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return (StudyMilestoneServicelocal) JNDIUtil.lookupPa("/pa/StudyMilestoneBeanLocal/local");
    }

    /**
     * @return RegistryUserServiceLocal
     */
    @Override
    public RegistryUserServiceLocal getRegistryUserService() {
        return (RegistryUserServiceLocal) JNDIUtil.lookupPa("/pa/RegistryUserBeanLocal/local");
    }

    /**
     * @return GridAccountServiceRemote
     */
    @Override
    public GridAccountServiceRemote getGridAccountService() {
        return (GridAccountServiceRemote) JNDIUtil.lookupPa("/pa/GridAccountServiceBean/remote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return (TSRReportGeneratorServiceRemote) JNDIUtil.lookupPa("/pa/TSRReportGeneratorServiceBean/remote");
    }

    /**
     * @return StudyOnholdServiceLocal
     */
    @Override
    public StudyOnholdServiceLocal getStudyOnholdService() {
        return (StudyOnholdServiceLocal) JNDIUtil.lookupPa("/pa/StudyOnholdBeanLocal/local");
    }

    /**
     * @return MailManagerServiceLocal
     */
    @Override
    public MailManagerServiceLocal getMailManagerService() {
        return (MailManagerServiceLocal) JNDIUtil.lookupPa("/pa/MailManagerBeanLocal/local");
    }

    /**
     * @return StudyObjectiveServiceLocal
     */
    @Override
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return (StudyObjectiveServiceLocal) JNDIUtil.lookupPa("/pa/StudyObjectiveBeanLocal/local");
    }

    /**
     * @return StudyRecruitmentStatusService
     */
    @Override
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return (StudyRecruitmentStatusServiceLocal) JNDIUtil.lookupPa("/pa/StudyRecruitmentStatusBeanLocal/local");
    }

    /**
     * @return StudyMilestoneService
     */
    @Override
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return (StudyMilestoneTasksServiceLocal) JNDIUtil.lookupPa("/pa/StudyMilestoneTasksServiceBean/local");
    }

    /**
     * @return OrganizationCorrelationServiceRemote
     */
    @Override
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return (OrganizationCorrelationServiceRemote)
            JNDIUtil.lookupPa("/pa/OrganizationCorrelationServiceBean/remote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return (StudySiteAccrualAccessServiceLocal) JNDIUtil.lookupPa("/pa/StudySiteAccrualAccessServiceBean/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return (TrialRegistrationServiceLocal) JNDIUtil.lookupPa("/pa/TrialRegistrationBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyInboxServiceLocal getStudyInboxService() {
        return (StudyInboxServiceLocal) JNDIUtil.lookupPa("/pa/StudyInboxServiceBean/local");
    }

    /**
     * @return StudySiteOverallStatusServiceLocal
     */
    @Override
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return (StudySiteOverallStatusServiceLocal) JNDIUtil.lookupPa("/pa/StudySiteOverallStatusServiceBean/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return (StudyCheckoutServiceLocal) JNDIUtil.lookupPa("/pa/StudyCheckoutServiceBean/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return (PlannedSubstanceAdministrationServiceRemote) JNDIUtil
            .lookupPa("/pa/PlannedSubstanceAdministrationServiceBean/remote");
    }

    /**
     * @return protocol service
     */
    @Override
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return (StudyRelationshipServiceLocal) JNDIUtil.lookupPa("pa/StudyRelationshipBeanLocal/local");
    }

    /**
     * @return service for partial save
     */
    @Override
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return (StudyProtocolStageServiceLocal) JNDIUtil.lookupPa("/pa/StudyProtocolStageBeanLocal/local");
    }

    /**
     * @return service for ProprietaryTrial
     */
    @Override
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return (ProprietaryTrialManagementServiceLocal)
            JNDIUtil.lookupPa("/pa/ProprietaryTrialManagementBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return (ParticipatingSiteServiceLocal) JNDIUtil.lookupPa("/pa/ParticipatingSiteBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
       return (PDQUpdateGeneratorTaskServiceLocal) JNDIUtil.lookupPa("/pa/PDQUpdateGeneratorTaskServiceBean/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return (PlannedMarkerServiceLocal) JNDIUtil.lookupPa("/pa/PlannedMarkerServiceBean/local");
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerAttributesServiceLocal getMarkerAttributesService() {
        return (MarkerAttributesServiceLocal) JNDIUtil.lookupPa("/pa/MarkerAttributesBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuditTrailServiceLocal getAuditTrailService() {
        return (AuditTrailServiceLocal) JNDIUtil.lookupPa("/pa/AuditTrailServiceBean/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialUploadService getPDQTrialUploadService() {
        return (PDQTrialUploadService) JNDIUtil.lookupPa("/pa/PDQTrialUploadBean/remote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingOrgServiceLocal getParticipatingOrgService() {
        return (ParticipatingOrgServiceLocal) JNDIUtil.lookupPa("/pa/ParticipatingOrgServiceBean/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyServiceLocal getFamilyService() {
        return (FamilyServiceLocal) JNDIUtil.lookupPa("/pa/FamilyServiceBeanLocal/local");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public CTGovUploadServiceLocal getCTGovUploadService() {
        return (CTGovUploadServiceLocal) JNDIUtil.lookupPa("/pa/CTGovUploadServiceBeanLocal/local");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSyncWithCaDSRServiceLocal getPMWithCaDSRService() {
        return (PlannedMarkerSyncWithCaDSRServiceLocal) JNDIUtil
        .lookupPa("/pa/PlannedMarkerSyncWithCaDSRBeanLocal/local");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialDataVerificationServiceLocal getTrialDataVerificationService() {
        return (TrialDataVerificationServiceLocal) JNDIUtil
                .lookupPa("/pa/TrialDataVerificationBeanLocal/local");
    }

    @Override
    public I2EGrantsServiceLocal getI2EGrantsService() {
        return (I2EGrantsServiceLocal) JNDIUtil.lookupPa("/pa/I2EGrantsServiceBean/local");
    }
    @Override
    public CTGovSyncServiceLocal getCTGovSyncService() {
        return (CTGovSyncServiceLocal) JNDIUtil.lookupPa("/pa/CTGovSyncServiceBean/local");
    }
    
    @Override
    public CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService() {
        return (CTGovSyncNightlyServiceLocal) JNDIUtil.lookupPa("/pa/CTGovSyncNightlyServiceBeanLocal/local");
    }

    @Override
    public ProtocolComparisonServiceLocal getProtocolComparisonService() {
        return (ProtocolComparisonServiceLocal) JNDIUtil.lookupPa("/pa/ProtocolComparisonServiceBean/local");
    }
    
    @Override
    public PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService() {
        return (PendingPatientAccrualsServiceLocal) JNDIUtil.lookupPa("/pa/PendingPatientAccrualsServiceBean/local");
    }

    @Override
    public StudyIdentifiersService getStudyIdentifiersService() {
        return (StudyIdentifiersService) JNDIUtil
                .lookupPa("/pa/StudyIdentifiersBeanLocal/local");
    }
}
