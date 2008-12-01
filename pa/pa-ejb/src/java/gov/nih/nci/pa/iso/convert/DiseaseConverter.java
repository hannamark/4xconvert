package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class DiseaseConverter extends AbstractConverter<DiseaseDTO, Disease> {

    /**
     * @param bo domain object
     * @return iso dto
     * @throws PAException exception
     */
    @Override
    public DiseaseDTO convertFromDomainToDto(Disease bo) throws PAException {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setDiseaseCode(StConverter.convertToSt(bo.getDiseaseCode()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setMenuDisplayName(StConverter.convertToSt(bo.getMenuDisplayName()));
        dto.setNtTermIdentifier(StConverter.convertToSt(bo.getNtTermIdentifier()));
        dto.setPreferredName(StConverter.convertToSt(bo.getPreferredName()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        return dto;
    }

    /**
     * @param dto iso dto
     * @return domain object
     * @throws PAException exception
     */
    @Override
    public Disease convertFromDtoToDomain(DiseaseDTO dto) throws PAException {
        Disease bo = new Disease();
        bo.setDiseaseCode(StConverter.convertToString(dto.getDiseaseCode()));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setMenuDisplayName(StConverter.convertToString(dto.getMenuDisplayName()));
        bo.setNtTermIdentifier(StConverter.convertToString(dto.getNtTermIdentifier()));
        bo.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
        bo.setStatusCode(ActiveInactivePendingCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        return bo;
    }

}
