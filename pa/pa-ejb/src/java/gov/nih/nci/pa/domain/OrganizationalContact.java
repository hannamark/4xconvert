package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * This person serves as a contact for the organization.
 * @author Naveen Amiruddin
 * @since 07/24/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without \km, the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "ORGANIZATIONAL_CONTACT")
public class OrganizationalContact extends StructuralRole {

    private static final long serialVersionUID = 1234567890L;
    private String identifier;
    private Person person;
    private Organization organization;

    /**
     * @return the identifier
     */
    @Column(name = "assigned_identifier")
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
    @JoinColumn(name = "PERSON_IDENTIFIER", updatable = false)
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
    @JoinColumn(name = "ORGANIZATION_IDENTIFIER", updatable = false)
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
    
}
