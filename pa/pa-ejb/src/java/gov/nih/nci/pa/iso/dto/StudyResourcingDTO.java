package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;

/**
 * StudyResourcingDTO for transferring Study Resourcing object .
 * @author Naveen Amiruddin
 * @since 09/09/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyResourcingDTO extends BaseDTO {
    private Cd typeCode;
    private Bl summary4ReportedResourceIndicator;
    private Ii organizationIdentifier;
    private Ii resourceProviderIdentifier;
    Ii studyProtocolIi;
    
    /**
     * 
     * @return typeCode
     */
    public Cd getTypeCode() {
        return typeCode;
    }
    /**
     * 
     * @param typeCode typeCode
     */
    public void setTypeCode(Cd typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * 
     * @return summary4ReportedResourceIndicator
     */
    public Bl getSummary4ReportedResourceIndicator() {
        return summary4ReportedResourceIndicator;
    }
    /**
     * 
     * @param summary4ReportedResourceIndicator Bl
     */
    public void setSummary4ReportedResourceIndicator(
            Bl summary4ReportedResourceIndicator) {
        this.summary4ReportedResourceIndicator = summary4ReportedResourceIndicator;
    }
    /**
     * 
     * @return organizationIdentifier
     */
    public Ii getOrganizationIdentifier() {
        return organizationIdentifier;
    }
    /**
     * 
     * @param organizationIdentifier Ii
     */
    public void setOrganizationIdentifier(Ii organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
    /**
     * 
     * @return resourceProviderIdentifier
     */
    public Ii getResourceProviderIdentifier() {
        return resourceProviderIdentifier;
    }
    /**
     * 
     * @param resourceProviderIdentifier Ii
     */
    public void setResourceProviderIdentifier(Ii resourceProviderIdentifier) {
        this.resourceProviderIdentifier = resourceProviderIdentifier;
    }

    /**
     * @return the studyProtocolidentifier
     */
    public Ii getStudyProtocolIi() {
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolIi ii
     */
    public void setStudyProtocolIi(Ii studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }    
}
