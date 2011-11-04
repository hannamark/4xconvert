package gov.nih.nci.qa.selenium.PageComponents;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.PageObjects.CtroReportSelectionPage;
import gov.nih.nci.qa.selenium.util.PageUtil;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class AdHocPageDiseaseLookUp extends
		LoadableComponent<AdHocPageDiseaseLookUp> {

	@FindBy(how = How.CSS, using = "span.search")
	WebElement lookUpButton;

	@FindBy(how = How.ID, using = "searchName")
	WebElement diseaseNameTextBox;

	@FindBy(how = How.ID, using = "includeSynonym")
	WebElement includeSynonymCheckbox;

	@FindBy(how = How.ID, using = "exactMatch")
	WebElement exactMatchOnlyCheckbox;

	@FindBy(how = How.ID, using = "getDiseases")
	WebElement diseasesList;

	@FindBy(how = How.CSS, using = "span.close")
	WebElement closeButton;

	private DiseaseLocatorResultsTable diseaseLocatorResultsTable;

	private final WebDriver webDriver;

	public AdHocPageDiseaseLookUp(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setDiseaseName(String keysToSend) {
		diseaseNameTextBox.sendKeys(keysToSend);
	}

	public void setIncludeSynonym(boolean includeSynonym) {
		PageUtil.setCheckbox(includeSynonymCheckbox, includeSynonym);
	}

	public void setExactMatch(boolean exactMatchOnly) {
		PageUtil.setCheckbox(exactMatchOnlyCheckbox, exactMatchOnly);
	}

	public DiseaseLocatorResultsTable clickSearchButton() {
		lookUpButton.click();
		return new DiseaseLocatorResultsTable(webDriver);
	}

	public AdHocReportPage clickSelectButton() {
		diseaseLocatorResultsTable.clickSelectButton();
		return new AdHocReportPage(webDriver);
	}

	public AdHocReportPage clickCloseButton() {
		closeButton.click();
		return new AdHocReportPage(webDriver);
	}

	@Override
	protected void isLoaded() throws Error {
		WebElement popUp = webDriver.findElement(By.id("popupTitle"));
		if (!"Disease".equals(popUp.getText())) {
			throw new IllegalStateException(
					"This is not the Search Diseases Popup!");
		}

	}

	@Override
	protected void load() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		AdHocReportPage adHocReportPage = ctroReportSelectionPage
				.clickAdHocLink();
		AdHocPageDiseaseLookUp adHocPageDiseaseLookUp = adHocReportPage
				.clickLookUpButton();
		split.stop();
	}

}
