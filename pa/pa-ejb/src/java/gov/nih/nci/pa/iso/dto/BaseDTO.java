package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

import java.io.Serializable;

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


}
