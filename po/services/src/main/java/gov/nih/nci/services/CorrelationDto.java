
package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;

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
    
    /**
     * @return the duplicate of this
     */
    Ii getDuplicateOf();
    
    /**
     * @param obj set the duplicate of this
     */
    void setDuplicateOf(Ii obj);
}
