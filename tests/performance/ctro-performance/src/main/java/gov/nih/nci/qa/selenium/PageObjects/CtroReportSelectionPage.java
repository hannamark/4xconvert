package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.util.SplitUtil;

import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class CtroReportSelectionPage extends
		LoadableComponent<CtroReportSelectionPage> {

	@FindBy(how = How.XPATH, using = "//ul[@id='mainmenu']/li[2]/a/span")
	private WebElement mainMenuLink;

	@FindBy(how = How.XPATH, using = "//ul[@id='reportmenu']/li[4]/a/span")
	private WebElement adHocLink;

	@FindBy(how = How.XPATH, using = "//ul[@id='reportmenu']/li[3]/a/span")
	private WebElement summary4TypeLink;

	@FindBy(how = How.XPATH, using = "//ul[@id='reportmenu']/li[2]/a/span")
	private WebElement trialsSubmittedByInstitutionLink;

	@FindBy(how = How.XPATH, using = "//ul[@id='reportmenu']/li/a/span")
	private WebElement summaryOfSubmissionLink;

	@FindBy(how = How.LINK_TEXT, using = "Log Out")
	private WebElement logOutLink;

	private final WebDriver webDriver;

	public CtroReportSelectionPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public AdHocReportPage clickAdHocLink() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"CtroReportSelectionPage", "clickAdHocLink");
		Actions actions = new Actions(webDriver);
		actions.click(mainMenuLink).click(adHocLink);
		actions.perform();
		split.stop();
		return new AdHocReportPage(webDriver);
	}

	public Summary4TypeReportPage clickSummary4TypeLink() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"CtroReportSelectionPage", "clickSummary4TypeLink");
		Actions actions = new Actions(webDriver);
		actions.click(mainMenuLink).click(summary4TypeLink);
		actions.perform();
		split.stop();
		return new Summary4TypeReportPage(webDriver);
	}

	public TrialsSubmittedByInstitutionReportPage clickTrialsSubmittedByInstitutionLink() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"CtroReportSelectionPage",
				"clickTrialsSubmittedByInstitutionLink");
		Actions actions = new Actions(webDriver);
		actions.click(mainMenuLink).click(trialsSubmittedByInstitutionLink);
		actions.perform();
		split.stop();
		return new TrialsSubmittedByInstitutionReportPage(webDriver);
	}

	public SummaryOfSubmissionReportPage clickSummaryOfSubmissionLink() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"CtroReportSelectionPage", "clickSummaryOfSubmissionLink");
		Actions actions = new Actions(webDriver);
		actions.click(mainMenuLink).click(summaryOfSubmissionLink);
		actions.perform();
		split.stop();
		return new SummaryOfSubmissionReportPage(webDriver);
	}

	public HomePage clickLogOutLink() {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"CtroReportSelectionPage", "clickLogOutLink");
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
		Split split = SplitUtil.getNavigationSplit("CtroReportSelectionPage");
		DisclaimerPage disclaimerPage = new DisclaimerPage(webDriver).get();
		disclaimerPage.acceptDisclaimer();
		split.stop();
	}

}
