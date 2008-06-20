package gov.nih.nci.pa.service;

import java.io.Serializable;

import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;

/**
 * Class used to hold criteria used in searching protocols.
 * 
 *  Attr.                    Corresponding bo attribute
 * =====                    ==========================
 * protocolId                 domain.Protocol.id
 * nciIdentifier              domain.Protocol.nciIdentifier
 * leadOrganizationId         domain.HealthcareSite.id
 * leadOrganizationProtocolId domain.StudySite.id
 * studyPhaseCode             domain.Protocol.StudyPhaseCode
 * @author Hugh Reinhart
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class ProtocolSearchCriteria implements Serializable {    
    static final long serialVersionUID = 252345L;
    
    private Long protocolId;
    private String nciIdentifier;
    private String longTitleText;
    private String leadOrganizationId;
    private String leadOrganizationProtocolId;
    private String studyPhaseCode;
    private StudyStatusCode studyStatusCode;
    private StudyTypeCode studyTypeCode;
 
    /**
     * @return protocolId
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * @param protocolId protocolId
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    /**
     * @return accession number
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    
    /**
     * @param nciIdentifier nciIdentifier number
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    /**
     * @return title
     */
    public String getLongTitleText() {
        return longTitleText;
    }
    
    /**
     * @param longTitleText title
     */
    public void setLongTitleText(String longTitleText) {
        this.longTitleText = longTitleText;
    }

    /**
     * @return leadOrganizationId
     */
    public String getLeadOrganizationId() {
        return leadOrganizationId;
    }

    /**
     * 
     * @param leadOrganizationId leadOrganizationId
     */
    public void setLeadOrganizationId(String leadOrganizationId) {
        this.leadOrganizationId = leadOrganizationId;
    }

    /**
     * @return leadOrganizationProtocolId
     */
    public String getLeadOrganizationProtocolId() {
        return leadOrganizationProtocolId;
    }

    /**
     * 
     * @param leadOrganizationProtocolId leadOrganizationProtocolId
     */
    public void setLeadOrganizationProtocolId(String leadOrganizationProtocolId) {
        this.leadOrganizationProtocolId = leadOrganizationProtocolId;
    }

    /**
     * 
     * @return StudyPhaseCode
     */
    public String getStudyPhaseCode() {
        return studyPhaseCode;
    }

    /**
     * @param studyPhaseCode studyPhaseCode
     */
    public void setStudyPhaseCode(String studyPhaseCode) {
        this.studyPhaseCode = studyPhaseCode;
    }

    /**
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
     * @return studyTypeCode
     */
    public StudyTypeCode getStudyTypeCode() {
        return studyTypeCode;
    }

    /**
     * @param studyTypeCode studyTypeCode
     */
    public void setStudyTypeCode(StudyTypeCode studyTypeCode) {
        this.studyTypeCode = studyTypeCode;
    }
    
    
    
}