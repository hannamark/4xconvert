/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.OSDesignDetailsWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class ObservationalStudyDesignActionTest extends AbstractPaActionTest {
	
	ObservationalStudyDesignAction observationalStudy;
	
	@Before 
	public void setUp() throws PAException {
	  observationalStudy =  new ObservationalStudyDesignAction();	
	  getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
	 
	}
	

	/**
	 * Test method for {@link gov.nih.nci.pa.action.ObservationalStudyDesignAction#detailsQuery()}.
	 */
	@Test
	public void testDetailsQuery() {
		assertEquals("details", observationalStudy.detailsQuery());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.ObservationalStudyDesignAction#update()}.
	 */
	@Test
	public void testUpdate() {
		OSDesignDetailsWebDTO webDTO = new OSDesignDetailsWebDTO();
		webDTO.setBiospecimenDescription("Test");
		webDTO.setBiospecimenRetentionCode("Retained");
		webDTO.setMinimumTargetAccrualNumber("1");
		webDTO.setNumberOfGroups("1");
		webDTO.setStudyModelCode("Cohort");
		webDTO.setStudyModelOtherText("text");
		webDTO.setTimePerspectiveCode("Prospective");
		webDTO.setTimePerspectiveOtherText("timeText");
		observationalStudy.setWebDTO(webDTO);
		assertEquals("details", observationalStudy.update());
	}

}
