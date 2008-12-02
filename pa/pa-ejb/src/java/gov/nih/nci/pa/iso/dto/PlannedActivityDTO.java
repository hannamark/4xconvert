package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class PlannedActivityDTO extends StudyDTO {
    private static final long serialVersionUID = 1238767890L;
    private Cd categoryCode;
    private Ii interventionIdentifier;
    private Bl leadProductIndicator;
    private Cd subcategoryCode;
    private St textDescription; 
    /**
     * @return the categoryCode
     */
    public Cd getCategoryCode() {
        return categoryCode;
    }
    /**
     * @param categoryCode the categoryCode to set
     */
    public void setCategoryCode(Cd categoryCode) {
        this.categoryCode = categoryCode;
    }
    /**
     * @return the interventionIdentifier
     */
    public Ii getInterventionIdentifier() {
        return interventionIdentifier;
    }
    /**
     * @param interventionIdentifier the interventionIdentifier to set
     */
    public void setInterventionIdentifier(Ii interventionIdentifier) {
        this.interventionIdentifier = interventionIdentifier;
    }
    /**
     * @return the leadProductIndicator
     */
    public Bl getLeadProductIndicator() {
        return leadProductIndicator;
    }
    /**
     * @param leadProductIndicator the leadProductIndicator to set
     */
    public void setLeadProductIndicator(Bl leadProductIndicator) {
        this.leadProductIndicator = leadProductIndicator;
    }
    /**
     * @return the subcategoryCode
     */
    public Cd getSubcategoryCode() {
        return subcategoryCode;
    }
    /**
     * @param subcategoryCode the subcategoryCode to set
     */
    public void setSubcategoryCode(Cd subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }
    /**
     * @return textDescription
     */
    public St getTextDescription() {
      return textDescription;
    }
    /**
     * @param textDescription textDescription
     */
    public void setTextDescription(St textDescription) {
      this.textDescription = textDescription;
    }    
}
