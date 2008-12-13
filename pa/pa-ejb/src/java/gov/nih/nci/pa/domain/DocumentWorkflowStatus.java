package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * These are the workflow status associated with a Protocol from Submission thru Abstraction..
 * 
 * @author Naveen Amiruddin
 * @since 07/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "DOCUMENT_WORKFLOW_STATUS")
public class DocumentWorkflowStatus extends AbstractEntity {

    private static final long serialVersionUID = 1234567890L;
    private String commentText;
    private DocumentWorkflowStatusCode statusCode;
    private Timestamp statusDateRangeLow;
    private StudyProtocol studyProtocol;
    
    /**
     * @return commentText
     */
    @Column(name = "COMMENT_TEXT")
    public String getCommentText() {
      return commentText;
    }
    /**
     * @param commentText commentText
     */
    public void setCommentText(String commentText) {
      this.commentText = commentText;
    }
    /**
     * 
     * @return documentWorkflowStatusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public DocumentWorkflowStatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode statusCode
     */
    public void setStatusCode(
            DocumentWorkflowStatusCode statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 
     * @return statusDateRangeLow
     */
    @Column(name = "STATUS_DATE_RANGE_LOW")
    public Timestamp getStatusDateRangeLow() {
        return statusDateRangeLow;
    }
    /**
     * 
     * @param statusDateRangeLow statusDateRangeLow
     */
    public void setStatusDateRangeLow(Timestamp statusDateRangeLow) {
        this.statusDateRangeLow = statusDateRangeLow;
    }
    /**
     * 
     * @return studyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
    public StudyProtocol getStudyProtocol() {
       return studyProtocol;
    }
    /**
     * 
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
}
