package gov.nih.nci.pa.test.integration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author dkrylov
 */
@SuppressWarnings({ "deprecation", "unused", "unchecked" })
public class DashboardTest extends AbstractTrialStatusTest {

    private static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 10000
            : 2000;

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        restoreDefaultWorkloadMilestones();
    }

    @Test
    public void testWorkloadSubmissionTypeFilter() throws Exception {
        deactivateAllTrials();

        TrialInfo abbreviated = createAcceptedTrial();
        TrialInfo complete = createAcceptedTrial();
        TrialInfo amendment = createAcceptedTrial();
        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + abbreviated.id);
        new QueryRunner()
                .update(connection,
                        "update study_protocol set proprietary_trial_indicator=false, amendment_date=now() where identifier="
                                + amendment.id);

        loginAsSuperAbstractor();
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Abbreviated" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Complete" });
        assertFalse(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Amendment" });
        assertFalse(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Complete", "Amendment" });
        assertFalse(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] { "Abbreviated", "Complete",
                "Amendment" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        applySubmissionTypeFilter(new String[] {});
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        // Check Refresh button
        applySubmissionTypeFilter(new String[] { "Abbreviated" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        clickAndWait("//input[@value='Refresh']");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertTrue(isTrialInWorkloadTab(complete));
        assertTrue(isTrialInWorkloadTab(amendment));

        // Make sure sorts do not reset the filter.
        String filledFunnelPath = "//i[@class='fa fa-filter fa-2x submissionType']";
        applySubmissionTypeFilter(new String[] { "Abbreviated" });
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        sort("Submission Type");
        sort("Submission Type");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Selecting the trial in Details tab and performing actions on it must
        // not reset filters either.
        clickAndWait(getXPathForNciIdInWorkloadTab(abbreviated));
        verifyDetailsTabActive();
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Trial Check-Out (Admin and Scientific) Successful"));
        s.click("link=Admin/Scientific Check In");
        s.type("comments", "No comments.");
        clickAndWait("//button[text()='Ok']");
        assertTrue(s
                .isTextPresent("Trial Check-In (Admin and Scientific) Successful"));
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Performing searches in Search tab must not reset filters.
        s.click("searchid");
        searchAndFindTrial(abbreviated);
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(abbreviated));
        assertFalse(isTrialInWorkloadTab(complete));
        assertFalse(isTrialInWorkloadTab(amendment));
        assertTrue(s.isElementPresent(filledFunnelPath));

    }

    private void applySubmissionTypeFilter(String[] types) {
        clickAndWait("//input[@value='Refresh']");
        verifyWorkfloadTabActive();

        // Find Funnel for this column; it will be unselected.
        String emptyFunnelPath = "//i[@class='fa fa-filter fa-2x fa-inverse submissionType']";
        String filledFunnelPath = "//i[@class='fa fa-filter fa-2x submissionType']";
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Click on Funnel
        verifySubmissionTypePopup(driver.findElement(By.xpath(emptyFunnelPath)));

        // Check boxes.
        for (WebElement el : driver.findElements(By
                .name("submissionTypeFilter"))) {
            if (ArrayUtils.contains(types, el.getAttribute("value"))) {
                el.click();
            }
        }

        // Submit.
        clickAndWait("//div[@aria-describedby='submission-type-filter']//button//span[text()='OK']");

        // If at least one option selected, funnel must turn black.
        if (types.length > 0) {
            assertTrue(s.isElementPresent(filledFunnelPath));
        } else {
            assertTrue(s.isElementPresent(emptyFunnelPath));
        }
    }

    private void verifySubmissionTypePopup(WebElement elementThatInvokesPopup) {
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("submission-type-filter"));
        assertEquals(
                "Submission Type",
                s.getText("//div[@aria-describedby='submission-type-filter']//span[@class='ui-dialog-title']"));
        assertEquals("Limit the results to the following submission types:",
                s.getText("//div[@id='submission-type-filter']/p"));

        // Check 'x' icon (close)
        clickAndWait("//div[@aria-describedby='submission-type-filter']//button[@title='Close']");
        assertFalse(s.isVisible("submission-type-filter"));

        // Check Cancel button
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("submission-type-filter"));
        clickAndWait("//div[@aria-describedby='submission-type-filter']//button//span[text()='Cancel']");
        assertFalse(s.isVisible("submission-type-filter"));

        // Verify 3 options are there.
        elementThatInvokesPopup.click();
        assertEquals(3, driver.findElements(By.name("submissionTypeFilter"))
                .size());
        assertTrue(s
                .isVisible("//label[@class='checkboxLabel' and text()='Abbreviated']"));
        assertTrue(s
                .isVisible("//label[@class='checkboxLabel' and text()='Amendment']"));
        assertTrue(s
                .isVisible("//label[@class='checkboxLabel' and text()='Complete']"));

    }

    @Test
    public void testWorkloadDateRangeFilter() throws Exception {
        deactivateAllTrials();
        TrialInfo second = createSubmittedTrial();
        TrialInfo first = createAcceptedTrial();
        loginAsSuperAbstractor();

        // Submitted On.
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-06-01 09:15.000' where identifier="
                                + first.id);
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-06-02 09:15.000' where identifier="
                                + second.id);
        verifyDateRangeFilter(first, "06/01/2015", second, "06/02/2015",
                "Submitted On");

        // Submission Plus 10 Business Days
        clickAndWait("link=Dashboard");
        verifyDateRangeFilter(first,
                getColumnValue(2, "Submission Plus 10 Business Days"), second,
                getColumnValue(1, "Submission Plus 10 Business Days"),
                "Submission Plus 10 Business Days");

        // Expected Abstraction Completion Date
        clickAndWait("link=Dashboard");
        verifyDateRangeFilter(first,
                getColumnValue(2, "Expected Abstraction Completion Date"),
                second,
                getColumnValue(1, "Expected Abstraction Completion Date"),
                "Expected Abstraction Completion Date");

        // Current On-Hold Date
        addOnHold(first, "SUBMISSION_INCOM", date("06/01/2015"), null,
                "Submitter");
        addOnHold(second, "SUBMISSION_INCOM", date("06/02/2015"), null,
                "Submitter");
        verifyDateRangeFilter(first, "06/01/2015", second, "06/02/2015",
                "Current On-Hold Date");

        // Accepted
        addDWS(second, "ACCEPTED");
        addMilestone(second, "SUBMISSION_ACCEPTED",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow, "Accepted");

        // ADMINISTRATIVE_PROCESSING_COMPLETED_DATE
        addMilestone(first, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        addMilestone(second, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Admin Abstraction Completed");

        // ADMINISTRATIVE_QC_COMPLETE
        addMilestone(first, "ADMINISTRATIVE_QC_COMPLETE", jdbcTs(new Date()));
        addMilestone(second, "ADMINISTRATIVE_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Admin QC Completed");

        // SCIENTIFIC_PROCESSING_COMPLETED_DATE
        addMilestone(first, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(new Date()));
        addMilestone(second, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Scientific Abstraction Completed");

        // SCIENTIFIC_QC_COMPLETE
        addMilestone(first, "SCIENTIFIC_QC_COMPLETE", jdbcTs(new Date()));
        addMilestone(second, "SCIENTIFIC_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow,
                "Scientific QC Completed");

        // READY_FOR_TSR
        addMilestone(first, "READY_FOR_TSR", jdbcTs(new Date()));
        addMilestone(second, "READY_FOR_TSR",
                jdbcTs(DateUtils.addDays(new Date(), 1)));
        verifyDateRangeFilter(first, today, second, tomorrow, "Ready for TSR");

    }

    private void verifyDateRangeFilter(TrialInfo first, String date1,
            TrialInfo second, String date2, String columnHeader) {
        clickAndWait("link=Dashboard");
        verifyWorkfloadTabActive();

        // Find Funnel for this column; it will be unselected.
        String emptyFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader
                + "']/../..//i[@class='fa fa-filter fa-2x fa-inverse']";
        String filledFunnelPath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader + "']/../..//i[@class='fa fa-filter fa-2x']";
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Click on Funnel
        verifyDateRangePopup(driver.findElement(By.xpath(emptyFunnelPath)));

        // Clear range
        s.type("dateFrom", "");
        s.type("dateTo", "");

        // Date of first trial should return only first trial.
        s.type("dateFrom", date1);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));

        // Date of second trial should return only 2nd trial. Funnel must change
        // to filled. Pop up dates should remain after submission.
        assertFalse(s.isElementPresent(emptyFunnelPath));
        s.click(filledFunnelPath);
        assertEquals(date1, s.getValue("dateFrom"));
        assertEquals(date1, s.getValue("dateTo"));
        s.type("dateFrom", date2);
        s.type("dateTo", date2);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertFalse(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));

        // Clearing both dates must reset filter.
        s.click(filledFunnelPath);
        s.type("dateFrom", "");
        s.type("dateTo", "");
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Type dates so that both trials included.
        s.click(emptyFunnelPath);
        s.type("dateFrom", date1);
        s.type("dateTo", date2);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));

        // Ensure Refresh resets filters.
        s.click(filledFunnelPath);
        s.type("dateFrom", date1);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        clickAndWait("//input[@value='Refresh']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(emptyFunnelPath));

        // Providing only one of the two dates must produce both trials.
        s.click(emptyFunnelPath);
        s.type("dateFrom", date1);
        s.type("dateTo", "");
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        s.click(filledFunnelPath);
        s.type("dateFrom", "");
        s.type("dateTo", date2);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertTrue(isTrialInWorkloadTab(second));
        clickAndWait("//input[@value='Refresh']");

        // Negative date range should not error out, but must produce no
        // results.
        s.click(emptyFunnelPath);
        s.type("dateFrom", date2);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(s.isTextPresent("Nothing found to display."));
        clickAndWait("//input[@value='Refresh']");

        // Ensure column sorting does not reset filters.
        s.click(emptyFunnelPath);
        s.type("dateFrom", date1);
        s.type("dateTo", date1);
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        sort(columnHeader);
        sort(columnHeader);
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Selecting the trial in Details tab and performing actions on it must
        // not reset filters either.
        clickAndWait(getXPathForNciIdInWorkloadTab(first));
        verifyDetailsTabActive();
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Trial Check-Out (Admin and Scientific) Successful"));
        s.click("link=Admin/Scientific Check In");
        s.type("comments", "No comments.");
        clickAndWait("//button[text()='Ok']");
        assertTrue(s
                .isTextPresent("Trial Check-In (Admin and Scientific) Successful"));
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(filledFunnelPath));

        // Performing searches in Search tab must not reset filters.
        s.click("searchid");
        searchAndFindTrial(second);
        s.click("workloadid");
        assertTrue(isTrialInWorkloadTab(first));
        assertFalse(isTrialInWorkloadTab(second));
        assertTrue(s.isElementPresent(filledFunnelPath));

    }

    private void verifyDateRangePopup(final WebElement elementThatInvokesPopup) {
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("date-range-filter"));
        assertEquals(
                "Date Filter",
                s.getText("//div[@aria-describedby='date-range-filter']//span[@class='ui-dialog-title']"));
        assertEquals(
                "Limit the results to the following date range (inclusive):",
                s.getText("//div[@id='date-range-filter']/p"));

        // Check 'x' icon (close)
        clickAndWait("//div[@aria-describedby='date-range-filter']//button[@title='Close']");
        assertFalse(s.isVisible("date-range-filter"));

        // Check Cancel button
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("date-range-filter"));
        clickAndWait("//div[@aria-describedby='date-range-filter']//button//span[text()='Cancel']");
        assertFalse(s.isVisible("date-range-filter"));

        // verify textboxes are there
        elementThatInvokesPopup.click();
        assertTrue(s.isVisible("dateFrom"));
        assertTrue(s.isVisible("dateTo"));

        // Verify Calendars come up.
        clickAndWait("//input[@id='dateFrom']/following-sibling::img");
        assertTrue(s.isVisible("ui-datepicker-div"));
        clickAndWait("//input[@id='dateFrom']/following-sibling::img");
        assertFalse(s.isVisible("ui-datepicker-div"));
        clickAndWait("//input[@id='dateTo']/following-sibling::img");
        assertTrue(s.isVisible("ui-datepicker-div"));
        clickAndWait("//input[@id='dateTo']/following-sibling::img");
        assertFalse(s.isVisible("ui-datepicker-div"));

        // Check basic date validation.
        assertFalse(s.isVisible("validationError"));
        s.type("dateFrom", "05/32/2015");
        s.click("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(s.isVisible("validationError"));
        assertTrue(s.isTextPresent("Invalid From Date: 05/32/2015"));
        s.click("//div[@aria-describedby='validationError']//button[@title='Close']");
        assertFalse(s.isVisible("validationError"));

        s.type("dateFrom", "");
        s.type("dateTo", "05/33/2015");
        s.click("//div[@aria-describedby='date-range-filter']//button//span[text()='OK']");
        assertTrue(s.isVisible("validationError"));
        assertTrue(s.isTextPresent("Invalid To Date: 05/33/2015"));
        s.click("//div[@aria-describedby='validationError']//button[@title='Close']");
        assertFalse(s.isVisible("validationError"));

    }

    @Test
    public void testWorkloadTab() throws Exception {
        deactivateAllTrials();

        TrialInfo acceptedTrial = createAcceptedTrial();
        TrialInfo submittedTrial = createSubmittedTrial();
        loginAsSuperAbstractor();

        clickAndWait("link=Dashboard");
        verifyWorkfloadTabActive();
        assertTrue(s.isTextPresent("2 trials found, displaying all trials."));

        // Verify column headers Super Abstractor sees
        verifySuperAbstractorWorkloadHeaders();

        // Admin/Scientific Abstractors see less.
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("link=Dashboard");
        verifyWorkfloadTabActive();
        verifyAdminScientificAbstractorWorkloadHeaders();
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("link=Dashboard");
        verifyWorkfloadTabActive();
        verifyAdminScientificAbstractorWorkloadHeaders();

        // Verify selection criteria (last milestone, excluding Rejected
        // trials).
        logoutPA();
        loginAsSuperAbstractor();
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(submittedTrial));
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        changePaProperty("dashboard.workload.milestones",
                "Submission Received Date");
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(submittedTrial));
        assertFalse(isTrialInWorkloadTab(acceptedTrial));
        changePaProperty("dashboard.workload.milestones",
                "Submission Acceptance Date");
        clickAndWait("link=Dashboard");
        assertFalse(isTrialInWorkloadTab(submittedTrial));
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        changePaProperty("dashboard.workload.milestones",
                "Submission Received Date,Submission Acceptance Date");
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(submittedTrial));
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        addDWS(submittedTrial, "REJECTED");
        addDWS(acceptedTrial, "REJECTED");
        clickAndWait("link=Dashboard");
        assertFalse(isTrialInWorkloadTab(submittedTrial));
        assertFalse(isTrialInWorkloadTab(acceptedTrial));
        restoreDefaultWorkloadMilestones();

        // Verify submission type.
        deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Submission Type", "Complete");
        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + acceptedTrial.id);
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Submission Type", "Abbreviated");
        new QueryRunner()
                .update(connection,
                        "update study_protocol set proprietary_trial_indicator=false, amendment_date=now() where identifier="
                                + acceptedTrial.id);
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Submission Type", "Amendment");

        // Submitted On
        deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-22 09:15.000' where identifier="
                                + acceptedTrial.id);
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Submitted On", "05/22/2015");
        verifyColumnValue(1, "Submission Plus 10 Business Days", "06/08/2015");
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-06-01 09:15.000' where identifier="
                                + acceptedTrial.id);
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Submitted On", "06/01/2015");
        verifyColumnValue(1, "Submission Plus 10 Business Days", "06/15/2015");

        // Expected Abstraction Completion Date & Business Days On Hold.
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-22 09:15.000' where identifier="
                                + acceptedTrial.id);
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/08/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "0");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "0");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "CTRP");
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/08/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "2");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "0");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "Submitter");
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/10/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "2");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "2");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/24/2015"),
                date("05/25/2015"), "Submitter");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/23/2015"),
                date("05/25/2015"), "CTRP");
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Expected Abstraction Completion Date",
                "06/10/2015");
        verifyColumnValue(1, "Business Days on Hold (CTRP)", "2");
        verifyColumnValue(1, "Business Days on Hold (Submitter)", "2");

        // Business Days Since Submitted
        final Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        new QueryRunner().update(connection,
                "update study_protocol set date_last_created=" + jdbcTs(date)
                        + " where identifier=" + acceptedTrial.id);
        clickAndWait("link=Dashboard");
        verifyColumnValue(
                1,
                "Business Days Since Submitted",
                cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                        && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY ? "1"
                        : "0");

        // Current On Hold Date.
        verifyColumnValue(1, "Current On-Hold Date", "");
        addOnHold(acceptedTrial, "SUBMISSION_INCOM", date("05/23/2015"), null,
                "CTRP");
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Current On-Hold Date", "05/23/2015");

        // Accepted
        verifyColumnValue(1, "Accepted", today);

        // Milestones
        verifyColumnValue(1, "Admin Abstraction Completed", "");
        verifyColumnValue(1, "Admin QC Completed", "");
        verifyColumnValue(1, "Scientific Abstraction Completed", "");
        verifyColumnValue(1, "Scientific QC Completed", "");
        verifyColumnValue(1, "Ready for TSR", "");
        addMilestone(acceptedTrial, "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(date, 1)));
        addMilestone(acceptedTrial, "ADMINISTRATIVE_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(date, 2)));
        addMilestone(acceptedTrial, "SCIENTIFIC_PROCESSING_COMPLETED_DATE",
                jdbcTs(DateUtils.addDays(date, 3)));
        addMilestone(acceptedTrial, "SCIENTIFIC_QC_COMPLETE",
                jdbcTs(DateUtils.addDays(date, 4)));
        addMilestone(acceptedTrial, "READY_FOR_TSR",
                jdbcTs(DateUtils.addDays(date, 5)));
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "Admin Abstraction Completed",
                fmt(DateUtils.addDays(date, 1)));
        verifyColumnValue(1, "Admin QC Completed",
                fmt(DateUtils.addDays(date, 2)));
        verifyColumnValue(1, "Scientific Abstraction Completed",
                fmt(DateUtils.addDays(date, 3)));
        verifyColumnValue(1, "Scientific QC Completed",
                fmt(DateUtils.addDays(date, 4)));
        verifyColumnValue(1, "Ready for TSR", fmt(DateUtils.addDays(date, 5)));

        // Checked out by
        verifyColumnValue(1, "Checked Out By", "");
        findAndSelectTrialInDashboard(acceptedTrial);
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin and Scientific) Successful"));
        clickAndWait("link=Dashboard");
        assertTrue(isTrialInWorkloadTab(acceptedTrial));
        verifyColumnValue(1, "Checked Out By", "CI, ctrpsubstractor (AS)");
        deactivateAllTrials();
        acceptedTrial = createAcceptedTrial();
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + acceptedTrial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        clickAndWait("//input[@value='Refresh']");
        verifyColumnValue(1, "Checked Out By", "admin-ci (AD)");
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + acceptedTrial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        clickAndWait("//input[@value='Refresh']");
        verifyColumnValue(1, "Checked Out By",
                "admin-ci (AD) scientific-ci (SC)");

        // Initial list sort is by Abstraction Expected Completion Date,
        // descending
        deactivateAllTrials();
        logoutPA();
        TrialInfo second = createAcceptedTrial();
        TrialInfo first = createAcceptedTrial();
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-05 09:15.000' where identifier="
                                + first.id);
        new QueryRunner()
                .update(connection,
                        "update study_protocol set date_last_created='2015-05-04 09:15.000' where identifier="
                                + second.id);
        loginAsSuperAbstractor();
        clickAndWait("link=Dashboard");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Expected Abstraction Completion Date");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort NCI ID.
        sort("NCI Trial Identifier");
        if (first.nciID.compareTo(second.nciID) < 0) {
            verifyColumnValue(1, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(2, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        } else {
            verifyColumnValue(2, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(1, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        }
        sort("NCI Trial Identifier");
        if (first.nciID.compareTo(second.nciID) < 0) {
            verifyColumnValue(2, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(1, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        } else {
            verifyColumnValue(1, "NCI Trial Identifier",
                    first.nciID.replaceFirst("NCI-", ""));
            verifyColumnValue(2, "NCI Trial Identifier",
                    second.nciID.replaceFirst("NCI-", ""));
        }

        // Sort Submission Type.
        new QueryRunner().update(connection,
                "update study_protocol set proprietary_trial_indicator=true where identifier="
                        + first.id);
        clickAndWait("//input[@value='Refresh']");
        sort("Submission Type");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submission Type");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort Submitted On.
        sort("Submitted On");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submitted On");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort Submission Plus 10 Business Days.
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort Business Days Since Submitted
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Submission Plus 10 Business Days");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by Business Days on Hold.
        addOnHold(first, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "CTRP");
        clickAndWait("//input[@value='Refresh']");
        sort("Business Days on Hold (CTRP)");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Business Days on Hold (CTRP)");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        addOnHold(first, "SUBMISSION_INCOM", date("05/26/2015"),
                date("05/27/2015"), "Submitter");
        clickAndWait("//input[@value='Refresh']");
        sort("Business Days on Hold (Submitter)");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Business Days on Hold (Submitter)");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by On Hold Date.
        addOnHold(first, "SUBMISSION_INCOM", date("05/27/2015"), null, "CTRP");
        clickAndWait("//input[@value='Refresh']");
        sort("Current On-Hold Date");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Current On-Hold Date");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by Submission Accepted
        addMilestone(first, "SUBMISSION_ACCEPTED",
                jdbcTs(DateUtils.addDays(date, 2)));
        clickAndWait("//input[@value='Refresh']");
        sort("Accepted");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Accepted");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Sort by all other milestones.
        checkSortByMilestone(first, second, "Admin Abstraction Completed",
                "ADMINISTRATIVE_PROCESSING_COMPLETED_DATE");
        checkSortByMilestone(first, second, "Admin QC Completed",
                "ADMINISTRATIVE_QC_COMPLETE");
        checkSortByMilestone(first, second, "Scientific Abstraction Completed",
                "SCIENTIFIC_PROCESSING_COMPLETED_DATE");
        checkSortByMilestone(first, second, "Scientific QC Completed",
                "SCIENTIFIC_QC_COMPLETE");
        checkSortByMilestone(first, second, "Ready for TSR", "READY_FOR_TSR");

        // Finally, sort by check out.
        logoutPA();
        loginAsAdminAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + first.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        logoutPA();
        loginAsScientificAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + second.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        clickAndWait("link=Dashboard");
        sort("Checked Out By");
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort("Checked Out By");
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

        // Verify CSV Export; firefox only.
        verifyWorkloadCSVExport();

    }

    /**
     * @throws SQLException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private void verifyWorkloadCSVExport() throws SQLException, IOException {
        deactivateAllTrials();
        TrialInfo trial = createAcceptedTrial();
        new QueryRunner().update(connection,
                "update study_protocol set date_last_created="
                        + jdbcTs(new Date()) + " where identifier=" + trial.id);
        logoutPA();
        loginAsSuperAbstractor();
        clickAndWait("link=Dashboard");
        verifyWorkfloadTabActive();

        // Export banner must be at top and at bottom.
        assertTrue(s
                .isElementPresent("xpath=//div[@id='workload']/div[@class='exportlinks'][1]"));
        assertTrue(s
                .isElementPresent("xpath=//div[@id='workload']/div[@class='exportlinks'][2]/preceding-sibling::table[@id='wl']"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//div[@id='workload']//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "workload.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();

            List<String> lines = FileUtils.readLines(csv);
            assertEquals(
                    "NCI Trial Identifier,Submission Type,Submitted On,Submission Plus 10 Business Days,Expected Abstraction Completion Date,Business Days Since Submitted,Business Days on Hold (CTRP),Business Days on Hold (Submitter),Current On-Hold Date,Accepted,Admin Abstraction Completed,Admin QC Completed,Scientific Abstraction Completed,Scientific QC Completed,Ready for TSR,Checked Out By,Lead Organization,Lead Org PO ID,ClinicalTrials.gov Identifier,CTEP ID,DCP ID,CDR ID,Amendment #,Summary 4 Funding,On Hold Date,Off Hold Date,On Hold Reason,On Hold Description,Trial Type,NCI Sponsored,Processing Status,Processing Status Date,Admin Check out Name,Admin Check out Date,Scientific Check out Name,Scientific Check out Date,CTEP/DCP,Submitting Organization,Submission Date,Last Milestone,Last Milestone Date,Submission Source,Processing Priority,Comments,This Trial is,Submission Received Date,Added By,Added On,Submission Acceptance Date,Added By,Added On,Submission Rejection Date,Added By,Added On,Submission Terminated Date,Added By,Added On,Submission Reactivated Date,Added By,Added On,Administrative Processing Completed Date,Added By,Added On,Administrative QC Completed Date,Added By,Added On,Scientific Processing Completed Date,Added By,Added On,Scientific QC Completed Date,Added By,Added On,Trial Summary Report Date,Added By,Added On,Submitter Trial Summary Report Feedback Date,Added By,Added On,Initial Abstraction Verified Date,Added By,Added On,On-going Abstraction Verified Date,Added By,Added On,Late Rejection Date,Added By,Added On",
                    lines.get(0));

            final String normalizedContent = lines.get(1).replaceAll("\\s+",
                    " ");
            final String expected = trial.nciID.replaceFirst("NCI-", "")
                    + ",Complete,"
                    + today
                    + ",\\d{2}/\\d{2}/\\d{4},\\d{2}/\\d{2}/\\d{4},\\d,0,0,,"
                    + today
                    + ",,,,,,,ClinicalTrials.gov,1,,,,,,,,,,,Interventional,No,Accepted,"
                    + today
                    + ",,,,,,ClinicalTrials.gov,"
                    + today
                    + ",Submission Acceptance Date,"
                    + today
                    + ",Other,2,,Ready for Admin ProcessingReady for Scientific Processing,"
                    + today + ",," + today + "," + today + ",," + today
                    + ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";

            System.out.println(normalizedContent);
            System.out.println(expected);
            assertTrue(normalizedContent.matches(expected));

            csv.delete();
        }
    }

    private void checkSortByMilestone(TrialInfo first, TrialInfo second,
            String column, String code) throws SQLException {
        // Initially both trials have empty dates, verify sort does not change
        // anything.
        clickAndWait("//input[@value='Refresh']");
        sort(column);
        String before = getColumnValue(1, "NCI Trial Identifier");
        sort(column);
        String after = getColumnValue(1, "NCI Trial Identifier");
        assertEquals(before, after);

        addMilestone(first, code, jdbcTs(DateUtils.addDays(new Date(), 1)));
        addMilestone(second, code, jdbcTs(DateUtils.addDays(new Date(), 2)));
        clickAndWait("//input[@value='Refresh']");

        sort(column);
        verifyColumnValue(1, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(2, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));
        sort(column);
        verifyColumnValue(2, "NCI Trial Identifier",
                first.nciID.replaceFirst("NCI-", ""));
        verifyColumnValue(1, "NCI Trial Identifier",
                second.nciID.replaceFirst("NCI-", ""));

    }

    private void sort(String columnHeader) {
        final String xpath = "//table[@id='wl']//th//a[normalize-space(text())='"
                + columnHeader + "']";
        moveElementIntoView(By.xpath(xpath));
        clickAndWait(xpath);
    }

    private String fmt(Date date) {
        return DateFormatUtils.format(date, "MM/dd/yyyy");
    }

    private Date date(String date) throws ParseException {
        return DateUtils.parseDate(date, new String[] { "MM/dd/yyyy" });
    }

    private void verifyColumnValue(int row, String header, String value) {
        assertEquals(value, getColumnValue(row, header));
    }

    private String getColumnValue(int row, String header) {
        // determine column index.
        int index = 1;
        while (!s.getText("//table[@id='wl']/thead/tr/th[" + index + "]//a")
                .equalsIgnoreCase(header)) {
            index++;
        }
        return s.getText(
                "//table[@id='wl']/tbody/tr[" + row + "]/td[" + index + "]")
                .replaceAll("\\s+", " ");
    }

    /**
     * @throws SQLException
     */
    private void restoreDefaultWorkloadMilestones() throws SQLException {
        changePaProperty(
                "dashboard.workload.milestones",
                "Submission Received Date,Submission Acceptance Date,Submission Reactivated Date,Administrative Processing Start Date,Administrative Processing Completed Date,Ready for Administrative QC Date,Administrative QC Start Date,Administrative QC Completed Date,Scientific Processing Start Date,Scientific Processing Completed Date,Ready for Scientific QC Date,Scientific QC Start Date,Scientific QC Completed Date,Ready for Trial Summary Report Date");
    }

    /**
     * @throws SQLException
     */
    private void changePaProperty(String name, String value)
            throws SQLException {
        new QueryRunner().update(connection, "update pa_properties set value='"
                + value + "' where name='" + name + "'");

    }

    private boolean isTrialInWorkloadTab(TrialInfo trial) {
        final String xpath = getXPathForNciIdInWorkloadTab(trial);
        return s.isElementPresent(xpath) && s.isVisible(xpath);
    }

    /**
     * @param trial
     * @return
     */
    private String getXPathForNciIdInWorkloadTab(TrialInfo trial) {
        final String xpath = "xpath=//table[@id='wl']//td[1]/a[normalize-space(text())='"
                + trial.nciID.replaceFirst("NCI-", "") + "']";
        return xpath;
    }

    /**
     * 
     */
    private void verifyAdminScientificAbstractorWorkloadHeaders() {
        verifyWorkloadColumnHeader(1, "NCI Trial Identifier");
        verifyWorkloadColumnHeader(2, "Submission Type");
        verifyWorkloadColumnHeader(3, "Submitted On");
        verifyWorkloadColumnHeader(4, "Expected Abstraction Completion Date");
        verifyWorkloadColumnHeader(5, "Current On-Hold Date");
        verifyWorkloadColumnHeader(6, "Accepted");
        verifyWorkloadColumnHeader(7, "Admin Abstraction Completed");
        verifyWorkloadColumnHeader(8, "Admin QC Completed");
        verifyWorkloadColumnHeader(9, "Scientific Abstraction Completed");
        verifyWorkloadColumnHeader(10, "Scientific QC Completed");
        verifyWorkloadColumnHeader(11, "Ready for TSR");
        verifyWorkloadColumnHeader(12, "Checked Out By");
    }

    /**
     * 
     */
    private void verifySuperAbstractorWorkloadHeaders() {
        verifyWorkloadColumnHeader(1, "NCI Trial Identifier");
        verifyWorkloadColumnHeader(2, "Submission Type");
        verifyWorkloadColumnHeader(3, "Submitted On");
        verifyWorkloadColumnHeader(4, "Submission Plus 10 Business Days");
        verifyWorkloadColumnHeader(5, "Expected Abstraction Completion Date");
        verifyWorkloadColumnHeader(6, "Business Days Since Submitted");
        verifyWorkloadColumnHeader(7, "Business Days on Hold (CTRP)");
        verifyWorkloadColumnHeader(8, "Business Days on Hold (Submitter)");
        verifyWorkloadColumnHeader(9, "Current On-Hold Date");
        verifyWorkloadColumnHeader(10, "Accepted");
        verifyWorkloadColumnHeader(11, "Admin Abstraction Completed");
        verifyWorkloadColumnHeader(12, "Admin QC Completed");
        verifyWorkloadColumnHeader(13, "Scientific Abstraction Completed");
        verifyWorkloadColumnHeader(14, "Scientific QC Completed");
        verifyWorkloadColumnHeader(15, "Ready for TSR");
        verifyWorkloadColumnHeader(16, "Checked Out By");
    }

    private void verifyWorkloadColumnHeader(int pos, String header) {
        assertEquals(header,
                s.getText("//table[@id='wl']/thead/tr/th[" + pos + "]//a"));
    }

    @SuppressWarnings("deprecation")
    private void verifyWorkfloadTabActive() {
        assertTrue(s.isVisible("workloadid"));
        assertTrue(s.isVisible("workload"));
        assertTrue(s.isVisible("wl"));
    }

    @SuppressWarnings("deprecation")
    private void verifyDetailsTabActive() {
        assertTrue(s.isVisible("detailsid"));
        assertTrue(s.isVisible("details"));

    }

    @SuppressWarnings({ "deprecation", "unused", "unchecked" })
    @Test
    public void testCsvExport() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        goToDashboardSearch();
        s.type("submittedOnOrAfter", "01/01/1990");
        clickAndWait("xpath=//a//span[text()='Search']");

        // Export banner must be at top and at bottom.
        assertTrue(s
                .isElementPresent("xpath=//div[@id='results']/div[@class='exportlinks'][1]"));
        assertTrue(s
                .isElementPresent("xpath=//div[@id='results']/div[@class='exportlinks'][2]/preceding-sibling::table[@id='results']"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//div[@id='results']//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "dashboardSearchResults.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();

            List<String> lines = FileUtils.readLines(csv);
            String content = FileUtils.readFileToString(csv);
            assertEquals(
                    "NCI Trial Identifier,Lead Organization,Lead Org PO ID,ClinicalTrials.gov Identifier,CTEP ID,DCP ID,CDR ID,Amendment #,Summary 4 Funding,On Hold Date,Off Hold Date,On Hold Reason,On Hold Description,Trial Type,NCI Sponsored,Processing Status,Processing Status Date,Admin Check out Name,Admin Check out Date,Scientific Check out Name,Scientific Check out Date,Submission Type,CTEP/DCP,Submitting Organization,Submission Date,Last Milestone,Last Milestone Date,Submission Source,Processing Priority,Comments,This Trial is,Submission Received Date,Added By,Added On,Submission Acceptance Date,Added By,Added On,Submission Rejection Date,Added By,Added On,Submission Terminated Date,Added By,Added On,Submission Reactivated Date,Added By,Added On,Administrative Processing Completed Date,Added By,Added On,Administrative QC Completed Date,Added By,Added On,Scientific Processing Completed Date,Added By,Added On,Scientific QC Completed Date,Added By,Added On,Trial Summary Report Date,Added By,Added On,Submitter Trial Summary Report Feedback Date,Added By,Added On,Initial Abstraction Verified Date,Added By,Added On,On-going Abstraction Verified Date,Added By,Added On,Late Rejection Date,Added By,Added On",
                    lines.get(0));

            final String normalizedContent = content.replaceAll("\\s+", " ");
            final String expected = trial.nciID.replaceFirst("NCI-", "")
                    + ",ClinicalTrials.gov,1,,,,,,,,,,,Interventional,No,Accepted,"
                    + today
                    + ",,,,,Original,,ClinicalTrials.gov,04/16/2014,Submission Acceptance Date,"
                    + today
                    + ",Other,2,,\"Ready for Admin Processing Ready for Scientific Processing\","
                    + today + ",," + today + "," + today + ",," + today
                    + ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";

            System.out.println(normalizedContent);
            System.out.println(expected);
            assertTrue(normalizedContent.contains(expected));

            csv.delete();
        }

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAdminCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin) Successful"));
        assertEquals("My Dashboard - Administrative Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("admin-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testScientificCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsScientificAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Scientific) Successful"));
        assertEquals("My Dashboard - Scientific Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("scientific-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        findAndSelectTrialInDashboard(trial);
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin and Scientific) Successful"));
        assertEquals("My Dashboard - Super Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSiteInterventionAndDiseaseCombinedSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Adding site along is not sufficient to find.
        findAndSelectTrialInDashboard(trial);
        addAnatomicSite("Kidney");
        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Adding site and intervention still not enough.
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Only when all 3 criterions present, trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        runCombinedSearch();
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

    }

    /**
     * 
     */
    private void runCombinedSearch() {
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        clickAndWait("link=Search");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDiseaseSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // trichothiodystrophy not found.
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add trichothiodystrophy to disease list, and now trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Trial found using both diseases, too
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // verify disease option removal.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        useSelect2ToUnselectOption("xerostomia");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("trichothiodystrophy");
        assertOptionSelected("xerostomia");
        clickAndWait("link=Reset");
        assertOptionNotSelected("trichothiodystrophy");
        assertOptionNotSelected("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDiseaseSearchWithDiseaseWidget() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // trichothiodystrophy not found.
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add trichothiodystrophy to disease list, and now trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Trial found using both diseases, too
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // verify disease option removal.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        selectDiseasesUsingWidget("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        useSelect2ToUnselectOption("xerostomia");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        selectDiseasesUsingWidget("trichothiodystrophy");
        selectDiseasesUsingWidget("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("trichothiodystrophy");
        assertOptionSelected("xerostomia");
        clickAndWait("link=Reset");
        assertOptionNotSelected("trichothiodystrophy");
        assertOptionNotSelected("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));
    }

    @SuppressWarnings("deprecation")
    private void selectDiseasesUsingWidget(String... diseases) {
        for (String disease : diseases) {
            selectDiseaseUsingWidget(disease);
        }
    }

    /**
     * @param disease
     */
    @SuppressWarnings("deprecation")
    private void selectDiseaseUsingWidget(String disease) {
        WebElement sitesBox = driver
                .findElement(By
                        .xpath("//span[preceding-sibling::select[@id='diseases']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-diseases-results"));
        sitesBox.sendKeys(disease);

        By xpath = By.xpath("//li[@role='treeitem']//td/b[text()='" + disease
                + "']/../../td[2]/i[@class='fa fa-sitemap']");
        waitForElementToBecomeAvailable(xpath, 10);

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(xpath));
        driver.findElement(xpath).click();

        driver.switchTo().frame("popupFrame");
        waitForElementToBecomeAvailable(By.id("pdq_tree_dialog"), 15);
        final By diseaseHighlightedInTree = By
                .xpath("//span[@class='pdq-tree-highlight' and text()='"
                        + disease + "']");
        waitForElementToBecomeAvailable(diseaseHighlightedInTree, 30);
        driver.findElement(diseaseHighlightedInTree).click();
        final By diseaseSelected = By
                .xpath("//span[@class='selectionFeaturedElement' and text()='"
                        + disease + "']");
        waitForElementToBecomeAvailable(diseaseSelected, 5);
        s.click("//span[@class='ui-icon ui-icon-closethick']");
        clickAndWait("//span[@class='add' and text()='Add']");
        driver.switchTo().defaultContent();
        waitForElementToBecomeAvailable(
                By.xpath(getXPathForSelectedOption(disease)), 10);
        assertOptionSelected(disease);
    }

    @SuppressWarnings("deprecation")
    private void addDisease(String disease) {
        clickAndWait("link=Disease/Condition");
        clickAndWait("link=Add");
        driver.switchTo().frame("popupFrame");
        s.type("disease", disease);
        clickAndWait("css=.search_inner_button");
        clickAndWait("//span[@class='breadcrumbHighlight' and text()='"
                + disease + "']");
        clickAndWait("//span[@class='add' and text()='Add']");
        driver.switchTo().defaultContent();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInterventionSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // tarenflurbil not found.
        findAndSelectTrialInDashboard(trial);
        addInterevention("pyroxamide");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // now found.
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // both found.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        addInterevention("pyroxamide");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "pyroxamide", "pyroxamide");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // add both, remove one, not found.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        useSelect2ToPickAnOption("interventions", "pyroxamide", "pyroxamide");
        useSelect2ToUnselectOption("tarenflurbil");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("tarenflurbil");
        clickAndWait("link=Reset");
        assertOptionNotSelected("tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

    }

    @SuppressWarnings("deprecation")
    private void addInterevention(String in) {
        clickAndWait("link=Interventions");
        clickAndWait("link=Add");
        clickAndWait("link=Look Up");
        driver.switchTo().frame("popupFrame");
        s.type("searchName", in);
        clickAndWait("link=Search");
        clickAndWait("//td[@class='action']//span[text()='Select']");
        driver.switchTo().defaultContent();
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Created."));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAnatomicSiteSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // Kidney not found.
        findAndSelectTrialInDashboard(trial);
        addAnatomicSite("Colon");
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Colon now found.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Colon & Kidney now found.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add Colon & Kidney, then remove Colon. Not found.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        useSelect2ToUnselectOption("Colon");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        goToDashboardSearch();
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("Colon");
        clickAndWait("link=Reset");
        assertOptionNotSelected("Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

    }

    @SuppressWarnings("deprecation")
    private void useSelect2ToUnselectOption(String option) {
        s.click("//li[@class='select2-selection__choice' and @title='" + option
                + "']/span[@class='select2-selection__choice__remove']");
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));

    }

    @SuppressWarnings("deprecation")
    private void useSelect2ToPickAnOption(String id, String sendKeys,
            String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-" + id + "-results"));
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        try {
            xpath = By.xpath("//li[@role='treeitem' and text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 3);
        } catch (TimeoutException e) {
            xpath = By.xpath("//li[@role='treeitem']//b[text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 15);
        }

        driver.findElement(xpath).click();
        assertOptionSelected(option);
    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    private void assertOptionSelected(String option) {
        assertTrue(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param option
     * @return
     */
    private String getXPathForSelectedOption(String option) {
        return "//li[@class='select2-selection__choice' and @title='" + option
                + "']";
    }

    @SuppressWarnings("deprecation")
    private void assertOptionNotSelected(String option) {
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param site
     */
    @SuppressWarnings("deprecation")
    private void addAnatomicSite(String site) {
        clickAndWait("link=Summary 4 Anatomic Site");
        clickAndWait("link=Add");
        s.select("anatomicSite_code", site);
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Created."));
    }

    /**
     * @param trial
     */
    @SuppressWarnings("deprecation")
    private void findAndSelectTrialInDashboard(TrialInfo trial) {
        goToDashboardSearch();
        searchAndFindTrial(trial);
    }

    /**
     * @param trial
     */
    private void searchAndFindTrial(TrialInfo trial) {
        s.type("submittedOnOrAfter", "01/01/1990");
        clickAndWait("xpath=//a//span[text()='Search']");
        clickAndWait("xpath=//table[@id='results']//td/a[normalize-space(text())='"
                + trial.nciID.replaceFirst("NCI-", "") + "']");
    }

    /**
     * 
     */
    protected void goToDashboardSearch() {
        clickAndWait("link=Dashboard");
        clickAndWait("searchid");
    }
}
