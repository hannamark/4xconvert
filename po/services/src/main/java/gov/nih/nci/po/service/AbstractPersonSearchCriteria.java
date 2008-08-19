package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * Used to share Person Search Criteria functionality.
 */
public abstract class AbstractPersonSearchCriteria extends AbstractSearchCriteria {

    private static final Logger LOG = Logger.getLogger(AbstractPersonSearchCriteria.class);
    static final String PERSON_ALIAS = "p";

    static final String PERSON_FIRST_NAME_PROPERTY = "firstName";
    static final String PERSON_LAST_NAME_PROPERTY = "lastName";
    static final String PERSON_MIDDLE_NAME_PROPERTY = "middleName";
    static final String PERSON_SUFFIX_PROPERTY = "suffix";
    static final String PERSON_PREFIX_PROPERTY = "prefix";
    static final String PERSON_ID_PROPERTY = "id";
    static final String PERSON_EMAIL_PROPERTY_NAME = "email";
    static final String PERSON_PHONE_PROPERTY_NAME = "phone";
    static final String PERSON_FAX_PROPERTY_NAME = "fax";
    static final String PERSON_TTY_PROPERTY_NAME = "tty";
    static final String PERSON_URL_PROPERTY_NAME = "url";
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
            queryString.append(PERSON_ALIAS);
            queryString.append(" ");
        }
        queryString.append(FROM);
        queryString.append(tableAlias(Person.class, PERSON_ALIAS));

        queryString.append(getQueryWhereClause(namedParameters, PERSON_ALIAS));

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
     * @param personAlias Person table alias
     * @return HQL-based where clause
     */
    protected abstract StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String personAlias);
}
