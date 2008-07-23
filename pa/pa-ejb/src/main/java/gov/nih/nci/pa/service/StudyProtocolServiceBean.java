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
 *
 */
@Stateless
public class StudyProtocolServiceBean  implements StudyProtocolServiceLocal, StudyProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceBean.class);
    
    /**
     * @param studyProtocolQueryCriteria studyProtocolQueryCriteria
     * @return List queryStudyProtocolCriteria
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> 
                getStudyProtocolByCriteria(StudyProtocolQueryCriteria studyProtocolQueryCriteria) throws PAException {
        LOG.debug("Entering getProtocol ");
        StudyProtocolServiceImpl pImpl = new StudyProtocolServiceImpl();
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        pdtos = pImpl.getStudyProtocolByCriteria(studyProtocolQueryCriteria);
        LOG.debug("Leaving getProtocol ");
        return pdtos;
    }
    

}
