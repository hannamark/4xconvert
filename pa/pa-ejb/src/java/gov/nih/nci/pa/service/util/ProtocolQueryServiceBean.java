package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode; 
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceBean;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Naveen Amiruddin
 * @since 08/13/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
        "PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals",
        "PMD.NPathComplexity" })
public class ProtocolQueryServiceBean implements ProtocolQueryServiceLocal {

    private static final Logger LOG = Logger
            .getLogger(StudyProtocolServiceBean.class);
    private static final int PI_PERSON_3 = 3;
    private static final int LEAD_ORG_4 = 4;
    private static final int ST_PARTY_5 = 5;

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
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria spsc) throws PAException {
        LOG.debug("Entering getStudyProtocolByCriteria ");
        // StudyProtocolServiceImpl pImpl = new StudyProtocolServiceImpl();
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        List<Object> queryList = getStudyProtocolQueryResults(spsc);
        pdtos = convertToStudyProtocolDTO(queryList);
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
        LOG.debug("Entering getTrialSummaryByStudyProtocolId ");
        if (studyProtocolId == null) {
            LOG.error(" studyProtocolId cannot be null ");
            throw new PAException(" studyProtocolId cannot be null ");
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
        Organization organization = null;
        Person person = null;
        StudyParticipation studyParticipation = null;
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
                studyProtocol = (StudyProtocol) searchResult[0];
                // get documentWorkflowStatus
                documentWorkflowStatus = (DocumentWorkflowStatus) searchResult[1];
                // get studyOverallStatus
                studyOverallStatus = (StudyOverallStatus) searchResult[2];
                // get the person
                person = (Person) searchResult[PI_PERSON_3];
                // get the organization
                organization = (Organization) searchResult[LEAD_ORG_4];
                // get the StudyParticipation
                studyParticipation = (StudyParticipation) searchResult[ST_PARTY_5];

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
//                        throw new PAException(" Unknown StudyProtocol type found for protocol id = " 
//                                + studyProtocol.getIdentifier() + " title " + studyProtocol.getOfficialTitle());
                        studyProtocolDto.setStudyProtocolType(studyProtocol.getClass().getName());
                    }

                    studyProtocolDto.setOfficialTitle(studyProtocol
                            .getOfficialTitle());
                    studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
                    studyProtocolDto.setNciIdentifier(studyProtocol
                            .getIdentifier());
                    studyProtocolDto
                            .setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
                    studyProtocolDto.setPhaseCode(studyProtocol.getPhaseCode());
                    studyProtocolDto.setUserLastCreated(studyProtocol.getUserLastCreated());
                    // @todo : hardcoded for interventional, its has to be
                    // derived
                }
                if (studyOverallStatus != null) {
                    studyProtocolDto.setStudyStatusCode(studyOverallStatus
                            .getStatusCode());
                    studyProtocolDto.setStudyStatusDate(studyOverallStatus
                            .getStatusDate());
                }
                if (organization != null) {
                    studyProtocolDto.setLeadOrganizationName(organization
                            .getName());
                    studyProtocolDto
                            .setLeadOrganizationId(organization.getId());
                }
                if (person != null) {
                    studyProtocolDto.setPiFullName(person.getFullName());
                    studyProtocolDto.setPiId(person.getId());
                }
                if (studyParticipation != null) {
                    studyProtocolDto
                            .setLocalStudyProtocolIdentifier(studyParticipation
                                    .getLocalStudyProtocolIdentifier());
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

    /**
     * 
     * @param studyProtocolQueryCriteria
     *            studyProtocolQueryCriteria
     * @return List queryList
     * @throws PAException
     *             paException
     */
    private List<Object> getStudyProtocolQueryResults(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria)
            throws PAException {
        LOG.debug("Entering getStudyProtocolByCriteria ");
        List<Object> queryList = new ArrayList<Object>();
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            // step 1: form the hql
            String hql = generateStudyProtocolQuery(studyProtocolQueryCriteria);
            // String hql = "select sp from StudyProtocol sp";
            LOG.info(" query protocol = " + hql);
            // step 2: construct query object
            query = session.createQuery(hql);
            // step 3: query the result
            queryList = query.list();

        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getStudyProtocolByCriteria ",
                    hbe);
            throw new PAException(
                    " Hibernate exception in getStudyProtocolByCriteria ", hbe);
        }
        LOG.debug("Leaving getStudyProtocolByCriteria ");
        return queryList;
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
                    .append(" select sp , dws , sos  , per , org , sps , sc from StudyProtocol as sp  "
                            + "left outer join sp.documentWorkflowStatuses as dws  "
                            + "left outer join sp.studyOverallStatuses as sos  "
                            + "left outer join sp.studyContacts as sc "
                            + "left outer join sc.healthCareProvider as hcp "
                            + "left outer join hcp.person as per "
                            + "left outer join sp.studyParticipations as sps  "
                            + "left outer join sps.healthCareFacility as hcf "
                            + "left outer join hcf.organization as org ");

            hql.append(generateWhereClause(studyProtocolQueryCriteria));
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
    @SuppressWarnings({ "PMD.InefficientStringBuffering",
            "PMD.ConsecutiveLiteralAppends", "PMD.ExcessiveMethodLength",
            "NPathComplexity" })
    private String generateWhereClause(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria)
            throws PAException {
        LOG.debug("Entering generateWhereClause ");
        StringBuffer where = new StringBuffer();
        try {
            where.append("where 1 = 1 ");
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getStudyProtocolId())) {
                where.append(" and sp.id = ").append(
                        studyProtocolQueryCriteria.getStudyProtocolId());

            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getOfficialTitle())) {
                where.append(" and upper(sp.officialTitle)  like '%"
                        + studyProtocolQueryCriteria.getOfficialTitle()
                                .toUpperCase().trim().replaceAll("'", "''")
                        + "%'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getPhaseCode())) {
                where.append(" and sp.phaseCode  = '"
                        + PhaseCode.getByCode(studyProtocolQueryCriteria
                                .getPhaseCode()) + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getNciIdentifier())) {
                where.append(" and upper(sp.identifier)  like '%"
                        + studyProtocolQueryCriteria.getNciIdentifier()
                                .toUpperCase().trim().replaceAll("'", "''")
                        + "%'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getLeadOrganizationTrialIdentifier())) {
                if (studyProtocolQueryCriteria.getIdentifierType() == null)  {
                    where
                            .append(" and upper(sps.localStudyProtocolIdentifier) like '%"
                                    + studyProtocolQueryCriteria
                                            .getLeadOrganizationTrialIdentifier()
                                            .toUpperCase().trim().replaceAll(
                                                    "'", "''") + "%'");
                    where.append(" and sps.functionalCode = '").append(
                            StudyParticipationFunctionalCode.LEAD_ORAGANIZATION
                                    + "'");
                } else if (studyProtocolQueryCriteria.getIdentifierType()
                        .equalsIgnoreCase("Lead Organization")) {
                    where.append(" and upper(sps.localStudyProtocolIdentifier) like '%"
                                    + studyProtocolQueryCriteria
                                            .getLeadOrganizationTrialIdentifier()
                                            .toUpperCase().trim().replaceAll(
                                                    "'", "''") + "%'");
                    where.append(" and sps.functionalCode = '").append(
                            StudyParticipationFunctionalCode.LEAD_ORAGANIZATION
                                    + "'");
                } else if (studyProtocolQueryCriteria.getIdentifierType()
                        .equalsIgnoreCase("NCI")) {
                    where.append(" and upper(sp.identifier) like '%"
                            + studyProtocolQueryCriteria.getLeadOrganizationTrialIdentifier()
                                    .toUpperCase().trim().replaceAll("'", "''")
                            + "%'");
                } else {
                    where.append(" and upper(sps.localStudyProtocolIdentifier) like '%"
                                    + studyProtocolQueryCriteria
                                            .getLeadOrganizationTrialIdentifier()
                                            .toUpperCase().trim().replaceAll(
                                                    "'", "''") + "%'");
                    where.append(" and sps.functionalCode = '").append(
                            StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER
                                    + "'");
                }
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getStudyStatusCode())) {
                where.append(" and sos.statusCode  = '"
                        + StudyStatusCode.getByCode(studyProtocolQueryCriteria
                                .getStudyStatusCode()) + "'");
            } else {
                // add the subquery to pick the latest record
                where
                        .append(" and ( sos.id in (select max(id) from StudyOverallStatus as sos1 "
                                + "                where sos.studyProtocol = sos1.studyProtocol )"
                                + " or sos.id is null ) ");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getPrimaryPurposeCode())) {
                where.append(" and sp.primaryPurposeCode  = '"
                        + PrimaryPurposeCode
                                .getByCode(studyProtocolQueryCriteria
                                        .getPrimaryPurposeCode()) + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getDocumentWorkflowStatusCode())) {
                where
                        .append(" and dws.statusCode  = '"
                                + DocumentWorkflowStatusCode
                                        .getByCode(studyProtocolQueryCriteria
                                                .getDocumentWorkflowStatusCode())
                                + "'");
            } else {
                // add the subquery to pick the latest record
                where
                        .append(" and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                                + "                where dws.studyProtocol = dws1.studyProtocol )"
                                + " or dws.id is null ) ");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getLeadOrganizationId())) {
                if ((studyProtocolQueryCriteria.getOrganizationType() == null)
                        || (studyProtocolQueryCriteria.getOrganizationType()
                                .equalsIgnoreCase("Lead"))) {
                    // if org is added, add the where clause
                    where.append(" and org.id = "
                            + studyProtocolQueryCriteria
                                    .getLeadOrganizationId());
                    where
                            .append(" and sps.functionalCode ='"
                                    + StudyParticipationFunctionalCode.LEAD_ORAGANIZATION
                                    + "'");
                } else if (studyProtocolQueryCriteria.getOrganizationType()
                        .equalsIgnoreCase("Participating")) {
                    where.append(" and org.id = "
                            + studyProtocolQueryCriteria
                                    .getLeadOrganizationId());
                    where.append(" and sps.functionalCode = '").append(
                            StudyParticipationFunctionalCode.TREATING_SITE
                                    + "'");
                }
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getPrincipalInvestigatorId())) {
                where.append(" and per.id = "
                        + studyProtocolQueryCriteria
                                .getPrincipalInvestigatorId());
            }
                where.append(" and dws.statusCode  <>  '"
                    + DocumentWorkflowStatusCode.REJECTED + "'");
           if  ((PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getClientName())) 
                       && (studyProtocolQueryCriteria.getClientName().equalsIgnoreCase("MyTrials"))) {
               where.append(" and sp.userLastCreated = '").append(
                       studyProtocolQueryCriteria.getUserLastCreated() + "'");
           }
        } catch (Exception e) {
            LOG.error("General error in while create where cluase", e);
            throw new PAException("General error in while create where cluase",
                    e);
        } finally {
            LOG.debug("Leaving generateWhereClause ");
        }
        return where.toString();
    }
}
