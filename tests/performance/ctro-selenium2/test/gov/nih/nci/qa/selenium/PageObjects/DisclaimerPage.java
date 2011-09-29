package gov.nih.nci.qa.selenium.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class DisclaimerPage extends LoadableComponent<DisclaimerPage> {

	@FindBy(how = How.ID, using = "acceptDisclaimer")
	@CacheLookup
	private WebElement acceptDisclaimerButton;

	@FindBy(how = How.ID, using = "rejectDisclaimer")
	@CacheLookup
	private WebElement rejectDisclaimerButton;

	private final WebDriver webDriver;

	public DisclaimerPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public HomePage rejectDisclaimer() {
		rejectDisclaimerButton.click();
		return new HomePage(webDriver);
	}

	public CtroReportSelectionPage acceptDisclaimer() {
		acceptDisclaimerButton.click();
		return new CtroReportSelectionPage(webDriver);
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
		LoginPage loginPage = new LoginPage(webDriver).get();
		loginPage.loginAs("abstractor", "Coppa#12345", "Training");
	}

}
