package gov.nih.nci.coppa.po.grid.services;

import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.LimitOffset;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.services.PoDto;

import java.rmi.RemoteException;

/**
 * Interface for correlation-related grid services.
 * @param <DTO> DTO object
 * @param <XML> XML object
 */
public interface CorrelationGridService<DTO extends PoDto, XML extends Object> {

    /**
     * @return the type representing the xml element
     */
    Class<XML> getXMLType();

    /**
     * @return the type representing the DTO (remote-ejb)
     */
    Class<DTO> getDTOType();

    // CHECKSTYLE:OFF
    /**
     * Get a correlation by ID.
     * @param id ID of object to get
     * @return object matching the id
     * @throws RemoteException for unexpected errors
     * @throws gov.nih.nci.coppa.po.faults.NullifiedRoleFault if a requested correlation is NULLIFIED
     */
    // CHECKSTYLE:ON
    XML getById(Id id) throws RemoteException;

    // CHECKSTYLE:OFF
    /**
     * Get correlations by their IDs.
     * @param id IDs of objects to get
     * @return objects matching the requested ids
     * @throws RemoteException for any unexpected errors
     * @throws gov.nih.nci.coppa.po.faults.NullifiedRoleFault if a requested correlations are NULLIFIED
     */
    // CHECKSTYLE:ON
    XML[] getByIds(Id[] id) throws RemoteException;

    /**
     * @param criteria the search criteria
     * @return objects matching the criteria
     * @throws RemoteException for unexpected errors
     */
    XML[] search(XML criteria) throws RemoteException;
    
    /**
     * @param criteria the search criteria
     * @param pageParams the results paging parameters 
     * @return objects matching the criteria
     * @throws RemoteException for unexpected errors
     */
    XML[] query(XML criteria, LimitOffset pageParams) throws RemoteException;

    // CHECKSTYLE:OFF
    /**
     * @param correlation to update
     * @throws RemoteException for unexpected errors
     * @throws gov.nih.nci.coppa.po.faults.EntityValidationFault when any validation errors occur
     */
    // CHECKSTYLE:ON
    void update(XML correlation) throws RemoteException;

    // CHECKSTYLE:OFF
    /**
     * @param targetId the identifier of the correlation to change
     * @param statusCode status code to set
     * @throws RemoteException for unexpected errors
     * @throws gov.nih.nci.coppa.po.faults.EntityValidationFault when any validation errors occur
     */
    // CHECKSTYLE:ON
    void updateStatus(gov.nih.nci.coppa.po.Id targetId, gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException;

    /**
     * @param correlation the correlation's data
     * @return a break-down of the errors by field, if any are found
     * @throws RemoteException for unexpected errors
     */
    StringMap validate(XML correlation) throws RemoteException;

}
