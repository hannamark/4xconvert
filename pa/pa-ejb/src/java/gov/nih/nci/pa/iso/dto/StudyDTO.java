package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

/**
 * BaseDTO for all common fields included in objects associated with StudyProtocol.
 * @author Hugh Reinhart
 * @since 11/06/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyDTO extends BaseDTO {
    private static final long serialVersionUID = 1234599890L;
    Ii studyProtocolIdentifier;

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
