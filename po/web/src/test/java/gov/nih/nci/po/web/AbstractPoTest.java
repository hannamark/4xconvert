package gov.nih.nci.po.web;


import gov.nih.nci.po.web.util.MockServiceLocator;
import gov.nih.nci.po.web.util.PoRegistry;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.opensymphony.xwork2.ActionContext;

/**
 * Used to set up common test infrastructure for the web tier.
 * @author Scott Miller
 */
public abstract class AbstractPoTest {

    /**
     * Sets up the test service locator.
     */
    @Before
    public void initializeTestServiceLocator() {
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator());
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
    
    /**
     * @return MockHttpServletRequest
     */
    protected MockHttpServletRequest getRequest() {
        return (MockHttpServletRequest) ServletActionContext.getRequest();
    }
    
    /**
     * @return MockHttpSession
     */
    protected MockHttpSession getSession() {
        return (MockHttpSession) ServletActionContext.getRequest().getSession();
    }
}
