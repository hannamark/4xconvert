/***
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common  control
 * with the entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in
 * its rights in the caBIG Software, including any copyright or patent rights therein, to (i) use,install,
 * disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties. For sake of clarity,and not by
 * way of limitation, caBIG Participant shall have no right of accounting or right of payment from You or Your
 * sub licensees for the rights granted under this License.   This  License  is  granted  at no  charge  to You.
 * Your downloading, copying, modifying, displaying, distributing or use of caBIG Software constitutes acceptance
 * of  all of the terms and conditions of this Agreement.  If You do not agree to such terms and conditions,
 * You have no right to download,  copy,  modify, display, distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object
 * code form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6
 * in the documentation and/or other materials provided with the distribution, if any.
 *
 * 2. Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such
 * third parties required to incorporate the caBIG Software  into such third party proprietary programs and for
 * informing Your sub licensees, including without limitation Your end-users, of their obligation  to  secure  any
 * required permissions from such third parties before incorporating the caBIG Software into such third party
 * proprietary software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in
 * Your sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a
 * whole, provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions
 * stated in this License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS caBIG SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */

package gov.nih.nci.pa.domain;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.util.NotEmptyIiExtension;
import gov.nih.nci.pa.util.NotEmptyIiRoot;
import gov.nih.nci.pa.util.ValidIi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;

/**
 * An action plan and execution of a pre-clinical or clinical study including
 * all activities to test a particular hypothesis that is the basis of the study
 * regarding the effectiveness of a particular treatment, drug, device,
 * procedure, or care plan. This includes prevention, observational,
 * therapeutic, and other types of studies that involve subjects.
 *
 * @author Naveen Amiruddin
 * @since 07/07/2007
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Study_Protocol_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "STUDY_PROTOCOL")
public class StudyProtocol extends AbstractStudyProtocol {
    /**
     *
     */
    private static final String STUDY_PROTOCOL_MAPPING = "studyProtocol";

    private static final long serialVersionUID = 1234567890L;

    private AccrualReportingMethodCode accrualReportingMethodCode;
    private String acronym;
    private Boolean expandedAccessIndicator;
    private Boolean reviewBoardApprovalRequiredIndicator;
    private String keywordText;
    private Integer maximumTargetAccrualNumber;
    private Integer minimumTargetAccrualNumber;
    private String publicDescription;
    private String publicTitle;
    private Timestamp recordVerificationDate;
    private String scientificDescription;
    private Boolean acceptHealthyVolunteersIndicator;
    private ActStatusCode statusCode;
    private Timestamp statusDate;
    private String amendmentNumber;
    private Timestamp amendmentDate;
    private AmendmentReasonCode amendmentReasonCode;
    private Integer submissionNumber;

    private List<StudyOverallStatus> studyOverallStatuses = new ArrayList<StudyOverallStatus>();
    private List<DocumentWorkflowStatus> documentWorkflowStatuses = new ArrayList<DocumentWorkflowStatus>();
    private List<StudySite> studySites = new ArrayList<StudySite>();
    private List<StudyContact> studyContacts = new ArrayList<StudyContact>();
    private List<StudyResourcing> studyResourcings = new ArrayList<StudyResourcing>();
    private List<PlannedActivity> plannedActivities = new ArrayList<PlannedActivity>();
    private List<Arm> arms = new ArrayList<Arm>();
    private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();
    private List<StudyMilestone> studyMilestones = new ArrayList<StudyMilestone>();
    private List<StudyOnhold> studyOnholds = new ArrayList<StudyOnhold>();
    private List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    private List<PerformedActivity> performedActivities = new ArrayList<PerformedActivity>();
    private List<Submission> submissions = new ArrayList<Submission>();
    private List<StudyInbox> studyInbox = new ArrayList<StudyInbox>();
    private List<StudyCheckout> studyCheckout = new ArrayList<StudyCheckout>();

    private Set<RegistryUser> studyOwners = new HashSet<RegistryUser>();
    /**
     * @return accrualReportingMethodCode
     */
    @Column(name = "ACCR_REPT_METH_CODE")
    @Enumerated(EnumType.STRING)
    public AccrualReportingMethodCode getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }

    /**
     * @param accrualReportingMethodCode
     *            accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(
            AccrualReportingMethodCode accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }

    /**
     * @return acronym
     */
    public String getAcronym() {
        return acronym;
    }

    /**
     * @param acronym
     *            acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    /**
     *
     * @return expandedAccessIndicator expandedAccessIndicator
     */
    @Column(name = "EXPD_ACCESS_INDIDICATOR")
    public Boolean getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     *
     * @param expandedAccessIndicator
     *            expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(Boolean expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }

    /**
     * @return the reviewBoardApprovalRequiredIndicator
     */
    @Column(name = "REVIEW_BRD_APPROVAL_REQ_INDICATOR")
    public Boolean getReviewBoardApprovalRequiredIndicator() {
        return reviewBoardApprovalRequiredIndicator;
    }

    /**
     * @param reviewBoardApprovalRequiredIndicator
     *            the reviewBoardApprovalRequiredIndicator to set
     */
    public void setReviewBoardApprovalRequiredIndicator(
            Boolean reviewBoardApprovalRequiredIndicator) {
        this.reviewBoardApprovalRequiredIndicator = reviewBoardApprovalRequiredIndicator;
    }

    /**
     *
     * @return keywordText
     */
    @Column(name = "KEYWORD_TEXT")
    public String getKeywordText() {
        return keywordText;
    }

    /**
     *
     * @param keywordText
     *            keywordText
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    /**
     *
     * @return maximumTargetAccrualNumber
     */
    @Column(name = "MAX_TARGET_ACCRUAL_NUM")
    public Integer getMaximumTargetAccrualNumber() {
        return maximumTargetAccrualNumber;
    }

    /**
     *
     * @param maximumTargetAccrualNumber
     *            maximumTargetAccrualNumber
     */
    public void setMaximumTargetAccrualNumber(Integer maximumTargetAccrualNumber) {
        this.maximumTargetAccrualNumber = maximumTargetAccrualNumber;
    }

    /**
     * @return the minimumTargetAccrualNumber
     */
    @Column(name = "MIN_TARGET_ACCRUAL_NUM")
    public Integer getMinimumTargetAccrualNumber() {
        return minimumTargetAccrualNumber;
    }

    /**
     * @param minimumTargetAccrualNumber
     *            the minimumTargetAccrualNumber to set
     */
    public void setMinimumTargetAccrualNumber(Integer minimumTargetAccrualNumber) {
        this.minimumTargetAccrualNumber = minimumTargetAccrualNumber;
    }

    /**
     *
     * @return publicDescription
     */
    @Column(name = "PUBLIC_DESCRIPTION")
    public String getPublicDescription() {
        return publicDescription;
    }

    /**
     *
     * @param publicDescription
     *            publicDescription
     */
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }

    /**
     *
     * @return publicTitle
     */
    @Column(name = "PUBLIC_TITTLE")
    public String getPublicTitle() {
        return publicTitle;
    }

    /**
     *
     * @param publicTitle
     *            publicTitle
     */
    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }

    /**
     *
     * @return recordVerificationDate
     */
    @Column(name = "RECORD_VERIFICATION_DATE")
    public Timestamp getRecordVerificationDate() {
        return recordVerificationDate;
    }

    /**
     *
     * @param recordVerificationDate
     *            recordVerificationDate
     */
    public void setRecordVerificationDate(Timestamp recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }

    /**
     *
     * @return scientificDescription
     */
    @Column(name = "SCIENTIFIC_DESCRIPTION")
    public String getScientificDescription() {
        return scientificDescription;
    }

    /**
     *
     * @param scientificDescription
     *            scientificDescription
     */
    public void setScientificDescription(String scientificDescription) {
        this.scientificDescription = scientificDescription;
    }

    /**
     *
     * @return studyOverallStatuses
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    public List<StudyOverallStatus> getStudyOverallStatuses() {
        return studyOverallStatuses;
    }

    /**
     *
     * @param studyOverallStatuses
     *            studyOverallStatuses
     */
    public void setStudyOverallStatuses(
            List<StudyOverallStatus> studyOverallStatuses) {
        this.studyOverallStatuses = studyOverallStatuses;
    }

    /**
     *
     * @return documentWorkflowStatuses
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    public List<DocumentWorkflowStatus> getDocumentWorkflowStatuses() {
        return documentWorkflowStatuses;
    }

    /**
     *
     * @param documentWorkflowStatuses
     *            documentWorkflowStatuses
     */
    public void setDocumentWorkflowStatuses(
            List<DocumentWorkflowStatus> documentWorkflowStatuses) {
        this.documentWorkflowStatuses = documentWorkflowStatuses;
    }

    /**
     *
     * @return studySites
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    public List<StudySite> getStudySites() {
        return studySites;
    }

    /**
     *
     * @param studySites
     *            studySites
     */
    public void setStudySites(List<StudySite> studySites) {
        this.studySites = studySites;
    }

    /**
     *
     * @return studyContacts
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    public List<StudyContact> getStudyContacts() {
        return studyContacts;
    }

    /**
     *
     * @param studyContacts
     *            studyContacts
     */
    public void setStudyContacts(List<StudyContact> studyContacts) {
        this.studyContacts = studyContacts;
    }

    /**
     *
     * @return studyResourcings
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    public List<StudyResourcing> getStudyResourcings() {
        return studyResourcings;
    }

    /**
     *
     * @param studyResourcings
     *            studyResourcings
     */
    public void setStudyResourcings(List<StudyResourcing> studyResourcings) {
        this.studyResourcings = studyResourcings;
    }

    /**
     * @return the plannedActivities
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<PlannedActivity> getPlannedActivities() {
        return plannedActivities;
    }

    /**
     * @param plannedActivities
     *            the plannedActivities to set
     */
    public void setPlannedActivities(List<PlannedActivity> plannedActivities) {
        this.plannedActivities = plannedActivities;
    }

    /**
     * @return the arms
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Arm> getArms() {
        return arms;
    }

    /**
     * @param arms
     *            the arms to set
     */
    public void setArms(List<Arm> arms) {
        this.arms = arms;
    }

    /**
     * @return acceptHealthyVolunteersIndicator
     */
    @Column(name = "ACCEPT_HEALTHY_VOLUNTEERS_INDICATOR")
    public Boolean getAcceptHealthyVolunteersIndicator() {
        return acceptHealthyVolunteersIndicator;
    }

    /**
     * @param acceptHealthyVolunteersIndicator
     *            acceptHealthyVolunteersIndicator
     */
    public void setAcceptHealthyVolunteersIndicator(
            Boolean acceptHealthyVolunteersIndicator) {
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
    }

    /**
     * @return the studyDiseases
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyDisease> getStudyDiseases() {
        return studyDiseases;
    }

    /**
     * @param studyDiseases
     *            the studyDiseases to set
     */
    public void setStudyDiseases(List<StudyDisease> studyDiseases) {
        this.studyDiseases = studyDiseases;
    }

    /**
     * @return the studyMilestones
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyMilestone> getStudyMilestones() {
        return studyMilestones;
    }

    /**
     * @param studyMilestones
     *            the studyMilestones to set
     */
    public void setStudyMilestones(List<StudyMilestone> studyMilestones) {
        this.studyMilestones = studyMilestones;
    }

    /**
     * @return the studyOnholds
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyOnhold> getStudyOnholds() {
        return studyOnholds;
    }

    /**
     * @param studyOnholds
     *            the studyOnholds to set
     */
    public void setStudyOnholds(List<StudyOnhold> studyOnholds) {
        this.studyOnholds = studyOnholds;
    }

    /**
     * @return the studySubjects
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudySubject> getStudySubjects() {
        return studySubjects;
    }

    /**
     * @param studySubjects
     *            the studySubjects to set
     */
    public void setStudySubjects(List<StudySubject> studySubjects) {
        this.studySubjects = studySubjects;
    }

    /**
     * @return the statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public ActStatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(ActStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusDate
     */
    @Column(name = "STATUS_DATE")
    public Timestamp getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate
     *            the statusDate to set
     */
    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the amendmentNumber
     */
    @Column(name = "AMENDMENT_NUMBER")
    public String getAmendmentNumber() {
        return amendmentNumber;
    }

    /**
     * @param amendmentNumber
     *            the amendmentNumber to set
     */
    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }

    /**
     * @return the amendmentDate
     */
    @Column(name = "AMENDMENT_DATE")
    public Timestamp getAmendmentDate() {
        return amendmentDate;
    }

    /**
     * @param amendmentDate
     *            the amendmentDate to set
     */
    public void setAmendmentDate(Timestamp amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    /**
     * @return the amendmentReasonCode
     */
    @Column(name = "AMENDMENT_REASON_CODE")
    @Enumerated(EnumType.STRING)
    public AmendmentReasonCode getAmendmentReasonCode() {
        return amendmentReasonCode;
    }

    /**
     * @param amendmentReasonCode
     *            the amendmentReasonCode to set
     */
    public void setAmendmentReasonCode(AmendmentReasonCode amendmentReasonCode) {
        this.amendmentReasonCode = amendmentReasonCode;
    }

    /**
     * @return the submissionNumber
     */
    @Column(name = "SUBMISSION_NUMBER")
    @NotNull
    public Integer getSubmissionNumber() {
        return submissionNumber;
    }

    /**
     * @param submissionNumber
     *            the submissionNumber to set
     */
    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    /**
     * @return the performedActivities
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<PerformedActivity> getPerformedActivities() {
        return performedActivities;
    }

    /**
     * @param performedActivities
     *            the performedActivities to set
     */
    public void setPerformedActivities(
            List<PerformedActivity> performedActivities) {
        this.performedActivities = performedActivities;
    }

    /**
     * @return the submissions
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * @param submissions
     *            the submissions to set
     */
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * @return the studyInbox
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyInbox> getStudyInbox() {
        return studyInbox;
    }
    /**
     * @param studyInbox the studyInbox to set
     */
    public void setStudyInbox(List<StudyInbox> studyInbox) {
        this.studyInbox = studyInbox;
    }

    /**
     * @return the studyCheckout
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyCheckout> getStudyCheckout() {
        return studyCheckout;
    }

    /**
     * @param studyCheckout the studyCheckout to set
     */
    public void setStudyCheckout(List<StudyCheckout> studyCheckout) {
        this.studyCheckout = studyCheckout;
    }

    /**
     * @return the studyOwners
     */
     @ManyToMany(mappedBy = "studyProtocols")
     public Set<RegistryUser> getStudyOwners() {
        return studyOwners;
    }

    /**
     * @param studyOwners the studyOwners to set
     */
    public void setStudyOwners(Set<RegistryUser> studyOwners) {
        this.studyOwners = studyOwners;
    }

    /**
     * {@inheritDoc}
     */
    @CollectionOfElements(fetch = FetchType.EAGER)
    @Fetch (FetchMode.SELECT)
    @JoinTable(
            name = "STUDY_OTHERIDENTIFIERS",
            joinColumns = @JoinColumn(name = "STUDY_PROTOCOL_ID")
    )
    @ForeignKey(name = "STUDY_OI_FK")
    @Type(type = "gov.nih.nci.pa.iso.util.IiCompositeUserType")
    @Columns(columns = {
            @Column(name = "null_flavor"),
            @Column(name = "displayable"),
            @Column(name = "extension"),
            @Column(name = "identifier_name"),
            @Column(name = "reliability"),
            @Column(name = "root"),
            @Column(name = "scope")
    })
    @ValidIi
    @NotEmptyIiExtension
    @NotEmptyIiRoot
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public Set<Ii> getOtherIdentifiers() {
        return super.getOtherIdentifiers();
    }

}
