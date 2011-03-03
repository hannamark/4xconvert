/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.PDQDiseaseAlternameDTO;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;
import gov.nih.nci.pa.iso.util.StConverter;
/**
 * @author ASharma
 *
 */
public class MockDiseaseAlternateService implements PDQDiseaseAlternameServiceLocal {


	public List<PDQDiseaseAlternameDTO> getByDisease(Ii ii) throws PAException {
		PDQDiseaseAlternameDTO daDto = new PDQDiseaseAlternameDTO();
		daDto.setAlternateName(StConverter.convertToSt("name1"));
		List<PDQDiseaseAlternameDTO> alternameList = new ArrayList<PDQDiseaseAlternameDTO>();
		alternameList.add(daDto);
		return alternameList;
	}


	public PDQDiseaseAlternameDTO create(PDQDiseaseAlternameDTO dto)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}


	public PDQDiseaseAlternameDTO get(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public PDQDiseaseAlternameDTO update(PDQDiseaseAlternameDTO dto)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void validate(PDQDiseaseAlternameDTO dto) throws PAException {
		// TODO Auto-generated method stub

	}

}
