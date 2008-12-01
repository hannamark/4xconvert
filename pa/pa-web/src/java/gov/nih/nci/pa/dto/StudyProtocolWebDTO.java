/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Hong Gao
 *
 */
public class StudyProtocolWebDTO {
    private String trialTitle;
    private String trialPhase;
    private String nciAccessionNumber;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;
    private String studyProtocolType;
    private String primaryPurposeCode;
    private String actionName;

    /**
     * @param iso
     * StudyProtocolWebDTO object
     */
    public StudyProtocolWebDTO(StudyProtocolDTO iso) {
        super();
        this.studyProtocolType = iso.getStudyProtocolType().getValue();
        this.primaryPurposeCode = iso.getPrimaryPurposeCode().getCode();
        this.trialPhase = iso.getPhaseCode().getCode();
        this.trialTitle = iso.getOfficialTitle().getValue();
        this.nciAccessionNumber =  IiConverter.convertToString(iso.getAssignedIdentifier());
        if (iso.getStartDate() != null) {
            this.startDate = PAUtil.normalizeDateString(
                           TsConverter.convertToTimestamp(iso.getStartDate()).toString());
            this.startDateType = CdConverter.convertCdToString(iso.getStartDateTypeCode());
        }
        if (iso.getPrimaryCompletionDate() != null) {
            this.completionDate = PAUtil.normalizeDateString(
                    TsConverter.convertToTimestamp(iso.getPrimaryCompletionDate()).toString());
            
            this.completionDateType = CdConverter.convertCdToString(
                                        iso.getPrimaryCompletionDateTypeCode());
        }
    }
    /** .
     *  Default Constructor
     */
    public StudyProtocolWebDTO() {
        super();
    }

    /**
     * @return result
     */
    public String getTrialTitle() {
        return trialTitle;
    }
    /**
     * @param trialTitle trialTitle
     */
    public void setTrialTitle(String trialTitle) {
        this.trialTitle = trialTitle;
    }
    /**
     * @return result
     */
    public String getTrialPhase() {
        return trialPhase;
    }
    /**
     * @param trialPhase trialPhase
     */
    public void setTrialPhase(String trialPhase) {
        this.trialPhase = trialPhase;
    }
    
    /**
     * @return result
     */
    public String getNciAccessionNumber() {
        return nciAccessionNumber;
    }
    /**
     * @param nciAccessionNumber nciAccessionNumber
     */
    public void setNciAccessionNumber(String nciAccessionNumber) {
        this.nciAccessionNumber = nciAccessionNumber;
    }
    
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return the startDateType
     */
    public String getStartDateType() {
        return startDateType;
    }

    /**
     * @param startDateType the startDateType to set
     */
    public void setStartDateType(String startDateType) {
        this.startDateType = startDateType;
    }

    /**
     * @return the completionDateType
     */
    public String getCompletionDateType() {
        return completionDateType;
    }

    /**
     * @param completionDateType the completionDateType to set
     */
    public void setCompletionDateType(String completionDateType) {
        this.completionDateType = completionDateType;
    }
    /**
     * @return the studyProtocolType
     */
    public String getStudyProtocolType() {
        return studyProtocolType;
    }
    /**
     * @param studyProtocolType the studyProtocolType to set
     */
    public void setStudyProtocolType(String studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }
    /**
     * @return the primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return the actionName
     */
    public String getActionName() {
        return actionName;
    }
    /**
     * @param actionName the actionName to set
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


}
