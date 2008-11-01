package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;

import javax.ejb.Remote;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@Remote
public interface StudyIndldeServiceRemote  extends StudyPaService<StudyIndldeDTO> {
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyIndldeDTO   
     * @throws PAException on error 
     */
    List<StudyIndldeDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException;
}
