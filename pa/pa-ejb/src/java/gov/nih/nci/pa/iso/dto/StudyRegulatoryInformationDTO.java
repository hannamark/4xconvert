package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.St;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyRegulatoryInformationDTO extends BaseDTO {
    private St trialOversgtAuthCountry;  
    private St trialOversgtAuthOrgName;
    /**
     * @return the trialOversgtAuthCountry
     */
    public St getTrialOversgtAuthCountry() {
        return trialOversgtAuthCountry;
    }
    /**
     * @param trialOversgtAuthCountry the trialOversgtAuthCountry to set
     */
    public void setTrialOversgtAuthCountry(St trialOversgtAuthCountry) {
        this.trialOversgtAuthCountry = trialOversgtAuthCountry;
    }
    /**
     * @return the trialOversgtAuthOrgName
     */
    public St getTrialOversgtAuthOrgName() {
        return trialOversgtAuthOrgName;
    }
    /**
     * @param trialOversgtAuthOrgName the trialOversgtAuthOrgName to set
     */
    public void setTrialOversgtAuthOrgName(St trialOversgtAuthOrgName) {
        this.trialOversgtAuthOrgName = trialOversgtAuthOrgName;
    }   
}
