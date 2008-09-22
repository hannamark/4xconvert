package gov.nih.nci.po.service;

import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.Searchable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * @author smatyas
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public abstract class AbstractSearchCriteria {
    private static final Logger LOG = Logger.getLogger(AbstractSearchCriteria.class);

    /**
     * HQL select statement.
     */
    protected static final String SELECT = " SELECT ";

    /**
     * HQL from statement.
     */
    protected static final String FROM = " FROM ";

    /**
     * HQL as keyword.
     */
    protected static final String AS = " as ";
    /**
     * HQL comma keyword.
     */
    protected static final String COMMA = " , ";

    /**
     * HQL JOIN statement.
     */
    protected static final String JOIN = " JOIN ";

    /**
     * HQL WHERE statement.
     */
    protected static final String WHERE = " WHERE ";

    /**
     * HQL AND operator.
     */
    protected static final String AND = " AND ";

    /**
     * HQL OR operator.
     */
    protected static final String OR = " OR ";

    /**
     * HQL property separator.
     */
    protected static final String DOT = ".";

    /**
     * Enum to control where clause expression operator (conjunction or disjunction).
     */
    protected enum WhereClauseOperator {
        /**
         * conjunction.
         */
        AND(true),

        /**
         * disjunction.
         */
        OR(false),

        /**
         * conjunction.
         */
        CONJUNCTION(true),

        /**
         * disjunction.
         */
        DISJUNCTION(false);

        private final boolean and;

        private WhereClauseOperator(boolean b) {
            this.and = b;
        }

        /**
         * @return operator.
         */
        public boolean isConjunction() {
            return and;
        }
    }

    /**
     * PersistentObject property name.
     */
    protected static final String ID = "id";

    /**
     * {@inheritDoc}
     */
    public boolean isValid() {
        if (!hasOneCriterionSpecified()) {
            throw new OneCriterionRequiredException();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasOneCriterionSpecified() {
        return true;
    }

    /**
     * @param value criteria value
     * @return true if valid; false otherwise
     */
    protected boolean isValueSpecified(String value) {
        return StringUtils.isNotBlank(value);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param query Hibernate HQL query to set named parameters on
     */
    protected void setNamedParameters(Map<String, Object> namedParameters, Query query) {
        Set<String> keySet = namedParameters.keySet();
        for (String paramName : keySet) {
            // Structure lifted from AbstractQueryImpl.setProperties(Map)
            Object object = namedParameters.get(paramName);
            Class<?> retType = object.getClass();
            if (Collection.class.isAssignableFrom(retType)) {
                query.setParameterList(paramName, (Collection<?>) object);
            } else if (retType.isArray()) {
                query.setParameterList(paramName, (Object[]) object);
            } else {
                query.setParameter(paramName, object);
            }
        }
    }

    /**
     * @param type Hibernate mapped type
     * @param alias table alias
     * @return HQL table with alias
     */
    protected String tableAlias(Class<? extends PersistentObject> type, String alias) {
        return " " + type.getName() + " " + alias;
    }

    /**
     * @param subselectWhereClause operands to be included
     * @param isConjunction controls clause operator (true is AND; false is OR)
     * @return HQL where clause
     */
    protected StringBuffer buildWhereClause(List<String> subselectWhereClause, WhereClauseOperator isConjunction) {
        CollectionUtils.filter(subselectWhereClause, new Predicate() {
            public boolean evaluate(Object input) {
                return StringUtils.isNotBlank(input.toString());
            }
        });
        if (subselectWhereClause.isEmpty()) {
            return new StringBuffer(StringUtils.EMPTY);
        } else {
            StringBuffer buf = new StringBuffer();
            buf.append(WHERE);
            for (Iterator<String> iterator = subselectWhereClause.iterator(); iterator.hasNext();) {
                String clause = iterator.next();
                buf.append(clause);
                if (iterator.hasNext()) {
                    if (isConjunction.isConjunction()) {
                        buf.append(AND);
                    } else {
                        buf.append(OR);
                    }
                }
            }
            return buf;
        }
    }

    /**
     * @param propertyName hibernate query property name
     * @param parameterName hibernate named parameter name
     * @param criteriaValue hibernate named paramter value
     * @param namedParams map to use to set named parameter values in the Query
     * @return HQL or empty
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    protected String addILike(String propertyName, String parameterName, String criteriaValue,
            Map<String, Object> namedParams) {
        if (isValueSpecified(criteriaValue)) {
            namedParams.put(parameterName, "%" + criteriaValue.toLowerCase() + "%");
            return ilike(propertyName, parameterName);
        }
        return "";
    }

    /**
     * @param propertyName hibernate query property name
     * @param parameterName hibernate named parameter name
     * @param criteriaValue hibernate named paramter value
     * @param namedParams map to use to set named parameter values in the Query
     * @return HQL or empty
     */
    protected String addEqual(String propertyName, String parameterName, Object criteriaValue,
            Map<String, Object> namedParams) {
        if (criteriaValue != null) {
            namedParams.put(parameterName, criteriaValue);
            return propertyName + " = " + ":" + parameterName;
        }
        return "";
    }
    /**
     *
     * @param propertyName hibernate query property name
     * @param parameterName hibernate named parameter name
     * @param criteriaValue hibernate named paramter value
     * @param namedParams map to use to set named parameter values in the Query
     * @return HQL or empty
     */
    protected String addNotEqual(String propertyName, String parameterName, Object criteriaValue,
            Map<String, Object> namedParams) {
        if (criteriaValue != null) {
            namedParams.put(parameterName, criteriaValue);
            return propertyName + " <> " + ":" + parameterName;
        }
        return "";
    }

    /**
     * @param propertyName hibernate query property name
     * @param parameterName hibernate named parameter name
     * @param propValues hibernate named paramter value
     * @param namedParams map to use to set named parameter values in the Query
     * @return HQL or empty
     */
    protected String addInRestriction(String propertyName, String parameterName, Collection<?> propValues,
            Map<String, Object> namedParams) {
        if (propValues != null && !propValues.isEmpty()) {
            namedParams.put(parameterName, propValues);
            return propertyName + " in (" + ":" + parameterName + ")";
        }
        return "";
    }

    /**
     * @param lhs left hand side
     * @param rhs right hand side
     * @return HQL in expression
     */
    @SuppressWarnings("PMD.ShortMethodName")
    protected String in(String lhs, String rhs) {
        return lhs + " in (" + rhs + ")";
    }

    /**
     * @param propertyName property name
     * @return lower(propertyName)
     */
    protected String lower(String propertyName) {
        return "lower(" + propertyName + ")";
    }

    /**
     * @param lhsPropertyName property name
     * @param rhsNamedParameter named parameter name
     * @return lhsPropertyName like :rhsNamedParameter
     */
    protected String like(String lhsPropertyName, String rhsNamedParameter) {
        return lhsPropertyName + " like " + ":" + rhsNamedParameter;
    }

    /**
     * @param lhsPropertyName property name
     * @param rhsNamedParameter named parameter name
     * @return lower(lhsPropertyName) like :rhsNamedParameter
     */
    protected String ilike(String lhsPropertyName, String rhsNamedParameter) {
        return lower(lhsPropertyName) + " like " + ":" + rhsNamedParameter;
    }

    /**
     * @param lhs left-hand side of in
     * @param rhs right-hand side of in
     * @return lhs in (rhs) if rhs is not blank, otherwise return blank
     */
    protected String inIfRhs(String lhs, String rhs) {
        if (StringUtils.isNotBlank(rhs)) {
            return in(lhs, rhs);
        }
        return "";
    }

    /**
     * Looks through all Searchable methods and determines if the example object has at least
     * one method that participates in the query by example.  For each method marked as searchable,
     * the result of invoking the method is examined as follows:
     * <ul>
     *   <li>Null results do not participate in query by example.
     *   <li>If the result is a <code>PersistentObject</code> and the <code>getId</code>
     *       method returns a non-null value, the object is considered to have a criteria.
     *   <li>If the result is a <code>Collection</code> and the <code>isEmpty</code> method
     *       returns false, the object is considered to have a criterion.
     *   <li>Otherwise, if the result is non-null, the object is considered to have a criterion.
     * </ul>
     *
     * @param o the object to examine
     * @return whether one (or more) methods participate in the criteria
     */
    public static boolean hasSearchableCriterion(final Object o) {

        AnnotationCallback ac = new AnnotationCallback() {

            private boolean hasOneCriterion;

            public void callback(Method m, Object result) {
                if (hasOneCriterion) {
                    return;
                }

                if (result instanceof PersistentObject && ((PersistentObject) result).getId() != null) {
                    hasOneCriterion = true;
                }
                if (result instanceof Collection<?> && !((Collection<?>) result).isEmpty()) {
                    hasOneCriterion = true;
                }
                if (result != null) {
                    hasOneCriterion = true;
                }
            }

            public Object getSavedState() {
                return hasOneCriterion;
            }
        };

        iterateAnnotatedMethods(o, ac);
        return (Boolean) ac.getSavedState();
    }

    /**
     * @param obj object to inspect
     * @param isCountOnly do a count query
     * @return query object
     */
    public static Query getQueryBySearchableFields(final Object obj, boolean isCountOnly) {
        final StringBuffer query = new StringBuffer(SELECT);
        final Map<String, Object> params = new HashMap<String, Object>();

        query.append((isCountOnly ? "COUNT(obj) " : "obj"));
        query.append(FROM);
        query.append(obj.getClass().getName());
        query.append(" obj");

        AnnotationCallback ac = new AnnotationCallback() {

            private String whereOrAnd = WHERE;

            @SuppressWarnings({"PMD.UseStringBufferForStringAppends", "PMD.AvoidThrowingRawExceptionTypes" })
            public void callback(Method m, Object result) {
                String fieldName = StringUtils.uncapitalize(m.getName().substring("get".length()));
                String paramName = fieldName;
                Object paramValue = result;
                if (result instanceof PersistentObject) {
                    paramValue = ((PersistentObject) result).getId();
                    fieldName = fieldName + ".id";
                }
                if (paramValue != null) {
                    query.append(whereOrAnd);
                    if (result instanceof Collection<?>) {
                        throw new RuntimeException("collection types not yet implemented");
                    } else {
                        query.append(String.format("obj.%s = :%s", fieldName, paramName));
                        params.put(paramName, paramValue);
                    }
                    whereOrAnd = AND;
                }
            }

            public Object getSavedState() {
                return whereOrAnd;
            }
        };

        iterateAnnotatedMethods(obj, ac);

        Session session = PoHibernateUtil.getCurrentSession();
        Query q = session.createQuery(query.toString());
        for (String key : params.keySet()) {
            q.setParameter(key, params.get(key));
        }

        return q;
    }

    /**
     * Callback mechanism.
     */
    private static interface AnnotationCallback {
        void callback(Method m, Object result);
        Object getSavedState();
    }

    private static void iterateAnnotatedMethods(Object o, AnnotationCallback cb) {
        if (o == null) {
            return;
        }
        for (Method m : o.getClass().getMethods()) {
            if (m.getAnnotation(Searchable.class) != null) {
                if (m.getParameterTypes().length != 0) {
                    LOG.error(String.format("The @Searchable annotation cannot be applied to %s.%s()",
                                            o.getClass(), m));
                } else {
                    try {
                        cb.callback(m, m.invoke(o));
                    } catch (IllegalArgumentException e) {
                        LOG.error(String.format("Unable to invoke @Searchable on %s.%s", o.getClass(), m), e);
                    } catch (IllegalAccessException e) {
                        LOG.error(String.format("Unable to invoke @Searchable on %s.%s", o.getClass(), m), e);
                    } catch (InvocationTargetException e) {
                        LOG.error(String.format("Unable to invoke @Searchable on %s.%s", o.getClass(), m), e);
                    }
                }
            }
        }
    }

    /**
     * Used to build a findMatchingAddressOrCountry query.
     */
    enum AddressQuery {
        /**
         * ContactInfo.person property.
         */
        PERSON("person"),

        /**
         * ContactInfo.organization property.
         */
        ORGANIZATION("organization");

        private final String type;

        private AddressQuery(String type) {
            this.type = type;

        }

        public String getType() {
            return type;
        }
    }
}