package gov.nih.nci.pa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filter ensures that the request.AUTHENTICATION_SOURCE_MAP is always available on the request.
 * 
 * @author bpickeral
 * 
 */
public class CGMMAuthSourceRequestFilter implements Filter {

    /**
     * The name of the AUTHENTICATION_SOURCE_MAP variable in session and the request.
     */
    public static final String AUTH_SOURCE_MAP = "AUTHENTICATION_SOURCE_MAP";

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        // do nothing
    }

    /**
     * Ensures that the request always contains AUTHENTICATION_SOURCE_MAP once it has first appeared for a given
     * session.
     * @param req the request
     * @param resp the response
     * @param chain the filter chain
     * @throws IOException if error occurs
     * @throws ServletException if error occurs
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpSession session = httpRequest.getSession();
        
        Object requestAuthSrcMap = httpRequest.getAttribute(AUTH_SOURCE_MAP);
        if (requestAuthSrcMap != null) {
            session.getServletContext().setAttribute(AUTH_SOURCE_MAP, requestAuthSrcMap);
        }
        chain.doFilter(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig arg0) throws ServletException {
        // do nothing
    }    
    
}
