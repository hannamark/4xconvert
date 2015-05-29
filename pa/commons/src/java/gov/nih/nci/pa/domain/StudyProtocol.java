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
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.AnatomicSiteComparator;
import gov.nih.nci.pa.util.LastCreatedComparator;
import gov.nih.nci.pa.util.NotEmptyIiExtension;
import gov.nih.nci.pa.util.NotEmptyIiRoot;
import gov.nih.nci.pa.util.StudyAlternateTitleComparator;
import gov.nih.nci.pa.util.StudyContactComparator;
import gov.nih.nci.pa.util.StudyInboxComparator;
import gov.nih.nci.pa.util.StudySiteComparator;
import gov.nih.nci.pa.util.ValidIi;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.search.Searchable;

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
public class StudyProtocol extends AbstractStudyProtocol implements Auditable {
    private static final String STUDY_PROTOCOL_MAPPING = "studyProtocol";

    private static final long serialVersionUID = 1234567890L;

    private AccrualReportingMethodCode accrualReportingMethodCode;
    private String acronym;
    private Boolean expandedAccessIndicator;
    private Boolean reviewBoardApprovalRequiredIndicator;
    private String keywordText;
    private Integer maximumTargetAccrualNumber;
    private Integer minimumTargetAccrualNumber;
    private Integer finalAccrualNumber;
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
    private String comments;
    private Integer processingPriority;
    private User assignedUser;
    private Boolean ctroOverride;
    private Boolean nciGrant;
    private StudySourceCode studySource;
    private Organization submitingOrganization;

    private Set<StudyOverallStatus> studyOverallStatuses = new TreeSet<StudyOverallStatus>(
            new StudyOverallStatusComparator());
    private Set<DocumentWorkflowStatus> documentWorkflowStatuses =
        new TreeSet<DocumentWorkflowStatus>(new LastCreatedComparator());
    private Set<StudySite> studySites = new TreeSet<StudySite>(new StudySiteComparator());
    private Set<StudyContact> studyContacts = new TreeSet<StudyContact>(new StudyContactComparator());
    private List<StudyResourcing> studyResourcings = new ArrayList<StudyResourcing>();
    private List<PlannedActivity> plannedActivities = new ArrayList<PlannedActivity>();
    private List<Arm> arms = new ArrayList<Arm>();
    private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();
    private Set<AnatomicSite> summary4AnatomicSites = new TreeSet<AnatomicSite>(new AnatomicSiteComparator());
    private Set<StudyMilestone> studyMilestones = new TreeSet<StudyMilestone>(new LastCreatedComparator());
    private List<StudyOnhold> studyOnholds = new ArrayList<StudyOnhold>();
    private List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    private List<PerformedActivity> performedActivities = new ArrayList<PerformedActivity>();
    private Set<StudyInbox> studyInbox = new TreeSet<StudyInbox>(new StudyInboxComparator());
    private Set<StudyCheckout> studyCheckout = new TreeSet<StudyCheckout>(new LastCreatedComparator());
    private Set<SecondaryPurpose> secondaryPurposes = new LinkedHashSet<SecondaryPurpose>();
    private Set<StudyAlternateTitle> studyAlternateTitles = new TreeSet<StudyAlternateTitle>(
            new StudyAlternateTitleComparator());
    private String secondaryPurposeOtherText;
    

    private Set<RegistryUser> studyOwners = new HashSet<RegistryUser>();
    
   
    private String ctroOverRideFlagComments;
  
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
    public void setAccrualReportingMethodCode(AccrualReportingMethodCode accrualReportingMethodCode) {
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
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
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
    @Sort(type = SortType.COMPARATOR, comparator = StudyOverallStatusComparator.class)
    @Where(clause = "deleted='false'")
    public Set<StudyOverallStatus> getStudyOverallStatuses() {
        return studyOverallStatuses;
    }

    /**
     *
     * @param studyOverallStatuses
     *            studyOverallStatuses
     */
    public void setStudyOverallStatuses(Set<StudyOverallStatus> studyOverallStatuses) {
        this.studyOverallStatuses = studyOverallStatuses;
    }

    /**
     *
     * @return documentWorkflowStatuses
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @Sort(type = SortType.COMPARATOR, comparator = LastCreatedComparator.class)
    public Set<DocumentWorkflowStatus> getDocumentWorkflowStatuses() {
        return documentWorkflowStatuses;
    }

    /**
     *
     * @param documentWorkflowStatuses
     *            documentWorkflowStatuses
     */
    public void setDocumentWorkflowStatuses(Set<DocumentWorkflowStatus> documentWorkflowStatuses) {
        this.documentWorkflowStatuses = documentWorkflowStatuses;
    }

    /**
     *
     * @return studySites
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @Sort(type = SortType.COMPARATOR, comparator = StudySiteComparator.class)
    @Searchable(nested = true)
    @Fetch(FetchMode.SUBSELECT)
    public Set<StudySite> getStudySites() {
        return studySites;
    }

    /**
     *
     * @param studySites
     *            studySites
     */
    public void setStudySites(Set<StudySite> studySites) {
        this.studySites = studySites;
    }

    /**
     *
     * @return studyContacts
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @Sort(type = SortType.COMPARATOR, comparator = StudyContactComparator.class)
    @Searchable(nested = true)
    public Set<StudyContact> getStudyContacts() {
        return studyContacts;
    }

    /**
     *
     * @param studyContacts
     *            studyContacts
     */
    public void setStudyContacts(Set<StudyContact> studyContacts) {
        this.studyContacts = studyContacts;
    }

    /**
     *
     * @return studyResourcings
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @Searchable(nested = true)
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
    @Searchable(nested = true)
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
    @Searchable(nested = true)
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
     * @return the StudyAnatomicSites
     */
    @ManyToMany
    @JoinTable(
            name = "study_anatomic_site",
            joinColumns = @JoinColumn(name = "study_protocol_identifier"),
            inverseJoinColumns = @JoinColumn(name = "anatomic_sites_identifier")
    )
    @ForeignKey(name = "FK_STUDY_ANATOMIC_SITE_STUDY_PROTOCOL", inverseName = "FK_STUDY_ANATOMIC_SITE_ANATOMIC_SITES")
    @Searchable(nested = true)
    public Set<AnatomicSite> getSummary4AnatomicSites() {
        return summary4AnatomicSites;
    }

    /**
     * @param myStudyAnatomicSites
     *            the StudyAnatomicSites to set
     */
    public void setSummary4AnatomicSites(Set<AnatomicSite> myStudyAnatomicSites) {
        this.summary4AnatomicSites = myStudyAnatomicSites;
    }

    /**
     * @return the studyMilestones
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Sort(type = SortType.COMPARATOR, comparator = LastCreatedComparator.class)
    public Set<StudyMilestone> getStudyMilestones() {
        return studyMilestones;
    }

    /**
     * @param studyMilestones
     *            the studyMilestones to set
     */
    public void setStudyMilestones(Set<StudyMilestone> studyMilestones) {
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
    @Searchable
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
     * @return the studyInbox
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Sort(type = SortType.COMPARATOR, comparator = StudyInboxComparator.class)
    public Set<StudyInbox> getStudyInbox() {
        return studyInbox;
    }

    /**
     * @param studyInbox the studyInbox to set
     */
    public void setStudyInbox(Set<StudyInbox> studyInbox) {
        this.studyInbox = studyInbox;
    }

    /**
     * @return the studyCheckout
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Sort(type = SortType.COMPARATOR, comparator = LastCreatedComparator.class)
    public Set<StudyCheckout> getStudyCheckout() {
        return studyCheckout;
    }

    /**
     * @param studyCheckout the studyCheckout to set
     */
    public void setStudyCheckout(Set<StudyCheckout> studyCheckout) {
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
     * @return the officialTitle
     */
    @Column(name = "OFFICIAL_TITLE")
    public String getOfficialTitle() {
        return super.getOfficialTitle();
    }
    
    /**
     * {@inheritDoc}
     */
    @CollectionOfElements(fetch = FetchType.EAGER)
    @Fetch (FetchMode.SELECT)
    @JoinTable(
            name = "STUDY_OTHERIDENTIFIERS",
            joinColumns = @JoinColumn(name = "STUDY_PROTOCOL_ID"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"STUDY_PROTOCOL_ID", "ROOT", "EXTENSION" }) }
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
    public Set<Ii> getOtherIdentifiers() {
        return super.getOtherIdentifiers();
    }
    
    /**
     * @return the secondaryPurposes
     */
    @ManyToMany(targetEntity = SecondaryPurpose.class, fetch = FetchType.EAGER)
    @JoinTable(name = "study_protocol_sec_purpose", joinColumns = 
        @JoinColumn(name = "study_protocol_id"), inverseJoinColumns = @JoinColumn(name = "secondary_purpose_id"))
    @ForeignKey(name = "study_protocol_sec_purpose_study_id", 
        inverseName = "study_protocol_sec_purpose_secondary_purpose_id")
    public Set<SecondaryPurpose> getSecondaryPurposes() {
        return secondaryPurposes;
    }

    /**
     * @param secondaryPurposes the secondaryPurposes to set
     */
    public void setSecondaryPurposes(Set<SecondaryPurpose> secondaryPurposes) {
        this.secondaryPurposes = secondaryPurposes;
    }

    /**
     * @return the comments
     */
    @Column(name = "comments")
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the processingPriority
     */
    @Column(name = "processing_priority")
    @Searchable
    public Integer getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority the processingPriority to set
     */
    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @return the assignedUser
     */
    @ManyToOne
    @JoinColumn(name = "assigned_user_id") 
    @Searchable
    public User getAssignedUser() {
        return assignedUser;
    }

    /**
     * @param assignedUser the assignedUser to set
     */
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * @return the ctroOverride
     */
    @Column(name = "ctro_override")
    @Searchable
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
     * @return the secondaryPurposeOtherText
     */
    @Column(name = "secondary_purpose_other_text")
    public String getSecondaryPurposeOtherText() {
        return secondaryPurposeOtherText;
    }

    /**
     * @param secondaryPurposeOtherText the secondaryPurposeOtherText to set
     */
    public void setSecondaryPurposeOtherText(String secondaryPurposeOtherText) {
        this.secondaryPurposeOtherText = secondaryPurposeOtherText;
    }

    /**
     * @return the nciGrant
     */
    @Column(name = "nci_grant")
    @Searchable
     public Boolean getNciGrant() {
        return nciGrant;
    }

    /**
     * @param nciGrant the nciGrant to set
     */
    public void setNciGrant(Boolean nciGrant) {
        this.nciGrant = nciGrant;
    }

    /**
     * @return the finalAccrualNumber
     */
    @Column(name = "final_accrual_num")
    public Integer getFinalAccrualNumber() {
        return finalAccrualNumber;
    }

    /**
     * @param finalAccrualNumber the finalAccrualNumber to set
     */
    public void setFinalAccrualNumber(Integer finalAccrualNumber) {
        this.finalAccrualNumber = finalAccrualNumber;
    }

    /**
     * @return study alternate titles
     */
    @OneToMany(mappedBy = STUDY_PROTOCOL_MAPPING, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Sort(type = SortType.COMPARATOR, comparator = StudyAlternateTitleComparator.class)
    public Set<StudyAlternateTitle> getStudyAlternateTitles() {
        return studyAlternateTitles;
    }

    /**
     * 
     * @param studyAlternateTitles study alternate titles to set.
     */
    public void setStudyAlternateTitles(
            Set<StudyAlternateTitle> studyAlternateTitles) {
        this.studyAlternateTitles = studyAlternateTitles;
    }
    
    /**
     * Gets the source of the study, ie i it was entered through registry, through batch, etc.
     * @return the source
     */
    @Column(name = "STUDY_SOURCE")
    @Enumerated(EnumType.STRING)
    public StudySourceCode getStudySource() {
        return studySource;
    }
  
    /**
     * Sets the source of the study, ie i it was entered through registry, through batch, etc.
     * @param source the source
     */
    public void setStudySource(StudySourceCode source) {
        studySource = source;
    }
    
    /**
     * @return the accrual disease code system (e.g. SDC)
     */
    @Column(name = "ACCRUAL_DISEASE_CODE_SYSTEM", nullable = false)
    public String getAccrualDiseaseCodeSystem() {
        return super.getAccrualDiseaseCodeSystem();
    }

    /**
     * @return the submitingOrganization
     */
    @ManyToOne
    @JoinColumn(name = "submitting_organization_id") 
    @Searchable
    public Organization getSubmitingOrganization() {
        return submitingOrganization;
    }
    
    /**
     * @param submitingOrganization the submitingOrganization to set
     */
    public void setSubmitingOrganization(Organization submitingOrganization) {
        this.submitingOrganization = submitingOrganization;
    }
    
   
    /**
     * @return ctroOverRideFlagComments
     */
    @Column(name = "ctro_override_flag_comments")
    public String getCtroOverRideFlagComments() {
        return ctroOverRideFlagComments;
    }
    
    
    /**
     * @param ctroOverRideFlagComments ctroOverRideFlagComments
     */
    public void setCtroOverRideFlagComments(String ctroOverRideFlagComments) {
        this.ctroOverRideFlagComments = ctroOverRideFlagComments;
    }

   

    /**
     * @return NciID
     */
    @Transient
    public String getNciID() {
       for (Ii ii: getOtherIdentifiers()) {
           if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
               return ii.getExtension();
           }
       }
       return StringUtils.EMPTY;
    }   
    
    
    
    /**
     * Sorts study overall statuses properly: by status dates in descending
     * order, and by id (descending) within the same date. Thus, this comparator
     * will always put the "current" trial status first in the collection.
     * 
     * @author dkrylov
     * 
     */
    public static final class StudyOverallStatusComparator implements
            Comparator<StudyOverallStatus>, Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5117406038792440978L;

        @Override
        @SuppressWarnings("PMD.NPathComplexity")
        public int compare(StudyOverallStatus sos1, StudyOverallStatus sos2) {
            Timestamp date1 = sos1.getStatusDate() != null ? sos1.getStatusDate() : new Timestamp(0);
            Timestamp date2 = sos2.getStatusDate() != null ? sos2.getStatusDate() : new Timestamp(0);
            
             Long id1 = sos1.getId() != null ? sos1.getId() : 0;
             Long id2 = sos2.getId() != null ? sos2.getId() : 0;
            if (date1.equals(date2)) {
                return -id1.compareTo(id2);
            }
            return -date1.compareTo(date2);
        }
    }
    
   
    
}
