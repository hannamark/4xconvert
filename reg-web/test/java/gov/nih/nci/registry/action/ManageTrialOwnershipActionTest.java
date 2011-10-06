package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.util.SelectedRegistryUser;
import gov.nih.nci.registry.util.SelectedStudyProtocol;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * @author Kalpana Guthikonda
 *
 */
public class ManageTrialOwnershipActionTest extends AbstractRegWebTest {
    private ManageTrialOwnershipAction action;

    @Test
    public void testSearch() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
    }

    @Test
    public void testView() throws PAException {
        action = new ManageTrialOwnershipAction();
        assertEquals("viewResults", action.view());
    }

    @Test
	public void testSetRegUser() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("regUserId", "3");
		request.setupAddParameter("isOwner", "true");
		request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        action.setRegUser();
    }

    @Test
    public void testSetTrial() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("trialId", "3");
        request.setupAddParameter("isSelected", "true");
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("studyProtocolsList", action.getStudyProtocols());
        action.setTrial();
    }

    @Test
    public void testAssignOwnershipException() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        try {
            action.assignOwnership();
        } catch(PAException e) {
            //expected
        }
    }

    @Test
    public void testAssignOwnership() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", new ArrayList<SelectedRegistryUser>());
        request.getSession().setAttribute("studyProtocolsList", new ArrayList<SelectedStudyProtocol>());
        assertEquals("viewResults", action.assignOwnership());
        action.search();
        action.getRegistryUsers().get(0).setSelected(true);
        action.getStudyProtocols().get(0).setSelected(true);
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        request.getSession().setAttribute("studyProtocolsList", action.getStudyProtocols());
        assertEquals("viewResults", action.assignOwnership());
    }

    @Test
    public void testUnAssignOwnershipException() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        try {
            action.unassignOwnership();
        } catch(PAException e) {
            //expected
        }
    }

    @Test
    public void testUnAssignOwnership() throws PAException {
        action = new ManageTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", new ArrayList<SelectedRegistryUser>());
        request.getSession().setAttribute("studyProtocolsList", new ArrayList<SelectedStudyProtocol>());
        assertEquals("viewResults", action.unassignOwnership());
        action.search();
        action.getRegistryUsers().get(0).setSelected(true);
        action.getStudyProtocols().get(0).setSelected(true);
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        request.getSession().setAttribute("studyProtocolsList", action.getStudyProtocols());
        assertEquals("viewResults", action.unassignOwnership());
    }
}
