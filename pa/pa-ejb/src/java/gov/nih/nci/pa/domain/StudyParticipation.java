package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * StudySite bean for managing StudySite.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_PARTICIPATION")
public class StudyParticipation extends OrganizationFunctionalRole {
    private static final long serialVersionUID = 1234567890L;

    private StudyParticipationFunctionalCode functionalCode;
    private String localStudyProtocolIdentifier;
    private String reviewBoardApprovalNumber;
    private ReviewBoardApprovalStatusCode reviewBoardApprovalStatusCode; 
    private Integer targetAccrualNumber;
    private Timestamp reviewBoardApprovalDate;
    private HealthCareFacility healthCareFacility;
    private ResearchOrganization researchOrganization;
    private OversightCommittee oversightCommittee;
    private List<StudySiteAccrualStatus> studySiteAccrualStatuses;
    private List<StudyParticipationContact> studyParticipationContacts;
    
    /**
     * 
     * @return functionalCode
     */
    @Column(name = "FUNCTIONAL_CODE")
    @Enumerated(EnumType.STRING)    
    public StudyParticipationFunctionalCode getFunctionalCode() {
        return functionalCode;
    }
    /**
     * 
     * @param functionalCode functionalCode
     */
    public void setFunctionalCode(StudyParticipationFunctionalCode functionalCode) {
        this.functionalCode = functionalCode;
    }
    /**
     * 
     * @return localStudyProtocolIdentifier
     */
    @Column(name = "LOCAL_SP_INDENTIFIER")
    public String getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     * 
     * @param localStudyProtocolIdentifier localStudyProtocolIdentifier
     */
    public void setLocalStudyProtocolIdentifier(String localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
    /**
     * @return the reviewBoardApprovalNumber
     */
    @Column(name = "REVIEW_BOARD_APPROVAL_NUMBER")
    public String getReviewBoardApprovalNumber() {
        return reviewBoardApprovalNumber;
    }
    /**
     * @param reviewBoardApprovalNumber the reviewBoardApprovalNumber to set
     */
    public void setReviewBoardApprovalNumber(String reviewBoardApprovalNumber) {
        this.reviewBoardApprovalNumber = reviewBoardApprovalNumber;
    }
    /**
     * @return the reviewBoardApprovalDate
     */
    @Column(name = "REVIEW_BOARD_APPROVAL_DATE")
    @Enumerated(EnumType.STRING)
    public Timestamp getReviewBoardApprovalDate() {
        return reviewBoardApprovalDate;
    }
    /**
     * @param reviewBoardApprovalDate the reviewBoardApprovalDate to set
     */
    public void setReviewBoardApprovalDate(Timestamp reviewBoardApprovalDate) {
        this.reviewBoardApprovalDate = reviewBoardApprovalDate;
    }
    /**
     * @return the reviewBoardApprovalStatusCode
     */
    @Column(name = "REVIEW_BOARD_APPROVAL_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public ReviewBoardApprovalStatusCode getReviewBoardApprovalStatusCode() {
        return reviewBoardApprovalStatusCode;
    }
    /**
     * @param reviewBoardApprovalStatusCode the reviewBoardApprovalStatusCode to set
     */
    public void setReviewBoardApprovalStatusCode(
            ReviewBoardApprovalStatusCode reviewBoardApprovalStatusCode) {
        this.reviewBoardApprovalStatusCode = reviewBoardApprovalStatusCode;
    }
    /**
     * @return the targetAccrualNumber
     */
    @Column(name = "TARGET_ACCRUAL_NUMBER")
    public Integer getTargetAccrualNumber() {
        return targetAccrualNumber;
    }
    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(Integer targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }
    /**
     * 
     * @return healthCareFacility
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "HEALTHCARE_FACILITY_IDENTIFIER", nullable = true, updatable = false)
    public HealthCareFacility getHealthCareFacility() {
        return healthCareFacility;
    }
    /**
     * 
     * @param healthCareFacility healthCareFacility
     */
    public void setHealthCareFacility(HealthCareFacility healthCareFacility) {
        this.healthCareFacility = healthCareFacility;
    }
    /**
     * 
     * @return researchOrganization
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "RESEARCH_ORGANIZATION_IDENTIFIER", nullable = true, updatable = false)
    public ResearchOrganization getResearchOrganization() {
        return researchOrganization;
    }
    /**
     * 
     * @param researchOrganization ResearchOrganization
     */
    public void setResearchOrganization(ResearchOrganization researchOrganization) {
        this.researchOrganization = researchOrganization;
    }
    /**
     * @return the oversightCommittee
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "OVERSIGHT_COMMITTEE_IDENTIFIER", nullable = true)
    public OversightCommittee getOversightCommittee() {
        return oversightCommittee;
    }
    /**
     * @param oversightCommittee the oversightCommittee to set
     */
    public void setOversightCommittee(OversightCommittee oversightCommittee) {
        this.oversightCommittee = oversightCommittee;
    }
    /**
     * @return the studySiteAccrualStatuses
     */
    @OneToMany(mappedBy = "studyParticipation")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudySiteAccrualStatus> getStudySiteAccrualStatuses() {
        return studySiteAccrualStatuses;
    }
    /**
     * @param studySiteAccrualStatuses the studySiteAccrualStatuses to set
     */
    public void setStudySiteAccrualStatuses(
            List<StudySiteAccrualStatus> studySiteAccrualStatuses) {
        this.studySiteAccrualStatuses = studySiteAccrualStatuses;
    }
    /**
     * @return the studyParticipationContacts
     */
    @OneToMany(mappedBy = "studyParticipation")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyParticipationContact> getStudyParticipationContacts() {
        return studyParticipationContacts;
    }
    /**
     * @param studyParticipationContacts the studyParticipationContacts to set
     */
    public void setStudyParticipationContacts(
            List<StudyParticipationContact> studyParticipationContacts) {
        this.studyParticipationContacts = studyParticipationContacts;
    }
}
