package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.service.impl.ProtocolOrganizationServiceImpl;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;


/** 
* Bean implementation for providing access to the client.
* 
* @author Naveen Amiruddin
* @since 06/26/2007
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
public class ProtocolOrganizationServiceBean implements 
    ProtocolOrganizationServiceLocal, ProtocolOrganizationServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(ProtocolOrganizationServiceBean.class);
    
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
            ProtocolOrganizationServiceImpl impl = new ProtocolOrganizationServiceImpl();
            return impl.getOrganizationsAssociatedWithProtocol();
        } finally {
            LOG.debug("Leaving getOrganizationAssociatedsWithProtocol");
        }
    }

}
