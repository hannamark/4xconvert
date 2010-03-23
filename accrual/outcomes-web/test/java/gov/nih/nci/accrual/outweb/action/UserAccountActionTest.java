package gov.nih.nci.accrual.outweb.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.accrual.outweb.dto.util.PhysicianWebDTO;
import gov.nih.nci.accrual.outweb.dto.util.TreatmentSiteWebDTO;
import gov.nih.nci.accrual.outweb.dto.util.UserAccountWebDto;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

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
    UserAccountWebDto userAccount;
    TreatmentSiteWebDTO treatmentSiteSearchCriteria = new TreatmentSiteWebDTO();
    PhysicianWebDTO physicianSearchCriteria = new PhysicianWebDTO();


        @Before
        public void initAction() throws Exception {
            action = new UserAccountAction();
            action.prepare();
            userAccount = new UserAccountWebDto();
            action.setPhysicianSearchCriteria(physicianSearchCriteria);
            action.setTreatmentSiteSearchCriteria(treatmentSiteSearchCriteria);
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
            assertNotNull(action.isPatients());
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

        //@Test
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
            request.setupAddParameter("loginName", "amFuZS5kb2VAZ29vZ2xlLmNvbQ==");
            request.setupAddParameter("password", "cGFzc3dvcmQ=");
            request.setSession(sess);
            ServletActionContext.setRequest(request);
            assertEquals("create", action.activate());
        }
        @Test
        public void testCreateAccount(){
            userAccount.setIdentity(StConverter.convertToSt("test@test.com"));
            userAccount.setPassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setRetypePassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setFirstName(StConverter.convertToSt("firstName"));
            userAccount.setLastName(StConverter.convertToSt("lastName"));
            userAccount.setState(StConverter.convertToSt("Texas"));
            userAccount.setCountry(StConverter.convertToSt("USA"));
            userAccount.setPhone(StConverter.convertToSt("phone"));
            userAccount.setAffiliateOrg(StConverter.convertToSt("testOrg"));
            userAccount.setPhysicianIdentifier(IiConverter.convertToIi("physicianID"));
            userAccount.setTreatmentSiteIdentifier(IiConverter.convertToIi("treatmentSiteID"));
            action.setUserAccount(userAccount);
            assertEquals("redirectToLogin", action.create());
        }
        @Test
        public void testCreateAccountException(){
            userAccount.setIdentity(StConverter.convertToSt("test@test.com"));
            userAccount.setPassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setRetypePassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setFirstName(StConverter.convertToSt("firstName"));
            userAccount.setLastName(StConverter.convertToSt("lastName"));
            userAccount.setState(StConverter.convertToSt("Texas"));
            userAccount.setCountry(StConverter.convertToSt("USA"));
            userAccount.setPhone(StConverter.convertToSt("phone"));
            action.setUserAccount(userAccount);
            assertEquals("input", action.create());
        }
        @Test
        public void testCreateAccountWithId(){
            userAccount.setIdentity(StConverter.convertToSt("test@test.com"));
            userAccount.setPassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setRetypePassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setFirstName(StConverter.convertToSt("firstName"));
            userAccount.setLastName(StConverter.convertToSt("lastName"));
            userAccount.setState(StConverter.convertToSt("Texas"));
            userAccount.setCountry(StConverter.convertToSt("USA"));
            userAccount.setPhone(StConverter.convertToSt("phone"));
            userAccount.setAffiliateOrg(StConverter.convertToSt("testOrg"));
            userAccount.setPhysician(StConverter.convertToSt("physician"));
            userAccount.setTreatmentSite(StConverter.convertToSt("treatmentSite"));
            userAccount.setTreatmentSiteIdentifier(IiConverter.convertToIi("1"));
            userAccount.setPhysicianIdentifier(IiConverter.convertToIi("PO PERSON ID 01"));
            userAccount.setIdentifier(IiConverter.convertToIi("1"));
            action.setUserAccount(userAccount);
            assertEquals("create", action.create());
        }
        @Test
        public void testUpdateAccountExitsingAcc(){
            userAccount.setIdentity(StConverter.convertToSt("test@test.com"));
            userAccount.setPassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setRetypePassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setFirstName(StConverter.convertToSt("firstName"));
            userAccount.setLastName(StConverter.convertToSt("lastName"));
            userAccount.setState(StConverter.convertToSt("None"));
            userAccount.setCountry(StConverter.convertToSt("country"));
            userAccount.setPhone(StConverter.convertToSt("phone"));
            userAccount.setAffiliateOrg(StConverter.convertToSt("testOrg"));
            userAccount.setPhysicianIdentifier(IiConverter.convertToIi("physicianID"));
            userAccount.setTreatmentSiteIdentifier(IiConverter.convertToIi("treatmentSiteID"));
            userAccount.setIdentifier(IiConverter.convertToIi("1"));
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
            userAccount.setIdentity(StConverter.convertToSt("test@test.com"));
            userAccount.setPassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setRetypePassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setFirstName(StConverter.convertToSt("firstName"));
            userAccount.setLastName(StConverter.convertToSt("lastName"));
            userAccount.setState(StConverter.convertToSt("Texas"));
            userAccount.setCountry(StConverter.convertToSt("USA"));
            userAccount.setPhone(StConverter.convertToSt("phone"));
            userAccount.setAffiliateOrg(StConverter.convertToSt("testOrg"));
            userAccount.setPhysicianIdentifier(IiConverter.convertToIi("physician"));
            userAccount.setTreatmentSiteIdentifier(IiConverter.convertToIi("treatmentSite"));
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
            userAccount.setIdentity(StConverter.convertToSt("test@test.com"));
            userAccount.setPassword(StConverter.convertToSt("testPassword1!"));
            userAccount.setFirstName(StConverter.convertToSt("firstName"));
            userAccount.setLastName(StConverter.convertToSt("lastName"));
            userAccount.setState(StConverter.convertToSt("Texas"));
            userAccount.setCountry(StConverter.convertToSt("USA"));
            userAccount.setPhone(StConverter.convertToSt("phone"));
            action.setUserAccount(userAccount);
            assertEquals("input", action.request());
        }
        @Test
        public void testRequestAccountException2(){
            userAccount.setPassword(StConverter.convertToSt("test"));
            action.setUserAccount(userAccount);
            assertEquals("input", action.request());
        }
        @Test
        public void testRequestAccountException3(){
            userAccount.setIdentity(StConverter.convertToSt("test"));
            userAccount.setPassword(StConverter.convertToSt("testing1"));
            action.setUserAccount(userAccount);
            assertEquals("input", action.request());
        }

        @Test
        public void testCreatePersonWithEmpty(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("firstName", "");
            request.setupAddParameter("lastName", "");
            request.setupAddParameter("email", "");
            request.setupAddParameter("streetAddr", "");
            request.setupAddParameter("country", "aaa");
            request.setupAddParameter("city", "");
            request.setupAddParameter("state", "");
            request.setupAddParameter("zip", "");
            ServletActionContext.setRequest(request);
            assertEquals("create_pers_response", action.lookupCreatePerson());
            assertNotNull(action.hasActionErrors());
        }
        @Test
        public void testCreatePersonWithCountryAsUSAWith3LetterStateCode(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("firstName", "firstName");
            request.setupAddParameter("lastName", "lastName");
            request.setupAddParameter("country", "USA");
            request.setupAddParameter("city", "");
            request.setupAddParameter("state", "abs");
            request.setupAddParameter("zip", "MAS");
            request.setupAddParameter("email", "");
            ServletActionContext.setRequest(request);
            assertEquals("create_pers_response", action.lookupCreatePerson());
            assertTrue(action.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
        }
        @Test
        public void testCreatePersonWithCountryAsCANWith3LetterStateCode(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("firstName", "First");
            request.setupAddParameter("lastName", "lastName");
            request.setupAddParameter("country", "CAN");
            request.setupAddParameter("city", "");
            request.setupAddParameter("state", "abs");
            request.setupAddParameter("zip", "");
            request.setupAddParameter("email", "email");
            ServletActionContext.setRequest(request);
            assertEquals("create_pers_response", action.lookupCreatePerson());
            assertTrue(action.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
        }
        @Test
        public void testCreatePersonWithCountryAsAUSWith4LetterState(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("firstName", "FirstName");
            request.setupAddParameter("lastName", "lastName");
            request.setupAddParameter("country", "AUS");
            request.setupAddParameter("city", "");
            request.setupAddParameter("state", "ASDAD");
            request.setupAddParameter("zip", "");
            request.setupAddParameter("email", "");
            ServletActionContext.setRequest(request);
            assertEquals("create_pers_response", action.lookupCreatePerson());
            assertTrue(action.getActionErrors().contains("2/3-letter State/Province Code required for Australia"));
        }
        @Test
        public void testCreatePerson_USA(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("firstName", "FirstName");
            request.setupAddParameter("preFix", "preFix");
            request.setupAddParameter("midName", "midName");
            request.setupAddParameter("suffix", "suffix");
            request.setupAddParameter("lastName", "lastName");
            request.setupAddParameter("country", "USA");
            request.setupAddParameter("city", "rock");
            request.setupAddParameter("state", "MD");
            request.setupAddParameter("zip", "20663");
            request.setupAddParameter("email", "org@org.com");
            request.setupAddParameter("phone", "phoneNumber");
            request.setupAddParameter("fax", "fax");
            request.setupAddParameter("tty", "tty");
            request.setupAddParameter("url", "http://org@org.com");
            ServletActionContext.setRequest(request);
            assertEquals("create_pers_response", action.lookupCreatePerson());
        }
        @Test
        public void testCreateOrgWithEmpty(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("orgName", "");
            request.setupAddParameter("countryName", "aaa");
            request.setupAddParameter("cityName", "");
            request.setupAddParameter("orgStAddress", "");
            request.setupAddParameter("stateName", "");
            request.setupAddParameter("zipCode", "");
            request.setupAddParameter("email", "");
            ServletActionContext.setRequest(request);
            assertEquals("create_org_response", action.lookupCreateOrganization());
            assertNotNull(action.hasActionErrors());
        }
        @Test
        public void testCreateOrgWithCountryAsUSAWith3LetterStateCode() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("orgName", "");
            request.setupAddParameter("countryName", "USA");
            request.setupAddParameter("cityName", "");
            request.setupAddParameter("stateName", "abs");
            request.setupAddParameter("zipCode", "");
            request.setupAddParameter("email", "");
            ServletActionContext.setRequest(request);
            assertEquals("create_org_response", action.lookupCreateOrganization());
            assertTrue(action.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
        }
        @Test
        public void testCreateOrgWithCountryAsCANWith3LetterStateCode() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("orgName", "");
            request.setupAddParameter("countryName", "CAN");
            request.setupAddParameter("cityName", "");
            request.setupAddParameter("stateName", "abs");
            request.setupAddParameter("zipCode", "");
            request.setupAddParameter("email", "email");
            ServletActionContext.setRequest(request);
            assertEquals("create_org_response", action.lookupCreateOrganization());
            assertTrue(action.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
        }
        @Test
        public void testCreateOrgWithCountryAsAUSWith4LetterState() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("orgName", "");
            request.setupAddParameter("countryName", "AUS");
            request.setupAddParameter("cityName", "");
            request.setupAddParameter("stateName", "ASDAD");
            request.setupAddParameter("zipCode", "");
            request.setupAddParameter("email", "");
            ServletActionContext.setRequest(request);
            assertEquals("create_org_response", action.lookupCreateOrganization());
            assertTrue(action.getActionErrors().contains("2/3-letter State/Province Code required for Australia"));
        }
        @Test
        public void testCreateOrg_USA() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setupAddParameter("orgName", "Some Name");
            request.setupAddParameter("countryName", "USA");
            request.setupAddParameter("cityName", "rock");
            request.setupAddParameter("stateName", "MD");
            request.setupAddParameter("zipCode", "20663");
            request.setupAddParameter("email", "org@org.com");
            request.setupAddParameter("phoneNumber", "phoneNumber");
            request.setupAddParameter("fax", "fax");
            request.setupAddParameter("tty", "tty");
            request.setupAddParameter("url", "http://org@org.com");
            ServletActionContext.setRequest(request);
            assertEquals("create_org_response", action.lookupCreateOrganization());
        }
}
