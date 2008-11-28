package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Remote;

/** 
* service bean for validating the Abstraction.
* 
* @author Kalpana Guthikonda
* @since 11/27/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Remote
public interface AbstractionCompletionServiceRemote {
  /**
   * @param studyProtocolIi studyProtocolIi
   * @return AbstractionCompletionDTO list
   * @throws PAException on error
   */
  List<AbstractionCompletionDTO> validateAbstractionCompletion(Ii studyProtocolIi) throws PAException;
}
