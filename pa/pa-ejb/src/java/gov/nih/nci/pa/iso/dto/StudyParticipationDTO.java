/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;

/**
 * StudyParticipationDTO for transferring StudyParticipation object .
 * @author Hugh Reinhart
 * @since 07/22/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyParticipationDTO extends OrganizationFunctionalRoleDTO {
    private static final long serialVersionUID = 1234587878L;
    
    private Ii healthcareFacilityIi;
    private Ii researchOrganizationIi;
    private Ii oversightCommitteeIi;
    private Cd functionalCode;
    private St localStudyProtocolIdentifier;
    private Cd reviewBoardApprovalStatusCode;
    private St reviewBoardApprovalNumber;
    private Ts reviewBoardApprovalDate;
    private Int targetAccrualNumber;
    
    /**
     * @return the healthcareFacilityIi
     */
    public Ii getHealthcareFacilityIi() {
        return healthcareFacilityIi;
    }
    /**
     * @param healthcareFacilityIi the healthcareFacilityIi to set
     */
    public void setHealthcareFacilityIi(Ii healthcareFacilityIi) {
        this.healthcareFacilityIi = healthcareFacilityIi;
    }
    /**
     * @return the researchOrganizationIi
     */
    public Ii getResearchOrganizationIi() {
        return researchOrganizationIi;
    }
    /**
     * @param researchOrganizationIi the researchOrganizationIi to set
     */
    public void setResearchOrganizationIi(Ii researchOrganizationIi) {
        this.researchOrganizationIi = researchOrganizationIi;
    }
    /**
     * @return the oversightCommitteeIi
     */
    public Ii getOversightCommitteeIi() {
        return oversightCommitteeIi;
    }
    /**
     * @param oversightCommitteeIi the oversightCommitteeIi to set
     */
    public void setOversightCommitteeIi(Ii oversightCommitteeIi) {
        this.oversightCommitteeIi = oversightCommitteeIi;
    }
    /**
     * @return the functionalCode
     */
    public Cd getFunctionalCode() {
        return functionalCode;
    }
    /**
     * @param functionalCode the functionalCode to set
     */
    public void setFunctionalCode(Cd functionalCode) {
        this.functionalCode = functionalCode;
    }
    /**
     * @return the localStudyProtocolIdentifier
     */
    public St getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     * @param localStudyProtocolIdentifier the localStudyProtocolIdentifier to set
     */
    public void setLocalStudyProtocolIdentifier(St localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
    /**
     * @return the reviewBoardApprovalStatusCode
     */
    public Cd getReviewBoardApprovalStatusCode() {
        return reviewBoardApprovalStatusCode;
    }
    /**
     * @param reviewBoardApprovalStatusCode the reviewBoardApprovalStatusCode to set
     */
    public void setReviewBoardApprovalStatusCode(Cd reviewBoardApprovalStatusCode) {
        this.reviewBoardApprovalStatusCode = reviewBoardApprovalStatusCode;
    }
    /**
     * @return the reviewBoardApprovalNumber
     */
    public St getReviewBoardApprovalNumber() {
        return reviewBoardApprovalNumber;
    }
    /**
     * @param reviewBoardApprovalNumber the reviewBoardApprovalNumber to set
     */
    public void setReviewBoardApprovalNumber(St reviewBoardApprovalNumber) {
        this.reviewBoardApprovalNumber = reviewBoardApprovalNumber;
    }
    /**
     * @return the reviewBoardApprovalDate
     */
    public Ts getReviewBoardApprovalDate() {
        return reviewBoardApprovalDate;
    }
    /**
     * @param reviewBoardApprovalDate the reviewBoardApprovalDate to set
     */
    public void setReviewBoardApprovalDate(Ts reviewBoardApprovalDate) {
        this.reviewBoardApprovalDate = reviewBoardApprovalDate;
    }
    /**
     * @return the targetAccrualNumber
     */
    public Int getTargetAccrualNumber() {
        return targetAccrualNumber;
    }
    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(Int targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }
}
