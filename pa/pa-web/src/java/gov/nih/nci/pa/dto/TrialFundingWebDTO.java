package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

/**
 * Class for holding attributes for StudyResourcing DTO.
 * @author Kalpana Guthikonda
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class TrialFundingWebDTO {
    
    private String fundingTypeCode;
    private String fundingMechanismCode;
    private String nihInstitutionCode;
    private String nciDivisionProgramCode;
    private String suffixgrantYear;
    private String suffixOther;
    private String id;
    private String serialNumber;
    private String inactiveCommentText;
    
    /**
     * @param iso StudyResourcingDTO object
     */
    public TrialFundingWebDTO(StudyResourcingDTO iso) {
        super();
        this.fundingMechanismCode = iso.getFundingMechanismCode().getCode();
        this.nihInstitutionCode = iso.getNihInstitutionCode().getCode();
        this.nciDivisionProgramCode = iso.getNciDivisionProgramCode().getCode();
        this.fundingTypeCode = iso.getFundingTypeCode().getCode();
        this.suffixgrantYear = iso.getSuffixGrantYear().getValue();
        this.suffixOther = iso.getSuffixOther().getValue();
        this.id = iso.getIi().getExtension();
        this.serialNumber = iso.getSerialNumber().getValue();
        //this.inactiveCommentText = iso.getInactiveCommentText().getValue();
    }
    
    /** .
     *  Default Constructor
     */
    public TrialFundingWebDTO() {
        super();
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
     * @return nihInstitutionCode
     */
    public String getNihInstitutionCode() {
        return nihInstitutionCode;
    }

    /**
     * @param nihInstitutionCode nihInstitutionCode
     */
    public void setNihInstitutionCode(String nihInstitutionCode) {
        this.nihInstitutionCode = nihInstitutionCode;
    }

    /**
     * @return nciDivisionProgramCode
     */
    public String getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }

    /**
     * @param nciDivisionProgramCode nciDivisionProgramCode
     */
    public void setNciDivisionProgramCode(String nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }

    /**
     * @return suffixgrantYear
     */
    public String getSuffixgrantYear() {
        return suffixgrantYear;
    }

    /**
     * @param suffixgrantYear suffixgrantYear
     */
    public void setSuffixgrantYear(String suffixgrantYear) {
        this.suffixgrantYear = suffixgrantYear;
    }

    /**
     * @return suffixOther
     */
    public String getSuffixOther() {
        return suffixOther;
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

    /**
     * @return inactiveCommentText
     */
    public String getInactiveCommentText() {
        return inactiveCommentText;
    }

    /**
     * @param inactiveCommentText inactiveCommentText
     */
    public void setInactiveCommentText(String inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }

    
}
