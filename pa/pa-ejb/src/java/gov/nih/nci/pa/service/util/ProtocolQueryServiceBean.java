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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Naveen Amiruddin
 * @since 08/13/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
        "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength"  })
public class ProtocolQueryServiceBean implements ProtocolQueryServiceLocal {

    private static final Logger LOG = Logger.getLogger(ProtocolQueryServiceBean.class);
    private static final int STUDY_PROTOCOL_INDEX = 0;
    private static final int WORKFLOW_STATUS_INDEX = 1;
    private static final int MILESTONE_INDEX = 2;
    private static final int OVERALL_STATUS_INDEX = 3;
    private static final int PI_INDEX = 4;
    private static final int LEAD_ORG_INDEX = 5;
    private static final int STUDY_SITE_INDEX = 6;
    private static final int STUDY_INBOX_INDEX = 8;
    private static final int STUDY_CHECKOUT_INDEX = 9;

    /**
     * gets a list StudyProtocl by criteria.
     *
     * @param spsc
     *            spsc
     * @return pdtos
     * @throws PAException
     *             PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(StudyProtocolQueryCriteria spsc) throws PAException {
        LOG.debug("Entering getStudyProtocolByCriteria ");
        if (isCriteriaEmpty(spsc)) {
            throw new PAException("At least one criteria is required.");
        }
        // StudyProtocolServiceImpl pImpl = new StudyProtocolServiceImpl();
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        List<Object> queryList = getStudyProtocolQueryResults(spsc);
        pdtos = convertToStudyProtocolDTO(queryList);
        if (pdtos != null && !pdtos.isEmpty()) {
            pdtos = appendOnHold(pdtos);
        }
        LOG.debug("Leaving getStudyProtocolByCriteria ");
        return pdtos;
    }

    /**
     *
     * @param studyProtocolId
     *            studyProtocolId
     * @return StudyProtocolQueryDTO
     * @throws PAException
     *             PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {
        LOG.debug("Entering getTrialSummaryByStudyProtocolId ");
        if (studyProtocolId == null) {
            LOG.error(" studyProtocol Identifier cannot be null ");
            throw new PAException(" studyProtocol Identifier cannot be null ");
        }
        StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();
        spqc.setStudyProtocolId(studyProtocolId);
        List<Object> queryList = getStudyProtocolQueryResults(spqc);
        if (queryList == null) {
            // this will never happen is real scenario, as a practice throw
            // exception
            LOG.error(" Study protcol was not found for id " + studyProtocolId);
            throw new PAException(" Study protcol was not found for id "
                    + studyProtocolId);
        }
        List<StudyProtocolQueryDTO> trialSummarys = convertToStudyProtocolDTO(queryList);

        if (trialSummarys == null || trialSummarys.size() <= 0) {
            // this will never happen is real scenario, as a practice throw
            // exception
            LOG.error(" Could not be converted to DTO for id "
                    + studyProtocolId);
            throw new PAException(" Could not be converted to DTO for id "
                    + studyProtocolId);
        }
        StudyProtocolQueryDTO trialSummary = trialSummarys.get(0);
        LOG.debug("Leaving getTrialSummaryByStudyProtocolId ");
        return trialSummary;

    }

    /**
     *
     * @param protocolQueryResult
     * @return List ProtocolDTO
     * @throws PAException
     *             paException
     */

    private List<StudyProtocolQueryDTO> convertToStudyProtocolDTO(
            List<Object> protocolQueryResult) throws PAException {
        LOG.debug("Entering convertToStudyProtocolDTO ");
        List<StudyProtocolQueryDTO> studyProtocolDtos = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO studyProtocolDto = null;
        StudyProtocol studyProtocol = null;
        StudyOverallStatus studyOverallStatus = null;
        DocumentWorkflowStatus documentWorkflowStatus = null;
        StudyMilestone studyMilestone;
        Organization organization = null;
        Person person = null;
        StudySite studySite = null;
        StudyInbox studyInbox = null;
        StudyCheckout studyCheckout = null;
        // array of objects for each row
        Object[] searchResult = null;
        try {
            for (int i = 0; i < protocolQueryResult.size(); i++) {
                searchResult = (Object[]) protocolQueryResult.get(i);
                if (searchResult == null) {
                    break;
                }
                studyProtocolDto = new StudyProtocolQueryDTO();
                // get study protocol
                studyProtocol = (StudyProtocol) searchResult[STUDY_PROTOCOL_INDEX];
                // get documentWorkflowStatus
                documentWorkflowStatus = (DocumentWorkflowStatus) searchResult[WORKFLOW_STATUS_INDEX];
                // get studyMilestone
                studyMilestone = (StudyMilestone) searchResult[MILESTONE_INDEX];
                // get studyOverallStatus
                studyOverallStatus = (StudyOverallStatus) searchResult[OVERALL_STATUS_INDEX];
                // get the person
                person = (Person) searchResult[PI_INDEX];
                // get the organization
                organization = (Organization) searchResult[LEAD_ORG_INDEX];
                // get the StudySite
                studySite = (StudySite) searchResult[STUDY_SITE_INDEX];
                // get the StudyInbox
                studyInbox = (StudyInbox) searchResult[STUDY_INBOX_INDEX];
                studyCheckout = (StudyCheckout) searchResult[STUDY_CHECKOUT_INDEX];

                // transfer protocol to studyProtocolDto
                if (documentWorkflowStatus != null) {
                    studyProtocolDto
                            .setDocumentWorkflowStatusCode(documentWorkflowStatus
                                    .getStatusCode());
                    studyProtocolDto
                            .setDocumentWorkflowStatusDate(documentWorkflowStatus
                                    .getStatusDateRangeLow());
                }
                if (studyProtocol != null) {
                    if (studyProtocol instanceof ObservationalStudyProtocol) {
                        studyProtocolDto.setStudyProtocolType("ObservationalStudyProtocol");
                    } else if (studyProtocol instanceof InterventionalStudyProtocol) {
                        studyProtocolDto.setStudyProtocolType("InterventionalStudyProtocol");
                    } else {
                        studyProtocolDto.setStudyProtocolType("InterventionalStudyProtocol");
                    }

                    studyProtocolDto.setOfficialTitle(studyProtocol.getOfficialTitle());
                    studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
                    studyProtocolDto.setNciIdentifier(PADomainUtils.getAssignedIdentifierExtension(studyProtocol));
                    studyProtocolDto.setOtherIdentifiers(PADomainUtils.getOtherIdentifierExtensions(studyProtocol));
                    studyProtocolDto.setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
                    studyProtocolDto.setPhaseCode(studyProtocol.getPhaseCode());
                    studyProtocolDto.setUserLastCreated(studyProtocol.getUserLastCreated());
                    studyProtocolDto.setDateLastCreated(studyProtocol.getDateLastCreated());
                    // return amendment number and date only for amended trials
                    if (studyProtocol.getSubmissionNumber() != null
                            &&  studyProtocol.getSubmissionNumber().intValue() > 1) {
                        studyProtocolDto.setAmendmentNumber(studyProtocol.getAmendmentNumber());
                        studyProtocolDto.setAmendmentDate(studyProtocol.getAmendmentDate());
                        studyProtocolDto.setSubmissionTypeCode(SubmissionTypeCode.A);
                    } else {
                        studyProtocolDto.setSubmissionTypeCode(SubmissionTypeCode.O);
                    }
                   if (studyProtocol.getProprietaryTrialIndicator() != null) {
                       studyProtocolDto.setIsProprietaryTrial(
                               studyProtocol.getProprietaryTrialIndicator().toString());
                   } else {
                       studyProtocolDto.setIsProprietaryTrial("false");
                   }
                   studyProtocolDto.setRecordVerificationDate(studyProtocol.getRecordVerificationDate());
                   if (!(studyProtocol.getCtgovXmlRequiredIndicator() != null
                       && !studyProtocol.getCtgovXmlRequiredIndicator().booleanValue())) {
                       studyProtocolDto.setCtgovXmlRequiredIndicator(Boolean.TRUE);
                   } else {
                       studyProtocolDto.setCtgovXmlRequiredIndicator(Boolean.FALSE);
                   }
                }
                if (studyMilestone != null) {
                    studyProtocolDto.setStudyMilsetone(studyMilestone.getMilestoneCode());
                    studyProtocolDto.setStudyMilestoneDate(studyMilestone.getMilestoneDate());
                }
                if (studyOverallStatus != null) {
                    studyProtocolDto.setStudyStatusCode(studyOverallStatus.getStatusCode());
                    studyProtocolDto.setStudyStatusDate(studyOverallStatus.getStatusDate());
                }
                if (organization != null) {
                    studyProtocolDto.setLeadOrganizationName(organization
                            .getName());
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
                  if (studyInbox.getId() != null) {
                    studyProtocolDto.setStudyInboxId(studyInbox.getId());
                  }
                  if (studyInbox.getComments() != null) {
                    studyProtocolDto.setUpdatedComments(studyInbox.getComments());
                  }
                  if (studyInbox.getOpenDate() != null) {
                    studyProtocolDto.setUpdatedDate(studyInbox.getOpenDate());
                  }
                }
                if (studyCheckout != null) {
                    studyProtocolDto.setStudyCheckoutBy(studyCheckout.getUserIdentifier());
                    studyProtocolDto.setStudyCheckoutId(studyCheckout.getId());
                }
                // add to the list
                studyProtocolDtos.add(studyProtocolDto);
            } // for loop
        } catch (Exception e) {
            LOG.error("General error in while converting to DTO", e);
            throw new PAException("General error in while converting to DTO2",
                    e);
        } finally {
            LOG.debug("Leaving convertToStudyProtocolDTO ");
        }
        return studyProtocolDtos;
    }

    @SuppressWarnings("unchecked")
    private List<StudyProtocolQueryDTO> appendOnHold(List<StudyProtocolQueryDTO> spDtos) throws PAException {
        StringBuffer hql = new StringBuffer();
        Session session = null;
        long[] ids = getProtocolIds(spDtos);
        session = HibernateUtil.getCurrentSession();
        hql.append(" select sp , soh from StudyProtocol as sp  "
                + "join sp.studyOnholds as soh  where sp.id in ( ");
        for (int i = 0; i < ids.length; i++) {
            hql.append(ids[i]);
            hql.append((i < ids.length - 1 ? " ," : " "));
        }
        hql.append(" ) and soh.offholdDate is null");
        Query query = null;
        query = session.createQuery(hql.toString());
        Map<Long , List<StudyOnhold>> studyOnHolds = generateOnholdMap(query.list());
        populateOnHoldData(spDtos , studyOnHolds);
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

    private void populateOnHoldData(List<StudyProtocolQueryDTO> spDtos , Map<Long , List<StudyOnhold>> onHold) {
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
     * @param studyProtocolQueryCriteria
     *            studyProtocolQueryCriteria
     * @return List queryList
     * @throws PAException
     *             paException
     */
    @SuppressWarnings("unchecked")
    private List<Object> getStudyProtocolQueryResults(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria)
            throws PAException {
        LOG.debug("Entering getStudyProtocolByCriteria ");
        List<Object> queryList = new ArrayList<Object>();
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        Query query = null;
        // step 1: form the hql
        String hql = generateStudyProtocolQuery(studyProtocolQueryCriteria);
        // String hql = "select sp from StudyProtocol sp";
        LOG.debug(" query protocol = " + hql);
        // step 2: construct query object
        query = session.createQuery(hql);
        // step 3: query the result
        queryList = query.list();
        if (queryList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new PAException("Results exceed more than 500, Please redefine the seacrh criteria");
        }
        LOG.debug("Leaving getStudyProtocolByCriteria ");
        return queryList;
    }

    @SuppressWarnings("unchecked")
    private List<StudyProtocol> getStudyProtocolQueryResults(Long orgIdentifier) throws PAException {
        StringBuffer hql = new StringBuffer();
        hql.append("select sp from StudyProtocol as sp left outer join sp.documentWorkflowStatuses as "
                + "dws left outer join sp.studySites as sps left outer join sps.researchOrganization as "
                + "ro left outer join ro.organization as org where 1=1 and str(")
        .append(orgIdentifier.toString())
        .append(") in (select researchOrganization.organization.identifier from StudySite where functionalCode ='")
        .append(StudySiteFunctionalCode.LEAD_ORGANIZATION)
        .append("' and studyProtocol.id = sp.id) and dws.statusCode  <> '")
        .append(DocumentWorkflowStatusCode.REJECTED)
        .append("' and (dws.id in (select max(id) from DocumentWorkflowStatus as dws1 where sp.id=dws1.studyProtocol) "
                + "or dws.id is null) and sps.functionalCode = '")
        .append(StudySiteFunctionalCode.LEAD_ORGANIZATION)
        .append("' ");

        Session session = HibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql.toString());
        return query.list();
    }

    /**
     * generate HQL query for search protocol.
     *
     * @param studyProtocolQueryCriteria
     *            studyProtocolQueryCriteria
     * @return hql
     * @throws PAException
     *             paException
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private String generateStudyProtocolQuery(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria)
            throws PAException {
        LOG.debug("Entering generateStudyProtocolQuery ");
        StringBuffer hql = new StringBuffer();
        try {
            hql
                 .append(" select sp , dws , sms , sos  , per , org , sps , sc, sinbx, "
                            + "scheckout from StudyProtocol as sp  "
                            + "left outer join sp.documentWorkflowStatuses as dws  "
                            + "left outer join sp.studyMilestones as sms  "
                            + "left outer join sp.studyOverallStatuses as sos  "
                            + "left outer join sp.studyContacts as sc "
                            + "left outer join sc.clinicalResearchStaff as hcp "
                            + "left outer join hcp.person as per "
                            + "left outer join sp.studySites as sps  "
                            + "left outer join sps.researchOrganization as ro "
                            + "left outer join ro.organization as org "
                            + "left outer join sp.studyInbox as sinbx "
                            + "left outer join sp.studyCheckout as scheckout "
                            + "left outer join sp.studyOwners as sowner ");
            
            if (StringUtils.isNotEmpty(studyProtocolQueryCriteria.getNctNumber())) {
                hql.append(" left outer join sp.studySites as sps_nct ");
            }
            
            hql.append(generateWhereClause(studyProtocolQueryCriteria));
            hql.append(" order by dws.statusDateRangeLow asc");
        } catch (Exception e) {
            LOG.error("General error in while executing Study Query protocol",
                    e);
            throw new PAException(
                    "General error in while executing Study Query protocol", e);
        } finally {
            LOG.debug("Leaving generateStudyProtocolQuery ");
        }
        return hql.toString();

    }

    /**
     * generate a where clause for a given protocol search criteria.
     *
     * @param studyProtocolQueryCriteria
     * @return String String
     * @throws PAException
     *             paException
     */
    @SuppressWarnings({"PMD.InefficientStringBuffering",
            "PMD.ConsecutiveLiteralAppends", "PMD.ExcessiveMethodLength",
            "NPathComplexity" })
    private String generateWhereClause(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria)
            throws PAException {
        LOG.debug("Entering generateWhereClause ");
        StringBuffer where = new StringBuffer();
        try {
            where.append("where 1 = 1 ");
            if (studyProtocolQueryCriteria.getStudyProtocolId() != null) {
                where.append(" and sp.id = ").append(studyProtocolQueryCriteria.getStudyProtocolId());
            }
            if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getOfficialTitle())) {
                where.append(" and upper(sp.officialTitle)  like '%"
                        + studyProtocolQueryCriteria.getOfficialTitle().toUpperCase().trim().replaceAll("'", "''")
                        + "%'");
            }
            if (StringUtils.isNotBlank(studyProtocolQueryCriteria.getPhaseCode())) {
                where.append(" and sp.phaseCode  = '"
                        + PhaseCode.getByCode(studyProtocolQueryCriteria.getPhaseCode()) + "'");
            }
            if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getNciIdentifier())) {
                where.append(" and sp.otherIdentifiers.extension like '%"
                        + studyProtocolQueryCriteria.getNciIdentifier().toUpperCase().trim().replaceAll("'", "''")
                        + "%'");
            } else if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getOtherIdentifier())) {
                where.append(" and upper(sp.otherIdentifiers.extension) like '%"
                        + studyProtocolQueryCriteria.getOtherIdentifier()
                                .toUpperCase().trim().replaceAll("'", "''")
                        + "%'");
            }
            if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getStudyStatusCode())) {
                where.append(" and sos.statusCode  = '"
                        + StudyStatusCode.getByCode(studyProtocolQueryCriteria
                                .getStudyStatusCode()) + "'");
                where.append(" and ( sos.id in (select max(id) from StudyOverallStatus as sos1 "
                        + "                where sp.id = sos1.studyProtocol )"
                        + " or sos.id is null ) ");
            } else {
                // add the subquery to pick the latest record
                where.append(" and ( sos.id in (select max(id) from StudyOverallStatus as sos1 "
                                + "                where sp.id = sos1.studyProtocol )"
                                + " or sos.id is null ) ");
            }
            if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getPrimaryPurposeCode())) {
                where.append(" and sp.primaryPurposeCode  = '" + PrimaryPurposeCode.getByCode(studyProtocolQueryCriteria
                                        .getPrimaryPurposeCode()) + "'");
            }
            if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getDocumentWorkflowStatusCode())) {
                where.append(" and dws.statusCode  = '" + DocumentWorkflowStatusCode.
                        getByCode(studyProtocolQueryCriteria.getDocumentWorkflowStatusCode()) + "'");
                where.append(" and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                        + "                where sp.id = dws1.studyProtocol )"
                        + " or dws.id is null ) ");
            } else {
                whereClauseForRegistrySearch(studyProtocolQueryCriteria, where);
           }
           if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getLeadOrganizationTrialIdentifier())) {
                where.append(" and upper(sps.localStudyProtocolIdentifier) like '%"
                                + studyProtocolQueryCriteria.getLeadOrganizationTrialIdentifier()
                                        .toUpperCase().trim().replaceAll("'", "''") + "%'");
           }
           if (StringUtils.isNotEmpty(studyProtocolQueryCriteria.getNctNumber())) {
               where.append(" and upper(sps_nct.localStudyProtocolIdentifier) = '"
                       + studyProtocolQueryCriteria.getNctNumber().toUpperCase().trim().replaceAll("'", "''") 
                       + "' and sps_nct.functionalCode = '" + StudySiteFunctionalCode.IDENTIFIER_ASSIGNER + "'"); 
           }
           if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getLeadOrganizationId())) {
                    where.append(" and org.id = " + studyProtocolQueryCriteria.getLeadOrganizationId());

           }
           if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getPrincipalInvestigatorId())) {
                where.append(" and per.id = " + studyProtocolQueryCriteria.getPrincipalInvestigatorId());
           }
           // required for Registry duplicate trial check
           // Rejected trial should be excluded from duplicate check
           if (studyProtocolQueryCriteria.getExcludeRejectProtocol() != null
                   && studyProtocolQueryCriteria.getExcludeRejectProtocol().booleanValue()) {
               where.append(" and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'");
           }
           if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getStudyMilestone())) {
               where.append(" and sms.milestoneCode  = '" + MilestoneCode.
                       getByCode(studyProtocolQueryCriteria.getStudyMilestone()) + "'");
               where.append(" and ( sms.id in (select max(id) from StudyMilestone as sms1 "
                       + " where sp.id = sms1.studyProtocol )"
                       + " or sms.id is null ) ");
           } else {
               // add the sub-query to pick the latest record
               where.append(" and ( sms.id in (select max(id) from StudyMilestone as sms1 "
                               + " where sp.id = sms1.studyProtocol )"
                               + " or sms.id is null ) ");
           }
           // sub-query for inbox processing to retrieve only the updated records for review
           if (studyProtocolQueryCriteria.getInBoxProcessing() != null
                   && studyProtocolQueryCriteria.getInBoxProcessing().booleanValue()) {
             where.append(" and sinbx.id in (select distinct id from StudyInbox as sinbx1 "
                   + " where sp.id = sinbx1.studyProtocol and"
                     + " sinbx1.closeDate is null)");
           } else {
               where.append(" and (sinbx.id in (select max(id) from StudyInbox as sinbx1 "
                       + " where sp.id = sinbx1.studyProtocol )"
                         + " or sinbx.id is null)");
           }
           studyCheckoutWhereClause(where);
           where.append(" and sps.functionalCode ='"
                   + StudySiteFunctionalCode.LEAD_ORGANIZATION + "'");
           where.append(" and (sc.roleCode ='"
                   + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "' or sc.studyProtocol is null) ");
           where.append(" and sp.statusCode ='" + ActStatusCode.ACTIVE + "'");
           if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getTrialCategory())) {
               if (studyProtocolQueryCriteria.getTrialCategory().equalsIgnoreCase("p")) {
                   where.append(" and sp.proprietaryTrialIndicator is true");
               } else if (studyProtocolQueryCriteria.getTrialCategory().equalsIgnoreCase("n")) {
                   where.append(" and (sp.proprietaryTrialIndicator is false or sp.proprietaryTrialIndicator is null)");
               }
           }
           addSubQueries(studyProtocolQueryCriteria, where);
        } catch (Exception e) {
            LOG.error("General error in while create where cluase", e);
            throw new PAException("General error in while create where cluase", e);
        } finally {
            LOG.debug("Leaving generateWhereClause ");
        }
        return where.toString();
    }

    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private void whereClauseForRegistrySearch(StudyProtocolQueryCriteria criteria, StringBuffer where) {
        // added for Registry Trial Search
        if (criteria.getMyTrialsOnly() != null && PAUtil.isNotEmpty(criteria.getUserLastCreated())) {
            if (criteria.getMyTrialsOnly().booleanValue()) {
                where.append(" and ( sowner.id = ").append(criteria.getUserId());
                where.append(" or ").append(criteria.getUserId());
                where.append(" in (select id from RegistryUser where affiliatedOrgUserType = '");
                where.append(UserOrgType.ADMIN).append("' and str(affiliatedOrganizationId) in ( select ");
                where.append(" researchOrganization.organization.identifier from StudySite where ");
                where.append(" functionalCode ='").append(StudySiteFunctionalCode.LEAD_ORGANIZATION);
                where.append("' and studyProtocol.id = sp.id))) ");
                where.append(" and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'");
                // add the subquery to pick the latest record
                where.append(" and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                        + "                where sp.id = dws1.studyProtocol )" + " or dws.id is null ) ");
            } else if (!criteria.getMyTrialsOnly().booleanValue()) {
                where.append(" and ((sowner.id ='").append(criteria.getUserId());
                where.append("' and dws.statusCode <> '" + DocumentWorkflowStatusCode.REJECTED + "'");
                // add the subquery to pick the latest record
                where.append(" and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                        + "                where sp.id = dws1.studyProtocol )" + " or dws.id is null )) ");
                where.append(" or (sowner.id <> '").append(criteria.getUserId());
                where.append("' and dws.statusCode not in('" + DocumentWorkflowStatusCode.REJECTED + "'," + "'"
                        + DocumentWorkflowStatusCode.SUBMITTED + "'" + "'"
                        + DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED + "')");
                // add the subquery to pick the latest record
                where.append(" and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                        + "                where sp.id = dws1.studyProtocol )" + " or dws.id is null ))) ");
            }
        } else {
            // add the subquery to pick the latest record
            where.append(" and ( dws.id in ");
            where.append(" (select max(id) from DocumentWorkflowStatus as dws1 where sp.id = dws1.studyProtocol) ");
            where.append(" or dws.id is null ) ");
        }
    }

    private void studyCheckoutWhereClause(StringBuffer where) {
        where.append(" and (scheckout.id in (select max(id) from StudyCheckout as scheckout1 "
                + " where scheckout.studyProtocol = scheckout1.studyProtocol )" + " or scheckout.id is null)");
    }

    /**
     * @param studyProtocolQueryCriteria
     * @param where
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private void addSubQueries(StudyProtocolQueryCriteria studyProtocolQueryCriteria, StringBuffer where) {
        // sub-query for searching trials by Participating site
        if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getOrganizationType())
                && studyProtocolQueryCriteria.getOrganizationType().equalsIgnoreCase(PAConstants.PARTICIPATING_SITE)) {
            where.append(" and sp.id in(select sp2.id from StudyProtocol as sp2  "
                    + "left outer join sp2.studySites as sps2  " + "left outer join sps2.healthCareFacility as hcf "
                    + "left outer join hcf.organization as site " + " where site.id = "
                    + studyProtocolQueryCriteria.getParticipatingSiteId() + ")");
        }
        // sub-query for searching only on hold trials
        if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getSearchOnHold())
                && studyProtocolQueryCriteria.getSearchOnHold().equals("true")) {
            where.append(" and sp.id in(select distinct sp3.id from StudyProtocol as sp3 "
                    + " left outer join sp3.studyOnholds as spoh " + " where spoh.onholdDate is not null and "
                    + " spoh.offholdDate is null)");
        }
        // sub-query for searching only Amend trials
        if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getSubmissionType())
                && studyProtocolQueryCriteria.getSubmissionType().equalsIgnoreCase(SubmissionTypeCode.A.getCode())) {
            where.append(" and sp.submissionNumber > 1 and sp.amendmentNumber is not null and "
                    + " sp.amendmentDate is not null)");
        }
        if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getSubmissionType())
                && studyProtocolQueryCriteria.getSubmissionType().equalsIgnoreCase(SubmissionTypeCode.O.getCode())) {
            where.append(" and sp.submissionNumber = 1 and sp.amendmentNumber is null and "
                    + " sp.amendmentDate is null)");
        }
        if (PAUtil.isNotEmpty(studyProtocolQueryCriteria.getStudyLockedBy())
                && studyProtocolQueryCriteria.getStudyLockedBy().equals("true")) {
            where.append(" and sp.id in(select sp3.id from StudyProtocol as sp3 "
                    + " left outer join sp3.studyCheckout as spco " + " where spco.userIdentifier='"
                    + studyProtocolQueryCriteria.getUserLastCreated() + "')");
        }
    }

    private boolean isCriteriaEmpty(StudyProtocolQueryCriteria criteria) {
        return (PAUtil.isEmpty(criteria.getNciIdentifier())
                && PAUtil.isEmpty(criteria.getOfficialTitle())
                && PAUtil.isEmpty(criteria.getLeadOrganizationId())
                && PAUtil.isEmpty(criteria.getLeadOrganizationTrialIdentifier())
                && PAUtil.isEmpty(criteria.getPrincipalInvestigatorId())
                && PAUtil.isEmpty(criteria.getPrimaryPurposeCode())
                && PAUtil.isEmpty(criteria.getPhaseCode())
                && PAUtil.isEmpty(criteria.getStudyStatusCode())
                && PAUtil.isEmpty(criteria.getDocumentWorkflowStatusCode())
                && PAUtil.isEmpty(criteria.getStudyMilestone())
                && PAUtil.isEmpty(criteria.getOtherIdentifier())
                && PAUtil.isEmpty(criteria.getNctNumber())
                && ((PAUtil.isNotEmpty(criteria.getSearchOnHold()) && criteria.getSearchOnHold().equalsIgnoreCase(
                        "false")) || PAUtil.isEmpty(criteria.getSearchOnHold()))
                && ((PAUtil.isNotEmpty(criteria.getStudyLockedBy()) && criteria.getStudyLockedBy().equalsIgnoreCase(
                        "false")) || PAUtil.isEmpty(criteria.getStudyLockedBy()))
                && PAUtil.isEmpty(criteria.getSubmissionType())
                && PAUtil.isEmpty(criteria.getTrialCategory())
                && (criteria.getMyTrialsOnly() != null && !criteria.getMyTrialsOnly()
                        || criteria.getMyTrialsOnly() == null));
    }


    /**
     * @param orgIdentifier the org identifier
     * @return list studyProtocols
     * @throws PAException on error
     */
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(Long orgIdentifier) throws PAException {
        LOG.debug("Entering getStudyProtocolByOrgIdentifier ");
        if (orgIdentifier == null) {
            throw new PAException("Organization Identifier is null.");
        }
        List<StudyProtocol> pdtos = getStudyProtocolQueryResults(orgIdentifier);
        LOG.debug("Leaving getStudyProtocolByOrgIdentifier ");
        return pdtos;
    }
}
