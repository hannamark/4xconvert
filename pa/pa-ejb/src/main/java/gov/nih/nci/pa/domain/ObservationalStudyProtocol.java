package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Entity;

import gov.nih.nci.pa.enums.BiospecimenRetentionCode;

/**
 * An action plan and execution of a pre-clinical or clinical study including all 
 * activities to test a particular hypothesis that is the basis of the study regarding the 
 * effectiveness of a particular treatment, drug, device, procedure, or care plan. 
 * This includes prevention, observational, therapeutic, and other types of studies 
 * that involve subjects. 
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@DiscriminatorValue(value = "OSP")
public class ObservationalStudyProtocol extends StudyProtocol {
    
    private static final long serialVersionUID = 1234567890L;
    
    /** Specify all types of biospecimens to be retained (e.g., whole blood, serum, 
     * white cells, urine, tissue). */
    private String biospecimenDescription;
    
    private BiospecimenRetentionCode biospecimenRetentionCode;

    /** Number of study groups/cohorts. Enter 1 for a single-group study. Many observational
     *  studies have one group/cohort; case control studies typically have two* */
    private Integer groupNumber;
    //@todo below two to be changed to enum, once we know the values
    private String studyModelCode;
    private String timePerspectiveCode;
    
    /**
     * 
     * @return biospecimenDescription
     */
    @Column(name = "BIOSPECIMEN_DESCRIPTION")
    public String getBiospecimenDescription() {
        return biospecimenDescription;
    }
    /**
     * 
     * @param biospecimenDescription biospecimenDescription
     */
    public void setBiospecimenDescription(String biospecimenDescription) {
        this.biospecimenDescription = biospecimenDescription;
    }
    /**
     * 
     * @return biospecimenRetentionCode
     */
    @Column(name = "BIOSPECIMEN_RETENTION_CODE")
    @Enumerated(EnumType.STRING)
    public BiospecimenRetentionCode getBiospecimenRetentionCode() {
        return biospecimenRetentionCode;
    }
    /**
     * 
     * @param biospecimenRetentionCode biospecimenRetentionCode
     */
    public void setBiospecimenRetentionCode(
            BiospecimenRetentionCode biospecimenRetentionCode) {
        this.biospecimenRetentionCode = biospecimenRetentionCode;
    }
    /**
     * 
     * @return groupNumber
     */
    @Column(name = "GROUP_NUMBER")
    public Integer getGroupNumber() {
        return groupNumber;
    }
    /**
     * 
     * @param groupNumber Number of study groups/cohorts. 
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }
    /**
     * 
     * @return studyModelCode
     */
    @Column(name = "STUDY_MODEL_CODE")
    public String getStudyModelCode() {
        return studyModelCode;
    }
    /**
     * 
     * @param studyModelCode primary strategy for subject identification and follow-up
     */
    public void setStudyModelCode(String studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     * 
     * @return timePerspectiveCode
     */
    @Column(name = "TIME_PRESPECTIVE_CODE")
    public String getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     * 
     * @param timePerspectiveCode temporal relationship of observation period to time of subject enrollment
     */
    public void setTimePerspectiveCode(String timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }
}
