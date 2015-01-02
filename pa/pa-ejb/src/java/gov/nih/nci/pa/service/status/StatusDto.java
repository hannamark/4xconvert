package gov.nih.nci.pa.service.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StatusDto {
    
    private String statusCode;
    private Date statusDate;
    private String reason;
    private boolean systemCreated;
    private Long id;
    
    private boolean editable;
    private boolean deletable;
    private boolean undoable;
    
    private List<String> comments = new ArrayList<String>();
    
    private List<ValidationError> validationErrors = new ArrayList<ValidationError>();
    
    /**
     * Returns status code
     * @return status code string
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set status code
     * @param statusCode String
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns status date
     * @return status date
     */
    public Date getStatusDate() {
        return statusDate;
    }
    
    /**
     * Set status date
     * @param statusDate - Date
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
    
    /**
     *  Returns reason for transition
     * @return reason String
     */
    public String getReason() {
        return reason;
    }
    
    /**
     * Set reason for transition
     * @param reason String
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Returns flag to indicate system created
     * @return flag boolean
     */
    public boolean isSystemCreated() {
        return systemCreated;
    }

    /**
     * Set flag that indicates system created
     * @param systemCreated - boolean
     */
    public void setSystemCreated(boolean systemCreated) {
        this.systemCreated = systemCreated;
    }

    /**
     * Returns Status instance id
     * @return identifier
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set Status instance id
     * @param id - Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns flag that indicates status is editable or not
     * @return flag - boolean
     */
    public boolean isEditable() {
        return editable;
    }
    
    /**
     * Sets flag that indicates status is editable or not
     * @param editable - boolean
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    /**
     * Returns flag that indicates status is deletable or not
     * @return flag - boolean
     */
    public boolean isDeletable() {
        return deletable;
    }
    
    /**
     * Sets flag that indicates status is deletable or not
     * @param deletable - boolean
     */
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
    
    /**
     * Returns flag that indicates status is undoable or not
     * @return undoable - boolean
     */
    public boolean isUndoable() {
        return undoable;
    }
    
    /**
     * Returns flag that indicates status is undoable or not
     * @param undoable - boolean
     */
    public void setUndoable(boolean undoable) {
        this.undoable = undoable;
    }
    
    /**
     * Returns list of comments or empty list
     * @return List<String> - List of comments
     */
    public List<String> getComments() {
        if (comments == null) {
            comments = new ArrayList<String>();
        }
        return comments;
    }
    
    /**
     * Sets list of comments
     * @param comments - List<String>
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }
    
    /**
     * Returns list of status transition validation errors or empty list
     * @return List<ValidationError>
     */
    public List<ValidationError> getValidationErrors() {
        if (validationErrors == null) {
            validationErrors = new ArrayList<ValidationError>();
        }
        return validationErrors;
    }
    
    /**
     * Sets list of status transition validation errors
     * @param validationErrors - List<ValidationError>
     */
    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
    
    /**
     * Returns flag to indicate there are errors
     * @return boolean 
     */
    public boolean hasErrors() {
        return getValidationErrors().isEmpty();
    }
}
