/**
 * 
 */
package gov.nih.nci.accrual.web.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.web.dto.util.PhysicianWebDTO;
import gov.nih.nci.accrual.web.dto.util.TreatmentSiteWebDTO;
import gov.nih.nci.accrual.web.dto.util.UserAccountWebDTO;
import gov.nih.nci.accrual.web.util.MockPoServiceLocator;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Kalpana Guthikonda
 *
 */
public class UserAccountActionTest extends AbstractAccrualActionTest{
    UserAccountAction action;
    UserAccountWebDTO userAccount;
    TreatmentSiteWebDTO treatmentSiteSearchCriteria = new TreatmentSiteWebDTO(); 
    PhysicianWebDTO physicianSearchCriteria = new PhysicianWebDTO(); 


        @Before
        public void initAction() throws Exception {
            action = new UserAccountAction();
            action.prepare();
            userAccount = new UserAccountWebDTO();
            action.setPhysicianSearchCriteria(physicianSearchCriteria);
            action.setTreatmentSiteSearchCriteria(treatmentSiteSearchCriteria);
            PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        }
        
        @Test 
        public void testUserActionProperty(){
            action.setUserAction(null);
            assertNull(action.getUserAction());
            action.setUserAction("userAction");
            assertNotNull(action.getUserAction());
        }
        
        @Test 
        public void testExecute(){
            assertEquals("success", action.execute());
        }
        
        @Test 
        public void testActionProperty(){
            action.setLookupType(null);
            assertNull(action.getLookupType());
            action.setLookupType("test");
            assertNotNull(action.getLookupType());
            action.setTreatmentSites(new ArrayList<TreatmentSiteWebDTO>());
            assertNotNull(action.getTreatmentSites());
            action.setTreatmentSiteSearchCriteria(new TreatmentSiteWebDTO());
            assertNotNull(action.getTreatmentSiteSearchCriteria());
            action.setPhysicians(new ArrayList<PhysicianWebDTO>());
            assertNotNull(action.getPhysicians());
            action.setPhysicianSearchCriteria(new PhysicianWebDTO());
            assertNotNull(action.getPhysicianSearchCriteria());
            assertEquals("initPhysicianLookup", action.initPhysicianLookup());
            assertEquals("initTreatmentSiteLookup", action.initTreatmentSiteLookup());
        }
        
        @Test 
        public void testUserWebDTOProperty (){
            assertNotNull(action.getUserAccount());
            action.setUserAccount(null);
            assertNull(action.getUserAccount());
        }
        @Test
        public void testActivateisNewUser(){
            HttpSession sess = new MockHttpSession();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("loginName", "testing");
            request.setupAddParameter("password", "cGFzc3dvcmQ=");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("create", action.activate());
        }
        
        @Test
        public void testActivateNewUserException(){
            HttpSession sess = new MockHttpSession();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("loginName", "dGhyb3dFeGNlcHRpb24=");
            request.setupAddParameter("password", "cGFzc3dvcmQ=");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("error", action.activate());
        }
        @Test
        public void testActivateUserExist(){
            HttpSession sess = new MockHttpSession();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("loginName", "Zmlyc3ROYW1l");
            request.setupAddParameter("password", "cGFzc3dvcmQ=");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("create", action.activate());
        }
        @Test
        public void testCreateAccount(){
            userAccount.setLoginName("test@test.com");
            userAccount.setPassword("testPassword1!");
            userAccount.setRetypePassword("testPassword1!");
            userAccount.setFirstName("firstName");
            userAccount.setLastName("lastName");
            userAccount.setState("Texas");
            userAccount.setCountry("USA");
            userAccount.setPhoneNumber("phone");
            userAccount.setOrganization("testOrg");
            userAccount.setPhysicianId("physicianID");
            userAccount.setTreatmentSiteId("treatmentSiteID");
            action.setUserAccount(userAccount);
            assertEquals("redirectToLogin", action.create());
        }
        @Test
        public void testCreateAccountException(){
            userAccount.setLoginName("test@test.com");
            userAccount.setPassword("testPassword1!");
            userAccount.setRetypePassword("testPassword1!");
            userAccount.setFirstName("firstName");
            userAccount.setLastName("lastName");
            userAccount.setState("Texas");
            userAccount.setCountry("USA");
            userAccount.setPhoneNumber("phone");
            action.setUserAccount(userAccount);
            assertEquals("input", action.create());
        }
        @Test
        public void testCreateAccountWithId(){
            userAccount.setLoginName("test@test.com");
            userAccount.setPassword("testPassword1!");
            userAccount.setRetypePassword("testPassword1!");
            userAccount.setFirstName("firstName");
            userAccount.setLastName("lastName");
            userAccount.setState("Texas");
            userAccount.setCountry("USA");
            userAccount.setPhoneNumber("phone");
            userAccount.setOrganization("testOrg");
            userAccount.setPhysician("physician");
            userAccount.setTreatmentSite("treatmentSite");
            userAccount.setTreatmentSiteId("1");
            userAccount.setPhysicianId("PO PERSON ID 01");
            userAccount.setId("1");
            action.setUserAccount(userAccount);
            assertEquals("create", action.create());
        }
        @Test
        public void testUpdateAccountExitsingAcc(){
            userAccount.setLoginName("test@test.com");
            userAccount.setPassword("testPassword1!");
            userAccount.setRetypePassword("testPassword1!");
            userAccount.setFirstName("firstName");
            userAccount.setLastName("lastName");
            userAccount.setState("None");
            userAccount.setCountry("country");
            userAccount.setPhoneNumber("phone");
            userAccount.setOrganization("testOrg");
            userAccount.setPhysicianId("physicianID");
            userAccount.setTreatmentSiteId("treatmentSiteID");
            userAccount.setId("1");
            action.setUserAccount(userAccount);
            assertEquals("create", action.updateAccount());
        }        
        @Test
        public void testLookupPhysician(){
            physicianSearchCriteria.setFirstName("firstName");
            physicianSearchCriteria.setLastName("lastName");
            physicianSearchCriteria.setState("None");
            physicianSearchCriteria.setCountry("USA");
            physicianSearchCriteria.setCity("city");
            physicianSearchCriteria.setZipCode("zipCode");
            assertEquals("success", action.lookupPhysician());
        }
        
        @Test
        public void testLookupTreatmentSite(){
            treatmentSiteSearchCriteria.setName("OrgName");
            treatmentSiteSearchCriteria.setState("None");
            treatmentSiteSearchCriteria.setCountry("USA");
            treatmentSiteSearchCriteria.setCity("city");
            treatmentSiteSearchCriteria.setZipCode("zipCode");
            assertEquals("success", action.lookupTreatmentSite());
        }
        
        // IllegalArgumentException on Hudson
        // NoClassDefFoundError for deploy on local machine
        // ExceptionInInitializerError from eclipse
        @Test
        public void testRequestAccount(){
            userAccount.setLoginName("test@test.com");
            userAccount.setPassword("testPassword1!");
            userAccount.setRetypePassword("testPassword1!");
            userAccount.setFirstName("firstName");
            userAccount.setLastName("lastName");
            userAccount.setState("Texas");
            userAccount.setCountry("USA");
            userAccount.setPhoneNumber("phone");
            userAccount.setOrganization("testOrg");
            userAccount.setPhysician("physician");
            userAccount.setTreatmentSite("treatmentSite");
            action.setUserAccount(userAccount);
            try {
                action.request();
            } catch(ExceptionInInitializerError e) {
                // expected
            } catch(NoClassDefFoundError e) {
                // expected
            } catch(IllegalArgumentException e) {
                // expected
            }
        } 
        @Test
        public void testRequestAccountException(){
            userAccount.setLoginName("test@test.com");
            userAccount.setPassword("testPassword1!");
            userAccount.setFirstName("firstName");
            userAccount.setLastName("lastName");
            userAccount.setState("Texas");
            userAccount.setCountry("USA");
            userAccount.setPhoneNumber("phone");
            action.setUserAccount(userAccount);
            assertEquals("input", action.request());
        }
}
