package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.Summary4TypeReportTable;
import gov.nih.nci.qa.selenium.enumerations.Summary4ReportMessage;
import gov.nih.nci.qa.selenium.util.PageUtil;
import gov.nih.nci.qa.selenium.util.SplitUtil;

import java.util.List;

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

public class Summary4TypeReportPage extends
		LoadableComponent<Summary4TypeReportPage> {

	@FindBy(how = How.ID, using = "intervalStartDate")
	WebElement intervalStartDate;

	@FindBy(how = How.ID, using = "intervalEndDate")
	WebElement intervalEndDate;

	@FindBy(how = How.ID, using = "criteria_orgSearchTypeFind by Org Name")
	WebElement findByOrgNameRadio;

	@FindBy(how = How.ID, using = "autocomplete")
	WebElement findByOrgNameField;

	@FindBy(how = How.ID, using = "criteria_orgSearchTypeFind by Family")
	WebElement findByFamilyRadio;

	@FindBy(how = How.ID, using = "familyId")
	WebElement findByFamilyDropDown;

	@FindBy(how = How.ID, using = "orgNames")
	WebElement findByFamilyOrgNames;

	@FindBy(how = How.ID, using = "organization_choices")
	WebElement noOrganizationsFoundMessage;

	@FindBy(how = How.ID, using = "orgSelectAllCheckbox")
	WebElement selectAllCheckbox;

	@FindBy(how = How.LINK_TEXT, using = "Run report")
	WebElement runReportButton;

	@FindBy(how = How.LINK_TEXT, using = "Reset")
	WebElement resetButton;

	private final WebDriver webDriver;

	public Summary4TypeReportPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setDateRange(String startDate, String endDate) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setDateRange");
		PageUtil.setDateInterval(intervalStartDate, startDate, intervalEndDate,
				endDate);
		split.stop();
	}

	public void setStartDate(String startDate) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setStartDate");
		PageUtil.setDate(intervalStartDate, startDate);
		split.stop();
	}

	public void setEndDate(String endDate) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setEndDate");
		PageUtil.setDate(intervalEndDate, endDate);
		split.stop();
	}

	public void setFindByOrgName(String orgName) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setFindByOrgName");
		findByOrgNameRadio.click();
		findByOrgNameField.sendKeys(orgName);
		split.stop();
	}

	public void clickFindByOrgNameRadio() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setFindByOrgName");
		findByOrgNameRadio.click();
		split.stop();
	}

	public void clickFindByFamilyRadio() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "clickFindByFamilyRadio");
		findByFamilyRadio.click();
		split.stop();

	}

	public void setFindByFamily(String familyName) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setFindByFamily");
		findByFamilyRadio.click();
		// Special case when the database has no data. There's nothing to select
		// from the list except for "--Select--". Selenium will present a
		// timeout attempting to set the dropdown. This is the correct behavior.
		PageUtil.setDropDown(findByFamilyDropDown, familyName);
		waitForLoadingToFinish();
		split.stop();
	}

	public List<String> getFamilyList() {
		return PageUtil.getOptions(findByFamilyDropDown);
	}

	public Summary4TypeReportTable clickRunReportButton() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "clickRunReportButton");
		runReportButton.click();
		split.stop();
		return new Summary4TypeReportTable(webDriver);
	}

	public Summary4TypeReportPage clickResetButton() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "clickResetButton");
		resetButton.click();
		split.stop();
		return new Summary4TypeReportPage(webDriver);
	}

	public void setOrganizations(List<String> selections) {
		PageUtil.setMultiSelect(findByFamilyOrgNames, selections);
	}

	public void setSelectAllCheckbox(boolean selectAll) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "setSelectAllCheckbox");
		PageUtil.setCheckbox(selectAllCheckbox, selectAll);
		split.stop();
	}

	public Summary4ReportMessage getMessage() {
		WebElement errorMessage = null;
		try {
			WebElement adHocResults = webDriver.findElement(By.id("criteria"));
			errorMessage = adHocResults.findElement(By
					.className("errorMessage"));
		} catch (NoSuchElementException e) {
			return Summary4ReportMessage.NO_ERROR_FOUND;
		}
		String errorText = errorMessage.getText();
		if (errorText.equals("ERROR: End date must not be before start date.")) {
			return Summary4ReportMessage.DATE_INTERVAL_ERROR;
		} else if (errorText.equals("Invalid date.")) {
			return Summary4ReportMessage.INVALID_DATE;
		} else if (errorText.equals("An Organization name is required.")) {
			return Summary4ReportMessage.ORG_NAME_REQUIRED;
		} else if (errorText.equals("A Start Date is required.")) {
			return Summary4ReportMessage.START_DATE_REQUIRED;
		} else if (errorText.equals("An End Date is required.")) {
			return Summary4ReportMessage.END_DATE_REQUIRED;
		} else if (errorText.contains("A Start Date is required.")
				&& errorText.contains("An End Date is required.")) {
			return Summary4ReportMessage.START_AND_END_DATE_REQUIRED;
		} else if (errorText
				.equals("ERROR: End date must not be in the future.")) {
			return Summary4ReportMessage.INVALID_END_DATE;
		} else {
			return null;
		}
	}

	private void waitForLoadingToFinish() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "waitForLoadingToFinish");
		// PO-4297
		// TODO This is GROSS!!! If no diseases families are entered in the
		// database the Find By Familty drop down doesn’t tell you that “No
		// organizations found” until you click Reset or Run Report.
		WebElement noOrgsFound = webDriver.findElement(By
				.id("organization_choices"));
		String MESSAGE = "No organizations found.";

		if (!noOrgsFound.getText().contains(MESSAGE)) {
			// TODO Waiting to this to appear won't work if there aren't any
			// disease families in the database.
			WebElement diseasesForm = (new WebDriverWait(webDriver, 10))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("orgNames"));
						}
					});

			// If it contains "Loading..." look again.
			while (diseasesForm.getText().contains("Loading...")) {
				diseasesForm = (new WebDriverWait(webDriver, 10))
						.until(new ExpectedCondition<WebElement>() {
							public WebElement apply(WebDriver d) {
								return d.findElement(By.id("orgNames"));
							}
						});
			}
		}
		split.stop();

	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Summary 4 Type Report".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Summary 4 Type Report page!");
		}

	}

	@Override
	protected void load() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"Summary4TypeReportPage", "load");
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickSummary4TypeLink();
		split.stop();
	}

}
