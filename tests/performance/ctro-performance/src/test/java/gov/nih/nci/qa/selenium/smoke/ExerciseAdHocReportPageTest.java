package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsBuilder;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.ArrayList;
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

public class ExerciseAdHocReportPageTest {

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
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		// Print all the options available...
		printOptions(adHocReportPage.getPrimaryPurposes());
		printOptions(adHocReportPage.getTrialPhases());
		printOptions(adHocReportPage.getIdentifierTypes());
		printOptions(adHocReportPage.getLeadOrganizations());
		printOptions(adHocReportPage.getPrincipalInvestigators());
		printOptions(adHocReportPage.getProcessingStatuses());
		printOptions(adHocReportPage.getCurrentTrialStatues());
		printOptions(adHocReportPage.getSubmissionTypes());
		printOptions(adHocReportPage.getTrialCategories());
		printOptions(adHocReportPage.getMilestones());
		printOptions(adHocReportPage.getInterventions());
		printOptions(adHocReportPage.getParticipatingSites());
		printOptions(adHocReportPage.getCountries());
		printOptions(adHocReportPage.getStates());
		printOptions(adHocReportPage.getBiomarkers());
		printOptions(adHocReportPage.getSummary4Sponsors());
		printOptions(adHocReportPage.getSummary4FundingCategories());
		printOptions(adHocReportPage.getSummary4AnatomicSites());

		// Use a builder object to create a parameter. This keeps the Ad-Hoc
		// Report page services intact.
		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setOfficialTitle("offical title");
		builder.setPrimaryPurpose("Treatment");
		builder.setIdentifierTypeDropDown("");
		builder.setIdentifierTextBox("");
		builder.setPrincipalInvestigator("All");
		builder.setProcessingStatus("Submitted");
		builder.setCurrentTrialStatus("Approved");
		builder.setLeadOrganizationDropDown("All");
		builder.setSearchBySubmissionType("Original");
		builder.setSearchByTrialCategory("Complete");
		builder.setCurrentTrialStatus("Approved");
		builder.setMilestone("Submission Acceptance Date");

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("I/II");
		trialPhases.add("NA");
		builder.setTrialPhases(trialPhases);

		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setInterventions("Drug");

		adHocReportPage.setParticipatingSites("All");

		ArrayList<String> states = new ArrayList<String>();
		states.add("Washington");
		adHocReportPage.setTrialGeographicArea("USA", states, "Tacoma");

		adHocReportPage.setDiseaseConditionAndStage("recurrent thyroid", true,
				true);
		String diseaseCount = adHocReportPage.getDiseaseCount();
		System.out.println("Disease count [" + diseaseCount + "].");

		ArrayList<String> sites = new ArrayList<String>();
		sites.add("Kaposi's Sarcoma");
		sites.add("Multiple Myeloma");
		adHocReportPage.setSummary4AnatomicSite("All", sites, "National");

		ArrayList<String> biomarkers = new ArrayList<String>();
		biomarkers.add("All");
		adHocReportPage.setBiomarkers(biomarkers);

		AdHocReportTable reportTable = adHocReportPage.clickRunReportButton();
		if (reportTable.hasResults()) {
			System.out.println("The table had results.");
		} else {
			System.out.println("The table did not have results.");
		}

		// Resetting and running the report should yield a message.
		adHocReportPage = adHocReportPage.clickResetButton();
		adHocReportPage.clickRunReportButton();

		AdHocReportMessage message = adHocReportPage.getMessage();
		System.out.println("Message on the page was " + message);
	}

	private void printOptions(List<String> options) {
		for (String option : options) {
			System.out.println("option = " + option);
		}
	}

}
