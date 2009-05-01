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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author Kalpana Guthikonda
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", 
    "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class InterventionalStudyDesignAction extends ActionSupport {

    private static final long serialVersionUID = -8139821069851279621L;
    private static final String OUTCOME = "outcome";
    private static final String FALSE = "false";
    private static final String OUTCOMEADD = "outcomeAdd";
    private static final int MAXIMUM_CHAR = 200;
    private static final int MAXIMUM_CHAR_OUTCOME = 254; 
    private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    private String subject;
    private String investigator;
    private String caregiver;
    private String outcomesassessor;
    private List<ISDesignDetailsWebDTO> outcomeList;
    private Long id = null;
    private String page;

    /**  
     * @return res
     */
    public String detailsQuery() {
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
            ispDTO = PaRegistry.getStudyProtocolService().getInterventionalStudyProtocol(studyProtocolIi);
            webDTO = setDesignDetailsDTO(ispDTO);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return "details";
    }

    /**  
     * @return res
     */
    public String update() {
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return "details";
        }
        try {  
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            InterventionalStudyProtocolDTO ispDTO = PaRegistry.getStudyProtocolService()
            .getInterventionalStudyProtocol(studyProtocolIi);
            ispDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(webDTO.getPhaseCode()))); 
            ispDTO.setPrimaryPurposeCode(
                    CdConverter.convertToCd(PrimaryPurposeCode.getByCode(webDTO.getPrimaryPurposeCode()))); 
            ispDTO.setBlindingSchemaCode(
                    CdConverter.convertToCd(BlindingSchemaCode.getByCode(webDTO.getBlindingSchemaCode()))); 
            ispDTO.setDesignConfigurationCode(
                    CdConverter.convertToCd(DesignConfigurationCode.getByCode(webDTO.getDesignConfigurationCode()))); 
            ispDTO.setNumberOfInterventionGroups(
                    IntConverter.convertToInt(webDTO.getNumberOfInterventionGroups()));
            ispDTO.setAllocationCode(
                    CdConverter.convertToCd(AllocationCode.getByCode(webDTO.getAllocationCode()))); 
            ispDTO.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(webDTO.getPrimaryPurposeOtherText()));
            ispDTO.setPhaseOtherText(
                    StConverter.convertToSt(webDTO.getPhaseOtherText()));
            ispDTO.setMaximumTargetAccrualNumber(
                    IntConverter.convertToInt(webDTO.getMaximumTargetAccrualNumber()));
            ispDTO.setStudyClassificationCode(
                    CdConverter.convertToCd(StudyClassificationCode.getByCode(webDTO.getStudyClassificationCode())));

            List<Cd> cds = new ArrayList<Cd>();
            if (webDTO.getBlindingSchemaCode() != null && !webDTO.getBlindingSchemaCode().equalsIgnoreCase("open")) {
                if (caregiver != null && !caregiver.equalsIgnoreCase(FALSE)) {
                    cds.add(CdConverter.convertStringToCd(caregiver));
                }
                if (investigator != null && !investigator.equalsIgnoreCase(FALSE)) {
                    cds.add(CdConverter.convertStringToCd(investigator));
                }
                if (outcomesassessor != null && !outcomesassessor.equalsIgnoreCase(FALSE)) {
                    cds.add(CdConverter.convertStringToCd(outcomesassessor));
                }
                if (subject != null && !subject.equalsIgnoreCase(FALSE)) {
                    cds.add(CdConverter.convertStringToCd(subject));
                }
            }
            ispDTO.setBlindedRoleCode(DSetConverter.convertCdListToDSet(cds));

            PaRegistry.getStudyProtocolService().updateInterventionalStudyProtocol(ispDTO);
            detailsQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }

    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(webDTO.getPrimaryPurposeCode())) {
            addFieldError("webDTO.primaryPurposeCode", getText("error.primary"));
        }
        
        if (webDTO.getPrimaryPurposeCode() != null && webDTO.getPrimaryPurposeCode().equalsIgnoreCase("Other") 
                && PAUtil.isEmpty(webDTO.getPrimaryPurposeOtherText())) {          
            addFieldError("webDTO.primaryPurposeOtherText", getText("error.comment"));
        }
        
        if (PAUtil.isNotEmpty(webDTO.getPrimaryPurposeOtherText())
            && webDTO.getPrimaryPurposeOtherText().length() > MAXIMUM_CHAR) {
          addFieldError("webDTO.primaryPurposeOtherText", getText("error.spType.other.maximumChar"));        
        }

        if (PAUtil.isEmpty(webDTO.getPhaseCode())) {
            addFieldError("webDTO.phaseCode", getText("error.phase"));
        }
        if (webDTO.getPhaseCode() != null && webDTO.getPhaseCode().equalsIgnoreCase("Other") 
                && PAUtil.isEmpty(webDTO.getPhaseOtherText())) {          
            addFieldError("webDTO.phaseOtherText", getText("error.comment"));
        }
        if (PAUtil.isNotEmpty(webDTO.getPhaseOtherText())
                && webDTO.getPhaseOtherText().length() > MAXIMUM_CHAR) {
              addFieldError("webDTO.phaseOtherText", getText("error.spType.other.maximumChar"));        
        }

        if (PAUtil.isEmpty(webDTO.getDesignConfigurationCode())) {
            addFieldError("webDTO.designConfigurationCode", getText("error.intervention"));
        }
        if (PAUtil.isEmpty(webDTO.getNumberOfInterventionGroups())) {
            addFieldError("webDTO.numberOfInterventionGroups", getText("error.arms"));
        }
        if (PAUtil.isNotEmpty(webDTO.getNumberOfInterventionGroups())) {
            try {
                Integer.valueOf(webDTO.getNumberOfInterventionGroups());
            } catch (NumberFormatException e) {
                addFieldError("webDTO.numberOfInterventionGroups", getText("error.numeric"));
            }
        }
        if (PAUtil.isEmpty(webDTO.getBlindingSchemaCode())) {
            addFieldError("webDTO.blindingSchemaCode", getText("error.masking"));
        }
        if (PAUtil.isEmpty(webDTO.getAllocationCode())) {
            addFieldError("webDTO.allocationCode", getText("error.allocation"));
        }
        if (PAUtil.isEmpty(webDTO.getMaximumTargetAccrualNumber())) {
            addFieldError("webDTO.maximumTargetAccrualNumber", getText("error.target.enrollment"));
        }
        if (PAUtil.isNotEmpty(webDTO.getMaximumTargetAccrualNumber())) {
            try {
                Integer.valueOf(webDTO.getMaximumTargetAccrualNumber());
            } catch (NumberFormatException e) {
                addFieldError("webDTO.maximumTargetAccrualNumber", getText("error.numeric"));
            }
        }
    }

    /**
     * @param ispDTO InterventionalStudyProtocolDTO
     * @return DesignDetailsWebDTO
     */
    @SuppressWarnings({"PMD.AvoidDeeplyNestedIfStmts" })
    private ISDesignDetailsWebDTO setDesignDetailsDTO(
            InterventionalStudyProtocolDTO ispDTO) {
        ISDesignDetailsWebDTO dto = new ISDesignDetailsWebDTO();
        if (ispDTO != null) {
            if (ispDTO.getPhaseCode() != null) {
                dto.setPhaseCode(ispDTO.getPhaseCode().getCode()); 
            }
            if (ispDTO.getPrimaryPurposeCode() != null) {
                dto.setPrimaryPurposeCode(ispDTO.getPrimaryPurposeCode().getCode());
            }
            if (ispDTO.getBlindingSchemaCode() != null) {
                dto.setBlindingSchemaCode(ispDTO.getBlindingSchemaCode().getCode());
            }
            if (ispDTO.getDesignConfigurationCode() != null) {
                dto.setDesignConfigurationCode(ispDTO.getDesignConfigurationCode().getCode());
            }
            if (ispDTO.getAllocationCode() != null) {
                dto.setAllocationCode(ispDTO.getAllocationCode().getCode());
            }
            if (ispDTO.getNumberOfInterventionGroups().getValue() != null) {
                dto.setNumberOfInterventionGroups(ispDTO.getNumberOfInterventionGroups().getValue().toString());
            }
            if (ispDTO.getPhaseOtherText() != null) {
                dto.setPhaseOtherText(ispDTO.getPhaseOtherText().getValue());
            }
            if (ispDTO.getPrimaryPurposeOtherText() != null) {
                dto.setPrimaryPurposeOtherText(ispDTO.getPrimaryPurposeOtherText().getValue());
            }
            if (ispDTO.getBlindedRoleCode() != null) {
                List<Cd> cds =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
                if (cds != null) {
                    for (Cd cd : cds) {
                        if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                            this.caregiver = BlindingRoleCode.CAREGIVER.getCode();
                            continue;
                        }
                        if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                            this.investigator = BlindingRoleCode.INVESTIGATOR.getCode();
                            continue;
                        }
                        if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                            this.outcomesassessor = BlindingRoleCode.OUTCOMES_ASSESSOR.getCode();
                            continue;
                        }
                        if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                            this.subject = BlindingRoleCode.SUBJECT.getCode();
                            continue;
                        }
                    }
                }
            }
            if (ispDTO.getMaximumTargetAccrualNumber().getValue() != null) {
                dto.setMaximumTargetAccrualNumber(
                        ispDTO.getMaximumTargetAccrualNumber().getValue().toString());
            }
            if (ispDTO.getStudyClassificationCode() != null) {
                dto.setStudyClassificationCode(ispDTO.getStudyClassificationCode().getCode());
            }
        }
        return dto;
    }
    /**
     * @return result
     */
    public String outcomeQuery()  {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            List<StudyOutcomeMeasureDTO> isoList = PaRegistry.getOutcomeMeasurService().
            getByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {
                outcomeList = new ArrayList<ISDesignDetailsWebDTO>();
                for (StudyOutcomeMeasureDTO dto : isoList) {
                    outcomeList.add(setOutcomeMeasureDTO(dto));
                }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        getText("No OutcomeMeasures exists for the trial."));
            }            

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOME;
    }

    /**
     * @return result
     */
    public String outcomeinput() {
        return OUTCOMEADD;
    }

    /**
     * @return result
     */
    public String outcomecreate() {
        enforceOutcomeBusinessRules();
        if (hasFieldErrors()) {
            return OUTCOMEADD;
        }
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyOutcomeMeasureDTO sgDTO = new StudyOutcomeMeasureDTO();
            sgDTO.setStudyProtocolIdentifier(studyProtocolIi);
            sgDTO.setName(StConverter.convertToSt(webDTO.getName()));
            updateBooleanValues();
            sgDTO.setPrimaryIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getPrimaryIndicator())));
            sgDTO.setSafetyIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSafetyIndicator())));
            sgDTO.setTimeFrame(StConverter.convertToSt(webDTO.getTimeFrame()));
            PaRegistry.getOutcomeMeasurService().create(sgDTO);
            outcomeQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            return OUTCOME;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return OUTCOMEADD;
        }         
    }
    /**
     * @return result
     */
    public String outcomeedit() {
        try {
            StudyOutcomeMeasureDTO  sgDTO =
                PaRegistry.getOutcomeMeasurService().get(IiConverter.convertToIi(id));
            webDTO = setOutcomeMeasureDTO(sgDTO);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOMEADD;
    }

    /**
     * @return result
     */
    public String outcomeupdate() {
        enforceOutcomeBusinessRules();
        if (hasFieldErrors()) {
            return OUTCOMEADD;
        }
        try {

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyOutcomeMeasureDTO  sgDTO = new StudyOutcomeMeasureDTO();
            sgDTO.setIdentifier(IiConverter.convertToIi(id));
            sgDTO.setStudyProtocolIdentifier(studyProtocolIi);
            sgDTO.setName(StConverter.convertToSt(webDTO.getName()));
            updateBooleanValues();
            sgDTO.setPrimaryIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getPrimaryIndicator())));
            sgDTO.setSafetyIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSafetyIndicator())));
            sgDTO.setTimeFrame(StConverter.convertToSt(webDTO.getTimeFrame()));
            PaRegistry.getOutcomeMeasurService().update(sgDTO);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            outcomeQuery();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return OUTCOMEADD;
        }
        return OUTCOME;
    }
    @SuppressWarnings({"PMD" })
    private void updateBooleanValues() {
      if (webDTO.getPrimaryIndicator().equalsIgnoreCase("Yes")) {
        webDTO.setPrimaryIndicator("true");
      } else {
        webDTO.setPrimaryIndicator(FALSE);
      }
      if (webDTO.getSafetyIndicator().equalsIgnoreCase("Yes")) {
        webDTO.setSafetyIndicator("true");
      } else {
        webDTO.setSafetyIndicator(FALSE);
      }
    }

    private void enforceOutcomeBusinessRules() {
        if (PAUtil.isEmpty(webDTO.getPrimaryIndicator())) {
            addFieldError("webDTO.primaryIndicator",
                    getText("error.outcome.primary"));
        }
        if (PAUtil.isEmpty(webDTO.getName())) {
            addFieldError("webDTO.name",
                    getText("error.outcome.description"));
        }
        if (PAUtil.isNotEmpty(webDTO.getName())
            && webDTO.getName().length() > MAXIMUM_CHAR_OUTCOME) {
          addFieldError("webDTO.name",
              getText("error.outcome.maximumChar"));        
        }
        if (PAUtil.isEmpty(webDTO.getTimeFrame())) {
            addFieldError("webDTO.timeFrame",
                    getText("error.outcome.timeFrame"));
        } 
        if (PAUtil.isNotEmpty(webDTO.getTimeFrame())
            && webDTO.getTimeFrame().length() > MAXIMUM_CHAR_OUTCOME) {
          addFieldError("webDTO.timeFrame",
              getText("error.outcome.maximumChar"));        
        }
        if (PAUtil.isEmpty(webDTO.getSafetyIndicator())) {
            addFieldError("webDTO.safetyIndicator",
                    getText("error.outcome.safety"));
        } 
    }

    /**
     * @return result
     */
    public String outcomedelete()  {

        try {
            PaRegistry.getOutcomeMeasurService().delete(IiConverter.convertToIi(id));
            outcomeQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOME;
    }
    @SuppressWarnings({"PMD" })
    private ISDesignDetailsWebDTO setOutcomeMeasureDTO(
            StudyOutcomeMeasureDTO dto) {
        ISDesignDetailsWebDTO webdto = new ISDesignDetailsWebDTO();
        if (dto != null) {
            if (dto.getPrimaryIndicator().getValue() != null) {
                if (dto.getPrimaryIndicator().getValue().toString().equalsIgnoreCase("true")) {
                  webdto.setPrimaryIndicator(("Yes"));
                } else {
                  webdto.setPrimaryIndicator("No");
                }               
            }
            if (dto.getName() != null) {
                webdto.setName(dto.getName().getValue());
            }
            if (dto.getTimeFrame() != null) {
                webdto.setTimeFrame(dto.getTimeFrame().getValue());
            }
            if (dto.getSafetyIndicator().getValue() != null) {
               if (dto.getSafetyIndicator().getValue().toString().equalsIgnoreCase("true")) {
                 webdto.setSafetyIndicator(("Yes"));
               } else {
                 webdto.setSafetyIndicator("No");
               }                
            }
            if (dto.getIdentifier() != null) {
                webdto.setId(dto.getIdentifier().getExtension());
            }

        }
        return webdto;
    }

    /**
     * @return webDTO
     */
    public ISDesignDetailsWebDTO getWebDTO() {
        return webDTO;
    }


    /**
     * @param webDTO webDTO
     */
    public void setWebDTO(ISDesignDetailsWebDTO webDTO) {
        this.webDTO = webDTO;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return investigator
     */
    public String getInvestigator() {
        return investigator;
    }

    /**
     * @param investigator investigator
     */
    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    /**
     * @return caregiver
     */
    public String getCaregiver() {
        return caregiver;
    }

    /**
     * @param caregiver caregiver
     */
    public void setCaregiver(String caregiver) {
        this.caregiver = caregiver;
    }

    /**
     * @return outcomesassessor
     */
    public String getOutcomesassessor() {
        return outcomesassessor;
    }

    /**
     * @param outcomesassessor outcomesassessor
     */
    public void setOutcomesassessor(String outcomesassessor) {
        this.outcomesassessor = outcomesassessor;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return outcomeList
     */
    public List<ISDesignDetailsWebDTO> getOutcomeList() {
        return outcomeList;
    }

    /**
     * @param outcomeList outcomeList
     */
    public void setOutcomeList(List<ISDesignDetailsWebDTO> outcomeList) {
        this.outcomeList = outcomeList;
    }

    /**
     * 
     * @return boolean value
     */
    public boolean isCaregiverChecked() {        
        if (BlindingRoleCode.CAREGIVER.getCode().equals(this.caregiver)) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @return boolean value
     */
    public boolean isInvestigatorChecked() { 
        if (BlindingRoleCode.INVESTIGATOR.getCode().equals(this.investigator)) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @return boolean value
     */
    public boolean isOutcomesAssessorChecked() { 
        if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(this.outcomesassessor)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return boolean value
     */
    public boolean isSubjectChecked() { 
        if (BlindingRoleCode.SUBJECT.getCode().equals(this.subject)) {
            return true;
        }
        return false;
    }

}
