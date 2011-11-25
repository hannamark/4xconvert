package gov.nih.nci.pa.viewer.action;

import static org.junit.Assert.assertNotNull;

import gov.nih.nci.pa.viewer.util.ViewerConstants;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * Base class for viewer action test classes
 * 
 * @author Michael Visee
 */
public abstract class AbstractViewerActionTest {
    /**
     * user attached to the request
     */
    protected static final String USER = "joe@barngrill.com";

    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockRequest() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        assertNotNull(ActionContext.getContext());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser(USER);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        ServletActionContext.setResponse(response);
        setRole(ViewerConstants.ROLE_CTRO);
    }

    /**
     * Clean out the action context to ensure one test does not impact another.
     */
    @After
    public void cleanUpActionContext() {
        ActionContext.setContext(null);
    }

    /**
     * Sets the role in the session.
     * @param role The role to set
     */
    public void setRole(String role) {
        if (role != null) {
            ServletActionContext.getRequest().getSession().setAttribute(ViewerConstants.SESSION_ATTR_ROLE, role);
        } else {
            ServletActionContext.getRequest().getSession().removeAttribute(ViewerConstants.SESSION_ATTR_ROLE);
        }
    }
}
