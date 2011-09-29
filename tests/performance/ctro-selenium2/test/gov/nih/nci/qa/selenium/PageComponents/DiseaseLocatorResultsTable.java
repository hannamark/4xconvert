package gov.nih.nci.qa.selenium.PageComponents;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DiseaseLocatorResultsTable extends
		LoadableComponent<DiseaseLocatorResultsTable> {
	@FindBy(how = How.ID, using = "row")
	WebElement tableRow;

	private final WebDriver webDriver;

	public DiseaseLocatorResultsTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public boolean hasResults() {
		waitForLoadingToFinish();

		// Get the table rows.
		List<WebElement> tableRowList = webDriver
				.findElements(By.tagName("tr"));
		Iterator<WebElement> it = tableRowList.iterator();
		while (it.hasNext()) {
			WebElement currentRow = it.next();
			String text = currentRow.getText();
			if (text.contains("Nothing found to display.")) {
				return false;
			}
		}

		// Assume that there are now rows to check.
		WebElement tableBodyElement = webDriver
				.findElement(By.tagName("tbody"));
		List<WebElement> tableRows = tableBodyElement.findElements(By
				.tagName("tr"));
		if (tableRows.size() > 0) {
			return true;
		}
		return false;
	}

	public void clickSelectButton() {
		waitForLoadingToFinish();

		WebElement selectButton = webDriver.findElement(By.className("action"));
		WebElement btnElement = selectButton.findElement(By.className("btn"));
		btnElement.click();
	}

	private void waitForLoadingToFinish() {
		WebElement diseasesForm = (new WebDriverWait(webDriver, 10))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.id("getDiseases"));
					}
				});

		// If it contains "Loading..." look again.
		// int counter = 0;
		while (diseasesForm.getText().contains("Loading...")) {
			// System.out.println("disease row wait count [" + counter + "].");
			diseasesForm = (new WebDriverWait(webDriver, 10))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("getDiseases"));
						}
					});
			// counter++;
		}
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
		// TODO Auto-generated method stub
	}

}
