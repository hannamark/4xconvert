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

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.web.dto.util.StagingWebDto;
import gov.nih.nci.accrual.web.dto.util.TumorMarkerWebDto;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * The Class StagingAction.
 *
 * @author Lisa Kelley
 * @since 10/30/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.AvoidDeeplyNestedIfStmts" })
public class StagingAction extends AbstractListEditAccrualAction<TumorMarkerWebDto> {

    private static final long serialVersionUID = 1L;
    private StagingWebDto staging = new StagingWebDto();

    /**
     * {@inheritDoc}
     */
    @Override
    @SkipValidation
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    public String execute() {
    try {
       List<PerformedObservationDto> psmList = performedActivitySvc.getPerformedObservationByStudySubject(
                                               getParticipantIi());
      for (PerformedObservationDto psm : psmList) {
        if (psm.getNameCode() != null && psm.getNameCode().getCode() != null
            && psm.getNameCode().getCode().equals(ActivityNameCode.STAGING.getCode())) {
           staging.setId(psm.getIdentifier());
           if (psm.getMethodCode() != null) {
            List<Cd> cds = new ArrayList<Cd>();
            cds = DSetConverter.convertDsetToCdList(psm.getMethodCode());
            staging.setMethod(cds.get(0));
           }
           List<PerformedClinicalResultDto> pcrList = 
             performedObservationResultSvc.getPerformedClinicalResultByPerformedActivity(psm.getIdentifier());
           if (!pcrList.isEmpty()) {
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
       }
     }
    } catch (RemoteException e) {
       LOG.error("Error in StagingAction.execute() " + e.getLocalizedMessage());
       return ERROR;
    }
     return super.execute();
    }

    /**
     * Save user entries.
     * @return result for next action
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
    public String save() {
     try {  
      if (staging.getId() != null && staging.getId().getExtension() != null 
          && !staging.getId().getExtension().equals("")) {
           PerformedObservationDto dto = performedActivitySvc.getPerformedObservation(staging.getId());
           List<Cd> cds = new ArrayList<Cd>();
           cds.add(staging.getMethod());
           dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
           performedActivitySvc.updatePerformedObservation(dto);
           List<PerformedClinicalResultDto> pcrList = 
              performedObservationResultSvc.getPerformedClinicalResultByPerformedActivity(dto.getIdentifier());
           if (!pcrList.isEmpty()) {
               for (PerformedClinicalResultDto pcr : pcrList) {
                if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.T.getCode())) {
                  pcr.setResultText(staging.getTt());
                } 
                if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.N.getCode())) {
                   pcr.setResultText(staging.getNn());
                } 
                if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.M.getCode())) {
                   pcr.setResultText(staging.getMm());
                } 
                if (pcr.getTypeCode().getCode().equals(PerformedObservationResultTypeCode.STAGE.getCode())) {
                   pcr.setResultText(staging.getStage());
                } 
                pcr.setStageCodingSystem(staging.getSystem());  
                performedObservationResultSvc.updatePerformedClinicalResult(pcr);
              }
             }
       } else {
        PerformedObservationDto dto = new PerformedObservationDto();
        dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.STAGING));
        List<Cd> cds = new ArrayList<Cd>();
        cds.add(staging.getMethod());
        dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
        dto.setStudyProtocolIdentifier(getSpIi());
        dto.setStudySubjectIdentifier(getParticipantIi());
        dto = performedActivitySvc.createPerformedObservation(dto);
      
        //T 
        PerformedClinicalResultDto pcrDto1 = new PerformedClinicalResultDto();
        pcrDto1.setPerformedObservationIdentifier(dto.getIdentifier());
        pcrDto1.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.T));
        pcrDto1.setResultText(staging.getTt());
        pcrDto1.setStudyProtocolIdentifier(getSpIi());
        pcrDto1.setStageCodingSystem(staging.getSystem());
        performedObservationResultSvc.createPerformedClinicalResult(pcrDto1);
        //N
        PerformedClinicalResultDto pcrDto2 = new PerformedClinicalResultDto();
        pcrDto2.setPerformedObservationIdentifier(dto.getIdentifier());
        pcrDto2.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.N));
        pcrDto2.setResultText(staging.getNn());
        pcrDto2.setStageCodingSystem(staging.getSystem());
        pcrDto2.setStudyProtocolIdentifier(getSpIi());
        performedObservationResultSvc.createPerformedClinicalResult(pcrDto2);
        //M
        PerformedClinicalResultDto pcrDto3 = new PerformedClinicalResultDto();
        pcrDto3.setPerformedObservationIdentifier(dto.getIdentifier());
        pcrDto3.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.M));
        pcrDto3.setResultText(staging.getMm());
        pcrDto3.setStudyProtocolIdentifier(getSpIi());
        pcrDto3.setStageCodingSystem(staging.getSystem());
        performedObservationResultSvc.createPerformedClinicalResult(pcrDto3);
        //Stage
        PerformedClinicalResultDto pcrDto4 = new PerformedClinicalResultDto();
        pcrDto4.setPerformedObservationIdentifier(dto.getIdentifier());
        pcrDto4.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.STAGE));
        pcrDto4.setResultText(staging.getStage());
        pcrDto4.setStudyProtocolIdentifier(getSpIi());
        pcrDto4.setStageCodingSystem(staging.getSystem());
        performedObservationResultSvc.createPerformedClinicalResult(pcrDto4);
      }
      
    } catch (RemoteException re) {
      addActionError("Error saving the  Staging." + re.getLocalizedMessage());
      return INPUT;
    } catch (DataFormatException dfe) {
      addActionError("Error saving the  Staging." + dfe.getLocalizedMessage());
      return INPUT;
    }
    return this.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
     List<TumorMarkerWebDto> tumorMarkerList = new ArrayList<TumorMarkerWebDto>();
     try {
            List<PerformedActivityDto> paList = performedActivitySvc.getByStudySubject(getParticipantIi());
            for (PerformedActivityDto pa : paList) {
                if (pa.getCategoryCode() != null && pa.getCategoryCode().getCode() != null
                        && pa.getCategoryCode().getCode().equals(ActivityCategoryCode.TUMOR_MARKER.getCode())) {
                 TumorMarkerWebDto item = new TumorMarkerWebDto();
                 if (!PAUtil.isStNull(pa.getName())) {
                  item.setTumorMarker(CdConverter.convertStringToCd(pa.getName().getValue()));
                 }
                 List<PerformedObservationResultDto> performedResultList = 
                   performedObservationResultSvc.getPerformedObservationResultByPerformedActivity(pa.getIdentifier());
                  if (!performedResultList.isEmpty()) {
                     for (PerformedObservationResultDto resultDTO : performedResultList) {
                      if (resultDTO.getResultQuantity() != null 
                          && resultDTO.getResultQuantity().getUnit() != null 
                          && resultDTO.getResultQuantity().getValue() != null) {
                           item.setTmvUom(resultDTO.getResultQuantity());
                           item.setTumorMarkerValue(
                            StConverter.convertToSt(resultDTO.getResultQuantity().getValue().toString()));
                      } else if (resultDTO.getResultText() != null) {
                          item.setTumorMarkerValue(resultDTO.getResultText()); 
                      }
                     } 
                   }
                   tumorMarkerList.add(item);
                }
            }
           setDisplayTagList(tumorMarkerList);
           
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
        }
    }

    /**
     * @return the staging form data
     */
    @VisitorFieldValidator(message = "> ")
    public StagingWebDto getStaging() {
        return staging;
    }

    /**
     * @param staging the staging form data
     */
    public void setStaging(StagingWebDto staging) {
        this.staging = staging;
    }
}