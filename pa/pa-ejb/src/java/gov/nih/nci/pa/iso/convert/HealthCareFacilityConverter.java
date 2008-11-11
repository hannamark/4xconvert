/**
 *
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.iso.dto.PAHealthCareFacilityDTO;
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
    public static PAHealthCareFacilityDTO convertFromDomainToDTO(
            HealthCareFacility bo) throws PAException {
        PAHealthCareFacilityDTO dto = new PAHealthCareFacilityDTO();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setAssignedIdentifier(StConverter.convertToSt(bo.getIdentifier()));
        dto.setOrganizationIi(IiConverter.convertToIi(bo.getOrganization().getId()));
        return dto;
    }

    /**
     * @param dto HealthCareFacilityDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static HealthCareFacility convertFromDtoToDomain(
            PAHealthCareFacilityDTO dto) throws PAException {
        Organization oBo = new Organization();
        oBo.setId(IiConverter.convertToLong(dto.getOrganizationIi()));

        HealthCareFacility bo = new HealthCareFacility();
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIdentifier(StConverter.convertToString(dto.getAssignedIdentifier()));
        bo.setOrganization(oBo);
        return bo;
    }
}
