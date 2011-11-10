package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.TrialsSubmittedByInstitutionResultsTable;
import gov.nih.nci.qa.selenium.util.PageUtil;
import gov.nih.nci.qa.selenium.util.SplitUtil;

import java.util.List;

import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class TrialsSubmittedByInstitutionReportPage extends
		LoadableComponent<TrialsSubmittedByInstitutionReportPage> {

	@FindBy(how = How.LINK_TEXT, using = "Report Filters")
	WebElement reportFilterTab;

	@FindBy(how = How.LINK_TEXT, using = "Results")
	WebElement resultsTab;

	@FindBy(how = How.ID, using = "intervalStartDate")
	WebElement intervalStartDate;

	@FindBy(how = How.ID, using = "intervalEndDate")
	WebElement intervalEndDate;

	@FindBy(how = How.ID, using = "submissionType")
	WebElement submissionTypeDropDown;

	@FindBy(how = How.ID, using = "ctep")
	WebElement includeTrialsCheckbox;

	@FindBy(how = How.ID, using = "institutions")
	WebElement institutionsMultiSelect;

	@FindBy(how = How.ID, using = "runButton")
	WebElement runReportButton;

	@FindBy(how = How.ID, using = "resetButton")
	WebElement resetButton;

	TrialsSubmittedByInstitutionResultsTable resultsTable;

	private final WebDriver webDriver;

	public TrialsSubmittedByInstitutionReportPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setTrialsSubmittedByInstitution(String startDate,
			String endDate, String type, boolean includeTrials,
			List<String> institutions) {
		setDateRange(startDate, endDate);
		setIncludeTrials(includeTrials);
		setInstitutions(institutions);
		setSubmissionType(type);
	}

	public TrialsSubmittedByInstitutionReportPage clickResetButton() {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage", "clickResetButton");
		// Change to the report filters tab first.
		reportFilterTab.click();
		resetButton.click();
		split.stop();
		return new TrialsSubmittedByInstitutionReportPage(webDriver);
	}

	public TrialsSubmittedByInstitutionResultsTable clickRunReportButton() {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage",
				"clickRunReportButton");
		// Change to the report filters tab first.
		reportFilterTab.click();
		runReportButton.click();
		split.stop();
		return new TrialsSubmittedByInstitutionResultsTable(webDriver);
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Trials Submitted by Institution".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error(
					"This is not the Trials Submitted by Institution report page.");
		}
	}

	@Override
	protected void load() {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage", "load");
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickTrialsSubmittedByInstitutionLink();
		split.stop();
	}

	// privates
	private void setSubmissionType(String type) {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage", "setSubmissionType");
		PageUtil.setDropDown(submissionTypeDropDown, type);
		split.stop();
	}

	private void setIncludeTrials(boolean includeTrials) {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage", "setIncludeTrials");
		PageUtil.setCheckbox(includeTrialsCheckbox, includeTrials);
		split.stop();
	}

	private void setInstitutions(List<String> selections) {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage", "setInstitutions");
		PageUtil.setMultiSelect(institutionsMultiSelect, selections);
		split.stop();
	}

	private void setDateRange(String startDate, String endDate) {
		Split split = SplitUtil.getPageElementSplit(
				"TrialsSubmittedByInstitutionReportPage", "setDateRange");
		PageUtil.setDateInterval(intervalStartDate, startDate, intervalEndDate,
				endDate);
		split.stop();
	}
}
