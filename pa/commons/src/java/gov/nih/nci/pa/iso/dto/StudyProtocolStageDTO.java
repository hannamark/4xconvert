/**
 *
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class StudyProtocolStageDTO extends BaseDTO {
    private static final long serialVersionUID = 7235772554482606139L;
    private St localProtocolIdentifier;
    private St nctIdentifier;
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
    private Bl ctgovXmlRequiredIndicator;
    private Ii submitterOrganizationIdentifier;
    private Ii siteProtocolIdentifier;
    private Ii sitePiIdentifier;
    private Int siteTargetAccrual;
    private Ii siteSummaryFourOrgIdentifier;
    private Cd siteSummaryFourFundingTypeCode;
    private St siteProgramCodeText;
    private Cd siteRecruitmentStatus;
    private Ts siteRecruitmentStatusDate;
    private Ts opendedForAccrualDate;
    private Ts closedForAccrualDate;
    private Bl piInitiatedIndicator;
    private Bl siteNciDesignatedCancerCenterIndicator;
    private List<Ii> secondaryIdentifierList = new ArrayList<Ii>();

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
    /**
     * @return the ctgovXmlRequiredIndicator
     */
    public Bl getCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }
    /**
     * @param ctgovXmlRequiredIndicator the ctgovXmlRequiredIndicator to set
     */
    public void setCtgovXmlRequiredIndicator(Bl ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }
    /**
     * @return the submitterOrganizationIdentifier
     */
    public Ii getSubmitterOrganizationIdentifier() {
        return submitterOrganizationIdentifier;
    }
    /**
     * @param submitterOrganizationIdentifier the submitterOrganizationIdentifier to set
     */
    public void setSubmitterOrganizationIdentifier(
            Ii submitterOrganizationIdentifier) {
        this.submitterOrganizationIdentifier = submitterOrganizationIdentifier;
    }
    /**
     * @return the siteProtocolIdentifier
     */
    public Ii getSiteProtocolIdentifier() {
        return siteProtocolIdentifier;
    }
    /**
     * @param siteProtocolIdentifier the siteProtocolIdentifier to set
     */
    public void setSiteProtocolIdentifier(Ii siteProtocolIdentifier) {
        this.siteProtocolIdentifier = siteProtocolIdentifier;
    }
    /**
     * @return the sitePiIdentifier
     */
    public Ii getSitePiIdentifier() {
        return sitePiIdentifier;
    }
    /**
     * @param sitePiIdentifier the sitePiIdentifier to set
     */
    public void setSitePiIdentifier(Ii sitePiIdentifier) {
        this.sitePiIdentifier = sitePiIdentifier;
    }
    /**
     * @return the siteTargetAccrual
     */
    public Int getSiteTargetAccrual() {
        return siteTargetAccrual;
    }
    /**
     * @param siteTargetAccrual the siteTargetAccrual to set
     */
    public void setSiteTargetAccrual(Int siteTargetAccrual) {
        this.siteTargetAccrual = siteTargetAccrual;
    }
    /**
     * @return the siteSummaryFourOrgIdentifier
     */
    public Ii getSiteSummaryFourOrgIdentifier() {
        return siteSummaryFourOrgIdentifier;
    }
    /**
     * @param siteSummaryFourOrgIdentifier the siteSummaryFourOrgIdentifier to set
     */
    public void setSiteSummaryFourOrgIdentifier(Ii siteSummaryFourOrgIdentifier) {
        this.siteSummaryFourOrgIdentifier = siteSummaryFourOrgIdentifier;
    }
    /**
     * @return the siteSummaryFourFundingTypeCode
     */
    public Cd getSiteSummaryFourFundingTypeCode() {
        return siteSummaryFourFundingTypeCode;
    }
    /**
     * @param siteSummaryFourFundingTypeCode the siteSummaryFourFundingTypeCode to set
     */
    public void setSiteSummaryFourFundingTypeCode(Cd siteSummaryFourFundingTypeCode) {
        this.siteSummaryFourFundingTypeCode = siteSummaryFourFundingTypeCode;
    }
    /**
     * @return the siteProgramCodeText
     */
    public St getSiteProgramCodeText() {
        return siteProgramCodeText;
    }
    /**
     * @param siteProgramCodeText the siteProgramCodeText to set
     */
    public void setSiteProgramCodeText(St siteProgramCodeText) {
        this.siteProgramCodeText = siteProgramCodeText;
    }
    /**
     * @return the siteRecruitmentStatus
     */
    public Cd getSiteRecruitmentStatus() {
        return siteRecruitmentStatus;
    }
    /**
     * @param siteRecruitmentStatus the siteRecruitmentStatus to set
     */
    public void setSiteRecruitmentStatus(Cd siteRecruitmentStatus) {
        this.siteRecruitmentStatus = siteRecruitmentStatus;
    }
    /**
     * @return the siteRecruitmentStatusDate
     */
    public Ts getSiteRecruitmentStatusDate() {
        return siteRecruitmentStatusDate;
    }
    /**
     * @param siteRecruitmentStatusDate the siteRecruitmentStatusDate to set
     */
    public void setSiteRecruitmentStatusDate(Ts siteRecruitmentStatusDate) {
        this.siteRecruitmentStatusDate = siteRecruitmentStatusDate;
    }
    /**
     * @return the opendedForAccrualDate
     */
    public Ts getOpendedForAccrualDate() {
        return opendedForAccrualDate;
    }
    /**
     * @param opendedForAccrualDate the opendedForAccrualDate to set
     */
    public void setOpendedForAccrualDate(Ts opendedForAccrualDate) {
        this.opendedForAccrualDate = opendedForAccrualDate;
    }
    /**
     * @return the closedForAccrualDate
     */
    public Ts getClosedForAccrualDate() {
        return closedForAccrualDate;
    }
    /**
     * @param closedForAccrualDate the closedForAccrualDate to set
     */
    public void setClosedForAccrualDate(Ts closedForAccrualDate) {
        this.closedForAccrualDate = closedForAccrualDate;
    }
    /**
     * @return the piInitiatedIndicator
     */
    public Bl getPiInitiatedIndicator() {
        return piInitiatedIndicator;
    }
    /**
     * @param piInitiatedIndicator the piInitiatedIndicator to set
     */
    public void setPiInitiatedIndicator(Bl piInitiatedIndicator) {
        this.piInitiatedIndicator = piInitiatedIndicator;
    }
    /**
     * @return the siteNciDesignatedCancerCenterIndicator
     */
    public Bl getSiteNciDesignatedCancerCenterIndicator() {
        return siteNciDesignatedCancerCenterIndicator;
    }
    /**
     * @param siteNciDesignatedCancerCenterIndicator the siteNciDesignatedCancerCenterIndicator to set
     */
    public void setSiteNciDesignatedCancerCenterIndicator(
            Bl siteNciDesignatedCancerCenterIndicator) {
        this.siteNciDesignatedCancerCenterIndicator = siteNciDesignatedCancerCenterIndicator;
    }

    /**
     * @return the secondaryIdentifierList
     */
    public List<Ii> getSecondaryIdentifierList() {
        return secondaryIdentifierList;
    }

    /**
     * @param secondaryIdentifierList the secondaryIdentifierList to set
     */
    public void setSecondaryIdentifierList(List<Ii> secondaryIdentifierList) {
        this.secondaryIdentifierList = secondaryIdentifierList;
    }

}
