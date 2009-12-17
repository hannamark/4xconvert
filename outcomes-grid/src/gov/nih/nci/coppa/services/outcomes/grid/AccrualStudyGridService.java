package gov.nih.nci.coppa.services.outcomes.grid;


import gov.nih.nci.accrual.dto.AbstractStudyDto;
import gov.nih.nci.coppa.services.outcomes.Id;

import java.rmi.RemoteException;

/**
 * Interface for pa grid services.
 * @param <DTO> DTO object
 * @param <XML> XML object
 */
public interface AccrualStudyGridService<DTO extends AbstractStudyDto, XML extends Object> extends
        AccrualGridService<DTO, XML> {

    /**
     * Get objects by study protocol.
     * @param id id of the study protocol.
     * @return array of XML objects.
     * @throws RemoteException for unexpected errors
     */
    XML[] getByStudyProtocol(Id id) throws RemoteException;

}
