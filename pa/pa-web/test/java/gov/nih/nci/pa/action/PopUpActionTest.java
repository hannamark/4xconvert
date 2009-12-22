/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpActionTest extends AbstractPaActionTest {

    PopUpAction popUpAction;
    
    @Before
    public void setUp(){
        popUpAction = new PopUpAction();
        List<String> countryList = new ArrayList <String>();
        getSession().setAttribute("countrylist",countryList);
        
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#lookupcontactpersons()}.
     */
    @Test
    public void testLookupcontactpersons() throws Exception {
    	getRequest().setupAddParameter("email", "test@test.org");
    	getRequest().setupAddParameter("tel", "132412312");
        String result = popUpAction.lookupcontactpersons();
        assertEquals("test@test.org",(String)getRequest().getSession().getAttribute("emailEntered"));
        assertEquals("132412312",(String)getRequest().getSession().getAttribute("telephoneEntered"));
        assertEquals("contactpersons",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#lookuporgs()}.
     */
    @Test
    public void testLookuporgs() {
        String result = popUpAction.lookuporgs();
        assertEquals("orgs",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#displayOrgList()}.
     */
    @Test
    public void testLookuppersons() {
        String result = popUpAction.lookuppersons();
        assertEquals("persons",result);
    }
    @Test
    public void testdisplaycontactPersonsList() {
    	getRequest().setupAddParameter("firstName", "fname");
    	getRequest().setupAddParameter("lastName", "lname");
    	getRequest().setupAddParameter("countryName", "USA");
    	getRequest().setupAddParameter("cityName", "dallas");
    	getRequest().setupAddParameter("zipCode", "75090");
    	getRequest().setupAddParameter("stateName", "TX");
    	String result = popUpAction.displaycontactPersonsList();
        assertEquals("success",result);
    }
    @Test
    public void testdisplaycontactPersonsListDisplayTag() {
    	getRequest().setupAddParameter("firstName", "fname");
    	getRequest().setupAddParameter("lastName", "lname");
    	getRequest().setupAddParameter("countryName", "USA");
    	getRequest().setupAddParameter("cityName", "dallas");
    	getRequest().setupAddParameter("zipCode", "75090");
    	getRequest().setupAddParameter("stateName", "TX");
    	String result = popUpAction.displaycontactPersonsListDisplayTag();
        assertEquals("contactpersons",result);
    }
    @Test
    public void testdisplayPersonsList() {
    	getRequest().setupAddParameter("firstName", "fname");
    	getRequest().setupAddParameter("lastName", "lname");
    	getRequest().setupAddParameter("countryName", "USA");
    	getRequest().setupAddParameter("cityName", "dallas");
    	getRequest().setupAddParameter("zipCode", "75090");
    	getRequest().setupAddParameter("stateName", "TX");
    	String result = popUpAction.displayPersonsList();
        assertEquals("success",result);
    }
    @Test
    public void testdisplayPersonsListDisplayTag() {
    	getRequest().setupAddParameter("firstName", "fname");
    	getRequest().setupAddParameter("lastName", "lname");
    	getRequest().setupAddParameter("countryName", "USA");
    	getRequest().setupAddParameter("cityName", "dallas");
    	getRequest().setupAddParameter("zipCode", "75090");
    	getRequest().setupAddParameter("stateName", "TX");
    	String result = popUpAction.displayPersonsListDisplayTag();
        assertEquals("persons",result);
    }
    @Test
    public void testdisplayOrgList() {
    	getRequest().setupAddParameter("orgName", "OrgName");
    	getRequest().setupAddParameter("countryName", "USA");
    	getRequest().setupAddParameter("cityName", "dallas");
    	getRequest().setupAddParameter("zipCode", "75090");
    	getRequest().setupAddParameter("stateName", "TX");
    	String result = popUpAction.displayOrgList();
        assertEquals("success",result);
    }
    @Test
    public void testdisplayOrgListDisplayTag() {
    	getRequest().setupAddParameter("orgName", "OrgName");
    	getRequest().setupAddParameter("countryName", "USA");
    	getRequest().setupAddParameter("cityName", "dallas");
    	getRequest().setupAddParameter("zipCode", "75090");
    	getRequest().setupAddParameter("stateName", "TX");
    	String result = popUpAction.displayOrgListDisplayTag();
        assertEquals("orgs",result);
    }
}
