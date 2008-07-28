package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.NotNull;



/**
 * This class is an abstract concept that contains attributes common to all types of study documents.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
public class StudyContact extends AbstractEntity {

    private static final long serialVersionUID = 1234567890L;
    private StudyProtocol studyProtocol;
    private HealthCareProvider healthCareProvider;

    private Boolean primaryIndicator;
    private Person person;
    private List<StudyContactRole> studyContactRoles = 
        new ArrayList<StudyContactRole>();
    

    /**
     * 
     * @return studyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", updatable = false)
    @NotNull
    
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * 
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }

    /**
     * 
     * @return healthCareProvider healthCareProvider
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_PROVIDER_ID", updatable = false)
    @NotNull
    public HealthCareProvider getHealthCareProvider() {
        return healthCareProvider;
    }

    /**
     * 
     * @param healthCareProvider healthCareProvider
     */
    public void setHealthCareProvider(HealthCareProvider healthCareProvider) {
        this.healthCareProvider = healthCareProvider;
    }
    
    /**
     * 
     * @return primaryIndicator primaryIndicator
     */
    @Column(name = "PRIMARY_INDICATOR")
    public Boolean getPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * 
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(Boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }

    /**
     * 
     * @return person
     */
    
    public Person getPerson() {
        return person;
    }

    /**
     * 
     * @param person person
     */
    public void setPerson(Person person) {
        this.person = person;
    }


    /**
     * 
     * @return studyContactRoles studyContactRoles
     */
    @OneToMany(mappedBy = "studyContact")
    public List<StudyContactRole> getStudyContactRoles() {
        return studyContactRoles;
    }

    /**
     * 
     * @param studyContactRoles studyContactRoles) {
     */
    @OneToMany(mappedBy = "studyContact")
    public void setStudyContactRoles(List<StudyContactRole> studyContactRoles) {
        this.studyContactRoles = studyContactRoles;
    }
}
