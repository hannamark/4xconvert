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

package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.web.dto.util.DeathInfoWebDto;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * The Class DeathInformationAction.
 *
 * @author Kalpana Guthikonda
 * @since 10/28/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
public class DeathInformationAction extends AbstractEditAccrualAction<DeathInfoWebDto> {

    private static final long serialVersionUID = 1L;
    private DeathInfoWebDto deathInfo = new DeathInfoWebDto();

    /**
     * {@inheritDoc}
     */
    @SkipValidation
    @Override
    public String execute() {
        try {
            loadTreatmentPlans();
            List<PerformedObservationResultDto> deathCauseList = null;
            List<PerformedObservationResultDto> autopsyList = null;
            List<ActivityRelationshipDto> arList = null;

            List<PerformedObservationDto> poList = performedActivitySvc.getPerformedObservationByStudySubject(
                    getParticipantIi());
            for (PerformedObservationDto poBean : poList) {
                if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.DEATH_INFORMATION.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    deathCauseList = performedObservationResultSvc.
                                      getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                    deathInfo.setId(po.getIdentifier());
                    deathInfo.setOldTreatmentPlanId(arList.get(0).
                            getSourcePerformedActivityIdentifier().getExtension());
                    deathInfo.setTreatmentPlanId(arList.get(0).getSourcePerformedActivityIdentifier().getExtension());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.AUTOPSY_INFORMATION.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    deathInfo.setAutopsySite(po.getTargetSiteCode());
                    autopsyList = performedObservationResultSvc.getPerformedObservationResultByPerformedActivity(
                            po.getIdentifier());

                }
                if (deathCauseList != null && !deathCauseList.isEmpty()
                        && autopsyList != null && !autopsyList.isEmpty()) {
                    for (PerformedObservationResultDto dto : deathCauseList) {
                        if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                                      DEATH_CAUSE.getCode())) {
                            deathInfo.setCause(dto.getResultCode());
                        } else if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                                    DEATH_DATE.getCode())) {
                            deathInfo.setEventDate(dto.getResultDateRange().getLow());
                        }
                    }
                    for (PerformedObservationResultDto dto : autopsyList) {
                        if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                AUTOPSY_PERFORMED_INDICATOR.getCode())) {
                            deathInfo.setAutopsyInd(dto.getResultCode());
                        } else if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY.getCode())) {
                            deathInfo.setCauseByAutopsy(dto.getResultCode());
                        }
                    }

                    deathCauseList = null;
                    autopsyList = null;
                }

            }
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.execute();
        }
        return super.execute();
    }

    /**
     * Save user entries.
     * @return result for next action
     */
    @Override
    public String save() {
        DeathInfoWebDto.validate(deathInfo, this);
        if (hasActionErrors() || hasFieldErrors()) {
            return INPUT;
        }
        try {
            if (deathInfo.getId() != null && deathInfo.getId().getExtension() == null) {
                PerformedObservationDto dto = new PerformedObservationDto();
                dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.DEATH_INFORMATION));
                dto.setStudyProtocolIdentifier(getSpIi());
                dto.setStudySubjectIdentifier(getParticipantIi());
                dto = performedActivitySvc.createPerformedObservation(dto);

                PerformedObservationResultDto porDto1 = new PerformedObservationResultDto();
                porDto1.setPerformedObservationIdentifier(dto.getIdentifier());
                porDto1.setResultCode(deathInfo.getCause());
                porDto1.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.DEATH_CAUSE));
                porDto1.setStudyProtocolIdentifier(getSpIi());
                performedObservationResultSvc.create(porDto1);

                PerformedObservationResultDto porDto2 = new PerformedObservationResultDto();
                porDto2.setPerformedObservationIdentifier(dto.getIdentifier());
                if (porDto2.getResultDateRange() == null) {
                    porDto2.setResultDateRange(new Ivl<Ts>());
                }
                porDto2.getResultDateRange().setLow(deathInfo.getEventDate());
                porDto2.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.DEATH_DATE));
                porDto2.setStudyProtocolIdentifier(getSpIi());
                performedObservationResultSvc.create(porDto2);

                ActivityRelationshipDto arDto = new ActivityRelationshipDto();
                arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
                arDto.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(deathInfo.getTreatmentPlanId()));
                arDto.setTargetPerformedActivityIdentifier(dto.getIdentifier());
                activityRelationshipSvc.create(arDto);

                PerformedObservationDto dto2 = new PerformedObservationDto();
                dto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.AUTOPSY_INFORMATION));
                dto2.setTargetSiteCode(deathInfo.getAutopsySite());
                dto2.setStudyProtocolIdentifier(getSpIi());
                dto2.setStudySubjectIdentifier(getParticipantIi());
                dto2 = performedActivitySvc.createPerformedObservation(dto2);

                PerformedObservationResultDto porDto3 = new PerformedObservationResultDto();
                porDto3.setPerformedObservationIdentifier(dto2.getIdentifier());
                porDto3.setResultCode(deathInfo.getAutopsyInd());
                porDto3.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                        AUTOPSY_PERFORMED_INDICATOR));
                porDto3.setStudyProtocolIdentifier(getSpIi());
                performedObservationResultSvc.create(porDto3);

                PerformedObservationResultDto porDto4 = new PerformedObservationResultDto();
                porDto4.setPerformedObservationIdentifier(dto2.getIdentifier());
                porDto4.setResultCode(deathInfo.getCauseByAutopsy());
                porDto4.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                        CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY));
                porDto4.setStudyProtocolIdentifier(getSpIi());
                performedObservationResultSvc.create(porDto4);

                ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
                arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
                arDto2.setSourcePerformedActivityIdentifier(dto.getIdentifier());
                arDto2.setTargetPerformedActivityIdentifier(dto2.getIdentifier());
                activityRelationshipSvc.create(arDto2);

            } else {
                List<ActivityRelationshipDto> arList = activityRelationshipSvc.getByTargetPerformedActivity(
                        deathInfo.getId(), CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
                        arList = activityRelationshipSvc.getBySourcePerformedActivity(
                        arList.get(0).getTargetPerformedActivityIdentifier(), CdConverter.convertToCd(
                                ActivityRelationshipTypeCode.COMP));

                        PerformedObservationDto deathDto = performedActivitySvc.getPerformedObservation(
                                arList.get(0).getSourcePerformedActivityIdentifier());
                        PerformedObservationDto autopsyDto = performedActivitySvc.getPerformedObservation(
                                arList.get(0).getTargetPerformedActivityIdentifier());

                        List<PerformedObservationResultDto> deathCauseList = performedObservationResultSvc.
                        getPerformedObservationResultByPerformedActivity(deathDto.getIdentifier());

                        List<PerformedObservationResultDto> autopsyList = performedObservationResultSvc.
                        getPerformedObservationResultByPerformedActivity(autopsyDto.getIdentifier());

                        for (PerformedObservationResultDto dto : deathCauseList) {
                            if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                                          DEATH_CAUSE.getCode())) {
                                dto.setResultCode(deathInfo.getCause());
                            } else if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                                        DEATH_DATE.getCode())) {
                                dto.getResultDateRange().setLow(deathInfo.getEventDate());
                            }
                            performedObservationResultSvc.update(dto);
                        }
                        for (PerformedObservationResultDto dto : autopsyList) {
                            if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                    AUTOPSY_PERFORMED_INDICATOR.getCode())) {
                                dto.setResultCode(deathInfo.getAutopsyInd());
                            } else if (dto.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.
                                    CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY.getCode())) {
                                dto.setResultCode(deathInfo.getCauseByAutopsy());
                            }
                            performedObservationResultSvc.update(dto);
                        }
                        autopsyDto.setTargetSiteCode(deathInfo.getAutopsySite());
                        performedActivitySvc.updatePerformedObservation(autopsyDto);

                        if (!deathInfo.getTreatmentPlanId().equals(deathInfo.getOldTreatmentPlanId())) {
                            arList = activityRelationshipSvc.getBySourcePerformedActivity(IiConverter.convertToIi(
                                    deathInfo.getOldTreatmentPlanId()),
                                    CdConverter.convertStringToCd(AccrualConstants.COMP));
                            for (ActivityRelationshipDto ar : arList) {
                                if (ar.getTargetPerformedActivityIdentifier().getExtension().equals(
                                        deathDto.getIdentifier().getExtension()) && ar.getTypeCode().getCode().equals(
                                                AccrualConstants.COMP)) {
                                    ar.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(
                                            deathInfo.getTreatmentPlanId()));
                                    activityRelationshipSvc.update(ar);
                                }
                            }
                        }

            }
        } catch (Exception e) {
            addActionError("Error in DeathInformationAction save().  " + e.getLocalizedMessage());
            return INPUT;
        }
        return super.save();
    }

    /**
     * @param deathInfo the deathInfo to set
     */
    public void setDeathInfo(DeathInfoWebDto deathInfo) {
        this.deathInfo = deathInfo;
    }

    /**
     * @return the deathInfo
     */
    @VisitorFieldValidator(message = "> ")
    public DeathInfoWebDto getDeathInfo() {
        return deathInfo;
    }
}
