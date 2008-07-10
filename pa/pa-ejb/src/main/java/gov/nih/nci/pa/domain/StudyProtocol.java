package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name =  "STUDY_PROTOCOL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue(value = "SP")
public class StudyProtocol extends Document {
    private static final long serialVersionUID = 1234567890L;
    
    private String acronym;
    private MonitorCode monitorCode;
    private PhaseCode phaseCode;
    private List<StudyCondition> studyConditions = new ArrayList<StudyCondition>();
    
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
    
}
