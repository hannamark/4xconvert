package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.impl.StudyProtocolServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since 08/13/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class StudyProtocolServiceBean  implements StudyProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceBean.class);
  
    /**
     * gets a list StudyProtocl by criteria.
     * @param studyProtocolQueryCriteria studyProtocolQueryCriteria
     * @return pdtos
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> 
                getStudyProtocolByCriteria(StudyProtocolQueryCriteria studyProtocolQueryCriteria) throws PAException {
            LOG.debug("Entering getStudyProtocolByCriteria ");
        StudyProtocolServiceImpl pImpl = new StudyProtocolServiceImpl();
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        pdtos = pImpl.getStudyProtocolByCriteria(studyProtocolQueryCriteria);
        LOG.debug("Leaving getStudyProtocolByCriteria ");
        return pdtos;
    }
    
    /**
     * 
     * @param studyProtocolId studyProtocolId
     * @return StudyProtocolQueryDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) 
    throws PAException {
        LOG.debug("Entering getTrialSummaryByStudyProtocolId ");
        StudyProtocolQueryDTO trialSummary = (new StudyProtocolServiceImpl()).
            getTrialSummaryByStudyProtocolId(studyProtocolId);
        LOG.debug("Leaving getTrialSummaryByStudyProtocolId ");
        return trialSummary;
    }
    
    

}
