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
import gov.nih.nci.accrual.web.dto.util.ParticipantOutcomesWebDto;
import gov.nih.nci.accrual.web.enums.ResponseInds;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * @author Kalpana Guthikonda
 * @since 10/28/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength",
    "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods", "PMD.TooManyFields" , 
        "PMD.AvoidDeeplyNestedIfStmts" })
        public class ParticipantOutcomesAction extends AbstractListEditAccrualAction<ParticipantOutcomesWebDto> {

    private static final long serialVersionUID = 1L;
    private ParticipantOutcomesWebDto targetOutcome = new ParticipantOutcomesWebDto();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        try {
            loadTreatmentPlans();
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.execute();
        }        
        return super.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        setDisplayTagList(new ArrayList<ParticipantOutcomesWebDto>());
        try {
            List<PerformedObservationDto> poList = performedActivitySvc.getPerformedObservationByStudySubject(
                                                               getParticipantIi());
            List<PerformedObservationResultDto> participantOutcomesList = null;
            List<PerformedObservationResultDto> diseaseStatusList = null;
            List<PerformedObservationResultDto> diseaseProgressionList = null;
            List<PerformedObservationResultDto> bestResponseList = null;
            List<PerformedObservationResultDto> evidenceofDiseaseList = null;
            List<ActivityRelationshipDto> arList = null;
            ParticipantOutcomesWebDto webdto = new ParticipantOutcomesWebDto();
            for (PerformedObservationDto poBean : poList) {
                if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.PARTICIPANT_OUTCOMES.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    webdto.setEvaluationDate(po.getActualDateRange().getLow());
                    participantOutcomesList = performedObservationResultSvc.
                                      getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.DISEASE_STATUS.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    webdto.setDiseaseStatusDate(po.getActualDateRange().getLow());
                    List<Cd> cds = new ArrayList<Cd>();
                    cds = DSetConverter.convertDsetToCdList(po.getMethodCode());
                    webdto.setAssessmentType(cds.get(0));
                    diseaseStatusList = performedObservationResultSvc.getPerformedObservationResultByPerformedActivity(
                            po.getIdentifier());

                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(
                        ActivityNameCode.DISEASE_PROGRESSION.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    webdto.setProgressionSite(po.getTargetSiteCode());
                    webdto.setProgressionDate(po.getActualDateRange().getLow());
                    diseaseProgressionList = performedObservationResultSvc.
                                   getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.BEST_RESPONSE.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    webdto.setBestResponseDate(po.getActualDateRange().getLow());
                    bestResponseList = performedObservationResultSvc.getPerformedObservationResultByPerformedActivity(
                            po.getIdentifier());
                } else if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                        && poBean.getNameCode().getCode().equals(ActivityNameCode.EVIDENCE_OF_DISEASE.getCode())) {
                    arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            poBean.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arList.get(0)
                            .getTargetPerformedActivityIdentifier());
                    evidenceofDiseaseList = performedObservationResultSvc.
                                   getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                    webdto.setDiseaseEvidence(evidenceofDiseaseList.get(0).getResultCode());
                    webdto.setTreatmentPlanId(arList.get(0).getSourcePerformedActivityIdentifier().getExtension());
                }

                if (participantOutcomesList != null && !participantOutcomesList.isEmpty() 
                        && diseaseStatusList != null && !diseaseStatusList.isEmpty()
                        && diseaseProgressionList != null && !diseaseProgressionList.isEmpty()
                        && bestResponseList != null && !bestResponseList.isEmpty()
                        && evidenceofDiseaseList != null && !evidenceofDiseaseList.isEmpty()) {
                    getDisplayTagList().add(new ParticipantOutcomesWebDto(
                            participantOutcomesList, diseaseStatusList, diseaseProgressionList, 
                            bestResponseList, webdto));
                    
                    participantOutcomesList = null;
                    diseaseStatusList = null;
                    diseaseProgressionList = null;
                    bestResponseList = null;
                }

            }


        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public String add() throws RemoteException {
        PerformedObservationDto dto = new PerformedObservationDto();
        Ivl<Ts> evalutionDate = new Ivl<Ts>();
        evalutionDate.setLow(targetOutcome.getEvaluationDate());
        dto.setActualDateRange(evalutionDate);
        dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Participant Outcomes")));
        dto.setStudyProtocolIdentifier(getSpIi());
        dto.setStudySubjectIdentifier(getParticipantIi());
        try {
            dto = performedActivitySvc.createPerformedObservation(dto);

            createPerformedObservationResult(dto);
            
            createDiseaseStatus();
            
            PerformedObservationDto dto3 = new PerformedObservationDto();
            dto3.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Disease Progression")));
            dto3.setTargetSiteCode(targetOutcome.getProgressionSite());
            Ivl<Ts> diseaseProgressionDate = new Ivl<Ts>();
            diseaseProgressionDate.setLow(targetOutcome.getProgressionDate());
            dto3.setActualDateRange(diseaseProgressionDate);
            dto3.setStudyProtocolIdentifier(getSpIi());
            dto3.setStudySubjectIdentifier(getParticipantIi());

            dto3 = performedActivitySvc.createPerformedObservation(dto3);

            PerformedObservationResultDto porDto5 = new PerformedObservationResultDto();
            porDto5.setPerformedObservationIdentifier(dto3.getIdentifier());
            porDto5.setResultCode(targetOutcome.getProgressionInd());
            porDto5.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.create(porDto5);
            
            ActivityRelationshipDto arDto3 = new ActivityRelationshipDto();
            arDto3.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.COMP)));
            arDto3.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(targetOutcome.getTreatmentPlanId()));
            arDto3.setTargetPerformedActivityIdentifier(dto3.getIdentifier());
            activityRelationshipSvc.create(arDto3);
            
            PerformedObservationDto dto5 = new PerformedObservationDto();
            Ivl<Ts> bestResponseDate = new Ivl<Ts>();
            bestResponseDate.setLow(targetOutcome.getBestResponseDate());
            dto5.setActualDateRange(bestResponseDate);
            dto5.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Best Response")));
            dto5.setStudyProtocolIdentifier(getSpIi());
            dto5.setStudySubjectIdentifier(getParticipantIi());

            dto5 = performedActivitySvc.createPerformedObservation(dto5);

            PerformedObservationResultDto porDto7 = new PerformedObservationResultDto();
            porDto7.setPerformedObservationIdentifier(dto5.getIdentifier());
            porDto7.setResultCode(targetOutcome.getBestResponse());
            porDto7.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.create(porDto7);

            ActivityRelationshipDto arDto5 = new ActivityRelationshipDto();
            arDto5.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.COMP)));
            arDto5.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(targetOutcome.getTreatmentPlanId()));
            arDto5.setTargetPerformedActivityIdentifier(dto5.getIdentifier());
            activityRelationshipSvc.create(arDto5);
            
            PerformedObservationDto dto6 = new PerformedObservationDto();
            dto6.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Evidence of Disease")));
            dto6.setStudyProtocolIdentifier(getSpIi());
            dto6.setStudySubjectIdentifier(getParticipantIi());

            dto6 = performedActivitySvc.createPerformedObservation(dto6);

            PerformedObservationResultDto porDto8 = new PerformedObservationResultDto();
            porDto8.setPerformedObservationIdentifier(dto6.getIdentifier());
            porDto8.setResultCode(targetOutcome.getDiseaseEvidence());
            porDto8.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.create(porDto8);

            ActivityRelationshipDto arDto6 = new ActivityRelationshipDto();
            arDto6.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.COMP)));
            arDto6.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(targetOutcome.getTreatmentPlanId()));
            arDto6.setTargetPerformedActivityIdentifier(dto6.getIdentifier());
            activityRelationshipSvc.create(arDto6);

        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        } catch (DataFormatException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        }
        return super.add();
    }

    private void createDiseaseStatus() throws RemoteException, DataFormatException {
        PerformedObservationDto dto2 = new PerformedObservationDto();
        Ivl<Ts> diseaseStatusDate = new Ivl<Ts>();
        diseaseStatusDate.setLow(targetOutcome.getDiseaseStatusDate());
        dto2.setActualDateRange(diseaseStatusDate);
        dto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.getByCode("Disease Status")));
        List<Cd> cds = new ArrayList<Cd>();
        cds.add(targetOutcome.getAssessmentType());
        dto2.setMethodCode(DSetConverter.convertCdListToDSet(cds));
        dto2.setStudyProtocolIdentifier(getSpIi());
        dto2.setStudySubjectIdentifier(getParticipantIi());

        dto2 = performedActivitySvc.createPerformedObservation(dto2);

        PerformedObservationResultDto porDto4 = new PerformedObservationResultDto();
        porDto4.setPerformedObservationIdentifier(dto2.getIdentifier());
        porDto4.setResultCode(targetOutcome.getDiseaseStatus());
        porDto4.setStudyProtocolIdentifier(getSpIi());
        performedObservationResultSvc.create(porDto4);

        ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
        arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.COMP)));
        arDto2.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(targetOutcome.getTreatmentPlanId()));
        arDto2.setTargetPerformedActivityIdentifier(dto2.getIdentifier());
        activityRelationshipSvc.create(arDto2);
    }

    private void createPerformedObservationResult(PerformedObservationDto dto) throws RemoteException {
        PerformedObservationResultDto porDto1 = new PerformedObservationResultDto();
        porDto1.setPerformedObservationIdentifier(dto.getIdentifier());
        porDto1.setResultCode(targetOutcome.getVitalStatus());
        porDto1.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.getByCode("Vital Status")));
        porDto1.setStudyProtocolIdentifier(getSpIi());
        performedObservationResultSvc.create(porDto1);

        PerformedObservationResultDto porDto2 = new PerformedObservationResultDto();
        porDto2.setPerformedObservationIdentifier(dto.getIdentifier());
        if (targetOutcome.getResponseInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
            porDto2.setResultIndicator(BlConverter.convertToBl(true));
        } else if (targetOutcome.getResponseInd().getCode().equalsIgnoreCase(ResponseInds.NO.getCode())) {
            porDto2.setResultIndicator(BlConverter.convertToBl(false));
        }
        porDto2.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                                        getByCode("Evaluable for Response")));
        porDto2.setStudyProtocolIdentifier(getSpIi());
        performedObservationResultSvc.create(porDto2);

        PerformedObservationResultDto porDto3 = new PerformedObservationResultDto();
        porDto3.setPerformedObservationIdentifier(dto.getIdentifier());
        porDto3.setResultCode(targetOutcome.getRecurrenceInd());
        porDto3.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.
                                    getByCode("Disease Recurrence Indicator")));
        porDto3.setStudyProtocolIdentifier(getSpIi());

        if (targetOutcome.getRecurrenceInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
            Ivl<Ts> resultDateRange = new Ivl<Ts>();
            resultDateRange.setLow(targetOutcome.getRecurrenceDate());
            porDto3.setResultDateRange(resultDateRange);
        }
        performedObservationResultSvc.create(porDto3);

        ActivityRelationshipDto arDto1 = new ActivityRelationshipDto();
        arDto1.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.getByCode(AccrualConstants.COMP)));
        arDto1.setSourcePerformedActivityIdentifier(IiConverter.convertToIi(targetOutcome.getTreatmentPlanId()));
        arDto1.setTargetPerformedActivityIdentifier(dto.getIdentifier());
        activityRelationshipSvc.create(arDto1);
    }

    /**
     * @param targetOutcome the targetOutcome to set
     */
    public void setTargetOutcome(ParticipantOutcomesWebDto targetOutcome) {
        this.targetOutcome = targetOutcome;
    }

    /**
     * @return the targetOutcome
     */
    @VisitorFieldValidator(message = "> ")
    public ParticipantOutcomesWebDto getTargetOutcome() {
        return targetOutcome;
    }
}
