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

package gov.nih.nci.accrual.web.dto.util;

import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.accrual.web.enums.AutopsyPerformed;
import gov.nih.nci.accrual.web.enums.ResponseInds;
import gov.nih.nci.accrual.web.util.WebUtil;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.DiseaseStatusCode;
import gov.nih.nci.pa.enums.PatientVitalStatus;
import gov.nih.nci.pa.iso.util.CdConverter;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * @author lhebel
 * @since 10/28/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveParameterList", "PMD.TooManyFields",
    "PMD.NPathComplexity", "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength" })
public class ParticipantOutcomesWebDto implements Serializable {

    private static final long serialVersionUID = 1839478941711659167L;
    
    /** The Constant validDateMessage. */
    private static final String VALIDDATEMESSAGE = "Please Enter Current or Past Date";
    
    private Ii id;
    private Cd vitalStatus;
    private Cd responseInd;
    private Cd diseaseStatus;
    private Cd assessmentType;
    private Cd recurrenceInd;
    private Cd progressionInd;
    private Ts diseaseStatusDate;
    private Ts recurrenceDate;
    private Ts progressionDate;
    private Ts evaluationDate;
    private Cd progressionSite;
    private Cd bestResponse;
    private Ts bestResponseDate;
    private Cd diseaseEvidence;
    private String treatmentPlanId;


    /**
     * Instantiates a new patient outcomes web dto.
     */
    public ParticipantOutcomesWebDto() {
        // default constructor
    }
    
    /**
     * Validate.
     * 
     * @param dto the dto
     * @param action the action
     * @throws RemoteException remote exception
     */
    public static void validate(ParticipantOutcomesWebDto dto, AbstractAccrualAction action) throws RemoteException {  
        if (dto.getBestResponseDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getBestResponseDate().getValue());
            if (!validDate) {
                action.addFieldError("targetOutcome.bestResponseDate", VALIDDATEMESSAGE);
            }
        }
        if (dto.getEvaluationDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getEvaluationDate().getValue());
            if (!validDate) {
                action.addFieldError("targetOutcome.evaluationDate", VALIDDATEMESSAGE);
            }
        }
        if (dto.getProgressionDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getProgressionDate().getValue());
            if (!validDate) {
                action.addFieldError("targetOutcome.progressionDate", VALIDDATEMESSAGE);
            }
        }
        if (dto.getRecurrenceDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getRecurrenceDate().getValue());
            if (!validDate) {
                action.addFieldError("targetOutcome.recurrenceDate", VALIDDATEMESSAGE);
            }
        }
        if (dto.getDiseaseStatusDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getDiseaseStatusDate().getValue());
            if (!validDate) {
                action.addFieldError("targetOutcome.diseaseStatusDate", VALIDDATEMESSAGE);
            }
        }
        Date courseDate = action.getEarliestCourseDate();
        Date diagnosisDate = action.getDiagnosisDate();
        Date deathDate = action.getEarliestDeathDate();
        if (diagnosisDate != null && dto.getBestResponseDate().getValue().before(diagnosisDate)) {
            action.addFieldError("targetOutcome.bestResponseDate", "Date can't be less than Diagnosis Date" 
                    + AccrualUtil.dateToMDY(diagnosisDate));
        } 
        if (deathDate != null && dto.getBestResponseDate().getValue().after(deathDate)) {
            action.addFieldError("targetOutcome.bestResponseDate", "Date can't be greater than Death Date" 
                    + AccrualUtil.dateToMDY(deathDate));
        } 
        if (courseDate != null && dto.getBestResponseDate().getValue().before(courseDate)) {
            action.addFieldError("targetOutcome.bestResponseDate", "Date can't be less than Course Date" 
                    + AccrualUtil.dateToMDY(courseDate));
        }
        
        if (diagnosisDate != null && dto.getEvaluationDate().getValue().before(diagnosisDate)) {
            action.addFieldError("targetOutcome.evaluationDate", "Date can't be less than Diagnosis Date" 
                    + AccrualUtil.dateToMDY(diagnosisDate));
        } 
        if (deathDate != null && dto.getEvaluationDate().getValue().after(deathDate)) {
            action.addFieldError("targetOutcome.evaluationDate", "Date can't be greater than Death Date" 
                    + AccrualUtil.dateToMDY(deathDate));
        } 
        if (courseDate != null && dto.getEvaluationDate().getValue().before(courseDate)) {
            action.addFieldError("targetOutcome.evaluationDate", "Date can't be less than Course Date" 
                    + AccrualUtil.dateToMDY(courseDate));
        }
        
        if (diagnosisDate != null && dto.getProgressionDate().getValue().before(diagnosisDate)) {
            action.addFieldError("targetOutcome.progressionDate", "Date can't be less than Diagnosis Date" 
                    + AccrualUtil.dateToMDY(diagnosisDate));
        } 
        if (deathDate != null && dto.getProgressionDate().getValue().after(deathDate)) {
            action.addFieldError("targetOutcome.progressionDate", "Date can't be greater than Death Date" 
                    + AccrualUtil.dateToMDY(deathDate));
        } 
        if (courseDate != null && dto.getProgressionDate().getValue().before(courseDate)) {
            action.addFieldError("targetOutcome.progressionDate", "Date can't be less than Course Date" 
                    + AccrualUtil.dateToMDY(courseDate));
        }
        
        if (diagnosisDate != null && dto.getRecurrenceDate().getValue().before(diagnosisDate)) {
            action.addFieldError("targetOutcome.recurrenceDate", "Date can't be less than Diagnosis Date" 
                    + AccrualUtil.dateToMDY(diagnosisDate));
        } 
        if (deathDate != null && dto.getRecurrenceDate().getValue().after(deathDate)) {
            action.addFieldError("targetOutcome.recurrenceDate", "Date can't be greater than Death Date" 
                    + AccrualUtil.dateToMDY(deathDate));
        } 
        if (courseDate != null && dto.getRecurrenceDate().getValue().before(courseDate)) {
            action.addFieldError("targetOutcome.recurrenceDate", "Date can't be less than Course Date" 
                    + AccrualUtil.dateToMDY(courseDate));
        }
        
        if (diagnosisDate != null && dto.getDiseaseStatusDate().getValue().before(diagnosisDate)) {
            action.addFieldError("targetOutcome.diseaseStatusDate", "Date can't be less than Diagnosis Date" 
                    + AccrualUtil.dateToMDY(diagnosisDate));
        } 
        if (deathDate != null && dto.getDiseaseStatusDate().getValue().after(deathDate)) {
            action.addFieldError("targetOutcome.diseaseStatusDate", "Date can't be greater than Death Date" 
                    + AccrualUtil.dateToMDY(deathDate));
        } 
        if (courseDate != null && dto.getDiseaseStatusDate().getValue().before(courseDate)) {
            action.addFieldError("targetOutcome.diseaseStatusDate", "Date can't be less than Course Date" 
                    + AccrualUtil.dateToMDY(courseDate));
        }
    } 

    /**
     * Instantiates a new participant outcomes web dto.
     * 
     * @param participantOutcomesList the participant outcomes list
     * @param diseaseStatusList the disease status list
     * @param diseaseProgressionIndicatorList the disease progression indicator list
     * @param bestResponseList the best response list
     * @param webdto the webdto
     */
    public ParticipantOutcomesWebDto(
            List<PerformedObservationResultDto> participantOutcomesList,
            List<PerformedObservationResultDto> diseaseStatusList,
            List<PerformedObservationResultDto> diseaseProgressionIndicatorList,
            List<PerformedObservationResultDto> bestResponseList,
            ParticipantOutcomesWebDto webdto) {
        for (PerformedObservationResultDto por : participantOutcomesList) {
            if (por.getTypeCode().getCode().equalsIgnoreCase("Vital Status")) {
                vitalStatus = por.getResultCode();
            } else  if (por.getTypeCode().getCode().equalsIgnoreCase("Evaluable for Response")) {
                if (por.getResultIndicator().getValue().equals(true)) {
                    responseInd = CdConverter.convertStringToCd(ResponseInds.YES.getCode());
                } else if (por.getResultIndicator().getValue().equals(false)) {
                    responseInd = CdConverter.convertStringToCd(ResponseInds.NO.getCode());
                }
            } else  if (por.getTypeCode().getCode().equalsIgnoreCase("Disease Recurrence Indicator")) {
                recurrenceInd = por.getResultCode();
                if (getRecurrenceInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
                recurrenceDate = por.getResultDateRange().getLow();
                }
            }            
        }
        diseaseStatus = diseaseStatusList.get(0).getResultCode();
        bestResponse = bestResponseList.get(0).getResultCode();
        progressionInd = diseaseProgressionIndicatorList.get(0).getResultCode();
        progressionDate = webdto.getProgressionDate();
        evaluationDate = webdto.getEvaluationDate();
        diseaseStatusDate = webdto.getDiseaseStatusDate();
        assessmentType = webdto.getAssessmentType();
        progressionSite = webdto.getProgressionSite();
        bestResponseDate = webdto.getBestResponseDate();
        diseaseEvidence = webdto.getDiseaseEvidence();
        
    }

    /**
     * @return the id
     */
    public Ii getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Ii id) {
        this.id = id;
    }

    /**
     * @return the vitalStatus
     */
    @FieldExpressionValidator(expression = "vitalStatus.code != null && vitalStatus.code.length() > 0",
            message = "Please provide a Vital Status")
    public Cd getVitalStatus() {
        return vitalStatus;
    }

    /**
     * @param vitalStatus the vitalStatus to set
     */
    public void setVitalStatus(Cd vitalStatus) {
        this.vitalStatus = vitalStatus;
    }

    /**
     * @return the responseInd
     */
    @FieldExpressionValidator(expression = "responseInd.code != null && responseInd.code.length() > 0",
            message = "Please provide an Evaluable for Response")
    public Cd getResponseInd() {
        return responseInd;
    }

    /**
     * @param responseInd the responseInd to set
     */
    public void setResponseInd(Cd responseInd) {
        this.responseInd = responseInd;
    }

    /**
     * @return the diseaseStatus
     */
    @FieldExpressionValidator(expression = "diseaseStatus.code != null && diseaseStatus.code.length() > 0",
            message = "Please provide a Disease Status")
    public Cd getDiseaseStatus() {
        return diseaseStatus;
    }

    /**
     * @param diseaseStatus the diseaseStatus to set
     */
    public void setDiseaseStatus(Cd diseaseStatus) {
        this.diseaseStatus = diseaseStatus;
    }

    /**
     * @return the diseaseStatusDate
     */
    @FieldExpressionValidator(expression = "diseaseStatusDate.value != null",
            message = "Please provide a Disease Status Date")
    public Ts getDiseaseStatusDate() {
        return diseaseStatusDate;
    }

    /**
     * @param diseaseStatusDate the diseaseStatusDate to set
     */
    public void setDiseaseStatusDate(Ts diseaseStatusDate) {
        this.diseaseStatusDate = diseaseStatusDate;
    }

    /**
     * @return the assessmentType
     */
    @FieldExpressionValidator(expression = "assessmentType.code != null && assessmentType.code.length() > 0",
            message = "Please provide a Method of Disease Status Evaluation")
    public Cd getAssessmentType() {
        return assessmentType;
    }

    /**
     * @param assessmentType the assessmentType to set
     */
    public void setAssessmentType(Cd assessmentType) {
        this.assessmentType = assessmentType;
    }

    /**
     * @return the recurrenceInd
     */
    @FieldExpressionValidator(expression = "recurrenceInd.code != null && recurrenceInd.code.length() > 0",
            message = "Please provide a Disease Recurrence Indicator")
    public Cd getRecurrenceInd() {
        return recurrenceInd;
    }

    /**
     * @param recurrenceInd the recurrenceInd to set
     */
    public void setRecurrenceInd(Cd recurrenceInd) {
        this.recurrenceInd = recurrenceInd;
    }

    /**
     * @return the recurrenceDate
     */
    @FieldExpressionValidator(expression = "recurrenceDate.value != null",
            message = "Please provide a Recurrence Date")
    public Ts getRecurrenceDate() {
        return recurrenceDate;
    }

    /**
     * @param recurrenceDate the recurrenceDate to set
     */
    public void setRecurrenceDate(Ts recurrenceDate) {
        this.recurrenceDate = recurrenceDate;
    }

    /**
     * @return the progressionInd
     */
    @FieldExpressionValidator(expression = "progressionInd.code != null && progressionInd.code.length() > 0",
            message = "Please provide a Disease Progression Indicator")
    public Cd getProgressionInd() {
        return progressionInd;
    }

    /**
     * @param progressionInd the progressionInd to set
     */
    public void setProgressionInd(Cd progressionInd) {
        this.progressionInd = progressionInd;
    }

    /**
     * @return the progressionSite
     */
    @FieldExpressionValidator(expression = "progressionSite.code != null && progressionSite.code.length() > 0",
            message = "Please provide a Disease Progression Anatomic Site")
    public Cd getProgressionSite() {
        return progressionSite;
    }

    /**
     * @param progressionSite the progressionSite to set
     */
    public void setProgressionSite(Cd progressionSite) {
        this.progressionSite = progressionSite;
    }

    /**
     * @return the progressionDate
     */
    @FieldExpressionValidator(expression = "progressionDate.value != null",
            message = "Please provide a Disease Progression Date")
    public Ts getProgressionDate() {
        return progressionDate;
    }

    /**
     * @param progressionDate the progressionDate to set
     */
    public void setProgressionDate(Ts progressionDate) {
        this.progressionDate = progressionDate;
    }

    /**
     * @param evaluationDate the evaluationDate to set
     */
    public void setEvaluationDate(Ts evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    /**
     * @return the evaluationDate
     */
    @FieldExpressionValidator(expression = "evaluationDate.value != null",
            message = "Please provide an Outcomes Evaluation Date")
    public Ts getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * @return list of vital statuses
     */
    public List<PatientVitalStatus> getVitalStatuses() {
        return Arrays.asList(PatientVitalStatus.values());
    }

    /**
     * @return list of assessment types
     */
    public List<DiseaseStatusCode> getDiseaseStatuses() {
        return Arrays.asList(DiseaseStatusCode.values());
    }

    /**
     * @return list of response indicators
     */
    public List<ResponseInds> getResponseInds() {
        return Arrays.asList(ResponseInds.values());
    }

    /**
     * @return list of recurrence indicators
     */
    public List<AutopsyPerformed> getRecurrenceInds() {
        return Arrays.asList(AutopsyPerformed.values());
    }

    /**
     * @return list of progression indicators
     */
    public List<AutopsyPerformed> getProgressionInds() {
        return Arrays.asList(AutopsyPerformed.values());
    }

    /**
     * @return bestResponse
     */
    @FieldExpressionValidator(expression = "bestResponse.code != null && bestResponse.code.length() > 0",
            message = "Please select Best Response")
    public Cd getBestResponse() {
        return bestResponse;
    }

    /**
     * @param bestResponse bestResponse
     */
    public void setBestResponse(Cd bestResponse) {
        this.bestResponse = bestResponse;
    }

    /**
     * @return bestResponseDate
     */
    @FieldExpressionValidator(expression = "bestResponseDate.value != null",
            message = "Please provide an Best Response Date")
    public Ts getBestResponseDate() {
        return bestResponseDate;
    }

    /**
     * @param bestResponseDate bestResponseDate
     */
    public void setBestResponseDate(Ts bestResponseDate) {
        this.bestResponseDate = bestResponseDate;
    }

    /**
     * Gets the disease evidence.
     * @return the disease evidence
     */
    @FieldExpressionValidator(expression = "diseaseEvidence.code != null && diseaseEvidence.code.length() > 0",
            message = "Please select Evidence of Disease")
    public Cd getDiseaseEvidence() {
        return diseaseEvidence;
    }

    /**
     * Sets the disease evidence.
     * @param diseaseEvidence the new disease evidence
     */
    public void setDiseaseEvidence(Cd diseaseEvidence) {
        this.diseaseEvidence = diseaseEvidence;
    }

    /**
     * Gets the treatment plan id.
     * @return the treatment plan id
     */
    @FieldExpressionValidator(
            expression = "treatmentPlanId != null && treatmentPlanId.length() > 0", 
            message = "Please Select TreatmentPlan")
    public String getTreatmentPlanId() {
        return treatmentPlanId;
    }

    /**
     * Sets the treatment plan id.
     * @param treatmentPlanId the new treatment plan id
     */
    public void setTreatmentPlanId(String treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }
}
