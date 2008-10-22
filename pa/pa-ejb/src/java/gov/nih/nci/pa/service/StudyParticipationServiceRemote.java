/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyParticipationServiceRemote extends StudyPaService<StudyParticipationDTO> {
    
    /**
     * @param studyProtocolIi id of protocol
     * @param spDTO StudyParticipationDTO
     * @return list StudyParticipationDTO   
     * @throws PAException on error 
     */
    List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi , StudyParticipationDTO spDTO) throws PAException;
    /**
     * @param studyProtocolIi id of protocol
     * @param spDTOList List of StudyParticipationDTO containing criteria
     * @return list StudyParticipationDTO   
     * @throws PAException on error 
     */
    List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi , List<StudyParticipationDTO> spDTOList) throws PAException;
}
