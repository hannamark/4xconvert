package gov.nih.nci.qa.selenium.test;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.PageObjects.CtroReportSelectionPage;
import gov.nih.nci.qa.selenium.PageObjects.CurrentMilestoneReportPage;
import gov.nih.nci.qa.selenium.PageObjects.DisclaimerPage;
import gov.nih.nci.qa.selenium.PageObjects.HomePage;
import gov.nih.nci.qa.selenium.PageObjects.LoginPage;
import gov.nih.nci.qa.selenium.PageObjects.PortfolioAverageMilestoneReportPage;
import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;
import gov.nih.nci.qa.selenium.PageObjects.SummaryOfSubmissionReportPage;
import gov.nih.nci.qa.selenium.PageObjects.TrialsProcessingReportPage;
import gov.nih.nci.qa.selenium.PageObjects.TrialsSubmittedByDateReportPage;
import gov.nih.nci.qa.selenium.PageObjects.TrialsSubmittedByInstitutionReportPage;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * User: kgann Date: 9/22/11 Time: 3:19 PM
 */
public class ExercisePageObjectTest {

	private WebDriver webDriver;

	@Before
	public void setUp() {
		// TODO Get driver to choose from a properties file.
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@Test
	public void homePageTest() {
		HomePage homePage = new HomePage(webDriver).get();
		LoginPage loginPage = homePage.clickLogInLink();
	}

	@Test
	public void loginValidUserTest() {
		LoginPage loginPage = new LoginPage(webDriver).get();
		DisclaimerPage disclaimerPage = loginPage.loginAs("abstractor", "Coppa#12345", "Training");
	}

	@Test
	public void disclaimerAcceptTest() {
		DisclaimerPage disclaimerPage = new DisclaimerPage(webDriver).get();
		CtroReportSelectionPage acceptDisclaimer = disclaimerPage.acceptDisclaimer();
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
	public void currentMilestoneReportNavTest() {
		CurrentMilestoneReportPage currentMilestoneReportPage = new CurrentMilestoneReportPage(
				webDriver).get();
	}

	@Test
	public void trialsSubmittedByInstituionNavTest() {
		TrialsSubmittedByInstitutionReportPage trialsSubmittedByInstitutionReportPage = new TrialsSubmittedByInstitutionReportPage(
				webDriver).get();
	}

	@Test
	public void trialsSubmittedByDateNavTest() {
		TrialsSubmittedByDateReportPage trialsSubmittedByDateReportPage = new TrialsSubmittedByDateReportPage(
				webDriver).get();
	}

	@Test
	public void portfolioAverageMilestoneNavTest() {
		PortfolioAverageMilestoneReportPage portfolioAverageMilestoneReportPage = new PortfolioAverageMilestoneReportPage(
				webDriver).get();
	}

	@Test
	public void trialsProcessingNavTest() {
		TrialsProcessingReportPage trialsProcessingReportPage = new TrialsProcessingReportPage(
				webDriver).get();
	}

	@Test
	public void summaryOfSubmissionNavTest() {
		SummaryOfSubmissionReportPage summaryOfSubmissionReportPage = new SummaryOfSubmissionReportPage(
				webDriver).get();
	}

}
