package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.ActionContext;
/**
 * @author Bala
 *
 */

public class BaseRegistryTest {

    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockrequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        ServletActionContext.setRequest(request);
    }

    /**
     * Clean out the action context to ensure one test does not impact another.
     */
    @After
    public void cleanUpActionContext() {
        ActionContext.setContext(null);
    }

    @Test
    public void testDummy() {
        assertEquals(true, true);
    }

}
