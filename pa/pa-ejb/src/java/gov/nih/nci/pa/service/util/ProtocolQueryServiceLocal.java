package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Naveen Amiruddin
 * @since 08/13/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Local
public interface ProtocolQueryServiceLocal {
    
    /**
     * 
     * @param pSc StudyProtocolSearchCriteria
     * @return list protocolDto   
     * @throws PAException on error 
     */
    List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(StudyProtocolQueryCriteria pSc) throws PAException;


    /**
     * 
     * @param studyProtocolId protocol id
     * @return StudyProtocolQueryDTO
     * @throws PAException on error
     */
     StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) throws PAException;
    
     

}
