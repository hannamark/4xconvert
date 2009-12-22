/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
import gov.nih.nci.pa.service.DiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;
import gov.nih.nci.pa.iso.util.StConverter;
/**
 * @author ASharma
 *
 */
public class MockDiseaseAlternateService implements DiseaseAlternameServiceLocal {


	public List<DiseaseAlternameDTO> getByDisease(Ii ii) throws PAException {
		DiseaseAlternameDTO daDto = new DiseaseAlternameDTO();
		daDto.setAlternateName(StConverter.convertToSt("name1"));
		List<DiseaseAlternameDTO> alternameList = new ArrayList<DiseaseAlternameDTO>();
		alternameList.add(daDto);
		return alternameList;
	}


	public DiseaseAlternameDTO create(DiseaseAlternameDTO dto)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}


	public DiseaseAlternameDTO get(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public DiseaseAlternameDTO update(DiseaseAlternameDTO dto)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void validate(DiseaseAlternameDTO dto) throws PAException {
		// TODO Auto-generated method stub

	}

}
