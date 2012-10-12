/**
 *
 */
package gov.nih.nci.registry.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.NotEmpty;

/**
 * @author Vrushali
 *
 */
public class BaseTrialDTO { // NOPMD
    private String assignedIdentifier; // used to store nci-accession number
    private String officialTitle;
    private String phaseCode;
    private String phaseAdditionalQualifier;
    private String primaryPurposeCode;
    private String primaryPurposeAdditionalQualifierCode;
    private String primaryPurposeOtherText;
    private Long secondaryPurposeId;
    private String secondaryPurposeName;
    private String leadOrgTrialIdentifier;
    private String leadOrganizationIdentifier;
    private String leadOrganizationName;

    private String summaryFourOrgIdentifier;
    private String summaryFourOrgName;
    private String summaryFourFundingCategoryCode;
    private String nctIdentifier;
    private String trialType;
    private String identifier;
    private String submissionNumber;
    private String propritaryTrialIndicator;
    private String studyProtocolId;
    private List<TrialFundingWebDTO> fundingDtos;
    private List<TrialDocumentWebDTO> docDtos;
    private List <TrialIndIdeDTO> indIdeDtos;
    private String studySubtypeCode;
    private String studyModelCode;
    private String studyModelOtherText;
    private String timePerspectiveCode;
    private String timePerspectiveOtherText;
    
    private static final int TRIAL_TITLE_MAX_LENGTH = 4000;

    /**
     * default Cons.
     */
    public BaseTrialDTO() {
        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        docDtos = new ArrayList<TrialDocumentWebDTO>();
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
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
     * @return the phaseAdditonalQualifier
     */
    public String getPhaseAdditionalQualifier() {
        return phaseAdditionalQualifier;
    }
    /**
     * @param phaseAdditionalQualifier the phaseAdditonalQualifier to set
     */
    public void setPhaseAdditionalQualifier(String phaseAdditionalQualifier) {
        this.phaseAdditionalQualifier = phaseAdditionalQualifier;
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
     * @return the primaryPurposeAdditonalQualifierCode
     */
    public String getPrimaryPurposeAdditionalQualifierCode() {
        return primaryPurposeAdditionalQualifierCode;
    }
    /**
     * @param primaryPurposeAdditionalQualifierCode the primaryPurposeAdditionalQualifierCode to set
     */
    public void setPrimaryPurposeAdditionalQualifierCode(String primaryPurposeAdditionalQualifierCode) {
         this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
    }
    /**
     * @return the leadOrgTrialIdentifier
     */
    @NotEmpty(message = "error.submit.localProtocolIdentifier")
    public String getLeadOrgTrialIdentifier() {
        return leadOrgTrialIdentifier;
    }
    /**
     * @param leadOrgTrialIdentifier the leadOrgTrialIdentifier to set
     */
    public void setLeadOrgTrialIdentifier(String leadOrgTrialIdentifier) {
        this.leadOrgTrialIdentifier = leadOrgTrialIdentifier;
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
     * @return the summaryFourOrgIdentifier
     */
    @NotEmpty (message = "error.submit.sumFourOrganization")
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
    @NotEmpty (message = "error.submit.sumFourCategoryCode")
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
     * @return the submissionNumber
     */
    public String getSubmissionNumber() {
        return submissionNumber;
    }
    /**
     * @param submissionNumber the submissionNumber to set
     */
    public void setSubmissionNumber(String submissionNumber) {
        this.submissionNumber = submissionNumber;
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
     * @param propritaryTrialIndicator the propritaryTrialIndicator to set
     */
    public void setPropritaryTrialIndicator(String propritaryTrialIndicator) {
        this.propritaryTrialIndicator = propritaryTrialIndicator;
    }
    /**
     * @return the propritaryTrialIndicator
     */
    public String getPropritaryTrialIndicator() {
        return propritaryTrialIndicator;
    }
    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    /**
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }
    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
         this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return the primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @return the secondaryPurposeId
     */
    public Long getSecondaryPurposeId() {
        return secondaryPurposeId;
    }
    /**
     * @param secondaryPurposeId the secondaryPurposeId to set
     */
    public void setSecondaryPurposeId(Long secondaryPurposeId) {
        this.secondaryPurposeId = secondaryPurposeId;
    }
    /**
     * @return the secondaryPurposeName
     */
    public String getSecondaryPurposeName() {
        return secondaryPurposeName;
    }
    /**
     * @param secondaryPurposeName the secondaryPurposeName to set
     */
    public void setSecondaryPurposeName(String secondaryPurposeName) {
        this.secondaryPurposeName = secondaryPurposeName;
    }
    /**
     * @return the studySubtypeCode
     */
    public String getStudySubtypeCode() {
        return studySubtypeCode;
    }
    /**
     * @param studySubtypeCode the studySubtypeCode to set
     */
    public void setStudySubtypeCode(String studySubtypeCode) {
        this.studySubtypeCode = studySubtypeCode;
    }
    /**
     * @return the studyModelCode
     */
    public String getStudyModelCode() {
        return studyModelCode;
    }
    /**
     * @param studyModelCode the studyModelCode to set
     */
    public void setStudyModelCode(String studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     * @return the studyModelOtherText
     */
    public String getStudyModelOtherText() {
        return studyModelOtherText;
    }
    /**
     * @param studyModelOtherText the studyModelOtherText to set
     */
    public void setStudyModelOtherText(String studyModelOtherText) {
        this.studyModelOtherText = studyModelOtherText;
    }
    /**
     * @return the timePerspectiveCode
     */
    public String getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     * @param timePerspectiveCode the timePerspectiveCode to set
     */
    public void setTimePerspectiveCode(String timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }
    /**
     * @return the timePerspectiveOtherText
     */
    public String getTimePerspectiveOtherText() {
        return timePerspectiveOtherText;
    }
    /**
     * @param timePerspectiveOtherText the timePerspectiveOtherText to set
     */
    public void setTimePerspectiveOtherText(String timePerspectiveOtherText) {
        this.timePerspectiveOtherText = timePerspectiveOtherText;
    }

}
