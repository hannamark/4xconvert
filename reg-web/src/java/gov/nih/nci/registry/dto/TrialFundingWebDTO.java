package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

/**
 * Class for holding attributes for StudyResourcing DTO.
 * @author Bala Nair
 */
public class TrialFundingWebDTO {

    private String id;
    private String fundingMechanismCode;
    private String nihInstitutionCode;
    private String nciDivisionProgramCode;
    private String serialNumber;

    /**
     * @param iso StudyResourcingDTO object
     */
    public TrialFundingWebDTO(StudyResourcingDTO iso) {
        super();
        this.fundingMechanismCode = iso.getFundingMechanismCode().getCode();
        this.nihInstitutionCode = iso.getNihInstitutionCode().getCode();
        this.nciDivisionProgramCode = iso.getNciDivisionProgramCode().getCode();
        this.serialNumber = iso.getSerialNumber().getValue().toString();
        this.id = iso.getIi().getExtension();
    }

    /** .
     *  Default Constructor
     */
    public TrialFundingWebDTO() {
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
