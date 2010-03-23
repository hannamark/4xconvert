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

import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.outcomes.svc.dto.TumorMarkerSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Hugh Reinhart
 * @since Feb 22, 2010
 *
 */
public class TumorMarkerSvcBean extends AbstractOutcomesBusSvcBean<TumorMarkerSvcDto> {

    private static TumorMarkerSvcBean instance = new TumorMarkerSvcBean();

    /**
     * @return the instance
     */
    public static TumorMarkerSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi patient identifier
     * @return list of all tumor markers for patient
     * @throws OutcomesException exception
     */
    public List<TumorMarkerSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<TumorMarkerSvcDto> retListOfSvcDtos = new ArrayList<TumorMarkerSvcDto>();
        try {
            List<TumorMarkerSvcDto> listOfSvcDto = new ArrayList<TumorMarkerSvcDto>();
            List<PerformedObservationDto> paList = cctx.getPerformedActivityService()
                .getPerformedObservationByStudySubject(parentIi);
            for (PerformedObservationDto pa : paList) {
                if (pa.getCategoryCode() != null && pa.getCategoryCode().getCode() != null
                        && pa.getCategoryCode().getCode().equals(ActivityCategoryCode.TUMOR_MARKER.getCode())) {
                    TumorMarkerSvcDto item = new TumorMarkerSvcDto();
                    item.setIdentifier(pa.getIdentifier());
                    listOfSvcDto.add(item);
                }
            }
            
            for (TumorMarkerSvcDto dto : listOfSvcDto) {
                retListOfSvcDtos.add(get(dto, cctx, parentIi));
            }
        } catch (RemoteException e) {
            throw new OutcomesException(e.getLocalizedMessage());
        }
        
        return retListOfSvcDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TumorMarkerSvcDto get(TumorMarkerSvcDto svcDto, SvcContext cctx,
            Ii parentIi) throws OutcomesException {
        if (svcDto == null || PAUtil.isIiNull(svcDto.getIdentifier())) {
            throw new OutcomesException("Ii cannot be null");
        }
        TumorMarkerSvcDto returnSvcDto = new TumorMarkerSvcDto();
        try {
            PerformedObservationDto perObDto = cctx.getPerformedActivityService().getPerformedObservation(
                        svcDto.getIdentifier());
            if (!PAUtil.isStNull(perObDto.getName())) {
                returnSvcDto.setTumorMarker(CdConverter.convertStringToCd(perObDto.getName().getValue()));
            }
            List<PerformedObservationResultDto> performedResultList = cctx.getPerFormedObservationResultService()
                    .getPerformedObservationResultByPerformedActivity(svcDto.getIdentifier());
            if (performedResultList != null && !performedResultList.isEmpty()) {
                for (PerformedObservationResultDto resultDTO : performedResultList) {
                    if (resultDTO.getResultQuantity() != null
                            && resultDTO.getResultQuantity().getUnit() != null
                            && resultDTO.getResultQuantity().getValue() != null) {
                        returnSvcDto.setTmvUom(resultDTO.getResultQuantity());
                        returnSvcDto.setTumorMarkerValue(
                                StConverter.convertToSt(resultDTO.getResultQuantity().getValue().toString()));
                    } else if (resultDTO.getResultText() != null) {
                        returnSvcDto.setTumorMarkerValue(resultDTO.getResultText());
                    }
                }
            }
        } catch (RemoteException e) {
            throw new OutcomesException(e.getLocalizedMessage());
        }
        return returnSvcDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TumorMarkerSvcDto create(TumorMarkerSvcDto svcDto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        businessValidation(svcDto, cctx);
        try {
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.TUMOR_MARKER));
            dto.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            dto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            dto.setName(StConverter.convertToSt(svcDto.getTumorMarker().getCode()));
            dto = cctx.getPerformedActivityService().createPerformedObservation(dto);
            if (svcDto.getTmvUom() != null
                && svcDto.getTmvUom().getUnit() != null && !svcDto.getTmvUom().getUnit().equals("")
                && svcDto.getTumorMarkerValue() != null
                && svcDto.getTumorMarkerValue().getValue() != null
                && !svcDto.getTumorMarkerValue().getValue().equals("")) {
             //UOM
             PerformedObservationResultDto pcrDto1 = new PerformedObservationResultDto();
             pcrDto1.setPerformedObservationIdentifier(dto.getIdentifier());
             pcrDto1.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
             Pq pq = new Pq();
             pq.setUnit(svcDto.getTmvUom().getUnit());
             pq.setValue(BigDecimal.valueOf(Long.parseLong(svcDto.getTumorMarkerValue().getValue())));
             pcrDto1.setResultQuantity(pq);
             cctx.getPerFormedObservationResultService().create(pcrDto1);
            } else {
                //NO UOM
                PerformedObservationResultDto pcrDto1 = new PerformedObservationResultDto();
                pcrDto1.setPerformedObservationIdentifier(dto.getIdentifier());
                pcrDto1.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
                pcrDto1.setResultText(svcDto.getTumorMarkerValue());
                cctx.getPerFormedObservationResultService().create(pcrDto1);
            }
            svcDto.setIdentifier(dto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in TumorMarkerSvcBean.create()" + e.getMessage());
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in TumorMarkerSvcBean.create()" + e.getMessage());
        }
        return svcDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TumorMarkerSvcDto delete(TumorMarkerSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        throw new OutcomesException("Delete for Tumor Marker is not supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TumorMarkerSvcDto update(TumorMarkerSvcDto svcDto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        throw new OutcomesException("Update for Tumor Marker is not supported.");
    }
    
    private void businessValidation(TumorMarkerSvcDto dto, SvcContext cctx) throws OutcomesException {
        if (dto.getTmvUom() != null
                && dto.getTmvUom().getUnit() != null && !dto.getTmvUom().getUnit().equals("")) {
            boolean uomExists;
            try {
                uomExists = cctx.getLookUpService().validateLookUp(dto.getTmvUom().getUnit(), 
                        "UNIT_OF_MEASUREMENT", "CODE");
            } catch (RemoteException e) {
                throw new OutcomesException("Error in TumorMarkerSvcBean.businessValidation().", e);
            }
            if (!uomExists) {
                throw new OutcomesFieldException(getClass(), "tmvUom.unit", dto.getTmvUom().getUnit() 
                        + " Tumor Marker UOM is not a valid value");
            }
        }
        boolean tumorMarkerExists;
        try {
            tumorMarkerExists = cctx.getLookUpService().validateLookUp(dto.getTumorMarker().getCode(), 
                    "TUMOR_MARKER", "CODE");
        } catch (RemoteException e) {
            throw new OutcomesException("Error in TumorMarkerSvcBean.businessValidation().", e);
        }
        if (!tumorMarkerExists) {
            throw new OutcomesFieldException(getClass(), "tumorMarker", dto.getTumorMarker().getCode() 
                    + " Tumor Marker is not a valid value");
        }
    }

}
