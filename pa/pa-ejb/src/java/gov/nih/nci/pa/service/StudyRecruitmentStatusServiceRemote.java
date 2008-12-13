/**
 * 
 */
package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 10/14/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyRecruitmentStatusServiceRemote extends StudyPaService<StudyRecruitmentStatusDTO> {
    
    /**
     * 
     * @param studyProtocolIi ii
     * @return StudyRecruitmentStatusDTO StudyRecruitmentStatusDTO
     * @throws PAException error 
     */
    List<StudyRecruitmentStatusDTO> getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException;
    

}
