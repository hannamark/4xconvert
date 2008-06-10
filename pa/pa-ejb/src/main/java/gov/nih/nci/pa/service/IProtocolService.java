package gov.nih.nci.pa.service;

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
}
