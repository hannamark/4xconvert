
package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Cd;

/**
 *
 * @author gax
 */
public interface CorrelationDto extends PoDto {
    /**
     * @return the role status of this corolation.
     */
    Cd getStatus();
    
    /**
     * @param status the new status to set.
     */
    void setStatus(Cd status);
}
