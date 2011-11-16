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

	public List<String> getPrimaryPurposeOptions() {
		return getOptions(primaryPurposeDropDown);
	}

	public List<String> getTrialPhases() {
		return getOptions(trialPhaseMultiSelect);
	}

	public List<String> getIdentifierTypes() {
		return getOptions(identifierTypeDropDown);
	}

	public List<String> getLeadOrganizations() {
		return getOptions(leadOrganizationDropDown);
	}

	public List<String> getPrincipalInvestigators() {
		return getOptions(principalInvestigatorDropDown);
	}

	public List<String> getProcessingStatuses() {
		return getOptions(processingStatusDropDown);
	}

	public List<String> getCurrentTrialStatues() {
		return getOptions(currentTrialStatusDropDown);
	}

	public List<String> getSubmissionTypes() {
		return getOptions(searchBySubmissionTypeDropDown);
	}

	public List<String> getTrialCategories() {
		return getOptions(searchByTrialCategoryDropDown);
	}

	public List<String> getMilestones() {
		return getOptions(milestoneDropDown);
	}

	// privates

	private List<String> getOptions(WebElement webElement) {
		return PageUtil.getOptions(webElement);
	}

	private void setOfficialTitle(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setOfficialTitle");
		officialTitleTextBox.clear();
		officialTitleTextBox.sendKeys(keysToSend);
		split.stop();
	}

	private void setPrimaryPurpose(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setPrimaryPurpose");
		PageUtil.setDropDown(primaryPurposeDropDown, keysToSend);
		split.stop();
	}

	private void setTrialPhase(List<String> selectList) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setTrialPhase");
		PageUtil.setMultiSelect(trialPhaseMultiSelect, selectList);
		split.stop();
	}

	private void setIdentifierType(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setIdentifierType");
		PageUtil.setDropDown(identifierTypeDropDown, keysToSend);
		split.stop();
	}

	private void setPrincipalInvestigator(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setPrincipalInvestigator");
		PageUtil.setDropDown(principalInvestigatorDropDown, keysToSend);
		split.stop();
	}

	private void setProcessingStatus(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setProcessingStatus");
		PageUtil.setDropDown(processingStatusDropDown, keysToSend);
		split.stop();
	}

	private void setCurrentTrialStatus(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setCurrentTrialStatus");
		PageUtil.setDropDown(currentTrialStatusDropDown, keysToSend);
		split.stop();
	}

	private void setLeadOrganization(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setLeadOrganization");
		PageUtil.setDropDown(leadOrganizationDropDown, keysToSend);
		split.stop();
	}

	private void setIdentifier(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setIdentifier");
		identifierTextBox.clear();
		identifierTextBox.sendKeys(keysToSend);
		split.stop();
	}

	private void setSearchBySubmissionType(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setSearchBySubmissionType");
		PageUtil.setDropDown(searchBySubmissionTypeDropDown, keysToSend);
		split.stop();
	}

	private void setSearchByTrialCategory(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setSearchByTrialCategory");
		PageUtil.setDropDown(searchByTrialCategoryDropDown, keysToSend);
		split.stop();
	}

	private void setMilestone(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setMilestone");
		PageUtil.setDropDown(milestoneDropDown, keysToSend);
		split.stop();
	}

}
