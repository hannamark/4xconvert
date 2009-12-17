/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialArmsActionTest extends AbstractPaActionTest {

	TrialArmsAction trialsArmsAction;
	
	@Before 
	public void setUp() throws PAException {
		trialsArmsAction =  new TrialArmsAction();	
		StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
		dto.setStudyProtocolId(1L);
		 getSession().setAttribute(Constants.TRIAL_SUMMARY, dto);
		 trialsArmsAction.prepare();
	} 

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#execute()}.
	 */
	@Test
	public void testExecute() throws PAException {
		assertEquals("list", trialsArmsAction.execute());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#observational()}.
	 */
	@Test
	public void testObservational()  throws PAException {
		assertEquals("list", trialsArmsAction.observational());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#create()}.
	 */
	@Test
	public void testCreate() throws PAException {
		assertEquals("edit", trialsArmsAction.create());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#createGroup()}.
	 */
	@Test
	public void testCreateGroup() throws PAException {
		assertEquals("edit", trialsArmsAction.createGroup());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#edit()}.
	 */
	@Test
	public void testEdit() throws PAException {
		trialsArmsAction.setSelectedArmIdentifier(null);
		assertEquals("edit", trialsArmsAction.edit());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#editGroup()}.
	 */
	@Test
	public void testEditGroup() throws PAException {
		trialsArmsAction.setSelectedArmIdentifier(null);
		assertEquals("edit", trialsArmsAction.editGroup());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#delete()}.
	 */
	@Test
	public void testDelete() throws PAException {
		trialsArmsAction.delete();
		 assertEquals("Record Deleted", getRequest().getAttribute("successMessage"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#add()}.
	 */
	@Test
	public void testAdd() throws PAException {
		trialsArmsAction.setArmType("Experimental");
		trialsArmsAction.setArmDescription("test");
		trialsArmsAction.setCurrentAction("listArm");
		trialsArmsAction.add();
		assertEquals("Record Created", getRequest().getAttribute("successMessage"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#update()}.
	 */
	@Test
	public void testUpdate() throws PAException {
		trialsArmsAction.setArmDescription("test");
		trialsArmsAction.setCurrentAction("listArm");
		assertEquals("edit", trialsArmsAction.update());
	}

}
