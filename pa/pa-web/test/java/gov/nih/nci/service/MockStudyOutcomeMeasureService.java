/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyOutcomeMeasureService extends MockAbstractBaseIsoService<StudyOutcomeMeasureDTO> implements
        StudyOutcomeMeasureServiceLocal {
    static List<StudyOutcomeMeasureDTO> list;
    static {
        list = new ArrayList<StudyOutcomeMeasureDTO>();
        StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setName(StConverter.convertToSt("Name"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setSafetyIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setTimeFrame(StConverter.convertToSt("sd"));
        list.add(dto);
        dto = new StudyOutcomeMeasureDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setName(StConverter.convertToSt("Name"));
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setSafetyIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setTimeFrame(StConverter.convertToSt("sd"));
        list.add(dto);
    }

    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        return null;

    }

    /**
     * {@inheritDoc}
     */
    public List<StudyOutcomeMeasureDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        List<StudyOutcomeMeasureDTO> retList = new ArrayList<StudyOutcomeMeasureDTO>();
        for (StudyOutcomeMeasureDTO dto: list){
            if(dto.getStudyProtocolIdentifier().getExtension().equals(ii.getExtension())){
                retList.add(dto);
            }
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureDTO getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOutcomeMeasureDTO create(StudyOutcomeMeasureDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii ii) throws PAException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOutcomeMeasureDTO get(Ii ii) throws PAException {
        for (StudyOutcomeMeasureDTO dto : list) {
            if (dto.getIdentifier().getExtension().equals(ii.getExtension())) {
                return dto;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOutcomeMeasureDTO update(StudyOutcomeMeasureDTO dto) throws PAException {
        return null;
    }

}
