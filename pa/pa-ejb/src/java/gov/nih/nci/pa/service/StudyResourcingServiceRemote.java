package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

import javax.ejb.Remote;

/**
 * @author Naveen Amiruddin
 * @since 09/10/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyResourcingServiceRemote {
    
    /**
     * @param studyProtocolIi Ii 
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyResourcingDTO getsummary4ReportedResource(Ii studyProtocolIi) throws PAException;
    
    /**
     * 
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException;
    
    /**
     * 
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException;
    
    /**
     * @param studyProtocolIi Ii 
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    List<StudyResourcingDTO> getstudyResourceByStudyProtocol(Ii studyProtocolIi) throws PAException; 
    /**
     * @param studyResourceIi Ii 
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    StudyResourcingDTO getStudyResourceByID(Ii studyResourceIi) throws PAException; 
    /**
     * @param studyResourcingDTO StudyResourcingDTO 
     * @return Boolean
     * @throws PAException PAException
     */
    Boolean deleteStudyResourceByID(StudyResourcingDTO studyResourcingDTO) throws PAException;
}
