/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActionCategoryCode;
import gov.nih.nci.pa.enums.ActionSubcategoryCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "PLANNED_ACTIVITY")
public class PlannedActivity extends Activity {
    private static final long serialVersionUID = 1239781890L;

    private ActionCategoryCode categoryCode;
    private ActionSubcategoryCode subcategoryCode; 
    private String name;
    private String alternateName;
    private Boolean leadProductIndicator;
    private String descriptionText;
    private Intervention intervention;
    
    /**
     * @return the categoryCode
     */
    @Column(name = "CATEGORY_CODE")
    @Enumerated(EnumType.STRING)
    public ActionCategoryCode getCategoryCode() {
        return categoryCode;
    }
    /**
     * @param categoryCode the categoryCode to set
     */
    public void setCategoryCode(ActionCategoryCode categoryCode) {
        this.categoryCode = categoryCode;
    }
    /**
     * @return the subcategoryCode
     */
    @Column(name = "SUBCATEGORY_CODE")
    @Enumerated(EnumType.STRING)
    public ActionSubcategoryCode getSubcategoryCode() {
        return subcategoryCode;
    }
    /**
     * @param subcategoryCode the subcategoryCode to set
     */
    public void setSubcategoryCode(ActionSubcategoryCode subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }
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
     * @return the alternateName
     */
    @Column(name = "ALTERNATE_NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getAlternateName() {
        return alternateName;
    }
    /**
     * @param alternateName the alternateName to set
     */
    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }
    /**
     * @return the leadProductIndicator
     */
    @Column(name = "LEAD_PRODUCT_INDICATOR")
    public Boolean getLeadProductIndicator() {
        return leadProductIndicator;
    }
    /**
     * @param leadProductIndicator the leadProductIndicator to set
     */
    public void setLeadProductIndicator(Boolean leadProductIndicator) {
        this.leadProductIndicator = leadProductIndicator;
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
     * @return the intervention
     */
    @ManyToOne
    @JoinColumn(name = "INTERVENTION_ID")
    public Intervention getIntervention() {
        return intervention;
    }
    /**
     * @param intervention the intervention to set
     */
    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }
}
