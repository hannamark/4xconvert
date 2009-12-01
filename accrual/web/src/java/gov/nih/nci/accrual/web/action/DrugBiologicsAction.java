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
import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.accrual.web.dto.util.DrugBiologicsWebDto;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.ActivityRelationshipTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * The Class DrugBiologicsAction.
 *
 * @author Kalpana Guthikonda
 * @since 10/28/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class DrugBiologicsAction extends AbstractListEditAccrualAction<DrugBiologicsWebDto> {

    private static final long serialVersionUID = 1L;
    private DrugBiologicsWebDto drugBiologic = new DrugBiologicsWebDto();

    /**
     * {@inheritDoc}
     */
    @Override
    public Epoch getEpoch() {
        return Epoch.TREATMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        setDisplayTagList(new ArrayList<DrugBiologicsWebDto>());
        try {
            List<PerformedSubstanceAdministrationDto> psaList = performedActivitySvc.
            getPerformedSubstanceAdministrationByStudySubject(getParticipantIi());
            
            List<PerformedObservationResultDto>porList = null;
            DrugBiologicsWebDto webDto = new DrugBiologicsWebDto();
            
            for (PerformedSubstanceAdministrationDto psaDto : psaList) {
                if (psaDto.getCategoryCode().getCode().equals(ActivityCategoryCode.DRUG_BIOLOGIC.getCode())) {
                    List<ActivityRelationshipDto> arList = activityRelationshipSvc.getByTargetPerformedActivity(
                            psaDto.getIdentifier(), CdConverter.convertStringToCd(AccrualConstants.COMP));
                    arList = activityRelationshipSvc.getBySourcePerformedActivity(
                            arList.get(0).getTargetPerformedActivityIdentifier(), 
                            CdConverter.convertStringToCd(AccrualConstants.COMP));
                    for (ActivityRelationshipDto arDto : arList) {
                        PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arDto
                                .getTargetPerformedActivityIdentifier());
                        if (po.getNameCode().getCode().equals(ActivityNameCode.HEIGHT.getCode())) {
                            porList = performedObservationResultSvc.
                            getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                            webDto.setHeight(porList.get(0).getResultQuantity());
                        } else if (po.getNameCode().getCode().equals(ActivityNameCode.WEIGHT.getCode())) {
                            porList = performedObservationResultSvc.
                            getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                            webDto.setWeight(porList.get(0).getResultQuantity());
                        } else if (po.getNameCode().getCode().equals(ActivityNameCode.BSA.getCode())) {
                            porList = performedObservationResultSvc.
                            getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                            webDto.setBsa(porList.get(0).getResultQuantity());
                        }
                    }
                    InterventionDTO dto = interventionSvc.get(psaDto.getInterventionIdentifier());
                    webDto.setDrugName(dto.getName());
                    webDto.setInterventionId(dto.getIdentifier());
                    getDisplayTagList().add(new DrugBiologicsWebDto(psaDto, webDto));
                }
            }
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() {
        DrugBiologicsWebDto.validate(drugBiologic, this);
        if (hasActionErrors() || hasFieldErrors()) {
            setCurrentAction(CA_CREATE);
            return INPUT;
        }
        try {
            PerformedSubstanceAdministrationDto psaDto = new PerformedSubstanceAdministrationDto();         
            psaDto.setInterventionIdentifier(drugBiologic.getInterventionId());
            psaDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.DRUG_BIOLOGIC));
            psaDto.setDose(drugBiologic.getDose());
            psaDto.setRouteOfAdministrationCode(drugBiologic.getDoseRoute());
            psaDto.setDoseFrequencyCode(drugBiologic.getDoseFreq());
            psaDto.setDoseDuration(drugBiologic.getDoseDur());
            psaDto.setDoseTotal(drugBiologic.getDoseTotal());
            psaDto.setDoseModificationType(drugBiologic.getDoseModType());
            psaDto.setStudyProtocolIdentifier(getSpIi());
            psaDto.setStudySubjectIdentifier(getParticipantIi());
            
            psaDto = performedActivitySvc.createPerformedSubstanceAdministration(psaDto);
            
            ActivityRelationshipDto arDto = new ActivityRelationshipDto();
            arDto.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto.setSourcePerformedActivityIdentifier(getCourseIi());
            arDto.setTargetPerformedActivityIdentifier(psaDto.getIdentifier());
            activityRelationshipSvc.create(arDto);
            
            PerformedObservationDto poDto1 = new PerformedObservationDto();
            poDto1.setNameCode(CdConverter.convertToCd(ActivityNameCode.HEIGHT));
            poDto1.setStudyProtocolIdentifier(getSpIi());
            poDto1.setStudySubjectIdentifier(getParticipantIi());
            poDto1 = performedActivitySvc.createPerformedObservation(poDto1);
            
            PerformedObservationResultDto porDto1 = new PerformedObservationResultDto();
            porDto1.setPerformedObservationIdentifier(poDto1.getIdentifier());
            porDto1.setResultQuantity(drugBiologic.getHeight());
            porDto1.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.create(porDto1);
            
            ActivityRelationshipDto arDto2 = new ActivityRelationshipDto();
            arDto2.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto2.setSourcePerformedActivityIdentifier(psaDto.getIdentifier());
            arDto2.setTargetPerformedActivityIdentifier(poDto1.getIdentifier());
            activityRelationshipSvc.create(arDto2);
            
            PerformedObservationDto poDto2 = new PerformedObservationDto();
            poDto2.setNameCode(CdConverter.convertToCd(ActivityNameCode.WEIGHT));
            poDto2.setStudyProtocolIdentifier(getSpIi());
            poDto2.setStudySubjectIdentifier(getParticipantIi());
            poDto2 = performedActivitySvc.createPerformedObservation(poDto2);
            
            PerformedObservationResultDto porDto2 = new PerformedObservationResultDto();
            porDto2.setPerformedObservationIdentifier(poDto2.getIdentifier());
            porDto2.setResultQuantity(drugBiologic.getWeight());
            porDto2.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.create(porDto2);
            
            ActivityRelationshipDto arDto3 = new ActivityRelationshipDto();
            arDto3.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto3.setSourcePerformedActivityIdentifier(psaDto.getIdentifier());
            arDto3.setTargetPerformedActivityIdentifier(poDto2.getIdentifier());
            activityRelationshipSvc.create(arDto3);
            
            PerformedObservationDto poDto3 = new PerformedObservationDto();
            poDto3.setNameCode(CdConverter.convertToCd(ActivityNameCode.BSA));
            poDto3.setStudyProtocolIdentifier(getSpIi());
            poDto3.setStudySubjectIdentifier(getParticipantIi());
            poDto3 = performedActivitySvc.createPerformedObservation(poDto3);
            
            PerformedObservationResultDto porDto3 = new PerformedObservationResultDto();
            porDto3.setPerformedObservationIdentifier(poDto3.getIdentifier());
            drugBiologic.getBsa().setUnit("m2");
            porDto3.setResultQuantity(drugBiologic.getBsa());
            porDto3.setStudyProtocolIdentifier(getSpIi());
            performedObservationResultSvc.create(porDto3);
            
            ActivityRelationshipDto arDto4 = new ActivityRelationshipDto();
            arDto4.setTypeCode(CdConverter.convertToCd(ActivityRelationshipTypeCode.COMP));
            arDto4.setSourcePerformedActivityIdentifier(psaDto.getIdentifier());
            arDto4.setTargetPerformedActivityIdentifier(poDto3.getIdentifier());
            activityRelationshipSvc.create(arDto4);            
            
            return super.add();
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            setCurrentAction(CA_CREATE);
            return INPUT;
        } catch (DataFormatException e) {
            addActionError(e.getLocalizedMessage());
            setCurrentAction(CA_CREATE);
            return INPUT;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        drugBiologic = null;
        try {
            loadDisplayList();
            for (DrugBiologicsWebDto drug : getDisplayTagList()) {
                if (drug.getId().getExtension().equals(getSelectedRowIdentifier())) {
                    drugBiologic = drug;
                }
            }
        } catch (Exception e) {
            drugBiologic = null;
            LOG.error("Error in DrugBiologicsAction.update().", e);
        }
        if (drugBiologic == null) {
            addActionError("Error retrieving drugBiologic info for update.");
            return execute();
        }
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws RemoteException {
        if (hasActionErrors() || hasFieldErrors()) {
            setCurrentAction(CA_CREATE);
            return INPUT;
        }
        try {
            List<PerformedObservationResultDto>porList = null;
            
            List<ActivityRelationshipDto> arList = activityRelationshipSvc.getByTargetPerformedActivity(
            IiConverter.convertToIi(getSelectedRowIdentifier()), CdConverter.convertStringToCd(AccrualConstants.COMP));
            
            arList = activityRelationshipSvc.getBySourcePerformedActivity(
                    arList.get(0).getTargetPerformedActivityIdentifier(), 
                    CdConverter.convertStringToCd(AccrualConstants.COMP));
            for (ActivityRelationshipDto arDto : arList) {
                PerformedObservationDto po = performedActivitySvc.getPerformedObservation(arDto
                        .getTargetPerformedActivityIdentifier());
                if (po.getNameCode().getCode().equals(ActivityNameCode.HEIGHT.getCode())) {
                    porList = performedObservationResultSvc.
                    getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                    porList.get(0).setResultQuantity(drugBiologic.getHeight());
                    performedObservationResultSvc.update(porList.get(0));
                } else if (po.getNameCode().getCode().equals(ActivityNameCode.WEIGHT.getCode())) {
                    porList = performedObservationResultSvc.
                    getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                    porList.get(0).setResultQuantity(drugBiologic.getWeight());
                    performedObservationResultSvc.update(porList.get(0));
                } else if (po.getNameCode().getCode().equals(ActivityNameCode.BSA.getCode())) {
                    porList = performedObservationResultSvc.
                    getPerformedObservationResultByPerformedActivity(po.getIdentifier());
                    porList.get(0).setResultQuantity(drugBiologic.getBsa());
                    performedObservationResultSvc.update(porList.get(0));
                }
            }
            PerformedSubstanceAdministrationDto psa = performedActivitySvc.
            getPerformedSubstanceAdministration(arList.get(0).getSourcePerformedActivityIdentifier());
            
            if (psa.getCategoryCode().getCode().equals(ActivityCategoryCode.DRUG_BIOLOGIC.getCode())) {
            psa.setInterventionIdentifier(drugBiologic.getInterventionId());            
            psa.setDose(drugBiologic.getDose());
            psa.setRouteOfAdministrationCode(drugBiologic.getDoseRoute());
            psa.setDoseFrequencyCode(drugBiologic.getDoseFreq());
            psa.setDoseDuration(drugBiologic.getDoseDur());
            psa.setDoseTotal(drugBiologic.getDoseTotal());
            psa.setDoseModificationType(drugBiologic.getDoseModType());
            
            performedActivitySvc.updatePerformedSubstanceAdministration(psa);
            }
            
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            setCurrentAction(CA_CREATE);
            return INPUT;
        } catch (DataFormatException e) {
            addActionError(e.getLocalizedMessage());
            setCurrentAction(CA_CREATE);
            return INPUT;
        }
        return super.edit();
    }

    /**
     * Gets the drug biologic.
     * @return the drug biologic
     */
    @VisitorFieldValidator(message = "> ")
    public DrugBiologicsWebDto getDrugBiologic() {
        return drugBiologic;
    }

    /**
     * Sets the drug biologic.
     * @param drugBiologic the new drug biologic
     */
    public void setDrugBiologic(DrugBiologicsWebDto drugBiologic) {
        this.drugBiologic = drugBiologic;
    }
}
