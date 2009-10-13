package gov.nih.nci.registry.dto;

import org.hibernate.validator.NotEmpty;

/**
 * 
 * @author Vrushali
 *
 */
public class OrganizationBatchDTO extends AddressDTO {
    private String name;
    private String orgCTEPId;
    private String type;
    private static final int ORG_NAME_MAX_LENGTH = 160;
    
    /**
     * @return the name
     */
    @org.hibernate.validator.Length(message = "(fieldName) Name must be 160 characters max.\n", 
            max = ORG_NAME_MAX_LENGTH)
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
