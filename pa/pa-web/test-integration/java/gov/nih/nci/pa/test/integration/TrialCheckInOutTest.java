package gov.nih.nci.pa.test.integration;
import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;

import org.junit.Test;
/**
 *
 * @author Reshma Koganti
 */
public class TrialCheckInOutTest extends AbstractPaSeleniumTest {
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
