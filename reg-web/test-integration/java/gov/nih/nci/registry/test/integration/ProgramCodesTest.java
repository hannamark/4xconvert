package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;

public class ProgramCodesTest extends AbstractRegistrySeleniumTest {	
	
	/**
     * Test the program codes menu items
     */
    @Test
    public void testProgramCodesMenu() {
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);        
        hoverLink("Administration");   
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));        
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
        assertTrue(selenium.isTextPresent("Manage Code Assignments"));
    }    
    
    
    @Test
    public void testProgramCodesMenuNotVisibleForNonFamily() throws SQLException {    	   	
          
        String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        Number userID = createCSMUser(loginName);
        createRegistryUser(userID);
        assignUserToGroup(userID, "Submitter");
        changeUserToNoFamilyOrg(userID.toString());        
        login(loginName, "pass");
        handleDisclaimer(true); 
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");        
        assertFalse(selenium.isTextPresent("Program Codes"));       
        
    }
    
    @Test
    public void testProgramCodesMenuNotVisibleForNonSiteAdmin() throws SQLException {
    	loginAsSubmitter();
        handleDisclaimer(true);        
        pause(1000);                
        assertFalse(selenium.isTextPresent("Administration"));        
    }     
    
    private void changeUserToNoFamilyOrg(String csmUserId)
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Integer clinGovIdentifier = (Integer) runner
                .query(connection,
                        "select identifier from organization where name = 'ClinicalTrials.gov'",
                        new ArrayHandler())[0];
    	
        String sql = "update registry_user set affiliated_org_user_type = 'ADMIN', affiliated_org_id = " + clinGovIdentifier  +  " where csm_user_id = " + csmUserId;        
        runner.update(connection, sql);          
     
    }  
    

}