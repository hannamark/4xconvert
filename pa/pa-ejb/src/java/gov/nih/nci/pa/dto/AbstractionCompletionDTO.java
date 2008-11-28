package gov.nih.nci.pa.dto;
/**
 * @author Kalpana Guthikonda
 * @since 11/24/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class AbstractionCompletionDTO {


  private String errorCode;
  private String errorDescription;
  private String errorType;
  private String comment;
  
  /**
   * @return errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }
  /**
   * @param errorCode errorCode
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  /**
   * @return errorDescription
   */
  public String getErrorDescription() {
    return errorDescription;
  }
  /**
   * @param errorDescription errorDescription
   */
  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }
  /**
   * @return errorType
   */
  public String getErrorType() {
    return errorType;
  }
  /**
   * @param errorType errorType
   */
  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }
  /**
   * @return comment
   */
  public String getComment() {
    return comment;
  }
  /**
   * @param comment comment
   */
  public void setComment(String comment) {
    this.comment = comment;
  }
}
