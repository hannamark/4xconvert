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
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.outweb.enums.ResponseInds;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.DiseaseEvaluationSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;

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
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class DiseaseEvaluationSvcBean extends
        AbstractOutcomesBusSvcBean<DiseaseEvaluationSvcDto> {

    private static DiseaseEvaluationSvcBean instance = new DiseaseEvaluationSvcBean();

    /**
     * @return the instance
     */
    public static DiseaseEvaluationSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi treatment regiment identifier
     * @return list of all disease evaluations for treatment regimen
     * @throws OutcomesException exception
     */
    public List< DiseaseEvaluationSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<DiseaseEvaluationSvcDto> dEvaluationSvcList = new ArrayList<DiseaseEvaluationSvcDto>();
        try {
            List<PerformedObservationDto> poList = cctx.getPerformedActivityService().
            getPerformedObservationByStudySubject(cctx.getStudySubjectIi());
            List<PerformedObservationResultDto> participantOutcomesList = null;
            List<PerformedObservationResultDto> diseaseStatusList = null;
            List<PerformedObservationResultDto> bestResponseList = null;
            List<ActivityRelationshipDto> arList = null;
            DiseaseEvaluationSvcDto svcDto = new DiseaseEvaluationSvcDto();
            for (PerformedObservationDto poBean : poList) {
                if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.PARTICIPANT_OUTCOMES.getCode())) {
                    arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(
                   poBean.getIdentifier(), CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                    PerformedObservationDto po = cctx.getPerformedActivityService().
                    getPerformedObservation(arList.get(0).getTargetPerformedActivityIdentifier());
                    svcDto.setEvaluationDate(po.getActualDateRange().getLow());
                    svcDto.setIdentifier(po.getIdentifier());
                    participantOutcomesList = cctx.getPerFormedObservationResultService().
                                      getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.DISEASE_STATUS.getCode())) {
                    arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(
                   poBean.getIdentifier(), CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                    PerformedObservationDto po = cctx.getPerformedActivityService().
                    getPerformedObservation(arList.get(0).getTargetPerformedActivityIdentifier());
                    svcDto.setDiseaseStatusDate(po.getActualDateRange().getLow());
                    List<Cd> cds = new ArrayList<Cd>();
                    cds = DSetConverter.convertDsetToCdList(po.getMethodCode());
                    svcDto.setAssessmentType(cds.get(0));
                    diseaseStatusList = cctx.getPerFormedObservationResultService().
                    getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.BEST_RESPONSE.getCode())) {
                    arList = cctx.getActivityRelationshipService().getByTargetPerformedActivity(
                   poBean.getIdentifier(), CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                    PerformedObservationDto po = cctx.getPerformedActivityService().
                    getPerformedObservation(arList.get(0).getTargetPerformedActivityIdentifier());
                    svcDto.setBestResponseDate(po.getActualDateRange().getLow());
                    bestResponseList = cctx.getPerFormedObservationResultService().
                    getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                }

                if (participantOutcomesList != null && !participantOutcomesList.isEmpty()
                        && diseaseStatusList != null && !diseaseStatusList.isEmpty()
                        && bestResponseList != null && !bestResponseList.isEmpty()) {
                    for (PerformedObservationResultDto por : participantOutcomesList) {
                        if (por.getTypeCode().getCode().equalsIgnoreCase("Vital Status")) {
                            svcDto.setVitalStatus(por.getResultCode());
                        } else  if (por.getTypeCode().getCode().equalsIgnoreCase("Evaluable for Response")) {
                            if (por.getResultIndicator().getValue().equals(true)) {
                                svcDto.setResponseInd(CdConverter.convertStringToCd(ResponseInds.YES.getCode()));
                            } else if (por.getResultIndicator().getValue().equals(false)) {
                                svcDto.setResponseInd(CdConverter.convertStringToCd(ResponseInds.NO.getCode()));
                            }
                        }
                    }
                    svcDto.setDiseaseStatus(diseaseStatusList.get(0).getResultCode());
                    svcDto.setBestResponse(bestResponseList.get(0).getResultCode());

                    dEvaluationSvcList.add(svcDto);

                    svcDto = new DiseaseEvaluationSvcDto();
                    participantOutcomesList = null;
                    diseaseStatusList = null;
                    bestResponseList = null;
                }

            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DiseaseEvaluationSvcBean.search().", e);
        }
        return dEvaluationSvcList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiseaseEvaluationSvcDto get(DiseaseEvaluationSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        if (dto == null) {
            return null;
        }
        DiseaseEvaluationSvcDto result = null;
        List<DiseaseEvaluationSvcDto> dEvaluationSvcList = search(cctx, parentIi);
        for (DiseaseEvaluationSvcDto svcDto : dEvaluationSvcList) {
            if (dto.getIdentifier().getExtension().equals(svcDto.getIdentifier().getExtension())) {
                result = svcDto;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DiseaseEvaluationSvcDto create(DiseaseEvaluationSvcDto svcDto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        businessValidation(svcDto, cctx);
        try {
            Ii studyProtocolIdentifier = cctx.getSearchTrialService().getOutcomesStudyProtocolIi();
            PerformedObservationDto dto = new PerformedObservationDto();
            Ivl<Ts> evalutionDate = new Ivl<Ts>();
            evalutionDate.setLow(svcDto.getEvaluationDate());
            dto.setActualDateRange(evalutionDate);
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Participant Outcomes")));
            dto.setStudyProtocolIdentifier(studyProtocolIdentifier);
            dto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            dto = cctx.getPerformedActivityService().createPerformedObservation(dto);

            createPerformedObservationResult(dto, svcDto, cctx, studyProtocolIdentifier, parentIi);

            createDiseaseStatus(svcDto, cctx, cctx.getStudySubjectIi(), studyProtocolIdentifier, parentIi);

            PerformedObservationDto dto5 = new PerformedObservationDto();
            Ivl<Ts> bestResponseDate = new Ivl<Ts>();
            bestResponseDate.setLow(svcDto.getBestResponseDate());
            dto5.setActualDateRange(bestResponseDate);
            dto5.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Best Response")));
            dto5.setStudyProtocolIdentifier(studyProtocolIdentifier);
            dto5.setStudySubjectIdentifier(cctx.getStudySubjectIi());

            dto5 = cctx.getPerformedActivityService().createPerformedObservation(dto5);

            PerformedObservationResultDto porDto7 = new PerformedObservationResultDto();
            porDto7.setPerformedObservationIdentifier(dto5.getIdentifier());
            porDto7.setResultCode(svcDto.getBestResponse());
            porDto7.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto7);

            ActivityRelationshipDto arDto5 = new ActivityRelationshipDto();
            arDto5.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto5.setSourcePerformedActivityIdentifier(parentIi);
            arDto5.setTargetPerformedActivityIdentifier(dto5.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto5);
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.create().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.create().", e);
        }
        return svcDto;
    }
    private void createDiseaseStatus(DiseaseEvaluationSvcDto svcDto, SvcContext cctx,
            Ii studySubjectIi, Ii studyProtocolIdentifier, Ii parentIi) throws RemoteException, DataFormatException {
        PerformedObservationDto dto2 = new PerformedObservationDto();
        Ivl<Ts> diseaseStatusDate = new Ivl<Ts>();
        diseaseStatusDate.setLow(svcDto.getDiseaseStatusDate());
        dto2.setActualDateRange(diseaseStatusDate);
        dto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Disease Status")));
        List<Cd> cds = new ArrayList<Cd>();
        cds.add(svcDto.getAssessmentType());
        dto2.setMethodCode(DSetConverter.convertCdListToDSet(cds));
        dto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
        dto2.setStudySubjectIdentifier(studySubjectIi);

        dto2 = cctx.getPerformedActivityService().createPerformedObservation(dto2);

        PerformedObservationResultDto porDto4 = new PerformedObservationResultDto();
        porDto4.setPerformedObservationIdentifier(dto2.getIdentifier());
        porDto4.setResultCode(svcDto.getDiseaseStatus());
        porDto4.setStudyProtocolIdentifier(studyProtocolIdentifier);
        cctx.getPerFormedObservationResultService().create(porDto4);

        ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
        arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
        arDto2.setSourcePerformedActivityIdentifier(parentIi);
        arDto2.setTargetPerformedActivityIdentifier(dto2.getIdentifier());
        cctx.getActivityRelationshipService().create(arDto2);
    }

    private void createPerformedObservationResult(PerformedObservationDto dto, DiseaseEvaluationSvcDto svcDto,
            SvcContext cctx, Ii studyProtocolIdentifier, Ii parentIi) throws RemoteException {
        PerformedObservationResultDto porDto1 = new PerformedObservationResultDto();
        porDto1.setPerformedObservationIdentifier(dto.getIdentifier());
        porDto1.setResultCode(svcDto.getVitalStatus());
        porDto1.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.getByCode("Vital Status")));
        porDto1.setStudyProtocolIdentifier(studyProtocolIdentifier);
        cctx.getPerFormedObservationResultService().create(porDto1);

        PerformedObservationResultDto porDto2 = new PerformedObservationResultDto();
        porDto2.setPerformedObservationIdentifier(dto.getIdentifier());
        if (svcDto.getResponseInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
            porDto2.setResultIndicator(BlConverter.convertToBl(true));
        } else if (svcDto.getResponseInd().getCode().equalsIgnoreCase(ResponseInds.NO.getCode())) {
            porDto2.setResultIndicator(BlConverter.convertToBl(false));
        }
        porDto2.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                                        getByCode("Evaluable for Response")));
        porDto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
        cctx.getPerFormedObservationResultService().create(porDto2);

        ActivityRelationshipDto arDto1 = new ActivityRelationshipDto();
        arDto1.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
        arDto1.setSourcePerformedActivityIdentifier(parentIi);
        arDto1.setTargetPerformedActivityIdentifier(dto.getIdentifier());
        cctx.getActivityRelationshipService().create(arDto1);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected DiseaseEvaluationSvcDto delete(DiseaseEvaluationSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        throw new OutcomesException("Delete method is not implemented for DiseaseEvaluationSvcBean");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DiseaseEvaluationSvcDto update(DiseaseEvaluationSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        throw new OutcomesException("Update method is not implemented for DiseaseEvaluationSvcBean");
    }

    /**
     * Business validations.
     *
     * @param dto the dto
     * @param cctx the cctx
     *
     * @throws OutcomesException the outcomes exception
     */
    private void businessValidation(DiseaseEvaluationSvcDto dto, SvcContext cctx) throws OutcomesException {
        Date courseDate = null;
        Date diagnosisDate = null;
        Date deathDate = null;

        try {
            courseDate = OutcomesUtil.getEarliestCourseDate(cctx);
            diagnosisDate = OutcomesUtil.getDiagnosisDate(cctx);
            deathDate = OutcomesUtil.getEarliestDeathDate(cctx);
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DiseaseEvaluationSvcBean.businessValidation().", e);
        }

        if (diagnosisDate != null && dto.getBestResponseDate().getValue().before(diagnosisDate)) {
            throw new OutcomesFieldException(getClass(), "bestResponseDate", "Date can't be less than Diagnosis Date "
                    + AccrualUtil.dateToMDY(diagnosisDate));
        }
        if (deathDate != null && dto.getBestResponseDate().getValue().after(deathDate)) {
            throw new OutcomesFieldException(getClass(), "bestResponseDate", "Date can't be greater than Death Date "
                    + AccrualUtil.dateToMDY(deathDate));
        }
        if (courseDate != null && dto.getBestResponseDate().getValue().before(courseDate)) {
            throw new OutcomesFieldException(getClass(), "bestResponseDate", "Date can't be less than Cycle Date "
                    + AccrualUtil.dateToMDY(courseDate));
        }

        if (diagnosisDate != null && dto.getEvaluationDate().getValue().before(diagnosisDate)) {
            throw new OutcomesFieldException(getClass(), "evaluationDate", "Date can't be less than Diagnosis Date "
                    + AccrualUtil.dateToMDY(diagnosisDate));
        }
        if (deathDate != null && dto.getEvaluationDate().getValue().after(deathDate)) {
            throw new OutcomesFieldException(getClass(), "evaluationDate", "Date can't be greater than Death Date "
                    + AccrualUtil.dateToMDY(deathDate));
        }
        if (courseDate != null && dto.getEvaluationDate().getValue().before(courseDate)) {
            throw new OutcomesFieldException(getClass(), "evaluationDate", "Date can't be less than Cycle Date "
                    + AccrualUtil.dateToMDY(courseDate));
        }
        if (diagnosisDate != null && dto.getDiseaseStatusDate().getValue().before(diagnosisDate)) {
            throw new OutcomesFieldException(getClass(), "diseaseStatusDate", "Date can't be less than Diagnosis Date "
                    + AccrualUtil.dateToMDY(diagnosisDate));
        }
        if (deathDate != null && dto.getDiseaseStatusDate().getValue().after(deathDate)) {
            throw new OutcomesFieldException(getClass(), "diseaseStatusDate", "Date can't be greater than Death Date "
                    + AccrualUtil.dateToMDY(deathDate));
        }
        if (courseDate != null && dto.getDiseaseStatusDate().getValue().before(courseDate)) {
            throw new OutcomesFieldException(getClass(), "diseaseStatusDate", "Date can't be less than Cycle Date "
                    + AccrualUtil.dateToMDY(courseDate));
        }
        boolean assessmentTypeExists;
        try {
            assessmentTypeExists = cctx.getLookUpService().validateLookUp(dto.getAssessmentType().getCode(),
                    "ASSESSMENT_TYPE", "CODE");
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DiseaseEvaluationSvcBean.businessValidation().", e);
        }
        if (!assessmentTypeExists) {
            throw new OutcomesFieldException(getClass(), "assessmentType", dto.getAssessmentType().getCode()
                    + " AssessmentType is not a valid value");
        }
    }
}
