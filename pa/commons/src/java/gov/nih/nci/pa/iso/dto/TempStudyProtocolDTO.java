/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.TooManyFields" })
public class TempStudyProtocolDTO extends BaseDTO {
    private static final long serialVersionUID = 7235772554482606139L;
    private St localProtocolIdentifier;
    private St nctIdentifier;
    private St ctepIdentifier;
    private St dcpIdentifier;
    private St officialTitle;
    private Cd phaseCode;
    private St phaseOtherText;
    private St trialType;
    private Cd primaryPurposeCode;
    private St primaryPurposeOtherText;
    
    private Ii leadOrganizationIdentifier;
    private Ii piIdentifier;
    private Ii sponsorIdentifier;

    private St responsiblePartyType;
    private Ii responsibleIdentifier;
    private St contactPhone;
    private St contactEmail;

    private Ii summaryFourOrgIdentifier;
    private Cd summaryFourFundingCategoryCode;
    private St programCodeText;
    private Cd trialStatusCode;
    private Ts trialStatusDate;
    private St statusReason;
    
    private Ts startDate;
    private Cd startDateTypeCode;
    private Ts primaryCompletionDate;
    private Cd primaryCompletionDateTypeCode;
    
    private St studyProtocolType;
    
    private Bl dataMonitoringCommitteeAppointedIndicator;
    private Bl fdaRegulatedIndicator;
    private Bl section801Indicator;
    private Bl delayedpostingIndicator;

    private St userLastCreated;
    private Bl nciDesignatedCancerCenterIndicator;
    private Bl proprietaryTrialIndicator;
    private Ii oversightAuthorityCountryId;
    private Ii oversightAuthorityOrgId;
    /**
     * @return the localProtocolIdentifier
     */
    public St getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }
    /**
     * @param localProtocolIdentifier the localProtocolIdentifier to set
     */
    public void setLocalProtocolIdentifier(St localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }
    /**
     * @return the nctIdentifier
     */
    public St getNctIdentifier() {
        return nctIdentifier;
    }
    /**
     * @param nctIdentifier the nctIdentifier to set
     */
    public void setNctIdentifier(St nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }
    /**
     * @return the ctepIdentifier
     */
    public St getCtepIdentifier() {
        return ctepIdentifier;
    }
    /**
     * @param ctepIdentifier the ctepIdentifier to set
     */
    public void setCtepIdentifier(St ctepIdentifier) {
        this.ctepIdentifier = ctepIdentifier;
    }
    /**
     * @return the dcpIdentifier
     */
    public St getDcpIdentifier() {
        return dcpIdentifier;
    }
    /**
     * @param dcpIdentifier the dcpIdentifier to set
     */
    public void setDcpIdentifier(St dcpIdentifier) {
        this.dcpIdentifier = dcpIdentifier;
    }
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
     * @return the phaseCode
     */
    public Cd getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(Cd phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * @return the phaseOtherText
     */
    public St getPhaseOtherText() {
        return phaseOtherText;
    }
    /**
     * @param phaseOtherText the phaseOtherText to set
     */
    public void setPhaseOtherText(St phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
    }
    /**
     * @return the trialType
     */
    public St getTrialType() {
        return trialType;
    }
    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(St trialType) {
        this.trialType = trialType;
    }
    /**
     * @return the primaryPurposeCode
     */
    public Cd getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(Cd primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return the primaryPurposeOtherText
     */
    public St getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(St primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return the leadOrganizationIdentifier
     */
    public Ii getLeadOrganizationIdentifier() {
        return leadOrganizationIdentifier;
    }
    /**
     * @param leadOrganizationIdentifier the leadOrganizationIdentifier to set
     */
    public void setLeadOrganizationIdentifier(Ii leadOrganizationIdentifier) {
        this.leadOrganizationIdentifier = leadOrganizationIdentifier;
    }
    /**
     * @return the piIdentifier
     */
    public Ii getPiIdentifier() {
        return piIdentifier;
    }
    /**
     * @param piIdentifier the piIdentifier to set
     */
    public void setPiIdentifier(Ii piIdentifier) {
        this.piIdentifier = piIdentifier;
    }
    /**
     * @return the sponsorIdentifier
     */
    public Ii getSponsorIdentifier() {
        return sponsorIdentifier;
    }
    /**
     * @param sponsorIdentifier the sponsorIdentifier to set
     */
    public void setSponsorIdentifier(Ii sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }
    /**
     * @return the responsiblePartyType
     */
    public St getResponsiblePartyType() {
        return responsiblePartyType;
    }
    /**
     * @param responsiblePartyType the responsiblePartyType to set
     */
    public void setResponsiblePartyType(St responsiblePartyType) {
        this.responsiblePartyType = responsiblePartyType;
    }
    /**
     * @return the responsibleIdentifier
     */
    public Ii getResponsibleIdentifier() {
        return responsibleIdentifier;
    }
    /**
     * @param responsibleIdentifier the responsibleIdentifier to set
     */
    public void setResponsibleIdentifier(Ii responsibleIdentifier) {
        this.responsibleIdentifier = responsibleIdentifier;
    }
    /**
     * @return the contactPhone
     */
    public St getContactPhone() {
        return contactPhone;
    }
    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(St contactPhone) {
        this.contactPhone = contactPhone;
    }
    /**
     * @return the contactEmail
     */
    public St getContactEmail() {
        return contactEmail;
    }
    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(St contactEmail) {
        this.contactEmail = contactEmail;
    }
    /**
     * @return the summaryFourOrgIdentifier
     */
    public Ii getSummaryFourOrgIdentifier() {
        return summaryFourOrgIdentifier;
    }
    /**
     * @param summaryFourOrgIdentifier the summaryFourOrgIdentifier to set
     */
    public void setSummaryFourOrgIdentifier(Ii summaryFourOrgIdentifier) {
        this.summaryFourOrgIdentifier = summaryFourOrgIdentifier;
    }
    /**
     * @return the summaryFourFundingCategoryCode
     */
    public Cd getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * @param summaryFourFundingCategoryCode the summaryFourFundingCategoryCode to set
     */
    public void setSummaryFourFundingCategoryCode(Cd summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * @return the programCodeText
     */
    public St getProgramCodeText() {
        return programCodeText;
    }
    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(St programCodeText) {
        this.programCodeText = programCodeText;
    }
    /**
     * @return the statusCode
     */
    public Cd getTrialStatusCode() {
        return trialStatusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setTrialStatusCode(Cd statusCode) {
        this.trialStatusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public Ts getTrialStatusDate() {
        return trialStatusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setTrialStatusDate(Ts statusDate) {
        this.trialStatusDate = statusDate;
    }
    /**
     * @return the statusReason
     */
    public St getStatusReason() {
        return statusReason;
    }
    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(St statusReason) {
        this.statusReason = statusReason;
    }
    /**
     * @return the startDate
     */
    public Ts getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Ts startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the startDateTypeCode
     */
    public Cd getStartDateTypeCode() {
        return startDateTypeCode;
    }
    /**
     * @param startDateTypeCode the startDateTypeCode to set
     */
    public void setStartDateTypeCode(Cd startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }
    /**
     * @return the primaryCompletionDate
     */
    public Ts getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }
    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(Ts primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }
    /**
     * @return the primaryCompletionDateTypeCode
     */
    public Cd getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }
    /**
     * @param primaryCompletionDateTypeCode the primaryCompletionDateTypeCode to set
     */
    public void setPrimaryCompletionDateTypeCode(Cd primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }
    /**
     * @return the studyProtocolType
     */
    public St getStudyProtocolType() {
        return studyProtocolType;
    }
    /**
     * @param studyProtocolType the studyProtocolType to set
     */
    public void setStudyProtocolType(St studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }
    /**
     * @return the dataMonitoringCommitteeAppointedIndicator
     */
    public Bl getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }
    /**
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator to set
     */
    public void setDataMonitoringCommitteeAppointedIndicator(
            Bl dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }
    /**
     * @return the fdaRegulatedIndicator
     */
    public Bl getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }
    /**
     * @param fdaRegulatedIndicator the fdaRegulatedIndicator to set
     */
    public void setFdaRegulatedIndicator(Bl fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }
    /**
     * @return the section801Indicator
     */
    public Bl getSection801Indicator() {
        return section801Indicator;
    }
    /**
     * @param section801Indicator the section801Indicator to set
     */
    public void setSection801Indicator(Bl section801Indicator) {
        this.section801Indicator = section801Indicator;
    }
    /**
     * @return the delayedpostingIndicator
     */
    public Bl getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }
    /**
     * @param delayedpostingIndicator the delayedpostingIndicator to set
     */
    public void setDelayedpostingIndicator(Bl delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
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
    /**
     * @return the nciDesignatedCancerCenterIndicator
     */
    public Bl getNciDesignatedCancerCenterIndicator() {
        return nciDesignatedCancerCenterIndicator;
    }
    /**
     * @param nciDesignatedCancerCenterIndicator the nciDesignatedCancerCenterIndicator to set
     */
    public void setNciDesignatedCancerCenterIndicator(
            Bl nciDesignatedCancerCenterIndicator) {
        this.nciDesignatedCancerCenterIndicator = nciDesignatedCancerCenterIndicator;
    }
    /**
     * @return the proprietaryTrialIndicator
     */
    public Bl getProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }
    /**
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator to set
     */
    public void setProprietaryTrialIndicator(Bl proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }
    /**
     * @return the oversightAuthorityCountryId
     */
    public Ii getOversightAuthorityCountryId() {
        return oversightAuthorityCountryId;
    }
    /**
     * @param oversightAuthorityCountryId the oversightAuthorityCountryId to set
     */
    public void setOversightAuthorityCountryId(Ii oversightAuthorityCountryId) {
        this.oversightAuthorityCountryId = oversightAuthorityCountryId;
    }
    /**
     * @return the oversightAuthorityOrgId
     */
    public Ii getOversightAuthorityOrgId() {
        return oversightAuthorityOrgId;
    }
    /**
     * @param oversightAuthorityOrgId the oversightAuthorityOrgId to set
     */
    public void setOversightAuthorityOrgId(Ii oversightAuthorityOrgId) {
        this.oversightAuthorityOrgId = oversightAuthorityOrgId;
    }
    
}
