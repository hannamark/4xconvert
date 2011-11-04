package gov.nih.nci.qa.selenium.PageComponents;

import gov.nih.nci.qa.selenium.util.TableUtil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SummaryOfSubmissionResultsTable {

	@FindBy(how = How.ID, using = "row")
	WebElement resultsTable;

	private TableUtil tableHelper;

	private static int SUBMITTING_SITE_COLUMN = 0;
	private static int NUMBER_OF_ORIGINAL_SUBMISSIONS_COLUMN = 1;
	private static int NUMBER_OF_SUBMITTED_AMENDMENTS_COLUMN = 2;
	private static int NUMBER_OF_SUBMISSIONS_COLUMN = 3;

	private WebDriver webDriver;

	public SummaryOfSubmissionResultsTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);

		tableHelper = new TableUtil(resultsTable);
	}

	public String getSubmittingSite(int row) {
		return tableHelper.getCellValue(SUBMITTING_SITE_COLUMN, row);
	}

	public String getNumberOfOriginalSubmissions(int row) {
		return tableHelper.getCellValue(NUMBER_OF_ORIGINAL_SUBMISSIONS_COLUMN,
				row);
	}

	public String getNumberOfSubmittedAmendments(int row) {
		return tableHelper.getCellValue(NUMBER_OF_SUBMITTED_AMENDMENTS_COLUMN,
				row);
	}

	public String getNumberOfSubmissions(int row) {
		return tableHelper.getCellValue(NUMBER_OF_SUBMISSIONS_COLUMN, row);
	}
}
