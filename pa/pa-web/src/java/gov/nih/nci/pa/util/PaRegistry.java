/*
 * Copyright Notice. Copyright 2008, ScenPro, Inc, (�caBIG� Participant�). The Protocol Abstraction (PA) Application was
 * created with NCI funding and is part of the caBIG� initiative. The software subject to this notice and license
 * includes both human readable source code form and machine readable, binary, object code form (the �caBIG� Software�).
 * This caBIG� Software License (the �License�) is between caBIG� Participant and You. �You (or �Your�) shall mean a
 * person or an entity, and all other entities that control, are controlled by, or are under common control with the
 * entity. �Control� for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity. License. Provided that You agree to the conditions
 * described below, caBIG� Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge,
 * irrevocable, transferable and royalty-free right and license in its rights in the caBIG� Software, including any
 * copyright or patent rights therein, to (i) use, install, disclose, access, operate, execute, reproduce, copy, modify,
 * translate, market, publicly display, publicly perform, and prepare derivative works of the caBIG� Software in any
 * manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and
 * offer for sale, import, and/or otherwise dispose of caBIG� Software (or portions thereof); (iii) distribute and have
 * distributed to and by third parties the caBIG� Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including the right to license such
 * rights to further third parties. For sake of clarity, and not by way of limitation, caBIG� Participant shall have no
 * right of accounting or right of payment from You or Your sub licensees for the rights granted under this License.
 * This License is granted at no charge to You. Your downloading, copying, modifying, displaying, distributing or use of
 * caBIG� Software constitutes acceptance of all of the terms and conditions of this Agreement. If You do not agree to
 * such terms and conditions, You have no right to download, copy, modify, display, distribute or use the caBIG�
 * Software. 1. Your redistributions of the source code for the caBIG� Software must retain the above copyright notice,
 * this list of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in
 * object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6
 * in the documentation and/or other materials provided with the distribution, if any. 2. Your end-user documentation
 * included with the redistribution, if any, must include the following acknowledgment: �This product includes software
 * developed by ScenPro, Inc.� If You do not include such end-user documentation, You shall include this acknowledgment
 * in the caBIG� Software itself, wherever such third-party acknowledgments normally appear. 3. You may not use the
 * names �ScenPro, Inc.�, �The National Cancer Institute�, �NCI�, �Cancer Bioinformatics Grid� or �caBIG�� to endorse or
 * promote products derived from this caBIG� Software. This License does not authorize You to use any trademarks,
 * service marks, trade names, logos or product names of either caBIG� Participant, NCI or caBIG�, except as required to
 * comply with the terms of this License. 4. For sake of clarity, and not by way of limitation, You may incorporate this
 * caBIG� Software into Your proprietary programs and into any third party proprietary programs. However, if You
 * incorporate the caBIG� Software into third party proprietary programs, You agree that You are solely responsible for
 * obtaining any permission from such third parties required to incorporate the caBIG� Software into such third party
 * proprietary programs and for informing Your sub licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before incorporating the caBIG� Software into
 * such third party proprietary software programs. In the event that You fail to obtain such permissions, You agree to
 * indemnify caBIG� Participant for any claims against caBIG� Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5. For sake of clarity, and not by way of
 * limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may
 * provide additional or different license terms and conditions in Your sublicenses of modifications of the caBIG�
 * Software, or any derivative works of the caBIG� Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in this License. 6. THIS caBIG� SOFTWARE IS
 * PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
 * ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG� SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.DiseaseAlternameServiceRemote;
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
import gov.nih.nci.pa.service.StudyMilestoneServiceRemote;
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
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
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
    /**
     * 
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        return getInstance().getServiceLocator().getPoOrganizationalContactCorrelationService();
    }
       
    /**
     * @return StudyMilestoneService
     */
    public static StudyMilestoneServiceRemote getStudyMilestoneService() {
        return getInstance().getServiceLocator().getStudyMilestoneService();
    }
    
    /**
     * @return StudyMilestoneService
     */
    public static RegistryUserServiceRemote getRegisterUserService() {
        return getInstance().getServiceLocator().getRegisterUserService();
    }    
     
    /**
     * 
     * @return TSRReportGeneratorServiceRemote
     * @throws PAException on error 
     */
    public static TSRReportGeneratorServiceRemote getTSRReportGeneratorService() 
        throws PAException {
        return getInstance().getServiceLocator().getTSRReportGeneratorService();
    }
}

