package gov.nih.nci.qa.selenium.test;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AdHocReportTest {

	private WebDriver webDriver;

	@Before
	public void setUp() {
		// TODO Get driver to choose from a properties file.
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
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
		adHocReportPage.setOfficialTitle("Returns No Result!");
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
		adHocReportPage.setPrimaryPurposeDropDown("Treatment");
		adHocReportPage.setTrialPhaseDropDown("II");
		adHocReportPage.setSearchByTrialCategoryDropDown("Both");
		adHocReportPage.setDiseaseCondition("recurrent thyroid cancer", false,
				false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 18,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);

	}

	@Test
	public void secondCustomerTestCase() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setPrimaryPurposeDropDown("Treatment");
		adHocReportPage.setSearchByTrialCategoryDropDown("Both");
		adHocReportPage.setDiseaseCondition("recurrent thyroid cancer", false,
				false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 32,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);

	}

	@Test
	public void thirdCustomerTestCase() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setLeadOrganizationDropDown("Mayo Clinic");
		adHocReportPage.setSearchByTrialCategoryDropDown("Complete");
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 122,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);

	}

	@Test
	public void databaseResultCount5740() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setPrimaryPurposeDropDown("Treatment");

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
		adHocReportPage.setTrialPhaseDropDown("II");

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
		adHocReportPage.setTrialPhaseDropDown("I");

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
		adHocReportPage.setTrialPhaseDropDown("III");

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
		adHocReportPage.setPrimaryPurposeDropDown("Supportive Care");

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
		adHocReportPage.setMilestoneDropDown("Trial Summary Report Sent Date");
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
		adHocReportPage.setPrimaryPurposeDropDown("Prevention");
		adHocReportPage.setSummary4FundingCategoryDropDown("Industrial");
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
		adHocReportPage.setPrincipalInvestigatorDropDown("Abrams,Ross");
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
		adHocReportPage.setProcessingStatus("Verification Pending");
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
		adHocReportPage.setProcessingStatus("Rejected");
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 323,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void currentTrialStatusEnrollingByInvitation() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage
				.setCurrentTrialStatusDropDown("Enrolling by Invitation");
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 1,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void ziopharmOncologySingleCriteria() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
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
		adHocReportPage.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		adHocReportPage.setPrimaryPurposeDropDown("Treatment");
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
		adHocReportPage.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		adHocReportPage.setPrimaryPurposeDropDown("Treatment");
		adHocReportPage.setInterventionTypeDropDown("Drug");
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
		adHocReportPage.setLeadOrganizationDropDown("Ziopharm Oncology Inc");
		adHocReportPage.setPrimaryPurposeDropDown("Treatment");
		adHocReportPage.setInterventionTypeDropDown("Drug");
		adHocReportPage.setSummary4FundingCategoryDropDown("Industrial");
		adHocReportPage.setSearchBySubmissionTypeDropDown("Original");
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
		adHocReportPage.setOfficialTitle(officialTitleText);
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
		adHocReportPage
				.setLeadOrganizationDropDown("Fred Hutchinson Cancer Research Center/University of Washington Cancer Consortium");
		adHocReportPage.setDiseaseCondition(
				"accelerated phase chronic myelogenous leukemia", false, false);
		adHocReportPage.setSummary4FundingCategoryDropDown("Institutional");
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();

		assertEquals("Not the expected result amount.", 12,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

	@Test
	public void nciIdentifier() {
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		adHocReportPage.setIdentifierTypeDropDown("NCI");
		adHocReportPage.setIdentifierTextBox("NCI-2010-00194");
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
		adHocReportPage.setDiseaseCondition(diseaseConditionText, false, false);
		AdHocReportTable adHocReportTable = adHocReportPage
				.clickRunReportButton();
		assertEquals("Not the expected result amount.", 128,
				adHocReportTable.getResultCount());

		AdHocReportMessage adHocReportMessage = adHocReportPage.getMessage();
		assertEquals("Didn't expect an error to be present.",
				AdHocReportMessage.NO_ERROR_FOUND, adHocReportMessage);
	}

}
