package gov.nih.nci.pa.service.impl;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import gov.nih.nci.pa.dao.StudyCoordinatingCenterDAO;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.service.IProtocolOrganizationService;
import gov.nih.nci.pa.service.PAException;


/**
 * Implementer class for ProtocolOrganizationService, which will be invoked by the EJB bean.
 * If need be, these methods can be exposed as web service
 * @author Harsha, Naveen
 */
public class ProtocolOrganizationServiceImpl implements IProtocolOrganizationService {
    
    private static final Logger LOG  = Logger.getLogger(ProtocolOrganizationServiceImpl.class);
    /**
     * returns all the organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @throws PAException pa exception
     */
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<OrganizationDTO> getOrganizationsAssociatedWithProtocol() 
    throws PAException {
        try {
        LOG.debug("Entereing getOrganizationAssociatedsWithProtocol");
        StudyCoordinatingCenterDAO sccd = new StudyCoordinatingCenterDAO(); 
        return sccd.getLeadOrganizations();
        } finally {
            LOG.debug("Leaving getOrganizationAssociatedsWithProtocol");
        }
    }

}
