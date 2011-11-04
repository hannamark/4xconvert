package gov.nih.nci.qa;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;
import gov.nih.nci.qa.selenium.enumerations.Summary4ReportMessage;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Summary4TypeReportTest {

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
	public void firstCustomerTestCase() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/2011");
		summary4TypeReportPage
				.setFindByFamily("Dana-Farber/Harvard Cancer Center");
		// TODO Fix this so it fails rather than explodes if database isn't
		// populated.
		summary4TypeReportPage.setSelectAllCheckbox(true);
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void setIndividualOrganizations() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/2011");
		summary4TypeReportPage
				.setFindByFamily("Dana-Farber/Harvard Cancer Center");
		List<String> selections = new ArrayList<String>();
		selections.add("Beth Israel Deaconess Medical Center (AFFILIATION)");
		selections
				.add("The Dana-Farber Cancer Institute at Londonberry (ORGANIZATIONAL)");
		summary4TypeReportPage.setOrganizations(selections);
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void summary4FindByOrgName() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/2011");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void endDateBeforeStartDateError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/2000");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Not the error expected.",
				Summary4ReportMessage.DATE_INTERVAL_ERROR, reportMessage);
	}

	@Test
	public void startDateRequiredError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("", "9/7/2000");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Not the error expected.",
				Summary4ReportMessage.START_DATE_REQUIRED, reportMessage);
	}

	@Test
	public void endDateRequiredError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("9/7/2000", "");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Not the error expected.",
				Summary4ReportMessage.END_DATE_REQUIRED, reportMessage);
	}

	@Test
	public void startAndEndDateRequiredError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("", "");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Not the error expected.",
				Summary4ReportMessage.START_AND_END_DATE_REQUIRED,
				reportMessage);
	}

	@Test
	public void organizationNameRequiredError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/0200");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Not the error expected.",
				Summary4ReportMessage.ORG_NAME_REQUIRED, reportMessage);
	}

	@Test
	public void invalidDateError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("Invalid!", "Invalid!");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Not the error expected.",
				Summary4ReportMessage.INVALID_DATE, reportMessage);
	}

	@Test
	public void endDateOneMonthInFutureError() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();

		// Get the current date and increment it a month.
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String oneMonthAhead = formatter.format(currentDate.getTime());

		summary4TypeReportPage.setDateRange("1/1/2009", oneMonthAhead);
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.INVALID_END_DATE, reportMessage);
	}

	@Test
	public void tenYearDateRange() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/1990", "1/1/2000");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void twentyYearDateRange() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/1980", "1/1/2000");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void fiftyYearDateRange() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/1950", "1/1/2000");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void oneHundredYearDateRange() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/1900", "1/1/2000");
		summary4TypeReportPage
				.setFindByOrgName("Dana-Farber Harvard Cancer Center");
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}

	@Test
	public void oneAgentDeviceResult() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();
		summary4TypeReportPage.setDateRange("1/1/1990", "9/27/2010");
		summary4TypeReportPage.setFindByFamily("Dan L. Duncan Cancer Center");
		// TODO Fix this so it fails rather than explodes if database isn't
		// populated.
		summary4TypeReportPage.setSelectAllCheckbox(true);
		summary4TypeReportPage.clickRunReportButton();

		Summary4ReportMessage reportMessage = summary4TypeReportPage
				.getMessage();
		assertEquals("Didn't expect an error to be present.",
				Summary4ReportMessage.NO_ERROR_FOUND, reportMessage);
	}
}
