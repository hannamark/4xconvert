/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialIndideActionTest extends AbstractPaActionTest {

	TrialIndideAction trialIndideAction;
	
	@Before 
	public void setUp() throws PAException {
		trialIndideAction =  new TrialIndideAction();	
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
		 
	} 

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#displayJs()}.
	 */
	@Test
	public void testDisplayJs() {
		assertEquals("success", trialIndideAction.displayJs());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#query()}.
	 */
	@Test
	public void testQuery() {
		 assertEquals("query",trialIndideAction.query());
		 assertEquals("No IND/IDE records exist on the trial", getRequest().getAttribute("successMessage"));
		
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#create()}.
	 */
	@Test
	public void testCreate() {
		 assertEquals("add",trialIndideAction.create());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#update()}.
	 */
	@Test
	public void testUpdate() {
		assertEquals("edit",trialIndideAction.update());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#delete()}.
	 */
	@Test
	public void testDelete() {
		trialIndideAction.setCbValue(1L);
		assertEquals("query",trialIndideAction.delete());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#edit()}.
	 */
	@Test
	public void testEdit() {
		trialIndideAction.setCbValue(1L);
		assertEquals("edit",trialIndideAction.edit());
	}

}
