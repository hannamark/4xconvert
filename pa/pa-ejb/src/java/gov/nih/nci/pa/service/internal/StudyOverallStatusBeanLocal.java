/**
 * 
 */
package gov.nih.nci.pa.service.internal;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.AbstractCurrentStudyIsoService;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyOverallStatusBeanLocal 
 extends AbstractCurrentStudyIsoService<StudyOverallStatusDTO, StudyOverallStatus, StudyOverallStatusConverter>
 implements StudyOverallStatusServiceLocal {
 
  private static final Logger LOG  = Logger.getLogger(StudyOverallStatusBeanLocal.class);
  /** Standard error message for empty methods to be overridden. */
  protected static String errMsgMethodNotImplemented = "Method not yet implemented.";
  @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
  @EJB
    DocumentWorkflowStatusServiceLocal dwsService = null;
  
  
  /**
   * @return log4j Logger
   */
  @Override
   protected Logger getLogger() {
     return LOG;
   }

  /**
   * Method used to update the StudyOverallStatus and StudyRecruitmentStatus.
   * Note that this is the only method which does this.  StudyRecruitmentStatusService
   * is used for reporting only.
   *
   * @param dto studyOverallStatusDTO
   * @return StudyOverallStatusDTO
   * @throws PAException PAException
   */
 @Override
 @SuppressWarnings({"PMD.ExcessiveMethodLength" })
 public StudyOverallStatusDTO create(
         StudyOverallStatusDTO dto) throws PAException {
     if (!PAUtil.isIiNull(dto.getIdentifier())) {
         String errMsg = " Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ";
         LOG.error(errMsg);
         throw new PAException(errMsg);
     }
     StudyOverallStatusDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     // enforce business rules
     StudyOverallStatusDTO oldStatus = getCurrentByStudyProtocol(dto.getStudyProtocolIdentifier());
     if (oldStatus != null && !isTrialStatusOrDateChanged(dto, dto.getStudyProtocolIdentifier())) {
         //this means no change in update 
         return oldStatus;   
     } 
     StudyStatusCode oldCode = null;
     Timestamp oldDate = null;

     if (oldStatus != null) {
         oldCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
         oldDate = TsConverter.convertToTimestamp(oldStatus.getStatusDate());
     }
     StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
     Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
     if (newCode == null) {
         throw new PAException("Study status must be set.  ");
     }
     if (newDate == null) {
         throw new PAException("Study status date must be set.  ");
     }
     if ((oldCode != null) && !oldCode.canTransitionTo(newCode)) {
         throw new PAException("Illegal study status transition from " + oldCode.getCode()
                 + " to " + newCode.getCode() + ".  ");
     }
     if ((oldDate != null) && newDate.before(oldDate)) {
         throw new PAException("New current status date should be bigger/same as old date.  ");
     }

     StudyOverallStatus bo = Converters.get(StudyOverallStatusConverter.class).convertFromDtoToDomain(dto);
     if (StudyStatusCode.WITHDRAWN.equals(bo.getStatusCode())
             || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(bo.getStatusCode())
             || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(bo.getStatusCode())
             || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(bo.getStatusCode())) {
         if ((bo.getCommentText() == null) || (bo.getCommentText().length() < 1)) {
             throw new PAException("A reason must be entered when the study status is set to "
                     + bo.getStatusCode().getCode() + ".  ");
         }
     } else {
         bo.setCommentText(null);
     }

     // update
     session.saveOrUpdate(bo);
     StudyRecruitmentStatus srs = StudyRecruitmentStatusBeanLocal.create(bo);
     if (srs != null) {
         session.saveOrUpdate(StudyRecruitmentStatusBeanLocal.create(bo));
     }
     resultDto = Converters.get(StudyOverallStatusConverter.class).convertFromDomainToDto(bo);
     return resultDto;
 }

 /**
  * @param dto dto
  * @return null
  * @throws PAException exception
  */
 @Override
 public StudyOverallStatusDTO update(StudyOverallStatusDTO dto) throws PAException {
     StudyOverallStatusDTO resultDto = null;
     Session session = null;

     StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
     Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
     if (newCode == null) {
         throw new PAException("Study status must be set.  ");
     }
     if (newDate == null) {
         throw new PAException("Study status date must be set.  ");
     }
     StudyProtocolDTO studyProtocolDto = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
     if (IntConverter.convertToInteger(studyProtocolDto.getSubmissionNumber()) > 1) {
         throw new PAException("Study status Cannot be updated.  ");
     }
     session = HibernateUtil.getCurrentSession();
     StudyOverallStatus bo = Converters.get(StudyOverallStatusConverter.class).convertFromDtoToDomain(dto);
     if (StudyStatusCode.WITHDRAWN.equals(bo.getStatusCode())
             || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(bo.getStatusCode())
             || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(bo.getStatusCode())
             || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(bo.getStatusCode())) {
         if ((bo.getCommentText() == null) || (bo.getCommentText().length() < 1)) {
             throw new PAException("A reason must be entered when the study status is set to "
                     + bo.getStatusCode().getCode() + ".  ");
         }
     } else {
         bo.setCommentText(null);
     }
     session.merge(bo);
     resultDto = Converters.get(StudyOverallStatusConverter.class).convertFromDomainToDto(bo);
     return resultDto;
 }

 /**
  * @param ii index of object
  * @throws PAException exception
  */
 @Override
 public void delete(Ii ii) throws PAException {
 throw new PAException(errMsgMethodNotImplemented);
 }
 /**
  * 
  * @param newStatusDto dto
  * @return s
  * @throws PAException e
  */
 private boolean isTrialStatusOrDateChanged(StudyOverallStatusDTO newStatusDto,
         Ii studyProtocolIi) throws PAException {
     DocumentWorkflowStatusDTO dwsDTO = dwsService.getCurrentByStudyProtocol(studyProtocolIi);
     StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
     boolean statusOrDateChanged = true;
     //original submission
     if (dwsDTO.getStatusCode().getCode() != null 
             && DocumentWorkflowStatusCode.SUBMITTED.getCode().
                 equalsIgnoreCase(dwsDTO.getStatusCode().getCode()) 
             && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) {
         statusOrDateChanged = false;
     }
     StudyOverallStatusDTO  currentDBdto = getCurrentByStudyProtocol(studyProtocolIi);
     StudyStatusCode currentStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
     Timestamp currentStatusDate = TsConverter.convertToTimestamp(currentDBdto.getStatusDate());
     
     StudyStatusCode newStatusCode =  StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode());
     Timestamp newStatusDate = TsConverter.convertToTimestamp(newStatusDto.getStatusDate());
     boolean codeChanged = (newStatusCode == null)
             ? (currentStatusCode != null) : !newStatusCode.equals(currentStatusCode);
     boolean statusDateChanged = (currentStatusDate == null) 
             ? (newStatusDate != null) : !currentStatusDate.equals(newStatusDate);
     if (!codeChanged && !statusDateChanged) {
         statusOrDateChanged = false;
     }
     return statusOrDateChanged;
 }
 
 /**
  * 
  * @param statusDto sDto
  * @param studyProtocolDTO protocolDto
  * @throws PAException e
  */
 public void validate(StudyOverallStatusDTO statusDto,
         StudyProtocolDTO studyProtocolDTO) throws PAException {
     StringBuffer errorMsg = new StringBuffer();
     if (!PAUtil.isIiNull(studyProtocolDTO.getIdentifier()) 
             && isTrialStatusOrDateChanged(statusDto, studyProtocolDTO.getIdentifier())) {
         errorMsg.append(enforceBusniessRuleForUpdate(statusDto, studyProtocolDTO));
     }
     errorMsg.append(validateTrialDates(studyProtocolDTO, statusDto));
     if (errorMsg.length() > 0) {
         throw new PAException("Validation Exception " + errorMsg);
 }
 }

 /**
  * @param statusDto
  * @param studyProtocolDTO
  * @param addActionError
  * @throws PAException
  * @return 
  */
 @SuppressWarnings({"PMD.ExcessiveMethodLength" })
 private StringBuffer  enforceBusniessRuleForUpdate(StudyOverallStatusDTO statusDto,
         StudyProtocolDTO studyProtocolDTO)
         throws PAException {
     StringBuffer errMsg = new StringBuffer();
     StudyStatusCode newCode = StudyStatusCode.getByCode(statusDto.getStatusCode().getCode());
     Timestamp newStatusTimestamp = PAUtil.dateStringToTimestamp(statusDto.getStatusDate().toString());
     StudyOverallStatusDTO  currentDBdto = getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
     StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
     Timestamp newStartDate = TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate());
     String actualString = "Actual";
     String anticipatedString = "Anticipated";
     String newStartDateType = studyProtocolDTO.getStartDateTypeCode().getCode();
     String newCompletionDateType = studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode();
     
     if (oldStatusCode != null && !oldStatusCode.canTransitionTo(newCode)) {
         errMsg.append("Illegal study status transition from '" + oldStatusCode.getCode()
                 + "' to '" + newCode.getCode() + "'.  ");
     }
     if (!PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode()) 
             && PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())) {
         
         if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
             if (newStartDate.equals(newStatusTimestamp)) {
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
             if (TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate())
                     .before(TsConverter.convertToTimestamp(oldStatusDto.getStatusDate()))) {
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
 @SuppressWarnings({"PMD.ExcessiveMethodLength" })
 private StringBuffer validateTrialDates(StudyProtocolDTO dto, StudyOverallStatusDTO statusDto) {
     StringBuffer errors = new StringBuffer();
     Timestamp statusDate = TsConverter.convertToTimestamp(statusDto.getStatusDate());
     String statusCode = CdConverter.convertCdToString(statusDto.getStatusCode());
     
     Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
     Timestamp trialStartDate = TsConverter.convertToTimestamp(dto.getStartDate()); 
     Timestamp trialCompletionDate = TsConverter.convertToTimestamp(dto.getPrimaryCompletionDate()); 
     String studyStartDateType = CdConverter.convertCdToString(dto.getStartDateTypeCode());
     String primaryCompletionDateType = CdConverter.convertCdToString(dto.getPrimaryCompletionDateTypeCode());
     
     // Constraint/Rule: 22 Current Trial Status Date must be current or past.
     if (currentTimeStamp.before(statusDate)) {
             errors.append("Current Trial Status Date cannot be in the future.\n");                
     }
     // Constraint/Rule: 23 Trial Start Date must be current/past if 'actual' trial start date type 
     // is selected and must be future if 'anticipated' trial start date type is selected. 
     if (studyStartDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())
                 && currentTimeStamp.before(trialStartDate)) {
         errors.append("Actual Trial Start Date must be current or in past. \n");                
     } else if (studyStartDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())
             && currentTimeStamp.after(trialStartDate)) {
         errors.append("Anticipated Start Date must be in future. \n");                
     }          
     //Constraint/Rule:24 Primary Completion Date must be current/past if 'actual' primary completion date type  
     //is selected and must be future if 'anticipated'trial primary completion date type is selected. 
     if (primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())
             && currentTimeStamp.before(trialCompletionDate)) {
         errors.append("Actual Primary Completion Date must be current or in past.\n");                
     } else if (primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())
             && currentTimeStamp.after(trialCompletionDate)) {
         errors.append("Anticipated Primary Completion Date must be in future. \n");                
     }          
     // Constraint/Rule: 25 If Current Trial Status is 'Active', Trial Start Date must be the same as 
     //Current Trial Status Date and have 'actual' type. New Rule added-01/15/09 if start date is smaller 
     //than the Current Trial Status Date, replace Current Trial Status date with the actual Start Date.            
     //pa2.0 as part of release removing the "replace Current Trial Status date with the actual Start Date."
     if (StudyStatusCode.ACTIVE.getCode().equals(statusCode) && (trialStartDate.after(statusDate) 
             || !studyStartDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode()))) {
             errors.append("If Current Trial Status is Active, Trial Start Date must be Actual "
                           + " and same as or smaller than Current Trial Status Date.\n");
     }
     // Constraint/Rule: 26 If Current Trial Status is 'Approved', Trial Start Date must have 'anticipated' type. 
     //Trial Start Date must have 'actual' type for any other Current Trial Status value besides 'Approved'. 
       if (StudyStatusCode.APPROVED.getCode().equals(statusCode)
               || StudyStatusCode.IN_REVIEW.getCode().equals(statusCode)
               || StudyStatusCode.WITHDRAWN.getCode().equals(statusCode)
               || StudyStatusCode.DISAPPROVED.getCode().equals(statusCode)) {
           if (!studyStartDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
             errors.append("If Current Trial Status is " + statusCode + ", Trial Start Date must be Anticipated.\n");
           } 
       } else if (!studyStartDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
         errors.append("Trial Start Date must be Actual for any Current Trial Status besides Approved/In Review.\n");
       }
       // Constraint/Rule: 27 If Current Trial Status is 'Completed', Primary Completion Date must be the 
       // same as Current Trial Status Date and have 'actual' type.
       if (StudyStatusCode.COMPLETE.getCode().equals(statusCode)
               && (!primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode()))) {
                 errors.append("If Current Trial Status is Completed, Primary Completion Date must be Actual ");
       }            
       // Constraint/Rule: 28 If Current Trial Status is 'Completed' or 'Administratively Completed', 
       // Primary Completion Date must have 'actual' type. Primary Completion Date must have 'anticipated' type 
       // for any other Current Trial Status value besides 'Completed' or 'Administratively Completed'.
       if (StudyStatusCode.COMPLETE.getCode().equals(statusCode) 
           || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(statusCode)) { 
           if (!primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                 errors.append("If Current Trial Status is Complete or Administratively Complete, "
                         + " Primary Completion Date must be  Actual.\n");
           }
       } else if (!primaryCompletionDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                     errors.append("Primary Completion Date  must be Anticipated for any other Current Trial"
                         + " Status value besides Complete or Administratively Complete.\n");                  
       }          
       // Constraint/Rule:29 Trial Start Date must be same/smaller than Primary Completion Date. 
       if (trialCompletionDate.before(trialStartDate)) {
          errors.append("Trial Start Date must be same or earlier than Primary Completion Date.\n");                
       }
        
     return errors;
 }


}
