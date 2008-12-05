package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.ProgramCodesForNCI;
import gov.nih.nci.pa.enums.ProgramCodesForNIH;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@Entity
@Table(name =  "STUDY_INDLDE")
public class StudyIndlde extends AbstractEntity {

    private StudyProtocol studyProtocol;
    private ExpandedAccessStatusCode expandedAccessStatusCode;
    private Boolean expandedAccessIndicator;
    private GrantorCode grantorCode;
    private ProgramCodesForNIH nihInstHolderCode;
    private ProgramCodesForNCI nciDivProgHolderCode;
    private HolderTypeCode holderTypeCode;
    private String indldeNumber;
    private IndldeTypeCode indldeTypeCode;
    
    /**
     * @return studyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    /**
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    
    /**
     * @return expandedAccessStatusCode
     */
    @Column(name = "EXPANDED_ACCESS_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public ExpandedAccessStatusCode getExpandedAccessStatusCode() {
        return expandedAccessStatusCode;
    }
    /** 
     * @param expandedAccessStatusCode expandedAccessStatusCode
     */
    public void setExpandedAccessStatusCode(
            ExpandedAccessStatusCode expandedAccessStatusCode) {
        this.expandedAccessStatusCode = expandedAccessStatusCode;
    }
    /**
     * @return expandedAccessIndicator
     */
    @Column(name = "EXPANDED_ACCESS_INDICATOR")
    public Boolean getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }
    /**
     * @param expandedAccessIndicator expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(Boolean expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }
    /**
     * @return grantorCode
     */
    @Column(name = "GRANTOR_CODE")
    @Enumerated(EnumType.STRING)
    public GrantorCode getGrantorCode() {
        return grantorCode;
    }
    /**
     * @param grantorCode grantorCode
     */
    public void setGrantorCode(GrantorCode grantorCode) {
        this.grantorCode = grantorCode;
    }
    
    /**
     * @return nihInstHolderCode
     */
    @Column(name = "NIH_INST_HOLDER_CODE")
    @Enumerated(EnumType.STRING)
    public ProgramCodesForNIH getNihInstHolderCode() {
        return nihInstHolderCode;
    }
    /**
     * @param nihInstHolderCode nihInstHolderCode
     */
    public void setNihInstHolderCode(ProgramCodesForNIH nihInstHolderCode) {
        this.nihInstHolderCode = nihInstHolderCode;
    }
    /**
     * @return nciDivProgHolderCode
     */
    @Column(name = "NCI_DIV_PROG_HOLDER_CODE")
    @Enumerated(EnumType.STRING)
    public ProgramCodesForNCI getNciDivProgHolderCode() {
        return nciDivProgHolderCode;
    }
    /**
     * @param nciDivProgHolderCode nciDivProgHolderCode
     */
    public void setNciDivProgHolderCode(ProgramCodesForNCI nciDivProgHolderCode) {
        this.nciDivProgHolderCode = nciDivProgHolderCode;
    }
    /**
     * @return holderTypeCode
     */
    @Column(name = "HOLDER_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public HolderTypeCode getHolderTypeCode() {
        return holderTypeCode;
    }
    /**
     * @param holderTypeCode holderTypeCode
     */
    public void setHolderTypeCode(HolderTypeCode holderTypeCode) {
        this.holderTypeCode = holderTypeCode;
    }
    /**
     * @return indldeNumber
     */
    @Column(name = "INDLDE_NUMBER")
    public String getIndldeNumber() {
        return indldeNumber;
    }
    /**
     * @param indldeNumber indldeNumber
     */
    public void setIndldeNumber(String indldeNumber) {
        this.indldeNumber = indldeNumber;
    }
    /**
     * @return indldeTypeCode
     */
    @Column(name = "INDLDE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public IndldeTypeCode getIndldeTypeCode() {
        return indldeTypeCode;
    }
    /**
     * @param indldeTypeCode indldeTypeCode
     */
    public void setIndldeTypeCode(IndldeTypeCode indldeTypeCode) {
        this.indldeTypeCode = indldeTypeCode;
    }
}
