/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
/**
 * @author ASharma
 *
 */
public class MockDiseaseService implements PDQDiseaseServiceLocal {


	public List<PDQDiseaseDTO> search(PDQDiseaseDTO searchCriteria)
			throws PAException {
		PDQDiseaseDTO dto = new PDQDiseaseDTO();
		dto.setDiseaseCode(StConverter.convertToSt("code"));
		dto.setDisplayName(StConverter.convertToSt("disease"));
		dto.setNtTermIdentifier(StConverter.convertToSt("1"));
		dto.setPreferredName(StConverter.convertToSt("disease"));
		dto.setIdentifier(IiConverter.convertToIi("1"));
		List<PDQDiseaseDTO> dtoList =  new ArrayList<PDQDiseaseDTO>();
		dtoList.add(dto);
		return dtoList;
	}


	public PDQDiseaseDTO create(PDQDiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}

	public PDQDiseaseDTO get(Ii ii) throws PAException {
		PDQDiseaseDTO dto = new PDQDiseaseDTO();
		dto.setDiseaseCode(StConverter.convertToSt("code"));
		dto.setDisplayName(StConverter.convertToSt("disease"));
		dto.setNtTermIdentifier(StConverter.convertToSt("1"));
		dto.setPreferredName(StConverter.convertToSt("disease"));
		dto.setIdentifier(IiConverter.convertToIi("1"));
		dto.setPreferredName(StConverter.convertToSt("name"));
		return dto;
	}


	public PDQDiseaseDTO update(PDQDiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void validate(PDQDiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub

	}




}
