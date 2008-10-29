package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
/**
*
* @author Kalpana Guthikonda
* @since 10/23/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class ObservationalStudyProtocolDTO extends StudyProtocolDTO {
    private St biospecimenDescription;
    private Cd biospecimenRetentionCode;
    private Int numberOfGroups;
    private Cd samplingMethodCode;
    private Cd studyModelCode;
    private St studyModelOtherText;
    private Cd timePerspectiveCode;
    private St timePerspectiveOtherText;
    
    /**
     * @return biospecimenDescription
     */
    public St getBiospecimenDescription() {
        return biospecimenDescription;
    }
    /**
     * @param biospecimenDescription biospecimenDescription
     */
    public void setBiospecimenDescription(St biospecimenDescription) {
        this.biospecimenDescription = biospecimenDescription;
    }
    /**
     * @return biospecimenRetentionCode
     */
    public Cd getBiospecimenRetentionCode() {
        return biospecimenRetentionCode;
    }
    /**
     * @param biospecimenRetentionCode biospecimenRetentionCode
     */
    public void setBiospecimenRetentionCode(Cd biospecimenRetentionCode) {
        this.biospecimenRetentionCode = biospecimenRetentionCode;
    }
    /**
     * @return numberOfGroups
     */
    public Int getNumberOfGroups() {
        return numberOfGroups;
    }
    /**
     * @param numberOfGroups numberOfGroups
     */
    public void setNumberOfGroups(Int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
    /**
     * @return samplingMethodCode
     */
    public Cd getSamplingMethodCode() {
        return samplingMethodCode;
    }
    /**
     * @param samplingMethodCode samplingMethodCode
     */
    public void setSamplingMethodCode(Cd samplingMethodCode) {
        this.samplingMethodCode = samplingMethodCode;
    }
    /**
     * @return studyModelCode
     */
    public Cd getStudyModelCode() {
        return studyModelCode;
    }
    /**
     * @param studyModelCode studyModelCode
     */
    public void setStudyModelCode(Cd studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     * @return studyModelOtherText
     */
    public St getStudyModelOtherText() {
        return studyModelOtherText;
    }
    /**
     * @param studyModelOtherText studyModelOtherText
     */
    public void setStudyModelOtherText(St studyModelOtherText) {
        this.studyModelOtherText = studyModelOtherText;
    }
    /**
     * @return timePerspectiveCode
     */
    public Cd getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     * @param timePerspectiveCode timePerspectiveCode
     */
    public void setTimePerspectiveCode(Cd timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }
    /**
     * @return timePerspectiveOtherText
     */
    public St getTimePerspectiveOtherText() {
        return timePerspectiveOtherText;
    }
    /**
     * @param timePerspectiveOtherText timePerspectiveOtherText
     */
    public void setTimePerspectiveOtherText(St timePerspectiveOtherText) {
        this.timePerspectiveOtherText = timePerspectiveOtherText;
    }
}
