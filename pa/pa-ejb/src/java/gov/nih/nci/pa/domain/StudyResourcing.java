package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;


/**
 * Fiscal support for research.
 *
 * @author Naveen Amiruddin
 * @since 09/09/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name =  "STUDY_RESOURCING")
public class StudyResourcing extends AbstractEntity {

    private SummaryFourFundingCategoryCode typeCode;
    private Boolean summary4ReportedResourceIndicator;
    private String organizationIdentifier;
    private String resourceProviderIdentifier;
    private StudyProtocol studyProtocol;
    
    /**
     * 
     * @return typeCode
     */
    @Column(name = "TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public SummaryFourFundingCategoryCode getTypeCode() {
        return typeCode;
    }
    /**
     * 
     * @param typeCode typeCode
     */
    public void setTypeCode(SummaryFourFundingCategoryCode typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * 
     * @return summary4ReportedResourceIndicator
     */
    @Column(name = "SUMM_4_REPT_INDICATOR")
    public Boolean getSummary4ReportedResourceIndicator() {
        return summary4ReportedResourceIndicator;
    }
    /**
     * 
     * @param summary4ReportedResourceIndicator summary4ReportedResourceIndicator
     */
    public void setSummary4ReportedResourceIndicator(
            Boolean summary4ReportedResourceIndicator) {
        this.summary4ReportedResourceIndicator = summary4ReportedResourceIndicator;
    }
    /**
     * 
     * @return organizationIdentifier
     */
    @Column(name = "ORGANIZATION_ID")
    public String getOrganizationIdentifier() {
        return organizationIdentifier;
    }
    /**
     * 
     * @param organizationIdentifier organizationIdentifier
     */
    public void setOrganizationIdentifier(String organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
    /**
     * 
     * @return resourceProviderIdentifier
     */
    @Column(name = "RESOURCE_PROVIDER_ID")
    public String getResourceProviderIdentifier() {
        return resourceProviderIdentifier;
    }
    /**
     * 
     * @param resourceProviderIdentifier resourceProviderIdentifier
     */
    public void setResourceProviderIdentifier(String resourceProviderIdentifier) {
        this.resourceProviderIdentifier = resourceProviderIdentifier;
    }
    
    /**
     * 
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", updatable = false)
    @NotNull
    public StudyProtocol getStudyProtocol() {
       return studyProtocol;
    }
    /**
     * 
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }    
}
