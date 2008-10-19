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
 * A person who directly or indirectly administers interventions that are designed to 
 * improve the physical or emotional status of another person. A person licensed, certified or 
 * otherwise authorized or permitted by law to administer health care in the ordinary course of 
 * business or practice of a profession, including a health care facility. 
 * 
 * @author Naveen Amiruddin
 * @since 07/24/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without \km, the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "HEALTHCARE_PROVIDER")
public class HealthCareProvider extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    private Person person;
    private Long identifier;
    private List<StudyContact> studyContacts = new ArrayList<StudyContact>();
    private List<StudyParticipationContact> studyPartContacts = new ArrayList<StudyParticipationContact>();

    
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
    /**
     * @return the identifier
     */
    @Column(name = "identifier")
    public Long getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }
}
