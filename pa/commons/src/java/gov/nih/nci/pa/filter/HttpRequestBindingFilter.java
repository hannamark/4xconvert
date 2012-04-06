/**
 * 
 */
package gov.nih.nci.pa.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * A filter that binds current {@link HttpServletRequest} to the current thread
 * and also maps {@link HttpSession} to its {@link HttpServletRequest}.
 * 
 * The purpose of this filter is to solely debug
 * https://tracker.nci.nih.gov/browse/PO-4773. I'm not sure how the session gets
 * invalidated in the middle of request processing. Hopefully, output of this
 * filter will help to understand & fix that, after which the filter can be
 * removed.
 * 
 * @author Denis G. Krylov
 * @see https://tracker.nci.nih.gov/browse/PO-4773
 * 
 */
// CHECKSTYLE:OFF
@SuppressWarnings({ "PMD" })
public final class HttpRequestBindingFilter implements Filter {

    private static final ThreadLocal<ServletRequest> REQUEST_HOLDER = new ThreadLocal<ServletRequest>();

    private static final Map<HttpSession, List<HttpServletRequest>> SESSION_TO_REQUEST_MAP = new ConcurrentHashMap<HttpSession, List<HttpServletRequest>>();

    private static final Map<HttpServletRequest, Thread> REQUEST_TO_THREAD_MAP = new ConcurrentHashMap<HttpServletRequest, Thread>();

    private static final Logger LOG = Logger
            .getLogger(HttpRequestBindingFilter.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        try {
            final HttpServletRequest httpRequest = (HttpServletRequest) request;
            final HttpSession session = httpRequest.getSession();

            REQUEST_HOLDER.set(request);
            REQUEST_TO_THREAD_MAP.put(httpRequest, Thread.currentThread());

            List<HttpServletRequest> list = SESSION_TO_REQUEST_MAP.get(session);
            if (list == null) {
                list = Collections
                        .synchronizedList(new ArrayList<HttpServletRequest>());
                SESSION_TO_REQUEST_MAP.put(session, list);
            }
            list.add(httpRequest);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        try {
            chain.doFilter(request, response);
        } finally {
            try {
                final HttpServletRequest httpRequest = (HttpServletRequest) request;
                final HttpSession session = httpRequest.getSession();
                
                REQUEST_HOLDER.remove();
                REQUEST_TO_THREAD_MAP.remove(httpRequest);

                List<HttpServletRequest> list = SESSION_TO_REQUEST_MAP
                        .get(session);
                if (list != null) {
                    list.remove(httpRequest);
                    if (list.isEmpty()) {
                        SESSION_TO_REQUEST_MAP.remove(session);
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

    }

    /**
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentHttpServletRequest() {
        return (HttpServletRequest) REQUEST_HOLDER.get();
    }

    /**
     * @param session
     *            HttpSession
     * @return HttpServletRequest
     */
    public static List<HttpServletRequest> getRequestsBySession(
            HttpSession session) {
        final List<HttpServletRequest> requests = SESSION_TO_REQUEST_MAP
                .get(session);
        return requests != null ? requests : new ArrayList<HttpServletRequest>();
    }

    /**
     * @param request
     *            HttpServletRequest
     * @return Thread
     */
    public static Thread getThreadByRequest(HttpServletRequest request) {
        return REQUEST_TO_THREAD_MAP.get(request);
    }
    
    /**
     * @return
     */
    public static List<HttpServletRequest> getAllActiveRequests() {
        return new ArrayList<HttpServletRequest>(REQUEST_TO_THREAD_MAP.keySet());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        REQUEST_TO_THREAD_MAP.clear();
        SESSION_TO_REQUEST_MAP.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOG.debug("HttpRequestBindingFilter is starting up...");
    }

}
