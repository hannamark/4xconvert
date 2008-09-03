package gov.nih.nci.pa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Protocol bean for managing protocol.
 * @author Naveen Amiruddin
 * @since 05/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@MappedSuperclass
public  class AbstractEntity implements Serializable, Auditable {
    
    private static final long serialVersionUID = 1234567890L;
    
    private Long id;
    private Date dateLastUpdated;
    private String userLastUpdated;
    
    /**
     * Standard max length for text columns.
     */
    public static final int LONG_TEXT_LENGTH = 200;
    
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
    public Long getId() {
        return this.id;
    }
    
    /**
     * 
     * @return statusDateRangeLow
     */
    @Column(name = "DATE_LAST_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateLastUpdated() {
        return this.dateLastUpdated;
    }

    /**
     * @param dateLastUpdated last updated date
     */
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    /**
     * 
     * @return userLastUpdated
     */
    @Column(name = "USER_LAST_UPDATED")
    public String getUserLastUpdated() {
        return userLastUpdated;
    }

    /**
     * 
     * @param userLastUpdated userLastUpdated
     */
    public void setUserLastUpdated(String userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }
    
    
}


