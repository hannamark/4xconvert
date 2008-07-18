package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.service.impl.NCISpecificInfoServiceImpl;

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
public class NCISpecificInfoServiceBean implements NCISpecificInfoServiceLocal, NCISpecificInfoServiceRemote {

    private static final Logger LOG  = Logger.getLogger(NCISpecificInfoServiceBean.class);
    
    /**
     * @param spid Study protocol ID
     * @return NCISpecificInfoDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NCISpecificInfoDTO getNCISpecificInfo(String spid) throws PAException {
        LOG.debug("Entering getNCISpecificInfo ");
        NCISpecificInfoServiceImpl nciImpl = new NCISpecificInfoServiceImpl();
       
        return nciImpl.getNCISpecificInfo(spid);
    }
    
    /**
     * @param nciSpecificInfoData  ref
     * @return NCISpecificInfoDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NCISpecificInfoDTO updateNCISpecificData(NCISpecificInfoData nciSpecificInfoData) throws PAException {
        LOG.debug("Entering updateNCISpecificData ");
        NCISpecificInfoServiceImpl nciImpl = new NCISpecificInfoServiceImpl();
       
        return nciImpl.updateNCISpecificData(nciSpecificInfoData);
    }
}