/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

/**
 * @author Hugh Reinhart
 * @since 09/29/2008

 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@SuppressWarnings("PMD")
public class PersonFunctionalRoleDTO extends FunctionalRoleDTO {
    private static final long serialVersionUID = 198569324790L;
    
    private Ii healthCareProvider;
    private Ii clinicalResearchStaff;
    /**
     * 
     * @return healthCareProvider
     */
    public Ii getHealthCareProvider() {
        return healthCareProvider;
    }
    /**
     * 
     * @param healthCareProvider healthCareProvider
     */
    public void setHealthCareProvider(Ii healthCareProvider) {
        this.healthCareProvider = healthCareProvider;
    }
    /**
     * 
     * @return healthCareProvider
     */
    public Ii getClinicalResearchStaff() {
        return clinicalResearchStaff;
    }
    /**
     * 
     * @param clinicalResearchStaff healthCareProvider
     */
    public void setClinicalResearchStaff(Ii clinicalResearchStaff) {
        this.clinicalResearchStaff = clinicalResearchStaff;
    }
    
    

}
