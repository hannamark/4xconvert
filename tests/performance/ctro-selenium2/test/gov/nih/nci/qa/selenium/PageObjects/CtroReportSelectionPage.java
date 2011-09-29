package gov.nih.nci.qa.selenium.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class CtroReportSelectionPage extends
		LoadableComponent<CtroReportSelectionPage> {

	@FindBy(how = How.LINK_TEXT, using = "Ad Hoc")
	@CacheLookup
	private WebElement adHocLink;

	@FindBy(how = How.LINK_TEXT, using = "Summary 4 Type")
	@CacheLookup
	private WebElement summary4TypeLink;

	@FindBy(how = How.LINK_TEXT, using = "Current Milestone")
	@CacheLookup
	private WebElement currentMilestoneLink;

	@FindBy(how = How.LINK_TEXT, using = "Trials Submitted by Institution")
	@CacheLookup
	private WebElement trialsSubmittedByInstitutionLink;

	@FindBy(how = How.LINK_TEXT, using = "Trials Submitted by Date")
	@CacheLookup
	private WebElement trialsSubmittedByDateLink;

	@FindBy(how = How.LINK_TEXT, using = "Portfolio Average Milestone")
	@CacheLookup
	private WebElement portfolioAverageMilestoneLink;

	@FindBy(how = How.LINK_TEXT, using = "Trial Processing")
	@CacheLookup
	private WebElement trialProcessingLink;

	@FindBy(how = How.LINK_TEXT, using = "Summary of Submission")
	
	private WebElement summaryOfSubmissionLink;

	@FindBy(how = How.LINK_TEXT, using = "Log Out")
	@CacheLookup
	private WebElement logOutLink;

	private final WebDriver webDriver;

	public CtroReportSelectionPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public AdHocReportPage clickAdHocLink() {
		adHocLink.click();
		return new AdHocReportPage(webDriver);
	}

	public Summary4TypeReportPage clickSummary4TypeLink() {
		summary4TypeLink.click();
		return new Summary4TypeReportPage(webDriver);
	}

	public CurrentMilestoneReportPage clickCurrentMilestoneLink() {
		currentMilestoneLink.click();
		return new CurrentMilestoneReportPage(webDriver);
	}

	public TrialsSubmittedByInstitutionReportPage clickTrialsSubmittedByInstitutionLink() {
		trialsSubmittedByInstitutionLink.click();
		return new TrialsSubmittedByInstitutionReportPage(webDriver);
	}

	public TrialsSubmittedByDateReportPage clickTrialsSubmittedByDateLink() {
		trialsSubmittedByDateLink.click();
		return new TrialsSubmittedByDateReportPage(webDriver);
	}

	public PortfolioAverageMilestoneReportPage clickPortfolioAverageMilestoneLink() {
		portfolioAverageMilestoneLink.click();
		return new PortfolioAverageMilestoneReportPage(webDriver);
	}

	public TrialsProcessingReportPage clickTrialProcessingLink() {
		trialProcessingLink.click();
		return new TrialsProcessingReportPage(webDriver);
	}

	public SummaryOfSubmissionReportPage clickSummaryOfSubmissionLink() {
		summaryOfSubmissionLink.click();
		return new SummaryOfSubmissionReportPage(webDriver);
	}
	
	public HomePage clickLogOutLink(){
		logOutLink.click();
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
		DisclaimerPage disclaimerPage = new DisclaimerPage(webDriver).get();
		disclaimerPage.acceptDisclaimer();
	}

}
