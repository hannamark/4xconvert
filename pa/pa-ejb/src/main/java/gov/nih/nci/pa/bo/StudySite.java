package gov.nih.nci.pa.bo;

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

    private Long id;
    private String localProtocolIdentifier;
    private Protocol protocol;
    private HealthcareSite healtcareSite;

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
     * @return localProtocolIdentifer Local Protocol Identifier
     */
    @Column(name = "LOCAL_PROTOCOL_IDENTIFIER", length = LONG_TEXT_LENGTH)
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }

    /**
     * set protocolIdentifier.
     * @param localProtocolIdentifier localProtocolIdentifier
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
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
     * @return healtcareSite
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_SITE_ID", nullable = false)
    public HealthcareSite getHealtcareSite() {
       return healtcareSite;
    }
    
    /**
     * @param healtcareSite healtcareSite
     */
    public void setHealtcareSite(HealthcareSite healtcareSite) {
       this.healtcareSite = healtcareSite;
    }
}
