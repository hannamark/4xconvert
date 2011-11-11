package gov.nih.nci.qa;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsBuilder;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AdHocReportTest {

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
		StopwatchUtil.printReport(SplitUtil.getRootName());
	}

	@Test
	public void criteriaErrorDisplayed() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.CRITERIA_ERROR, adHocReportMessage);
	}

	@Test
	public void noResultsDisplayed() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setOfficialTitle("Returns No Result!");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("No results were expected!.", 0,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void firstCustomerTestCase() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setPrimaryPurposeDropDown("Treatment");
		builder.setSearchByTrialCategoryDropDown("Both");

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("II");
		builder.setTrialPhases(trialPhases);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setDiseaseConditionAndStage("recurrent thyroid cancer",
				false, false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void secondCustomerTestCase() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setPrimaryPurposeDropDown("Treatment");
		builder.setSearchByTrialCategoryDropDown("Both");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setDiseaseConditionAndStage("recurrent thyroid cancer",
				false, false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void thirdCustomerTestCase() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setLeadOrganizationDropDown("Mayo Clinic");
		builder.setSearchByTrialCategoryDropDown("Complete");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void databaseResultCount5740() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();

		builder.setPrimaryPurposeDropDown("Treatment");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		// select count(*) from study_protocol where
		// primary_purpose_code='TREATMENT';
		// 5740
		adHocReportPage.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void resultsExceedFiveHundred() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("II");
		builder.setTrialPhases(trialPhases);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		// select count(*) from study_protocol where
		// study_protocol.phase_code='II';
		// 2527
		adHocReportPage.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void databaseResultCount1453() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("I");
		builder.setTrialPhases(trialPhases);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		// select count(*) from study_protocol where
		// study_protocol.phase_code='I';
		// 1453
		adHocReportPage.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void databaseResultCount694() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();

		ArrayList<String> trialPhases = new ArrayList<String>();
		trialPhases.add("III");
		builder.setTrialPhases(trialPhases);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		// select count(*) from study_protocol where
		// study_protocol.phase_code='III';
		// 694
		adHocReportPage.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void databaseResultCount365() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setPrimaryPurposeDropDown("Supportive Care");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		// select count(*) from study_protocol where
		// primary_purpose_code='SUPPORTIVE_CARE';
		// 365
		adHocReportPage.clickRunReportButton();

		// I assume that this test takes longer than the ones over 500 because
		// of the translating the result set into displayable items?
		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void milestoneTrialSummaryReportSentData() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setMilestoneDropDown("Trial Summary Report Sent Date");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 17,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void preventionAndIndustrial() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setPrimaryPurposeDropDown("Prevention");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setSummary4AnatomicSite(null, null, "Industrial");

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 18,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void principalInvestigatorSelected() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setPrincipalInvestigatorDropDown("Abrams,Ross");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 1,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void processingStatusGeneralError() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setProcessingStatusDropDown("Verification Pending");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.clickRunReportButton();

		// TODO Exposes
		// "General error in while converting to StudyProtocolQueryDTO"
		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);

	}

	@Test
	public void processingStatusRejected() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setProcessingStatusDropDown("Rejected");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		// assertEquals("Not the expected result amount.", 323,
		// adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void currentTrialStatusEnrollingByInvitation() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setCurrentTrialStatusDropDown("Enrolling by Invitation");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		// assertEquals("Not the expected result amount.", 1,
		// adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void ziopharmOncologySingleCriteria() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 6,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void ziopharmOncologyTwoCriteria() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		builder.setPrimaryPurposeDropDown("Treatment");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 6,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void ziopharmOncologyThreeCriteria() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		builder.setPrimaryPurposeDropDown("Treatment");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setInterventions("Drug");
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 5,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void ziopharmOncologyFiveCriteria() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		builder.setPrimaryPurposeDropDown("Treatment");
		builder.setSearchBySubmissionTypeDropDown("Original");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setSummary4AnatomicSite(null, null, "Industrial");

		adHocReportPage.setInterventions("Drug");

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 5,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void ziopharmOncologyOfficialTitle() {
		String officialTitleText = "A Phase II Multicenter, Parallel Group, Randomized Study of Palifosfamide Tris Plus Doxorubicin versus Doxorubicin in Subjects With Unresectable or Metastatic Soft-Tissue Sarcoma (PICASSO)";

		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setOfficialTitle(officialTitleText);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 1,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void summary4SponsorDiseaseConditionInstitutional() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setLeadOrganizationDropDown("Fred Hutchinson Cancer Research Center/University of Washington Cancer Consortium");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		adHocReportPage.setDiseaseConditionAndStage(
				"accelerated phase chronic myelogenous leukemia", false, false);

		adHocReportPage.setSummary4AnatomicSite(null, null, "Institutional");
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		// assertEquals("Not the expected result amount.", 12,
		// adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void nciIdentifier() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();

		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setIdentifierTypeDropDown("NCI");
		builder.setIdentifierTextBox("NCI-2010-00194");
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 1,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void diseaseCondition() {
		String diseaseConditionText = "noncontiguous stage II adult Burkitt lymphoma";

		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setDiseaseConditionAndStage(diseaseConditionText,
				false, false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();
		assertEquals("Not the expected result amount.", 128,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void diseaseConditionNotFound() {
		String diseaseConditionText = "lorem ipsum disease";

		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setDiseaseConditionAndStage(diseaseConditionText,
				false, false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.CRITERIA_ERROR, adHocReportMessage);
	}

}
