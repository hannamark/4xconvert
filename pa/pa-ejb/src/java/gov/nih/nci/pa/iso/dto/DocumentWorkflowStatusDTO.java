package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;

/**
 * @author Kalpana Guthikonda
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class DocumentWorkflowStatusDTO extends StudyDTO {
    private St commentText;
    private Cd statusCode;
    private Ts statusDateRange;
    
    
    /**
     * @return commentText
     */
    public St getCommentText() {
      return commentText;
    }
    /**
     * @param commentText commentText
     */
    public void setCommentText(St commentText) {
      this.commentText = commentText;
    }
    /**
     * @return statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode statusCode
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDateRange
     */
    public Ts getStatusDateRange() {
        return statusDateRange;
    }
    /**
     * @param statusDateRange the statusDateRange to set
     */
    public void setStatusDateRange(Ts statusDateRange) {
        this.statusDateRange = statusDateRange;
    }
}
