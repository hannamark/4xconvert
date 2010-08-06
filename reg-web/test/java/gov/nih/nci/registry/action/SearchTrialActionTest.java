/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class SearchTrialActionTest extends AbstractRegWebTest {
    private SearchTrialAction action;
    //private static CtrpHibernateHelper testHelper = new TestHibernateHelper();
    @Before
    public void setup(){
       /* HibernateUtil.testHelper = testHelper;
        Session session = HibernateUtil.getCurrentSession();
        session.clear();*/
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("firstName");
        ServletActionContext.setRequest(request);
        CSMUserService.getInstance();
        CSMUserService.setRegistryUserService(new MockCSMUserService());
    }

    @Test
    public void testRecordsProperty(){
        action = new SearchTrialAction();
        assertNull(action.getRecords());
    }
    @Test
    public void testCriteriaProperty(){
        action = new SearchTrialAction();
        assertNotNull(action.getCriteria());
        action.setCriteria(null);
        assertNull(action.getCriteria());
    }
    @Test
    public void testStudyProtocolIdProperty(){
        action = new SearchTrialAction();
        assertNull(action.getStudyProtocolId());
        action.setStudyProtocolId(1L);
        assertNotNull(action.getStudyProtocolId());
    }
    @Test
    public void testShowCriteria(){
        action = new SearchTrialAction();
        assertEquals("criteria",action.showCriteria());
    }
    @Test
    public void testQueryErr(){
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("abc");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("avs");
        action.setCriteria(criteria );
        assertEquals("error",action.query());
    }
    @Test
    public void testQuery(){
        action = new SearchTrialAction();
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
        action = new SearchTrialAction();
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
        action = new SearchTrialAction();
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
        action = new SearchTrialAction();
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
        action = new SearchTrialAction();
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
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setPrincipalInvestigatorId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testExecute() {
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        action = new SearchTrialAction();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "noaccept");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("show_Disclaimer_Page", action.execute());
        action = new SearchTrialAction();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("show_Disclaimer_Page", action.execute());
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setSession(session);
        request.setupAddParameter("trialAction", "submit");
        ServletActionContext.setRequest(request);
        // primeData();
        try {
            action.execute();
        } catch (Exception e) {

        }
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setupAddParameter("trialAction", "amend");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        try {
            action.execute();
        } catch (Exception e) {

        }
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setupAddParameter("trialAction", "");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        try {
            action.execute();
        } catch (Exception e) {

        }

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setupAddParameter("trialAction", "view");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        try {
            action.execute();
        } catch (Exception e) {

        }
    }

    @Test
    public void testView() {
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("studyProtocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        try {
            action.view();
        } catch (Exception e) {

        }
    }

    @Test
    public void testViewUsercreated() {
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("studyProtocolId", "1");
        request.setupAddParameter("usercreated", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        try {
            action.view();
        } catch (Exception e) {

        }
    }

    @Test
    public void testViewDoc() {
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("identifier", "1");
        session.setAttribute("spidfromviewresults", IiConverter.convertToIi("1"));
        request.setSession(session);
        ServletActionContext.setRequest(request);
        HttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        action.viewDoc();
    }

    @Test
    public void testGetMyPartiallySavedTrial() {
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setOfficialTitle("officialTitle");
        action.setCriteria(criteria);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("usercreated", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success", action.getMyPartiallySavedTrial());

        action = new SearchTrialAction();
        criteria = new SearchProtocolCriteria();
        criteria.setOfficialTitle("ThrowException");
        action.setCriteria(criteria);
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setupAddParameter("usercreated", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success", action.getMyPartiallySavedTrial());
        assertNotNull(action.getActionErrors());
    }

    @Test
    public void testPartiallySubmittedView() {
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("studyProtocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("partialView", action.partiallySubmittedView());

        action = new SearchTrialAction();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.partiallySubmittedView());
    }

    @Test
    public void testSendXmlEmail() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        action = new SearchTrialAction();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setNciIdentifier("NCI-2009-00001");
        session.setAttribute("studySearchCriteria", queryCriteria);
        request.setSession(session);
        request.setRemoteUser("firstName");
        ServletActionContext.setRequest(request);
        assertEquals("success", action.sendXml());
    }

    @Test
    public void testMyTrialsOnly() {
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setMyTrialsOnly(true);
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        action.setCriteria(criteria);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("userLastCreated");
        ServletActionContext.setRequest(request);
        assertEquals("success", action.query());
        assertTrue(action.getRecords().size() >= 1);
    }
}
