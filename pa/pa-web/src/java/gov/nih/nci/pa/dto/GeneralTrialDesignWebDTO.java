package gov.nih.nci.pa.dto;


/**
 * this class holds the general information of protocol.
 * @author Naveen Amiruddin
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class GeneralTrialDesignWebDTO {

    private String acronym;
    private String allocationCode;
    private String accrualReportingMethodCode;
    private String assignedIdentifier; // used to store nci-accession number
    private String officialTitle;
    private String phaseCode;
    private String primaryPurposeCode;
    private String publicTitle;
    private String publicDescription;
    private String scientificDescription;
    private String keywordText;
    
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

}
