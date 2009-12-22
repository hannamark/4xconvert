/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialHelperTest  extends AbstractPaActionTest {

	TrialHelper trialHelper;
	
	 @Before
	    public void setup() throws Exception {
		 trialHelper = new TrialHelper();
	     getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
	     }
	 
	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialHelper#getTrialDTO(gov.nih.nci.coppa.iso.Ii, java.lang.String)}.
	 */
    @Test(expected=Exception.class)
	public void testGetTrialDTO() throws PAException, NullifiedRoleException {
		assertNotNull(trialHelper.getTrialDTO(IiConverter.convertToIi(1L), "Abstraction"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialHelper#saveTrial(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO, java.lang.String)}.
	 */
    @Test(expected=Exception.class)
	public void testSaveTrial() throws NullifiedEntityException, NullifiedRoleException, PAException {
		GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
		gtdDTO.setOfficialTitle("Test");
		gtdDTO.setAcronym("test");
		gtdDTO.setKeywordText("key");
		gtdDTO.setLeadOrganizationIdentifier("1");
		gtdDTO.setLocalProtocolIdentifier("1");
		gtdDTO.setNctIdentifier("test");
		
		trialHelper.saveTrial(IiConverter.convertToIi(1L),gtdDTO ,"Abstraction");
		
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialHelper#createOrUpdateCentralContact(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO)}.
	 */
	@Test
	public void testCreateOrUpdateCentralContact() throws PAException, NullifiedEntityException, NullifiedRoleException {
		GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
		gtdDTO.setOfficialTitle("Test");
		gtdDTO.setAcronym("test");
		gtdDTO.setKeywordText("key");
		gtdDTO.setLeadOrganizationIdentifier("1");
		gtdDTO.setLocalProtocolIdentifier("1");
		gtdDTO.setNctIdentifier("test");
		trialHelper.createOrUpdateCentralContact(IiConverter.convertToIi(1L),gtdDTO);
		
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialHelper#createStudyContactObj(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.pa.iso.dto.StudyContactDTO, gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO)}.
	 */
	@Test
	public void testCreateStudyContactObj() throws PAException, NullifiedEntityException, NullifiedRoleException {
		GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
		gtdDTO.setOfficialTitle("Test");
		gtdDTO.setAcronym("test");
		gtdDTO.setKeywordText("key");
		gtdDTO.setLeadOrganizationIdentifier("1");
		gtdDTO.setLocalProtocolIdentifier("1");
		gtdDTO.setNctIdentifier("test");
		gtdDTO.setCentralContactIdentifier("2");
		gtdDTO.setCentralContactPhone("123456789");
		assertNotNull(trialHelper.createStudyContactObj(IiConverter.convertToIi("1"), new StudyContactDTO(), gtdDTO));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialHelper#createSponorContact(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO)}.
	 */
	@Test(expected=Exception.class)
	public void testCreateSponorContact() throws PAException {
		GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
		gtdDTO.setOfficialTitle("Test");
		gtdDTO.setAcronym("test");
		gtdDTO.setKeywordText("key");
		gtdDTO.setLeadOrganizationIdentifier("1");
		gtdDTO.setLocalProtocolIdentifier("1");
		gtdDTO.setNctIdentifier("test");
		gtdDTO.setCentralContactIdentifier("2");
		gtdDTO.setCentralContactPhone("123456789");
		gtdDTO.setResponsiblePartyType("sponsor");
		gtdDTO.setSponsorIdentifier("1");
		gtdDTO.setCentralContactEmail("a@a.com");
		gtdDTO.setContactPhone("123456789");
		gtdDTO.setResponsiblePersonName("test");
		gtdDTO.setResponsiblePersonIdentifier("1");
		trialHelper.createSponorContact(IiConverter.convertToIi("1"),gtdDTO);
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialHelper#setMenuLinks(gov.nih.nci.pa.enums.DocumentWorkflowStatusCode)}.
	 */
	@Test
	public void testSetMenuLinks() {
		assertEquals(DocumentWorkflowStatusCode.ACCEPTED.getCode(), trialHelper.setMenuLinks(DocumentWorkflowStatusCode.ABSTRACTED));
	}

}
