package gov.nih.nci.po.service;

import org.hibernate.Query;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Search criteria interface to generalize common behavior.
 *
 * @param <T> type of criteria to search by
 */
public interface SearchCriteria<T extends PersistentObject> {

    /**
     * @return true is at least one criteria is specified otherwise, return false.
     */
    boolean hasOneCriterionSpecified();

    /**
     * @return true when criteria is valid, otherwise throws exception.
     */
    boolean isValid();

    /**
     * @param orderByProperty TODO
     * @param isCountOnly TODO
     * @return the generated Hibernate Query
     */
    Query getQuery(String orderByProperty, boolean isCountOnly);
    
    /**
     * @return the root object alias
     */
    String getRootAlias();
}