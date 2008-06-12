package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.pa.dto.ProtocolDTO;

/**
 * @author Harsha
 *
 */
public interface IProtocolService {
    /**
     * @param id object id
     * @return protocol dto
     */
    ProtocolDTO getProtocol(long id);
    
    /**
     * 
     * @param pSc ProtocolSearchCriteria
     * @return list protocolDto    
     */
    List<ProtocolDTO> getProtocol(ProtocolSearchCriteria pSc);
}
