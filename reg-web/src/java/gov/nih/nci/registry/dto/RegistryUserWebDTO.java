/**
 * 
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.domain.RegistryUser;

/**
 * @author Bala Nair
 *
 */
@SuppressWarnings({"PMD.TooManyFields" })
public class RegistryUserWebDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String affiliateOrg;
    private Long csmUserId;
    private String username;
    private String password;
    private String retypePassword;
    private String prsOrgName;
    private Long treatmentSiteId;
    private Long physicianId;
    private String emailAddress;
    private String oldPassword;
    
    /**
     * 
     * @param registryUser registryUser
     * @param username username
     * @param password password
     */
    public RegistryUserWebDTO(RegistryUser registryUser, String username, String password) {
        this.id = registryUser.getId();
        this.csmUserId = registryUser.getCsmUserId();
        this.username = username;
        this.firstName = registryUser.getFirstName();
        this.lastName =  registryUser.getLastName();
        this.middleName =  registryUser.getMiddleName();
        this.addressLine = registryUser.getAddressLine();
        this.city = registryUser.getCity();
        this.state = registryUser.getState();
        this.postalCode = registryUser.getPostalCode();
        this.country = registryUser.getCountry();
        this.phone = registryUser.getPhone();
        this.affiliateOrg = registryUser.getAffiliateOrg();
        this.password = password;
        this.retypePassword = password;
        this.prsOrgName = registryUser.getPrsOrgName();
        this.emailAddress = registryUser.getEmailAddress();
        this.treatmentSiteId = registryUser.getPoOrganizationId();
        this.physicianId = registryUser.getPoPersonId();
        this.oldPassword = password;
    }

    /** .
     *  Default Constructor
     */
    public RegistryUserWebDTO() {
        super();
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        if (username != null) {
            this.username = username.trim();
        }
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }
    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * @return the addressLine
     */
    public String getAddressLine() {
        return addressLine;
    }
    /**
     * @param addressLine the addressLine to set
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
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
     * @return the postalCode
     */
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the affiliateOrg
     */
    public String getAffiliateOrg() {
        return affiliateOrg;
    }
    /**
     * @param affiliateOrg the affiliateOrg to set
     */
    public void setAffiliateOrg(String affiliateOrg) {
        this.affiliateOrg = affiliateOrg;
    }
    /**
     * @return the csmUserId
     */
    public Long getCsmUserId() {
        return csmUserId;
    }
    /**
     * @param csmUserId the csmUserId to set
     */
    public void setCsmUserId(Long csmUserId) {
        this.csmUserId = csmUserId;
    }
    
    /**
     * @return the retypePassword
     */
    public String getRetypePassword() {
        return retypePassword;
    }

    /**
     * @param retypePassword the retypePassword to set
     */
    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    /**
     * @return the prsOrgName
     */
    public String getPrsOrgName() {
        return prsOrgName;
    }

    /**
     * @param prsOrgName the prsOrgName to set
     */
    public void setPrsOrgName(String prsOrgName) {
        this.prsOrgName = prsOrgName;
    }

    /**
     * @return the treatmentSiteId
     */
    public Long getTreatmentSiteId() {
        return treatmentSiteId;
    }

    /**
     * @param treatmentSiteId the treatmentSiteId to set
     */
    public void setTreatmentSiteId(Long treatmentSiteId) {
        this.treatmentSiteId = treatmentSiteId;
    }
    
    /**
     * @return the physicianId
     */
    public Long getPhysicianId() {
        return physicianId;
    }

    /**
     * @param physicianId the physicianId to set
     */
    public void setPhysicianId(Long physicianId) {
        this.physicianId = physicianId;
    }
    
    /**
     * @return the email address
     */
    public String getEmailAddress() {
        return this.emailAddress;
    }
    
    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
