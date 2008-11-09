/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 * 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface PlannedActivityServiceRemote 
        extends StudyPaService<PlannedActivityDTO> {
    /**
     * @param ii index of arm
     * @return list of planned activities associated w/arm
     * @throws PAException exception
     */
    List<PlannedActivityDTO> getByArm(Ii ii) throws PAException;
}
