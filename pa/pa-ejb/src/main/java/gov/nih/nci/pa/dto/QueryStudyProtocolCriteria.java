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
public class QueryStudyProtocolCriteria implements Serializable {    
    static final long serialVersionUID = 252345L;
    
    private Long studyProtocolId;
    private String nciIdentifier;
    private String officialTitle;
    private Long organizationId;
    private String leadOrganizationTrialIdentifier;
    private String phaseCode;
    private String studyStatusCode;
    private String documentWorkflowStatusCode;
    
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
     * @return organizationId
     */
    public Long getOrganizationId() {
        return organizationId;
    }
    /**
     * 
     * @param organizationId organizationId
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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
}