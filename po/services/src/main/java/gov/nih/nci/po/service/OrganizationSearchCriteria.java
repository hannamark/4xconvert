/**
 * 
 */
package gov.nih.nci.po.service;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Organization;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 * 
 */
public final class OrganizationSearchCriteria implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String ctepID;
    private String id;
    private String statusCode;
    private String name;
    private String familyName;
    private Boolean hasChangeRequests;
    private Boolean hasPendingHcfRoles;
    private Boolean hasPendingRoRoles;
    private Boolean searchAliases;    

    private Organization organization = new Organization();
    
    /**
     * 
     */
    @SuppressWarnings("deprecation")
    public OrganizationSearchCriteria() {
        getOrganization().setPostalAddress(new Address());
        getOrganization().getPostalAddress().setCountry(new Country());
    }

    /**
     * @return the ctepID
     */
    public String getCtepID() {
        return ctepID;
    }

    /**
     * @param ctepID
     *            the ctepID to set
     */
    public void setCtepID(String ctepID) {
        this.ctepID = ctepID;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName
     *            the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the hasChangeRequests
     */
    public Boolean getHasChangeRequests() {
        return hasChangeRequests;
    }

    /**
     * @param hasChangeRequests
     *            the hasChangeRequests to set
     */
    public void setHasChangeRequests(Boolean hasChangeRequests) {
        this.hasChangeRequests = hasChangeRequests;
    }

    /**
     * @return the hasPendingHcfRoles
     */
    public Boolean getHasPendingHcfRoles() {
        return hasPendingHcfRoles;
    }

    /**
     * @param hasPendingHcfRoles
     *            the hasPendingHcfRoles to set
     */
    public void setHasPendingHcfRoles(Boolean hasPendingHcfRoles) {
        this.hasPendingHcfRoles = hasPendingHcfRoles;
    }

    /**
     * @return the hasPendingRoRoles
     */
    public Boolean getHasPendingRoRoles() {
        return hasPendingRoRoles;
    }

    /**
     * @param hasPendingRoRoles
     *            the hasPendingRoRoles to set
     */
    public void setHasPendingRoRoles(Boolean hasPendingRoRoles) {
        this.hasPendingRoRoles = hasPendingRoRoles;
    }

    /**
     * 
     * @return searchAliases flag
     */
    public Boolean getSearchAliases() {
        return searchAliases;
    }

    /**
     * 
     * @param searchAliases flag for searchAliases
     */
    public void setSearchAliases(Boolean searchAliases) {
        this.searchAliases = searchAliases;
    }
    
    /**
     * Is criteria empty.
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        final Address addr = getOrganization().getPostalAddress();
        return StringUtils.isBlank(ctepID) && StringUtils.isBlank(id)
                && StringUtils.isBlank(statusCode) && StringUtils.isBlank(name)
                && StringUtils.isBlank(familyName)
                && !Boolean.TRUE.equals(hasChangeRequests)
                && !Boolean.TRUE.equals(hasPendingHcfRoles)
                && !Boolean.TRUE.equals(hasPendingRoRoles)
                && (addr.getCountry() == null || addr.getCountry().getId() == null)
                && StringUtils.isBlank(addr.getStreetAddressLine())
                && StringUtils.isBlank(addr.getDeliveryAddressLine())
                && StringUtils.isBlank(addr.getCityOrMunicipality())
                && StringUtils.isBlank(addr.getStateOrProvince())
                && StringUtils.isBlank(addr.getPostalCode());
    }

    /**
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * @return String
     */
    public String getCityOrMunicipality() {       
        return getOrganization().getPostalAddress().getCityOrMunicipality();
    }

    /**
     * @return String
     */
    public Long getCountryId() {
        final Country country = getOrganization().getPostalAddress()
                .getCountry();
        return country != null ? country.getId() : null;
    }

    /**
     * @return String
     */
    public String getDeliveryAddressLine() {
        return getOrganization().getPostalAddress().getDeliveryAddressLine();
    }

    /**
     * @return String
     */
    public String getPostalCode() {
        return getOrganization().getPostalAddress().getPostalCode();
    }

    /**
     * @return String
     */
    public String getStateOrProvince() {
        return getOrganization().getPostalAddress().getStateOrProvince();
    }

    /**
     * @return String
     */
    public String getStreetAddressLine() {
        return getOrganization().getPostalAddress().getStreetAddressLine();
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

}
