package gov.nih.nci.pa.domain;

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
@Table(name = "STUDY_DISEASE")
public class StudyDisease extends AbstractStudyEntity {
    private static final long serialVersionUID = 1898967890L;
    
    private Disease disease;
    private Boolean leadDiseaseIndicator;
    /**
     * @return the disease
     */
    @ManyToOne
    @JoinColumn(name = "DISEASE_ID", updatable = false)
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
     * @return the leadDiseaseIndicator
     */
    @Column(name = "LEAD_DISEASE_INDICATOR")
    public Boolean getLeadDiseaseIndicator() {
        return leadDiseaseIndicator;
    }
    /**
     * @param leadDiseaseIndicator the leadDiseaseIndicator to set
     */
    public void setLeadDiseaseIndicator(Boolean leadDiseaseIndicator) {
        this.leadDiseaseIndicator = leadDiseaseIndicator;
    }
}
