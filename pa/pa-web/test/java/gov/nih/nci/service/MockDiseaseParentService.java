/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ASharma
 *
 */
public class MockDiseaseParentService implements DiseaseParentServiceRemote {


	public List<DiseaseParentDTO> getByChildDisease(Ii ii) throws PAException {
		return initList();
	}


	public List<DiseaseParentDTO> getByChildDisease(Ii[] iis)
			throws PAException {

		return initList();
	}


	public List<DiseaseParentDTO> getByParentDisease(Ii ii) throws PAException {
		return initList();
	}


	public DiseaseParentDTO create(DiseaseParentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub

	}


	public DiseaseParentDTO get(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public DiseaseParentDTO update(DiseaseParentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public void validate(DiseaseParentDTO dto) throws PAException {
		// TODO Auto-generated method stub

	}
   private List<DiseaseParentDTO> initList() {
	   DiseaseParentDTO dpDTO = new DiseaseParentDTO();
		dpDTO.setIdentifier(IiConverter.convertToIi("1"));
		dpDTO.setParentDiseaseIdentifier(IiConverter.convertToIi("1"));
		List<DiseaseParentDTO> dpDtos = new ArrayList<DiseaseParentDTO>();
		dpDtos.add(dpDTO);
		return dpDtos;
   }

}
