/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class ArmDTO extends StudyDTO {
    private static final long serialVersionUID = 1238767890L;
    private St name;
    private Cd typeCode;
    private St descriptionText;
    private DSet<Ii> interventions;
    
    /**
     * @return the name
     */
    public St getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(St name) {
        this.name = name;
    }
    /**
     * @return the typeCode
     */
    public Cd getTypeCode() {
        return typeCode;
    }
    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(Cd typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * @return the descriptionText
     */
    public St getDescriptionText() {
        return descriptionText;
    }
    /**
     * @param descriptionText the descriptionText to set
     */
    public void setDescriptionText(St descriptionText) {
        this.descriptionText = descriptionText;
    }
    /**
     * @return the interventions
     */
    public DSet<Ii> getInterventions() {
        return interventions;
    }
    /**
     * @param interventions the interventions to set
     */
    public void setInterventions(DSet<Ii> interventions) {
        this.interventions = interventions;
    }
}
