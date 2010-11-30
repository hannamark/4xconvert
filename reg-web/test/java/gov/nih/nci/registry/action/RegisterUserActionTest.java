/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * @author Vrushali
 *
 */
public class RegisterUserActionTest extends AbstractRegWebTest{
        private RegisterUserAction action;
        @Before
        public void setup() {
            CSMUserService.getInstance();
            CSMUserService.setRegistryUserService(new MockCSMUserService());
        }
        @Test
        public void testUserActionProperty(){
            action = new RegisterUserAction();
            assertNull(action.getUserAction());
            action.setUserAction("userAction");
            assertNotNull(action.getUserAction());
        }
        @Test
        public void testRegistryUserWebDTOProperty (){
            action = new RegisterUserAction();
            assertNotNull(action.getRegistryUserWebDTO());
            action.setRegistryUserWebDTO(null);
            assertNull(action.getRegistryUserWebDTO());
        }
        @Test
        public void testSendMailErr(){
            action = new RegisterUserAction();
            assertEquals("registerUserError",action.sendMail());
        }
        @Test
        public void testSendMail(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
            registryUserWebDTO.setPassword("password");
            registryUserWebDTO.setRetypePassword("password");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("confirmation",action.sendMail());
        }
        @Test
        public void testSendMailErrPasswordEmail(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test.test.com");
            registryUserWebDTO.setPassword("pass");
            registryUserWebDTO.setRetypePassword("1234");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("registerUserError",action.sendMail());
        }
        @Test
        public void testActivateisNewUser(){
            action = new RegisterUserAction();
            assertEquals("myAccount", action.activate());
        }
        @Test
        public void testActivateUserExist(){
            action = new RegisterUserAction();
            HttpSession sess = new MockHttpSession();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("emailAddress", "Zmlyc3ROYW1l");
            request.setupAddParameter("password", "cGFzc3dvcmQ=");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("myAccount", action.activate());
        }
        @Test
        public void testActivateException(){
            action = new RegisterUserAction();
            HttpSession sess = new MockHttpSession();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("emailAddress", "dGhyb3dFeGNlcHRpb24=");
            request.setupAddParameter("password", "cGFzc3dvcmQ=");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("applicationError", action.activate());
        }
        @Test
        public void testShowMyAccountErr(){
            action = new RegisterUserAction();
            MockHttpServletRequest request = new MockHttpServletRequest();
            HttpSession sess = new MockHttpSession();
            request.setRemoteUser("firstName");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("myAccount", action.showMyAccount());
            List<RegistryUser> lst = (List<RegistryUser>) sess.getAttribute("adminUsers");
            assertEquals(0, lst.size());
            request = new MockHttpServletRequest();
            sess = new MockHttpSession();
            request.setRemoteUser("affiliated Org");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("myAccount", action.showMyAccount());
            lst = (List<RegistryUser>) sess.getAttribute("adminUsers");
            assertNull(lst);
        }
        @Test
        public void testUpdateAccount(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setId(1L);
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
            registryUserWebDTO.setOldPassword("Testing@01");
            registryUserWebDTO.setPassword("Mvedjbtp123!!!");
            registryUserWebDTO.setRetypePassword("Mvedjbtp123!!!");
            registryUserWebDTO.setFirstName("firstName");
            registryUserWebDTO.setLastName("lastName");
            registryUserWebDTO.setAddressLine("123 Fake St.");
            registryUserWebDTO.setCity("Here");
            registryUserWebDTO.setPostalCode("11111");
            registryUserWebDTO.setState("None");
            registryUserWebDTO.setCountry("country");
            registryUserWebDTO.setPhone("phone");
            registryUserWebDTO.setAffiliatedOrganizationId(2L);
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("myAccountError", action.updateAccount());
        }
        @Test
        public void testUpdateAccountExitsingAcc(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setId(1L);
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
            registryUserWebDTO.setFirstName("firstName");
            registryUserWebDTO.setLastName("lastName");
            registryUserWebDTO.setAddressLine("123 Fake St.");
            registryUserWebDTO.setCity("Here");
            registryUserWebDTO.setPostalCode("11111");
            registryUserWebDTO.setState("None");
            registryUserWebDTO.setCountry("country");
            registryUserWebDTO.setPhone("phone");
            registryUserWebDTO.setAffiliateOrg("affiliateOrg");
            registryUserWebDTO.setAffiliatedOrganizationId(2L);
            registryUserWebDTO.setId(1L);
            registryUserWebDTO.setCsmUserId(1L);
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("redirect_to_login", action.updateAccount());
            registryUserWebDTO.setPassword("Mvedjbtp123!!!");
            registryUserWebDTO.setRetypePassword("Mvedjbtp123!!!");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("myAccountError", action.updateAccount());
            action = new RegisterUserAction();
            registryUserWebDTO.setPassword("");
            registryUserWebDTO.setOldPassword("Testing@01");
            registryUserWebDTO.setRetypePassword("Mvedjbtp123!!!");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("myAccountError", action.updateAccount());
            action = new RegisterUserAction();
            registryUserWebDTO.setPassword("Mvedjbtp123!!!");
            registryUserWebDTO.setOldPassword("Testing@01");
            registryUserWebDTO.setRetypePassword("");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("myAccountError", action.updateAccount());
            action = new RegisterUserAction();
            registryUserWebDTO.setPassword("Mvedjbtp123!!!");
            registryUserWebDTO.setOldPassword("Testing@01");
            registryUserWebDTO.setRetypePassword("Mvedjbtp123!!!");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("redirect_to_login", action.updateAccount());
        }
        @Test
        public void testUpdateAccountErr(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
            registryUserWebDTO.setPassword("password");
            registryUserWebDTO.setRetypePassword("password");
            registryUserWebDTO.setState("None");
            registryUserWebDTO.setCountry("United States");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("myAccountError", action.updateAccount());
        }
        @Test
        public void testUpdateAccountErrStateCountry(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
            registryUserWebDTO.setPassword("password");
            registryUserWebDTO.setRetypePassword("password");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("myAccountError", action.updateAccount());
        }

    @Test
    public void testWebDto() {
        action = new RegisterUserAction();
        RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
        registryUserWebDTO.setId(1L);
        registryUserWebDTO.setUsername("testuser");
        registryUserWebDTO.setEmailAddress("test@test.com");
        registryUserWebDTO.setOldPassword("Testing@01");
        registryUserWebDTO.setPassword("Mvedjbtp123!!!");
        registryUserWebDTO.setRetypePassword("Mvedjbtp123!!!");
        registryUserWebDTO.setFirstName("firstName");
        registryUserWebDTO.setMiddleName("MiddleName");
        registryUserWebDTO.setLastName("lastName");
        registryUserWebDTO.setAddressLine("123 Fake St.");
        registryUserWebDTO.setCity("Here");
        registryUserWebDTO.setPostalCode("11111");
        registryUserWebDTO.setState("None");
        registryUserWebDTO.setCountry("country");
        registryUserWebDTO.setPhone("phone");
        registryUserWebDTO.setAffiliatedOrganizationId(2L);
        registryUserWebDTO.setPrsOrgName("prsorgname");
        registryUserWebDTO.setTreatmentSiteId(1L);
        registryUserWebDTO.setHasExistingGridAccount(true);
        registryUserWebDTO.setRequestAdminAccess(true);
        registryUserWebDTO.setAdminForAffiliatedOrg(true);
        registryUserWebDTO.setPhysicianId(1L);
        action.setRegistryUserWebDTO(registryUserWebDTO);
        assertNotNull(registryUserWebDTO.getDisplayUsername());
        assertNotNull(registryUserWebDTO.getPrsOrgName());
        assertNotNull(registryUserWebDTO.getTreatmentSiteId());
        assertNotNull(registryUserWebDTO.getPhysicianId());
        assertNotNull(registryUserWebDTO.isAdminForAffiliatedOrg());
        action.setSelectedIdentityProvider("Test");
        assertNotNull(action.getSelectedIdentityProvider());
        action.setIdentityProviders(new HashMap<String, String>());
        assertNotNull(action.getIdentityProviders());
    }

    @Test
    public void testloadAdminUsers() {
        action = new RegisterUserAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        request.setupAddParameter("affiliatedOrgId", "3");
        request.setupAddParameter("action", "viewUsers");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewAdminUser", action.loadAdminUsers());
    }

    @Test
    public void testRegisterExistingGridAccount() {
        action = new RegisterUserAction();
        RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
        registryUserWebDTO.setEmailAddress("test@test.com");
        registryUserWebDTO.setFirstName("firstName");
        registryUserWebDTO.setLastName("lastName");
        action.setRegistryUserWebDTO(registryUserWebDTO);
        assertEquals("myAccount", action.registerExistingGridAccount());
    }
}

