package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.enums.AssigningAuthorityCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
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
 * @since 05/22/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
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
            hql.append(" select sp , sos , dws , di from StudyProtocol as sp  "
                    +  "left outer join sp.studyOverallStatuses as sos  "
                    +  "left outer join sp.documentWorkflowStatuses as dws  "
                    +  "left outer join sp.documentIdentifications as di  ")
            .append(generateWhereClause(studyProtocolQueryCriteria));
        } catch (Exception e) {
            LOG.error("General error in while converting to DTO3", e);
            throw new PAException("General error in while converting to DTO4", e);
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
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.InefficientStringBuffering", "PMD.ConsecutiveLiteralAppends" })
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
                where.append("and sp.phaseCode  = '" 
                        + StudyStatusCode.getByCode(studyProtocolQueryCriteria.getPhaseCode())
                        + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria
                    .getNciIdentifier())) {
                where.append(" and di.identifier = '").append(studyProtocolQueryCriteria.getNciIdentifier() + "'");
                where.append(" and di.assigningAuthorityCode = '").append(AssigningAuthorityCode.NCI + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getStudyStatusCode())) {
                where.append("and sos.studyStatusCode  = '" 
                        + StudyStatusCode.getByCode(studyProtocolQueryCriteria.getStudyStatusCode())
                        + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(studyProtocolQueryCriteria.getDocumentWorkflowStatusCode())) {
                where.append("and dws.documentWorkflowStatusCode  = '" 
                        + DocumentWorkflowStatusCode.getByCode(
                                studyProtocolQueryCriteria.getDocumentWorkflowStatusCode())
                        + "'");
            }
            
        } catch (Exception e) {
            LOG.error("General error in while create where cluase", e);
            throw new PAException("General error in while create where cluase", e);
        } finally {
            LOG.debug("Leaving generateWhereClause ");
        }
        return where.toString();
    }


}
