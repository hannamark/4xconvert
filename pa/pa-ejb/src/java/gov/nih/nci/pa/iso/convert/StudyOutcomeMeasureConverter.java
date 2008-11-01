package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.Date;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/29/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@SuppressWarnings({  "PMD.NPathComplexity" , "PMD.CyclomaticComplexity" })
public class StudyOutcomeMeasureConverter {

    /**
     * @param som StudyOutcomeMeasure
     * @return StudyOutcomeMeasureDTO
     */
    public static StudyOutcomeMeasureDTO convertFromDomainToDTO(StudyOutcomeMeasure som) {
        StudyOutcomeMeasureDTO somDTO = new StudyOutcomeMeasureDTO();
        somDTO.setIdentifier(IiConverter.convertToIi(som.getId()));
        somDTO.setName(StConverter.convertToSt(som.getName()));
        somDTO.setTimeFrame(StConverter.convertToSt(som.getTimeFrame()));
        somDTO.setStudyProtocolIi(IiConverter.convertToIi(som.getStudyProtocol().getId()));
        somDTO.setPrimaryIndicator(BlConverter.convertToBl(som.getPrimaryIndicator()));
        somDTO.setSafetyIndicator(BlConverter.convertToBl(som.getSafetyIndicator()));
        
        return somDTO;
    }


    /**
     * @param somDTO StudyOutcomeMeasureDTO
     * @return StudyOutcomeMeasure
     */
    public static StudyOutcomeMeasure convertFromDTOToDomain(StudyOutcomeMeasureDTO somDTO) {
        StudyOutcomeMeasure som = new StudyOutcomeMeasure();

        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(somDTO.getStudyProtocolIi()));
        som.setDateLastUpdated(new Date());
        som.setStudyProtocol(spBo);
        if (somDTO.getIdentifier() != null) {
            som.setId(IiConverter.convertToLong(somDTO.getIdentifier()));
        }
        if (somDTO.getUserLastUpdated() != null) {
            som.setUserLastUpdated(somDTO.getUserLastUpdated().getValue());
        }
        if (somDTO.getName() != null) {
            som.setName(somDTO.getName().getValue());
        }
        if (somDTO.getTimeFrame() != null) {
            som.setTimeFrame(somDTO.getTimeFrame().getValue());
        }
        if (somDTO.getPrimaryIndicator() != null) {
            som.setPrimaryIndicator(somDTO.getPrimaryIndicator().getValue());
        }
        if (somDTO.getSafetyIndicator() != null) {
            som.setSafetyIndicator(somDTO.getSafetyIndicator().getValue());
        }
        return som;
    }

}
