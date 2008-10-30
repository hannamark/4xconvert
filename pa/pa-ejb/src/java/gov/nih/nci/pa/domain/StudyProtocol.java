package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;

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
    private String acronym;
    private AccrualReportingMethodCode accrualReportingMethodCode;
    private Boolean expandedAccessIndicator;
    private String identifier; // used to store nci-accession number
    private Boolean dataMonitoringCommitteeAppointedIndicator;
    private MonitorCode monitorCode;
    private String officialTitle;
    private PhaseCode phaseCode;
    private Timestamp primaryCompletionDate;
    private ActualAnticipatedTypeCode primaryCompletionDateTypeCode;
    private Timestamp startDate;
    private ActualAnticipatedTypeCode startDateTypeCode;
    private PrimaryPurposeCode primaryPurposeCode;
    private Boolean indIdeIndicator;
    private List<StudyOverallStatus> studyOverallStatuses = new ArrayList<StudyOverallStatus>();
    private List<DocumentWorkflowStatus> documentWorkflowStatuses = new ArrayList<DocumentWorkflowStatus>();
    private List<StudyParticipation> studyParticipations = new ArrayList<StudyParticipation>();
    private List<StudyContact> studyContacts = new ArrayList<StudyContact>();
    private List<StudyResourcing> studyResourcings = new ArrayList<StudyResourcing>();
    private List<PlannedActivity> plannedActivities = new ArrayList<PlannedActivity>();
    private String primaryPurposeOtherText;
    private String phaseOtherText;
    private Integer maximumTargetAccrualNumber;
    private StudyClassificationCode studyClassificationCode;

    /**
     * 
     * @return acronym
     */
    public String getAcronym() {
        return acronym;
    }

    /**
     * 
     * @param acronym acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    /**
     * 
     * @return accrualReportingMethodCode
     */
    @Column(name = "ACCR_REPT_METH_CODE")
    @Enumerated(EnumType.STRING)
    public AccrualReportingMethodCode getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }

    /**
     * 
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(AccrualReportingMethodCode accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
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
     * @return identifier
     */
    @Column(name = "NCI_IDENTIFIER")
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
     * @return monitorCode
     */
    @Column(name = "MONITOR_CODE")
    @Enumerated(EnumType.STRING)
    public MonitorCode getMonitorCode() {
        return monitorCode;
    }

    /**
     * 
     * @param monitorCode monitorCode
     */
    public void setMonitorCode(MonitorCode monitorCode) {
        this.monitorCode = monitorCode;
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
     * @return primaryCompletionDate
     */
    @Column(name = "PRI_COMPL_DATE")
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
     * @return the indIdeIndicator
     */
    @Column(name = "ind_ide_indicator")
    public Boolean getIndIdeIndicator() {
        return indIdeIndicator;
    }

    /**
     * @param indIdeIndicator the indIdeIndicator to set
     */
    public void setIndIdeIndicator(Boolean indIdeIndicator) {
        this.indIdeIndicator = indIdeIndicator;
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
     * @return maximumTargetAccrualNumber
     */
    @Column(name = "MAXIMUM_TARGET_ACCRUAL_NUMBER")
    public Integer getMaximumTargetAccrualNumber() {
        return maximumTargetAccrualNumber;
    }

    /**
     * @param maximumTargetAccrualNumber maximumTargetAccrualNumber
     */
    public void setMaximumTargetAccrualNumber(Integer maximumTargetAccrualNumber) {
        this.maximumTargetAccrualNumber = maximumTargetAccrualNumber;
    }

    /**
     * @return studyClassificationCode
     */
    @Column(name = "STUDY_CLASSIFICATION_CODE")
    @Enumerated(EnumType.STRING)
    public StudyClassificationCode getStudyClassificationCode() {
        return studyClassificationCode;
    }

    /**
     * @param studyClassificationCode studyClassificationCode
     */
    public void setStudyClassificationCode(
            StudyClassificationCode studyClassificationCode) {
        this.studyClassificationCode = studyClassificationCode;
    }
}
