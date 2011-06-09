/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.Country;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
        List<Country> countryList = new ArrayList <Country>();
        Country usa = new Country();
        usa.setName("United States");
        usa.setAlpha2("US");
        usa.setAlpha3("USA");
        countryList.add(usa);
        getSession().setAttribute("countrylist",countryList);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#lookupcontactpersons()}.
     */
    @Test
    public void testLookupcontactpersons() throws Exception {
        popUpAction.setEmail("test@test.org");
        popUpAction.setTelephone("132412312");
        assertEquals("contactpersons", popUpAction.lookupcontactpersons());
        assertEquals(1, popUpAction.getCountryList().size());
        assertNull(popUpAction.getPersons());
        assertEquals("test@test.org", getRequest().getSession().getAttribute("emailEntered"));
        assertEquals("132412312", getRequest().getSession().getAttribute("telephoneEntered"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#lookuporgs()}.
     */
    @Test
    public void testLookuporgs() {
        assertEquals("orgs", popUpAction.lookuporgs());
        assertEquals(1, popUpAction.getCountryList().size());
        assertTrue(CollectionUtils.isEmpty(popUpAction.getOrgs()));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#displayOrgList()}.
     */
    @Test
    public void testLookuppersons() {
        assertEquals("persons", popUpAction.lookuppersons());
        assertEquals(1, popUpAction.getCountryList().size());
        assertTrue(CollectionUtils.isEmpty(popUpAction.getOrgs()));
        assertTrue(CollectionUtils.isEmpty(popUpAction.getPersons()));
    }
    @Test
    public void testdisplaycontactPersonsList() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("success", popUpAction.displaycontactPersonsList());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
    @Test
    public void testdisplaycontactPersonsListDisplayTag() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("contactpersons", popUpAction.displaycontactPersonsListDisplayTag());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
    @Test
    public void testdisplayPersonsList() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("success", popUpAction.displayPersonsList());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
    @Test
    public void testdisplayPersonsListDisplayTag() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
    @Test
    public void testdisplayOrgList() {
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("success", popUpAction.displayOrgList());
        assertNotNull(popUpAction.getOrgs());
        assertEquals(5, popUpAction.getOrgs().size());
    }
    
    @Test
    public void testdisplayOrgListByCtepId() {
        popUpAction.setCtepId("CTEP ID");
        assertEquals("success", popUpAction.displayOrgList());
        assertNotNull(popUpAction.getOrgs());
        assertEquals(1, popUpAction.getOrgs().size());
    }
    
    @Test
    public void testdisplayOrgListDisplayTag() {
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
        assertNotNull(popUpAction.getOrgs());
        assertEquals(5, popUpAction.getOrgs().size());
    }
}