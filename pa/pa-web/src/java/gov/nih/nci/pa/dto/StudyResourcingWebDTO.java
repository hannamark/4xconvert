package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

/**
 * Class for holding attributes for StudyResourcing DTO.
 * @author Kalpana Guthikonda
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class StudyResourcingWebDTO {
    
    private String fundingTypeCode;
    private String fundingMechanismCode;
    private String institutionCode;
    private String monitorCode;
    private String suffixgrantYear;
    private String suffixOther;
    private String id;
    private String serialNumber;
    
    /**
     * @param iso StudyResourcingDTO object
     */
    public StudyResourcingWebDTO(StudyResourcingDTO iso) {
        super();
        this.fundingMechanismCode = iso.getFundingMechanismCode().getCode();
        this.institutionCode = iso.getNihInstitutionCode().getCode();
        this.monitorCode = iso.getNciDivisionProgramCode().getCode();
        this.fundingTypeCode = iso.getFundingTypeCode().getCode();
        this.suffixgrantYear = iso.getSuffixGrantYear().getValue();
        this.suffixOther = iso.getSuffixOther().getValue();
        this.id = iso.getIi().getExtension();
        this.serialNumber = iso.getSerialNumber().getValue();
    }
    
    /** .
     *  Default Constructor
     */
    public StudyResourcingWebDTO() {
        super();
    }

    /**
     * @return fundingMechanismCode
     */
    public String getFundingMechanismCode() {
        return fundingMechanismCode;
    }
    /**
     * @param fundingMechanismCode fundingMechanismCode
     */
    public void setFundingMechanismCode(String fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    /**
     * @return fundingTypeCode
     */
    public String getFundingTypeCode() {
        return fundingTypeCode;
    }
    /**
     * @return institutionCode
     */
    public String getInstitutionCode() {
        return institutionCode;
    }
    /**
     * @return monitorCode
     */
    public String getMonitorCode() {
        return monitorCode;
    }
    /**
     * @return suffixgrantYear
     */
    public String getSuffixgrantYear() {
        return suffixgrantYear;
    }
    /**
     * @return suffixOther
     */
    public String getSuffixOther() {
        return suffixOther;
    }
    /**
     * @param fundingTypeCode fundingTypeCode
     */
    public void setFundingTypeCode(String fundingTypeCode) {
        this.fundingTypeCode = fundingTypeCode;
    }
    /**
     * @param institutionCode institutionCode
     */
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }
    /**
     * @param monitorCode monitorCode
     */
    public void setMonitorCode(String monitorCode) {
        this.monitorCode = monitorCode;
    }
    /**
     * @param suffixgrantYear suffixgrantYear
     */
    public void setSuffixgrantYear(String suffixgrantYear) {
        this.suffixgrantYear = suffixgrantYear;
    }
    /**
     * @param suffixOther suffixOther
     */
    public void setSuffixOther(String suffixOther) {
        this.suffixOther = suffixOther;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber serialNumber
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
