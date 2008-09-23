/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;

/**
 * Convert HealthCareFacility domain to/from DTO.
 *
 * @author Hugh Reinhart
 * @since 09/23/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class HealthCareFacilityConverter {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static HealthCareFacilityDTO convertFromDomainToDTO(
            HealthCareFacility bo) throws PAException {
        HealthCareFacilityDTO dto = new HealthCareFacilityDTO();
        dto.setIi(IiConverter.convertToIi(bo.getId()));
        dto.setOrganizationIi(IiConverter.convertToIi(bo.getOrganization().getId()));
        dto.setUserLastUpdated(StConverter.convertToSt(bo.getUserLastUpdated()));
        return dto;
    }

    /**
     * @param dto HealthCareFacilityDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static HealthCareFacility convertFromDtoToDomain(
            HealthCareFacilityDTO dto) throws PAException {
        Organization oBo = new Organization();
        oBo.setId(IiConverter.convertToLong(dto.getOrganizationIi()));
        
        HealthCareFacility bo = new HealthCareFacility();
        bo.setId(IiConverter.convertToLong(dto.getIi()));
        bo.setOrganization(oBo);
        bo.setUserLastUpdated(StConverter.convertToString(dto.getUserLastUpdated()));
        return bo;
    }
}
