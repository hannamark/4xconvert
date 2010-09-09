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
 */package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.TrialRegistrationHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/**
 * Prop trial Management Bean for registering and updating the protocol.
 * @author Naveen Amiruddin
 * @since 05/24/2010
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(HibernateSessionInterceptor.class)
public class ProprietaryTrialManagementBeanLocal implements ProprietaryTrialManagementServiceLocal {

    private static final String VALIDATION_EXCEPTION = "Validation Exception ";
    private SessionContext ejbContext;
    private static PAServiceUtils paServiceUtils = new PAServiceUtils();

    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private MailManagerServiceLocal mailManagerSerivceLocal;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkFlowStatusService;
    @EJB
    private StudyInboxServiceLocal studyInboxServiceLocal;
    @EJB
    private DocumentServiceLocal documentService;
    @EJB
    private StudyMilestoneServicelocal studyMilestoneService;
    @EJB
    private RegistryUserServiceLocal userServiceLocal;

    private TrialRegistrationHelper trialRegistrationHelper = null;

    @Resource
    void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

    /**
     * update a proprietary trial.
     * @param studyProtocolDTO study protocol dto
     * @param leadOrganizationDTO lead organization dto
     * @param summary4OrganizationDTO summary 4 organization dto
     * @param leadOrganizationIdentifier lead organization identifier
     * @param nctIdentifier nct Identifier
     * @param summary4TypeCode summary 4 type code
     * @param documentDTOs list of dtos
     * @param studySiteDTOs list of study site dtos
     * @param studySiteAccrualDTOs list of study site Accrual status
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @SuppressWarnings("PMD.ExcessiveParameterList")
    // CHECKSTYLE:OFF More than 7 Parameters
    public void update(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO,
            OrganizationDTO summary4OrganizationDTO, St leadOrganizationIdentifier, St nctIdentifier,
            Cd summary4TypeCode, List<DocumentDTO> documentDTOs, List<StudySiteDTO> studySiteDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs) throws PAException {
        // CHECKSTYLE:ON
        if (studyProtocolDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol DTO is null");
        }
        if (PAUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol DTO identifier is null");
        }
        try {
            StudyProtocolDTO spDto = studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier());
            studyProtocolDTO.setProprietaryTrialIndicator(spDto.getProprietaryTrialIndicator());
            validate(studyProtocolDTO, leadOrganizationDTO, leadOrganizationIdentifier, nctIdentifier, documentDTOs,
                    studySiteDTOs, studySiteAccrualDTOs);
            trialRegistrationHelper = new TrialRegistrationHelper(docWrkFlowStatusService, null,
                    studyProtocolService, null, studySiteAccrualStatusService, null, null);

            StudyResourcingDTO summary4StudyResourcingDTO = new StudyResourcingDTO();
            summary4StudyResourcingDTO.setTypeCode(summary4TypeCode);
            trialRegistrationHelper.enforceSummaryFourSponsorAndCategory(studyProtocolDTO, summary4OrganizationDTO,
                    summary4StudyResourcingDTO);
            // the validation are done, proceed to update
            Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
            spDto.setOfficialTitle(studyProtocolDTO.getOfficialTitle());
            spDto.setPrimaryPurposeCode(studyProtocolDTO.getPrimaryPurposeCode());
            spDto.setPhaseCode(studyProtocolDTO.getPhaseCode());
            studyProtocolService.updateStudyProtocol(spDto);
            updateLeadOrganization(paServiceUtils.findOrCreateEntity(leadOrganizationDTO), leadOrganizationIdentifier,
                    studyProtocolIi);
            updateNctIdentifier(nctIdentifier, studyProtocolIi);
            StudyResourcingDTO srDto = null;
            if (!PAUtil.isCdNull(summary4TypeCode)) {
                srDto = new StudyResourcingDTO();
                srDto.setTypeCode(summary4TypeCode);
            }
            paServiceUtils.manageSummaryFour(studyProtocolIi,
                    (summary4OrganizationDTO != null ? paServiceUtils.findOrCreateEntity(summary4OrganizationDTO)
                            : null), srDto);
            for (StudySiteDTO ssDto : studySiteDTOs) {
                StudySiteDTO studySiteDto = studySiteService.get(ssDto.getIdentifier());
                studySiteDto.setProgramCodeText(ssDto.getProgramCodeText());
                studySiteDto.setLocalStudyProtocolIdentifier(ssDto.getLocalStudyProtocolIdentifier());
                studySiteDto.setAccrualDateRange(ssDto.getAccrualDateRange());
                studySiteService.update(studySiteDto);
            }

            for (StudySiteAccrualStatusDTO ssasDto : studySiteAccrualDTOs) {
                ssasDto.setIdentifier(null);
                studySiteAccrualStatusService.createStudySiteAccrualStatus(ssasDto);
            }
            paServiceUtils.createOrUpdate(documentDTOs, IiConverter.convertToDocumentIi(null),
                    studyProtocolDTO.getIdentifier());
            studyInboxServiceLocal.create(documentDTOs, studyProtocolIi);
            mailManagerSerivceLocal.sendUpdateNotificationMail(studyProtocolIi);
            StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(studyProtocolIi);
            List<StudyInboxDTO> inbox = studyInboxServiceLocal.getByStudyProtocol(studyProtocolIi);
            if (!isActiveRecordInInbox(inbox)
                    && MilestoneCode.isAboveTrialSummaryReport(MilestoneCode.getByCode(CdConverter
                            .convertCdToString(smDto.getMilestoneCode())))) {
                paServiceUtils.createMilestone(studyProtocolIi, MilestoneCode.TRIAL_SUMMARY_SENT, null);
            }
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }

    /**
     * @param inbox
     */
    private boolean isActiveRecordInInbox(List<StudyInboxDTO> inbox) {
        boolean activeRecord = false;
        if (CollectionUtils.isNotEmpty(inbox)) {
            for (StudyInboxDTO inboxDto : inbox) {
                String strCloseDate = IvlConverter.convertTs().convertHighToString(inboxDto.getInboxDateRange());
                if (StringUtils.isEmpty(strCloseDate)) {
                    activeRecord = true;
                }
            }
        }
        return activeRecord;
    }

    private void updateLeadOrganization(OrganizationDTO leadOrg, St leadOrganizationIdentifier, Ii studyProtocolIi)
            throws PAException {
        StudySiteDTO ssCriteriaDTO = new StudySiteDTO();
        ssCriteriaDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        ssCriteriaDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<StudySiteDTO> studySiteDtos = paServiceUtils.getStudySite(ssCriteriaDTO, true);
        StudySiteDTO studySiteDTO = PAUtil.getFirstObj(studySiteDtos);
        if (studySiteDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Lead organization not found for Study Protocol "
                    + studyProtocolIi.getExtension());
        }
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToIi(PaRegistry.getOrganizationCorrelationService()
                .createResearchOrganizationCorrelations(leadOrg.getIdentifier().getExtension())));
        studySiteDTO.setLocalStudyProtocolIdentifier(leadOrganizationIdentifier);
        studySiteService.update(studySiteDTO);
    }

    private void updateNctIdentifier(St nctIdentifier, Ii studyProtocolIi) throws PAException {
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        nctIdentifierDTO.setLocalStudyProtocolIdentifier(nctIdentifier);
        nctIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
        nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId =
                PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                        PAConstants.NCT_IDENTIFIER_TYPE);
        nctIdentifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService()
                .getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
        paServiceUtils.manageStudyIdentifiers(nctIdentifierDTO);
    }

    @SuppressWarnings({"PMD.ExcessiveParameterList" })
    private void validate(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO,
            St leadOrganizationIdentifier, St nctIdentifier, List<DocumentDTO> documentDTOs,
            List<StudySiteDTO> studySiteDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        notNullCheck(studyProtocolDTO, leadOrganizationDTO, leadOrganizationIdentifier, nctIdentifier, errorMsg);
        validateOwner(studyProtocolDTO, errorMsg);
        if (PAUtil.isStNull(nctIdentifier)
                && CollectionUtils.isEmpty(documentService.getByStudyProtocol(studyProtocolDTO.getIdentifier()))) {
            errorMsg.append("NCT identifier is required as there are no Documents");
        }
        if (CollectionUtils.isNotEmpty(studySiteDTOs)) {
            for (StudySiteDTO studySiteDto : studySiteDTOs) {
                if (PAUtil.isIiNull(studySiteDto.getStudyProtocolIdentifier())) {
                    errorMsg.append("Study Protocol Identifier  from Study Site cannot be null ");
                }
                if (PAUtil.isIiNull(studySiteDto.getIdentifier())) {
                    errorMsg.append("Study Site Identifier cannot be null ");
                }
            }
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
        validateStudySites(studySiteDTOs, studySiteAccrualDTOs, errorMsg);
        validateDocWrkStatus(studyProtocolDTO, errorMsg);
        validateDocuments(documentDTOs, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
    }

    /**
     * @param documentDTOs
     * @param errorMsg
     */
    private void validateDocuments(List<DocumentDTO> documentDTOs, StringBuffer errorMsg) {
        for (DocumentDTO docDto : documentDTOs) {
            if (PAUtil.isIiNotNull(docDto.getIdentifier()) && (!paServiceUtils.isIiExistInPA(docDto.getIdentifier()))) {
                errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exits.");
            }
        }
        for (Iterator<DocumentDTO> iter = documentDTOs.listIterator(); iter.hasNext();) {
            DocumentDTO docDto = iter.next();
            if (PAUtil.isEdNull(docDto.getText())) {
                iter.remove();
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @param errorMsg
     * @throws PAException
     */
    private void validateDocWrkStatus(StudyProtocolDTO studyProtocolDTO, StringBuffer errorMsg) throws PAException {
        DocumentWorkflowStatusDTO isoDocWrkStatus =
                docWrkFlowStatusService.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
        String dwfs = isoDocWrkStatus.getStatusCode().getCode();
        if (dwfs.equals(DocumentWorkflowStatusCode.SUBMITTED.getCode())
                || dwfs.equals(DocumentWorkflowStatusCode.REJECTED.getCode())) {
            errorMsg.append("Only Trials with processing status Accepted or Abstracted or  "
                    + " Abstraction Verified No Response or   Abstraction Verified No Response can be Updated.");
        }
    }

    /**
     * @param studySiteDTOs
     * @param studySiteAccrualDTOs
     * @param errorMsg
     * @throws PAException
     */
    private void validateStudySites(List<StudySiteDTO> studySiteDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs, StringBuffer errorMsg) throws PAException {
        Map<String, StudySiteDTO> studySiteMap = new HashMap<String, StudySiteDTO>();
        for (StudySiteDTO ssDto : studySiteDTOs) {
            if (PAUtil.isIiNull(ssDto.getIdentifier())) {
                errorMsg.append(" Study Site identifier cannot be null");
                continue;
            }
            StudySiteDTO studySiteDto = studySiteService.get(ssDto.getIdentifier());
            if (studySiteDto == null) {
                errorMsg.append(" Study site identifier not found for ").append(ssDto.getIdentifier().getExtension());
            }
            studySiteMap.put(ssDto.getIdentifier().getExtension(), studySiteDto);
        }
        for (StudySiteAccrualStatusDTO ssasDto : studySiteAccrualDTOs) {
            if (PAUtil.isIiNull(ssasDto.getStudySiteIi())) {
                errorMsg.append(" Study Site Accrual Status identifier cannot be null");
                continue;
            }
            if (studySiteMap.get(ssasDto.getStudySiteIi().getExtension()) == null) {
                errorMsg.append(" Study site identifier not found in Study Site Accural Status DTO ").append(
                        ssasDto.getStudySiteIi().getExtension());
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @param leadOrganizationDTO
     * @param leadOrganizationIdentifier
     * @param nctIdentifier
     * @param errorMsg
     * @throws PAException
     */
    private void notNullCheck(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO,
            St leadOrganizationIdentifier, St nctIdentifier, StringBuffer errorMsg) throws PAException {
        if (studyProtocolDTO == null) {
            errorMsg.append("Study Protocol DTO cannot be null , ");
        }
        if (leadOrganizationDTO == null) {
            errorMsg.append("Lead Organization DTO cannot be null , ");
        }
        if (PAUtil.isStNull(leadOrganizationIdentifier)) {
            errorMsg.append("Lead Organization identifier cannot be null , ");
        }
        if (studyProtocolDTO != null) {
            if (PAUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
                errorMsg.append("Study Protocol Identifier cannot be null ");
            }
            if (PAUtil.isStNull(studyProtocolDTO.getOfficialTitle())) {
                errorMsg.append("Official Title cannot be null ");
            }
            if (PAUtil.isStNull(nctIdentifier)) {
                if (PAUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode())) {
                    errorMsg.append("Purpose cannot be null ");
                }
                if (PAUtil.isCdNull(studyProtocolDTO.getPhaseCode())) {
                    errorMsg.append("Phase cannot be null ");
                }
            }
        } else {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
    }

    /**
     * @param studyProtocolDTO
     * @param errorMsg
     * @throws PAException
     */
    private void validateOwner(StudyProtocolDTO studyProtocolDTO, StringBuffer errorMsg) throws PAException {
        String loginName = "";
        if (!PAUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
            loginName = studyProtocolDTO.getUserLastCreated().getValue();
            CSMUserService userService = new CSMUserService();
            User user = userService.getCSMUser(loginName);
            if (user == null) {
                errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
            }
        } else {
            errorMsg.append("Submitter is required.");
        }
        if (StringUtils.isNotEmpty(loginName)
                && !userServiceLocal.hasTrialAccess(loginName,
                        Long.parseLong(studyProtocolDTO.getIdentifier().getExtension()))) {
            errorMsg.append("Update to Trial can be submitted by the Owner of the original Trial.\n");
        }
    }
}
