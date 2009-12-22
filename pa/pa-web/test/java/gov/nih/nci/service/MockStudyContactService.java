/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author asharma
 *
 */
public class MockStudyContactService implements StudyContactServiceLocal {


	public List<StudyContactDTO> search(StudyContactDTO dto,
			LimitOffset pagingParams) throws PAException,
			TooManyResultsException {
		List<StudyContactDTO> studyContactDtos = new ArrayList<StudyContactDTO>(); 
		return studyContactDtos;
	}

	
	public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
		// TODO Auto-generated method stub
		
	}

	
	public List<StudyContactDTO> getByStudyProtocol(Ii studyProtocolIi,
			StudyContactDTO dto) throws PAException {
		List<StudyContactDTO> studyContactDtos = new ArrayList<StudyContactDTO>(); 
		return studyContactDtos;
	}

	
	public List<StudyContactDTO> getByStudyProtocol(Ii studyProtocolIi,
			List<StudyContactDTO> dto) throws PAException {
		List<StudyContactDTO> studyContactDtos = new ArrayList<StudyContactDTO>(); 
		return studyContactDtos;
	}

	
	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<StudyContactDTO> getByStudyProtocol(Ii ii) throws PAException {
		List<StudyContactDTO> studyContactDtos = new ArrayList<StudyContactDTO>(); 
		return studyContactDtos;
	}

	
	public StudyContactDTO create(StudyContactDTO dto) throws PAException {
		return new StudyContactDTO();
	}

	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		
	}

	
	public StudyContactDTO get(Ii ii) throws PAException {
		return new StudyContactDTO();
	}

	
	public StudyContactDTO update(StudyContactDTO dto) throws PAException {
		return new StudyContactDTO();
	}

	
	public void validate(StudyContactDTO dto) throws PAException {
		// TODO Auto-generated method stub
		
	}

}
