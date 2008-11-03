package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;

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
 * @since 09/07/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */

@Entity
@DiscriminatorColumn(name = "InterventionalStudyProtocol", discriminatorType = DiscriminatorType.STRING)
public class InterventionalStudyProtocol extends StudyProtocol {

    private AllocationCode allocationCode;
    private BlindingRoleCode blindingRoleCodeSubject;
    private BlindingRoleCode blindingRoleCodeCaregiver;
    private BlindingRoleCode blindingRoleCodeInvestigator;
    private BlindingRoleCode blindingRoleCodeOutcome;
    private BlindingSchemaCode blindingSchemaCode;
    private DesignConfigurationCode designConfigurationCode;
    private Integer numberOfInterventionGroups;
    private StudyClassificationCode studyClassificationCode;

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
     * @param allocationCode
     *            allocation Code
     */
    public void setAllocationCode(AllocationCode allocationCode) {
        this.allocationCode = allocationCode;
    }

    /**
     * 
     * @return blindingRoleCodeSubject
     */
    @Column(name = "BLINDING_ROLE_CODE_SUBJECT")
    @Enumerated(EnumType.STRING)
    public BlindingRoleCode getBlindingRoleCodeSubject() {
        return blindingRoleCodeSubject;
    }

    /**
     * 
     * @param blindingRoleCodeSubject
     *            blindingRoleCodeSubject
     */
    public void setBlindingRoleCodeSubject(
            BlindingRoleCode blindingRoleCodeSubject) {
        this.blindingRoleCodeSubject = blindingRoleCodeSubject;
    }

    /**
     * 
     * @return blindingRoleCodeCaregiver
     */
    @Column(name = "BLINDING_ROLE_CODE_CAREGIVER")
    @Enumerated(EnumType.STRING)
    public BlindingRoleCode getBlindingRoleCodeCaregiver() {
        return blindingRoleCodeCaregiver;
    }

    /**
     * 
     * @param blindingRoleCodeCaregiver
     *            blindingRoleCodeCaregiver
     */
    public void setBlindingRoleCodeCaregiver(
            BlindingRoleCode blindingRoleCodeCaregiver) {
        this.blindingRoleCodeCaregiver = blindingRoleCodeCaregiver;
    }

    /**
     * 
     * @return blindingRoleCodeInvestigator
     */
    @Column(name = "BLINDING_ROLE_CODE_INVESTIGATOR")
    @Enumerated(EnumType.STRING)
    public BlindingRoleCode getBlindingRoleCodeInvestigator() {
        return blindingRoleCodeInvestigator;
    }

    /**
     * 
     * @param blindingRoleCodeInvestigator
     *            blindingRoleCodeInvestigator
     */
    public void setBlindingRoleCodeInvestigator(
            BlindingRoleCode blindingRoleCodeInvestigator) {
        this.blindingRoleCodeInvestigator = blindingRoleCodeInvestigator;
    }

    /**
     * 
     * @return blindingRoleCodeOutcome
     */
    @Column(name = "BLINDING_ROLE_CODE_OUTCOME")
    @Enumerated(EnumType.STRING)
    public BlindingRoleCode getBlindingRoleCodeOutcome() {
        return blindingRoleCodeOutcome;
    }

    /**
     * 
     * @param blindingRoleCodeOutcome
     *            blindingRoleCodeOutcome
     */
    public void setBlindingRoleCodeOutcome(
            BlindingRoleCode blindingRoleCodeOutcome) {
        this.blindingRoleCodeOutcome = blindingRoleCodeOutcome;
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
     * @param blindingSchemaCode
     *            blindingSchemaCode
     */
    public void setBlindingSchemaCode(BlindingSchemaCode blindingSchemaCode) {
        this.blindingSchemaCode = blindingSchemaCode;
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
     * @param designConfigurationCode
     *            designConfigurationCode
     */
    public void setDesignConfigurationCode(
            DesignConfigurationCode designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }

    /**
     * @return numberOfInterventionGroups
     */
    @Column(name = "NUMBER_OF_INTERVENTION_GROUPS")
    public Integer getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }

    /**
     * @param numberOfInterventionGroups
     *            numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(Integer numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }

    /**
     * 
     * @return studyClassificationCode
     */
    @Column(name = "STUDY_CLASSIFICATION_CODE")
    @Enumerated(EnumType.STRING)
    public StudyClassificationCode getStudyClassificationCode() {
        return studyClassificationCode;
    }

    /**
     * 
     * @param studyClassificationCode
     *            studyClassificationCode
     */
    public void setStudyClassificationCode(
            StudyClassificationCode studyClassificationCode) {
        this.studyClassificationCode = studyClassificationCode;
    }

}
