package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

/**
 * @author hreinhart
 *
 */
public class DiseaseAlternameConverter extends AbstractConverter<DiseaseAlternameDTO, DiseaseAltername> {

    /**
     * @param bo domain object
     * @return iso dto
     * @throws PAException exception
     */
    @Override
    public DiseaseAlternameDTO convertFromDomainToDto(DiseaseAltername bo) throws PAException {
        DiseaseAlternameDTO dto = new DiseaseAlternameDTO();
        dto.setAlternateName(StConverter.convertToSt(bo.getAlternateName()));
        dto.setDiseaseIdentifier(IiConverter.convertToIi(bo.getDisease().getId()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
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
    public DiseaseAltername convertFromDtoToDomain(DiseaseAlternameDTO dto) throws PAException {
        Disease dBo = new Disease();
        dBo.setId(IiConverter.convertToLong(dto.getDiseaseIdentifier()));
        
        DiseaseAltername bo = new DiseaseAltername();
        bo.setAlternateName(StConverter.convertToString(dto.getAlternateName()));
        bo.setDisease(dBo);
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setStatusCode(ActiveInactiveCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        return bo;
    }

}
