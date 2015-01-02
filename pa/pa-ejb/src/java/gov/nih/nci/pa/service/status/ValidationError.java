package gov.nih.nci.pa.service.status;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class ValidationError {
    
    private ErrorType errorType;
    private String errorMessage;
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
}
