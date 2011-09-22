/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;

/**
 * Converter for StudyProtocolQuery dto from study trial.
 * @author mshestopalov
 */
// need all these methods for now, but will be able to reduce once
// trial search and ad hoc report functionality is merged
@SuppressWarnings("PMD.TooManyMethods")
public class StudyProtocolQueryConverter {

    private static final int SUMM_FIELD_LASTNAME = 0;
    private static final int SUMM_FIELD_FIRSTNAME = 1;
    private static final int SUMM_FIELD_PI_ID = 2;
    private static final int SUMM_FIELD_LEADORG_NAME = 3;
    private static final int SUMM_FIELD_LEADORG_ID = 4;
    private static final int SUMM_FIELD_LOCAL_PROTOCOL_ID = 5;
    private static final int SUMM_FIELD_FUND_SRC_CAT = 6;
    private static final int SUMM_FIELD_STATUS_CODE = 7;
    private static final int SUMM_FIELD_STATUS_DATE = 8;
    private static final int SUMM_FIELD_DOC_WORKFLOW_STATUS = 9;
    private static final int SUMM_FIELD_DOC_WORKFLOW_DATE = 10;
    private static final int SUMM_FIELD_STUDY_INBOX_ID = 11;
    private static final int SUMM_FIELD_STUDY_INBOX_COMMENTS = 12;
    private static final int SUMM_FIELD_STUDY_INBOX_OPEN_DATE = 13;
    private static final int SUMM_FIELD_STUDY_INBOX_CLOSE_DATE = 14;

    private final RegistryUserServiceLocal registryUserService;
    private final PAServiceUtils paServiceUtils;

    private final String generateDiseaseNamesSql =
        "select d.PREFERRED_NAME from study_disease AS sd "
        + "inner JOIN PDQ_DISEASE AS d ON sd.DISEASE_IDENTIFIER = d.identifier "
        + "where sd.study_protocol_identifier = :spId order by d.PREFERRED_NAME";

    private final String generateInterventionTypeCodesSql =
        "select i.TYPE_CODE from PLANNED_ACTIVITY AS pa "
        + "inner JOIN INTERVENTION AS i ON pa.INTERVENTION_IDENTIFIER = i.identifier "
        + "where pa.study_protocol_identifier = :spId order by i.TYPE_CODE";

    /**
     * StudyProtocolQueryConverter.
     * @param registryUserSvc RegistryUserService
     * @param paSvcUtils pa service utils.
     */
    public StudyProtocolQueryConverter(RegistryUserServiceLocal registryUserSvc,
            PAServiceUtils paSvcUtils) {
        registryUserService = registryUserSvc;
        paServiceUtils = paSvcUtils;
    }
    /**
     * Convert trial domain object into a fully loaded dto for trial search and trial summary.
     * @param studyProtocol trial domain object
     * @param myTrialsOnly only able to view user's trials
     * @param potentialOwner trial owner
     * @return dto
     * @throws PAException when error
     */
    public StudyProtocolQueryDTO convertToStudyProtocolDto(StudyProtocol studyProtocol,
            boolean myTrialsOnly, RegistryUser potentialOwner) throws PAException {
        StudyProtocolQueryDTO studyProtocolDto = new StudyProtocolQueryDTO();

        if (!userHasAccess(potentialOwner, studyProtocolDto, studyProtocol, myTrialsOnly)) {
            return null;
        }

        setStudyProtocolFields(studyProtocolDto, studyProtocol);
        setStudyResourcing(studyProtocolDto, studyProtocol);
        setOverallStatus(studyProtocolDto, studyProtocol);
        setInboxAndSubmissionType(studyProtocolDto, studyProtocol);
        setOrganization(studyProtocolDto, studyProtocol);
        setPerson(studyProtocolDto, studyProtocol);
        setDocumentWorkflowStatus(studyProtocolDto, studyProtocol);
        studyProtocolDto.setNciIdentifier(PADomainUtils.getAssignedIdentifierExtension(studyProtocol));
        studyProtocolDto.setOtherIdentifiers(PADomainUtils.getOtherIdentifierExtensions(studyProtocol));
        studyProtocolDto.setDiseaseNames(PADomainUtils.getDiseaseNames(studyProtocol));
        studyProtocolDto.setInterventionType(PADomainUtils.getInterventionTypes(studyProtocol));
        if (studyProtocol.getUserLastCreated() != null) {
            studyProtocolDto.getLastCreated().setUserLastCreated(
                    studyProtocol.getUserLastCreated().getLoginName());

            studyProtocolDto.getLastCreated().setUserLastDisplayName(CsmUserUtil
                    .getDisplayUsername(studyProtocol.getUserLastCreated()));
        }
        setCheckout(studyProtocolDto, studyProtocol);

        PAUtil.convertMilestonesToDTO(studyProtocolDto.getMilestones(),
                studyProtocol.getStudyMilestones());

        String nctNumber = paServiceUtils.getStudyIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocol.getId()), PAConstants.NCT_IDENTIFIER_TYPE);
        studyProtocolDto.setNctNumber(nctNumber);
        return studyProtocolDto;
    }

    /**
     * Convert trial domain object into a fully loaded dto for ad hoc report.
     * @param studyProtocol trial domain object
     * @param myTrialsOnly only able to view user's trials
     * @param potentialOwner trial owner
     * @return dto
     * @throws PAException when error
     */
    public StudyProtocolQueryDTO convertToStudyProtocolDtoForReporting(StudyProtocol studyProtocol,
            boolean myTrialsOnly, RegistryUser potentialOwner) throws PAException {
        StudyProtocolQueryDTO studyProtocolDto = new StudyProtocolQueryDTO();

        if (!userHasAccess(potentialOwner, studyProtocolDto, studyProtocol, myTrialsOnly)) {
            return null;
        }

        setStudyProtocolFields(studyProtocolDto, studyProtocol);
        // We might be able to increase the performance of converting the milestones, but the process is very
        // complex, so use the slower conversion (for now)
        PAUtil.convertMilestonesToDTO(studyProtocolDto.getMilestones(),
                studyProtocol.getStudyMilestones());

        findTrialSummaryFields(studyProtocolDto, studyProtocol);

        studyProtocolDto.setNciIdentifier(PADomainUtils.getAssignedIdentifierExtension(studyProtocol));

        findDiseaseNameFields(studyProtocolDto, studyProtocol);
        findInterventionTypeFields(studyProtocolDto, studyProtocol);

        setViewTSR(studyProtocolDto, studyProtocolDto.getDocumentWorkflowStatusCode());
        return studyProtocolDto;
    }


    /**
     * Set checkout user.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setCheckout(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        if (CollectionUtils.isNotEmpty(studyProtocol.getStudyCheckout())) {
            for (StudyCheckout studyCheckout : studyProtocol.getStudyCheckout()) {
                switch (studyCheckout.getCheckOutType()) {
                case ADMINISTRATIVE:
                    studyProtocolDto.getAdminCheckout().setCheckoutBy(studyCheckout.getUserIdentifier());
                    studyProtocolDto.getAdminCheckout().setCheckoutId(studyCheckout.getId());
                    break;
                case SCIENTIFIC:
                    studyProtocolDto.getScientificCheckout().setCheckoutBy(studyCheckout.getUserIdentifier());
                    studyProtocolDto.getScientificCheckout().setCheckoutId(studyCheckout.getId());
                    break;
                default:
                    break;
                }
            }
        }
    }
    /**
     * Set overall status.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setOverallStatus(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // get studyOverallStatus
        StudyOverallStatus studyOverallStatus = studyProtocol.getStudyOverallStatuses().isEmpty() ? null
                : studyProtocol.getStudyOverallStatuses().iterator().next();
        if (studyOverallStatus != null) {
            studyProtocolDto.setStudyStatusCode(studyOverallStatus.getStatusCode());
            studyProtocolDto.setStudyStatusDate(studyOverallStatus.getStatusDate());
        }
    }

    /**
     * Set study resourcing.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setStudyResourcing(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        StudyResourcing studyResourcing = findSumm4FundingSrc(studyProtocol);
        studyProtocolDto.setSumm4FundingSrcCategory(studyResourcing != null
                && studyResourcing.getTypeCode() != null ? studyResourcing.getTypeCode().getCode() : null);

    }

    /**
     * Set Organization.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setOrganization(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // study site and organization
        StudySite studySite = studyProtocol.getStudySites().isEmpty() ? null
                : studyProtocol.getStudySites().iterator().next();
        Organization organization = (studySite != null) ? studySite.getResearchOrganization().getOrganization()
                : null;
        if (organization != null) {
            studyProtocolDto.setLeadOrganizationName(organization.getName());
            studyProtocolDto.setLeadOrganizationId(organization.getId());
        }

        if (studySite != null) {
            studyProtocolDto.setLocalStudyProtocolIdentifier(studySite.getLocalStudyProtocolIdentifier());
        }
    }

    private boolean userHasAccess(RegistryUser potentialOwner, StudyProtocolQueryDTO studyProtocolDto,
            StudyProtocol studyProtocol, boolean myTrialsOnly) throws PAException {
        if (potentialOwner != null) {
            studyProtocolDto.setSearcherTrialOwner(registryUserService.hasTrialAccess(potentialOwner,
                    studyProtocol.getId()));
        }
        return (myTrialsOnly && studyProtocolDto.isSearcherTrialOwner()) || !myTrialsOnly;

    }

    /**
     * Generate reporting sql.
     * @return sql.
     */
    protected static String generateReportingSql() {
        return "select crs_p.last_name, crs_p.first_name, crs_p.identifier, ro_org.name, ro_org.identifier, "
        + "ss.LOCAL_SP_INDENTIFIER, sr.type_code, sos.sosSc, sos.sosSd, dws.dwsSc, "
        + "dws.dwsSd, si.siId, si.siCm, si.siOd, si.siCd "
        + "from study_site AS ss "
        + "left JOIN research_organization AS ro ON ss.research_organization_identifier = ro.identifier "
        + "left JOIN organization AS ro_org ON ro.organization_identifier = ro_org.identifier "
        + "inner JOIN study_contact AS sc ON sc.study_protocol_identifier = ss.study_protocol_identifier "
        + "and sc.role_code = :piRole "
        + "left JOIN clinical_research_staff AS crs ON sc.clinical_research_staff_identifier = crs.identifier "
        + "left JOIN person AS crs_p ON crs.person_identifier = crs_p.identifier "
        + "left JOIN study_resourcing AS sr ON sr.study_protocol_identifier = ss.study_protocol_identifier and "
        + "sr.SUMM_4_REPT_INDICATOR = true "
        + "left join (select status_code as sosSc, status_date as sosSd, study_protocol_identifier as sosSpi from "
        + "study_overall_status where study_protocol_identifier = :spId order by identifier desc limit 1) AS sos "
        + "ON sos.sosSpi = ss.study_protocol_identifier "
        + "left join (select status_code as dwsSc, status_date_range_low as dwsSd, study_protocol_identifier as dwsSpi "
        + "from document_workflow_status where study_protocol_identifier = :spId order by identifier desc limit 1) "
        + "AS dws ON dws.dwsSpi = ss.study_protocol_identifier "
        + "left join (select identifier as siId, comments as siCm, open_date as siOd, close_date as siCd, "
        + "study_protocol_identifier as siSpi from study_inbox "
        + "where study_protocol_identifier = :spId order by identifier desc limit 1) AS si "
        + "ON si.siSpi = ss.study_protocol_identifier "
        + "where ss.study_protocol_identifier = :spId and ss.functional_code = :leadOrgRole";
    }

    private void findTrialSummaryFields(StudyProtocolQueryDTO spDto, StudyProtocol studyProtocol) {
        String sql = generateReportingSql();
        Query query = PaHibernateUtil.getCurrentSession().createSQLQuery(sql);
        query.setLong("spId", studyProtocol.getId());
        query.setString("leadOrgRole", StudySiteFunctionalCode.LEAD_ORGANIZATION.name());
        query.setString("piRole", StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.name());
        Object[] piData = (Object[]) query.uniqueResult();
        if (piData != null) {
            if (piData[1] == null) {
                spDto.setPiFullName((String) piData[SUMM_FIELD_LASTNAME]);
            } else {
                spDto.setPiFullName((String) piData[SUMM_FIELD_LASTNAME]
                                                    + ", " + (String) piData[SUMM_FIELD_FIRSTNAME]);
            }
            spDto.setPiId(((Integer) piData[SUMM_FIELD_PI_ID]).longValue());
            spDto.setLeadOrganizationName((String) piData[SUMM_FIELD_LEADORG_NAME]);
            spDto.setLeadOrganizationId(((Integer) piData[SUMM_FIELD_LEADORG_ID]).longValue());
            spDto.setLocalStudyProtocolIdentifier((String) piData[SUMM_FIELD_LOCAL_PROTOCOL_ID]);
            spDto.setSumm4FundingSrcCategory(SummaryFourFundingCategoryCode.valueOf(
                    (String) piData[SUMM_FIELD_FUND_SRC_CAT]).getCode());
            spDto.setStudyStatusCode(StudyStatusCode.valueOf((String) piData[SUMM_FIELD_STATUS_CODE]));
            spDto.setStudyStatusDate((Timestamp) piData[SUMM_FIELD_STATUS_DATE]);
            spDto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.valueOf(
                    (String) piData[SUMM_FIELD_DOC_WORKFLOW_STATUS]));
            spDto.setDocumentWorkflowStatusDate((Timestamp) piData[SUMM_FIELD_DOC_WORKFLOW_DATE]);
            if (piData[SUMM_FIELD_STUDY_INBOX_ID] != null) {
                spDto.setStudyInboxId(((Integer) piData[SUMM_FIELD_STUDY_INBOX_ID]).longValue());
            }
            spDto.setUpdatedComments((String) piData[SUMM_FIELD_STUDY_INBOX_COMMENTS]);
            spDto.setUpdatedDate((Timestamp) piData[SUMM_FIELD_STUDY_INBOX_OPEN_DATE]);
            Timestamp closedDate = (Timestamp) piData[SUMM_FIELD_STUDY_INBOX_CLOSE_DATE];
            setSubmissionType(spDto.getStudyInboxId() != null,
                    closedDate, spDto, studyProtocol);
        }
    }

    /**
     * Set document workflow status.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setDocumentWorkflowStatus(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // get documentWorkflowStatus
        DocumentWorkflowStatus documentWorkflowStatus = studyProtocol.getDocumentWorkflowStatuses().isEmpty()
                ? null : studyProtocol.getDocumentWorkflowStatuses().iterator().next();
        // transfer protocol to studyProtocolDto
        if (documentWorkflowStatus != null) {
            studyProtocolDto.setDocumentWorkflowStatusCode(documentWorkflowStatus.getStatusCode());
            studyProtocolDto.setDocumentWorkflowStatusDate(documentWorkflowStatus.getStatusDateRangeLow());
        }
        setViewTSR(studyProtocolDto, documentWorkflowStatus.getStatusCode());

    }

    /**
     * Set Inbox and Submission Type.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setInboxAndSubmissionType(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // get the StudyInbox
        StudyInbox studyInbox = studyProtocol.getStudyInbox().isEmpty() ? null
                : studyProtocol.getStudyInbox().iterator().next();

        if (studyInbox != null) {
            studyProtocolDto.setStudyInboxId(studyInbox.getId());
            studyProtocolDto.setUpdatedComments(studyInbox.getComments());
            studyProtocolDto.setUpdatedDate(studyInbox.getOpenDate());
        }
        setSubmissionType(studyInbox != null,
                studyInbox != null ? studyInbox.getCloseDate() : null,
                studyProtocolDto, studyProtocol);

    }

    /**
     * Set Person.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setPerson(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // get the person
        StudyContact sc = studyProtocol.getStudyContacts().isEmpty() ? null
                : studyProtocol.getStudyContacts().iterator().next();
        Person person = (sc != null) ? sc.getClinicalResearchStaff().getPerson() : null;

        if (person != null) {
            studyProtocolDto.setPiFullName(person.getFullName());
            studyProtocolDto.setPiId(person.getId());
        }
    }

    /**
     * Set Submission Type.
     * @param inboxExists does inbox record exist.
     * @param closedDate inbox close date.
     * @param spDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setSubmissionType(boolean inboxExists, Timestamp closedDate,
            StudyProtocolQueryDTO spDto, StudyProtocol studyProtocol) {
        if (isUpdated(inboxExists, closedDate)) {
            //Studies are considered updated if they have a study inbox entry without a closed date
            spDto.setSubmissionTypeCode(SubmissionTypeCode.U);
        } else if (isAmended(studyProtocol.getSubmissionNumber())) {
            // return amendment number and date only for amended trials
            spDto.setAmendmentNumber(studyProtocol.getAmendmentNumber());
            spDto.setAmendmentDate(studyProtocol.getAmendmentDate());
            spDto.setSubmissionTypeCode(SubmissionTypeCode.A);
        } else if (isOriginal(studyProtocol.getSubmissionNumber())) {
            spDto.setSubmissionTypeCode(SubmissionTypeCode.O);
        }
    }

    private boolean isUpdated(boolean inboxExists, Timestamp closedDate) {
        return inboxExists && closedDate == null;
    }

    private boolean isAmended(Integer subNumber) {
        return subNumber != null &&  subNumber.intValue() > 1;
    }

    private boolean isOriginal(Integer subNumber) {
        return subNumber != null &&  subNumber.intValue() == 1;
    }

    /**
     * Find Summ4 Funding Src Code.
     * @param studyProtocol trial domain
     * @return study resourcing.
     */
    private StudyResourcing findSumm4FundingSrc(StudyProtocol studyProtocol) {

        for (StudyResourcing item : studyProtocol.getStudyResourcings()) {
            if (item.getSummary4ReportedResourceIndicator()) {
                return item;
            }
        }

        return null;
    }

    /**
     * Set Study Protocol fields.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setStudyProtocolFields(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        if (studyProtocol instanceof ObservationalStudyProtocol) {
            studyProtocolDto.setStudyProtocolType("ObservationalStudyProtocol");
        } else {
            studyProtocolDto.setStudyProtocolType("InterventionalStudyProtocol");
        }
        studyProtocolDto.setOfficialTitle(studyProtocol.getOfficialTitle());
        studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
        studyProtocolDto.setPhaseCode(studyProtocol.getPhaseCode());
        if (studyProtocol.getPrimaryPurposeCode() != null) {
            studyProtocolDto.setPrimaryPurpose(studyProtocol.getPrimaryPurposeCode().getCode());
        }
        studyProtocolDto.setProprietaryTrial(
                BooleanUtils.toBoolean(studyProtocol.getProprietaryTrialIndicator()));
        studyProtocolDto.setRecordVerificationDate(studyProtocol.getRecordVerificationDate());
        studyProtocolDto.setCtgovXmlRequiredIndicator(
                BooleanUtils.toBoolean(studyProtocol.getCtgovXmlRequiredIndicator()));
        studyProtocolDto.setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
        studyProtocolDto.setPhaseAdditionalQualifier(studyProtocol.getPhaseAdditionalQualifierCode());
        studyProtocolDto.getLastCreated().setDateLastCreated(studyProtocol.getDateLastCreated());
    }

    /**
     * Set view TSR.
     * @param studyProtocolDto trial dto
     * @param documentWorkflowStatusCode process status code.
     */
    private void setViewTSR(StudyProtocolQueryDTO studyProtocolDto,
                DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        List<DocumentWorkflowStatusCode> nonViewableMilestones = new ArrayList<DocumentWorkflowStatusCode>();
        nonViewableMilestones.add(DocumentWorkflowStatusCode.SUBMITTED);
        nonViewableMilestones.add(DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED);
        nonViewableMilestones.add(DocumentWorkflowStatusCode.REJECTED);
        studyProtocolDto.setViewTSR(!nonViewableMilestones.contains(documentWorkflowStatusCode));
    }

    private void findDiseaseNameFields(StudyProtocolQueryDTO spDto, StudyProtocol studyProtocol) {
        Query query = PaHibernateUtil.getCurrentSession().createSQLQuery(generateDiseaseNamesSql);
        query.setLong("spId", studyProtocol.getId());
        Set<String> diseaseSet = new HashSet<String>();
        for (Object disease : query.list()) {
            diseaseSet.add((String) disease);
        }
        spDto.setDiseaseNames(diseaseSet);
    }

    private void findInterventionTypeFields(StudyProtocolQueryDTO spDto, StudyProtocol studyProtocol) {
        Query query = PaHibernateUtil.getCurrentSession().createSQLQuery(generateInterventionTypeCodesSql);
        query.setLong("spId", studyProtocol.getId());
        Set<String> interventionTypeSet = new HashSet<String>();
        for (Object interventionType : query.list()) {
            interventionTypeSet.add(InterventionTypeCode.valueOf((String) interventionType).getCode());
        }
        spDto.setInterventionType(interventionTypeSet);
    }
}
