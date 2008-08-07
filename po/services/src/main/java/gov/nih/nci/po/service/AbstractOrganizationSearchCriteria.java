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

    private boolean bJoinMailingAddress;
    private boolean bJoinMailingAddressCountry;

    /**
     * Used to control whether the Organization.mailingAddress is available to getQueryWhereClause().
     */
    protected void joinMailingAddress() {
        bJoinMailingAddress = true;
    }

    /**
     * Used to control whether the Organization.mailingAddress.country is available to getQueryWhereClause().
     */
    protected void joinMailingAddressCountry() {
        bJoinMailingAddressCountry = true;
    }
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
        if (this.bJoinMailingAddress) {
            String joinAddressStr = ORGANIZATION_ALIAS + ".primaryContactInfo.mailingAddress as "
                    + ORGANIZATION_ADDRESS_ALIAS;
            queryString.append(JOIN).append(joinAddressStr);
        }
        if (this.bJoinMailingAddressCountry) {
            String joinCountryStr = ORGANIZATION_ALIAS + ".primaryContactInfo.mailingAddress.country as "
                    + ORGANIZATION_COUNTRY_ALIAS;
            queryString.append(JOIN).append(joinCountryStr);
        }

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
