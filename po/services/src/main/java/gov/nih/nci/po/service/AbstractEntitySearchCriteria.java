package gov.nih.nci.po.service;

import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Abstract search criteria class for Entity types.
 */
public abstract class AbstractEntitySearchCriteria extends AbstractHQLSearchCriteria {
    private static final Logger LOG = Logger.getLogger(AbstractEntitySearchCriteria.class);
    /**
     * root object alias type.
     */
    protected static final String ROOT_OBJ_ALIAS = "_root_obj";
    
    /**
     * {@inheritDoc}
     */
    public boolean hasOneCriterionSpecified() {
        return true;
    }

    /**
     * @return the root object type within the from clause of the hql query
     */
    protected abstract Class<? extends PersistentObject> getRootObjectType();

    /**
     * {@inheritDoc}
     */
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        Map<String, Object> namedParameters = new HashMap<String, Object>();

        StringBuffer queryString = new StringBuffer();

        queryString.append(SELECT);
        if (isCountOnly) {
            queryString.append("COUNT(*)");
        } else {
            queryString.append(ROOT_OBJ_ALIAS);
            queryString.append(' ');
        }
        queryString.append(FROM);
        queryString.append(tableAlias(getRootObjectType(), ROOT_OBJ_ALIAS));

        queryString.append(getQueryWhereClause(namedParameters, ROOT_OBJ_ALIAS));

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
     * @param rootTypeAlias the table alias
     * @return HQL-based where clause
     */
    protected abstract StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String rootTypeAlias);
}
