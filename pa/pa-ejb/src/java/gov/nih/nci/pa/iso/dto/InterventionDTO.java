package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

public class InterventionDTO extends BaseDTO {
    private static final long serialVersionUID = 1234567567L;
    private Cd typeCode;
    private St name;
    private St descriptionText;
    private Cd statusCode;
    private Ts statusDateRangeLow;
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
     * @return the statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDateRangeLow
     */
    public Ts getStatusDateRangeLow() {
        return statusDateRangeLow;
    }
    /**
     * @param statusDateRangeLow the statusDateRangeLow to set
     */
    public void setStatusDateRangeLow(Ts statusDateRangeLow) {
        this.statusDateRangeLow = statusDateRangeLow;
    }
}
