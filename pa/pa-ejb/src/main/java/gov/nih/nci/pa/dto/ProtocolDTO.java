package gov.nih.nci.pa.dto;

import java.io.Serializable;

import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.enums.SponsorMonitorCode;
import gov.nih.nci.pa.enums.StudyPhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;

/**
 * ProtocolDto for transferring protocol object .
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */


public class ProtocolDTO implements Serializable {
    static final long serialVersionUID = 283476876L;
    
    private Long protocolId;
    private String longTitleText;
    private StudyTypeCode studyTypeCode;
    private SponsorMonitorCode sponsorMonitorCode;
    private StudyPhaseCode studyPhaseCode;
    private StudyStatusCode studyStatusCode;
    private String leadOrganizationProtocolId;
    private String nciIdentifier;
    private SponsorMonitorCode sponsorMonitorcode;
    private String statusDate;
    private String leadOrganizationName;
    private Long leadOrganizationId;
    private String principalInvestigatorFullName;

    
    /**
     * Default constructor.
     */
    public ProtocolDTO() {
        // empty constructor
    }

    /**
     * Constructor for creating a dto from a domain object.
     * @param bo domain object
     */
    public ProtocolDTO(Protocol bo) {
        this.protocolId = bo.getId();
        this.longTitleText = bo.getLongTitleText();
        this.studyTypeCode = bo.getStudyTypeCode();
        this.sponsorMonitorCode = bo.getSponsorMonitorCode();
        this.studyPhaseCode = bo.getStudyPhaseCode();
        this.nciIdentifier = bo.getNciIdentifier();
        this.sponsorMonitorcode = bo.getSponsorMonitorCode();
    }
    
    /**
     * 
     * @return protocolId
     */
    public Long getProtocolId() {
        return protocolId;
    }
    /**
     * 
     * @param protocolId protocolId
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }
    /**
     * 
     * @return longTitleText
     */
    public String getLongTitleText() {
        return longTitleText;
    }
    /**
     * 
     * @param longTitleText longTitleText
     */
    public void setLongTitleText(String longTitleText) {
        this.longTitleText = longTitleText;
    }
    /**
     * 
     * @return StudyTypeCode studyTypeCode
     */
    public StudyTypeCode getStudyTypeCode() {
        return studyTypeCode;
    }
    /**
     * 
     * @param studyTypeCode studyTypeCode
     */
    public void setStudyTypeCode(StudyTypeCode studyTypeCode) {
        this.studyTypeCode = studyTypeCode;
    }
    /**
     * 
     * @return sponsorMonitorCode
     */
    public SponsorMonitorCode getSponsorMonitorCode() {
        return sponsorMonitorCode;
    }
    /**
     * 
     * @param sponsorMonitorCode sponsorMonitorCode
     */
    public void setSponsorMonitorCode(SponsorMonitorCode sponsorMonitorCode) {
        this.sponsorMonitorCode = sponsorMonitorCode;
    }
    /**
     * 
     * @return StudyPhaseCode
     */
    public StudyPhaseCode getStudyPhaseCode() {
        return studyPhaseCode;
    }
    /**
     * 
     * @param studyPhaseCode studyPhaseCode
     */
    public void setStudyPhaseCode(StudyPhaseCode studyPhaseCode) {
        this.studyPhaseCode = studyPhaseCode;
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
     * @return String nciIdentifier
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
     * @return sponsorMonitorcode
     */
    public SponsorMonitorCode getSponsorMonitorcode() {
        return sponsorMonitorcode;
    }
    /**
     * 
     * @param sponsorMonitorcode sponsorMonitorcode
     */
    public void setSponsorMonitorcode(SponsorMonitorCode sponsorMonitorcode) {
        this.sponsorMonitorcode = sponsorMonitorcode;
    }
    /**
     * 
     * @return statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }
    /**
     * 
     * @param statusDate statusDate
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
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
     * @return principalInvestigatorFullName
     */
    public String getPrincipalInvestigatorFullName() {
        return principalInvestigatorFullName;
    }
    /**
     * 
     * @param principalInvestigatorFullName principalInvestigatorFullName
     */
    public void setPrincipalInvestigatorFullName(
            String principalInvestigatorFullName) {
        this.principalInvestigatorFullName = principalInvestigatorFullName;
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
    
    
}
