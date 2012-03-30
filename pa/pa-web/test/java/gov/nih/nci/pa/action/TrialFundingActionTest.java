/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.apache.struts2.ServletActionContext;
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
		 assertEquals("query", trialFundingAction.query());
	}

	/**
	 * Test create.
	 */
	@Test
	public void testCreate() {
	    assertEquals(trialFundingAction.create(), "error");
	    trialFundingAction.clearErrorsAndMessages();

	    TrialFundingWebDTO dto = new TrialFundingWebDTO();
	    dto.setFundingMechanismCode("B09");
	    dto.setNihInstitutionCode("AA");
	    dto.setNciDivisionProgramCode("CCR");
	    dto.setSerialNumber("00001");

	    trialFundingAction.setTrialFundingWebDTO(dto);
	    assertEquals(trialFundingAction.create(), "query");
	}

	/**
	 * Test update.
	 */
	@Test
	public void testUpdate() {
	    assertEquals("error",trialFundingAction.update());
	    trialFundingAction.clearErrorsAndMessages();

	    TrialFundingWebDTO dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("B09");
        dto.setNihInstitutionCode("AA");
        dto.setNciDivisionProgramCode("CCR");
        dto.setSerialNumber("00001");

        trialFundingAction.setTrialFundingWebDTO(dto);
	    trialFundingAction.setCbValue(1L);
	    assertEquals(trialFundingAction.update(), "query");
	}


	/**
	 * Test delete.
	 */
	@Test
	public void testDelete() {
	    assertEquals("query",trialFundingAction.delete());
	    assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.FAILURE_MESSAGE)!=null);	    
	    trialFundingAction.clearErrorsAndMessages();

	    TrialFundingWebDTO dto = new TrialFundingWebDTO();
        dto.setInactiveCommentText("Deleting Funding");

        trialFundingAction.setTrialFundingWebDTO(dto);
        trialFundingAction.setObjectsToDelete(new String[] {"1"});
        assertEquals(trialFundingAction.delete(), "query");
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
