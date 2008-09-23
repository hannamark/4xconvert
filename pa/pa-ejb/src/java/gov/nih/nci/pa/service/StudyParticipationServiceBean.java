/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;

import java.util.List;

import javax.ejb.Stateless;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class StudyParticipationServiceBean implements StudyParticipationServiceRemote {

    private static String errMsgMethodNotImplemented = "Method not yet implemented.";
    /**
     * @param ii index
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    public StudyParticipationDTO getStudyParticipation(Ii ii)
    throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    public StudyParticipationDTO createStudyParticipation(
            StudyParticipationDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    public StudyParticipationDTO updateStudyParticipation(
            StudyParticipationDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyParticipationDTO   
     * @throws PAException on error 
     */
    public List<StudyParticipationDTO> getStudyParticipationByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
}
