package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.result.TrialProcessingHeaderResultDto;

/**
 * @author Hugh Reinhart
 * @since 06/04/2009
 */
public class TrialProcessingHeaderResultWebDto {
    private String officialTitle;
    private String assignedIdentifier;
    private String leadOrganization;
    private String statusCode;
    private String userLastCreated;

    /**
     * Constructor using service dto.
     * @param dto the service iso dto
     */
    public TrialProcessingHeaderResultWebDto(TrialProcessingHeaderResultDto dto) {
        if (dto == null) { return; }
        assignedIdentifier = StConverter.convertToString(dto.getAssignedIdentifier());
        officialTitle = StConverter.convertToString(dto.getOfficialTitle());
        leadOrganization = StConverter.convertToString(dto.getLeadOrganization());
        statusCode = CdConverter.convertCdToString(dto.getStatusCode());
        userLastCreated = StConverter.convertToString(dto.getUserLastCreated());
    }

    /**
     * @return the officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * @return the assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the leadOrganization
     */
    public String getLeadOrganization() {
        return leadOrganization;
    }
    /**
     * @param leadOrganization the leadOrganization to set
     */
    public void setLeadOrganization(String leadOrganization) {
        this.leadOrganization = leadOrganization;
    }
    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the userLastCreated
     */
    public String getUserLastCreated() {
        return userLastCreated;
    }
    /**
     * @param userLastCreated the userLastCreated to set
     */
    public void setUserLastCreated(String userLastCreated) {
        this.userLastCreated = userLastCreated;
    }

}
