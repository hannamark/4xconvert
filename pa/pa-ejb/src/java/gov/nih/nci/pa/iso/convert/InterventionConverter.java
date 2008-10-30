/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.Date;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class InterventionConverter {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static InterventionDTO convertFromDomainToDTO(
            Intervention bo) throws PAException {
        InterventionDTO dto = new InterventionDTO();
        dto.setDescriptionText(StConverter.convertToSt(bo.getDescriptionText()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setName(StConverter.convertToSt(bo.getName()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setTypeCode(CdConverter.convertToCd(bo.getTypeCode()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto InterventionDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static Intervention convertFromDtoToDomain(
            InterventionDTO dto) throws PAException {
        Intervention bo = new Intervention();
        bo.setDateLastUpdated(new Date());
        bo.setDescriptionText(StConverter.convertToString(dto.getDescriptionText()));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStatusCode(ActiveInactivePendingCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        bo.setTypeCode(InterventionTypeCode.getByCode(CdConverter.convertCdToString(dto.getTypeCode())));
        return bo;
    }
}
