package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Protocol DAO for accessing DAO.
 *
 * @author Naveen Amiruddin
 * @since 05/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
//@SuppressWarnings({ "PMD.ExcessiveMethodLength", "NPathComplexity" , "PMD.CyclomaticComplexity" })
@SuppressWarnings("PMD") 
public class StudyProtocolDAO {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolDAO.class);    


    /**
     *
     * @param studyProtocolQueryCriteria
     *            studyProtocolQueryCriteria
     * @return List queryList
     * @throws PAException paException
     */
    public List<Object> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria) throws PAException {
        LOG.debug("Entering getStudyProtocolByCriteria ");
        List<Object> queryList = new ArrayList<Object>();
        Session session = null;
        try {
        session = HibernateUtil.getCurrentSession();
        Query query = null;
        // step 1: form the hql
        String hql = generateStudyProtocolQuery(studyProtocolQueryCriteria);
        //String hql = "select sp from StudyProtocol sp";
        LOG.info(" query protocol = " + hql);
        // step 2: construct query object
        query = session.createQuery(hql);
        // step 3: query the result
        queryList = query.list();
       
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getStudyProtocolByCriteria ", hbe);
            throw new PAException(" Hibernate exception in getStudyProtocolByCriteria ", hbe);
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
     * @throws PAException paException
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private String generateStudyProtocolQuery(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria) throws PAException {
        LOG.debug("Entering generateStudyProtocolQuery ");
        StringBuffer hql = new StringBuffer();
        try {
            /*
            hql.append(" select sp , sos , dws , di , org , per from StudyProtocol as sp  "
                    + "left outer join sp.studyOverallStatuses as sos  "
                    + "left outer join sp.documentWorkflowStatuses as dws  "
                    + "left outer join sp.documentIdentifications as di  "
                    + "left outer join sp.studySites as st "  
                    + "left outer join st.organization as org "
                    + "left outer join sp.studyContacts as sc "
                    + "left outer join sc.studyContactRoles as scr " 
                    + "left outer join sc.healthCareProvider as hcp " 
                    + "left outer join hcp.person as per ");
            hql.append(generateWhereClause(studyProtocolQueryCriteria));
            */
            hql.append(" select sp , dws , sos , sps , sc , per from StudyProtocol as sp  "
                    + "left outer join sp.documentWorkflowStatuses as dws  "
                    + "left outer join sp.studyOverallStatuses as sos  "
                    + "left outer join sp.studyContacts as sc "
                    + "left outer join sc.studyContactRoles as scr " 
                    + "left outer join sc.healthCareProvider as hcp " 
                    + "left outer join hcp.person as per "
                    + "left outer join sp.studyParticipations as sps  "
                    + "left outer join sps.healthCareFacility as hcf " 
                    + "left outer join hcf.organization as org ");

            hql.append(generateWhereClause(studyProtocolQueryCriteria));
        } catch (Exception e) {
            LOG.error("General error in while executing Study Query protocol", e);
            throw new PAException("General error in while executing Study Query protocol", e);
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
     * @throws PAException paException
     */
    @SuppressWarnings({"PMD.InefficientStringBuffering", 
        "PMD.ConsecutiveLiteralAppends"  , "PMD.ExcessiveMethodLength" , "NPathComplexity" })
    private String generateWhereClause(
            StudyProtocolQueryCriteria studyProtocolQueryCriteria) throws PAException {
        LOG.debug("Entering generateWhereClause ");
        StringBuffer where = new StringBuffer();
        try {
            where.append("where 1 = 1 ");
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getStudyProtocolId())) {
                where.append(" and sp.id = ").append(studyProtocolQueryCriteria.getStudyProtocolId());
                      
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getOfficialTitle())) {
                where.append(" and upper(sp.officialTitle)  like '%"
                        + studyProtocolQueryCriteria.getOfficialTitle().toUpperCase()
                                .trim().replaceAll("'", "''") + "%'");
            }
            
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getPhaseCode())) {
                where.append(" and sp.phaseCode  = '" 
                        + PhaseCode.getByCode(studyProtocolQueryCriteria.getPhaseCode())
                        + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getNciIdentifier())) {
                where.append(" and sp.identifier = '").append(studyProtocolQueryCriteria.getNciIdentifier() + "'");
            }

            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getLeadOrganizationTrialIdentifier())) {
                where.append(" and upper(sps.localStudyProtocolIdentifier) like '%"
                    + studyProtocolQueryCriteria.getLeadOrganizationTrialIdentifier().toUpperCase()
                    .trim().replaceAll("'", "''") + "%'");
                where.append(" and sps.functionalCode = '").
                    append(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION + "'");
            }
            
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getStudyStatusCode())) {
                where.append(" and sos.statusCode  = '" 
                        + StudyStatusCode.getByCode(studyProtocolQueryCriteria.getStudyStatusCode())
                        + "'");
            } else {
                // add the subquery to pick the latest record
                where.append(" and ( sos.id in (select max(id) from StudyOverallStatus as sos1 " 
               +  "                where sos.studyProtocol = sos1.studyProtocol )"
               + " or sos.id is null ) ");
            }
            
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getPrimaryPurposeCode())) {
                where.append(" and sp.primaryPurposeCode  = '" 
                        + PrimaryPurposeCode.getByCode(studyProtocolQueryCriteria.getPrimaryPurposeCode())
                        + "'");
            }
            
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getDocumentWorkflowStatusCode())) {
                where.append(" and dws.statusCode  = '" 
                        + DocumentWorkflowStatusCode.getByCode(
                                studyProtocolQueryCriteria.getDocumentWorkflowStatusCode())
                        + "'");
            } else {
                // add the subquery to pick the latest record
                where.append(" and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 " 
                +  "                where dws.studyProtocol = dws1.studyProtocol )" 
                + " or dws.id is null ) ");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getLeadOrganizationId())) {
                // if org is added, add the where clause
                where.append(" and org.id = " + studyProtocolQueryCriteria.getLeadOrganizationId());
            }   
            where.append(" and sps.functionalCode ='" + StudyParticipationFunctionalCode.LEAD_ORAGANIZATION + "'");

            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getPrincipalInvestigatorId())) {
                where.append(" and per.id = " + studyProtocolQueryCriteria.getPrincipalInvestigatorId());
            }
            where.append(" and scr.studyContactRoleCode ='" + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "'");

            
        } catch (Exception e) {
            LOG.error("General error in while create where cluase", e);
            throw new PAException("General error in while create where cluase", e);
        } finally {
            LOG.debug("Leaving generateWhereClause ");
        }
        return where.toString();
    }


}
