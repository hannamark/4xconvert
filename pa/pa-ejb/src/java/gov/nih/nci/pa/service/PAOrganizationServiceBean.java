package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.service.impl.PAOrganizationServiceImpl;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;


/** 
* Bean implementation for providing access to the client.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
public class PAOrganizationServiceBean implements 
    PAOrganizationServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(PAOrganizationServiceRemote.class);
    
    /**
     * returns distinct organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @throws PAException pa exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<OrganizationDTO> getOrganizationsAssociatedWithStudyProtocol() 
    throws PAException {
        try {
            LOG.debug("Entereing getOrganizationsAssociatedWithStudyProtocol");
            PAOrganizationServiceImpl impl = new PAOrganizationServiceImpl();
            return impl.getOrganizationsAssociatedWithStudyProtocol();
        } finally {
            LOG.debug("Leaving getOrganizationsAssociatedWithStudyProtocol");
        }
    }

}
