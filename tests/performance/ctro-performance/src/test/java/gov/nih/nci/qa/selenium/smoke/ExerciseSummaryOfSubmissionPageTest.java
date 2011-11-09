package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageComponents.SummaryOfSubmissionResultsTable;
import gov.nih.nci.qa.selenium.PageObjects.SummaryOfSubmissionReportPage;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.concurrent.TimeUnit;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseSummaryOfSubmissionPageTest {

	private WebDriver webDriver;

	@Before
	public void setUp() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "startup").start();
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		split.stop();
	}

	@After
	public void tearDown() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "shutdown").start();
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
		split.stop();
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
