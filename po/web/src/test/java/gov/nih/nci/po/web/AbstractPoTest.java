package gov.nih.nci.po.web;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.MockServiceLocator;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * Used to set up common test infrastructure for the web tier.
 * 
 * @author Scott Miller
 */
public abstract class AbstractPoTest {

    /**
     * Sets up the test service locator.
     */
    @Before
    public final void initialize() {
        initActionContext_getContext();
        initMockrequest();
        initMockrResponse();
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator());
    }

    private void initMockrResponse() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

    }

    /**
     * Initialize the mock request.
     */
    private final void initMockrequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("abc-123", new Object());
        ServletActionContext.setRequest(request);
    }

    private final void initActionContext_getContext() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager
                .addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class)
                .createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        assertNotNull(ActionContext.getContext());
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
     * @return
     */
    protected MockHttpServletResponse getResponse() {
        return (MockHttpServletResponse) ServletActionContext.getResponse();
    }

    /**
     * @return MockHttpSession
     */
    protected MockHttpSession getSession() {
        return (MockHttpSession) ServletActionContext.getRequest().getSession();
    }
}
