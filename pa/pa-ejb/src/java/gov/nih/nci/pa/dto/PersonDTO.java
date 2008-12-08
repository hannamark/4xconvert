package gov.nih.nci.pa.dto;

/**
 * Person DTO  for transferring Person object .
 * @author Naveen Amiruddin
 * @since 7/28/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

public class PersonDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    //these fields are used for creating the person
    private String preFix;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String email;
    
    /**
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * 
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * 
     * @param firstName firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * 
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * 
     * @return middleName
     */
    public String getMiddleName() {
        return middleName;
    }
    /**
     * 
     * @param middleName middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * 
     * @return fullName
     */
    public String getFullName() {
        if (lastName != null) {
            fullName = lastName;
        }
        if (firstName != null) {
            fullName = lastName + "," + firstName;
        }
        return fullName;
    }
    /**
     * @return the preFix
     */
    public String getPreFix() {
        return preFix;
    }
    /**
     * @param preFix the preFix to set
     */
    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }
    /**
     * @return the streetAddress
     */
    public String getStreetAddress() {
        return streetAddress;
    }
    /**
     * @param streetAddress the streetAddress to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
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
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    

}
