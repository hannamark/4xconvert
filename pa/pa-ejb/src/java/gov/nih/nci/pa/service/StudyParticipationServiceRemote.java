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
public interface StudyParticipationServiceRemote {
    // Standard methods
    /**
     * @param ii index
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    StudyParticipationDTO getStudyParticipation(Ii ii) throws PAException;
    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    StudyParticipationDTO createStudyParticipation(StudyParticipationDTO dto) throws PAException;
    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    StudyParticipationDTO updateStudyParticipation(StudyParticipationDTO dto) throws PAException;
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyParticipationDTO   
     * @throws PAException on error 
     */
    List<StudyParticipationDTO> getStudyParticipationByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    
}
