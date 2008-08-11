package gov.nih.nci.pa.service;

import org.apache.log4j.Logger;

/**
 * Search protocol Exception.
 * 
 * @author Harsha
 *
 */
public class NoStudyProtocolFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(PAException.class);
    /**
     * Empty Constructor.
     */
    public NoStudyProtocolFoundException() {
       super();      
    }
    /**
     * @param msg error message
     */
    public NoStudyProtocolFoundException(String msg) {
        super(msg);
        LOG.info(msg);
    }
}
