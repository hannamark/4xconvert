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
    
    /**
     * @return errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }
    
    /**
     * @param errorType ErrorType
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
    
    /**
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * @param errorMessage String
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
}
