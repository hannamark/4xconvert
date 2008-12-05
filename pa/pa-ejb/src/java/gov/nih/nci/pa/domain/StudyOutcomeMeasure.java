package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/29/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@Entity
@Table(name =  "STUDY_OUTCOME_MEASURE")
public class StudyOutcomeMeasure extends AbstractEntity {

    private String name;
    private String timeFrame;
    private Boolean primaryIndicator;
    private Boolean safetyIndicator;
    private StudyProtocol studyProtocol;
    
    /**
     * @return name
     */
    @Column(name = "NAME")
    public String getName() {
        return name;
    }
    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return timeFrame
     */
    @Column(name = "TIMEFRAME")
    public String getTimeFrame() {
        return timeFrame;
    }
    /**
     * @param timeFrame timeFrame
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
    /**
     * @return primaryIndicator
     */
    @Column(name = "PRIMARY_INDICATOR")
    public Boolean getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(Boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return safetyIndicator
     */
    @Column(name = "SAFETY_INDICATOR")
    public Boolean getSafetyIndicator() {
        return safetyIndicator;
    }
    /**
     * @param safetyIndicator safetyIndicator
     */
    public void setSafetyIndicator(Boolean safetyIndicator) {
        this.safetyIndicator = safetyIndicator;
    }
    /**
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    /**
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
}
