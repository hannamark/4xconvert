/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
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
public class InterventionAlternateNameConverter 
        extends AbstractConverter<InterventionAlternateNameDTO, InterventionAlternateName> {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    @Override
    public InterventionAlternateNameDTO convertFromDomainToDto(InterventionAlternateName bo) throws PAException {
        InterventionAlternateNameDTO dto = new InterventionAlternateNameDTO();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setInterventionIdentifier(IiConverter.convertToIi(bo.getIntervention().getId()));
        dto.setName(StConverter.convertToSt(bo.getName()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto InterventionAlternateNameDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    @Override
    public InterventionAlternateName convertFromDtoToDomain(InterventionAlternateNameDTO dto) throws PAException {
        Intervention iBo = new Intervention();
        iBo.setId(IiConverter.convertToLong(dto.getInterventionIdentifier()));

        InterventionAlternateName bo = new InterventionAlternateName();
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIntervention(iBo);
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStatusCode(ActiveInactiveCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        return bo;
    }

}
