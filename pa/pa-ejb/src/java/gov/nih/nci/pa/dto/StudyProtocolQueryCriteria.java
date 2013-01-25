/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
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
    private String ctepIdentifier;
    private String nctNumber;
    private String officialTitle;
    private String otherIdentifier;
    private String leadOrganizationTrialIdentifier;
    private String phaseAdditionalQualifierCode;
    private String studyStatusCode;
    private String principalInvestigatorId;
    private String primaryPurposeCode;
    private String identifierType;
    private String organizationType;
    private String userLastCreated;
    private Boolean excludeRejectProtocol;
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
    
    private String familyId = "0";
    private String participatingSiteFamilyId = "0";
    private String submitter;
    private Boolean checkedOut;
    private List<String> processingPriority = new ArrayList<String>();
    private Date submittedOnOrAfter;
    private Date submittedOnOrBefore;  
    private String submitterAffiliateOrgId;
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
                .append(", principalInvestigatorId=")
                .append(principalInvestigatorId)
                .append(", primaryPurposeCode=").append(primaryPurposeCode)
                .append(", identifierType=").append(identifierType)
                .append(", organizationType=").append(organizationType)
                .append(", userLastCreated=").append(userLastCreated)
                .append(", excludeRejectProtocol=")
                .append(excludeRejectProtocol).append(", myTrialsOnly=")
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
                .append(submitterAffiliateOrgId).append(", holdRecordExists=")
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
        
    
}
