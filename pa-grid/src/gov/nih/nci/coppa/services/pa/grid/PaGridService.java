package gov.nih.nci.coppa.services.pa.grid;

import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.pa.iso.dto.BaseDTO;

import java.rmi.RemoteException;

/**
 * Interface for pa grid services.
 * @param <DTO> DTO object
 * @param <XML> XML object
 */
public interface PaGridService<DTO extends BaseDTO, XML extends Object> {

    /**
     * @return the type representing the xml element
     */
    Class<XML> getXMLType();

    /**
     * @return the type representing the DTO (remote-ejb)
     */
    Class<DTO> getDTOType();

    /**
     * Get object by ID.
     * @param id IDs of objects to get
     * @return objects matching the requested ids
     * @throws RemoteException for any unexpected errors
     */
    XML get(Id id) throws RemoteException;

    /**
     * Create object by passing in XML object.
     * @param xml to create.
     * @return XML object
     * @throws RemoteException for any unexpected errors.
     */
    XML create(XML xml) throws RemoteException;

    /**
     * Update object by passing in XML object.
     * @param xml to update.
     * @return XML object
     * @throws RemoteException for any unexpected errors.
     */
    XML update(XML xml) throws RemoteException;

    /**
     * Delete object by oassing in id.
     * @param id id of object to be deleted.
     * @throws RemoteException for any unexpected errors.
     */
    void delete(Id id) throws RemoteException;
}
