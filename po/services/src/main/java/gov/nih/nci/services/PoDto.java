package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Ii;

import java.io.Serializable;

/**
 * Marker interface for ceratin DTO classes.
 */
public interface PoDto extends Serializable {

    /**
     * @return the system assigned identifier
     */
    Ii getIdentifier();

    /**
     * @param identifier new identifier to set
     */
    void setIdentifier(Ii identifier);
}
