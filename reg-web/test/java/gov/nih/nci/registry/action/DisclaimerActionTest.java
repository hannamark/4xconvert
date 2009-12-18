/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class DisclaimerActionTest extends AbstractRegWebTest {
    private DisclaimerAction action = new DisclaimerAction();
    @Test
    public void testActionNameProperty() {
        assertNull(action.getActionName());
        action.setActionName("actionName");
        assertNotNull(action.getActionName());
    }
    @Test
    public void testExecute() {
        assertEquals("show_Disclaimer_Page",action.execute());   
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
     assertEquals("accept",(String)ServletActionContext.getRequest()
             .getSession().getAttribute("disclaimer"));
     assertEquals("searchTrial.action", action.getActionName());
    }
}
