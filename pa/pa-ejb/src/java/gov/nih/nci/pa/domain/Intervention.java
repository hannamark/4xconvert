/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "INTERVENTION")
public class Intervention extends AbstractEntityWithStatusCode<ActiveInactivePendingCode> {
    private static final long serialVersionUID = 7367567890L;
    
    private String name;
    private InterventionTypeCode typeCode;
    private String descriptionText;
    private String pdqTermIdentifier;
    private String ntTermIdentifier;
    private List<InterventionAlternateName> interventionAlternateNames = new ArrayList<InterventionAlternateName>();
    private List<PlannedActivity> plannedActivities = new ArrayList<PlannedActivity>();

    /**
     * @return the name
     */
    @Column(name = "NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    @NotNull
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
     * @return the typeCode
     */
    @Column(name = "TYPE_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    public InterventionTypeCode getTypeCode() {
        return typeCode;
    }
    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(InterventionTypeCode typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * @return the descriptionText
     */
    @Column(name = "DESCRIPTION_TEXT")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
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
     * @return the pdqTermIdentifier
     */
    @Column(name = "PDQ_TERM_IDENTIFIER")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getPdqTermIdentifier() {
        return pdqTermIdentifier;
    }
    /**
     * @param pdqTermIdentifier the pdqTermIdentifier to set
     */
    public void setPdqTermIdentifier(String pdqTermIdentifier) {
        this.pdqTermIdentifier = pdqTermIdentifier;
    }
    /**
     * @return the ntTermIdentifier
     */
    @Column(name = "NT_TERM_IDENTIFIER")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getNtTermIdentifier() {
        return ntTermIdentifier;
    }
    /**
     * @param ntTermIdentifier the ntTermIdentifier to set
     */
    public void setNtTermIdentifier(String ntTermIdentifier) {
        this.ntTermIdentifier = ntTermIdentifier;
    }
    /**
     * @return the interventionOthers
     */
    @OneToMany(mappedBy = "intervention")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<InterventionAlternateName> getInterventionAlternateNames() {
        return interventionAlternateNames;
    }
    /**
     * @param interventionOthers the interventionOthers to set
     */
    public void setInterventionAlternateNames(List<InterventionAlternateName> interventionOthers) {
        this.interventionAlternateNames = interventionOthers;
    }
    /**
     * @return the plannedActivities
     */
    @OneToMany(mappedBy = "intervention")
    public List<PlannedActivity> getPlannedActivities() {
        return plannedActivities;
    }
    /**
     * @param plannedActivities the plannedActivities to set
     */
    public void setPlannedActivities(List<PlannedActivity> plannedActivities) {
        this.plannedActivities = plannedActivities;
    }
}
