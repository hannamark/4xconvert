/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;

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
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyResourcingService#createStudyResourcing(gov.nih.nci.pa.iso.dto.StudyResourcingDTO)
     */
    public StudyResourcingDTO createStudyResourcing(
            StudyResourcingDTO studyResourcingDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyResourcingService#deleteStudyResourceByID(gov.nih.nci.pa.iso.dto.StudyResourcingDTO)
     */
    public Boolean deleteStudyResourceByID(StudyResourcingDTO studyResourcingDTO)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyResourcingService#getStudyResourceByID(gov.nih.nci.coppa.iso.Ii)
     */
    public StudyResourcingDTO getStudyResourceByID(Ii studyResourceIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyResourcingService#getstudyResourceByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyResourcingDTO> getstudyResourceByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        List<StudyResourcingDTO> matchingDtosList = new ArrayList<StudyResourcingDTO>();
        for (StudyResourcingDTO dto :list){
            if(dto.getStudyProtocolIdentifier().getExtension().equals(studyProtocolIi.getExtension())){
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyResourcingService#getsummary4ReportedResource(gov.nih.nci.coppa.iso.Ii)
     */
    public StudyResourcingDTO getsummary4ReportedResource(Ii studyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyResourcingService#updateStudyResourcing(gov.nih.nci.pa.iso.dto.StudyResourcingDTO)
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
