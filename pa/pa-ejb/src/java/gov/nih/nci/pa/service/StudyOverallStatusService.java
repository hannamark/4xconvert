/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 08/22/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public interface StudyOverallStatusService {
    /**
     * 
     * @param studyProtocolId id of protocol
     * @return list StudyOverallStatusDTO   
     * @throws PAException on error 
     */
    List<StudyOverallStatusDTO> getStudyOverallStatusByStudyProtocol(Ii studyProtocolId) throws PAException;
    
    /**
     * @param studyProtocolId Primary key assigned to a StudyProtocl.
     * @return StudyOverallStatusDTO Current status.
     * @throws PAException Exception.
     */
    StudyOverallStatusDTO getCurrentStudyOverallStatusByStudyProtocol(Ii studyProtocolId) throws PAException;
    
    /**
     * @param studyOverallStatusDTO studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    StudyOverallStatusDTO updateStudyOverallStatus(StudyOverallStatusDTO studyOverallStatusDTO) throws PAException;
    

}
