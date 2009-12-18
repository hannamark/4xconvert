/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.service.PAException;

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
public class PopupActionTest extends AbstractRegWebTest {
    private PopupAction popUpAction;
    @Test
    public void testLookuporgs(){
        popUpAction = new PopupAction();
        assertEquals("orgs",popUpAction.lookuporgs());
    }
    @Test
    public void testLookuporgsCountryListInSession(){
        popUpAction = new PopupAction();
        List<Country> countryList = new ArrayList<Country>();
        Country country = new Country();
        country.setAlpha2("US");
        country.setAlpha3("USA");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("CA");
        country.setAlpha3("CAN");
        countryList.add(country);
        countryList.add(country);
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute("countrylist",countryList);
        request.setSession(sess);
        ServletActionContext.setRequest(request);

        assertEquals("orgs",popUpAction.lookuporgs());
    }

    @Test
    public void testLookuppersons(){
        popUpAction = new PopupAction();
        assertEquals("persons",popUpAction.lookuppersons());
    }
    @Test
    public void testDisplayOrgList(){
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "OrgName");
        request.setupAddParameter("countryName", "USA");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListWithCountryList() throws Exception{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "OrgName");
        request.setupAddParameter("countryName", "USA");
        popUpAction.prepare();
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListNoRecords(){
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "abs");
        request.setupAddParameter("countryName", "USA");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListException(){
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "");
        request.setupAddParameter("orgStAddress", "orgStAddress");
        request.setupAddParameter("countryName", "");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "");
        request.setupAddParameter("ZipCode", "");
        request.setupAddParameter("ctepid","");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListWithAllEmptyParam(){
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "");
        request.setupAddParameter("countryName", "");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("ctepid","");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayOrgList());
        assertTrue(popUpAction.getActionErrors().contains("Please enter at least one search criteria"));
    }
    @Test
    public void testDisplayOrgListWithCountryEmpty(){
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "OrgName");
        request.setupAddParameter("countryName", "");
        request.setupAddParameter("cityName", "rock");
        request.setupAddParameter("stateName", "md");
        request.setupAddParameter("zipCode", "342");
        request.setupAddParameter("ctepid","");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayOrgList());
        assertEquals(1,popUpAction.getActionErrors().size());
        assertTrue(popUpAction.getActionErrors().contains("Please select a country"));
    }

    @Test
    public void testDisplayOrgListDisplayTag(){
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "OrgName");
        request.setupAddParameter("countryName", "USA");
        ServletActionContext.setRequest(request);
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
        
    }

    @Test
    public void testCountryListProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getCountryList());
        popUpAction.setCountryList(null);
        assertNull(popUpAction.getCountryList());
    }
    @Test
    public void testPersonsProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getPersons());
        popUpAction.setPersons(null);
        assertNull(popUpAction.getPersons());
    }
    @Test
    public void testOrgSearchCriteriaProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getOrgSearchCriteria());
        popUpAction.setOrgSearchCriteria(null);
        assertNull(popUpAction.getOrgSearchCriteria());
    }
    @Test
    public void testPersonDTOProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getPersonDTO());
        popUpAction.setPersonDTO(null);
        assertNull(popUpAction.getPersonDTO());
    }
    @Test
    public void testOrgsProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getOrgs());
        popUpAction.setOrgs(null);
        assertNull(popUpAction.getOrgs());
    }
    @Test
    public void testCreateOrgProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getCreateOrg());
        popUpAction.setCreateOrg(null);
        assertNull(popUpAction.getCreateOrg());
    }
    @Test
    public void testCreateOrgWithEmpty() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "");
        request.setupAddParameter("countryName", "aaa");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("email", "");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("Organization is a required field"));
    }
    @Test
    public void testCreateOrgWithCountryAsUSAWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "");
        request.setupAddParameter("countryName", "USA");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "abs");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("email", "");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }
    @Test
    public void testCreateOrgWithCountryAsCANWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "");
        request.setupAddParameter("countryName", "CAN");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "abs");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("email", "email");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }
    @Test
    public void testCreateOrgWithCountryAsAUSWith4LetterState() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "");
        request.setupAddParameter("countryName", "AUS");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "ASDAD");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("email", "");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("2/3-letter State/Province Code required for Australia"));
    }
    @Test
    public void testCreateOrg_USA() throws PAException{
        popUpAction = new PopupAction();
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
        assertEquals("create_org_response", popUpAction.createOrganization());
    }
    @Test
    public void testCreateOrg_CAN() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "Some Name");
        request.setupAddParameter("countryName", "CAN");
        request.setupAddParameter("cityName", "rock");
        request.setupAddParameter("stateName", "MD");
        request.setupAddParameter("zipCode", "20663");
        request.setupAddParameter("email", "org@org.com");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
    }
    @Test
    public void testCreateOrg_AUS2LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "Some Name");
        request.setupAddParameter("countryName", "AUS");
        request.setupAddParameter("cityName", "rock");
        request.setupAddParameter("stateName", "MD");
        request.setupAddParameter("zipCode", "20663");
        request.setupAddParameter("email", "org@org.com");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
    }
    @Test
    public void testCreateOrg_AUS3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "Some Name");
        request.setupAddParameter("countryName", "AUS");
        request.setupAddParameter("cityName", "rock");
        request.setupAddParameter("stateName", "MDA");
        request.setupAddParameter("zipCode", "20663");
        request.setupAddParameter("email", "org@org.com");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_response", popUpAction.createOrganization());
    }
    @Test
    public void testDisplayPersonsListException() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayPersonsList());
    }
    @Test
    public void testDisplayPersonsListAllParamNull() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "");
        request.setupAddParameter("lastName", "");
        request.setupAddParameter("email", "");
        request.setupAddParameter("country", "");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("ctepId","");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayPersonsList());
        assertTrue(popUpAction.getActionErrors().contains("Please enter at least one search criteria"));
    }
    @Test
    public void testDisplayPersonsList() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "firstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("email", "em@mail.com");
        request.setupAddParameter("country", "USA");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("ctepId","");
        ServletActionContext.setRequest(request);
        assertEquals("success", popUpAction.displayPersonsList());
     }
    @Test
    public void testDisplayPersonsListDisplayTag() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "firstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("email", "em@mail.com");
        request.setupAddParameter("country", "USA");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("ctepId","");
        ServletActionContext.setRequest(request);
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
     }
    @Test
    public void testCreatePersonWithEmpty() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "");
        request.setupAddParameter("lastName", "");
        request.setupAddParameter("email", "");
        request.setupAddParameter("country", "aaa");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("ctepId","");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("First Name is a required field"));
    }
    @Test
    public void testCreatePersonWithCountryAsUSAWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "firstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("country", "USA");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "abs");
        request.setupAddParameter("zip", "MAS");
        request.setupAddParameter("email", "");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }
    @Test
    public void testCreatePersonWithCountryAsCANWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "First");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("country", "CAN");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "abs");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("email", "email");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }
    @Test
    public void testCreatePersonWithCountryAsAUSWith4LetterState() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "FirstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("country", "AUS");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "ASDAD");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("email", "");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("2/3-letter State/Province Code required for Australia"));
    }
    @Test
    public void testCreatePerson_USA() throws PAException{
        popUpAction = new PopupAction();
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
        assertEquals("create_pers_response", popUpAction.createPerson());
    }
    @Test
    public void testCreatePer_CAN() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "FirstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("country", "CAN");
        request.setupAddParameter("city", "rock");
        request.setupAddParameter("state", "MD");
        request.setupAddParameter("zip", "20663");
        request.setupAddParameter("email", "org@org.com");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
    }
    @Test
    public void testCreatePerson_AUS2LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "FirstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("country", "AUS");
        request.setupAddParameter("city", "rock");
        request.setupAddParameter("state", "MD");
        request.setupAddParameter("zip", "20663");
        request.setupAddParameter("email", "org@org.com");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
    }
    @Test
    public void testCreatePerson_AUS3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "FirstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("streetAddr", "streetAddr");
        request.setupAddParameter("country", "AUS");
        request.setupAddParameter("city", "rock");
        request.setupAddParameter("state", "MDA");
        request.setupAddParameter("zip", "20663");
        request.setupAddParameter("email", "org@org.com");
        ServletActionContext.setRequest(request);
        assertEquals("create_pers_response", popUpAction.createPerson());
    }
    @Test
    public void testDisplayPersonsListDisplayTagWithCtepId() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "firstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("email", "em@mail.com");
        request.setupAddParameter("country", "USA");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("ctepId","1");
        ServletActionContext.setRequest(request);
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
     }
    @Test
    public void testDisplayOrgListDisplayTagWithCtepId() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "OrgName");
        request.setupAddParameter("email", "em@mail.com");
        request.setupAddParameter("countryName", "USA");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("ctepid","1");
        ServletActionContext.setRequest(request);
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
     }
    @Test
    public void testDisplayPersonsListDisplayTagWithCtepIdNoResult() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("firstName", "firstName");
        request.setupAddParameter("lastName", "lastName");
        request.setupAddParameter("email", "em@mail.com");
        request.setupAddParameter("country", "USA");
        request.setupAddParameter("city", "");
        request.setupAddParameter("state", "");
        request.setupAddParameter("zip", "");
        request.setupAddParameter("ctepId","2");
        ServletActionContext.setRequest(request);
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
     }
    @Test
    public void testDisplayOrgListDisplayTagWithCtepIdNoResult() throws PAException{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgName", "OrgName");
        request.setupAddParameter("email", "em@mail.com");
        request.setupAddParameter("countryName", "USA");
        request.setupAddParameter("cityName", "");
        request.setupAddParameter("stateName", "");
        request.setupAddParameter("zipCode", "");
        request.setupAddParameter("ctepId","2");
        ServletActionContext.setRequest(request);
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
     }
    @Test
    public void testPrepare() throws Exception{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession sess =  new MockHttpSession();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        popUpAction.prepare();
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("countrylist"));
     
    }
    @Test
    public void testPrepareSessionWithValue() throws Exception{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession sess =  new MockHttpSession();
        List<String> countryList = new ArrayList<String>();
        countryList.add("USA");
        sess.setAttribute("countrylist",countryList);
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        popUpAction.prepare();
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("countrylist"));
     
    }
}
