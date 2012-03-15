/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.service.MockPAOrganizationService;
import gov.nih.nci.registry.service.MockPAPersonServiceRemote;
import gov.nih.nci.registry.util.TrialUtil;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.Action;

/**
 * @author Vrushali
 *
 */
public class SearchTrialActionTest extends AbstractRegWebTest {
    private SearchTrialAction action;

    /**
     * Initialization.
     * @throws Exception in case of error
     */
    @Before
    public void setup() throws Exception {
        Organization org = new Organization();
        org.setIdentifier("1");
        org.setCity("city");
        org.setCountryName("countryName");
        org.setName("name");
        org.setPostalCode("postalCode");

        Person person = new Person();
        person.setIdentifier("1");
        person.setFirstName("firstName");
        person.setLastName("lastName");

        PAContactDTO contactDTO = new PAContactDTO();
        contactDTO.setFullName("Contact User");

        CorrelationUtilsRemote correlationUtils = mock(CorrelationUtilsRemote.class);
        when(correlationUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);
        when(correlationUtils.getPAPersonByIi(any(Ii.class))).thenReturn(person);
        when(correlationUtils.getContactByPAOrganizationalContactId(any(Long.class))).thenReturn(contactDTO);

        action = new SearchTrialAction();
        action.prepare();
        TrialUtil trialUtils = new TrialUtil();
        trialUtils.setCorrelationUtils(correlationUtils);
        action.setTrialUtils(trialUtils);

        MockHttpSession session = new MockHttpSession();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("firstName");
        request.setSession(session);

        ServletActionContext.setRequest(request);

        HttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

        CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test
    public void testRecordsProperty(){
        assertNull(action.getRecords());
    }

    @Test
    public void testShowCriteria(){
        assertEquals("criteria", action.showCriteria());
    }

    @Test
    public void testQueryErr(){
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("abc");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("avs");
        action.setCriteria(criteria );
        assertEquals("error", action.query());
    }

    @Test
    public void testQuery(){
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setPhaseCode("");
        criteria.setPrimaryPurposeCode("");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryWithException() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("ThrowException");
        criteria.setOrganizationType("");
        action.setCriteria(criteria);
        assertEquals("error", action.query());
    }

    @Test
    public void testQueryNCIType() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("a");
        criteria.setIdentifierType("NCI");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setOrganizationId("");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryNCTType() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("nct");
        criteria.setIdentifierType("NCT");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("organizationType");
        criteria.setOrganizationId("1");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryLeadOrgType() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("lead");
        criteria.setIdentifierType("Lead Organization");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("organizationType");
        criteria.setOrganizationId("1");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryPI() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setPrincipalInvestigatorId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testExecute() {
        action.setTrialAction("");
        assertEquals("success", action.execute());

        action.setTrialAction("view");
        assertEquals("success", action.execute());

        ServletActionContext.getRequest().getSession().setAttribute("protocolId", "1");
        action.setTrialAction("submit");
        assertEquals("view", action.execute());

        action.setTrialAction("amend");
        assertEquals("view", action.execute());

        action.setTrialAction("update");
        assertEquals("view", action.execute());

    }

    @Test
    public void testView() {
        action.setStudyProtocolId(1L);
        assertEquals("view", action.view());
    }

    @Test
    public void testViewDoc() throws Exception {
        action.setIdentifier(1L);
        assertEquals(Action.NONE, action.viewDoc());
    }

    @Test
    public void testGetMyPartiallySavedTrial() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setOfficialTitle("officialTitle");
        action.setCriteria(criteria);
        assertEquals("success", action.getMyPartiallySavedTrial());

        criteria = new SearchProtocolCriteria();
        criteria.setOfficialTitle("ThrowException");
        action.setCriteria(criteria);
        assertEquals("success", action.getMyPartiallySavedTrial());
        assertNotNull(action.getActionErrors());
    }

    @Test
    public void testPartiallySubmittedView() {
        assertEquals("error", action.partiallySubmittedView());

        action.setStudyProtocolId(1L);
        assertEquals("partialView", action.partiallySubmittedView());
    }

    @Test
    public void testSendXmlEmail() {
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setNciIdentifier("NCI-2009-00001");
        ServletActionContext.getRequest().getSession().setAttribute("studySearchCriteria", queryCriteria);
        assertEquals("success", action.sendXml());
    }

    @Test
    public void testMyTrialsOnly() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setMyTrialsOnly(true);
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        action.setCriteria(criteria);

        assertEquals("success", action.query());
        assertTrue(action.getRecords().size() >= 1);
    }
    
    @Test
    public void testGetOrganizationsAssociatedWithStudyProtocol() throws PAException, InterruptedException {
        SearchTrialAction.initializeCache(3);
        
        // note the instance equality operator. Do not use equals here.
        assertTrue(MockPAOrganizationService.leadPaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        assertTrue(MockPAOrganizationService.sitePaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Participating Site"));    
        
        // Point the mock at different collections.
        MockPAOrganizationService.leadPaOrganizationDTOs = new ArrayList<PaOrganizationDTO>();
        MockPAOrganizationService.sitePaOrganizationDTOs = new ArrayList<PaOrganizationDTO>();
        
        // now should be false because previous cached objects will be returned instead of above ones.
        assertFalse(MockPAOrganizationService.leadPaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        assertFalse(MockPAOrganizationService.sitePaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Participating Site"));
        
        Thread.sleep(7000);
        
        // cache has expired; so now we should get out new fresh objects back.
        assertTrue(MockPAOrganizationService.leadPaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        assertTrue(MockPAOrganizationService.sitePaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Participating Site"));           
    }
    
    @Test
    public void testGetAllPrincipalInvestigators() throws PAException, InterruptedException {
        SearchTrialAction.initializeCache(3);
        
        // note the instance equality operator. Do not use equals here.
        assertTrue(MockPAPersonServiceRemote.investigators == action
                .getAllPrincipalInvestigators());         
        
        // Point the mock at different collections.
        MockPAPersonServiceRemote.investigators = new ArrayList<PaPersonDTO>();        
        
        // now should be false because previous cached objects will be returned instead of above ones.
        assertFalse(MockPAPersonServiceRemote.investigators == action
                .getAllPrincipalInvestigators());        
        
        Thread.sleep(7000);
        
        // cache has expired; so now we should get out new fresh objects back.
        assertTrue(MockPAPersonServiceRemote.investigators == action
                .getAllPrincipalInvestigators());         
                 
    }
    
}
