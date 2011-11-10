package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.util.SplitUtil;

import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class DisclaimerPage extends LoadableComponent<DisclaimerPage> {

	@FindBy(how = How.ID, using = "acceptDisclaimer")
	private WebElement acceptDisclaimerButton;

	@FindBy(how = How.ID, using = "rejectDisclaimer")
	private WebElement rejectDisclaimerButton;

	private static String DISCLAIMER_TEXT = "NCI CLINICAL TRIALS REPORTING PROGRAM (CTRP) SYSTEM";

	private final WebDriver webDriver;

	public DisclaimerPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public HomePage rejectDisclaimer() {
		Split split = SplitUtil.getPageElementSplit("DisclaimerPage",
				"rejectDisclaimer");
		rejectDisclaimerButton.click();
		split.stop();
		return new HomePage(webDriver);
	}

	public CtroReportSelectionPage acceptDisclaimer() {
		Split split = SplitUtil.getPageElementSplit("DisclaimerPage",
				"acceptDisclaimer");
		acceptDisclaimerButton.click();
		split.stop();
		return new CtroReportSelectionPage(webDriver);
	}

	public boolean isDisclaimerPresent() {
		if (webDriver.getPageSource().contains(DISCLAIMER_TEXT)) {
			return true;
		}
		return false;
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Disclaimer".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Disclaimer page");
		}
	}

	@Override
	protected void load() {
		Split split = SplitUtil.getPageElementSplit("DisclaimerPage", "load");
		LoginPage loginPage = new LoginPage(webDriver).get();
		loginPage.loginAs("abstractor", "Coppa#12345", "Training");
		split.stop();
	}

}
