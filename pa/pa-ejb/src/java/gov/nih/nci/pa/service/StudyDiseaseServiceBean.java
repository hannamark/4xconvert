package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.iso.convert.StudyDiseaseConverter;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import javax.ejb.Stateless;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudyDiseaseServiceBean 
        extends AbstractStudyIsoService<StudyDiseaseDTO, StudyDisease, StudyDiseaseConverter>
        implements StudyDiseaseServiceRemote {

    private void enforceOnlyOneLead(StudyDiseaseDTO dto) throws PAException {
        if (!PAUtil.isBlNull(dto.getLeadDiseaseIndicator()) 
                && BlConverter.covertToBoolean(dto.getLeadDiseaseIndicator())) {
            List<StudyDiseaseDTO> sdList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyDiseaseDTO sd : sdList) {
                if (!IiConverter.convertToLong(dto.getIdentifier()).equals(
                        IiConverter.convertToLong(sd.getIdentifier()))
                    && (!PAUtil.isBlNull(sd.getLeadDiseaseIndicator()) 
                    && BlConverter.covertToBoolean(sd.getLeadDiseaseIndicator()))) {
                        sd.setLeadDiseaseIndicator(BlConverter.convertToBl(false));
                        update(sd);
                    }
            }
        }
    }

    /**
     * @param dto new study disease
     * @return created study disease
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO create(StudyDiseaseDTO dto) throws PAException {
        StudyDiseaseDTO resultDto = super.create(dto);
        enforceOnlyOneLead(resultDto);
        return resultDto;
    }

    /**
     * @param dto changed study disease
     * @return updated study disease
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO update(StudyDiseaseDTO dto) throws PAException {
        StudyDiseaseDTO resultDto =  super.update(dto);
        enforceOnlyOneLead(resultDto);
        return resultDto;
    }
}
