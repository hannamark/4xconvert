package gov.nih.nci.coppa.services.pa.grid;

import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.pa.iso.dto.BaseDTO;

import java.rmi.RemoteException;

/**
 * Interface for pa grid services.
 * @param <DTO> DTO object
 * @param <XML> XML object
 */
public interface StudyPaGridService<DTO extends BaseDTO, XML extends Object> extends PaGridService<DTO, XML> {

    /**
     * Copy by ID.
     * @param from ID of object from
     * @param to ID of object to
     * @throws RemoteException for unexpected errors
     */
    void copy(Id from, Id to) throws RemoteException;

    /**
     * Get objects by study protocol.
     * @param id id of the study protocol.
     * @return array of XML objects.
     * @throws RemoteException for unexpected errors
     */
    XML[] getByStudyProtocol(Id id) throws RemoteException;

}
