/**
 * 
 */
package gov.nih.nci.pa.dto;


/**
 * DTO class for displaying study status history as a list.
 * 
 * @author Hugh Reinhart
 * @since 09/10/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusWebDTO {
    private Long id;
    private String statusCode;
    private String statusDate;

    /**
     * @param id id
     * @param statusCode text status
     * @param statusDate text date
     */
    public StudyOverallStatusWebDTO(Long id, String statusCode, String statusDate) {
        super();
        this.id = id;
        this.statusCode = statusCode;
        this.statusDate = statusDate;
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
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
}
