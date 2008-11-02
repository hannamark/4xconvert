/**
 * 
 */
package gov.nih.nci.pa.dto;


/**
 * DTO class for displaying study status history as a list.
 * 
 * @author Hugh Reinhart
 * @since 11/01/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class InterventionWebDTO {
    private String identifier;
    private String name;
    private String description;
    private String otherNames;
    private String leadIndicator;
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
     * @return the otherNames
     */
    public String getOtherNames() {
        return otherNames;
    }
    /**
     * @param otherNames the otherNames to set
     */
    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }
    /**
     * @return the leadIndicator
     */
    public String getLeadIndicator() {
        return leadIndicator;
    }
    /**
     * @param leadIndicator the leadIndicator to set
     */
    public void setLeadIndicator(String leadIndicator) {
        this.leadIndicator = leadIndicator;
    }
}
