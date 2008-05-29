package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Investigator bean for managing PI.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "STUDY_INVESTIGATOR")
public class StudyInvestigator extends AbstractEntity {
    private Long id;
    private String responsibilityRoleCode;
    private Protocol protocol;
    private Investigator investigator;
    
    /**
     * set id.
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    /**
     * @return responsibilityRoleCode
     */
    @Column(name = "RESPONSIBILITY_ROLE_CODE")
    public String getResponsibilityRoleCode() {
        return responsibilityRoleCode;
    }

    /**
     * @param responsibilityRoleCode responsibilityRoleCode
     */
    public void setResponsibilityRoleCode(String responsibilityRoleCode) {
        this.responsibilityRoleCode = responsibilityRoleCode;
    }

    /**
     * @return protocol Protocol
     */
     @ManyToOne
     @JoinColumn(name = "PROTOCOL_ID", nullable = false)
     public Protocol getProtocol() {
        return protocol;
     }
     
     /**
      * @param protocol protocol
      */
     public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
     }

     /**
      * @return investigator
      */
     @ManyToOne
     @JoinColumn(name = "INVESTIGATOR_ID", nullable = false)
     public Investigator getInvestigator() {
        return investigator;
     }
     
     /**
      * @param investigator investigator
      */
     public void setInvestigator(Investigator investigator) {
        this.investigator = investigator;
     }     
}
