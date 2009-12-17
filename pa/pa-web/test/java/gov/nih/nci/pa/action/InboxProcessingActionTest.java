package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
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
	}

	@Test(expected=Exception.class)
	public void testShowCriteria() throws PAException {
		inboxAction.showCriteria();
	}

	@Test
	public void testQuery() throws PAException {
		assertEquals("success", inboxAction.query());
	}

	@Test(expected=Exception.class)
	public void testView() throws PAException {
		assertEquals("error", inboxAction.view());
	}

	@Test
	public void testRemove() throws PAException {
		assertEquals("success", inboxAction.remove());
	}

}
