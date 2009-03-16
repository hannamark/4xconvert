package gov.nih.nci.coppa.po.grid.services;

import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.services.PoDto;

import java.rmi.RemoteException;

public interface CorrelationGridService<DTO extends PoDto, XML extends Object> {

    /**
     * @return the type representing the xml element
     */
    Class<XML> getXMLType();

    /**
     * @return the type representing the DTO (remote-ejb)
     */
    Class<DTO> getDTOType();

    /**
     * @param id
     * @return object matching the id
     * @throws RemoteException for unexpected errors
     * @throws NullifiedRoleFault if a requested correlation is NULLIFIED
     */
    @SuppressWarnings("unchecked")
    XML getById(Id id) throws RemoteException, NullifiedRoleFault;

    /**
     * @param id
     * @return objects matching the requested ids
     * @throws RemoteException for any unexpected errors
     * @throws NullifiedRoleFault if a requested correlations are NULLIFIED
     */
    XML[] getByIds(Id[] id) throws RemoteException, NullifiedRoleFault;

    /**
     * @param criteria the search criteria
     * @return objects matching the criteria
     * @throws RemoteException for unexpected errors
     */
    @SuppressWarnings("unchecked")
    XML[] search(XML criteria) throws RemoteException;

    /**
     * @param correlation to update
     * @throws RemoteException for unexpected errors
     * @throws EntityValidationFault when any validation errors occur
     */
    void update(XML correlation) throws RemoteException, EntityValidationFault;

    /**
     * @param targetId the identifier of the correlation to change
     * @param statusCode
     * @throws RemoteException for unexpected errors
     * @throws EntityValidationFault when any validation errors occur
     */
    void updateStatus(gov.nih.nci.coppa.po.Id targetId, gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException,
            EntityValidationFault;

    /**
     * @param correlation the correlation's data
     * @return a break-down of the errors by field, if any are found
     * @throws RemoteException
     */
    StringMap validate(XML correlation) throws RemoteException;

}