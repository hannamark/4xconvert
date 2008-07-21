package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.QueryStudyProtocolDTO;
import gov.nih.nci.pa.dto.QueryStudyProtocolCriteria;
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
public class ProtocolServiceBean  implements ProtocolServiceLocal, ProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(ProtocolServiceBean.class);
    
    /**
     * @param queryStudyProtocolCriteria queryStudyProtocolCriteria
     * @return List queryStudyProtocolCriteria
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<QueryStudyProtocolDTO> 
                getStudyProtocolByCriteria(QueryStudyProtocolCriteria queryStudyProtocolCriteria) throws PAException {
        LOG.debug("Entering getProtocol ");
        StudyProtocolServiceImpl pImpl = new StudyProtocolServiceImpl();
        List<QueryStudyProtocolDTO> pdtos = new ArrayList<QueryStudyProtocolDTO>();
        pdtos = pImpl.getStudyProtocolByCriteria(queryStudyProtocolCriteria);
        LOG.debug("Leaving getProtocol ");
        return pdtos;
    }
    

}
