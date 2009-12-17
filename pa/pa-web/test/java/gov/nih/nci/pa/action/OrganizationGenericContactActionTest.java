/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class OrganizationGenericContactActionTest  extends AbstractPaActionTest {

	OrganizationGenericContactAction orgGenericContact;
	
	@Before 
	public void setUp() throws PAException {
	  orgGenericContact =  new OrganizationGenericContactAction();	
	  orgGenericContact.prepare();
	  getRequest().setupAddParameter("orgGenericContactIdentifier", "1");
	  getRequest().setupAddParameter("type", "type");
	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.OrganizationGenericContactAction#lookupByTitle()}.
	 */
	@Test
	public void testLookupByTitle() {
		assertEquals("success",orgGenericContact.lookupByTitle());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.OrganizationGenericContactAction#displayTitleList()}.
	 */
	@Test
	public void testDisplayTitleList() {
		orgGenericContact.displayTitleList();
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.OrganizationGenericContactAction#create()}.
	 */
	@Test
	public void testCreate() {
		assertEquals("create_org_contact_response", orgGenericContact.create());
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}

}
