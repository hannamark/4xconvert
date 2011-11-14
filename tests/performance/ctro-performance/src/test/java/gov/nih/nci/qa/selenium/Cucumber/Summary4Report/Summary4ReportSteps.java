package gov.nih.nci.qa.selenium.Cucumber.Summary4Report;

import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.annotation.After;
import cucumber.annotation.Before;

public class Summary4ReportSteps {

	private static WebDriver webDriver;
	private Summary4TypeReportPage summary4TypeReportPage;

	@Before
	public void prepare() {
		// Load the browser driver.
		webDriver = new FirefoxDriver();

		// Get the page under test.

		summary4TypeReportPage = new Summary4TypeReportPage(webDriver).get();
	}

	@After
	public void cleanUp() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
	}

}
