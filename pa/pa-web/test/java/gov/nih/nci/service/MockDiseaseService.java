/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.service.DiseaseServiceLocal;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
/**
 * @author ASharma
 *
 */
public class MockDiseaseService implements DiseaseServiceLocal {


	public List<DiseaseDTO> search(DiseaseDTO searchCriteria)
			throws PAException {
		DiseaseDTO dto = new DiseaseDTO();
		dto.setDiseaseCode(StConverter.convertToSt("code"));
		dto.setMenuDisplayName(StConverter.convertToSt("disease"));
		dto.setNtTermIdentifier(StConverter.convertToSt("1"));
		dto.setPreferredName(StConverter.convertToSt("disease"));
		dto.setIdentifier(IiConverter.convertToIi("1"));
		List<DiseaseDTO> dtoList =  new ArrayList<DiseaseDTO>();
		dtoList.add(dto);
		return dtoList;
	}


	public DiseaseDTO create(DiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}

	public DiseaseDTO get(Ii ii) throws PAException {
		DiseaseDTO dto = new DiseaseDTO();
		dto.setDiseaseCode(StConverter.convertToSt("code"));
		dto.setMenuDisplayName(StConverter.convertToSt("disease"));
		dto.setNtTermIdentifier(StConverter.convertToSt("1"));
		dto.setPreferredName(StConverter.convertToSt("disease"));
		dto.setIdentifier(IiConverter.convertToIi("1"));
		dto.setPreferredName(StConverter.convertToSt("name"));
		return dto;
	}


	public DiseaseDTO update(DiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void validate(DiseaseDTO dto) throws PAException {
		// TODO Auto-generated method stub

	}




}
