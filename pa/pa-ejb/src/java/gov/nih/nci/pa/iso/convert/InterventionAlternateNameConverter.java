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
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class InterventionAlternateNameConverter {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static InterventionAlternateNameDTO convertFromDomainToDTO(
            InterventionAlternateName bo) throws PAException {
        InterventionAlternateNameDTO dto = new InterventionAlternateNameDTO();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        if (bo.getIntervention() != null) {
            dto.setInterventionIdentifier(IiConverter.convertToIi(bo.getIntervention().getId()));
        }
        dto.setName(StConverter.convertToSt(bo.getName()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setUserLastUpdated(StConverter.convertToSt(bo.getUserLastUpdated()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto InterventionAlternateNameDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static InterventionAlternateName convertFromDtoToDomain(
            InterventionAlternateNameDTO dto) throws PAException {
        Intervention iBo = null;
        if (PAUtil.isIiNull(dto.getInterventionIdentifier())) {
            iBo = new Intervention();
            iBo.setId(IiConverter.convertToLong(dto.getInterventionIdentifier()));
        }

        InterventionAlternateName bo = new InterventionAlternateName();
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIntervention(iBo);
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStatusCode(ActiveInactiveCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        bo.setUserLastUpdated(StConverter.convertToString(dto.getUserLastUpdated()));
        return bo;
    }

}
