package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.enums.StudyContactRoleCode;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;



public class ResultsReportingStudyContactsTest  extends AbstractPaSeleniumTest {
    
    protected static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 15000
            : 2000;
    String baseUrl=null;

  public ResultsReportingStudyContactsTest() {
      baseUrl ="/pa/protected/resultsReportingContactexecute.action?studyProtocolId=";
  }
   

    @SuppressWarnings("deprecation")
    @Test
    public void testAddOrEditDesigneeSC() throws SQLException, ParseException {
        TrialInfo trial = createAcceptedTrial();
        loginAsResultsAbstractor();
        searchAndSelectTrial(trial.title);
        openAndWait(baseUrl+trial.id);
        
        long count = getRecordCount(trial.id, StudyContactRoleCode.DESIGNEE_CONTACT.name());
        assertTrue(count ==0);
        
        assertTrue(selenium.isTextPresent("No designee study contacts found."));
        
        addDesignee("CTEP", "JDOE01", "jdoe01@some.com", "703-111-1111");
        assertTrue(selenium.isTextPresent("Designee contact has been added/updated successfully."));
        waitForElementToBecomeVisible(By.xpath("//table[@id='dscWeb']/tbody/tr[1]"), 5);
        
        addDesignee("CTEP", "JDOE01", "jdoe02@some.com", "703-111-2222");
        assertTrue(selenium.isTextPresent("Designee contact has been added/updated successfully."));
        waitForElementToBecomeVisible(By.xpath("//table[@id='dscWeb']/tbody/tr[2]"), 5);
        
        clickAndWaitAjax("//table[@id='dscWeb']/tbody/tr[2]/td[6]/div/img");
        waitForPageToLoad();
        pause(1000);
        
        selenium.type("dscEmail", "newjdoe02@some.com");
        pause(100);
        
        selenium.click("//a[@id='addEditDSC']");
        pause(1000);
        waitForPageToLoad();
        waitForTextToAppear(By.xpath("//table[@id='dscWeb']/tbody/tr[2]/td[4]"), "newjdoe02@some.com", 5);
        
        selenium.click("//table[@id='dscWeb']/tbody/tr[1]/td[7]//input[@type='checkbox']");
        
        selenium.click("//a[@id='saveSC']");
        waitForPageToLoad();
        pause(1000);
        assertTrue(selenium.isTextPresent("Saved final changes to study contacts successfully"));
        assertFalse(selenium.isElementPresent("//table[@id='dscWeb']/tbody/tr[2]"));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testAddOrEditPioSC() throws SQLException, ParseException {
        TrialInfo trial = createAcceptedTrial();
        loginAsResultsAbstractor();
        searchAndSelectTrial(trial.title);
        openAndWait(baseUrl+trial.id);
        
        long count = getRecordCount(trial.id, StudyContactRoleCode.PIO_CONTACT.name());
        assertTrue(count ==0);
        
        assertTrue(selenium.isTextPresent("No PIO study contacts found."));
        
        addPio("JDOE01", "jdoe01@some.com", "703-111-1111");
        assertTrue(selenium.isTextPresent("PIO contact has been added/updated successfully."));
        waitForElementToBecomeVisible(By.xpath("//table[@id='pscWeb']/tbody/tr[1]"), 5);
        
        addPio("JDOE01", "jdoe02@some.com", "703-111-2222");
        assertTrue(selenium.isTextPresent("PIO contact has been added/updated successfully."));
        waitForElementToBecomeVisible(By.xpath("//table[@id='pscWeb']/tbody/tr[2]"), 5);
        
        clickAndWaitAjax("//table[@id='pscWeb']/tbody/tr[2]/td[4]/div/img");
        waitForPageToLoad();
        pause(1000);
        
        selenium.type("pscEmail", "newjdoe02@some.com");
        pause(100);
        
        selenium.click("//a[@id='addEditPSC']");
        pause(1000);
        waitForPageToLoad();
        waitForTextToAppear(By.xpath("//table[@id='pscWeb']/tbody/tr[2]/td[2]"), "newjdoe02@some.com", 5);
        
        selenium.click("//table[@id='pscWeb']/tbody/tr[1]/td[5]//input[@type='checkbox']");
        
        selenium.click("//a[@id='saveSC']");
        pause(1000);
        waitForPageToLoad();
        pause(1000);
        assertTrue(selenium.isTextPresent("Saved final changes to study contacts successfully"));
        assertFalse(selenium.isElementPresent("//table[@id='pscWeb']/tbody/tr[2]"));
    }
    
    
    
    private void addDesignee(String orgCtepId, String personCtepId, String email, String phone) {
        lookupOrg("lookupdesigneeorg", orgCtepId);
        lookupPerson("lookupdesigneeperson", personCtepId);
        selenium.type("dscPrsUserNm", "dscPrsUserNm");
        selenium.type("dscEmail", email);
        selenium.type("dscPhone", phone);
        selenium.type("dscExt", "123");
        selenium.type("dscComments", "dscComments");
        pause(100);
        
        selenium.click("//a[@id='addEditDSC']");
        pause(1000);
        waitForElementById("dscPrsUserNm", 5);
        pause(OP_WAIT_TIME);
        waitForPageToLoad();
    }
    
    private void addPio(String personCtepId, String email, String phone) {
        lookupPerson("lookuppioperson", personCtepId);
        selenium.type("pscEmail", email);
        selenium.type("pscPhone", phone);
        selenium.type("pscExt", "123");
        pause(100);
        
        selenium.click("//a[@id='addEditPSC']");
        pause(1000);
        waitForElementById("pscEmail", 5);
        pause(OP_WAIT_TIME);
        waitForPageToLoad();
    }
    
    private void lookupOrg(String orgFldId, String orgCtepId) {
        clickAndWaitAjax("//a[@id='" + orgFldId + "']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("orgCtepIdSearch", 15);
        selenium.type("orgCtepIdSearch", orgCtepId);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
    }
    
    private void lookupPerson(String prsnFldId, String personCtepId) {
        clickAndWaitAjax("//a[@id='" + prsnFldId + "']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("ctepID", 15);
        selenium.type("ctepID", personCtepId);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
    }
    
    
   
   private long getRecordCount(long trialId, String typeCode)
           throws SQLException {
       String sql;
       
           sql = "select count(*) from study_contact where study_protocol_identifier ="+trialId+" and role_code='"+typeCode+"'";
      
       long trialCount =0;
       QueryRunner runner = new QueryRunner();
       final List<Object[]> results = runner.query(connection, sql,
               new ArrayListHandler());
       for (Object[] row : results) {
            trialCount = (long) row[0];
          
       }
       return trialCount;
   }
   
}
