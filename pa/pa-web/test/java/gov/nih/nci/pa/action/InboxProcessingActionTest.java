package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

public class InboxProcessingActionTest extends AbstractPaActionTest {
	InboxProcessingAction inboxAction;
	@Before
	public void setUp() throws PAException {
	 inboxAction	= new InboxProcessingAction();
	 getSession().setAttribute(Constants.USER_ROLE, Constants.ABSTRACTOR);

	}
	@Test
	public void testExecute() throws PAException {
		assertEquals("success", inboxAction.execute());
		assertEquals(1,inboxAction.getPendingAdminUsers().size());
	}

	@Test(expected=Exception.class)
	public void testShowCriteria() throws PAException {
		inboxAction.showCriteria();
	}

	@Test(expected=Exception.class)
	public void testView() throws PAException {
		assertEquals("error", inboxAction.view());
	}

	@Test
	public void testprocessUserRole() {
	    getRequest().setupAddParameter("action", "accept");
	    getRequest().setupAddParameter("id","2");
	    assertEquals("success",inboxAction.processUserRole());
	    assertNotNull(getRequest().getAttribute("failureMessage"));
	    assertEquals("User not found",getRequest().getAttribute("failureMessage"));

	    getRequest().setupAddParameter("action", "accept");
        getRequest().setupAddParameter("id","1");
        assertEquals("success",inboxAction.processUserRole());
        assertNotNull(getRequest().getAttribute("successMessage"));

        getRequest().setupAddParameter("action", "reject");
        getRequest().setupAddParameter("id","1");
        assertEquals("success",inboxAction.processUserRole());
        assertNotNull(getRequest().getAttribute("successMessage"));
	}

}
