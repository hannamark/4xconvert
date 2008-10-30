/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactiveCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "INTERVENTION_ALTERNATE_NAME")
public class InterventionAlternateName extends AbstractEntity {
    private static final long serialVersionUID = 1095667890L;

    private String name;
    private Intervention intervention;
    private ActiveInactiveCode statusCode;
    private Timestamp statusDateRangeLow;
    /**
     * @return the name
     */
    @Column(name = "NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the intervention
     */
    @ManyToOne
    @JoinColumn(name = "INTERVENTION_ID")
    public Intervention getIntervention() {
        return intervention;
    }
    /**
     * @param intervention the intervention to set
     */
    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }
    /**
     * @return the statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public ActiveInactiveCode getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(ActiveInactiveCode statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDateRangeLow
     */
    @Column(name = "STATUS_DATE_RANGE_LOW")
    public Timestamp getStatusDateRangeLow() {
        return statusDateRangeLow;
    }
    /**
     * @param statusDateRangeLow the statusDateRangeLow to set
     */
    public void setStatusDateRangeLow(Timestamp statusDateRangeLow) {
        this.statusDateRangeLow = statusDateRangeLow;
    }
}
