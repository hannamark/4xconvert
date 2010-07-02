/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vrushali
 *
 */
public class MockAbstractionCompletionService implements AbstractionCompletionServiceRemote {

	/* (non-Javadoc)
	 * @see gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote#validateAbstractionCompletion(gov.nih.nci.iso21090.Ii)
	 */
	public List<AbstractionCompletionDTO> validateAbstractionCompletion(
			Ii studyProtocolIi) throws PAException {
		return new ArrayList<AbstractionCompletionDTO>();
	}

}
