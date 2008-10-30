/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Bala Nair
 * @since 10/23/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyContactServiceRemote extends StudyPaService<StudyContactDTO> {
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyContacDTO   
     * @throws PAException on error 
     */
    List<StudyContactDTO> getByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * 
     * @param studyContactDTO StudyContactDTO
     * @return StudyContactDTO
     * @throws PAException PAException
     */
    StudyContactDTO create(StudyContactDTO studyContactDTO) throws PAException;

}
