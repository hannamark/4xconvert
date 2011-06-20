/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyRelationshipTypeCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudySiteStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TrialRegistrationHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.EntityDto;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(PaHibernateSessionInterceptor.class)
public class TrialRegistrationBeanLocal extends AbstractTrialRegistrationBean implements TrialRegistrationServiceLocal {
    @EJB private StudyProtocolServiceLocal studyProtocolService = null;
    @EJB private StudyRelationshipServiceLocal studyRelationshipService = null;
    @EJB private StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB private StudyIndldeServiceLocal studyIndldeService = null;
    @EJB private StudyResourcingServiceLocal studyResourcingService = null;
    @EJB private StudyMilestoneServicelocal studyMilestoneService = null;
    @EJB private DocumentServiceLocal documentService = null;
    @EJB private StudyDiseaseServiceLocal studyDiseaseService = null;
    @EJB private ArmServiceLocal armService = null;
    @EJB private PlannedActivityServiceLocal plannedActivityService = null;
    @EJB private StratumGroupServiceLocal subGroupsService = null;
    @EJB private StudySiteServiceLocal studySiteService = null;
    @EJB private StudySiteContactServiceLocal studySiteContactService = null;
    @EJB private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
    @EJB private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = null;
    @EJB private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = null;
    @EJB private OrganizationCorrelationServiceRemote ocsr = null;
    @EJB private StudyContactServiceLocal studyContactService = null;
    @EJB private TSRReportGeneratorServiceRemote tsrReportService = null;
    @EJB private DocumentWorkflowStatusServiceLocal docWrkFlowStatusService = null;
    @EJB private RegulatoryInformationServiceRemote regulatoryInfoBean = null;
    @EJB private StudyRecruitmentStatusServiceLocal studyRecruitmentStatusServiceLocal = null;
    @EJB private StudyObjectiveServiceLocal studyObjectiveService = null;
    @EJB private StudySiteOverallStatusServiceLocal studySiteOverallStatusService = null;
    @EJB private AbstractionCompletionServiceRemote abstractionCompletionService = null;
    @EJB private StudyInboxServiceLocal studyInboxServiceLocal = null;
    @EJB private MailManagerServiceLocal mailManagerSerivceLocal = null;
    @EJB private RegistryUserServiceLocal userServiceLocal = null;

    private static final String CREATE = "Create";
    private static final String AMENDMENT = "Amendment";
    private static final String UPDATE = "Update";
    private static final String REJECTION = "Reject";
    private static final String PROTOCOL_ID_NULL = "Study Protocol Identifier is null";
    private static final String NO_PROTOCOL_FOUND = "No Study Protocol found for = ";
    private static final String EMAIL_NOT_NULL = "Email cannot be null, ";
    private static final String PHONE_NOT_NULL = "Phone cannot be null, ";
    private static final String SQL_APPEND = " AND FUNCTIONAL_CODE IN ";
    private static final String VALIDATION_EXCEPTION = "Validation Exception ";
    private TrialRegistrationHelper trialRegistrationHelper = null;

    /**
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
    /**
     * @return the studyRelationshipService
     */
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return studyRelationshipService;
    }
    /**
     * @param studyRelationshipService the studyRelationshipService to set
     */
    public void setStudyRelationshipService(StudyRelationshipServiceLocal studyRelationshipService) {
        this.studyRelationshipService = studyRelationshipService;
    }
    /**
     * @return the studyOverallStatusService
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return studyOverallStatusService;
    }
    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }
    /**
     * @return the studyIndldeService
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndldeService;
    }
    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }
    /**
     * @return the studyResourcingService
     */
    public StudyResourcingServiceLocal getStudyResourcingService() {
        return studyResourcingService;
    }
    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }
    /**
     * @return the studyMilestoneService
     */
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return studyMilestoneService;
    }
    /**
     * @param studyMilestoneService the studyMilestoneService to set
     */
    public void setStudyMilestoneService(StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }
    /**
     * @return the documentService
     */
    public DocumentServiceLocal getDocumentService() {
        return documentService;
    }
    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }
    /**
     * @return the studyDiseaseService
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return studyDiseaseService;
    }
    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }
    /**
     * @return the armService
     */
    public ArmServiceLocal getArmService() {
        return armService;
    }
    /**
     * @param armService the armService to set
     */
    public void setArmService(ArmServiceLocal armService) {
        this.armService = armService;
    }
    /**
     * @return the plannedActivityService
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return plannedActivityService;
    }
    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }
    /**
     * @return the subGroupsService
     */
    public StratumGroupServiceLocal getSubGroupsService() {
        return subGroupsService;
    }
    /**
     * @param subGroupsService the subGroupsService to set
     */
    public void setSubGroupsService(StratumGroupServiceLocal subGroupsService) {
        this.subGroupsService = subGroupsService;
    }
    /**
     * @return the studySiteService
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }
    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }
    /**
     * @return the studySiteContactService
     */
    public StudySiteContactServiceLocal getStudySiteContactService() {
        return studySiteContactService;
    }
    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }
    /**
     * @return the studySiteAccrualStatusService
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }
    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }
    /**
     * @return the studyOutcomeMeasureService
     */
    public StudyOutcomeMeasureServiceLocal getStudyOutcomeMeasureService() {
        return studyOutcomeMeasureService;
    }
    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }
    /**
     * @return the studyRegulatoryAuthorityService
     */
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return studyRegulatoryAuthorityService;
    }
    /**
     * @param studyRegulatoryAuthorityService the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(
            StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService) {
        this.studyRegulatoryAuthorityService = studyRegulatoryAuthorityService;
    }
    /**
     * @return the ocsr
     */
    public OrganizationCorrelationServiceRemote getOcsr() {
        return ocsr;
    }
    /**
     * @param ocsr the ocsr to set
     */
    public void setOcsr(OrganizationCorrelationServiceRemote ocsr) {
        this.ocsr = ocsr;
    }
    /**
     * @return the studyContactService
     */
    public StudyContactServiceLocal getStudyContactService() {
        return studyContactService;
    }
    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }
    /**
     * @return the tsrReportService
     */
    public TSRReportGeneratorServiceRemote getTsrReportService() {
        return tsrReportService;
    }
    /**
     * @param tsrReportService the tsrReportService to set
     */
    public void setTsrReportService(TSRReportGeneratorServiceRemote tsrReportService) {
        this.tsrReportService = tsrReportService;
    }
    /**
     * @return the docWrkFlowStatusService
     */
    public DocumentWorkflowStatusServiceLocal getDocWrkFlowStatusService() {
        return docWrkFlowStatusService;
    }
    /**
     * @param docWrkFlowStatusService the docWrkFlowStatusService to set
     */
    public void setDocWrkFlowStatusService(DocumentWorkflowStatusServiceLocal docWrkFlowStatusService) {
        this.docWrkFlowStatusService = docWrkFlowStatusService;
    }
    /**
     * @return the regulatoryInfoBean
     */
    public RegulatoryInformationServiceRemote getRegulatoryInfoBean() {
        return regulatoryInfoBean;
    }
    /**
     * @param regulatoryInfoBean the regulatoryInfoBean to set
     */
    public void setRegulatoryInfoBean(RegulatoryInformationServiceRemote regulatoryInfoBean) {
        this.regulatoryInfoBean = regulatoryInfoBean;
    }
    /**
     * @return the studyRecruitmentStatusServiceLocal
     */
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusServiceLocal() {
        return studyRecruitmentStatusServiceLocal;
    }
    /**
     * @param studyRecruitmentStatusServiceLocal the studyRecruitmentStatusServiceLocal to set
     */
    public void setStudyRecruitmentStatusServiceLocal(
            StudyRecruitmentStatusServiceLocal studyRecruitmentStatusServiceLocal) {
        this.studyRecruitmentStatusServiceLocal = studyRecruitmentStatusServiceLocal;
    }
    /**
     * @return the studyObjectiveService
     */
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return studyObjectiveService;
    }
    /**
     * @param studyObjectiveService the studyObjectiveService to set
     */
    public void setStudyObjectiveService(StudyObjectiveServiceLocal studyObjectiveService) {
        this.studyObjectiveService = studyObjectiveService;
    }
    /**
     * @return the studySiteOverallStatusService
     */
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return studySiteOverallStatusService;
    }
    /**
     * @param studySiteOverallStatusService the studySiteOverallStatusService to set
     */
    public void setStudySiteOverallStatusService(StudySiteOverallStatusServiceLocal studySiteOverallStatusService) {
        this.studySiteOverallStatusService = studySiteOverallStatusService;
    }
    /**
     * @return the abstractionCompletionService
     */
    public AbstractionCompletionServiceRemote getAbstractionCompletionService() {
        return abstractionCompletionService;
    }
    /**
     * @param abstractionCompletionService the abstractionCompletionService to set
     */
    public void setAbstractionCompletionService(AbstractionCompletionServiceRemote abstractionCompletionService) {
        this.abstractionCompletionService = abstractionCompletionService;
    }
    /**
     * @return the studyInboxServiceLocal
     */
    public StudyInboxServiceLocal getStudyInboxServiceLocal() {
        return studyInboxServiceLocal;
    }
    /**
     * @param studyInboxServiceLocal the studyInboxServiceLocal to set
     */
    public void setStudyInboxServiceLocal(StudyInboxServiceLocal studyInboxServiceLocal) {
        this.studyInboxServiceLocal = studyInboxServiceLocal;
    }
    /**
     * @return the mailManagerSerivceLocal
     */
    public MailManagerServiceLocal getMailManagerSerivceLocal() {
        return mailManagerSerivceLocal;
    }
    /**
     * @param mailManagerSerivceLocal the mailManagerSerivceLocal to set
     */
    public void setMailManagerSerivceLocal(MailManagerServiceLocal mailManagerSerivceLocal) {
        this.mailManagerSerivceLocal = mailManagerSerivceLocal;
    }
    /**
     * @return the userServiceLocal
     */
    public RegistryUserServiceLocal getUserServiceLocal() {
        return userServiceLocal;
    }
    /**
     * @param userServiceLocal the userServiceLocal to set
     */
    public void setUserServiceLocal(RegistryUserServiceLocal userServiceLocal) {
        this.userServiceLocal = userServiceLocal;
    }

    private void addNciOrgAsCollaborator(StudyProtocolDTO studyProtocolDTO, Ii studyProtocolIi)
            throws TooManyResultsException, PAException {
        StudySiteDTO nCiCollaborator = new StudySiteDTO();
        nCiCollaborator.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
        nCiCollaborator.setStatusCode(CdConverter.convertToCd(StudySiteStatusCode.ACTIVE));
        // The assumption is that there will be only 1 active org with name
        // 'National Cancer Institute' in PO and that while there may be others that contain
        // that name, there will not be a large number to pull in this very broad search.
        OrganizationDTO criteria = new OrganizationDTO();
        String exactString = "National Cancer Institute";
        criteria.setName(EnOnConverter.convertToEnOn(exactString));
        criteria.setStatusCode(CdConverter.convertToCd(EntityStatusCode.ACTIVE));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> listOrgs = new ArrayList<OrganizationDTO>();
        listOrgs = PoRegistry.getOrganizationEntityService().search(criteria, limit);
        for (OrganizationDTO poOrg : listOrgs) {
            if (EnOnConverter.convertEnOnToString(poOrg.getName()).matches(exactString)) {
                Long paOrgId = ocsr.createResearchOrganizationCorrelations(poOrg.getIdentifier().getExtension());
                nCiCollaborator.setResearchOrganizationIi(IiConverter.convertToIi(paOrgId));
                nCiCollaborator.setStudyProtocolIdentifier(studyProtocolIi);
                nCiCollaborator.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.FUNDING_SOURCE));
                nCiCollaborator.setHealthcareFacilityIi(null);
                nCiCollaborator.setIdentifier(null);
                nCiCollaborator.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
                studySiteService.create(nCiCollaborator);
                break;
            }
        }
    }

    /**
     * An action plan and execution of a pre-clinical for amending an existing protocols.
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs List of Study Identifier DTO
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException {
        // CHECKSTYLE:ON

        try {
            validateStudyExist(studyProtocolDTO, AMENDMENT);
            StudyProtocolDTO spDTO = getStudyProtocolForCreateOrAmend(studyProtocolDTO, AMENDMENT);
            if (studyRegAuthDTO != null) {
                studyRegAuthDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
                StudyRegulatoryAuthorityDTO tempDTO = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spDTO
                        .getIdentifier());
                if (tempDTO != null) {
                    studyRegAuthDTO.setIdentifier(tempDTO.getIdentifier());
                }
            }
            updateStudyProtocolObjs(spDTO, overallStatusDTO, leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                    studyIndldeDTOs, studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                    sponsorOrganizationDTO, studyContactDTO, studySiteContactDTO, summary4organizationDTO,
                    summary4studyResourcingDTO, responsiblePartyContactIi, studyRegAuthDTO, null, null, null,
                    AMENDMENT, isBatchMode);
        } catch (Exception e) {
            throw new PAException(e);
        }

        return studyProtocolDTO.getIdentifier();

    }

    private void checkTelecomAddress(DSet<Tel> telecomAddress) throws PAException {
        StringBuffer sb = new StringBuffer();
        sb.append(DSetConverter.getFirstElement(telecomAddress, PAConstants.EMAIL) == null ? EMAIL_NOT_NULL : "");
        sb.append(DSetConverter.getFirstElement(telecomAddress, PAConstants.PHONE) == null ? PHONE_NOT_NULL : "");
        if (sb.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + sb.toString());
        }
    }

    private void createInboxProcessingComments(StudyProtocolDTO studyProtocolDTO)
            throws PAException {
        String inboxProcessingComments = null;
        if (trialRegistrationHelper != null) {
            inboxProcessingComments = trialRegistrationHelper.getInboxProcessingComments();
        }
        if (StringUtils.isNotEmpty(inboxProcessingComments)) {
            StudyInboxDTO studyInboxDTO = new StudyInboxDTO();
            studyInboxDTO.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
            studyInboxDTO.setInboxDateRange(IvlConverter.convertTs()
                    .convertToIvl(new Timestamp(new Date().getTime()), null));
            studyInboxDTO.setComments(StConverter.convertToSt(inboxProcessingComments));
            // create the inbox processing comments.
            studyInboxServiceLocal.create(studyInboxDTO);
        }
    }

    /**
     * An action plan and execution of a pre-clinical or clinical study including all activities to test a particular
     * hypothesis that is the basis of the study regarding the effectiveness of a particular treatment, drug, device,
     * procedure, or care plan. This includes prevention, observational, therapeutic, and other types of studies that
     * involve subjects.:{@link URL}.
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO lead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsor Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs study Identifiers
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO create studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException {
        // CHECKSTYLE:ON
        Ii studyProtocolIi = null;
        try {
            if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
                for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
                    studyResourcingDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
                    studyResourcingDTO.setOrganizationIdentifier(null);
                    studyResourcingDTO.setTypeCode(null);
                }
            }
            studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
            studyProtocolIi = createStudyProtocolObjs(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                    sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs, studyContactDTO,
                    studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                    responsiblePartyContactIi, studyRegAuthDTO, CREATE, isBatchMode);
        } catch (Exception e) {
            throw new PAException(e);
        }
        return studyProtocolIi;

    }

    /**
     * @param studyProtocolDTO studyProtocolDTO
     * @param studySiteAccrualStatusDTO studySiteAccrualStatusDTO
     * @param documentDTOs documentDTOs
     * @param leadOrganizationDTO leadOrganizationDTO
     * @param studySiteInvestigatorDTO studySiteInvestigatorDTO
     * @param leadOrganizationStudySiteDTO leadOrganizationStudySiteDTO
     * @param studySiteOrganizationDTO studySiteOrganizationDTO
     * @param studySiteDTO studySiteDTO
     * @param nctIdentifierDTO nctIdentifierDTO
     * @param summary4OrganizationDTO summary4OrganizationDTO
     * @param summary4StudyResourcingDTO summary4StudyResourcingDTO
     * @param isBatchMode to identify if batch is caller
     * @return Ii s
     * @throws PAException e
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createAbbreviatedInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, OrganizationDTO summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode) throws PAException {
        // CHECKSTYLE:ON
        Ii studyProtocolIi = null;
        StudyTypeCode studyTypeCode = null;
        // validate method needs to be here
        StringBuffer errorMsg = new StringBuffer();
        setPhaseAdditionalQualifier(studyProtocolDTO, studyProtocolDTO);
        setPrimaryPurposeCode(studyProtocolDTO, studyProtocolDTO);
        errorMsg.append(enforceBusinessRulesForProprietary(studyProtocolDTO, studySiteAccrualStatusDTO, documentDTOs,
                leadOrganizationDTO, studySiteInvestigatorDTO, leadOrganizationStudySiteDTO, studySiteOrganizationDTO,
                studySiteDTO, nctIdentifierDTO, summary4OrganizationDTO, summary4StudyResourcingDTO));
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
        studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
        validateSummary4Information(studyProtocolDTO, summary4OrganizationDTO, summary4StudyResourcingDTO);
        List<PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
        listOfDTOToCreateInPO.add(leadOrganizationDTO);
        listOfDTOToCreateInPO.add(studySiteOrganizationDTO);
        listOfDTOToCreateInPO.add(studySiteInvestigatorDTO);
        listOfDTOToCreateInPO.add(summary4OrganizationDTO);
        getPAServiceUtils().createPoObject(listOfDTOToCreateInPO);
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));

        try {
            if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
                studyProtocolIi = studyProtocolService.createInterventionalStudyProtocol(
                         (InterventionalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
                studyTypeCode = StudyTypeCode.INTERVENTIONAL;
            } else {
                studyProtocolIi = studyProtocolService .createObservationalStudyProtocol(
                    (ObservationalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
                studyTypeCode = StudyTypeCode.OBSERVATIONAL;
            }
            getPAServiceUtils().createMilestone(studyProtocolIi, MilestoneCode.SUBMISSION_RECEIVED, null);
            List<DocumentDTO> savedDocs = getPAServiceUtils().createOrUpdate(documentDTOs,
                    IiConverter.convertToDocumentIi(null), studyProtocolIi);
            getPAServiceUtils().manageSummaryFour(studyProtocolIi, summary4OrganizationDTO, summary4StudyResourcingDTO);

            if (leadOrganizationStudySiteDTO != null) {
                leadOrganizationStudySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
                ocsr.createResearchOrganizationCorrelations(leadOrganizationDTO.getIdentifier().getExtension());
                leadOrganizationStudySiteDTO.setResearchOrganizationIi(ocsr
                        .getPoResearchOrganizationByEntityIdentifier(leadOrganizationDTO.getIdentifier()));
                leadOrganizationStudySiteDTO.setFunctionalCode(CdConverter
                        .convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            }
            getPAServiceUtils().manageStudyIdentifiers(leadOrganizationStudySiteDTO);
            nctIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
            nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
            String poOrgId = ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
            nctIdentifierDTO.setResearchOrganizationIi(ocsr.getPoResearchOrganizationByEntityIdentifier(IiConverter
                    .convertToPoOrganizationIi(String.valueOf(poOrgId))));
            getPAServiceUtils().manageStudyIdentifiers(nctIdentifierDTO);
            // create StudySite
            Ii studySiteIi = createStudySite(studyProtocolIi, studySiteOrganizationDTO, studySiteDTO);
            studySiteAccrualStatusDTO.setStudySiteIi(studySiteIi);
            studySiteAccrualStatusService.createStudySiteAccrualStatus(studySiteAccrualStatusDTO);
            // set PI
            createStudySiteContact(studySiteIi, studyProtocolIi, studySiteOrganizationDTO, studySiteInvestigatorDTO,
                    studyTypeCode);
            assignOwnership(studyProtocolDTO, studyProtocolIi);
            getPAServiceUtils().addNciIdentifierToTrial(studyProtocolIi);
            getPAServiceUtils().moveDocumentContents(savedDocs, studyProtocolIi);
            sendMail(CREATE, isBatchMode, studyProtocolIi);
        } catch (Exception e) {
            throw new PAException(e);
        }
        return studyProtocolIi;
    }
    /**This will assign ownership.
     * @param studyProtocolDTO
     * @param isBatchMode
     * @param studyProtocolIi
     * @throws PAException
     */
    private void assignOwnership(StudyProtocolDTO studyProtocolDTO, Ii studyProtocolIi) throws PAException {
        // assign ownership
        RegistryUser usr = userServiceLocal.getUser(StConverter.convertToString(studyProtocolDTO
                .getUserLastCreated()));
        userServiceLocal.assignOwnership(usr.getId(), IiConverter.convertToLong(studyProtocolIi));
        //PO-2646: We're adding an explicit evict of the study protocol so the complete trial is loaded
        //when searched upon later in the trial creation process. Failure to do so was resulting in NPE further down
        //the line.
        Session session = PaHibernateUtil.getCurrentSession();
        session.evict(session.get(StudyProtocol.class, IiConverter.convertToLong(studyProtocolIi)));
    }

    private void createSponsor(Ii studyProtocolIi, OrganizationDTO sponsorOrganizationDto) throws PAException {
        String orgPoIdentifier = sponsorOrganizationDto.getIdentifier().getExtension();
        if (orgPoIdentifier == null) {
            throw new PAException("Organization Identifier is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException(PROTOCOL_ID_NULL);
        }
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException(NO_PROTOCOL_FOUND + studyProtocolIi.getExtension());
        }
        Long roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
        StudySiteDTO studyPartDTO = new StudySiteDTO();
        studyPartDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.SPONSOR.getCode()));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studyPartDTO = studySiteService.create(studyPartDTO);
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private Ii createStudyProtocolObjs(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, String operation, Bl isBatchMode) throws PAException,
            TooManyResultsException {
        // CHECKSTYLE:ON
        validate(studyProtocolDTO, overallStatusDTO, operation, studyResourcingDTOs, documentDTOs, leadOrganizationDTO,
                sponsorOrganizationDTO, summary4organizationDTO, principalInvestigatorDTO, responsiblePartyContactIi,
                studyIndldeDTOs);
        enforceBusinessRules(studyProtocolDTO, overallStatusDTO, documentDTOs, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, studyContactDTO, studySiteContactDTO,
                leadOrganizationSiteIdentifierDTO);
        validateSummary4Information(studyProtocolDTO, summary4organizationDTO, summary4studyResourcingDTO);
        getPAServiceUtils().enforceRegulatoryInfo(studyProtocolDTO, studyRegAuthDTO, studyIndldeDTOs
                , regulatoryInfoBean);

        List<PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
        listOfDTOToCreateInPO.add(leadOrganizationDTO);
        // created only if the ctGovXmlRequired is true
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            listOfDTOToCreateInPO.add(sponsorOrganizationDTO);
        }
        listOfDTOToCreateInPO.add(principalInvestigatorDTO);
        OrganizationDTO newSummary4OrganizationDTO = getPAServiceUtils().findOrCreateEntity(summary4organizationDTO);
        listOfDTOToCreateInPO.add(newSummary4OrganizationDTO);
        getPAServiceUtils().createPoObject(listOfDTOToCreateInPO);

        Ii studyProtocolIi = null;
        StudyTypeCode studyTypeCode = null;
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()
                && !BlConverter.convertToBool(studyProtocolDTO.getFdaRegulatedIndicator())
                && (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty())) {
            studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
            studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));

            // size of ind/ide > 0
        }
        studyProtocolDTO.setIdentifier(null);
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));
        if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
            studyProtocolIi = studyProtocolService.createInterventionalStudyProtocol(
                 (InterventionalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
            studyTypeCode = StudyTypeCode.INTERVENTIONAL;
        } else {
            studyProtocolIi = studyProtocolService.createObservationalStudyProtocol(
               (ObservationalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
            studyTypeCode = StudyTypeCode.OBSERVATIONAL;
        }
        getPAServiceUtils().createMilestone(studyProtocolIi, MilestoneCode.SUBMISSION_RECEIVED, null);
        overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studyOverallStatusService.create(overallStatusDTO);
        getPAServiceUtils().createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null), studyProtocolIi);
        getPAServiceUtils().createOrUpdate(studyResourcingDTOs, IiConverter.convertToStudyResourcingIi(null),
                studyProtocolIi);
        List<DocumentDTO> savedDocs =
            getPAServiceUtils().createOrUpdate(documentDTOs, IiConverter.convertToDocumentIi(null), studyProtocolIi);
        getPAServiceUtils().manageSummaryFour(studyProtocolIi, newSummary4OrganizationDTO, summary4studyResourcingDTO);
        if (leadOrganizationSiteIdentifierDTO != null) {
            leadOrganizationSiteIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
            ocsr.createResearchOrganizationCorrelations(leadOrganizationDTO.getIdentifier().getExtension());
            leadOrganizationSiteIdentifierDTO.setResearchOrganizationIi(ocsr
                    .getPoResearchOrganizationByEntityIdentifier(leadOrganizationDTO.getIdentifier()));
            leadOrganizationSiteIdentifierDTO.setFunctionalCode(CdConverter
                    .convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        }
        getPAServiceUtils().manageStudyIdentifiers(leadOrganizationSiteIdentifierDTO);

        getPAServiceUtils().managePrincipalInvestigator(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO,
                studyTypeCode);
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            createSponsor(studyProtocolIi, sponsorOrganizationDTO);
            getPAServiceUtils().createResponsibleParty(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO,
                    sponsorOrganizationDTO, responsiblePartyContactIi, studyContactDTO, studySiteContactDTO);
        }
        // list of study identifiers like NCT,DCP, CTEP
        for (StudySiteDTO studyIdentifierDTO : studyIdentifierDTOs) {
           if (studyIdentifierDTO != null && !PAUtil.isStNull(studyIdentifierDTO.getLocalStudyProtocolIdentifier())
                    && PAUtil.isIiNotNull(studyIdentifierDTO.getResearchOrganizationIi())) {
             studyIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
             studyIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
             getPAServiceUtils().manageStudyIdentifiers(studyIdentifierDTO);
           }
        }
        addNciOrgAsCollaborator(studyProtocolDTO, studyProtocolIi);
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue() && studyRegAuthDTO != null) {
            studyRegAuthDTO.setStudyProtocolIdentifier(studyProtocolIi);
            getPAServiceUtils().createOrUpdate(Arrays.asList(studyRegAuthDTO),
                    IiConverter.convertToStudyRegulatoryAuthorityIi(null), studyProtocolIi);
        }
        assignOwnership(studyProtocolDTO, studyProtocolIi);
        getPAServiceUtils().addNciIdentifierToTrial(studyProtocolIi);
        getPAServiceUtils().moveDocumentContents(savedDocs, studyProtocolIi);
        sendMail(CREATE, isBatchMode, studyProtocolIi);
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolDTO
     * @param summary4organizationDTO
     * @param summary4studyResourcingDTO
     * @throws PAException
     */
    private void validateSummary4Information(StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO) throws PAException {
        trialRegistrationHelper = new TrialRegistrationHelper(docWrkFlowStatusService, abstractionCompletionService,
                studyProtocolService, studyOverallStatusService, studySiteAccrualStatusService, studyIndldeService,
                studyResourcingService);
        trialRegistrationHelper.enforceSummaryFourSponsorAndCategory(studyProtocolDTO, summary4organizationDTO,
                summary4studyResourcingDTO);
    }

    private void createStudyRelationship(Ii fromStudyProtocolIi, Ii toStudyProtocolIi, StudyProtocolDTO spDto)
            throws PAException {
        StudyRelationshipDTO srDto = new StudyRelationshipDTO();
        srDto.setSequenceNumber(spDto.getSubmissionNumber());
        srDto.setSourceStudyProtocolIdentifier(toStudyProtocolIi);
        srDto.setTargetStudyProtocolIdentifier(fromStudyProtocolIi);
        srDto.setTypeCode(CdConverter.convertToCd(StudyRelationshipTypeCode.MOD));
        studyRelationshipService.create(srDto);
    }

    private Ii createStudySite(Ii studyProtocolIi, OrganizationDTO studySiteDTO, StudySiteDTO siteDTO)
            throws PAException {
        Long paHealthCareFacilityId = ocsr.createHealthCareFacilityCorrelations(studySiteDTO.getIdentifier()
                .getExtension());
        StudySiteDTO sp = new StudySiteDTO();
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        sp.setHealthcareFacilityIi(IiConverter.convertToIi(paHealthCareFacilityId));
        sp.setIdentifier(null);
        sp.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        sp.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        sp.setStudyProtocolIdentifier(studyProtocolIi);
        sp.setProgramCodeText(siteDTO.getProgramCodeText());
        sp.setLocalStudyProtocolIdentifier(siteDTO.getLocalStudyProtocolIdentifier());
        sp.setAccrualDateRange(siteDTO.getAccrualDateRange());
        sp = studySiteService.create(sp);
        return sp.getIdentifier();
    }

    private Ii createStudySiteContact(Ii studySiteIi, Ii studyProtocolIi, OrganizationDTO siteDto, PersonDTO piDto,
            StudyTypeCode studyTypeCode) throws PAException {
        Ii studySiteContactIi = null;
        String orgPoIdentifier = siteDto.getIdentifier().getExtension();
        String perIdentifier = piDto.getIdentifier().getExtension();
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        studySiteContactDTO.setStudySiteIi(studySiteIi);
        Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean().createClinicalResearchStaffCorrelations(
                orgPoIdentifier, perIdentifier);
        Long healthCareProviderIi = null;
        if (studyTypeCode.equals(StudyTypeCode.INTERVENTIONAL)) {
            healthCareProviderIi = new HealthCareProviderCorrelationBean().createHealthCareProviderCorrelationBeans(
                    orgPoIdentifier, perIdentifier);
        }
        studySiteContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
        if (healthCareProviderIi != null) {
            studySiteContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));
        }
        studySiteContactDTO.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR));

        studySiteContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studySiteContactDTO.setStatusCode(CdConverter.convertStringToCd(FunctionalRoleStatusCode.PENDING.getCode()));

        studySiteContactService.create(studySiteContactDTO);
        return studySiteContactIi;
    }

    private List<String> deleteAndReplace(Ii sourceIi, Ii targetIi) {
        String sqlUpd = targetIi.getExtension() + " WHERE STUDY_PROTOCOL_IDENTIFIER = " + sourceIi.getExtension();
        List<String> sqls = new ArrayList<String>();
        String targetId = targetIi.getExtension();
        sqls.add("Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_MILESTONE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE DOCUMENT_WORKFLOW_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension()
                + " and TYPE_CODE = '" + DocumentTypeCode.TSR.getCode() + "'");
        sqls.add("UPDATE DOCUMENT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("UPDATE STUDY_ONHOLD SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from STUDY_OVERALL_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_OVERALL_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from STUDY_RECRUITMENT_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_RECRUITMENT_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("Delete from STUDY_INDLDE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_INDLDE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("Delete from STUDY_RESOURCING WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_RESOURCING SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("DELETE FROM STUDY_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId
                + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
        sqls.add("UPDATE STUDY_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd
                + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
        sqls.add("DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + SQL_APPEND
                + "('RESPONSIBLE_PARTY_SPONSOR')");
        sqls.add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + SQL_APPEND
                + "('RESPONSIBLE_PARTY_SPONSOR')");
        sqls.add("DELETE FROM STUDY_SITE_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND ROLE_CODE IN "
                + "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
        sqls.add("UPDATE STUDY_SITE_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND ROLE_CODE IN "
                + "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
        // nct reject
        sqls.add("DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + SQL_APPEND
                + "('IDENTIFIER_ASSIGNER')");
        sqls
                .add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + SQL_APPEND
                        + "('IDENTIFIER_ASSIGNER')");
        // regulatory
        sqls.add("Delete from  STUDY_REGULATORY_AUTHORITY WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE  STUDY_REGULATORY_AUTHORITY SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("Delete from STUDY_RELATIONSHIP WHERE TARGET_STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension());
        sqls.add("Delete from STUDY_OTHERIDENTIFIERS WHERE STUDY_PROTOCOL_ID  = " + targetId);
        sqls.add("Update STUDY_OTHERIDENTIFIERS SET STUDY_PROTOCOL_ID = " + targetId + " WHERE STUDY_PROTOCOL_ID  = "
                + sourceIi.getExtension());
        sqls.add("Delete from STUDY_PROTOCOL WHERE IDENTIFIER  = " + sourceIi.getExtension());
        return sqls;
    }

    private void deleteSponsor(Ii targetSpIi) {
        String sql = "DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetSpIi.getExtension()
                + SQL_APPEND + "('SPONSOR')";
        getPAServiceUtils().executeSql(sql);
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private void enforceBusinessRules(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO)
    throws PAException {
        // CHECKSTYLE:ON
        StringBuffer sb = new StringBuffer();
        // validate of null objects
        sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
        sb.append(overallStatusDTO == null ? "Study OverallStatus DTO cannot be null , " : "");
        sb.append(documentDTOs == null ? "Document DTO's cannot be null , " : "");
        sb.append(leadOrganizationDTO == null ? "Lead Organization DTO cannot be null , " : "");
        sb.append(principalInvestigatorDTO == null ? "Principal Investigator DTO cannot be null , " : "");
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            sb.append(sponsorOrganizationDTO == null ? "Sponsor Organization DTO cannot be null , " : "");
        }
        // validates for attributes
        sb.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle()) ? "Official Title cannot be null , " : "");
        validatePhase(studyProtocolDTO, sb);
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode()) ? "Trial Start Date Type cannot be null , "
                : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                ? "Primary Completion Date Type cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getStartDate()) ? "Trial Start Date cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
                && studyProtocolDTO.getPrimaryCompletionDate().getNullFlavor() != NullFlavor.UNK
                ? "Primary Completion Date cannot be null , " : "");
        if (leadOrganizationSiteIdentifierDTO != null) {
            sb.append(PAUtil.isStNull(leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier())
                    ? "Local StudyProtocol Identifier cannot be null , " : "");
        }
        sb.append(PAUtil.isIiNull(leadOrganizationDTO.getIdentifier())
                ? "Lead Organization Identifier cannot be null , " : "");
        sb.append(PAUtil.isIiNull(principalInvestigatorDTO.getIdentifier())
                ? "Principal Investigator  Identifier cannot be null , " : "");
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            sb.append(PAUtil.isIiNull(sponsorOrganizationDTO.getIdentifier())
                    ? "Sponsor Organization  Identifier cannot be null , " : "");
        }
        if (overallStatusDTO != null) {
            sb.append(PAUtil.isCdNull(overallStatusDTO.getStatusCode()) ? "Current Trial Status Code cannot be null , "
                    : "");
            sb.append(PAUtil.isTsNull(overallStatusDTO.getStatusDate()) ? "Current Trial Status Date cannot be null , "
                    : "");
        }
        if (sb.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + sb.toString());
        }
        enforceBusinessRulesForStudyContact(studyProtocolDTO, studyContactDTO, studySiteContactDTO);

    }
    /**
     * @param studyProtocolDTO
     * @param sb
     */
    private void validatePhase(StudyProtocolDTO studyProtocolDTO,
            StringBuffer sb) {
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
        if (PhaseCode.getByCode(CdConverter.convertCdToString(studyProtocolDTO.getPhaseCode())) == null) {
                sb.append("Please enter valid value for Phase Code.");
        }
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private String enforceBusinessRulesForProprietary(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, OrganizationDTO summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO) throws PAException {
        // CHECKSTYLE:ON
        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
        errorMsg.append(studySiteAccrualStatusDTO == null ? "Study Site Accrual Status DTO cannot be null , " : "");

        errorMsg.append(leadOrganizationDTO == null ? "Lead Organization DTO cannot be null , " : "");
        errorMsg.append(studySiteInvestigatorDTO == null ? "Principal Investigator DTO cannot be null , " : "");
        errorMsg.append(studySiteOrganizationDTO == null ? "Study Site Organization DTO cannot be null , " : "");

        errorMsg.append(leadOrganizationStudySiteDTO == null ? "Lead Organization Study SiteDTO cannot be null , "
                : "");
        errorMsg.append(studySiteDTO == null ? "Study Site DTO cannot be null , " : "");

        String loginName = "";
        if (!PAUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
            loginName = studyProtocolDTO.getUserLastCreated().getValue();
            CSMUserUtil userService = CSMUserService.getInstance();
            User user = userService.getCSMUser(loginName);
            if (user == null) {
                errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
            }
        } else {
            errorMsg.append("Submitter is required.");
        }
        try {
            // duplicate check only for NCT
            if (!PAUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
                nctIdentifierDTO
                        .setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
                String poOrgId = ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
                nctIdentifierDTO.setResearchOrganizationIi(ocsr.getPoResearchOrganizationByEntityIdentifier(IiConverter
                        .convertToPoOrganizationIi(String.valueOf(poOrgId))));
                studySiteService.validate(nctIdentifierDTO);
            }
        } catch (PAException e) {
            errorMsg.append(e.getMessage());
        }

        // validates for attributes
        errorMsg.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle()) ? "Official Title cannot be null , " : "");
        if (nctIdentifierDTO != null) {
            if (PAUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
                validatePhase(studyProtocolDTO, errorMsg);
                errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode()) ? "Purpose cannot be null , "
                        : "");
                if (CollectionUtils.isEmpty(documentDTOs)
                        || !getPAServiceUtils().isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)) {
                    errorMsg.append("Proprietary template document is mandatory if NCT number is not provided");
                }
            }
        } else {
            validatePhase(studyProtocolDTO, errorMsg);
            errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode()) ? "Purpose cannot be null , "
                    : "");
        }
        if (studySiteDTO != null) {
            errorMsg.append(PAUtil.isStNull(studySiteDTO.getLocalStudyProtocolIdentifier())
                    ? "Submitting Organization Local Trial Identifier cannot be null, " : "");
        }
        if (leadOrganizationStudySiteDTO != null) {
            errorMsg.append(PAUtil.isStNull(leadOrganizationStudySiteDTO.getLocalStudyProtocolIdentifier())
                    ? "Lead Organization Trial Identifier cannot be null, " : "");
        }
        errorMsg.append(getPAServiceUtils().validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO));
        if (summary4StudyResourcingDTO != null && !PAUtil.isCdNull(summary4StudyResourcingDTO.getTypeCode())
                && null == SummaryFourFundingCategoryCode.getByCode(CdConverter
                        .convertCdToString(summary4StudyResourcingDTO.getTypeCode()))) {
            errorMsg.append(CdConverter.convertCdToString(summary4StudyResourcingDTO.getTypeCode())).append(
                    " is not valid Summary Four Funding Category Code");
        }
        errorMsg.append(validatePoObjects(leadOrganizationDTO, "Lead Organization ", false));
        errorMsg.append(validatePoObjects(studySiteOrganizationDTO, "Study Site Organization ", false));
        errorMsg.append(validatePoObjects(summary4OrganizationDTO, "Summary 4 Organization ", false));
        errorMsg.append(validatePoObjects(studySiteInvestigatorDTO, "Study Site Investigator ", false));

        return errorMsg.toString();
    }

    private void enforceBusinessRulesForStudyContact(StudyProtocolDTO studyProtocolDTO,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studySiteContactDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            if (studyContactDTO != null && studySiteContactDTO != null) {
                sb.append("Either StudyContactDTO or studySiteContactDTO should be null ,");
            }
            if (studyContactDTO == null && studySiteContactDTO == null) {
                sb.append("Either StudyContactDTO or studySiteContactDTO should not be null ,");
            }
            if (studyContactDTO != null) {
                checkTelecomAddress(studyContactDTO.getTelecomAddresses());
            }
            if (studySiteContactDTO != null) {
                checkTelecomAddress(studySiteContactDTO.getTelecomAddresses());
            }
            if (sb.length() > 0) {
                throw new PAException(VALIDATION_EXCEPTION + sb.toString());
            }
        }
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private void enforceBusinessRulesForUpdate(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteAccrualStatusDTO> participatingSites, String operation, List<StudySiteDTO> collaborators)
            throws PAException {
        // CHECKSTYLE:ON
        StringBuffer sb = new StringBuffer();
        // validate of null objects
        sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
        sb.append(overallStatusDTO == null ? "Study OverallStatus DTO cannot be null , " : "");

        // validates for attributes
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode()) ? "Trial Start Date Type cannot be null , "
                : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                ? "Primary Completion Date Type cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getStartDate()) ? "Trial Start Date cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
                && studyProtocolDTO.getPrimaryCompletionDate().getNullFlavor() != NullFlavor.UNK
                ? "Primary Completion Date cannot be null , " : "");
        validatePhase(studyProtocolDTO, sb);
        if (overallStatusDTO != null) {
            sb.append(PAUtil.isCdNull(overallStatusDTO.getStatusCode()) ? "Current Trial Status cannot be null , "
                            : "");
            sb.append(PAUtil.isTsNull(overallStatusDTO.getStatusDate()) ? "Current Trial Status Date cannot be null , "
                    : "");
        }
        if (UPDATE.equalsIgnoreCase(operation) && collaborators != null && !collaborators.isEmpty()) {
            for (StudySiteDTO collaborator : collaborators) {
                if (PAUtil.isIiNotNull(collaborator.getIdentifier())
                        && !getPAServiceUtils().isIiExistInPA(IiConverter.convertToStudySiteIi(Long.valueOf(collaborator
                                .getIdentifier().getExtension())))) {
                    sb.append("Collaborator Id " + collaborator.getIdentifier().getExtension() + " does not exist.");
                }
            }
        }
        if (sb.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + sb.toString());
        }
        enforceBusinessRulesForStudyContact(studyProtocolDTO, studyContactDTO, studySiteContactDTO);
        getPAServiceUtils().enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);
        getPAServiceUtils().enforceNoDuplicateGrants(studyResourcingDTOs);
        getPAServiceUtils().enforceRegulatoryInfo(studyProtocolDTO, studyRegAuthDTO, studyIndldeDTOs
                , regulatoryInfoBean);
        if (UPDATE.equalsIgnoreCase(operation)) {
            enforceRecruitmentStatus(studyProtocolDTO, participatingSites);
        }
    }

    private void enforceRecruitmentStatus(StudyProtocolDTO studyProtocolDTO,
            List<StudySiteAccrualStatusDTO> participatingSites) throws PAException {
        if (participatingSites != null && !participatingSites.isEmpty()) {
            StudyRecruitmentStatusDTO recruitmentStatusDto = studyRecruitmentStatusServiceLocal
                    .getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
            getPAServiceUtils().enforceRecruitmentStatus(studyProtocolDTO, participatingSites, recruitmentStatusDto);
        }
    }

    private StudyProtocolDTO getStudyProtocolForCreateOrAmend(StudyProtocolDTO studyProtocolDTO, String operation)
            throws PAException {
        StudyProtocolDTO createStudyProtocolDTO = null;
        if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
            createStudyProtocolDTO = new InterventionalStudyProtocolDTO();
        } else if (studyProtocolDTO instanceof ObservationalStudyProtocolDTO) {
            createStudyProtocolDTO = new ObservationalStudyProtocolDTO();
        }
        if (AMENDMENT.equalsIgnoreCase(operation)) {
            createStudyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(studyProtocolDTO
                    .getIdentifier());
            createStudyProtocolDTO.setAmendmentDate(studyProtocolDTO.getAmendmentDate());
            createStudyProtocolDTO.setAmendmentNumber(studyProtocolDTO.getAmendmentNumber());
        } else {
            createStudyProtocolDTO.setSubmissionNumber(studyProtocolDTO.getSubmissionNumber());
            createStudyProtocolDTO.setIdentifier(null);
        }
        createStudyProtocolDTO.setOfficialTitle(studyProtocolDTO.getOfficialTitle());
        createStudyProtocolDTO.setPhaseCode(studyProtocolDTO.getPhaseCode());
        setPhaseAdditionalQualifier(studyProtocolDTO, createStudyProtocolDTO);
        setPrimaryPurposeCode(studyProtocolDTO, createStudyProtocolDTO);
        createStudyProtocolDTO.setStartDate(studyProtocolDTO.getStartDate());
        createStudyProtocolDTO.setStartDateTypeCode(studyProtocolDTO.getStartDateTypeCode());
        createStudyProtocolDTO.setPrimaryCompletionDate(studyProtocolDTO.getPrimaryCompletionDate());
        createStudyProtocolDTO.setPrimaryCompletionDateTypeCode(studyProtocolDTO.getPrimaryCompletionDateTypeCode());
        createStudyProtocolDTO.setStudyProtocolType(studyProtocolDTO.getStudyProtocolType());
        createStudyProtocolDTO.setProgramCodeText(studyProtocolDTO.getProgramCodeText());
        createStudyProtocolDTO.setFdaRegulatedIndicator(studyProtocolDTO.getFdaRegulatedIndicator());
        createStudyProtocolDTO.setSection801Indicator(studyProtocolDTO.getSection801Indicator());
        createStudyProtocolDTO.setDelayedpostingIndicator(studyProtocolDTO.getDelayedpostingIndicator());
        createStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(studyProtocolDTO
                .getDataMonitoringCommitteeAppointedIndicator());
        createStudyProtocolDTO.setProprietaryTrialIndicator(studyProtocolDTO.getProprietaryTrialIndicator());
        createStudyProtocolDTO.setUserLastCreated(studyProtocolDTO.getUserLastCreated());
        if (studyProtocolDTO.getProprietaryTrialIndicator() == null
                || !studyProtocolDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator() == null) {
                createStudyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
            } else {
                createStudyProtocolDTO.setCtgovXmlRequiredIndicator(studyProtocolDTO.getCtgovXmlRequiredIndicator());
            }
        }
        if (studyProtocolDTO.getSecondaryIdentifiers() != null
                && studyProtocolDTO.getSecondaryIdentifiers().getItem() != null) {
            createStudyProtocolDTO.setSecondaryIdentifiers(studyProtocolDTO.getSecondaryIdentifiers());
        }
        return createStudyProtocolDTO;
    }


    /**
     * Reject a protocol and rollback all the changes.
     * @param studyProtocolIi study protocol identifier
     * @param rejectionReason rejectionReason
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void reject(Ii studyProtocolIi, St rejectionReason) throws PAException {
        try {
            StudyProtocolDTO studyProtocolDto = studyProtocolService.getInterventionalStudyProtocol(studyProtocolIi);
            validate(studyProtocolDto, null, REJECTION, null, null, null, null, null, null, null, null);
            // Original trial Rejection
            if (studyProtocolDto.getSubmissionNumber().getValue().intValue() == 1) {
                StudyMilestoneDTO smDto = new StudyMilestoneDTO();
                smDto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
                smDto.setStudyProtocolIdentifier(studyProtocolIi);
                smDto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_REJECTED));
                smDto.setCommentText(rejectionReason);
                studyMilestoneService.create(smDto);
            } else {
                Ii targetSpIi = studyProtocolIi;
                Ii sourceSpIi = null;
                // search the StudyProtocol to get the latest accepted protocol.
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
                StudyProtocolDTO studyToSearch = new StudyProtocolDTO();
                studyToSearch.setSecondaryIdentifiers(studyProtocolDto.getSecondaryIdentifiers());
                studyToSearch.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));

                List<StudyProtocolDTO> spList = studyProtocolService.search(studyToSearch, limit);
                if (CollectionUtils.isNotEmpty(spList)) {
                    Collections.sort(spList, new Comparator<StudyProtocolDTO>() {
                        public int compare(StudyProtocolDTO o1, StudyProtocolDTO o2) {
                            return o1.getSubmissionNumber().getValue().compareTo(o2.getSubmissionNumber().getValue());
                        }

                    });
                    sourceSpIi = spList.get(spList.size() - 1).getIdentifier();
                }
                if (CollectionUtils.isEmpty(spList)) {
                    throw new PAException("Discrepancies occured while Rejecting the Amended Protocol");
                }
                if (sourceSpIi == null) {
                    throw new PAException("Discrepancies occured while Rejecting the Amended Protocol");
                }
                if (targetSpIi == null) {
                    throw new PAException("Study Relationship not found for the Amended Protocol");

                }
                InterventionalStudyProtocolDTO sourceSpDto = studyProtocolService.getInterventionalStudyProtocol(
                        sourceSpIi);
                // overwrite with the target
                sourceSpDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                // studyProtocolService.updateStudyProtocol(sourceSpDto);
                Session session = PaHibernateUtil.getCurrentSession();
                InterventionalStudyProtocol source = InterventionalStudyProtocolConverter
                        .convertFromDTOToDomain(sourceSpDto);
                Long id = IiConverter.convertToLong(targetSpIi);
                InterventionalStudyProtocol target = (InterventionalStudyProtocol) session.load(
                        InterventionalStudyProtocol.class, id);
                source.setId(target.getId());
                target = source;
                session.merge(target);
                Ii sourceIi = null;
                List<StudyContactDTO> studyContactDtos = getPAServiceUtils().getStudyContact(sourceSpIi,
                        StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
                StudyContactDTO scSourceDTO = null;
                if (PAUtil.getFirstObj(studyContactDtos) != null) {
                    scSourceDTO = PAUtil.getFirstObj(studyContactDtos);
                } else {
                    throw new PAException("Source Study Principal Investigator is not available");
                }
                sourceIi = scSourceDTO.getIdentifier();
                studyContactDtos = getPAServiceUtils().getStudyContact(targetSpIi,
                        StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
                StudyContactDTO scTargetDTO = null;
                if (PAUtil.getFirstObj(studyContactDtos) != null) {
                    scTargetDTO = PAUtil.getFirstObj(studyContactDtos);
                } else {
                    throw new PAException("Target Study Principal Investigator is not available");
                }
                // replace target with source
                scSourceDTO.setIdentifier(scTargetDTO.getIdentifier());
                scSourceDTO.setStudyProtocolIdentifier(targetSpIi);
                studyContactService.delete(sourceIi);
                studyContactService.update(scSourceDTO);

                StudySiteDTO studySiteDto = new StudySiteDTO();
                studySiteDto.setStudyProtocolIdentifier(sourceSpIi);
                studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));

                List<StudySiteDTO> studySiteDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                StudySiteDTO ssSourceDTO = null;
                if (PAUtil.getFirstObj(studySiteDtos) != null) {
                    ssSourceDTO = PAUtil.getFirstObj(studySiteDtos);
                } else {
                    throw new PAException("Source Lead Organization is not available");
                }
                sourceIi = ssSourceDTO.getIdentifier();
                studySiteDto = new StudySiteDTO();
                studySiteDto.setStudyProtocolIdentifier(targetSpIi);
                studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
                studySiteDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                StudySiteDTO ssTargetDTO = null;
                if (PAUtil.getFirstObj(studySiteDtos) != null) {
                    ssTargetDTO = PAUtil.getFirstObj(studySiteDtos);
                } else {
                    throw new PAException("Target Lead Organization is not available");
                }
                ssSourceDTO.setIdentifier(ssTargetDTO.getIdentifier());
                ssSourceDTO.setStudyProtocolIdentifier(targetSpIi);
                studySiteService.delete(sourceIi);
                studySiteService.update(ssSourceDTO);
                if (sourceSpDto.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                    // sponsor
                    studySiteDto = new StudySiteDTO();
                    studySiteDto.setStudyProtocolIdentifier(sourceSpIi);
                    studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));

                    List<StudySiteDTO> studySiteSponsorDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                    StudySiteDTO ssSponsorSourceDTO = null;
                    if (PAUtil.getFirstObj(studySiteSponsorDtos) != null) {
                        ssSponsorSourceDTO = PAUtil.getFirstObj(studySiteSponsorDtos);
                    } else {
                        throw new PAException("Source Lead Organization is not available");
                    }
                    sourceIi = ssSponsorSourceDTO.getIdentifier();
                    studySiteDto = new StudySiteDTO();
                    studySiteDto.setStudyProtocolIdentifier(targetSpIi);
                    studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
                    studySiteSponsorDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                    StudySiteDTO ssSponsorTargetDTO = null;
                    if (PAUtil.getFirstObj(studySiteSponsorDtos) != null) {
                        ssSponsorTargetDTO = PAUtil.getFirstObj(studySiteSponsorDtos);
                        ssSponsorSourceDTO.setIdentifier(ssSponsorTargetDTO.getIdentifier());
                        ssSponsorSourceDTO.setStudyProtocolIdentifier(targetSpIi);
                        studySiteService.delete(sourceIi);
                        studySiteService.update(ssSponsorSourceDTO);
                    } else {
                        updateSponsor(sourceSpIi, targetSpIi);
                    }
                } else {
                    if (studyProtocolDto.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                        deleteSponsor(targetSpIi);
                    }
                }
                getPAServiceUtils().executeSql(deleteAndReplace(sourceSpIi, targetSpIi));
            }
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * @param operation
     * @param isBatchMode
     * @param studyProtocolIi
     * @throws PAException
     */
    private void sendMail(String operation, Bl isBatchMode, Ii studyProtocolIi) throws PAException {
        if (PAUtil.isBlNull(isBatchMode) || !BlConverter.convertToBool(isBatchMode)) {
            if (AMENDMENT.equalsIgnoreCase(operation)) {
                mailManagerSerivceLocal.sendAmendNotificationMail(studyProtocolIi);
            } else if (UPDATE.equalsIgnoreCase(operation)) {
                mailManagerSerivceLocal.sendUpdateNotificationMail(studyProtocolIi);
            } else if (CREATE.equalsIgnoreCase(operation)) {
                mailManagerSerivceLocal.sendNotificationMail(studyProtocolIi);
            }
        }
    }

    /**
     * An action plan and execution of a pre-clinical for Updating an existing protocols.
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIdentifierDTOs List of Study Identifier
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs IRB document
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO StudySiteContactDTO
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO updated studyRegAuthDTO
     * @param collaboratorDTOs list of updated collaborators
     * @param studySiteAccrualStatusDTOs list of updated participating sites
     * @param studySiteDTOs list of StudySite DTOs with updated program code
     * @param isBatchMode to identify if batch is caller
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudySiteDTO> studyIdentifierDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO, StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaboratorDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs, Bl isBatchMode) throws PAException {
        // CHECKSTYLE:ON

        try {
            validateStudyExist(studyProtocolDTO, UPDATE);
            CorrelationUtils cUtils = new CorrelationUtils();
            OrganizationDTO leadOrgDTO = new OrganizationDTO();
            StudySiteDTO studySiteDto = new StudySiteDTO();
            studySiteDto.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
            studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            studySiteDto = PAUtil.getFirstObj(getPAServiceUtils().getStudySite(studySiteDto, true));
            leadOrgDTO.setIdentifier(IiConverter.convertToIi(cUtils.getPAOrganizationByIi(
                    studySiteDto.getResearchOrganizationIi()).getIdentifier()));
            // updated only if the ctGovXmlRequired is true
            OrganizationDTO sponsorOrgDTO = new OrganizationDTO();
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                studySiteDto = new StudySiteDTO();
                studySiteDto.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
                studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
                studySiteDto = PAUtil.getFirstObj(getPAServiceUtils().getStudySite(studySiteDto, true));
                sponsorOrgDTO.setIdentifier(IiConverter.convertToIi(cUtils.getPAOrganizationByIi(
                        studySiteDto.getResearchOrganizationIi()).getIdentifier()));
            }
            PersonDTO principalInvestigatorDTO = new PersonDTO();
            StudyContactDTO studyContactDto = PAUtil.getFirstObj(getPAServiceUtils().getStudyContact(studyProtocolDTO
                    .getIdentifier(), StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true));
            principalInvestigatorDTO.setIdentifier(IiConverter.convertToIi(cUtils.getPAPersonByIi(
                    studyContactDto.getClinicalResearchStaffIi()).getIdentifier()));

            InterventionalStudyProtocolDTO dbStudyProtocolDTO = studyProtocolService
                    .getInterventionalStudyProtocol(studyProtocolDTO.getIdentifier());
            setPrimaryPurposeCode(studyProtocolDTO, dbStudyProtocolDTO);
            dbStudyProtocolDTO.setStartDate(studyProtocolDTO.getStartDate());
            dbStudyProtocolDTO.setStartDateTypeCode(studyProtocolDTO.getStartDateTypeCode());
            dbStudyProtocolDTO.setPrimaryCompletionDate(studyProtocolDTO.getPrimaryCompletionDate());
            dbStudyProtocolDTO.setPrimaryCompletionDateTypeCode(studyProtocolDTO.getPrimaryCompletionDateTypeCode());
            dbStudyProtocolDTO.setFdaRegulatedIndicator(studyProtocolDTO.getFdaRegulatedIndicator());
            dbStudyProtocolDTO.setSection801Indicator(studyProtocolDTO.getSection801Indicator());
            dbStudyProtocolDTO.setDelayedpostingIndicator(studyProtocolDTO.getDelayedpostingIndicator());
            dbStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(studyProtocolDTO
                    .getDataMonitoringCommitteeAppointedIndicator());
            dbStudyProtocolDTO.setProgramCodeText(studyProtocolDTO.getProgramCodeText());
            dbStudyProtocolDTO.setSecondaryIdentifiers(studyProtocolDTO.getSecondaryIdentifiers());
            // Even though we are setting UserLastCreated value which came from DB, the value will not be updated in DB.
            // UserLastCreated is used as a place holder to determine the currently logged in user and/or the person
            // submitting the update.
            // Also, to determine the owner of the trial. Remove this line when the ejbContext.callerPrincipal will give
            // the userLogged in value.
            dbStudyProtocolDTO.setUserLastCreated(studyProtocolDTO.getUserLastCreated());
            updateStudyProtocolObjs(dbStudyProtocolDTO, overallStatusDTO, null, studyIdentifierDTOs, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs, leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO,
                    studyContactDTO, studyParticipationContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                    responsiblePartyContactIi, studyRegAuthDTO, collaboratorDTOs, studySiteAccrualStatusDTOs,
                    studySiteDTOs, UPDATE, isBatchMode);
            StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
            List<StudyInboxDTO> inbox = studyInboxServiceLocal.getByStudyProtocol(studyProtocolDTO.getIdentifier());
            sendTSRXML(studyProtocolDTO.getIdentifier(), smDto.getMilestoneCode(), inbox);
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    private void updateParticipatingSites(List<StudySiteAccrualStatusDTO> participatingSites) throws PAException {
        if (participatingSites != null) {
            for (StudySiteAccrualStatusDTO sdto : participatingSites) {
                studySiteAccrualStatusService.createStudySiteAccrualStatus(sdto);
            }
        }
    }

    private void updateSponsor(Ii sourceSpIi, Ii targetSpIi) {
        String sqlUpd = targetSpIi.getExtension() + " WHERE STUDY_PROTOCOL_IDENTIFIER = " + sourceSpIi.getExtension();
        String sql = "UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + SQL_APPEND + "('SPONSOR')";
        getPAServiceUtils().executeSql(sql);
    }

    private Ii updateStudyProtocol(StudyProtocolDTO studyProtocolDTO, Ii toStudyProtocolIi, String operation)
        throws PAException {
        if (AMENDMENT.equalsIgnoreCase(operation)) {
            ByteArrayOutputStream rtfStream = tsrReportService.generateRtfTsrReport(studyProtocolDTO.getIdentifier());
            DocumentDTO docDto = new DocumentDTO();
            docDto.setStudyProtocolIdentifier(toStudyProtocolIi);
            docDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
            docDto.setText(EdConverter.convertToEd(rtfStream.toByteArray()));
            docDto.setFileName(StConverter.convertToSt("TSR.rtf"));

            documentService.create(docDto);
            // reset milestones
            String sql = null;
            Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
            sql = "Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + studyProtocolIi.getExtension();
            getPAServiceUtils().executeSql(sql);
            sql = "Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = "
                    + studyProtocolIi.getExtension();
            getPAServiceUtils().executeSql(sql);
            sql = "Delete from STUDY_ONHOLD WHERE STUDY_PROTOCOL_IDENTIFIER  = " + studyProtocolIi.getExtension();
            getPAServiceUtils().executeSql(sql);
            studyProtocolDTO.setAmendmentReasonCode(null);
            studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt(getPAServiceUtils()
                    .generateSubmissionNumber(PAUtil.getAssignedIdentifierExtension(studyProtocolDTO))));
            studyProtocolDTO.setStatusDate(TsConverter.convertToTs(null));
        }
        if (UPDATE.equalsIgnoreCase(operation)) {
            studyProtocolDTO.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
        }
        studyProtocolService.updateStudyProtocol(studyProtocolDTO);
        return studyProtocolDTO.getIdentifier();
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private void updateStudyProtocolObjs(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO, List<StudySiteDTO> studyIdentifierDTOs,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, List<StudySiteDTO> collaborators,
            List<StudySiteAccrualStatusDTO> participatingSites, List<StudySiteDTO> pgCdUpdatedList, String operation,
            Bl isBatchMode) throws PAException {
        // CHECKSTYLE:ON

        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        Ii toStudyProtocolIi = null;
        validate(studyProtocolDTO, overallStatusDTO, operation, studyResourcingDTOs, documentDTOs, leadOrganizationDTO,
                sponsorOrganizationDTO, summary4organizationDTO, principalInvestigatorDTO, responsiblePartyContactIi,
                studyIndldeDTOs);

        enforceBusinessRulesForUpdate(studyProtocolDTO, overallStatusDTO, studyContactDTO, studySiteContactDTO,
                studyIndldeDTOs, studyResourcingDTOs, studyRegAuthDTO, participatingSites, operation, collaborators);
        validateSummary4Information(studyProtocolDTO, summary4organizationDTO, summary4studyResourcingDTO);
        if (UPDATE.equalsIgnoreCase(operation)) {
            trialRegistrationHelper = new TrialRegistrationHelper(docWrkFlowStatusService, abstractionCompletionService,
                    studyProtocolService, studyOverallStatusService, studySiteAccrualStatusService, studyIndldeService,
                    studyResourcingService);
            trialRegistrationHelper.checkForInboxProcessingComments(studyProtocolDTO, documentDTOs, overallStatusDTO,
                    participatingSites, studyIndldeDTOs, studyResourcingDTOs);
        } else if (AMENDMENT.equalsIgnoreCase(operation)) {
            toStudyProtocolIi = getPAServiceUtils().copy(studyProtocolDTO.getIdentifier());
        }
        // update studyProtocol
        studyProtocolIi = updateStudyProtocol(studyProtocolDTO, toStudyProtocolIi, operation);
        // list of study identifiers like NCT,DCP, CTEP
        if (studyIdentifierDTOs != null) {
            for (StudySiteDTO studyIdentifierDTO : studyIdentifierDTOs) {
                if (studyIdentifierDTO != null && !PAUtil.isStNull(studyIdentifierDTO.getLocalStudyProtocolIdentifier())
                        && PAUtil.isIiNotNull(studyIdentifierDTO.getResearchOrganizationIi())) {
                    studyIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
                    studyIdentifierDTO.setFunctionalCode(CdConverter
                            .convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
                    getPAServiceUtils().manageStudyIdentifiers(studyIdentifierDTO);
                }
            }
        }
        getPAServiceUtils().createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null), studyProtocolIi);
        getPAServiceUtils().createOrUpdate(studyResourcingDTOs, IiConverter.convertToStudyResourcingIi(null),
                studyProtocolIi);

        if (UPDATE.equalsIgnoreCase(operation)) {
            List<StudySiteDTO> collaboratorDTOs = new ArrayList<StudySiteDTO>();
            if (CollectionUtils.isNotEmpty(collaborators)) {
                for (StudySiteDTO collaborator : collaborators) {
                    StudySiteDTO dbCollaborator = studySiteService.get(collaborator.getIdentifier());
                    dbCollaborator.setFunctionalCode(collaborator.getFunctionalCode());
                    collaboratorDTOs.add(dbCollaborator);
                }
            }
            getPAServiceUtils().createOrUpdate(collaboratorDTOs, IiConverter.convertToStudySiteIi(null)
                    , studyProtocolIi);
            updateParticipatingSites(participatingSites);
            getPAServiceUtils().createOrUpdate(pgCdUpdatedList, IiConverter.convertToStudySiteIi(null)
                    , studyProtocolIi);
        }
        // updated only if the ctGovXmlRequired is true
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            // regulatory auth update
            if (studyRegAuthDTO != null) {
                List<StudyRegulatoryAuthorityDTO> sraDto = new ArrayList<StudyRegulatoryAuthorityDTO>();
                sraDto.add(studyRegAuthDTO);
                getPAServiceUtils().createOrUpdate(sraDto, IiConverter.convertToStudyRegulatoryAuthorityIi(null),
                        studyProtocolIi);

            }
            // responsible party and sponsor update
            getPAServiceUtils().removeResponsibleParty(studyProtocolDTO.getIdentifier());
            if (AMENDMENT.equalsIgnoreCase(operation)) {
                getPAServiceUtils().manageSponsor(studyProtocolIi, sponsorOrganizationDTO);
            }
            getPAServiceUtils().createResponsibleParty(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO,
                    sponsorOrganizationDTO, responsiblePartyContactIi, studyContactDTO, studySiteContactDTO);
        } else {
            if (AMENDMENT.equalsIgnoreCase(operation)) {
                // remove study regulatory authority
                getPAServiceUtils().removeRegulatoryAuthority(studyProtocolDTO.getIdentifier());
                // remove the associated responsible party/sponsor
                getPAServiceUtils().removeResponsibleParty(studyProtocolDTO.getIdentifier());
                // remove sponsor
                getPAServiceUtils().removeSponsor(studyProtocolDTO.getIdentifier());

            }
        }

        // update summary4
        getPAServiceUtils().manageSummaryFour(studyProtocolIi, summary4organizationDTO, summary4studyResourcingDTO);
        if (AMENDMENT.equalsIgnoreCase(operation)) {

            if (leadOrganizationSiteIdentifierDTO != null) {
                leadOrganizationSiteIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
                ocsr.createResearchOrganizationCorrelations(leadOrganizationDTO.getIdentifier().getExtension());
                leadOrganizationSiteIdentifierDTO.setResearchOrganizationIi(ocsr
                        .getPoResearchOrganizationByEntityIdentifier(leadOrganizationDTO.getIdentifier()));
                leadOrganizationSiteIdentifierDTO.setFunctionalCode(CdConverter
                        .convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            }
            getPAServiceUtils().manageStudyIdentifiers(leadOrganizationSiteIdentifierDTO);

            getPAServiceUtils().managePrincipalInvestigator(studyProtocolIi, leadOrganizationDTO
                    , principalInvestigatorDTO, StudyTypeCode.INTERVENTIONAL);
            overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
            createStudyRelationship(studyProtocolIi, toStudyProtocolIi, studyProtocolDTO);
            getPAServiceUtils().createMilestone(studyProtocolIi, MilestoneCode.SUBMISSION_RECEIVED, null);
        }
        studyOverallStatusService.create(overallStatusDTO);
        List<DocumentDTO> savedDocs = getPAServiceUtils().createOrUpdate(documentDTOs,
                IiConverter.convertToDocumentIi(null), studyProtocolDTO.getIdentifier());
        if (UPDATE.equalsIgnoreCase(operation)) {
            createInboxProcessingComments(studyProtocolDTO);
        }
        getPAServiceUtils().moveDocumentContents(savedDocs, studyProtocolDTO.getIdentifier());
        // do not send the mail when its batch mode
        sendMail(operation, isBatchMode, studyProtocolIi);
    }

    // CHECKSTYLE:OFF More than 7 Parameters
    private void validate(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO, String operation,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, OrganizationDTO sponsorOrganizationDTO,
            OrganizationDTO summary4organizationDTO, PersonDTO piPersonDTO, Ii responsiblePartyContactIi,
            List<StudyIndldeDTO> studyIndldeDTOs) throws PAException {
        // CHECKSTYLE:ON
        StringBuffer errorMsg = new StringBuffer();
        // is user valid
        String loginName = "";
        if (!PAUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
            loginName = studyProtocolDTO.getUserLastCreated().getValue();
            CSMUserUtil userService = CSMUserService.getInstance();
            User user = userService.getCSMUser(loginName);
            if (user == null) {
                errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
            }
        } else {
            errorMsg.append("Submitter is required.");
        }
        if (REJECTION.equalsIgnoreCase(operation)) {
            DocumentWorkflowStatusDTO dws = docWrkFlowStatusService.getCurrentByStudyProtocol(studyProtocolDTO
                    .getIdentifier());
            if (!DocumentWorkflowStatusCode.SUBMITTED.getCode().equals(dws.getStatusCode().getCode())
                 && !DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED.getCode().equals(dws.getStatusCode().getCode())) {
                errorMsg.append("Only Trials with SUBMITTED can be REJECTED");
            }
        } else {
            try {
                studyOverallStatusService.validate(overallStatusDTO, studyProtocolDTO);
            } catch (PAException e) {
                errorMsg.append(e.getMessage());
            }
            if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
                for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
                    try {
                        studyResourcingDTO.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
                        studyResourcingService.validate(studyResourcingDTO);
                    } catch (PAException e) {
                        errorMsg.append(e.getMessage());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
                for (StudyIndldeDTO indIdeDto : studyIndldeDTOs) {
                    try {
                        indIdeDto.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
                        studyIndldeService.validate(indIdeDto);
                    } catch (PAException e) {
                        errorMsg.append(e.getMessage());
                    }
                }
            }
        }
        if (AMENDMENT.equalsIgnoreCase(operation) || UPDATE.equalsIgnoreCase(operation)) {
            DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService
                    .getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
            String dwfs = isoDocWrkStatus.getStatusCode().getCode();
            if (!userServiceLocal.hasTrialAccess(loginName, Long.parseLong(studyProtocolDTO.getIdentifier()
                    .getExtension()))) {
                errorMsg.append(operation)
                        .append("to Trial can be submitted by the submitter of the original Trial.\n");
            }
            StudyOverallStatusDTO statusDTO = studyOverallStatusService.getCurrentByStudyProtocol(studyProtocolDTO
                    .getIdentifier());
            if ((AMENDMENT.equalsIgnoreCase(operation))
                    && (!(dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode()) || dwfs
                            .equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())))) {
                errorMsg.append("Trial with processing status Abstraction Verified Response or "
                        + " Abstraction Verified No Response can be Amended.\n");
            }
            if ((UPDATE.equalsIgnoreCase(operation))
                    && (dwfs.equals(DocumentWorkflowStatusCode.SUBMITTED.getCode()) || dwfs
                            .equals(DocumentWorkflowStatusCode.REJECTED.getCode()))) {
                errorMsg.append("Only Trials with processing status Accepted or Abstracted or  "
                        + " Abstraction Verified No Response or  "
                        + " Abstraction Verified No Response can be Updated.");
            }
            if (statusDTO.getStatusCode().getCode().equals(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode())
                    || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.WITHDRAWN.getCode())
                    || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.COMPLETE.getCode())) {
                errorMsg.append(operation).append(
                        " to a Trial with Current Trial Status as Disapproved or"
                                + " Withdrawn or Complete or Administratively Complete is not allowed.\n");
            }
        }
        if (CREATE.equalsIgnoreCase(operation) || AMENDMENT.equalsIgnoreCase(operation)) {
            errorMsg.append(getPAServiceUtils().checkDocumentListForValidFileTypes(documentDTOs));
            if (!getPAServiceUtils().isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)) {
                errorMsg.append("Protocol Document is required.\n");
            }
            if (!getPAServiceUtils().isDocumentInList(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT)) {
                errorMsg.append("IRB Approval Document is required.\n");
            }
            errorMsg.append(validatePoObjects(leadOrganizationDTO, "Lead Organization ", true));
            // if ctGovXMLreq is true
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                errorMsg.append(validatePoObjects(sponsorOrganizationDTO, "Sponsor Organization ", true));
            }
            errorMsg.append(validatePoObjects(summary4organizationDTO, "Summary 4 Organization ", false));
            errorMsg.append(validatePoObjects(piPersonDTO, "Principal Investigator ", false));
        }
        if (UPDATE.equalsIgnoreCase(operation) && documentDTOs != null && !documentDTOs.isEmpty()) {
            for (DocumentDTO docDto : documentDTOs) {
                if (PAUtil.isIiNotNull(docDto.getIdentifier())
                        && !getPAServiceUtils().isIiExistInPA(IiConverter.convertToDocumentIi(Long.valueOf(docDto
                                .getIdentifier().getExtension())))) {
                    errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exits.");
                }
            }
            errorMsg.append(validatePoObjects(summary4organizationDTO, "Summary 4 Organization ", false));
        }
        if (AMENDMENT.equalsIgnoreCase(operation)
                && !getPAServiceUtils().isDocumentInList(documentDTOs, DocumentTypeCode.CHANGE_MEMO_DOCUMENT)) {
            errorMsg.append("Change Memo Document is required.\n");
        }
        // if ctGovXMLreq is true - then perform the validation.
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()
                && PAUtil.isIiNotNull(responsiblePartyContactIi)
                && !getPAServiceUtils().isIiExistInPO(responsiblePartyContactIi)) {
            errorMsg.append("Error getting Responsible Party Contact from PO for id = "
                    + responsiblePartyContactIi.getExtension() + ".  ");
        }
        if (AMENDMENT.equalsIgnoreCase(operation) && PAUtil.isTsNull(studyProtocolDTO.getAmendmentDate())) {
            errorMsg.append("Amendment Date is required.  ");
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
        }
    }

    private <TYPE extends PoDto> String validatePoObjects(TYPE poDTO, String fieldName, boolean iiRequired) {
        String strNewLine = ".\n";
        StringBuffer errorMsg = new StringBuffer();
        
        if (iiRequired && PAUtil.isIiNull(((EntityDto) poDTO).getIdentifier())) {
            errorMsg.append("Error getting ").append(fieldName)
                .append(" from PO. Identifier is required ").append(strNewLine);
        }
        
        if (poDTO instanceof OrganizationDTO && PAUtil.isIiNotNull(((OrganizationDTO) poDTO).getIdentifier())) {
            if (!getPAServiceUtils().isIiExistInPO(((OrganizationDTO) poDTO).getIdentifier())) {
                errorMsg.append("Error getting ").append(fieldName).append(" from PO for id = ").append(
                        ((OrganizationDTO) poDTO).getIdentifier().getExtension()).append(strNewLine);
            }
        } else if (poDTO instanceof PersonDTO && PAUtil.isIiNotNull(((PersonDTO) poDTO).getIdentifier())) {
            if (!getPAServiceUtils().isIiExistInPO(((PersonDTO) poDTO).getIdentifier())) {
                errorMsg.append("Error getting ").append(fieldName).append(" from PO for id = ").append(
                        ((PersonDTO) poDTO).getIdentifier().getExtension()).append(strNewLine);
            }
        } else {
            String poValidErr = getPAServiceUtils().isDTOValidInPO(poDTO);
            if (StringUtils.isNotEmpty(poValidErr)) {
                errorMsg.append("Error validating ").append(fieldName).append(strNewLine).append(poValidErr);
            }
        }
        return errorMsg.toString();
    }

    private void validateStudyExist(StudyProtocolDTO studyProtocolDTO, String operation) throws PAException {
        if (AMENDMENT.equalsIgnoreCase(operation) || UPDATE.equalsIgnoreCase(operation)) {
            // make sure Trial Exist
            InterventionalStudyProtocolDTO dto = studyProtocolService.getInterventionalStudyProtocol(studyProtocolDTO
                    .getIdentifier());
            if (dto == null) {
                throw new PAException("No Trial found for given Trial Identifier.\n");
            }
            if (!PAUtil.isBlNull(dto.getProprietaryTrialIndicator())
                    && dto.getProprietaryTrialIndicator().getValue().booleanValue()) {
                throw new PAException(operation + " to Proprietary trial is not supported. ");
            }
        }
    }
}
