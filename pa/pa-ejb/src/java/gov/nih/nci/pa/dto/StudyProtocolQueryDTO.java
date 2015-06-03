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

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;


/**
 * StudyProtocolQueryDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 07/22/2007
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.TooManyFields",
        "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength" })
public class StudyProtocolQueryDTO extends TrialSearchStudyProtocolQueryDTO implements Serializable {

    private static final long serialVersionUID = 8200069337460780488L;
    
    private boolean viewTSR;
    private boolean proprietaryTrial;
    private Date recordVerificationDate;
    private Boolean showSendXml = false;
    private Boolean showViewTSR = false;
    private String summ4FundingSrcCategory;
    private boolean searcherTrialOwner = false;
    private String nctNumber;
    
    private String phaseName;
    private Date startDate;
    private String sponsorName;
    private String summary4FundingSponsorType;
    private String responsiblePartyName;
    private String category;
    
    private String lastUpdatedUserDisplayName;
    
    private String lastUpdaterDisplayName;
    
    private String recentHoldReason;
    private String recentHoldDescription;
    private String recentHoldCategory;
    private Date recentOnHoldDate;
    private Date recentOffHoldDate;
    private boolean verifyData = false;
    private boolean showAccrualOption = false;
    private Date verificationDueDate;
    private Set<StudyAlternateTitleDTO> studyAlternateTitles;
    private String studySource;
    private List<OrganizationDTO> orgsThatCanBeAddedAsSite;
    private String accrualDiseaseCode;
    private List<StudyOnholdDTO> allHolds = new ArrayList<>();
   

    /**
     * Whether this trial permits self-registration of participating sites. 
     * @see https://tracker.nci.nih.gov/browse/PO-2034
     */
    private boolean siteSelfRegistrable;
    
    /**
     * Whether the current user's organization is a participating site on this trial.
     */
    private boolean currentUserHasSite;
    
    /**
     * Whether the current user's organization is a participating site on this trial and the current user
     * is a study site owner for.
     */
    private boolean currentUserIsSiteOwner;
    
    private Date primaryCompletionDate;

    private Long poOrganizationId;

    private Integer bizDaysOnHoldCTRP;

    private Integer bizDaysOnHoldSubmitter;

    private Integer bizDaysSinceSubmitted;

    private Date expectedAbstractionCompletionDate;
    
    
    /**
     * @return link
     */
    public String getAmend() {
        boolean isProprietaryTrial = isProprietaryTrial();
        boolean isOwner = isSearcherTrialOwner();
        DocumentWorkflowStatusCode dwfs = getDocumentWorkflowStatusCode();
        StudyStatusCode studyStatusCode = getStudyStatusCode();

        if (!isProprietaryTrial && isAmendDWFS(dwfs) && isOwner && isAmendStatus(studyStatusCode)) {
            return "Amend";
        }
        return "";
    }

    private boolean isAmendStatus(StudyStatusCode statusCode) {
        return !(StudyStatusCode.WITHDRAWN.equals(statusCode)
                || StudyStatusCode.COMPLETE.equals(statusCode)
                || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(statusCode));
    }

    private boolean isAmendDWFS(DocumentWorkflowStatusCode dwfs) {
        return DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.equals(dwfs)
                || DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.equals(dwfs);
    }
    

    /**
     * @return the trialCategory
     */
    public String getTrialCategory() {
        return isProprietaryTrial() ? "Abbreviated Trial" : "Complete Trial";
    }

    /**
     * @return the isProprietaryTrial
     */
    public boolean isProprietaryTrial() {
        return proprietaryTrial;
    }

    /**
     * @param proprietaryTrial the isProprietaryTrial to set
     */
    public void setProprietaryTrial(boolean proprietaryTrial) {
        this.proprietaryTrial = proprietaryTrial;
    }

    /**
     * 
     * @return tsr
     */
    public boolean isViewTSR() {
        return viewTSR;
    }

    /**
     * 
     * @param viewTSR tsr
     */
    public void setViewTSR(boolean viewTSR) {
        this.viewTSR = viewTSR;
    }

    /**
     * @return the recordVerificationDate
     */
    public Date getRecordVerificationDate() {
        return recordVerificationDate;
    }

    /**
     * @param recordVerificationDate the recordVerificationDate to set
     */
    public void setRecordVerificationDate(Date recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }


    /**
     * @return showSendXml
     */
    public Boolean getShowSendXml() {
        return showSendXml;
    }

    /**
     * @param showSendXml showSendXml to set
     */
    public void setShowSendXml(Boolean showSendXml) {
        this.showSendXml = showSendXml;
    }
    /**
     * 
     * @return showViewTSR
     */
    public Boolean getShowViewTSR() {
        return showViewTSR;
    }
    /**
     * 
     * @param showViewTSR showViewTSR
     */
    public void setShowViewTSR(Boolean showViewTSR) {
        this.showViewTSR = showViewTSR;
    }

    /**
     * This field is set to true if and only if the person performing the search is considered a trial owner.
     * @return the isSearcherTrialOwner
     */
    public boolean isSearcherTrialOwner() {
        return searcherTrialOwner;
    }

    /**
     * @param isSearcherTrialOwner the isSearcherTrialOwner to set
     */
    public void setSearcherTrialOwner(boolean isSearcherTrialOwner) {
        this.searcherTrialOwner = isSearcherTrialOwner;
    }
    
    /**
     * 
     * @return verifyData verifyData
     */

    public boolean isVerifyData() {
        return verifyData;
    }
    /**
     * @param verifyData the verifyData to set
     */
    public void setVerifyData(boolean verifyData) {
        this.verifyData = verifyData;
    }

    /**
     * @param summ4FundingSrcCategory the summ4FundingSrcCategory to set
     */
    public void setSumm4FundingSrcCategory(String summ4FundingSrcCategory) {
        this.summ4FundingSrcCategory = summ4FundingSrcCategory;
    }

    /**
     * @return the summ4FundingSrcCategory
     */
    public String getSumm4FundingSrcCategory() {
        return summ4FundingSrcCategory;
    }

    /**
     * @return the nctNumber
     */
    public String getNctNumber() {
        return nctNumber;
    }

    /**
     * @param nctNumber the nctNumber to set
     */
    public void setNctNumber(String nctNumber) {
        this.nctNumber = nctNumber;
    }

    /**
     * @return the siteSelfRegistrable
     */
    public boolean isSiteSelfRegistrable() {
        return siteSelfRegistrable;
    }

    /**
     * @param siteSelfRegistrable the siteSelfRegistrable to set
     */
    public void setSiteSelfRegistrable(boolean siteSelfRegistrable) {
        this.siteSelfRegistrable = siteSelfRegistrable;
    }

    /**
     * @return the currentUserHasSite
     */
    public boolean isCurrentUserHasSite() {
        return currentUserHasSite;
    }

    /**
     * @param currentUserHasSite the currentUserHasSite to set
     */
    public void setCurrentUserHasSite(boolean currentUserHasSite) {
        this.currentUserHasSite = currentUserHasSite;
    }    
    
    /**
     * Tells whether the currently logged in user can add his/her site to the
     * trial.
     * 
     * @return boolean
     */
    public boolean isCurrentUserCanAddSite() {
        return isSiteSelfRegistrable() && !isCurrentUserHasSite();
    }

    /**
     * Tells whether the currently logged in user can edit his/her site info.
     * 
     * @return boolean
     */
    public boolean isCurrentUserCanEditSite() {
        return isSiteSelfRegistrable() && isCurrentUserHasSite() && isCurrentUserIsSiteOwner();
    }

    /**
     * @return the currentUserIsSiteOwner
     */
    public boolean isCurrentUserIsSiteOwner() {
        return currentUserIsSiteOwner;
    }

    /**
     * @param currentUserIsSiteOwner the currentUserIsSiteOwner to set
     */
    public void setCurrentUserIsSiteOwner(boolean currentUserIsSiteOwner) {
        this.currentUserIsSiteOwner = currentUserIsSiteOwner;
    }
    
    
    /**
     * @return string
     */
    public String getPhaseName() {
        return phaseName;
    }

    /**
     * 
     * @param phaseName phaseCode
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    /**
     * 
     * @return date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return string 
     */
    public String getSponsorName() {
        return sponsorName;
    }

    /**
     * 
     * @param sponsorName sponsorName
     */
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    /**
     * 
     * @return string
     */
    public String getSummary4FundingSponsorType() {
        return summary4FundingSponsorType;
    }

    /**
     * 
     * @param summary4FundingSponsorName summary4FundingSponsorName
     */
    public void setSummary4FundingSponsorType(String summary4FundingSponsorName) {
        this.summary4FundingSponsorType = summary4FundingSponsorName;
    }

    /**
     * 
     * @return string
     */
    public String getResponsiblePartyName() {
        return responsiblePartyName;
    }

    /**
     * 
     * @param responsiblePartyName responsiblePartyName
     */
    public void setResponsiblePartyName(String responsiblePartyName) {
        this.responsiblePartyName = responsiblePartyName;
    }
    

    /**
     * 
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * 
     * @return String
     */
    public String getLastUpdatedUserDisplayName() {
        return lastUpdatedUserDisplayName;
    }

    /**
     * 
     * @param lastUpdatedUserDisplayName lastUpdatedUserDisplayName
     */
    public void setLastUpdatedUserDisplayName(String lastUpdatedUserDisplayName) {
        this.lastUpdatedUserDisplayName = lastUpdatedUserDisplayName;
    }
    
    /**
     * 
     * @return String
     */
    public String getLastUpdaterDisplayName() {
        return lastUpdaterDisplayName;
    }

    /**
     * 
     * @param lastUpdaterDisplayName lastUpdaterDisplayName
     */
    public void setLastUpdaterDisplayName(String lastUpdaterDisplayName) {
        this.lastUpdaterDisplayName = lastUpdaterDisplayName;
    }
    
    /**
     * 
     * @return PrimaryCompletionDate
     */
    public Date getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * 
     * @param primaryCompletionDate primaryCompletionDate
     */
    public void setPrimaryCompletionDate(Date primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     *  Determines whether to show actions dropdown or not.
     * @return boolean
     */
    public boolean isActionVisible() {
        return StringUtils.isNotEmpty(getUpdate()) 
            || StringUtils.isNotEmpty(getAmend()) 
            || StringUtils.isNotEmpty(getStatusChangeLinkText()) 
            || getShowSendXml().booleanValue() 
            || getShowViewTSR().booleanValue() 
            || isCurrentUserCanAddSite() 
            || isCurrentUserCanEditSite();
    }
  
    /**
     * @return CtepOrDcp
     */
    public String getCtepOrDcp() {
        if (StringUtils.isNotBlank(getCtepId())
                && StringUtils.isBlank(getDcpId())) { 
            return "CTEP";
        }
        if (StringUtils.isNotBlank(getDcpId())) {
            return "DCP";
        }
        return "";
    }
    
    
    /**
     * @return the checkedOutByMe
     */
    public boolean isCheckedOutByMe() {
        return UsernameHolder.getUser().equalsIgnoreCase(
                getAdminCheckout().getCheckoutBy())
                || UsernameHolder.getUser().equalsIgnoreCase(
                        getScientificCheckout().getCheckoutBy());
    } 
    
    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForAdminProcessing() {
        return MilestoneCode.SUBMISSION_ACCEPTED == getMilestones()
                .getActiveMilestone().getMilestone();
    }
    
    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForAdminQC() {
        return MilestoneCode.ADMINISTRATIVE_READY_FOR_QC == getMilestones()
                .getActiveMilestone().getMilestone();
    }
    
    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForTSRSubmission() {
        return MilestoneCode.READY_FOR_TSR == getMilestones()
                .getActiveMilestone().getMilestone();
    }
    
    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isSubmittedNotAccepted() {
        return MilestoneCode.SUBMISSION_RECEIVED == getMilestones()
                .getActiveMilestone().getMilestone();
    }
    
    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForScientificProcessing() {
        return MilestoneCode.READY_FOR_SCIENTIFIC_PROCESSING_LIST
                .contains(getMilestones().getActiveMilestone().getMilestone())
                && findMilestoneInHistory(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE) == null;
    }
    
    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForScientificQC() {
        return MilestoneCode.SCIENTIFIC_READY_FOR_QC == getMilestones()
                .getActiveMilestone().getMilestone();
    }

    /**
     * @return the recentHoldReason
     */
    public String getRecentHoldReason() {
        return recentHoldReason;
    }

    /**
     * @param recentHoldReason the recentHoldReason to set
     */
    public void setRecentHoldReason(String recentHoldReason) {
        this.recentHoldReason = recentHoldReason;
    }

    /**
     * @return the recentOnHoldDate
     */
    public Date getRecentOnHoldDate() {
        return recentOnHoldDate;
    }

    /**
     * @param recentOnHoldDate the recentOnHoldDate to set
     */
    public void setRecentOnHoldDate(Date recentOnHoldDate) {
        this.recentOnHoldDate = recentOnHoldDate;
    }

    /**
     * @return the recentOffHoldDate
     */
    public Date getRecentOffHoldDate() {
        return recentOffHoldDate;
    }

    /**
     * @param recentOffHoldDate the recentOffHoldDate to set
     */
    public void setRecentOffHoldDate(Date recentOffHoldDate) {
        this.recentOffHoldDate = recentOffHoldDate;
    }
    
    /**
     * @return MilestoneListForReporting
     */
    public List<MilestoneDTO> getMilestoneListForReporting() {
        List<MilestoneDTO> list = new ArrayList<MilestoneDTO>();
        for (MilestoneCode code : MilestoneCode.getMilestoneCodesForReporting()) {
            MilestoneDTO historicMilestone = findMilestoneInHistory(code);
            if (historicMilestone != null) {
                list.add(historicMilestone);
            } else {
                MilestoneDTO dto = new MilestoneDTO();
                dto.setMilestone(code);
                list.add(dto);
            }
        }
        return list;
    }
    
    /**
     * @param code
     *            MilestoneCode
     * @return MilestoneDTO
     */
    public MilestoneDTO getMilestoneForReporting(final MilestoneCode code) {
        return (MilestoneDTO) CollectionUtils.find(getMilestoneListForReporting(),
                new Predicate() {
                    @Override
                    public boolean evaluate(Object obj) {
                        return code == ((MilestoneDTO) obj).getMilestone();
                    }
                });
    }

    private MilestoneDTO findMilestoneInHistory(final MilestoneCode code) {
        return (MilestoneDTO) CollectionUtils.find(getMilestoneHistory(),
                new Predicate() {
                    @Override
                    public boolean evaluate(Object mstone) {
                        return code.equals(((MilestoneDTO) mstone)
                                .getMilestone());
                    }
                });
    }

    /**
     * @return the recentHoldDescription
     */
    public String getRecentHoldDescription() {
        return recentHoldDescription;
    }

    /**
     * @param recentHoldDescription the recentHoldDescription to set
     */
    public void setRecentHoldDescription(String recentHoldDescription) {
        this.recentHoldDescription = recentHoldDescription;
    }
    /**
     * 
     * @return verificationDueDate verificationDueDate
     */
    public Date getVerificationDueDate() {
        return verificationDueDate;
    }
    /**
     * 
     * @param verificationDueDate verificationDueDate
     */
    public void setVerificationDueDate(Date verificationDueDate) {
        this.verificationDueDate = verificationDueDate;
    }

    /**
     * @return study alternate titles
     */
    public Set<StudyAlternateTitleDTO> getStudyAlternateTitles() {
        return studyAlternateTitles;
    }

    /**
     * @param studyAlternateTitles study alternate titles to set
     */
    public void setStudyAlternateTitles(
            Set<StudyAlternateTitleDTO> studyAlternateTitles) {
        this.studyAlternateTitles = studyAlternateTitles;
    }

    /**
     * 
     * @return the source where the study was created.
     */
    public String getStudySource() {
        return studySource;
    }

    /**
     * 
     * @param studySource sets which pathway was used to submit the study.
     */
    public void setStudySource(StudySourceCode studySource) {
        if (studySource == null) {
            this.studySource = "";
        } else {
            this.studySource = studySource.getCode();
        }
    }
    
    /**
     * 
     * @param studySource sets which pathway was used to submit the study.
     */
    public void setStudySource(String studySource) {
        this.studySource = studySource;
    }

    /**
     * @return the orgsThatCanBeAddedAsSite
     */
    public List<OrganizationDTO> getOrgsThatCanBeAddedAsSite() {
        return orgsThatCanBeAddedAsSite;
    }

    /**
     * @param orgsThatCanBeAddedAsSite the orgsThatCanBeAddedAsSite to set
     */
    public void setOrgsThatCanBeAddedAsSite(
            List<OrganizationDTO> orgsThatCanBeAddedAsSite) {
        this.orgsThatCanBeAddedAsSite = orgsThatCanBeAddedAsSite;
    }
    
    /**
     * 
     * @return showAccrualOption showAccrualOption
     */
    public boolean isShowAccrualOption() {
        return showAccrualOption;
    }
    /**
     * 
     * @param showAccrualOption showAccrualOption
     */
    public void setShowAccrualOption(boolean showAccrualOption) {
        this.showAccrualOption = showAccrualOption;
    }
    /**
     * 
     * @return accrualDiseaseCode accrualDiseaseCode
     */ 
    public String getAccrualDiseaseCode() {
        return accrualDiseaseCode;
    }
    /**
     * 
     * @param accrualDiseaseCode accrualDiseaseCode
     */
    public void setAccrualDiseaseCode(String accrualDiseaseCode) {
        this.accrualDiseaseCode = accrualDiseaseCode;
    }

    /**
     * This sets the PO id of the Submiting organization.
     * @param poOrgId the PO id for the organization.
     */
    public void setSubmitterOrgId(Long poOrgId) {
        this.poOrganizationId = poOrgId;
    }

    /**
     * Returns the PO ID for the submiting organization.
     * @return the PO ID for the organization.
     */
    public Long getSubmitterOrgId() {
        return poOrganizationId;
    }
    
    // CHECKSTYLE:OFF
    /**
     * @return Date
     */
    public final Date getExpectedAbstractionCompletionDate() {
        return expectedAbstractionCompletionDate != null ? expectedAbstractionCompletionDate
                : (expectedAbstractionCompletionDate = DateUtils.addDays(
                        getLastCreated().getDateLastCreatedPlusTenBiz(),
                        getBizDaysOnHoldSubmitter()));
    }
    
    /**
     * Business Days Elapsed Since Submitted is a calculated field. It is equal
     * to Today's date minus Submission Date minus (weekend and federal holiday
     * days between Today date and the Submission Date, inclusive)
     * 
     * @return int BizDaysOnHoldSubmitter
     */
    public final Integer getBizDaysSinceSubmitted() {
        return bizDaysSinceSubmitted != null ? bizDaysSinceSubmitted
                : (bizDaysSinceSubmitted = PAUtil.getBusinessDaysBetween(
                        getLastCreated().getDateLastCreated(), new Date()));
    }
    
    /**
     * @return int BizDaysOnHoldSubmitter
     */
    public final Integer getBizDaysOnHoldSubmitter() {
        return bizDaysOnHoldSubmitter != null ? bizDaysOnHoldSubmitter
                : (bizDaysOnHoldSubmitter = getBizDaysOnHold("Submitter"));
    }

    /**
     * @return int BizDaysOnHoldCTRP
     */
    public final Integer getBizDaysOnHoldCTRP() {
        return bizDaysOnHoldCTRP != null ? bizDaysOnHoldCTRP
                : (bizDaysOnHoldCTRP = getBizDaysOnHold("CTRP"));
    }

    private int getBizDaysOnHold(String cat) {
        int days = 0;
        for (StudyOnholdDTO hold : getAllHolds()) {
            if (cat.equalsIgnoreCase(StConverter.convertToString(hold
                    .getOnHoldCategory()))) {
                Date holdStart = IvlConverter.convertTs().convertLow(
                        hold.getOnholdDate());
                Date holdEnd = IvlConverter.convertTs().convertHigh(
                        hold.getOnholdDate());
                days += PAUtil.getBusinessDaysBetween(holdStart,
                        holdEnd == null ? new Date() : holdEnd);
            }
        }
        return days;
    }

    /**
     * @return the recentHoldCategory
     */
    public String getRecentHoldCategory() {
        return recentHoldCategory;
    }

    /**
     * @param recentHoldCategory
     *            the recentHoldCategory to set
     */
    public void setRecentHoldCategory(String recentHoldCategory) {
        this.recentHoldCategory = recentHoldCategory;
    }

    /**
     * @return the allHolds
     */
    public List<StudyOnholdDTO> getAllHolds() {
        return allHolds;
    }

    /**
     * @param allHolds the allHolds to set
     */
    public void setAllHolds(List<StudyOnholdDTO> allHolds) {
        this.allHolds = allHolds;
    }
    
    /**
     * @return date
     */
    public Date getAdminAbstractionCompletedDate() {
        return getDateOfMilestone(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE);
    }
    
    /**
     * @return date
     */
    public Date getAdminQCCompletedDate() {
        return getDateOfMilestone(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE);
    }
    
    /**
     * @return date
     */
    public Date getScientificAbstractionCompletedDate() {
        return getDateOfMilestone(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE);
    }

    /**
     * @return date
     */
    public Date getScientificQCCompletedDate() {
        return getDateOfMilestone(MilestoneCode.SCIENTIFIC_QC_COMPLETE);
    }
    
    /**
     * @return date
     */
    public Date getReadyForTSRDate() {
        return getDateOfMilestone(MilestoneCode.READY_FOR_TSR);
    }
    
    /**
     * @return date when submission was accepted.
     */
    public Date getAcceptedDate() {
        return getDateOfMilestone(MilestoneCode.SUBMISSION_ACCEPTED);
    }

    private Date getDateOfMilestone(MilestoneCode mc) {
        MilestoneDTO milestone = findMilestoneInHistory(mc);
        return milestone != null ? milestone.getMilestoneDate() : null;
    }
    
}
