/**
 * 
 */
package gov.nih.nci.registry.dto;

/**
 * @author Vrushali
 * 
 */
@SuppressWarnings("PMD")
public class StudyProtocolBatchDTO {
    private String uniqueTrialId;
    private String localProtocolIdentifier;
    private String nctNumber;
    private String title;
    private String trialType;
    private String primaryPurpose;
    private String primaryPurposeOtherValueSp;
    private String phase;
    private String phaseOtherValueSp;
    //Sponser 
    private String sponsorOrgName;
    private String sponsorCTEPOrgNumber;
    private String sponsorStreetAddress;
    private String sponsorCity;
    private String sponsorState;
    private String sponsorZip;
    private String sponsorCountry;
    private String sponsorEmail;
    private String sponsorPhone;
    private String sponsorTTY;
    private String sponsorFax;
    private String sponsorURL;
    
    private String responsibleParty;
    
    private String sponsorContactFName;
    private String sponsorContactMName;
    private String sponsorContactLName;
    private String sponsorContactCTEPPerNo;
    private String sponsorContactStreetAddress;
    private String sponsorContactCity;
    private String sponsorContactState;
    private String sponsorContactZip;
    private String sponsorContactCountry;
    private String sponsorContactEmail;
    private String sponsorContactPhone;
    private String sponsorContactTTY;
    private String sponsorContactFax;
    private String sponsorContactUrl;
    
    //lead org
    private String leadOrgName;
    private String leadOrgCTEPOrgNo;
    private String leadOrgStreetAddress;
    private String leadOrgCity;
    private String leadOrgState;
    private String leadOrgZip;
    private String leadOrgCountry;
    private String leadOrgEmail;
    private String leadOrgPhone;
    private String leadOrgTTY;
    private String leadOrgFax;
    private String leadOrgUrl;
    private String leadOrgType;
    //PI
    private String piFirstName;
    private String piMiddleName;
    private String piLastName;
    private String piPersonCTEPPersonNo;
    private String piStreetAddress;
    private String piCity;
    private String piState;
    private String piZip;
    private String piCountry;
    private String piEmail;
    private String piPhone;
    private String piTTY;
    private String piFax;
    private String piUrl;

    private String summ4FundingCat;
    private String summ4OrgName;
    private String summ4OrgCTEPOrgNo;
    private String summ4OrgStreetAddress;
    private String summ4City;
    private String summ4State;
    private String summ4Zip;
    private String summ4Country;
    private String summ4Email;
    private String summ4Phone;
    private String summ4TTY;
    private String summ4Fax;
    private String summ4Url;
    
    private String nihGrantFundingMechanism;
    private String nihGrantInstituteCode;
    private String nihGrantSrNumber;
    private String nihGrantNCIDivisionCode;
    private String currentTrialStatus;
    private String reasonForStudyStopped;
    private String currentTrialStatusDate;
    private String studyStartDate;
    private String studyStartDateType;
    private String primaryCompletionDate;
    private String primaryCompletionDateType;
    
    private String indType;
    private String indNumber;
    private String indGrantor;
    private String indHolderType;
    private String indNIHInstitution;
    private String indNCIDivision;
    private String indHasExpandedAccess;
    private String indExpandedAccessStatus;
    
    private String protcolDocumentFileName;
    private String irbApprovalDocumentFileName;
    private String participatinSiteDocumentFileName;
    private String informedConsentDocumentFileName;
    private String otherTrialRelDocumentFileName;
    
    private boolean validRecord;

    /**
     * . Default Constructor
     */
    public StudyProtocolBatchDTO() {
        super();
    }

    /**
     * @return the currentTrialStatus
     */
    public String getCurrentTrialStatus() {
        return currentTrialStatus;
    }

    /**
     * @param currentTrialStatus the currentTrialStatus to set
     */
    public void setCurrentTrialStatus(String currentTrialStatus) {
        this.currentTrialStatus = currentTrialStatus;
    }

    /**
     * @return the currentTrialStatusDate
     */
    public String getCurrentTrialStatusDate() {
        return currentTrialStatusDate;
    }

    /**
     * @param currentTrialStatusDate the currentTrialStatusDate to set
     */
    public void setCurrentTrialStatusDate(String currentTrialStatusDate) {
        this.currentTrialStatusDate = currentTrialStatusDate;
    }

    /**
     * @return the leadOrgCity
     */
    public String getLeadOrgCity() {
        return leadOrgCity;
    }

    /**
     * @param leadOrgCity the leadOrgCity to set
     */
    public void setLeadOrgCity(String leadOrgCity) {
        this.leadOrgCity = leadOrgCity;
    }

    /**
     * @return the leadOrgCountry
     */
    public String getLeadOrgCountry() {
        return leadOrgCountry;
    }

    /**
     * @param leadOrgCountry the leadOrgCountry to set
     */
    public void setLeadOrgCountry(String leadOrgCountry) {
        this.leadOrgCountry = leadOrgCountry;
    }

    /**
     * @return the leadOrgCTEPOrgNo
     */
    public String getLeadOrgCTEPOrgNo() {
        return leadOrgCTEPOrgNo;
    }

    /**
     * @param leadOrgCTEPOrgNo the leadOrgCTEPOrgNo to set
     */
    public void setLeadOrgCTEPOrgNo(String leadOrgCTEPOrgNo) {
        this.leadOrgCTEPOrgNo = leadOrgCTEPOrgNo;
    }

    /**
     * @return the leadOrgEmail
     */
    public String getLeadOrgEmail() {
        return leadOrgEmail;
    }

    /**
     * @param leadOrgEmail the leadOrgEmail to set
     */
    public void setLeadOrgEmail(String leadOrgEmail) {
        this.leadOrgEmail = leadOrgEmail;
    }

    /**
     * @return the leadOrgFax
     */
    public String getLeadOrgFax() {
        return leadOrgFax;
    }

    /**
     * @param leadOrgFax the leadOrgFax to set
     */
    public void setLeadOrgFax(String leadOrgFax) {
        this.leadOrgFax = leadOrgFax;
    }

    /**
     * @return the leadOrgName
     */
    public String getLeadOrgName() {
        return leadOrgName;
    }

    /**
     * @param leadOrgName the leadOrgName to set
     */
    public void setLeadOrgName(String leadOrgName) {
        this.leadOrgName = leadOrgName;
    }

    /**
     * @return the leadOrgPhone
     */
    public String getLeadOrgPhone() {
        return leadOrgPhone;
    }

    /**
     * @param leadOrgPhone the leadOrgPhone to set
     */
    public void setLeadOrgPhone(String leadOrgPhone) {
        this.leadOrgPhone = leadOrgPhone;
    }

    /**
     * @return the leadOrgState
     */
    public String getLeadOrgState() {
        return leadOrgState;
    }

    /**
     * @param leadOrgState the leadOrgState to set
     */
    public void setLeadOrgState(String leadOrgState) {
        this.leadOrgState = leadOrgState;
    }

    /**
     * @return the leadOrgStreetAddress
     */
    public String getLeadOrgStreetAddress() {
        return leadOrgStreetAddress;
    }

    /**
     * @param leadOrgStreetAddress the leadOrgStreetAddress to set
     */
    public void setLeadOrgStreetAddress(String leadOrgStreetAddress) {
        this.leadOrgStreetAddress = leadOrgStreetAddress;
    }

    /**
     * @return the leadOrgTTY
     */
    public String getLeadOrgTTY() {
        return leadOrgTTY;
    }

    /**
     * @param leadOrgTTY the leadOrgTTY to set
     */
    public void setLeadOrgTTY(String leadOrgTTY) {
        this.leadOrgTTY = leadOrgTTY;
    }

    /**
     * @return the leadOrgType
     */
    public String getLeadOrgType() {
        return leadOrgType;
    }

    /**
     * @param leadOrgType the leadOrgType to set
     */
    public void setLeadOrgType(String leadOrgType) {
        this.leadOrgType = leadOrgType;
    }

    /**
     * @return the leadOrgUrl
     */
    public String getLeadOrgUrl() {
        return leadOrgUrl;
    }

    /**
     * @param leadOrgUrl the leadOrgUrl to set
     */
    public void setLeadOrgUrl(String leadOrgUrl) {
        this.leadOrgUrl = leadOrgUrl;
    }

    /**
     * @return the leadOrgZip
     */
    public String getLeadOrgZip() {
        return leadOrgZip;
    }

    /**
     * @param leadOrgZip the leadOrgZip to set
     */
    public void setLeadOrgZip(String leadOrgZip) {
        this.leadOrgZip = leadOrgZip;
    }

    /**
     * @return the localProtocolIdentifier
     */
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }

    /**
     * @param localProtocolIdentifier the localProtocolIdentifier to set
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }

    /**
     * @param nctNumber the nctNumber to set
     */
    public void setNctNumber(String nctNumber) {
        this.nctNumber = nctNumber;
    }

    /**
     * @return the nctNumber
     */
    public String getNctNumber() {
        return nctNumber;
    }

    /**
     * @return the nihGrantFundingMechanism
     */
    public String getNihGrantFundingMechanism() {
        return nihGrantFundingMechanism;
    }

    /**
     * @param nihGrantFundingMechanism the nihGrantFundingMechanism to set
     */
    public void setNihGrantFundingMechanism(String nihGrantFundingMechanism) {
        this.nihGrantFundingMechanism = nihGrantFundingMechanism;
    }

    /**
     * @return the nihGrantInstituteCode
     */
    public String getNihGrantInstituteCode() {
        return nihGrantInstituteCode;
    }

    /**
     * @param nihGrantInstituteCode the nihGrantInstituteCode to set
     */
    public void setNihGrantInstituteCode(String nihGrantInstituteCode) {
        this.nihGrantInstituteCode = nihGrantInstituteCode;
    }

    /**
     * @return the nihGrantNCIDivisionCode
     */
    public String getNihGrantNCIDivisionCode() {
        return nihGrantNCIDivisionCode;
    }

    /**
     * @param nihGrantNCIDivisionCode the nihGrantNCIDivisionCode to set
     */
    public void setNihGrantNCIDivisionCode(String nihGrantNCIDivisionCode) {
        this.nihGrantNCIDivisionCode = nihGrantNCIDivisionCode;
    }

    /**
     * @return the nihGrantSrNumber
     */
    public String getNihGrantSrNumber() {
        return nihGrantSrNumber;
    }

    /**
     * @param nihGrantSrNumber the nihGrantSrNumber to set
     */
    public void setNihGrantSrNumber(String nihGrantSrNumber) {
        this.nihGrantSrNumber = nihGrantSrNumber;
    }

    /**
     * @return the phase
     */
    public String getPhase() {
        return phase;
    }

    /**
     * @param phase the phase to set
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }

    /**
     * @return the phaseOtherValueSp
     */
    public String getPhaseOtherValueSp() {
        return phaseOtherValueSp;
    }

    /**
     * @param phaseOtherValueSp the phaseOtherValueSp to set
     */
    public void setPhaseOtherValueSp(String phaseOtherValueSp) {
        this.phaseOtherValueSp = phaseOtherValueSp;
    }

    /**
     * @return the piCity
     */
    public String getPiCity() {
        return piCity;
    }

    /**
     * @param piCity the piCity to set
     */
    public void setPiCity(String piCity) {
        this.piCity = piCity;
    }

    /**
     * @return the piCountry
     */
    public String getPiCountry() {
        return piCountry;
    }

    /**
     * @param piCountry the piCountry to set
     */
    public void setPiCountry(String piCountry) {
        this.piCountry = piCountry;
    }

    /**
     * @return the piEmail
     */
    public String getPiEmail() {
        return piEmail;
    }

    /**
     * @param piEmail the piEmail to set
     */
    public void setPiEmail(String piEmail) {
        this.piEmail = piEmail;
    }

    /**
     * @return the piFax
     */
    public String getPiFax() {
        return piFax;
    }

    /**
     * @param piFax the piFax to set
     */
    public void setPiFax(String piFax) {
        this.piFax = piFax;
    }

    /**
     * @return the piFirstName
     */
    public String getPiFirstName() {
        return piFirstName;
    }

    /**
     * @param piFirstName the piFirstName to set
     */
    public void setPiFirstName(String piFirstName) {
        this.piFirstName = piFirstName;
    }

    /**
     * @return the piLastName
     */
    public String getPiLastName() {
        return piLastName;
    }

    /**
     * @param piLastName the piLastName to set
     */
    public void setPiLastName(String piLastName) {
        this.piLastName = piLastName;
    }

    /**
     * @return the piMiddleName
     */
    public String getPiMiddleName() {
        return piMiddleName;
    }

    /**
     * @param piMiddleName the piMiddleName to set
     */
    public void setPiMiddleName(String piMiddleName) {
        this.piMiddleName = piMiddleName;
    }

    /**
     * @return the piPersonCTEPPersonNo
     */
    public String getPiPersonCTEPPersonNo() {
        return piPersonCTEPPersonNo;
    }

    /**
     * @param piPersonCTEPPersonNo the piPersonCTEPPersonNo to set
     */
    public void setPiPersonCTEPPersonNo(String piPersonCTEPPersonNo) {
        this.piPersonCTEPPersonNo = piPersonCTEPPersonNo;
    }

    /**
     * @return the piPhone
     */
    public String getPiPhone() {
        return piPhone;
    }

    /**
     * @param piPhone the piPhone to set
     */
    public void setPiPhone(String piPhone) {
        this.piPhone = piPhone;
    }

    /**
     * @return the piState
     */
    public String getPiState() {
        return piState;
    }

    /**
     * @param piState the piState to set
     */
    public void setPiState(String piState) {
        this.piState = piState;
    }

    /**
     * @return the piStreetAddress
     */
    public String getPiStreetAddress() {
        return piStreetAddress;
    }

    /**
     * @param piStreetAddress the piStreetAddress to set
     */
    public void setPiStreetAddress(String piStreetAddress) {
        this.piStreetAddress = piStreetAddress;
    }

    /**
     * @return the piTTY
     */
    public String getPiTTY() {
        return piTTY;
    }

    /**
     * @param piTTY the piTTY to set
     */
    public void setPiTTY(String piTTY) {
        this.piTTY = piTTY;
    }

    /**
     * @return the piUrl
     */
    public String getPiUrl() {
        return piUrl;
    }

    /**
     * @param piUrl the piUrl to set
     */
    public void setPiUrl(String piUrl) {
        this.piUrl = piUrl;
    }

    /**
     * @return the piZip
     */
    public String getPiZip() {
        return piZip;
    }

    /**
     * @param piZip the piZip to set
     */
    public void setPiZip(String piZip) {
        this.piZip = piZip;
    }

    /**
     * @return the primaryCompletionDate
     */
    public String getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(String primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * @return the primaryCompletionDateType
     */
    public String getPrimaryCompletionDateType() {
        return primaryCompletionDateType;
    }

    /**
     * @param primaryCompletionDateType the primaryCompletionDateType to set
     */
    public void setPrimaryCompletionDateType(String primaryCompletionDateType) {
        this.primaryCompletionDateType = primaryCompletionDateType;
    }

    /**
     * @return the primaryPurpose
     */
    public String getPrimaryPurpose() {
        return primaryPurpose;
    }

    /**
     * @param primaryPurpose the primaryPurpose to set
     */
    public void setPrimaryPurpose(String primaryPurpose) {
        this.primaryPurpose = primaryPurpose;
    }

    /**
     * @return the primaryPurposeOtherValueSp
     */
    public String getPrimaryPurposeOtherValueSp() {
        return primaryPurposeOtherValueSp;
    }

    /**
     * @param primaryPurposeOtherValueSp the primaryPurposeOtherValueSp to set
     */
    public void setPrimaryPurposeOtherValueSp(String primaryPurposeOtherValueSp) {
        this.primaryPurposeOtherValueSp = primaryPurposeOtherValueSp;
    }

    /**
     * @return the responsibleParty
     */
    public String getResponsibleParty() {
        return responsibleParty;
    }

    /**
     * @param responsibleParty the responsibleParty to set
     */
    public void setResponsibleParty(String responsibleParty) {
        this.responsibleParty = responsibleParty;
    }

    /**
     * @return the sponsorCity
     */
    public String getSponsorCity() {
        return sponsorCity;
    }

    /**
     * @param sponsorCity the sponsorCity to set
     */
    public void setSponsorCity(String sponsorCity) {
        this.sponsorCity = sponsorCity;
    }

    /**
     * @return the sponsorContactCity
     */
    public String getSponsorContactCity() {
        return sponsorContactCity;
    }

    /**
     * @param sponsorContactCity the sponsorContactCity to set
     */
    public void setSponsorContactCity(String sponsorContactCity) {
        this.sponsorContactCity = sponsorContactCity;
    }

    /**
     * @return the sponsorContactCountry
     */
    public String getSponsorContactCountry() {
        return sponsorContactCountry;
    }

    /**
     * @param sponsorContactCountry the sponsorContactCountry to set
     */
    public void setSponsorContactCountry(String sponsorContactCountry) {
        this.sponsorContactCountry = sponsorContactCountry;
    }

    /**
     * @return the sponsorContactCTEPPerNo
     */
    public String getSponsorContactCTEPPerNo() {
        return sponsorContactCTEPPerNo;
    }

    /**
     * @param sponsorContactCTEPPerNo the sponsorContactCTEPPerNo to set
     */
    public void setSponsorContactCTEPPerNo(String sponsorContactCTEPPerNo) {
        this.sponsorContactCTEPPerNo = sponsorContactCTEPPerNo;
    }

    /**
     * @return the sponsorContactEmail
     */
    public String getSponsorContactEmail() {
        return sponsorContactEmail;
    }

    /**
     * @param sponsorContactEmail the sponsorContactEmail to set
     */
    public void setSponsorContactEmail(String sponsorContactEmail) {
        this.sponsorContactEmail = sponsorContactEmail;
    }

    /**
     * @return the sponsorContactFax
     */
    public String getSponsorContactFax() {
        return sponsorContactFax;
    }

    /**
     * @param sponsorContactFax the sponsorContactFax to set
     */
    public void setSponsorContactFax(String sponsorContactFax) {
        this.sponsorContactFax = sponsorContactFax;
    }

    /**
     * @return the sponsorContactFName
     */
    public String getSponsorContactFName() {
        return sponsorContactFName;
    }

    /**
     * @param sponsorContactFName the sponsorContactFName to set
     */
    public void setSponsorContactFName(String sponsorContactFName) {
        this.sponsorContactFName = sponsorContactFName;
    }

    /**
     * @return the sponsorContactLName
     */
    public String getSponsorContactLName() {
        return sponsorContactLName;
    }

    /**
     * @param sponsorContactLName the sponsorContactLName to set
     */
    public void setSponsorContactLName(String sponsorContactLName) {
        this.sponsorContactLName = sponsorContactLName;
    }

    /**
     * @return the sponsorContactMName
     */
    public String getSponsorContactMName() {
        return sponsorContactMName;
    }

    /**
     * @param sponsorContactMName the sponsorContactMName to set
     */
    public void setSponsorContactMName(String sponsorContactMName) {
        this.sponsorContactMName = sponsorContactMName;
    }

    /**
     * @return the sponsorContactPhone
     */
    public String getSponsorContactPhone() {
        return sponsorContactPhone;
    }

    /**
     * @param sponsorContactPhone the sponsorContactPhone to set
     */
    public void setSponsorContactPhone(String sponsorContactPhone) {
        this.sponsorContactPhone = sponsorContactPhone;
    }

    /**
     * @return the sponsorContactState
     */
    public String getSponsorContactState() {
        return sponsorContactState;
    }

    /**
     * @param sponsorContactState the sponsorContactState to set
     */
    public void setSponsorContactState(String sponsorContactState) {
        this.sponsorContactState = sponsorContactState;
    }

    /**
     * @return the sponsorContactStreetAddress
     */
    public String getSponsorContactStreetAddress() {
        return sponsorContactStreetAddress;
    }

    /**
     * @param sponsorContactStreetAddress the sponsorContactStreetAddress to set
     */
    public void setSponsorContactStreetAddress(String sponsorContactStreetAddress) {
        this.sponsorContactStreetAddress = sponsorContactStreetAddress;
    }

    /**
     * @return the sponsorContactTTY
     */
    public String getSponsorContactTTY() {
        return sponsorContactTTY;
    }

    /**
     * @param sponsorContactTTY the sponsorContactTTY to set
     */
    public void setSponsorContactTTY(String sponsorContactTTY) {
        this.sponsorContactTTY = sponsorContactTTY;
    }

    /**
     * @return the sponsorContactUrl
     */
    public String getSponsorContactUrl() {
        return sponsorContactUrl;
    }

    /**
     * @param sponsorContactUrl the sponsorContactUrl to set
     */
    public void setSponsorContactUrl(String sponsorContactUrl) {
        this.sponsorContactUrl = sponsorContactUrl;
    }

    /**
     * @return the sponsorContactZip
     */
    public String getSponsorContactZip() {
        return sponsorContactZip;
    }

    /**
     * @param sponsorContactZip the sponsorContactZip to set
     */
    public void setSponsorContactZip(String sponsorContactZip) {
        this.sponsorContactZip = sponsorContactZip;
    }

    /**
     * @return the sponsorCountry
     */
    public String getSponsorCountry() {
        return sponsorCountry;
    }

    /**
     * @param sponsorCountry the sponsorCountry to set
     */
    public void setSponsorCountry(String sponsorCountry) {
        this.sponsorCountry = sponsorCountry;
    }

    /**
     * @return the sponsorCTEPOrgNumber
     */
    public String getSponsorCTEPOrgNumber() {
        return sponsorCTEPOrgNumber;
    }

    /**
     * @param sponsorCTEPOrgNumber the sponsorCTEPOrgNumber to set
     */
    public void setSponsorCTEPOrgNumber(String sponsorCTEPOrgNumber) {
        this.sponsorCTEPOrgNumber = sponsorCTEPOrgNumber;
    }

    /**
     * @return the sponsorEmail
     */
    public String getSponsorEmail() {
        return sponsorEmail;
    }

    /**
     * @param sponsorEmail the sponsorEmail to set
     */
    public void setSponsorEmail(String sponsorEmail) {
        this.sponsorEmail = sponsorEmail;
    }

    /**
     * @return the sponsorFax
     */
    public String getSponsorFax() {
        return sponsorFax;
    }

    /**
     * @param sponsorFax the sponsorFax to set
     */
    public void setSponsorFax(String sponsorFax) {
        this.sponsorFax = sponsorFax;
    }

    /**
     * @return the sponsorOrgName
     */
    public String getSponsorOrgName() {
        return sponsorOrgName;
    }

    /**
     * @param sponsorOrgName the sponsorOrgName to set
     */
    public void setSponsorOrgName(String sponsorOrgName) {
        this.sponsorOrgName = sponsorOrgName;
    }

    /**
     * @return the sponsorPhone
     */
    public String getSponsorPhone() {
        return sponsorPhone;
    }

    /**
     * @param sponsorPhone the sponsorPhone to set
     */
    public void setSponsorPhone(String sponsorPhone) {
        this.sponsorPhone = sponsorPhone;
    }

    /**
     * @return the sponsorState
     */
    public String getSponsorState() {
        return sponsorState;
    }

    /**
     * @param sponsorState the sponsorState to set
     */
    public void setSponsorState(String sponsorState) {
        this.sponsorState = sponsorState;
    }

    /**
     * @return the sponsorStreetAddress
     */
    public String getSponsorStreetAddress() {
        return sponsorStreetAddress;
    }

    /**
     * @param sponsorStreetAddress the sponsorStreetAddress to set
     */
    public void setSponsorStreetAddress(String sponsorStreetAddress) {
        this.sponsorStreetAddress = sponsorStreetAddress;
    }

    /**
     * @return the sponsorTTY
     */
    public String getSponsorTTY() {
        return sponsorTTY;
    }

    /**
     * @param sponsorTTY the sponsorTTY to set
     */
    public void setSponsorTTY(String sponsorTTY) {
        this.sponsorTTY = sponsorTTY;
    }

    /**
     * @return the sponsorURL
     */
    public String getSponsorURL() {
        return sponsorURL;
    }

    /**
     * @param sponsorURL the sponsorURL to set
     */
    public void setSponsorURL(String sponsorURL) {
        this.sponsorURL = sponsorURL;
    }

    /**
     * @return the sponsorZip
     */
    public String getSponsorZip() {
        return sponsorZip;
    }

    /**
     * @param sponsorZip the sponsorZip to set
     */
    public void setSponsorZip(String sponsorZip) {
        this.sponsorZip = sponsorZip;
    }

    /**
     * @return the studyStartDate
     */
    public String getStudyStartDate() {
        return studyStartDate;
    }

    /**
     * @param studyStartDate the studyStartDate to set
     */
    public void setStudyStartDate(String studyStartDate) {
        this.studyStartDate = studyStartDate;
    }

    /**
     * @return the studyStartDateType
     */
    public String getStudyStartDateType() {
        return studyStartDateType;
    }

    /**
     * @param studyStartDateType the studyStartDateType to set
     */
    public void setStudyStartDateType(String studyStartDateType) {
        this.studyStartDateType = studyStartDateType;
    }

    /**
     * @return the summ4City
     */
    public String getSumm4City() {
        return summ4City;
    }

    /**
     * @param summ4City the summ4City to set
     */
    public void setSumm4City(String summ4City) {
        this.summ4City = summ4City;
    }

    /**
     * @return the summ4Country
     */
    public String getSumm4Country() {
        return summ4Country;
    }

    /**
     * @param summ4Country the summ4Country to set
     */
    public void setSumm4Country(String summ4Country) {
        this.summ4Country = summ4Country;
    }

    /**
     * @return the summ4Email
     */
    public String getSumm4Email() {
        return summ4Email;
    }

    /**
     * @param summ4Email the summ4Email to set
     */
    public void setSumm4Email(String summ4Email) {
        this.summ4Email = summ4Email;
    }

    /**
     * @return the summ4Fax
     */
    public String getSumm4Fax() {
        return summ4Fax;
    }

    /**
     * @param summ4Fax the summ4Fax to set
     */
    public void setSumm4Fax(String summ4Fax) {
        this.summ4Fax = summ4Fax;
    }

    /**
     * @return the summ4FundingCat
     */
    public String getSumm4FundingCat() {
        return summ4FundingCat;
    }

    /**
     * @param summ4FundingCat the summ4FundingCat to set
     */
    public void setSumm4FundingCat(String summ4FundingCat) {
        this.summ4FundingCat = summ4FundingCat;
    }

    /**
     * @return the summ4OrgCTEPOrgNo
     */
    public String getSumm4OrgCTEPOrgNo() {
        return summ4OrgCTEPOrgNo;
    }

    /**
     * @param summ4OrgCTEPOrgNo the summ4OrgCTEPOrgNo to set
     */
    public void setSumm4OrgCTEPOrgNo(String summ4OrgCTEPOrgNo) {
        this.summ4OrgCTEPOrgNo = summ4OrgCTEPOrgNo;
    }

    /**
     * @return the summ4OrgName
     */
    public String getSumm4OrgName() {
        return summ4OrgName;
    }

    /**
     * @param summ4OrgName the summ4OrgName to set
     */
    public void setSumm4OrgName(String summ4OrgName) {
        this.summ4OrgName = summ4OrgName;
    }

    /**
     * @return the summ4OrgStreetAddress
     */
    public String getSumm4OrgStreetAddress() {
        return summ4OrgStreetAddress;
    }

    /**
     * @param summ4OrgStreetAddress the summ4OrgStreetAddress to set
     */
    public void setSumm4OrgStreetAddress(String summ4OrgStreetAddress) {
        this.summ4OrgStreetAddress = summ4OrgStreetAddress;
    }

    /**
     * @return the summ4Phone
     */
    public String getSumm4Phone() {
        return summ4Phone;
    }

    /**
     * @param summ4Phone the summ4Phone to set
     */
    public void setSumm4Phone(String summ4Phone) {
        this.summ4Phone = summ4Phone;
    }

    /**
     * @return the summ4State
     */
    public String getSumm4State() {
        return summ4State;
    }

    /**
     * @param summ4State the summ4State to set
     */
    public void setSumm4State(String summ4State) {
        this.summ4State = summ4State;
    }

    /**
     * @return the summ4TTY
     */
    public String getSumm4TTY() {
        return summ4TTY;
    }

    /**
     * @param summ4TTY the summ4TTY to set
     */
    public void setSumm4TTY(String summ4TTY) {
        this.summ4TTY = summ4TTY;
    }

    /**
     * @return the summ4Url
     */
    public String getSumm4Url() {
        return summ4Url;
    }

    /**
     * @param summ4Url the summ4Url to set
     */
    public void setSumm4Url(String summ4Url) {
        this.summ4Url = summ4Url;
    }

    /**
     * @return the summ4Zip
     */
    public String getSumm4Zip() {
        return summ4Zip;
    }

    /**
     * @param summ4Zip the summ4Zip to set
     */
    public void setSumm4Zip(String summ4Zip) {
        this.summ4Zip = summ4Zip;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the trialType
     */
    public String getTrialType() {
        return trialType;
    }

    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }

    /**
     * @return the uniqueTrialId
     */
    public String getUniqueTrialId() {
        return uniqueTrialId;
    }

    /**
     * @param uniqueTrialId the uniqueTrialId to set
     */
    public void setUniqueTrialId(String uniqueTrialId) {
        this.uniqueTrialId = uniqueTrialId;
    }
    

    /**
     * @return the indExpandedAccessStatus
     */
    public String getIndExpandedAccessStatus() {
        return indExpandedAccessStatus;
    }

    /**
     * @param indExpandedAccessStatus the indExpandedAccessStatus to set
     */
    public void setIndExpandedAccessStatus(String indExpandedAccessStatus) {
        this.indExpandedAccessStatus = indExpandedAccessStatus;
    }

    /**
     * @return the indGrantor
     */
    public String getIndGrantor() {
        return indGrantor;
    }

    /**
     * @param indGrantor the indGrantor to set
     */
    public void setIndGrantor(String indGrantor) {
        this.indGrantor = indGrantor;
    }

    /**
     * @return the indHasExpandedAccess
     */
    public String getIndHasExpandedAccess() {
        return indHasExpandedAccess;
    }

    /**
     * @param indHasExpandedAccess the indHasExpandedAccess to set
     */
    public void setIndHasExpandedAccess(String indHasExpandedAccess) {
        this.indHasExpandedAccess = indHasExpandedAccess;
    }

    /**
     * @return the indHolderType
     */
    public String getIndHolderType() {
        return indHolderType;
    }

    /**
     * @param indHolderType the indHolderType to set
     */
    public void setIndHolderType(String indHolderType) {
        this.indHolderType = indHolderType;
    }

    /**
     * @return the indNCIDivision
     */
    public String getIndNCIDivision() {
        return indNCIDivision;
    }

    /**
     * @param indNCIDivision the indNCIDivision to set
     */
    public void setIndNCIDivision(String indNCIDivision) {
        this.indNCIDivision = indNCIDivision;
    }

    /**
     * @return the indNIHInstitution
     */
    public String getIndNIHInstitution() {
        return indNIHInstitution;
    }

    /**
     * @param indNIHInstitution the indNIHInstitution to set
     */
    public void setIndNIHInstitution(String indNIHInstitution) {
        this.indNIHInstitution = indNIHInstitution;
    }

    /**
     * @return the indNumber
     */
    public String getIndNumber() {
        return indNumber;
    }

    /**
     * @param indNumber the indNumber to set
     */
    public void setIndNumber(String indNumber) {
        this.indNumber = indNumber;
    }

    /**
     * @return the indType
     */
    public String getIndType() {
        return indType;
    }

    /**
     * @param indType the indType to set
     */
    public void setIndType(String indType) {
        this.indType = indType;
    }

    /**
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocumentFileName;
    }

    /**
     * @param informedConsentDocumentFileName the informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(
            String informedConsentDocumentFileName) {
        this.informedConsentDocumentFileName = informedConsentDocumentFileName;
    }

    /**
     * @return the irbApprovalDocumentFileName
     */
    public String getIrbApprovalDocumentFileName() {
        return irbApprovalDocumentFileName;
    }

    /**
     * @param irbApprovalDocumentFileName the irbApprovalDocumentFileName to set
     */
    public void setIrbApprovalDocumentFileName(String irbApprovalDocumentFileName) {
        this.irbApprovalDocumentFileName = irbApprovalDocumentFileName;
    }

    /**
     * @return the otherTrialRelDocumentFileName
     */
    public String getOtherTrialRelDocumentFileName() {
        return otherTrialRelDocumentFileName;
    }

    /**
     * @param otherTrialRelDocumentFileName the otherTrialRelDocumentFileName to set
     */
    public void setOtherTrialRelDocumentFileName(
            String otherTrialRelDocumentFileName) {
        this.otherTrialRelDocumentFileName = otherTrialRelDocumentFileName;
    }

    /**
     * @return the participatinSiteDocumentFileName
     */
    public String getParticipatinSiteDocumentFileName() {
        return participatinSiteDocumentFileName;
    }

    /**
     * @param participatinSiteDocumentFileName the participatinSiteDocumentFileName to set
     */
    public void setParticipatinSiteDocumentFileName(
            String participatinSiteDocumentFileName) {
        this.participatinSiteDocumentFileName = participatinSiteDocumentFileName;
    }

    /**
     * @return the protcolDocumentFileName
     */
    public String getProtcolDocumentFileName() {
        return protcolDocumentFileName;
    }

    /**
     * @param protcolDocumentFileName the protcolDocumentFileName to set
     */
    public void setProtcolDocumentFileName(String protcolDocumentFileName) {
        this.protcolDocumentFileName = protcolDocumentFileName;
    }

    /**
     * @return the validRecord
     */
    public boolean isValidRecord() {
        return validRecord;
    }

    /**
     * @param validRecord the validRecord to set
     */
    public void setValidRecord(boolean validRecord) {
        this.validRecord = validRecord;
    }

    /**
     * @return the reasonForStudyStopped
     */
    public String getReasonForStudyStopped() {
        return reasonForStudyStopped;
    }

    /**
     * @param reasonForStudyStopped the reasonForStudyStopped to set
     */
    public void setReasonForStudyStopped(String reasonForStudyStopped) {
        this.reasonForStudyStopped = reasonForStudyStopped;
    }

}
