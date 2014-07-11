package gov.nih.nci.po.service;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

import java.util.List;
/**
 * @author Todd Parnell
 * @param <T> type
 * @param <SC> SearchCriteria
 */
public interface GenericSearchService<T extends PersistentObject, SC extends SearchCriteria<T>> {

    /**
     * @param criteria to search types of T.
     * @return list of type T objects
     */
    List<T> search(SC criteria);

    /**
     * @param criteria to search types of T.
     * @param pageSortParams pagination and sorting bean.
     * @return list of type T objects
     */
    List<T> search(SC criteria, PageSortParams<T> pageSortParams);

    /**
     * Gets the total count of objects meeting the provided criteria.  Needed for
     * external paging in display tag.
     *
     * @param criteria search criteria
     * @return total count of objects meeting the criteria
     */
    int count(SC criteria);
}
