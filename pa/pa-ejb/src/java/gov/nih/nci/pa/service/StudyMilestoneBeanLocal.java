/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyMilestoneConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyMilestoneSortCriterion;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyMilestoneBeanLocal
    extends AbstractCurrentStudyIsoService<StudyMilestoneDTO, StudyMilestone, StudyMilestoneConverter>
    implements StudyMilestoneServicelocal {

    @EJB
    private StudyOnholdServiceLocal studyOnholdService;
    @EJB
    private AbstractionCompletionServiceRemote abstractionCompletionService;
    @EJB
    private MailManagerServiceLocal mailManagerService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudyInboxServiceLocal studyInboxService;
    /** For testing purposes only. Set to false to bypass abstraction validations. */
    private boolean validateAbstractions = true;

    /**
     * @param dto dto
     * @return created dto
     * @throws PAException exception
     */
    @Override
    public StudyMilestoneDTO create(StudyMilestoneDTO dto) throws PAException {
        //PO-2961:The order of the below calls is significant.
        //The emails must be sent after creation has happened. - aevansel, 12/21/2010.
        StudyMilestoneDTO workDto = businessRules(dto);
        StudyMilestoneDTO resultDto = super.create(workDto);
        createDocumentWorkflowStatuses(resultDto);
        updateRecordVerificationDates(resultDto);
        createReadyForTSRMilestone(resultDto);
        // Send TSR e-mail for the appropriate milestone
        sendTSREmail(workDto);
        sendLateRejectionEmail(workDto);
        return resultDto;
    }

    /**
     * @param ii index of milestone
     * @throws PAException exception
     */
    @Override
    public void delete(Ii ii) throws PAException {
        throw new PAException("The delete() method in the StudyMilestoneService has been disabled.");
    }

    /**
     * @param dto dto
     * @return updated dto
     * @throws PAException exception
     */
    @Override
    public StudyMilestoneDTO update(StudyMilestoneDTO dto) throws PAException {
        throw new PAException("The update() method in the StudyMilestoneService has been disabled.");
    }

    private DocumentWorkflowStatusCode getCurrentDocumentWorkflowStatus(Ii studyProtocolIi) throws PAException {
        DocumentWorkflowStatusDTO dw =
            PaRegistry.getDocumentWorkflowStatusService().getCurrentByStudyProtocol(studyProtocolIi);
        return  (dw == null) ? null
                : DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dw.getStatusCode()));
    }

    private StudyMilestoneDTO businessRules(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        checkRequiredDataRules(dto);
        checkDateRules(dto, existingDtoList);
        checkLateRejectionRules(dto);
        checkOnHoldRules(dto, newCode);
        checkInboxRules(dto, newCode);
        checkUniquenessRules(newCode, existingDtoList);
        checkMilestoneSpecificRules(newCode, existingDtoList);
        checkDocumentWorkflowStatusRules(dto, newCode);
        checkAbstractionsRules(dto, newCode);        
        return dto;
    }

    private void checkRequiredDataRules(StudyMilestoneDTO dto) throws PAException {
        if (PAUtil.isCdNull(dto.getMilestoneCode())) {
            throw new PAException("Milestone code is required.");
        }
    }

    private void checkDateRules(StudyMilestoneDTO dto, List<StudyMilestoneDTO> existingDtoList) throws PAException {
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getMilestoneDate());
        if (newDate == null) {
            throw new PAException("Milestone date is required.");
        }
        if (newDate.after(new Timestamp(new Date().getTime()))) {
            throw new PAException("Milestone dates may not be in the future.");
        }
        if (!existingDtoList.isEmpty()) {
            StudyMilestoneDTO last = existingDtoList.get(existingDtoList.size() - 1);
            Timestamp lastDate = TsConverter.convertToTimestamp(last.getMilestoneDate());
            if (lastDate.after(newDate)) {
                String msg = "A milestone cannot predate existing milestones. The prior milestone date is {0}.";
                String lastMileStoneDate = PAUtil.normalizeDateStringWithTime(lastDate.toString());
                throw new PAException(MessageFormat.format(msg, lastMileStoneDate));
            }
        }
    }

    private void checkLateRejectionRules(StudyMilestoneDTO dto) throws PAException {
        if (MilestoneCode.LATE_REJECTION_DATE.getCode().equalsIgnoreCase(dto.getMilestoneCode().getCode())) {
            if (PAUtil.isStNull(dto.getCommentText())) {
                throw new PAException("Milestone Comment is required.");
            }
            StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
            if (sp.getSubmissionNumber().getValue().intValue() > 1) {
                throw new PAException("Late Rejection Date is applicable to Original Submission.");
            }
        }
    }

    private void checkOnHoldRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        if (!newCode.isAllowedIfOnhold()
                && BlConverter.convertToBool(studyOnholdService.isOnhold(dto.getStudyProtocolIdentifier()))) {
            String msg = "The milestone \"{0}\" cannot be recorded if there is an active on-hold record.";
            throw new PAException(MessageFormat.format(msg, newCode.getCode()));
        }
    }

    private void checkInboxRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        if (!newCode.isAllowedIfInBox()) {
            List<StudyInboxDTO> listInboxDTO = studyInboxService.getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyInboxDTO inboxDto : listInboxDTO) {
                String strCloseDate = IvlConverter.convertTs().convertHighToString(inboxDto.getInboxDateRange());
                if (StringUtils.isEmpty(strCloseDate)) {
                    String msg = "The milestone \"{0}\" cannot be recorded if there is an active In box record.";
                    throw new PAException(MessageFormat.format(msg, newCode.getCode()));
                }
            }
        }
    }

    private void checkUniquenessRules(MilestoneCode newCode, List<StudyMilestoneDTO> existingDtoList)
            throws PAException {
        if (newCode.isUnique()) {
            for (StudyMilestoneDTO edto : existingDtoList) {
                if (newCode.getCode().equals(edto.getMilestoneCode().getCode())) {
                    String msg = "The milestone \"{0}\" must be unique.  It was previously recorded on {1}.";
                    String lastMileStoneDate = PAUtil.normalizeDateString(TsConverter
                        .convertToTimestamp(edto.getMilestoneDate()).toString());
                    throw new PAException(MessageFormat.format(msg, newCode.getCode(), lastMileStoneDate));
                }
            }
        }
    }

    private void checkDocumentWorkflowStatusRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        if (newCode != MilestoneCode.SUBMISSION_RECEIVED && newCode != MilestoneCode.SUBMISSION_ACCEPTED
                && newCode != MilestoneCode.SUBMISSION_REJECTED && !newCode.isValidDwfStatus(dwStatus)) {
            StringBuilder errMsg = new StringBuilder("The processing status must be ");
            int iSize = newCode.getValidDwfStatuses().size();
            for (int x = 0; x < iSize; x++) {
                errMsg.append("'" + newCode.getValidDwfStatuses().get(x).getCode() + "'");
                if ((iSize == 2) && (x == 0)) {
                    errMsg.append(" or ");
                }
                if ((iSize > 2) && (x < iSize - 2)) {
                    errMsg.append(", ");
                }
                if ((iSize > 2) && (x == iSize - 2)) {
                    errMsg.append(", or ");
                }
            }
            errMsg.append(" when entering the milestone '");
            errMsg.append(newCode.getCode());
            errMsg.append("'.  The current processing status is ");
            errMsg.append(((dwStatus == null) ? "null." : "'" + dwStatus.getCode() + "'."));
            throw new PAException(errMsg.toString());
        }
    }

    private void checkAbstractionsRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        if (validateAbstractions && newCode.isValidationTrigger()) {
            if (abstractionCompletionService == null) {
                throw new PAException("Error injecting reference to AbstractionCompletionService.");
            }
            List<AbstractionCompletionDTO> errorList = abstractionCompletionService.validateAbstractionCompletion(dto
                .getStudyProtocolIdentifier());
            if (!errorList.isEmpty() && hasAnyAbstractionErrors(errorList)) {
                String msg = "The milestone \"{0}\" can only be recorded if the abstraction is valid.  There is a"
                        + " problem with the current abstraction.  Select 'Abstraction Validation' under 'Completion'"
                        + " menu to view details.";
                throw new PAException(MessageFormat.format(msg, newCode.getCode()));
            }
        }
    }

    private boolean hasAnyAbstractionErrors(List<AbstractionCompletionDTO> errorList) {
        for (AbstractionCompletionDTO absDto : errorList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                return true;
            }
        }
        return false;
    }
    
    private void checkReadyForTSRMilestone(List<MilestoneCode> milestones) throws PAException {  
            if (!canCreateReadyForTSRMilestone(milestones)) {
                String msg = "\"{0}\" can not be created at this stage.";
                throw new PAException(MessageFormat.format(msg, MilestoneCode.READY_FOR_TSR.getCode()));            
        }
    }

    private void checkMilestoneSpecificRules(MilestoneCode newCode, List<StudyMilestoneDTO> existingDtoList)
            throws PAException {
        List<MilestoneCode> milestones = getExistingMilestones(existingDtoList);
        switch (newCode) {
        case ADMINISTRATIVE_PROCESSING_START_DATE:
        case ADMINISTRATIVE_PROCESSING_COMPLETED_DATE:
        case ADMINISTRATIVE_READY_FOR_QC:
        case ADMINISTRATIVE_QC_START:
        case ADMINISTRATIVE_QC_COMPLETE:
        case SCIENTIFIC_PROCESSING_START_DATE:
        case SCIENTIFIC_PROCESSING_COMPLETED_DATE:
        case SCIENTIFIC_READY_FOR_QC:
        case SCIENTIFIC_QC_START:
        case SCIENTIFIC_QC_COMPLETE:
            checkProcessAndQC(milestones, newCode);
            break;
        case TRIAL_SUMMARY_FEEDBACK:
            checkPrerequisite(milestones, newCode, Arrays.asList(MilestoneCode.SUBMISSION_ACCEPTED,
                                                                 MilestoneCode.TRIAL_SUMMARY_FEEDBACK),
                                                                 MilestoneCode.TRIAL_SUMMARY_SENT);
            break;
        case LATE_REJECTION_DATE:
            checkPrerequisite(milestones, newCode, new ArrayList<MilestoneCode>(), MilestoneCode.SUBMISSION_ACCEPTED);
            break;
            
        case READY_FOR_TSR:
            checkReadyForTSRMilestone(milestones);
            break;
            
        default:
            break;
        }
    }

    /**
     * Check the milestones in the processing and QC branches.
     * @param milestones The list of all existing milestones
     * @param milestone The new milestone to check. It must be one of the administrative or scientific processing or QC
     *        milestones.
     * @throws PAException if the given milestone is not acceptable in the current state.
     */
    private void checkProcessAndQC(List<MilestoneCode> milestones, MilestoneCode milestone) throws PAException {
        List<MilestoneCode> mainSequence = null;
        List<MilestoneCode> altSequence = null;
        if (MilestoneCode.ADMIN_SEQ.contains(milestone)) {
            mainSequence = MilestoneCode.ADMIN_SEQ;
            altSequence = MilestoneCode.SCIENTIFIC_SEQ;
        } else {
            mainSequence = MilestoneCode.SCIENTIFIC_SEQ;
            altSequence = MilestoneCode.ADMIN_SEQ;
        }
        int milestoneIndex = mainSequence.indexOf(milestone);
        List<MilestoneCode> predecessors = new ArrayList<MilestoneCode>();
        if (milestoneIndex > 0) {
            predecessors.add(mainSequence.get(milestoneIndex - 1));
        } else {
            predecessors.add(MilestoneCode.SUBMISSION_ACCEPTED);
            predecessors.add(MilestoneCode.TRIAL_SUMMARY_FEEDBACK);
        }
        for (int i = milestones.size() - 1; i >= 0; i--) {
            MilestoneCode current = milestones.get(i);
            if (predecessors.contains(current)) {
                return;
            }
            if (altSequence.contains(current)) {
                continue;
            }
            int currentIndex = mainSequence.indexOf(current);
            if (currentIndex >= milestoneIndex) {
                String msg = "\"{0}\" already reached.";
                throw new PAException(MessageFormat.format(msg, milestone.getCode()));
            }
            String msg = "\"{0}\" can not be reached at this stage.";
            throw new PAException(MessageFormat.format(msg, milestone.getCode()));
        }
        String msg = "\"{0}\" can not be reached at this stage.";
        throw new PAException(MessageFormat.format(msg, milestone.getCode()));
    }

    private void checkPrerequisite(List<MilestoneCode> milestones, MilestoneCode milestone,
            List<MilestoneCode> stopSearchMilestones, MilestoneCode preRequisite) throws PAException {
        for (int i = milestones.size() - 1; i >= 0; i--) {
            MilestoneCode current = milestones.get(i);
            if (current.equals(preRequisite)) {
                return;
            }
            if (stopSearchMilestones.contains(current)) {
                break;
            }
        }
        String msg = "\"{0}\" is a prerequisite to \"{1}\".";
        throw new PAException(MessageFormat.format(msg, preRequisite.getCode(), milestone.getCode()));
    }

    private List<MilestoneCode> getExistingMilestones(List<StudyMilestoneDTO> existingDTOs) {
        List<MilestoneCode> existingMilestones = new ArrayList<MilestoneCode>();
        if (existingDTOs != null) {
            for (StudyMilestoneDTO edto : existingDTOs) {
                existingMilestones.add(MilestoneCode.getByCode(edto.getMilestoneCode().getCode()));
            }
        }
        return existingMilestones;
    }

    private void createDocumentWorkflowStatuses(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());

        if (newCode == MilestoneCode.SUBMISSION_RECEIVED && sp.getSubmissionNumber().getValue().intValue() == 1) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.SUBMITTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_RECEIVED && sp.getSubmissionNumber().getValue().intValue() > 1) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_ACCEPTED
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ACCEPTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ACCEPTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_REJECTED
                && canTransition(dwStatus, DocumentWorkflowStatusCode.REJECTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , dto);
        }
        if (newCode == MilestoneCode.READY_FOR_TSR && dwStatus != null
                && DocumentWorkflowStatusCode.ACCEPTED == dwStatus
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTED, dto);
        }

        if (newCode == MilestoneCode.INITIAL_ABSTRACTION_VERIFY
                && (dwStatus != null)
                && (DocumentWorkflowStatusCode.ABSTRACTED == dwStatus
                        || DocumentWorkflowStatusCode.VERIFICATION_PENDING == dwStatus)) {

            if (milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto)) {
                if (canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)) {
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE , dto);
                }
            } else {
                if (canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE)) {
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE , dto);
                }
            }
        }

        if (newCode == MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION
                && (dwStatus != null)
                && DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE == dwStatus
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)
                && milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE, dto);
        }


        if (newCode == MilestoneCode.TRIAL_SUMMARY_SENT
                && (dwStatus != null)
                && DocumentWorkflowStatusCode.ABSTRACTED == dwStatus
                && canTransition(dwStatus, DocumentWorkflowStatusCode.VERIFICATION_PENDING)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.VERIFICATION_PENDING, dto);
        }
        if (newCode == MilestoneCode.LATE_REJECTION_DATE
                && canTransition(dwStatus, DocumentWorkflowStatusCode.REJECTED)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , dto);
        }
    }

    private void createDocumentWorkflowStatus(
            DocumentWorkflowStatusCode dwf, StudyMilestoneDTO dto) throws PAException {
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(dwf));
        dwfDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                TsConverter.convertToTimestamp(dto.getMilestoneDate()), null));
        dwfDto.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
        if (dto.getCommentText() != null) {
            dwfDto.setCommentText(dto.getCommentText());
        }
        PaRegistry.getDocumentWorkflowStatusService().create(dwfDto);
    }

    private void updateRecordVerificationDates(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        if (newCode == MilestoneCode.READY_FOR_TSR) {
            DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
            if ((dwStatus != null) && DocumentWorkflowStatusCode.ACCEPTED == dwStatus) {
                updateRecordVerificationDate(dto);
            }
        }
        if (newCode == MilestoneCode.INITIAL_ABSTRACTION_VERIFY
                || newCode == MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION) {
            updateRecordVerificationDate(dto);
        }
    }

    private void updateRecordVerificationDate(StudyMilestoneDTO dto) throws PAException {
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
        sp.setRecordVerificationDate(dto.getMilestoneDate());
        studyProtocolService.updateStudyProtocol(sp);
    }

    boolean milestoneExists(MilestoneCode milestoneCode, StudyMilestoneDTO dto) throws PAException {
        List<StudyMilestoneDTO> smList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudyMilestoneDTO sm : smList) {
            MilestoneCode tempCode = MilestoneCode.getByCode(CdConverter.convertCdToString(sm.getMilestoneCode()));
            if (tempCode.equals(milestoneCode)) {
                return true;
            }
        }
        return false;
    }

    private void createReadyForTSRMilestone(StudyMilestoneDTO dto) throws PAException {
        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        List<MilestoneCode> mileStones = getExistingMilestones(existingDtoList);
        if (canCreateReadyForTSRMilestone(mileStones)) {
            StudyMilestoneDTO readyForTSR = new StudyMilestoneDTO();
            readyForTSR.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.READY_FOR_TSR));
            readyForTSR.setMilestoneDate(dto.getMilestoneDate());
            readyForTSR.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
            create(readyForTSR);            
        }
    }
    
    private boolean canCreateReadyForTSRMilestone(List<MilestoneCode> mileStones) {
        boolean admin = false;
        boolean scientific = false;
        for (int i = mileStones.size() - 1; i >= 0; i--) {
            switch (mileStones.get(i)) {
            case ADMINISTRATIVE_QC_COMPLETE:
                admin = true;
                break;
            case SCIENTIFIC_QC_COMPLETE:
                scientific = true;
                break;
            case ADMINISTRATIVE_PROCESSING_START_DATE:
            case ADMINISTRATIVE_PROCESSING_COMPLETED_DATE:
            case ADMINISTRATIVE_READY_FOR_QC:
            case ADMINISTRATIVE_QC_START:
            case SCIENTIFIC_PROCESSING_START_DATE:
            case SCIENTIFIC_PROCESSING_COMPLETED_DATE:
            case SCIENTIFIC_READY_FOR_QC:
            case SCIENTIFIC_QC_START:
                break;
            default:
                return false;
            }
            if (admin && scientific) {
                return true;
            }
        }
        return false;
    }
    

    private void sendTSREmail(StudyMilestoneDTO workDto) throws PAException {
        MilestoneCode milestoneCode = MilestoneCode.getByCode(
                CdConverter.convertCdToString(workDto.getMilestoneCode()));
        if ((MilestoneCode.TRIAL_SUMMARY_SENT.equals(milestoneCode))) {
            try {
                mailManagerService.sendTSREmail(workDto.getStudyProtocolIdentifier());
            } catch (PAException e) {
                throw new PAException(workDto.getMilestoneCode().getCode() + "' could not "
                        + "be recorded as sending the TSR report to the submitter  failed.", e);
            }
        }
    }

    private boolean canTransition(DocumentWorkflowStatusCode dwStatus, DocumentWorkflowStatusCode newCode)
    throws PAException {
        boolean canTransition = false;
        if (dwStatus.canTransitionTo(newCode)) {
            canTransition = true;
        } else {
            throw new PAException("Invalid DocumentWorkflow status transition from '" + dwStatus.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        return canTransition;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyMilestoneDTO> search(StudyMilestoneDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyMilestoneDTO should not be null.");
        }
        StudyMilestone criteria = Converters.get(StudyMilestoneConverter.class).convertFromDtoToDomain(dto);

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyMilestone> params = new PageSortParams<StudyMilestone>(maxLimit,
                pagingParams.getOffset(), StudyMilestoneSortCriterion.STUDY_MILESTONE_ID, false);
        List<StudyMilestone> studyMilestoneList =
            search(new AnnotatedBeanSearchCriteria<StudyMilestone>(criteria), params);

        if (studyMilestoneList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTOs(studyMilestoneList);
    }

    private void sendLateRejectionEmail(StudyMilestoneDTO workDto) throws PAException {
        MilestoneCode milestoneCode = MilestoneCode.getByCode(
                CdConverter.convertCdToString(workDto.getMilestoneCode()));
        if ((MilestoneCode.LATE_REJECTION_DATE.equals(milestoneCode))) {
            try {
                mailManagerService.sendRejectionEmail(workDto.getStudyProtocolIdentifier());
            } catch (PAException e) {
                throw new PAException(workDto.getMilestoneCode().getCode() + "' could not "
                        + "be recorded as sending the rejection email to the submitter  failed.", e);
            }
        }
    }

    /**
     * @param studyOnholdService the studyOnholdService to set
     */
    public void setStudyOnholdService(StudyOnholdServiceLocal studyOnholdService) {
        this.studyOnholdService = studyOnholdService;
    }

    /**
     * @param abstractionCompletionService the abstractionCompletionService to set
     */
    public void setAbstractionCompletionService(AbstractionCompletionServiceRemote abstractionCompletionService) {
        this.abstractionCompletionService = abstractionCompletionService;
    }

    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param studyInboxService the studyInboxService to set
     */
    public void setStudyInboxService(StudyInboxServiceLocal studyInboxService) {
        this.studyInboxService = studyInboxService;
    }

    /**
     * @param validateAbstractions the validateAbstractions to set
     */
    public void setValidateAbstractions(boolean validateAbstractions) {
        this.validateAbstractions = validateAbstractions;
    }
}
