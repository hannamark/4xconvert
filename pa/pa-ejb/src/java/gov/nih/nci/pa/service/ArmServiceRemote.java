package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.ArmDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface ArmServiceRemote extends StudyPaService<ArmDTO> {
    /**
     * @param ii index of planned activity
     * @return list of arms associated w/planned activity
     * @throws PAException exception
     */
    List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException;
}
