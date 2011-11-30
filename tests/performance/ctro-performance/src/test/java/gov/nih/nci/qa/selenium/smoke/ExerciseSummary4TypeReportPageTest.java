package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseSummary4TypeReportPageTest {

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
		StopwatchUtil.printCompleteReport();
	}

	@Test
	public void exercisePageObjects() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();

		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/2011");

		summary4TypeReportPage.clickFindByFamilyRadio();
		// Just grab something from the list to exercise the page object.
		List<String> availableOptions = summary4TypeReportPage.getFamilyList();
		Iterator<String> it = availableOptions.iterator();
		String currentOption = null;
		while (it.hasNext()) {
			currentOption = it.next();
			if (!currentOption.contains("Select")) {
				break;
			}
		}
		summary4TypeReportPage.setFindByFamily(currentOption);

		summary4TypeReportPage.clickFindByOrgNameRadio();
		summary4TypeReportPage.setFindByOrgName("Lorem Ipsum");
		summary4TypeReportPage.clickRunReportButton();
		summary4TypeReportPage.clickReportFiltersTab();
		summary4TypeReportPage.clickResetButton();

	}

}
