package gov.nih.nci.qa.selenium.Cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsBuilder;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class CucumberAdHocReportSteps {

	@Rule
	public TestName testName = new TestName();

	private WebDriver webDriver;
	private AdHocReportPage adHocReportPage;
	private AdHocReportTable adHocReportTable;

	@Before
	public void prepare() {
		// Load the browser driver.
		webDriver = new FirefoxDriver();

		// Get the page under test.
		adHocReportPage = new AdHocReportPage(webDriver).get();
	}

	@After
	public void cleanUp() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@Given("^I select a primary purpose of (.*)$")
	public void selectPrimaryPurposeOfTreatment(String purpose) {
		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setPrimaryPurposeDropDown(purpose);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);
	}

	@And("^I select a disease condition of (.*)$")
	public void selectDiseaseCondition(String disease) {
		adHocReportPage.setDiseaseConditionAndStage(disease, true, false);
	}

	@And("^I select a trial category of (.*)$")
	public void selectTrialCategory(String category) {
		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setSearchByTrialCategoryDropDown(category);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);
	}

	@And("^I select a trial phase of (.*)$")
	public void selectTrialPhase(String phase) {
		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setTrialPhase(phase);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);
	}

	@Then("^a report will be displayed with (.*) results$")
	public void assertResultsTotal(String expected) {
		int intValue = Integer.valueOf(expected).intValue();
		assertEquals("Result total doesn't match!", intValue,
				adHocReportTable.getResultCount());
	}

	@When("^the run report button is clicked$")
	public void clickReportButton() {
		adHocReportTable = adHocReportPage.clickRunReportButton();
	}

	@And("^the program shall respond in less than (.*) seconds$")
	public void assertPerformance(String seconds) {
		// Output to a report.
		Map<String, Double> maxValues = StopwatchUtil.getMaxValues(SplitUtil
				.getRootName());

		Set<Entry<String, Double>> entrySet = maxValues.entrySet();
		Iterator<Entry<String, Double>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, Double> me = it.next();
			System.out.println(me.getKey() + "=" + me.getValue());
			Double lessThanMe = Double.valueOf(seconds);
			assertTrue(me.getKey() + " was not less than " + lessThanMe
					+ " seconds.", (lessThanMe > me.getValue()));
		}
	}
}
