/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public interface StudyParticipationContactServiceRemote {
    // Standard methods
    /**
     * @param ii index
     * @return StudyParticipationContactDTO
     * @throws PAException PAException
     */
    StudyParticipationContactDTO getStudyParticipationContact(Ii ii) throws PAException;
    /**
     * @param dto StudyParticipationContactDTO
     * @return StudyParticipationContactDTO
     * @throws PAException PAException
     */
    StudyParticipationContactDTO createStudyParticipationContact(StudyParticipationContactDTO dto) throws PAException;
    /**
     * @param dto StudyParticipationContactDTO
     * @return StudyParticipationContactDTO
     * @throws PAException PAException
     */
    StudyParticipationContactDTO updateStudyParticipationContact(StudyParticipationContactDTO dto) throws PAException;
}
