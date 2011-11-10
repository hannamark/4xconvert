package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageComponents.TrialsSubmittedByInstitutionResultsTable;
import gov.nih.nci.qa.selenium.PageObjects.TrialsSubmittedByInstitutionReportPage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseTrialsSubmittedByInstitutionReportPageTest {

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
	public void exercisePageObjects() {
		TrialsSubmittedByInstitutionReportPage trialsReportPage = new TrialsSubmittedByInstitutionReportPage(
				webDriver).get();

		ArrayList<String> institutions = new ArrayList<String>();
		institutions.add("All");

		trialsReportPage.setTrialsSubmittedByInstitution("1/1/2009",
				"9/7/2011", "Both", false, institutions);

		TrialsSubmittedByInstitutionResultsTable resultsTable = trialsReportPage
				.clickRunReportButton();

		int results = resultsTable.getResultCount();

		System.out.println("results " + results);

		for (int i = 0; i < results; i++) {
			System.out.println("getNciTrialIdentifier "
					+ resultsTable.getNciTrialIdentifier(i));
			System.out.println("getSubmissionType "
					+ resultsTable.getSubmissionType(i));
			System.out.println("getSubmittedOrganization "
					+ resultsTable.getSubmittedOrganization(i));
		}

		trialsReportPage.clickResetButton();
	}

}
