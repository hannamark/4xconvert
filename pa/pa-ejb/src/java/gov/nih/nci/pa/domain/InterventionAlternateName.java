/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactiveCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "INTERVENTION_ALTERNATE_NAME")
public class InterventionAlternateName extends AbstractEntityWithStatusCode<ActiveInactiveCode> {
    private static final long serialVersionUID = 1095667890L;

    private String name;
    private Intervention intervention;
    /**
     * @return the name
     */
    @Column(name = "NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    @NotNull
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
    @JoinColumn(name = "INTERVENTION_IDENTIFIER")
    @NotNull
    public Intervention getIntervention() {
        return intervention;
    }
    /**
     * @param intervention the intervention to set
     */
    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }
}
