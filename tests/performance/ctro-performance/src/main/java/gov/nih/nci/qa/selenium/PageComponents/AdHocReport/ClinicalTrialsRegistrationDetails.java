package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.util.PageUtil;
import gov.nih.nci.qa.selenium.util.SplitUtil;

import java.util.List;

import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * This is the accordion
 * 
 * @author kgann
 * 
 */
public class ClinicalTrialsRegistrationDetails {

	@FindBy(how = How.ID, using = "officialTitle")
	WebElement officialTitleTextBox;

	@FindBy(how = How.ID, using = "primaryPurpose")
	WebElement primaryPurposeDropDown;

	@FindBy(how = How.ID, using = "phaseCodes")
	WebElement trialPhaseMultiSelect;

	@FindBy(how = How.ID, using = "identifierType")
	WebElement identifierTypeDropDown;

	@FindBy(how = How.ID, using = "principalInvestigatorId")
	WebElement principalInvestigatorDropDown;

	@FindBy(how = How.ID, using = "documentWorkflowStatusCode")
	WebElement processingStatusDropDown;

	@FindBy(how = How.ID, using = "identifier")
	WebElement identifierTextBox;

	@FindBy(how = How.ID, using = "submissionType")
	WebElement searchBySubmissionTypeDropDown;

	@FindBy(how = How.ID, using = "trialCategory")
	WebElement searchByTrialCategoryDropDown;

	@FindBy(how = How.ID, using = "studyMilestone")
	WebElement milestoneDropDown;

	@FindBy(how = How.ID, using = "studyStatusCode")
	WebElement currentTrialStatusDropDown;

	@FindBy(how = How.ID, using = "leadOrganizationId")
	WebElement leadOrganizationDropDown;

	private final WebDriver webDriver;

	public ClinicalTrialsRegistrationDetails(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setClinicalTrialsRegistrationDetails(
			ClinicalTrialRegistrationDetailsParam params) {
		// If things aren't null set them.
		if (params.getOfficialTitleTextBox() != null) {
			setOfficialTitle(params.getOfficialTitleTextBox());
		}
		if (params.getPrimaryPurposeDropDown() != null) {
			setPrimaryPurpose(params.getPrimaryPurposeDropDown());
		}
		if (params.getTrialPhaseMultiSelect() != null) {
			setTrialPhase(params.getTrialPhaseMultiSelect());
		}
		if (params.getIdentifierTypeDropDown() != null) {
			setIdentifierType(params.getIdentifierTypeDropDown());
		}
		if (params.getIdentifierTextBox() != null) {
			setIdentifier(params.getIdentifierTextBox());
		}
		if (params.getLeadOrganizationDropDown() != null) {
			setLeadOrganization(params.getLeadOrganizationDropDown());
		}
		if (params.getPrincipalInvestigatorDropDown() != null) {
			setPrincipalInvestigator(params.getPrincipalInvestigatorDropDown());
		}
		if (params.getProcessingStatusDropDown() != null) {
			setProcessingStatus(params.getProcessingStatusDropDown());
		}
		if (params.getCurrentTrialStatusDropDown() != null) {
			setCurrentTrialStatus(params.getCurrentTrialStatusDropDown());
		}
		if (params.getSearchBySubmissionTypeDropDown() != null) {
			setSearchBySubmissionType(params
					.getSearchBySubmissionTypeDropDown());
		}
		if (params.getSearchByTrialCategoryDropDown() != null) {
			setSearchByTrialCategory(params.getSearchByTrialCategoryDropDown());
		}
		if (params.getMilestoneDropDown() != null) {
			setMilestone(params.getMilestoneDropDown());
		}
	}

	private void setOfficialTitle(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setOfficialTitle");
		officialTitleTextBox.clear();
		officialTitleTextBox.sendKeys(keysToSend);
		split.stop();
	}

	private void setPrimaryPurpose(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setPrimaryPurpose");
		PageUtil.setDropDown(primaryPurposeDropDown, keysToSend);
		split.stop();
	}

	private void setTrialPhase(List<String> selectList) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setTrialPhase");
		PageUtil.setMultiSelect(trialPhaseMultiSelect, selectList);
		split.stop();
	}

	private void setIdentifierType(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setIdentifierType");
		PageUtil.setDropDown(identifierTypeDropDown, keysToSend);
		split.stop();
	}

	private void setPrincipalInvestigator(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setPrincipalInvestigator");
		PageUtil.setDropDown(principalInvestigatorDropDown, keysToSend);
		split.stop();
	}

	private void setProcessingStatus(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setProcessingStatus");
		PageUtil.setDropDown(processingStatusDropDown, keysToSend);
		split.stop();
	}

	private void setCurrentTrialStatus(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setCurrentTrialStatus");
		PageUtil.setDropDown(currentTrialStatusDropDown, keysToSend);
		split.stop();
	}

	private void setLeadOrganization(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setLeadOrganization");
		PageUtil.setDropDown(leadOrganizationDropDown, keysToSend);
		split.stop();
	}

	private void setIdentifier(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setIdentifier");
		identifierTextBox.clear();
		identifierTextBox.sendKeys(keysToSend);
		split.stop();
	}

	private void setSearchBySubmissionType(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setSearchBySubmissionType");
		PageUtil.setDropDown(searchBySubmissionTypeDropDown, keysToSend);
		split.stop();
	}

	private void setSearchByTrialCategory(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setSearchByTrialCategory");
		PageUtil.setDropDown(searchByTrialCategoryDropDown, keysToSend);
		split.stop();
	}

	private void setMilestone(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit("AdHocReportPage",
				"setMilestone");
		PageUtil.setDropDown(milestoneDropDown, keysToSend);
		split.stop();
	}

}
