/**
 *
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.NotEmpty;

/**
 * @author Vrushali
 *
 */
public class TrialDTO extends BaseTrialDTO {
    
    /**
     * PI responsible party type. 
     */
    public static final String RESPONSIBLE_PARTY_TYPE_PI = "PI";
    
    /**
     * Sponsor responsible party type. 
     */
    public static final String RESPONSIBLE_PARTY_TYPE_SPONSOR = "sponsor";

    private String accrualReportingMethodCode;
    private String piIdentifier;
    private String piName;
    private String sponsorName;
    private String sponsorIdentifier;
    private String responsiblePartyType;
    private String responsiblePersonName;
    private String responsiblePersonIdentifier;
    private String contactPhone;
    private String contactEmail;

    private String statusCode;
    private String statusDate;
    private String reason;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;
    private String localAmendmentNumber;
    private String amendmentDate;
    private String programCodeText;
    private String responsibleGenericContactName;

    private List<PaOrganizationDTO> collaborators;
    private List<PaOrganizationDTO> participatingSites;
    private List<CountryRegAuthorityDTO> countryList = new ArrayList<CountryRegAuthorityDTO>();
    private List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
    private List<TrialIndIdeDTO> indIdeUpdateDtos;
    private List<TrialFundingWebDTO> fundingAddDtos;
    private List<TrialIndIdeDTO> indIdeAddDtos;
    private String lst = null;
    private String selectedRegAuth = null;

    private String fdaRegulatoryInformationIndicator;
    private String section801Indicator;
    private String delayedPostingIndicator;
    private String dataMonitoringCommitteeAppointedIndicator;
    private String ctepIdentifier;
    private String dcpIdentifier;
    private String contactPhoneExtn;
    private String nciDesignedCancerCenter;

    private String trialOversgtAuthCountryName;
    private String trialOversgtAuthOrgName;
    private boolean xmlRequired = true;
    private List<Ii> secondaryIdentifierList;
    private List<Ii> secondaryIdentifierAddList;
    private String assignedIdentifier;

    /**
     * Constructor.
     */
    public TrialDTO() {
        super();
        collaborators = new ArrayList<PaOrganizationDTO>();
        participatingSites = new ArrayList<PaOrganizationDTO>();
        indIdeUpdateDtos = new ArrayList<TrialIndIdeDTO>();
        fundingAddDtos = new ArrayList<TrialFundingWebDTO>();
        indIdeAddDtos = new ArrayList<TrialIndIdeDTO>();
        secondaryIdentifierList = new ArrayList<Ii>();
        secondaryIdentifierAddList = new ArrayList<Ii>();
    }

    /**
     * @return the accrualReportingMethodCode
     */
    public String getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }

    /**
     * @param accrualReportingMethodCode the accrualReportingMethodCode to set
     */
    public void setAccrualReportingMethodCode(String accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }

    /**
     * @return the piIdentifier
     */
    @NotEmpty(message = "error.submit.leadPrincipalInvestigator")
    public String getPiIdentifier() {
        return piIdentifier;
    }

    /**
     * @param piIdentifier the piIdentifier to set
     */
    public void setPiIdentifier(String piIdentifier) {
        this.piIdentifier = piIdentifier;
    }

    /**
     * @return the piName
     */
    public String getPiName() {
        return piName;
    }

    /**
     * @param piName the piName to set
     */
    public void setPiName(String piName) {
        this.piName = piName;
    }

    /**
     * @return the sponsorName
     */
    public String getSponsorName() {
        return sponsorName;
    }

    /**
     * @param sponsorName the sponsorName to set
     */
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    /**
     * @return the sponsorIdentifier
     */
    public String getSponsorIdentifier() {
        return sponsorIdentifier;
    }

    /**
     * @param sponsorIdentifier the sponsorIdentifier to set
     */
    public void setSponsorIdentifier(String sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }

    /**
     * @return the responsiblePartyType
     */
    public String getResponsiblePartyType() {
        return responsiblePartyType;
    }

    /**
     * @param responsiblePartyType the responsiblePartyType to set
     */
    public void setResponsiblePartyType(String responsiblePartyType) {
        this.responsiblePartyType = responsiblePartyType;
    }

    /**
     * @return the responsiblePersonName
     */
    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }

    /**
     * @param responsiblePersonName the responsiblePersonName to set
     */
    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }

    /**
     * @return the responsiblePersonIdentifier
     */
    public String getResponsiblePersonIdentifier() {
        return responsiblePersonIdentifier;
    }

    /**
     * @param responsiblePersonIdentifier the responsiblePersonIdentifier to set
     */
    public void setResponsiblePersonIdentifier(String responsiblePersonIdentifier) {
        this.responsiblePersonIdentifier = responsiblePersonIdentifier;
    }

    /**
     * @return the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the statusCode
     */
    @NotEmpty(message = "error.submit.statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusDate
     */
    @NotEmpty(message = "error.submit.statusDate")
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the startDate
     */
    @NotEmpty(message = "error.submit.startDate")
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the completionDate
     */
    @NotEmpty(message = "error.submit.completionDate")
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return the startDateType
     */
    @NotEmpty(message = "error.submit.dateType")
    public String getStartDateType() {
        return startDateType;
    }

    /**
     * @param startDateType the startDateType to set
     */
    public void setStartDateType(String startDateType) {
        this.startDateType = startDateType;
    }

    /**
     * @return the completionDateType
     */
    @NotEmpty(message = "error.submit.dateType")
    public String getCompletionDateType() {
        return completionDateType;
    }

    /**
     * @param completionDateType the completionDateType to set
     */
    public void setCompletionDateType(String completionDateType) {
        this.completionDateType = completionDateType;
    }

    /**
     * @return the localAmendmentNumber
     */
    public String getLocalAmendmentNumber() {
        return localAmendmentNumber;
    }

    /**
     * @param localAmendmentNumber the localAmendmentNumber to set
     */
    public void setLocalAmendmentNumber(String localAmendmentNumber) {
        this.localAmendmentNumber = localAmendmentNumber;
    }

    /**
     * @return the amendmentDate
     */
    public String getAmendmentDate() {
        return amendmentDate;
    }

    /**
     * @param amendmentDate the amendmentDate to set
     */
    public void setAmendmentDate(String amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    /**
     * @return the programCodeText
     */
    public String getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(String programCodeText) {
        this.programCodeText = programCodeText;
    }

    /**
     * @return the responsibleGenericContactName
     */
    public String getResponsibleGenericContactName() {
        return responsibleGenericContactName;
    }

    /**
     * @param responsibleGenericContactName the responsibleGenericContactName to set
     */
    public void setResponsibleGenericContactName(String responsibleGenericContactName) {
        this.responsibleGenericContactName = responsibleGenericContactName;
    }

    /**
     * @return the collaborators
     */
    public List<PaOrganizationDTO> getCollaborators() {
        return collaborators;
    }

    /**
     * @param collaborators the collaborators to set
     */
    public void setCollaborators(List<PaOrganizationDTO> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * @return the participatingSites
     */
    public List<PaOrganizationDTO> getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(List<PaOrganizationDTO> participatingSites) {
        this.participatingSites = participatingSites;
    }

    /**
     * @return the countryList
     */
    public List<CountryRegAuthorityDTO> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<CountryRegAuthorityDTO> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the regIdAuthOrgList
     */
    public List<RegulatoryAuthOrgDTO> getRegIdAuthOrgList() {
        return regIdAuthOrgList;
    }

    /**
     * @param regIdAuthOrgList the regIdAuthOrgList to set
     */
    public void setRegIdAuthOrgList(List<RegulatoryAuthOrgDTO> regIdAuthOrgList) {
        this.regIdAuthOrgList = regIdAuthOrgList;
    }

    /**
     * @return the lst
     */
    public String getLst() {
        return lst;
    }

    /**
     * @param lst the lst to set
     */
    public void setLst(String lst) {
        this.lst = lst;
    }

    /**
     * @return the selectedRegAuth
     */
    public String getSelectedRegAuth() {
        return selectedRegAuth;
    }

    /**
     * @param selectedRegAuth the selectedRegAuth to set
     */
    public void setSelectedRegAuth(String selectedRegAuth) {
        this.selectedRegAuth = selectedRegAuth;
    }

    /**
     * @return the indIdeUpdateDtos
     */
    public List<TrialIndIdeDTO> getIndIdeUpdateDtos() {
        return indIdeUpdateDtos;
    }

    /**
     * @param indIdeUpdateDtos the indIdeUpdateDtos to set
     */
    public void setIndIdeUpdateDtos(List<TrialIndIdeDTO> indIdeUpdateDtos) {
        this.indIdeUpdateDtos = indIdeUpdateDtos;
    }

    /**
     * @return the fundingAddDtos
     */
    public List<TrialFundingWebDTO> getFundingAddDtos() {
        return fundingAddDtos;
    }

    /**
     * @param fundingAddDtos the fundingAddDtos to set
     */
    public void setFundingAddDtos(List<TrialFundingWebDTO> fundingAddDtos) {
        this.fundingAddDtos = fundingAddDtos;
    }

    /**
     * @return the indIdeAddDtos
     */
    public List<TrialIndIdeDTO> getIndIdeAddDtos() {
        return indIdeAddDtos;
    }

    /**
     * @param indIdeAddDtos the indIdeAddDtos to set
     */
    public void setIndIdeAddDtos(List<TrialIndIdeDTO> indIdeAddDtos) {
        this.indIdeAddDtos = indIdeAddDtos;
    }

    /**
     * @return the fdaRegulatoryInformationIndicator
     */
    public String getFdaRegulatoryInformationIndicator() {
        return fdaRegulatoryInformationIndicator;
    }

    /**
     * @param fdaRegulatoryInformationIndicator the fdaRegulatoryInformationIndicator to set
     */
    public void setFdaRegulatoryInformationIndicator(String fdaRegulatoryInformationIndicator) {
        this.fdaRegulatoryInformationIndicator = fdaRegulatoryInformationIndicator;
    }

    /**
     * @return the section801Indicator
     */
    public String getSection801Indicator() {
        return section801Indicator;
    }

    /**
     * @param section801Indicator the section801Indicator to set
     */
    public void setSection801Indicator(String section801Indicator) {
        this.section801Indicator = section801Indicator;
    }

    /**
     * @return the delayedPostingIndicator
     */
    public String getDelayedPostingIndicator() {
        return delayedPostingIndicator;
    }

    /**
     * @param delayedPostingIndicator the delayedPostingIndicator to set
     */
    public void setDelayedPostingIndicator(String delayedPostingIndicator) {
        this.delayedPostingIndicator = delayedPostingIndicator;
    }

    /**
     * @return the dataMonitoringCommitteeAppointedIndicator
     */
    public String getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator to set
     */
    public void setDataMonitoringCommitteeAppointedIndicator(String dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @param ctepIdentifier the ctepIdentifier to set
     */
    public void setCtepIdentifier(String ctepIdentifier) {
        this.ctepIdentifier = ctepIdentifier;
    }

    /**
     * @return the ctepIdentifier
     */
    public String getCtepIdentifier() {
        return ctepIdentifier;
    }

    /**
     * @param dcpIdentifier the dcpIdentifier to set
     */
    public void setDcpIdentifier(String dcpIdentifier) {
        this.dcpIdentifier = dcpIdentifier;
    }

    /**
     * @return the dcpIdentifier
     */
    public String getDcpIdentifier() {
        return dcpIdentifier;
    }

    /**
     * @return the contactPhoneExtn
     */
    public String getContactPhoneExtn() {
        return contactPhoneExtn;
    }

    /**
     * @param contactPhoneExtn the contactPhoneExtn to set
     */
    public void setContactPhoneExtn(String contactPhoneExtn) {
        this.contactPhoneExtn = contactPhoneExtn;
    }

    /**
     * @param nciDesignedCancerCenter the nciDesignedCancerCenter to set
     */
    public void setNciDesignedCancerCenter(String nciDesignedCancerCenter) {
        this.nciDesignedCancerCenter = nciDesignedCancerCenter;
    }

    /**
     * @return the nciDesignedCancerCenter
     */
    public String getNciDesignedCancerCenter() {
        return nciDesignedCancerCenter;
    }

    /**
     * @param trialOversgtAuthCountry the trialOversgtAuthCountry to set
     */
    public void setTrialOversgtAuthCountryName(String trialOversgtAuthCountry) {
        this.trialOversgtAuthCountryName = trialOversgtAuthCountry;
    }

    /**
     * @return the trialOversgtAuthCountry
     */
    public String getTrialOversgtAuthCountryName() {
        return trialOversgtAuthCountryName;
    }

    /**
     * @param trialOversgtAuthOrgName the trialOversgtAuthOrgName to set
     */
    public void setTrialOversgtAuthOrgName(String trialOversgtAuthOrgName) {
        this.trialOversgtAuthOrgName = trialOversgtAuthOrgName;
    }

    /**
     * @return the trialOversgtAuthOrgName
     */
    public String getTrialOversgtAuthOrgName() {
        return trialOversgtAuthOrgName;
    }

    /**
     * @return the xml required
     */
    public boolean isXmlRequired() {
        return xmlRequired;
    }

    /**
     * @param xmlRequired the new xml required
     */
    public void setXmlRequired(boolean xmlRequired) {
        this.xmlRequired = xmlRequired;
    }

    /**
     * @return the secondaryIdentifierList
     */
    public List<Ii> getSecondaryIdentifierList() {
        return secondaryIdentifierList;
    }

    /**
     * @param secondaryIdentifierList the secondaryIdentifierList to set
     */
    public void setSecondaryIdentifierList(List<Ii> secondaryIdentifierList) {
        this.secondaryIdentifierList = secondaryIdentifierList;
    }

    /**
     * @return the assignedIdentifier
     */
    @Override
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }

    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    @Override
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }

    /**
     * @return the secondaryIdentifierAddList
     */
    public List<Ii> getSecondaryIdentifierAddList() {
        return secondaryIdentifierAddList;
    }

    /**
     * @param secondaryIdentifierAddList the secondaryIdentifierAddList to set
     */
    public void setSecondaryIdentifierAddList(List<Ii> secondaryIdentifierAddList) {
        this.secondaryIdentifierAddList = secondaryIdentifierAddList;
    }
    
    /**
     * Returns the regulatory authority country.
     * @return the regulatory authority country.
     */
    public CountryRegAuthorityDTO getRegAuthorityCountry() {
        if (lst != null) {
            Long id = Long.valueOf(lst);
            for (CountryRegAuthorityDTO country : countryList) {
                if (country.getId().equals(id)) {
                    return country;
                }
            }
        }
        return null;
    }

    /**
     * Returns the selected authority organization.
     * @return the selected authority organization.
     */
    public RegulatoryAuthOrgDTO getRegAuthorityOrg() {
        if (selectedRegAuth != null) {
            Long id = Long.valueOf(selectedRegAuth);
            for (RegulatoryAuthOrgDTO org : regIdAuthOrgList) {
                if (org.getId().equals(id)) {
                    return org;
                }
            }
        }
        return null;
    }
}
