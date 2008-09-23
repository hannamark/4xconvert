/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

/**
 * StudyParticipationDTO for transferring StudyParticipation object .
 * @author Hugh Reinhart
 * @since 07/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyParticipationDTO extends BaseDTO {
    Ii studyProtocolIdentifier;
    Ii healthcareFacilityIdentifier;
    
    /**
     * @return the studyProtocolIdentifier
     */
    public Ii getStudyProtocolIdentifier() {
        return studyProtocolIdentifier;
    }
    /**
     * @param studyProtocolIdentifier the studyProtocolIdentifier to set
     */
    public void setStudyProtocolIdentifier(Ii studyProtocolIdentifier) {
        this.studyProtocolIdentifier = studyProtocolIdentifier;
    }
}
