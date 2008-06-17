package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.pa.dto.ProtocolDTO;

/**
 * @author Harsha
 *
 */
public interface IProtocolService {
    
    /**
     * 
     * @param pSc ProtocolSearchCriteria
     * @return list protocolDto    
     */
    List<ProtocolDTO> getProtocol(ProtocolSearchCriteria pSc);
}
