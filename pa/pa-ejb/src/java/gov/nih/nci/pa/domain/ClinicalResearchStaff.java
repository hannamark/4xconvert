package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;


/**
 * Individuals who are employed and/or involved in any aspect of clinical research.
 * For example, administrators, clinical and data managers, clinical research 
 * pharmacists, clinical research associates, clinical trials compliance coordinators, 
 * clinical trials specialists, laboratory technologists, nurses, research services 
 * consultants, study coordinators and others.
 * 
 * @author Naveen Amiruddin
 * @since 07/24/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without \km, the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "CLINICAL_RESEARCH_STAFF")
public class ClinicalResearchStaff  extends StructuralRole {
    private static final long serialVersionUID = 1234567890L;
    
    private String identifier;
    private Person person;
    private Organization organization;
    private List<StudyContact> studyContacts = new ArrayList<StudyContact>();
    private List<StudyParticipationContact> studyPartContacts = new ArrayList<StudyParticipationContact>();

    /**
     * @return the identifier
     */
    @Column(name = "identifier")
    @NotNull
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    
    /**
     * 
     * @return person person
     */
    @ManyToOne
    @JoinColumn(name = "PERSON_ID", updatable = false)
    @NotNull
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
     * @return person person
     */
    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", updatable = false)
    @NotNull
    public Organization getOrganization() {
        return organization;
    }

    
    /**
     * 
     * @param organization o
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    /**
     * 
     * @return studyContacts studyContacts
     */
    @OneToMany(mappedBy = "healthCareProvider")
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
     * @return the studyPartContacts
     */
    @OneToMany(mappedBy = "healthCareProvider")
    public List<StudyParticipationContact> getStudyPartContacts() {
        return studyPartContacts;
    }
    /**
     * @param studyPartContacts the studyPartContacts to set
     */
    public void setStudyPartContacts(List<StudyParticipationContact> studyPartContacts) {
        this.studyPartContacts = studyPartContacts;
    }
}
