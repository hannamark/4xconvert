/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ArmTypeCode;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "ARM")
public class Arm extends AbstractEntity {
    private static final long serialVersionUID = 1237144890L;

    private StudyProtocol studyProtocol;
    private String name;
    private String descriptionText;
    private ArmTypeCode typeCode;
    private Collection<PlannedActivity> interventions = new ArrayList<PlannedActivity>();
    /**
     * @return the studyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID")
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    /**
     * @param studyProtocol the studyProtocol to set
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    /**
     * @return the name
     */
    @Column(name = "NAME")
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
     * @return the descriptionText
     */
    @Column(name = "DESCRIPTION_TEXT")
    public String getDescriptionText() {
        return descriptionText;
    }
    /**
     * @param descriptionText the descriptionText to set
     */
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }
    /**
     * @return the typeCode
     */
    public ArmTypeCode getTypeCode() {
        return typeCode;
    }
    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(ArmTypeCode typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * @return the interventions
     */
    @ManyToMany(
            targetEntity = PlannedActivity.class,
            cascade = {CascadeType.ALL })
    @JoinTable(
        name = "ARM_INTERVENTION",
        joinColumns = @JoinColumn(name = "ARM_ID"),
        inverseJoinColumns = @JoinColumn(name = "PLANNED_ACTIVITY_ID"))
    public Collection<PlannedActivity> getInterventions() {
        return interventions;
    }
    /**
     * @param interventions the interventions to set
     */
    public void setInterventions(Collection<PlannedActivity> interventions) {
        this.interventions = interventions;
    }
}
