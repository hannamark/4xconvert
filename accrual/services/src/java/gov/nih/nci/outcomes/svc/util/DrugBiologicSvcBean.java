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
import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.DrugBiologicSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Hugh Reinhart
 * @since Feb 24, 2010
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength",
    "PMD.AvoidDuplicateLiterals" })
public class DrugBiologicSvcBean extends AbstractOutcomesBusSvcBean<DrugBiologicSvcDto> {

    private static DrugBiologicSvcBean instance = new DrugBiologicSvcBean();

    /**
     * @return the instance
     */
    public static DrugBiologicSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi cycle identifier
     * @return list of drug/biologic for given cycle
     * @throws OutcomesException exception
     */
    public List<DrugBiologicSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<DrugBiologicSvcDto> durSvcList = new ArrayList<DrugBiologicSvcDto>();
        List<DrugBiologicSvcDto> listOfSvcDto = new ArrayList<DrugBiologicSvcDto>();
        try {
            List<PerformedSubstanceAdministrationDto> psaList = cctx.getPerformedActivityService().
            getPerformedSubstanceAdministrationByStudySubject(cctx.getStudySubjectIi());
            for (PerformedSubstanceAdministrationDto psa : psaList) {
                if (!PAUtil.isCdNull(psa.getCategoryCode())
                        && psa.getCategoryCode().getCode().equals(ActivityCategoryCode.DRUG_BIOLOGIC.getCode())) {
                    List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                    getByTargetPerformedActivity(psa.getIdentifier(), CdConverter.convertStringToCd(
                            ActivityRelationshipTypeCode.COMP.getCode()));
                    if (!arList.isEmpty() && arList.get(0).getSourcePerformedActivityIdentifier().getExtension()
                            .equals(parentIi.getExtension())) {
                        DrugBiologicSvcDto dto = new DrugBiologicSvcDto();
                        dto.setIdentifier(psa.getIdentifier());
                        listOfSvcDto.add(dto);
                    }
                }
            }

            for (DrugBiologicSvcDto dto : listOfSvcDto) {
                durSvcList.add(get(dto, cctx, parentIi));
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.search().", e);
        }
        return durSvcList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrugBiologicSvcDto get(DrugBiologicSvcDto dto, SvcContext cctx,
            Ii parentIi) throws OutcomesException {
        if (dto == null) {
            return null;
        }
        List<PerformedObservationResultDto>porList = null;
        Pq height = null;
        Pq weight = null;
        Pq bsa = null;
        DrugBiologicSvcDto returnSvcDto = new DrugBiologicSvcDto();
        try {
            PerformedSubstanceAdministrationDto psa = cctx.getPerformedActivityService().
            getPerformedSubstanceAdministration(dto.getIdentifier());
            List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
            getByTargetPerformedActivity(psa.getIdentifier(), CdConverter.convertStringToCd(
                    ActivityRelationshipTypeCode.COMP.getCode()));
            if (!arList.isEmpty() && arList.get(0).getSourcePerformedActivityIdentifier().getExtension()
                    .equals(parentIi.getExtension())) {
                arList = cctx.getActivityRelationshipService().getBySourcePerformedActivity(
                        arList.get(0).getTargetPerformedActivityIdentifier(),
                        CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                for (ActivityRelationshipDto arDto : arList) {
                    PerformedObservationDto po = cctx.getPerformedActivityService().getPerformedObservation(
                            arDto.getTargetPerformedActivityIdentifier());
                    if (po.getNameCode().getCode().equals(ActivityNameCode.HEIGHT.getCode())) {
                        porList = cctx.getPerFormedObservationResultService().
                        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                        height = porList.get(0).getResultQuantity();
                    } else if (po.getNameCode().getCode().equals(ActivityNameCode.WEIGHT.getCode())) {
                        porList = cctx.getPerFormedObservationResultService().
                        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                        weight = porList.get(0).getResultQuantity();
                    } else if (po.getNameCode().getCode().equals(ActivityNameCode.BSA.getCode())) {
                        porList = cctx.getPerFormedObservationResultService().
                        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                        bsa = porList.get(0).getResultQuantity();
                    }
                }
                returnSvcDto = getDrugBiologicSvcDto(psa, height, weight, bsa);
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.get().", e);
        }
        return returnSvcDto;
    }

    private DrugBiologicSvcDto getDrugBiologicSvcDto(PerformedSubstanceAdministrationDto psaDto,
            Pq height, Pq weight, Pq bsa) {
        DrugBiologicSvcDto result = new DrugBiologicSvcDto();
        result.setIdentifier(psaDto.getIdentifier());
        result.setDose(psaDto.getDose());
        result.setDoseRoute(psaDto.getRouteOfAdministrationCode());
        result.setDoseFreq(psaDto.getDoseFrequencyCode());
        result.setDoseDur(psaDto.getDoseDuration());
        result.setHeight(height);
        result.setWeight(weight);
        result.setBsa(bsa);
        result.setDoseTotal(psaDto.getDoseTotal());
        result.setDoseModType(psaDto.getDoseModificationType());
        result.setInterventionId(psaDto.getInterventionIdentifier());
        result.setStartDate(psaDto.getActualDateRange().getLow());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DrugBiologicSvcDto create(DrugBiologicSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        businessValidation(dto, cctx);
        try {
            Ii studyProtocolIdentifier = cctx.getSearchTrialService().getOutcomesStudyProtocolIi();
            PerformedSubstanceAdministrationDto psaDto = new PerformedSubstanceAdministrationDto();
            psaDto.setInterventionIdentifier(dto.getInterventionId());
            psaDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.DRUG_BIOLOGIC));
            psaDto.setDose(dto.getDose());
            psaDto.setRouteOfAdministrationCode(dto.getDoseRoute());
            psaDto.setDoseFrequencyCode(dto.getDoseFreq());

            psaDto.setDoseDuration(dto.getDoseDur());
            psaDto.setDoseTotal(dto.getDoseTotal());
            psaDto.setDoseModificationType(dto.getDoseModType());
            psaDto.setStudyProtocolIdentifier(studyProtocolIdentifier);
            psaDto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            if (psaDto.getActualDateRange() == null) {
                psaDto.setActualDateRange(new Ivl<Ts>());
            }
            psaDto.getActualDateRange().setLow(dto.getStartDate());

            psaDto = cctx.getPerformedActivityService().createPerformedSubstanceAdministration(psaDto);

            ActivityRelationshipDto arDto = new ActivityRelationshipDto();
            arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto.setSourcePerformedActivityIdentifier(parentIi);
            arDto.setTargetPerformedActivityIdentifier(psaDto.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto);

            PerformedObservationDto poDto1 = new PerformedObservationDto();
            poDto1.setNameCode(CdConverter.convertToCd(ActivityNameCode.HEIGHT));
            poDto1.setStudyProtocolIdentifier(studyProtocolIdentifier);
            poDto1.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            poDto1 = cctx.getPerformedActivityService().createPerformedObservation(poDto1);

            PerformedObservationResultDto porDto1 = new PerformedObservationResultDto();
            porDto1.setPerformedObservationIdentifier(poDto1.getIdentifier());
            porDto1.setResultQuantity(dto.getHeight());
            porDto1.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto1);

            ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
            arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto2.setSourcePerformedActivityIdentifier(psaDto.getIdentifier());
            arDto2.setTargetPerformedActivityIdentifier(poDto1.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto2);

            PerformedObservationDto poDto2 = new PerformedObservationDto();
            poDto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.WEIGHT));
            poDto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
            poDto2.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            poDto2 = cctx.getPerformedActivityService().createPerformedObservation(poDto2);

            PerformedObservationResultDto porDto2 = new PerformedObservationResultDto();
            porDto2.setPerformedObservationIdentifier(poDto2.getIdentifier());
            porDto2.setResultQuantity(dto.getWeight());
            porDto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto2);

            ActivityRelationshipDto arDto3 = new ActivityRelationshipDto();
            arDto3.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto3.setSourcePerformedActivityIdentifier(psaDto.getIdentifier());
            arDto3.setTargetPerformedActivityIdentifier(poDto2.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto3);

            PerformedObservationDto poDto3 = new PerformedObservationDto();
            poDto3.setNameCode(CdConverter.convertToCd(ActivityNameCode.BSA));
            poDto3.setStudyProtocolIdentifier(studyProtocolIdentifier);
            poDto3.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            poDto3 = cctx.getPerformedActivityService().createPerformedObservation(poDto3);

            PerformedObservationResultDto porDto3 = new PerformedObservationResultDto();
            porDto3.setPerformedObservationIdentifier(poDto3.getIdentifier());
            dto.getBsa().setUnit("m2");
            porDto3.setResultQuantity(dto.getBsa());
            porDto3.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto3);

            ActivityRelationshipDto arDto4 = new ActivityRelationshipDto();
            arDto4.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto4.setSourcePerformedActivityIdentifier(psaDto.getIdentifier());
            arDto4.setTargetPerformedActivityIdentifier(poDto3.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto4);

            dto.setIdentifier(psaDto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.create().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.create().", e);
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DrugBiologicSvcDto delete(DrugBiologicSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        throw new OutcomesException("Delete method is not implemented for DrugBiologicSvcBean");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DrugBiologicSvcDto update(DrugBiologicSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        businessValidation(dto, cctx);
        try {
            if (OutcomesUtil.getPerformedActivityByCategoryCode(ActivityCategoryCode.DRUG_BIOLOGIC, cctx,
                    cctx.getStudySubjectIi(), dto.getIdentifier())) {
                List<PerformedObservationResultDto>porList = null;

                List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                getByTargetPerformedActivity(dto.getIdentifier(), CdConverter.convertStringToCd(
                        ActivityRelationshipTypeCode.COMP.getCode()));
                if (!arList.get(0).getSourcePerformedActivityIdentifier().getExtension().equals(
                        parentIi.getExtension())) {
                    throw new OutcomesException("ParentIi is not same as CycleIdentifier in "
                            + "DrugBiologicSvcBean.update().");
                }
                arList = cctx.getActivityRelationshipService().getBySourcePerformedActivity(
                        arList.get(0).getTargetPerformedActivityIdentifier(),
                        CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                for (ActivityRelationshipDto arDto : arList) {
                    PerformedObservationDto po = cctx.getPerformedActivityService().getPerformedObservation(arDto
                            .getTargetPerformedActivityIdentifier());
                    if (po.getNameCode().getCode().equals(ActivityNameCode.HEIGHT.getCode())) {
                        porList = cctx.getPerFormedObservationResultService().
                        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                        porList.get(0).setResultQuantity(dto.getHeight());
                        cctx.getPerFormedObservationResultService().update(porList.get(0));
                    } else if (po.getNameCode().getCode().equals(ActivityNameCode.WEIGHT.getCode())) {
                        porList = cctx.getPerFormedObservationResultService().
                        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                        porList.get(0).setResultQuantity(dto.getWeight());
                        cctx.getPerFormedObservationResultService().update(porList.get(0));
                    } else if (po.getNameCode().getCode().equals(ActivityNameCode.BSA.getCode())) {
                        porList = cctx.getPerFormedObservationResultService().
                        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                        porList.get(0).setResultQuantity(dto.getBsa());
                        cctx.getPerFormedObservationResultService().update(porList.get(0));
                    }
                }
                PerformedSubstanceAdministrationDto psa = cctx.getPerformedActivityService().
                getPerformedSubstanceAdministration(arList.get(0).getSourcePerformedActivityIdentifier());

                if (psa.getCategoryCode().getCode().equals(ActivityCategoryCode.DRUG_BIOLOGIC.getCode())) {
                    psa.setInterventionIdentifier(dto.getInterventionId());
                    psa.setDose(dto.getDose());
                    psa.setRouteOfAdministrationCode(dto.getDoseRoute());
                    psa.setDoseFrequencyCode(dto.getDoseFreq());

                    psa.setDoseDuration(dto.getDoseDur());
                    psa.setDoseTotal(dto.getDoseTotal());
                    psa.setDoseModificationType(dto.getDoseModType());
                    if (psa.getActualDateRange() == null) {
                        psa.setActualDateRange(new Ivl<Ts>());
                    }
                    psa.getActualDateRange().setLow(dto.getStartDate());
                    cctx.getPerformedActivityService().updatePerformedSubstanceAdministration(psa);
                }
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.update().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.update().", e);
        }
        return dto;
    }

    private void businessValidation(DrugBiologicSvcDto dto, SvcContext cctx) throws OutcomesException {
        boolean doseUomExists;
        boolean doseFrequencyExists;
        boolean routeOfAdministrationExists;
        boolean doseDurationUomExists = false;
        boolean doseTotalUomExists;
        try {
            doseUomExists = cctx.getLookUpService().validateLookUp(dto.getDose().getUnit(),
                    "UNIT_OF_MEASUREMENT", "CODE");
            doseFrequencyExists = cctx.getLookUpService().validateLookUp(dto.getDoseFreq().getCode(),
                    "DOSE_FREQUENCY", "CODE");
            routeOfAdministrationExists = cctx.getLookUpService().validateLookUp(dto.getDoseRoute().getCode(),
                    "ROUTE_OF_ADMINISTRATION", "CODE");
            if (dto.getDoseDur() != null && !dto.getDoseDur().getUnit().equals("")) {
                doseDurationUomExists = cctx.getLookUpService().validateLookUp(dto.getDoseDur().getUnit(),
                        "UNIT_OF_MEASUREMENT", "CODE");
            }
            doseTotalUomExists = cctx.getLookUpService().validateLookUp(dto.getDoseTotal().getUnit(),
                    "UNIT_OF_MEASUREMENT", "CODE");
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DrugBiologicSvcBean.businessValidation().", e);
        }
        if (!doseUomExists) {
            throw new OutcomesFieldException(getClass(), "dose.unit", dto.getDose().getUnit()
                    + " Dose UOM is not a valid value");
        }
        if (!doseFrequencyExists) {
            throw new OutcomesFieldException(getClass(), "doseFreq", dto.getDoseFreq().getCode()
                    + " Dose Frequency is not a valid value");
        }
        if (!routeOfAdministrationExists) {
            throw new OutcomesFieldException(getClass(), "doseRoute", dto.getDoseRoute().getCode()
                    + " RouteOfAdministration is not a valid value");
        }
        if (dto.getDoseDur() != null && !dto.getDoseDur().getUnit().equals("")
                && !doseDurationUomExists) {
            throw new OutcomesFieldException(getClass(), "doseDur.unit", dto.getDoseDur().getUnit()
                    + " Dose Duration UOM is not a valid value");
        }
        if (!doseTotalUomExists) {
            throw new OutcomesFieldException(getClass(), "doseTotal.unit", dto.getDoseTotal().getUnit()
                    + " Dose Total UOM is not a valid value");
        }
    }

}
