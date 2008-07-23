package gov.nih.nci.pa;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.test.util.MockServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import com.opensymphony.xwork2.ActionContext;

/**
 * adapted from PO.
 * @author Harsha
 *
 */

public class BasePaTest {
    /**
     * Sets up the test service locator.
     */
    @Before
    public void initializeTestServiceLocator() {
        PaRegistry.getInstance().setServiceLocator(new MockServiceLocator());
    }

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
