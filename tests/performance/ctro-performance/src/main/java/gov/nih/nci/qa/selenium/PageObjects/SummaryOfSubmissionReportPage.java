package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.SummaryOfSubmissionResultsTable;
import gov.nih.nci.qa.selenium.util.PageUtil;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class SummaryOfSubmissionReportPage extends
		LoadableComponent<SummaryOfSubmissionReportPage> {

	@FindBy(how = How.ID, using = "criteriaSummaryOfSubmission_criteria_intervalStartDate")
	WebElement intervalStartDate;

	@FindBy(how = How.ID, using = "criteriaSummaryOfSubmission_criteria_intervalEndDate")
	WebElement intervalEndDate;

	@FindBy(how = How.ID, using = "criteriaSummaryOfSubmission_criteria_ctep")
	WebElement includeTrialsCheckbox;

	@FindBy(how = How.LINK_TEXT, using = "Run report")
	WebElement runReportButton;

	@FindBy(how = How.LINK_TEXT, using = "Reset")
	WebElement resetButton;

	private final WebDriver webDriver;

	public SummaryOfSubmissionReportPage clickResetButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "SummaryOfSubmissionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickResetButton")
				.start();
		resetButton.click();
		split.stop();
		return new SummaryOfSubmissionReportPage(webDriver);
	}

	public SummaryOfSubmissionResultsTable clickRunReportButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "SummaryOfSubmissionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickRunReportButton")
				.start();
		runReportButton.click();
		split.stop();
		return new SummaryOfSubmissionResultsTable(webDriver);
	}

	public void setDateRange(String startDate, String endDate) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "SummaryOfSubmissionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setDateRange").start();
		PageUtil.setDateInterval(intervalStartDate, startDate, intervalEndDate,
				endDate);
		split.stop();
	}

	public void setIncludeTrials(boolean includeTrials) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "SummaryOfSubmissionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setIncludeTrials")
				.start();
		PageUtil.setCheckbox(includeTrialsCheckbox, includeTrials);
		split.stop();
	}

	public SummaryOfSubmissionReportPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Summary of Submission".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error(
					"This is not the Summary of Submission report page.");
		}
	}

	@Override
	protected void load() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "SummaryOfSubmissionReportPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickSummaryOfSubmissionLink();
		split.stop();
	}

}
