/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.dto.PAResearchOrganizationDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Remote;


/**
 * @author Hugh Reinhart
 * @since 10/21/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Remote
public interface PAResearchOrganizationServiceRemote {
    /**
     * @return list containing the PAResearchOrganizationDTO or empty list if not found
     * @param organizationId organization id
     * @throws PAException exception
     */
    List<PAResearchOrganizationDTO> getByOrganization(Long organizationId) throws PAException;
    
    /**
     * @param researchOrganization research organization
     * @return the created research organization
     * @throws PAException exception
     */
    PAResearchOrganizationDTO create(PAResearchOrganizationDTO researchOrganization) throws PAException;
    /**
     * @param researchOrganizationId the research organization id
     * @return the research organization object
     * @throws PAException exception
     */
    PAResearchOrganizationDTO get(Long researchOrganizationId) throws PAException;
}
