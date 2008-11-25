package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StudyRegulatoryAuthorityDTO extends BaseDTO {
    private Ii studyProtocolIdentifier;
    private Ii regulatoryAuthorityIdentifier;
    /**
     * @return the studyProtocolIdentifer
     */
    public Ii getStudyProtocolIdentifier() {
        return studyProtocolIdentifier;
    }
    /**
     * @param studyProtocolIdentifier the protocolId to set
     */
    public void setStudyProtocolIdentifier(Ii studyProtocolIdentifier) {
        this.studyProtocolIdentifier = studyProtocolIdentifier;
    }
    /**
     * @return the regulatoryAuthorityIdentifier
     */
    public Ii getRegulatoryAuthorityIdentifier() {
        return regulatoryAuthorityIdentifier;
    }
    /**
     * @param regulatoryAuthorityIdentifier the regulatoryAuthorityId to set
     */
    public void setRegulatoryAuthorityIdentifier(Ii regulatoryAuthorityIdentifier) {
        this.regulatoryAuthorityIdentifier = regulatoryAuthorityIdentifier;
    }

}
