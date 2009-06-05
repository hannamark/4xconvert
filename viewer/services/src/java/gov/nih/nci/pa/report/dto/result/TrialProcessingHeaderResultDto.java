package gov.nih.nci.pa.report.dto.result;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * @author Hugh Reinhart
 * @since 06/04/2009
 */
public class TrialProcessingHeaderResultDto {

    private St officialTitle = StConverter.convertToSt(null);
    private St assignedIdentifier = StConverter.convertToSt(null);
    private St leadOrganization = StConverter.convertToSt(null);
    private Cd statusCode = CdConverter.convertToCd(null);
    private St userLastCreated  = StConverter.convertToSt(null);

    /**
     * @return the officialTitle
     */
    public St getOfficialTitle() {
        return officialTitle;
    }
    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(St officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * @return the assignedIdentifier
     */
    public St getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(St assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the leadOrganization
     */
    public St getLeadOrganization() {
        return leadOrganization;
    }
    /**
     * @param leadOrganization the leadOrganization to set
     */
    public void setLeadOrganization(St leadOrganization) {
        this.leadOrganization = leadOrganization;
    }
    /**
     * @return the statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the userLastCreated
     */
    public St getUserLastCreated() {
        return userLastCreated;
    }
    /**
     * @param userLastCreated the userLastCreated to set
     */
    public void setUserLastCreated(St userLastCreated) {
        this.userLastCreated = userLastCreated;
    }
}