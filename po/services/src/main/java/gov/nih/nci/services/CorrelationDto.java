
package gov.nih.nci.services;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;

/**
 *
 * @author gax
 */
public interface CorrelationDto extends PoDto {
    /**
     * @return the role status of this correlation.
     */
    Cd getStatus();

    /**
     * @param status the new status to set.
     */
    void setStatus(Cd status);

    /**
     * Get the identifiers of this correlation.
     * @return DSet of identifiers
     */
    DSet<Ii> getIdentifier();

    /**
     * Set the identifiers of this correlation.
     * @param identifier identifiers to set
     */
    void setIdentifier(DSet<Ii> identifier);

    /**
     * @return the duplicate of this
     */
    Ii getDuplicateOf();

    /**
     * @param obj set the duplicate of this
     */
    void setDuplicateOf(Ii obj);
}
