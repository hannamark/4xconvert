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
import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.DrugBiologicSvcDto;
import gov.nih.nci.outcomes.svc.dto.RadiationSvcDto;
import gov.nih.nci.outcomes.svc.dto.SurgerySvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since Feb 24, 2010
 *
 */
public class CycleSvcBean extends AbstractOutcomesBusSvcBean<CycleSvcDto> {

    private static CycleSvcBean instance = new CycleSvcBean();

    /**
     * @return the instance
     */
    public static CycleSvcBean getInstance() {
        return instance;
    }

    /**
     * @param cctx context
     * @param parentIi treatment regiment identifier
     * @return list of all cycles for treatment regimen
     * @throws OutcomesException exception
     */
    public List<CycleSvcDto> search(SvcContext cctx, Ii parentIi) throws OutcomesException {
        List<CycleSvcDto> cySvcList = new ArrayList<CycleSvcDto>();
        try {
            List<PerformedActivityDto> paList = cctx.getPerformedActivityService().
                                                                    getByStudySubject(cctx.getStudySubjectIi());
            for (PerformedActivityDto pa : paList) {
                if (!PAUtil.isCdNull(pa.getCategoryCode())
                        && pa.getCategoryCode().getCode().equals(ActivityCategoryCode.COURSE.getCode())) {
                    List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                    getByTargetPerformedActivity(pa.getIdentifier(), CdConverter.convertStringToCd(
                            ActivityRelationshipTypeCode.COMP.getCode()));
                    if (!arList.isEmpty() && arList.get(0).getSourcePerformedActivityIdentifier().getExtension()
                            .equals(parentIi.getExtension())) {
                        cySvcList.add(getCycleSvcDto(pa));
                    }
                }
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in CycleSvcBean.search().", e);
        }
        return cySvcList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CycleSvcDto get(CycleSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        CycleSvcDto result = null;
        if (dto != null && !PAUtil.isIiNull(dto.getIdentifier())) {
            PerformedActivityDto paDto = null;
            try {
                paDto = cctx.getPerformedActivityService().get(dto.getIdentifier());
                result = getCycleSvcDto(paDto);
            } catch (RemoteException e) {
                throw new OutcomesException("Error in CycleSvcBean.get().", e);
            }

            if (dto.getDrugBiologics() != null) {
                if (dto.getDrugBiologics().isEmpty()) {
                    result.setDrugBiologics(DrugBiologicSvcBean.getInstance().search(cctx, result.getIdentifier()));
                } else {
                    List<DrugBiologicSvcDto> dbList = new ArrayList<DrugBiologicSvcDto>();
                    for (DrugBiologicSvcDto db : dto.getDrugBiologics()) {
                        dbList.add(DrugBiologicSvcBean.getInstance().get(db, cctx, result.getIdentifier()));
                    }
                    result.setDrugBiologics(dbList);
                }
            }

            if (dto.getSurgeries() != null) {
                if (dto.getSurgeries().isEmpty()) {
                    result.setSurgeries(SurgerySvcBean.getInstance().search(cctx, result.getIdentifier()));
                } else {
                    List<SurgerySvcDto> sList = new ArrayList<SurgerySvcDto>();
                    for (SurgerySvcDto s : dto.getSurgeries()) {
                        sList.add(SurgerySvcBean.getInstance().get(s, cctx, result.getIdentifier()));
                    }
                    result.setSurgeries(sList);
                }
            }

            if (dto.getRadiations() != null) {
                if (dto.getRadiations().isEmpty()) {
                    result.setRadiations(RadiationSvcBean.getInstance().search(cctx, result.getIdentifier()));
                } else {
                    List<RadiationSvcDto> rList = new ArrayList<RadiationSvcDto>();
                    for (RadiationSvcDto r : dto.getRadiations()) {
                        rList.add(RadiationSvcBean.getInstance().get(r, cctx, result.getIdentifier()));
                    }
                    result.setRadiations(rList);
                }
            }
        }
        return result;
    }

    private CycleSvcDto getCycleSvcDto(PerformedActivityDto paDto) {
        CycleSvcDto result = new CycleSvcDto();
        result.setIdentifier(paDto.getIdentifier());
        result.setName(paDto.getName());
        result.setCreateDate(paDto.getActualDateRange().getLow());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CycleSvcDto put(CycleSvcDto dto, SvcContext cctx, Ii parentIi) throws OutcomesException {
        CycleSvcDto result = super.put(dto, cctx, parentIi);

        if (dto.getDrugBiologics() != null) {
            List<DrugBiologicSvcDto> dbList = new ArrayList<DrugBiologicSvcDto>();
            for (DrugBiologicSvcDto db : dto.getDrugBiologics()) {
                dbList.add(DrugBiologicSvcBean.getInstance().put(db, cctx, result.getIdentifier()));
            }
            result.setDrugBiologics(dbList);
        }

        if (dto.getSurgeries() != null) {
            List<SurgerySvcDto> sList = new ArrayList<SurgerySvcDto>();
            for (SurgerySvcDto s : dto.getSurgeries()) {
                sList.add(SurgerySvcBean.getInstance().put(s, cctx, result.getIdentifier()));
            }
            result.setSurgeries(sList);
        }

        if (dto.getRadiations() != null) {
            List<RadiationSvcDto> rList = new ArrayList<RadiationSvcDto>();
            for (RadiationSvcDto r : dto.getRadiations()) {
                rList.add(RadiationSvcBean.getInstance().put(r, cctx, result.getIdentifier()));
            }
            result.setRadiations(rList);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CycleSvcDto create(CycleSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        businessValidation(dto, cctx);
        try {
        PerformedActivityDto paDto = new PerformedActivityDto();
        Ivl<Ts> courseDate = new Ivl<Ts>();
        courseDate.setLow(dto.getCreateDate());
        paDto.setActualDateRange(courseDate);
        paDto.setName(dto.getName());
        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.getByCode("Course")));
        paDto.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
        paDto.setStudySubjectIdentifier(cctx.getStudySubjectIi());
        paDto = cctx.getPerformedActivityService().create(paDto);

        ActivityRelationshipDto arDto = new ActivityRelationshipDto();
        arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
        arDto.setSourcePerformedActivityIdentifier(parentIi);
        arDto.setTargetPerformedActivityIdentifier(paDto.getIdentifier());
        cctx.getActivityRelationshipService().create(arDto);

        dto.setIdentifier(paDto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error in CycleSvcBean.create().", e);
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CycleSvcDto delete(CycleSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        throw new OutcomesException("Delete method is not implemented for CycleSvcBean");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CycleSvcDto update(CycleSvcDto dto, SvcContext cctx, Ii parentIi)
            throws OutcomesException {
        businessValidation(dto, cctx);
        try {
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                throw new OutcomesException("Cycle identifier is null ");
            }
            if (OutcomesUtil.getPerformedActivityByCategoryCode(ActivityCategoryCode.COURSE, cctx, 
                    cctx.getStudySubjectIi(), dto.getIdentifier())) {
                PerformedActivityDto paDto = cctx.getPerformedActivityService().get(dto.getIdentifier());
                List<ActivityRelationshipDto> arList = cctx.getActivityRelationshipService().
                getByTargetPerformedActivity(paDto.getIdentifier(), CdConverter.convertStringToCd(
                        ActivityRelationshipTypeCode.COMP.getCode()));
                if (!arList.get(0).getSourcePerformedActivityIdentifier().getExtension().equals(
                        parentIi.getExtension())) {
                    throw new OutcomesException("ParentIi is not same as TreatmentRegimenIdentifier"
                            + " in CycleSvcBean.update(). ");
                }
                paDto.getActualDateRange().setLow(dto.getCreateDate());
                paDto.setName(dto.getName());
                cctx.getPerformedActivityService().update(paDto);
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in CycleSvcBean.update().", e);
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
    private void businessValidation(CycleSvcDto dto, SvcContext cctx) throws OutcomesException {
        if (dto.getCreateDate() != null) {
            boolean validDate = OutcomesUtil.checkValidDate(dto.getCreateDate().getValue());
            if (!validDate) {
                throw new OutcomesFieldException(getClass(), "createDate", "Please Enter Current or Past Date.");
            }
        }
        Date offTreatDate = null;
        Date diagnosisDate = null;
        Date deathDate = null;
        
        try {
            offTreatDate = OutcomesUtil.getEarliestOffTreatmentDate(cctx);
            diagnosisDate = OutcomesUtil.getDiagnosisDate(cctx);
            deathDate = OutcomesUtil.getEarliestDeathDate(cctx);
        } catch (RemoteException e) {
            throw new OutcomesException("Error in CycleSvcBean.businessValidation().", e);
        }
        
        if (diagnosisDate != null && dto.getCreateDate().getValue().before(diagnosisDate)) {
            throw new OutcomesFieldException(getClass(), "createDate", "Date can't be less than Diagnosis Date " 
                    + AccrualUtil.dateToMDY(diagnosisDate));
        } 
        if (deathDate != null && dto.getCreateDate().getValue().after(deathDate)) {
            throw new OutcomesFieldException(getClass(), "createDate", "Date can't be greater than Death Date " 
                    + AccrualUtil.dateToMDY(deathDate));
        } 
        if (offTreatDate != null && dto.getCreateDate().getValue().after(offTreatDate)) {
            throw new OutcomesFieldException(getClass(), "createDate", 
                    "Date can't be greater than Offtreatment Date " + AccrualUtil.dateToMDY(offTreatDate));
        }

    }

}
