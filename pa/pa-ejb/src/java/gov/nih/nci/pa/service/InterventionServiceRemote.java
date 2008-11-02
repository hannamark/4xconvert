/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.InterventionDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 * 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface InterventionServiceRemote 
        extends BasePaService<InterventionDTO> {
    
    /**
     * @param searchCriteria search string
     * @return all interventions of given type with names or alternate names matching search string
     * @throws PAException exception
     */
    List<InterventionDTO> search(InterventionDTO searchCriteria) throws PAException;

}
