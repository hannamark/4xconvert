package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.TrialDesignDTO;
import gov.nih.nci.pa.service.impl.TrialDesignServiceImpl;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import javax.ejb.Stateless;



/**
 * 
 * @author gnaveh
 *
 */
@Stateless
public class TrialDesignServiceBean implements TrialDesignServiceLocal, TrialDesignServiceRemote {

    private static final Logger LOG  = Logger.getLogger(TrialDesignServiceBean.class);
    
    /**
     * @param spid Study protocol ID
     * @return TrialDesignDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public TrialDesignDTO getTrialDesign(String spid) throws PAException {
        LOG.debug("Entering getTrialDesign ");
        TrialDesignServiceImpl tdImpl = new TrialDesignServiceImpl();
       
        return tdImpl.getTrialDesign(spid);
    }
}