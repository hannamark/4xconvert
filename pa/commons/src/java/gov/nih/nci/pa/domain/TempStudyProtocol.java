/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author Vrushali
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuppressWarnings({ "PMD.TooManyFields", "PMD.AvoidDuplicateLiterals",
        "PMD.ExcessiveClassLength" })
@Table(name = "TEMP_STUDY_PROTOCOL")        
public class TempStudyProtocol extends AbstractEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String localProtocolIdentifier;
    private String nctIdentifier;
    private String ctepIdentifier;
    private String dcpIdentifier;
    private String officialTitle;
    private PhaseCode phaseCode;
    private String phaseOtherText;
    private String trialType;
    private PrimaryPurposeCode primaryPurposeCode;
    private String primaryPurposeOtherText;
    private String leadOrganizationIdentifier;
    private String piIdentifier;
    private String sponsorIdentifier;
    private String responsiblePartyType;
    private String responsibleIdentifier;
    private String contactPhone;
    private String contactEmail;

    private String summaryFourOrgIdentifier;
    private SummaryFourFundingCategoryCode summaryFourFundingCategoryCode;
    private String programCodeText;
    
    private StudyStatusCode  trialStatusCode;
    private Timestamp trialStatusDate;
    private String reason;
    private Timestamp startDate;
    private ActualAnticipatedTypeCode startDateTypeCode;
    private Timestamp primaryCompletionDate;
    private ActualAnticipatedTypeCode primaryCompletionDateTypeCode;

    private Boolean dataMonitoringCommitteeAppointedIndicator;
    private Boolean delayedpostingIndicator;
    private Boolean fdaRegulatedIndicator;
    private Boolean section801Indicator;
    private Boolean proprietaryTrialIndicator;
    private Boolean nciDesignatedCancerCenterIndicator;
    private String oversightAuthorityCountryId;
    private String oversightAuthorityOrgId;

    /**
     * @return the localProtocolIdentifier
     */
    @Column(name = "LOCAL_PROTOCOL_INDENTIFIER")
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
     * @return the nctIdentifier
     */
    @Column (name = "NCT_IDENTIFIER")
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
     * @return the ctepIdentifier
     */
    @Column (name = "CTEP_IDENTIFIER")
    public String getCtepIdentifier() {
        return ctepIdentifier;
    }
    /**
     * @param ctepIdentifier the ctepIdentifier to set
     */
    public void setCtepIdentifier(String ctepIdentifier) {
        this.ctepIdentifier = ctepIdentifier;
    }
    /**
     * @return the dcpIdentifier
     */
    @Column (name = "DCP_IDENTIFIER")
    public String getDcpIdentifier() {
        return dcpIdentifier;
    }
    /**
     * @param dcpIdentifier the dcpIdentifier to set
     */
    public void setDcpIdentifier(String dcpIdentifier) {
        this.dcpIdentifier = dcpIdentifier;
    }
    /**
     * @return the officialTitle
     */
    @Column(name = "OFFICIAL_TITLE")
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
     * @return the phaseCode
     */
    @Column(name = "PHASE_CODE")
    @Enumerated(EnumType.STRING)
    public PhaseCode getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(PhaseCode phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * @return the phaseOtherText
     */
    @Column(name = "PHASE_OTHER_TEXT")
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
     * @return the trialType
     */
    @Column (name = "STUDY_PROTOCOL_TYPE")
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
     * @return the primaryPurposeCode
     */
    @Column (name = "PRIMARY_PURPOSE_CODE")
    @Enumerated(EnumType.STRING)
    public PrimaryPurposeCode getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(PrimaryPurposeCode primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return the primaryPurposeOtherText
     */
    @Column (name = "PRIMARY_PURPOSE_OTHER_TEXT")
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
     * @return the leadOrganizationIdentifier
     */
    @Column (name = "LEAD_ORGANIZATION_IDENTIFIER")
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
     * @return the piIdentifier
     */
    @Column (name = "PI_IDENTIFIER")
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
     * @return the sponsorIdentifier
     */
    @Column (name = "SPONSOR_IDENTIFIER")
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
    @Column (name = "RESPONSIBLE_PARTY_TYPE")
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
     * @return the responsiblePersonIdentifier
     */
    @Column (name = "RESPONSIBLE_IDENTIFIER")
    public String getResponsibleIdentifier() {
        return responsibleIdentifier;
    }
    /**
     * @param responsibleIdentifier the responsibleIdentifier to set
     */
    public void setResponsibleIdentifier(String responsibleIdentifier) {
        this.responsibleIdentifier = responsibleIdentifier;
    }
    /**
     * @return the contactPhone
     */
    @Column (name = "RESPONSIBLE_CONTACT_PHONE")
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
    @Column (name = "RESPONSIBLE_CONTACT_EMAIL")
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
     * @return the summaryFourOrgIdentifier
     */
    @Column (name = "SUMMARY_FOUR_ORG_IDENTIFIER")
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
     * @return the summaryFourFundingCategoryCode
     */
    @Column (name = "SUMMARY_FOUR_FUNDING_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public SummaryFourFundingCategoryCode getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * @param summaryFourFundingCategoryCode the summaryFourFundingCategoryCode to set
     */
    public void setSummaryFourFundingCategoryCode(
            SummaryFourFundingCategoryCode summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * @return the programCodeText
     */
    @Column (name = "PROGRAM_CODE_TEXT")
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
     * @return the statusCode
     */
    @Column (name = "TRIAL_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public StudyStatusCode getTrialStatusCode() {
        return trialStatusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setTrialStatusCode(StudyStatusCode statusCode) {
        this.trialStatusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    @Column (name = "TRIAL_STATUS_DATE")
    public Timestamp getTrialStatusDate() {
        return trialStatusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setTrialStatusDate(Timestamp statusDate) {
        this.trialStatusDate = statusDate;
    }
    /**
     * @return the reason
     */
    @Column (name = "STATUS_REASON")
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
     * @return the startDate
     */
    @Column (name = "START_DATE")
    public Timestamp getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the startDateTypeCode
     */
    @Column (name = "START_DATE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ActualAnticipatedTypeCode getStartDateTypeCode() {
        return startDateTypeCode;
    }
    /**
     * @param startDateTypeCode the startDateTypeCode to set
     */
    public void setStartDateTypeCode(ActualAnticipatedTypeCode startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }
    /**
     * @return the primaryCompletionDate
     */
    @Column (name = "PRI_COMPL_DATE")
    public Timestamp getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }
    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(Timestamp primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }
    /**
     * @return the primaryCompletionDateTypeCode
     */
    @Column (name = "PRI_COMPL_DATE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ActualAnticipatedTypeCode getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }
    /**
     * @param primaryCompletionDateTypeCode the primaryCompletionDateTypeCode to set
     */
    public void setPrimaryCompletionDateTypeCode(
            ActualAnticipatedTypeCode primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }
    /**
     * @return the dataMonitoringCommitteeAppointedIndicator
     */
    @Column(name = "DATA_MONTY_COMTY_APPTN_INDICATOR")
    public Boolean getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }
    /**
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator to set
     */
    public void setDataMonitoringCommitteeAppointedIndicator(
            Boolean dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }
    /**
     * @return the delayedpostingIndicator
     */
    @Column(name = "DELAYED_POSTING_INDICATOR")
    public Boolean getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }
    /**
     * @param delayedpostingIndicator the delayedpostingIndicator to set
     */
    public void setDelayedpostingIndicator(Boolean delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
    }
    /**
     * @return the fdaRegulatedIndicator
     */
    @Column(name = "FDA_REGULATED_INDICATOR")
    public Boolean getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }
    /**
     * @param fdaRegulatedIndicator the fdaRegulatedIndicator to set
     */
    public void setFdaRegulatedIndicator(Boolean fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }
    /**
     * @return the section801Indicator
     */
    @Column (name = "SECTION801_INDICATOR")
    public Boolean getSection801Indicator() {
        return section801Indicator;
    }
    /**
     * @param section801Indicator the section801Indicator to set
     */
    public void setSection801Indicator(Boolean section801Indicator) {
        this.section801Indicator = section801Indicator;
    }
    /**
     * @return the proprietaryTrialIndicator
     */
    @Column (name = "PROPRIETARY_TRIAL_INDICATOR")
    public Boolean getProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }
    /**
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator to set
     */
    public void setProprietaryTrialIndicator(Boolean proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }
    /**
     * @return the nciDesignatedCancerCenterIndicator
     */
    @Column (name = "NCI_DESIGNATED_CANCER_CENTER_INDICATOR")
    public Boolean getNciDesignatedCancerCenterIndicator() {
        return nciDesignatedCancerCenterIndicator;
    }
    /**
     * @param nciDesignatedCancerCenterIndicator the nciDesignatedCancerCenterIndicator to set
     */
    public void setNciDesignatedCancerCenterIndicator(
            boolean nciDesignatedCancerCenterIndicator) {
        this.nciDesignatedCancerCenterIndicator = nciDesignatedCancerCenterIndicator;
    }
    /**
     * @param oversightAuthorityCountryId the oversightAuthorityCountryId to set
     */
    public void setOversightAuthorityCountryId(
            String oversightAuthorityCountryId) {
        this.oversightAuthorityCountryId = oversightAuthorityCountryId;
    }
    /**
     * @return the oversightAuthorityCountryId
     */
    @Column (name = "OVERSIGHT_AUTHORITY_COUNTRY_ID")
    public String getOversightAuthorityCountryId() {
        return oversightAuthorityCountryId;
    }
    /**
     * @param oversightAuthorityOrgId the oversightAuthorityOrgId to set
     */
    public void setOversightAuthorityOrgId(String oversightAuthorityOrgId) {
        this.oversightAuthorityOrgId = oversightAuthorityOrgId;
    }
    /**
     * @return the oversightAuthorityOrgId
     */
    @Column (name = "OVERSIGHT_AUTHORITY_ORG_ID")
    public String getOversightAuthorityOrgId() {
        return oversightAuthorityOrgId;
    }


}
