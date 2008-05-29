package gov.nih.nci.pa.domain;

import java.io.Serializable;

/**
 * Protocol bean for managing protocol.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public abstract class AbstractEntity implements Serializable, Auditable {
    /**
     * Standard max length for text columns.
     */
    public static final int LONG_TEXT_LENGTH = 200;
    
    /**
     * @return Long long
     */
    public abstract Long getId();




    
 
}
