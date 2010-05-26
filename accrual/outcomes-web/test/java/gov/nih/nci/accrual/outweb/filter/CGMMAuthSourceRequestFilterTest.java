package gov.nih.nci.accrual.outweb.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import javax.servlet.ServletException;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockFilterChain;

import org.junit.Test;

public class CGMMAuthSourceRequestFilterTest {
    
    private static final String TEST_MAP = "test";

    @Test
    public void testFilterRequest() throws IOException, ServletException {
        // Test case where object is stored in request
        CGMMAuthSourceRequestFilter filter = new CGMMAuthSourceRequestFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP, TEST_MAP);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        filter.doFilter(request, response, chain);
        
        assertEquals(TEST_MAP, session.getAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP));
    }
    
    @Test
    public void testFilterSession() throws IOException, ServletException {
        // Test case where object is stored in session
        CGMMAuthSourceRequestFilter filter = new CGMMAuthSourceRequestFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP, TEST_MAP);
        request.setSession(session);
        filter.doFilter(request, response, chain);
        
        assertEquals(TEST_MAP, request.getAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP));
    }
    
    public void testFilterBoth() throws IOException, ServletException {
        // Test case where object is stored in session and request
        CGMMAuthSourceRequestFilter filter = new CGMMAuthSourceRequestFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP, TEST_MAP);
        request.setAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP, TEST_MAP);
        request.setSession(session);
        filter.doFilter(request, response, chain);
        
        assertEquals(TEST_MAP, request.getAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP));
        assertEquals(TEST_MAP, session.getAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP));
    }
    
    @Test
    public void testFilterNiether() throws IOException, ServletException {
        // Test case where object is not stored in session and not stored in request
        CGMMAuthSourceRequestFilter filter = new CGMMAuthSourceRequestFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        filter.doFilter(request, response, chain);
        
        assertNull(request.getAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP));
        assertNull(session.getAttribute(CGMMAuthSourceRequestFilter.AUTH_SOURCE_MAP));
    }
}
