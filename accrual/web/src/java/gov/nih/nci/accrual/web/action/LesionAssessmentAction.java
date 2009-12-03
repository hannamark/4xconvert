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
package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.web.dto.util.LesionAssessmentWebDto;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.enums.MeasurableEvaluableDiseaseTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * The Class LesionAssessmentAction.
 *
 * @author Kalpana Guthikonda
 * @since 11/20/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength",
    "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods", "PMD.TooManyFields" , 
        "PMD.AvoidDeeplyNestedIfStmts" })
public class LesionAssessmentAction extends AbstractListEditAccrualAction<LesionAssessmentWebDto> {

    private static final long serialVersionUID = 1L;
    private LesionAssessmentWebDto lesionAssessment = new LesionAssessmentWebDto(); 
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        try {
            loadTreatmentPlans();
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.execute();
        }        
        return super.execute();
    }

   
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        setDisplayTagList(new ArrayList<LesionAssessmentWebDto>());
        try {

            List<PerformedImagingDto> piList = performedActivitySvc.getPerformedImagingByStudySubject(
                                                                getParticipantIi());
            for (PerformedImagingDto pi : piList) {
                if (pi.getNameCode() != null && pi.getNameCode().getCode() != null
                        && pi.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                    List<ActivityRelationshipDto> arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            pi.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.PERT));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(
                            arList.get(0).getSourcePerformedActivityIdentifier());
                    if (po.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                        arList = activityRelationshipSvc.getByTargetPerformedActivity(po.getIdentifier(), 
                                CdConverter.convertStringToCd(AccrualConstants.COMP));
                        PerformedObservationDto treatmentPlan = performedActivitySvc.getPerformedObservation(
                                          arList.get(0).getTargetPerformedActivityIdentifier());
                        Ii sourceTreatmentPlanId = arList.get(0).getSourcePerformedActivityIdentifier();
                        if (treatmentPlan.getNameCode().getCode().equals(
                                ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                            List<PerformedLesionDescriptionDto> pldList = performedObservationResultSvc.
                            getPerformedLesionDescriptionByPerformedActivity(po.getIdentifier());
                            List<PerformedImageDto> imageList = performedObservationResultSvc.
                            getPerformedImageByPerformedActivity(pi.getIdentifier());
                            getDisplayTagList().add(
                                    new LesionAssessmentWebDto(po, pi, pldList, imageList, sourceTreatmentPlanId));
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws RemoteException {
        List<ActivityRelationshipDto> arList = activityRelationshipSvc.getByTargetPerformedActivity(
        IiConverter.convertToIi(getSelectedRowIdentifier()), CdConverter.convertStringToCd(AccrualConstants.COMP));
        arList = activityRelationshipSvc.getBySourcePerformedActivity(
         arList.get(0).getTargetPerformedActivityIdentifier(), CdConverter.convertStringToCd(AccrualConstants.PERT));
        
        performedActivitySvc.delete(arList.get(0).getSourcePerformedActivityIdentifier());
        performedActivitySvc.delete(arList.get(0).getTargetPerformedActivityIdentifier());        
        return super.delete();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        lesionAssessment = null;
        try {
            loadDisplayList();
            for (LesionAssessmentWebDto la : getDisplayTagList()) {
                if (la.getId().getExtension().equals(getSelectedRowIdentifier())) {
                    lesionAssessment = la;
                }
            }
        } catch (Exception e) {
            lesionAssessment = null;
            LOG.error("Error in LesionAssessmentAction.update().", e);
        }
        if (lesionAssessment == null) {
            addActionError("Error retrieving lesionAssessment info for update.");
            return execute();
        }
        return super.update();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String add() {
        LesionAssessmentWebDto.validate(lesionAssessment, this);
        if (hasActionErrors() || hasFieldErrors()) {
            setCurrentAction(CA_CREATE);
            return INPUT;
        }
        try {
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Lesion Assessment")));
            dto.setTargetSiteCode(lesionAssessment.getLesionSite()); 
            List<Cd> cds = new ArrayList<Cd>();
            cds.add(lesionAssessment.getLesionMeasurementMethod());
            dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
            dto.setStudyProtocolIdentifier(getSpIi());
            dto.setStudySubjectIdentifier(getParticipantIi());

            dto = performedActivitySvc.createPerformedObservation(dto);

            PerformedLesionDescriptionDto pldDto1 = new PerformedLesionDescriptionDto();
            pldDto1.setPerformedObservationIdentifier(dto.getIdentifier());
            pldDto1.setLesionNumber(IntConverter.convertToInt(lesionAssessment.getLesionNum().getExtension()));
            if (lesionAssessment.getMeasurableEvaluableDiseaseType().getCode().equals(
                    MeasurableEvaluableDiseaseTypeCode.MEASURABLE.getCode())) {
                pldDto1.setMeasurableIndicator(BlConverter.convertToBl(true));
                pldDto1.setEvaluableIndicator(BlConverter.convertToBl(false));
            } else if (lesionAssessment.getMeasurableEvaluableDiseaseType().getCode().equals(
                    MeasurableEvaluableDiseaseTypeCode.EVALUABLE.getCode())) {
                pldDto1.setEvaluableIndicator(BlConverter.convertToBl(true));
                pldDto1.setMeasurableIndicator(BlConverter.convertToBl(false));
            } else if (lesionAssessment.getMeasurableEvaluableDiseaseType().getCode().equals(
                    MeasurableEvaluableDiseaseTypeCode.BOTH.getCode())) {
                pldDto1.setMeasurableIndicator(BlConverter.convertToBl(true));
                pldDto1.setEvaluableIndicator(BlConverter.convertToBl(true));
            }
            Pq longestDiameter = new Pq();
            longestDiameter.setUnit("mm");
            longestDiameter.setValue(lesionAssessment.getLesionLongestDiameter().getValue());
            pldDto1.setLongestDiameter(longestDiameter);
            Ivl<Ts> clinicalAssessmentDate = new Ivl<Ts>();
            clinicalAssessmentDate.setLow(lesionAssessment.getClinicalAssessmentDate());
            pldDto1.setResultDateRange(clinicalAssessmentDate);
            pldDto1.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.createPerformedLesionDescription(pldDto1);

            PerformedImagingDto dto2 = new PerformedImagingDto();
            dto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Lesion Assessment")));
            if (lesionAssessment.getContrastAgentIndicator().getCode().equalsIgnoreCase("Yes")) {
                dto2.setContrastAgentEnhancementIndicator(BlConverter.convertToBl(true));
            } else if (lesionAssessment.getContrastAgentIndicator().getCode().equalsIgnoreCase("No")) {
                dto2.setContrastAgentEnhancementIndicator(BlConverter.convertToBl(false));
            }
            dto2.setStudyProtocolIdentifier(getSpIi());
            dto2.setStudySubjectIdentifier(getParticipantIi());

            dto2 = performedActivitySvc.createPerformedImaging(dto2);

            PerformedImageDto piDto = new PerformedImageDto();
            piDto.setPerformedObservationIdentifier(dto2.getIdentifier());
            piDto.setImageIdentifier(lesionAssessment.getImageIdentifier());
            piDto.setSeriesIdentifier(lesionAssessment.getImageSeriesIdentifier());
            piDto.setResultDateRange(clinicalAssessmentDate);
            piDto.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.createPerformedImage(piDto);

            ActivityRelationshipDto arDto = new ActivityRelationshipDto();
            arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.PERT)));
            arDto.setSourcePerformedActivityIdentifier(dto.getIdentifier());
            arDto.setTargetPerformedActivityIdentifier(dto2.getIdentifier());
            activityRelationshipSvc.create(arDto);

            ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
            arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.COMP)));
            arDto2.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(lesionAssessment.getTreatmentPlanId()));
            arDto2.setTargetPerformedActivityIdentifier(dto.getIdentifier());
            activityRelationshipSvc.create(arDto2);

            return super.add();
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        } catch (DataFormatException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws RemoteException {
        LesionAssessmentWebDto.validate(lesionAssessment, this);
        if (hasActionErrors() || hasFieldErrors()) {
            setCurrentAction(CA_CREATE);
            return INPUT;
        }
        try {                                    
            List<ActivityRelationshipDto> arList = activityRelationshipSvc.getByTargetPerformedActivity(
            IiConverter.convertToIi(getSelectedRowIdentifier()), CdConverter.convertStringToCd(AccrualConstants.COMP));
            arList = activityRelationshipSvc.getBySourcePerformedActivity(
            arList.get(0).getTargetPerformedActivityIdentifier(), CdConverter.convertStringToCd(AccrualConstants.PERT));

            PerformedObservationDto poDto = performedActivitySvc.getPerformedObservation(
                    arList.get(0).getSourcePerformedActivityIdentifier());
            PerformedImagingDto piDto = performedActivitySvc.getPerformedImaging(
                    arList.get(0).getTargetPerformedActivityIdentifier());      


            if (piDto.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                arList = activityRelationshipSvc.getByTargetPerformedActivity(
                        piDto.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.PERT));
                if (lesionAssessment.getContrastAgentIndicator().getCode().equalsIgnoreCase("Yes")) {
                    piDto.setContrastAgentEnhancementIndicator(BlConverter.convertToBl(true));
                } else if (lesionAssessment.getContrastAgentIndicator().getCode().equalsIgnoreCase("No")) {
                    piDto.setContrastAgentEnhancementIndicator(BlConverter.convertToBl(false));
                }

                piDto = performedActivitySvc.updatePerformedImaging(piDto);

                PerformedObservationDto po = performedActivitySvc.getPerformedObservation(
                        arList.get(0).getSourcePerformedActivityIdentifier());
                if (po.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {

                    po.setTargetSiteCode(lesionAssessment.getLesionSite()); 
                    List<Cd> cds = new ArrayList<Cd>();
                    cds.add(lesionAssessment.getLesionMeasurementMethod());
                    po.setMethodCode(DSetConverter.convertCdListToDSet(cds));
                    po = performedActivitySvc.updatePerformedObservation(po);

                    arList = activityRelationshipSvc.getByTargetPerformedActivity(po.getIdentifier(), 
                            CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto treatmentPlan = performedActivitySvc.getPerformedObservation(
                            arList.get(0).getTargetPerformedActivityIdentifier());
                    if (treatmentPlan.getNameCode().getCode().equals(
                            ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                        List<PerformedLesionDescriptionDto> pldList = performedObservationResultSvc.
                        getPerformedLesionDescriptionByPerformedActivity(po.getIdentifier());
                        pldList.get(0).setLesionNumber(IntConverter.convertToInt(lesionAssessment
                                .getLesionNum().getExtension()));
                        if (lesionAssessment.getMeasurableEvaluableDiseaseType().getCode().equals(
                                MeasurableEvaluableDiseaseTypeCode.MEASURABLE.getCode())) {
                            pldList.get(0).setMeasurableIndicator(BlConverter.convertToBl(true));
                            pldList.get(0).setEvaluableIndicator(BlConverter.convertToBl(false));
                        } else if (lesionAssessment.getMeasurableEvaluableDiseaseType().getCode().equals(
                                MeasurableEvaluableDiseaseTypeCode.EVALUABLE.getCode())) {
                            pldList.get(0).setEvaluableIndicator(BlConverter.convertToBl(true));
                            pldList.get(0).setMeasurableIndicator(BlConverter.convertToBl(false));
                        } else if (lesionAssessment.getMeasurableEvaluableDiseaseType().getCode().equals(
                                MeasurableEvaluableDiseaseTypeCode.BOTH.getCode())) {
                            pldList.get(0).setMeasurableIndicator(BlConverter.convertToBl(true));
                            pldList.get(0).setEvaluableIndicator(BlConverter.convertToBl(true));
                        }
                        Pq longestDiameter = new Pq();
                        longestDiameter.setUnit("mm");
                        longestDiameter.setValue(lesionAssessment.getLesionLongestDiameter().getValue());
                        pldList.get(0).setLongestDiameter(longestDiameter);
                        Ivl<Ts> clinicalAssessmentDate = new Ivl<Ts>();
                        clinicalAssessmentDate.setLow(lesionAssessment.getClinicalAssessmentDate());
                        pldList.get(0).setResultDateRange(clinicalAssessmentDate);
                        performedObservationResultSvc.updatePerformedLesionDescription(pldList.get(0));

                        List<PerformedImageDto> imageList = performedObservationResultSvc.
                        getPerformedImageByPerformedActivity(piDto.getIdentifier());
                        imageList.get(0).setImageIdentifier(lesionAssessment.getImageIdentifier());
                        imageList.get(0).setSeriesIdentifier(lesionAssessment.getImageSeriesIdentifier());
                        imageList.get(0).setResultDateRange(clinicalAssessmentDate);
                        performedObservationResultSvc.updatePerformedImage(imageList.get(0));
                    } 
                }
            }

            if (!lesionAssessment.getTreatmentPlanId().equals(lesionAssessment.getOldTreatmentPlanId())) {
                arList = activityRelationshipSvc.getBySourcePerformedActivity(IiConverter.convertToIi(
                        lesionAssessment.getOldTreatmentPlanId()), 
                        CdConverter.convertStringToCd(AccrualConstants.COMP));
                for (ActivityRelationshipDto ar : arList) {
                    if (ar.getTargetPerformedActivityIdentifier().getExtension().equals(poDto.getIdentifier()
                            .getExtension()) && ar.getTypeCode().getCode().equals(AccrualConstants.COMP)) {
                        ar.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(
                                lesionAssessment.getTreatmentPlanId()));
                        activityRelationshipSvc.update(ar);
                    }
                }
            }


        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return INPUT;
        } catch (DataFormatException e) {
            addActionError(e.getLocalizedMessage());
            return INPUT;
        }
        return super.edit();
    }

    /**
     * Gets the lesion assessment.
     * @return the lesion assessment
     */
    @VisitorFieldValidator(message = "> ")
    public LesionAssessmentWebDto getLesionAssessment() {
        return lesionAssessment;
    }

    /**
     * Sets the lesion assessment.
     * @param lesionAssessment the new lesion assessment
     */
    public void setLesionAssessment(LesionAssessmentWebDto lesionAssessment) {
        this.lesionAssessment = lesionAssessment;
    }

}
