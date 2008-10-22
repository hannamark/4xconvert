/**
 * 
 */
package gov.nih.nci.pa.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author Hugh Reinhart
 * @since 10/21/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */

@Entity
@Table(name = "RESEARCH_ORGANIZATION")
public class ResearchOrganization extends AbstractEntity {
    private static final long serialVersionUID = 1736507890L;
    
    private Organization organization;
    private String identifier;  
    private List<StudyParticipation> studyParticipations = new ArrayList<StudyParticipation>();
    
    /**
     * 
     * @return organization
     */
    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID",  nullable = false, updatable = false)
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
    @OneToMany(mappedBy = "researchOrganization")
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
