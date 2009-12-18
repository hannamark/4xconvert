/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.registry.util.Constants;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class ManageIndIdeActionTest extends AbstractRegWebTest {
    private ManageIndIdeAction indAction;
    @Test
    public void testAddIndIdeToExitingList(){
        indAction = new ManageIndIdeAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("indIde", "IND");
        request.setupAddParameter("number", "AG");
        request.setupAddParameter("grantor", "CDER");
        request.setupAddParameter("holdertype", "12345");
        request.setupAddParameter("programcode", "programcode");
        request.setupAddParameter("expandedaccess", "arg1");
        request.setupAddParameter("expandedaccesstype", "arg1");
        sess.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        sess.setAttribute(Constants.INDIDE_ADD_LIST, getIndDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_ideind",indAction.addIdeIndIndicator());
        assertEquals("display_ideindadd",indAction.addIdeIndIndicatorForUpdate());
    }
    @Test
    public void testAddIndIde(){
        indAction = new ManageIndIdeAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("indIde", "IND");
        request.setupAddParameter("number", "AG");
        request.setupAddParameter("grantor", "CDER");
        request.setupAddParameter("holdertype", "12345");
        request.setupAddParameter("programcode", "programcode");
        request.setupAddParameter("expandedaccess", "arg1");
        request.setupAddParameter("expandedaccesstype", "arg1");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_ideind",indAction.addIdeIndIndicator());
        assertEquals("display_ideindadd" ,indAction.addIdeIndIndicatorForUpdate());
    }
    @Test
    public void testAddIndIdeSomeEmpty(){
        indAction = new ManageIndIdeAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("indIde", "undefined");
        request.setupAddParameter("number", "");
        request.setupAddParameter("grantor", "");
        request.setupAddParameter("holdertype", "");
        request.setupAddParameter("programcode", "");
        request.setupAddParameter("expandedaccess", "No");
        request.setupAddParameter("expandedaccesstype", "");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("success",indAction.addIdeIndIndicator());
        assertEquals("success",indAction.addIdeIndIndicatorForUpdate());
    }
    @Test
    public void testAddIndIdeAllEmpty(){
        indAction = new ManageIndIdeAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("indIde", "");
        request.setupAddParameter("number", "");
        request.setupAddParameter("grantor", "");
        request.setupAddParameter("holdertype", "");
        request.setupAddParameter("programcode", "");
        request.setupAddParameter("expandedaccess", "");
        request.setupAddParameter("expandedaccesstype", "");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_ideind",indAction.addIdeIndIndicator());
        assertEquals("display_ideindadd" ,indAction.addIdeIndIndicatorForUpdate());
    }
    @Test
    public void testDeleteIndIde(){
        indAction = new ManageIndIdeAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("uuid", "1");
        sess.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        sess.setAttribute(Constants.INDIDE_ADD_LIST, getIndDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_ideind",indAction.deleteIndIde());
        assertEquals("display_ideindadd",indAction.deleteIndIdeForUpdate());
    }
    @Test
    public void testDeleteIndIdeNotInList(){
        indAction = new ManageIndIdeAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("uuid", "2");
        sess.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        sess.setAttribute(Constants.INDIDE_ADD_LIST, getIndDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_ideind",indAction.deleteIndIde());
        assertEquals("display_ideindadd",indAction.deleteIndIdeForUpdate());
    }
   @Test
   public void testPropertyStudyProtocolId() {
       indAction = new ManageIndIdeAction();
       assertNull(indAction.getStudyProtocolId());
       indAction.setStudyProtocolId("studyProtocolId");
       assertNotNull(indAction.getStudyProtocolId());
   }
}
