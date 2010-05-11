/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class RegisterUserActionTest extends AbstractRegWebTest{
        private RegisterUserAction action;
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
            sess.setAttribute("disclaimer", "accept");
            request.setRemoteUser("firstName");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("applicationError", action.showMyAccount());
        }
        @Test
        public void testUpdateAccount(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setId(1L);
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
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
            registryUserWebDTO.setAffiliateOrg("affiliateOrg");
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("applicationError", action.updateAccount());
        }
        @Test
        public void testUpdateAccountExitsingAcc(){
            action = new RegisterUserAction();
            RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
            registryUserWebDTO.setId(1L);
            registryUserWebDTO.setUsername("testuser");
            registryUserWebDTO.setEmailAddress("test@test.com");
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
            registryUserWebDTO.setAffiliateOrg("affiliateOrg");
            registryUserWebDTO.setId(1L);
            registryUserWebDTO.setCsmUserId(1L);
            action.setRegistryUserWebDTO(registryUserWebDTO);
            assertEquals("applicationError", action.updateAccount());
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
}
