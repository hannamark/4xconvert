package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseParent;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
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
public class DiseaseParentConverter extends AbstractConverter<DiseaseParentDTO, DiseaseParent> {

    /**
     * @param bo domain object
     * @return iso dto
     * @throws PAException exception
     */
    @Override
    public DiseaseParentDTO convertFromDomainToDto(DiseaseParent bo) throws PAException {
        DiseaseParentDTO dto = new DiseaseParentDTO();
        dto.setDiseaseIdentifier(IiConverter.convertToIi(bo.getDisease().getId()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setParentDiseaseCode(StConverter.convertToSt(bo.getParentDiseaseCode()));
        dto.setParentDiseaseIdentifier(IiConverter.convertToIi(bo.getParentDisease().getId()));
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
    public DiseaseParent convertFromDtoToDomain(DiseaseParentDTO dto) throws PAException {
        Disease pdBo = new Disease();
        pdBo.setId(IiConverter.convertToLong(dto.getParentDiseaseIdentifier()));
        
        Disease dBo = new Disease();
        dBo.setId(IiConverter.convertToLong(dto.getDiseaseIdentifier()));
        
        DiseaseParent bo = new DiseaseParent();
        bo.setDisease(dBo);
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setParentDisease(pdBo);
        bo.setParentDiseaseCode(StConverter.convertToString(dto.getParentDiseaseCode()));
        bo.setStatusCode(ActiveInactiveCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        return bo;
    }

}
