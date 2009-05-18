package gov.nih.nci.registry.dto;

import org.hibernate.validator.NotEmpty;

import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Vrushali
 *
 */
@Validation
public class OrganizationBatchDTO extends AddressDTO {
    private String name;
    private String orgCTEPId;
    private String type;
    
    /**
     * @return the name
     */
    @NotEmpty(message = "(fieldName) Name is required.\n")
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the orgCTEPId
     */
    public String getOrgCTEPId() {
        return orgCTEPId;
    }
    /**
     * @param orgCTEPId the orgCTEPId to set
     */
    public void setOrgCTEPId(String orgCTEPId) {
        this.orgCTEPId = orgCTEPId;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
   
    
}
