/**
 * 
 */
package gov.nih.nci.coppa.services.pa.grid.remote;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public final class InterceptingFilter implements Filter {

    private static final Logger LOG = LogManager
            .getLogger(InterceptingFilter.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain fc) throws IOException, ServletException {
        try {
            fc.doFilter(req, resp);
        } finally {
            List<InitialContext> list = GridSecurityJNDIServiceLocator.CTX_HOLDER
                    .get();
            GridSecurityJNDIServiceLocator.CTX_HOLDER.remove();
            if (list != null) {
                for (InitialContext ic : list) {
                    try {
                        ic.close();
                    } catch (Exception e) {
                        LOG.warn("Unable to close InitialContext due to "
                                + e.getMessage());
                    }
                }
                list.clear();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {

    }

}
