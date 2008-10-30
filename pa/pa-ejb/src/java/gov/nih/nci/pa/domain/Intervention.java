/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;

import java.sql.Timestamp;
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

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "INTERVENTION")
public class Intervention extends AbstractEntity {
    private static final long serialVersionUID = 7367567890L;
    
    private String name;
    private InterventionTypeCode typeCode;
    private String descriptionText;
    private ActiveInactivePendingCode statusCode;
    private Timestamp statusDateRangeLow;
    private List<InterventionAlternateName> interventionAlternateNames = new ArrayList<InterventionAlternateName>();
    private List<PlannedActivity> plannedActivities = new ArrayList<PlannedActivity>();

    /**
     * @return the name
     */
    @Column(name = "NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
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
     * @return the statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public ActiveInactivePendingCode getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(ActiveInactivePendingCode statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDateRangeLow
     */
    @Column(name = "STATUS_DATE_RANGE_LOW")
    public Timestamp getStatusDateRangeLow() {
        return statusDateRangeLow;
    }
    /**
     * @param statusDateRangeLow the statusDateRangeLow to set
     */
    public void setStatusDateRangeLow(Timestamp statusDateRangeLow) {
        this.statusDateRangeLow = statusDateRangeLow;
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
