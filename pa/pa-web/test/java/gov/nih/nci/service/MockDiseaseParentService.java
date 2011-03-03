/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ASharma
 *
 */
public class MockDiseaseParentService implements PDQDiseaseParentServiceRemote {


	public List<PDQDiseaseParentDTO> getByChildDisease(Ii ii) throws PAException {
		return initList();
	}


	public List<PDQDiseaseParentDTO> getByChildDisease(Ii[] iis)
			throws PAException {

		return initList();
	}


	public List<PDQDiseaseParentDTO> getByParentDisease(Ii ii) throws PAException {
		return initList();
	}


	public PDQDiseaseParentDTO create(PDQDiseaseParentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}


	public PDQDiseaseParentDTO get(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public PDQDiseaseParentDTO update(PDQDiseaseParentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void validate(PDQDiseaseParentDTO dto) throws PAException {
		// TODO Auto-generated method stub

	}
   private List<PDQDiseaseParentDTO> initList() {
	   PDQDiseaseParentDTO dpDTO = new PDQDiseaseParentDTO();
		dpDTO.setIdentifier(IiConverter.convertToIi("1"));
		dpDTO.setParentDiseaseIdentifier(IiConverter.convertToIi("1"));
		List<PDQDiseaseParentDTO> dpDtos = new ArrayList<PDQDiseaseParentDTO>();
		dpDtos.add(dpDTO);
		return dpDtos;
   }

}
