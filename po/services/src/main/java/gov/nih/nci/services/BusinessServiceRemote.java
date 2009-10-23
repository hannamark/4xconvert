package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
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
    
    /**
     * Get Correlations by Ii with player and scoper entities.
     * @param id Ii of correlation.
     * @param player if true get player entity.
     * @param scoper if true get scoper entity.
     * @return correlation node dto.
     * @throws NullifiedRoleException if the requested id has a NULLIFIED role status
     */
    CorrelationNodeDTO getCorrelationByIdWithEntities(Ii id, boolean player, boolean scoper) 
        throws NullifiedRoleException;
    
    /**
     * Get list of Correlations by Ii with player and scoper entities.
     * @param ids Ii of correlation.
     * @param player if true get player entity.
     * @param scoper if true get scoper entity.
     * @return list of correlation node dto.
     * @throws NullifiedRoleException if the requested id has a NULLIFIED role status
     */
    CorrelationNodeDTO[] getCorrelationsByIdsWithEntities(Ii[] ids, boolean player, boolean scoper) 
        throws NullifiedRoleException;
   
    /**
     * Get list of Correlations by player Ii with player and scoper entities.
     * @param correlationType type of correlations to get.
     * @param playerIds list of player Iis.
     * @param player if true get player entity.
     * @param scoper if true get scoper entity.
     * @return list of correlation node dto.
     * @throws NullifiedRoleException if the requested id has a NULLIFIED role status
     */
    CorrelationNodeDTO[] getCorrelationsByPlayerIdsWithEntities(Cd correlationType, 
            Ii[] playerIds, boolean player, boolean scoper) throws NullifiedRoleException;
    
}
