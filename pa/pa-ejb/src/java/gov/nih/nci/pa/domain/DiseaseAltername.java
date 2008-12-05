package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactiveCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since 11/29/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "DISEASE_ALTERNAME")
public class DiseaseAltername extends AbstractEntityWithStatusCode<ActiveInactiveCode> {
    private static final long serialVersionUID = 1234583780L;
    
    private String alternateName;
    private Disease disease;
    
    /**
     * @return the alternateName
     */
    @Column(name = "ALTERNATE_NAME")
    public String getAlternateName() {
        return alternateName;
    }
    /**
     * @param alternateName the alternateName to set
     */
    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }
    /**
     * @return the disease
     */
    @ManyToOne
    @JoinColumn(name = "DISEASE_IDENTIFIER", updatable = false)
    @NotNull
    public Disease getDisease() {
        return disease;
    }
    /**
     * @param disease the disease to set
     */
    public void setDisease(Disease disease) {
        this.disease = disease;
    }
    
}
