package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
import gov.nih.nci.pa.service.impl.NCISpecificInformationServiceImpl;

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
public class NCISpecificInformationServiceBean 
                           implements NCISpecificInformationServiceLocal, NCISpecificInformationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(NCISpecificInformationServiceBean.class);
    
    /**
     * @param spid Study protocol ID
     * @return NCISpecificInfoDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NCISpecificInfoDTO getNCISpecificInfo(String spid) throws PAException {
        LOG.debug("Entering getNCISpecificInfo ");
        NCISpecificInformationServiceImpl nciImpl = new NCISpecificInformationServiceImpl();
       
        return nciImpl.getNCISpecificInfo(spid);
    }
    
    /**
     * @param nciSpecificInformartionData  ref
     * @return NCISpecificInfoDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NCISpecificInfoDTO updateNCISpecificData(NCISpecificInformationData nciSpecificInformartionData) 
                                                                                             throws PAException {
        LOG.debug("Entering updateNCISpecificData ");
        NCISpecificInformationServiceImpl nciImpl = new NCISpecificInformationServiceImpl();
       
        return nciImpl.updateNCISpecificData(nciSpecificInformartionData);
    }
}