package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.NotNull;

/**
 * An action plan and execution of a pre-clinical or clinical study including
 * all activities to test a particular hypothesis that is the basis of the study
 * regarding the effectiveness of a particular treatment, drug, device,
 * procedure, or care plan. This includes prevention, observational,
 * therapeutic, and other types of studies that involve subjects.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Study_Protocol_type", discriminatorType = DiscriminatorType.STRING)
@SuppressWarnings({ "PMD.TooManyFields", "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength" })
@Table(name = "STUDY_PROTOCOL")
public class StudyProtocol extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;

    private AccrualReportingMethodCode accrualReportingMethodCode;
    private String acronym;
    private Boolean dataMonitoringCommitteeAppointedIndicator;
    private Boolean delayedpostingIndicator;
    private Boolean expandedAccessIndicator;
    private Boolean fdaRegulatedIndicator;
    private Boolean reviewBoardApprovalRequiredIndicator;
    private String identifier; // used to store nci-accession number
    private String keywordText;
    private Integer maximumTargetAccrualNumber;
    private String officialTitle;
    private PhaseCode phaseCode;
    private String phaseOtherText;
    private Timestamp primaryCompletionDate;
    private ActualAnticipatedTypeCode primaryCompletionDateTypeCode;
    private PrimaryPurposeCode primaryPurposeCode;
    private String primaryPurposeOtherText;
    private String publicDescription;
    private String publicTitle;
    private Timestamp recordVerificationDate;
    private String scientificDescription;
    private Boolean section801Indicator;
    private Timestamp startDate;
    private ActualAnticipatedTypeCode startDateTypeCode;
    private Boolean acceptHealthyVolunteersIndicator;
    
    private List<StudyOverallStatus> studyOverallStatuses = new ArrayList<StudyOverallStatus>();
    private List<DocumentWorkflowStatus> documentWorkflowStatuses = new ArrayList<DocumentWorkflowStatus>();
    private List<StudyParticipation> studyParticipations = new ArrayList<StudyParticipation>();
    private List<StudyContact> studyContacts = new ArrayList<StudyContact>();
    private List<StudyResourcing> studyResourcings = new ArrayList<StudyResourcing>();
    private List<PlannedActivity> plannedActivities = new ArrayList<PlannedActivity>();
    private List<Arm> arms = new ArrayList<Arm>();
    private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();

    /** 
     * @return accrualReportingMethodCode
     */
    @Column(name = "ACCR_REPT_METH_CODE")
    @Enumerated(EnumType.STRING)
    public AccrualReportingMethodCode getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    /**
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(AccrualReportingMethodCode accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }
    /**
     * @return acronym
     */
    public String getAcronym() {
        return acronym;
    }
    /**
     * @param acronym acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    
    /**
     * 
     * @return dataMonitoringCommitteeAppointedIndicator
     */
    @Column(name = "DATA_MONTY_COMTY_APPTN_INDICATOR")
    public Boolean getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * 
     * @param dataMonitoringCommitteeAppointedIndicator ind
     */
    public void setDataMonitoringCommitteeAppointedIndicator(Boolean dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }

    /**
    *
    * @return delayedpostingIndicator
    */
    @Column(name = "DELAYED_POSTING_INDICATOR")
    public Boolean getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }
    /**
    *
    * @param delayedpostingIndicator delayedposting Indicator
    */
    public void setDelayedpostingIndicator(Boolean delayedpostingIndicator) {
      this.delayedpostingIndicator = delayedpostingIndicator;
    }
    
    /**
     * 
     * @return expandedAccessIndicator expandedAccessIndicator
     */
    @Column(name = "EXPD_ACCESS_INDIDICATOR")
    public Boolean getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     * 
     * @param expandedAccessIndicator expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(Boolean expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }
    /**
    *
    * @return fdaRegulatedIndicator
    */
    @Column(name = "FDA_REGULATED_INDICATOR")
    public Boolean getFdaRegulatedIndicator() {
       return fdaRegulatedIndicator;
    }
    /**
    *
    * @param fdaRegulatedIndicator fdaRegulatedIndicator
    */
    public void setFdaRegulatedIndicator(Boolean fdaRegulatedIndicator) {
       this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }

    /**
     * @return the reviewBoardApprovalRequiredIndicator
     */
    @Column(name = "REVIEW_BRD_APPROVAL_REQ_INDICATOR")
    public Boolean getReviewBoardApprovalRequiredIndicator() {
        return reviewBoardApprovalRequiredIndicator;
    }
    /**
     * @param reviewBoardApprovalRequiredIndicator the reviewBoardApprovalRequiredIndicator to set
     */
    public void setReviewBoardApprovalRequiredIndicator(
            Boolean reviewBoardApprovalRequiredIndicator) {
        this.reviewBoardApprovalRequiredIndicator = reviewBoardApprovalRequiredIndicator;
    }
    /**
     * 
     * @return identifier
     */
    @Column(name = "ASSIGNED_IDENTIFIER" , updatable = false)
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 
     * @return keywordText
     */
    @Column(name = "KEYWORD_TEXT")
    public String getKeywordText() {
        return keywordText;
    }
    /**
     * 
     * @param keywordText keywordText
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }
    
    /**
     * 
     * @return maximumTargetAccrualNumber
     */
    @Column(name = "MAX_TARGET_ACCRUAL_NUM")
    public Integer getMaximumTargetAccrualNumber() {
        return maximumTargetAccrualNumber;
    }
    /**
     * 
     * @param maximumTargetAccrualNumber maximumTargetAccrualNumber
     */
    public void setMaximumTargetAccrualNumber(Integer maximumTargetAccrualNumber) {
        this.maximumTargetAccrualNumber = maximumTargetAccrualNumber;
    }
    /**
     * 
     * @return officialTitle
     */
    @Column(name = "OFFICIAL_TITLE")
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    
    /**
     * 
     * @return phaseCode
     */
    @Column(name = "PHASE_CODE")
    @Enumerated(EnumType.STRING)
    public PhaseCode getPhaseCode() {
        return phaseCode;
    }

    /**
     * 
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(PhaseCode phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * 
     * @return phaseOtherText
     */
    @Column(name = "PHASE_OTHER_TEXT")
    public String getPhaseOtherText() {
        return phaseOtherText;
    }

    /**
     * @param phaseOtherText phaseOtherText
     */
    public void setPhaseOtherText(String phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
    }

    /**
     * 
     * @return primaryCompletionDate
     */
    @Column(name = "PRI_COMPL_DATE")
    @NotNull
    public Timestamp getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * 
     * @param primaryCompletionDate primaryCompletionDate
     */
    public void setPrimaryCompletionDate(Timestamp primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * 
     * @return primaryCompletionDateTypeCode
     */
    @Column(name = "PRI_COMPL_DATE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    public ActualAnticipatedTypeCode getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }

    /**
     * 
     * @param primaryCompletionDateTypeCode primaryCompletionDateTypeCode
     */
    public void setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }

    /**
     * 
     * @return startDate
     */
    @Column(name = "START_DATE")
    @NotNull
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate start Date
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return startDateTypeCode
     */
    @Column(name = "START_DATE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    public ActualAnticipatedTypeCode getStartDateTypeCode() {
        return startDateTypeCode;
    }

    /**
     * 
     * @param startDateTypeCode startDateTypeCode
     */
    public void setStartDateTypeCode(ActualAnticipatedTypeCode startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }

    /**
     * 
     * @return primaryPurposeCode
     */
    @Column(name = "PRIMARY_PURPOSE_CODE")
    @Enumerated(EnumType.STRING)
    public PrimaryPurposeCode getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }

    /**
     * 
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(PrimaryPurposeCode primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }

    /**
     * 
     * @return primaryPurposeOtherText
     */
    @Column(name = "PRIMARY_PURPOSE_OTHER_TEXT")
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }

    /**
     * @param primaryPurposeOtherText primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    
    /**
     * 
     * @return publicDescription
     */
    @Column(name = "PUBLIC_DESCRIPTION")
    public String getPublicDescription() {
        return publicDescription;
    }
    /**
     * 
     * @param publicDescription publicDescription
     */
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }
    /**
     * 
     * @return publicTitle
     */
    @Column(name = "PUBLIC_TITTLE")
    public String getPublicTitle() {
        return publicTitle;
    }
    /**
     * 
     * @param publicTitle publicTitle
     */
    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }
    /**
    *
    * @return section801Indicator
    */
    @Column(name = "SECTION801_INDICATOR")
    public Boolean getSection801Indicator() {
        return section801Indicator;
    }
   /**
    *
    * @param section801Indicator section801Indicator
    */
    public void setSection801Indicator(Boolean section801Indicator) {
       this.section801Indicator = section801Indicator;
    }
    
    /**
     * 
     * @return recordVerificationDate
     */
    @Column(name = "RECORD_VERIFICATION_DATE")
    public Timestamp getRecordVerificationDate() {
        return recordVerificationDate;
    }
    /**
     * 
     * @param recordVerificationDate recordVerificationDate
     */
    public void setRecordVerificationDate(Timestamp recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }
    /**
     * 
     * @return scientificDescription
     */
    @Column(name = "SCIENTIFIC_DESCRIPTION")
    public String getScientificDescription() {
        return scientificDescription;
    }
    /**
     * 
     * @param scientificDescription scientificDescription
     */
    public void setScientificDescription(String scientificDescription) {
        this.scientificDescription = scientificDescription;
    }
    
    
    /**
     * 
     * @return studyOverallStatuses
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudyOverallStatus> getStudyOverallStatuses() {
        return studyOverallStatuses;
    }

    /**
     * 
     * @param studyOverallStatuses studyOverallStatuses
     */
    public void setStudyOverallStatuses(List<StudyOverallStatus> studyOverallStatuses) {
        this.studyOverallStatuses = studyOverallStatuses;
    }

    /**
     * 
     * @return documentWorkflowStatuses
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<DocumentWorkflowStatus> getDocumentWorkflowStatuses() {
        return documentWorkflowStatuses;
    }

    /**
     * 
     * @param documentWorkflowStatuses documentWorkflowStatuses
     */
    public void setDocumentWorkflowStatuses(List<DocumentWorkflowStatus> documentWorkflowStatuses) {
        this.documentWorkflowStatuses = documentWorkflowStatuses;
    }

    /**
     * 
     * @return studyParticipations
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudyParticipation> getStudyParticipations() {
        return studyParticipations;
    }

    /**
     * 
     * @param studyParticipations studyParticipations
     */
    public void setStudyParticipations(List<StudyParticipation> studyParticipations) {
        this.studyParticipations = studyParticipations;
    }

    /**
     * 
     * @return studyContacts
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudyContact> getStudyContacts() {
        return studyContacts;
    }

    /**
     * 
     * @param studyContacts studyContacts
     */
    public void setStudyContacts(List<StudyContact> studyContacts) {
        this.studyContacts = studyContacts;
    }

    /**
     * 
     * @return studyResourcings
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudyResourcing> getStudyResourcings() {
        return studyResourcings;
    }

    /**
     * 
     * @param studyResourcings studyResourcings
     */
    public void setStudyResourcings(List<StudyResourcing> studyResourcings) {
        this.studyResourcings = studyResourcings;
    }

    /**
     * @return the plannedActivities
     */
    @OneToMany(mappedBy = "studyProtocol")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<PlannedActivity> getPlannedActivities() {
        return plannedActivities;
    }

    /**
     * @param plannedActivities the plannedActivities to set
     */
    public void setPlannedActivities(List<PlannedActivity> plannedActivities) {
        this.plannedActivities = plannedActivities;
    }

    /**
     * @return the arms
     */
    @OneToMany(mappedBy = "studyProtocol")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Arm> getArms() {
        return arms;
    }

    /**
     * @param arms the arms to set
     */
    public void setArms(List<Arm> arms) {
        this.arms = arms;
    }
    /**
     * @return acceptHealthyVolunteersIndicator
     */
    @Column(name = "ACCEPT_HEALTHY_VOLUNTEERS_INDICATOR")
    public Boolean getAcceptHealthyVolunteersIndicator() {
        return acceptHealthyVolunteersIndicator;
    }
    /**
     * @param acceptHealthyVolunteersIndicator acceptHealthyVolunteersIndicator
     */
    public void setAcceptHealthyVolunteersIndicator(
            Boolean acceptHealthyVolunteersIndicator) {
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
    }
    /**
     * @return the studyDiseases
     */
    @OneToMany(mappedBy = "studyProtocol")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyDisease> getStudyDiseases() {
        return studyDiseases;
    }
    /**
     * @param studyDiseases the studyDiseases to set
     */
    public void setStudyDiseases(List<StudyDisease> studyDiseases) {
        this.studyDiseases = studyDiseases;
    }
}

