/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ArmTypeCode;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "ARM")
public class Arm extends AbstractStudyEntity {
    private static final long serialVersionUID = 1237144890L;
    
    /** Maximum length for name (label) attrubute. */
    public static final int NAME_LENGTH = 62;
    /** Maximum length for discriptionText attrubute. */
    public static final int DESCRIPTION_TEXT_LENGTH = 1000;

    private String name;
    private String descriptionText;
    private ArmTypeCode typeCode;
    private Collection<PlannedActivity> interventions = new ArrayList<PlannedActivity>();
    /**
     * @return the name
     */
    @Column(name = "NAME")
    @NotNull
    @Length(max = NAME_LENGTH)
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
    @NotNull
    @Length(max = DESCRIPTION_TEXT_LENGTH)
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
    @Column(name = "TYPE_CODE")
    @Enumerated(EnumType.STRING)
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
            targetEntity = PlannedActivity.class)
    @JoinTable(
        name = "ARM_INTERVENTION",
        joinColumns = @JoinColumn(name = "ARM_IDENTIFIER"),
        inverseJoinColumns = @JoinColumn(name = "PLANNED_ACTIVITY_IDENTIFIER"))
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
