/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class ManageGrantsActionTest extends AbstractRegWebTest {
    private ManageGrantsAction grantAction;
    @Test
    public void testAddGrantToExitingList(){
        grantAction = new ManageGrantsAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("fundingMechanismCode", "B09");
        request.setupAddParameter("nihInstitutionCode", "AG");
        request.setupAddParameter("nciDivisionProgramCode", "CCR");
        request.setupAddParameter("serialNumber", "12345");
        sess.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_grants",grantAction.addGrant());
    }
    @Test
    public void testAddGrant(){
        grantAction = new ManageGrantsAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("fundingMechanismCode", "B09");
        request.setupAddParameter("nihInstitutionCode", "AG");
        request.setupAddParameter("nciDivisionProgramCode", "CCR");
        request.setupAddParameter("serialNumber", "12345");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_grants",grantAction.addGrant());
    }
    @Test
    public void testDeleteGrant(){
        grantAction = new ManageGrantsAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        request.setupAddParameter("uuid", "1");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_grants",grantAction.deleteGrant());
    }
    @Test
    public void testDeleteGrantNotInList(){
        grantAction = new ManageGrantsAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        request.setupAddParameter("uuid", "2");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_grants",grantAction.deleteGrant());
    }
    @Test
    public void testShowWaitDialog(){
        grantAction = new ManageGrantsAction();
        assertEquals("show_ok_create", grantAction.showWaitDialog());
    }
   
}
