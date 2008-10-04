/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> data transfer object
 */
public abstract class AbstractBasePaService<DTO> 
        implements BasePaService<DTO> {
    
    /** Standard error message for empty methods to be overridden. */
    protected static String errMsgMethodNotImplemented = "Method not yet implemented.";
    
    /**
     * @return log4j Logger
     */
    protected abstract Logger getLogger();
    
    /**
     * @param ii index of object
     * @return null
     * @throws PAException exception
     */
    public DTO get(Ii ii) throws PAException {
        serviceError(errMsgMethodNotImplemented);
        return null;
    }

    /**
     * @param dto dto
     * @return null
     * @throws PAException exception
     */
    public DTO create(DTO dto) throws PAException {
        serviceError(errMsgMethodNotImplemented);
        return null;
    }
    

    /**
     * @param dto dto
     * @return null
     * @throws PAException exception
     */
    public DTO update(DTO dto) throws PAException {
        serviceError(errMsgMethodNotImplemented);
        return null;
    }
    
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
}
