package gov.nih.nci.commons.ctrp.web.struts2.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

public class AbstractInterceptorBaseTest {
    private ActionInvocation actionInvocation;
    private ActionContext invocationContext;
    private Map<String, Object> params;
    private ValueStack stack;
    private ValueStackFactory stackFactory;
    private Map<String, Object> session;

    @Before
    public void setup() {
        actionInvocation = mock(ActionInvocation.class);
        invocationContext = mock(ActionContext.class);
        params = new HashMap<String, Object>();

        session = new HashMap<String, Object>();
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();
        stackFactory = container.getInstance(ValueStackFactory.class);
        stack = stackFactory.createValueStack();
        stack.push(params);

        when(actionInvocation.getInvocationContext()).thenReturn(invocationContext);
        when(actionInvocation.getInvocationContext().getParameters()).thenReturn(params);
        when(actionInvocation.getInvocationContext().getSession()).thenReturn(session);
        when(actionInvocation.getStack()).thenReturn(stack);
    }

    @Test
    public void testProcessedParameters() throws Exception {
        String KEY_1 = "(aaa)(('\u0023context[\'xwork.MethodAccessor.denyMethodExecution\']\u003d\u0023foo')(\u0023foo\u003dnew java.lang.Boolean('false')))";
        String KEY_2 = "(asdf)(('\u0023rt.exit(1)')(\u0023rt\u003d@java.lang.Runtime@getRuntime()))";

        params.put(KEY_1, "Key 1 value");
        params.put(KEY_2, "Key 2 value");
        params.put("A_KEY", "Hello &lt;   < > some text.");

        AbstractInterceptorBaseTestImpl interceptor = new AbstractInterceptorBaseTestImpl();
        interceptor.intercept(getActionInvocation());

        assertEquals(1, interceptor.getProcessedParameters().size());
        assertNull(interceptor.getProcessedParameters().get(KEY_1));
        assertNull(interceptor.getProcessedParameters().get(KEY_2));
        assertTrue("Hello &lt;   < > some text.".equals(interceptor.getProcessedParameters().get("A_KEY")));
    }

    protected ActionInvocation getActionInvocation() {
        return actionInvocation;
    }

    protected Map<String, Object> getParams() {
        return params;
    }

    protected ValueStack getStack() {
        return stack;
    }

}
