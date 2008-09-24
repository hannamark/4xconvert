package gov.nih.nci.pa.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;


import gov.nih.nci.pa.enums.MonitorCode;
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
    private String fundingMechanismCode;
    private String nihInstituteCode;
    private String fundingTypeCode;
    private MonitorCode nciDivisionProgramCode;
    private String suffixGrantYear;
    private String suffixOther;
    private String serialNumber;
    private StudyProtocol studyProtocol;
    private Boolean activeIndicator;
    private String inactiveCommentText;

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
     * @return fundingMechanismCode
     */
    @Column(name = "FUNDING_MECHANISM_CODE")
    public String getFundingMechanismCode() {
        return fundingMechanismCode;
    }
    /**
     * @param fundingMechanismCode fundingMechanismCode
     */
    public void setFundingMechanismCode(String fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    /**
     * @return fundingTypeCode
     */
    @Column(name = "FUNDING_TYPE_CODE")
    public String getFundingTypeCode() {
        return fundingTypeCode;
    }
    /**
     * @param fundingTypeCode fundingTypeCode
     */
    public void setFundingTypeCode(String fundingTypeCode) {
        this.fundingTypeCode = fundingTypeCode;
    }

    /**
     * @return suffixOther
     */
    @Column(name = "SUFFIX_OTHER")
    public String getSuffixOther() {
        return suffixOther;
    }
    /**
     * @param suffixOther suffixOther
     */
    public void setSuffixOther(String suffixOther) {
        this.suffixOther = suffixOther;
    }
    /**
     * @return serialNumber
     */
    @Column(name = "SERIAL_NUMBER")
    public String getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber serialNumber
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    /**
     * 
     * @return nihInstituteCode
     */
    @Column(name = "NIH_INSTITUTE_CODE")
    public String getNihInstituteCode() {
        return nihInstituteCode;
    }
    /**
     * 
     * @param nihInstituteCode nihInstituteCode
     */
    public void setNihInstituteCode(String nihInstituteCode) {
        this.nihInstituteCode = nihInstituteCode;
    }
    /**
     * 
     * @return nciDivisionProgramCode
     */
    @Column(name = "NCI_DIVISION_PROGRAM_CODE")
    @Enumerated(EnumType.STRING)    
    public MonitorCode getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }
    /**
     * 
     * @param nciDivisionProgramCode nciDivisionProgramCode
     */
    public void setNciDivisionProgramCode(MonitorCode nciDivisionProgramCode) {
        this.nciDivisionProgramCode = nciDivisionProgramCode;
    }
    /**
     * 
     * @return suffixGrantYear
     */
    @Column(name = "SUFFIX_GRANT_YEAR")
    public String getSuffixGrantYear() {
        return suffixGrantYear;
    }
    
    /**
     * 
     * @param suffixGrantYear suffixGrantYear
     */
    public void setSuffixGrantYear(String suffixGrantYear) {
        this.suffixGrantYear = suffixGrantYear;
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
    /**
     * 
     * @return activeIndicator
     */
    @Column(name = "ACTIVE_INDICATOR")
    public Boolean getActiveIndicator() {
        return activeIndicator;
    }
    
    /**
     * 
     * @param activeIndicator activeIndicator
     */
    public void setActiveIndicator(Boolean activeIndicator) {
        this.activeIndicator = activeIndicator;
    }
    
    /**
     * 
     * @return inactiveCommentText
     */
    @Column(name = "INACTIVE_COMMENT_TEXT")
    public String getInactiveCommentText() {
        return inactiveCommentText;
    }
    
    /**
     * 
     * @param inactiveCommentText inactiveCommentText
     */
    public void setInactiveCommentText(String inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }

}
