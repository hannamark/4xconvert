/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;

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
 * Describes the recruitment state of the study.
 * 
 * @author Hugh Reinhart
 * @since 10/10/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_RECRUITMENT_STATUS")
public class StudyRecruitmentStatus extends AbstractEntity {
    private static final long serialVersionUID = 1234568364L;
    
    private StudyRecruitmentStatusCode statusCode;
    private Timestamp statusDate;
    private StudyProtocol studyProtocol;
    
    
    /**
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public StudyRecruitmentStatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode status code
     */
    public void setStatusCode(StudyRecruitmentStatusCode statusCode) {
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

    /**
     * 
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", updatable = false)
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
