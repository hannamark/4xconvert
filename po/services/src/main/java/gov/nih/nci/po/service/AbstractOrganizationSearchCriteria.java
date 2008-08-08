package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Used to share Organization Search Criteria functionality.
 */
public abstract class AbstractOrganizationSearchCriteria extends AbstractSearchCriteria {

    private static final Logger LOG = Logger.getLogger(AbstractOrganizationSearchCriteria.class);

    static final String ORGANIZATION_ADDRESS_ALIAS = "addr";
    static final String ORGANIZATION_COUNTRY_ALIAS = "ctry";
    static final String ORGANIZATION_ALIAS = "org";

    static final String ORGANIZATION_ID_PROPERTY = "id";
    static final String ORGANIZATION_NAME_PROPERTY = "name";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.AppendCharacterWithChar")
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        Map<String, Object> namedParameters = new HashMap<String, Object>();

        StringBuffer queryString = new StringBuffer();

        queryString.append(SELECT);
        if (isCountOnly) {
            queryString.append("COUNT(*)");
        } else {
            queryString.append(ORGANIZATION_ALIAS);
            queryString.append(" ");
        }
        queryString.append(FROM);
        queryString.append(tableAlias(Organization.class, ORGANIZATION_ALIAS));

        queryString.append(getQueryWhereClause(namedParameters, ORGANIZATION_ALIAS));

        // ADD ORDER BY CLAUSE
        queryString.append(orderByProperty);

        Session session = PoHibernateUtil.getCurrentSession();
        Query query = session.createQuery(queryString.toString());

        setNamedParameters(namedParameters, query);
        LOG.debug("queryString=" + query.getQueryString());
        return query;
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param orgAlias Organization table alias
     * @return HQL-based where clause
     */
    protected abstract StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String orgAlias);
}
