package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
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
                           implements NCISpecificInformationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(NCISpecificInformationServiceBean.class);
    
    /**
     * @param studyProtocolID Study protocol ID
     * @return NCISpecificInformationDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NCISpecificInformationDTO getNCISpecificInformation(Long studyProtocolID) throws PAException {
        LOG.debug("Entering getNCISpecificInformation ");
        NCISpecificInformationServiceImpl nciImpl = new NCISpecificInformationServiceImpl();
       
        return nciImpl.getNCISpecificInformation(studyProtocolID);
    }
    
    /**
     * @param nciSpecificInformartionData  ref
     * @return NCISpecificInformationDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public NCISpecificInformationDTO updateNCISpecificInformation(
                   NCISpecificInformationData nciSpecificInformartionData) throws PAException {
        LOG.debug("Entering updateNCISpecificInformation ");
        NCISpecificInformationServiceImpl nciImpl = new NCISpecificInformationServiceImpl();
       
        return nciImpl.updateNCISpecificInformation(nciSpecificInformartionData);
    }
}