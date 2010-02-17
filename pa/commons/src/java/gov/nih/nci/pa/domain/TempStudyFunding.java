/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.NciDivisionProgramCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author Vrushali
 *
 */
@Entity
@Table (name = "TEMP_STUDY_FUNDING")
public class TempStudyFunding extends AbstractEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -4538577427187053537L;
    private String fundingMechanismCode;
    private String nihInstituteCode;
    private NciDivisionProgramCode nciDivisionProgramCode;
    private String serialNumber;
    private TempStudyProtocol tempStudyProtocol;
    /**
     * @return the fundingMechanismCode
     */
    @Column (name = "FUNDING_MECHANISM_CODE")
    public String getFundingMechanismCode() {
        return fundingMechanismCode;
    }
    /**
     * @param fundingMechanismCode the fundingMechanismCode to set
     */
    public void setFundingMechanismCode(String fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    /**
     * @return the nihInstituteCode
     */
    @Column (name = "NIH_INSTITUTE_CODE")
    public String getNihInstituteCode() {
        return nihInstituteCode;
    }
    /**
     * @param nihInstituteCode the nihInstituteCode to set
     */
    public void setNihInstituteCode(String nihInstituteCode) {
        this.nihInstituteCode = nihInstituteCode;
    }
    /**
     * @return the nciDivisionProgramCode
     */
    @Column (name = "NCI_DIVISION_PROGRAM_CODE")
    @Enumerated(EnumType.STRING)
    public NciDivisionProgramCode getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }
    /**
     * @param nciDivisionProgramCode the nciDivisionProgramCode to set
     */
    public void setNciDivisionProgramCode(
            NciDivisionProgramCode nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }
    /**
     * @return the serialNumber
     */
    @Column (name = "SERIAL_NUMBER")
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
     * @param tempStudyProtocol the tempStudyProtocol to set
     */
    public void setTempStudyProtocol(TempStudyProtocol tempStudyProtocol) {
        this.tempStudyProtocol = tempStudyProtocol;
    }
    /**
     * @return the tempStudyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "TEMP_STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
    public TempStudyProtocol getTempStudyProtocol() {
        return tempStudyProtocol;
    }
    
}
