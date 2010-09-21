/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.iso.convert.StudyDiseaseConverter;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyDiseaseBeanLocal extends
        AbstractStudyIsoService<StudyDiseaseDTO, StudyDisease, StudyDiseaseConverter> implements
        StudyDiseaseServiceLocal {

    private StudyDiseaseDTO businessRules(StudyDiseaseDTO dto) throws PAException {
        boolean isNew = PAUtil.isIiNull(dto.getIdentifier());
        // no duplicate diseases in a study
        if (isNew) {
            long newDiseaseId = IiConverter.convertToLong(dto.getDiseaseIdentifier());
            List<StudyDiseaseDTO> sdList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyDiseaseDTO sd : sdList) {
                if (newDiseaseId == IiConverter.convertToLong(sd.getDiseaseIdentifier())) {
                    throw new PAException("Redundancy error:  this trial already includes the selected disease.  ");
                }
            }
        }
        return dto;
    }

    /**
     * @param dto new study disease
     * @return created study disease
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO create(StudyDiseaseDTO dto) throws PAException {
        StudyDiseaseDTO createDto = businessRules(dto);
        return super.create(createDto);
    }

    /**
     * @param dto changed study disease
     * @return updated study disease
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO update(StudyDiseaseDTO dto) throws PAException {
        StudyDiseaseDTO updateDto = businessRules(dto);
        return super.update(updateDto);
    }

}
