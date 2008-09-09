package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import java.io.Serializable;
import java.util.Date;


/**
 * StudyProtocolQueryDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 07/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */


public class StudyProtocolQueryDTO implements Serializable {
    static final long serialVersionUID = 283476876L;
    
    private Long studyProtocolId;
    private String nciIdentifier;    
    private String officialTitle;
    private StudyStatusCode studyStatusCode;
    private Date studyStatusDate;
    private DocumentWorkflowStatusCode documentWorkflowStatusCode;
    private Date documentWorkflowStatusDate;
    private String leadOrganizationName;
    private Long leadOrganizationId;
    private String piFullName;
    private Long piId;
    private String localStudyProtocolIdentifier;
    
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
     * @return studyStatusCode
     */
    public StudyStatusCode getStudyStatusCode() {
        return studyStatusCode;
    }
    /**
     * 
     * @param studyStatusCode studyStatusCode
     */
    public void setStudyStatusCode(StudyStatusCode studyStatusCode) {
        this.studyStatusCode = studyStatusCode;
    }
    /**
     * 
     * @return studyStatusDate
     */
    public Date getStudyStatusDate() {
        return studyStatusDate;
    }
    /**
     * 
     * @param studyStatusDate studyStatusDate
     */
    public void setStudyStatusDate(Date studyStatusDate) {
        this.studyStatusDate = studyStatusDate;
    }
    /**
     * 
     * @return documentWorkflowStatusCode
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }
    /**
     * 
     * @param documentWorkflowStatusCode documentWorkflowStatusCode
     */
    public void setDocumentWorkflowStatusCode(
            DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
    }
    /**
     * 
     * @return documentWorkflowStatusDate
     */
    public Date getDocumentWorkflowStatusDate() {
        return documentWorkflowStatusDate;
    }
    /**
     * 
     * @param documentWorkflowStatusDate documentWorkflowStatusDate
     */
    public void setDocumentWorkflowStatusDate(Date documentWorkflowStatusDate) {
        this.documentWorkflowStatusDate = documentWorkflowStatusDate;
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
     * @return piFullName
     */
    public String getPiFullName() {
        return piFullName;
    }
    /**
     * 
     * @param piFullName piFullName
     */
    public void setPiFullName(String piFullName) {
        this.piFullName = piFullName;
    }
    /**
     * 
     * @return piId
     */
    public Long getPiId() {
        return piId;
    }
    /**
     * 
     * @param piId piId
     */
    public void setPiId(Long piId) {
        this.piId = piId;
    }
    
    /**
     * 
     * @return localStudyProtocolIdentifier
     */
    public String getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     * 
     * @param localStudyProtocolIdentifier localStudyProtocolIdentifier
     */
    public void setLocalStudyProtocolIdentifier(String localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
    
    
}
