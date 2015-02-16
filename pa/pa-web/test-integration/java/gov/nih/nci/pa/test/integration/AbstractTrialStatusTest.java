/**
 * 
 */
package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.openqa.selenium.By;

/**
 * @author dkrylov
 * 
 */
public abstract class AbstractTrialStatusTest extends AbstractPaSeleniumTest {
    protected String tomorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), 1));
    protected String today = MONTH_DAY_YEAR_FMT.format(new Date());
    protected String yesterday = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), -1));

    @SuppressWarnings("deprecation")
    protected void insertStatus(String newCode, String newDate, String reason,
            String comment) {
        selenium.click("xpath=//div[@class='actionsrow']//span[normalize-space(text())='Add New Status']");
        waitForElementToBecomeVisible(By.id("edit-dialog"), 2);
        assertEquals("Add Trial Status", selenium.getText("edit-dialog-header"));
        selenium.select("id=statusCode", "label=" + newCode);
        selenium.type("statusDate", newDate);
        selenium.type("reason", reason);
        selenium.type("comment", comment);
        selenium.click("xpath=//div[@id='edit-dialog']//input[@value='Save']");
        waitForPageToLoad();

    }

    /**
     * @return
     * @throws SQLException
     */
    protected TrialInfo createTrialAndAccessStatusPage() throws SQLException {
        return createTrialAndAccessStatusPage("admin-ci");
    }

    protected TrialInfo createTrialAndAccessStatusPage(String username)
            throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        login(username, "Coppa#12345");
        disclaimer(true);
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();   
        if (selenium.isElementPresent("link=Scientific Check Out")) {
            checkOutTrialAsScientificAbstractor();
        }
        clickAndWait("link=Trial Status");

        // Verify History button
        openHistory();
        return trial;
    }

    /**
     * 
     */
    protected void openHistory() {
        selenium.click("xpath=//span[normalize-space(text())='History']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("row_info", 15);
    }

}
