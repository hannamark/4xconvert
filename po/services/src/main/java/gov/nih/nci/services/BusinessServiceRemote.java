package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.entity.NullifiedEntityException;

import javax.ejb.Remote;

/**
 * Remote Service that contains methods which scope extends a simple entity. 
 * 
 * @author ludetc
 *
 */
@Remote
public interface BusinessServiceRemote {

    /**
     * Return an Entity by its ii Id, along with requested player and scoper correlations.
     * 
     * @param id id to search by
     * @param players list of player correlations to include in result. 
     * @param scopers list of scoper correlations to include in result. 
     * @return the entityNodeDto
     * @throws NullifiedEntityException if the requested id has a NULLIFIED entity status
     */
    EntityNodeDto getEntityByIdWithCorrelations(Ii id, Cd[] players, Cd[] scopers)
        throws NullifiedEntityException;
    
}
