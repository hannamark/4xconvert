package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;

/**
 * Investigator bean for managing PI.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "INVESTIGATOR")
public class Investigator extends AbstractEntity {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String telecomAddress;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String nciIdentifier;
    
    
    /**
     * set id.
     * @param id id
     */
     public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    /**
     * 
     * @return city city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return lastName last name
     */
    @Column(name = "LAST_NAME", length = LONG_TEXT_LENGTH)
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return firstName firstName
     */
    @Column(name = "FIRST_NAME", length = LONG_TEXT_LENGTH)
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return middleName
     */
    @Column(name = "MIDDLE_NAME", length = LONG_TEXT_LENGTH)
    public String getMiddleName() {
       return middleName;
    }

    /**
     * @param middleName middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return telecomAddress telecomAddress
     */
    @Column(name = "TELECOM_ADDRESS", length = LONG_TEXT_LENGTH)
    @Email
    public String getTelecomAddress() {
        return telecomAddress;
    }

    /**
     * @param telecomAddress telecomAddress
     */
    public void setTelecomAddress(String telecomAddress) {
        this.telecomAddress = telecomAddress;
    }

    /**
     * @return streetAddress
     */
    @Column(name = "STREET_ADDRESS", length = LONG_TEXT_LENGTH)
    public String getStreetAddress() {
        return streetAddress;
    }
    
    /**
     * @param streetAddress streetAddress
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state state
     */
    public void setState(String state) {
       this.state = state;
    }

    /**
     * @return zipCode
     */
    @Column(name = "ZIP_CODE", length = LONG_TEXT_LENGTH)
    public String getZipCode() {
        return zipCode;
    }


    /**
     * @param zipCode zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone phone
     */
    public void setPhone(String phone) {
       this.phone = phone;
    }

    /**
     * @return nciIdentifier nciIdentifier
     */
    @Column(name = "NCI_IDENTIFIER", length = LONG_TEXT_LENGTH)
    public String getNciIdentifier() {
       return nciIdentifier;
    }

    /**
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
    
    
     /**
     * helper method to combine last name, middle name & first name.
     * @return fullName
     */
    @Transient
    public String getFullName() {
       StringBuffer fullName = new StringBuffer();
       if (this.lastName != null) {
           fullName.append(lastName).append(" , ");
       }
       if (this.middleName != null) {
           fullName.append(middleName).append(" , ");
       }
       if (this.firstName != null) {
           fullName.append(fullName).append(firstName);
       }
       fullName.trimToSize();
       int lastIndex = fullName.lastIndexOf(","); 
       if (lastIndex > 0 
           && lastIndex == fullName.length() - 1) {
               fullName.substring(0 , lastIndex - 1);
       }
       return fullName.toString();
    }
}
