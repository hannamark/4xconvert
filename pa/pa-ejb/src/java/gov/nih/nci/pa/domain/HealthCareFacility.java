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
 * An organization that devotes some or all of its resources (people, places, things) to the 
 * delivery of healthcare services (including the financial and administrative management of those resources).  
 * A healthcare facility may be manifest as a single physical location (e.g. building), or, 
 * alternatively, as a distributed collection of physical spaces.. 
 * 
 * @author Naveen Amiruddin
 * @since 07/24/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */

@Entity
@Table(name = "HEALTHCARE_FACILITY")
public class HealthCareFacility  extends StructuralRole {
    
    private Organization organization;
    private String identifier;  
    private List<StudyParticipation> studyParticipations = new ArrayList<StudyParticipation>();
    
    /**
     * 
     * @return organization
     */
    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", updatable = false)
    @NotNull    
    public Organization getOrganization() {
        return organization;
    }
    /**
     * 
     * @param organization organization
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    /**
     * @return the identifier
     */
    @Column(name = "IDENTIFIER")
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
     * @return studyParticipations
     */
    @OneToMany(mappedBy = "healthCareFacility")
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
    
    
    
    

}
