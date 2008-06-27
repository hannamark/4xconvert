package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.OrganizationDTO;

import java.util.List;

/** 
* ProtocolOrganization Service for interacting between Protocol & Organization.
* 
* @author Naveen Amiruddin
* @since 06/26/2007
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public interface IProtocolOrganizationService {
    
    /**
     * returns all the organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @throws PAException PAException
     */
    List<OrganizationDTO> getOrganizationsAssociatedWithProtocol()
    throws PAException;

}
