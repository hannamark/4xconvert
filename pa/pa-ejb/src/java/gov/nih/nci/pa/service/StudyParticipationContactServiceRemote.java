/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyParticipationContactServiceRemote 
        extends BasePaService<StudyParticipationContactDTO> {
}
