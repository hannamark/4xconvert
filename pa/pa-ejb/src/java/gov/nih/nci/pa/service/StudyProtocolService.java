package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
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
}
