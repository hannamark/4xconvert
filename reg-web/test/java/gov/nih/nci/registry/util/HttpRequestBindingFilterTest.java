/**
 * 
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockFilterConfig;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Denis G. Krylov
 * 
 */
public class HttpRequestBindingFilterTest {

    private HttpRequestBindingFilter filter;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        filter = new HttpRequestBindingFilter();
        filter.init(new MockFilterConfig());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        filter.destroy();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.registry.util.HttpRequestBindingFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)}
     * .
     * 
     * @throws ServletException
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Test
    public final void testDoFilter() throws IOException, ServletException,
            SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockHttpSession session = new MockHttpSession();
        request.setSession(session);

        filter.doFilter(request, response, new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest,
                    ServletResponse servletResponse) throws IOException,
                    ServletException {
                assertEquals(request, servletRequest);
                assertEquals(response, servletResponse);
                assertEquals(1, HttpRequestBindingFilter.getAllActiveRequests()
                        .size());
                assertEquals(request, HttpRequestBindingFilter
                        .getAllActiveRequests().get(0));
                assertEquals(request,
                        HttpRequestBindingFilter.getCurrentHttpServletRequest());
                assertEquals(1,
                        HttpRequestBindingFilter.getRequestsBySession(session)
                                .size());
                assertEquals(request, HttpRequestBindingFilter
                        .getRequestsBySession(session).get(0));
                assertEquals(Thread.currentThread(),
                        HttpRequestBindingFilter.getThreadByRequest(request));
            }
        });

        assertEquals(0, HttpRequestBindingFilter.getAllActiveRequests().size());
        assertEquals(null,
                HttpRequestBindingFilter.getCurrentHttpServletRequest());
        assertEquals(0, HttpRequestBindingFilter.getRequestsBySession(session)
                .size());
        assertEquals(null, HttpRequestBindingFilter.getThreadByRequest(request));

        Field field = HttpRequestBindingFilter.class
                .getDeclaredField("SESSION_TO_REQUEST_MAP");
        field.setAccessible(true);
        Map map = (Map) field.get(null);
        assertTrue(map.isEmpty());

        field = HttpRequestBindingFilter.class
                .getDeclaredField("REQUEST_TO_THREAD_MAP");
        field.setAccessible(true);
        map = (Map) field.get(null);
        assertTrue(map.isEmpty());

    }

}
