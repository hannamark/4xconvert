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
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.interceptor.ProprietaryTrialInterceptor;
import gov.nih.nci.pa.iso.convert.AbstractStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.joda.time.DateMidnight;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({PaHibernateSessionInterceptor.class, ProprietaryTrialInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyOverallStatusBeanLocal extends
        AbstractCurrentStudyIsoService<StudyOverallStatusDTO, StudyOverallStatus, StudyOverallStatusConverter>
        implements StudyOverallStatusServiceLocal {

    /** Standard error message for empty methods to be overridden. */
    private static final String ERR_MSG_METHOD_NOT_IMPLEMENTED = "Method not yet implemented.";

    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;

    /**
     * {@inheritDoc}
     */
    @Override
    @RolesAllowed({SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE })
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    public StudyOverallStatusDTO create(StudyOverallStatusDTO dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Existing StudyOverallStatus objects cannot be modified. Append new object instead.");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        // enforce business rules
        StudyOverallStatusDTO oldStatus = getCurrentByStudyProtocol(dto.getStudyProtocolIdentifier());
        String currentText = null;
        if (oldStatus != null && oldStatus.getReasonText() != null) {
            currentText = oldStatus.getReasonText().getValue();
        }
        String newText = null;
        if (dto.getReasonText() != null) {
            newText = dto.getReasonText().getValue();
        }
        boolean statusTextChanged = 
                (currentText == null) ? (newText != null) : !currentText.equals(newText);
        if (oldStatus != null && !isTrialStatusOrDateChanged(dto, dto.getStudyProtocolIdentifier()) 
                && !statusTextChanged) {
            //this means no change in update
            return oldStatus;
        }
        StudyStatusCode oldCode = null;
        DateMidnight oldDate = null;

        if (oldStatus != null) {
            oldCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
            oldDate = TsConverter.convertToDateMidnight(oldStatus.getStatusDate());
        }
        StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
        DateMidnight newDate = TsConverter.convertToDateMidnight(dto.getStatusDate());
        validateStatusCodeAndDate(oldCode, newCode, oldDate, newDate);
        validateReasonText(dto);

        StudyOverallStatus bo = convertFromDtoToDomain(dto);

        //Create intermediate status if we're transitioning directly from In-Review to Active or from Active to
        //Closed to Accrual and Intervention or Active to Completed or Temporarily Closed to Accrual to
        //Administratively Complete
        createIntermediateStudyOverallStatus(oldStatus, dto);

        // update
        session.saveOrUpdate(bo);
        StudyRecruitmentStatus srs = createStudyRecruitmentStatus(bo);
        if (srs != null) {
            session.saveOrUpdate(srs);
        }
        return convertFromDomainToDto(bo);
    }

    /**
     * Creates intermediate statuses if the transition from one status to the other skips one.
     * Also handles creation of the proper study recruitment status as well.
     * @param oldStatus The status being transitioned from
     * @param newStatus The status being transitioned to
     * @throws PAException on error
     */
    private void createIntermediateStudyOverallStatus(StudyOverallStatusDTO oldStatus,
            StudyOverallStatusDTO newStatus) throws PAException {
        if (oldStatus == null || newStatus == null) {
            return;
        }
        StudyStatusCode oldStudyStatusCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
        StudyStatusCode newStudyStatusCode = StudyStatusCode.getByCode(newStatus.getStatusCode().getCode());
        List<StudyOverallStatus> intermediateStatuses = new ArrayList<StudyOverallStatus>();

        if (oldStudyStatusCode == StudyStatusCode.IN_REVIEW && (newStudyStatusCode == StudyStatusCode.ACTIVE
                        || newStudyStatusCode == StudyStatusCode.ENROLLING_BY_INVITATION)) {
            intermediateStatuses.add(getSystemStudyOverallStatus(newStatus, StudyStatusCode.APPROVED));
        } else if (oldStudyStatusCode ==  StudyStatusCode.ACTIVE
                && newStudyStatusCode == StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION) {
            intermediateStatuses.add(getSystemStudyOverallStatus(newStatus, StudyStatusCode.CLOSED_TO_ACCRUAL));
        } else if ((oldStudyStatusCode == StudyStatusCode.ACTIVE
                    || oldStudyStatusCode == StudyStatusCode.ENROLLING_BY_INVITATION)
                && newStudyStatusCode == StudyStatusCode.COMPLETE) {
            intermediateStatuses.add(getSystemStudyOverallStatus(newStatus, StudyStatusCode.CLOSED_TO_ACCRUAL));
            intermediateStatuses.add(getSystemStudyOverallStatus(newStatus,
                    StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        } else if (oldStudyStatusCode == StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL
                && newStudyStatusCode == StudyStatusCode.ADMINISTRATIVELY_COMPLETE) {
            intermediateStatuses.add(getSystemStudyOverallStatus(newStatus,
                    StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        } else if (oldStudyStatusCode == StudyStatusCode.ENROLLING_BY_INVITATION
                && newStudyStatusCode == StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION) {
            intermediateStatuses.add(getSystemStudyOverallStatus(newStatus, StudyStatusCode.CLOSED_TO_ACCRUAL));
        }
        for (StudyOverallStatus status : intermediateStatuses) {
            PaHibernateUtil.getCurrentSession().saveOrUpdate(status);
            StudyRecruitmentStatus srs = createStudyRecruitmentStatus(status);
            if (srs != null) {
                PaHibernateUtil.getCurrentSession().saveOrUpdate(srs);
            }
        }
    }

    /**
     * Creates a recruitment status for the given StudyOverallStatus.
     * @param bo the StudyOverallStatus domain object.
     * @return the recruitment status domain object.
     */
    StudyRecruitmentStatus createStudyRecruitmentStatus(StudyOverallStatus bo) {
        // automatically update StudyRecruitmentStatus for applicable overall status code's
        if (bo != null && bo.getStatusCode() != null) {
            StudyRecruitmentStatus srsBo = new StudyRecruitmentStatus();
            srsBo.setStatusCode(RecruitmentStatusCode.getByStatusCode(bo.getStatusCode()));
            srsBo.setStatusDate(bo.getStatusDate());
            srsBo.setStudyProtocol(bo.getStudyProtocol());
            return srsBo;
        }
        return null;
    }

    private StudyOverallStatus getSystemStudyOverallStatus(StudyOverallStatusDTO newStatus, StudyStatusCode statusCode)
            throws PAException {
        StudyOverallStatus status = convertFromDtoToDomain(newStatus);
        status.setSystemCreated(true);
        status.setStatusCode(statusCode);
        return status;
    }

    /**
     * Performs validation of status date and code.
     * @param oldCode the current status code
     * @param newCode the status code to transition to
     * @param oldDate the current date
     * @param newDate the date of the new transition
     * @throws PAException on error
     */
    private void validateStatusCodeAndDate(StudyStatusCode oldCode, StudyStatusCode newCode, DateMidnight oldDate,
            DateMidnight newDate) throws PAException {
        checkCondition(newCode == null, "Study status must be set.");
        checkCondition(newDate == null, "Study status date must be set.");
        if (oldCode != null) {
            checkCondition(!oldCode.canTransitionTo(newCode),
                           "Invalid study status transition from " + oldCode.getCode() + " to " + newCode.getCode()
                                   + ".");
        }
        checkCondition(oldDate != null && newDate.isBefore(oldDate),
                       "New current status date should be bigger/same as old date.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RolesAllowed({SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE })
    public StudyOverallStatusDTO update(StudyOverallStatusDTO dto) throws PAException {
        checkCondition(StudyStatusCode.getByCode(dto.getStatusCode().getCode()) == null, "Study status must be set.");
        checkCondition(TsConverter.convertToTimestamp(dto.getStatusDate()) == null, "Study status date must be set.");
        StudyProtocolDTO studyProtocolDto = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
        checkCondition(IntConverter.convertToInteger(studyProtocolDto.getSubmissionNumber()) > 1,
                       "Study status Cannot be updated.");
        validateReasonText(dto);
        return super.update(dto);
    }

    /**
     * @param ii index of object
     * @throws PAException exception
     */
    @Override
    @RolesAllowed({SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE })
    public void delete(Ii ii) throws PAException {
        throw new PAException(ERR_MSG_METHOD_NOT_IMPLEMENTED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrialStatusOrDateChanged(StudyOverallStatusDTO newStatusDto, Ii studyProtocolIi)
            throws PAException {
        DocumentWorkflowStatusDTO dwsDTO = documentWorkFlowStatusService.getCurrentByStudyProtocol(studyProtocolIi);
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        boolean statusOrDateChanged = true;
        // original submission
        DocumentWorkflowStatusCode currentDwfStatus =
                DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dwsDTO.getStatusCode()));
        if (DocumentWorkflowStatusCode.SUBMITTED == currentDwfStatus
                && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) {
            statusOrDateChanged = false;
        }
        StudyOverallStatusDTO currentDBdto = getCurrentByStudyProtocol(studyProtocolIi);
        StudyStatusCode currentStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
        DateMidnight currentStatusDate = TsConverter.convertToDateMidnight(currentDBdto.getStatusDate());
        StudyStatusCode newStatusCode = StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode());
        DateMidnight newStatusDate = TsConverter.convertToDateMidnight(newStatusDto.getStatusDate());
 
        boolean codeChanged =
                (newStatusCode == null) ? (currentStatusCode != null) : !newStatusCode.equals(currentStatusCode);
        boolean statusDateChanged =
                (currentStatusDate == null) ? (newStatusDate != null) : !currentStatusDate.equals(newStatusDate);       
        if (!codeChanged && !statusDateChanged) {
            statusOrDateChanged = false;
        }
        return statusOrDateChanged;
    }

   /**
    * {@inheritDoc}
    */
    @Override
    public void validate(StudyOverallStatusDTO statusDto, StudyProtocolDTO studyProtocolDTO) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        this.validate(statusDto, studyProtocolDTO, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAValidationException("Validation Exception " + errorMsg);
        }
    }

    private void validateReasonText(StudyOverallStatusDTO statusDto) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        StudyStatusCode status = StudyStatusCode.getByCode(CdConverter.convertCdToString(statusDto.getStatusCode()));
        if (status != null && status.requiresReasonText()) {
            if (ISOUtil.isStNull(statusDto.getReasonText())) {
                errorMsg.append("A reason must be entered when the study status is set to "
                        + CdConverter.convertCdToString(statusDto.getStatusCode()) + ".");
            }
            if (StringUtils.length(StConverter.convertToString(statusDto.getReasonText()))
                    > PAAttributeMaxLen.LEN_2000) {
                errorMsg.append("Reason must be less than 2000 characters.");
            }
        } else {
            statusDto.setReasonText(StConverter.convertToSt(null));
        }
        if (StringUtils.isNotEmpty(errorMsg.toString())) {
            throw new PAValidationException("Validation Exception " + errorMsg.toString());
        }
    }

    /**
     * Checks the given condition and generates a PAException accordingly.
     * @param condition The condition that must cause a PAException
     * @param msg The message in the exception
     * @throws PAException thrown if the given condition is true.
     */
    private void checkCondition(boolean condition, String msg) throws PAException {
        if (condition) {
            throw new PAException(msg);
        }
    }

    /**
     * @param statusDto
     * @param studyProtocolDTO
     * @param addActionError
     * @throws PAException
     * @return
     */
    private StringBuffer enforceBusniessRuleForUpdate(StudyOverallStatusDTO statusDto,
            StudyProtocolDTO studyProtocolDTO) throws PAException {
        StringBuffer errMsg = new StringBuffer();
        StudyStatusCode newCode = StudyStatusCode.getByCode(statusDto.getStatusCode().getCode());
        DateMidnight newStatusDate = TsConverter.convertToDateMidnight(statusDto.getStatusDate());
        StudyOverallStatusDTO  currentDBdto = getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
        StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
        DateMidnight newStartDate = TsConverter.convertToDateMidnight(studyProtocolDTO.getStartDate());
        String actualString = "Actual";
        String anticipatedString = "Anticipated";
        String newStartDateType = studyProtocolDTO.getStartDateTypeCode().getCode();
        String newCompletionDateType = studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode();

        if (newCode == null) {
            errMsg.append("Invalid new study status: '" + statusDto.getStatusCode().getCode() + "'. ");
        } else if (oldStatusCode != null && !oldStatusCode.canTransitionTo(newCode)) {
            errMsg.append("Invalid study status transition from '" + oldStatusCode.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        if (!ISOUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())
                && ISOUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())) {

            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
                if (newStartDate.equals(newStatusDate)) {
                    errMsg.append("When transitioning from 'Approved' to 'Active' the trial start "
                            + "date must be the same as the status date.");
                }
                if (!newStartDateType.equals(actualString)) {
                    errMsg.append("When transitioning from 'Approved' to 'Active' "
                            + "the trial start date must be 'Actual'.");
                }
            }
            if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                    && newStartDateType.equals(anticipatedString)) {
                errMsg.append("Trial start date can be 'Anticipated' only if the status is "
                        + "'Approved' or 'Withdrawn'.");
            }
            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.WITHDRAWN.equals(newCode)
                    && newStartDateType.equals(actualString)) {
                errMsg.append("Trial Start date type should be 'Anticipated' and Trial Start date "
                        + "should be future date if Trial Status is changed from 'Approved' to 'Withdrawn'.  ");
            }
            if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
                StudyOverallStatusDTO oldStatusDto = getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
                if (newCompletionDateType.equals(anticipatedString)) {
                    errMsg.append("Primary Completion Date cannot be 'Anticipated' when "
                            + "Current Trial Status is '");
                    errMsg.append(newCode.getCode());
                    errMsg.append("'.");
                }
                if (TsConverter.convertToDateMidnight(studyProtocolDTO.getPrimaryCompletionDate())
                        .isBefore(TsConverter.convertToDateMidnight(oldStatusDto.getStatusDate()))) {
                    errMsg.append("Primary Completion Date must be the same or greater than Current Trial "
                            + " Status Date when Current Trial Status is '");
                    errMsg.append(newCode.getCode());
                    errMsg.append("'.");
                }
            } else {
                if (!newCompletionDateType.equals(anticipatedString)) {
                    errMsg.append("Trial completion date must be 'Anticipated' when the status is "
                            + "not 'Complete' or 'Administratively Complete'.");
                }
            }
        }
        return errMsg;
    }

    private StringBuffer validateTrialDates(StudyProtocolDTO dto, StudyOverallStatusDTO statusDto) {
        StringBuffer errors = new StringBuffer();
        DateMidnight statusDate = TsConverter.convertToDateMidnight(statusDto.getStatusDate());
        String statusCode = CdConverter.convertCdToString(statusDto.getStatusCode());

        DateMidnight today = new DateMidnight();
        StudyProtocolDates dates = AbstractStudyProtocolConverter.convertDatesToDomain(dto);
        DateMidnight startDate = (dates.getStartDate() != null) ? new DateMidnight(dates.getStartDate()) : null;
        DateMidnight primaryCompletionDate =
                (dates.getPrimaryCompletionDate() != null) ? new DateMidnight(dates.getPrimaryCompletionDate()) : null;
        DateMidnight completionDate =
                (dates.getCompletionDate() != null) ? new DateMidnight(dates.getCompletionDate()) : null;
        //If the null flavor is unknown we ignore primary completion date, thus making it option for PO-2429
        boolean unknownPrimaryCompletionDate = dto.getPrimaryCompletionDate().getNullFlavor() == NullFlavor.UNK;

        // Constraint/Rule: 22 Current Trial Status Date must be current or past.
        if (today.isBefore(statusDate)) {
            errors.append("Current Trial Status Date cannot be in the future.\n");
        }
        // Constraint/Rule: 23 Trial Start Date must be current/past if 'actual' trial start date type
        // is selected and must be future if 'anticipated' trial start date type is selected.
        if (dates.getStartDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL
                && today.isBefore(startDate)) {
            errors.append("Actual Trial Start Date must be current or in the past. \n");
        } else if (dates.getStartDateTypeCode() == ActualAnticipatedTypeCode.ANTICIPATED
                && today.isAfter(startDate)) {
            errors.append("Anticipated Start Date must be current or in the future. \n");
        }
        // Constraint/Rule:24 Primary Completion Date must be current/past if 'actual' primary completion date type
        // is selected and must be future if 'anticipated'trial primary completion date type is selected.
        if (unknownPrimaryCompletionDate) {
            if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL) {
                errors.append("Unknown Primary Completion date must be marked as Anticipated.\n");
            }
        } else {
            if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL
                    && today.isBefore(primaryCompletionDate)) {
                errors.append("Actual Primary Completion Date must be current or in the past.\n");
            } else if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.ANTICIPATED
                    && today.isAfter(primaryCompletionDate)) {
                errors.append("Anticipated Primary Completion Date must be current or in the future. \n");
            }

        }

        Set<String> compareStatusCodes = new HashSet<String>();
        compareStatusCodes.add(StudyStatusCode.APPROVED.getCode());
        compareStatusCodes.add(StudyStatusCode.IN_REVIEW.getCode());
        compareStatusCodes.add(StudyStatusCode.WITHDRAWN.getCode());
        if (!compareStatusCodes.contains(statusCode)
                && ActualAnticipatedTypeCode.ACTUAL != dates.getStartDateTypeCode()) {
            errors.append("Trial Start Date must be Actual for any Current Trial Status besides Approved/In Review.\n");
        }

        // Constraint/Rule: 25 If Current Trial Status is 'Active', Trial Start Date must be the same as
        //Current Trial Status Date and have 'actual' type. New Rule added-01/15/09 if start date is smaller
        //than the Current Trial Status Date, replace Current Trial Status date with the actual Start Date.
        //pa2.0 as part of release removing the "replace Current Trial Status date with the actual Start Date."
        if (StudyStatusCode.ACTIVE.getCode().equals(statusCode) && (startDate.isAfter(statusDate)
                || dates.getStartDateTypeCode() != ActualAnticipatedTypeCode.ACTUAL)) {
            errors.append("If Current Trial Status is Active, Trial Start Date must be Actual "
                    + " and same as or smaller than Current Trial Status Date.\n");
        }


        // Constraint/Rule: 27 If Current Trial Status is 'Completed', Primary Completion Date must be the
        // same as Current Trial Status Date and have 'actual' type.
        if (StudyStatusCode.COMPLETE.getCode().equals(statusCode)
                && (dates.getPrimaryCompletionDateTypeCode() != ActualAnticipatedTypeCode.ACTUAL)) {
            errors.append("If Current Trial Status is Completed, Primary Completion Date must be Actual ");
        }

        // Constraint/Rule: 28 If Current Trial Status is 'Completed' or 'Administratively Completed',
        // Primary Completion Date must have 'actual' type. Primary Completion Date must have 'anticipated' type
        // for any other Current Trial Status value besides 'Completed' or 'Administratively Completed'.
        if (StudyStatusCode.COMPLETE.getCode().equals(statusCode)
                || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(statusCode)) {
            if (dates.getPrimaryCompletionDateTypeCode() != ActualAnticipatedTypeCode.ACTUAL) {
                errors.append("If Current Trial Status is Complete or Administratively Complete, "
                        + " Primary Completion Date must be  Actual.\n");
            }
        }

        // Constraint/Rule:29 Trial Start Date must be same/smaller than Primary Completion Date.
        if (!unknownPrimaryCompletionDate
                && dates.getPrimaryCompletionDate() != null
                && dates.getPrimaryCompletionDate()
                        .before(dates.getStartDate())) {
            errors.append("Trial Start Date must be same or earlier than Primary Completion Date.\n");
        }

        if (completionDate != null) {
            if (dates.getCompletionDateTypeCode() == null) {
                errors.append("Completion Date Type must be specified.\n");
            } else {
                if (dates.getCompletionDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL) {
                    if (today.isBefore(completionDate)) {
                        errors.append("Actual Trial Completion Date must be current or in the past.\n");
                    }
                } else {
                    if (today.isAfter(completionDate)) {
                        errors.append("Anticipated Completion Date must be current or in the future\n");
                    }
                }
            }
            if (!unknownPrimaryCompletionDate && completionDate.isBefore(primaryCompletionDate)) {
                errors.append("Completion date must be >= Primary completion date.\n");
            }
        }

        return errors;
    }

    /**
     * @param documentWorkFlowStatusService the documentWorkFlowStatusService to set
     */
    public void setDocumentWorkFlowStatusService(DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService) {
        this.documentWorkFlowStatusService = documentWorkFlowStatusService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusServiceLocal#validate(gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO,
     *  gov.nih.nci.pa.iso.dto.StudyProtocolDTO, java.lang.StringBuilder)
     */
    @Override
    public void validate(StudyOverallStatusDTO statusDto,
            StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) {
        try {
            if (statusDto == null) {
                errorMsg.append("Study Overall Status cannot be null. ");
            } else {
                if (!ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())
                        && this.isTrialStatusOrDateChanged(statusDto,
                                studyProtocolDTO.getIdentifier())) {
                    errorMsg.append(enforceBusniessRuleForUpdate(statusDto,
                            studyProtocolDTO));
                }
                errorMsg.append(validateTrialDates(studyProtocolDTO, statusDto));
                validateReasonText(statusDto);
            }
        } catch (PAException e) {
            errorMsg.append(e.getMessage());
        }
    }
}
