package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ResponsibilityCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * .
 * 
 * @author Naveen Amiruddin
 * @since 07/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_COORDINATING_CENTER_ROLES")
public class StudyCoordinatingCenterRole extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private ResponsibilityCode responsibilityCode;
    private StudyCoordinatingCenter studyCoordinatingCenter;
    
    /**
     * 
     * @return responsibilityCode
     */
    @Column(name = "RESPONSIBILITY_CODE")
    @Enumerated(EnumType.STRING)
    public ResponsibilityCode getResponsibilityCode() {
        return responsibilityCode;
    }
    /**
     * 
     * @param responsibilityCode responsibilityCode
     */
    public void setResponsibilityCode(ResponsibilityCode responsibilityCode) {
        this.responsibilityCode = responsibilityCode;
    }
    /**
     * 
     * @return studyCoordinatingCenter
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_COORDINATING_CENTER_IDENTIFIER", nullable = false)
    public StudyCoordinatingCenter getStudyCoordinatingCenter() {
        return studyCoordinatingCenter;
    }
    /**
     * 
     * @param studyCoordinatingCenter studyCoordinatingCenter
     */
    public void setStudyCoordinatingCenter(
            StudyCoordinatingCenter studyCoordinatingCenter) {
        this.studyCoordinatingCenter = studyCoordinatingCenter;
    }
}
