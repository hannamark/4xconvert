package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author Reshma Koganti
 *
 */
public class CaDSRDTO implements Serializable {
    private static final long serialVersionUID = -2615508147751532945L;
    private String id;
    private String preferredQuestion;
    private Long publicId;
    private String version;
    private String vmName;
    private String vmMeaning;
    private String vmDescription;
    
    /**
     * @return the preferredQuestion
     */
     public String getPreferredQuestion() {
       return preferredQuestion;
     }

     /**
     * @param preferredQuestion the preferredQuestion to set
     */
    public void setPreferredQuestion(String preferredQuestion) {
       this.preferredQuestion = preferredQuestion;
    }

    /**
     * @return the publicId
     */
     public Long getPublicId() {
       return publicId;
     }

     /**
      * @param publicId the publicId to set
      */
     public void setPublicId(Long publicId) {
       this.publicId = publicId;
     }

     /**
      * @return the version
      */
      public String getVersion() {
        return version;
     }

    /**
     * @param version the version to set
     */
     public void setVersion(String version) {
       this.version = version;
     }

    /**
     * @return the id
     */
    public String getId() {
      return id;
    }

    /**
     * @param id the id to set
     */
     public void setId(String id) {
       this.id = id;
     }

    /**
     * @return the name
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * @param name the value meaning name to set
     */
    public void setVmName(String name) {
        this.vmName = name;
    }

    /**
     * @return the value meaning meaning
     */
    public String getVmMeaning() {
        return vmMeaning;
    }

    /**
     * @param meaning the value meaning meaning to set
     */
    public void setVmMeaning(String meaning) {
        this.vmMeaning = meaning;
    }

    /**
     * @return the value meaning description
     */
    public String getVmDescription() {
        return vmDescription;
    }

    /**
     * @param description the value meaning description to set
     */
    public void setVmDescription(String description) {
        this.vmDescription = description;
    }
}
