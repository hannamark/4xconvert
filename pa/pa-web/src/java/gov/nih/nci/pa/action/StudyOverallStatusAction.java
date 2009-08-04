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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * Action class for viewing and editing the protocol status.
 *
 * @author Hugh Reinhart
 * @since 08/20/2008
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyFields", "PMD.SignatureDeclareThrowsException",
    "PMD.ExcessiveClassLength" })

public class StudyOverallStatusAction extends ActionSupport implements
        Preparable {
    private static final long serialVersionUID = 1L;
    private static final String ACTION_HISTORY = "historypopup";
    private static String actualString = "Actual";
    private static String anticipatedString = "Anticipated";

    private Map<String, String>  dateTypeList;
    private StudyProtocolServiceRemote spService;
    private StudyOverallStatusServiceRemote sosService;
    private ProtocolQueryServiceLocal spqService;
    private Ii spIdIi;
    private String currentTrialStatus;
    private String statusDate;
    private String statusReason;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;
    private List<StudyOverallStatusWebDTO> overallStatusList;

    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        dateTypeList = new HashMap<String, String>();
        dateTypeList.put(actualString, actualString);
        dateTypeList.put(anticipatedString, anticipatedString);

        spService = PaRegistry.getStudyProtocolService();
        sosService = PaRegistry.getStudyOverallStatusService();
        spqService = PaRegistry.getProtocolQueryService();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
        spDTO = spqService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(spIdIi));
    }

    /**
     * @return Action result.
     */
    @Override
    public String execute()  {
        try {
        loadForm();
        } catch (PAException e) {
            addActionError(e.getMessage());   
        }
        return Action.SUCCESS;
    }

    /**
     * @return result
     */
    public String update() {
        clearErrorsAndMessages();

        boolean statusChanged;
        try {
            statusChanged = enforceBusinessRules();
            validateTrialDates();
            if (hasActionErrors()) {
                return Action.SUCCESS;
            }
            if (statusChanged) {
                insertOrUpdateStudyOverallStatus();
            }
            if (!hasActionErrors()) {
                updateStudyProtocol();
            }
            if (!hasActionErrors()) {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
                loadForm();
            }
        } catch (PAException e) {
          addActionError(e.getMessage());
        }
        return Action.SUCCESS;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String historypopup() throws Exception {
        overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        List<StudyOverallStatusDTO> isoList = sosService.getByStudyProtocol(spIdIi);
        for (StudyOverallStatusDTO iso : isoList) {
            overallStatusList.add(new StudyOverallStatusWebDTO(iso));
        }
        return ACTION_HISTORY;
    }

    /**
     * @return the startDateType
     */
    public String getStartDateType() {
        return startDateType;
    }

    /**
     * @param startDateType the startDateType to set
     */
    public void setStartDateType(String startDateType) {
        this.startDateType = startDateType;
    }

    /**
     * @return the completionDateType
     */
    public String getCompletionDateType() {
        return completionDateType;
    }

    /**
     * @param completionDateType the completionDateType to set
     */
    public void setCompletionDateType(String completionDateType) {
        this.completionDateType = completionDateType;
    }

    /**
     * @return Trial completion date.
     */
    public Map<String, String> getPrimaryCompletionDate() {
        return dateTypeList;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = PAUtil.normalizeDateString(startDate);
    }

    /**
     * @return the completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = PAUtil.normalizeDateString(completionDate);
    }

    /**
     * @return the dateTypeList
     */
    public Map<String, String> getDateTypeList() {
        return dateTypeList;
    }

    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = PAUtil.normalizeDateString(statusDate);
    }

    /**
     * @return the statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = PAUtil.stringSetter(statusReason);
    }

    /**
     * @return the currentTrialStatus
     */
    public String getCurrentTrialStatus() {
        return currentTrialStatus;
    }

    /**
     * @param currentTrialStatus the currentTrialStatus to set
     */
    public void setCurrentTrialStatus(String currentTrialStatus) {
        this.currentTrialStatus = currentTrialStatus;
    }

    /**
     * @return the overallStatusList
     */
    public List<StudyOverallStatusWebDTO> getOverallStatusList() {
        return overallStatusList;
    }

    /**
     * @param overallStatusList the overallStatusList to set
     */
    public void setOverallStatusList(List<StudyOverallStatusWebDTO> overallStatusList) {
        this.overallStatusList = overallStatusList;
    }
    private void insertOrUpdateStudyOverallStatus() throws PAException {
        if (currentTrialStatus != null) {
            StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
            dto.setIdentifier(IiConverter.convertToIi((Long) null));
            dto.setReasonText(StConverter.convertToSt(this.getStatusReason()));
            dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(currentTrialStatus)));
            dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(statusDate)));
            dto.setStudyProtocolIdentifier(spIdIi);

            StudyProtocolQueryDTO spqDTO = spqService.getTrialSummaryByStudyProtocolId(
                        IiConverter.convertToLong(spIdIi));

            StudyProtocolDTO spDTO = spService.getStudyProtocol(spIdIi);
                //original submission
                if (spqDTO.getDocumentWorkflowStatusCode() != null 
                        && spqDTO.getDocumentWorkflowStatusCode().getCode().equalsIgnoreCase("SUBMITTED") 
                && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) {
                    StudyOverallStatusDTO sosDto = null;
                    sosDto = sosService.getCurrentByStudyProtocol(spIdIi);
                    dto.setIdentifier(sosDto.getIdentifier());
                    sosService.update(dto);
                } else {
                    sosService.create(dto);
                }
                // set the current date and status to the session
                spqDTO.setStudyStatusCode(StudyStatusCode.getByCode(currentTrialStatus));
                spqDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp(statusDate));
                // set the nee object back to session
                ServletActionContext.getRequest().getSession().setAttribute(
                        Constants.TRIAL_SUMMARY, spqDTO);
        }
    }

    private void updateStudyProtocol() {
        StudyProtocolDTO dto;
        try {
            dto = spService.getStudyProtocol(spIdIi);
            dto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(startDate)));
            dto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(completionDate)));
            dto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(startDateType)));
            dto.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(completionDateType)));
            spService.updateStudyProtocol(dto);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }

    private void loadForm() throws PAException  {
        StudyProtocolDTO spDto = spService.getStudyProtocol(spIdIi);
        StudyOverallStatusDTO sosDto = null;
        sosDto = sosService.getCurrentByStudyProtocol(spIdIi);
        Timestamp tsTemp;
        if (spDto != null) {
            tsTemp = TsConverter.convertToTimestamp(spDto.getStartDate());
            if (tsTemp != null) {
                setStartDate(tsTemp.toString());
            } else {
                setStartDate(null);
            }

            tsTemp = TsConverter.convertToTimestamp(spDto.getPrimaryCompletionDate());
            if (tsTemp != null) {
                setCompletionDate(tsTemp.toString());
            } else {
                setCompletionDate(null);
            }
            setStartDateType(spDto.getStartDateTypeCode().getCode());
            setCompletionDateType(spDto.getPrimaryCompletionDateTypeCode().getCode());
        } else {
            setStartDate(null);
            setCompletionDate(null);
            setStartDateType(null);
            setCompletionDateType(null);
        }

        if (sosDto != null) {
            setCurrentTrialStatus((sosDto.getStatusCode() != null)
                    ? sosDto.getStatusCode().getCode() : null);
            tsTemp = TsConverter.convertToTimestamp(sosDto.getStatusDate());
            if (tsTemp != null) {
                setStatusDate(tsTemp.toString());
            } else {
                setStatusDate(null);
            }
            setStatusReason(StConverter.convertToString(sosDto.getReasonText()));
        } else {
            setCurrentTrialStatus(null);
            setStatusDate(null);
            setStatusReason(null);
        }
    }

    @SuppressWarnings({"PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
    private boolean enforceBusinessRules() throws PAException {
        StudyStatusCode newCode = StudyStatusCode.getByCode(currentTrialStatus);

        Timestamp statusTimestamp = PAUtil.dateStringToTimestamp(statusDate);
        Timestamp startTimestamp = PAUtil.dateStringToTimestamp(startDate);
        Timestamp completionTimestamp = PAUtil.dateStringToTimestamp(completionDate);
        StudyStatusCode oldCode = null;
        Timestamp oldDate = null;
        String oldReason = null;
        StudyOverallStatusDTO sos = sosService.getCurrentByStudyProtocol(spIdIi);
        if (sos != null) {
            oldCode = StudyStatusCode.getByCode(CdConverter.convertCdToString(sos.getStatusCode()));
            oldDate = TsConverter.convertToTimestamp(sos.getStatusDate());
            oldReason = StConverter.convertToString(sos.getReasonText());
        }
        StudyOverallStatusDTO oldStatusDto = sosService.getCurrentByStudyProtocol(spIdIi);
        boolean codeChanged = (newCode == null) ? (oldCode != null) : !newCode.equals(oldCode);
        boolean dateChanged = (oldDate == null) ? (statusTimestamp != null) : !oldDate.equals(statusTimestamp);
        boolean reasonChanged = (oldReason == null) ? (statusReason != null) : !oldReason.equals(statusReason);
        if (!codeChanged && !dateChanged && !reasonChanged) {
            return false;
        }
        StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest()
        .getSession().getAttribute(Constants.TRIAL_SUMMARY);
        
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(spIdIi);
            //original submission don't check status transition
            if (!(spqDTO.getDocumentWorkflowStatusCode() != null 
                    && spqDTO.getDocumentWorkflowStatusCode().getCode().equalsIgnoreCase("SUBMITTED") 
            && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) //{ 
             // enforce status transition rules (this must be first check per Tracker 17366
                 && ((oldCode != null) && !oldCode.canTransitionTo(newCode))) {
                    addActionError("Illegal study status transition from '" + oldCode.getCode()
                            + "' to '" + newCode.getCode() + "'.  ");
              //}
            }
        // enforce date rules related to status transitions
        if ((startDateType != null) && (completionDateType != null)) {
            if (StudyStatusCode.APPROVED.equals(oldCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
                if (!startTimestamp.equals(statusTimestamp)) {
                    addActionError("When transitioning from 'Approved' to 'Active' the trial start "
                            + "date must be the same as the status date.");
                }
                if (!startDateType.equals(actualString)) {
                    addActionError("When transitioning from 'Approved' to 'Active' "
                            + "the trial start date must be 'Actual'.");
                }
            }
            if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                    && !StudyStatusCode.IN_REVIEW.equals(newCode) && !StudyStatusCode.DISAPPROVED.equals(newCode)
                    && startDateType.equals(anticipatedString)) {
                addActionError("Trial start date can be 'Anticipated' only if the status is "
                            + "'Approved' or 'Withdrawn'.");
            }
            if (StudyStatusCode.APPROVED.equals(oldCode) && StudyStatusCode.WITHDRAWN.equals(newCode)
                    && !StudyStatusCode.IN_REVIEW.equals(newCode) && !StudyStatusCode.DISAPPROVED.equals(newCode)
                    && startDateType.equals(actualString)) {
                addActionError("Trial Start date type should be ‘Anticipated’ and Trial Start date "
                            + "should be future date if Trial Status is changed from ‘Approved’ to ‘Withdrawn’.  ");
            }
            if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
                if (completionDateType.equals(anticipatedString)) {
                    addActionError("Primary Completion Date cannot be 'Anticipated' when "
                            + "Current Trial Status is '" + newCode.getCode() + "'.");
                }
                if (completionTimestamp.before(TsConverter.convertToTimestamp(oldStatusDto.getStatusDate()))) {
                    addActionError("Primary Completion Date must be the same or greater than "
                            + "Current Trial Status Date when Current Trial Status is '" + newCode.getCode() + "'.");
                }
            } else {
                if (!completionDateType.equals(anticipatedString)) {
                    addActionError("Trial completion date must be 'Anticipated' when the status is "
                            + "not 'Complete' or 'Administratively Complete'.");
                }
            }
        }
        return true;
    }
    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
    private void validateTrialDates() {
        // Constraint/Rule: 18 Current Trial Status Date must be current or past.
            Timestamp userStatusDate = PAUtil.dateStringToTimestamp(statusDate);
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(userStatusDate)) {
                addActionError("Current Trial Status Date cannot be in the future.");                
            }        
        // Constraint/Rule: 19 Trial Start Date must be current/past if 'actual' trial start date type 
        // is selected and must be future if 'anticipated' trial start date type is selected.
            if (startDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                Timestamp trialStartDate = PAUtil.dateStringToTimestamp(startDate);
                currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.before(trialStartDate)) {
                    addActionError("Actual Trial Start Date must be current or in past.");                
                }
           } else if (startDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(startDate);
              currentTimeStamp = new Timestamp((new Date()).getTime());
              if (currentTimeStamp.after(trialStartDate)) {
                  addActionError("Anticipated Start Date must be in future.");                
              }
            }          
        // Constraint/Rule: 20 Primary Completion Date must be current/past if 'actual' 
        // primary completion date type is selected and must be future if 'anticipated' 
        // trial primary completion date type is selected.
        if (PAUtil.isNotEmpty(completionDate) && PAUtil.isNotEmpty(completionDateType)) {
            if (completionDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                Timestamp userCompletionDate = PAUtil.dateStringToTimestamp(completionDate);
                currentTimeStamp = new Timestamp((new Date()).getTime());
                 if (currentTimeStamp.before(userCompletionDate)) {
                     addActionError("Actual Primary Completion Date must be current or in past.");                
                   }
            } else if (completionDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                    Timestamp userCompletionDate = PAUtil.dateStringToTimestamp(completionDate);
                    currentTimeStamp = new Timestamp((new Date()).getTime());
                    if (currentTimeStamp.after(userCompletionDate)) {
                        addActionError("Anticipated Primary Completion Date must be in future.");                
                        }
            }          
        }        
        // Constraint/Rule:  21 If Current Trial Status is 'Active', Trial Start Date must be the same as 
        // Current Trial Status Date and have 'actual' type.
        //pa2.0 release adding Trial Start date is smaller or same Current Trial Status Date
        if (PAUtil.isNotEmpty(currentTrialStatus)
            && PAUtil.isNotEmpty(statusDate)
            && PAUtil.isNotEmpty(startDate)
            && PAUtil.isNotEmpty(startDateType)
            && StudyStatusCode.ACTIVE.getCode().equals(currentTrialStatus)) {
              userStatusDate = PAUtil.dateStringToTimestamp(statusDate);
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(startDate);
              if (trialStartDate.after(userStatusDate) 
                              || !startDateType.equals(
                                  ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  addActionError("If Current Trial Status is Active, Trial Start Date must be Actual "
                          + " and same as or smaller than Current Trial Status Date");
              }                
          }            
        // Constraint/Rule: 22 If Current Trial Status is 'Approved', Trial Start Date must have 'anticipated' type. 
        //  Trial Start Date must have 'actual' type for any other Current Trial Status value besides 'Approved'. 
        if (PAUtil.isNotEmpty(currentTrialStatus)
                         && PAUtil.isNotEmpty(startDateType)) {
          if (StudyStatusCode.APPROVED.getCode().equals(currentTrialStatus)
              || StudyStatusCode.IN_REVIEW.getCode().equals(currentTrialStatus)
              || StudyStatusCode.DISAPPROVED.getCode().equals(currentTrialStatus)
              || StudyStatusCode.WITHDRAWN.getCode().equals(currentTrialStatus)) {
              if (!startDateType.equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addActionError("If Current Trial Status is " + currentTrialStatus   
                          + " , Trial Start Date must be Anticipated");
              }                
          } else {
              if (!startDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
              addActionError("Trial Start Date must be Actual for any Current Trial Status besides Approved/In Review");
              }              
          }
        }        
        // Constraint/Rule: 23 If Current Trial Status is 'Completed', Primary Completion Date must be the 
        // same as Current Trial Status Date and have 'actual' type.
        if (PAUtil.isNotEmpty(currentTrialStatus) && PAUtil.isNotEmpty(statusDate)
            && PAUtil.isNotEmpty(completionDate) && PAUtil.isNotEmpty(completionDateType)
            && StudyStatusCode.COMPLETE.getCode().equals(currentTrialStatus)) {
                  userStatusDate = PAUtil.dateStringToTimestamp(statusDate);
                  //Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(completionDate);
                  if (!completionDateType.equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                      addActionError("If Current Trial Status is Complete, Primary Completion Date must be Actual");
              }                
          }            
        // Constraint/Rule: 24 If Current Trial Status is 'Completed' or 'Administratively Completed', 
        // Primary Completion Date must have 'actual' type. Primary Completion Date must have 'anticipated' type 
        // for any other Current Trial Status value besides 'Completed' or 'Administratively Completed'. 
        if (PAUtil.isNotEmpty(currentTrialStatus) && PAUtil.isNotEmpty(completionDateType)) {
          if (StudyStatusCode.COMPLETE.getCode().equals(currentTrialStatus) 
              || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(currentTrialStatus)) {
              if (!completionDateType.equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  addActionError("If Current Trial Status is Complete or Administratively Complete,"
                          + " Primary Completion Date must be  Actual.");
              }
          } else {
              if (PAUtil.isNotEmpty(completionDateType) && !completionDateType.equals(
                  ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addActionError("Primary Completion Date  must be Anticipated for any other Current Trial "
                          + "Status value besides Complete or Administratively Complete.");                  
              }
          }          
        }        
        // Constraint/Rule:25 Trial Start Date must be same/smaller than Primary Completion Date. 
        if (PAUtil.isNotEmpty(startDate) && PAUtil.isNotEmpty(completionDate)) {
            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(startDate);
            Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(completionDate);
            if (trialCompletionDate.before(trialStartDate)) {
              addActionError("Trial Start Date must be same or earlier than Primary Completion Date.");                
            }
        }   

    }
}
