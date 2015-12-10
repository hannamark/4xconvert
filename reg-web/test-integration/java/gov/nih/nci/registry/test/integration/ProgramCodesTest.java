package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.pomock.MockFamilyService;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

public class ProgramCodesTest extends AbstractRegistrySeleniumTest {
	
	@Test
	public void testMultiFamilySwitch() throws SQLException {
		deleteAllExistingFamilies();
		
		// Creating a new user
		String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        Number userID = createCSMUser(loginName);
        createRegistryUser(userID);
        assignUserToGroup(userID, "Submitter");                
        login(loginName, "pass");
        handleDisclaimer(true);
        
        // Affliate the user to multi family org
        changeUserOrgToMultiFamily(); 
        pause(500);
        
        // Make user admin of registry
        changeUserToAdmin(userID.toString());
        
        // Re-login with admin privileges
        logoutUser();
        login(loginName, "pass");
        handleDisclaimer(true);  
        
        // access program codes screen        
        accessManageMasterListScreen();        
        
        // Change reporting length to "5" 
        switchReportingPeriodLength("5");         
        
        // switching to next family. DTO changes.
        switchFamily("Family Two");
        Select dropdown = new Select(driver.findElement(By.id("reportingPeriodLength")));
        WebElement option =  dropdown.getFirstSelectedOption();        
        assertEquals("12", option.getText());        
        
        // Back to recently changed family
        switchFamily("Family One");                        
        Select dropdown2 = new Select(driver.findElement(By.id("reportingPeriodLength")));
        WebElement option2 =  dropdown2.getFirstSelectedOption();   
        assertEquals("5", option2.getText());       
       
	}
	
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
    	loginAndAcceptDisclaimer();
    	accessManageMasterListScreen();
        pause(500);
        assertEquals(1, getCountOfFamily().intValue());
    }    
    
    /** 
     * @throws SQLException 
     */
    @Test
    public void testProgramCodesChangeDate() throws SQLException {
    	loginAndAcceptDisclaimer();
    	accessManageMasterListScreen();
    	String poId = getPoId();
    	Date currentDate = getReportingPeriodDate(poId);        
        changeReportingDate(); 
        Date newSavedDate = getReportingPeriodDate(poId);
        assertTrue(newSavedDate != null);
        assertFalse(DateUtils.isSameDay(currentDate, newSavedDate));
    }
    
    
    @Test
    public void testProgramCodesChangeReportingLength() throws SQLException {
    	loginAndAcceptDisclaimer();
    	accessManageMasterListScreen();        
        switchReportingPeriodLength("24");     
        String poId = getPoId();
        assertEquals(24, getReportingPeriodLength(poId).intValue());
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
    
    private void changeUserToAdmin(String csmUserId)
            throws SQLException {
    	QueryRunner runner = new QueryRunner();    	    	
        String sql = "update registry_user set affiliated_org_user_type = 'ADMIN' where csm_user_id = " + csmUserId;        
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
    
    private Date getReportingPeriodDate(String poId)
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Date currDate = (Date) runner
                .query(connection,
                        "select rep_period_end from family where po_id = " + poId,
                        new ArrayHandler())[0];  	
                  
        return currDate;
    }  
    
    private Integer getReportingPeriodLength(String poId)
            throws SQLException {
    	QueryRunner runner = new QueryRunner();
    	Integer repLength = (Integer) runner
                .query(connection,
                        "select rep_period_len_months from family where po_id = " + poId ,
                        new ArrayHandler())[0];  	
                  
        return repLength;
    }  
    
    private void changeUserOrgToMultiFamily() {
    	clickAndWait("css=a.nav-user");
        clickAndWait("css=a.account");
        driver.switchTo().frame(0);
    	moveElementIntoView(By.id("registryUserWebDTO.affiliateOrgField"));    
        JavascriptExecutor js = (JavascriptExecutor) driver;                
        js.executeScript("showPopWin('orgPoplookuporgs.action', 850, 550, loadAffliatedOrgDiv, 'Select Affiliated Organization')");
        pause(500);
        
        // Searching for organization
        driver.switchTo().frame(0);
        selenium.type("id=orgNameSearch", "Multi Family Org");             
        WebElement element = driver.findElement(By.id("search_organization_btn"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        waitForElementById("row", 20);       
        
        // Changing the organization
        WebElement selectButton = driver.findElement(By.xpath("//table[@id='row']/tbody/tr/td[8]/button"));
        JavascriptExecutor selectExecutor = (JavascriptExecutor)driver;
        selectExecutor.executeScript("arguments[0].click();", selectButton);          
        
        // Saving / updating the account
        driver.switchTo().defaultContent();
        driver.switchTo().frame(0);
        waitForElementToBecomeAvailable(By.xpath("//button[normalize-space(text())='Save']"), 20);
        WebElement save = driver.findElement(By.xpath("//button[normalize-space(text())='Save']"));
        JavascriptExecutor saveExecutor = (JavascriptExecutor)driver;
        
        
        saveExecutor.executeScript("arguments[0].click();", save);       
        
    }    
     
    private void switchReportingPeriodLength(String length) {
    	Select dropdown = new Select(driver.findElement(By.id("reportingPeriodLength")));
        dropdown.selectByVisibleText(length);
        waitForElementToBecomeVisible(By.id("reporting_flash"), 10);
        waitForElementToBecomeInvisible(By.id("reporting_flash"), 10); 
    }
    
    private void changeReportingDate() {
    	selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='reportingPeriodEndDate']]");
        clickOnFirstVisible(By.xpath("//td[@class='day']"));
        clickOnFirstVisible(By
                .xpath("//div[@class='datepicker']/button[@class='close']"));
        waitForElementToBecomeVisible(By.id("date_flash"), 10);
        waitForElementToBecomeInvisible(By.id("date_flash"), 10);
    }
    
    private void switchFamily(String family) {
    	Select dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        dropdown.selectByVisibleText(family);         
    }
    
    private String getPoId() {
    	WebElement hiddenInput = driver.findElement(By.id("poID"));
    	String value = hiddenInput.getAttribute("value");
    	return value;
    }

}