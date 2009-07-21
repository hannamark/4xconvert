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
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;

import java.io.Serializable;
import java.util.Date;


/**
 * StudyProtocolQueryDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 07/22/2007
 */

@SuppressWarnings({ "PMD.TooManyFields" })
public class StudyProtocolQueryDTO implements Serializable {
    static final long serialVersionUID = 283476876L;

    private Long studyProtocolId;
    private String nciIdentifier;
    private String officialTitle;
    private StudyStatusCode studyStatusCode;
    private Date studyStatusDate;
    private DocumentWorkflowStatusCode documentWorkflowStatusCode;
    private Date documentWorkflowStatusDate;
    private String leadOrganizationName;
    private Long leadOrganizationId;
    private String piFullName;
    private Long piId;
    private String localStudyProtocolIdentifier;
    private StudyTypeCode studyTypeCode;
    private PhaseCode phaseCode;
    private String studyProtocolType;
    private String action;
    private String viewTSR;
    private String primaryPurpose;
    private String primaryPurposeOtherText;
    private String userLastCreated;
    private Date dateLastCreated;
    private String onHoldReasons;
    private String offHoldDates;
    private MilestoneCode studyMilsetone;
    private Date studyMilestoneDate;
    private String amendmentNumber;
    private Date amendmentDate;
    private SubmissionTypeCode submissionTypeCode;


   
    /**
     * @return the submissionTypeCode
     */
    public SubmissionTypeCode getSubmissionTypeCode() {
        return submissionTypeCode;
    }
    /**
     * @param submissionTypeCode the submissionTypeCode to set
     */
    public void setSubmissionTypeCode(SubmissionTypeCode submissionTypeCode) {
        this.submissionTypeCode = submissionTypeCode;
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
     * @return studyStatusCode
     */
    public StudyStatusCode getStudyStatusCode() {
        return studyStatusCode;
    }
    /**
     *
     * @param studyStatusCode studyStatusCode
     */
    public void setStudyStatusCode(StudyStatusCode studyStatusCode) {
        this.studyStatusCode = studyStatusCode;
    }
    /**
     *
     * @return studyStatusDate
     */
    public Date getStudyStatusDate() {
        return studyStatusDate;
    }
    /**
     *
     * @param studyStatusDate studyStatusDate
     */
    public void setStudyStatusDate(Date studyStatusDate) {
        this.studyStatusDate = studyStatusDate;
    }
    /**
     *
     * @return documentWorkflowStatusCode
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }
    /**
     *
     * @param documentWorkflowStatusCode documentWorkflowStatusCode
     */
    public void setDocumentWorkflowStatusCode(
            DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
    }
    /**
     *
     * @return documentWorkflowStatusDate
     */
    public Date getDocumentWorkflowStatusDate() {
        return documentWorkflowStatusDate;
    }
    /**
     *
     * @param documentWorkflowStatusDate documentWorkflowStatusDate
     */
    public void setDocumentWorkflowStatusDate(Date documentWorkflowStatusDate) {
        this.documentWorkflowStatusDate = documentWorkflowStatusDate;
    }
    /**
     *
     * @return leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }
    /**
     *
     * @param leadOrganizationName leadOrganizationName
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }
    /**
     *
     * @return leadOrganizationId
     */
    public Long getLeadOrganizationId() {
        return leadOrganizationId;
    }
    /**
     *
     * @param leadOrganizationId leadOrganizationId
     */
    public void setLeadOrganizationId(Long leadOrganizationId) {
        this.leadOrganizationId = leadOrganizationId;
    }
    /**
     *
     * @return piFullName
     */
    public String getPiFullName() {
        return piFullName;
    }
    /**
     *
     * @param piFullName piFullName
     */
    public void setPiFullName(String piFullName) {
        this.piFullName = piFullName;
    }
    /**
     *
     * @return piId
     */
    public Long getPiId() {
        return piId;
    }
    /**
     *
     * @param piId piId
     */
    public void setPiId(Long piId) {
        this.piId = piId;
    }

    /**
     *
     * @return localStudyProtocolIdentifier
     */
    public String getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     *
     * @param localStudyProtocolIdentifier localStudyProtocolIdentifier
     */
    public void setLocalStudyProtocolIdentifier(String localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
    /**
     *
     * @return studyTypeCode
     */
    public StudyTypeCode getStudyTypeCode() {
        return studyTypeCode;
    }
    /**
     *
     * @param studyTypeCode studyTypeCode
     */
    public void setStudyTypeCode(StudyTypeCode studyTypeCode) {
        this.studyTypeCode = studyTypeCode;
    }
    /**
     * @return the phaseCode
     */
    public PhaseCode getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(PhaseCode phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     *
     * @return studyProtocolType
     */
    public String getStudyProtocolType() {
        return studyProtocolType;
    }
    /**
     *
     * @param studyProtocolType studyProtocolType
     */
    public void setStudyProtocolType(String studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }
    /**
     *
     * @return action
     */
    public String getAction() {
        return action;
    }
    /**
     *
     * @param action action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return tsr
     */
    public String getViewTSR() {
        return viewTSR;
    }
    /**
     *
     * @param viewTSR tsr
     */
    public void setViewTSR(String viewTSR) {
        this.viewTSR = viewTSR;
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
     * @return the primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
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
     * @return dateLastCreated
     */
    public Date getDateLastCreated() {
      return dateLastCreated;
    }
    /**
     * @param dateLastCreated dateLastCreated
     */
    public void setDateLastCreated(Date dateLastCreated) {
      this.dateLastCreated = dateLastCreated;
    }
    /**
     * 
     * @return onHoldReasons 
     */
    public String getOnHoldReasons() {
        return onHoldReasons;
    }
    /**
     * 
     * @param onHoldReasons onHoldReasons
     */
    public void setOnHoldReasons(String onHoldReasons) {
        this.onHoldReasons = onHoldReasons;
    }
    /**
     * 
     * @return offHoldDates
     */
    public String getOffHoldDates() {
        return offHoldDates;
    }
    
    /**
     * 
     * @param offHoldDates offHoldDates
     */
    public void setOffHoldDates(String offHoldDates) {
        this.offHoldDates = offHoldDates;
    }
    
    /**
     * @param studyMilsetone the studyMilsetone to set
     */
    public void setStudyMilsetone(MilestoneCode studyMilsetone) {
        this.studyMilsetone = studyMilsetone;
    }
    /**
     * @return the studyMilsetone
     */
    public MilestoneCode getStudyMilsetone() {
        return studyMilsetone;
    }
    /**
     * @param studyMilestoneDate the studyMilestoneDate to set
     */
    public void setStudyMilestoneDate(Date studyMilestoneDate) {
        this.studyMilestoneDate = studyMilestoneDate;
    }
    /**
     * @return the studyMilestoneDate
     */
    public Date getStudyMilestoneDate() {
        return studyMilestoneDate;
    }
    /**
     * @param amendmentNumber the amendmentNumber to set
     */
    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }
    /**
     * @return the amendmentNumber
     */
    public String getAmendmentNumber() {
        return amendmentNumber;
    }
    /**
     * @param amendmentDate the amendmentDate to set
     */
    public void setAmendmentDate(Date amendmentDate) {
        this.amendmentDate = amendmentDate;
    }
    /**
     * @return the amendmentDate
     */
    public Date getAmendmentDate() {
        return amendmentDate;
    }

    
    
    


}
