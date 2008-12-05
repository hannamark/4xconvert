package gov.nih.nci.pa.dto;


/**
 * this class holds the general information of protocol.
 * @author Naveen Amiruddin
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD.TooManyFields")
public class GeneralTrialDesignWebDTO {

    private String acronym;
    private String allocationCode;
    private String accrualReportingMethodCode;
    private String assignedIdentifier; // used to store nci-accession number
    private String officialTitle;
    private String phaseCode;
    private String phaseOtherText;
    private String primaryPurposeCode;
    private String primaryPurposeOtherText;
    private String publicTitle;
    private String publicDescription;
    private String scientificDescription;
    private String keywordText;
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
    private String responsibleParty;
    private String responsiblePersonName;
    private String responsiblePersonIdentifier;
    private String contactPhone;
    private String contactEmail;
    
    /**
     * 
     * @return acronym acronym
     */
    public String getAcronym() {
        return acronym;
    }
    /**
     * 
     * @param acronym acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    /**
     * 
     * @return allocationCode
     */
    public String getAllocationCode() {
        return allocationCode;
    }
    /**
     * 
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(String allocationCode) {
        this.allocationCode = allocationCode;
    }
    /**
     * 
     * @return accrualReportingMethodCode
     */
    public String getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    /**
     * 
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(String accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }
    /**
     * 
     * @return assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * 
     * @param assignedIdentifier assignedIdentifier
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    
    /**
     * 
     * @return officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * 
     * @return phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * 
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * 
     * @return phaseOtherText
     */
    public String getPhaseOtherText() {
        return phaseOtherText;
    }
    /**
     * 
     * @param phaseOtherText phaseOtherText
     */
    public void setPhaseOtherText(String phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
    }
    /**
     * 
     * @return primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * 
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * 
     * @return primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * 
     * @param primaryPurposeOtherText primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return publicTitle
     */
    public String getPublicTitle() {
        return publicTitle;
    }
    /**
     * @param publicTitle publicTitle
     */
    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }
    /**
     * @return publicDescription
     */
    public String getPublicDescription() {
        return publicDescription;
    }
    /**
     * @param publicDescription publicDescription
     */
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }
    /**
     * @return scientificDescription
     */
    public String getScientificDescription() {
        return scientificDescription;
    }
    /**
     * @param scientificDescription scientificDescription
     */
    public void setScientificDescription(String scientificDescription) {
        this.scientificDescription = scientificDescription;
    }
    /**
     * @return keywordText
     */
    public String getKeywordText() {
        return keywordText;
    }
    /**
     * @param keywordText keywordText
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }
    /**
     * 
     * @return localProtocolIdentifier
     */
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }
    /**
     * 
     * @param localProtocolIdentifier localProtocolIdentifier
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }
    /**
     * 
     * @return leadOrganizationIdentifier
     */
    public String getLeadOrganizationIdentifier() {
        return leadOrganizationIdentifier;
    }
    /**
     * 
     * @param leadOrganizationIdentifier leadOrganizationIdentifier
     */
    public void setLeadOrganizationIdentifier(String leadOrganizationIdentifier) {
        this.leadOrganizationIdentifier = leadOrganizationIdentifier;
    }
    /**
     * 
     * @return leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }
    /**
     * 
     * @param leadOrganizationName leadOrganizationName
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }
    /**
     * 
     * @return piIdentifier
     */
    public String getPiIdentifier() {
        return piIdentifier;
    }
    /**
     * 
     * @param piIdentifier piIdentifier
     */
    public void setPiIdentifier(String piIdentifier) {
        this.piIdentifier = piIdentifier;
    }
    /**
     * 
     * @return piName
     */
    public String getPiName() {
        return piName;
    }
    /**
     * 
     * @param piName piName
     */
    public void setPiName(String piName) {
        this.piName = piName;
    }
    /**
     * 
     * @return summaryFourOrgIdentifier
     */
    public String getSummaryFourOrgIdentifier() {
        return summaryFourOrgIdentifier;
    }
    /**
     * 
     * @param summaryFourOrgIdentifier summaryFourOrgIdentifier
     */
    public void setSummaryFourOrgIdentifier(String summaryFourOrgIdentifier) {
        this.summaryFourOrgIdentifier = summaryFourOrgIdentifier;
    }
    /**
     * 
     * @return summaryFourOrgName
     */
    public String getSummaryFourOrgName() {
        return summaryFourOrgName;
    }
    /**
     * 
     * @param summaryFourOrgName summaryFourOrgName
     */
    public void setSummaryFourOrgName(String summaryFourOrgName) {
        this.summaryFourOrgName = summaryFourOrgName;
    }
    /**
     * 
     * @return summaryFourFundingCategoryCode
     */
    public String getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * 
     * @param summaryFourFundingCategoryCode summaryFourFundingCategoryCode
     */
    public void setSummaryFourFundingCategoryCode(
            String summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * 
     * @return sponsorName
     */
    public String getSponsorName() {
        return sponsorName;
    }
    /**
     * 
     * @param sponsorName sponsorName
     */
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
    /**
     * 
     * @return sponsorIdentifier
     */
    public String getSponsorIdentifier() {
        return sponsorIdentifier;
    }
    /**
     * 
     * @param sponsorIdentifier sponsorIdentifier
     */
    public void setSponsorIdentifier(String sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }
    /**
     * 
     * @return responsibleParty
     */
    public String getResponsibleParty() {
        return responsibleParty;
    }
    /**
     * 
     * @param responsibleParty responsibleParty
     */
    public void setResponsibleParty(String responsibleParty) {
        this.responsibleParty = responsibleParty;
    }
    /**
     * 
     * @return responsiblePersonName
     */
    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }
    /**
     * 
     * @param responsiblePersonName responsibleName
     */
    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }
    /**
     * 
     * @return  responsiblePersonIdentifier
     */
    public String getResponsiblePersonIdentifier() {
        return responsiblePersonIdentifier;
    }
    /**
     * 
     * @param responsiblePersonIdentifier responsiblePersonIdentifier
     */
    public void setResponsiblePersonIdentifier(String responsiblePersonIdentifier) {
        this.responsiblePersonIdentifier = responsiblePersonIdentifier;
    }
    /**
     * 
     * @return contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    /**
     * 
     * @param contactPhone contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    /**
     * 
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }
    /**
     * 
     * @param contactEmail contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
}
