package gov.nih.nci.pa.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

public class PopUpDisActionTest extends AbstractPaActionTest {

	PopUpDisAction popUpDisAction;
	
	@Before 
	public void setUp() throws PAException {
	  popUpDisAction =  new PopUpDisAction();	  
	} 
	
	@Test
	public void testDisplayList() {
	 getRequest().setupAddParameter("searchName", "");
	 getRequest().setupAddParameter("includeSynonym", "true");
	 getRequest().setupAddParameter("exactMatch", "true");
	 popUpDisAction.displayList();
	assertNotNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testDisplayListWithSearchResults() {
	 getRequest().setupAddParameter("searchName", "disease");
	 getRequest().setupAddParameter("includeSynonym", "true");
	 getRequest().setupAddParameter("exactMatch", "true");
	 assertEquals("success", popUpDisAction.displayList());
	}
}
