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
    
    private Ii healthCareProviderIi;
    private Ii clinicalResearchStaffIi;
    /**
     * 
     * @return healthCareProvider
     */
    public Ii getHealthCareProviderIi() {
        return healthCareProviderIi;
    }
    /**
     * 
     * @param healthCareProviderIi healthCareProviderIi
     */
    public void setHealthCareProviderIi(Ii healthCareProviderIi) {
        this.healthCareProviderIi = healthCareProviderIi;
    }
    /**
     * 
     * @return healthCareProviderIi
     */
    public Ii getClinicalResearchStaffIi() {
        return clinicalResearchStaffIi;
    }
    /**
     * 
     * @param clinicalResearchStaffIi healthCareProviderIi
     */
    public void setClinicalResearchStaffIi(Ii clinicalResearchStaffIi) {
        this.clinicalResearchStaffIi = clinicalResearchStaffIi;
    }
    
    

}
