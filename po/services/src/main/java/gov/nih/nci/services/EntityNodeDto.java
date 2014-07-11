package gov.nih.nci.services;

import gov.nih.nci.iso21090.Bl;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * The Entity Node Dto Contains an Entity and optional correlations.
 *
 * @author ludetc
 *
 */
public class EntityNodeDto implements PoDto {
    private static final long serialVersionUID = 2L;

    private Bl correlationOverflow;
    private Set<CorrelationDto> players = new HashSet<CorrelationDto>();
    private Set<CorrelationDto> scopers = new HashSet<CorrelationDto>();
    private EntityDto entityDto;

    /**
     * The correlationOverflow attribute is true if the number of either players
     * or scopers exceeds the limit (500); if true, the client needs to revert to
     * using the older methods to retrieve the roles using a search on playerId/scoperId with LimitOffset.
     * @return Bl for true of false
     */
    public Bl getCorrelationOverflow() {
        return correlationOverflow;
    }

    /**
    *
    * @param correlationOverflow correlationOverflow
    */
    public void setCorrelationOverflow(Bl correlationOverflow) {
        this.correlationOverflow = correlationOverflow;
    }

    /**
     *
     * @return Array of players
     */
    public CorrelationDto[] getPlayers() {
        if (players != null) {
            return players.toArray(new CorrelationDto[players.size()]);
        } else {
            return null;
        }
    }

    /**
     *
     * @param players Players
     */
    @SuppressWarnings ({ "PMD.ArrayIsStoredDirectly" })
    public void setPlayers(CorrelationDto[] players) {
        if (players == null) {
            this.players = null;
        } else {
            this.players = new HashSet<CorrelationDto>();
            CollectionUtils.addAll(this.players, players);
        }
    }

    /**
     * @return Array of scopers
     */
    public CorrelationDto[] getScopers() {
        if (scopers != null) {
            return scopers.toArray(new CorrelationDto[scopers.size()]);
        } else {
            return null;
        }
    }

    /**
     *
     * @param scopers Scopers
     */
    @SuppressWarnings ({ "PMD.ArrayIsStoredDirectly" })
    public void setScopers(CorrelationDto[] scopers) {
        if (scopers == null) {
            this.scopers = null;
        } else {
            this.scopers = new HashSet<CorrelationDto>();
            CollectionUtils.addAll(this.scopers, scopers);
        }
    }

    /**
     *
     * @return EntityDto
     */
    public EntityDto getEntityDto() {
        return entityDto;
    }

    /**
     *
     * @param entityDto Entity
     */
    public void setEntityDto(EntityDto entityDto) {
        this.entityDto = entityDto;
    }
}
