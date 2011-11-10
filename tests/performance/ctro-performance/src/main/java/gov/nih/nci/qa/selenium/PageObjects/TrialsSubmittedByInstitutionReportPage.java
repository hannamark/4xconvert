package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.TrialsSubmittedByInstitutionResultsTable;
import gov.nih.nci.qa.selenium.util.PageUtil;

import java.util.List;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
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
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickResetButton")
				.start();
		// Change to the report filters tab first.
		reportFilterTab.click();
		resetButton.click();
		split.stop();
		return new TrialsSubmittedByInstitutionReportPage(webDriver);
	}

	public TrialsSubmittedByInstitutionResultsTable clickRunReportButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickRunReportButton")
				.start();
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
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickTrialsSubmittedByInstitutionLink();
		split.stop();
	}

	// privates
	private void setSubmissionType(String type) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setSubmissionType")
				.start();
		PageUtil.setDropDown(submissionTypeDropDown, type);
		split.stop();
	}

	private void setIncludeTrials(boolean includeTrials) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setIncludeTrials")
				.start();
		PageUtil.setCheckbox(includeTrialsCheckbox, includeTrials);
		split.stop();
	}

	private void setInstitutions(List<String> selections) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setInstitutions")
				.start();
		PageUtil.setMultiSelect(institutionsMultiSelect, selections);
		split.stop();
	}

	private void setDateRange(String startDate, String endDate) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "TrialsSubmittedByInstitutionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setDateRange").start();
		PageUtil.setDateInterval(intervalStartDate, startDate, intervalEndDate,
				endDate);
		split.stop();
	}
}
