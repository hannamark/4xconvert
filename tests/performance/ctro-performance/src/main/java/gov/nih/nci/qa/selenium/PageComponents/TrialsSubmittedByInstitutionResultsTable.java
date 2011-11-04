package gov.nih.nci.qa.selenium.PageComponents;

import gov.nih.nci.qa.selenium.util.TableUtil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class TrialsSubmittedByInstitutionResultsTable {

	@FindBy(how = How.ID, using = "row")
	WebElement resultsTable;

	private TableUtil tableHelper;

	private static int NCI_TRIAL_IDENTIFIER_COLUMN = 0;
	private static int SUBMISSION_TYPE_COLUMN = 1;
	private static int SUBMITTED_ORGANIZATION_COLUMN = 2;
	private static int LEAD_ORG_TRIAL_IDENTIFIER_COLUMN = 3;
	private static int LEAD_ORGANIZATION_COLUMN = 4;
	private static int SUBMISSION_DATE_COLUMN = 5;
	private static int CURRENT_PROCESSING_STATUS_COLUMN = 6;
	private static int CURRENT_PROCESSING_DATE_COLUMN = 7;
	private static int CURRENT_MILESTONE_COLUMN = 8;
	private static int ADMIN_MILESTONE_COLUMN = 9;
	private static int SCIENTIFIC_MILESTONE_COLUMN = 10;

	private WebDriver webDriver;

	public TrialsSubmittedByInstitutionResultsTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);

		tableHelper = new TableUtil(resultsTable);
	}

	public int getResultCount() {
		return tableHelper.getRowCount();
	}

	public String getNciTrialIdentifier(int row) {
		return tableHelper.getCellValue(NCI_TRIAL_IDENTIFIER_COLUMN, row);
	}

	public String getSubmissionType(int row) {
		return tableHelper.getCellValue(SUBMISSION_TYPE_COLUMN, row);
	}

	public String getSubmittedOrganization(int row) {
		return tableHelper.getCellValue(SUBMITTED_ORGANIZATION_COLUMN, row);
	}

	public String getLeadOrgTrialIdentifier(int row) {
		return tableHelper.getCellValue(LEAD_ORG_TRIAL_IDENTIFIER_COLUMN, row);
	}

	public String getLeadOrganization(int row) {
		return tableHelper.getCellValue(LEAD_ORGANIZATION_COLUMN, row);
	}

	public String getSubmissionDate(int row) {
		return tableHelper.getCellValue(SUBMISSION_DATE_COLUMN, row);
	}

	public String getCurrentProcessingStatus(int row) {
		return tableHelper.getCellValue(CURRENT_PROCESSING_STATUS_COLUMN, row);
	}

	public String getCurrentProcessingDate(int row) {
		return tableHelper.getCellValue(CURRENT_PROCESSING_DATE_COLUMN, row);
	}

	public String getCurrentMileStone(int row) {
		return tableHelper.getCellValue(CURRENT_MILESTONE_COLUMN, row);
	}

	public String getAdminMilestone(int row) {
		return tableHelper.getCellValue(ADMIN_MILESTONE_COLUMN, row);
	}

	public String getScientificMilestone(int row) {
		return tableHelper.getCellValue(SCIENTIFIC_MILESTONE_COLUMN, row);
	}

}
