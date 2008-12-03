package gov.nih.nci.pa.dto;

/**
 * Web DTO class for displaying list of diseases associated with SP.
 * @author Hugh Reinhart
 * @since 12/02/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class DiseaseWebDTO {
    private String preferredName;
    private String code;
    private String conceptId;
    private String menuDisplayName;
    private String parentPreferredName;
    private String lead;
    /**
     * @return the preferredName
     */
    public String getPreferredName() {
        return preferredName;
    }
    /**
     * @param preferredName the preferredName to set
     */
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the conceptId
     */
    public String getConceptId() {
        return conceptId;
    }
    /**
     * @param conceptId the conceptId to set
     */
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }
    /**
     * @return the menuDisplayName
     */
    public String getMenuDisplayName() {
        return menuDisplayName;
    }
    /**
     * @param menuDisplayName the menuDisplayName to set
     */
    public void setMenuDisplayName(String menuDisplayName) {
        this.menuDisplayName = menuDisplayName;
    }
    /**
     * @return the parentPreferredName
     */
    public String getParentPreferredName() {
        return parentPreferredName;
    }
    /**
     * @param parentPreferredName the parentPreferredName to set
     */
    public void setParentPreferredName(String parentPreferredName) {
        this.parentPreferredName = parentPreferredName;
    }
    /**
     * @return the lead
     */
    public String getLead() {
        return lead;
    }
    /**
     * @param lead the lead to set
     */
    public void setLead(String lead) {
        this.lead = lead;
    }
}
