/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.test.util.MockServiceLocator;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import javax.servlet.http.HttpSession;

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
        StudyProtocolQueryDTO ts = new StudyProtocolQueryDTO();
        ts.setStudyProtocolId(1L);
        
        HttpSession sess = new MockHttpSession();
        sess.setAttribute(Constants.TRIAL_SUMMARY, ts);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
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
