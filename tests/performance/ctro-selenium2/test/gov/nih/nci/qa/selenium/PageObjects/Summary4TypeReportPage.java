package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.Summary4TypeReportTable;
import gov.nih.nci.qa.selenium.enumerations.Summary4ReportMessage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
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
		intervalStartDate.clear();
		intervalStartDate.sendKeys(startDate);
		intervalEndDate.clear();
		intervalEndDate.sendKeys(endDate);
	}

	public void selectFindByOrgName(String orgName) {
		findByOrgNameRadio.click();
		findByOrgNameField.sendKeys(orgName);
	}

	public void setFindByFamily(String familyName) {
		findByFamilyRadio.click();
		setDropDown(findByFamilyDropDown, familyName);
		waitForLoadingToFinish();
	}

	public Summary4TypeReportTable clickRunReportButton() {
		runReportButton.click();
		return new Summary4TypeReportTable(webDriver);
	}

	public void setSelectAllCheckbox(Boolean selectAll) {
		if (selectAll) {
			selectAllCheckbox.click();
		} else {
			if (selectAllCheckbox.isSelected()) {
				selectAllCheckbox.click();
			}
		}
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
		} else if (errorText.equals("ERROR: End date must not be in the future.")){
			return Summary4ReportMessage.INVALID_END_DATE;
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

	private void waitForLoadingToFinish() {
		WebElement diseasesForm = (new WebDriverWait(webDriver, 10))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.id("orgNames"));
					}
				});

		// If it contains "Loading..." look again.
		int counter = 0;
		while (diseasesForm.getText().contains("Loading...")) {
			System.out.println("org names wait count [" + counter + "].");
			diseasesForm = (new WebDriverWait(webDriver, 10))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("orgNames"));
						}
					});
			counter++;
		}
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
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickSummary4TypeLink();
	}

}
