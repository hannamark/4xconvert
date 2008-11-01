package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;

import javax.ejb.Remote;

/**
 * @author Kalpana Guthikonda
 * @since 10/30/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyOutcomeMeasureServiceRemote extends StudyPaService<StudyOutcomeMeasureDTO> {
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyOutcomeMeasureDTO   
     * @throws PAException on error 
     */
    List<StudyOutcomeMeasureDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException;
}
