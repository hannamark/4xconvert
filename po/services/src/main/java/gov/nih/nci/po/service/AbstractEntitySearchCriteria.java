package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Abstract search criteria class for Entity types.
 */
public abstract class AbstractEntitySearchCriteria extends AbstractSearchCriteria {

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param values list of criterion values
     * @param entityType criterion type
     * @param inElementsProperty the root entity type's collection property to find matches
     * @param rootType root entity type
     * @return HQL select to identify matching entries of type Contact
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AvoidDeeplyNestedIfStmts" })
    protected StringBuffer findMatchingContact(Map<String, Object> namedParameters, List<? extends Contact> values,
            Class<? extends Contact> entityType, String inElementsProperty, 
            Class<? extends PersistentObject> rootType) {
        StringBuffer subselect = new StringBuffer();
        if (CollectionUtils.isNotEmpty(values)) {
            String rootTypeTableAlias = "p_" + inElementsProperty;
            String contactEntityAlias = "_" + inElementsProperty;
            subselect.append(SELECT).append(rootTypeTableAlias).append(DOT).append(ID).append(FROM).append(
                    tableAlias(rootType, rootTypeTableAlias));
            subselect.append(COMMA);
            List<String> subselectWhereClause = new ArrayList<String>();
            int j = 0;
            for (Iterator<? extends Contact> eAliasItr = values.iterator(); eAliasItr.hasNext();) {
                Contact e = (Contact) eAliasItr.next();
                if (isValueSpecified(e.getValue())) {
                    String entityTableAlias = contactEntityAlias + j++;
                    subselect.append(tableAlias(entityType, entityTableAlias));
                    if (eAliasItr.hasNext()) {
                        subselect.append(COMMA);
                    }
                    String parameterName = "_param" + j;
                    subselectWhereClause.add(entityTableAlias + " in elements (" + rootTypeTableAlias + "."
                            + inElementsProperty + ")");
                    subselectWhereClause.add(addILike(entityTableAlias + ".value", parameterName, e.getValue(),
                            namedParameters));
                }
            }
            subselect.append(buildWhereClause(subselectWhereClause, WhereClauseOperator.CONJUNCTION));
        }
        return subselect;
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param sc search criteria
     * @param rootType root entity type that references the Address type
     * @param rootTypeAddressPropertyName property name referencing the Address type
     * @return HQL-based select statement to find Address(s) that match the root type (Person or Organization)
     */
    protected StringBuffer findMatchingAddress(Map<String, Object> namedParameters, AddressSearchCriteria sc,
            Class<? extends PersistentObject> rootType, String rootTypeAddressPropertyName) {
        return sc.findMatchingAddress(namedParameters, sc, rootType, rootTypeAddressPropertyName);
    }

}
