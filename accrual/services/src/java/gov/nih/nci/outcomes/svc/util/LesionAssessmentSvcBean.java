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
package gov.nih.nci.outcomes.svc.util;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.enums.MeasurableEvaluableDiseaseTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Hugh Reinhart
 * @since Feb 22, 2010
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength",
    "PMD.AvoidDuplicateLiterals" })
public class LesionAssessmentSvcBean extends
        AbstractOutcomesBusSvcBean<LesionAssessmentSvcDto> {

    private static LesionAssessmentSvcBean instance = new LesionAssessmentSvcBean();

    /**
     * @return the instance
     */
    public static LesionAssessmentSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi treatment regiment identifier
     * @return list of all lesion assessments for treatment regimen
     * @throws OutcomesException exception
     */
    public List<LesionAssessmentSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<LesionAssessmentSvcDto> laSvcList = new ArrayList<LesionAssessmentSvcDto>();
        List<LesionAssessmentSvcDto> svcList = new ArrayList<LesionAssessmentSvcDto>();
        try {
            List<PerformedImagingDto> piList = cctx.getPerformedActivityService().getPerformedImagingByStudySubject(
                                                               cctx.getStudySubjectIi());
            for (PerformedImagingDto pi : piList) {
                if (pi.getNameCode() != null && pi.getNameCode().getCode() != null
                        && pi.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                    List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                    getByTargetPerformedActivity(pi.getIdentifier(), CdConverter.convertStringToCd(
                            ActivityRelationshipTypeCode.PERT.getCode()));
                    LesionAssessmentSvcDto dto = new LesionAssessmentSvcDto();
                    dto.setIdentifier(arList.get(0).getSourcePerformedActivityIdentifier());
                    svcList.add(dto);
                }
            }

            for (LesionAssessmentSvcDto dto : svcList) {
                laSvcList.add(get(dto, cctx, parentIi));
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.search().", e);
        }
        return laSvcList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LesionAssessmentSvcDto get(LesionAssessmentSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        if (dto == null) {
            return null;
        }
        LesionAssessmentSvcDto returnSvcDto = new LesionAssessmentSvcDto();
        try {
            List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(
                    dto.getIdentifier(), CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));

            arList = cctx.getActivityRelationshipService().getBySourcePerformedActivity(
                    dto.getIdentifier(), CdConverter.convertStringToCd(ActivityRelationshipTypeCode.PERT.getCode()));

            PerformedObservationDto poDto = cctx.getPerformedActivityService().getPerformedObservation(
                    arList.get(0).getSourcePerformedActivityIdentifier());
            PerformedImagingDto piDto = cctx.getPerformedActivityService().getPerformedImaging(
                    arList.get(0).getTargetPerformedActivityIdentifier());

            List<PerformedLesionDescriptionDto> pldList = cctx.getPerFormedObservationResultService().
            getPerformedLesionDescriptionByPerformedActivity(poDto.getIdentifier());

            List<PerformedImageDto> imageList = cctx.getPerFormedObservationResultService().
            getPerformedImageByPerformedActivity(piDto.getIdentifier());

            returnSvcDto = getLesionAssessmentSvcDto(poDto, pldList, imageList);

        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.get().", e);
        }
        return returnSvcDto;
    }

    private LesionAssessmentSvcDto getLesionAssessmentSvcDto(PerformedObservationDto po,
            List<PerformedLesionDescriptionDto> pldList, List<PerformedImageDto> imageList) {
        LesionAssessmentSvcDto result = new LesionAssessmentSvcDto();
        result.setLesionSite(po.getTargetSiteCode());
        List<Cd> cds = new ArrayList<Cd>();
        cds = DSetConverter.convertDsetToCdList(po.getMethodCode());
        result.setLesionMeasurementMethod(cds.get(0));
        result.setLesionNum(IiConverter.convertToIi(IntConverter.convertToString(pldList.get(0).getLesionNumber())));
        result.setLesionLongestDiameter(pldList.get(0).getLongestDiameter());
        if (pldList.get(0).getMeasurableIndicator().getValue().equals(true)
                && pldList.get(0).getEvaluableIndicator().getValue().equals(false)) {
            result.setMeasurableEvaluableDiseaseType(CdConverter.convertStringToCd(
                    MeasurableEvaluableDiseaseTypeCode.MEASURABLE.getCode()));
        } else if (pldList.get(0).getEvaluableIndicator().getValue().equals(true)
                && pldList.get(0).getMeasurableIndicator().getValue().equals(false)) {
            result.setMeasurableEvaluableDiseaseType(CdConverter.convertStringToCd(
                    MeasurableEvaluableDiseaseTypeCode.EVALUABLE.getCode()));
        } else if (pldList.get(0).getMeasurableIndicator().getValue().equals(true)
                && pldList.get(0).getEvaluableIndicator().getValue().equals(true)) {
            result.setMeasurableEvaluableDiseaseType(CdConverter.convertStringToCd(
                    MeasurableEvaluableDiseaseTypeCode.BOTH.getCode()));
        }
        result.setImageIdentifier(imageList.get(0).getImageIdentifier());
        result.setImageSeriesIdentifier(imageList.get(0).getSeriesIdentifier());
        result.setClinicalAssessmentDate(imageList.get(0).getResultDateRange().getLow());
        result.setIdentifier(po.getIdentifier());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LesionAssessmentSvcDto create(LesionAssessmentSvcDto svcDto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        businessValidation(svcDto, cctx);
        try {
            Ii studyProtocolIdentifier = cctx.getSearchTrialService().getOutcomesStudyProtocolIi();
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Lesion Assessment")));
            dto.setTargetSiteCode(svcDto.getLesionSite());
            List<Cd> cds = new ArrayList<Cd>();
            cds.add(svcDto.getLesionMeasurementMethod());
            dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
            dto.setStudyProtocolIdentifier(studyProtocolIdentifier);
            dto.setStudySubjectIdentifier(cctx.getStudySubjectIi());

            dto = cctx.getPerformedActivityService().createPerformedObservation(dto);

            PerformedLesionDescriptionDto pldDto1 = new PerformedLesionDescriptionDto();
            pldDto1.setPerformedObservationIdentifier(dto.getIdentifier());
            pldDto1.setLesionNumber(IntConverter.convertToInt(svcDto.getLesionNum().getExtension()));
            if (svcDto.getMeasurableEvaluableDiseaseType().getCode().equals(
                    MeasurableEvaluableDiseaseTypeCode.MEASURABLE.getCode())) {
                pldDto1.setMeasurableIndicator(BlConverter.convertToBl(true));
                pldDto1.setEvaluableIndicator(BlConverter.convertToBl(false));
            } else if (svcDto.getMeasurableEvaluableDiseaseType().getCode().equals(
                    MeasurableEvaluableDiseaseTypeCode.EVALUABLE.getCode())) {
                pldDto1.setEvaluableIndicator(BlConverter.convertToBl(true));
                pldDto1.setMeasurableIndicator(BlConverter.convertToBl(false));
            } else if (svcDto.getMeasurableEvaluableDiseaseType().getCode().equals(
                    MeasurableEvaluableDiseaseTypeCode.BOTH.getCode())) {
                pldDto1.setMeasurableIndicator(BlConverter.convertToBl(true));
                pldDto1.setEvaluableIndicator(BlConverter.convertToBl(true));
            }
            if (!PAUtil.isPqValueNull(svcDto.getLesionLongestDiameter())) {
                Pq longestDiameter = new Pq();
                longestDiameter.setUnit("mm");
                longestDiameter.setValue(svcDto.getLesionLongestDiameter().getValue());
                pldDto1.setLongestDiameter(longestDiameter);
            }
            Ivl<Ts> clinicalAssessmentDate = new Ivl<Ts>();
            clinicalAssessmentDate.setLow(svcDto.getClinicalAssessmentDate());
            pldDto1.setResultDateRange(clinicalAssessmentDate);
            pldDto1.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().createPerformedLesionDescription(pldDto1);

            PerformedImagingDto dto2 = new PerformedImagingDto();
            dto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Lesion Assessment")));
            dto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
            dto2.setStudySubjectIdentifier(cctx.getStudySubjectIi());

            dto2 = cctx.getPerformedActivityService().createPerformedImaging(dto2);

            PerformedImageDto piDto = new PerformedImageDto();
            piDto.setPerformedObservationIdentifier(dto2.getIdentifier());
            piDto.setImageIdentifier(svcDto.getImageIdentifier());
            piDto.setSeriesIdentifier(svcDto.getImageSeriesIdentifier());
            piDto.setResultDateRange(clinicalAssessmentDate);
            piDto.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().createPerformedImage(piDto);

            ActivityRelationshipDto arDto = new ActivityRelationshipDto();
            arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.PERT));
            arDto.setSourcePerformedActivityIdentifier(dto.getIdentifier());
            arDto.setTargetPerformedActivityIdentifier(dto2.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto);

            ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
            arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto2.setSourcePerformedActivityIdentifier(parentIi);
            arDto2.setTargetPerformedActivityIdentifier(dto.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto2);

            svcDto.setIdentifier(dto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.create().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.create().", e);
        }
        return svcDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LesionAssessmentSvcDto delete(LesionAssessmentSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        try {
            List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService()
            .getByTargetPerformedActivity(dto.getIdentifier(), CdConverter.convertStringToCd(
                    ActivityRelationshipTypeCode.COMP.getCode()));
            if (!arList.get(0).getSourcePerformedActivityIdentifier().getExtension().equals(
                    parentIi.getExtension())) {
                throw new OutcomesException("ParentIi is not same as TreatmentRegimenIdentifier"
                        + " in LesionAssessmentSvcBean.delete(). ");
            }
            arList = cctx.getActivityRelationshipService().getBySourcePerformedActivity(
                    arList.get(0).getTargetPerformedActivityIdentifier(),
                    CdConverter.convertStringToCd(ActivityRelationshipTypeCode.PERT.getCode()));
            cctx.getPerformedActivityService().delete(arList.get(0).getSourcePerformedActivityIdentifier());
            cctx.getPerformedActivityService().delete(arList.get(0).getTargetPerformedActivityIdentifier());

        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.delete().", e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LesionAssessmentSvcDto update(LesionAssessmentSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        businessValidation(dto, cctx);
        try {
            List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService()
            .getByTargetPerformedActivity(dto.getIdentifier(), CdConverter.convertStringToCd(
                    ActivityRelationshipTypeCode.COMP.getCode()));

            arList = cctx.getActivityRelationshipService().getBySourcePerformedActivity(
                    arList.get(0).getTargetPerformedActivityIdentifier(),
                    CdConverter.convertStringToCd(ActivityRelationshipTypeCode.PERT.getCode()));

            PerformedObservationDto poDto = cctx.getPerformedActivityService().getPerformedObservation(
                    arList.get(0).getSourcePerformedActivityIdentifier());
            PerformedImagingDto piDto = cctx.getPerformedActivityService().getPerformedImaging(
                    arList.get(0).getTargetPerformedActivityIdentifier());


            if (piDto.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(
                        piDto.getIdentifier(), CdConverter.convertStringToCd(
                                ActivityRelationshipTypeCode.PERT.getCode()));

                PerformedObservationDto po = cctx.getPerformedActivityService().getPerformedObservation(
                        arList.get(0).getSourcePerformedActivityIdentifier());
                if (po.getNameCode().getCode().equals(ActivityNameCode.LESION_ASSESSMENT.getCode())) {

                    po.setTargetSiteCode(dto.getLesionSite());
                    List<Cd> cds = new ArrayList<Cd>();
                    cds.add(dto.getLesionMeasurementMethod());
                    po.setMethodCode(DSetConverter.convertCdListToDSet(cds));
                    po = cctx.getPerformedActivityService().updatePerformedObservation(po);

                    arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(po.getIdentifier(),
                            CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                    PerformedObservationDto treatmentPlan = cctx.getPerformedActivityService().
                    getPerformedObservation(arList.get(0).getTargetPerformedActivityIdentifier());
                    if (treatmentPlan.getNameCode().getCode().equals(
                            ActivityNameCode.LESION_ASSESSMENT.getCode())) {
                        List<PerformedLesionDescriptionDto> pldList = cctx.getPerFormedObservationResultService().
                        getPerformedLesionDescriptionByPerformedActivity(po.getIdentifier());
                        pldList.get(0).setLesionNumber(IntConverter.convertToInt(
                                dto.getLesionNum().getExtension()));
                        if (dto.getMeasurableEvaluableDiseaseType().getCode().equals(
                                MeasurableEvaluableDiseaseTypeCode.MEASURABLE.getCode())) {
                            pldList.get(0).setMeasurableIndicator(BlConverter.convertToBl(true));
                            pldList.get(0).setEvaluableIndicator(BlConverter.convertToBl(false));
                        } else if (dto.getMeasurableEvaluableDiseaseType().getCode().equals(
                                MeasurableEvaluableDiseaseTypeCode.EVALUABLE.getCode())) {
                            pldList.get(0).setEvaluableIndicator(BlConverter.convertToBl(true));
                            pldList.get(0).setMeasurableIndicator(BlConverter.convertToBl(false));
                        } else if (dto.getMeasurableEvaluableDiseaseType().getCode().equals(
                                MeasurableEvaluableDiseaseTypeCode.BOTH.getCode())) {
                            pldList.get(0).setMeasurableIndicator(BlConverter.convertToBl(true));
                            pldList.get(0).setEvaluableIndicator(BlConverter.convertToBl(true));
                        }
                        if (!PAUtil.isPqValueNull(dto.getLesionLongestDiameter())) {
                            Pq longestDiameter = new Pq();
                            longestDiameter.setUnit("mm");
                            longestDiameter.setValue(dto.getLesionLongestDiameter().getValue());
                            pldList.get(0).setLongestDiameter(longestDiameter);
                        }
                        Ivl<Ts> clinicalAssessmentDate = new Ivl<Ts>();
                        clinicalAssessmentDate.setLow(dto.getClinicalAssessmentDate());
                        pldList.get(0).setResultDateRange(clinicalAssessmentDate);
                        cctx.getPerFormedObservationResultService().updatePerformedLesionDescription(
                                pldList.get(0));

                        List<PerformedImageDto> imageList = cctx.getPerFormedObservationResultService().
                        getPerformedImageByPerformedActivity(piDto.getIdentifier());
                        imageList.get(0).setImageIdentifier(dto.getImageIdentifier());
                        imageList.get(0).setSeriesIdentifier(dto.getImageSeriesIdentifier());
                        imageList.get(0).setResultDateRange(clinicalAssessmentDate);
                        cctx.getPerFormedObservationResultService().updatePerformedImage(imageList.get(0));
                    }
                }
            }

            arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(
                    dto.getIdentifier(), CdConverter.convertStringToCd(
                            ActivityRelationshipTypeCode.COMP.getCode()));

            for (ActivityRelationshipDto ar : arList) {
                if (ar.getTargetPerformedActivityIdentifier().getExtension().equals(
                        poDto.getIdentifier().getExtension())
                        && ar.getTypeCode().getCode().equals(ActivityRelationshipTypeCode.COMP.getCode())
                        && !ar.getSourcePerformedActivityIdentifier().getExtension().equals(
                                parentIi.getExtension())) {
                    ar.setSourcePerformedActivityIdentifier(parentIi);
                    cctx.getActivityRelationshipService().update(ar);
                }
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.create().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.create().", e);
        }
        return dto;
    }

    /**
     * Business validations.
     *
     * @param dto the dto
     * @param cctx the cctx
     *
     * @throws OutcomesException the outcomes exception
     */
    private void businessValidation(LesionAssessmentSvcDto dto, SvcContext cctx) throws OutcomesException {
        Date earliest = null;

        try {
            earliest = OutcomesUtil.getEarliestDeathDate(cctx);
        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.businessValidation().", e);
        }

        if (earliest != null && dto.getClinicalAssessmentDate().getValue().after(earliest)) {
            throw new OutcomesFieldException(getClass(), "clinicalAssessmentDate", "Date must be on or before "
                    + AccrualUtil.dateToMDY(earliest));
        }
        boolean lesionSiteExists;
        try {
            lesionSiteExists = cctx.getLookUpService().validateLookUp(dto.getLesionSite().getCode(),
                    "LESION_LOCATION_ANATOMIC_SITE", "CODE");
        } catch (RemoteException e) {
            throw new OutcomesException("Error in LesionAssessmentSvcBean.businessValidation().", e);
        }
        if (!lesionSiteExists) {
            throw new OutcomesFieldException(getClass(), "lesionSite", dto.getLesionSite().getCode()
                    + " Lesion Site is not a valid value");
        }
    }
}
