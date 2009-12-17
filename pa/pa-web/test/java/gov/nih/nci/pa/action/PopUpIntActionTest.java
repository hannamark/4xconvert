/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpIntActionTest extends AbstractPaActionTest {

	PopUpIntAction popUpIntAction;
	
	@Before 
	public void setUp() throws PAException {
	  popUpIntAction =  new PopUpIntAction();	
	  getRequest().setupAddParameter("searchName", "");
	  getRequest().setupAddParameter("includeSynonym", "yes");
	  getRequest().setupAddParameter("exactMatch", "yes");
	} 
	
	@Test
	public void testDisplayList() {
		popUpIntAction.displayList();
		assertNotNull(getRequest().getAttribute("failureMessage"));
		assertEquals("Please enter at least one search criteria.", getRequest().getAttribute("failureMessage"));
	
	}
	@Test
	public void testDisplayListWithData() {
		getRequest().setupAddParameter("searchName", "Test");
		assertEquals("success", popUpIntAction.displayList());
			
	}
}
