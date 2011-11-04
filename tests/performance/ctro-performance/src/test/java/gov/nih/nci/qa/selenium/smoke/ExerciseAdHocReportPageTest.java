package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
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

		// Run down through all the page elements.
		adHocReportPage.setOfficialTitle("");
		adHocReportPage.setPrimaryPurpose("");

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("I/II");
		trialPhases.add("NA");
		adHocReportPage.setTrialPhase(trialPhases);

		adHocReportPage.setIdentifierType("");
		adHocReportPage.setPrincipalInvestigator("");
		adHocReportPage.setProcessingStatus("");
		adHocReportPage.setInterventionType("");
		adHocReportPage.setCurrentTrialStatus("");
		adHocReportPage.setCountry("");

		ArrayList<String> states = new ArrayList<String>();
		states.add("Washington");
		adHocReportPage.setState(states);

		adHocReportPage.setCity("");
		adHocReportPage.setLeadOrganization("");
		adHocReportPage.setParticipatingSite("");
		adHocReportPage.setSummary4Sponsor("");

		ArrayList<String> sites = new ArrayList<String>();
		sites.add("Kaposi's Sarcoma");
		sites.add("Multiple Myeloma");
		adHocReportPage.setSummary4AnatomicSites(sites);

		ArrayList<String> biomarkers = new ArrayList<String>();
		biomarkers.add("All");
		adHocReportPage.setBiomarker(biomarkers);

		adHocReportPage.setDiseaseCondition("", true, true);
		adHocReportPage.setIdentifier("");
		adHocReportPage.setSearchBySubmissionType("");
		adHocReportPage.setSearchByTrialCategory("");
		adHocReportPage.setMilestone("");
		adHocReportPage.clickResetButton();
		adHocReportPage.clickRunReportButton();
	}

}
