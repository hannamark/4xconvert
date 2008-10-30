/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Bl;


/**
 * @author Bala Nair
 * @since 10/23/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyContactDTO extends PersonFunctionalRoleDTO {
    

    private Ii studyProtocolIi;
    private Bl primaryIndicator;
    private Ii healthCareProvider;
    private Cd roleCode;
    private Ad postalAddress;
    
    /**
     * @return the postalAddress
     */
    public Ad getPostalAddress() {
        return postalAddress;
    }
    /**
     * @param postalAddress the postalAddress to set
     */
    public void setPostalAddress(Ad postalAddress) {
        this.postalAddress = postalAddress;
    }
    /**
     * @return the roleCode
     */
    public Cd getRoleCode() {
        return roleCode;
    }
    /**
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(Cd roleCode) {
        this.roleCode = roleCode;
    }
    /**
     * @return the studyProtocolIi
     */
    public Ii getStudyProtocolIi() {
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolIi the studyProtocolIi to set
     */
    public void setStudyProtocolIi(Ii studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }
    
    /**
     * @return the primaryIndicator
     */
    public Bl getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator the primaryIndicator to set
     */
    public void setPrimaryIndicator(Bl primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return the healthCareProvider
     */
    public Ii getHealthCareProvider() {
        return healthCareProvider;
    }
    /**
     * @param healthCareProvider the healthCareProvider to set
     */
    public void setHealthCareProvider(Ii healthCareProvider) {
        this.healthCareProvider = healthCareProvider;
    }



}
