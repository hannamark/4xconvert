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
  "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
  public class EligibilityCriteriaAction extends ActionSupport {

  private static final String ELIGIBILITY = "eligibility";
  private static final String ELIGIBILITYADD = "eligibilityAdd";
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
        List<ISDesignDetailsWebDTO> list = new ArrayList<ISDesignDetailsWebDTO>();
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
      pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
      pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getInclusionIndicator())));
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
      pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
      pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getInclusionIndicator())));
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
        webdto.setInclusionIndicator(dto.getInclusionIndicator()
            .getValue().toString());
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
        addFieldError("studyPopulationDescription",
            getText("error.trialPopulationDescription"));
      }
      if (PAUtil.isEmpty(samplingMethodCode)) {
        addFieldError("samplingMethodCode", getText("error.samplingMethod"));
      }
    }

    if (PAUtil.isEmpty(acceptHealthyVolunteersIndicator)) {
      addFieldError("acceptHealthyVolunteersIndicator",
          getText("error.acceptHealthyVolunteersIndicator"));
    }
    if (PAUtil.isEmpty(eligibleGenderCode)) {
      addFieldError("eligibleGenderCode",
          getText("error.eligibleGenderCode"));
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
    if (PAUtil.isEmpty(webDTO.getInclusionIndicator())) {
      addFieldError("webDTO.inclusionIndicator",
          getText("error.inclusionIndicator"));
    }
    if (PAUtil.isEmpty(webDTO.getTextDescription())) {
      if (PAUtil.isEmpty(webDTO.getCriterionName())  
          && PAUtil.isEmpty(webDTO.getOperator())
          && PAUtil.isEmpty(webDTO.getValue())
          && PAUtil.isEmpty(webDTO.getUnit())) {

        addFieldError("webDTO.mandatory",
            getText("error.mandatory"));
        return;
      } else if (PAUtil.isEmpty(webDTO.getCriterionName())
          || PAUtil.isEmpty(webDTO.getOperator())
          || PAUtil.isEmpty(webDTO.getValue())
          || PAUtil.isEmpty(webDTO.getUnit()))  {

        addFieldError("webDTO.buldcriterion",
            getText("error.buldcriterion"));

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

}
