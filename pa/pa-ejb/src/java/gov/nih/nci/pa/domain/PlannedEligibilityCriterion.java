package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.EligibleGenderCode;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Hugh Reinhart
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@DiscriminatorColumn(name = "PlannedEligibilityCriterion", discriminatorType = DiscriminatorType.STRING)
public class PlannedEligibilityCriterion extends PlannedActivity {
    private Boolean inclusionIndicator;
    private String criterionName;
    private String operator;
    private BigDecimal value;
    //private UnitsCode unit;
    private String unit;
    private EligibleGenderCode eligibleGenderCode;
    /**
     * @return inclusionIndicator
     */
    @Column(name = "INCLUSION_INDICATOR")
    public Boolean getInclusionIndicator() {
        return inclusionIndicator;
    }
    /**
     * @param inclusionIndicator inclusionIndicator
     */
    public void setInclusionIndicator(Boolean inclusionIndicator) {
        this.inclusionIndicator = inclusionIndicator;
    }
    /**
     * @return criterionName
     */
    @Column(name = "CRITERION_NAME")
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
     * @return operator
     */
    @Column(name = "OPERATOR")
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
     * @return eligibleGenderCode
     */
    @Column(name = "ELIGIBLE_GENDER_CODE")
    @Enumerated(EnumType.STRING)
    public EligibleGenderCode getEligibleGenderCode() {
        return eligibleGenderCode;
    }
    /**
     * @param eligibleGenderCode eligibleGenderCode
     */
    public void setEligibleGenderCode(EligibleGenderCode eligibleGenderCode) {
        this.eligibleGenderCode = eligibleGenderCode;
    }
    
    /**
     * @return unit
     *//*
    @Column(name = "UNIT")
    @Enumerated(EnumType.STRING)
    public UnitsCode getUnit() {
        return unit;
    }
    *//**
     * @param unit unit
     *//*
    public void setUnit(UnitsCode unit) {
        this.unit = unit;
    }*/
    /**
     * @return value
     */
    @Column(name = "VALUE")
    public BigDecimal getValue() {
      return value;
    }
    /**
     * @param value value
     */
    public void setValue(BigDecimal value) {
      this.value = value;
    }
    /**
     * @return unit
     */
    @Column(name = "UNIT")
    public String getUnit() {
      return unit;
    }
    /**
     * @param unit unit
     */
    public void setUnit(String unit) {
      this.unit = unit;
    }
    
}
