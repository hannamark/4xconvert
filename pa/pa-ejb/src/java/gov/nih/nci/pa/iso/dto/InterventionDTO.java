package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.St;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

public class InterventionDTO extends BaseDTOWithStatusCode {
    private static final long serialVersionUID = 1234567567L;
    private Cd typeCode;
    private St name;
    private St descriptionText;
    private St pdqTermIdentifier;
    private St ntTermIdentifier;
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
     * @return the pdqTermIdentifier
     */
    public St getPdqTermIdentifier() {
        return pdqTermIdentifier;
    }
    /**
     * @param pdqTermIdentifier the pdqTermIdentifier to set
     */
    public void setPdqTermIdentifier(St pdqTermIdentifier) {
        this.pdqTermIdentifier = pdqTermIdentifier;
    }
    /**
     * @return the ntTermIdentifier
     */
    public St getNtTermIdentifier() {
        return ntTermIdentifier;
    }
    /**
     * @param ntTermIdentifier the ntTermIdentifier to set
     */
    public void setNtTermIdentifier(St ntTermIdentifier) {
        this.ntTermIdentifier = ntTermIdentifier;
    }
}
