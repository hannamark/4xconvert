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

import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.outcomes.svc.dto.StagingSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
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
public class StagingSvcBean extends AbstractOutcomesBusSvcBean<StagingSvcDto> {

    private static StagingSvcBean instance = new StagingSvcBean();

    /**
     * @return the instance
     */
    public static StagingSvcBean getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StagingSvcDto get(StagingSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
            if (dto == null) {
                return null;
            }
            StagingSvcDto staging = new StagingSvcDto();
            PerformedObservationDto psm = getPerformObserStaging(dto, cctx, parentIi, "get");
            try {
                staging.setIdentifier(psm.getIdentifier());
                if (psm.getMethodCode() != null) {
                    List<Cd> cds = new ArrayList<Cd>();
                    cds = DSetConverter.convertDsetToCdList(psm.getMethodCode());
                    staging.setMethod(cds.get(0));
                }
                List<PerformedClinicalResultDto> pcrList = cctx.getPerFormedObservationResultService()
                        .getPerformedClinicalResultByPerformedActivity(psm.getIdentifier());
                if (pcrList != null && !pcrList.isEmpty()) {
                    for (PerformedClinicalResultDto pcr : pcrList) {
                        if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.T.getCode())) {
                            staging.setTt(pcr.getResultText());
                        }
                        if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.N.getCode())) {
                            staging.setNn(pcr.getResultText());
                        }
                        if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.M.getCode())) {
                            staging.setMm(pcr.getResultText());
                        }
                        if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.STAGE.getCode())) {
                            staging.setStage(pcr.getResultText());
                        }
                            staging.setSystem(pcr.getStageCodingSystem());
                        }
                    }
            } catch (RemoteException e) {
                throw new OutcomesException(e.getLocalizedMessage());
            }   
        return staging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StagingSvcDto create(StagingSvcDto svcDto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        svcDto.validate();
        PerformedObservationDto existingDto = getPerformObserStaging(new StagingSvcDto(), cctx, parentIi, "get");
        if (PAUtil.isIiNotNull(existingDto.getIdentifier())) {
            throw new OutcomesException("Patient can have only one " + svcDto.getClass().getName());
        }
        try {
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.STAGING));
            List<Cd> cds = new ArrayList<Cd>();
            cds.add(svcDto.getMethod());
            dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
            dto.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            dto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            dto = cctx.getPerformedActivityService().createPerformedObservation(dto);

            //T
            PerformedClinicalResultDto pcrDto1 = new PerformedClinicalResultDto();
            pcrDto1.setPerformedObservationIdentifier(dto.getIdentifier());
            pcrDto1.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.T));
            pcrDto1.setResultText(svcDto.getTt());
            pcrDto1.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            pcrDto1.setStageCodingSystem(svcDto.getSystem());
            cctx.getPerFormedObservationResultService().createPerformedClinicalResult(pcrDto1);
            //N
            PerformedClinicalResultDto pcrDto2 = new PerformedClinicalResultDto();
            pcrDto2.setPerformedObservationIdentifier(dto.getIdentifier());
            pcrDto2.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.N));
            pcrDto2.setResultText(svcDto.getNn());
            pcrDto2.setStageCodingSystem(svcDto.getSystem());
            pcrDto2.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            cctx.getPerFormedObservationResultService().createPerformedClinicalResult(pcrDto2);
            //M
            PerformedClinicalResultDto pcrDto3 = new PerformedClinicalResultDto();
            pcrDto3.setPerformedObservationIdentifier(dto.getIdentifier());
            pcrDto3.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.M));
            pcrDto3.setResultText(svcDto.getMm());
            pcrDto3.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            pcrDto3.setStageCodingSystem(svcDto.getSystem());
            cctx.getPerFormedObservationResultService().createPerformedClinicalResult(pcrDto3);
            //Stage
            PerformedClinicalResultDto pcrDto4 = new PerformedClinicalResultDto();
            pcrDto4.setPerformedObservationIdentifier(dto.getIdentifier());
            pcrDto4.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.STAGE));
            pcrDto4.setResultText(svcDto.getStage());
            pcrDto4.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            pcrDto4.setStageCodingSystem(svcDto.getSystem());
            cctx.getPerFormedObservationResultService().createPerformedClinicalResult(pcrDto4);

            svcDto.setIdentifier(dto.getIdentifier());
          } catch (RemoteException e) {
              throw new OutcomesException("Error in StagingSvcBean.create()" + e.getLocalizedMessage());
          } catch (DataFormatException e) {
              throw new OutcomesException("Error in StagingSvcBean.create()" + e.getLocalizedMessage());
        }
        return svcDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StagingSvcDto delete(StagingSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        throw new OutcomesException("Delete for Staging is not supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StagingSvcDto update(StagingSvcDto svcDto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        svcDto.validate();
        try {
            PerformedObservationDto dto = getPerformObserStaging(svcDto, cctx, parentIi, "update");
            List<Cd> cds = new ArrayList<Cd>();
            cds.add(svcDto.getMethod());
            dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
            cctx.getPerformedActivityService().updatePerformedObservation(dto);
            List<PerformedClinicalResultDto> pcrList =
               cctx.getPerFormedObservationResultService().getPerformedClinicalResultByPerformedActivity(
                       dto.getIdentifier());
            if (pcrList != null && !pcrList.isEmpty()) {
                for (PerformedClinicalResultDto pcr : pcrList) {
                 if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.T.getCode())) {
                   pcr.setResultText(svcDto.getTt());
                 }
                 if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.N.getCode())) {
                    pcr.setResultText(svcDto.getNn());
                 }
                 if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.M.getCode())) {
                    pcr.setResultText(svcDto.getMm());
                 }
                 if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.STAGE.getCode())) {
                    pcr.setResultText(svcDto.getStage());
                 }
                 pcr.setStageCodingSystem(svcDto.getSystem());
                 cctx.getPerFormedObservationResultService().updatePerformedClinicalResult(pcr);
               }
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in StagingSvcBean.update()" + e.getLocalizedMessage());
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in StagingSvcBean.update()" + e.getLocalizedMessage());
        }
        return svcDto;
    }
    /**
     * @param dto
     * @param cctx
     * @param parentIi
     * @throws OutcomesException
     */
    private PerformedObservationDto getPerformObserStaging(StagingSvcDto dto, SvcContext cctx,
            Ii parentIi, String methodName) throws OutcomesException {
        PerformedObservationDto performObserDto = new PerformedObservationDto();
        List<PerformedObservationDto> paList = null;
        if (methodName.equalsIgnoreCase("get")) {
            paList = OutcomesUtil.getPerformedObservationByNameCode(
                    ActivityNameCode.STAGING, cctx, parentIi, true, false, false, null);
        } else {
            paList = OutcomesUtil.getPerformedObservationByNameCode(
                    ActivityNameCode.STAGING, cctx, parentIi, true, true, false, null);
        }
        if (paList != null && !paList.isEmpty()) {
            performObserDto = paList.get(0);
        }
        if (PAUtil.isIiNotNull(dto.getIdentifier()) 
            && !dto.getIdentifier().getExtension().equalsIgnoreCase(performObserDto.getIdentifier().getExtension())) {
                throw new OutcomesException(getClass() + " dto identifer" + dto.getIdentifier().getExtension()  
                     + " is not matches the studySubjectIdentifier " + parentIi.getExtension() + " from the database");
         } 
        return performObserDto;
    }
}
