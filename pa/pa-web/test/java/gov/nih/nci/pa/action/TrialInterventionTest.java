package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

public class TrialInterventionTest extends AbstractPaActionTest {

	TrialInterventionsAction  trialIntervention;
	@Before
    public void prepare() throws Exception {
		trialIntervention = new TrialInterventionsAction();
		trialIntervention.prepare();		      
     }
	/*@Test(expected=Exception.class)
	public void testAdd() throws PAException{
		trialIntervention.add();
	}

	/*@Test(expected=Exception.class)
	public void testUpdate() throws PAException {
		trialIntervention.update();
	}*/

	@Test
	public void testDelete() throws PAException{
		trialIntervention.setSelectedRowIdentifier("1");
		assertEquals(trialIntervention.delete(),"list");
	}

	
}
