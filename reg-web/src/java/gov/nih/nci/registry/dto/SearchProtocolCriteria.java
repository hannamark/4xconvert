/**
 * 
 */
package gov.nih.nci.registry.dto;

/**
 * Criteria  class for Registry Protocol search.
 * @author Bala Nair
 *
 */
public class SearchProtocolCriteria {
    
    private String identifierType;
    private String identifier;
    private String officialTitle;
    private Long organizationId;
    private Long participatingSiteId;
    private String phaseCode;
    private String primaryPurposeCode;
    private String organizationType;
    private String myTrialsOnly;
    
    /**
     * @return the identifierType
     */
    public String getIdentifierType() {
        return identifierType;
    }
    /**
     * @param identifierType the identifierType to set
     */
    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
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
     * @return the officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * @return the organizationId
     */
    public Long getOrganizationId() {
        return organizationId;
    }
    /**
     * @param organizationId the organizationId to set
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
    /**
     * @param participatingSiteId the participatingSiteId to set
     */
    public void setParticipatingSiteId(Long participatingSiteId) {
        this.participatingSiteId = participatingSiteId;
    }
    /**
     * @return the participatingSiteId
     */
    public Long getParticipatingSiteId() {
        return participatingSiteId;
    }
    /**
     * @return the phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * @return the primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return the organizationType
     */
    public String getOrganizationType() {
        return organizationType;
    }
    /**
     * @param organizationType the organizationType to set
     */
    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }
    /**
     * @return the myTrialsOnly
     */
    public String getMyTrialsOnly() {
        return myTrialsOnly;
    }
    /**
     * @param myTrialsOnly the myTrialsOnly to set
     */
    public void setMyTrialsOnly(String myTrialsOnly) {
        this.myTrialsOnly = myTrialsOnly;
    }


}
