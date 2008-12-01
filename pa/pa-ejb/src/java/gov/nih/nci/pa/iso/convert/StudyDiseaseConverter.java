package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class StudyDiseaseConverter extends AbstractConverter<StudyDiseaseDTO, StudyDisease> {

    /**
     * @param bo domain object
     * @return iso dto
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO convertFromDomainToDto(StudyDisease bo) throws PAException {
        StudyDiseaseDTO dto = new StudyDiseaseDTO();
        dto.setDiseaseIdentifier(IiConverter.convertToIi(bo.getDisease().getId()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setLeadDiseaseIndicator(BlConverter.convertToBl(bo.getLeadDiseaseIndicator()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        return dto;
    }

    /**
     * @param dto iso dto
     * @return domain object
     * @throws PAException exception
     */
    @Override
    public StudyDisease convertFromDtoToDomain(StudyDiseaseDTO dto) throws PAException {
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));

        Disease dBo = new Disease();
        dBo.setId(IiConverter.convertToLong(dto.getDiseaseIdentifier()));

        StudyDisease bo = new StudyDisease();
        bo.setDisease(dBo);
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setLeadDiseaseIndicator(BlConverter.covertToBoolean(dto.getLeadDiseaseIndicator()));
        bo.setStudyProtocol(spBo);
        return bo;
    }

}
