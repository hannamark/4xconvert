package gov.nih.nci.pa.action;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

public class PopUpDisActionTest extends AbstractPaActionTest {

	PopUpDisAction popUpDisAction;
	
	@Before 
	public void setUp() throws PAException {
	  popUpDisAction =  new PopUpDisAction();	
	  getRequest().setupAddParameter("searchName", "");
	  getRequest().setupAddParameter("includeSynonym", "yes");
	  getRequest().setupAddParameter("exactMatch", "yes");
	} 
	
	@Test
	public void testDisplayList() {
		popUpDisAction.displayList();
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}	
}
