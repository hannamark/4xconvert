package gov.nih.nci.pa.service;

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
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
/**
 * Prop trial Management Bean for registering and updating the protocol.
 * @author Naveen Amiruddin
 * @since 05/24/2010
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(HibernateSessionInterceptor.class)
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class ProprietaryTrialManagementBeanLocal implements ProprietaryTrialManagementServiceLocal {

    private static final String VALIDATION_EXCEPTION = "Validation Exception ";
    private SessionContext ejbContext;
    private static PAServiceUtils paServiceUtils = new PAServiceUtils();

    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudySiteServiceLocal studySiteService = null;
    @EJB
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
    @EJB
    MailManagerServiceLocal mailManagerSerivceLocal = null;
    @EJB
    DocumentWorkflowStatusServiceLocal docWrkFlowStatusService = null;
    @EJB
    AbstractionCompletionServiceRemote abstractionCompletionService = null;
    @EJB
    StudyInboxServiceLocal studyInboxServiceLocal = null;
    @EJB
    DocumentServiceLocal documentService = null;

    @EJB
    StudyMilestoneServicelocal studyMilestoneService = null;

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
    @SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.NPathComplexity" ,
        "PMD.ExcessiveParameterList" , "PMD.ExcessiveMethodLength" })
    public void update(
            StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO leadOrganizationDTO ,
            OrganizationDTO summary4OrganizationDTO ,
            St leadOrganizationIdentifier ,
            St nctIdentifier,
            Cd summary4TypeCode ,
            List<DocumentDTO> documentDTOs ,
            List<StudySiteDTO> studySiteDTOs ,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs)
    throws PAException {
        if (studyProtocolDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol DTO is null");
        }
        if (PAUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol DTO identifier is null");
        }
        try {
            StudyProtocolDTO spDto = studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier());
            String userLastCreated = StConverter.convertToString(spDto.getUserLastCreated());

            validate(studyProtocolDTO , leadOrganizationDTO ,  leadOrganizationIdentifier ,
                    nctIdentifier, documentDTOs , studySiteDTOs , studySiteAccrualDTOs ,
                    userLastCreated);
            // the validation are done, proceed to update
            Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
            spDto.setOfficialTitle(studyProtocolDTO.getOfficialTitle());
            spDto.setPrimaryPurposeCode(studyProtocolDTO.getPrimaryPurposeCode());
            spDto.setPhaseCode(studyProtocolDTO.getPhaseCode());
            studyProtocolService.updateStudyProtocol(spDto);
            updateLeadOrganization(paServiceUtils.findOrCreateEntity(leadOrganizationDTO) ,
                    leadOrganizationIdentifier , studyProtocolIi);
            updateNctIdentifier(nctIdentifier, studyProtocolIi);
            StudyResourcingDTO srDto = null;
            if (!PAUtil.isCdNull(summary4TypeCode)) {
                srDto = new StudyResourcingDTO();
                srDto.setTypeCode(summary4TypeCode);
            }
            paServiceUtils.manageSummaryFour(studyProtocolIi, (summary4OrganizationDTO != null
                    ? paServiceUtils.findOrCreateEntity(summary4OrganizationDTO) : null) , srDto);
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
            paServiceUtils.createOrUpdate(documentDTOs , IiConverter.convertToDocumentIi(null) ,
                    studyProtocolDTO.getIdentifier());
            studyInboxServiceLocal.create(documentDTOs, studyProtocolIi);
            mailManagerSerivceLocal.sendUpdateNotificationMail(studyProtocolIi);
            StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(studyProtocolIi);
            List<StudyInboxDTO> inbox = studyInboxServiceLocal.getByStudyProtocol(studyProtocolIi);
            if (PAUtil.isListEmpty(inbox) && MilestoneCode.isAboveTrialSummaryReport(
                    MilestoneCode.getByCode(CdConverter.convertCdToString(smDto.getMilestoneCode())))) {
                paServiceUtils.createMilestone(studyProtocolIi, MilestoneCode.TRIAL_SUMMARY_SENT, null);
            }
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }

    private void updateLeadOrganization(OrganizationDTO leadOrg , St leadOrganizationIdentifier ,
            Ii studyProtocolIi) throws PAException {
        StudySiteDTO ssCriteriaDTO = new StudySiteDTO();
        ssCriteriaDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        ssCriteriaDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<StudySiteDTO> studySiteDtos = paServiceUtils.getStudySite(ssCriteriaDTO, true);
        StudySiteDTO studySiteDTO = PAUtil.getFirstObj(studySiteDtos);
        if (studySiteDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Lead organization not found for Study Protocol "
                    + studyProtocolIi.getExtension());
        }
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToIi(PaRegistry.getOrganizationCorrelationService().
            createResearchOrganizationCorrelations(leadOrg.getIdentifier().getExtension())));
        studySiteDTO.setLocalStudyProtocolIdentifier(leadOrganizationIdentifier);
        studySiteService.update(studySiteDTO);
    }

    private void updateNctIdentifier(St nctIdentifier , Ii studyProtocolIi) throws PAException {
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        nctIdentifierDTO.setLocalStudyProtocolIdentifier(nctIdentifier);
        nctIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
        nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                PAConstants.NCT_IDENTIFIER_TYPE);
        nctIdentifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
                getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
        paServiceUtils.manageStudyIdentifiers(nctIdentifierDTO);
    }
    @SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.NPathComplexity" , "PMD.ExcessiveParameterList"
        , "PMD.ExcessiveMethodLength" })
    private void validate(StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO leadOrganizationDTO ,
            St leadOrganizationIdentifier ,
            St nctIdentifier,
            List<DocumentDTO> documentDTOs ,
            List<StudySiteDTO> studySiteDTOs ,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs , String userCreated) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
        errorMsg.append(leadOrganizationDTO == null ? "Lead Organization DTO cannot be null , " : "");
        errorMsg.append(PAUtil.isStNull(leadOrganizationIdentifier)
                ? "Lead Organization identifier cannot be null , " : "");
        if (studyProtocolDTO != null) {
            errorMsg.append(PAUtil.isIiNull(studyProtocolDTO.getIdentifier())
                    ? "Study Protocol Identifier cannot be null " : "");
            errorMsg.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle())
                    ? "Official Title cannot be null " : "");
            if (PAUtil.isStNull(nctIdentifier)) {
                errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode())
                    ? "Purpose cannot be null " : "");
                errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null " : "");
            }
            errorMsg.append(PAUtil.isStNull(studyProtocolDTO.getUserLastCreated())
                    ? "User created by cannot be null " : "");
        } else {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
        if (PAUtil.isListNotEmpty(studySiteDTOs)) {
            for (StudySiteDTO studySiteDto : studySiteDTOs) {
                errorMsg.append(PAUtil.isIiNull(studySiteDto.getStudyProtocolIdentifier())
                        ? "Study Protocol Identifier  from Study Site cannot be null " : "");
                errorMsg.append(PAUtil.isIiNull(studySiteDto.getIdentifier())
                        ? "Study Site Identifier cannot be null " : "");
            }
        }
        if (PAUtil.isStNull(nctIdentifier)
                && PAUtil.isListEmpty(documentService.getByStudyProtocol(studyProtocolDTO.getIdentifier()))) {
                errorMsg.append("NCT identifier is required as there are no Documents");
        }
        if (userCreated != null && !userCreated.equalsIgnoreCase(
                StConverter.convertToString(studyProtocolDTO.getUserLastCreated()))) {
            errorMsg.append("Update to Trial can be submitted by the submitter of the Trial.");
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
        Map<String , StudySiteDTO> studySiteMap = new HashMap<String, StudySiteDTO>();
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
                errorMsg.append(" Study site identifier not found in Study Site Accural Status DTO ")
                        .append(ssasDto.getStudySiteIi().getExtension());
            }
        }
        DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
                studyProtocolDTO.getIdentifier());
        String dwfs = isoDocWrkStatus.getStatusCode().getCode();
        if (dwfs.equals(DocumentWorkflowStatusCode.SUBMITTED.getCode())
                || dwfs.equals(DocumentWorkflowStatusCode.REJECTED.getCode())) {
            errorMsg.append("Only Trials with processing status Accepted or Abstracted or  "
                    + " Abstraction Verified No Response or   Abstraction Verified No Response can be Updated.");
        }
        for (DocumentDTO docDto : documentDTOs) {
            if (PAUtil.isIiNotNull(docDto.getIdentifier()) && (!paServiceUtils.isIiExistInPA(docDto.getIdentifier()))) {
                errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exits.");
            }
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
    }
}
