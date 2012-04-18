/**
 * 
 */
package gov.nih.nci.services.person;

import gov.nih.nci.services.PoDto;

/**
 * @author Denis G. Krylov
 * 
 */
public class PersonSearchCriteriaDTO implements PoDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1698012246217376L;

    private String id;
    private String firstName;
    private String lastName;
    private String functionalRole;
    private String ctepId;
    private String status;
    private String affiliation;

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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
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
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the functionalRole
     */
    public String getFunctionalRole() {
        return functionalRole;
    }

    /**
     * @param functionalRole
     *            the functionalRole to set
     */
    public void setFunctionalRole(String functionalRole) {
        this.functionalRole = functionalRole;
    }

    /**
     * @return the ctepId
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     * @param ctepId
     *            the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * @param affiliation
     *            the affiliation to set
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonSearchCriteriaDTO [id=").append(id)
                .append(", firstName=").append(firstName).append(", lastName=")
                .append(lastName).append(", functionalRole=")
                .append(functionalRole).append(", ctepId=").append(ctepId)
                .append(", status=").append(status).append(", affiliation=")
                .append(affiliation).append("]");
        return builder.toString();
    }

}
