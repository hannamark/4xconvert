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

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.DiseaseEvaluationSvcDto;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since Feb 22, 2010
  */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class TreatmentRegimenSvcBean extends AbstractOutcomesBusSvcBean<TreatmentRegimenSvcDto> {

    private static TreatmentRegimenSvcBean instance = new TreatmentRegimenSvcBean();

    /**
     * @return the instance
     */
    public static TreatmentRegimenSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi patient identifier
     * @return list of all treatment regimen for patient
     * @throws OutcomesException exception
     */
    public List<TreatmentRegimenSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<TreatmentRegimenSvcDto> trSvcList = new ArrayList<TreatmentRegimenSvcDto>();
        try {
            List<PerformedActivityDto> paList = cctx.getPerformedActivityService().getByStudySubject(parentIi);
            for (PerformedActivityDto pa : paList) {
                if (!PAUtil.isCdNull(pa.getCategoryCode())
                        && pa.getCategoryCode().getCode().equals(ActivityCategoryCode.TREATMENT_PLAN.getCode())) {
                    trSvcList.add(getTreatmentRegimenSvcDto(pa));
                }
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in TreatmentRegimenSvcBean.search().", e);
        }
        return trSvcList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreatmentRegimenSvcDto get(TreatmentRegimenSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        if (dto == null) {
            return null;
        }
        TreatmentRegimenSvcDto result = null;
        if (dto != null && !PAUtil.isIiNull(dto.getIdentifier())) {
            PerformedActivityDto paDto = null;
            try {
                paDto = cctx.getPerformedActivityService().get(dto.getIdentifier());
                result = getTreatmentRegimenSvcDto(paDto);
            } catch (RemoteException e) {
                throw new OutcomesException("Error in TreatmentRegimenSvcBean.get().", e);
            }

            result.setOffTreatment(OffTreatmentSvcBean.getInstance().get(dto.getOffTreatment(),
                    cctx, result.getIdentifier()));

            if (dto.getDiseaseEvaluations() != null) {
                if (dto.getDiseaseEvaluations().isEmpty()) {
                    result.setDiseaseEvaluations(DiseaseEvaluationSvcBean.getInstance().search(
                            cctx, result.getIdentifier()));
                } else {
                    List<DiseaseEvaluationSvcDto> deList = new ArrayList<DiseaseEvaluationSvcDto>();
                    for (DiseaseEvaluationSvcDto de : dto.getDiseaseEvaluations()) {
                        deList.add(DiseaseEvaluationSvcBean.getInstance().get(de, cctx, result.getIdentifier()));
                    }
                    result.setDiseaseEvaluations(deList);
                }
            }

            if (dto.getLesionAssessments() != null) {
                if (dto.getLesionAssessments().isEmpty()) {
                    result.setLesionAssessments(LesionAssessmentSvcBean.getInstance().search(
                            cctx, result.getIdentifier()));
                } else {
                    List<LesionAssessmentSvcDto> laList = new ArrayList<LesionAssessmentSvcDto>();
                    for (LesionAssessmentSvcDto la : dto.getLesionAssessments()) {
                        laList.add(LesionAssessmentSvcBean.getInstance().get(la, cctx, result.getIdentifier()));
                    }
                    result.setLesionAssessments(laList);
                }
            }

            if (dto.getCycles() != null) {
                if (dto.getCycles().isEmpty()) {
                    result.setCycles(CycleSvcBean.getInstance().search(cctx, result.getIdentifier()));
                } else {

                    List<CycleSvcDto> cList = new ArrayList<CycleSvcDto>();
                    for (CycleSvcDto c : dto.getCycles()) {
                        cList.add(CycleSvcBean.getInstance().get(c, cctx, result.getIdentifier()));
                    }
                    result.setCycles(cList);
                }
            }
        }
        return result;
    }

    private TreatmentRegimenSvcDto getTreatmentRegimenSvcDto(PerformedActivityDto paDto) {
        TreatmentRegimenSvcDto result = new TreatmentRegimenSvcDto();
        result.setIdentifier(paDto.getIdentifier());
        result.setName(paDto.getName());
        result.setDescription(paDto.getTextDescription());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreatmentRegimenSvcDto put(TreatmentRegimenSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        TreatmentRegimenSvcDto result = super.put(dto, cctx, parentIi);
        result.setOffTreatment(OffTreatmentSvcBean.getInstance().put(dto.getOffTreatment(), cctx,
                result.getIdentifier()));

        if (dto.getDiseaseEvaluations() != null) {
            List<DiseaseEvaluationSvcDto> deList = new ArrayList<DiseaseEvaluationSvcDto>();
            for (DiseaseEvaluationSvcDto de : dto.getDiseaseEvaluations()) {
                deList.add(DiseaseEvaluationSvcBean.getInstance().put(de, cctx, result.getIdentifier()));
            }
            result.setDiseaseEvaluations(deList);
        }

        if (dto.getLesionAssessments() != null) {
            List<LesionAssessmentSvcDto> laList = new ArrayList<LesionAssessmentSvcDto>();
            for (LesionAssessmentSvcDto la : dto.getLesionAssessments()) {
                laList.add(LesionAssessmentSvcBean.getInstance().put(la, cctx, result.getIdentifier()));
            }
            result.setLesionAssessments(laList);
        }

        if (dto.getCycles() != null) {
            List<CycleSvcDto> cList = new ArrayList<CycleSvcDto>();
            for (CycleSvcDto c : dto.getCycles()) {
                cList.add(CycleSvcBean.getInstance().put(c, cctx, result.getIdentifier()));
            }
            result.setCycles(cList);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TreatmentRegimenSvcDto create(TreatmentRegimenSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        try {
            PerformedActivityDto paDto = new PerformedActivityDto();
            paDto.setTextDescription(dto.getDescription());
            paDto.setName(dto.getName());
            paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.getByCode("Treatment Plan")));
            paDto.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            paDto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            paDto = cctx.getPerformedActivityService().create(paDto);

            dto.setIdentifier(paDto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in TreatmentRegimenSvcBean.create().", e);
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TreatmentRegimenSvcDto delete(TreatmentRegimenSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        throw new OutcomesException("Delete method is not implemented for TreatmentRegimenSvcBean");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TreatmentRegimenSvcDto update(TreatmentRegimenSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        try {
            if (OutcomesUtil.getPerformedActivityByCategoryCode(ActivityCategoryCode.TREATMENT_PLAN, cctx,
                    parentIi, dto.getIdentifier())) {
                PerformedActivityDto paDto = cctx.getPerformedActivityService().get(dto.getIdentifier());
                if (!parentIi.getExtension().equals(paDto.getStudySubjectIdentifier().getExtension())) {
                    throw new OutcomesException("ParentIi is not same as StudySubjectIdentifier in"
                            + "TreatmentRegimenSvcBean.update(). ");
                }
                paDto.setTextDescription(dto.getDescription());
                paDto.setName(dto.getName());
                paDto = cctx.getPerformedActivityService().update(paDto);
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in TreatmentRegimenSvcBean.update().", e);
        }
        return dto;
    }

}
