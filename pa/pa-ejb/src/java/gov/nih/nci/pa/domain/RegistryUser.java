/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Bala Nair
 *
 */
@Entity
@Table(name =  "REGISTRY_USER")
public class RegistryUser extends  AbstractEntity {
    
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
    
    /**
     * @return the csmUserId
     */
    @Column(name = "CSM_USER_ID")
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
     * @return the firstName
     */
    @Column(name = "FIRST_NAME")
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
    @Column(name = "LAST_NAME")
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
    @Column(name = "MIDDLE_NAME")
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
    @Column(name = "ADDRESS_LINE")
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
     * @return the state
     */
    @Column(name = "STATE")
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
     * @return the country
     */
    @Column(name = "COUNTRY")
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
    @Column(name = "PHONE")
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
    @Column(name = "AFFILIATE_ORG")
    public String getAffiliateOrg() {
        return affiliateOrg;
    }
    /**
     * @param affiliateOrg the affiliateOrg to set
     */
    public void setAffiliateOrg(String affiliateOrg) {
        this.affiliateOrg = affiliateOrg;
    }


}
