package gov.nih.nci.registry.test.integration;

import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *Integration test for Program Code assignment screen.
 */
public class ProgramCodeAssignmentTest  extends AbstractRegistrySeleniumTest {

    private List<TrialInfo> trials = new ArrayList<TrialInfo>();

    /**
     * Test the program codes menu items
     * @throws Exception exception
     */
    @Test
    public void testProgramCodesMenu() throws Exception {
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
        assertTrue(selenium.isTextPresent("Manage Code Assignments"));
        assertTrue(selenium.isTextPresent("Manage Accruals"));
        logoutUser();
    }

    /**
     * Test changing family
     * @throws Exception
     */
    @Test
    public void testChangeFamily() throws Exception {
        //When I first access search screen
        accessManageCodeAssignmentsScreen();
        // I see empty table
        selenium.isTextPresent("Showing 0 to 0 of 0 entries");

        //when I change family
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");

        //then I should see associated trials.
        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trial4Title + "']"), 10);

        TrialInfo trial3 = trials.get(3);
        //when I filter trial details.
        selenium.typeKeys("//div[@id='trialsTbl_filter']/descendant::label/descendant::input", trial3.nciID);

        //then I should see filtered set of trials
        waitForElementToGoAway(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trial4Title + "']"), 10);

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trial3.title + "']"), 10);

        logoutUser();

    }

    @Test
    public void testVerifyAcessPrivilege() {
        //login as non site admin
        loginAsSubmitter();

        //and directly access program code assignment
        openAndWait("/registry/siteadmin/managePCAssignmentchangeFamily.action");

        //then I should get error page.

        selenium.isTextPresent("403");
        logoutUser();
    }

    @Test
    public void testVerifyExport() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");

        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trial4Title + "']"), 10);

        verifyCSVExport();
        verifyExcelExport();
        logoutUser();
    }


    @Test
    public void testUnassignProgramCode() throws Exception {
        accessManageCodeAssignmentsScreen();

        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");
        TrialInfo trial1 = trials.get(1);


        selenium.type("cfProgramCode", "PG4");

        //delete PG1 from trial 1
        waitForElementToBecomeAvailable(By.id(trial1.id + "_PG1_a"), 5);
        s.click(trial1.id + "_PG1_a");
        
        //make sure that "Code unassigned" text appear and then disappear after few seconds
        waitForElementToBecomeAvailable(By.id(trial1.id + "_PG1_span"), 5);

        //make sure the dynamically added elements are removed from DOM
        waitForElementToGoAway(By.id(trial1.id + "_PG1_img"), 10);
        waitForElementToGoAway(By.id(trial1.id + "_PG1_span"), 10);
        waitForElementToGoAway(By.id(trial1.id + "_PG1_a"), 10);

        //refresh the search result and make sure PG1 from trial 4 no longer show up
        dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByIndex(0);
        dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");

        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[2]"), 10);
        selenium.type("cfProgramCode", "PG4");

        waitForElementToGoAway(By.id(trial1.id + "_PG1_a"), 5);

        logoutUser();
    }

    @Test
    public void testParticipation() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");
        TrialInfo trial4 = trials.get(4);
        String trial4Title = trial4.title;
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trial4Title + "']"), 10);

        clickLinkAndWait("Show my participation");
        waitForElementToBecomeVisible(By.xpath("//table[@id='participationTbl']"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='participationTbl']/tbody//tr//td[text()='National Cancer Institute Division of Cancer Prevention']"), 10);
        selenium.isTextPresent("Abraham, Sony; Kennedy, James");
        selenium.click("xpath=//button/span[text()='Close']");
        waitForElementToBecomeInvisible(By.xpath("//table[@id='participationTbl']"), 10);

        logoutUser();
    }


    @Test
    public void testUnAssignMultipeAndAssignMultiple() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");

        //Initialli I see the buttons disabled
        verifyAssignUnassignButtonState(false);
        //When I click a row
        clickTableRow(1);

        //I see the buttons enabled
        verifyAssignUnassignButtonState(true);

        //When I deselect the row
        clickTableRow(1);
        //I see the buttons disabled
        verifyAssignUnassignButtonState(false);

        //Select 4 rows
        clickTableRow(1);
        clickTableRow(2);
        clickTableRow(3);
        clickTableRow(4);

        //I see the buttons enabled
        verifyAssignUnassignButtonState(true);

        //Also I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        //click the Unassign button
        clickAndWait("unassignPGCBtn");

        //Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrm-dialog"), 5);

        //on the popup select
        pickMultiSelectOptions("pgc-mrm-sel-div", Arrays.asList("PG2"), Arrays.asList("PG1", "PG3", "PG4"));

        //When I click Cancel
        clickAndWait("pgc-mrm-dialog-cancel");

        //Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrm-dialog"), 5);

        //the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        //And I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        //click the Unassign button
        clickAndWait("unassignPGCBtn");

        //Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrm-dialog"), 5);

        //on the popup select
        pickMultiSelectOptions("pgc-mrm-sel-div", Arrays.asList("PG2"), Arrays.asList("PG1", "PG3", "PG4"));

        //When I click OK
        clickAndWait("pgc-mrm-dialog-ok");

        //Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrm-dialog"), 5);

        //Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        selenium.isTextPresent("Program Codes were successfully unassigned");

        //And I see the confirmation go away after 5 seconds
        waitForElementToBecomeVisible(By.id("pgcInfo"), 10);


        //Now I see PG2 no longer associated with those rows
        verifyTrialProgramCodeAssociation(1, false, "PG2");
        verifyTrialProgramCodeAssociation(2, false, "PG2");
        verifyTrialProgramCodeAssociation(3, false, "PG2");
        verifyTrialProgramCodeAssociation(4, false, "PG2");

        //And I see the assign/unassign buttons disabled.
        verifyAssignUnassignButtonState(false);

        //Now select 2 rows
        clickTableRow(3);
        clickTableRow(4);

        //the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        //click the assign button
        clickAndWait("assignPGCBtn");

        //Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        //on the popup select
        pickMultiSelectOptions("pgc-madd-sel-div", Arrays.asList("PG2"), Arrays.asList("PG1", "PG3", "PG4"));

        //When I click Cancel
        clickAndWait("pgc-madd-dialog-cancel");

        //Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-madd-dialog"), 5);

        //the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        //And still the trials are not associated with PG2
        verifyTrialProgramCodeAssociation(3, false, "PG2");
        verifyTrialProgramCodeAssociation(4, false, "PG2");

        //click the assign button
        clickAndWait("assignPGCBtn");

        //Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-madd-dialog"), 5);

        //on the popup select
        pickMultiSelectOptions("pgc-madd-sel-div", Arrays.asList("PG2"), Arrays.asList("PG1", "PG3", "PG4"));

        //When I click Cancel
        clickAndWait("pgc-madd-dialog-ok");

        //Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-madd-dialog"), 5);

        //Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        selenium.isTextPresent("Program Codes were successfully assigned");

        //And I see the confirmation go away after 5 seconds
        waitForElementToBecomeVisible(By.id("pgcInfo"), 10);

        //Now I see PG2 no longer associated with first rows
        verifyTrialProgramCodeAssociation(1, false, "PG2");
        verifyTrialProgramCodeAssociation(2, false, "PG2");

        //And PG2 is associated with the 3rd and 4th row
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        //the assign button is disabled
        verifyAssignUnassignButtonState(false);


        logoutUser();
    }





    @Test
    public void testReplaceMultipeAndAssignMultiple() throws Exception {
        accessManageCodeAssignmentsScreen();
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");

        //Select 4 rows
        clickTableRow(1);
        clickTableRow(2);
        clickTableRow(3);
        clickTableRow(4);

        //I see the buttons enabled
        verifyAssignUnassignButtonState(true);

        //Also I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        //click the Replace button
        clickAndWait("replacePGCBtn");

        //Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrpl-dialog"), 5);

        //on the popup select
        pickMultiSelectOptions("pgc-mrpl-selone-div", Arrays.asList("PG2"), Arrays.asList("PG1", "PG3", "PG4"));

        //When I click Cancel
        clickAndWait("pgc-mrpl-dialog-cancel");

        //Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrpl-dialog"), 5);

        //the assign button is still enabled
        verifyAssignUnassignButtonState(true);

        //And I see PG2 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG2");
        verifyTrialProgramCodeAssociation(2, true, "PG2");
        verifyTrialProgramCodeAssociation(3, true, "PG2");
        verifyTrialProgramCodeAssociation(4, true, "PG2");

        //click the Replace button
        clickAndWait("replacePGCBtn");

        //Then the dialog showup
        waitForElementToBecomeVisible(By.id("pgc-mrpl-dialog"), 5);

        //on the popup select
        pickMultiSelectOptions("pgc-mrpl-selone-div", Arrays.asList("PG2"), Arrays.asList("PG1", "PG3", "PG4"));
        pickMultiSelectOptions("pgc-mrpl-seltwo-div", Arrays.asList("PG4"), Arrays.asList("PG1", "PG2", "PG3"));

        //When I click OK
        clickAndWait("pgc-mrpl-dialog-ok");

        //Then the dialog closes
        waitForElementToBecomeInvisible(By.id("pgc-mrpl-dialog"), 5);

        //Then I see confirmation
        waitForElementToBecomeVisible(By.id("pgcInfo"), 4);
        selenium.isTextPresent("Program Code was successfully replaced");

        //And I see the confirmation go away after 5 seconds
        waitForElementToBecomeVisible(By.id("pgcInfo"), 10);


        //Now I see PG2 no longer associated with those rows
        verifyTrialProgramCodeAssociation(1, false, "PG2");
        verifyTrialProgramCodeAssociation(2, false, "PG2");
        verifyTrialProgramCodeAssociation(3, false, "PG2");
        verifyTrialProgramCodeAssociation(4, false, "PG2");

        //And I see PG4 in those rows
        verifyTrialProgramCodeAssociation(1, true, "PG4");
        verifyTrialProgramCodeAssociation(2, true, "PG4");
        verifyTrialProgramCodeAssociation(3, true, "PG4");
        verifyTrialProgramCodeAssociation(4, true, "PG4");



        //And I see the assign/unassign buttons disabled.
        verifyAssignUnassignButtonState(false);



        logoutUser();
    }
    @Test
    public void testFilterByProgramCode() throws  Exception  {

        accessManageCodeAssignmentsScreen();

        //When I pass the program code filter parameter
        openAndWait("/registry/siteadmin/managePCAssignmentchangeFamily.action?pgcFilter=PG4");

        //Then I should see data filtered
        selenium.isTextPresent("PG4");

        //Also when I change the filter options
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");
        
        //I should see that the original filter is preserved
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trials.get(0).title + "']"), 10);
        selenium.isTextPresent("Showing 1 to 2 of 2 (filtered from 11 total entries)");

        //When a user changes the filter,
        selenium.type("cfProgramCode", "PG3");

        //then I should see data filtered
        int last = trials.size() - 1;
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='" + trials.get(last).title + "']"), 10);
        selenium.isTextPresent("Showing 1 to 3 of 3 (filtered from 11 total entries)");

        logoutUser();
    }


    @Test
    public void testFunnelFilter() throws Exception {

        accessManageCodeAssignmentsScreen();
        //Then funnel filter is not visible when family is not selected
        waitForElementToGoAway(By.id("fpgc-icon-a"), 0);


        //when I change family
        Select dropdown = new Select(driver.findElement(By.id("familyPoId")));
        dropdown.selectByVisibleText("National Cancer Institute");
        changePageLength("25");
        //then I should see associated trials.
        TrialInfo trial4 = trials.get(4);

        //And I see the funnel filter
        waitForElementToBecomeAvailable(By.id("fpgc-icon-a"), 5);
        clickAndWait("fpgc-icon-a");

        //Then I filter by PG4 in funnel
        pickMultiSelectOptions("fpgc-div", Arrays.asList("PG4"), Arrays.asList("PG1", "PG2", "PG3"));

        //filter on trial 4
        selenium.typeKeys("//div[@id='trialsTbl_filter']/descendant::label/descendant::input", trial4.nciID);
        waitForElementToBecomeAvailable(
                By.xpath(String.format("//table[@id='trialsTbl']/tbody//tr[@id='trial_%s']", trial4.id)), 5);

        //click on the program-code dropdown
        driver.findElement(By.id(trial4.id + "_tra")).click();

        //select PG4
        pickSelect2Item(trial4.id + "_trDiv", trial4.id + "_trSel", "Cancer Program4");

        // indicator disappears
        waitForElementToGoAway(By.id(trial4.id + "_PG4_img"), 10);

        //then we should have PG4 as an option
        waitForElementToBecomeAvailable(By.id(trial4.id + "_PG4_a"), 5);

        logoutUser();
    }

    /**
     * Will pick an option from select2
     * @param containerId - the container where the s2 is
     * @param selBoxId  - the id of the s2
     * @param optionLabel - the option to select
     */
    protected void pickSelect2Item(String containerId, String selBoxId, String optionLabel) {
        waitForElementToBecomeVisible(By.xpath(String.format("//div[@id='%s']", containerId)), 10);

        driver.findElement(By.cssSelector("span.select2-selection__arrow")).click();
        waitForElementToBecomeAvailable(By.xpath(String.format("//ul[@id='select2-%s-results']", selBoxId)), 5);
        driver.findElement(By.xpath(String.format("//ul[@id='select2-%s-results']//li[text()='%s']", selBoxId, optionLabel))).click();

    }

    /**
     * Will open the multislect and select few options and dselect others
     *
     * @param containerId    - the container where select is present
     * @param optsToSelect   - to select
     * @param optsToUnselect - to unslect
     */
    protected void pickMultiSelectOptions(String containerId, List<String> optsToSelect, List<String> optsToUnselect) {
        String buttonXPath = String.format("//div[@id='%s']/div/button", containerId);
        driver.findElement(By.xpath(buttonXPath)).click();
        for (String opt : optsToSelect) {
            WebElement el = driver.findElement(By.xpath(String.format("//div[@id='%s']//input[@value='%s']", containerId, opt)));
            if (!el.isSelected()) {
                el.click();
            }
        }
        for (String opt : optsToUnselect) {
           try {
               WebElement el = driver.findElement(By.xpath(String.format("//div[@id='%s']//input[@value='%s']", containerId, opt)));
               if (el.isSelected()) {
                   el.click();
               }
           } catch (NoSuchElementException ignore) {

           }
        }
        driver.findElement(By.xpath(buttonXPath)).click();

    }

    private void accessManageCodeAssignmentsScreen() throws Exception {
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        hoverLink("Program Codes");
        clickAndWait("link=Manage Code Assignments");
        recreateFamilies();
        recreateTrials();
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialsTbl']/tbody//tr//td[text()='No data available in table']"), 10);
    }


    private void recreateFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from family");
        qr.update(connection, String.format("INSERT INTO family( identifier, po_id, " +
                "rep_period_end, rep_period_len_months)VALUES (1, 1, '%s', 12)", date(180)));
    }

    private void associateProgramCodes() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from study_program_code");
        qr.update(connection, "delete from program_code");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG1', 'Cancer Program1', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG2', 'Cancer Program2', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG3', 'Cancer Program3', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG4', 'Cancer Program4', 'ACTIVE')");


        for (TrialInfo trial : trials) {
            qr.update(connection, "insert into study_program_code " +
                    "values((select identifier from program_code where program_code='PG1')," +
                    trial.id + ")");
            qr.update(connection, "insert into study_program_code " +
                    "values((select identifier from program_code where program_code='PG2')," +
                    trial.id + ")");
        }

        //associate PG3 to first two and last trial
        qr.update(connection, "insert into study_program_code " +
                "values((select identifier from program_code where program_code='PG3')," +
                trials.get(0).id + ")");
        qr.update(connection, "insert into study_program_code " +
                "values((select identifier from program_code where program_code='PG3')," +
                trials.get(1).id + ")");
        qr.update(connection, "insert into study_program_code " +
                "values((select identifier from program_code where program_code='PG3')," +
                trials.get(trials.size() -1).id + ")");

        //associate PG-4 to first two trials.
        qr.update(connection, "insert into study_program_code " +
                "values((select identifier from program_code where program_code='PG4')," +
                trials.get(0).id + ")");
        qr.update(connection, "insert into study_program_code " +
                "values((select identifier from program_code where program_code='PG4')," +
                trials.get(1).id + ")");
    }

    private void recreateTrials() throws Exception {
       deactivateAllTrials();
       for (int i = 1; i <= 11; i++) {
          TrialInfo trial =  createAcceptedTrial();
          addParticipatingSite(trial, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
          addSiteInvestigator(trial,  "National Cancer Institute Division of Cancer Prevention", "45" + i , "James",
                  "H", "Kennedy",  StudySiteContactRoleCode.SUB_INVESTIGATOR.name());
           addSiteInvestigator(trial, "National Cancer Institute Division of Cancer Prevention", "55" + i, "Sony",
                   "K", "Abraham", StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.name());
          trials.add(trial);

       }
       associateProgramCodes();
    }

    private void verifyCSVExport() throws  Exception {
        File csv = new File(downloadDir, "Manage Program Code Assignments.csv");
        if (csv.exists()) csv.delete();
        assertFalse(csv.exists());
        clickLinkAndWait("CSV");
        pause(1000);
        assertTrue(csv.exists());
        List<String> lines = FileUtils.readLines(csv);
        assertTrue((lines.size() - 1) == trials.size());
        for (TrialInfo trial : trials) {
            boolean trialPresent = false;
            for (String line : lines) {
                trialPresent |= line.startsWith("\"" + trial.nciID);
            }
            assertTrue(trialPresent);
        }
        if (csv.exists()) csv.delete();
    }


    private void verifyExcelExport() throws Exception  {
        File excel = new File(downloadDir, "Manage Program Code Assignments.xlsx");
        if (excel.exists()) excel.delete();
        assertFalse(excel.exists());
        clickLinkAndWait("Excel");
        pause(1000);

//        assertTrue(excel.exists());
        excel.delete();
    }

    //verify if the buttons are disabled or enabled.
    private void verifyAssignUnassignButtonState(boolean enabled) {
         assertTrue(driver.findElement(By.id("assignPGCBtn")).isEnabled() == enabled);
         assertTrue(driver.findElement(By.id("unassignPGCBtn")).isEnabled() == enabled);
         assertTrue(driver.findElement(By.id("replacePGCBtn")).isEnabled() == enabled);
    }

    private void clickTableRow(int rowNumber) {
        By by = By.xpath("//table[@id='trialsTbl']/tbody/tr[" + rowNumber + "]");
        waitForElementToBecomeAvailable(by, 5);
        driver.findElement(by).click();
    }


    private void verifyTrialProgramCodeAssociation(int rowNumber, boolean present, String pg) {
        By by = By.xpath("//table[@id='trialsTbl']/tbody/tr[" + rowNumber + "]/td[6]");
        waitForElementToBecomeAvailable(by, 5);
        assertTrue(driver.findElement(by).getText().contains(pg) == present);
    }


    private void changePageLength(String number) {
        waitForElementToBecomeAvailable(By.xpath("//table[@id='trialsTbl']/tbody/tr[1]"), 10);
        Select s = new Select(driver.findElement(By.xpath("//select[@name='trialsTbl_length']")));
        s.selectByVisibleText(number);
        waitForElementToBecomeAvailable(By.xpath("//table[@id='trialsTbl']/tbody/tr[11]"), 10);
    }

}
