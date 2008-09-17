package gov.nih.nci.pa.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import gov.nih.nci.pa.enums.InstitutionCode;
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
    private StudyProtocol studyProtocol;
    private String fundingMechanismCode;
    private InstitutionCode institutionCode;
    private String fundingTypeCode;
    private MonitorCode monitorCode;
    private Timestamp suffixgrantYear;
    private String suffixOther;
    private String serialNumber;
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
    /**
     * @return fundingMechanismCode
     */
    @Column(name = "funding_mechanism_code")
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
     * @return institutionCode
     */
    @Column(name = "nihinstitute_code")
    @Enumerated(EnumType.STRING)
    public InstitutionCode getInstitutionCode() {
        return institutionCode;
    }
    /**
     * @return fundingTypeCode
     */
    @Column(name = "fundig_type_code")
    public String getFundingTypeCode() {
        return fundingTypeCode;
    }
    /**
     * @return monitorCode
     */
    @Column(name = "ncidivision_program_code")
    @Enumerated(EnumType.STRING)
    public MonitorCode getMonitorCode() {
        return monitorCode;
    }
    /**
     * @return suffixgrantYear
     */
    @Column(name = "suffix_grant_year")
    public Timestamp getSuffixgrantYear() {
        return suffixgrantYear;
    }
    /**
     * @return suffixOther
     */
    @Column(name = "suffix_other")
    public String getSuffixOther() {
        return suffixOther;
    }
    /**
     * @param institutionCode institutionCode
     */
    public void setInstitutionCode(InstitutionCode institutionCode) {
        this.institutionCode = institutionCode;
    }
    /**
     * @param fundingTypeCode fundingTypeCode
     */
    public void setFundingTypeCode(String fundingTypeCode) {
        this.fundingTypeCode = fundingTypeCode;
    }
    /**
     * @param monitorCode monitorCode
     */
    public void setMonitorCode(MonitorCode monitorCode) {
        this.monitorCode = monitorCode;
    }
    /**
     * @param suffixgrantYear suffixgrantYear
     */
    public void setSuffixgrantYear(Timestamp suffixgrantYear) {
        this.suffixgrantYear = suffixgrantYear;
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
    @Column(name = "serial_number")
    public String getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber serialNumber
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }      
   
}
