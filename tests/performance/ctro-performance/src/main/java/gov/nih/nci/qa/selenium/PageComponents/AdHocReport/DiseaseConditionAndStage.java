package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.PageComponents.AdHocPageDiseaseLookUp;
import gov.nih.nci.qa.selenium.PageComponents.DiseaseLocatorResultsTable;

import java.util.List;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DiseaseConditionAndStage {

	@FindBy(how = How.CSS, using = "span.search")
	WebElement lookUpButton;

	private final WebDriver webDriver;

	public DiseaseConditionAndStage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
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

}
