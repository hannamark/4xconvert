/**
 * 
 */
package gov.nih.nci.pa.domain;


import gov.nih.nci.pa.enums.StatusCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * A formalized group of persons or other organizations collected together for a common purpose 
 * (such as administrative, legal, political) and the infrastructure to carry out that purpose..
 * @author Hugh Reinhart
 *
 */
@Entity
@Table(name =  "ORGANIZATION")
public class Organization extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;
    
    private String name;
    private String identifier;
    private String city;
    private String countryName;
    private String postalCode;
    private String state;
    private StatusCode statusCode;
    private List<HealthCareFacility> healthCareFacilities = new ArrayList<HealthCareFacility>();
    private List<ResearchOrganization> researchOrganizations = new ArrayList<ResearchOrganization>();
    
    /**
     * @return the name
     */
    @NotNull
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
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
     * 
     * @return identifier 
     */
    @Column(name = "ASSIGNED_IDENTIFIER")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier identifier
     */
    public void setIdentifier(String identifier) {
       this.identifier = identifier;
    }

    /**
     * @return the city
     */
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country_name
     */
    @Column(name = "COUNTRY_NAME")
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the postalCode
     */
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state state 
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    public StatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode statusCode
     */
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
    
    /**
     * 
     * @return healthCareFacilities
     */
    @OneToMany(mappedBy = "organization")
    public List<HealthCareFacility> getHealthCareFacilities() {
        return healthCareFacilities;
    }

    /**
     * 
     * @param healthCareFacilities healthCareFacilities
     */
    public void setHealthCareFacilities(
            List<HealthCareFacility> healthCareFacilities) {
        this.healthCareFacilities = healthCareFacilities;
    }
    
    /**
     * 
     * @return researchOrganizations
     */
    @OneToMany(mappedBy = "organization")
    public List<ResearchOrganization> getResearchOrganizations() {
        return researchOrganizations;
    }

    /**
     * 
     * @param researchOrganizations researchOrganizations
     */
    public void setResearchOrganizations(
            List<ResearchOrganization> researchOrganizations) {
        this.researchOrganizations = researchOrganizations;
    }
    
    
}
