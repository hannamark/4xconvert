/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;

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
@Table (name = "TEMP_STUDY_INDIDE")
public class TempStudyIndIde extends AbstractEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4623392526279206778L;
    private String indIdeNumber;
    private IndldeTypeCode indldeTypeCode;
    private GrantorCode grantorCode;
    private HolderTypeCode holderTypeCode;
    private Boolean expandedAccessIndicator;
    private ExpandedAccessStatusCode expandedAccessStatusCode;
    private NihInstituteCode nihInstHolderCode;
    private NciDivisionProgramCode nciDivPrgHolderCode;
    private TempStudyProtocol tempStudyProtocol;
    /**
     * @return the indIdeNumber
     */
    @Column(name = "INDIDE_NUMBER")
    public String getIndIdeNumber() {
        return indIdeNumber;
    }
    /**
     * @param indIdeNumber the indIdeNumber to set
     */
    public void setIndIdeNumber(String indIdeNumber) {
        this.indIdeNumber = indIdeNumber;
    }
    /**
     * @return the indldeTypeCode
     */
    @Column (name = "INDIDE_TYPE_CODE")
    @Enumerated (EnumType.STRING)
    public IndldeTypeCode getIndldeTypeCode() {
        return indldeTypeCode;
    }
    /**
     * @param indldeTypeCode the indldeTypeCode to set
     */
    public void setIndldeTypeCode(IndldeTypeCode indldeTypeCode) {
        this.indldeTypeCode = indldeTypeCode;
    }
    /**
     * @return the grantorCode
     */
    @Column (name = "GRANTOR_CODE")
    @Enumerated(EnumType.STRING)
    public GrantorCode getGrantorCode() {
        return grantorCode;
    }
    /**
     * @param grantorCode the grantorCode to set
     */
    public void setGrantorCode(GrantorCode grantorCode) {
        this.grantorCode = grantorCode;
    }
    /**
     * @return the holderTypeCode
     */
    @Column (name = "HOLDER_TYPE_CODE")
    @Enumerated (EnumType.STRING)
    public HolderTypeCode getHolderTypeCode() {
        return holderTypeCode;
    }
    /**
     * @param holderTypeCode the holderTypeCode to set
     */
    public void setHolderTypeCode(HolderTypeCode holderTypeCode) {
        this.holderTypeCode = holderTypeCode;
    }
    /**
     * @return the expandedAccessIndicator
     */
    @Column (name = "EXPANDED_ACCESS_INDICATOR")
    public Boolean getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }
    /**
     * @param expandedAccessIndicator the expandedAccessIndicator to set
     */
    public void setExpandedAccessIndicator(Boolean expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }
    /**
     * @return the expandedAccessStatusCode
     */
    @Column (name = "EXPANDED_ACCESS_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public ExpandedAccessStatusCode getExpandedAccessStatusCode() {
        return expandedAccessStatusCode;
    }
    /**
     * @param expandedAccessStatusCode the expandedAccessStatusCode to set
     */
    public void setExpandedAccessStatusCode(ExpandedAccessStatusCode expandedAccessStatusCode) {
        this.expandedAccessStatusCode = expandedAccessStatusCode;
    }
    /**
     * @return the nihInstHolderCode
     */
    @Column (name = "NIH_INST_HOLDER_CODE")
    @Enumerated (EnumType.STRING)
    public NihInstituteCode getNihInstHolderCode() {
        return nihInstHolderCode;
    }
    /**
     * @param nihInstHolderCode the nihInstHolderCode to set
     */
    public void setNihInstHolderCode(NihInstituteCode nihInstHolderCode) {
        this.nihInstHolderCode = nihInstHolderCode;
    }
    /**
     * @return the nciDivPrgHolderCode
     */
    @Column (name = "NCI_DIV_PROG_HOLDER_CODE")
    @Enumerated (EnumType.STRING)
    public NciDivisionProgramCode getNciDivPrgHolderCode() {
        return nciDivPrgHolderCode;
    }
    /**
     * @param nciDivPrgHolderCode the nciDivPrgHolderCode to set
     */
    public void setNciDivPrgHolderCode(NciDivisionProgramCode nciDivPrgHolderCode) {
        this.nciDivPrgHolderCode = nciDivPrgHolderCode;
    }
    /**
     * @return the tempStudyProtocolIdentifier
     */
    @ManyToOne
    @JoinColumn(name = "TEMP_STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
    public TempStudyProtocol getTempStudyProtocol() {
        return tempStudyProtocol;
    }
    /**
     * @param tempStudyProtocol the tempStudyProtocol to set
     */
    public void setTempStudyProtocol(TempStudyProtocol tempStudyProtocol) {
        this.tempStudyProtocol = tempStudyProtocol;
    }
    
}
