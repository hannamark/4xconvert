/**
 * 
 */
package gov.nih.nci.pa.dto;

/**
 * Class for holding attributes for Arms DTO.
 * @author Hugh Reinhart
 * @since 11/06/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class TrialArmsWebDTO {
    private String identifier;
    private String name;
    private String type;
    private String description;
    private String interventions;

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the interventions
     */
    public String getInterventions() {
        return interventions;
    }

    /**
     * @param interventions the interventions to set
     */
    public void setInterventions(String interventions) {
        this.interventions = interventions;
    }
}
