package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyStatusCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Describes the comprehensive state of the study.
 * 
 * @author Naveen Amiruddin
 * @since 07/22/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_OVERALL_STATUS")
public class StudyOverallStatus extends AbstractStudyEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private String commentText;
    private StudyStatusCode statusCode;
    private Timestamp statusDate;
    
    
    /**
     * 
     * @return commentText
     */
    @Column(name = "COMMENT_TEXT")
    public String getCommentText() {
        return commentText;
    }
    /**
     * 
     * @param commentText comment text
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    /**
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public StudyStatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode status code
     */
    public void setStatusCode(StudyStatusCode statusCode) {
       this.statusCode = statusCode;
    }
    /**
     * 
     * @return statusDate
     */
    @Column(name = "STATUS_DATE")
    public Timestamp  getStatusDate() {
        return statusDate;
    }
    /**
     * 
     * @param statusDate status Date
     */
    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
    }
}
