package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.PageComponents.DiseaseLocatorResultsTable;
import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.util.PageUtil;
import gov.nih.nci.qa.selenium.util.SplitUtil;

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
		Split split = SplitUtil.getPageElementSplit("AdHocPageDiseaseLookUp",
				"clickSearchButton");
		lookUpButton.click();
		split.stop();
		return new DiseaseLocatorResultsTable(webDriver);
	}

	public AdHocReportPage clickSelectButton() {
		Split split = SplitUtil.getPageElementSplit("AdHocPageDiseaseLookUp",
				"clickSelectButton");
		diseaseLocatorResultsTable.clickSelectButton();
		split.stop();
		return new AdHocReportPage(webDriver);
	}

	public AdHocReportPage clickCloseButton() {
		Split split = SplitUtil.getPageElementSplit("AdHocPageDiseaseLookUp",
				"clickCloseButton");
		closeButton.click();
		split.stop();
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
	}

}
