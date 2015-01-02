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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSystemCreated() {
        return systemCreated;
    }

    public void setSystemCreated(boolean systemCreated) {
        this.systemCreated = systemCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isUndoable() {
        return undoable;
    }

    public void setUndoable(boolean undoable) {
        this.undoable = undoable;
    }

    public List<String> getComments() {
        if(comments == null) {
            comments = new ArrayList<String>();
        }
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<ValidationError> getValidationErrors() {
        if(validationErrors == null) {
            validationErrors = new ArrayList<ValidationError>();
        }
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
    
    public boolean hasErrors() {
        return getValidationErrors().isEmpty();
    }
}
