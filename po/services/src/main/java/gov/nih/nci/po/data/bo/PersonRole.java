package gov.nih.nci.po.data.bo;

/**
 * Define the unique aspects of a PersonRole (player and scoper).
 */
public interface PersonRole extends ScopedRole, PlayedRole<Person>, Correlation {

    /**
     * @param player the player
     */
    void setPlayer(Person player);
    
    /**
     * @param scoper the scoper
     */
    void setScoper(Organization scoper);
}
