package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.ControlConcurrencyTypeCode;
import gov.nih.nci.pa.enums.ControlTypeCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * InterventionalStudyProtocol.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@DiscriminatorValue(value = "ISP")
public class InterventionalStudyProtocol extends StudyProtocol {
    
    private static final long serialVersionUID = 1234567890L;
    
    private AllocationCode allocationCode;
    private ControlConcurrencyTypeCode controlConcurrencyTypeCode;
    private ControlTypeCode controlTypeCode;
    private Boolean delayedpostingIndicator;
    private DesignConfigurationCode designConfigurationCode;
    private Boolean fdaRegulatedIndicator;
    private InterventionTypeCode interventionTypeCode;
    private Integer numberOfInterventionGroups;
    private Boolean section801Indicator;
    
    /**
     * 
     * @return allocationCode 
     */
    @Column(name = "ALLOCATION_CODE")
    @Enumerated(EnumType.STRING)
    public AllocationCode getAllocationCode() {
        return allocationCode;
    }
    /**
     * 
     * @param allocationCode allocation Code
     */
    public void setAllocationCode(AllocationCode allocationCode) {
        this.allocationCode = allocationCode;
    }
    
    /**
     * 
     * @return ControlConcurrencyTypeCode
     */
    @Column(name = "CONTROL_CONCURRENCY_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ControlConcurrencyTypeCode getControlConcurrencyTypeCode() {
        return controlConcurrencyTypeCode;
    }
    
    /**
     * 
     * @param controlConcurrencyTypeCode control ConcurrencyType Code
     */
    public void setControlConcurrencyTypeCode(
            ControlConcurrencyTypeCode controlConcurrencyTypeCode) {
        this.controlConcurrencyTypeCode = controlConcurrencyTypeCode;
    }
    /**
     * 
     * @return ControlTypeCode
     */
    @Column(name = "CONTROL_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ControlTypeCode getControlTypeCode() {
        return controlTypeCode;
    }
    /**
     * 
     * @param controlTypeCode controlType Code
     */
    public void setControlTypeCode(ControlTypeCode controlTypeCode) {
        this.controlTypeCode = controlTypeCode;
    }
    
    /**
     * 
     * @return delayedpostingIndicator
     */
    @Column(name = "DELAYED_POSTING_INDICATOR")
    public Boolean getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }
    /**
     * 
     * @param delayedpostingIndicator delayedposting Indicator
     */
    public void setDelayedpostingIndicator(Boolean delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
    }
    /**
     * 
     * @return DesignConfigurationCode
     */
    @Column(name = "DESIGN_CONFIGURATION_CODE")
    @Enumerated(EnumType.STRING)
    public DesignConfigurationCode getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /**
     * 
     * @param designConfigurationCode designConfiguration Code
     */
    public void setDesignConfigurationCode(
            DesignConfigurationCode designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }
    /**
     * 
     * @return fdaRegulatedIndicator
     */
    @Column(name = "FDA_REGULATED_INDICATOR")
    public Boolean getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }
    /**
     * 
     * @param fdaRegulatedIndicator fdaRegulatedIndicator
     */
    public void setFdaRegulatedIndicator(Boolean fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }
    /**
     * 
     * @return InterventionTypeCode
     */
    @Column(name = "INTERVENTION_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public InterventionTypeCode getInterventionTypeCode() {
        return interventionTypeCode;
    }
    /**
     * 
     * @param interventionTypeCode interventionType Code
     */
    public void setInterventionTypeCode(InterventionTypeCode interventionTypeCode) {
        this.interventionTypeCode = interventionTypeCode;
    }
    
    /**
     * 
     * @return numberOfInterventionGroups
     */
    @Column(name = "NUMBER_OF_INTERVENTION_GROUPS")
    public Integer getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }
    
    /**
     * 
     * @param numberOfInterventionGroups numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(Integer numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }
    
    /**
     * 
     * @return section801Indicator
     */
    @Column(name = "SECTION801_INDICATOR")
    public Boolean getSection801Indicator() {
        return section801Indicator;
    }
    /**
     * 
     * @param section801Indicator section801Indicator
     */
    public void setSection801Indicator(Boolean section801Indicator) {
        this.section801Indicator = section801Indicator;
    }
}
