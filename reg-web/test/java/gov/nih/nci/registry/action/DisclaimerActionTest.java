/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class DisclaimerActionTest extends AbstractRegWebTest {

    private final DisclaimerAction action = new DisclaimerAction();
    @Before
    public void setup() {
        CSMUserService.getInstance();
        CSMUserService.setRegistryUserService(new MockCSMUserService());
    }

    @Test
    public void testActionNameProperty() {
        assertNull(action.getActionName());
        action.setActionName("actionName");
        assertNotNull(action.getActionName());
    }
    @Test
    public void testProperty() {
        assertNull(action.getFailureMessage());
        action.setFailureMessage("noUser");
        assertNotNull(action.getFailureMessage());
    }
    @Test
    public void testExecute() {
        assertEquals("redirect_to", action.execute());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("firstName");
        ServletActionContext.setRequest(request);
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("show_Disclaimer_Page", action.execute());
    }

    @Test
    public void testAccept() {
        action.accept();
        action.setActionName("");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.accept();
        assertTrue((Boolean)ServletActionContext.getRequest().getSession().getAttribute("disclaimerAccepted"));
        assertEquals("searchTrial.action", action.getActionName());

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("actionName", "submitTrial.action");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.accept();
        assertTrue((Boolean)ServletActionContext.getRequest().getSession().getAttribute("disclaimerAccepted"));
        assertEquals("submitTrial.action", action.getActionName());
    }

    @Test
    public void testAccept2() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("actionName", "Test");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.accept();
        assertEquals(Boolean.TRUE, ServletActionContext.getRequest().getSession().getAttribute("disclaimerAccepted"));
        assertEquals("Test", action.getActionName());
    }

}
