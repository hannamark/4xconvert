/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpTypeInterventionActionTest extends AbstractPaActionTest {

	PopUpTypeInterventionAction popUpInterventionAction;
	
	@Before 
	public void setUp() throws PAException {
    	popUpInterventionAction =  new PopUpTypeInterventionAction();	
	  getRequest().setupAddParameter("className", "uom");
	  getRequest().setupAddParameter("divName", "uomDetails");
	} 
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#type()}.
	 */
	@Test
	public void testType() {
		assertEquals("lookUp", popUpInterventionAction.type());
		assertEquals("uom",popUpInterventionAction.getLookupSearchCriteria().getType());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpList() {
		 getRequest().setupAddParameter("code", "");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "uomDetails");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
		 assertEquals("Please enter at least one search criteria", getRequest().getAttribute("failureMessage"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpListDisplayTag()}.
	 */
	@Test
	public void testDisplayLookUpListDisplayTag() {
		 getRequest().setupAddParameter("code", "");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "uomDetails");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("lookUp",popUpInterventionAction.displayLookUpListDisplayTag());
		 assertEquals("Please enter at least one search criteria", getRequest().getAttribute("failureMessage"));
	}

}
