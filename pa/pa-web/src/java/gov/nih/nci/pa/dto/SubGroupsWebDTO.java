package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
/**
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class SubGroupsWebDTO {

    private String description;
    private String groupNumberText;
    private String id;
    
    /**
     * @param iso StratumGroupDTO object
     */
    public SubGroupsWebDTO(StratumGroupDTO iso) {
        super();
        this.description = iso.getDescription().getValue();
        this.groupNumberText = iso.getGroupNumberText().getValue(); 
        this.id = iso.getIi().getExtension();
    }
    
    /** .
     *  Default Constructor
     */
    public SubGroupsWebDTO() {
        super();
    }
    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return groupNumberText
     */
    public String getGroupNumberText() {
        return groupNumberText;
    }
    /**
     * @param groupNumberText groupNumberText
     */
    public void setGroupNumberText(String groupNumberText) {
        this.groupNumberText = groupNumberText;
    }
    /**
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
}
