package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;

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
    private String rowId;
    /**
     * @param iso StudyResourcingDTO object
     */
    public TrialFundingWebDTO(StudyResourcingDTO iso) {
        super();
        this.fundingMechanismCode = CdConverter.convertCdToString(iso.getFundingMechanismCode());
        this.nihInstitutionCode = CdConverter.convertCdToString(iso.getNihInstitutionCode());
        this.nciDivisionProgramCode = CdConverter.convertCdToString(iso.getNciDivisionProgramCode());
        this.serialNumber = iso.getSerialNumber().getValue().toString();
        this.id = IiConverter.convertToString(iso.getIdentifier());
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

    /**
     * @return the rowId
     */
    public String getRowId() {
        return rowId;
    }

    /**
     * @param rowId the rowId to set
     */
    public void setRowId(String rowId) {
        this.rowId = rowId;
    }


}
