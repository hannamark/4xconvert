package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Int;


/**
 * StudyProtocolDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 09/04/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class InterventionalStudyProtocolDTO extends StudyProtocolDTO {

    Cd allocationCode;
    DSet blindedRoleCode;
    Cd blindingSchemaCode;
    Cd designConfigurationCode;
    Int numberOfInterventionGroups;    
    Cd studyClassificationCode;
    
    /**
     * @return allocationCode
     */
    public Cd getAllocationCode() {
        return allocationCode;
    }
    /**
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(Cd allocationCode) {
        this.allocationCode = allocationCode;
    }

    /**
     * 
     * @return blindedRoleCode
     */
    public DSet getBlindedRoleCode() {
        return blindedRoleCode;
    }
    /**
     * 
     * @param blindedRoleCode blindedRoleCode
     */
    public void setBlindedRoleCode(DSet blindedRoleCode) {
        this.blindedRoleCode = blindedRoleCode;
    }
    /**
     * @return blindingSchemaCode
     */
    public Cd getBlindingSchemaCode() {
        return blindingSchemaCode;
    }
    /**
     * @param blindingSchemaCode blindingSchemaCode
     */
    public void setBlindingSchemaCode(Cd blindingSchemaCode) {
        this.blindingSchemaCode = blindingSchemaCode;
    }
    /**
     * @return designConfigurationCode
     */
    public Cd getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /** 
     * @param designConfigurationCode designConfigurationCode
     */
    public void setDesignConfigurationCode(Cd designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }
        
    /**
     * @return numberOfInterventionGroups
     */
    public Int getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }
    /**
     * @param numberOfInterventionGroups numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(Int numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }
    /**
     * 
     * @return studyClassificationCode
     */
    public Cd getStudyClassificationCode() {
        return studyClassificationCode;
    }
    /**
     * 
     * @param studyClassificationCode studyClassificationCodeR
     */
    public void setStudyClassificationCode(Cd studyClassificationCode) {
        this.studyClassificationCode = studyClassificationCode;
    }
    
    
}
