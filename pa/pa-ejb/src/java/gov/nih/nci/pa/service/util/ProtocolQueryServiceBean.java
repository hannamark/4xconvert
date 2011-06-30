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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.StudyProtocolBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolOptions;
import gov.nih.nci.pa.service.search.StudyProtocolQueryBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolSortCriterion;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Naveen Amiruddin
 * @since 08/13/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(PaHibernateSessionInterceptor.class)
public class ProtocolQueryServiceBean extends AbstractBaseSearchBean<StudyProtocol>
    implements ProtocolQueryServiceLocal {

    @EJB
    private RegistryUserServiceLocal registryUserService;
    private static final Logger LOG = Logger.getLogger(ProtocolQueryServiceBean.class);

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(StudyProtocolQueryCriteria spsc) throws PAException {
        if (isCriteriaEmpty(spsc)) {
            throw new PAException("At least one criteria is required.");
        }
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        List<StudyProtocol> queryList = getStudyProtocolQueryResults(spsc);
        pdtos = convertToStudyProtocolDTO(queryList, spsc.getUserId(), BooleanUtils.toBoolean(spsc.isMyTrialsOnly()));
        if (CollectionUtils.isNotEmpty(pdtos)) {
            pdtos = appendOnHold(pdtos);
        }
        return pdtos;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) throws PAException {
        if (studyProtocolId == null) {
            LOG.error(" studyProtocol Identifier cannot be null ");
            throw new PAException(" studyProtocol Identifier cannot be null ");
        }

        Session session = PaHibernateUtil.getCurrentSession();
        // PO-3608. Session needs to be cleared, or else, when calling save first,
        // an incomplete object is returned.
        session.flush();
        session.clear();

        StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();
        spqc.setStudyProtocolId(studyProtocolId);
        List<StudyProtocol> queryList = getStudyProtocolQueryResults(spqc);
        if (queryList == null) {
            throw new PAException(" Study protocol was not found for id " + studyProtocolId);
        }
        List<StudyProtocolQueryDTO> trialSummaries = convertToStudyProtocolDTO(queryList, null, false);

        if (trialSummaries == null || trialSummaries.size() <= 0) {
            throw new PAException(" Could not be converted to DTO for id " + studyProtocolId);
        }
        return trialSummaries.get(0);
    }

    /**
     * Converts Study protocols objects to dtos.
     * @param protocolQueryResult the resulting study protocols
     * @param userId the id of the person calling the search
     * @param myTrialsOnly whether to return trials for the given user only
     * @return a list of StudyProtocolQueryDTOs
     * @throws PAException on error
     */
    private List<StudyProtocolQueryDTO> convertToStudyProtocolDTO(List<StudyProtocol> protocolQueryResult, Long userId,
            boolean myTrialsOnly)
        throws PAException {
        List<StudyProtocolQueryDTO> studyProtocolDtos = new ArrayList<StudyProtocolQueryDTO>();
        RegistryUser potentialOwner = userId == null ? null : registryUserService.getUserById(userId);
        try {
            for (StudyProtocol studyProtocol : protocolQueryResult) {
                StudyProtocolQueryDTO studyProtocolDto = new StudyProtocolQueryDTO();

                // get documentWorkflowStatus
                DocumentWorkflowStatus documentWorkflowStatus = studyProtocol.getDocumentWorkflowStatuses().isEmpty()
                        ? null : studyProtocol.getDocumentWorkflowStatuses().iterator().next();

                StudyMilestone studyMilestone = studyProtocol.getStudyMilestones().isEmpty() ? null
                        : studyProtocol.getStudyMilestones().iterator().next();
                // get studyOverallStatus
                StudyOverallStatus studyOverallStatus = studyProtocol.getStudyOverallStatuses().isEmpty() ? null
                        : studyProtocol.getStudyOverallStatuses().iterator().next();
                // get the person
                StudyContact sc = studyProtocol.getStudyContacts().isEmpty() ? null
                        : studyProtocol.getStudyContacts().iterator().next();
                Person person = (sc != null) ? sc.getClinicalResearchStaff().getPerson() : null;

                // study site and organization
                StudySite studySite = studyProtocol.getStudySites().isEmpty() ? null
                        : studyProtocol.getStudySites().iterator().next();
                Organization organization = (studySite != null) ? studySite.getResearchOrganization().getOrganization()
                        : null;
                // get the StudyInbox
                StudyInbox studyInbox = studyProtocol.getStudyInbox().isEmpty() ? null
                        : studyProtocol.getStudyInbox().iterator().next();
                StudyResourcing studyResourcing = findSumm4FundingSrc(studyProtocol);

                // transfer protocol to studyProtocolDto
                if (documentWorkflowStatus != null) {
                    studyProtocolDto.setDocumentWorkflowStatusCode(documentWorkflowStatus.getStatusCode());
                    studyProtocolDto.setDocumentWorkflowStatusDate(documentWorkflowStatus.getStatusDateRangeLow());
                }
                if (studyProtocol instanceof ObservationalStudyProtocol) {
                    studyProtocolDto.setStudyProtocolType("ObservationalStudyProtocol");
                } else {
                    studyProtocolDto.setStudyProtocolType("InterventionalStudyProtocol");
                }
                studyProtocolDto.setOfficialTitle(studyProtocol.getOfficialTitle());
                studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
                studyProtocolDto.setNciIdentifier(PADomainUtils.getAssignedIdentifierExtension(studyProtocol));
                studyProtocolDto.setOtherIdentifiers(PADomainUtils.getOtherIdentifierExtensions(studyProtocol));
                studyProtocolDto.setDiseaseNames(PADomainUtils.getDiseaseNames(studyProtocol));
                studyProtocolDto.setInterventionType(PADomainUtils.getInterventionTypes(studyProtocol));
                studyProtocolDto.setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
                studyProtocolDto.setPhaseCode(studyProtocol.getPhaseCode());
                studyProtocolDto.setPhaseAdditionalQualifier(studyProtocol.getPhaseAdditionalQualifierCode());
                if (studyProtocol.getUserLastCreated() != null) {
                    studyProtocolDto.setUserLastCreated(studyProtocol.getUserLastCreated().getLoginName());
                }
                if (studyProtocol.getPrimaryPurposeCode() != null) {
                    studyProtocolDto.setPrimaryPurpose(studyProtocol.getPrimaryPurposeCode().getCode());
                }
                studyProtocolDto.setDateLastCreated(studyProtocol.getDateLastCreated());
                studyProtocolDto.setSumm4FundingSrcCategory(studyResourcing != null
                        && studyResourcing.getTypeCode() != null ? studyResourcing.getTypeCode().getCode() : null);
                if (studyInbox != null && studyInbox.getCloseDate() == null) {
                    //Studies are considered updated if they have a study inbox entry without a closed date
                    studyProtocolDto.setSubmissionTypeCode(SubmissionTypeCode.U);
                } else if (studyProtocol.getSubmissionNumber() != null
                        &&  studyProtocol.getSubmissionNumber().intValue() > 1) {
                    // return amendment number and date only for amended trials
                    studyProtocolDto.setAmendmentNumber(studyProtocol.getAmendmentNumber());
                    studyProtocolDto.setAmendmentDate(studyProtocol.getAmendmentDate());
                    studyProtocolDto.setSubmissionTypeCode(SubmissionTypeCode.A);
                } else if (studyProtocol.getSubmissionNumber() != null
                        &&  studyProtocol.getSubmissionNumber().intValue() == 1) {
                    studyProtocolDto.setSubmissionTypeCode(SubmissionTypeCode.O);
                }
                studyProtocolDto.setProprietaryTrial(
                        BooleanUtils.toBoolean(studyProtocol.getProprietaryTrialIndicator()));
                studyProtocolDto.setRecordVerificationDate(studyProtocol.getRecordVerificationDate());
                studyProtocolDto.setCtgovXmlRequiredIndicator(
                        BooleanUtils.toBoolean(studyProtocol.getCtgovXmlRequiredIndicator()));
                if (studyMilestone != null) {
                    studyProtocolDto.setStudyMilsetone(studyMilestone.getMilestoneCode());
                    studyProtocolDto.setStudyMilestoneDate(studyMilestone.getMilestoneDate());
                }
                if (studyOverallStatus != null) {
                    studyProtocolDto.setStudyStatusCode(studyOverallStatus.getStatusCode());
                    studyProtocolDto.setStudyStatusDate(studyOverallStatus.getStatusDate());
                }
                if (organization != null) {
                    studyProtocolDto.setLeadOrganizationName(organization.getName());
                    studyProtocolDto.setLeadOrganizationId(organization.getId());
                }
                if (person != null) {
                    studyProtocolDto.setPiFullName(person.getFullName());
                    studyProtocolDto.setPiId(person.getId());
                }
                if (studySite != null) {
                    studyProtocolDto.setLocalStudyProtocolIdentifier(studySite.getLocalStudyProtocolIdentifier());
                }
                if (studyInbox != null) {
                    studyProtocolDto.setStudyInboxId(studyInbox.getId());
                    studyProtocolDto.setUpdatedComments(studyInbox.getComments());
                    studyProtocolDto.setUpdatedDate(studyInbox.getOpenDate());
                }
                if (CollectionUtils.isNotEmpty(studyProtocol.getStudyCheckout())) {
                    for (StudyCheckout studyCheckout : studyProtocol.getStudyCheckout()) {
                        switch (studyCheckout.getCheckOutType()) {
                        case ADMINISTRATIVE:
                            studyProtocolDto.setStudyAdminCheckoutBy(studyCheckout.getUserIdentifier());
                            studyProtocolDto.setStudyAdminCheckoutId(studyCheckout.getId());
                            break;
                        case SCIENTIFIC:
                            studyProtocolDto.setStudyScientificCheckoutBy(studyCheckout.getUserIdentifier());
                            studyProtocolDto.setStudyScientificCheckoutId(studyCheckout.getId());
                            break;
                        default:
                            break;
                        }
                    }
                }
                if (potentialOwner != null) {
                    studyProtocolDto.setSearcherTrialOwner(registryUserService.hasTrialAccess(potentialOwner,
                            studyProtocol.getId()));
                }

                List<DocumentWorkflowStatusCode> nonViewableMilestones = new ArrayList<DocumentWorkflowStatusCode>();
                nonViewableMilestones.add(DocumentWorkflowStatusCode.SUBMITTED);
                nonViewableMilestones.add(DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED);
                nonViewableMilestones.add(DocumentWorkflowStatusCode.REJECTED);
                studyProtocolDto.setViewTSR(!nonViewableMilestones.contains(documentWorkflowStatus));

                if ((myTrialsOnly && studyProtocolDto.isSearcherTrialOwner()) || !myTrialsOnly) {
                    studyProtocolDtos.add(studyProtocolDto);
                }


            }
        } catch (Exception e) {
            throw new PAException("General error in while converting to StudyProtocolQueryDTO", e);
        }
        return studyProtocolDtos;
    }

    private StudyResourcing findSumm4FundingSrc(StudyProtocol studyProtocol) {

        for (StudyResourcing item : studyProtocol.getStudyResourcings()) {
            if (item.getSummary4ReportedResourceIndicator()) {
                return item;
            }
        }

        return null;
    }


    @SuppressWarnings("unchecked")
    private List<StudyProtocolQueryDTO> appendOnHold(List<StudyProtocolQueryDTO> spDtos) throws PAException {
        StringBuffer hql = new StringBuffer();
        Session session = null;
        long[] ids = getProtocolIds(spDtos);
        session = PaHibernateUtil.getCurrentSession();
        hql.append(" select sp , soh from StudyProtocol as sp  join sp.studyOnholds as soh where sp.id in ( ");
        for (int i = 0; i < ids.length; i++) {
            hql.append(ids[i]);
            hql.append((i < ids.length - 1 ? " ," : " "));
        }
        hql.append(" ) and soh.offholdDate is null");
        Query query = null;
        query = session.createQuery(hql.toString());
        Map<Long, List<StudyOnhold>> studyOnHolds = generateOnholdMap(query.list());
        populateOnHoldData(spDtos, studyOnHolds);
        return spDtos;
    }

   private Map<Long , List<StudyOnhold>> generateOnholdMap(List<Object> onHoldReasons) {
        Object[] searchResult = null;
        StudyProtocol studyProtocol = null;
        StudyOnhold studyOnhold = null;
        Map<Long , List<StudyOnhold>> studyOnHolds = new HashMap<Long , List<StudyOnhold>>();
        ArrayList<StudyOnhold> sohList = null;
        for (int i = 0; i < onHoldReasons.size(); i++) {
            searchResult = (Object[]) onHoldReasons.get(i);
            if (searchResult == null) {
                break;
            }
            studyProtocol = (StudyProtocol) searchResult[0];
            studyOnhold = (StudyOnhold) searchResult[1];
            if (studyOnHolds.containsKey(studyProtocol.getId())) {
                studyOnHolds.get(studyProtocol.getId()).add(studyOnhold);
            } else {
                sohList = new ArrayList<StudyOnhold>();
                sohList.add(studyOnhold);
                studyOnHolds.put(studyProtocol.getId(), sohList);
            }
        }
        return studyOnHolds;
    }

    private void populateOnHoldData(List<StudyProtocolQueryDTO> spDtos, Map<Long, List<StudyOnhold>> onHold) {
        List<StudyOnhold> sohLists = null;
        StringBuffer sb = new StringBuffer();
        StringBuffer sbDate = new StringBuffer();
        for (StudyProtocolQueryDTO spqDto : spDtos) {
            if (onHold.containsKey(spqDto.getStudyProtocolId())) {
                sohLists = onHold.get(spqDto.getStudyProtocolId());
                sb = new StringBuffer();
                sbDate = new StringBuffer();
                for (StudyOnhold studyOnhold : sohLists) {
                    if (sb.length() == 0) {
                        sb.append(studyOnhold.getOnholdReasonCode().getCode());
                    } else {
                        sb.append(PAConstants.COMMA).append(studyOnhold.getOnholdReasonCode().getCode());
                    }
                    sbDate.append(PAUtil.normalizeDateString((
                                studyOnhold.getOnholdDate()).toString())).append(PAConstants.WHITESPACE);
                }
                spqDto.setOnHoldReasons(sb.toString());
                spqDto.setOffHoldDates(sbDate.toString());
            }
        }
    }


    private long[] getProtocolIds(List<StudyProtocolQueryDTO> spDtos) {
        long[] pids = new long[spDtos.size()];
        int x = 0;
        for (StudyProtocolQueryDTO spqDto : spDtos) {
            pids[x++] = spqDto.getStudyProtocolId().longValue();
        }
        return pids;
    }

    /**
     *
     * @param criteria
     *            studyProtocolQueryCriteria
     * @return List queryList
     * @throws PAException
     *             paException
     */
    private List<StudyProtocol> getStudyProtocolQueryResults(StudyProtocolQueryCriteria criteria)
    throws PAException {
        StudyProtocol example = new StudyProtocol();
        StudyProtocolOptions options = new StudyProtocolOptions();
        options.setUserId(criteria.getUserId());
        options.setExcludeRejectedTrials(BooleanUtils.isTrue(criteria.isExcludeRejectProtocol()));
        options.setLockedTrials(criteria.isStudyLockedBy());
        options.setLockedUser(criteria.getUserLastCreated());
        options.setMyTrialsOnly(BooleanUtils.isTrue(criteria.isMyTrialsOnly()));
        options.setParticipatingSite(StringUtils.equals(criteria.getOrganizationType(),
                PAConstants.PARTICIPATING_SITE));
        options.setParticipatingSiteId(criteria.getParticipatingSiteId() == null ? null
                : Long.valueOf(criteria.getParticipatingSiteId()));
        options.setTrialSubmissionType(SubmissionTypeCode.getByCode(criteria.getSubmissionType()));
        options.setSearchOnHoldTrials(criteria.isSearchOnHold());
        options.setInboxProcessing(BooleanUtils.isTrue(criteria.isInBoxProcessing()));

        populateExample(criteria, example);
        StudyProtocolQueryBeanSearchCriteria crit = new StudyProtocolQueryBeanSearchCriteria(example, options);
        List<StudyProtocol> results = new ArrayList<StudyProtocol>();
        try {
            results = search(crit);
        } catch (Exception e) {
            throw new PAException("An error has occurred when searching for trials.", e);
        }
        if (results.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new PAException("Results exceed more than 500.  Please refine the search criteria.");
        }
        return results;
    }

    private void populateExampleStudyProtocol(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        sp.setId(crit.getStudyProtocolId());
        sp.setOfficialTitle(crit.getOfficialTitle());
        sp.setPhaseCode(PhaseCode.getByCode(crit.getPhaseCode()));
        sp.setPhaseAdditionalQualifierCode(
                PhaseAdditionalQualifierCode.getByCode(crit.getPhaseAdditionalQualifierCode()));
        if (StringUtils.equalsIgnoreCase(crit.getTrialCategory(), "p")) {
            sp.setProprietaryTrialIndicator(Boolean.TRUE);
        } else if (StringUtils.equalsIgnoreCase(crit.getTrialCategory(), "n")) {
            sp.setProprietaryTrialIndicator(Boolean.FALSE);
        }

        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(crit.getPrimaryPurposeCode()));
        populateExampleStudyProtocolDiseaseInterventionType(crit, sp);
    }
    private void populateExampleStudyProtocolSumm4FundSrc(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (crit.getSumm4FundingSourceId() != null
                || StringUtils.isNotBlank(crit.getSumm4FundingSourceTypeCode())) {
            StudyResourcing stdRes = new StudyResourcing();
            if (crit.getSumm4FundingSourceId() != null
                    && StringUtils.isNotBlank(crit.getSumm4FundingSourceId().toString())) {
                stdRes.setOrganizationIdentifier(crit.getSumm4FundingSourceId().toString());
            }
            stdRes.setSummary4ReportedResourceIndicator(true);
            stdRes.setTypeCode(SummaryFourFundingCategoryCode.getByCode(crit.getSumm4FundingSourceTypeCode()));
            stdRes.setActiveIndicator(true);
            sp.getStudyResourcings().add(stdRes);
        }
    }
    private void populateExampleStudyProtocolDiseaseInterventionType(
            StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        populateExampleStudyProtocolSumm4FundSrc(crit, sp);

        if (crit.getDiseaseConditionId() != null) {
            StudyDisease stdDis = new StudyDisease();
            PDQDisease des = new PDQDisease();
            des.setId(crit.getDiseaseConditionId());
            stdDis.setDisease(des);
            sp.getStudyDiseases().add(stdDis);
        }

        if (crit.getInterventionType() != null) {
            PlannedActivity plndAct = new PlannedActivity();
            Intervention intVen = new Intervention();
            intVen.setTypeCode(InterventionTypeCode.getByCode(crit.getInterventionType()));
            plndAct.setIntervention(intVen);
            sp.getPlannedActivities().add(plndAct);
        }
    }

    private void populateExampleSpOtherIdentifiers(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getNciIdentifier())) {
            Ii nciId = new Ii();
            nciId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            nciId.setExtension(crit.getNciIdentifier());
            sp.getOtherIdentifiers().add(nciId);
        }

        if (StringUtils.isNotEmpty(crit.getOtherIdentifier())) {
            Ii otherId = new Ii();
            otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
            otherId.setExtension(crit.getOtherIdentifier());
            sp.getOtherIdentifiers().add(otherId);
        }
    }

    private void populateExampleStudyOverallStatus(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getStudyStatusCode())) {
            StudyOverallStatus sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.getByCode(crit.getStudyStatusCode()));
            sp.getStudyOverallStatuses().add(sos);
        }
    }

    private void populateExampleDocumentWork(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getDocumentWorkflowStatusCode())) {
            DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
            dws.setStatusCode(DocumentWorkflowStatusCode.getByCode(crit.getDocumentWorkflowStatusCode()));
            sp.getDocumentWorkflowStatuses().add(dws);
        }
    }

    private void populateExampleStudySites(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getLeadOrganizationTrialIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getLeadOrganizationTrialIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getNctNumber())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getNctNumber());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTGOV_ORG_NAME));
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getCtepIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getCtepIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTEP_ORG_NAME));
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getDcpIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getDcpIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.DCP_ORG_NAME));
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getLeadOrganizationId())) {
            StudySite ss = new StudySite();
            Organization organization = new Organization();
            organization.setId(Long.valueOf(crit.getLeadOrganizationId()));
            ResearchOrganization ro = new ResearchOrganization();
            ro.setOrganization(organization);
            ss.setResearchOrganization(ro);
            ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
            sp.getStudySites().add(ss);
        }
    }

    private void populateExampleStudyMilestones(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getStudyMilestone())) {
            StudyMilestone sm = new StudyMilestone();
            sm.setMilestoneCode(MilestoneCode.getByCode(crit.getStudyMilestone()));
            sp.getStudyMilestones().add(sm);
        }
    }

    private void populateExampleStudyContacts(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getPrincipalInvestigatorId())) {
            StudyContact sc = new StudyContact();
            ClinicalResearchStaff crs = new ClinicalResearchStaff();
            Person person = new Person();
            person.setId(Long.valueOf(crit.getPrincipalInvestigatorId()));
            crs.setPerson(person);
            sc.setClinicalResearchStaff(crs);
            sc.setRoleCode(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
            sp.getStudyContacts().add(sc);
        }
    }

    private void populateExample(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        populateExampleStudyProtocol(crit, sp);
        populateExampleSpOtherIdentifiers(crit, sp);
        populateExampleStudyOverallStatus(crit, sp);
        populateExampleDocumentWork(crit, sp);
        populateExampleStudySites(crit, sp);
        populateExampleStudyMilestones(crit, sp);
        populateExampleStudyContacts(crit, sp);
    }

    private boolean isCriteriaEmpty(StudyProtocolQueryCriteria criteria) {
        return (StringUtils.isEmpty(criteria.getNciIdentifier())
                && StringUtils.isEmpty(criteria.getOfficialTitle())
                && StringUtils.isEmpty(criteria.getLeadOrganizationId())
                && StringUtils.isEmpty(criteria.getLeadOrganizationTrialIdentifier())
                && StringUtils.isEmpty(criteria.getPrincipalInvestigatorId())
                && StringUtils.isEmpty(criteria.getPrimaryPurposeCode())
                && StringUtils.isEmpty(criteria.getPhaseCode())
                && StringUtils.isEmpty(criteria.getPhaseAdditionalQualifierCode())
                && StringUtils.isEmpty(criteria.getStudyStatusCode())
                && StringUtils.isEmpty(criteria.getDocumentWorkflowStatusCode())
                && StringUtils.isEmpty(criteria.getStudyMilestone())
                && StringUtils.isEmpty(criteria.getOtherIdentifier())
                && StringUtils.isEmpty(criteria.getNctNumber())
                && StringUtils.isEmpty(criteria.getDcpIdentifier())
                && StringUtils.isEmpty(criteria.getCtepIdentifier())
                && StringUtils.isEmpty(criteria.getParticipatingSiteId())
                && !criteria.isSearchOnHold()
                && !criteria.isStudyLockedBy()
                && StringUtils.isEmpty(criteria.getSubmissionType())
                && StringUtils.isEmpty(criteria.getTrialCategory())
                && criteria.getSumm4FundingSourceId() == null
                && StringUtils.isEmpty(criteria.getSumm4FundingSourceTypeCode())
                && criteria.getInterventionType() == null
                && criteria.getDiseaseConditionId() == null
                && (criteria.isMyTrialsOnly() != null && !criteria.isMyTrialsOnly()
                        || criteria.isMyTrialsOnly() == null));
    }


    /**
     * @param orgIdentifier the org identifier
     * @return list studyProtocols
     * @throws PAException on error
     */
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(Long orgIdentifier) throws PAException {
        if (orgIdentifier == null) {
            throw new PAException("Organization Identifier is null.");
        }
        StudyProtocol sp = new StudyProtocol();
        sp.setStatusCode(ActStatusCode.ACTIVE);

        Organization org = new Organization();
        org.setIdentifier(String.valueOf(orgIdentifier));

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(org);

        StudySite ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setResearchOrganization(ro);

        sp.getStudySites().add(ss);

        StudyProtocolBeanSearchCriteria crit = new StudyProtocolBeanSearchCriteria(sp);

        PageSortParams<StudyProtocol> params = new PageSortParams<StudyProtocol>(PAConstants.MAX_SEARCH_RESULTS, 0,
                StudyProtocolSortCriterion.STUDY_PROTOCOL_ID, false);
        return search(crit, params);
    }

    /**
     * @return the registry user service
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * @param registryUserService the registry user service to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }
}
