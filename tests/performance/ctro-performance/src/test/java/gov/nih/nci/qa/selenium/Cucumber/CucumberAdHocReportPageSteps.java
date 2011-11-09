package gov.nih.nci.qa.selenium.Cucumber;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.annotation.After;
import cucumber.annotation.Before;

public class CucumberAdHocReportPageSteps {
	private WebDriver webDriver;

	private AdHocReportPage adHocReportPage;

	@Before
	public void prepare() {
		webDriver = new FirefoxDriver();
		adHocReportPage = new AdHocReportPage(webDriver).get();
	}

	@After
	public void cleanUp() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
	}
}
