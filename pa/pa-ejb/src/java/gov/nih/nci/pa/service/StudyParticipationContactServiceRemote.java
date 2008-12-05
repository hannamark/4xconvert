/**
 * 
 */
package gov.nih.nci.pa.service;


import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;

import java.util.List;

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
    
    /**
     * @param studyParticipationIi id of protocol
     * @return list StudyParticipationContactDTO
     * @throws PAException on error
     */    
    List<StudyParticipationContactDTO> getByStudyParticipation(Ii studyParticipationIi) 
            throws PAException;
    
    /**
     * @param studyProtocolIi id of protocol
     * @param scDTO StudyContactDTO 
     * @return list StudyContactDTO   
     * @throws PAException on error 
     */
    List<StudyParticipationContactDTO> getByStudyProtocol(
            Ii studyProtocolIi , StudyParticipationContactDTO scDTO) throws PAException;
    
    /**
     * @param studyProtocolIi id of protocol
     * @param scDTOList List of StudyContactDTO containing criteria
     * @return list StudyContactDTO   
     * @throws PAException on error 
     */
    List<StudyParticipationContactDTO> getByStudyProtocol(
            Ii studyProtocolIi , List<StudyParticipationContactDTO> scDTOList) throws PAException;    
}
