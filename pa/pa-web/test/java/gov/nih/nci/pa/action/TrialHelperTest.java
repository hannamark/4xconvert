/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.service.MockCorrelationUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialHelperTest extends AbstractPaActionTest {

    TrialHelper trialHelper;

    @Before
    public void setup() throws Exception {
        PAServiceUtils svcUtils = mock(PAServiceUtils.class);
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("NCT-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.CTEP_IDENTIFIER_TYPE))).thenReturn("CTEP-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.DCP_IDENTIFIER_TYPE))).thenReturn("DCP-1");
        when(svcUtils.getStudyIdentifier(any(Ii.class), eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("NCI-1");

        trialHelper = new TrialHelper();
        trialHelper.setCorrelationUtils(new MockCorrelationUtils());
        trialHelper.setPaServiceUtils(svcUtils);
        trialHelper.setOrgService(orgSvc);
        trialHelper.setLookupTableService(lookupSvc);

        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
    }

    @Test
    public void testGetTrialDTO() throws Exception {
        assertNotNull(trialHelper.getTrialDTO(IiConverter.convertToIi(1L), "Abstraction"));
    }

    @Test
    public void testShouldRssOwnTrialYes() throws PAException {
        Organization rssOrg = new Organization();
        rssOrg.setName("American College of Surgeons Oncology Trials Group");
        when(orgSvc.getOrganizationByFunctionRole((Ii)anyObject(), (Cd)anyObject())).thenReturn(rssOrg);
        assertTrue(trialHelper.shouldRssOwnTrial(spId));
    }

    @Test
    public void testShouldRssOwnTrialNo() throws PAException {
        Organization rssOrg = new Organization();
        rssOrg.setName("Non Rss Org.");
        when(orgSvc.getOrganizationByFunctionRole((Ii)anyObject(), (Cd)anyObject())).thenReturn(rssOrg);
        assertFalse(trialHelper.shouldRssOwnTrial(spId));
    }

    @Test
    public void testSaveTrial() throws Exception {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        gtdDTO.setSubmissionNumber(1);
        gtdDTO.setProprietarytrialindicator("false");
        trialHelper.saveTrial(IiConverter.convertToIi(1L),gtdDTO ,"Abstraction");

    }

    @Test
    public void testCreateOrUpdateCentralContact() throws Exception {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setOfficialTitle("Test");
        gtdDTO.setAcronym("test");
        gtdDTO.setKeywordText("key");
        gtdDTO.setLeadOrganizationIdentifier("1");
        gtdDTO.setLocalProtocolIdentifier("1");
        gtdDTO.setNctIdentifier("test");
        trialHelper.createOrUpdateCentralContact(IiConverter.convertToIi(1L),gtdDTO);

    }

    @Test
    public void testCreateStudyContactObj() throws Exception {
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

    @Test(expected = Exception.class)
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

    @Test
    public void testSetMenuLinks() {
        assertEquals(DocumentWorkflowStatusCode.ACCEPTED.getCode(), trialHelper.setMenuLinks(DocumentWorkflowStatusCode.ABSTRACTED));
    }

}
