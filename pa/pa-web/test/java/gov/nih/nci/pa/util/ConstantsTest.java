/**
 * 
 */
package gov.nih.nci.pa.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;



/**
 * @author asharma
 *
 */
public class ConstantsTest {
	
	Constants constants = new Constants();
	@Test
	public void testContants() {
		assertEquals("User Name is required field",constants.USERNAME_REQ_ERROR);
		assertEquals("trialSummary",constants.TRIAL_SUMMARY);
		assertEquals("studyProtocolIi",constants.STUDY_PROTOCOL_II);
		assertEquals("successMessage",constants.SUCCESS_MESSAGE);
		assertEquals("Password is required field",constants.PASSWORD_REQ_ERROR);
		assertEquals("failureMessage",constants.FAILURE_MESSAGE);
		assertEquals("Record Deleted",constants.DELETE_MESSAGE);
		assertEquals("fundingMechanism",constants.FUNDING_MECHANISM);
		assertEquals("nihInstitute",constants.NIH_INSTITUTE);
	}

}
