/**
 * 
 */
package gov.nih.nci.service.util;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.service.MockStudyProtocolService;

/**
 * @author Vrushali
 *
 */
public class MockAbstractionCompletionService implements AbstractionCompletionServiceRemote {

	StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();
	  
	public List<AbstractionCompletionDTO> validateAbstractionCompletion(
			Ii studyProtocolIi) throws PAException {
		 List<AbstractionCompletionDTO> abstractionList = new ArrayList<AbstractionCompletionDTO>();
		 return abstractionList;
	}
	
	
	
}
