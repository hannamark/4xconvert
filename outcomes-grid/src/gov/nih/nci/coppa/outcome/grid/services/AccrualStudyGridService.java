package gov.nih.nci.coppa.outcome.grid.services;


import gov.nih.nci.coppa.services.outcomes.Id;
import gov.nih.nci.pa.iso.dto.BaseDTO;

import java.rmi.RemoteException;

/**
 * Interface for pa grid services.
 * @param <DTO> DTO object
 * @param <XML> XML object
 */
public interface AccrualStudyGridService<DTO extends BaseDTO, XML extends Object> extends AccrualGridService<DTO, XML> {

    /**
     * Get objects by study protocol.
     * @param id id of the study protocol.
     * @return array of XML objects.
     * @throws RemoteException for unexpected errors
     */
    XML[] getByStudyProtocol(Id id) throws RemoteException;

}
