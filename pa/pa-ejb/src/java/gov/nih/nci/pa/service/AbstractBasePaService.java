/**
 * 
 */
package gov.nih.nci.pa.service;

import org.apache.log4j.Logger;

/**
 * @author hreinhart
 *
 */
public abstract class AbstractBasePaService {
    
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";
    
    /**
     * @return log4j Logger
     */
    abstract Logger getLogger();

    /**
     * @param errMsg error string
     * @throws PAException exception
     */
    protected void serviceError(String errMsg) throws PAException {
        getLogger().error(errMsg);
        throw new PAException(errMsg);
    }
    
    /**
     * @param errMsg error string
     * @param t throwable error
     * @throws PAException exception
     */
    protected void serviceError(String errMsg, Throwable t) throws PAException {
        getLogger().error(errMsg, t);
        throw new PAException(errMsg, t);
    }
    
    /**
     * @throws PAException exception
     */
    protected void notImplementedError() throws PAException {
        serviceError(errMsgMethodNotImplemented);
    }
}
