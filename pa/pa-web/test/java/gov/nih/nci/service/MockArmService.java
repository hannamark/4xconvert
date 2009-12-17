/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockArmService extends MockAbstractBaseIsoService <ArmDTO> implements ArmServiceLocal {

	
	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi,
			Map<Ii, Ii> armMap) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ArmDTO> getByStudyProtocol(Ii ii) throws PAException {
		 List<ArmDTO> armIsoList =  new ArrayList<ArmDTO>();
		return armIsoList;
	}

	public ArmDTO create(ArmDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}


	public ArmDTO update(ArmDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public void validate(ArmDTO dto) throws PAException {
		// TODO Auto-generated method stub
		
	}

   
}
