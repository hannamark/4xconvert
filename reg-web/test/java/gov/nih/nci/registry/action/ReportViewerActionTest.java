/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerBeanLocal;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.service.MockLookUpTableService;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.test.util.MockPoServiceLocator;
//import gov.nih.nci.registry.util.SiteAdministrationCriteria;
import gov.nih.nci.registry.util.ReportViewerCriteria;

/**
 * @author vpoluri
 *
 */
public class ReportViewerActionTest extends AbstractRegWebTest {
    private ReportViewerAction action;

    @Before
    public void setup() throws PAException {
	CSMUserService.getInstance();
	CSMUserService.setInstance(new MockCSMUserService());
	action = new ReportViewerAction();
	setupProperties();
    }

    private void setupProperties() throws PAException {
	action.setLookUpTableService(new MockLookUpTableService());
	action.setMailManagerService(mock(MailManagerBeanLocal.class));
	MockRegistryUserService regUserSvc = new MockRegistryUserService();

	action.setRegistryUserService(regUserSvc);
	PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearch() throws PAException {
	// action = new ReportViewerAction();
	MockHttpServletRequest request = new MockHttpServletRequest();
	HttpSession sess = new MockHttpSession();
	request.setRemoteUser("firstName");
	request.setSession(sess);
	ServletActionContext.setRequest(request);
	assertEquals("viewResults", action.search());
	List<RegistryUser> lst = (List<RegistryUser>) sess.getAttribute("regUsersList");
	assertNotNull(lst);
	assertEquals(4, lst.size());

	List<RegistryUser> rlst = (List<RegistryUser>) sess.getAttribute("reportList");
	assertNotNull(rlst);
	assertEquals(2, rlst.size());

	try {
	    action.save();
	} catch (PAException e) {

	}
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSave() throws PAException {
	// action = new ReportViewerAction();
	MockHttpServletRequest request = new MockHttpServletRequest();
	HttpSession sess = new MockHttpSession();
	request.setRemoteUser("firstName");
	request.setSession(sess);
	ServletActionContext.setRequest(request);
	assertEquals("viewResults", action.search());
	List<RegistryUser> lst = (List<RegistryUser>) sess.getAttribute("regUsersList");
	assertNotNull(lst);
	assertEquals(2, lst.size());

	List<RegistryUser> rlst = (List<RegistryUser>) sess.getAttribute("reportList");
	assertNotNull(rlst);
	assertEquals(2, rlst.size());

	try {
	    
	    RegistryUser member = lst.get(0);
	    Long UPDATED_USERID = member.getId();
	    member.setEnableReports(true);
	    lst.set(0, member);
	    sess.setAttribute("regUsersList", lst);
	    
	    action.save();
	    
	    assertEquals("viewResults", action.search());
	    List<RegistryUser> updatedLst = (List<RegistryUser>) sess.getAttribute("regUsersList");
	    assertNotNull(updatedLst);
	    
	    for (RegistryUser registryUser : updatedLst) {
		if(registryUser.getId() == UPDATED_USERID){
		    assert(registryUser.getEnableReports());
		}
	    }
	    
	} catch (PAException e) {

	}
    }
    

    @Test
    public void testView() throws PAException {
	testSearch();
	assertEquals("viewResults", action.view());
	assertNotNull(action.getRegistryUsers());
	assertEquals(4, action.getRegistryUsers().size());
    }

    @Test
    public void testActionProperties() {
	// action = new ReportViewerAction();
	action.setCriteria(new ReportViewerCriteria());
	assertNotNull(action.getCriteria());
	assertNotNull(action.getAffiliatedOrgAdmins());
	action.setAffiliatedOrgAdmins(null);
	assertNull(action.getAffiliatedOrgAdmins());
	action.setRegistryUsers(new ArrayList<RegistryUser>());
	assertNotNull(action.getCriteria());
    }

}
