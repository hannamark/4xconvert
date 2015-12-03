package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class ProgramCodesTest extends AbstractRegistrySeleniumTest {	
	
	/**
     * Test the program codes menu items
	 * @throws SQLException 
     */
    @Test
    public void testProgramCodesMenu() throws SQLException {    	
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);        
        hoverLink("Administration");   
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));        
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
        assertTrue(selenium.isTextPresent("Manage Code Assignments"));
    }    
    
    /**
     * Test the program codes menu items
     * @throws SQLException 
     */
    @Test
    public void testProgramCodesInitialRecordCreation() throws SQLException {
    	deleteAllExistingFamilies();
    	assertEquals(0, getCountOfFamily().intValue());
    	accessManageMasterListScreen();
        pause(500);
        assertEquals(1, getCountOfFamily().intValue());
    }    
    
    /** 
     * @throws SQLException 
     */
    @Test
    public void testProgramCodesChangeDate() throws SQLException {
    	accessManageMasterListScreen();
    	Date currentDate = getReportingPeriodDate();        
        selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='reportingPeriodEndDate']]");
        clickOnFirstVisible(By.xpath("//td[@class='day']"));
        clickOnFirstVisible(By
                .xpath("//div[@class='datepicker']/button[@class='close']"));
        waitForElementToBecomeVisible(By.id("date_flash"), 10);
        waitForElementToBecomeInvisible(By.id("date_flash"), 10);  
        Date newSavedDate = getReportingPeriodDate();
        assertTrue(newSavedDate != null);
        assertFalse(DateUtils.isSameDay(currentDate, newSavedDate));
    }
    
    
    @Test
    public void testProgramCodesChangeReportingLength() throws SQLException {
    	accessManageMasterListScreen();        
        Select dropdown = new Select(driver.findElement(By.id("reportingPeriodLength")));
        dropdown.selectByVisibleText("24");
        waitForElementToBecomeVisible(By.id("reporting_flash"), 10);
        waitForElementToBecomeInvisible(By.id("reporting_flash"), 10);      
        assertEquals(24, getReportingPeriodLength().intValue());
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
    
    private void accessManageMasterListScreen() {
    	loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);        
        hoverLink("Administration");   
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);        
        hoverLink("Program Codes");
        clickAndWait("link=Manage Master List");
    }
    
    private void changeUserToNoFamilyOrg(String csmUserId)
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Integer clinGovIdentifier = (Integer) runner
                .query(connection,
                        "select identifier from organization where name = 'ClinicalTrials.gov'",
                        new ArrayHandler())[0];
    	
        String sql = "update registry_user set affiliated_org_user_type = 'ADMIN', affiliated_org_id = " 
        		+ clinGovIdentifier  +  " where csm_user_id = " + csmUserId;        
        runner.update(connection, sql);          
    }  
     
    private void deleteAllExistingFamilies()
            throws SQLException {
    	QueryRunner runner = new QueryRunner();    	
        String sql = "delete from family";        
        runner.update(connection, sql);         
    }  
    
    private Long getCountOfFamily()
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Long families = (Long) runner
                .query(connection,
                        "select count(*) from family",
                        new ArrayHandler())[0];  	
                  
        return families;
    }  
    
    private Date getReportingPeriodDate()
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Date currDate = (Date) runner
                .query(connection,
                        "select rep_period_end from family",
                        new ArrayHandler())[0];  	
                  
        return currDate;
    }  
    
    private Integer getReportingPeriodLength()
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Integer repLength = (Integer) runner
                .query(connection,
                        "select rep_period_len_months from family",
                        new ArrayHandler())[0];  	
                  
        return repLength;
    }  

}