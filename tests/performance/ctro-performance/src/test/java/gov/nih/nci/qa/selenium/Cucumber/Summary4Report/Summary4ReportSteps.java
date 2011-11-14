package gov.nih.nci.qa.selenium.Cucumber.Summary4Report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.qa.selenium.PageComponents.Summary4TypeReportTable;
import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class Summary4ReportSteps {

	private static WebDriver webDriver;
	private Summary4TypeReportPage summary4TypeReportPage;
	private Summary4TypeReportTable summary4ReportTable;

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

	@And("^I select '(.*)' as my family$")
	public void setFindByFamily(String familyName) {
		summary4TypeReportPage.setFindByFamily(familyName);
		summary4TypeReportPage.setSelectAllCheckbox(true);
	}

	@And("^I set the end date to (.*)$")
	public void setEndDate(String endDate) {
		summary4TypeReportPage.setEndDate(endDate);
	}

	@And("^no interface command shall take more than (\\d+) seconds$")
	public void assertPerformance(int seconds) {
		Map<String, Double> maxValues = StopwatchUtil
				.getMaxValues(SplitUtil.PAGE_CATEGORY);

		Set<Entry<String, Double>> entrySet = maxValues.entrySet();
		Iterator<Entry<String, Double>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, Double> me = it.next();
			Double lessThanMe = Double.valueOf(seconds);
			assertTrue(me.getKey() + " took " + me.getValue()
					+ " which is not less than " + lessThanMe + " second(s).",
					(lessThanMe > me.getValue()));
		}
	}

	@And("^a report will be displayed with (\\d+) institutional results")
	public void assertInstitutionalResults(int expected) {
		assertEquals("Institutional result total doesn't match!", expected,
				summary4ReportTable.getAgentDeviceInstitutionalResultCount());
	}

	@Given("^I set the start date to (.*)$")
	public void setStartDate(String startDate) {
		summary4TypeReportPage.setStartDate(startDate);
	}

	@Then("^a report will be displayed with (\\d+) externally peer reviewed results$")
	public void assertExternallyPeerReviewedResults(int expected) {
		assertEquals("Peer review sesult total doesn't match!", expected,
				summary4ReportTable
						.getAgentDeviceExternallyPeerReviewedResultCount());
	}

	@When("^the run report button is clicked$")
	public void clickRunReportButton() {
		summary4ReportTable = summary4TypeReportPage.clickRunReportButton();
	}

}
