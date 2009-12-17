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
public class TrialDescriptionActionTest extends AbstractPaActionTest {

	TrialDescriptionAction trialDescriptionAction;
	
	@Before 
	public void setUp() throws PAException {
		trialDescriptionAction =  new TrialDescriptionAction();	
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
		 
	} 

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialDescriptionAction#query()}.
	 */
	@Test
	public void testQuery() {
		assertEquals("edit", trialDescriptionAction.query());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialDescriptionAction#update()}.
	 */
	@Test
	public void testUpdate() {
		assertEquals("edit", trialDescriptionAction.update());
	}

}
