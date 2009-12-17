/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.MockPrincipal;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;


/**
 * @author asharma
 *
 */
public class StudyProtocolQueryActionTest extends AbstractPaActionTest {

	StudyProtocolQueryAction spqAction;
	 StudyProtocolQueryCriteria criteria;
	
	@Before 
	public void setUp() throws PAException {
	  spqAction =  new StudyProtocolQueryAction();	
	  criteria = new StudyProtocolQueryCriteria();
	  criteria.setNciIdentifier("NCI-2009-00001");
	  getRequest().setUserInRole(Constants.ABSTRACTOR, true);
	  getRequest().setUserPrincipal(new MockPrincipal("abstractor"));
	  getSession().setAttribute(Constants.USER_ROLE, Constants.ABSTRACTOR);
	 
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#execute()}.
	 */
	@Test
	public void testExecute() throws PAException {
		assertEquals("success", spqAction.execute());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#showCriteria()}.
	 */
	@Test
	public void testShowCriteria() throws PAException {
		assertEquals("criteriaProtected", spqAction.showCriteria());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#query()}.
	 */
	@Test
	public void testQuery() throws PAException {
		assertEquals("success", spqAction.query());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#view()}.
	 */
	@Test
	public void testView() throws PAException {
		spqAction.setStudyProtocolId(1L);
		assertEquals("view", spqAction.view());
		
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#viewTSR()}.
	 */
	@Test
	public void testViewTSR() throws PAException {
		getRequest().setupAddParameter("studyProtocolId", "1");
		assertEquals("none", spqAction.viewTSR());
		
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#viewTSRWord()}.
	 */
	@Test
	public void testViewTSRWord() throws PAException {
		getRequest().setupAddParameter("studyProtocolId", "1");
		assertEquals("success", spqAction.viewTSRWord());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#checkout()}.
	 */
	@Test
	public void testCheckout() throws PAException {
		spqAction.setStudyProtocolId(1L);
		spqAction.setCheckoutStatus(false);
		assertEquals("view", spqAction.checkout());
	}
   

}