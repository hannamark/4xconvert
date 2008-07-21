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
 * StudySite bean for managing StudySite.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "STUDY_SITE")

public class StudySite extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private String leadOrganizationProtocolId;
    private StudyProtocol protocol;
    private Organization healtcareSite;

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
     * 
     * @return localProtocolIdentifer Local StudyProtocol Identifier
     */
    @Column(name = "LOCAL_PROTOCOL_IDENTIFIER", length = LONG_TEXT_LENGTH)
    public String getLeadOrganizationProtocolId() {
        return leadOrganizationProtocolId;
    }

    /**
     * set leadOrganizationProtocolId.
     * @param leadOrganizationProtocolId leadOrganizationProtocolId
     */
    public void setLeadOrganizationProtocolId(String leadOrganizationProtocolId) {
        this.leadOrganizationProtocolId = leadOrganizationProtocolId;
    }

    /**
    * @return protocol StudyProtocol
    */
    @ManyToOne
    @JoinColumn(name = "PROTOCOL_ID", nullable = false)
    public StudyProtocol getProtocol() {
       return protocol;
    }
    
    /**
     * @param protocol protocol
     */
    public void setProtocol(StudyProtocol protocol) {
       this.protocol = protocol;
    }

    /**
     * @return healtcareSite
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_SITE_ID", nullable = false)
    public Organization getHealtcareSite() {
       return healtcareSite;
    }
    
    /**
     * @param healtcareSite healtcareSite
     */
    public void setHealtcareSite(Organization healtcareSite) {
       this.healtcareSite = healtcareSite;
    }
}
