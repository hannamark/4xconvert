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

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
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
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 08/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyOverallStatusServiceBean
extends AbstractStudyIsoService<StudyOverallStatusDTO, StudyOverallStatus, StudyOverallStatusConverter>
        implements StudyOverallStatusServiceRemote, StudyOverallStatusServiceLocal {

    private static final Logger LOG  = Logger.getLogger(StudyOverallStatusServiceBean.class);
    /** Standard error message for empty methods to be overridden. */
    protected static String errMsgMethodNotImplemented = "Method not yet implemented.";
    @EJB
       StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    ProtocolQueryServiceLocal spqService = null;
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
        try {
            session = HibernateUtil.getCurrentSession();

            // enforce business rules
           StudyOverallStatusDTO oldStatus = getCurrentByStudyProtocol(dto.getStudyProtocolIdentifier());
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
            StudyRecruitmentStatus srs = StudyRecruitmentStatusServiceBean.create(bo);
            if (srs != null) {
                session.saveOrUpdate(StudyRecruitmentStatusServiceBean.create(bo));
            }
            resultDto = Converters.get(StudyOverallStatusConverter.class).convertFromDomainToDto(bo);
        } catch (HibernateException hbe) {
        throw new PAException(" Hibernate exception in createStudyOverallStatus ", hbe);
        }
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
        try {
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
        } catch (HibernateException hbe) {
        throw new PAException(" Hibernate exception in createStudyOverallStatus ", hbe);
        }
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
        StudyProtocolQueryDTO spqDTO = spqService.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(studyProtocolIi));
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        boolean statusOrDateChanged = true;
        //original submission
        if (spqDTO.getDocumentWorkflowStatusCode() != null 
                && spqDTO.getDocumentWorkflowStatusCode().getCode().
                    equalsIgnoreCase(DocumentWorkflowStatusCode.SUBMITTED.getCode()) 
                && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) {
            statusOrDateChanged = false;
        }
        StudyOverallStatusDTO  currentDBdto = getCurrentByStudyProtocol(studyProtocolIi);
        StudyStatusCode currentStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
        Timestamp currentStatusDate = PAUtil.dateStringToTimestamp(currentDBdto.getStatusDate().toString());
        
        boolean codeChanged = (StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode()) == null)
                ? (currentStatusCode != null) 
                        : !StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode()).equals(currentStatusCode);
        boolean statusDateChanged = (currentStatusDate == null) 
                ? (PAUtil.dateStringToTimestamp(newStatusDto.getStatusDate().toString()) != null) 
                : !currentStatusDate.equals(PAUtil.dateStringToTimestamp(newStatusDto.getStatusDate().toString()));
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
        if (StudyStatusCode.ACTIVE.getCode().equals(statusCode)
                && (trialStartDate.after(statusDate) || !studyStartDateType.equals(
                         ActualAnticipatedTypeCode.ACTUAL.getCode()))) {
                errors.append("If Current Trial Status is Active, Trial Start Date must be Actual "
                              + " and same as Current Trial Status Date.\n");
        }
        // Constraint/Rule: 26 If Current Trial Status is 'Approved', Trial Start Date must have 'anticipated' type. 
        //Trial Start Date must have 'actual' type for any other Current Trial Status value besides 'Approved'. 
          if (StudyStatusCode.APPROVED.getCode().equals(statusCode)
                  || StudyStatusCode.IN_REVIEW.getCode().equals(statusCode)) {
              if (!studyStartDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                errors.append("If Current Trial Status is Approved/In Review, Trial Start Date must be Anticipated.\n");
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
