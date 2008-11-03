package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * domain class for ObservationalStudyProtocol.
 *
 * @author Naveen Amiruddin
 * @since 09/07/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Entity
@DiscriminatorColumn(
        name = "ObservationalStudyProtocol",
        discriminatorType = DiscriminatorType.STRING
    )

public class ObservationalStudyProtocol extends StudyProtocol {
    
    private String biospecimenDescription;
    private BiospecimenRetentionCode biospecimenRetentionCode;
    private Integer numberOfGroups;
    private SamplingMethodCode samplingMethodCode;
    private StudyModelCode studyModelCode;
    private String studyModelOtherText;
    private TimePerspectiveCode timePerspectiveCode;
    private String timePerspectiveOtherText;
    /**
     * 
     * @return biospecimenDescription
     */
    @Column(name = "BIO_SPECIMEN_DESCRIPTION")
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
    @Column(name = "BIO_SPECIMEN_RETENTION_CODE")
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
     * @return samplingMethodCode
     */
    @Column(name = "SAMPLING_METHOD_CODE")
    @Enumerated(EnumType.STRING)
    public SamplingMethodCode getSamplingMethodCode() {
        return samplingMethodCode;
    }
    /**
     * 
     * @param samplingMethodCode samplingMethodCode
     */
    public void setSamplingMethodCode(SamplingMethodCode samplingMethodCode) {
        this.samplingMethodCode = samplingMethodCode;
    }
    /**
     * @return numberOfGroups
     */
    @Column(name = "NUMBER_OF_GROUPS")
    public Integer getNumberOfGroups() {
        return numberOfGroups;
    }
    /**
     * @param numberOfGroups numberOfGroups
     */
    public void setNumberOfGroups(Integer numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
    /**
     * @return studyModelCode
     */
    @Column(name = "STUDY_MODEL_CODE")
    @Enumerated(EnumType.STRING)
    public StudyModelCode getStudyModelCode() {
        return studyModelCode;
    }
    /**
     * @param studyModelCode studyModelCode
     */
    public void setStudyModelCode(StudyModelCode studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     * @return studyModelOtherText
     */
    @Column(name = "STUDY_MODEL_OTHER_TEXT")
    public String getStudyModelOtherText() {
        return studyModelOtherText;
    }
    /**
     * @param studyModelOtherText studyModelOtherText
     */
    public void setStudyModelOtherText(String studyModelOtherText) {
        this.studyModelOtherText = studyModelOtherText;
    }
    /**
     * @return timePerspectiveCode
     */
    @Column(name = "TIME_PERSPECTIVE_CODE")
    @Enumerated(EnumType.STRING)
    public TimePerspectiveCode getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     * @param timePerspectiveCode timePerspectiveCode
     */
    public void setTimePerspectiveCode(TimePerspectiveCode timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }
    /**
     * @return timePerspectiveOtherText
     */
    @Column(name = "TIME_PERSPECTIVE_OTHER_TEXT")
    public String getTimePerspectiveOtherText() {
        return timePerspectiveOtherText;
    }
    /**
     * @param timePerspectiveOtherText timePerspectiveOtherText
     */
    public void setTimePerspectiveOtherText(String timePerspectiveOtherText) {
        this.timePerspectiveOtherText = timePerspectiveOtherText;
    }   

}
