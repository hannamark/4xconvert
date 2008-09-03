package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;

import java.util.List;


/**
 * @author Harsha
 *
 */

public interface StudyProtocolService {
    
    /**
     * 
     * @param pSc StudyProtocolSearchCriteria
     * @return list protocolDto   
     * @throws PAException on error 
     */
    List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(StudyProtocolQueryCriteria pSc) throws PAException;

    /**
     * 
     * @param studyProtocolId studyProtocolId
     * @return StudyProtocolQueryDTO
     * @throws PAException PAException
     */
     StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) throws PAException;

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
     
}
