package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ContactInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * @author smatyas
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class AbstractSearchCriteria {
    /**
     * HQL select statement.
     */
    protected static final String SELECT = " SELECT ";

    /**
     * HQL from statement.
     */
    protected static final String FROM = " FROM ";

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
        return type.getName() + " " + alias;
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

    /**
     * @return select person.id(s) for matching Address or Country details associated to a person's ContactInfo(s)
     */
    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param sc address criteria
     * @param personOrOrganization enum to generalize return it
     * @return person.id or organization.id
     */
    protected StringBuffer findMatchingAddressOrCountry(Map<String, Object> namedParameters, AddressSearchCriteria sc,
            AddressQuery personOrOrganization) {
        StringBuffer subselect = new StringBuffer();
        if (sc.hasOneCriterionSpecified()) {
            String addressAlias = "addr";
            String countryAlias = "cntry";
            String ciAlias = "ci";
            String ciFKProperty = "ci." + personOrOrganization.getType() + ".id";
            String joinAddress = ciAlias + ".mailingAddress as " + addressAlias;
            String joinCountry = ciAlias + ".mailingAddress.country as " + countryAlias;

            List<String> subselectWhereClause = new ArrayList<String>();
            subselect.append(SELECT).append(ciFKProperty).append(FROM).append(tableAlias(ContactInfo.class, ciAlias))
                    .append(JOIN).append(joinAddress).append(JOIN).append(joinCountry);

            subselectWhereClause.add(addILike(addressAlias + ".cityOrMunicipality", "cityOrMunicipality", sc
                    .getCityOrMunicipality(), namedParameters));
            subselectWhereClause.add(addILike(addressAlias + ".stateOrProvince", "stateOrProvince", sc
                    .getStateOrProvince(), namedParameters));
            subselectWhereClause.add(addILike(addressAlias + ".postalCode", "postalCode", sc.getPostalCode(),
                    namedParameters));
            if (sc.getCountryId() != null) {
                subselectWhereClause
                        .add(addEqual(countryAlias + ".id", "countryId", sc.getCountryId(), namedParameters));
            } else {
                subselectWhereClause.add(addILike(countryAlias + ".name", "countryName", sc.getCountryName(),
                        namedParameters));
            }
            subselect.append(buildWhereClause(subselectWhereClause, WhereClauseOperator.CONJUNCTION));
        }
        return subselect;
    }
}