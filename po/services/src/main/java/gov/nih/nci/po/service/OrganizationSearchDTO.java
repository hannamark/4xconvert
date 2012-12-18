/**
 * 
 */
package gov.nih.nci.po.service;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings("PMD.TooManyFields")
public class OrganizationSearchDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2833313620499941791L;

    private String id;
    private String name;
    private String familyName;
    private String roCtepId;
    private String hcfCtepId;
    private int changeRequests;
    private int pendingROs;
    private int pendingHCFs;
    private String statusCode;
    private Date statusDate;
    private int totalROs;
    private int totalHCFs;
    private int totalIdOrgs;
    private int totalOversightCommitees;
    private int totalOrgContacts;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String comments;
    private String emailAddresses;
    private String phones;
    private Number duplicateOf;

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
     * @return the roCtepId
     */
    public String getRoCtepId() {
        return roCtepId;
    }

    /**
     * @param roCtepId
     *            the roCtepId to set
     */
    public void setRoCtepId(String roCtepId) {
        this.roCtepId = roCtepId;
    }

    /**
     * @return the hcfCtepId
     */
    public String getHcfCtepId() {
        return hcfCtepId;
    }

    /**
     * @param hcfCtepId
     *            the hcfCtepId to set
     */
    public void setHcfCtepId(String hcfCtepId) {
        this.hcfCtepId = hcfCtepId;
    }

    /**
     * @return the changeRequests
     */
    public int getChangeRequests() {
        return changeRequests;
    }

    /**
     * @param changeRequests
     *            the changeRequests to set
     */
    public void setChangeRequests(int changeRequests) {
        this.changeRequests = changeRequests;
    }

    /**
     * @return the pendingROs
     */
    public int getPendingROs() {
        return pendingROs;
    }

    /**
     * @param pendingROs
     *            the pendingROs to set
     */
    public void setPendingROs(int pendingROs) {
        this.pendingROs = pendingROs;
    }

    /**
     * @return the pendingHCFs
     */
    public int getPendingHCFs() {
        return pendingHCFs;
    }

    /**
     * @param pendingHCFs
     *            the pendingHCFs to set
     */
    public void setPendingHCFs(int pendingHCFs) {
        this.pendingHCFs = pendingHCFs;
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
     * @return the statusDate
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate
     *            the statusDate to set
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the totalROs
     */
    public int getTotalROs() {
        return totalROs;
    }

    /**
     * @param totalROs
     *            the totalROs to set
     */
    public void setTotalROs(int totalROs) {
        this.totalROs = totalROs;
    }

    /**
     * @return the totalHCFs
     */
    public int getTotalHCFs() {
        return totalHCFs;
    }

    /**
     * @param totalHCFs
     *            the totalHCFs to set
     */
    public void setTotalHCFs(int totalHCFs) {
        this.totalHCFs = totalHCFs;
    }

    /**
     * @return the totalIdOrgs
     */
    public int getTotalIdOrgs() {
        return totalIdOrgs;
    }

    /**
     * @param totalIdOrgs
     *            the totalIdOrgs to set
     */
    public void setTotalIdOrgs(int totalIdOrgs) {
        this.totalIdOrgs = totalIdOrgs;
    }

    /**
     * @return the totalOversightCommitees
     */
    public int getTotalOversightCommitees() {
        return totalOversightCommitees;
    }

    /**
     * @param totalOversightCommitees
     *            the totalOversightCommitees to set
     */
    public void setTotalOversightCommitees(int totalOversightCommitees) {
        this.totalOversightCommitees = totalOversightCommitees;
    }

    /**
     * @return the totalOrgContacts
     */
    public int getTotalOrgContacts() {
        return totalOrgContacts;
    }

    /**
     * @param totalOrgContacts
     *            the totalOrgContacts to set
     */
    public void setTotalOrgContacts(int totalOrgContacts) {
        this.totalOrgContacts = totalOrgContacts;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrganizationSearchDTO [id=").append(id)
                .append(", name=").append(name).append(", familyName=")
                .append(familyName).append(", roCtepId=").append(roCtepId)
                .append(", hcfCtepId=").append(hcfCtepId)
                .append(", changeRequests=").append(changeRequests)
                .append(", pendingROs=").append(pendingROs)
                .append(", pendingHCFs=").append(pendingHCFs)
                .append(", statusCode=").append(statusCode)
                .append(", statusDate=").append(statusDate)
                .append(", totalROs=").append(totalROs).append(", totalHCFs=")
                .append(totalHCFs).append(", totalIdOrgs=").append(totalIdOrgs)
                .append(", totalOversightCommitees=")
                .append(totalOversightCommitees).append(", totalOrgContacts=")
                .append(totalOrgContacts).append(", address1=")
                .append(address1).append(", address2=").append(address2)
                .append(", city=").append(city).append(", state=")
                .append(state).append(", country=").append(country)
                .append(", zipCode=").append(zipCode).append(", comments=")
                .append(comments).append(", emailAddresses=")
                .append(emailAddresses).append(", phones=").append(phones)
                .append(", duplicateOf=").append(duplicateOf).append("]");
        return builder.toString();
    }

}
