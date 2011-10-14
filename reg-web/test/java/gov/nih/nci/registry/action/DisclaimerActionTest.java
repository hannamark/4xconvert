/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * Test Class.
 */
public class DisclaimerActionTest extends AbstractRegWebTest {

    private DisclaimerAction action;

    @Before
    public void initAction() {
        action = new DisclaimerAction();
        CSMUserService.getInstance();
        CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test
    public void acceptTest(){
        assertEquals("redirect_to", action.accept());
        assertTrue((Boolean)ServletActionContext.getRequest().getSession().
                getAttribute("disclaimerAccepted"));
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
        assertEquals("success", action.execute());
    }


}
