package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
/**
 * An action plan for InterventionalStudyProtocol.
 *
 * @author Naveen Amiruddin
 * @since 09/07/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Entity
@DiscriminatorColumn(
        name = "InterventionalStudyProtocol",
        discriminatorType = DiscriminatorType.STRING
    )

public class InterventionalStudyProtocol extends StudyProtocol {
    
    private AllocationCode allocationCode;
    private Boolean delayedpostingIndicator;
    private Boolean fdaRegulatedIndicator;
    private Boolean section801Indicator;
    private Integer numberOfInterventionGroups;
    private DesignConfigurationCode designConfigurationCode;
    private BlindingSchemaCode blindingSchemaCode;
    private BlindingRoleCode blindingRoleCode;
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
   
   /**
    * @return numberOfInterventionGroups
    */
    @Column(name = "NUMBEROF_INTERVENTION_GROUPS")
    public Integer getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }
   
   /**
    * @param numberOfInterventionGroups numberOfInterventionGroups
    */
    public void setNumberOfInterventionGroups(Integer numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }
    /**
    *
    * @return designConfigurationCode
    */
    @Column(name = "DESIGN_CONFIGURATION_CODE")
    @Enumerated(EnumType.STRING)
    public DesignConfigurationCode getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /**
    *
    * @param designConfigurationCode designConfigurationCode
    */
    public void setDesignConfigurationCode(
            DesignConfigurationCode designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }
    /**
    *
    * @return blindingSchemaCode
    */
    @Column(name = "BLINDING_SCHEMA_CODE")
    @Enumerated(EnumType.STRING)
    public BlindingSchemaCode getBlindingSchemaCode() {
        return blindingSchemaCode;
    }
    /**
    *
    * @param blindingSchemaCode blindingSchemaCode
    */
    public void setBlindingSchemaCode(BlindingSchemaCode blindingSchemaCode) {
        this.blindingSchemaCode = blindingSchemaCode;
    }
    /**
    *
    * @return blindingRoleCode
    */
    @Column(name = "BLINDING_ROLE_CODE")
    @Enumerated(EnumType.STRING)
    public BlindingRoleCode getBlindingRoleCode() {
        return blindingRoleCode;
    }
    /**
    *
    * @param blindingRoleCode blindingRoleCode
    */
    public void setBlindingRoleCode(BlindingRoleCode blindingRoleCode) {
        this.blindingRoleCode = blindingRoleCode;
    }
  
}
