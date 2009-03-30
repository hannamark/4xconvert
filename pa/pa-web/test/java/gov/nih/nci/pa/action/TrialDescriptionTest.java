/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertFalse;
import gov.nih.nci.pa.dto.TrialDescriptionWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialDescriptionTest extends AbstractPaActionTest {
	private static TrialDescriptionAction trialDescription ;
	
		
	 @Before
	 public void setUp(){
		 trialDescription = new TrialDescriptionAction();
		 getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
	 }
	
	 @Test
	    public void queryTest() throws Exception {
		 trialDescription.query();
		 assertFalse(trialDescription.hasActionErrors());
		}
	    
	    @Test
	    public void updateTest() throws Exception {
	    	TrialDescriptionWebDTO trialDTO = new TrialDescriptionWebDTO();
	    	trialDTO.setTrialBriefSummary("xxxx");
	    	trialDescription.update();
	    	assertFalse(trialDescription.hasActionErrors());
	    }
	

}
