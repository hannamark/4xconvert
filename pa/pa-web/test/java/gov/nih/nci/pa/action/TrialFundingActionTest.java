/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

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
	    dto.setFundingPercent("33.3");

	    trialFundingAction.setTrialFundingWebDTO(dto);
	    assertEquals(trialFundingAction.create(), "query");
	}

	@Test
	public void testFieldErrors() {
        TrialFundingWebDTO dto = new TrialFundingWebDTO();
        trialFundingAction.setTrialFundingWebDTO(dto);
        assertEquals(trialFundingAction.create(), "error");
        Map<String, List<String>> fes = trialFundingAction.getFieldErrors();
        assertEquals("error.trialFunding.funding.mechanism", fes.get("trialFundingWebDTO.fundingMechanismCode").get(0));
        assertEquals("error.trialFunding.institution.code", fes.get("trialFundingWebDTO.nihInstitutionCode").get(0));
        assertEquals("error.studyProtocol.monitorCode", fes.get("trialFundingWebDTO.nciDivisionProgramCode").get(0));
        assertEquals("error.trialFunding.serial.number", fes.get("trialFundingWebDTO.serialNumber").get(0));
        assertNull(fes.get("trialFundingWebDTO.fundingPercent"));
        trialFundingAction.clearErrorsAndMessages();
        dto.setSerialNumber("abc");
        dto.setFundingPercent("abc");
        assertEquals(trialFundingAction.create(), "error");
        fes = trialFundingAction.getFieldErrors();
        assertEquals("error.numeric", fes.get("trialFundingWebDTO.serialNumber").get(0));
        assertEquals("error.trialFunding.fundingPercent", fes.get("trialFundingWebDTO.fundingPercent").get(0));
        trialFundingAction.clearErrorsAndMessages();
        dto.setFundingPercent("101");
        assertEquals(trialFundingAction.create(), "error");
        fes = trialFundingAction.getFieldErrors();
        assertEquals("error.trialFunding.fundingPercent", fes.get("trialFundingWebDTO.fundingPercent").get(0));
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
        dto.setFundingPercent("33.3");

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

	@Test
	public void testUpdateGrant() throws Exception {
	    assertEquals(Action.NONE, trialFundingAction.updateNciGrant());
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
