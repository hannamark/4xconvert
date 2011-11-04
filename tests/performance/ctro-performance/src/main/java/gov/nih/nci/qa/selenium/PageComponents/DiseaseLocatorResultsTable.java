package gov.nih.nci.qa.selenium.PageComponents;

import gov.nih.nci.qa.selenium.util.TableUtil;

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

	@FindBy(how = How.ID, using = "getDiseases")
	WebElement diseasesList;

	private static final String NO_RESULTS_MESSAGE = "Nothing found to display.";
	private static final int NAME_COLUMN = 0;
	private static final int CODE_COLUMN = 1;
	private static final int NCI_THESAURUS_CONCEPT_ID_COLUMN = 2;
	private static final int MENU_DISPLAY_NAME_COLUMN = 3;
	private static final int PARENT_NAME_COLUMN = 4;
	private static final int SELECT_COLUMN = 5;

	private final WebDriver webDriver;
	TableUtil resultsTableHelper;

	public DiseaseLocatorResultsTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		resultsTableHelper = new TableUtil(tableRow);
	}

	public String getParentName(int row) {
		return resultsTableHelper.getCellValue(PARENT_NAME_COLUMN, row);
	}

	public String getMenuDisplayName(int row) {
		return resultsTableHelper.getCellValue(MENU_DISPLAY_NAME_COLUMN, row);
	}

	public String getNciThesaurusConceptId(int row) {
		return resultsTableHelper.getCellValue(NCI_THESAURUS_CONCEPT_ID_COLUMN,
				row);
	}

	public String getCode(int row) {
		return resultsTableHelper.getCellValue(CODE_COLUMN, row);
	}

	public String getName(int row) {
		return resultsTableHelper.getCellValue(NAME_COLUMN, row);
	}

	public boolean hasResults() {
		waitForLoadingToFinish();

		// Trust the results message.
		String message = diseasesList.getText();
		if (message.contains(NO_RESULTS_MESSAGE)) {
			return false;
		}

		// Get the table rows.
		TableUtil resultsTableHelper = new TableUtil(tableRow);
		if (resultsTableHelper.getRowCount() > 0) {
			return true;
		}

		return false;
	}

	public void clickSelectButton() {
		waitForLoadingToFinish();
		TableUtil resultsTableHelper = new TableUtil(tableRow);
		WebElement selectButton = resultsTableHelper.getCellWebElement(
				SELECT_COLUMN, 0);
		selectButton.click();
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
			diseasesForm = (new WebDriverWait(webDriver, 10))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(By.id("getDiseases"));
						}
					});
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
