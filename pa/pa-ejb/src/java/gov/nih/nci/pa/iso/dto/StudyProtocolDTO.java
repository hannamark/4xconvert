package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;


/**
 * StudyProtocolDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 08/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@SuppressWarnings({"PMD.TooManyFields" })
public class StudyProtocolDTO extends BaseDTO {
    
    private St acronym;
    private Cd accrualReportingMethodCode;
    private Ii assignedIdentifier; 
    private Bl dataMonitoringCommitteeAppointedIndicator;
    private Bl delayedpostingIndicator;
    private Bl expandedAccessIndicator;
    private Bl fdaRegulatedIndicator;
    private St officialTitle;
    private Int maximumTargetAccrualNumber;
    private Cd phaseCode;
    private St phaseOtherText;
    private Ts primaryCompletionDate;
    private Cd primaryCompletionDateTypeCode;
    private Cd primaryPurposeCode;
    private St primaryPurposeOtherText;
    private St publicDescription;
    private St publicTitle;
    private Ts recordVerificationDate;
    private St scientificDescription;
    private Bl section801Indicator;
    private Ts startDate;
    private Cd startDateTypeCode;
    
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
     * @return dataMonitoringCommitteeAppointedIndicator dataMonitoringCommitteeAppointedIndicator
     */
    public Bl getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * 
     * @param dataMonitoringCommitteeAppointedIndicator dataMonitoringCommitteeAppointedIndicator;
     */
    public void setDataMonitoringCommitteeAppointedIndicator(
            Bl dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * 
     * @return delayedpostingIndicator
     */
    public Bl getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }

    /**
     * 
     * @param delayedpostingIndicator delayedpostingIndicator
     */
    public void setDelayedpostingIndicator(Bl delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
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
     * @return fdaRegulatedIndicator
     */
    public Bl getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }

    /**
     * 
     * @param fdaRegulatedIndicator fdaRegulatedIndicator
     */
    public void setFdaRegulatedIndicator(Bl fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }

    
    /**
     * 
     * @return maximumTargetAccrualNumber
     */
    public Int getMaximumTargetAccrualNumber() {
        return maximumTargetAccrualNumber;
    }

    /**
     * 
     * @param maximumTargetAccrualNumber maximumTargetAccrualNumber
     */
    public void setMaximumTargetAccrualNumber(Int maximumTargetAccrualNumber) {
        this.maximumTargetAccrualNumber = maximumTargetAccrualNumber;
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
     * @return phaseOtherText
     */
    public St getPhaseOtherText() {
        return phaseOtherText;
    }

    /**
     * @param phaseOtherText phaseOtherText
     */
    public void setPhaseOtherText(St phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
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
     * @return primaryPurposeCode
     */
    public Cd getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }

    /**
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(Cd primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    
    /**
     * @return primaryPurposeOtherText
     */
    public St getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }

    /**
     * @param primaryPurposeOtherText primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(St primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }

    /**
     * 
     * @return publicDescription
     */
    public St getPublicDescription() {
        return publicDescription;
    }

    /**
     * 
     * @param publicDescription publicDescription
     */
    public void setPublicDescription(St publicDescription) {
        this.publicDescription = publicDescription;
    }

    /**
     * 
     * @return publicTitle
     */
    public St getPublicTitle() {
        return publicTitle;
    }

    /**
     * 
     * @param publicTitle publicTitle
     */
    public void setPublicTitle(St publicTitle) {
        this.publicTitle = publicTitle;
    }

    /**
     * 
     * @return recordVerificationDate
     */
    public Ts getRecordVerificationDate() {
        return recordVerificationDate;
    }

    /**
     * 
     * @param recordVerificationDate recordVerificationDate
     */
    public void setRecordVerificationDate(Ts recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }

    /**
     * 
     * @return scientificDescription
     */
    public St getScientificDescription() {
        return scientificDescription;
    }

    /**
     * 
     * @param scientificDescription scientificDescription
     */
    public void setScientificDescription(St scientificDescription) {
        this.scientificDescription = scientificDescription;
    }

    /**
     * 
     * @return section801Indicator
     */
    public Bl getSection801Indicator() {
        return section801Indicator;
    }

    /**
     * 
     * @param section801Indicator section801Indicator
     */
    public void setSection801Indicator(Bl section801Indicator) {
        this.section801Indicator = section801Indicator;
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

}

