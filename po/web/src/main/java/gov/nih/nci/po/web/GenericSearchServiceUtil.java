package gov.nih.nci.po.web;

import gov.nih.nci.po.service.GenericSearchService;
import gov.nih.nci.po.service.PoSortCriterion;

import org.apache.log4j.Logger;
import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;

/**
 * Helper class for invoking search methods on GenericSearchService based services.
 *
 * @author smatyas
 *
 */
public final class GenericSearchServiceUtil {
    /**
     * Logger to log results for debugging purposes only.
     */
    private static final Logger LOG = Logger.getLogger(GenericSearchServiceUtil.class);

    /**
     * @param <T> a
     * @param <SC> b
     * @param s c
     * @param c d
     * @param l e
     * @param sortCriterionType type of SortCriterion class
     */
    @SuppressWarnings("unchecked")
    public static <T extends PersistentObject, SC extends SearchCriteria<T>> void search(GenericSearchService<T, SC> s,
            SC c, PaginatedList<T> l, Class sortCriterionType) {
        Enum sc1 = Enum.valueOf(sortCriterionType, l.getSortCriterion());
        PoSortCriterion<T> sc = (PoSortCriterion<T>) sc1;
        PageSortParams<T> pageSortParams = new PageSortParams<T>(l.getObjectsPerPage(), (l
                .getPageNumber() - 1)
                * l.getObjectsPerPage(), sc.getOrderByList(), l.getSortDirection().equals(SortOrderEnum.DESCENDING));
        LOG.debug("Searching for match...");
        l.setList(s.search(c, pageSortParams));
        l.setFullListSize(s.count(c));
    }
}
