package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsBuilder;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseAdHocReportPageTest {

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
	public void exercisePageObjects() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		// Use a builder object to create a parameter. This keeps the Ad-Hoc
		// Report page services intact.
		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setOfficialTitle("offical title");
		builder.setPrimaryPurposeDropDown("Treatment");
		builder.setIdentifierTypeDropDown("");
		builder.setIdentifierTextBox("");
		builder.setPrincipalInvestigatorDropDown("All");
		builder.setProcessingStatusDropDown("Submitted");
		builder.setCurrentTrialStatusDropDown("Approved");
		builder.setLeadOrganizationDropDown("All");
		builder.setSearchBySubmissionTypeDropDown("Original");
		builder.setSearchByTrialCategoryDropDown("Complete");
		builder.setCurrentTrialStatusDropDown("Approved");
		builder.setMilestoneDropDown("Submission Acceptance Date");

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("I/II");
		trialPhases.add("NA");
		builder.setTrialPhaseMultiSelect(trialPhases);

		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setParticipatingSites("All");

		ArrayList<String> states = new ArrayList<String>();
		states.add("Washington");
		adHocReportPage.setTrialGeographicArea("USA", states, "Tacoma");

		adHocReportPage.setDiseaseConditionAndStage("recurrent thyroid", true,
				true);

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
}
