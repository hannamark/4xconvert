package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests for trial status transitions.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class TrialStatusHistoryTest extends AbstractPaSeleniumTest {
    private String tomorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), 1));
    private String today = MONTH_DAY_YEAR_FMT.format(new Date());
    private String yesterday = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), -1));

    @SuppressWarnings("deprecation")
    @Test
    public void testHistory() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        clickAndWait("link=Trial Status");

        // Verify History button
        selenium.click("xpath=//span[normalize-space(text())='History']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("row_info", 15);

        // Validation should have run upon entry
        assertTrue(selenium
                .isElementPresent("xpath=//div[@class='confirm_msg']"));
        assertEquals("Message. All Statuses and Status Transitions are valid.",
                selenium.getText("xpath=//div[@class='confirm_msg']").trim());

        // Verify table column headers.
        assertEquals("Status Date",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[1]")
                        .trim());
        assertEquals("Status",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[2]")
                        .trim());
        assertEquals("Comments",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[3]")
                        .trim());
        assertEquals("Validation Messages",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[4]")
                        .trim());
        assertEquals("Actions",
                selenium.getText("xpath=//table[@id='row']/thead/tr/th[5]")
                        .trim());

        // Verify initial status is present.
        verifyStatusRow(1, today, "Approved", "", "");

        // Edit it back to In Review.
        selenium.click("xpath=//table[@id='row']/tbody/tr/td[5]/a[1]");
        waitForElementToBecomeVisible(By.id("edit-dialog"), 2);
        selenium.select("id=statusCode", "label=In Review");
        selenium.type("statusDate", yesterday);
        selenium.type("reason", "This must be ignored");
        selenium.type("comment", "Back to In Review");
        selenium.click("xpath=//div[@id='edit-dialog']//input[@value='Save']");
        waitForPageToLoad();
        waitForElementToBecomeVisible(By.xpath("//div[@class='confirm_msg']"),
                15);
        assertEquals("Message. Trial status record has been updated.", selenium
                .getText("xpath=//div[@class='confirm_msg']").trim());

        // Verify update
        verifyStatusRow(1, yesterday, "In Review", "Back to In Review", "");
        assertEquals(1, getTrialStatusHistory(trial).size());
        verifyCurrentStatusInDB(trial, DateUtils.addDays(new Date(), -1),
                "IN_REVIEW", "Back to In Review", "");

    }

    @SuppressWarnings("deprecation")
    private void verifyCurrentStatusInDB(TrialInfo trial, Date date,
            String code, String comments, String whyStopped)
            throws SQLException {
        assertEquals(code, getCurrentTrialStatus(trial).statusCode);
        assertEquals(comments, getCurrentTrialStatus(trial).comments);
        assertTrue(DateUtils.isSameDay(date,
                getCurrentTrialStatus(trial).statusDate));
        assertTrue(StringUtils.isBlank(whyStopped) ? StringUtils
                .isBlank(getCurrentTrialStatus(trial).whyStopped) : whyStopped
                .equals(getCurrentTrialStatus(trial).whyStopped));
    }

    @SuppressWarnings("deprecation")
    private void verifyStatusRow(int row, String date, String code,
            String comments, String valMsg) {
        assertEquals(
                date,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[1]")
                        .trim());
        assertEquals(
                code,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[2]")
                        .trim());
        assertEquals(
                comments,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[3]")
                        .trim());
        assertEquals(
                valMsg,
                selenium.getText(
                        "xpath=//table[@id='row']/tbody/tr[" + row + "]/td[4]")
                        .trim());
    }

}
