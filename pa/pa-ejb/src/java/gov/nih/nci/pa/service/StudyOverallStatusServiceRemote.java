/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 08/22/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyOverallStatusServiceRemote extends StudyPaService<StudyOverallStatusDTO> {
    // Custom methods
    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return List Current status StudyOverllStatusDTO.
     * @throws PAException Exception.
     */
    List<StudyOverallStatusDTO> getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
}
