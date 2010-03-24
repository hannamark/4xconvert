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

package gov.nih.nci.accrual.outweb.dto.util;

import gov.nih.nci.accrual.outweb.enums.AutopsyPerformed;
import gov.nih.nci.accrual.outweb.enums.ResponseInds;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.AbstractDiseaseEvaluationDto;
import gov.nih.nci.outcomes.svc.dto.DiseaseEvaluationSvcDto;
import gov.nih.nci.pa.enums.DiseaseStatusCode;
import gov.nih.nci.pa.enums.PatientVitalStatus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * @author lhebel
 * @since 10/28/2009
 */
@SuppressWarnings({"PMD.UselessOverridingMethod", "PMD.CyclomaticComplexity", "PMD.ExcessiveParameterList", 
    "PMD.TooManyFields", "PMD.NPathComplexity", "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength" })
public class ParticipantOutcomesWebDto extends AbstractDiseaseEvaluationDto implements Serializable {

    private static final long serialVersionUID = 1839478941711659167L;
    private String treatmentPlanId;
    
    /**
     * Instantiates a new participant outcomes web dto.
     */
    public ParticipantOutcomesWebDto() {
        // default constructor
    } 
    
    /**
     * Instantiates a new participant outcomes web dto.
     * 
     * @param svcDto the svc dto
     */
    public ParticipantOutcomesWebDto(AbstractDiseaseEvaluationDto svcDto) {
        setAssessmentType(svcDto.getAssessmentType());
        setBestResponse(svcDto.getBestResponse());
        setBestResponseDate(svcDto.getBestResponseDate());
        setDiseaseStatus(svcDto.getDiseaseStatus());
        setDiseaseStatusDate(svcDto.getDiseaseStatusDate());
        setEvaluationDate(svcDto.getEvaluationDate());
        setResponseInd(svcDto.getResponseInd());
        setVitalStatus(svcDto.getVitalStatus());        
    }
    
    /**
     * @param svcField service field
     * @return field name in jsp
     */
    public static String svcFieldToWebField(String svcField) {
        String result = svcField;
        if ("vitalStatus".equals(result)) {
            result = "vitalStatus";
        }
        if ("responseInd".equals(result)) {
            result = "responseInd";
        }
        if ("diseaseStatus".equals(result)) {
            result = "diseaseStatus";
        }
        if ("assessmentType".equals(result)) {
            result = "assessmentType";
        }
        if ("diseaseStatusDate".equals(result)) {
            result = "diseaseStatusDate";
        }
        if ("evaluationDate".equals(result)) {
            result = "evaluationDate";
        }
        if ("bestResponse".equals(result)) {
            result = "bestResponse";
        }
        if ("bestResponseDate".equals(result)) {
            result = "bestResponseDate";
        }
        return "targetOutcome." + result;
    } 

    /**
     * @return the vitalStatus
     */
    @Override
    @FieldExpressionValidator(expression = "vitalStatus.code != null && vitalStatus.code.length() > 0",
            message = "Please provide a Vital Status")
    public Cd getVitalStatus() {
        return super.getVitalStatus();
    }

    /**
     * @return the responseInd
     */
    @Override
    @FieldExpressionValidator(expression = "responseInd.code != null && responseInd.code.length() > 0",
            message = "Please provide an Evaluable for Response")
    public Cd getResponseInd() {
        return super.getResponseInd();
    }

    /**
     * @return the diseaseStatus
     */
    @Override
    @FieldExpressionValidator(expression = "diseaseStatus.code != null && diseaseStatus.code.length() > 0",
            message = "Please provide a Disease Status")
    public Cd getDiseaseStatus() {
        return super.getDiseaseStatus();
    }

    /**
     * @return the diseaseStatusDate
     */
    @Override
    @FieldExpressionValidator(expression = "diseaseStatusDate.value != null",
            message = "Please provide a Disease Status Date")
    public Ts getDiseaseStatusDate() {
        return super.getDiseaseStatusDate();
    }

    /**
     * @return the assessmentType
     */
    @Override
    @FieldExpressionValidator(expression = "assessmentType.code != null && assessmentType.code.length() > 0",
            message = "Please provide a Method of Disease Status Evaluation")
    public Cd getAssessmentType() {
        return super.getAssessmentType();
    }

    /**
     * @return the evaluationDate
     */
    @Override
    @FieldExpressionValidator(expression = "evaluationDate.value != null",
            message = "Please provide an Outcomes Evaluation Date")
    public Ts getEvaluationDate() {
        return super.getEvaluationDate();
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
    @Override
    @FieldExpressionValidator(expression = "bestResponse.code != null && bestResponse.code.length() > 0",
            message = "Please select Best Response")
    public Cd getBestResponse() {
        return super.getBestResponse();
    }

    /**
     * @return bestResponseDate
     */
    @Override
    @FieldExpressionValidator(expression = "bestResponseDate.value != null",
            message = "Please provide an Best Response Date")
    public Ts getBestResponseDate() {
        return super.getBestResponseDate();
    }

    /**
     * Sets the treatment plan id.
     * @param treatmentPlanId the new treatment plan id
     */
    public void setTreatmentPlanId(String treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
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
     * Gets the svc dto.
     * 
     * @return the svc dto
     */
    public DiseaseEvaluationSvcDto getSvcDto() {
        DiseaseEvaluationSvcDto svcDto = new DiseaseEvaluationSvcDto();
        svcDto.setAssessmentType(getAssessmentType());
        svcDto.setBestResponse(getBestResponse());
        svcDto.setBestResponseDate(getBestResponseDate());
        svcDto.setDiseaseStatus(getDiseaseStatus());
        svcDto.setDiseaseStatusDate(getDiseaseStatusDate());
        svcDto.setEvaluationDate(getEvaluationDate());
        svcDto.setResponseInd(getResponseInd());
        svcDto.setVitalStatus(getVitalStatus());
        return svcDto;
    }
}
