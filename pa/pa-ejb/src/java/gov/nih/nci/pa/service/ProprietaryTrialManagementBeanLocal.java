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
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
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
    @EJB
    RegistryUserServiceLocal userServiceLocal = null;
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
            validate(studyProtocolDTO , leadOrganizationDTO ,  leadOrganizationIdentifier ,
                    nctIdentifier, documentDTOs , studySiteDTOs , studySiteAccrualDTOs);
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
    @SuppressWarnings({"PMD.ExcessiveParameterList" })
    private void validate(StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO leadOrganizationDTO ,
            St leadOrganizationIdentifier ,
            St nctIdentifier,
            List<DocumentDTO> documentDTOs ,
            List<StudySiteDTO> studySiteDTOs ,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        notNullCheck(studyProtocolDTO, leadOrganizationDTO,
                leadOrganizationIdentifier, nctIdentifier, errorMsg);
        validateOwner(studyProtocolDTO, errorMsg);
        if (PAUtil.isStNull(nctIdentifier) && PAUtil.isListEmpty(
                documentService.getByStudyProtocol(studyProtocolDTO.getIdentifier()))) {
                errorMsg.append("NCT identifier is required as there are no Documents");
        }
        if (PAUtil.isListNotEmpty(studySiteDTOs)) {
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
    private void validateDocuments(List<DocumentDTO> documentDTOs,
            StringBuffer errorMsg) {
        for (DocumentDTO docDto : documentDTOs) {
            if (PAUtil.isIiNotNull(docDto.getIdentifier()) && (!paServiceUtils.isIiExistInPA(docDto.getIdentifier()))) {
                errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exits.");
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @param errorMsg
     * @throws PAException
     */
    private void validateDocWrkStatus(StudyProtocolDTO studyProtocolDTO,
            StringBuffer errorMsg) throws PAException {
        DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
                studyProtocolDTO.getIdentifier());
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
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs,
            StringBuffer errorMsg) throws PAException {
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
    }

    /**
     * @param studyProtocolDTO
     * @param leadOrganizationDTO
     * @param leadOrganizationIdentifier
     * @param nctIdentifier
     * @param errorMsg
     * @throws PAException
     */
    private void notNullCheck(StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO leadOrganizationDTO, St leadOrganizationIdentifier,
            St nctIdentifier, StringBuffer errorMsg) throws PAException {
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
    private void validateOwner(StudyProtocolDTO studyProtocolDTO,
            StringBuffer errorMsg) throws PAException {
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
        if (StringUtils.isNotEmpty(loginName) && !userServiceLocal.hasTrialAccess(loginName,
                Long.parseLong(studyProtocolDTO.getIdentifier().getExtension()))) {
           errorMsg.append("Update to Trial can be submitted by the Owner of the original Trial.\n");
        }
    }
}
