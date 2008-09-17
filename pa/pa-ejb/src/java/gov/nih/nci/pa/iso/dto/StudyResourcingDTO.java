package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;

/**
 * StudyResourcingDTO for transferring Study Resourcing object .
 * @author Naveen Amiruddin
 * @since 09/09/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyResourcingDTO extends BaseDTO {
    private Cd typeCode;
    private Bl summary4ReportedResourceIndicator;
    private Ii organizationIdentifier;
    private Ii resourceProviderIdentifier;
    Ii studyProtocolIi;
    private Cd fundingMechanismCode;
    private String fundingTypeCode;
    private Cd monitorCode;
    private Cd institutionCode;
    private Ts suffixgrantYear;
    private St suffixOther;
    private Long id;
    private St serialNumber;
    /**
     * 
     * @return typeCode
     */
    public Cd getTypeCode() {
        return typeCode;
    }
    /**
     * 
     * @param typeCode typeCode
     */
    public void setTypeCode(Cd typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * 
     * @return summary4ReportedResourceIndicator
     */
    public Bl getSummary4ReportedResourceIndicator() {
        return summary4ReportedResourceIndicator;
    }
    /**
     * 
     * @param summary4ReportedResourceIndicator Bl
     */
    public void setSummary4ReportedResourceIndicator(
            Bl summary4ReportedResourceIndicator) {
        this.summary4ReportedResourceIndicator = summary4ReportedResourceIndicator;
    }
    /**
     * 
     * @return organizationIdentifier
     */
    public Ii getOrganizationIdentifier() {
        return organizationIdentifier;
    }
    /**
     * 
     * @param organizationIdentifier Ii
     */
    public void setOrganizationIdentifier(Ii organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
    /**
     * 
     * @return resourceProviderIdentifier
     */
    public Ii getResourceProviderIdentifier() {
        return resourceProviderIdentifier;
    }
    /**
     * 
     * @param resourceProviderIdentifier Ii
     */
    public void setResourceProviderIdentifier(Ii resourceProviderIdentifier) {
        this.resourceProviderIdentifier = resourceProviderIdentifier;
    }

    /**
     * @return the studyProtocolidentifier
     */
    public Ii getStudyProtocolIi() {
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolIi ii
     */
    public void setStudyProtocolIi(Ii studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }
    /**
     * @return the fundingMechanismCode
     */
    public Cd getFundingMechanismCode() {
        return fundingMechanismCode;
    }
    /**
     * @param fundingMechanismCode Cd
     */
    public void setFundingMechanismCode(Cd fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    
    /**
     * @return monitorCode
     */
    public Cd getMonitorCode() {
        return monitorCode;
    }
    /**
     * @return institutionCode
     */
    public Cd getInstitutionCode() {
        return institutionCode;
    }
    /**
     * @return suffixgrantYear
     */
    public Ts getSuffixgrantYear() {
        return suffixgrantYear;
    }
    /**
     * @return suffixOther
     */
    public St getSuffixOther() {
        return suffixOther;
    }
    
    /**
     * @param monitorCode monitorCode Cd
     */
    public void setMonitorCode(Cd monitorCode) {
        this.monitorCode = monitorCode;
    }
    /**
     * @param institutionCode institutionCode Cd
     */
    public void setInstitutionCode(Cd institutionCode) {
        this.institutionCode = institutionCode;
    }
    /**
     * @param suffixgrantYear suffixgrantYear Ts
     */
    public void setSuffixgrantYear(Ts suffixgrantYear) {
        this.suffixgrantYear = suffixgrantYear;
    }
    /**
     * @param suffixOther suffixOther St
     */
    public void setSuffixOther(St suffixOther) {
        this.suffixOther = suffixOther;
    }
    /**
     * @return fundingTypeCode
     */
    public String getFundingTypeCode() {
        return fundingTypeCode;
    }
    /**
     * @param fundingTypeCode fundingTypeCode
     */
    public void setFundingTypeCode(String fundingTypeCode) {
        this.fundingTypeCode = fundingTypeCode;
    }
    /**
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return serialNumber
     */
    public St getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber serialNumber
     */
    public void setSerialNumber(St serialNumber) {
        this.serialNumber = serialNumber;
    }    
}
