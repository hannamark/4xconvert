package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.RecruitmentStatusCode;

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
 * Maintains history of accrual status for the StudySite.
 * 
 * @author Hugh Reinhart
 * @since 09/26/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_SITE_ACCRUAL_STATUS")
public class StudySiteAccrualStatus extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private RecruitmentStatusCode statusCode;
    private Timestamp statusDate;
    private StudyParticipation studyParticipation;
    
    
    /**
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public RecruitmentStatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode status code
     */
    public void setStatusCode(RecruitmentStatusCode statusCode) {
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
     * @return the studyParticipation
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PARTICIPATION_IDENTIFIER", updatable = false)
    @NotNull
    public StudyParticipation getStudyParticipation() {
        return studyParticipation;
    }
    /**
     * @param studyParticipation the studyParticipation to set
     */
    public void setStudyParticipation(StudyParticipation studyParticipation) {
        this.studyParticipation = studyParticipation;
    }

}
