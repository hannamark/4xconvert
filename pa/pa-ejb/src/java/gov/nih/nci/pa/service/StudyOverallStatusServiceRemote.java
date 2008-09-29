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
public interface StudyOverallStatusServiceRemote  {
    // Standard methods
    /**
     * @param ii index
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    StudyOverallStatusDTO getStudyOverallStatus(Ii ii) throws PAException;
    /**
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    StudyOverallStatusDTO createStudyOverallStatus(StudyOverallStatusDTO dto) throws PAException;
    /**
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    StudyOverallStatusDTO updateStudyOverallStatus(StudyOverallStatusDTO dto) throws PAException;
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyOverallStatusDTO   
     * @throws PAException on error 
     */
    List<StudyOverallStatusDTO> getStudyOverallStatusByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    
    // Custom methods
    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return List Current status StudyOverllStatusDTO.
     * @throws PAException Exception.
     */
    List<StudyOverallStatusDTO> getCurrentStudyOverallStatusByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
}
