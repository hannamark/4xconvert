package gov.nih.nci.pa.test.integration;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class DashboardMenuTest extends AbstractPaSeleniumTest {
     
    public void testMenu() {
        loginAsResultsAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertTrue(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Abstractor"));
        
        logoutUser();
        loginAsSuperAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertFalse(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Super Abstractor"));
        
        logoutUser();
        loginAsAdminAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertFalse(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Admin Abstractor"));
        
        logoutUser();
        loginAsScientificAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertFalse(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Scientific Abstractor"));
    }
    
    
}
