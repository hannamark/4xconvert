package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.YesNoCode;

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

/**
 * Study Condition class.  
 * @author Naveen Amiruddin
 * @since 07/01/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_CONDITIONS")
/*
 * table name is study_conditions as a consistency in relations to conditions 
 * data base word
 */
public class StudyCondition extends AbstractEntity {

    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private YesNoCode leadIndicator;
    private Protocol protocol;
    private Condition condition;

    /**
     * @return id 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    public Long getId() {
        return id;
    }
    /**
     * 
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 
     * @return leadIndicator
     */
    @Column(name = "LEAD_INDICATOR")
    @Enumerated(EnumType.STRING)
    public YesNoCode getLeadIndicator() {
        return leadIndicator;
    }
    
    /**
     * 
     * @param leadIndicator leadIndicator
     */
    public void setLeadIndicator(YesNoCode leadIndicator) {
        this.leadIndicator = leadIndicator;
    }
    /**
     * 
     * @return protocol 
     */
    @ManyToOne
    @JoinColumn(name = "PROTOCOL_ID", nullable = false)
    public Protocol getProtocol() {
        return protocol;
    }
    /**
     * 
     * @param protocol protocol 
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    /**
     * 
     * @return condition
     */
    @ManyToOne
    @JoinColumn(name = "CONDITIONS_ID", nullable = false)
    public Condition getCondition() {
        return condition;
    }
    /**
     * 
     * @param condition condition
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

}
