package gov.nih.nci.qa.selenium.Parameters;

import java.util.ArrayList;
import java.util.List;

public class ClinicalTrialRegistrationDetailsBuilder {

	String officialTitleTextBox;
	String primaryPurposeDropDown;
	List<String> trialPhaseMultiSelect;
	String identifierTypeDropDown;
	String principalInvestigatorDropDown;
	String processingStatusDropDown;
	String identifierTextBox;
	String searchBySubmissionTypeDropDown;
	String searchByTrialCategoryDropDown;
	String milestoneDropDown;
	String currentTrialStatusDropDown;
	String leadOrganizationDropDown;

	public ClinicalTrialRegistrationDetailsBuilder() {
	}

	public ClinicalTrialRegistrationDetailsParam getParameters() {
		ClinicalTrialRegistrationDetailsParam params = new ClinicalTrialRegistrationDetailsParam();
		params.setOfficialTitleTextBox(officialTitleTextBox);
		params.setPrimaryPurposeDropDown(primaryPurposeDropDown);
		params.setTrialPhaseMultiSelect(trialPhaseMultiSelect);
		params.setIdentifierTypeDropDown(identifierTypeDropDown);
		params.setPrincipalInvestigatorDropDown(principalInvestigatorDropDown);
		params.setProcessingStatusDropDown(processingStatusDropDown);
		params.setIdentifierTextBox(identifierTextBox);
		params.setSearchBySubmissionTypeDropDown(searchBySubmissionTypeDropDown);
		params.setSearchByTrialCategoryDropDown(searchByTrialCategoryDropDown);
		params.setMilestoneDropDown(milestoneDropDown);
		params.setCurrentTrialStatusDropDown(currentTrialStatusDropDown);
		params.setLeadOrganizationDropDown(leadOrganizationDropDown);
		return params;
	}

	public void setOfficialTitle(String officialTitleTextBox) {
		this.officialTitleTextBox = officialTitleTextBox;
	}

	public void setPrimaryPurposeDropDown(String primaryPurposeDropDown) {
		this.primaryPurposeDropDown = primaryPurposeDropDown;
	}

	public void setTrialPhase(String trialPhase) {
		List<String> trialPhases = new ArrayList<String>();
		trialPhases.add(trialPhase);
		this.trialPhaseMultiSelect = trialPhases;
	}

	public void setTrialPhases(List<String> trialPhaseMultiSelect) {
		this.trialPhaseMultiSelect = trialPhaseMultiSelect;
	}

	public void setIdentifierTypeDropDown(String identifierTypeDropDown) {
		this.identifierTypeDropDown = identifierTypeDropDown;
	}

	public void setPrincipalInvestigatorDropDown(
			String principalInvestigatorDropDown) {
		this.principalInvestigatorDropDown = principalInvestigatorDropDown;
	}

	public void setProcessingStatusDropDown(String processingStatusDropDown) {
		this.processingStatusDropDown = processingStatusDropDown;
	}

	public void setIdentifierTextBox(String identifierTextBox) {
		this.identifierTextBox = identifierTextBox;
	}

	public void setSearchBySubmissionTypeDropDown(
			String searchBySubmissionTypeDropDown) {
		this.searchBySubmissionTypeDropDown = searchBySubmissionTypeDropDown;
	}

	public void setSearchByTrialCategoryDropDown(
			String searchByTrialCategoryDropDown) {
		this.searchByTrialCategoryDropDown = searchByTrialCategoryDropDown;
	}

	public void setMilestoneDropDown(String milestoneDropDown) {
		this.milestoneDropDown = milestoneDropDown;
	}

	public void setCurrentTrialStatusDropDown(String currentTrialStatusDropDown) {
		this.currentTrialStatusDropDown = currentTrialStatusDropDown;
	}

	public void setLeadOrganizationDropDown(String leadOrganizationDropDown) {
		this.leadOrganizationDropDown = leadOrganizationDropDown;
	}

}
