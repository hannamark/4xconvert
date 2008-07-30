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
    
    

}
