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

import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.outcomes.svc.dto.AbstractPriorTherapiesItemDto;
import gov.nih.nci.outcomes.svc.dto.PriorTherapiesItemSvcDto;
import gov.nih.nci.outcomes.svc.dto.PriorTherapySvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Hugh Reinhart
 * @since Feb 22, 2010
 *
 */
@SuppressWarnings({"PMD" })
public class PriorTherapySvcBean extends
        AbstractOutcomesBusSvcBean<PriorTherapySvcDto> {

    private static PriorTherapySvcBean instance = new PriorTherapySvcBean();

    /**
     * @return the instance
     */
    public static PriorTherapySvcBean getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PriorTherapySvcDto get(PriorTherapySvcDto svcDto, SvcContext cctx,
            Ii parentIi) throws OutcomesException {
        if (svcDto == null) {
            return null;
        }
        PriorTherapySvcDto priorTherapySvcDto = new PriorTherapySvcDto();
        try {
            List<AbstractPriorTherapiesItemDto> priorTherapiesItemList = new ArrayList<AbstractPriorTherapiesItemDto>();
            AbstractPriorTherapiesItemDto priorTherapiesItemDto;
            checkPriorTherapyIi(svcDto, cctx, parentIi, "get");
            List<PerformedMedicalHistoryResultDto> medicalHisDTOS = cctx.getPerFormedObservationResultService()
                            .getPerformedMedicalHistoryResultByPerformedActivity(svcDto.getIdentifier());
            priorTherapySvcDto.setIdentifier(svcDto.getIdentifier());
            if (medicalHisDTOS != null) {
                for (PerformedMedicalHistoryResultDto medicalHisDto : medicalHisDTOS) {
                    if (medicalHisDto.getResultIndicator() != null
                            && medicalHisDto.getResultIndicator().getValue() != null) {
                        priorTherapySvcDto.setHasPrior(BlConverter.covertToBoolean(medicalHisDto.getResultIndicator()));
                    }
                    if (medicalHisDto.getResultQuantity() != null
                            && medicalHisDto.getResultQuantity().getValue() != null) {
                            if (PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_THERAPIES.getCode()
                                    .equalsIgnoreCase(medicalHisDto.getTypeCode().getCode())) {
                                priorTherapySvcDto.setTotalRegimenNum(medicalHisDto.getResultQuantity());
                            }
                            if (PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_CHEMOTHERAPY_REGIMENS.getCode()
                                    .equalsIgnoreCase(medicalHisDto.getTypeCode().getCode())) {
                                priorTherapySvcDto.setChemoRegimenNum(medicalHisDto.getResultQuantity());
                            }
                    }
                    if (medicalHisDto.getResultCode() != null && medicalHisDto.getResultCode().getCode() != null) {
                        priorTherapiesItemDto = new PriorTherapiesItemSvcDto();
                        priorTherapiesItemDto.setIdentifier(medicalHisDto.getIdentifier());
                        priorTherapiesItemDto.setType(medicalHisDto.getResultCode());
                        priorTherapiesItemDto.setDescription(medicalHisDto.getDescription());
                        priorTherapiesItemList.add(priorTherapiesItemDto);
                    }
                }
                priorTherapySvcDto.setItemList(priorTherapiesItemList);
            }
        } catch (RemoteException e) {
            throw new OutcomesException(e.getLocalizedMessage());
        }
        return priorTherapySvcDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PriorTherapySvcDto create(PriorTherapySvcDto svcDto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        svcDto.validate();
        PriorTherapySvcDto existingDto = new PriorTherapySvcDto();
        checkPriorTherapyIi(existingDto, cctx, parentIi, "get");
        if (PAUtil.isIiNotNull(existingDto.getIdentifier())) {
            throw new OutcomesException("Patient can have only one " + svcDto.getClass().getName());
        }
        try {
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.PRIOR_THERAPIES));
            dto.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            dto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            PerformedObservationDto dbPerformedObservationDTO
                = cctx.getPerformedActivityService().createPerformedObservation(dto);

            PerformedMedicalHistoryResultDto perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
            perMedicalHistoryDTO.setPerformedObservationIdentifier(dbPerformedObservationDTO.getIdentifier());
            perMedicalHistoryDTO.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                    PerformedObservationResultTypeCode.HAD_PRIOR_THERAPIES));
            perMedicalHistoryDTO.setResultIndicator(BlConverter.convertToBl(svcDto.getHasPrior()));
            perMedicalHistoryDTO = cctx.getPerFormedObservationResultService()
                .createPerformedMedicalHistoryResult(perMedicalHistoryDTO);

          //create the new list
            createPriorTherapyType(svcDto, cctx, dbPerformedObservationDTO.getIdentifier());

            perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
            perMedicalHistoryDTO.setPerformedObservationIdentifier(dbPerformedObservationDTO.getIdentifier());
            perMedicalHistoryDTO.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                    PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_THERAPIES));
            perMedicalHistoryDTO.setResultQuantity(svcDto.getTotalRegimenNum());
            cctx.getPerFormedObservationResultService().createPerformedMedicalHistoryResult(perMedicalHistoryDTO);

            perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
            perMedicalHistoryDTO.setPerformedObservationIdentifier(dbPerformedObservationDTO.getIdentifier());
            perMedicalHistoryDTO.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                    PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_CHEMOTHERAPY_REGIMENS));
            perMedicalHistoryDTO.setResultQuantity(svcDto.getChemoRegimenNum());
            cctx.getPerFormedObservationResultService().createPerformedMedicalHistoryResult(perMedicalHistoryDTO);
            svcDto.setIdentifier(dbPerformedObservationDTO.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in PriorTherapySvcBean.create()" + e.getMessage());
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in PriorTherapySvcBean.create()" + e.getMessage());
        }

        return svcDto;
    }

    /**
     * @param svcDto
     * @param cctx
     * @param dbPerformedObservationDTO
     * @throws RemoteException
     * @throws DataFormatException
     */
    private void createPriorTherapyType(PriorTherapySvcDto svcDto,
            SvcContext cctx, Ii performedObservationIi)
            throws RemoteException, DataFormatException {
        PerformedMedicalHistoryResultDto perMedicalHistoryDTO;
        if (svcDto .getItemList() != null && !svcDto .getItemList().isEmpty()) {
            for (AbstractPriorTherapiesItemDto exitingPriorWebDto : svcDto .getItemList()) {
                perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
                perMedicalHistoryDTO.setPerformedObservationIdentifier(performedObservationIi);
                perMedicalHistoryDTO.setStudyProtocolIdentifier(cctx.getSearchTrialService()
                        .getOutcomesStudyProtocolIi());
                perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                        PerformedObservationResultTypeCode.PRIOR_THERAPY));
                perMedicalHistoryDTO.setResultCode(exitingPriorWebDto.getType());
                perMedicalHistoryDTO.setDescription(exitingPriorWebDto.getDescription());
                  cctx.getPerFormedObservationResultService().createPerformedMedicalHistoryResult(
                          perMedicalHistoryDTO);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PriorTherapySvcDto delete(PriorTherapySvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        if (dto == null) {
            throw new OutcomesException("Empty" + getClass() + " dto cannot be deleted.");
        }
        try {
            checkPriorTherapyIi(dto, cctx, parentIi, "delete");
            cctx.getPerformedActivityService().delete(dto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException(e.getLocalizedMessage());
        }
        return null;
    }

        /**
     * {@inheritDoc}
     */
    @Override
    protected PriorTherapySvcDto update(PriorTherapySvcDto svcDto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        svcDto.validate();
        try {
            //get the perform Activity record for the patient ii
            checkPriorTherapyIi(svcDto, cctx, parentIi, "update");
            //get all records form performed_observation_result for perform activity ii
            List<PerformedMedicalHistoryResultDto> perObResultList = cctx.getPerFormedObservationResultService()
                .getPerformedMedicalHistoryResultByPerformedActivity(svcDto.getIdentifier());
            for (PerformedMedicalHistoryResultDto perMedDto : perObResultList) {
                if (PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_THERAPIES.getCode().equalsIgnoreCase(
                        perMedDto.getTypeCode().getCode())) {
                    perMedDto.setResultQuantity(svcDto.getTotalRegimenNum());
                    cctx.getPerFormedObservationResultService().updatePerformedMedicalHistoryResult(perMedDto);
                }
                if (PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_CHEMOTHERAPY_REGIMENS.getCode().equalsIgnoreCase(
                        perMedDto.getTypeCode().getCode())) {
                    perMedDto.setResultQuantity(svcDto.getChemoRegimenNum());
                    cctx.getPerFormedObservationResultService().updatePerformedMedicalHistoryResult(perMedDto);
                }
                if (PerformedObservationResultTypeCode.PRIOR_THERAPY.getCode().equalsIgnoreCase(
                        perMedDto.getTypeCode().getCode())) {
                    cctx.getPerFormedObservationResultService().delete(perMedDto.getIdentifier());
                }
            }
            createPriorTherapyType(svcDto, cctx, svcDto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in PriorTherapySvcBean.update()" + e.getMessage());
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in PriorTherapySvcBean.update()" + e.getMessage());
        }
        return svcDto;
    }
    /**
     * @param dto
     * @param cctx
     * @param parentIi
     * @throws OutcomesException
     */
    private void checkPriorTherapyIi(PriorTherapySvcDto dto, SvcContext cctx,
            Ii parentIi, String methodName) throws OutcomesException {
        Ii performActivityIi = null;
        List<PerformedObservationDto> paList = null;
        if (methodName.equalsIgnoreCase("get")) {
            paList = OutcomesUtil.getPerformedObservationByNameCode(
                    ActivityNameCode.PRIOR_THERAPIES, cctx, parentIi, true, false, false, null);
        } else {
            paList = OutcomesUtil.getPerformedObservationByNameCode(
                    ActivityNameCode.PRIOR_THERAPIES, cctx, parentIi, true, true, false, null);
        }
        if (paList != null && !paList.isEmpty()) {
            performActivityIi = paList.get(0).getIdentifier();
        }
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            dto.setIdentifier(performActivityIi);
         } else if (!dto.getIdentifier().getExtension().equalsIgnoreCase(performActivityIi.getExtension())) {
           throw new OutcomesException(getClass() + " dto identifer" + dto.getIdentifier().getExtension()
                   + " is not matches the studySubjectIdentifier " + parentIi.getExtension() + " from the database");
         }
    }


}
