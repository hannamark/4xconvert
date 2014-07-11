/**
 * 
 */
package gov.nih.nci.po.service;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Person;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 * 
 */
public final class PersonSearchCriteria implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String ctepID;
    private String email;
    private String firstName;
    private Boolean hasPendingCrsRoles;
    private Boolean hasPendingHcpRoles;
    private Boolean hasPendingOcRoles;
    private Boolean hasPendingOpiRoles;
    private String id;
    private String lastName;
    private String org;
    private Person person = new Person();

    private String statusCode;

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    public PersonSearchCriteria() {
        getPerson().setPostalAddress(new Address());
        getPerson().getPostalAddress().setCountry(new Country());
    }

    /**
     * @return String
     */
    public String getCityOrMunicipality() {
        return getPerson().getPostalAddress().getCityOrMunicipality();
    }

    /**
     * @return String
     */
    public Long getCountryId() {
        final Country country = getPerson().getPostalAddress().getCountry();
        return country != null ? country.getId() : null;
    }

    /**
     * @return the ctepID
     */
    public String getCtepID() {
        return ctepID;
    }

    /**
     * @return String
     */
    public String getDeliveryAddressLine() {
        return getPerson().getPostalAddress().getDeliveryAddressLine();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the hasPendingCrsRoles
     */
    public Boolean getHasPendingCrsRoles() {
        return hasPendingCrsRoles;
    }

    /**
     * @return the hasPendingHcpRoles
     */
    public Boolean getHasPendingHcpRoles() {
        return hasPendingHcpRoles;
    }

    /**
     * @return the hasPendingOcRoles
     */
    public Boolean getHasPendingOcRoles() {
        return hasPendingOcRoles;
    }

    /**
     * @return the hasPendingOpiRoles
     */
    public Boolean getHasPendingOpiRoles() {
        return hasPendingOpiRoles;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the org
     */
    public String getOrg() {
        return org;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @return String
     */
    public String getPostalCode() {
        return getPerson().getPostalAddress().getPostalCode();
    }

    /**
     * @return String
     */
    public String getStateOrProvince() {
        return getPerson().getPostalAddress().getStateOrProvince();
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @return String
     */
    public String getStreetAddressLine() {
        return getPerson().getPostalAddress().getStreetAddressLine();
    }

    /**
     * Is criteria empty.
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        final Address addr = getPerson().getPostalAddress();
        return StringUtils.isBlank(ctepID)
                && StringUtils.isBlank(id)
                && StringUtils.isBlank(statusCode)
                && StringUtils.isBlank(email)
                && StringUtils.isBlank(firstName)
                && StringUtils.isBlank(lastName)
                && StringUtils.isBlank(org)
                && !Boolean.TRUE.equals(hasPendingCrsRoles)
                && !Boolean.TRUE.equals(hasPendingHcpRoles)
                && !Boolean.TRUE.equals(hasPendingOcRoles)
                && !Boolean.TRUE.equals(hasPendingOpiRoles)
                && (addr.getCountry() == null || addr.getCountry().getId() == null)
                && StringUtils.isBlank(addr.getStreetAddressLine())
                && StringUtils.isBlank(addr.getDeliveryAddressLine())
                && StringUtils.isBlank(addr.getCityOrMunicipality())
                && StringUtils.isBlank(addr.getStateOrProvince())
                && StringUtils.isBlank(addr.getPostalCode());
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid() {
        if (isEmpty()) {
            throw new OneCriterionRequiredException();
        }
        return true;
    }

    /**
     * @param ctepID
     *            the ctepID to set
     */
    public void setCtepID(String ctepID) {
        this.ctepID = ctepID;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param hasPendingCrsRoles
     *            the hasPendingCrsRoles to set
     */
    public void setHasPendingCrsRoles(Boolean hasPendingCrsRoles) {
        this.hasPendingCrsRoles = hasPendingCrsRoles;
    }

    /**
     * @param hasPendingHcpRoles
     *            the hasPendingHcpRoles to set
     */
    public void setHasPendingHcpRoles(Boolean hasPendingHcpRoles) {
        this.hasPendingHcpRoles = hasPendingHcpRoles;
    }

    /**
     * @param hasPendingOcRoles
     *            the hasPendingOcRoles to set
     */
    public void setHasPendingOcRoles(Boolean hasPendingOcRoles) {
        this.hasPendingOcRoles = hasPendingOcRoles;
    }

    /**
     * @param hasPendingOpiRoles
     *            the hasPendingOpiRoles to set
     */
    public void setHasPendingOpiRoles(Boolean hasPendingOpiRoles) {
        this.hasPendingOpiRoles = hasPendingOpiRoles;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param org
     *            the org to set
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * @param person
     *            the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
  

}
