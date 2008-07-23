package gov.nih.nci.pa.service;

import org.apache.log4j.Logger;

/**
 * PA Exception for error handling.
 * 
 * @author Naveen Amiruddin
 * @since 05/30/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings("PMD")
public class PAException extends Exception {

    private static Logger log = Logger.getLogger(PAException.class);

    /**
     * no argument constructor.
     */
    public PAException() {
        super();
    }

    /**
     * String constructor.
     * @param message message
     */
    public PAException(String message) {
        super(message);
        log.error(message);
    }

    /**
     * String and Throwable constructor.
     * @param message message
     * @param t t
     */
    public PAException(String message, Throwable t) {
        super(message, t);
        log.error(message, t);        
    }

    /**
     * 
     * @param t t
     */
    public PAException(Throwable t) {
        super(t);
        log.error(t);
    }
}

