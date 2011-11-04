package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.AdHocPageDiseaseLookUp;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageComponents.DiseaseLocatorResultsTable;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;
import gov.nih.nci.qa.selenium.util.PageUtil;

import java.util.List;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdHocReportPage extends LoadableComponent<AdHocReportPage> {

	@FindBy(how = How.ID, using = "officialTitle")
	WebElement officialTitleTextBox;

	@FindBy(how = How.ID, using = "primaryPurpose")
	WebElement primaryPurposeDropDown;

	@FindBy(how = How.ID, using = "phaseCodes")
	WebElement trialPhaseMultiSelect;

	@FindBy(how = How.ID, using = "identifierType")
	WebElement identifierTypeDropDown;

	@FindBy(how = How.ID, using = "principalInvestigatorId")
	WebElement principalInvestigatorDropDown;

	@FindBy(how = How.ID, using = "documentWorkflowStatusCode")
	WebElement processingStatusDropDown;

	@FindBy(how = How.ID, using = "country")
	WebElement countryDropDown;

	@FindBy(how = How.ID, using = "states")
	WebElement statesMultiSelect;

	@FindBy(how = How.ID, using = "city")
	WebElement cityTextBox;

	@FindBy(how = How.ID, using = "interventionType")
	WebElement interventionTypeDropDown;

	@FindBy(how = How.ID, using = "studyStatusCode")
	WebElement currentTrialStatusDropDown;

	@FindBy(how = How.ID, using = "leadOrganizationId")
	WebElement leadOrganizationDropDown;

	@FindBy(how = How.ID, using = "participatingSiteId")
	WebElement partipatingSiteDropDown;

	@FindBy(how = How.ID, using = "summ4Sponsor")
	WebElement summary4SponsorDropDown;

	@FindBy(how = How.ID, using = "anatomicSitesList")
	WebElement summary4AnatomicSitesMultiSelect;

	@FindBy(how = How.ID, using = "bioMarkersList")
	WebElement biomarkerMultiSelect;

	@FindBy(how = How.CSS, using = "span.search")
	WebElement lookUpButton;

	@FindBy(how = How.ID, using = "identifier")
	WebElement identifierTextBox;

	@FindBy(how = How.ID, using = "submissionType")
	WebElement searchBySubmissionTypeDropDown;

	@FindBy(how = How.ID, using = "trialCategory")
	WebElement searchByTrialCategoryDropDown;

	@FindBy(how = How.ID, using = "summ4FundingSourceTypeCode")
	WebElement summary4FundingCategoryDropDown;

	@FindBy(how = How.ID, using = "studyMilestone")
	WebElement milestoneDropDown;

	@FindBy(how = How.LINK_TEXT, using = "Run Report")
	WebElement runReportButton;

	@FindBy(how = How.LINK_TEXT, using = "Reset")
	WebElement resetButton;

	AdHocReportTable adHocResultsTable;

	private final WebDriver webDriver;

	public AdHocReportPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public AdHocReportTable clickRunReportButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickRunReportButton")
				.start();
		runReportButton.click();
		split.stop();
		return new AdHocReportTable(webDriver);
	}

	public AdHocReportPage clickResetButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickResetButton")
				.start();
		resetButton.click();
		split.stop();
		return new AdHocReportPage(webDriver);
	}

	public void setOfficialTitle(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setOfficialTitle")
				.start();
		officialTitleTextBox.clear();
		officialTitleTextBox.sendKeys(keysToSend);
		split.stop();
	}

	public void setPrimaryPurpose(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setPrimaryPurpose")
				.start();
		PageUtil.setDropDown(primaryPurposeDropDown, keysToSend);
		split.stop();
	}

	public void setTrialPhase(List<String> selectList) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setTrialPhase")
				.start();
		PageUtil.setMultiSelect(trialPhaseMultiSelect, selectList);
		split.stop();
	}

	public void setIdentifierType(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setIdentifierType")
				.start();
		PageUtil.setDropDown(identifierTypeDropDown, keysToSend);
		split.stop();
	}

	public void setPrincipalInvestigator(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setPrincipalInvestigator").start();
		PageUtil.setDropDown(principalInvestigatorDropDown, keysToSend);
		split.stop();
	}

	public void setProcessingStatus(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setProcessingStatus")
				.start();
		PageUtil.setDropDown(processingStatusDropDown, keysToSend);
		split.stop();
	}

	public void setCountry(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setCountry").start();
		PageUtil.setDropDown(countryDropDown, keysToSend);
		split.stop();
	}

	public void setState(List<String> selectList) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setState").start();
		PageUtil.setMultiSelect(statesMultiSelect, selectList);
		split.stop();
	}

	public void setCity(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setCity").start();
		cityTextBox.sendKeys(keysToSend);
		split.stop();
	}

	public void setInterventionType(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setInterventionType")
				.start();
		PageUtil.setDropDown(interventionTypeDropDown, keysToSend);
		split.stop();
	}

	public void setCurrentTrialStatus(String keysToSend) {
		Split split = SimonManager
				.getStopwatch(
						"parent" + Manager.HIERARCHY_DELIMITER
								+ "AdHocReportPage"
								+ Manager.HIERARCHY_DELIMITER
								+ "setCurrentTrialStatus").start();
		PageUtil.setDropDown(currentTrialStatusDropDown, keysToSend);
		split.stop();
	}

	public void setLeadOrganization(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setLeadOrganization")
				.start();
		PageUtil.setDropDown(leadOrganizationDropDown, keysToSend);
		split.stop();
	}

	public void setParticipatingSite(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setParticipatingSite")
				.start();
		PageUtil.setDropDown(partipatingSiteDropDown, keysToSend);
		split.stop();
	}

	public void setSummary4Sponsor(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setSummary4Sponsor")
				.start();
		PageUtil.setDropDown(summary4SponsorDropDown, keysToSend);
		split.stop();
	}

	public void setSummary4AnatomicSites(List<String> selectList) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setSummary4AnatomicSites").start();
		PageUtil.setMultiSelect(summary4AnatomicSitesMultiSelect, selectList);
		split.stop();
	}

	public void setBiomarker(List<String> selectList) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setBiomarker").start();
		PageUtil.setMultiSelect(biomarkerMultiSelect, selectList);
		split.stop();
	}

	public void setIdentifier(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setIdentifier")
				.start();
		identifierTextBox.clear();
		identifierTextBox.sendKeys(keysToSend);
		split.stop();
	}

	public void setSearchBySubmissionType(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setSearchBySubmissionType").start();
		PageUtil.setDropDown(searchBySubmissionTypeDropDown, keysToSend);
		split.stop();
	}

	public void setSearchByTrialCategory(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setSearchByTrialCategory").start();
		PageUtil.setDropDown(searchByTrialCategoryDropDown, keysToSend);
		split.stop();
	}

	public void setSummary4FundingCategory(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setSummary4FundingCategory").start();
		PageUtil.setDropDown(summary4FundingCategoryDropDown, keysToSend);
		split.stop();
	}

	public void setMilestone(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setMilestone").start();
		PageUtil.setDropDown(milestoneDropDown, keysToSend);
		split.stop();
	}

	public void setDiseaseCondition(String keysToSend, Boolean includeSynonym,
			Boolean exactMatchOnly) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setDiseaseCondition")
				.start();
		AdHocPageDiseaseLookUp adHocPageDiseaseLookUp = clickLookUpButton();

		waitForFrameToAppear();

		WebDriver frame = webDriver.switchTo().frame("popupFrame");

		adHocPageDiseaseLookUp.setDiseaseName(keysToSend);
		adHocPageDiseaseLookUp.setExactMatch(exactMatchOnly);
		adHocPageDiseaseLookUp.setIncludeSynonym(includeSynonym);

		DiseaseLocatorResultsTable diseaseLocatorResultsTable = adHocPageDiseaseLookUp
				.clickSearchButton();

		if (diseaseLocatorResultsTable.hasResults()) {
			diseaseLocatorResultsTable.clickSelectButton();
			frame.switchTo().defaultContent();
			waitForLoadingToFinish();
		} else {
			adHocPageDiseaseLookUp.clickCloseButton();
			frame.switchTo().defaultContent();
		}
		split.stop();
	}

	public AdHocReportMessage getMessage() {
		WebElement adHocResults = webDriver.findElement(By
				.id("resultsAdHocReport"));
		WebElement errorMessage = null;
		try {
			errorMessage = adHocResults.findElement(By
					.className("errorMessage"));
		} catch (NoSuchElementException e) {
			return AdHocReportMessage.NO_ERROR_FOUND;
		}
		String errorText = errorMessage.getText();
		if (errorText
				.equals("An error has occurred when searching for trials.")) {
			return AdHocReportMessage.ERROR_SEARCHING;
		} else if (errorText.equals("At least one criteria is required.")) {
			return AdHocReportMessage.CRITERIA_ERROR;
		} else if (errorText.contains("General error")) {
			return AdHocReportMessage.GENERAL_ERROR;
		} else if (errorText.contains("Results exceed more than 500.")) {
			return AdHocReportMessage.RESULTS_EXCEEDED_500;
		} else {
			return null;
		}
	}

	public AdHocPageDiseaseLookUp clickLookUpButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickLookUpButton")
				.start();
		lookUpButton.click();
		split.stop();
		return new AdHocPageDiseaseLookUp(webDriver);

	}

	private void waitForLoadingToFinish() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "waitForLoadingToFinish").start();
		WebElement selectedDisease = webDriver.findElement(By
				.id("criteriaAdHocReport"));
		// If it contains "Loading..." look again.
		while (selectedDisease.getText().contains("Loading...")) {
			selectedDisease = (new WebDriverWait(webDriver, 10))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("criteriaAdHocReport"));
						}
					});
		}
		split.stop();
	}

	private void waitForFrameToAppear() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "waitForFrameToAppear")
				.start();

		(new WebDriverWait(webDriver, 15))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						d.switchTo().defaultContent();
						List<WebElement> iframes = d.findElements(By
								.tagName("iframe"));
						for (WebElement iframe : iframes) {
							d.switchTo().frame(iframe);
							d.findElement(By.tagName("body"));
							d.switchTo().defaultContent();
						}
						return true;
					}
				});
		split.stop();
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Ad Hoc Report".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Ad Hoc Report page!");
		}

	}

	@Override
	protected void load() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickAdHocLink();
		split.stop();
	}
}
