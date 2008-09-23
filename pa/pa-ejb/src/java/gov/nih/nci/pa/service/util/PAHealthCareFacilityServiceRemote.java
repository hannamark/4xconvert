/**
 *
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Remote
public interface PAHealthCareFacilityServiceRemote {
    /**
     * @param ii index
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */ 
    HealthCareFacilityDTO getHealthCareFacility(Ii ii)
        throws PAException;

    /**
     * @param dto HealthCareFacilityDTO
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */
    HealthCareFacilityDTO createHealthCareFacility(
            HealthCareFacilityDTO dto) throws PAException;

    /**
     * @param dto HealthCareFacilityDTO
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */
    HealthCareFacilityDTO updateHealthCareFacility(
            HealthCareFacilityDTO dto) throws PAException;
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list HealthCareFacilityDTO   
     * @throws PAException on error 
     */
    List<HealthCareFacilityDTO> getHealthCareFacilityByStudyProtocol(
            Ii studyProtocolIi) throws PAException;
}
