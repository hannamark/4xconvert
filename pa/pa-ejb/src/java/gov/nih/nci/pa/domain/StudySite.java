package gov.nih.nci.pa.domain;


import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * StudySite bean for managing StudySite.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_SITE")

public class StudySite extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;

    private StudyProtocol studyProtocol;
    private Organization organization;
    private Boolean leadOrganizationIndicator; 


    /**
    * @return studyProtocol 
    */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", nullable = false)
    public StudyProtocol getStudyProtocol() {
       return studyProtocol;
    }
    
    /**
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
       this.studyProtocol = studyProtocol;
    }

    /**
     * 
     * @return leadOrganizationIndicator
     */
    @Column(name = "LEAD_ORGANIZATION_INDICATOR")
    @NotNull
    public Boolean getLeadOrganizationIndicator() {
        return leadOrganizationIndicator;
    }

    /**
     * 
     * @param leadOrganizationIndicator leadOrganizationIndicator
     */
    public void setLeadOrganizationIndicator(Boolean leadOrganizationIndicator) {
        this.leadOrganizationIndicator = leadOrganizationIndicator;
    }

    /**
     * @return organization
     */
    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", nullable = false)
    public Organization getOrganization() {
       return organization;
    }
    
    /**
     * @param organization organization
     */
    public void setOrganization(Organization organization) {
       this.organization = organization;
    }
}
