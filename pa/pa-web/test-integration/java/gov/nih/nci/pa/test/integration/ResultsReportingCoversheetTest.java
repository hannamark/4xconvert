package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

import com.dumbster.smtp.SmtpMessage;



public class ResultsReportingCoversheetTest  extends AbstractPaSeleniumTest {
    
    protected static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 15000
            : 2000;
    String baseUrl=null;

  public ResultsReportingCoversheetTest() {
      baseUrl ="/pa/protected/resultsReportingCoverSheetquery.action?studyProtocolId=";
  }
   

    @SuppressWarnings("deprecation")
    @Test
    public void testAddStudyDisc() throws SQLException, ParseException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        openAndWait(baseUrl+trial.id);
        
        long count = getRecordCount(trial.id,"DATA");
        assertTrue(count ==0);
        
        assertTrue(selenium.isTextPresent("No Data Discrepancies found."));
        
        assertTrue(selenium.isElementPresent("id=addDisc"));
        selenium.click("id=addDisc");
        
        pause(1000);
        waitForElementById("discrepancyType", 5);
        
        selenium.type("discrepancyType", "discrepancyType");
        selenium.type("actionTaken", "actionTaken");
        selenium.type("actionCompletionDate", today);
        pause(1000);
        
        selenium.click("xpath=//button/span[normalize-space(text())='Save']");
        pause(OP_WAIT_TIME);
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Data Discrepancy has been added successfully."));
        count = getRecordCount(trial.id,"DATA");
        assertTrue(count>0);
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testAddStudyChange() throws SQLException, ParseException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        openAndWait(baseUrl+trial.id);
        
        long count = getRecordCount(trial.id,"RECORD");
        assertTrue(count==0);
        
        assertTrue(selenium.isTextPresent("No study record changes found."));
        
        
        
        assertTrue(selenium.isElementPresent("id=addStudyRecord"));
        selenium.click("id=addStudyRecord");
        
        pause(1000);
        waitForElementById("changeType", 5);
        
        selenium.type("changeType", "changeType");
        selenium.type("actionTakenChangeType", "actionTakenChangeType");
        selenium.type("actionCompletionDateChangeType", today);
        pause(1000);
        
        selenium.click("xpath=//button/span[normalize-space(text())='Save']");
        pause(OP_WAIT_TIME);
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Record Change has been added successfully."));
        count = getRecordCount(trial.id,"RECORD");
        assertTrue(count>0);
    }
    
   @SuppressWarnings("deprecation")
   @Test
    public void testEditStudyDisc() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       long count = getRecordCount(trial.id,"DATA");
       assertTrue(count == 0);
       
       assertTrue(selenium.isTextPresent("No Data Discrepancies found."));
       
       assertTrue(selenium.isElementPresent("id=addDisc"));
       selenium.click("id=addDisc");
       
       pause(1000);
       waitForElementById("discrepancyType", 5);
       
       selenium.type("discrepancyType", "discrepancyType");
       selenium.type("actionTaken", "actionTaken");
       selenium.type("actionCompletionDate", today);
       pause(1000);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Data Discrepancy has been added successfully."));
       
       selenium.click("xpath=//table[@id='dataDisc']/tbody/tr[1]/td[4]");
       
       pause(1000);
       waitForElementById("discrepancyType", 5);
       
       selenium.type("discrepancyType", "discrepancyType123");
       selenium.type("actionTaken", "actionTaken123");
       selenium.type("actionCompletionDate", today);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       
       assertEquals(
               "discrepancyType123",
               selenium.getText("xpath=//table[@id='dataDisc']/tbody/tr[1]/td[1]"));

       assertEquals(
               "actionTaken123",
               selenium.getText("xpath=//table[@id='dataDisc']/tbody/tr[1]/td[2]"));
       
       count = getRecordCount(trial.id,"DATA");
       assertTrue(count>0);
    }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testEditStudyChange() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       long count = getRecordCount(trial.id,"RECORD");
       assertTrue(count==0);
       
       assertTrue(selenium.isTextPresent("No study record changes found."));
       
       assertTrue(selenium.isElementPresent("id=addStudyRecord"));
       selenium.click("id=addStudyRecord");
       
       pause(1000);
       waitForElementById("changeType", 5);
       
       selenium.type("changeType", "changeType");
       selenium.type("actionTakenChangeType", "actionTakenChangeType");
       selenium.type("actionCompletionDateChangeType", today);
       pause(1000);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Record Change has been added successfully."));
       
       selenium.click("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[4]");
       pause(1000);
       waitForElementById("changeType", 5);
       
       selenium.type("changeType", "changeType123");
       selenium.type("actionTakenChangeType", "actionTakenChangeType123");
       selenium.type("actionCompletionDateChangeType", today);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       
       assertEquals(
               "changeType123",
               selenium.getText("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[1]"));

       assertEquals(
               "actionTakenChangeType123",
               selenium.getText("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[2]"));
       
       count = getRecordCount(trial.id,"RECORD");
       assertTrue(count>0);
   }
   
   @SuppressWarnings("deprecation")
   @Test
    public void testDeleteStudyDisc() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       assertTrue(selenium.isTextPresent("No Data Discrepancies found."));
       
       assertTrue(selenium.isElementPresent("id=addDisc"));
       selenium.click("id=addDisc");
       
       pause(1000);
       waitForElementById("discrepancyType", 5);
       
       selenium.type("discrepancyType", "discrepancyType");
       selenium.type("actionTaken", "actionTaken");
       selenium.type("actionCompletionDate", today);
       pause(1000);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Data Discrepancy has been added successfully."));
       
       selenium.click("xpath=//table[@id='dataDisc']/tbody/tr[1]/td[5]//input");
       
       ((JavascriptExecutor) driver).executeScript("jQuery('#deleteType').val('disc'); jQuery('#coverSheetForm')[0].action = 'resultsReportingCoverSheetdelete.action'; jQuery('#coverSheetForm').submit();");
       waitForPageToLoad();
       
       assertTrue(selenium.isTextPresent("No Data Discrepancies found."));
       assertTrue(selenium.isTextPresent("Record(s) Deleted."));
       
       long count = getRecordCount(trial.id,"DATA");
       assertTrue(count==0);
    }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testDeleteStudyChange() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       assertTrue(selenium.isTextPresent("No study record changes found."));
       
       assertTrue(selenium.isElementPresent("id=addStudyRecord"));
       selenium.click("id=addStudyRecord");
       
       pause(1000);
       waitForElementById("changeType", 5);
       
       selenium.type("changeType", "changeType");
       selenium.type("actionTakenChangeType", "actionTakenChangeType");
       selenium.type("actionCompletionDateChangeType", today);
       pause(1000);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Record Change has been added successfully."));
       
       selenium.click("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[5]//input");
       ((JavascriptExecutor) driver).executeScript("jQuery('#deleteType').val('studyrecord'); jQuery('#coverSheetForm')[0].action = 'resultsReportingCoverSheetdelete.action'; jQuery('#coverSheetForm').submit();");
       waitForPageToLoad();
       
       assertTrue(selenium.isTextPresent("No study record changes found."));
       assertTrue(selenium.isTextPresent("Record(s) Deleted."));
       
       long count = getRecordCount(trial.id,"RECORD");
       assertTrue(count==0);
   
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testSaveFinalRecord() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       selenium.select("useStandardLanguage", "Yes");
       selenium.select("dateEnteredInPrs", "Yes");
       selenium.select("designeeAccessRevoked", "Yes");
       selenium.type("designeeAccessRevokedDate", today);
       selenium.select("changesInCtrpCtGov", "Yes");
       selenium.type("changesInCtrpCtGovDate", today);
       selenium.select("sendToCtGovUpdated", "Yes");
       
       selenium.click("id=saveFinal");
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent(" Record Updated."));
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testCoverSheetEmail() throws SQLException, ParseException, InterruptedException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       selenium.select("useStandardLanguage", "Yes");
       selenium.select("dateEnteredInPrs", "Yes");
       selenium.select("designeeAccessRevoked", "Yes");
       selenium.type("designeeAccessRevokedDate", today);
       selenium.select("changesInCtrpCtGov", "Yes");
       selenium.type("changesInCtrpCtGovDate", today);
       selenium.select("sendToCtGovUpdated", "Yes");
       
       selenium.click("id=saveFinal");
       waitForPageToLoad();     
       
       selenium.click("id=sendCoverSheetEmail");
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Email Sent Successfully."));
       
       waitForEmailsToArrive(1);
       Iterator emailIter = server.getReceivedEmail();
       SmtpMessage email = (SmtpMessage) emailIter.next();
       String subject = email.getHeaderValues("Subject")[0];
       String body = email.getBody().replaceAll("\\s+", " ")
               .replaceAll(">\\s+", ">");
       
       assertTrue(subject.equals("Trials Result Coversheet : "+trial.nciID));
       assertTrue(body.contains("<td>Results Designee Access Revoked?</td><td>YES "+today));
   }
   
   private long getRecordCount(long trialId, String typeCode)
           throws SQLException {
       String sql;
       
           sql = "select count(*) from study_notes where study_protocol_identifier ="+trialId+" and study_note_type='"+typeCode+"'";
      
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
