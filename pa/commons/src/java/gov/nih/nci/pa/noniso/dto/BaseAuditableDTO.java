/**
 * 
 */
package gov.nih.nci.pa.noniso.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vinodh
 *
 */
public class BaseAuditableDTO extends BaseDTO implements Serializable {

   
    private static final long serialVersionUID = -5665150782812241382L;
   
    private Date dateLastCreated;
    private String userLastCreated;
    private Date dateLastUpdated;
    private String userLastUpdated;
    /**
     * @return the dateLastCreated
     */
    public Date getDateLastCreated() {
        return dateLastCreated;
    }
    /**
     * @param dateLastCreated the dateLastCreated to set
     */
    public void setDateLastCreated(Date dateLastCreated) {
        this.dateLastCreated = dateLastCreated;
    }
    /**
     * @return the userLastCreated
     */
    public String getUserLastCreated() {
        return userLastCreated;
    }
    /**
     * @param userLastCreated the userLastCreated to set
     */
    public void setUserLastCreated(String userLastCreated) {
        this.userLastCreated = userLastCreated;
    }
    /**
     * @return the dateLastUpdated
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }
    /**
     * @param dateLastUpdated the dateLastUpdated to set
     */
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
    /**
     * @return the userLastUpdated
     */
    public String getUserLastUpdated() {
        return userLastUpdated;
    }
    /**
     * @param userLastUpdated the userLastUpdated to set
     */
    public void setUserLastUpdated(String userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }
    
    

}