/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyOverallStatusDTO;

import java.util.List;

import javax.ejb.Stateless;

/**
 * @author Hugh Reinhart
 * @since 08/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class StudyOverallStatusServiceBean implements StudyOverallStatusService {

    /**
     * @param studyProtocolId
     *            Primary key assigned to a StudyProtocl.
     * @return List.
     * @throws PAException
     *             Exception.
     */
    public List<StudyOverallStatusDTO> getStudyOverallStatusByStudyProtocl(
            Ii studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
