/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyResourcingService extends MockAbstractBaseIsoService <StudyResourcingDTO> 
implements StudyResourcingServiceLocal {

	
	public StudyResourcingDTO createStudyResourcing(
			StudyResourcingDTO studyResourcingDTO) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Boolean deleteStudyResourceByID(StudyResourcingDTO studyResourcingDTO)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public StudyResourcingDTO getStudyResourceByID(Ii studyResourceIi)
			throws PAException {
		StudyResourcingDTO dto = null;
		if (studyResourceIi.getExtension().equals("1")) {
		dto = new StudyResourcingDTO();
		dto.setActiveIndicator(BlConverter.convertToBl(Boolean.TRUE));
		dto.setFundingMechanismCode(CdConverter.convertStringToCd("CA"));
		dto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
		dto.setInactiveCommentText(StConverter.convertToSt("test"));
		dto.setNciDivisionProgramCode(CdConverter.convertStringToCd(NciDivisionProgramCode.CCR.getCode()));
		dto.setNihInstitutionCode(CdConverter.convertStringToCd("NIH"));
		dto.setSerialNumber(StConverter.convertToSt("1"));	
		}
		return dto;
	}

	
	public List<StudyResourcingDTO> getstudyResourceByStudyProtocol(
			Ii studyProtocolIi) throws PAException {
		// TODO Auto-generated method stub
		return new ArrayList<StudyResourcingDTO>();
	}

	
	public StudyResourcingDTO getsummary4ReportedResource(Ii studyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
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

	
	public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	

   
}
