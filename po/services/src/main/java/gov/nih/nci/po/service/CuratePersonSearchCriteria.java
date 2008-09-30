package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Criteria to find Organizations to curate.
 */
public class CuratePersonSearchCriteria extends AbstractPersonSearchCriteria implements
        SearchCriteria<Person> {
    private static final String PERSON_CHANGEREQUESTS_PROPERTY = "changeRequests";

    /**
     * {@inheritDoc}
     */
    @Override
    protected StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String personAlias) {
        List<String> whereClause = new ArrayList<String>();
        String personAliasDot = personAlias + DOT;
        whereClause.add(personAliasDot + PERSON_STATUS_PROPERTY + " = '" + EntityStatus.PENDING.name() + "'");
        whereClause.add("((select count(*) from " + personAliasDot + PERSON_CHANGEREQUESTS_PROPERTY + " as cr) > 0)");
        return buildWhereClause(whereClause, WhereClauseOperator.DISJUNCTION);
    }

}
