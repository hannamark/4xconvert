package gov.nih.nci.pa.iso.dto;

import java.io.Serializable;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;

/**
 * BaseDTO for all common fields .
 * @author Naveen Amiruddin
 * @since 08/26/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class BaseDTO implements Serializable {
    
    private static final long serialVersionUID = 1234567890L;
    private Ii ii;
    private Ivl statusDateRange;

    /**
     * 
     * @return ii
     */
    public Ii getIi() {
        return ii;
    }

    /**
     * 
     * @param ii ii
     */
    public void setIi(Ii ii) {
        this.ii = ii;
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
    
    

}
