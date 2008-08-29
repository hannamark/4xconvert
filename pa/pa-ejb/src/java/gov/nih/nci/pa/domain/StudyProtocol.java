package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.ControlConcurrencyTypeCode;
import gov.nih.nci.pa.enums.ControlTypeCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * An action plan and execution of a pre-clinical or clinical study including all
 * activities to test a particular hypothesis that is the basis of the study regarding the
 * effectiveness of a particular treatment, drug, device, procedure, or care plan.
 * This includes prevention, observational, therapeutic, and other types of studies
 * that involve subjects.
 *
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@SuppressWarnings({"PMD.TooManyFields", "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength" })
@Table(name =  "STUDY_PROTOCOL")
public class StudyProtocol extends Document {
    private static final long serialVersionUID = 1234567890L;

    private String acronym;
    private MonitorCode monitorCode;
    private PhaseCode phaseCode;
    private AllocationCode allocationCode;
    private ControlConcurrencyTypeCode controlConcurrencyTypeCode;
    private ControlTypeCode controlTypeCode;
    private Boolean delayedpostingIndicator;
    private DesignConfigurationCode designConfigurationCode;
    private Boolean fdaRegulatedIndicator;
    private InterventionTypeCode interventionTypeCode;
    private Integer numberOfInterventionGroups;
    private Boolean section801Indicator;
    private PrimaryPurposeCode primaryPurposeCode;
    private String biospecimenDescription;
    private BiospecimenRetentionCode biospecimenRetentionCode;
    private AccrualReportingMethodCode accrualReportingMethodCode;
    private SummaryFourFundingCategoryCode summaryFourFundingCategoryCode;

    /** Number of study groups/cohorts. Enter 1 for a single-group study. Many observational
     *  studies have one group/cohort; case control studies typically have two* */
    private Integer groupNumber;
    //@todo below two to be changed to enum, once we know the values
    private String studyModelCode;
    private String timePerspectiveCode;
    private Boolean indIndeIndicator;
    private Boolean dataMonitoringCommitteeIndicator;
    private Timestamp startDate;
    private Boolean startDateAnticipatedIndicator;
    private Timestamp completionDate;
    private Boolean completionDateAnticipatedIndicator;


    private List<StudyCondition> studyConditions = new ArrayList<StudyCondition>();
    private List<StudyOverallStatus> studyOverallStatuses = new ArrayList<StudyOverallStatus>();
    private List<DocumentWorkflowStatus> documentWorkflowStatuses =
                        new ArrayList<DocumentWorkflowStatus>();
    private List<StudyCoordinatingCenter> studyCoordinatingCenters  =
                        new ArrayList<StudyCoordinatingCenter>();
    private List<StudyContact> studyContacts  = new ArrayList<StudyContact>();
    private List<StudySite> studySites = new ArrayList<StudySite>();

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
     * @return allocationCode
     */
    @Column(name = "ALLOCATION_CODE")
    @Enumerated(EnumType.STRING)
    public AllocationCode getAllocationCode() {
        return allocationCode;
    }
    /**
     *
     * @param allocationCode allocation Code
     */
    public void setAllocationCode(AllocationCode allocationCode) {
        this.allocationCode = allocationCode;
    }

    /**
     *
     * @return ControlConcurrencyTypeCode
     */
    @Column(name = "CONTROL_CONCURRENCY_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ControlConcurrencyTypeCode getControlConcurrencyTypeCode() {
        return controlConcurrencyTypeCode;
    }

    /**
     *
     * @param controlConcurrencyTypeCode control ConcurrencyType Code
     */
    public void setControlConcurrencyTypeCode(
            ControlConcurrencyTypeCode controlConcurrencyTypeCode) {
        this.controlConcurrencyTypeCode = controlConcurrencyTypeCode;
    }
    /**
     *
     * @return ControlTypeCode
     */
    @Column(name = "CONTROL_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ControlTypeCode getControlTypeCode() {
        return controlTypeCode;
    }
    /**
     *
     * @param controlTypeCode controlType Code
     */
    public void setControlTypeCode(ControlTypeCode controlTypeCode) {
        this.controlTypeCode = controlTypeCode;
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
     * @return DesignConfigurationCode
     */
    @Column(name = "DESIGN_CONFIGURATION_CODE")
    @Enumerated(EnumType.STRING)
    public DesignConfigurationCode getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /**
     *
     * @param designConfigurationCode designConfiguration Code
     */
    public void setDesignConfigurationCode(
            DesignConfigurationCode designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
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
     *
     * @return InterventionTypeCode
     */
    @Column(name = "INTERVENTION_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public InterventionTypeCode getInterventionTypeCode() {
        return interventionTypeCode;
    }
    /**
     *
     * @param interventionTypeCode interventionType Code
     */
    public void setInterventionTypeCode(InterventionTypeCode interventionTypeCode) {
        this.interventionTypeCode = interventionTypeCode;
    }

    /**
     *
     * @return numberOfInterventionGroups
     */
    @Column(name = "NUMBER_OF_INTERVENTION_GROUPS")
    public Integer getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }

    /**
     *
     * @param numberOfInterventionGroups numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(Integer numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
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
     * @return biospecimenDescription
     */
    @Column(name = "BIOSPECIMEN_DESCRIPTION")
    public String getBiospecimenDescription() {
        return biospecimenDescription;
    }
    /**
     *
     * @param biospecimenDescription biospecimenDescription
     */
    public void setBiospecimenDescription(String biospecimenDescription) {
        this.biospecimenDescription = biospecimenDescription;
    }
    /**
     *
     * @return biospecimenRetentionCode
     */
    @Column(name = "BIOSPECIMEN_RETENTION_CODE")
    @Enumerated(EnumType.STRING)
    public BiospecimenRetentionCode getBiospecimenRetentionCode() {
        return biospecimenRetentionCode;
    }
    /**
     *
     * @param biospecimenRetentionCode biospecimenRetentionCode
     */
    public void setBiospecimenRetentionCode(
            BiospecimenRetentionCode biospecimenRetentionCode) {
        this.biospecimenRetentionCode = biospecimenRetentionCode;
    }


    /**
     * 
     * @return accrualReportingMethodCode
     */
    @Column(name = "REPORTING_DATASET_METHOD_CODE")
    @Enumerated(EnumType.STRING)
    public AccrualReportingMethodCode getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    /**
     * 
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(
            AccrualReportingMethodCode accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }

    

    
    /**
     *
     * @return SummaryFourFundingCategoryCode summaryFourFundingCategoryCode
     */
    @Column(name = "SUMMARY_FOUR_FUNDING_CATEGORY_CODE")
    @Enumerated(EnumType.STRING)
    public SummaryFourFundingCategoryCode getSummaryFourFundingCategoryCode() {
      return summaryFourFundingCategoryCode;
    }
    /**
     *
     * @param summaryFourFundingCategoryCode summaryFourFundingCategoryCode
     */
    public void setSummaryFourFundingCategoryCode(
      SummaryFourFundingCategoryCode summaryFourFundingCategoryCode) {
          this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }

    /**
     *
     * @return groupNumber
     */
    @Column(name = "GROUP_NUMBER")
    public Integer getGroupNumber() {
        return groupNumber;
    }
    /**
     *
     * @param groupNumber Number of study groups/cohorts.
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }
    /**
     *
     * @return studyModelCode
     */
    @Column(name = "STUDY_MODEL_CODE")
    public String getStudyModelCode() {
        return studyModelCode;
    }
    /**
     *
     * @param studyModelCode primary strategy for subject identification and follow-up
     */
    public void setStudyModelCode(String studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     *
     * @return timePerspectiveCode
     */
    @Column(name = "TIME_PRESPECTIVE_CODE")
    public String getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     *
     * @param timePerspectiveCode temporal relationship of observation period to time of subject enrollment
     */
    public void setTimePerspectiveCode(String timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }

    /**
     *
     * @return studyConditions
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudyCondition> getStudyConditions() {
        return studyConditions;
    }

    /**
     *
     * @param studyConditions studyConditions
     */
    public void setStudyConditions(List<StudyCondition> studyConditions) {
        this.studyConditions = studyConditions;
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
    public void setStudyOverallStatuses(
            List<StudyOverallStatus> studyOverallStatuses) {
        this.studyOverallStatuses = studyOverallStatuses;
    }
    /**
     *
     * @return documentWorkflowStatuses documentWorkflowStatuses
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<DocumentWorkflowStatus> getDocumentWorkflowStatuses() {
        return documentWorkflowStatuses;
    }
    /**
     *
     * @param documentWorkflowStatuses documentWorkflowStatuses
     */
    public void setDocumentWorkflowStatuses(
            List<DocumentWorkflowStatus> documentWorkflowStatuses) {
        this.documentWorkflowStatuses = documentWorkflowStatuses;
    }

    /**
     *
     * @return studyCoordinatingCenters
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudyCoordinatingCenter> getStudyCoordinatingCenters() {
        return studyCoordinatingCenters;
    }
    /**
     *
     * @param studyCoordinatingCenters studyCoordinatingCenters
     */
    public void setStudyCoordinatingCenters(
            List<StudyCoordinatingCenter> studyCoordinatingCenters) {
        this.studyCoordinatingCenters = studyCoordinatingCenters;
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
     * @return studySites
     */
    @OneToMany(mappedBy = "studyProtocol")
    public List<StudySite> getStudySites() {
        return studySites;
    }
    /**
     *
     * @param studySites studySites
     */
    public void setStudySites(List<StudySite> studySites) {
        this.studySites = studySites;
    }
    /**
     * @return the indIndeIndicator
     */
    @Column(name = "ide_ind_indicator")
    public Boolean getIndIndeIndicator() {
        return indIndeIndicator;
    }
    /**
     * @param indIndeIndicator the indIndeIndicator to set
     */
    public void setIndIndeIndicator(Boolean indIndeIndicator) {
        this.indIndeIndicator = indIndeIndicator;
    }
    /**
     * @return the dataMonitoringCommitteeIndicator
     */
    @Column(name = "data_mon_comittee_indicator")
    public Boolean getDataMonitoringCommitteeIndicator() {
        return dataMonitoringCommitteeIndicator;
    }
    /**
     * @param dataMonitoringCommitteeIndicator the dataMonitoringCommitteeIndicator to set
     */
    public void setDataMonitoringCommitteeIndicator(Boolean dataMonitoringCommitteeIndicator) {
        this.dataMonitoringCommitteeIndicator = dataMonitoringCommitteeIndicator;
    }
    
    /**
     * 
     * @return startDate
     */
    @Column(name = "START_DATE")
    public Timestamp  getStartDate() {
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
    * @return startDateAnticipatedIndicator
    */
    @Column(name = "START_DATE_ANTICIPATED_INDICATOR")
    public Boolean getStartDateAnticipatedIndicator() {
        return startDateAnticipatedIndicator;
    }
    /**
     *
     * @param startDateAnticipatedIndicator anticipated/acutal indicator
     */
    public void setStartDateAnticipatedIndicator(Boolean startDateAnticipatedIndicator) {
        this.startDateAnticipatedIndicator = startDateAnticipatedIndicator;
    }

    /**
     * 
     * @return completionDate
     */
    @Column(name = "COMPLETION_DATE")
    public Timestamp  getCompletionDate() {
        return completionDate;
    }
    
    /**
     * 
     * @param completionDate start Date
     */
    public void setCompletionDate(Timestamp completionDate) {
        this.completionDate = completionDate;
    }
    
    /**
    *
    * @return completionDateAnticipatedIndicator
    */
    @Column(name = "COMPLETION_DATE_ANTICIPATED_INDICATOR")
    public Boolean getCompletionDateAnticipatedIndicator() {
        return completionDateAnticipatedIndicator;
    }
    
    /**
     *
     * @param completionDateAnticipatedIndicator anticipated/acutal indicator
     */
    public void setCompletionDateAnticipatedIndicator(Boolean completionDateAnticipatedIndicator) {
        this.completionDateAnticipatedIndicator = completionDateAnticipatedIndicator;
    }
    
}
