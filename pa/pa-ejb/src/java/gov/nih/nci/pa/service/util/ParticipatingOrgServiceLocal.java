package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Jun 18, 2012
 */
@Local
public interface ParticipatingOrgServiceLocal {

    /**
     * Return a list of participating organizations for the given type. Return empty list if not found.
     * @param studyProtocolId the id of the StudyProtocol
     * @return populated list of organizations
     * @throws PAException exception
     */
    List<ParticipatingOrgDTO> getTreatingSites(Long studyProtocolId) throws PAException;
    
    /**
     * Gets a participating site.
     * @param studySiteId studySiteId
     * @return ParticipatingOrgDTO
     * @throws PAException PAException
     */
    ParticipatingOrgDTO getTreatingSite(Long studySiteId) throws PAException;
}
