package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;

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
import org.hibernate.validator.NotNull;

/**
 * StudySite bean for managing StudySite.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
//@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "STUDY_PARTICIPATION")

public class StudyParticipation extends OrganizationFunctionalRole {
    private static final long serialVersionUID = 1234567890L;

    private StudyParticipationFunctionalCode functionalCode;
    private String localStudyProtocolIdentifier;
    private HealthCareFacility  healthCareFacility;
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
     * 
     * @return healthCareFacility
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_FACILITY_ID", updatable = false)
    @NotNull
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
     * @return the studySiteAccrualStatuses
     */
    @OneToMany(mappedBy = "studyParticipation")
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
