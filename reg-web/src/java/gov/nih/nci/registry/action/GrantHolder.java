package gov.nih.nci.registry.action;

import java.io.Serializable;

/**
 * 
 * @author Harsha
 *
 */
public class GrantHolder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String fundingMechanism;
    private String instituteCode;
    private String serialNumber;
    private String nciDivisionProgramCode;
    private String rowId;
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
