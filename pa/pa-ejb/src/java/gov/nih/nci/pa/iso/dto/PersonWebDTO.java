package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.pa.enums.StudyContactRoleCode;

/**
 * 
 * @author Harsha
 * 
 */
public class PersonWebDTO {
    private String firstName;
    private String lastName;
    private Long id;
    private StudyContactRoleCode roleName;

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
}
