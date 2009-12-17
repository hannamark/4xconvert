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

// TODO: Auto-generated Javadoc
/**
 * The Class TrialFundingActionTest.
 * 
 * @author asharma
 */
public class TrialFundingActionTest extends AbstractPaActionTest {

	/** The trial funding action. */
	TrialFundingAction trialFundingAction;
	
	/**
	 * Sets the up.
	 * 
	 * @throws PAException the PA exception
	 */
	@Before 
	public void setUp() throws PAException {
		trialFundingAction =  new TrialFundingAction();	
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
		 
	} 


	/**
	 * Test display js.
	 */
	@Test
	public void testDisplayJs() {
		assertEquals("success", trialFundingAction.displayJs());
	}

	
	/**
	 * Test query.
	 */
	@Test
	public void testQuery() {
		 assertEquals("query",trialFundingAction.query());
		 		
	}

	
	/**
	 * Test create.
	 */
	@Test
	public void testCreate() {
		 assertEquals("error",trialFundingAction.create());
	}

	
	/**
	 * Test update.
	 */
	@Test
	public void testUpdate() {
		assertEquals("error",trialFundingAction.update());
	}

	
	/**
	 * Test delete.
	 */
	@Test
	public void testDelete() {
	  assertEquals("delete",trialFundingAction.delete());
	}

	
	/**
	 * Test edit.
	 */
	@Test
	public void testEdit() {
		trialFundingAction.setCbValue(1L);
		assertEquals("success",trialFundingAction.edit());
	}

}
