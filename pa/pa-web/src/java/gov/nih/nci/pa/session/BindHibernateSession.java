package gov.nih.nci.pa.session;

import gov.nih.nci.pa.util.HibernateUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @author Harsha
 *
 */
public class BindHibernateSession implements Filter {

    /**
     * {@inheritDoc}
     */   
    public void destroy() {
        //HibernateUtil.getHibernateHelper().unbindAndCleanupSession();
    }

    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SystemPrintln") 
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {        
        HibernateUtil.getHibernateHelper().openAndBindSession();
        arg2.doFilter(arg0, arg1);
        HibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        
    }

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig arg0) throws ServletException {
     // no-op
    }

}
