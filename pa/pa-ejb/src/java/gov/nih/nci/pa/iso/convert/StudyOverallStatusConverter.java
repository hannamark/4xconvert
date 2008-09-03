/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;

/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Hugh Reinhart
 * @since 09/02/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyOverallStatusConverter {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     */
    public static StudyOverallStatusDTO convertFromDomainToDTO(
            StudyOverallStatus bo) {
//        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        return new StudyOverallStatusDTO();
    }

    /**
     * 
     * @param studyProtocolDTO dto 
     * @return StudyProtocol StudyProtocol
     */
    public static StudyOverallStatus convertFromDtoToDomain(
            StudyOverallStatusDTO studyProtocolDTO) {
//        StudyOverallStatus bo = new StudyOverallStatus();
        return new StudyOverallStatus();
    }

}
