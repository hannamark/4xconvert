/**
 * 
 */
package gov.nih.nci.registry.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.NotEmpty;

import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.ExcessiveClassLength", "PMD.TooManyFields" })
@Validation
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
    private String localAmendmentNumber;
    private String amendmentDate;
    private String identifier;
    private String programCodeText;
    private String responsibleGenericContactName;
      
    private List<TrialFundingWebDTO> fundingDtos;
    private List<TrialDocumentWebDTO> docDtos;
    private List <TrialIndIdeDTO> indIdeDtos;
    private static final int TRIAL_TITLE_MAX_LENGTH = 4000;
    /**
     * 
     */
    public TrialDTO() {
        super();
        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        docDtos = new ArrayList<TrialDocumentWebDTO>();
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
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
    @NotEmpty(message = "error.submit.trialPhase")
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
    @NotEmpty(message = "error.submit.trialPurpose")
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
    @NotEmpty(message = "error.submit.localProtocolIdentifier")
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
    @NotEmpty (message = "error.submit.leadOrganization")
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
    @NotEmpty (message = "error.submit.leadPrincipalInvestigator")
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
    @NotEmpty (message = "error.submit.sponsor")
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
    @NotEmpty (message = "error.submit.ResponsibelParty")
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
    @NotEmpty (message = "error.submit.contactPhone")
    @org.hibernate.validator.Pattern(regex = "^([\\w\\s\\-\\.\\+\\(\\)])*$", 
            message = "error.register.invalidPhoneNumber")
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
    @NotEmpty (message = "error.submit.contactEmail")
    @org.hibernate.validator.Email (message = "error.submit.invalidContactEmailAddress")
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
    @NotEmpty (message = "error.submit.statusCode")
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
    @NotEmpty (message = "error.submit.statusDate")
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
     * @return the officialTitle
     */
    @NotEmpty (message = "error.submit.trialTitle")
    @org.hibernate.validator.Length(message = "error.submit.trialTitleLength", max = TRIAL_TITLE_MAX_LENGTH)
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
    @NotEmpty (message = "error.submit.startDate")
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
    @NotEmpty(message = "error.submit.completionDate")
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
    @NotEmpty (message = "error.submit.dateType")
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
    @NotEmpty (message = "error.submit.dateType")
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
    @NotEmpty (message = "error.submit.trialType")
    public String getTrialType() {
        return trialType;
    }
    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }
    /**
     * @return the localAmendmentNumber
     */
    public String getLocalAmendmentNumber() {
        return localAmendmentNumber;
    }
    /**
     * @param localAmendmentNumber the localAmendmentNumber to set
     */
    public void setLocalAmendmentNumber(String localAmendmentNumber) {
        this.localAmendmentNumber = localAmendmentNumber;
    }
    /**
     * @return the amendmentDate
     */
    public String getAmendmentDate() {
        return amendmentDate;
    }
    /**
     * @param amendmentDate the amendmentDate to set
     */
    public void setAmendmentDate(String amendmentDate) {
        this.amendmentDate = amendmentDate;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the fundingDtos
     */
    public List<TrialFundingWebDTO> getFundingDtos() {
        return fundingDtos;
    }
    /**
     * @param fundingDtos the fundingDtos to set
     */
    public void setFundingDtos(List<TrialFundingWebDTO> fundingDtos) {
        this.fundingDtos = fundingDtos;
    }
    /**
     * @return the docDtos
     */
    public List<TrialDocumentWebDTO> getDocDtos() {
        return docDtos;
    }
    /**
     * @param docDtos the docDtos to set
     */
    public void setDocDtos(List<TrialDocumentWebDTO> docDtos) {
        this.docDtos = docDtos;
    }
    /**
     * @return the indIdeDtos
     */
    public List<TrialIndIdeDTO> getIndIdeDtos() {
        return indIdeDtos;
    }
    /**
     * @param indIdeDtos the indIdeDtos to set
     */
    public void setIndIdeDtos(List<TrialIndIdeDTO> indIdeDtos) {
        this.indIdeDtos = indIdeDtos;
    }
    /**
     * @return the programCodeText
     */
    public String getProgramCodeText() {
        return programCodeText;
    }
    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(String programCodeText) {
        this.programCodeText = programCodeText;
    }
    /**
     * @return the responsibleGenericContactName
     */
    public String getResponsibleGenericContactName() {
        return responsibleGenericContactName;
    }
    /**
     * @param responsibleGenericContactName the responsibleGenericContactName to set
     */
    public void setResponsibleGenericContactName(
            String responsibleGenericContactName) {
        this.responsibleGenericContactName = responsibleGenericContactName;
    }
}
