/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 09/26/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudySiteAccrualStatusServiceRemote {

    // Standard methods
    /**
     * @param ii index
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii) throws PAException;
    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    StudySiteAccrualStatusDTO createStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException;
    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException;
    
    
    // Custom methods
    /**
     * @param studyParticipationIi id of Participation
     * @return list StudySiteAccrualStatusDTO   
     * @throws PAException on error 
     */
    List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudyParticipation(Ii studyParticipationIi) 
            throws PAException;
    /**
     * @param studyParticipationIi Primary key assigned to a StudyProtocl.
     * @return StudySiteAccrualStatusDTO Current status.
     * @throws PAException Exception.
     */
    List<StudySiteAccrualStatusDTO> getCurrentStudySiteAccrualStatusByStudyParticipation(Ii studyParticipationIi) 
            throws PAException;
    
}
