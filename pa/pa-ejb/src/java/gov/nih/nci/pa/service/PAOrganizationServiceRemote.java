package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationDTO;

import java.util.List;

import javax.ejb.Remote;

/** 
* PAOrganizationServiceRemote a remote interface for providing access to the client.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Remote
public interface PAOrganizationServiceRemote  {

    /**
     * returns all the organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @throws PAException PAException
     */
    List<OrganizationDTO> getOrganizationsAssociatedWithStudyProtocol()
    throws PAException;

    /**
     * This expects only id and identifier.
     * @param organization organization
     * @return Organization
     * @throws PAException PAException
     */
    Organization getOrganizationByIndetifers(Organization organization) throws PAException;
    
    /**
     * 
     * @param organization Organization
     * @return Organization
     * @throws PAException PAException
     */
    Organization createOrganization(Organization organization) throws PAException;    

}
