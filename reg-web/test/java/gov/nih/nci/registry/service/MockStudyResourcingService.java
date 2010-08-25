/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyResourcingService implements StudyResourcingServiceLocal {
    static List<StudyResourcingDTO> list;
    static {
        list = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO studyRDto = new StudyResourcingDTO();
        studyRDto.setFundingMechanismCode(CdConverter.convertStringToCd("B09"));
        studyRDto.setNihInstitutionCode(CdConverter.convertStringToCd("AG"));
        studyRDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("CCR"));
        studyRDto.setSerialNumber(StConverter.convertToSt("123456"));
        studyRDto.setIdentifier(IiConverter.convertToIi("1"));
        studyRDto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        list.add(studyRDto);
        studyRDto = new StudyResourcingDTO();
        studyRDto.setFundingMechanismCode(CdConverter.convertStringToCd("B09"));
        studyRDto.setNihInstitutionCode(CdConverter.convertStringToCd("AG"));
        studyRDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("CCR"));
        studyRDto.setSerialNumber(StConverter.convertToSt("123456"));
        studyRDto.setIdentifier(IiConverter.convertToIi("2"));
        studyRDto.setStudyProtocolIdentifier(IiConverter.convertToIi("3"));
        list.add(studyRDto);
    }

    /*
     * {@inheritDoc}
     */
    public StudyResourcingDTO createStudyResourcing(
            StudyResourcingDTO studyResourcingDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * {@inheritDoc}
     */
    public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * {@inheritDoc}
     */
    public StudyResourcingDTO getStudyResourcingById(Ii studyResourceIi)
            throws PAException {
        StudyResourcingDTO matchingDto= new StudyResourcingDTO();
        for (StudyResourcingDTO dto :list){
            if(dto.getIdentifier().getExtension().equals(studyResourceIi.getExtension())){
                matchingDto = dto;
            }
        }
        return matchingDto;
    }

    /*
     * {@inheritDoc}
     */
    public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        List<StudyResourcingDTO> matchingDtosList = new ArrayList<StudyResourcingDTO>();
        for (StudyResourcingDTO dto :list){
            if(dto.getStudyProtocolIdentifier().getExtension().equals(studyProtocolIi.getExtension())){
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList;
    }

    /*
     * {@inheritDoc}
     */
    public StudyResourcingDTO getSummary4ReportedResourcing(Ii studyProtocolIi)
            throws PAException {
        if (studyProtocolIi != null && studyProtocolIi.getExtension().equals("3")) {
            return new StudyResourcingDTO();
        }
        return null;
    }

    /*
     * {@inheritDoc}
     */
    public StudyResourcingDTO updateStudyResourcing(
            StudyResourcingDTO studyResourcingDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate(StudyResourcingDTO studyResourcingDTO)
            throws PAException {
        // TODO Auto-generated method stub

    }

	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StudyResourcingDTO> getByStudyProtocol(Ii ii)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public StudyResourcingDTO create(StudyResourcingDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}

	public StudyResourcingDTO get(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

}
