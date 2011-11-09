package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.PageComponents.AdHocReportTable;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.Biomarkers;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.ClinicalTrialsRegistrationDetails;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.DiseaseConditionAndStage;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.Interventions;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.ParticipatingSites;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.Summary4AnatomicSite;
import gov.nih.nci.qa.selenium.PageComponents.AdHocReport.TrialGeographicArea;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.enumerations.AdHocReportMessage;

import java.util.List;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class AdHocReportPage extends LoadableComponent<AdHocReportPage> {

	@FindBy(how = How.LINK_TEXT, using = "Report Filters")
	WebElement reportFiltersTab;

	@FindBy(how = How.LINK_TEXT, using = "Results")
	WebElement resultsTab;

	@FindBy(how = How.LINK_TEXT, using = "Clinical Trial Registration Details")
	WebElement clinicalTrialRegistrationDetailsLink;

	@FindBy(how = How.LINK_TEXT, using = "Disease/Condition and Stage")
	WebElement diseaseConditionAndStageLink;

	@FindBy(how = How.LINK_TEXT, using = "Interventions")
	WebElement interventionsLink;

	@FindBy(how = How.LINK_TEXT, using = "Participating Sites")
	WebElement participatingSitesLink;

	@FindBy(how = How.LINK_TEXT, using = "Biomarkers")
	WebElement biomarkersLink;

	@FindBy(how = How.LINK_TEXT, using = "Run Report")
	WebElement runReportButton;

	@FindBy(how = How.LINK_TEXT, using = "Reset")
	WebElement resetButton;

	@FindBy(how = How.LINK_TEXT, using = "Trial Geographic Area")
	WebElement trialGeographicAreaLink;

	@FindBy(how = How.LINK_TEXT, using = "Summary 4 Anatomic Site")
	WebElement summary4AnatomicSiteLink;

	AdHocReportTable adHocResultsTable;

	private final WebDriver webDriver;

	public AdHocReportPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setClinicalTrialsRegistrationDetails(
			ClinicalTrialRegistrationDetailsParam params) {
		ClinicalTrialsRegistrationDetails clinicalTrialRegistrationDetails = clickClinicalTrialRegistrationDetails();
		clinicalTrialRegistrationDetails
				.setClinicalTrialsRegistrationDetails(params);
	}

	public void setInterventions(String keysToSend) {
		Interventions interventions = clickInterventions();
		interventions.setInterventionType(keysToSend);
	}

	public void setParticipatingSites(String keysToSend) {
		ParticipatingSites sites = clickParticipatingSites();
		sites.setParticipatingSites(keysToSend);
	}

	public void setDiseaseConditionAndStage(String keysToSend,
			Boolean includeSynonym, Boolean exactMatchOnly) {
		DiseaseConditionAndStage diseaseAndStage = clickDiseaseAndStage();
		diseaseAndStage.setDiseaseCondition(keysToSend, includeSynonym,
				exactMatchOnly);
	}

	public void setTrialGeographicArea(String country, List<String> states,
			String city) {
		TrialGeographicArea geographicArea = clickTrialGeographicArea();
		geographicArea.setTrialGeographicArea(country, states, city);
	}

	public void setSummary4AnatomicSite(String sponsor, List<String> sites,
			String fundingCategory) {
		Summary4AnatomicSite summary4AnatomicSite = clickSummary4AnatomicSite();
		summary4AnatomicSite.setSummary4AnatomicSite(sponsor, sites,
				fundingCategory);
	}

	public void setBiomarkers(List<String> selectList) {
		Biomarkers biomarkers = clickBiomarkers();
		biomarkers.setBiomarkers(selectList);
	}

	public AdHocReportTable clickRunReportButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickRunReportButton")
				.start();
		// Make certain we're on the report filters tab. The reset button
		// doesn't exist on the results tab.
		reportFiltersTab.click();
		runReportButton.click();
		split.stop();
		return new AdHocReportTable(webDriver);
	}

	public AdHocReportPage clickResetButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "clickResetButton")
				.start();
		// Make certain we're on the report filters tab. The reset button
		// doesn't exist on the results tab.
		reportFiltersTab.click();
		resetButton.click();
		split.stop();
		return new AdHocReportPage(webDriver);
	}

	public AdHocReportMessage getMessage() {
		WebElement adHocResults = webDriver.findElement(By.id("content"));
		WebElement errorMessage = null;
		try {
			errorMessage = adHocResults.findElement(By
					.className("errorMessage"));
		} catch (NoSuchElementException e) {
			return AdHocReportMessage.NO_ERROR_FOUND;
		}
		String errorText = errorMessage.getText();
		if (errorText
				.equals("An error has occurred when searching for trials.")) {
			return AdHocReportMessage.ERROR_SEARCHING;
		} else if (errorText.equals("At least one criteria is required.")) {
			return AdHocReportMessage.CRITERIA_ERROR;
		} else if (errorText.contains("General error")) {
			return AdHocReportMessage.GENERAL_ERROR;
		} else if (errorText.contains("Results exceed more than 500.")) {
			return AdHocReportMessage.RESULTS_EXCEEDED_500;
		} else {
			return null;
		}
	}

	// private methods
	private ClinicalTrialsRegistrationDetails clickClinicalTrialRegistrationDetails() {
		clinicalTrialRegistrationDetailsLink.click();
		return new ClinicalTrialsRegistrationDetails(webDriver);
	}

	private DiseaseConditionAndStage clickDiseaseAndStage() {
		diseaseConditionAndStageLink.click();
		return new DiseaseConditionAndStage(webDriver);
	}

	private Interventions clickInterventions() {
		interventionsLink.click();
		return new Interventions(webDriver);
	}

	private ParticipatingSites clickParticipatingSites() {
		participatingSitesLink.click();
		return new ParticipatingSites(webDriver);
	}

	private TrialGeographicArea clickTrialGeographicArea() {
		trialGeographicAreaLink.click();
		return new TrialGeographicArea(webDriver);
	}

	private Biomarkers clickBiomarkers() {
		biomarkersLink.click();
		return new Biomarkers(webDriver);
	}

	private Summary4AnatomicSite clickSummary4AnatomicSite() {
		summary4AnatomicSiteLink.click();
		return new Summary4AnatomicSite(webDriver);
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Ad-Hoc Report".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Ad-Hoc Report page!");
		}
	}

	@Override
	protected void load() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(
				webDriver).get();
		ctroReportSelectionPage.clickAdHocLink();
		split.stop();
	}
}
