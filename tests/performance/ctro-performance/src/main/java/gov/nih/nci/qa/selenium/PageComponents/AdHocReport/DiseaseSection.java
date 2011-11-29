package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.PageComponents.AdHocPageDiseaseLookUp;
import gov.nih.nci.qa.selenium.util.SplitUtil;

import org.javasimon.Split;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DiseaseSection {

	@FindBy(how = How.CSS, using = "span.search")
	WebElement lookUpButton;

	@FindBy(how = How.ID, using = "disease")
	WebElement diseaseField;

	@FindBy(how = How.ID, using = "add_all_disease")
	WebElement addAllButton;

	@FindBy(how = How.ID, using = "show_tree_disease")
	WebElement showTreeButton;

	@FindBy(how = How.ID, using = "reset_disease")
	WebElement resetButton;

	@FindBy(how = How.ID, using = "disease_selections_count")
	WebElement diseaseSelectionCount;

	private final WebDriver webDriver;

	public DiseaseSection(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public AdHocPageDiseaseLookUp clickLookUpButton() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "clickLookUpButton");
		lookUpButton.click();
		split.stop();
		return new AdHocPageDiseaseLookUp(webDriver);
	}

	public String getDiseaseSelectionCount() {
		return diseaseSelectionCount.getText();
	}

	public void setDiseaseCondition(String keysToSend, Boolean includeSynonym,
			Boolean exactMatchOnly) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setDiseaseCondition");
		diseaseField.clear();
		diseaseField.sendKeys(keysToSend);
		diseaseField.sendKeys(Keys.RETURN);
		addAllButton.click();

		split.stop();
	}

}
