/**
 * 
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Bala Nair
 * 
 */
public class InterventionalStudyProtocolWebDTO {

    private String trialTitle;
    private String trialPhase;
    private String trialPurpose;
    private String nciAccessionNumber;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;
    private String otherPhaseText;
    private String otherPurposeText;

    /**
     * @param iso
     * InterventionalStudyProtocolWebDTO object
     */
    public InterventionalStudyProtocolWebDTO(StudyProtocolDTO iso) {
        super();
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
    public InterventionalStudyProtocolWebDTO() {
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
     * @return the trialPurpose
     */
    public String getTrialPurpose() {
        return trialPurpose;
    }
    /**
     * @param trialPurpose the trialPurpose to set
     */
    public void setTrialPurpose(String trialPurpose) {
        this.trialPurpose = trialPurpose;
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
     * @return the otherPhaseText
     */
    public String getOtherPhaseText() {
        return otherPhaseText;
    }
    /**
     * @param otherPhaseText the otherPhaseText to set
     */
    public void setOtherPhaseText(String otherPhaseText) {
        this.otherPhaseText = otherPhaseText;
    }
    /**
     * @return the otherPurposeText
     */
    public String getOtherPurposeText() {
        return otherPurposeText;
    }
    /**
     * @param otherPurposeText the otherPurposeText to set
     */
    public void setOtherPurposeText(String otherPurposeText) {
        this.otherPurposeText = otherPurposeText;
    }


}
