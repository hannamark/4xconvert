package gov.nih.nci.pa.service;

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

}
