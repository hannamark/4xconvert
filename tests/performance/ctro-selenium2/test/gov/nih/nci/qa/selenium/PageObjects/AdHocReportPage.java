package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.AdHocPageDiseaseLookUp;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageComponents.DiseaseLocatorResultsTable;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdHocReportPage extends LoadableComponent<AdHocReportPage> {

	@FindBy(how = How.ID, using = "officialTitle")
	@CacheLookup
	WebElement officialTitleTextBox;

	@FindBy(how = How.ID, using = "primaryPurpose")
	@CacheLookup
	WebElement primaryPurposeDropDown;

	@FindBy(how = How.ID, using = "phaseCode")
	@CacheLookup
	WebElement trialPhaseDropDown;

	@FindBy(how = How.ID, using = "identifierType")
	@CacheLookup
	WebElement identifierTypeDropDown;

	@FindBy(how = How.ID, using = "principalInvestigatorId")
	@CacheLookup
	WebElement principalInvestigatorDropDown;

	@FindBy(how = How.ID, using = "documentWorkflowStatusCode")
	@CacheLookup
	WebElement processingStatus;

	@FindBy(how = How.ID, using = "interventionType")
	@CacheLookup
	WebElement interventionTypeDropDown;

	@FindBy(how = How.ID, using = "studyStatusCode")
	@CacheLookup
	WebElement currentTrialStatusDropDown;

	@FindBy(how = How.ID, using = "leadOrganizationId")
	@CacheLookup
	WebElement leadOrganizationDropDown;

	@FindBy(how = How.ID, using = "summ4Sponsor")
	@CacheLookup
	WebElement summary4SponsorDropDown;

	@FindBy(how = How.CSS, using = "span.search")
	WebElement lookUpButton;

	@FindBy(how = How.ID, using = "identifier")
	@CacheLookup
	WebElement identifierTextBox;

	@FindBy(how = How.ID, using = "submissionType")
	@CacheLookup
	WebElement searchBySubmissionTypeDropDown;

	@FindBy(how = How.ID, using = "trialCategory")
	@CacheLookup
	WebElement searchByTrialCategoryDropDown;

	@FindBy(how = How.ID, using = "summ4FundingSourceTypeCode")
	@CacheLookup
	WebElement summary4FundingCategoryDropDown;

	@FindBy(how = How.ID, using = "studyMilestone")
	@CacheLookup
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
		runReportButton.click();
		return new AdHocReportTable(webDriver);
	}

	public AdHocReportPage clickResetButton() {
		resetButton.click();
		return new AdHocReportPage(webDriver);
	}

	public void setOfficialTitle(String keysToSend) {
		officialTitleTextBox.clear();
		officialTitleTextBox.sendKeys(keysToSend);
	}

	public void setPrimaryPurposeDropDown(String keysToSend) {
		setDropDown(primaryPurposeDropDown, keysToSend);
	}

	public void setTrialPhaseDropDown(String keysToSend) {
		setDropDown(trialPhaseDropDown, keysToSend);
	}

	public void setIdentifierTypeDropDown(String keysToSend) {
		setDropDown(identifierTypeDropDown, keysToSend);
	}

	public void setPrincipalInvestigatorDropDown(String keysToSend) {
		setDropDown(principalInvestigatorDropDown, keysToSend);
	}

	public void setProcessingStatus(String keysToSend) {
		setDropDown(processingStatus, keysToSend);
	}

	public void setInterventionTypeDropDown(String keysToSend) {
		setDropDown(interventionTypeDropDown, keysToSend);
	}

	public void setCurrentTrialStatusDropDown(String keysToSend) {
		setDropDown(currentTrialStatusDropDown, keysToSend);
	}

	public void setLeadOrganizationDropDown(String keysToSend) {
		setDropDown(leadOrganizationDropDown, keysToSend);
	}

	public void setSummary4SponsorDropDown(String keysToSend) {
		setDropDown(summary4SponsorDropDown, keysToSend);
	}

	public void setIdentifierTextBox(String keysToSend) {
		identifierTextBox.clear();
		identifierTextBox.sendKeys(keysToSend);
	}

	public void setSearchBySubmissionTypeDropDown(String keysToSend) {
		setDropDown(searchBySubmissionTypeDropDown, keysToSend);
	}

	public void setSearchByTrialCategoryDropDown(String keysToSend) {
		setDropDown(searchByTrialCategoryDropDown, keysToSend);
	}

	public void setSummary4FundingCategoryDropDown(String keysToSend) {
		setDropDown(summary4FundingCategoryDropDown, keysToSend);
	}

	public void setMilestoneDropDown(String keysToSend) {
		setDropDown(milestoneDropDown, keysToSend);
	}

	public void setDiseaseCondition(String keysToSend, Boolean includeSynonym,
			Boolean exactMatchOnly) {

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

	private void setDropDown(WebElement webElement, String keysToSend) {
		Select select = new Select(webElement);

		// Get a list of the options
		List<WebElement> options = select.getOptions();

		// For each option in the list, verify if it's the one you want and then
		// click it
		for (WebElement we : options) {
			if (we.getText().equals(keysToSend)) {
				we.click();
				break;
			}
		}
	}

	private AdHocPageDiseaseLookUp clickLookUpButton() {
		lookUpButton.click();
		return new AdHocPageDiseaseLookUp(webDriver);
	}

	private void waitForLoadingToFinish() {
		WebElement selectedDisease = webDriver.findElement(By
				.id("criteriaAdHocReport"));
		// If it contains "Loading..." look again.
		// int counter = 0;
		while (selectedDisease.getText().contains("Loading...")) {
			// System.out.println("load details wait count [" + counter + "].");
			selectedDisease = (new WebDriverWait(webDriver, 10))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("criteriaAdHocReport"));
						}
					});
			// counter++;
		}
	}

	private void waitForFrameToAppear() {
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
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickAdHocLink();
	}
}
