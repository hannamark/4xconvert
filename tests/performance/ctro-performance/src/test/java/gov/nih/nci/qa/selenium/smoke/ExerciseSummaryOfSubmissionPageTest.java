package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageComponents.SummaryOfSubmissionResultsTable;
import gov.nih.nci.qa.selenium.PageObjects.SummaryOfSubmissionReportPage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.concurrent.TimeUnit;

import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseSummaryOfSubmissionPageTest {

	@Rule
	public TestName testName = new TestName();

	private WebDriver webDriver;
	Split testCaseSplit;

	@Before
	public void setUp() {
		// Measure the browser startup time.
		Split browserStart = SplitUtil.getBrowserStartSplit();

		// Start the browser.
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Stop the browser timer.
		browserStart.stop();

		// Start the timer for the currently executing test.
		testCaseSplit = SplitUtil.getTestSplit(testName.getMethodName());
	}

	@After
	public void tearDown() {
		// Measure the browser shutdown time.
		Split shutdownSplit = SplitUtil.getBrowserShutdownSplit();

		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}

		// Stop the browser timer.
		shutdownSplit.stop();

		// Stop the timer for the currently executing test.
		testCaseSplit.stop();

		// Output to a report.
		StopwatchUtil.printReport("parent");
	}

	@Test
	public void exercisePageObject() {
		SummaryOfSubmissionReportPage summaryOfSubmissionReportPage = new SummaryOfSubmissionReportPage(
				webDriver).get();
		summaryOfSubmissionReportPage.setSummaryOfSubmission("1/1/2009",
				"9/7/2011", false);
		SummaryOfSubmissionResultsTable resultsTable = summaryOfSubmissionReportPage
				.clickRunReportButton();

		System.out.println("getSubmittingSite "
				+ resultsTable.getSubmittingSite(0));

		System.out.println("getNumberOfOriginalSubmissions "
				+ resultsTable.getNumberOfOriginalSubmissions(0));

		System.out.println("getNumberOfSubmissions "
				+ resultsTable.getNumberOfSubmissions(0));

		System.out.println("getNumberOfSubmittedAmendments "
				+ resultsTable.getNumberOfSubmittedAmendments(0));

		summaryOfSubmissionReportPage.clickResetButton();
	}

}
