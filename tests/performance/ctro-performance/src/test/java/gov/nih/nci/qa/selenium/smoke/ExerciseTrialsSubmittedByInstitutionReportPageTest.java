package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageComponents.TrialsSubmittedByInstitutionResultsTable;
import gov.nih.nci.qa.selenium.PageObjects.TrialsSubmittedByInstitutionReportPage;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseTrialsSubmittedByInstitutionReportPageTest {

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
	public void exercisePageObjects() {
		TrialsSubmittedByInstitutionReportPage trialsReportPage = new TrialsSubmittedByInstitutionReportPage(
				webDriver).get();
		trialsReportPage.setDateRange("1/1/2009", "9/7/2011");

		trialsReportPage.setSubmissionType("Both");
		trialsReportPage.setIncludeTrials(false);
		trialsReportPage.setIncludeTrials(true);

		ArrayList<String> selections = new ArrayList<String>();
		selections.add("All");
		trialsReportPage.setInstitutions(selections);

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
