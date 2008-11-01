package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
public class StudyIndldeDTO extends BaseDTO {

    private Ii studyProtocolIi;
    private Cd expandedAccessStatusCode;
    private Bl expandedAccessIndicator;
    private Cd grantorCode;
    private Cd holderTypeCode;
    private Cd nihInstHolderCode;
    private Cd nciDivProgHolderCode;
    private St indldeNumber;
    private Cd indldeTypeCode;
    
    /**
     * @return studyProtocolIi
     */
    public Ii getStudyProtocolIi() {
        return studyProtocolIi;
    }
    
    /**
     * @param studyProtocolIi studyProtocolIi
     */
    public void setStudyProtocolIi(Ii studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }
    /**
     * @return expandedAccessIndicator
     */
    public Bl getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }
    /**
     * @param expandedAccessIndicator expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(Bl expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }
    /**
     * @return grantorCode
     */
    public Cd getGrantorCode() {
        return grantorCode;
    }
    /**
     * @param grantorCode grantorCode
     */
    public void setGrantorCode(Cd grantorCode) {
        this.grantorCode = grantorCode;
    }    
    /**
     * @return holderTypeCode
     */
    public Cd getHolderTypeCode() {
        return holderTypeCode;
    }
    /**
     * @param holderTypeCode holderTypeCode
     */
    public void setHolderTypeCode(Cd holderTypeCode) {
        this.holderTypeCode = holderTypeCode;
    }
    /**
     * @return indldeNumber
     */
    public St getIndldeNumber() {
        return indldeNumber;
    }
    /**
     * @param indldeNumber indldeNumber
     */
    public void setIndldeNumber(St indldeNumber) {
        this.indldeNumber = indldeNumber;
    }
    /**
     * @return indldeTypeCode
     */
    public Cd getIndldeTypeCode() {
        return indldeTypeCode;
    }
    /**
     * @param indldeTypeCode indldeTypeCode
     */
    public void setIndldeTypeCode(Cd indldeTypeCode) {
        this.indldeTypeCode = indldeTypeCode;
    }

    /**
     * @return expandedAccessStatusCode
     */
    public Cd getExpandedAccessStatusCode() {
        return expandedAccessStatusCode;
    }

    /**
     * @param expandedAccessStatusCode expandedAccessStatusCode
     */
    public void setExpandedAccessStatusCode(Cd expandedAccessStatusCode) {
        this.expandedAccessStatusCode = expandedAccessStatusCode;
    }

    /**
     * @return nihInstHolderCode
     */
    public Cd getNihInstHolderCode() {
        return nihInstHolderCode;
    }

    /**
     * @param nihInstHolderCode nihInstHolderCode
     */
    public void setNihInstHolderCode(Cd nihInstHolderCode) {
        this.nihInstHolderCode = nihInstHolderCode;
    }

    /**
     * @return nciDivProgHolderCode
     */
    public Cd getNciDivProgHolderCode() {
        return nciDivProgHolderCode;
    }

    /**
     * @param nciDivProgHolderCode nciDivProgHolderCode
     */
    public void setNciDivProgHolderCode(Cd nciDivProgHolderCode) {
        this.nciDivProgHolderCode = nciDivProgHolderCode;
    }
}
