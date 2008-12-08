package gov.nih.nci.pa.dto;

import java.io.Serializable;


/**
 * Class used to hold criteria used in searching study protocols.
 * 
 * <pre>
 * Attr.                         Corresponding bo attribute
 * =====                         ==========================
 * studyProtocolId               domain.StudyProtocol.id
 * nciIdentifier                 domain.DocumentIdentification.NCI
 * longTitleText                 domain.Document.officialTitle
 * leadOrganizationId            domain.Organization.id
 * phaseCode                     domain.StudyProtocol.phaseCode
 * studyStatusCode               domain.StudyOverallStatus
 * documentWrokflowStatus        domain.DocumentWorkflowStatus
 * </pre>
 * 
 * @author Hugh Reinhart
 * @author Naveen Amiruddin
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class StudyProtocolQueryCriteria implements Serializable {    
    static final long serialVersionUID = 252345L;
    
    private Long studyProtocolId;
    private String nciIdentifier;
    private String officialTitle;
    private Long leadOrganizationId;
    private String leadOrganizationTrialIdentifier;
    private String phaseCode;
    private String studyStatusCode;
    private String documentWorkflowStatusCode;
    private Long principalInvestigatorId;
    private String primaryPurposeCode;
    private String identifierType;
    private String organizationType;
    private String userLastCreated;
    private Boolean excludeRejectProtocol;
    // for Registry trial search
    private Boolean myTrialsOnly; 
    
    
    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }
    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    /**
     * 
     * @return nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     * 
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
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
     * @return leadOrganizationId
     */
    public Long getLeadOrganizationId() {
        return leadOrganizationId;
    }
    /**
     * 
     * @param leadOrganizationId leadOrganizationId
     */
    public void setLeadOrganizationId(Long leadOrganizationId) {
        this.leadOrganizationId = leadOrganizationId;
    }
    /**
     * 
     * @return leadOrganizationTrialIdentifier
     */
    public String getLeadOrganizationTrialIdentifier() {
        return leadOrganizationTrialIdentifier;
    }
    /**
     * 
     * @param leadOrganizationTrialIdentifier leadOrganizationTrialIdentifier
     */
    public void setLeadOrganizationTrialIdentifier(
            String leadOrganizationTrialIdentifier) {
        this.leadOrganizationTrialIdentifier = leadOrganizationTrialIdentifier;
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
     * @return studyStatusCode
     */
    public String getStudyStatusCode() {
        return studyStatusCode;
    }
    /**
     * 
     * @param studyStatusCode studyStatusCode
     */
    public void setStudyStatusCode(String studyStatusCode) {
        this.studyStatusCode = studyStatusCode;
    }
    /**
     * 
     * @return documentWorkflowStatusCode
     */
    public String getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }
    /**
     * 
     * @param documentWorkflowStatusCode documentWorkflowStatusCode
     */
    public void setDocumentWorkflowStatusCode(
            String documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
    }
    /**
     * 
     * @return principalInvestigatorId
     */
    public Long getPrincipalInvestigatorId() {
        return principalInvestigatorId;
    }
    /**
     * 
     * @param principalInvestigatorId principalInvestigatorId
     */
    public void setPrincipalInvestigatorId(Long principalInvestigatorId) {
        this.principalInvestigatorId = principalInvestigatorId;
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
     * @return the identifierType
     */
    public String getIdentifierType() {
        return identifierType;
    }
    /**
     * @param identifierType the identifierType to set
     */
    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
    /**
     * @return the organizationType
     */
    public String getOrganizationType() {
        return organizationType;
    }
    /**
     * @param organizationType the organizationType to set
     */
    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
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

    /**
     * 
     * @return excludeRejectProtocol excludeRejectProtocol
     */
    public Boolean getExcludeRejectProtocol() {
        return excludeRejectProtocol;
    }
    /**
     * 
     * @param excludeRejectProtocol excludeRejectProtocol
     */
    public void setExcludeRejectProtocol(Boolean excludeRejectProtocol) {
        this.excludeRejectProtocol = excludeRejectProtocol;
    }
    /**
     * @return the myTrialsOnly
     */
    public Boolean getMyTrialsOnly() {
        return myTrialsOnly;
    }
    /**
     * @param myTrialsOnly the myTrialsOnly to set
     */
    public void setMyTrialsOnly(Boolean myTrialsOnly) {
        this.myTrialsOnly = myTrialsOnly;
    }
    
    
    
}