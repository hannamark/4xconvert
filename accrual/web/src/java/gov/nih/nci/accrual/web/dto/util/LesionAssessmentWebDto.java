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

import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.accrual.web.enums.ResponseInds;
import gov.nih.nci.accrual.web.util.WebUtil;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.MeasurableEvaluableDiseaseTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * The Class LesionAssessmentWebDto.
 * 
 * @author Kalpana Guthikonda
 * @since 11/20/2009
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class LesionAssessmentWebDto implements Serializable {

    private static final long serialVersionUID = -3658357689383961868L;
    private static final String NUMERICMESSAGE = "Please Enter Numeric Value";
    
    private Ii id;
    private Ii lesionNum;
    private Cd lesionSite;
    private Cd measurableEvaluableDiseaseType;
    private Cd lesionMeasurementMethod;
    private Cd contrastAgentIndicator;
    private Ii imageSeriesIdentifier;
    private Ii imageIdentifier;
    private Pq lesionLongestDiameter;
    private Ts clinicalAssessmentDate;
    private String treatmentPlanId;
    private String oldTreatmentPlanId;

   
    /**
     * Instantiates a new lesion assessment web dto.
     */
    public LesionAssessmentWebDto() {
        // default constructor
    }
    
    /**
     * Instantiates a new lesion assessment web dto.
     * @param po the po
     * @param pi the pi
     * @param pldList the pld list
     * @param imageList the image list
     * @param sourceTreatmentPlanId thetreatment plan id
     */
    public LesionAssessmentWebDto(PerformedObservationDto po,
            PerformedImagingDto pi, List<PerformedLesionDescriptionDto> pldList,
            List<PerformedImageDto> imageList, Ii sourceTreatmentPlanId) {        
        lesionSite = po.getTargetSiteCode();
        List<Cd> cds = new ArrayList<Cd>();
        cds = DSetConverter.convertDsetToCdList(po.getMethodCode());
        lesionMeasurementMethod = cds.get(0);
        lesionNum = IiConverter.convertToIi(IntConverter.convertToString(pldList.get(0).getLesionNumber()));
        lesionLongestDiameter = pldList.get(0).getLongestDiameter();
        if (pldList.get(0).getMeasurableIndicator().getValue().equals(true)
                && pldList.get(0).getEvaluableIndicator().getValue().equals(false)) {
            measurableEvaluableDiseaseType = CdConverter.convertStringToCd(
                    MeasurableEvaluableDiseaseTypeCode.MEASURABLE.getCode());
        } else if (pldList.get(0).getEvaluableIndicator().getValue().equals(true)
                && pldList.get(0).getMeasurableIndicator().getValue().equals(false)) {
            measurableEvaluableDiseaseType = CdConverter.convertStringToCd(
                    MeasurableEvaluableDiseaseTypeCode.EVALUABLE.getCode());
        } else if (pldList.get(0).getMeasurableIndicator().getValue().equals(true) 
                && pldList.get(0).getEvaluableIndicator().getValue().equals(true)) {
            measurableEvaluableDiseaseType = CdConverter.convertStringToCd(
                    MeasurableEvaluableDiseaseTypeCode.BOTH.getCode());
        }
        if (pi.getContrastAgentEnhancementIndicator().getValue().equals(true)) {
            contrastAgentIndicator = CdConverter.convertStringToCd(ResponseInds.YES.getCode());
        } else if (pi.getContrastAgentEnhancementIndicator().getValue().equals(false)) {
            contrastAgentIndicator = CdConverter.convertStringToCd(ResponseInds.NO.getCode());
        }
        imageIdentifier = imageList.get(0).getImageIdentifier();
        imageSeriesIdentifier = imageList.get(0).getSeriesIdentifier();
        clinicalAssessmentDate = imageList.get(0).getResultDateRange().getLow();
        id = po.getIdentifier();
        treatmentPlanId = sourceTreatmentPlanId.getExtension();
        oldTreatmentPlanId = sourceTreatmentPlanId.getExtension();
    }

    /**
     * Validate.
     * 
     * @param dto the dto
     * @param action the action
     * @throws RemoteException remote exception
     */
    public static void validate(LesionAssessmentWebDto dto, AbstractAccrualAction action) throws RemoteException {
        if (dto.getLesionLongestDiameter() == null || dto.getLesionLongestDiameter().getValue() == null) {
            action.addFieldError("lesionAssessment.lesionLongestDiameter.value", 
                    "Please enter Lesion Longest Diameter Value.");
        }
        if (dto.getLesionLongestDiameter() != null && dto.getLesionLongestDiameter().getValue() != null 
                && !PAUtil.isNumber(dto.getLesionLongestDiameter().getValue().toString())) {
            action.addFieldError("lesionAssessment.lesionLongestDiameter.value", NUMERICMESSAGE);
        }
        if (dto.getLesionNum().getExtension() != null && !PAUtil.isNumber(dto.getLesionNum().getExtension())) {
            action.addFieldError("lesionAssessment.lesionNum", NUMERICMESSAGE);
        }
        if (dto.getImageIdentifier().getExtension() != null 
                && !PAUtil.isNumber(dto.getImageIdentifier().getExtension())) {
            action.addFieldError("lesionAssessment.imageIdentifier", NUMERICMESSAGE);
        }
        if (dto.getImageSeriesIdentifier().getExtension() != null 
                && !PAUtil.isNumber(dto.getImageSeriesIdentifier().getExtension())) {
            action.addFieldError("lesionAssessment.imageSeriesIdentifier", NUMERICMESSAGE);
        }
        if (dto.getClinicalAssessmentDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getClinicalAssessmentDate().getValue());
            if (!validDate) {
                action.addFieldError("lesionAssessment.clinicalAssessmentDate", "Please Enter Current or Past Date.");
            }
        }
        Date earliest = action.getEarliestDeathDate();
        if (earliest != null && dto.getClinicalAssessmentDate().getValue().after(earliest)) {
            action.addFieldError("lesionAssessment.clinicalAssessmentDate", "Date must be on or before " 
                    + AccrualUtil.dateToMDY(earliest));
        }
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
     * Gets the lesion num.
     * @return the lesion num
     */
    @FieldExpressionValidator(expression = "lesionNum.extension != null && lesionNum.extension.length() > 0", 
            message = "Please enter Lesion Number")
    public Ii getLesionNum() {
        return lesionNum;
    }

    /**
     * Sets the lesion num.
     * @param lesionNum the new lesion num
     */
    public void setLesionNum(Ii lesionNum) {
        this.lesionNum = lesionNum;
    }

    /**
     * Gets the lesion site.
     * @return the lesion site
     */
    @FieldExpressionValidator(expression = "lesionSite.code != null && lesionSite.code.length() > 0", 
            message = "Please select a Lesion Site")
    public Cd getLesionSite() {
        return lesionSite;
    }

    /**
     * Sets the lesion site.
     * @param lesionSite the new lesion site
     */
    public void setLesionSite(Cd lesionSite) {
        this.lesionSite = lesionSite;
    }

    /**
     * Gets the measurable evaluable disease type.
     * @return the measurable evaluable disease type
     */
    @FieldExpressionValidator(
      expression = "measurableEvaluableDiseaseType.code != null && measurableEvaluableDiseaseType.code.length() > 0", 
            message = "Please select a Measurable Evaluable Disease Type")
    public Cd getMeasurableEvaluableDiseaseType() {
        return measurableEvaluableDiseaseType;
    }

    /**
     * Sets the measurable evaluable disease type.
     * @param measurableEvaluableDiseaseType the new measurable evaluable disease type
     */
    public void setMeasurableEvaluableDiseaseType(Cd measurableEvaluableDiseaseType) {
        this.measurableEvaluableDiseaseType = measurableEvaluableDiseaseType;
    }

    /**
     * Gets the lesion measurement method.
     * @return the lesion measurement method
     */
    @FieldExpressionValidator(
            expression = "lesionMeasurementMethod.code != null && lesionMeasurementMethod.code.length() > 0", 
            message = "Please select a Lesion Measurement Method")
    public Cd getLesionMeasurementMethod() {
        return lesionMeasurementMethod;
    }

    /**
     * Sets the lesion measurement method.
     * @param lesionMeasurementMethod the new lesion measurement method
     */
    public void setLesionMeasurementMethod(Cd lesionMeasurementMethod) {
        this.lesionMeasurementMethod = lesionMeasurementMethod;
    }

    /**
     * Gets the contrast agent indicator.
     * @return the contrast agent indicator
     */
    @FieldExpressionValidator(
            expression = "contrastAgentIndicator.code != null && contrastAgentIndicator.code.length() > 0", 
            message = "Please select a Contrast Agent Indicator")
    public Cd getContrastAgentIndicator() {
        return contrastAgentIndicator;
    }

    /**
     * Sets the contrast agent indicator.
     * @param contrastAgentIndicator the new contrast agent indicator
     */
    public void setContrastAgentIndicator(Cd contrastAgentIndicator) {
        this.contrastAgentIndicator = contrastAgentIndicator;
    }

    /**
     * Gets the image series identifier.
     * @return the image series identifier
     */
    @FieldExpressionValidator(
            expression = "imageSeriesIdentifier.extension != null && imageSeriesIdentifier.extension.length() > 0", 
            message = "Please enter Image Series Identifier")
    public Ii getImageSeriesIdentifier() {
        return imageSeriesIdentifier;
    }

    /**
     * Sets the image series identifier.
     * @param imageSeriesIdentifier the new image series identifier
     */
    public void setImageSeriesIdentifier(Ii imageSeriesIdentifier) {
        this.imageSeriesIdentifier = imageSeriesIdentifier;
    }

    /**
     * Gets the image identifier.
     * @return the image identifier
     */
    @FieldExpressionValidator(
            expression = "imageIdentifier.extension != null && imageIdentifier.extension.length() > 0", 
            message = "Please enter Image Identifier")
    public Ii getImageIdentifier() {
        return imageIdentifier;
    }

    /**
     * Sets the image identifier.
     * @param imageIdentifier the new image identifier
     */
    public void setImageIdentifier(Ii imageIdentifier) {
        this.imageIdentifier = imageIdentifier;
    }

    /**
     * Gets the lesion longest diameter.
     * @return the lesion longest diameter
     */
    public Pq getLesionLongestDiameter() {
        return lesionLongestDiameter;
    }

    /**
     * Sets the lesion longest diameter.
     * @param lesionLongestDiameter the new lesion longest diameter
     */
    public void setLesionLongestDiameter(Pq lesionLongestDiameter) {
        this.lesionLongestDiameter = lesionLongestDiameter;
    }

    /**
     * Gets the clinical assessment date.
     * @return the clinical assessment date
     */
    @FieldExpressionValidator(expression = "clinicalAssessmentDate.value != null",
            message = "Please provide a Clinical Assessment Date")
    public Ts getClinicalAssessmentDate() {
        return clinicalAssessmentDate;
    }

    /**
     * Sets the clinical assessment date.
     * @param clinicalAssessmentDate the new clinical assessment date
     */
    public void setClinicalAssessmentDate(Ts clinicalAssessmentDate) {
        this.clinicalAssessmentDate = clinicalAssessmentDate;
    }
    
    /**
     * @return list of response indicators
     */
    public List<ResponseInds> getContrastAgentInds() {
        return Arrays.asList(ResponseInds.values());
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
    /**
     * Gets the old treatment plan id.
     * @return the old treatment plan id
     */
    public String getOldTreatmentPlanId() {
        return oldTreatmentPlanId;
    }

    /**
     * Sets the old treatment plan id.
     * @param oldTreatmentPlanId the new old treatment plan id
     */
    public void setOldTreatmentPlanId(String oldTreatmentPlanId) {
        this.oldTreatmentPlanId = oldTreatmentPlanId;
    }
}
