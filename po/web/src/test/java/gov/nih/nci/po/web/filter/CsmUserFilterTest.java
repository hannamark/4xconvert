package gov.nih.nci.po.web.filter;

import gov.nih.nci.po.web.util.CsmHelper;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


/**
 * Test the csm user filter
 * @author ludetc
 *
 */
public class CsmUserFilterTest {

    @Test
    public void testFilter() throws Exception {

        CsmUserFilter filter = new CsmUserFilter();
        filter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
               
        request.getSession().setAttribute(CsmHelper.class.getName(), new CsmHelper(""));
        filter = new CsmUserFilter();
        chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
        
        filter.destroy();
    }

    @Test
    public void testFilter2() throws Exception {
        
        CsmUserFilter filter = new CsmUserFilter();
        filter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest() {
            public String getRemoteUser() {
                return "aUser";
            }
        };
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
        
        request.getSession().setAttribute(CsmHelper.class.getName(), new CsmHelper(""));
        filter = new CsmUserFilter();
        chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
        
        filter.destroy();
    }
}
