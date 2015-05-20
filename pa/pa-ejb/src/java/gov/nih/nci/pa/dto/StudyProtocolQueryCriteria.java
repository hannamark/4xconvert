package gov.nih.nci.pa.dto;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.service.search.StudyProtocolOptions.MilestoneFilter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;


/**
 * Class used to hold criteria used in searching study protocols.
 * 
 * <pre>
 * Attr.                         Corresponding bo attribute
 * =====                         ==========================
 * studyProtocolId               domain.StudyProtocol.id
 * nciIdentifier                 domain.DocumentIdentification.NCI
 * longTitleText                 domain.Document.officialTitle
 * leadOrganizationId            domain.Organization.id
 * phaseCode                     domain.StudyProtocol.phaseCode
 * studyStatusCode               domain.StudyOverallStatus
 * documentWrokflowStatus        domain.DocumentWorkflowStatus
 * principalInvestigatorId       domain.StudyContact.clinicalResearchStaff.person.id
 * </pre>
 * 
 * @author Hugh Reinhart
 * @author Naveen Amiruddin
 * 
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class StudyProtocolQueryCriteria implements Serializable {

    /**
     * All types of identifiers.
     */
    public static final String ALL = "All";

    private static final long serialVersionUID = 1047596217516203744L;
    
    private Long studyProtocolId;
    private String studyProtocolType;
    private String studySubtypeCode;
    private String anyTypeIdentifier;
    private String nciIdentifier;
    private String dcpIdentifier;
    private String ccrIdentifier;
    private String ctepIdentifier;
    private String nctNumber;
    private String officialTitle;
    private String otherIdentifier;
    private String leadOrganizationTrialIdentifier;
    private String phaseAdditionalQualifierCode;
    private String studyStatusCode;
    private List<String> studyStatusCodeList = new ArrayList<String>();
    private String principalInvestigatorId;
    private String primaryPurposeCode;
    private String identifierType;
    private String organizationType;
    private String userLastCreated;
    private Boolean excludeRejectProtocol;
    private Boolean excludeTerminatedTrials;
    // for Registry trial search
    private Boolean myTrialsOnly;    
    private List<String> studyMilestone = new ArrayList<String>();
    private String submissionType;
    private List<SubmissionTypeCode> trialSubmissionTypes = new ArrayList<SubmissionTypeCode>();
    private Boolean nciSponsored;

    // for Inbox Processing
    private Boolean inBoxProcessing;
    private boolean studyLockedBy;
    private String trialCategory;
    private String ctepDcpCategory;
    private String holdStatus;
    private Long userId;
    
    private String ctgovXmlRequiredIndicator;

    private Long summ4FundingSourceId;
    private String summ4FundingSourceTypeCode;

    private String countryName;
    private String city;
    
    private List<Long> bioMarkerIds = new ArrayList<Long>();
    private List<String> bioMarkerNames = new ArrayList<String>();
    private List<String> documentWorkflowStatusCodes = new ArrayList<String>();
    private List<Long> interventionIds = new ArrayList<Long>();
    private List<Long> interventionAlternateNameIds = new ArrayList<Long>();
    private List<String> interventionTypes = new ArrayList<String>();
    private List<Long> leadOrganizationIds = new ArrayList<Long>();
    private List<Long> participatingSiteIds = new ArrayList<Long>();
    private List<Long> pdqDiseases = new ArrayList<Long>();
    private List<String> phaseCodes = new ArrayList<String>();
    private List<String> states = new ArrayList<String>();
    private List<Long> summary4AnatomicSites = new ArrayList<Long>();
    private List<OnholdReasonCode> onholdReasons = new ArrayList<OnholdReasonCode>();
    private StudySourceCode studySource;
    
    private String familyId = "0";
    private String participatingSiteFamilyId = "0";
    private String submitter;
    private Boolean checkedOut;
    private List<String> processingPriority = new ArrayList<String>();
    private Date submittedOnOrAfter;
    private Date submittedOnOrBefore;  
    private String submitterAffiliateOrgId;
    private List<String> submitterAffiliateOrgNameList = new ArrayList<String>();
    private Boolean holdRecordExists;
    private MilestoneCode currentOrPreviousMilestone;          
    private List<MilestoneFilter> milestoneFilters = new ArrayList<MilestoneFilter>();
    private Boolean ctroOverride;
    private Long assignedUserId;
    
    /**
     * @return the inBoxProcessing
     */
    public Boolean isInBoxProcessing() {
        return inBoxProcessing;
    }

    /**
     * @param inBoxProcessing the inBoxProcessing to set
     */
    public void setInBoxProcessing(Boolean inBoxProcessing) {
        this.inBoxProcessing = inBoxProcessing;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * 
     * @return nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    /**
     * 
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    /**
     * 
     * @return officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * 
     * @return leadOrganizationIds
     */
    public List<Long> getLeadOrganizationIds() {
        return leadOrganizationIds;
    }

    /**
     * 
     * @param leadOrganizationIds leadOrganizationIds
     */
    public void setLeadOrganizationIds(List<Long> leadOrganizationIds) {
        this.leadOrganizationIds = cleanupIds(leadOrganizationIds);
    }

    /**
     * 
     * @return the other identifier
     */
    public String getOtherIdentifier() {
        return otherIdentifier;
    }

    /**
     * 
     * @param otherId identifier
     */
    public void setOtherIdentifier(String otherId) {
        this.otherIdentifier = otherId;
    }

    /**
     * 
     * @return leadOrganizationTrialIdentifier
     */
    public String getLeadOrganizationTrialIdentifier() {
        return leadOrganizationTrialIdentifier;
    }

    /**
     * 
     * @param leadOrganizationTrialIdentifier leadOrganizationTrialIdentifier
     */
    public void setLeadOrganizationTrialIdentifier(String leadOrganizationTrialIdentifier) {
        this.leadOrganizationTrialIdentifier = leadOrganizationTrialIdentifier;
    }

    /**
     * @param participatingSiteIds the participatingSiteIds to set
     */
    public void setParticipatingSiteIds(List<Long> participatingSiteIds) {
        this.participatingSiteIds = cleanupIds(participatingSiteIds);
    }

    /**
     * @return the participatingSiteIds
     */
    public List<Long> getParticipatingSiteIds() {
        return participatingSiteIds;
    }

    /**
     * 
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        List<String> code = new ArrayList<String>();
        code.add(phaseCode);
        setPhaseCodes(code);
    }

    /**
     * 
     * @return studyStatusCode
     */
    public String getStudyStatusCode() {
        return studyStatusCode;
    }

    /**
     * 
     * @param studyStatusCode studyStatusCode
     */
    public void setStudyStatusCode(String studyStatusCode) {
        this.studyStatusCode = studyStatusCode;
    }

    /**
     * 
     * @return documentWorkflowStatusCodes
     */
    public List<String> getDocumentWorkflowStatusCodes() {
        return documentWorkflowStatusCodes;
    }

    /**
     * 
     * @param documentWorkflowStatusCodes documentWorkflowStatusCodes
     */
    public void setDocumentWorkflowStatusCodes(List<String> documentWorkflowStatusCodes) {
        this.documentWorkflowStatusCodes = cleanupNames(documentWorkflowStatusCodes);
    }

    /**
     * 
     * @return principalInvestigatorId
     */
    public String getPrincipalInvestigatorId() {
        return principalInvestigatorId;
    }

    /**
     * 
     * @param principalInvestigatorId principalInvestigatorId
     */
    public void setPrincipalInvestigatorId(String principalInvestigatorId) {
        this.principalInvestigatorId = principalInvestigatorId;
    }

    /**
     * 
     * @return primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }

    /**
     * 
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }

    /**
     * @return the identifierType
     */
    public String getIdentifierType() {
        return identifierType;
    }

    /**
     * @param identifierType the identifierType to set
     */
    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    /**
     * @return the organizationType
     */
    public String getOrganizationType() {
        return organizationType;
    }

    /**
     * @param organizationType the organizationType to set
     */
    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    /**
     * @return the userLastCreated
     */
    public String getUserLastCreated() {
        return userLastCreated;
    }

    /**
     * @param userLastCreated the userLastCreated to set
     */
    public void setUserLastCreated(String userLastCreated) {
        this.userLastCreated = userLastCreated;
    }

    /**
     * 
     * @return excludeRejectProtocol excludeRejectProtocol
     */
    public Boolean isExcludeRejectProtocol() {
        return excludeRejectProtocol;
    }

    /**
     * 
     * @param excludeRejectProtocol excludeRejectProtocol
     */
    public void setExcludeRejectProtocol(Boolean excludeRejectProtocol) {
        this.excludeRejectProtocol = excludeRejectProtocol;
    }

    /**
     * @return the myTrialsOnly
     */
    public Boolean isMyTrialsOnly() {
        return myTrialsOnly;
    }

    /**
     * @param studyMilestone
     *            the studyMilestone to set
     */
    public void setStudyMilestone(List<String> studyMilestone) {
        this.studyMilestone = studyMilestone;
        CollectionUtils.filter(studyMilestone, new Predicate() {
            public boolean evaluate(Object obj) {
                return !StringUtils.EMPTY.equals(obj);
            }
        });
    }

    /**
     * @return the studyMilestone
     */
    public List<String> getStudyMilestone() {
        return studyMilestone;
    }

    /**
     * @param myTrialsOnly the myTrialsOnly to set
     */
    public void setMyTrialsOnly(Boolean myTrialsOnly) {
        this.myTrialsOnly = myTrialsOnly;
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
     * @return the submissionType
     */
    public String getSubmissionType() {
        return submissionType;
    }

    /**
     * @param submissionType the submissionType to set
     */
    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    /**
     * @return the getStudyLockedBy
     */
    public boolean isStudyLockedBy() {
        return studyLockedBy;
    }

    /**
     * @param studyLockedBy the studyLockedBy to set
     */
    public void setStudyLockedBy(boolean studyLockedBy) {
        this.studyLockedBy = studyLockedBy;
    }

    /**
     * @return the trialCategory
     */
    public String getTrialCategory() {
        return trialCategory;
    }

    /**
     * @param trialCategory the trialCategory to set
     */
    public void setTrialCategory(String trialCategory) {
        this.trialCategory = trialCategory;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode to set
     */
    public void setPhaseAdditionalQualifierCode(String phaseAdditionalQualifierCode) {
        this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }

    /**
     * @return the phaseAdditionalQualifierCode
     */
    public String getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }

    /**
     * @return the dcpIdentifier
     */
    public String getDcpIdentifier() {
        return dcpIdentifier;
    }

    /**
     * @param dcpIdentifier the dcpIdentifier to set
     */
    public void setDcpIdentifier(String dcpIdentifier) {
        this.dcpIdentifier = dcpIdentifier;
    }

    /**
     * @return the ctepIdentifier
     */
    public String getCtepIdentifier() {
        return ctepIdentifier;
    }

    /**
     * @param ctepIdentifier the ctepIdentifier to set
     */
    public void setCtepIdentifier(String ctepIdentifier) {
        this.ctepIdentifier = ctepIdentifier;
    }

    /**
     * @return the summ4FundingSourceId
     */
    public Long getSumm4FundingSourceId() {
        return summ4FundingSourceId;
    }

    /**
     * Note that this is the PA Db Org Id.
     * @param summ4FundingSourceId the summ4FundingSourceId to set
     */
    public void setSumm4FundingSourceId(Long summ4FundingSourceId) {
        this.summ4FundingSourceId = summ4FundingSourceId;
    }

    /**
     * @return the summ4FundingSourceTypeCode
     */
    public String getSumm4FundingSourceTypeCode() {
        return summ4FundingSourceTypeCode;
    }

    /**
     * @param summ4FundingSourceTypeCode the summ4FundingSourceTypeCode to set
     */
    public void setSumm4FundingSourceTypeCode(String summ4FundingSourceTypeCode) {
        this.summ4FundingSourceTypeCode = summ4FundingSourceTypeCode;
    }

    /**
     * @return the phaseCodes
     */
    public List<String> getPhaseCodes() {
        return phaseCodes;
    }

    /**
     * @param phaseCodes the phaseCodes to set
     */
    public void setPhaseCodes(List<String> phaseCodes) {
        this.phaseCodes = cleanupNames(phaseCodes);
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the states
     */
    public List<String> getStates() {
        return states;
    }

    /**
     * @param states the states to set
     */
    public void setStates(List<String> states) {
        this.states = cleanupNames(states);
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the summary4AnatomicSites
     */
    public List<Long> getSummary4AnatomicSites() {
        return summary4AnatomicSites;
    }

    /**
     * @param summary4AnatomicSiteIds the summary4AnatomicSites to set
     */
    public void setSummary4AnatomicSites(List<Long> summary4AnatomicSiteIds) {
        summary4AnatomicSites = cleanupIds(summary4AnatomicSiteIds);
    }
    
    /**
     * @param summary4AnatomicSiteIds
     *            the summary4AnatomicSites to set
     */
    @SuppressWarnings("unchecked")
    public void setSummary4AnatomicSitesAsStrings(
            final List<String> summary4AnatomicSiteIds) {
        setSummary4AnatomicSites(listOfStringToListOfLongs(summary4AnatomicSiteIds));
    }

    /**
     * @return the bioMarkerIds
     */
    public List<Long> getBioMarkerIds() {
        return bioMarkerIds;
    }

    /**
     * @param bioMarkerIds the bioMarkers to set
     */
    public void setBioMarkerIds(List<Long> bioMarkerIds) {
        this.bioMarkerIds = cleanupIds(bioMarkerIds);
    }

    /**
     * @return the bioMarkerNames
     */
    public List<String> getBioMarkerNames() {
        return bioMarkerNames;
    }

    /**
     * @param bioMarkerNames the bioMarkerNames to set
     */
    public void setBioMarkerNames(List<String> bioMarkerNames) {
        this.bioMarkerNames = cleanupNames(bioMarkerNames);
    }

    /**
     * @return the pdqDiseases
     */
    public List<Long> getPdqDiseases() {
        return pdqDiseases;
    }

    /**
     * @param pdqDiseaseIds the pdqDiseases to set
     */
    public void setPdqDiseases(List<Long> pdqDiseaseIds) {
        pdqDiseases = cleanupIds(pdqDiseaseIds);
    }
    
    /**
     * @param pdqDiseaseIds
     *            pdqDiseaseIds
     */
    @SuppressWarnings("unchecked")
    public void setPdqDiseasesAsStrings(
            final List<String> pdqDiseaseIds) {
        setPdqDiseases(listOfStringToListOfLongs(pdqDiseaseIds));
    }

    /**
     * @param listOfStrings
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Long> listOfStringToListOfLongs(
            final List<String> listOfStrings) {
        return (List<Long>) CollectionUtils.collect(
                listOfStrings, new Transformer() {
                    @Override
                    public Object transform(Object str) {
                        return Long.valueOf(str.toString());
                    }
                });
    }

    /**
     * @return the interventionIds
     */
    public List<Long> getInterventionIds() {
        return interventionIds;
    }

    /**
     * @param interventionIds the interventionIds to set
     */
    public void setInterventionIds(List<Long> interventionIds) {
        this.interventionIds = cleanupIds(interventionIds);
    }
    
    /**
     * @param interventions
     *            the summary4AnatomicSites to set
     */
    @SuppressWarnings("unchecked")
    public void setInterventionIdsAsStrings(final List<String> interventions) {
        setInterventionIds(listOfStringToListOfLongs(interventions));
    }


    /**
     * @return the interventionAlternateNameIds
     */
    public List<Long> getInterventionAlternateNameIds() {
        return interventionAlternateNameIds;
    }

    /**
     * @param interventionAlternateNameIds the interventionAlternateNameIds to set
     */
    public void setInterventionAlternateNameIds(List<Long> interventionAlternateNameIds) {
        this.interventionAlternateNameIds = cleanupIds(interventionAlternateNameIds);
    }     

    /**
     * @return the interventionTypes
     */
    public List<String> getInterventionTypes() {
        return interventionTypes;
    }

    /**
     * @param interventionTypes the interventionTypes to set
     */
    public void setInterventionTypes(List<String> interventionTypes) {
        this.interventionTypes = cleanupNames(interventionTypes);
    }

    /**
     * @return the familyId
     */
    public String getFamilyId() {
        return familyId;
    }

    /**
     * @param familyId the familyId to set
     */
    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    /**
     * @return the participatingSiteFamilyId
     */
    public String getParticipatingSiteFamilyId() {
        return participatingSiteFamilyId;
    }

    /**
     * @param participatingSiteFamilyId the participatingSiteFamilyId to set
     */
    public void setParticipatingSiteFamilyId(String participatingSiteFamilyId) {
        this.participatingSiteFamilyId = participatingSiteFamilyId;
    }

    /**
     * return true if criteria contains location data.
     * @return boolean
     */
    public boolean isByLocation() {
        return (StringUtils.isNotBlank(getCountryName()) || StringUtils.isNotBlank(getCity()) || CollectionUtils
            .isNotEmpty(getStates()));
    }

    /**
     * Set the appropriate identifier base on the identifier type.
     * @param identifier The identifier value
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void setIdentifier(String identifier) {
        if (ALL.equals(identifierType)) {
            setCtepIdentifier(null);
            setDcpIdentifier(null);
            setCcrIdentifier(null);
            setLeadOrganizationTrialIdentifier(null);
            setNciIdentifier(null);
            setNctNumber(null);
            setOtherIdentifier(null);
            setAnyTypeIdentifier(identifier);
        } else {
            IdentifierType idType = IdentifierType.getByCode(identifierType);
            switch (idType) {
            case CTEP:
                setCtepIdentifier(identifier);
                break;
            case DCP:
                setDcpIdentifier(identifier);
                break;
            case CCR:
                setCcrIdentifier(identifier);
                break;                
            case LEAD_ORG:
                setLeadOrganizationTrialIdentifier(identifier);
                break;
            case NCI:
                setNciIdentifier(identifier);
                break;
            case NCT:
                setNctNumber(identifier);
                break;
            case OTHER_IDENTIFIER:
                setOtherIdentifier(identifier);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Cleanup the given list of ids from nulls and duplicates.
     * 
     * @param ids The list to clean up.
     * @return A new List cleaned-up from nulls and duplicates
     */
    List<Long> cleanupIds(List<Long> ids) {
        List<Long> result = new ArrayList<Long>();
        if (CollectionUtils.isNotEmpty(ids)) {
            Set<Long> existingIds = new HashSet<Long>();
            for (Long id : ids) {
                if (id != null && !existingIds.contains(id)) {
                    result.add(id);
                    existingIds.add(id);
                }
            }
        }
        return result;
    }

    /**
     * Cleanup the given list of names from blanks and duplicates.
     * 
     * @param names The list to clean up.
     * @return A new List cleaned-up from blanks and duplicates
     */
    List<String> cleanupNames(List<String> names) {
        List<String> result = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(names)) {
            Set<String> existingNames = new HashSet<String>();
            for (String name : names) {
                if (StringUtils.isNotBlank(name) && !existingNames.contains(name)) {
                    result.add(name);
                    existingNames.add(name);
                }

            }
        }
        return result;
    }

    /**
     * gets the ctgov xml reuired field.
     * 
     * @return string value
     */
    public String getCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }

    /**
     * Sets ctgov xml requireds value.
     * 
     * @param ctgovXmlRequiredIndicator
     *            xml indicator
     */
    public void setCtgovXmlRequiredIndicator(String ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }

    /**
     * Returns a {@link String} key that identifies this criteria. Two criteria
     * objects that are exactly the same will return the same key. Two criteria
     * objects that are different at least in one field value will have
     * different keys.
     * 
     * @return String
     */
    public String getUniqueCriteriaKey() { // NOPMD
        StringBuilder builder = new StringBuilder();
        builder.append("StudyProtocolQueryCriteria [studyProtocolId=")
                .append(studyProtocolId).append(", studyProtocolType=")
                .append(studyProtocolType).append(", studySubtypeCode=")
                .append(studySubtypeCode).append(", anyTypeIdentifier=")
                .append(anyTypeIdentifier).append(", nciIdentifier=")
                .append(nciIdentifier).append(", dcpIdentifier=")
                .append(dcpIdentifier).append(", ctepIdentifier=")
                .append(ctepIdentifier).append(", nctNumber=")
                .append(nctNumber).append(", officialTitle=")
                .append(officialTitle).append(", otherIdentifier=")
                .append(otherIdentifier)
                .append(", leadOrganizationTrialIdentifier=")
                .append(leadOrganizationTrialIdentifier)
                .append(", phaseAdditionalQualifierCode=")
                .append(phaseAdditionalQualifierCode)
                .append(", studyStatusCode=").append(studyStatusCode)
                .append(", studyStatusCodeList=").append(studyStatusCodeList)
                .append(", principalInvestigatorId=")
                .append(principalInvestigatorId)
                .append(", primaryPurposeCode=").append(primaryPurposeCode)
                .append(", identifierType=").append(identifierType)
                .append(", organizationType=").append(organizationType)
                .append(", userLastCreated=").append(userLastCreated)
                .append(", excludeRejectProtocol=")
                .append(excludeRejectProtocol)
                .append(", excludeTerminatedTrials=")
                .append(excludeTerminatedTrials).append(", myTrialsOnly=")
                .append(myTrialsOnly).append(", studyMilestone=")
                .append(studyMilestone).append(", submissionType=")
                .append(submissionType).append(", trialSubmissionTypes=")
                .append(trialSubmissionTypes).append(", nciSponsored=")
                .append(nciSponsored).append(", inBoxProcessing=")
                .append(inBoxProcessing).append(", studyLockedBy=")
                .append(studyLockedBy).append(", trialCategory=")
                .append(trialCategory).append(", ctepDcpCategory=")
                .append(ctepDcpCategory).append(", holdStatus=")
                .append(holdStatus).append(", userId=").append(userId)
                .append(", ctgovXmlRequiredIndicator=")
                .append(ctgovXmlRequiredIndicator)
                .append(", summ4FundingSourceId=").append(summ4FundingSourceId)
                .append(", summ4FundingSourceTypeCode=")
                .append(summ4FundingSourceTypeCode).append(", countryName=")
                .append(countryName).append(", city=").append(city)
                .append(", bioMarkerIds=").append(bioMarkerIds)
                .append(", bioMarkerNames=").append(bioMarkerNames)
                .append(", documentWorkflowStatusCodes=")
                .append(documentWorkflowStatusCodes)
                .append(", interventionIds=").append(interventionIds)
                .append(", interventionAlternateNameIds=")
                .append(interventionAlternateNameIds)
                .append(", interventionTypes=").append(interventionTypes)
                .append(", leadOrganizationIds=").append(leadOrganizationIds)
                .append(", participatingSiteIds=").append(participatingSiteIds)
                .append(", pdqDiseases=").append(pdqDiseases)
                .append(", phaseCodes=").append(phaseCodes).append(", states=")
                .append(states).append(", summary4AnatomicSites=")
                .append(summary4AnatomicSites).append(", onholdReasons=")
                .append(onholdReasons).append(", familyId=").append(familyId)
                .append(", participatingSiteFamilyId=")
                .append(participatingSiteFamilyId).append(", submitter=")
                .append(submitter).append(", checkedOut=").append(checkedOut)
                .append(", processingPriority=").append(processingPriority)
                .append(", submittedOnOrAfter=").append(submittedOnOrAfter)
                .append(", submittedOnOrBefore=").append(submittedOnOrBefore)
                .append(", milestoneFilters=").append(milestoneFilters)
                .append(", submitterAffiliateOrgId=")
                .append(submitterAffiliateOrgId)
                .append(", submitterAffiliateOrgNameList=")
                .append(submitterAffiliateOrgNameList).append(", holdRecordExists=")
                .append(holdRecordExists)
                .append(", assignedUserId=")
                .append(assignedUserId)
                .append(", currentOrPreviousMilestone=")
                .append(currentOrPreviousMilestone).append("]");
        return builder.toString();
    }

    /**
     * @return the holdStatus
     */
    public String getHoldStatus() {
        return holdStatus;
    }

    /**
     * @param holdStatus the holdStatus to set
     */
    public void setHoldStatus(String holdStatus) {
        this.holdStatus = holdStatus;
    }

    /**
     * @return the ctepDcpCategory
     */
    public String getCtepDcpCategory() {
        return ctepDcpCategory;
    }

    /**
     * @param ctepDcpCategory the ctepDcpCategory to set
     */
    public void setCtepDcpCategory(String ctepDcpCategory) {
        this.ctepDcpCategory = ctepDcpCategory;
    }

    /**
     * @return the anyTypeIdentifier
     */
    public String getAnyTypeIdentifier() {
        return anyTypeIdentifier;
    }

    /**
     * @param anyTypeIdentifier the anyTypeIdentifier to set
     */
    public void setAnyTypeIdentifier(String anyTypeIdentifier) {
        this.anyTypeIdentifier = anyTypeIdentifier;
    }

    /**
     * @return the submitter
     */
    public String getSubmitter() {
        return submitter;
    }

    /**
     * @param submitter the submitter to set
     */
    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    /**
     * @return the studyProtocolType
     */
    public String getStudyProtocolType() {
        return studyProtocolType;
    }

    /**
     * @param studyProtocolType the studyProtocolType to set
     */
    public void setStudyProtocolType(String studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }

    /**
     * @return the studySubtypeCode
     */
    public String getStudySubtypeCode() {
        return studySubtypeCode;
    }

    /**
     * @param studySubtypeCode the studySubtypeCode to set
     */
    public void setStudySubtypeCode(String studySubtypeCode) {
        this.studySubtypeCode = studySubtypeCode;
    }

    /**
     * @return the checkedOut
     */
    public Boolean getCheckedOut() {
        return checkedOut;
    }

    /**
     * @param checkedOut the checkedOut to set
     */
    public void setCheckedOut(Boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    /**
     * @return the submittedOnOrAfter
     */
    public Date getSubmittedOnOrAfter() {
        return submittedOnOrAfter;
    }

    /**
     * @param submittedOnOrAfter the submittedOnOrAfter to set
     */
    public void setSubmittedOnOrAfter(Date submittedOnOrAfter) {
        this.submittedOnOrAfter = submittedOnOrAfter;
    }

    /**
     * @return the submittedOnOrBefore
     */
    public Date getSubmittedOnOrBefore() {
        return submittedOnOrBefore;
    }

    /**
     * @param submittedOnOrBefore the submittedOnOrBefore to set
     */
    public void setSubmittedOnOrBefore(Date submittedOnOrBefore) {
        this.submittedOnOrBefore = submittedOnOrBefore;
    }

    /**
     * @return the submitterAffiliateOrgId
     */
    public String getSubmitterAffiliateOrgId() {
        return submitterAffiliateOrgId;
    }

    /**
     * @param submitterAffiliateOrgId the submitterAffiliateOrgId to set
     */
    public void setSubmitterAffiliateOrgId(String submitterAffiliateOrgId) {
        this.submitterAffiliateOrgId = submitterAffiliateOrgId;
    }

    /**
     * 
     * @return submitterAffiliateOrgNameList submitterAffiliateOrgNameList
     */
    public List<String> getSubmitterAffiliateOrgNameList() {
        return submitterAffiliateOrgNameList;
    }
    /**
     * 
     * @param submitterAffiliateOrgNameList submitterAffiliateOrgNameList
     */
    public void setSubmitterAffiliateOrgNameList(
            List<String> submitterAffiliateOrgNameList) {
        this.submitterAffiliateOrgNameList = submitterAffiliateOrgNameList;
    }

    /**
     * @return the trialSubmissionTypes
     */
    public List<SubmissionTypeCode> getTrialSubmissionTypes() {
        return trialSubmissionTypes;
    }

    /**
     * @param trialSubmissionTypes the trialSubmissionTypes to set
     */
    public void setTrialSubmissionTypes(
            List<SubmissionTypeCode> trialSubmissionTypes) {
        this.trialSubmissionTypes = trialSubmissionTypes;
    }

    /**
     * @return the nciSponsored
     */
    public Boolean getNciSponsored() {
        return nciSponsored;
    }

    /**
     * @param nciSponsored the nciSponsored to set
     */
    public void setNciSponsored(Boolean nciSponsored) {
        this.nciSponsored = nciSponsored;
    }

    /**
     * @return the holdRecordExists
     */
    public Boolean getHoldRecordExists() {
        return holdRecordExists;
    }

    /**
     * @param holdRecordExists the holdRecordExists to set
     */
    public void setHoldRecordExists(Boolean holdRecordExists) {
        this.holdRecordExists = holdRecordExists;
    }

    /**
     * @return the onholdReasons
     */
    public List<OnholdReasonCode> getOnholdReasons() {
        return onholdReasons;
    }

    /**
     * @param onholdReasons the onholdReasons to set
     */
    public void setOnholdReasons(List<OnholdReasonCode> onholdReasons) {
        this.onholdReasons = onholdReasons;
    }

    /**
     * @return the currentOrPreviousMilestone
     */
    public MilestoneCode getCurrentOrPreviousMilestone() {
        return currentOrPreviousMilestone;
    }

    /**
     * @param currentOrPreviousMilestone the currentOrPreviousMilestone to set
     */
    public void setCurrentOrPreviousMilestone(
            MilestoneCode currentOrPreviousMilestone) {
        this.currentOrPreviousMilestone = currentOrPreviousMilestone;
    }

    
    /**
     * @return the milestoneFilters
     */
    public List<MilestoneFilter> getMilestoneFilters() {
        return milestoneFilters;
    }

    /**
     * @param milestoneFilters the milestoneFilters to set
     */
    public void setMilestoneFilters(List<MilestoneFilter> milestoneFilters) {
        this.milestoneFilters = milestoneFilters;
    }

    /**
     * @return the processingPriority
     */
    public List<String> getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority the processingPriority to set
     */
    public void setProcessingPriority(List<String> processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @return the ctroOverride
     */
    public Boolean getCtroOverride() {
        return ctroOverride;
    }

    /**
     * @param ctroOverride the ctroOverride to set
     */
    public void setCtroOverride(Boolean ctroOverride) {
        this.ctroOverride = ctroOverride;
    }

    /**
     * @return the assignedUserId
     */
    public Long getAssignedUserId() {
        return assignedUserId;
    }

    /**
     * @param assignedUserId the assignedUserId to set
     */
    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
    /**
     * 
     * @return studyStatusCodeList studyStatusCodeList
     */
    public List<String> getStudyStatusCodeList() {
        return studyStatusCodeList;
    }
    /**
     * 
     * @param studyStatusCodeList studyStatusCodeList
     */
    public void setStudyStatusCodeList(List<String> studyStatusCodeList) {
        this.studyStatusCodeList = studyStatusCodeList;
    }

    /**
     * @return the excludeTerminatedTrials
     */
    public Boolean isExcludeTerminatedTrials() {
        return excludeTerminatedTrials;
    }

    /**
     * @param excludeTerminatedTrials the excludeTerminatedTrials to set
     */
    public void setExcludeTerminatedTrials(Boolean excludeTerminatedTrials) {
        this.excludeTerminatedTrials = excludeTerminatedTrials;
    }


    /**
     * 
     * @return the study source to filter on.
     */
    public StudySourceCode getStudySourceByCode() {
        return studySource;
    }

    /**
     * 
     * @return th study source as a string.
     */
    public String getStudySource() {
        if (studySource == null) {
            return null;
        }
        return studySource.getCode();
    }
    
    /**
     * 
     * @param studySourceStr the study source to filter on
     */
    public void setStudySource(String studySourceStr) {
        if (studySourceStr == null || studySourceStr.isEmpty() || studySourceStr.equalsIgnoreCase("All")) {
            this.studySource = null;
        }
        
        this.studySource = StudySourceCode.getByCode(studySourceStr);
    }

    /**
     * @return the ccrIdentifier
     */
    public String getCcrIdentifier() {
        return ccrIdentifier;
    }

    /**
     * @param ccrIdentifier the ccrIdentifier to set
     */
    public void setCcrIdentifier(String ccrIdentifier) {
        this.ccrIdentifier = ccrIdentifier;
    }
    
    // CHECKSTYLE:OFF
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("PMD")
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StudyProtocolQueryCriteria [");
        if (studyProtocolId != null)
            builder.append("studyProtocolId=").append(studyProtocolId)
                    .append(", ");
        if (isNotBlank(studyProtocolType))
            builder.append("studyProtocolType=").append(studyProtocolType)
                    .append(", ");
        if (isNotBlank(studySubtypeCode))
            builder.append("studySubtypeCode=").append(studySubtypeCode)
                    .append(", ");
        if (isNotBlank(anyTypeIdentifier))
            builder.append("anyTypeIdentifier=").append(anyTypeIdentifier)
                    .append(", ");
        if (isNotBlank(nciIdentifier))
            builder.append("nciIdentifier=").append(nciIdentifier).append(", ");
        if (isNotBlank(dcpIdentifier))
            builder.append("dcpIdentifier=").append(dcpIdentifier).append(", ");
        if (isNotBlank(ccrIdentifier))
            builder.append("ccrIdentifier=").append(ccrIdentifier).append(", ");
        if (isNotBlank(ctepIdentifier))
            builder.append("ctepIdentifier=").append(ctepIdentifier)
                    .append(", ");
        if (isNotBlank(nctNumber))
            builder.append("nctNumber=").append(nctNumber).append(", ");
        if (isNotBlank(officialTitle))
            builder.append("officialTitle=").append(officialTitle).append(", ");
        if (isNotBlank(otherIdentifier))
            builder.append("otherIdentifier=").append(otherIdentifier)
                    .append(", ");
        if (isNotBlank(leadOrganizationTrialIdentifier))
            builder.append("leadOrganizationTrialIdentifier=")
                    .append(leadOrganizationTrialIdentifier).append(", ");
        if (isNotBlank(phaseAdditionalQualifierCode))
            builder.append("phaseAdditionalQualifierCode=")
                    .append(phaseAdditionalQualifierCode).append(", ");
        if (isNotBlank(studyStatusCode))
            builder.append("studyStatusCode=").append(studyStatusCode)
                    .append(", ");
        if (isNotEmpty(studyStatusCodeList))
            builder.append("studyStatusCodeList=").append(studyStatusCodeList)
                    .append(", ");
        if (isNotBlank(principalInvestigatorId))
            builder.append("principalInvestigatorId=")
                    .append(principalInvestigatorId).append(", ");
        if (isNotBlank(primaryPurposeCode))
            builder.append("primaryPurposeCode=").append(primaryPurposeCode)
                    .append(", ");
        if (isNotBlank(identifierType))
            builder.append("identifierType=").append(identifierType)
                    .append(", ");
        if (isNotBlank(organizationType))
            builder.append("organizationType=").append(organizationType)
                    .append(", ");       
        if ((excludeRejectProtocol) != null)
            builder.append("excludeRejectProtocol=")
                    .append(excludeRejectProtocol).append(", ");
        if ((excludeTerminatedTrials) != null)
            builder.append("excludeTerminatedTrials=")
                    .append(excludeTerminatedTrials).append(", ");
        if ((myTrialsOnly) != null)
            builder.append("myTrialsOnly=").append(myTrialsOnly).append(", ");
        if (isNotEmpty(studyMilestone))
            builder.append("studyMilestone=").append(studyMilestone)
                    .append(", ");
        if (isNotBlank(submissionType))
            builder.append("submissionType=").append(submissionType)
                    .append(", ");
        if (isNotEmpty(trialSubmissionTypes))
            builder.append("trialSubmissionTypes=")
                    .append(trialSubmissionTypes).append(", ");
        if ((nciSponsored) != null)
            builder.append("nciSponsored=").append(nciSponsored).append(", ");
        if ((inBoxProcessing) != null)
            builder.append("inBoxProcessing=").append(inBoxProcessing)
                    .append(", ");
        if (studyLockedBy)
            builder.append("studyLockedBy=").append(studyLockedBy).append(", ");
        if (isNotBlank(trialCategory))
            builder.append("trialCategory=").append(trialCategory).append(", ");
        if (isNotBlank(ctepDcpCategory))
            builder.append("ctepDcpCategory=").append(ctepDcpCategory)
                    .append(", ");
        if (isNotBlank(holdStatus))
            builder.append("holdStatus=").append(holdStatus).append(", ");
        if ((userId) != null)
            builder.append("userId=").append(userId).append(", ");
        if (isNotBlank(ctgovXmlRequiredIndicator))
            builder.append("ctgovXmlRequiredIndicator=")
                    .append(ctgovXmlRequiredIndicator).append(", ");
        if ((summ4FundingSourceId) != null)
            builder.append("summ4FundingSourceId=")
                    .append(summ4FundingSourceId).append(", ");
        if (isNotBlank(summ4FundingSourceTypeCode))
            builder.append("summ4FundingSourceTypeCode=")
                    .append(summ4FundingSourceTypeCode).append(", ");
        if (isNotBlank(countryName))
            builder.append("countryName=").append(countryName).append(", ");
        if (isNotBlank(city))
            builder.append("city=").append(city).append(", ");
        if (isNotEmpty(bioMarkerIds))
            builder.append("bioMarkerIds=").append(bioMarkerIds).append(", ");
        if (isNotEmpty(bioMarkerNames))
            builder.append("bioMarkerNames=").append(bioMarkerNames)
                    .append(", ");
        if (isNotEmpty(documentWorkflowStatusCodes))
            builder.append("documentWorkflowStatusCodes=")
                    .append(documentWorkflowStatusCodes).append(", ");
        if (isNotEmpty(interventionIds))
            builder.append("interventionIds=").append(interventionIds)
                    .append(", ");
        if (isNotEmpty(interventionAlternateNameIds))
            builder.append("interventionAlternateNameIds=")
                    .append(interventionAlternateNameIds).append(", ");
        if (isNotEmpty(interventionTypes))
            builder.append("interventionTypes=").append(interventionTypes)
                    .append(", ");
        if (isNotEmpty(leadOrganizationIds))
            builder.append("leadOrganizationIds=").append(leadOrganizationIds)
                    .append(", ");
        if (isNotEmpty(participatingSiteIds))
            builder.append("participatingSiteIds=")
                    .append(participatingSiteIds).append(", ");
        if (isNotEmpty(pdqDiseases))
            builder.append("pdqDiseases=").append(pdqDiseases).append(", ");
        if (isNotEmpty(phaseCodes))
            builder.append("phaseCodes=").append(phaseCodes).append(", ");
        if (isNotEmpty(states))
            builder.append("states=").append(states).append(", ");
        if (isNotEmpty(summary4AnatomicSites))
            builder.append("summary4AnatomicSites=")
                    .append(summary4AnatomicSites).append(", ");
        if (isNotEmpty(onholdReasons))
            builder.append("onholdReasons=").append(onholdReasons).append(", ");
        if ((studySource) != null)
            builder.append("studySource=").append(studySource).append(", ");
        if (isNotBlank(familyId) && !"0".equals(familyId))
            builder.append("familyId=").append(familyId).append(", ");
        if (isNotBlank(participatingSiteFamilyId) && !"0".equals(participatingSiteFamilyId))
            builder.append("participatingSiteFamilyId=")
                    .append(participatingSiteFamilyId).append(", ");
        if (isNotBlank(submitter))
            builder.append("submitter=").append(submitter).append(", ");
        if ((checkedOut) != null)
            builder.append("checkedOut=").append(checkedOut).append(", ");
        if (isNotEmpty(processingPriority))
            builder.append("processingPriority=").append(processingPriority)
                    .append(", ");
        if ((submittedOnOrAfter) != null)
            builder.append("submittedOnOrAfter=").append(submittedOnOrAfter)
                    .append(", ");
        if ((submittedOnOrBefore) != null)
            builder.append("submittedOnOrBefore=").append(submittedOnOrBefore)
                    .append(", ");
        if (isNotBlank(submitterAffiliateOrgId))
            builder.append("submitterAffiliateOrgId=")
                    .append(submitterAffiliateOrgId).append(", ");
        if (isNotEmpty(submitterAffiliateOrgNameList))
            builder.append("submitterAffiliateOrgNameList=")
                    .append(submitterAffiliateOrgNameList).append(", ");
        if ((holdRecordExists) != null)
            builder.append("holdRecordExists=").append(holdRecordExists)
                    .append(", ");
        if ((currentOrPreviousMilestone) != null)
            builder.append("currentOrPreviousMilestone=")
                    .append(currentOrPreviousMilestone).append(", ");
        if (isNotEmpty(milestoneFilters))
            builder.append("milestoneFilters=").append(milestoneFilters)
                    .append(", ");
        if ((ctroOverride) != null)
            builder.append("ctroOverride=").append(ctroOverride).append(", ");
        if ((assignedUserId) != null)
            builder.append("assignedUserId=").append(assignedUserId);
        builder.append("]");
        return builder.toString();
    }
    
}
