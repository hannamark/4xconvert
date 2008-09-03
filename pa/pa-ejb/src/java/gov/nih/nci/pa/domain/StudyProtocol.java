package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
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
public class StudyProtocol extends  AbstractEntity {
    private static final long serialVersionUID = 1234567890L;

    private String acronym;
    private AllocationCode allocationCode;
    private AccrualReportingMethodCode accrualReportingMethodCode;
    private Boolean expandedAccessIndicator;
    private String identifier; // used to store nci-accession number
    private MonitorCode monitorCode;
    private String officialTitle;
    private PhaseCode phaseCode;
    private Timestamp primaryCompletionDate;
    private ActualAnticipatedTypeCode primaryCompletionDateTypeCode;
    private Timestamp startDate;
    private ActualAnticipatedTypeCode startDateTypeCode;
    
    private List<StudyOverallStatus> studyOverallStatuses = new ArrayList<StudyOverallStatus>();
    
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
    public void setAccrualReportingMethodCode(
           AccrualReportingMethodCode accrualReportingMethodCode) {
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
    public void setPrimaryCompletionDateTypeCode(
            ActualAnticipatedTypeCode primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
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
    
}
