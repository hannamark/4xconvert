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

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kalpana Guthikonda
 * @since 11/12/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength",
  "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods", "PMD.TooManyFields" })
  public class EligibilityCriteriaAction extends ActionSupport {

  private static final String ELIGIBILITY = "eligibility";
  private static final String ELIGIBILITYADD = "eligibilityAdd";
  private static final int MAXIMUM_CHAR_POPULATION = 800;
  private static final int MAXIMUM_CHAR_DESCRIPTION = 400;
  private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
  private Long id = null;
  private String page;
  private String acceptHealthyVolunteersIndicator;
  private String eligibleGenderCode;
  private String maximumValue;
  private String maximumUnit;
  private String minimumValue;
  private String minimumUnit;
  private String eligibleGenderCodeId = null;
  private String maximumValueId = null;
  private String minimumValueId = null;
  private List<ISDesignDetailsWebDTO> eligibilityList = null;
  private List<ISDesignDetailsWebDTO> list = null;
  private static final int RECORDSVALUE = 3;
  private String studyPopulationDescription;
  private String samplingMethodCode;
 
  /**
   * @return res
   */
  @SuppressWarnings({ "PMD.AvoidDeeplyNestedIfStmts" })
  public String query() {

    try {
      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
      .getAttribute(Constants.STUDY_PROTOCOL_II);
      List<PlannedEligibilityCriterionDTO> pecList = PaRegistry.getPlannedActivityService()
      .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
      if (!(pecList.isEmpty())) {
        list = new ArrayList<ISDesignDetailsWebDTO>();
        for (PlannedEligibilityCriterionDTO dto : pecList) {
          list.add(setEligibilityDetailsDTO(dto));
        }
        if (list.size() > RECORDSVALUE) {
          eligibilityList = new ArrayList<ISDesignDetailsWebDTO>();
          for (ISDesignDetailsWebDTO weblist : list) {
            if (weblist.getCriterionName() == null
                || (!(weblist.getCriterionName().equalsIgnoreCase("GENDER")))
                && (!(weblist.getCriterionName().equalsIgnoreCase("MAXIMUM-AGE")))
                && (!(weblist.getCriterionName().equalsIgnoreCase("MINIMUM-AGE")))) {
              eligibilityList.add(weblist);
            }
          }
        }
      }
      StudyProtocolDTO spDTO = new StudyProtocolDTO();
      spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
      if (spDTO.getAcceptHealthyVolunteersIndicator().getValue() != null) {
        acceptHealthyVolunteersIndicator = spDTO.getAcceptHealthyVolunteersIndicator().getValue().toString();
      }
      StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext
      .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
      if (spqDTO.getStudyProtocolType().equalsIgnoreCase("ObservationalStudyProtocol")) {
        ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
        ospDTO = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(studyProtocolIi);
        if (ospDTO.getSamplingMethodCode().getCode() != null) {
          samplingMethodCode = ospDTO.getSamplingMethodCode().getCode().toString();
        }
        if (ospDTO.getStudyPopulationDescription().getValue() != null) {
          studyPopulationDescription = ospDTO.getStudyPopulationDescription().getValue().toString();
        }
      }

    } catch (PAException e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITY;
  }

  /**
   * @return res
   */
  public String save() {
    enforceBusinessRules();
    if (hasFieldErrors()) {
      return ELIGIBILITY;
    }
    try {
      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
      .getAttribute(Constants.STUDY_PROTOCOL_II);

      StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext
      .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
      if (spqDTO.getStudyProtocolType().equalsIgnoreCase("ObservationalStudyProtocol")) {
        ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
        ospDTO = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(studyProtocolIi);
        ospDTO.setStudyPopulationDescription(StConverter.convertToSt(studyPopulationDescription));
        ospDTO.setSamplingMethodCode(CdConverter.convertToCd(SamplingMethodCode
            .getByCode(samplingMethodCode)));
        ospDTO = PaRegistry.getStudyProtocolService().updateObservationalStudyProtocol(ospDTO);
      }

      StudyProtocolDTO spDTO = new StudyProtocolDTO();
      spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
      spDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(Boolean
          .valueOf(acceptHealthyVolunteersIndicator)));
      spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);

      PlannedEligibilityCriterionDTO pecDTO = new PlannedEligibilityCriterionDTO();
      PlannedEligibilityCriterionDTO pecDTO2 = new PlannedEligibilityCriterionDTO();
      PlannedEligibilityCriterionDTO pecDTO3 = new PlannedEligibilityCriterionDTO();
      if (eligibleGenderCode != null) {
        pecDTO.setStudyProtocolIdentifier(studyProtocolIi);
        pecDTO.setCriterionName(StConverter.convertToSt("GENDER"));
        pecDTO.setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode
            .getByCode(eligibleGenderCode)));
        pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (eligibleGenderCodeId.length() != 0) {
          pecDTO.setIdentifier(IiConverter.convertToIi(eligibleGenderCodeId));
          PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO);
        } else {
          PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO);
        }
      }
      if (maximumValue != null) {
        pecDTO2.setStudyProtocolIdentifier(studyProtocolIi);
        pecDTO2.setCriterionName(StConverter.convertToSt("MAXIMUM-AGE"));
        Pq pq = new Pq();
        pq.setValue(new BigDecimal(maximumValue));
        pq.setUnit(maximumUnit);
        pecDTO2.setValue(pq);
        pecDTO2.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        pecDTO2.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (maximumValueId.length() != 0) {
          pecDTO2.setIdentifier(IiConverter.convertToIi(maximumValueId));
          PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO2);
        } else {
          PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO2);
        }
      }
      if (minimumValue != null) {
        pecDTO3.setStudyProtocolIdentifier(studyProtocolIi);
        pecDTO3.setCriterionName(StConverter.convertToSt("MINIMUM-AGE"));
        Pq pq = new Pq();
        pq.setValue(new BigDecimal(minimumValue));
        pq.setUnit(minimumUnit);
        pecDTO3.setValue(pq);
        pecDTO3.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        pecDTO3.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (minimumValueId.length() != 0) {
          pecDTO3.setIdentifier(IiConverter.convertToIi(minimumValueId));
          PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO3);
        } else {
          PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO3);
        }
      }
      if (eligibleGenderCodeId.length() != 0) {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
      } else {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
      }
      query();            
    } catch (PAException e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITY;
  }

  /**
   * @return result
   */
  public String input() {
    return ELIGIBILITYADD;
  }

  /**
   * @return result
   */
  public String create() {
    enforceEligibilityBusinessRules();
    if (hasFieldErrors()) {
      return ELIGIBILITYADD;
    }
    try {
      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
      .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
      PlannedEligibilityCriterionDTO pecDTO = new PlannedEligibilityCriterionDTO();
      pecDTO.setStudyProtocolIdentifier(studyProtocolIi);
      pecDTO.setCriterionName(StConverter.convertToSt(webDTO.getCriterionName()));
      Pq pq = new Pq();
      if (webDTO.getValue() != null) {
        pq.setValue(new BigDecimal(webDTO.getValue()));
      }
      if (webDTO.getUnit() != null) {
        pq.setUnit(webDTO.getUnit());
      }
      pecDTO.setValue(pq);
      if (webDTO.getInclusionIndicator() == null) {
          pecDTO.setInclusionIndicator(BlConverter.convertToBl(null));
      } else  if (webDTO.getInclusionIndicator().equalsIgnoreCase("Inclusion")) {
          pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
      } else {
           pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.FALSE));
      }
      pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
      pecDTO.setTextDescription(StConverter.convertToSt(webDTO.getTextDescription()));
      pecDTO.setOperator(StConverter.convertToSt(webDTO.getOperator()));
      PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO);
      query();
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
      return ELIGIBILITY;
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
      return ELIGIBILITYADD;
    }
  }

  /**
   * @return result
   */
  public String edit() {
    try {
      PlannedEligibilityCriterionDTO sgDTO = PaRegistry.getPlannedActivityService()
      .getPlannedEligibilityCriterion(IiConverter.convertToIi(id));
      webDTO = setEligibilityDetailsDTO(sgDTO);
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITYADD;
  }

  /**
   * @return result
   */
  public String update() {
    enforceEligibilityBusinessRules();
    if (hasFieldErrors()) {
      return ELIGIBILITYADD;
    }
    try {

      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
      .getAttribute(Constants.STUDY_PROTOCOL_II);
      PlannedEligibilityCriterionDTO pecDTO = new PlannedEligibilityCriterionDTO();
      pecDTO.setIdentifier(IiConverter.convertToIi(id));
      pecDTO.setStudyProtocolIdentifier(studyProtocolIi);
      pecDTO.setCriterionName(StConverter.convertToSt(webDTO.getCriterionName()));
      Pq pq = new Pq();
      if (webDTO.getValue() != null) {
        pq.setValue(new BigDecimal(webDTO.getValue()));
      }
      if (webDTO.getUnit() != null) {
        pq.setUnit(webDTO.getUnit());
      }
      pecDTO.setValue(pq);
      if (webDTO.getInclusionIndicator() == null) {
          pecDTO.setInclusionIndicator(BlConverter.convertToBl(null));
      } else  if (webDTO.getInclusionIndicator().equalsIgnoreCase("Inclusion")) {
          pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
      } else {
           pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.FALSE));
      }
      pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
      pecDTO.setTextDescription(StConverter.convertToSt(webDTO.getTextDescription()));
      pecDTO.setOperator(StConverter.convertToSt(webDTO.getOperator()));
      PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO);
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
      query();
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
      return ELIGIBILITYADD;
    }
    return ELIGIBILITY;
  }

  /**
   * @return result
   */
  public String delete() {

    try {
      PaRegistry.getPlannedActivityService().deletePlannedEligibilityCriterion(IiConverter.convertToIi(id));
      query();
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITY;
  }

  private ISDesignDetailsWebDTO setEligibilityDetailsDTO(PlannedEligibilityCriterionDTO dto) {
    ISDesignDetailsWebDTO webdto = new ISDesignDetailsWebDTO();
    if (dto != null) {
      if (dto.getEligibleGenderCode().getCode() != null) {
        eligibleGenderCode = dto.getEligibleGenderCode().getCode();
        eligibleGenderCodeId = dto.getIdentifier().getExtension();
      }
      if (dto.getCriterionName().getValue() != null
          && dto.getCriterionName().getValue().equals("MAXIMUM-AGE")) {
        maximumValue = dto.getValue().getValue().toString();
        maximumUnit = dto.getValue().getUnit();
        maximumValueId = dto.getIdentifier().getExtension();
      }
      if (dto.getCriterionName().getValue() != null
          && dto.getCriterionName().getValue().equals("MINIMUM-AGE")) {
        minimumValue = dto.getValue().getValue().toString();
        minimumUnit = dto.getValue().getUnit();
        minimumValueId = dto.getIdentifier().getExtension();
      }
      if (dto.getCriterionName().getValue() != null) {
        webdto.setCriterionName(dto.getCriterionName().getValue());
      }
      if (dto.getInclusionIndicator().getValue() != null) {
        if (BlConverter.covertToBool(dto.getInclusionIndicator())) {
          webdto.setInclusionIndicator(("Inclusion"));
        } else {
          webdto.setInclusionIndicator("Exclusion");
        } 
      }
      if (dto.getIdentifier() != null) {
        webdto.setId(dto.getIdentifier().getExtension());
      }
      if (dto.getOperator() != null) {
        webdto.setOperator(dto.getOperator().getValue());
      }
      if (dto.getTextDescription() != null) {
        webdto.setTextDescription(dto.getTextDescription().getValue());
      }
      if (dto.getValue().getValue() != null) {
        webdto.setValue(dto.getValue().getValue().toString());
      }
      if (dto.getValue().getUnit() != null) {
        webdto.setUnit(dto.getValue().getUnit());
      }

    }
    return webdto;
  }

  private void enforceBusinessRules() {

    StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext
    .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
    if (spqDTO.getStudyProtocolType().equalsIgnoreCase("ObservationalStudyProtocol")) {
      if (PAUtil.isEmpty(studyPopulationDescription)) {
        addFieldError("studyPopulationDescription", getText("error.trialPopulationDescription"));
      }
      if (PAUtil.isNotEmpty(studyPopulationDescription)
          && studyPopulationDescription.length() > MAXIMUM_CHAR_POPULATION) {
        addFieldError("studyPopulationDescription", getText("error.spType.population.maximumChar"));        
      }
      if (PAUtil.isEmpty(samplingMethodCode)) {
        addFieldError("samplingMethodCode", getText("error.samplingMethod"));
      }
    }

    if (PAUtil.isEmpty(acceptHealthyVolunteersIndicator)) {
      addFieldError("acceptHealthyVolunteersIndicator", getText("error.acceptHealthyVolunteersIndicator"));
    }
    if (PAUtil.isEmpty(eligibleGenderCode)) {
      addFieldError("eligibleGenderCode", getText("error.eligibleGenderCode"));
    }
    if (PAUtil.isEmpty(this.maximumUnit)) {
      addFieldError("maximumUnit", getText("error.maximumUnit"));
    }
    if (PAUtil.isEmpty(this.maximumValue)) {
      addFieldError("maximumValue", getText("error.maximumValue"));
    }
    if (PAUtil.isNotEmpty(this.maximumValue)) {
      try {
        Long.parseLong(maximumValue);
      } catch (NumberFormatException e) {
        addFieldError("maximumValue", getText("error.numeric"));
      }
    }
    if (PAUtil.isEmpty(this.minimumUnit)) {
      addFieldError("minimumUnit", getText("error.minimumUnit"));
    }
    if (PAUtil.isEmpty(this.minimumValue)) {
      addFieldError("minimumValue", getText("error.minimumValue"));
    }
    if (PAUtil.isNotEmpty(this.minimumValue)) {
      try {
        Long.parseLong(minimumValue);
      } catch (NumberFormatException e) {
        addFieldError("minimumValue", getText("error.numeric"));
      }
    }
  }
  
  private void enforceEligibilityBusinessRules() {
//    if (PAUtil.isEmpty(webDTO.getInclusionIndicator())) {
//      addFieldError("webDTO.inclusionIndicator", getText("error.inclusionIndicator"));
//    }
    if (PAUtil.isNotEmpty(webDTO.getTextDescription())
        && webDTO.getTextDescription().length() > MAXIMUM_CHAR_DESCRIPTION) {
      addFieldError("webDTO.TextDescription", getText("error.spType.description.maximumChar"));        
    }
    if (PAUtil.isEmpty(webDTO.getTextDescription())) {
      if (PAUtil.isEmpty(webDTO.getCriterionName())  
          && PAUtil.isEmpty(webDTO.getOperator())
          && PAUtil.isEmpty(webDTO.getValue())
          && PAUtil.isEmpty(webDTO.getUnit())) {

        addFieldError("webDTO.mandatory",  getText("error.mandatory"));
        return;
      } else if (PAUtil.isEmpty(webDTO.getCriterionName())
          || PAUtil.isEmpty(webDTO.getOperator())
          || PAUtil.isEmpty(webDTO.getValue())
          || PAUtil.isEmpty(webDTO.getUnit()))  {

        addFieldError("webDTO.buldcriterion", getText("error.buldcriterion"));

      }
    }
  }
  /**
   * @return webDTO
   */
  public ISDesignDetailsWebDTO getWebDTO() {
    return webDTO;
  }

  /**
   * @param webDTO
   *            webDTO
   */
  public void setWebDTO(ISDesignDetailsWebDTO webDTO) {
    this.webDTO = webDTO;
  }

  /**
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *            id
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
   * @param page
   *            page
   */
  public void setPage(String page) {
    this.page = page;
  }

  /**
   * @return eligibleGenderCode
   */
  public String getEligibleGenderCode() {
    return eligibleGenderCode;
  }

  /**
   * @param eligibleGenderCode
   *            eligibleGenderCode
   */
  public void setEligibleGenderCode(String eligibleGenderCode) {
    this.eligibleGenderCode = eligibleGenderCode;
  }

  /**
   * @return maximumValue
   */
  public String getMaximumValue() {
    return maximumValue;
  }

  /**
   * @param maximumValue
   *            maximumValue
   */
  public void setMaximumValue(String maximumValue) {
    this.maximumValue = maximumValue;
  }

  /**
   * @return maximumUnit
   */
  public String getMaximumUnit() {
    return maximumUnit;
  }

  /**
   * @param maximumUnit
   *            maximumUnit
   */
  public void setMaximumUnit(String maximumUnit) {
    this.maximumUnit = maximumUnit;
  }

  /**
   * @return minimumValue
   */
  public String getMinimumValue() {
    return minimumValue;
  }

  /**
   * @param minimumValue
   *            minimumValue
   */
  public void setMinimumValue(String minimumValue) {
    this.minimumValue = minimumValue;
  }

  /**
   * @return minimumUnit
   */
  public String getMinimumUnit() {
    return minimumUnit;
  }

  /**
   * @param minimumUnit
   *            minimumUnit
   */
  public void setMinimumUnit(String minimumUnit) {
    this.minimumUnit = minimumUnit;
  }

  /**
   * @return eligibleGenderCodeId
   */
  public String getEligibleGenderCodeId() {
    return eligibleGenderCodeId;
  }

  /**
   * @param eligibleGenderCodeId
   *            eligibleGenderCodeId
   */
  public void setEligibleGenderCodeId(String eligibleGenderCodeId) {
    this.eligibleGenderCodeId = eligibleGenderCodeId;
  }

  /**
   * @return maximumValueId
   */
  public String getMaximumValueId() {
    return maximumValueId;
  }

  /**
   * @param maximumValueId
   *            maximumValueId
   */
  public void setMaximumValueId(String maximumValueId) {
    this.maximumValueId = maximumValueId;
  }

  /**
   * @return minimumValueId
   */
  public String getMinimumValueId() {
    return minimumValueId;
  }

  /**
   * @param minimumValueId
   *            minimumValueId
   */
  public void setMinimumValueId(String minimumValueId) {
    this.minimumValueId = minimumValueId;
  }

  /**
   * @return acceptHealthyVolunteersIndicator
   */
  public String getAcceptHealthyVolunteersIndicator() {
    return acceptHealthyVolunteersIndicator;
  }

  /**
   * @param acceptHealthyVolunteersIndicator
   *            acceptHealthyVolunteersIndicator
   */
  public void setAcceptHealthyVolunteersIndicator(
      String acceptHealthyVolunteersIndicator) {
    this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
  }

  /**
   * @return eligibilityList
   */
  public List<ISDesignDetailsWebDTO> getEligibilityList() {
    return eligibilityList;
  }

  /**
   * @param eligibilityList
   *            eligibilityList
   */
  public void setEligibilityList(List<ISDesignDetailsWebDTO> eligibilityList) {
    this.eligibilityList = eligibilityList;
  }

  /**
   * @return studyPopulationDescription
   */
  public String getStudyPopulationDescription() {
    return studyPopulationDescription;
  }

  /**
   * @param studyPopulationDescription
   *            studyPopulationDescription
   */
  public void setStudyPopulationDescription(String studyPopulationDescription) {
    this.studyPopulationDescription = studyPopulationDescription;
  }

  /**
   * @return samplingMethodCode
   */
  public String getSamplingMethodCode() {
    return samplingMethodCode;
  }

  /**
   * @param samplingMethodCode
   *            samplingMethodCode
   */
  public void setSamplingMethodCode(String samplingMethodCode) {
    this.samplingMethodCode = samplingMethodCode;
  }
  
  /**
   * @return list
   */
  public List<ISDesignDetailsWebDTO> getList() {
    return list;
  }

  /**
   * @param list list
   */
  public void setList(List<ISDesignDetailsWebDTO> list) {
    this.list = list;
  }



}
