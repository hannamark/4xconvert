package gov.nih.nci.pa.iso.dto;

import java.io.Serializable;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.St;

/**
 * BaseDTO for all common fields .
 * @author Naveen Amiruddin
 * @since 08/26/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class BaseDTO implements Serializable {
    
    private static final long serialVersionUID = 1234567890L;
    private Ii identifier;
    private Ivl statusDateRange;
    private St userLastUpdated;
    
    /**
     * 
     * @return ii
     */
    public Ii getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param ii ii
     */
    public void setIdentifier(Ii ii) {
        this.identifier = ii;
    }

    /**
     * 
     * @return statusDateRange
     */
    public Ivl getStatusDateRange() {
        return statusDateRange;
    }

    /**
     * 
     * @param statusDateRange statusDateRange
     */
    public void setStatusDateRange(Ivl statusDateRange) {
        this.statusDateRange = statusDateRange;
    }
    
    /**
     * 
     * @return userLastUpdated 
     */
    public St getUserLastUpdated() {
        return userLastUpdated;
    }


    /**
     * 
     * @param userLastUpdated userLastUpdated
     */
    public void setUserLastUpdated(St userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }
    

}
