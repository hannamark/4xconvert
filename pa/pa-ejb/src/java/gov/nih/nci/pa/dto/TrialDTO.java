/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({ "PMD.TooManyFields" })
public class TrialDTO {
    private String accrualReportingMethodCode;
    private String assignedIdentifier; // used to store nci-accession number
    private String officialTitle;
    private String phaseCode;
    private String phaseOtherText;
    private String primaryPurposeCode;
    private String primaryPurposeOtherText;
    private String localProtocolIdentifier;
    private String leadOrganizationIdentifier;
    private String leadOrganizationName;
    private String piIdentifier;
    private String piName;
    private String summaryFourOrgIdentifier;
    private String summaryFourOrgName;
    private String summaryFourFundingCategoryCode;
    private String sponsorName;
    private String sponsorIdentifier;
    private String responsiblePartyType;
    private String responsiblePersonName;
    private String responsiblePersonIdentifier;
    private String contactPhone;
    private String contactEmail;
    private String nctIdentifier;
    private String statusCode;
    private String statusDate;
    private String reason;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;
    private String trialType;

    private List<TrialFundingDTO> fundingDtos;
    private List<TrialIndIdeDTO> indDtos;
    /**
     * 
     */
    public TrialDTO() {
        super();
        indDtos = new ArrayList<TrialIndIdeDTO>();
        fundingDtos = new ArrayList<TrialFundingDTO>();
    }
    /**
     * @return the accrualReportingMethodCode
     */
    public String getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    /**
     * @param accrualReportingMethodCode the accrualReportingMethodCode to set
     */
    public void setAccrualReportingMethodCode(String accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
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
     * @return the phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * @return the phaseOtherText
     */
    public String getPhaseOtherText() {
        return phaseOtherText;
    }
    /**
     * @param phaseOtherText the phaseOtherText to set
     */
    public void setPhaseOtherText(String phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
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
     * @return the primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return the localProtocolIdentifier
     */
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }
    /**
     * @param localProtocolIdentifier the localProtocolIdentifier to set
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }
    /**
     * @return the leadOrganizationIdentifier
     */
    public String getLeadOrganizationIdentifier() {
        return leadOrganizationIdentifier;
    }
    /**
     * @param leadOrganizationIdentifier the leadOrganizationIdentifier to set
     */
    public void setLeadOrganizationIdentifier(String leadOrganizationIdentifier) {
        this.leadOrganizationIdentifier = leadOrganizationIdentifier;
    }
    /**
     * @return the leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }
    /**
     * @param leadOrganizationName the leadOrganizationName to set
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }
    /**
     * @return the piIdentifier
     */
    public String getPiIdentifier() {
        return piIdentifier;
    }
    /**
     * @param piIdentifier the piIdentifier to set
     */
    public void setPiIdentifier(String piIdentifier) {
        this.piIdentifier = piIdentifier;
    }
    /**
     * @return the piName
     */
    public String getPiName() {
        return piName;
    }
    /**
     * @param piName the piName to set
     */
    public void setPiName(String piName) {
        this.piName = piName;
    }
    /**
     * @return the summaryFourOrgIdentifier
     */
    public String getSummaryFourOrgIdentifier() {
        return summaryFourOrgIdentifier;
    }
    /**
     * @param summaryFourOrgIdentifier the summaryFourOrgIdentifier to set
     */
    public void setSummaryFourOrgIdentifier(String summaryFourOrgIdentifier) {
        this.summaryFourOrgIdentifier = summaryFourOrgIdentifier;
    }
    /**
     * @return the summaryFourOrgName
     */
    public String getSummaryFourOrgName() {
        return summaryFourOrgName;
    }
    /**
     * @param summaryFourOrgName the summaryFourOrgName to set
     */
    public void setSummaryFourOrgName(String summaryFourOrgName) {
        this.summaryFourOrgName = summaryFourOrgName;
    }
    /**
     * @return the summaryFourFundingCategoryCode
     */
    public String getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * @param summaryFourFundingCategoryCode the summaryFourFundingCategoryCode to set
     */
    public void setSummaryFourFundingCategoryCode(
            String summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * @return the sponsorName
     */
    public String getSponsorName() {
        return sponsorName;
    }
    /**
     * @param sponsorName the sponsorName to set
     */
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
    /**
     * @return the sponsorIdentifier
     */
    public String getSponsorIdentifier() {
        return sponsorIdentifier;
    }
    /**
     * @param sponsorIdentifier the sponsorIdentifier to set
     */
    public void setSponsorIdentifier(String sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }
    /**
     * @return the responsiblePartyType
     */
    public String getResponsiblePartyType() {
        return responsiblePartyType;
    }
    /**
     * @param responsiblePartyType the responsiblePartyType to set
     */
    public void setResponsiblePartyType(String responsiblePartyType) {
        this.responsiblePartyType = responsiblePartyType;
    }
    /**
     * @return the responsiblePersonName
     */
    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }
    /**
     * @param responsiblePersonName the responsiblePersonName to set
     */
    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }
    /**
     * @return the responsiblePersonIdentifier
     */
    public String getResponsiblePersonIdentifier() {
        return responsiblePersonIdentifier;
    }
    /**
     * @param responsiblePersonIdentifier the responsiblePersonIdentifier to set
     */
    public void setResponsiblePersonIdentifier(String responsiblePersonIdentifier) {
        this.responsiblePersonIdentifier = responsiblePersonIdentifier;
    }
    /**
     * @return the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }
    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    /**
     * @return the nctIdentifier
     */
    public String getNctIdentifier() {
        return nctIdentifier;
    }
    /**
     * @param nctIdentifier the nctIdentifier to set
     */
    public void setNctIdentifier(String nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
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
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }
    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
    /**
     * @return the fundingDtos
     */
    public List<TrialFundingDTO> getFundingDtos() {
        return fundingDtos;
    }
    /**
     * @param fundingDtos the fundingDtos to set
     */
    public void setFundingDtos(List<TrialFundingDTO> fundingDtos) {
        this.fundingDtos = fundingDtos;
    }
    /**
     * @return the indDtos
     */
    public List<TrialIndIdeDTO> getIndDtos() {
        return indDtos;
    }
    /**
     * @param indDtos the indDtos to set
     */
    public void setIndDtos(List<TrialIndIdeDTO> indDtos) {
        this.indDtos = indDtos;
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
     * @return the trialType
     */
    public String getTrialType() {
        return trialType;
    }
    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }

    
}
