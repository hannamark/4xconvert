package gov.nih.nci.qa.selenium.PageObjects;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class CtroReportSelectionPage extends
		LoadableComponent<CtroReportSelectionPage> {

	@FindBy(how = How.LINK_TEXT, using = "Ad Hoc")
	private WebElement adHocLink;

	@FindBy(how = How.LINK_TEXT, using = "Summary 4 Type")
	private WebElement summary4TypeLink;

	@FindBy(how = How.LINK_TEXT, using = "Trials Submitted by Institution")
	private WebElement trialsSubmittedByInstitutionLink;

	@FindBy(how = How.LINK_TEXT, using = "Summary of Submission")
	private WebElement summaryOfSubmissionLink;

	@FindBy(how = How.LINK_TEXT, using = "Log Out")
	private WebElement logOutLink;

	private final WebDriver webDriver;

	public CtroReportSelectionPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public AdHocReportPage clickAdHocLink() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "CtroReportSelectionPage"
						+ Manager.HIERARCHY_DELIMITER + "clickAdHocLink")
				.start();
		adHocLink.click();
		split.stop();
		return new AdHocReportPage(webDriver);
	}

	public Summary4TypeReportPage clickSummary4TypeLink() {
		Split split = SimonManager
				.getStopwatch(
						"parent" + Manager.HIERARCHY_DELIMITER
								+ "CtroReportSelectionPage"
								+ Manager.HIERARCHY_DELIMITER
								+ "clickSummary4TypeLink").start();
		summary4TypeLink.click();
		split.stop();
		return new Summary4TypeReportPage(webDriver);
	}

	public TrialsSubmittedByInstitutionReportPage clickTrialsSubmittedByInstitutionLink() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "CtroReportSelectionPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "clickTrialsSubmittedByInstitutionLink").start();
		trialsSubmittedByInstitutionLink.click();
		split.stop();
		return new TrialsSubmittedByInstitutionReportPage(webDriver);
	}

	public SummaryOfSubmissionReportPage clickSummaryOfSubmissionLink() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "CtroReportSelectionPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "clickSummaryOfSubmissionLink").start();
		summaryOfSubmissionLink.click();
		split.stop();
		return new SummaryOfSubmissionReportPage(webDriver);
	}

	public HomePage clickLogOutLink() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "CtroReportSelectionPage"
						+ Manager.HIERARCHY_DELIMITER + "clickLogOutLink")
				.start();
		logOutLink.click();
		split.stop();
		return new HomePage(webDriver);
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"NCI CTRP Viewer".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("CtroReportSelectionPage");
		}
	}

	@Override
	protected void load() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER
						+ "CtroReportSelectionPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		DisclaimerPage disclaimerPage = new DisclaimerPage(webDriver).get();
		disclaimerPage.acceptDisclaimer();
		split.stop();
	}

}
