package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests for trial status transitions.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class TrialStatusTest extends AbstractTrialStatusTest {

    @Test
    public void testAbstractionValidationWithErrors() throws SQLException {
        TrialInfo trial = createTrialAndAccessStatusPage("admin-ci");

        // Adding In Review next to Approved is an error.
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        insertStatus("In Review", today, "", "New status");
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        selectTrial(false, trial);
        clickAndWait("link=Abstraction Validation");
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Trial status transition errors were found.']"));
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Select Trial Status from Administrative Data menu, then click History.']"));

    }

    @Test
    public void testAbstractionValidationWithWarnings() throws SQLException {
        TrialInfo trial = createTrialAndAccessStatusPage("admin-ci");

        insertStatus("Temporarily Closed to Accrual", today,
                "Temporarily Closed to Accrual", "New status");
        // Close History
        clickAndWait("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        selectTrial(false, trial);

        clickAndWait("link=Abstraction Validation");
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Trial status transition warnings were found.']"));
        assertTrue(selenium
                .isElementPresent("xpath=//td[text()='Select Trial Status from Administrative Data menu, then click History.']"));

    }

    /**
     * Tests the standard trial transitions from In Review to Complete, with
     * stops at Approved, Active, Closed to Accrual & Closed to Accrual and
     * Intervention.
     * 
     * @throws SQLException
     */
    @Test
    public void testStandardTrialStatusTransitions() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Approved", false, false, null);
        changeStatus("Active", true, false, null);
        changeStatus("Closed to Accrual", false, false, null);
        changeStatus("Closed to Accrual and Intervention", false, false, null);
        changeStatus("Complete", false, true, null);
    }

    /**
     * Tests going from In Review to Active to Complete.
     * 
     * @throws SQLException
     */
    @Test
    public void testSkippedCompleteStatus() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Active", true, false, null);
        changeStatus("Complete", false, true, null);
    }

    /**
     * Tests going from In Review to Active to Administratively Complete.
     * 
     * @throws SQLException
     */
    @Test
    public void testSkippedAdministrativelyCompleteStatus() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Active", true, false, null);
        changeStatus("Administratively Complete", false, true,
                "Administratively Complete Reason.");
    }

    @SuppressWarnings("deprecation")
    private void changeStatus(String statusName, boolean startDateActual,
            boolean completionDateActual, String statusReason) {
        selenium.select("id=currentTrialStatus", "label=" + statusName);

        if (startDateActual) {
            selenium.type("id=startDate", yesterday);
            selenium.click("id=startDateTypeActual");
        }

        if (completionDateActual) {
            selenium.click("id=primaryCompletionDateTypeActual");
            selenium.type("id=primaryCompletionDate", today);
        }

        if (StringUtils.isNotEmpty(statusReason)) {
            selenium.type("id=statusReason", statusReason);
        }
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
    }

    @Test
    public void testStandardTrialStatusTransitionsForScientificAbs()
            throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsScientificAbstractor();
        searchSelectAndAcceptTrial(trial.title, false, true);
        clickAndWait("link=Trial Status");
        changeStatus("Approved", false, false, null);
        changeStatus("Active", true, false, null);
        changeStatus("Closed to Accrual", false, false, null);
        changeStatus("Closed to Accrual and Intervention", false, false, null);
        changeStatus("Complete", false, true, null);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTrialHistoryForScientificAbs() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsScientificAbstractor();
        searchSelectAndAcceptTrial(trial.title, false, true);
        clickAndWait("link=Trial Status");
        clickAndWait("link=History");
        assertTrue(selenium.isTextPresent("Status History"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTrialStatusHistoryBeforeCheckOut() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);
        checkInTrialAsAdminAbstractor();
        clickAndWait("link=Trial Status");
        assertFalse(selenium.isTextPresent("Save"));

        clickAndWait("link=History");
        assertTrue(selenium.isTextPresent("Status History"));
        assertFalse(selenium.isTextPresent("Add New Status"));
        assertFalse(selenium.isTextPresent("Validate Status Transactions"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorLogic() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Status");

        // Move to Active: no errors.
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(driver.findElements(By.className("error_msg")).isEmpty());
        assertEquals("ACTIVE", getCurrentTrialStatus(trial).statusCode);

        // Move to Withdrawn produces an error: bad transition.
        selenium.select("id=currentTrialStatus", "label=Withdrawn");
        selenium.type("statusReason", "Just testing.");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));
        assertEquals("WITHDRAWN", getCurrentTrialStatus(trial).statusCode);

        // Verify pop-up (Slide 24).
        waitForElementById("displaySuAbstractorAutoCheckoutMessage", 5);
        assertTrue(selenium
                .isVisible("id=displaySuAbstractorAutoCheckoutMessage"));
        assertEquals(
                "The system has checked-out this trial under your name because Trial Status Transition errors were found. Trial record cannot be checked-in until all Status Transition Errors have been resolved. Please use the Trial Status History button to review and make corrections, or Cancel to dismiss this message.",
                selenium.getText("id=displaySuAbstractorAutoCheckoutMessage")
                        .trim());
        assertEquals(
                "Trial Status Validation",
                selenium.getText(
                        "ui-dialog-title-displaySuAbstractorAutoCheckoutMessage")
                        .trim());
        // Verify History button
        selenium.click("xpath=//button/span[normalize-space(text())='Trial Status History']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("row", 5);
        selenium.click("xpath=//span[normalize-space(text())='Cancel']");
        driver.switchTo().defaultContent();

        clickAndWait("link=Check-Out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());
        assertTrue(StringUtils.isBlank(selenium
                .getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]")));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorLogicNoAutoCheckOutIfAlreadyCheckedOut()
            throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        logoutUser();

        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Status");

        // Move to Withdrawn produces an error: bad transition.
        selenium.select("id=currentTrialStatus", "label=Closed to Accrual");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors and Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));

        // Verify pop-up (Slide 24).
        assertFalse(selenium
                .isVisible("id=displaySuAbstractorAutoCheckoutMessage"));
        clickAndWait("link=Check-Out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("admin-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTransitionValidationService() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        clickAndWait("link=Trial Status");

        // Merely entering a comment should not affect the status history.
        selenium.type("additionalComments", "Just a comment");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        clickAndWait("link=Trial Status");
        assertEquals("Just a comment", selenium.getValue("additionalComments"));
        List<TrialStatus> hist = getTrialStatusHistory(trial);
        assertEquals(1, hist.size());
        assertEquals("APPROVED", hist.get(0).statusCode);
        assertEquals("Just a comment", hist.get(0).comments);

        // Should be unable to move to a status with a future date
        TrialStatus statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", tomorrow);
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("Current Trial Status Date cannot be in the future."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Should be unable to move to a status with a past date
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", yesterday);
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("New current status date should be bigger/same as old date."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Active status cannot precede trial start
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", "04/15/2013");
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("If Current Trial Status is Active, Trial Start Date must be Actual and same as or smaller than Current Trial Status Date."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Complete status past trial end date.
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", "04/17/2018");
        selenium.select("id=currentTrialStatus", "label=Complete");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("If Current Trial Status is Completed, Primary Completion Date must be Actual."));
        assertTrue(selenium
                .isTextPresent("Current Trial Status Date cannot be in the future."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Move to Active: no errors.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(driver.findElements(By.className("error_msg")).isEmpty());
        assertEquals("ACTIVE", getCurrentTrialStatus(trial).statusCode);

        // Move to Closed to Accrual and Intervention: a warning due to missing
        // optional status.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus",
                "label=Closed to Accrual and Intervention");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved"));
        assertEquals("CLOSED_TO_ACCRUAL_AND_INTERVENTION",
                getCurrentTrialStatus(trial).statusCode);

        // Move to Withdrawn produces an error: bad transition.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus", "label=Withdrawn");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("A reason must be entered when the study status is set to Withdrawn."));
        selenium.type("statusReason", "Just testing.");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors and Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));
        assertEquals("WITHDRAWN", getCurrentTrialStatus(trial).statusCode);

    }

}
