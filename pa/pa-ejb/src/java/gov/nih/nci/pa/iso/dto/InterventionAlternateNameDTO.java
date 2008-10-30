package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

public class InterventionAlternateNameDTO {
    Ii identifier;
    Ii interventionIdentifier;
    St name;
    Cd statusCode;
    Ts statusDateRangeLow;
    /**
     * @return the identifier
     */
    public Ii getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Ii identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the interventionIdentifier
     */
    public Ii getInterventionIdentifier() {
        return interventionIdentifier;
    }
    /**
     * @param interventionIdentifier the interventionIdentifier to set
     */
    public void setInterventionIdentifier(Ii interventionIdentifier) {
        this.interventionIdentifier = interventionIdentifier;
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
