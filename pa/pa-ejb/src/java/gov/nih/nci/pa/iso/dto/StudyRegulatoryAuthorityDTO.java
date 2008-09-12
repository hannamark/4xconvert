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
    private Ii protocolId;
    private Ii regulatoryAuthorityId;
    /**
     * @return the protocolId
     */
    public Ii getProtocolId() {
        return protocolId;
    }
    /**
     * @param protocolId the protocolId to set
     */
    public void setProtocolId(Ii protocolId) {
        this.protocolId = protocolId;
    }
    /**
     * @return the regulatoryAuthorityId
     */
    public Ii getRegulatoryAuthorityId() {
        return regulatoryAuthorityId;
    }
    /**
     * @param regulatoryAuthorityId the regulatoryAuthorityId to set
     */
    public void setRegulatoryAuthorityId(Ii regulatoryAuthorityId) {
        this.regulatoryAuthorityId = regulatoryAuthorityId;
    }

}
