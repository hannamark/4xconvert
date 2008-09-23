/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 08/13/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyProtocolServiceRemote {
    
    /**
     * 
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException;
    
    /**
     * 
     * @param studyProtocolDTO studyProtocolDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException;
    
    /**
     * 
     * @param ii ii
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */
    InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii) throws PAException;
    
    /**
     * 
     * @param ispDTO studyProtocolDTO
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */     
    InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException;
    
}
