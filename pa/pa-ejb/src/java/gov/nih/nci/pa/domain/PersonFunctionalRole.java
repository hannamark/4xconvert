package gov.nih.nci.pa.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Abstract class for managing Person Functional Role .
 * @author Naveen Amiruddin
 * @since 05/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@MappedSuperclass
@SuppressWarnings("PMD")
public abstract class PersonFunctionalRole extends FunctionalRole {
    
    private HealthCareProvider healthCareProvider;
    private ClinicalResearchStaff clinicalResearchStaff;

    /**
     * 
     * @return crs ClinicalResearchStaff
     */
    @ManyToOne 
    @JoinColumn(name = "CLINICAL_RESEARCH_STAFF_IDENTIFIER", updatable = false)
    public ClinicalResearchStaff getClinicalResearchStaff() {
        return clinicalResearchStaff;
    }
    /**
     * 
     * @param clinicalResearchStaff ClinicalResearchStaff
     */
    public void setClinicalResearchStaff(ClinicalResearchStaff clinicalResearchStaff) {
        this.clinicalResearchStaff = clinicalResearchStaff;
    }

    /**
     * 
     * @return healthCareProvider healthCareProvider
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_PROVIDER_IDENTIFIER", updatable = false)
    public HealthCareProvider getHealthCareProvider() {
        return healthCareProvider;
    }

    /**
     * 
     * @param healthCareProvider healthCareProvider
     */
    public void setHealthCareProvider(HealthCareProvider healthCareProvider) {
        this.healthCareProvider = healthCareProvider;
    }

}
