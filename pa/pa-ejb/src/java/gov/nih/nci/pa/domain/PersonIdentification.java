package gov.nih.nci.pa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 *  The unique identification of a person in a specified context. 
 * 
 * @author Naveen Amiruddin
 * @since 07/23/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "PERSON_IDENTIFICATION")
public class PersonIdentification extends Identification {
    private static final long serialVersionUID = 1234567890L;
    private Person person;
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
}
