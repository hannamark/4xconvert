package gov.nih.nci.pa.dto;

import gov.nih.nci.coppa.iso.Cd;

/**
 * StudyProtocolDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 08/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

public class StudyProtocolDTO extends BaseDTO {
    
    private Cd monitorCode;
    private Cd accrualReportingMethodCode;
    private Cd summaryFourFundingCategoryCode;
    private Cd phaseCode;
    private Cd allocationCode;
    private Cd controlConcurrencyTypeCode;
    private Cd controlTypeCode;
    
    
    /**
     * 
     * @return monitorCode
     */
    public Cd getMonitorCode() {
        return monitorCode;
    }

    /**
     * 
     * @param monitorCode monitorCode
     */
    public void setMonitorCode(Cd monitorCode) {
        this.monitorCode = monitorCode;
    }

    /**
     * 
     * @return accrualReportingMethodCode
     */
    public Cd getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }

    /**
     * 
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(Cd accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }

    /**
     * 
     * @return accrualReportingMethodCode
     */
    public Cd getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }

    /**
     * 
     * @param summaryFourFundingCategoryCode accrualReportingMethodCode
     */
    public void setSummaryFourFundingCategoryCode(Cd summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }

    /**
     * 
     * @return phaseCode
     */
    public Cd getPhaseCode() {
        return phaseCode;
    }

    /**
     * 
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(Cd phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * 
     * @return allocationCode
     */
    public Cd getAllocationCode() {
        return allocationCode;
    }

    /**
     * 
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(Cd allocationCode) {
        this.allocationCode = allocationCode;
    }

    /**
     * 
     * @return controlConcurrencyTypeCode
     */
    public Cd getControlConcurrencyTypeCode() {
        return controlConcurrencyTypeCode;
    }

    /**
     * 
     * @param controlConcurrencyTypeCode controlConcurrencyTypeCode
     */
    public void setControlConcurrencyTypeCode(Cd controlConcurrencyTypeCode) {
        this.controlConcurrencyTypeCode = controlConcurrencyTypeCode;
    }

    /**
     * 
     * @return controlTypeCode
     */
    public Cd getControlTypeCode() {
        return controlTypeCode;
    }

    /**
     * 
     * @param controlTypeCode controlTypeCode
     */
    public void setControlTypeCode(Cd controlTypeCode) {
        this.controlTypeCode = controlTypeCode;
    }
    

}
