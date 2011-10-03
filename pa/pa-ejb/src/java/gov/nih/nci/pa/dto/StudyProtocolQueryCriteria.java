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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class StudyProtocolQueryCriteria implements Serializable {
    static final long serialVersionUID = 252345L;

    private Long studyProtocolId;
    private String nciIdentifier;
    private String dcpIdentifier;
    private String ctepIdentifier;
    private String nctNumber;
    private String officialTitle;
    private String leadOrganizationId;
    private String otherIdentifier;
    private String participatingSiteId;
    private String leadOrganizationTrialIdentifier;   
    private String phaseAdditionalQualifierCode;
    private String studyStatusCode;
    private String documentWorkflowStatusCode;
    private String principalInvestigatorId;
    private String primaryPurposeCode;
    private String identifierType;
    private String organizationType;
    private String userLastCreated;
    private Boolean excludeRejectProtocol;
    // for Registry trial search
    private Boolean myTrialsOnly;
    private boolean searchOnHold;
    private String studyMilestone;
    private String submissionType;

    //for Inbox Processing
    private Boolean inBoxProcessing;
    private boolean studyLockedBy;
    private String trialCategory;
    private Long userId;
    
    private Long summ4FundingSourceId;
    private Long diseaseConditionId;
    private String interventionType;
    private String summ4FundingSourceTypeCode;
    private List<String> phaseCodes = new ArrayList<String>();
    
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
     * @return leadOrganizationId
     */
    public String getLeadOrganizationId() {
        return leadOrganizationId;
    }
    /**
     *
     * @param leadOrganizationId leadOrganizationId
     */
    public void setLeadOrganizationId(String leadOrganizationId) {
        this.leadOrganizationId = leadOrganizationId;
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
     * @param participatingSiteId the participatingSiteId to set
     */
    public void setParticipatingSiteId(String participatingSiteId) {
        this.participatingSiteId = participatingSiteId;
    }
    /**
     * @return the participatingSiteId
     */
    public String getParticipatingSiteId() {
        return participatingSiteId;
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
     * @return documentWorkflowStatusCode
     */
    public String getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }
    /**
     *
     * @param documentWorkflowStatusCode documentWorkflowStatusCode
     */
    public void setDocumentWorkflowStatusCode(String documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
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
     * @param searchOnHold the searchOnHold to set
     */
    public void setSearchOnHold(boolean searchOnHold) {
        this.searchOnHold = searchOnHold;
    }
    /**
     * @return the searchOnHold
     */
    public boolean isSearchOnHold() {
        return searchOnHold;
    }
    /**
     * @param studyMilestone the studyMilestone to set
     */
    public void setStudyMilestone(String studyMilestone) {
        this.studyMilestone = studyMilestone;
    }
    /**
     * @return the studyMilestone
     */
    public String getStudyMilestone() {
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
     * @return the diseaseConditionId
     */
    public Long getDiseaseConditionId() {
        return diseaseConditionId;
    }
    /**
     * @param diseaseConditionId the diseaseConditionId to set
     */
    public void setDiseaseConditionId(Long diseaseConditionId) {
        this.diseaseConditionId = diseaseConditionId;
    }
    /**
     * @return the leadAgentInterventionId
     */
    public String getInterventionType() {
        return interventionType;
    }
    /**
     * @param interventionType the interventionType to set
     */
    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
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
        for (String code : phaseCodes) {
            if (StringUtils.isNotBlank(code)) {
                this.phaseCodes.add(code);
            }
        }
    }    
}
