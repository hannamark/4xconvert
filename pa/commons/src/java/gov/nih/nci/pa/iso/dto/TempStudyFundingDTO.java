/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;

/**
 * @author Vrushalli
 *
 */
public class TempStudyFundingDTO extends BaseDTO {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1471396366056533574L;
    private Cd fundingMechanismCode;
    private Cd nciDivisionProgramCode;
    private Cd nihInstitutionCode;
    private St serialNumber;
    private Ii tempStudyProtocolIi;
    /**
     * @return the fundingMechanismCode
     */
    public Cd getFundingMechanismCode() {
        return fundingMechanismCode;
    }
    /**
     * @param fundingMechanismCode the fundingMechanismCode to set
     */
    public void setFundingMechanismCode(Cd fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    /**
     * @return the nciDivisionProgramCode
     */
    public Cd getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }
    /**
     * @param nciDivisionProgramCode the nciDivisionProgramCode to set
     */
    public void setNciDivisionProgramCode(Cd nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }
    /**
     * @return the nihInstitutionCode
     */
    public Cd getNihInstitutionCode() {
        return nihInstitutionCode;
    }
    /**
     * @param nihInstitutionCode the nihInstitutionCode to set
     */
    public void setNihInstitutionCode(Cd nihInstitutionCode) {
        this.nihInstitutionCode = nihInstitutionCode;
    }
    /**
     * @return the serialNumber
     */
    public St getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(St serialNumber) {
        this.serialNumber = serialNumber;
    }
    /**
     * @return the tempStudyProtocolIi
     */
    public Ii getTempStudyProtocolIi() {
        return tempStudyProtocolIi;
    }
    /**
     * @param tempStudyProtocolIi the tempStudyProtocolIi to set
     */
    public void setTempStudyProtocolIi(Ii tempStudyProtocolIi) {
        this.tempStudyProtocolIi = tempStudyProtocolIi;
    }

}
