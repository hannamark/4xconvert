package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hreinhart
 *
 */
public class MockStudyParticipationService implements gov.nih.nci.pa.service.StudyParticipationServiceRemote {

    static List<StudyParticipation> list;
    StudyParticipationConverter converter = new StudyParticipationConverter();
    static Long seq = 1L;
    
    static {
        list = new ArrayList<StudyParticipation>();
        StudyParticipation sp = new StudyParticipation();
        sp.setId(seq++);
        sp.setStudyProtocol(MockStudyProtocolService.list.get(0));
        sp.setFunctionalCode(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION);
        sp.setLocalStudyProtocolIdentifier("LSPID 001");
    }

    /**
     * @param studyProtocolIi
     * @param spDTOList
     * @return
     * @throws PAException
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudyParticipationDTO> spDTOList) throws PAException {
        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : list) {
            if (sp.getStudyProtocol().getId().equals(IiConverter.convertToLong(studyProtocolIi))) {
                for (StudyParticipationDTO criteria : spDTOList) {
                    if (criteria.getFunctionalCode().getCode().equals(sp.getFunctionalCode().getCode())) {
                        resultList.add(converter.convertFromDomainToDto(sp));
                        break;
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * @param studyProtocolIi
     * @param spDTO
     * @return
     * @throws PAException
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii studyProtocolIi,
            StudyParticipationDTO spDTO) throws PAException {
        List<StudyParticipationDTO> criteria = new ArrayList<StudyParticipationDTO>();
        criteria.add(spDTO);
        return getByStudyProtocol(studyProtocolIi, criteria);
    }

    /**
     * @param ii
     * @return
     * @throws PAException
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii ii)
            throws PAException {        
        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : list) {
            if (sp.getId() == IiConverter.convertToLong(ii)) {
                resultList.add(converter.convertFromDomainToDto(sp));
            }
        }
        return resultList;
    }

    /**
     * @param dto
     * @return
     * @throws PAException
     */
    public StudyParticipationDTO create(StudyParticipationDTO dto) throws PAException {
        StudyParticipation bo = converter.convertFromDtoToDomain(dto);
        bo.setId(seq++);
        list.add(bo);
        return converter.convertFromDomainToDto(bo);
    }

    /**
     * @param ii
     * @throws PAException
     */
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param ii
     * @return
     * @throws PAException
     */
    public StudyParticipationDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param dto
     * @return
     * @throws PAException
     */
    public StudyParticipationDTO update(StudyParticipationDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
