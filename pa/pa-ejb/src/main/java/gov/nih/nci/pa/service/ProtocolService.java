package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.pa.dto.QueryStudyProtocolCriteria;
import gov.nih.nci.pa.dto.QueryStudyProtocolDTO;
/**
 * @author Harsha
 *
 */

public interface ProtocolService {
    
    /**
     * 
     * @param pSc StudyProtocolSearchCriteria
     * @return list protocolDto   
     * @throws PAException on error 
     */
    List<QueryStudyProtocolDTO> getStudyProtocolByCriteria(QueryStudyProtocolCriteria pSc) throws PAException;
}
