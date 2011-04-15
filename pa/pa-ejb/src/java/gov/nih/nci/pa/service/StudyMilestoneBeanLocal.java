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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
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
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getMilestoneDate());
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));

        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        Timestamp lastDate = null;
        if (!existingDtoList.isEmpty()) {
            StudyMilestoneDTO last = existingDtoList.get(existingDtoList.size() - 1);
            lastDate = TsConverter.convertToTimestamp(last.getMilestoneDate());
        }

        // required data rules
        if (PAUtil.isCdNull(dto.getMilestoneCode())) {
            throw new PAException("Milestone code is required.");
        }
        if (newDate == null) {
            throw new PAException("Milestone date is required.");
        }
        //if the milestone is late rejection date then comment is required.
        if (MilestoneCode.LATE_REJECTION_DATE.getCode().equalsIgnoreCase(dto.getMilestoneCode().getCode())) {
            if (PAUtil.isStNull(dto.getCommentText())) {
                throw new PAException("Milestone Comment is required.");
            }
            StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
            if (sp.getSubmissionNumber().getValue().intValue() > 1) {
                throw new PAException("Late Rejection Date is applicable to Original Submission.");
            }
        }

        // onhold rules
        if (!newCode.isAllowedIfOnhold()
                && BlConverter.convertToBool(studyOnholdService.isOnhold(dto.getStudyProtocolIdentifier()))) {
            throw new PAException("The milestone '" + newCode.getCode()
                    + "' cannot be recorded if there is an active on-hold record.");
        }
        if (!newCode.isAllowedIfInBox()) {
            List<StudyInboxDTO> listInboxDTO = studyInboxService.getByStudyProtocol(dto.getStudyProtocolIdentifier());
            if (CollectionUtils.isNotEmpty(listInboxDTO)) {
                for (StudyInboxDTO inboxDto : listInboxDTO) {
                    String strCloseDate = IvlConverter.convertTs().convertHighToString(inboxDto.getInboxDateRange());
                    if (StringUtils.isEmpty(strCloseDate)) {
                        throw new PAException("The milestone '" + newCode.getCode()
                                + "' cannot be recorded if there is an active In box record.");
                    }
                }
            }
        }
        // date rules
        if (newDate.after(new Timestamp(new Date().getTime()))) {
            throw new PAException("Milestone dates may not be in the future.");
        }
        if (lastDate != null && lastDate.after(newDate)) {
            throw new PAException("Milestone's must not predate existing milestones.  The prior milestone date is "
                    + PAUtil.normalizeDateStringWithTime(lastDate.toString()) + ".");
        }

        // transition rules
        if (newCode.getPrerequisite() != null) {
            boolean prerequisiteFound = false;
            for (StudyMilestoneDTO edto : existingDtoList) {
                MilestoneCode cd = MilestoneCode.getByCode(CdConverter.convertCdToString(edto.getMilestoneCode()));
                if (newCode.getPrerequisite().getCode().equals(cd.getCode())) {
                    prerequisiteFound = true;
                    continue;
                }
            }
            if (!prerequisiteFound) {
                throw new PAException("'" + newCode.getPrerequisite().getCode() + "' is a prerequisite to '"
                        + newCode.getCode() + "'.");
            }
        }

        // uniqueness rules
        if (newCode.isUnique()) {
            for (StudyMilestoneDTO edto : existingDtoList) {
                if (newCode.getCode().equals(edto.getMilestoneCode().getCode())) {
                    throw new PAException("The milestone '" + newCode.getCode() + "' must be unique.  It was "
                            + "previously recorded on " + PAUtil.normalizeDateString(
                                    TsConverter.convertToTimestamp(edto.getMilestoneDate()).toString()) + ".");
                }
            }
        }

        newValidations(newCode, existingDtoList);

        // document work flow status rules
        DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        if ((!newCode.equals(MilestoneCode.SUBMISSION_RECEIVED)
                && !newCode.equals(MilestoneCode.SUBMISSION_ACCEPTED)
                && !newCode.equals(MilestoneCode.SUBMISSION_REJECTED))
                && !newCode.isValidDwfStatus(dwStatus)) {
            StringBuffer errMsg = new StringBuffer("The processing status must be ");
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
            errMsg.append(" when entering the milestone '" + newCode.getCode() + "'.  The current processing "
                    + "status is " + ((dwStatus == null) ? "null." : "'" + dwStatus.getCode() + "'."));
            throw new PAException(errMsg.toString());
        }


        // validate abstraction
        if (validateAbstractions && newCode.isValidationTrigger()) {
            if (abstractionCompletionService == null) {
                throw new PAException("Error injecting reference to AbstractionCompletionService.");
            }
            List<AbstractionCompletionDTO> errorList =
                abstractionCompletionService.validateAbstractionCompletion(dto.getStudyProtocolIdentifier());
            if (!errorList.isEmpty() && hasAnyAbstractionErrors(errorList)) {
                throw new PAException("The milestone '" + newCode.getCode() + "' can only be recorded if the "
                        + "abstraction is valid.  There is a problem with the current abstraction.  Select "
                        + "'Abstraction Validation' under 'Completion' menu to view details.");
            }

        }
        return dto;
    }

    private void newValidations(MilestoneCode newCode, List<StudyMilestoneDTO> existingDtoList) throws PAException {
        //check if the administrative and scientific processing is completed.
        if (newCode.equals(MilestoneCode.READY_FOR_QC)) {
            List<String> mileStones = getExistingMilestones(existingDtoList);
            if (!(mileStones.contains(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())
                    && mileStones.contains(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode()))) {
                throw new PAException("Ready for QC can only be recorded after "
                        + "Administrative and Scientific processing are completed");
            }
            if (!hasBeenPaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Start Date must be followed by "
                        + " Scientific Processing Completion Date");
            }
            if (!hasBeenPaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Administartive Processing Start Date must be followed by "
                        + " Administrative Processing Completion Date");
            }
        }

        //if Admin processing already started ::
        //check if the administrative processing is completed before the start of Sceintific processing
        if (newCode.equals(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList);
            if (mileStones.contains(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode())
                    && !mileStones.contains(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Start Date cannot be recorded"
                        + " if Administrative Processing started but was not completed");
            }
            if (!hasBeenPaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Start Date must be followed by "
                        + " Scientific Processing Completion Date");
            }
            if (!hasBeenPaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Administartive Processing Start Date must be followed by "
                        + " Administrative Processing Completion Date");
            }

        }
        //if Scientific processing already started ::
        //check if the sceintific processing is completed before the start of admin processing
        if (newCode.equals(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList);
            if (mileStones.contains(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode())
                    && !mileStones.contains(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Administrative Processing Start Date cannot be recorded"
                        + " if Scientific Processing started but was not completed");
            }

            if (!hasBeenPaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Administartive Processing Start Date must be followed by "
                        + " Administrative Processing Completion Date");
            }
            if (!hasBeenPaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Start Date must be followed by "
                        + " Scientific Processing Completion Date");
            }
        }

        if (newCode.equals(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList);
            if (!canBePaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Administartive Processing Completion Date must be preceded by "
                        + " Administrative Processing Start Date");
            }
        }

        if (newCode.equals(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList);
            if (!canBePaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Completion Date must be preceded by "
                        + " Scientific Processing Start Date");
            }
        }

    }

    private boolean hasAnyAbstractionErrors(List<AbstractionCompletionDTO> errorList) {
        boolean errorExist = false;
        for (AbstractionCompletionDTO  absDto : errorList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                errorExist = true;
                break;
            }
        }
        return errorExist;
    }

    private List<String> getExistingMilestones(List<StudyMilestoneDTO> existingDTOs)
    {
        List<String> existingMilestones = null;
        if (existingDTOs != null) {
            existingMilestones = new ArrayList<String>();
            for (StudyMilestoneDTO edto : existingDTOs) {
                existingMilestones.add(edto.getMilestoneCode().getCode());
            }
        }
        return existingMilestones;
    }

    private boolean canBePaired(List<String> mileStones, String mileStone1, String mileStone2) {
        int mileStone1Count = 0;
        int mileStone2Count = 0;
        for (String mileStone : mileStones) {
            if (mileStone.equals(mileStone1)) {
                mileStone1Count++;
            }
            if (mileStone.equals(mileStone2)) {
                mileStone2Count++;
            }
        }
        if (mileStone1Count == mileStone2Count) {
            return false;
        } else if (mileStone1Count == 1 && mileStone2Count == 0) {
            return true;
        } else if (mileStone1Count % 2 == 0 && mileStone2Count % 2 == 0) {
            return false;
        } else if ((mileStone1Count % 2 == 0 && mileStone2Count % 2 == 1) && (mileStone2Count > mileStone1Count)) {
            return false;
        } else if (mileStone1Count % 2 != 0) {
            return true;
        }
        return true;
    }

    private boolean hasBeenPaired(List<String> mileStones, String mileStone1, String mileStone2) {
        int mileStone1Count = 0;
        int mileStone2Count = 0;
        for (String mileStone : mileStones) {
            if (mileStone.equals(mileStone1)) {
                mileStone1Count++;
            }
            if (mileStone.equals(mileStone2)) {
                mileStone2Count++;
            }
        }
        if (mileStone1Count == mileStone2Count) {
            return true;
        } else if (mileStone1Count == 1 && mileStone2Count == 0) {
            return false;
        } else if (mileStone1Count % 2 == 0 && mileStone2Count % 2 == 0) {
            return true;
        } else if (mileStone1Count % 2 != 0) {
            return false;
        }
        return false;
    }

    private void createDocumentWorkflowStatuses(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());

        if (newCode.equals(MilestoneCode.SUBMISSION_RECEIVED) && sp.getSubmissionNumber().getValue().intValue() == 1) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.SUBMITTED , dto);
        }
        if (newCode.equals(MilestoneCode.SUBMISSION_RECEIVED) && sp.getSubmissionNumber().getValue().intValue() > 1) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED , dto);
        }
        if (newCode.equals(MilestoneCode.SUBMISSION_ACCEPTED)
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ACCEPTED)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ACCEPTED , dto);
        }
        if (newCode.equals(MilestoneCode.SUBMISSION_REJECTED)
                && canTransition(dwStatus, DocumentWorkflowStatusCode.REJECTED)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , dto);
        }
        if (newCode.equals(MilestoneCode.QC_COMPLETE) && dwStatus != null
                && DocumentWorkflowStatusCode.ACCEPTED.equals(dwStatus)
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTED, dto);
        }

        if (newCode.equals(MilestoneCode.INITIAL_ABSTRACTION_VERIFY)
                && (dwStatus != null)
                && (DocumentWorkflowStatusCode.ABSTRACTED.equals(dwStatus)
                        || DocumentWorkflowStatusCode.VERIFICATION_PENDING.equals(dwStatus))) {

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

        if (newCode.equals(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION)
                && (dwStatus != null)
                && DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.equals(dwStatus)
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)
                && milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE, dto);
        }


        if (newCode.equals(MilestoneCode.TRIAL_SUMMARY_SENT)
                && (dwStatus != null)
                && DocumentWorkflowStatusCode.ABSTRACTED.equals(dwStatus)
                && canTransition(dwStatus, DocumentWorkflowStatusCode.VERIFICATION_PENDING)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.VERIFICATION_PENDING, dto);
        }
        if (newCode.equals(MilestoneCode.LATE_REJECTION_DATE)
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
        if (newCode.equals(MilestoneCode.QC_COMPLETE)) {
            DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
            if ((dwStatus != null) && DocumentWorkflowStatusCode.ACCEPTED.equals(dwStatus)) {
                updateRecordVerificationDate(dto);
            }
        }
        if (newCode.equals(MilestoneCode.INITIAL_ABSTRACTION_VERIFY)
                || newCode.equals(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION)) {
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
            throw new PAException("Illegal DocumentWorkflow status transition from '" + dwStatus.getCode()
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
