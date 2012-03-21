/**
 * 
 */
package gov.nih.nci.registry.util;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * The purpose of this listener is to solely debug
 * https://tracker.nci.nih.gov/browse/PO-4773. I'm not sure how the session gets
 * invalidated in the middle of request processing. Hopefully, output of this
 * listener will help to understand & fix that, after which the filter can be
 * removed.
 * 
 * @author Denis G. Krylov
 * @see https://tracker.nci.nih.gov/browse/PO-4773
 * 
 */
// CHECKSTYLE:OFF
@SuppressWarnings({ "PMD" })
public final class HttpSessionDebuggingListener implements HttpSessionListener {

    private static final Logger LOG = Logger
            .getLogger(HttpSessionDebuggingListener.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
     * .HttpSessionEvent)
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setAttribute(
                "gov.nih.nci.registry.util.HttpSessionDebuggingListener.DEBUG",
                new HttpSessionBindingListener() {
                    @Override
                    public void valueUnbound(HttpSessionBindingEvent event) {
                        HttpSession session = event.getSession();
                        LOG.warn("valueUnbound event caught; session is about to be invalidated; debugging info starts below...");
                        writeDebugInfo(session);
                    }

                    @Override
                    public void valueBound(HttpSessionBindingEvent event) {
                    }
                });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
     * .http.HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        LOG.warn("Session is about to be invalidated; debugging info starts below...");
        writeDebugInfo(session);
    }

    /**
     * @param session
     *            HttpSession
     */

    @SuppressWarnings("rawtypes")
    public static void writeDebugInfo(HttpSession session) {
        try {
            LOG.warn("##### PO-4773 debugging information starts here; please ignore the entire section below. ######");
            LOG.warn("Session ID: " + session.getId());
            LOG.warn("Session maximum time interval: "
                    + session.getMaxInactiveInterval());
            try {
                LOG.warn("Session created at: "
                        + new Date(session.getCreationTime()));
                LOG.warn("Session last accessed at: "
                        + new Date(session.getLastAccessedTime()));
                LOG.warn("Session inactive for: "
                        + ((System.currentTimeMillis() - session
                                .getLastAccessedTime()) / 1000.0) + " seconds");

                final Enumeration attrNames = session.getAttributeNames();
                if (attrNames != null) {
                    while (attrNames.hasMoreElements()) {
                        String name = (String) attrNames.nextElement();
                        LOG.warn("Attr name: " + name);
                        LOG.warn("Attr value: " + session.getAttribute(name));
                    }
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to dump all session attributes: already invalidated.");
            }

            List<HttpServletRequest> requests = HttpRequestBindingFilter
                    .getRequestsBySession(session);
            if (requests != null && !requests.isEmpty()) {
                LOG.warn("There are "
                        + requests.size()
                        + " active request(s) associated with the current session.");
                for (HttpServletRequest httpServletRequest : requests) {
                    writeDebugInfo(httpServletRequest);
                }
            } else {
                LOG.warn("There are no known active requests associated with this session.");
            }
            LOG.warn("Now dump the current thread, the thread that must be invalidating this session at this instant...");
            writeDebugInfo(Thread.currentThread());

            LOG.warn("Finally, let's dump ALL other active requests being processed at this instant...");
            List<HttpServletRequest> otherRequests = HttpRequestBindingFilter
                    .getAllActiveRequests();
            otherRequests.removeAll(requests);
            for (HttpServletRequest httpServletRequest : otherRequests) {
                writeDebugInfo(httpServletRequest);
            }

            LOG.warn("##### PO-4773 debugging information stops here. ######");
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    @SuppressWarnings({ "rawtypes", "PMD" })
    public static void writeDebugInfo(HttpServletRequest request) {
        LOG.warn("Dumping request info...");
        LOG.warn("Protocol: " + request.getProtocol());
        LOG.warn("Scheme: " + request.getScheme());
        LOG.warn("Remote Addr: " + request.getRemoteAddr());
        LOG.warn("Secure: " + request.isSecure());
        LOG.warn("Context path: " + request.getContextPath());
        LOG.warn("Servlet path: " + request.getServletPath());
        LOG.warn("Request URI: " + request.getRequestURI());
        LOG.warn("Request URL: " + request.getRequestURL());
        LOG.warn("Path Info: " + request.getPathInfo());
        LOG.warn("Query String: " + request.getQueryString());
        LOG.warn("Remote User: " + request.getRemoteUser());
        LOG.warn("Requested Session ID: " + request.getRequestedSessionId());
        LOG.warn("Method: " + request.getMethod());
        LOG.warn("isRequestedSessionIdFromCookie: "
                + request.isRequestedSessionIdFromCookie());
        LOG.warn("isRequestedSessionIdFromURL: "
                + request.isRequestedSessionIdFromURL());
        LOG.warn("isRequestedSessionIdValid: "
                + request.isRequestedSessionIdValid());
        LOG.warn("Parameter map: " + request.getParameterMap());
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                LOG.warn("Cookie name: " + cookie.getName());
                LOG.warn("Cookie value: " + cookie.getValue());
            }
        }
        final Enumeration headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = (String) headerNames.nextElement();
                LOG.warn("Header name: " + name);
                LOG.warn("Header value: " + request.getHeader(name));
            }
        }
        final Enumeration attrNames = request.getAttributeNames();
        if (attrNames != null) {
            while (attrNames.hasMoreElements()) {
                String name = (String) attrNames.nextElement();
                LOG.warn("Attr name: " + name);
                LOG.warn("Attr value: " + request.getAttribute(name));
            }
        }

        Thread thread = HttpRequestBindingFilter.getThreadByRequest(request);
        if (thread != null) {
            LOG.warn("This request is being processed by the following thread:");
            writeDebugInfo(thread);
        }

    }

    /**
     * @param thread
     */
    public static void writeDebugInfo(Thread thread) {
        LOG.warn("Thread name: " + thread.getName());
        LOG.warn("Thread prio: " + thread.getPriority());
        StackTraceElement[] traceElements = thread.getStackTrace();
        Throwable ex = new Throwable();
        ex.setStackTrace(traceElements);
        LOG.warn("Thread stack trace: ");
        LOG.warn(ExceptionUtils.getFullStackTrace(ex));
    }
}
