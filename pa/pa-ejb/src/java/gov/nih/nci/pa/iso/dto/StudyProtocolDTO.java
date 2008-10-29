package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;


/**
 * StudyProtocolDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 08/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

public class StudyProtocolDTO extends BaseDTO {
    
    private St acronym;
    private Cd allocationCode;
    private Cd accrualReportingMethodCode;
    private Bl expandedAccessIndicator;
    private Ii assignedIdentifier; // used to store nci-accession number
    private Cd monitorCode;
    private St officialTitle;
    private Cd phaseCode;
    private Ts primaryCompletionDate;
    private Cd primaryCompletionDateTypeCode;
    private Ts startDate;
    private Cd startDateTypeCode;
    private Bl dataMonitoringCommitteInd;
    private Bl indIdeIndicator;
    
    
    /**
     * @return the indIdeIndicator
     */
    public Bl getIndIdeIndicator() {
        return indIdeIndicator;
    }

    /**
     * @param indIdeIndicator the indIdeIndicator to set
     */
    public void setIndIdeIndicator(Bl indIdeIndicator) {
        this.indIdeIndicator = indIdeIndicator;
    }

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
     * @return acronym
     */
    public St getAcronym() {
        return acronym;
    }

    /**
     * 
     * @param acronym acronym
     */
    public void setAcronym(St acronym) {
        this.acronym = acronym;
    }

    /**
     * 
     * @return expandedAccessIndicator
     */
    public Bl getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     * 
     * @param expandedAccessIndicator expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(Bl expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }

    /**
     * 
     * @return assignedIdentifier
     */
    public Ii getAssignedIdentifier() {
        return assignedIdentifier;
    }

    /**
     * 
     * @param assignedIdentifier assignedIdentifier
     */
    public void setAssignedIdentifier(Ii assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }

    /**
     * 
     * @return officialTitle
     */
    public St getOfficialTitle() {
        return officialTitle;
    }

    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(St officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * 
     * @return primaryCompletionDate
     */
    public Ts getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * 
     * @param primaryCompletionDate primaryCompletionDate
     */
    public void setPrimaryCompletionDate(Ts primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * 
     * @return primaryCompletionDateTypeCode
     */
    public Cd getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }

    /**
     * 
     * @param primaryCompletionDateTypeCode primaryCompletionDateTypeCode
     */
    public void setPrimaryCompletionDateTypeCode(Cd primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }

    /**
     * 
     * @return startDate
     */
    public Ts getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate startDate
     */
    public void setStartDate(Ts startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return startDateTypeCode
     */
    public Cd getStartDateTypeCode() {
        return startDateTypeCode;
    }

    /**
     * 
     * @param startDateTypeCode startDateTypeCode
     */
    public void setStartDateTypeCode(Cd startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }

    /**
     * @return the dataMonitoringCommitteInd
     */
    public Bl getDataMonitoringCommitteInd() {
        return dataMonitoringCommitteInd;
    }

    /**
     * @param dataMonitoringCommitteInd the dataMonitoringCommitteInd to set
     */
    public void setDataMonitoringCommitteInd(Bl dataMonitoringCommitteInd) {
        this.dataMonitoringCommitteInd = dataMonitoringCommitteInd;
    }
}

