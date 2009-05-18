package gov.nih.nci.registry.dto;

import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Pattern;

/**
 * 
 * @author Vrushali
 *
 */
public class PersonBatchDTO extends AddressDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String personCTEPId;
    
    /**
     * @return the firstName
     */
    @NotEmpty(message = "(fieldName) First Name is required.\n")
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
    @NotEmpty(message = "(fieldName) Last Name is required.\n")
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
     * @return the personCTEPId
     */
    public String getPersonCTEPId() {
        return personCTEPId;
    }
    /**
     * @param personCTEPId the personCTEPId to set
     */
    public void setPersonCTEPId(String personCTEPId) {
        this.personCTEPId = personCTEPId;
    }
    /**
     * @return the phone
     */
    @Pattern(regex = "^([\\w\\s\\-\\.\\+\\(\\)])*$" , message = "(fieldName) Phone is required.\n")
    @NotEmpty (message = "")
    @Override
    @SuppressWarnings({"PMD.UselessOverridingMethod" })
    public String getPhone() {
        return super.getPhone();
    }
}
