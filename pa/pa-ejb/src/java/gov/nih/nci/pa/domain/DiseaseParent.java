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
@Table(name = "DISEASE_PARENT")
public class DiseaseParent extends AbstractEntityWithStatusCode<ActiveInactiveCode> {
    private static final long serialVersionUID = 1255557890L;
    
    private Disease disease;
    private Disease parentDisease;
    private String parentDiseaseCode;
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
    /**
     * @return the parentDisease
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_DISEASE_IDENTIFIER", updatable = false)
    @NotNull
    public Disease getParentDisease() {
        return parentDisease;
    }
    /**
     * @param parentDisease the parentDisease to set
     */
    public void setParentDisease(Disease parentDisease) {
        this.parentDisease = parentDisease;
    }
    /**
     * @return the parentDiseaseCode
     */
    @Column(name = "PARENT_DISEASE_CODE")
    public String getParentDiseaseCode() {
        return parentDiseaseCode;
    }
    /**
     * @param parentDiseaseCode the parentDiseaseCode to set
     */
    public void setParentDiseaseCode(String parentDiseaseCode) {
        this.parentDiseaseCode = parentDiseaseCode;
    }
}
