package gov.nih.nci.pa.service.impl;



import gov.nih.nci.pa.dao.ProtocolDAO;
import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.IProtocolService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Implementer class for query Protocol, which will be invoked by the EJB bean.
 * If need be, these methods can be exposed as web service
 * @author Harsha, Naveen
 */
public class ProtocolServiceImpl implements IProtocolService {

    private static final Logger LOG  = Logger.getLogger(ProtocolServiceImpl.class);
    /**
     * @param sc ProtocolSearchCriteria
     * @return List ProtocolDTO   
     * @throws PAException on error 
     */
    public List<ProtocolDTO> getProtocol(ProtocolSearchCriteria sc) throws PAException {      
       LOG.debug("Entering getProtocol ");       
       return new ProtocolDAO().getProtocol(sc);      
    }
   

}