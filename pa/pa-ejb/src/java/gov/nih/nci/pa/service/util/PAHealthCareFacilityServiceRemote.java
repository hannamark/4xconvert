/**
 *
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.PAHealthCareFacilityDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyPaService;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Remote
public interface PAHealthCareFacilityServiceRemote extends StudyPaService<PAHealthCareFacilityDTO> {
    /**
     * @param organizationIi pa index of Organization
     * @return list of HealthCareFacilityDTO
     * @throws PAException exception
     */
    List<PAHealthCareFacilityDTO> getByOrganization(Ii organizationIi) throws PAException;
}
