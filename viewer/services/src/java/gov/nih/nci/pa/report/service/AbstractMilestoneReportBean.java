package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.report.dto.criteria.AbstractBaseCriteriaDto;
import gov.nih.nci.pa.report.util.ViewerHibernateUtil;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;

/**
 * @author Hugh Reinhart
 * @since 05/04/2009
 *
 * @param <CRITERIA> criteria dto
 * @param <RESULT> result dto
 */
public class AbstractMilestoneReportBean<CRITERIA extends AbstractBaseCriteriaDto, RESULT>
        extends AbstractBaseReportBean<CRITERIA, RESULT> {

    /** study protocol identifier. */
    protected static final int SP_IDENTIFIER_IDX = 0;
    /** assigned identifier. */
    protected static final int ASSIGNED_IDENTIFIER_IDX = 1;
    /** official title. */
    protected static final int OFFICIAL_TITLE_IDX = 2;
    /** organization. */
    protected static final int ORGANIZATION_IDX = 3;
    /** milestone. */
    protected static final int MILESTONE_CODE_IDX = 4;
    /** date. */
    protected static final int MILESTONE_DATE_IDX = 5;

    /**
     * @param criteria criteria
     * @param currentOnly only return the most recent milestone
     * @param summaryRelatedOnly only return milestones related to trial summary document
     * @return query result
     * @throws PAException exception
     */
    protected List<Object[]> getMilestones(CRITERIA criteria, boolean currentOnly, boolean summaryRelatedOnly)
            throws PAException {

        List<Object[]> resultList;
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer(
                  "SELECT sp.identifier, sp.assigned_identifier, sp.official_title, us.organization "
                + ", sm.milestone_code, sm.milestone_date "
                + "FROM study_milestone AS sm "
                + "  INNER JOIN study_protocol AS sp ON (sm.study_protocol_identifier = sp.identifier) "
                + "  LEFT OUTER JOIN csm_user AS us ON (sp.user_last_created = us.login_name) "
                + "WHERE 1=1 ");
            if (currentOnly) {
                sql.append(
                        "AND (sm.identifier, sm.study_protocol_identifier) in "
                      + "    (select max(identifier), study_protocol_identifier "
                      + "     from study_milestone "
                      + "     group by study_protocol_identifier) ");
            }
            if (summaryRelatedOnly) {
                sql.append(" AND ((sm.milestone_code = '" + MilestoneCode.TRIAL_SUMMARY_SENT.getName() + "' "
                                  + getDateRangeClauses(criteria, "sm.milestone_date") + ") "
                                  + "OR (sm.milestone_code = '" + MilestoneCode.TRIAL_SUMMARY_FEEDBACK.getName()
                                  + "')) ");
            } else {
                sql.append(getDateRangeClauses(criteria, "sm.milestone_date"));
            }
            sql.append("ORDER BY sp.assigned_identifier, sm.identifier ");
            logger.info("query = " + sql);
            query = session.createSQLQuery(sql.toString());
            setDateRangeParameters(criteria, query);
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            resultList = queryList;
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        return resultList;
    }
}
