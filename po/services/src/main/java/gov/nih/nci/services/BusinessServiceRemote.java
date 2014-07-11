package gov.nih.nci.services;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import javax.ejb.Remote;
import java.util.List;

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
    CorrelationNodeDTO getCorrelationByIdWithEntities(Ii id, Bl player, Bl scoper) 
        throws NullifiedRoleException;
    
    /**
     * Get list of Correlations by Ii with player and scoper entities.
     * @param ids Ii of correlation.
     * @param player if true get player entity.
     * @param scoper if true get scoper entity.
     * @return list of correlation node dto.
     * @throws NullifiedRoleException if the requested id has a NULLIFIED role status
     */
    List<CorrelationNodeDTO> getCorrelationsByIdsWithEntities(Ii[] ids, Bl player, Bl scoper) 
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
    List<CorrelationNodeDTO> getCorrelationsByPlayerIdsWithEntities(Cd correlationType, 
            Ii[] playerIds, Bl player, Bl scoper) throws NullifiedRoleException;
    
    /**
     * Return an array of Correlations, and optionally populated player, scoper, or both.
     * 
     * @param searchNode Element to do search by example on.
     * @param player true if player should be pre-populated
     * @param scoper true if scoper should be pre-populated 
     * @param limitOffset allows for pagination of records. Set to null for no pagination
     * @return The array of CorrelationNodeDTO
     * @throws TooManyResultsException if the number of result is greater than acceptable by the system
     */
     List<CorrelationNodeDTO> searchCorrelationsWithEntities(CorrelationNodeDTO searchNode, 
            Bl player, Bl scoper, LimitOffset limitOffset) throws TooManyResultsException;
    
    /**
     * 
     * @param searchNode Element to do search by example.
     * @param players Array of player correlations to pull.
     * @param scopers Array of scoper correlations to pull.
     * @param limitOffset allows for pagination of records. Set to null for no pagination
     * @return The array of EntityNodeDto
     * @throws TooManyResultsException if the number of result is greater than acceptable by the system.
     */
    List<EntityNodeDto> searchEntitiesWithCorrelations(EntityNodeDto searchNode, 
            Cd[] players, Cd[] scopers, LimitOffset limitOffset) throws TooManyResultsException;
        
    
}
