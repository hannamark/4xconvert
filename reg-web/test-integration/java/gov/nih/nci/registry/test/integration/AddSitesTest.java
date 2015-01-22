package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author Denis G. Krylov
 */
@SuppressWarnings("deprecation")
public class AddSitesTest extends AbstractRegistrySeleniumTest {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 30;

   
    @Test
    public void testEmptyCriteriaError() throws SQLException {
        goToAddSitesScreen();
        clickAndWait("id=runSearchBtn");
        assertTrue(selenium
                .isTextPresent("Please provide a search criteria; "
                        + "otherwise the number of trials returned may be unmanageable"));
    }

    @Test
    public void testTooManyResults() throws SQLException {
        goToAddSitesScreen();
        for (int i = 0; i < 101; i++) {
            createAcceptedTrial(true);
        }
        selenium.type("id=officialTitle", "-");
        clickAndWait("id=runSearchBtn");
        assertTrue(selenium
                .isTextPresent("Error Message: Search criteria you provided has produced"));
        assertTrue(selenium
                .isTextPresent("(!) matching trials, which is too many to manage at once on this page."
                        + " The limit is 100. If you could, please revise the criteria to produce a smaller number of trials"
                        + " that will be more manageable"));
    }

    /**
     * 
     */
    private void goToAddSitesScreen() {
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");        
        waitForElementToBecomeVisible(By.xpath("//a[text()='Add Sites']"), WAIT_FOR_ELEMENT_TIMEOUT);
        clickAndWait("link=Add Sites");
    }

    @Test
    public void testResetButton() throws SQLException {
        goToAddSitesScreen();
        selenium.type("id=identifier", "NCI-2014-00001");
        selenium.type("id=officialTitle", "Trial Title");
        clickAndWait("id=resetBtn");
        assertEquals("", selenium.getValue("id=identifier"));
        assertEquals("", selenium.getValue("id=officialTitle"));
    }

    @Test
    public void testDataTableSort() throws SQLException {
        goToAddSitesScreen();

        deactivateAllTrials();
        TrialInfo trial1 = createAcceptedTrial(true);
        TrialInfo trial2 = createAcceptedTrial(true);

        selenium.type("id=identifier", "NCI-"); // should give only two trials.
        clickAndWait("id=runSearchBtn");

        Set<String> ascending = new TreeSet<String>(Arrays.asList(trial1.nciID,
                trial2.nciID));

        Iterator<String> it = ascending.iterator();
        String smaller = it.next();
        String larger = it.next();

        // Ascending
        clickAndWait("link=Trial Identifier");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[2]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[2]")
                .contains(larger));
        // Descending
        clickAndWait("link=Trial Identifier");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[2]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[2]")
                .contains(larger));

        // now sort by titles
        ascending = new TreeSet<String>(Arrays.asList(trial1.title,
                trial2.title));

        it = ascending.iterator();
        smaller = it.next();
        larger = it.next();

        // Ascending
        clickAndWait("link=Trial Title");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[3]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[3]")
                .contains(larger));
        // Descending
        clickAndWait("link=Trial Title");
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[2]//td[3]")
                .contains(smaller));
        assertTrue(selenium.getText(
                "xpath=//table[@id='trialsTable']/tbody/tr[1]//td[3]")
                .contains(larger));

    }

    @Test
    public void testAddSite() throws SQLException {
        TrialInfo trial = createTrialAndBeginAddingSites();

        // Select Investigator
        searchAndSelectPerson(
                By.id("trial_" + trial.id + "_site_0_pi_lookupBtn"), "John",
                "Doe");
        assertTrue(selenium.isTextPresent("Doe, John"));
        assertEquals("1",
                selenium.getValue("id=trial_" + trial.id + "_site_0_pi_poid"));

        selenium.type("id=trial_" + trial.id + "_site_0_localID", "XYZ0001");
        selenium.type("id=trial_" + trial.id + "_site_0_pgcode", "PG0001");
        selenium.select("id=trial_" + trial.id + "_site_0_status",
                "Active");
        selenium.type("id=trial_" + trial.id + "_site_0_statusDate", today);
        clickAndWaitAjax("id=trial_" + trial.id + "_site_0_addStatusBtn");
        waitForElementToBecomeVisible(
                By.id("trial_" + trial.id + "_site_0_trialStatusHistoryTable"),
                WAIT_FOR_ELEMENT_TIMEOUT);
        
        clickAndWait("id=saveBtn");
        waitForElementById("summaryTable", WAIT_FOR_ELEMENT_TIMEOUT);

        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[1]")
                .contains(trial.nciID));
        assertTrue(selenium
                .getText("xpath=//table[@id='summaryTable']/tbody/tr[1]//td[2]")
                .contains(
                        "National Cancer Institute Division of Cancer Prevention"));
        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[3]")
                .contains("XYZ0001"));
        assertTrue(selenium.getText(
                "xpath=//table[@id='summaryTable']/tbody/tr[1]//td[4]")
                .contains("SUCCESS"));

        driver.findElement(By.className("fa-thumbs-up")).click();
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Search Trials"));
        assertNotNull(findParticipatingSite(trial,
                "National Cancer Institute Division of Cancer Prevention",
                "XYZ0001"));

    }

    /**
     * @return
     * @throws SQLException
     */
    private TrialInfo createTrialAndBeginAddingSites() throws SQLException {
        goToAddSitesScreen();

        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial(true);

        selenium.type("id=identifier", trial.nciID);
        clickAndWait("id=runSearchBtn");
        waitForElementById("trialsTable", WAIT_FOR_ELEMENT_TIMEOUT);

        // Make sure tooltip for "+" button is there.
        hover(By.id("plussign_" + trial.id));
        pause(1000);
        assertTrue(selenium.isTextPresent("Click here to add sites to "
                + trial.nciID));

        // click on "+" to start adding sites
        selenium.click("xpath=//table[@id='trialsTable']/tbody/tr[1]//td[1]//i");
        waitForElementById("saveCancelDiv", WAIT_FOR_ELEMENT_TIMEOUT);
        pause(1000);

        assertNotNull(driver.findElement(By.className("popover-content")));
        assertTrue(selenium
                .isTextPresent("If you change your mind about entering a particular site, simply leave all the fields empty and it will be ignored on save."));
        assertEquals("3",
                selenium.getValue("id=trial_" + trial.id + "_site_0_org_poid"));

        // Make sure tooltip for PI lookup button is there.
        hover(By.id("trial_" + trial.id + "_site_0_pi_lookupBtn"));
        pause(1000);
        assertTrue(selenium.isTextPresent("Select PI for this site"));
        return trial;
    }

    @Test
    public void testAddSiteNothingEntered() throws SQLException {
        TrialInfo trial = createTrialAndBeginAddingSites();
        clickAndWait("id=saveBtn");
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("No participating sites created."));
        driver.findElement(By.className("fa-thumbs-up")).click();
    }

    @Test
    public void testAddSiteValidationMissingFields() throws SQLException {
        TrialInfo trial = createTrialAndBeginAddingSites();
        selenium.type("id=trial_" + trial.id + "_site_0_pgcode", "PG0001");
        clickAndWait("id=saveBtn");
        waitForTextToAppear(By.className("alert-danger"),
                "Local Trial Identifier is required", WAIT_FOR_ELEMENT_TIMEOUT);
        waitForTextToAppear(By.className("alert-danger"),
                "Please choose a Site Principal Investigator using the lookup",
                WAIT_FOR_ELEMENT_TIMEOUT);
        waitForTextToAppear(By.className("alert-danger"),
                "Please enter a value for Recruitment Status",
                WAIT_FOR_ELEMENT_TIMEOUT);
        waitForTextToAppear(By.className("alert-danger"),
                "A valid Recruitment Status Date is required",
                WAIT_FOR_ELEMENT_TIMEOUT);

    }

    private void searchAndSelectPerson(By buttonToClick, String fname,
            String lname) {
        driver.findElement(buttonToClick).click();
        waitForElementById("popupFrame", WAIT_FOR_ELEMENT_TIMEOUT);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", WAIT_FOR_ELEMENT_TIMEOUT);
        selenium.type("id=firstName", fname);
        selenium.type("id=lastName", lname);
        clickAndWait("id=search_person_btn");
        waitForElementById("row", WAIT_FOR_ELEMENT_TIMEOUT);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
    }
}
