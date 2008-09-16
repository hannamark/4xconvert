/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.test.util.MockServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author hreinhart
 *
 */
public abstract class AbstractPaActionTest {


    /**
     * Set up services.
     */
    @Before
    public void setUpServices() {
        PaRegistry.getInstance().setServiceLocator(new MockServiceLocator());
    }


    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockRequest() {
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
