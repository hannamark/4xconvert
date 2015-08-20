package gov.nih.nci.registry.test.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import gov.nih.nci.registry.service.MockRestClientNCITServer;

public class ReportViewITest extends AbstractRegistrySeleniumTest{

    MockRestClientNCITServer mockRestClientNCITServer = new MockRestClientNCITServer();
    
  @Before
  public void setUp() throws Exception {
    super.setUp();
    mockRestClientNCITServer.startServer(20101);
  }

  @Test
  public void testSetDT4Report() throws Exception {

    loginAndAcceptDisclaimer();
    
    Number csmUserId = createCSMUser();
    assertNotNull(csmUserId);
    
    Number registryUserId = createRegistryUser(csmUserId);
    assertNotNull(registryUserId);
    
    navigateToReportViewerPage();
    
    WebElement selectElement = driver.findElement(By.id("drop-report-select-"+registryUserId.intValue()));
    if(selectElement != null ){
	
	Select reportSelect = new Select(selectElement);
	reportSelect.selectByIndex(0);
	
	driver.findElement(By.id("savereportuser")).click();
	boolean status = driver.findElement(By.className("alert-success")).getText().contains("Successful");
	
	checkReportViewerColumnUpdated(registryUserId);
	
	assert(status);
    }

    //waitForEmailsToArrive(1);
    logoutUser();

    int count = removeCSMUser(csmUserId);
    assert ( count > 0);
    count = removeRegistryUser(registryUserId);
    assert ( count > 0);
  }

  
  @Test
  public void testSearchUser() throws Exception {

    loginAndAcceptDisclaimer();
    Number csmUserId = createCSMUser();
    Number registryUserId = createRegistryUser(csmUserId);
    CharSequence userName = "test"+registryUserId;

    navigateToReportViewerPage();

    WebElement inputElement = driver.findElement(By.id("firstName"));
    if(inputElement != null ){
	
	inputElement.sendKeys(userName);
	driver.findElement(By.id("searchreportuser")).click();
	String text = driver.findElement(By.id("row")).getText();
	assert(text.contains(userName));
    }
    
    logoutUser();

    int count = removeCSMUser(csmUserId);
    assert ( count > 0);
    count = removeRegistryUser(registryUserId);
    assert ( count > 0);
  }
  
  
  private void navigateToReportViewerPage(){
      driver.findElement(By.linkText("Administration")).click();
      driver.findElement(By.id("viewReportViewersMenuOption")).click();
      assertEquals("Report Viewers", driver.findElement(By.cssSelector("h1.heading")).getText());
  }
  
  @After
  public void tearDown() throws Exception {
    super.tearDown();
    mockRestClientNCITServer.stopServer();
  }

}
