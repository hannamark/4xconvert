package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.PageObjects.CtroReportSelectionPage;
import gov.nih.nci.qa.selenium.PageObjects.DisclaimerPage;
import gov.nih.nci.qa.selenium.PageObjects.HomePage;
import gov.nih.nci.qa.selenium.PageObjects.LoginPage;
import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;
import gov.nih.nci.qa.selenium.PageObjects.SummaryOfSubmissionReportPage;
import gov.nih.nci.qa.selenium.PageObjects.TrialsSubmittedByInstitutionReportPage;
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

/**
 * User: kgann Date: 9/22/11 Time: 3:19 PM
 */
public class ExercisePageObjectLoadTest {

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
	public void homePageTest() {
		HomePage homePage = new HomePage(webDriver).get();
		LoginPage loginPage = homePage.clickLogInLink();
	}

	@Test
	public void loginValidUserTest() {
		LoginPage loginPage = new LoginPage(webDriver).get();
		DisclaimerPage disclaimerPage = loginPage.loginAs("abstractor",
				"Coppa#12345", "Training");
	}

	@Test
	public void disclaimerAcceptTest() {
		DisclaimerPage disclaimerPage = new DisclaimerPage(webDriver).get();
		CtroReportSelectionPage acceptDisclaimer = disclaimerPage
				.acceptDisclaimer();
	}

	@Test
	public void disclaimerRejectTest() {
		DisclaimerPage disclaimerPage = new DisclaimerPage(webDriver).get();
		HomePage homePage = disclaimerPage.rejectDisclaimer();
	}

	@Test
	public void ctroReportSelectorPageTest() {
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
	}

	@Test
	public void adHocReportNavTest() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
	}

	@Test
	public void summary4TypeReportNavTest() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
	}

	@Test
	public void trialsSubmittedByInstituionNavTest() {
		TrialsSubmittedByInstitutionReportPage trialsSubmittedByInstitutionReportPage = new TrialsSubmittedByInstitutionReportPage(
				webDriver).get();
	}

	@Test
	public void summaryOfSubmissionNavTest() {
		SummaryOfSubmissionReportPage summaryOfSubmissionReportPage = new SummaryOfSubmissionReportPage(
				webDriver).get();
	}

}
