package gov.nih.nci.qa.selenium.PageComponents;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;
import gov.nih.nci.qa.selenium.util.TableUtil;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AdHocReportTable {
	@FindBy(how = How.ID, using = "resultsTab")
	WebElement resultsTab;

	@FindBy(how = How.ID, using = "row")
	WebElement tableRow;

	private static final int PROCESSING_STATUS_DATE_COLUMN = 0;
	private static final int NCI_TRIAL_IDENTIFIER_COLUMN = 1;
	private static final int OFFICIAL_TITLE_COLUMN = 2;
	private static final int LEAD_ORGANIZATION_COLUMN = 3;
	private static final int LEAD_ORG_ID_COLUMN = 4;
	private static final int PRINCIPAL_INVESTIGATOR_COLUMN = 5;
	private static final int PRIMARY_PURPOSE_COLUMN = 6;
	private static final int DISEASE_CONDITION_COLUMN = 7;
	private static final int INTERVENTION_TYPE_COLUMN = 8;
	private static final int MILESTONE_COLUMN = 9;
	private static final int ADMIN_MILESTONE_COLUMN = 10;
	private static final int SCIENTIFIC_MILESTONE_COLUMN = 11;
	private static final int PROCESSING_STATUS_COLUMN = 12;
	private static final int SUBMISSION_TYPE_COLUMN = 13;
	private static final int TRIAL_PHASE_COLUMN = 14;
	private static final int SUMMARY_4_FUNDING_CATEGORY_COLUMN = 15;
	private static final int TRIAL_STATUS_COLUMN = 16;
	private static final int VIEW_TSR_COLUMN = 17;

	private WebDriver webDriver;
	TableUtil resultsTableHelper;

	public AdHocReportTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		resultsTableHelper = new TableUtil(tableRow);
	}

	public String getViewTsr(int row) {
		return resultsTableHelper.getCellValue(VIEW_TSR_COLUMN, row);
	}

	public String getTrialStatus(int row) {
		return resultsTableHelper.getCellValue(TRIAL_STATUS_COLUMN, row);
	}

	public String getSummary4FundingCategory(int row) {
		return resultsTableHelper.getCellValue(
				SUMMARY_4_FUNDING_CATEGORY_COLUMN, row);
	}

	public String getTrialPhase(int row) {
		return resultsTableHelper.getCellValue(TRIAL_PHASE_COLUMN, row);
	}

	public String getSubmissionType(int row) {
		return resultsTableHelper.getCellValue(SUBMISSION_TYPE_COLUMN, row);
	}

	public String getProcessingStatus(int row) {
		return resultsTableHelper.getCellValue(PROCESSING_STATUS_COLUMN, row);
	}

	public String getScientificMilestone(int row) {
		return resultsTableHelper
				.getCellValue(SCIENTIFIC_MILESTONE_COLUMN, row);
	}

	public String getAdminMilestone(int row) {
		return resultsTableHelper.getCellValue(ADMIN_MILESTONE_COLUMN, row);
	}

	public String getMilestone(int row) {
		return resultsTableHelper.getCellValue(MILESTONE_COLUMN, row);
	}

	public String getInterventionType(int row) {
		return resultsTableHelper.getCellValue(INTERVENTION_TYPE_COLUMN, row);
	}

	public String getDiseaseCondition(int row) {
		return resultsTableHelper.getCellValue(DISEASE_CONDITION_COLUMN, row);
	}

	public String getPrimaryPurpose(int row) {
		return resultsTableHelper.getCellValue(PRIMARY_PURPOSE_COLUMN, row);
	}

	public String getPrincipalInvestigator(int row) {
		return resultsTableHelper.getCellValue(PRINCIPAL_INVESTIGATOR_COLUMN,
				row);
	}

	public String getLeadOrgId(int row) {
		return resultsTableHelper.getCellValue(LEAD_ORG_ID_COLUMN, row);
	}

	public String getLeadOrganization(int row) {
		return resultsTableHelper.getCellValue(LEAD_ORGANIZATION_COLUMN, row);
	}

	public String getProcessingStatusDate(int row) {
		return resultsTableHelper.getCellValue(PROCESSING_STATUS_DATE_COLUMN,
				row);
	}

	public String getNciTrialIdentifier(int row) {
		return resultsTableHelper
				.getCellValue(NCI_TRIAL_IDENTIFIER_COLUMN, row);
	}

	public String getOfficialTitle(int row) {
		return resultsTableHelper.getCellValue(OFFICIAL_TITLE_COLUMN, row);
	}

	public boolean hasResults() {
		if (getResultCount() > 0) {
			return true;
		}
		return false;
	}

	public int getResultCount() {
		// Don't assume there are results. There might be an error.
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver);
		AdHocReportMessage message = adHocReportPage.getMessage();
		if (!message.equals(AdHocReportMessage.NO_ERROR_FOUND)) {
			return 0;
		}

		WebElement adHocReport = webDriver.findElement(By.id("resultsTab"));
		List<WebElement> tableDataCells = adHocReport.findElements(By
				.tagName("td"));
		Iterator<WebElement> it = tableDataCells.iterator();
		while (it.hasNext()) {
			String currentText = it.next().getText();
			// Assume the message is correct.
			if (currentText.contains("Nothing found to display.")) {
				return 0;
			}
			if (currentText.contains("At least one criteria is required.")) {
				return 0;
			}
		}

		List<WebElement> pageBanners = adHocReport.findElements(By
				.className("pagebanner"));
		if (pageBanners.size() > 1) {
			// this is bad.
			return -1;
		}

		WebElement pageBanner = pageBanners.get(0);
		String pageBannerText = pageBanner.getText();

		if (pageBannerText.contains("One item found.")) {
			return 1;
		} else {

		}

		String[] split = pageBannerText.split(" +");
		String number = split[0];

		// Remove the comma and return it.
		return (Integer.valueOf(StringUtils.remove(number, ",")));
	}

}
