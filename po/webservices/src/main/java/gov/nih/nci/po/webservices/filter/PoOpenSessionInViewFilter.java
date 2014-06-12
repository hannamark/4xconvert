package gov.nih.nci.po.webservices.filter;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;
import com.fiveamsolutions.nci.commons.web.filter.OpenSessionInViewFilter;
import gov.nih.nci.po.util.PoHibernateUtil;

/**
 * Filter extension that uses the PoHibernateUtil.
 */
public class PoOpenSessionInViewFilter extends OpenSessionInViewFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    public HibernateHelper getHibernateHelper() {
        return PoHibernateUtil.getHibernateHelper();
    }

}
