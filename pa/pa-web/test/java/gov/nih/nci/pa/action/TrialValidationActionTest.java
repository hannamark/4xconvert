/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialValidationActionTest extends AbstractPaActionTest {

	TrialValidationAction trialValidationAction;

	@Before
	public void setUp() throws PAException {
		trialValidationAction =  new TrialValidationAction();
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#query()}.
	 */
	@Test
	public void testQuery() {
		assertEquals("edit", trialValidationAction.query());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#update()}.
	 */
	@Test
	public void testUpdate() {
		assertEquals("edit", trialValidationAction.update());
		GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
		gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.update());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPrimaryPurposeCode("Other");
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.update());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPrimaryPurposeCode("Other");
        gtdDTO.setPrimaryPurposeAdditionalQualifierCode("Other");
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.update());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#accept()}.
	 */
	@Test
	public void testAccept() {
		trialValidationAction.getGtdDTO().setSubmissionNumber(1);
		assertEquals("edit", trialValidationAction.accept());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#reject()}.
	 */
	@Test
	public void testReject() {
		assertEquals("edit", trialValidationAction.reject());
		GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        gtdDTO.setCommentText("rejectcommentText");
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("edit", trialValidationAction.reject());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialValidationAction#rejectReason()}.
	 */
	@Test
	public void testRejectReason() {
	    GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCommentText("rejectcommentText");
        trialValidationAction.setGtdDTO(gtdDTO);
        getRequest().getSession().setAttribute("submissionNumber", 1);
        assertEquals("edit", trialValidationAction.rejectReason());
        gtdDTO = new GeneralTrialDesignWebDTO();
        trialValidationAction.setGtdDTO(gtdDTO);
        assertEquals("rejectReason", trialValidationAction.rejectReason());

	}

}
