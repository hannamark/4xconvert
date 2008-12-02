package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.St;
/**
 *
 * @author Kalpana Guthikonda
 * @since 11/10/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class PlannedEligibilityCriterionDTO extends PlannedActivityDTO {

    private Bl inclusionIndicator;
    private St criterionName;
    private St operator;
    private Pq value;
    private Cd eligibleGenderCode;
    /**
     * @return inclusionIndicator
     */
    public Bl getInclusionIndicator() {
        return inclusionIndicator;
    }
    /**
     * @param inclusionIndicator inclusionIndicator
     */
    public void setInclusionIndicator(Bl inclusionIndicator) {
        this.inclusionIndicator = inclusionIndicator;
    }
    /**
     * @return criterionName
     */
    public St getCriterionName() {
        return criterionName;
    }
    /**
     * @param criterionName criterionName
     */
    public void setCriterionName(St criterionName) {
        this.criterionName = criterionName;
    }
    /**
     * @return operator
     */
    public St getOperator() {
        return operator;
    }
    /**
     * @param operator operator
     */
    public void setOperator(St operator) {
        this.operator = operator;
    }    
    /**
     * @return eligibleGenderCode
     */
    public Cd getEligibleGenderCode() {
        return eligibleGenderCode;
    }
    /**
     * @param eligibleGenderCode eligibleGenderCode
     */
    public void setEligibleGenderCode(Cd eligibleGenderCode) {
        this.eligibleGenderCode = eligibleGenderCode;
    }
    /**
     * @return value
     */
    public Pq getValue() {
      return value;
    }
    /**
     * @param value value
     */
    public void setValue(Pq value) {
      this.value = value;
    }        
}
