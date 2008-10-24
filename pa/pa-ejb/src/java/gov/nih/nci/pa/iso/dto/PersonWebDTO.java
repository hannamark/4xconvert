package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.pa.enums.StudyContactRoleCode;

/**
 * This class is used to display results to the browser.
 * 
 * @author Harsha
 * 
 */
public class PersonWebDTO {
    private Long id;
    private StudyContactRoleCode roleName;
    private String firstName;
    private String lastName;    
    private String middleName;
    private String telephone;
    private String email;
    private Long selectedPersId; //PO-ID

    /**
     * @return the selectedPersId
     */
    public Long getSelectedPersId() {
        return selectedPersId;
    }

    /**
     * @param selectedPersId the selectedPersId to set
     */
    public void setSelectedPersId(Long selectedPersId) {
        this.selectedPersId = selectedPersId;
    }

    /**
     * @return the roleName
     */
    public StudyContactRoleCode getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(StudyContactRoleCode roleName) {
        this.roleName = roleName;
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
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
