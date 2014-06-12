/**
 * 
 */
package gov.nih.nci.po.service;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 * 
 */
public class BaseSearchResultDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7733437432626120581L;
    private Long id;
    private String statusCode;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String countryCode;
    private String zipCode;
    private String comments;
    private String emailAddresses;
    private String phones;
    private Number duplicateOf;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1
     *            the address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2
     *            the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
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
     * @param state
     *            the state to set
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
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the country code(alpha 3)
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode
     *            the countryCode(alpha 3) to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode
     *            the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the emailAddresses
     */
    public String getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * @param emailAddresses
     *            the emailAddresses to set
     */
    public void setEmailAddresses(String emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    /**
     * @return the phones
     */
    public String getPhones() {
        return phones;
    }

    /**
     * @param phones
     *            the phones to set
     */
    public void setPhones(String phones) {
        this.phones = phones;
    }

    /**
     * @return the duplicateOf
     */
    public Number getDuplicateOf() {
        return duplicateOf;
    }

    /**
     * @param duplicateOf
     *            the duplicateOf to set
     */
    public void setDuplicateOf(Number duplicateOf) {
        this.duplicateOf = duplicateOf;
    }

}
