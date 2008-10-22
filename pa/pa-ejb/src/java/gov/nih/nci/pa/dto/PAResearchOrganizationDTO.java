/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hreinhart
 *
 */
public class PAResearchOrganizationDTO implements Serializable {
    static final long serialVersionUID = 1230985673L;
    
    Long id;
    Long organizationId;
    Date dateLastUpdated;
    String identifier;
    String userLastUpdated;

    /**
     * 
     */
    public PAResearchOrganizationDTO() {
        // empty constructor
    }

    /**
     * @param researchOrganization domain object
     */
    public PAResearchOrganizationDTO(ResearchOrganization researchOrganization) {
        this.id = researchOrganization.getId();
        this.dateLastUpdated = researchOrganization.getDateLastUpdated();
        this.identifier = researchOrganization.getIdentifier();
        this.organizationId = researchOrganization.getOrganization().getId();
        researchOrganization.getUserLastUpdated();
    }
    
    /**
     * @return ResearchOrganization object
     */
    public ResearchOrganization getBo() {
        Organization org = new Organization();
        org.setId(getOrganizationId());
        
        ResearchOrganization result = new ResearchOrganization();
        result.setDateLastUpdated(getDateLastUpdated());
        result.setId(getId());
        result.setIdentifier(getIdentifier());
        result.setOrganization(org);
        result.setUserLastUpdated(getUserLastUpdated());
        return result;
    }
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the dateLastUpdated
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }
    /**
     * @param dateLastUpdated the dateLastUpdated to set
     */
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
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
     * @return the userLastUpdated
     */
    public String getUserLastUpdated() {
        return userLastUpdated;
    }
    /**
     * @param userLastUpdated the userLastUpdated to set
     */
    public void setUserLastUpdated(String userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }
}
