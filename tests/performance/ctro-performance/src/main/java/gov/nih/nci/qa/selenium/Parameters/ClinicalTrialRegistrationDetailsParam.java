package gov.nih.nci.qa.selenium.Parameters;

import java.util.List;

/**
 * This is a class to hold the parameters for the Clinical Trial Registration
 * Details accordion.
 * 
 * @author kgann
 * 
 */
public class ClinicalTrialRegistrationDetailsParam {

	private String officialTitleTextBox;
	private String primaryPurposeDropDown;
	private List<String> trialPhaseMultiSelect;
	private String identifierTypeDropDown;
	private String principalInvestigatorDropDown;
	private String processingStatusDropDown;
	private String identifierTextBox;
	private String searchBySubmissionTypeDropDown;
	private String searchByTrialCategoryDropDown;
	private String milestoneDropDown;
	private String currentTrialStatusDropDown;
	private String leadOrganizationDropDown;

	public String getOfficialTitleTextBox() {
		return officialTitleTextBox;
	}

	public void setOfficialTitleTextBox(String officialTitleTextBox) {
		this.officialTitleTextBox = officialTitleTextBox;
	}

	public String getPrimaryPurposeDropDown() {
		return primaryPurposeDropDown;
	}

	public void setPrimaryPurposeDropDown(String primaryPurposeDropDown) {
		this.primaryPurposeDropDown = primaryPurposeDropDown;
	}

	public List<String> getTrialPhaseMultiSelect() {
		return trialPhaseMultiSelect;
	}

	public void setTrialPhaseMultiSelect(List<String> trialPhaseMultiSelect) {
		this.trialPhaseMultiSelect = trialPhaseMultiSelect;
	}

	public String getIdentifierTypeDropDown() {
		return identifierTypeDropDown;
	}

	public void setIdentifierTypeDropDown(String identifierTypeDropDown) {
		this.identifierTypeDropDown = identifierTypeDropDown;
	}

	public String getPrincipalInvestigatorDropDown() {
		return principalInvestigatorDropDown;
	}

	public void setPrincipalInvestigatorDropDown(
			String principalInvestigatorDropDown) {
		this.principalInvestigatorDropDown = principalInvestigatorDropDown;
	}

	public String getProcessingStatusDropDown() {
		return processingStatusDropDown;
	}

	public void setProcessingStatusDropDown(String processingStatusDropDown) {
		this.processingStatusDropDown = processingStatusDropDown;
	}

	public String getIdentifierTextBox() {
		return identifierTextBox;
	}

	public void setIdentifierTextBox(String identifierTextBox) {
		this.identifierTextBox = identifierTextBox;
	}

	public String getSearchBySubmissionTypeDropDown() {
		return searchBySubmissionTypeDropDown;
	}

	public void setSearchBySubmissionTypeDropDown(
			String searchBySubmissionTypeDropDown) {
		this.searchBySubmissionTypeDropDown = searchBySubmissionTypeDropDown;
	}

	public String getSearchByTrialCategoryDropDown() {
		return searchByTrialCategoryDropDown;
	}

	public void setSearchByTrialCategoryDropDown(
			String searchByTrialCategoryDropDown) {
		this.searchByTrialCategoryDropDown = searchByTrialCategoryDropDown;
	}

	public String getMilestoneDropDown() {
		return milestoneDropDown;
	}

	public void setMilestoneDropDown(String milestoneDropDown) {
		this.milestoneDropDown = milestoneDropDown;
	}

	public String getCurrentTrialStatusDropDown() {
		return currentTrialStatusDropDown;
	}

	public void setCurrentTrialStatusDropDown(String currentTrialStatusDropDown) {
		this.currentTrialStatusDropDown = currentTrialStatusDropDown;
	}

	public String getLeadOrganizationDropDown() {
		return leadOrganizationDropDown;
	}

	public void setLeadOrganizationDropDown(String leadOrganizationDropDown) {
		this.leadOrganizationDropDown = leadOrganizationDropDown;
	}

}
