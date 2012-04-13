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
import gov.nih.nci.pa.enums.StudyStatusCode;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


/**
 * StudyProtocolQueryDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 07/22/2007
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.TooManyFields", "PMD.CyclomaticComplexity" })
public class StudyProtocolQueryDTO extends TrialSearchStudyProtocolQueryDTO implements Serializable {

    private static final long serialVersionUID = 8200069337460780484L;
    
    private boolean viewTSR;
    private boolean proprietaryTrial;
    private Date recordVerificationDate;
    private Boolean showSendXml = false;
    private String summ4FundingSrcCategory;
    private boolean searcherTrialOwner = false;
    private String nctNumber;
    
    private String phaseName;
    private Date startDate;
    private String sponsorName;
    private String summary4FundingSponsorName;
    private String responsiblePartyName;
    private String category;
    
    private String lastUpdatedUserDisplayName;
    
    private String lastUpdaterDisplayName;
    
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
        return this.proprietaryTrial ? "Abbreviated Trial" : "Complete Trial";
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
    public String getSummary4FundingSponsorName() {
        return summary4FundingSponsorName;
    }

    /**
     * 
     * @param summary4FundingSponsorName summary4FundingSponsorName
     */
    public void setSummary4FundingSponsorName(String summary4FundingSponsorName) {
        this.summary4FundingSponsorName = summary4FundingSponsorName;
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
     *  Determines whether to show actions dropdown or not.
     * @return boolean
     */
    public boolean isActionVisible() {
        return StringUtils.isNotEmpty(getUpdate()) 
            || StringUtils.isNotEmpty(getAmend()) 
            || StringUtils.isNotEmpty(getStatusChangeLinkText()) 
            || getShowSendXml().booleanValue() 
            || isCurrentUserCanAddSite() 
            || isCurrentUserCanEditSite();
    }
}
