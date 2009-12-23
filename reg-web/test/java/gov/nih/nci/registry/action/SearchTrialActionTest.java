/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.servlet.http.HttpServletResponse;

import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.CtrpHibernateHelper;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.util.TestHibernateHelper;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class SearchTrialActionTest extends AbstractRegWebTest{
    private SearchTrialAction action;
    private static CtrpHibernateHelper testHelper = new TestHibernateHelper();
    @Before 
    public void setup(){
        HibernateUtil.testHelper = testHelper;
        Session session = HibernateUtil.getCurrentSession();
        session.clear();
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
        criteria.setMyTrialsOnly("");
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
        criteria.setMyTrialsOnly("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        action.setCriteria(criteria );
        assertEquals("success",action.query());
    }
    @Test
    public void testQueryWithException(){
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setMyTrialsOnly("");
        criteria.setOfficialTitle("ThrowException");
        criteria.setOrganizationType("");
        action.setCriteria(criteria );
        assertEquals("error",action.query());
    }
    @Test
    public void testQueryNCIType(){
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("a");
        criteria.setIdentifierType("NCI");
        criteria.setMyTrialsOnly("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setOrganizationId("");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria );
        assertEquals("success",action.query());
    }
    @Test
    public void testQueryNCTType(){
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("nct");
        criteria.setIdentifierType("NCT");
        criteria.setMyTrialsOnly("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("organizationType");
        criteria.setOrganizationId("1");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria );
        assertEquals("success",action.query());
    }
    @Test
    public void testQueryLeadOrgType(){
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("lead");
        criteria.setIdentifierType("Lead Organization");
        criteria.setMyTrialsOnly("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("organizationType");
        criteria.setOrganizationId("1");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria );
        assertEquals("success",action.query());
    }
    @Test
    public void testQueryPI(){
        action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setPrincipalInvestigatorId("1");
        action.setCriteria(criteria );
        assertEquals("success",action.query());
    }
    @Test
    public void testExecute(){
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
        assertEquals("show_Disclaimer_Page",action.execute());
        action = new SearchTrialAction();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("show_Disclaimer_Page",action.execute());
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setSession(session);
        request.setupAddParameter("trialAction", "submit");
        ServletActionContext.setRequest(request);
        primeData();
        assertEquals("view",action.execute());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setupAddParameter("trialAction", "amend");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("view",action.execute());
        
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setupAddParameter("trialAction", "");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success",action.execute());
        
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("protocolId", "1");
        session.setAttribute("disclaimer", "accept");
        request.setupAddParameter("trialAction", "view");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success",action.execute());
    }
    @Test
    public void testView(){
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("studyProtocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("view",action.view());
    }
    @Test
    public void testViewUsercreated (){
        action = new SearchTrialAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setupAddParameter("studyProtocolId", "1");
        request.setupAddParameter("usercreated", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("view",action.view());
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
        assertEquals("error", action.viewDoc());
    }
}
