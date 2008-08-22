package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Criteria to find Organizations to curate.
 */
public class CurateOrganizationSearchCriteria extends AbstractOrganizationSearchCriteria implements
        SearchCriteria<Organization> {

    private static final String ORGANIZAITON_CHANGEREQUESTS_PROPERTY = "changeRequests";

    /**
     * {@inheritDoc}
     */
    @Override
    protected StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String orgAlias) {
        List<String> whereClause = new ArrayList<String>();
        String orgAliasDot = orgAlias + DOT;
        whereClause.add(orgAliasDot + ORGANIZATION_STATUS_PROPERTY + " = '" + EntityStatus.NEW.name() + "'");
        whereClause
                .add("((select count(*) from " + orgAliasDot + ORGANIZAITON_CHANGEREQUESTS_PROPERTY + " as cr) > 0)");
        return buildWhereClause(whereClause, WhereClauseOperator.DISJUNCTION);
    }

}
