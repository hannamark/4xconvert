package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;


/**
 * StudyProtocolDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 09/04/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class InterventionalStudyProtocolDTO extends StudyProtocolDTO {

    Cd allocationCode;
    Bl delayedpostingIndicator;
    Bl fdaRegulatedIndicator;
    Bl section801Indicator;
    
    /**
     * @return allocationCode
     */
    public Cd getAllocationCode() {
        return allocationCode;
    }
    /**
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(Cd allocationCode) {
        this.allocationCode = allocationCode;
    }
    /**
     * 
     * @return delayedpostingIndicator
     */
    public Bl getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }
    /**
     * 
     * @param delayedpostingIndicator delayedpostingIndicator
     */
    public void setDelayedpostingIndicator(Bl delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
    }
    /**
     * 
     * @return fdaRegulatedIndicator
     */
    public Bl getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }
    /**
     * 
     * @param fdaRegulatedIndicator fdaRegulatedIndicator
     */
    public void setFdaRegulatedIndicator(Bl fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }
    /**
     * 
     * @return section801Indicator
     */
    public Bl getSection801Indicator() {
        return section801Indicator;
    }
    /**
     * 
     * @param section801Indicator section801Indicator
     */
    public void setSection801Indicator(Bl section801Indicator) {
        this.section801Indicator = section801Indicator;
    }   
    
    
}
