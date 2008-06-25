package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.impl.ProtocolServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 *
 */
@Stateless
public class ProtocolServiceBean  implements ProtocolServiceLocal, ProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(ProtocolServiceBean.class);
    
    /**
     * @param psc ProtocolSearchCriteria
     * @return List ProtocolDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ProtocolDTO> getProtocol(ProtocolSearchCriteria psc) throws PAException {
        LOG.debug("Entering getProtocol ");
        ProtocolServiceImpl pImpl = new ProtocolServiceImpl();
        List<ProtocolDTO> pdtos = new ArrayList<ProtocolDTO>();
        pdtos = pImpl.getProtocol(psc);
        LOG.debug("Leaving getProtocol ");
        return pdtos;
    }
    

}
