/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ts;

/**
 * FunctionalRoleDTO for transferring FunctionalRole object .
 * @author Hugh Reinhart
 * @since 08/23/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class FunctionalRoleDTO extends StudyDTO {
    private static final long serialVersionUID = 123489324790L;

    Cd statusCode;
    Ts statusDateRangeLow;
    
    /**
     * @return the statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDateRangeLow
     */
    public Ts getStatusDateRangeLow() {
        return statusDateRangeLow;
    }
    /**
     * @param statusDateRangeLow the statusDateRangeLow to set
     */
    public void setStatusDateRangeLow(Ts statusDateRangeLow) {
        this.statusDateRangeLow = statusDateRangeLow;
    }
}
