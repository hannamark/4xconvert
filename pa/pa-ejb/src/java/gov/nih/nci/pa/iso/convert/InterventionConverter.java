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

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class InterventionConverter extends AbstractConverter<InterventionDTO, Intervention> {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    @Override
    public InterventionDTO convertFromDomainToDto(Intervention bo) throws PAException {
        InterventionDTO dto = new InterventionDTO();
        dto.setDescriptionText(StConverter.convertToSt(bo.getDescriptionText()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setName(StConverter.convertToSt(bo.getName()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setTypeCode(CdConverter.convertToCd(bo.getTypeCode()));
        dto.setNtTermIdentifier(StConverter.convertToSt(bo.getNtTermIdentifier()));
        dto.setPdqTermIdentifier(StConverter.convertToSt(bo.getPdqTermIdentifier()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto InterventionDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    @Override
    public Intervention convertFromDtoToDomain(InterventionDTO dto) throws PAException {
        Intervention bo = new Intervention();
        bo.setDescriptionText(StConverter.convertToString(dto.getDescriptionText()));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStatusCode(ActiveInactivePendingCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        bo.setTypeCode(InterventionTypeCode.getByCode(CdConverter.convertCdToString(dto.getTypeCode())));
        bo.setNtTermIdentifier(StConverter.convertToString(dto.getNtTermIdentifier()));
        bo.setPdqTermIdentifier(StConverter.convertToString(dto.getPdqTermIdentifier()));
        return bo;
    }
}
