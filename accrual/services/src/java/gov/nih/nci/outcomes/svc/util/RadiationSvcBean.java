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
import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.RadiationSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
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
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals", "PMD.NPathComplexity" })
public class RadiationSvcBean extends AbstractOutcomesBusSvcBean<RadiationSvcDto> {

    private static RadiationSvcBean instance = new RadiationSvcBean();

    /**
     * @return the instance
     */
    public static RadiationSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi cycle identifier
     * @return list of radiation for given cycle
     * @throws OutcomesException exception
     */
    public List<RadiationSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<RadiationSvcDto> radSvcList = new ArrayList<RadiationSvcDto>();
        List<RadiationSvcDto> listOfSvcDto = new ArrayList<RadiationSvcDto>();
        try {
            List<PerformedRadiationAdministrationDto> praList = cctx.getPerformedActivityService().
            getPerformedRadiationAdministrationByStudySubject(cctx.getStudySubjectIi());
            for (PerformedRadiationAdministrationDto pra : praList) {
                if (!PAUtil.isCdNull(pra.getCategoryCode())
                        && pra.getCategoryCode().getCode().equals(ActivityCategoryCode.RADIATION.getCode())) {
                    List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                    getByTargetPerformedActivity(pra.getIdentifier(), CdConverter.convertStringToCd(
                            ActivityRelationshipTypeCode.COMP.getCode()));
                    if (!arList.isEmpty() && arList.get(0).getSourcePerformedActivityIdentifier().getExtension()
                            .equals(parentIi.getExtension())) {
                        RadiationSvcDto dto = new RadiationSvcDto();
                        dto.setIdentifier(pra.getIdentifier());
                        listOfSvcDto.add(dto);
                    }
                }
            }

            for (RadiationSvcDto dto : listOfSvcDto) {
                radSvcList.add(get(dto, cctx, parentIi));
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in RadiationSvcBean.search().", e);
        }
        return radSvcList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadiationSvcDto get(RadiationSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        if (dto == null) {
            return null;
        }
        RadiationSvcDto returnSvcDto = new RadiationSvcDto();
        try {
            PerformedRadiationAdministrationDto pra = cctx.getPerformedActivityService().
            getPerformedRadiationAdministration(dto.getIdentifier());
            List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
            getByTargetPerformedActivity(pra.getIdentifier(), CdConverter.convertStringToCd(
                    ActivityRelationshipTypeCode.COMP.getCode()));
            if (!arList.isEmpty() && arList.get(0).getSourcePerformedActivityIdentifier().getExtension()
                    .equals(parentIi.getExtension())) {
                returnSvcDto = getRadiationSvcDto(pra);
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in RadiationSvcBean.get().", e);
        }
        return returnSvcDto;
    }

    private RadiationSvcDto getRadiationSvcDto(PerformedRadiationAdministrationDto pra) {
        RadiationSvcDto result = new RadiationSvcDto();
        result.setIdentifier(pra.getIdentifier());
        result.setType(CdConverter.convertStringToCd(pra.getName().getValue()));
        result.setRadDate(pra.getActualDateRange().getLow());
        result.setTotalDose(pra.getDoseTotal());
        result.setDuration(pra.getDoseDuration());
        result.setDose(pra.getDose());
        result.setDoseFreq(pra.getDoseFrequencyCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RadiationSvcDto create(RadiationSvcDto dto, SvcContext cctx,
            Ii parentIi) throws OutcomesException {
        businessValidation(dto, cctx);
        try {
            PerformedRadiationAdministrationDto praDto = new PerformedRadiationAdministrationDto();
            if (praDto.getActualDateRange() == null) {
                praDto.setActualDateRange(new Ivl<Ts>());
            }
            praDto.getActualDateRange().setLow(dto.getRadDate());
            praDto.setName(StConverter.convertToSt(dto.getType().getCode()));
            praDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.RADIATION));
            praDto.setDoseDuration(dto.getDuration());
            praDto.setDoseTotal(dto.getTotalDose());
            praDto.setDose(dto.getDose());
            praDto.setDoseFrequencyCode(dto.getDoseFreq());
            praDto.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            praDto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
            praDto = cctx.getPerformedActivityService().createPerformedRadiationAdministration(praDto);

            ActivityRelationshipDto arDto = new ActivityRelationshipDto();
            arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto.setSourcePerformedActivityIdentifier(parentIi);
            arDto.setTargetPerformedActivityIdentifier(praDto.getIdentifier());
            cctx.getActivityRelationshipService().create(arDto);

            dto.setIdentifier(praDto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in RadiationSvcBean.create().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in RadiationSvcBean.create().", e);
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RadiationSvcDto delete(RadiationSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        throw new OutcomesException("Delete method is not implemented for RadiationSvcBean");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RadiationSvcDto update(RadiationSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        businessValidation(dto, cctx);
        try {
            if (OutcomesUtil.getPerformedActivityByCategoryCode(ActivityCategoryCode.RADIATION, cctx,
                    cctx.getStudySubjectIi(), dto.getIdentifier())) {
                PerformedRadiationAdministrationDto praDto = cctx.getPerformedActivityService()
                .getPerformedRadiationAdministration(dto.getIdentifier());

                List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                getByTargetPerformedActivity(praDto.getIdentifier(),
                        CdConverter.convertStringToCd(ActivityRelationshipTypeCode.COMP.getCode()));
                if (!arList.get(0).getSourcePerformedActivityIdentifier().getExtension().equals(
                        parentIi.getExtension())) {
                  throw new OutcomesException("ParentIi is not same as CycleIdentifier in RadiationSvcBean.update().");
                }
                praDto.getActualDateRange().setLow(dto.getRadDate());
                praDto.setName(StConverter.convertToSt(dto.getType().getCode()));
                praDto.setDoseDuration(dto.getDuration());
                praDto.setDoseTotal(dto.getTotalDose());
                praDto.setDose(dto.getDose());
                praDto.setDoseFrequencyCode(dto.getDoseFreq());
                praDto = cctx.getPerformedActivityService().updatePerformedRadiationAdministration(praDto);
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in RadiationSvcBean.update().", e);
        } catch (DataFormatException e) {
            throw new OutcomesException("Error in RadiationSvcBean.update().", e);
        }
        return dto;
    }

    private void businessValidation(RadiationSvcDto dto, SvcContext cctx) throws OutcomesException {
        boolean doseUomExists;
        boolean doseFrequencyExists;
        boolean doseDurationUomExists = false;
        boolean doseTotalUomExists;
        try {
            doseUomExists = cctx.getLookUpService().validateLookUp(dto.getDose().getUnit(),
                    "UNIT_OF_MEASUREMENT", "CODE");
            doseFrequencyExists = cctx.getLookUpService().validateLookUp(dto.getDoseFreq().getCode(),
                    "DOSE_FREQUENCY", "CODE");
            if (dto.getDuration() != null && dto.getDuration().getUnit() != null 
                    && !dto.getDuration().getUnit().equals("")) {
                doseDurationUomExists = cctx.getLookUpService().validateLookUp(dto.getDuration().getUnit(),
                        "UNIT_OF_MEASUREMENT", "CODE");
            }
            doseTotalUomExists = cctx.getLookUpService().validateLookUp(dto.getTotalDose().getUnit(),
                    "UNIT_OF_MEASUREMENT", "CODE");
        } catch (RemoteException e) {
            throw new OutcomesException("Error in RadiationSvcBean.businessValidation().", e);
        }
        if (!doseUomExists) {
            throw new OutcomesFieldException(getClass(), "dose.unit", dto.getDose().getUnit()
                    + " Dose UOM is not a valid value");
        }
        if (!doseFrequencyExists) {
            throw new OutcomesFieldException(getClass(), "doseFreq", dto.getDoseFreq().getCode()
                    + " Dose Frequency is not a valid value");
        }
        if (dto.getDuration() != null && dto.getDuration().getUnit() != null 
                && !dto.getDuration().getUnit().equals("") && !doseDurationUomExists) {
            throw new OutcomesFieldException(getClass(), "duration.unit", dto.getDuration().getUnit()
                    + " Dose Duration UOM is not a valid value");
        }
        if (!doseTotalUomExists) {
            throw new OutcomesFieldException(getClass(), "totalDose.unit", dto.getTotalDose().getUnit()
                    + " Dose Total UOM is not a valid value");
        }
    }

}
