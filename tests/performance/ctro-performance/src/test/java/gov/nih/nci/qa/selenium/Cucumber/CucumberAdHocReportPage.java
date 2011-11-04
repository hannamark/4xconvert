package gov.nih.nci.qa.selenium.Cucumber;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.junit.Cucumber;
import cucumber.junit.Feature;

@RunWith(Cucumber.class)
@Feature("AdHocReport.feature")
public class CucumberAdHocReportPage {
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
