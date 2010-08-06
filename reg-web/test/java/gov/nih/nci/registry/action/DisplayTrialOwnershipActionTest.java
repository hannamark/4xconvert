package gov.nih.nci.registry.action;
 
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.service.PAException;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * @author Kalpana Guthikonda
 *
 */
public class DisplayTrialOwnershipActionTest extends AbstractRegWebTest{
    private DisplayTrialOwnershipAction action;
    
    @Test
    public void testSearch() throws PAException {
        action = new DisplayTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
    }

	@Test
    public void testView() throws PAException {
        action = new DisplayTrialOwnershipAction();
        assertEquals("viewResults", action.view());
    }

	@Test
	public void testRemoveOwnership() throws PAException {
        action = new DisplayTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("userId", "1");
		request.setupAddParameter("trialId", "1");
		request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.removeOwnership());
    }

	@Test
    public void testActionProperties(){
        action = new DisplayTrialOwnershipAction();
		action.setCriteria(new DisplayTrialOwnershipInformation());
        assertNotNull(action.getCriteria());
        assertNotNull(action.getTrialOwnershipInfo());
        action.setTrialOwnershipInfo(null);
        assertNull(action.getTrialOwnershipInfo());
    }
}
