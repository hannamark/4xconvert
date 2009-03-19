/**
 * 
 */
package gov.nih.nci.pa.dto;


/**
 * @author Vrushali
 *
 */
public class TrialFundingDTO {
    private String id;
    private String fundingMechanismCode;
    private String nihInstitutionCode;
    private String nciDivisionProgramCode;
    private String serialNumber;


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