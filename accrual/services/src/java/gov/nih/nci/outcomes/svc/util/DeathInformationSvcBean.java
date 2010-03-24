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
import gov.nih.nci.accrual.outweb.enums.ResponseInds;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.DeathInformationSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Hugh Reinhart
 * @since Feb 22, 2010
 */
public class DeathInformationSvcBean extends AbstractOutcomesBusSvcBean<DeathInformationSvcDto> {

    private static DeathInformationSvcBean instance = new DeathInformationSvcBean();

    /**
     * @return the instance
     */
    public static DeathInformationSvcBean getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeathInformationSvcDto get(DeathInformationSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        if (dto == null) {
            return null;
        }
        DeathInformationSvcDto deathInfo = new DeathInformationSvcDto();
        List<PerformedObservationResultDto> deathCauseList = null;
        List<PerformedObservationResultDto> autopsyList = null;
        try {
            List<PerformedObservationDto> poList = cctx.getPerformedActivityService().
            getPerformedObservationByStudySubject(parentIi);
            for (PerformedObservationDto poBean : poList) {
                if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.DEATH_INFORMATION.getCode())) {
                    deathCauseList = cctx.getPerFormedObservationResultService().
                    getPerformedObservationResultByPerformedActivity(poBean.getIdentifier());
                    deathInfo.setIdentifier(poBean.getIdentifier());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.AUTOPSY_INFORMATION.getCode())) {
                    deathInfo.setAutopsyId(poBean.getIdentifier());
                    deathInfo.setAutopsySite(poBean.getTargetSiteCode());
                    autopsyList = cctx.getPerFormedObservationResultService().
                    getPerformedObservationResultByPerformedActivity(poBean.getIdentifier());

                }
                if (deathCauseList != null && !deathCauseList.isEmpty()
                        && autopsyList != null && !autopsyList.isEmpty()) {
                    for (PerformedObservationResultDto porDto : deathCauseList) {
                        if (porDto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                DEATH_CAUSE.getCode())) {
                            deathInfo.setCause(porDto.getResultCode());
                        } else if (porDto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                DEATH_DATE.getCode())) {
                            deathInfo.setEventDate(porDto.getResultDateRange().getLow());
                        }
                    }
                    for (PerformedObservationResultDto porDto : autopsyList) {
                        if (!PAUtil.isCdNull(porDto.getTypeCode()) && porDto.getTypeCode().getCode().equals(
                                PerformedObservationResultTypeCode.AUTOPSY_PERFORMED_INDICATOR.getCode())) {
                            deathInfo.setAutopsyInd(porDto.getResultCode());
                        } else if (!PAUtil.isCdNull(porDto.getTypeCode()) && porDto.getTypeCode().getCode().equals(
                                PerformedObservationResultTypeCode.CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY.getCode())) {
                            deathInfo.setCauseByAutopsy(porDto.getResultCode());
                        }
                    }

                    deathCauseList = null;
                    autopsyList = null;
                }
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.get().", e);
        }
        return deathInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DeathInformationSvcDto create(DeathInformationSvcDto svcDto, SvcContext cctx,
            Ii parentIi) throws OutcomesException {
        businessValidation(svcDto, cctx);
        try {
            OutcomesUtil.getPerformedObservationByNameCode(ActivityNameCode.DEATH_INFORMATION, cctx, parentIi,
                    true, false , false , null);

            Ii studyProtocolIdentifier = cctx.getSearchTrialService().getOutcomesStudyProtocolIi();
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.DEATH_INFORMATION));
            dto.setStudyProtocolIdentifier(studyProtocolIdentifier);
            dto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            dto = cctx.getPerformedActivityService().createPerformedObservation(dto);

            PerformedObservationResultDto porDto1 = new PerformedObservationResultDto();
            porDto1.setPerformedObservationIdentifier(dto.getIdentifier());
            porDto1.setResultCode(svcDto.getCause());
            porDto1.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.DEATH_CAUSE));
            porDto1.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto1);

            PerformedObservationResultDto porDto2 = new PerformedObservationResultDto();
            porDto2.setPerformedObservationIdentifier(dto.getIdentifier());
            if (porDto2.getResultDateRange() == null) {
                porDto2.setResultDateRange(new Ivl<Ts>());
            }
            porDto2.getResultDateRange().setLow(svcDto.getEventDate());
            porDto2.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.DEATH_DATE));
            porDto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto2);

            OutcomesUtil.getPerformedObservationByNameCode(ActivityNameCode.AUTOPSY_INFORMATION, cctx, parentIi,
                    true, false , false , null);

            PerformedObservationDto dto2 = new PerformedObservationDto();
            dto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.AUTOPSY_INFORMATION));
            if (svcDto.getAutopsyInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
                dto2.setTargetSiteCode(svcDto.getAutopsySite());
            }
            dto2.setStudyProtocolIdentifier(studyProtocolIdentifier);
            dto2.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            dto2 = cctx.getPerformedActivityService().createPerformedObservation(dto2);

            PerformedObservationResultDto porDto3 = new PerformedObservationResultDto();
            porDto3.setPerformedObservationIdentifier(dto2.getIdentifier());
            porDto3.setResultCode(svcDto.getAutopsyInd());
            porDto3.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                    AUTOPSY_PERFORMED_INDICATOR));
            porDto3.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto3);

            PerformedObservationResultDto porDto4 = new PerformedObservationResultDto();
            porDto4.setPerformedObservationIdentifier(dto2.getIdentifier());
            porDto4.setResultCode(svcDto.getCauseByAutopsy());
            porDto4.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                    CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY));
            porDto4.setStudyProtocolIdentifier(studyProtocolIdentifier);
            cctx.getPerFormedObservationResultService().create(porDto4);

            svcDto.setIdentifier(dto.getIdentifier());
            svcDto.setAutopsyId(dto2.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.create().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.create().", e);
        }
        return svcDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DeathInformationSvcDto delete(DeathInformationSvcDto dto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        throw new OutcomesException("Delete method is not implemented for DeathInformationSvcBean");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DeathInformationSvcDto update(DeathInformationSvcDto svcDto,
            SvcContext cctx, Ii parentIi) throws OutcomesException {
        businessValidation(svcDto, cctx);
        try {
            PerformedObservationDto deathDto = OutcomesUtil.getPerformedObservationByNameCode(ActivityNameCode.
                    DEATH_INFORMATION, cctx, parentIi, true, true , true , svcDto.getIdentifier()).get(0);
            if (!parentIi.getExtension().equals(deathDto.getStudySubjectIdentifier().getExtension())) {
                throw new OutcomesException("ParentIi is not same as StudySubjectIdentifier in"
                        + "DeathInformationSvcBean.update(). ");
            }
            PerformedObservationDto autopsyDto = OutcomesUtil.getPerformedObservationByNameCode(ActivityNameCode.
                    AUTOPSY_INFORMATION, cctx, parentIi, true, true , true , svcDto.getAutopsyId()).get(0);
            if (!parentIi.getExtension().equals(autopsyDto.getStudySubjectIdentifier().getExtension())) {
                throw new OutcomesException("ParentIi is not same as StudySubjectIdentifier in"
                        + "DeathInformationSvcBean.update(). ");
            }

            List<PerformedObservationResultDto> deathCauseList = cctx.getPerFormedObservationResultService().
            getPerformedObservationResultByPerformedActivity(deathDto.getIdentifier());

            List<PerformedObservationResultDto> autopsyList = cctx.getPerFormedObservationResultService().
            getPerformedObservationResultByPerformedActivity(autopsyDto.getIdentifier());

            for (PerformedObservationResultDto dto : deathCauseList) {
                if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                        DEATH_CAUSE.getCode())) {
                    dto.setResultCode(svcDto.getCause());
                } else if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                        DEATH_DATE.getCode())) {
                    dto.getResultDateRange().setLow(svcDto.getEventDate());
                }
                cctx.getPerFormedObservationResultService().update(dto);
            }
            for (PerformedObservationResultDto dto : autopsyList) {
                if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                        AUTOPSY_PERFORMED_INDICATOR.getCode())) {
                    dto.setResultCode(svcDto.getAutopsyInd());
                } else if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                        CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY.getCode())) {
                    if (svcDto.getAutopsyInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
                        dto.setResultCode(svcDto.getCauseByAutopsy());
                    } else {
                        dto.setResultCode(CdConverter.convertStringToCd(""));
                    }
                }
                cctx.getPerFormedObservationResultService().update(dto);
            }
            if (svcDto.getAutopsyInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
                autopsyDto.setTargetSiteCode(svcDto.getAutopsySite());
            } else {
                autopsyDto.setTargetSiteCode(CdConverter.convertStringToCd(""));
            }
            cctx.getPerformedActivityService().updatePerformedObservation(autopsyDto);
        } catch (RemoteException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.update().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in DeathInformationSvcBean.update().", e);
        }
        return svcDto;
    }

    private void businessValidation(DeathInformationSvcDto dto, SvcContext cctx) throws OutcomesException {
        if (dto.getAutopsyInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
            boolean autopsySiteExists;
            try {
                autopsySiteExists = cctx.getLookUpService().validateLookUp(dto.getAutopsySite().getCode(),
                        "ANATOMIC_SITES", "CODE");
            } catch (RemoteException e) {
                throw new OutcomesException("Error in DeathInformationSvcBean.businessValidation().", e);
            }
            if (!autopsySiteExists) {
                throw new OutcomesFieldException(getClass(), "autopsySite", dto.getAutopsySite().getCode()
                        + " Site of Disease Autopsy is not a valid value");
            }
        }
    }
}
