package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyStatusCode;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * Protocol bean for managing protocol.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Entity
@Table(name = "PROTOCOL_STATUS")

public class ProtocolStatus extends AbstractEntity {

    private Long id;
    private StudyStatusCode studyStatusCode;
    private Date studyStatusDate;
    private Protocol protocol;
    
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
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public StudyStatusCode getStudyStatusCode() {
        return studyStatusCode;
    }
    /**
     * 
     * @param studyStatusCode status code
     */
    public void setStudyStatusCode(StudyStatusCode studyStatusCode) {
       this.studyStatusCode = studyStatusCode;
    }

    /**
     * 
     * @return statusDate
     */
    @Column(name = "STATUS_DATE")
    public Date getStudyStatusDate() {
        return studyStatusDate;
    }
    
    /**
     * 
     * @param studyStatusDate status Date
     */
    public void setStudyStatusDate(Date studyStatusDate) {
        this.studyStatusDate = studyStatusDate;
    }

    /**
     * 
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "PROTOCOL_ID", updatable = false)
    @NotNull
    public Protocol getProtocol() {
       return protocol;
    }
    /**
     * 
     * @param protocol Protocol
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

}
