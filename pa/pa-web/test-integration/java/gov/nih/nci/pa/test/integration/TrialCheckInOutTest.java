package gov.nih.nci.pa.test.integration;
import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
/**
 *
 * @author Reshma Koganti
 */
public class TrialCheckInOutTest extends AbstractPaSeleniumTest {
    
    @Test
    public void testSorting() throws Exception {        
        TrialInfo trial = createSubmittedTrial();
        createStudyCheckout(trial, DateUtils.parseDate("02/02/2013", new String[] {"MM/dd/yyyy"}));
        createStudyCheckout(trial, DateUtils.parseDate("01/01/2014", new String[] {"MM/dd/yyyy"}));
        createStudyCheckout(trial, DateUtils.parseDate("03/03/2015", new String[] {"MM/dd/yyyy"}));
        
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Check-Out History");
        
        clickAndWait("link=Check-In Time");        
        assertEquals("02/02/2013 24:00", selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        assertEquals("01/01/2014 24:00", selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[4]"));
        assertEquals("03/03/2015 24:00", selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[4]"));
        
        clickAndWait("link=Check-Out Time");        
        assertEquals("02/02/2013 24:00", selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("01/01/2014 24:00", selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[2]"));
        assertEquals("03/03/2015 24:00", selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[2]"));

        
    }
    
    @Test
    public void testAdminCheckIn() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        clickAndWait("link=Admin Check Out");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        clickAndWait("link=Admin Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"), "Cancel");
        selenium.type("id=comments", "Test admin check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
    }
    
    @Test
    public void testScientificCheckIn() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        clickAndWait("link=Scientific Check Out");
        assertFalse(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        clickAndWait("link=Scientific Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"), "Cancel");
        selenium.type("id=comments", "Test Scientific check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
    }
    
    
    @Test
    public void testAdminScientificCheckIn() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check Out"));
        clickAndWait("link=Admin/Scientific Check Out");
        assertFalse(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        assertFalse(selenium.isElementPresent("link=Admin/Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check In"));
        clickAndWait("link=Admin/Scientific Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"), "Cancel");
        selenium.type("id=comments", "Test both Admin and Scientific check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check Out"));
        
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        assertTrue(selenium.isElementPresent("link=Admin/Scientific Check In"));
        
        clickAndWait("link=Scientific Check In");
        selenium.type("id=comments", "Test Scientific check in comments only");
        clickAndWait("class=btn btn-icon btn-primary");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertFalse(selenium.isElementPresent("link=Admin/Scientific Check Out"));
        
        
        clickAndWait("link=Admin Check In");
        selenium.type("id=comments", "Test Admin check in comments only");
        clickAndWait("class=btn btn-icon btn-default");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertFalse(selenium.isElementPresent("link=Admin/Scientific Check Out"));
    }
}
