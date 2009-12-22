/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author asharma
 *
 */
public class MockStudyDiseaseService implements StudyDiseaseServiceLocal {

	
	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<StudyDiseaseDTO> getByStudyProtocol(Ii ii) throws PAException {
		 List<StudyDiseaseDTO> sdList = new ArrayList<StudyDiseaseDTO>();
		return sdList;
	}

	
	public StudyDiseaseDTO create(StudyDiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		
	}

	
	public StudyDiseaseDTO get(Ii ii) throws PAException {
		StudyDiseaseDTO sd =  new StudyDiseaseDTO();
		sd.setDiseaseIdentifier(IiConverter.convertToIi("1"));
		sd.setLeadDiseaseIndicator(BlConverter.convertToBl(Boolean.TRUE));
		sd.setCtGovXmlIndicator(BlConverter.convertToBl(Boolean.FALSE));
		return sd;
	}


	public StudyDiseaseDTO update(StudyDiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void validate(StudyDiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		
	}

}
