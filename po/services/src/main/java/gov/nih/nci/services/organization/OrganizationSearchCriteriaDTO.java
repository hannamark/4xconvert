/**
 * 
 */
package gov.nih.nci.services.organization;

import gov.nih.nci.services.PoDto;

/**
 * @author Denis G. Krylov
 * 
 */
public class OrganizationSearchCriteriaDTO implements PoDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5640983655194032107L;

    private String identifier;
    private String familyName;
    private String functionalRole;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String name;
    private String status;
    private String ctepId;
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
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }
    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    /**
     * @return the functionalRole
     */
    public String getFunctionalRole() {
        return functionalRole;
    }
    /**
     * @param functionalRole the functionalRole to set
     */
    public void setFunctionalRole(String functionalRole) {
        this.functionalRole = functionalRole;
    }
    /**
     * @return the city
     */
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
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }
    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the ctepId
     */
    public String getCtepId() {
        return ctepId;
    }
    /**
     * @param ctepId the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrganizationSearchCriteriaDTO [identifier=")
                .append(identifier).append(", familyName=").append(familyName)
                .append(", functionalRole=").append(functionalRole)
                .append(", city=").append(city).append(", state=")
                .append(state).append(", country=").append(country)
                .append(", zip=").append(zip).append(", name=").append(name)
                .append(", status=").append(status).append(", ctepId=")
                .append(ctepId).append("]");
        return builder.toString();
    }

}
