package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;


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
    private Ii studyProtocolIi;
    private Cd fundingMechanismCode;
    private Cd fundingTypeCode;
    private Cd nciDivisionProgramCode;
    private Cd nihInstitutionCode;
    private St suffixGrantYear;
    private St suffixOther;
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
     * @return suffixOther
     */
    public St getSuffixOther() {
        return suffixOther;
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
    public Cd getFundingTypeCode() {
        return fundingTypeCode;
    }
    /**
     * @param fundingTypeCode fundingTypeCode
     */
    public void setFundingTypeCode(Cd fundingTypeCode) {
        this.fundingTypeCode = fundingTypeCode;
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
    /**
     * 
     * @return nciDivisionProgramCode
     */
    public Cd getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }
    /**
     * 
     * @param nciDivisionProgramCode nciDivisionProgramCode
     */
    public void setNciDivisionProgramCode(Cd nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }
    /**
     * 
     * @return nihInstitutionCode
     */
    public Cd getNihInstitutionCode() {
        return nihInstitutionCode;
    }
    /**
     * 
     * @param nihInstitutionCode nihInstitutionCode
     */
    public void setNihInstitutionCode(Cd nihInstitutionCode) {
        this.nihInstitutionCode = nihInstitutionCode;
    }
    
    /**
     * 
     * @return suffixGrantYear
     */
    public St getSuffixGrantYear() {
        return suffixGrantYear;
    }
    /**
     * 
     * @param suffixGrantYear suffixGrantYear
     */
    public void setSuffixGrantYear(St suffixGrantYear) {
        this.suffixGrantYear = suffixGrantYear;
    }    
    
    
    
    
    
}
