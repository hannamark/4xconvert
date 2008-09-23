/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;

import java.util.List;

import javax.ejb.Stateless;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class HealthCareFacilityServiceBean implements HealthCareFacilityServiceRemote {
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";

    /**
     * @param ii index
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */ 
    public HealthCareFacilityDTO getHealthCareFacility(Ii ii)
    throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    /**
     * @param dto HealthCareFacilityDTO
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */
    public HealthCareFacilityDTO createHealthCareFacility(
            HealthCareFacilityDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto HealthCareFacilityDTO
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */
    public HealthCareFacilityDTO updateHealthCareFacility(
            HealthCareFacilityDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list HealthCareFacilityDTO   
     * @throws PAException on error 
     */
    public List<HealthCareFacilityDTO> getHealthCareFacilityByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
}
