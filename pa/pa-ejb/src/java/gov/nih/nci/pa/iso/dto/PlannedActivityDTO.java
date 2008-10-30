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
public class PlannedActivityDTO extends BaseDTO {
    private static final long serialVersionUID = 1238767890L;
    private Ii studyProtocolIdentifier;
    private St alternateName;
    private Cd categoryCode;
    private St descriptionText;
    private Ii interventionIdentifier;
    private Bl leadProductIndicator;
    private St name;
    private Cd subcategoryCode;
    /**
     * @return the studyProtocolIdentifier
     */
    public Ii getStudyProtocolIdentifier() {
        return studyProtocolIdentifier;
    }
    /**
     * @param studyProtocolIdentifier the studyProtocolIdentifier to set
     */
    public void setStudyProtocolIdentifier(Ii studyProtocolIdentifier) {
        this.studyProtocolIdentifier = studyProtocolIdentifier;
    }
    /**
     * @return the alternateName
     */
    public St getAlternateName() {
        return alternateName;
    }
    /**
     * @param alternateName the alternateName to set
     */
    public void setAlternateName(St alternateName) {
        this.alternateName = alternateName;
    }
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
     * @return the descriptionText
     */
    public St getDescriptionText() {
        return descriptionText;
    }
    /**
     * @param descriptionText the descriptionText to set
     */
    public void setDescriptionText(St descriptionText) {
        this.descriptionText = descriptionText;
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
     * @return the name
     */
    public St getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(St name) {
        this.name = name;
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
}
