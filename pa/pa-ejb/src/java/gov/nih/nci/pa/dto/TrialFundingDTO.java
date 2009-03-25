/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.util.UUID;

import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;


/**
 * @author Vrushali
 *
 */
public class TrialFundingDTO {
    private String id;
    private String fundingMechanism;
    private String instituteCode;
    private String nciDivisionProgramCode;
    private String serialNumber;
    private String rowId;
    /**
     * 
     * @param isoDto dto
     */
    public TrialFundingDTO(StudyResourcingDTO isoDto) {
       super(); 
       this.id = IiConverter.convertToString(isoDto.getIdentifier());
       this.fundingMechanism = CdConverter.convertCdToString(isoDto.getFundingMechanismCode());
       this.instituteCode = CdConverter.convertCdToString(isoDto.getNihInstitutionCode());
       this.nciDivisionProgramCode = CdConverter.convertCdToString(isoDto.getNciDivisionProgramCode());
       this.serialNumber = isoDto.getSerialNumber().getValue().toString();
       this.rowId = UUID.randomUUID().toString();
    }
    /**
     * Deafult Constructor.
     */
    public TrialFundingDTO() {
        super();
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the fundingMechanism
     */
    public String getFundingMechanism() {
        return fundingMechanism;
    }
    /**
     * @param fundingMechanism the fundingMechanism to set
     */
    public void setFundingMechanism(String fundingMechanism) {
        this.fundingMechanism = fundingMechanism;
    }
    /**
     * @return the instituteCode
     */
    public String getInstituteCode() {
        return instituteCode;
    }
    /**
     * @param instituteCode the instituteCode to set
     */
    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }
    /**
     * @return the nciDivisionProgramCode
     */
    public String getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }
    /**
     * @param nciDivisionProgramCode the nciDivisionProgramCode to set
     */
    public void setNciDivisionProgramCode(String nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }
    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber the serialNumber to set
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