/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;

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
public interface InterventionAlternateNameServiceRemote
    extends BasePaService<InterventionAlternateNameDTO> {
    
    /**
     * @param interventionIi index of associated Intervention
     * @return list of InterventionAlternateNameDTO
     * @throws PAException exception
     */
    List<InterventionAlternateNameDTO> getByIntervention(Ii interventionIi) throws PAException; 
    /**
     * @param interventionsIi array of indexes of associated Intervention
     * @return list of InterventionAlternateNameDTO
     * @throws PAException exception
     */
    List<InterventionAlternateNameDTO> getByIntervention(Ii[] interventionsIi) throws PAException; 
}
