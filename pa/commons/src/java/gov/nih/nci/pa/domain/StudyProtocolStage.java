/**
 *
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.util.NotEmptyIiExtension;
import gov.nih.nci.pa.util.NotEmptyIiRoot;
import gov.nih.nci.pa.util.ValidIi;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * A draft StudyProtocol.
 * @author Vrushali
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "STUDY_PROTOCOL_STAGE")
public class StudyProtocolStage extends AbstractStudyProtocol {
    private static final long serialVersionUID = 1L;
    private String localProtocolIdentifier;
    private String nctIdentifier;
    private String trialType;
    private String leadOrganizationIdentifier;
    private String piIdentifier;
    private String sponsorIdentifier;
    private String responsiblePartyType;
    private String responsibleIdentifier;
    private String contactPhone;
    private String contactEmail;

    private String summaryFourOrgIdentifier;
    private SummaryFourFundingCategoryCode summaryFourFundingCategoryCode;

    private StudyStatusCode  trialStatusCode;
    private Timestamp trialStatusDate;
    private String reason;

    private Boolean nciDesignatedCancerCenterIndicator;
    private String oversightAuthorityCountryId;
    private String oversightAuthorityOrgId;
    private String submitterOrganizationIdentifier;
    private String siteProtocolIdentifier;
    private String sitePiIdentifier;
    private Integer  siteTargetAccrual;
    private String siteSummaryFourOrgIdentifier;
    private SummaryFourFundingCategoryCode siteSummaryFourFundingTypeCode;
    private String siteProgramCodeText;
    private RecruitmentStatusCode siteRecruitmentStatus;
    private Timestamp siteRecruitmentStatusDate;
    private Timestamp opendedForAccrualDate;
    private Timestamp closedForAccrualDate;
    private Boolean piInitiatedIndicator;
    private Boolean siteNciDesignatedCancerCenterIndicator;

    /**
     * @return the localProtocolIdentifier
     */
    @Column(name = "LEAD_PROTOCOL_INDENTIFIER")
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
            Boolean nciDesignatedCancerCenterIndicator) {
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
    /**
     * @return the submitterOrganizationIdentifier
     */
    @Column(name = "SUBMITTER_ORGANIZATION_IDENTIFIER")
    public String getSubmitterOrganizationIdentifier() {
        return submitterOrganizationIdentifier;
    }
    /**
     * @param submitterOrganizationIdentifier the submitterOrganizationIdentifier to set
     */
    public void setSubmitterOrganizationIdentifier(
            String submitterOrganizationIdentifier) {
        this.submitterOrganizationIdentifier = submitterOrganizationIdentifier;
    }
    /**
     * @return the siteProtocolIdentifier
     */
    @Column(name = "SITE_PROTOCOL_IDENTIFIER")
    public String getSiteProtocolIdentifier() {
        return siteProtocolIdentifier;
    }
    /**
     * @param siteProtocolIdentifier the siteProtocolIdentifier to set
     */
    public void setSiteProtocolIdentifier(String siteProtocolIdentifier) {
        this.siteProtocolIdentifier = siteProtocolIdentifier;
    }
    /**
     * @return the sitePiIdentifier
     */
    @Column (name = "SITE_PRINCIPAL_INVESTOGATOR_IDENTIFIER")
    public String getSitePiIdentifier() {
        return sitePiIdentifier;
    }
    /**
     * @param sitePiIdentifier the sitePiIdentifier to set
     */
    public void setSitePiIdentifier(String sitePiIdentifier) {
        this.sitePiIdentifier = sitePiIdentifier;
    }
    /**
     * @return the siteTargetAccrual
     */
    @Column (name = "SITE_TARGET_ACCRUAL")
    public Integer getSiteTargetAccrual() {
        return siteTargetAccrual;
    }
    /**
     * @param siteTargetAccrual the siteTargetAccrual to set
     */
    public void setSiteTargetAccrual(Integer siteTargetAccrual) {
        this.siteTargetAccrual = siteTargetAccrual;
    }
    /**
     * @return the siteSummaryFourOrgIdentifier
     */
    @Column (name = "SITE_SUMMARY_FOUR_ORG_IDENTIFIER")
    public String getSiteSummaryFourOrgIdentifier() {
        return siteSummaryFourOrgIdentifier;
    }
    /**
     * @param siteSummaryFourOrgIdentifier the siteSummaryFourOrgIdentifier to set
     */
    public void setSiteSummaryFourOrgIdentifier(String siteSummaryFourOrgIdentifier) {
        this.siteSummaryFourOrgIdentifier = siteSummaryFourOrgIdentifier;
    }
    /**
     * @return the siteSummaryFourFundingTypeCode
     */
    @Column (name = "SITE_SUMMARY_FOUR_FUNDING_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public SummaryFourFundingCategoryCode getSiteSummaryFourFundingTypeCode() {
        return siteSummaryFourFundingTypeCode;
    }
    /**
     * @param siteSummaryFourFundingTypeCode the siteSummaryFourFundingTypeCode to set
     */
    public void setSiteSummaryFourFundingTypeCode(
            SummaryFourFundingCategoryCode siteSummaryFourFundingTypeCode) {
        this.siteSummaryFourFundingTypeCode = siteSummaryFourFundingTypeCode;
    }
    /**
     * @return the siteProgramCodeText
     */
    @Column (name = "SITE_PROGRAM_CODE_TEXT")
    public String getSiteProgramCodeText() {
        return siteProgramCodeText;
    }
    /**
     * @param siteProgramCodeText the siteProgramCodeText to set
     */
    public void setSiteProgramCodeText(String siteProgramCodeText) {
        this.siteProgramCodeText = siteProgramCodeText;
    }
    /**
     * @return the siteRecruitmentStatus
     */
    @Column (name = "SITE_RECRUITMENT_STATUS")
    @Enumerated(EnumType.STRING)
    public RecruitmentStatusCode getSiteRecruitmentStatus() {
        return siteRecruitmentStatus;
    }
    /**
     * @param siteRecruitmentStatus the siteRecruitmentStatus to set
     */
    public void setSiteRecruitmentStatus(RecruitmentStatusCode siteRecruitmentStatus) {
        this.siteRecruitmentStatus = siteRecruitmentStatus;
    }
    /**
     * @return the siteRecruitmentStatusDate
     */
    @Column (name = "SITE_RECRUITMENT_STATUS_DATE")
    public Timestamp getSiteRecruitmentStatusDate() {
        return siteRecruitmentStatusDate;
    }
    /**
     * @param siteRecruitmentStatusDate the siteRecruitmentStatusDate to set
     */
    public void setSiteRecruitmentStatusDate(Timestamp siteRecruitmentStatusDate) {
        this.siteRecruitmentStatusDate = siteRecruitmentStatusDate;
    }
    /**
     * @return the opendedForAccrualDate
     */
    @Column (name = "OPENDED_FOR_ACCRUAL_DATE")
    public Timestamp getOpendedForAccrualDate() {
        return opendedForAccrualDate;
    }
    /**
     * @param opendedForAccrualDate the opendedForAccrualDate to set
     */
    public void setOpendedForAccrualDate(Timestamp opendedForAccrualDate) {
        this.opendedForAccrualDate = opendedForAccrualDate;
    }
    /**
     * @return the closedForAccrualDate
     */
    @Column (name = "CLOSED_FOR_ACCRUAL_DATE")
    public Timestamp getClosedForAccrualDate() {
        return closedForAccrualDate;
    }
    /**
     * @param closedForAccrualDate the closedForAccrualDate to set
     */
    public void setClosedForAccrualDate(Timestamp closedForAccrualDate) {
        this.closedForAccrualDate = closedForAccrualDate;
    }
    /**
     * @return the piInitiatedIndicator
     */
    @Column (name = "PI_INITIATED_INDICATOR")
    public Boolean getPiInitiatedIndicator() {
        return piInitiatedIndicator;
    }
    /**
     * @param piInitiatedIndicator the piInitiatedIndicator to set
     */
    public void setPiInitiatedIndicator(Boolean piInitiatedIndicator) {
        this.piInitiatedIndicator = piInitiatedIndicator;
    }
    /**
     * @return the siteNciDesignatedCancerCenterIndicator
     */
    @Column (name = "SITE_NCI_DESIGNATED_CANCER_CENTER_INDICATOR")
    public Boolean getSiteNciDesignatedCancerCenterIndicator() {
        return siteNciDesignatedCancerCenterIndicator;
    }
    /**
     * @param siteNciDesignatedCancerCenterIndicator the siteNciDesignatedCancerCenterIndicator to set
     */
    public void setSiteNciDesignatedCancerCenterIndicator(
            Boolean siteNciDesignatedCancerCenterIndicator) {
        this.siteNciDesignatedCancerCenterIndicator = siteNciDesignatedCancerCenterIndicator;
    }

    /**
     * {@inheritDoc}
     */
    @CollectionOfElements(fetch = FetchType.EAGER)
    @Fetch (FetchMode.SELECT)
    @JoinTable(
            name = "STUDY_OTHERIDENTIFIERS_STAGE",
            joinColumns = @JoinColumn(name = "STUDY_PROTOCOL_ID")
    )
    @ForeignKey(name = "STUDY_OI_STAGE_FK")
    @Type(type = "gov.nih.nci.pa.iso.util.IiCompositeUserType")
    @Columns(columns = {
            @Column(name = "null_flavor"),
            @Column(name = "displayable"),
            @Column(name = "extension"),
            @Column(name = "identifier_name"),
            @Column(name = "reliability"),
            @Column(name = "root"),
            @Column(name = "scope")
    })
    @ValidIi
    @NotEmptyIiExtension
    @NotEmptyIiRoot
    @Override
    public Set<Ii> getOtherIdentifiers() {
        return super.getOtherIdentifiers();
    }
}
