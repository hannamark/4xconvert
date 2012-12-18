/**
 * 
 */
package gov.nih.nci.po.web.util;

import gov.nih.nci.po.util.PoRegistry;

import java.util.ArrayList;
import java.util.Collection;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;

/**
 * Utility functions for use in JSPs.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class Functions {

    /**
     * Wraps given Collection into {@link PaginatedList} using default
     * page/sorting settings.
     * 
     * @param c Collection
     * @return PaginatedList
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static PaginatedList wrapInPaginated(Collection c) {
        return new PaginatedList(c.size(), new ArrayList(c),
                PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null, "ID",
                SortOrderEnum.ASCENDING);
    }

}
