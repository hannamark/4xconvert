package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
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
     * 
     * @param ii primary id of StudyResourcing
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyProtocolDTO getStudyResourcing(Ii ii) throws PAException;    

}
