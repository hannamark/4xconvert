package gov.nih.nci.pa.report.dto.result;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.Lov;

/**
 * @author Hugh Reinhart
 * @since  06/04/2009
 */
public class TrialProcessingResultDto {

    private Int submissionNumber = IntConverter.convertToInt((Integer) null);
    private Cd milestoneCode = CdConverter.convertToCd((Lov) null);
    private St milestoneDays = StConverter.convertToSt(null);
    private St cumulativeDays = StConverter.convertToSt(null);

    /**
     * @return the submissionNumber
     */
    public Int getSubmissionNumber() {
        return submissionNumber;
    }
    /**
     * @param submissionNumber the submissionNumber to set
     */
    public void setSubmissionNumber(Int submissionNumber) {
        this.submissionNumber = submissionNumber;
    }
    /**
     * @return the milestoneCode
     */
    public Cd getMilestoneCode() {
        return milestoneCode;
    }
    /**
     * @param milestoneCode the milestoneCode to set
     */
    public void setMilestoneCode(Cd milestoneCode) {
        this.milestoneCode = milestoneCode;
    }
    /**
     * @return the milestoneDays
     */
    public St getMilestoneDays() {
        return milestoneDays;
    }
    /**
     * @param milestoneDays the milestoneDays to set
     */
    public void setMilestoneDays(St milestoneDays) {
        this.milestoneDays = milestoneDays;
    }
    /**
     * @return the cumulativeDays
     */
    public St getCumulativeDays() {
        return cumulativeDays;
    }
    /**
     * @param cumulativeDays the cumulativeDays to set
     */
    public void setCumulativeDays(St cumulativeDays) {
        this.cumulativeDays = cumulativeDays;
    }
}
