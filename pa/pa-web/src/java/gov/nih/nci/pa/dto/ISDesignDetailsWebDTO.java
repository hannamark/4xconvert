package gov.nih.nci.pa.dto;
/**
 * @author Kalpana Guthikonda
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.TooManyFields" })
public class ISDesignDetailsWebDTO {
    
    private String primaryPurposeCode;
    private String phaseCode;
    private String designConfigurationCode;
    private String numberOfInterventionGroups;
    private String blindingSchemaCode;
    private String allocationCode;
    private String primaryPurposeOtherText;
    private String phaseOtherText;
    private String blindingRoleCode;
    private String maximumTargetAccrualNumber;
    private String studyClassificationCode;
    
    private String name;
    private String timeFrame;
    private String primaryIndicator;
    private String safetyIndicator;
    private String id;

    private String criterionName;
    private String inclusionIndicator;
    private String operator;
    private String value;
    private String unit;
    private String textDescription;
    
    /**
     * @return primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * @return designConfigurationCode
     */
    public String getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /**
     * @param designConfigurationCode designConfigurationCode
     */
    public void setDesignConfigurationCode(String designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }
    /**
     * @return numberOfInterventionGroups
     */
    public String getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }
    /**
     * @param numberOfInterventionGroups numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(String numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }
    /**
     * @return blindingSchemaCode
     */
    public String getBlindingSchemaCode() {
        return blindingSchemaCode;
    }
    /**
     * @param blindingSchemaCode blindingSchemaCode
     */
    public void setBlindingSchemaCode(String blindingSchemaCode) {
        this.blindingSchemaCode = blindingSchemaCode;
    }
    /**
     * @return allocationCode
     */
    public String getAllocationCode() {
        return allocationCode;
    }
    /**
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(String allocationCode) {
        this.allocationCode = allocationCode;
    }
    /**
     * @return primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @param primaryPurposeOtherText primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return phaseOtherText
     */
    public String getPhaseOtherText() {
        return phaseOtherText;
    }
    /**
     * @param phaseOtherText phaseOtherText
     */
    public void setPhaseOtherText(String phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
    }
    /**
     * @return blindingRoleCode
     */
    public String getBlindingRoleCode() {
        return blindingRoleCode;
    }
    /**
     * @param blindingRoleCode blindingRoleCode
     */
    public void setBlindingRoleCode(String blindingRoleCode) {
        this.blindingRoleCode = blindingRoleCode;
    }
    /**
     * @return maximumTargetAccrualNumber
     */
    public String getMaximumTargetAccrualNumber() {
        return maximumTargetAccrualNumber;
    }
    /**
     * @param maximumTargetAccrualNumber mTargetAccrualNo
     */
    public void setMaximumTargetAccrualNumber(
            String maximumTargetAccrualNumber) {
        this.maximumTargetAccrualNumber = maximumTargetAccrualNumber;
    }
    /**
     * @return studyClassificationCode
     */
    public String getStudyClassificationCode() {
        return studyClassificationCode;
    }
    /**
     * @param studyClassificationCode studyClassificationCode
     */
    public void setStudyClassificationCode(String studyClassificationCode) {
        this.studyClassificationCode = studyClassificationCode;
    }
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return timeFrame
     */
    public String getTimeFrame() {
        return timeFrame;
    }
    /**
     * @param timeFrame timeFrame
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
    /**
     * @return primaryIndicator
     */
    public String getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(String primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return safetyIndicator
     */
    public String getSafetyIndicator() {
        return safetyIndicator;
    }
    /**
     * @param safetyIndicator safetyIndicator
     */
    public void setSafetyIndicator(String safetyIndicator) {
        this.safetyIndicator = safetyIndicator;
    }
    /**
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return criterionName
     */
    public String getCriterionName() {
        return criterionName;
    }
    /**
     * @param criterionName criterionName
     */
    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }
    /**
     * @return inclusionIndicator
     */
    public String getInclusionIndicator() {
        return inclusionIndicator;
    }
    /**
     * @param inclusionIndicator inclusionIndicator
     */
    public void setInclusionIndicator(String inclusionIndicator) {
        this.inclusionIndicator = inclusionIndicator;
    }
    /**
     * @return operator
     */
    public String getOperator() {
        return operator;
    }
    /**
     * @param operator operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }    
    /**
     * @return unit
     */
    public String getUnit() {
        return unit;
    }
    /**
     * @param unit unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    /**
     * @return value
     */
    public String getValue() {
      return value;
    }
    /**
     * @param value value
     */
    public void setValue(String value) {
      this.value = value;
    }
    /**
     * @return textDescription
     */
    public String getTextDescription() {
      return textDescription;
    }
    /**
     * @param textDescription textDescription
     */
    public void setTextDescription(String textDescription) {
      this.textDescription = textDescription;
    }
    
}
