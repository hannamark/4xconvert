package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.TrialDesignDAO;
import gov.nih.nci.pa.dto.TrialDesignDTO;
import gov.nih.nci.pa.service.PAException;
import org.apache.log4j.Logger;

/**
 * 
 * @author gnaveh
 *
 */
public class TrialDesignServiceImpl {

    private static final Logger LOG  = Logger.getLogger(TrialDesignServiceImpl.class);
    /**
     * @param spid ProtocolID
     * @return TrialDesignDTO   
     * @throws PAException on error 
     */
    public TrialDesignDTO getTrialDesign(String spid) throws PAException {      
       LOG.debug("Entering TrialDesignServiceImpl ");       
       return new TrialDesignDAO().getTrialDesign(spid);      
    }


}