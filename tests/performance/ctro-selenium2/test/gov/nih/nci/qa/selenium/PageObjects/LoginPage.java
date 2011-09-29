package gov.nih.nci.qa.selenium.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends LoadableComponent<LoginPage>{

	@FindBy(how = How.NAME, using = "j_username")
	@CacheLookup
	private WebElement loginField;

	@FindBy(how = How.NAME, using = "j_password")
	@CacheLookup
	private WebElement passwordField;

	@FindBy(how = How.ID, using = "loginButton")
	@CacheLookup
	private WebElement loginButton;
	
	@FindBy(how=How.NAME, using="authenticationServiceURL")
	@CacheLookup
	private WebElement accountSourceDropDown; 

	private final WebDriver webDriver;

	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public DisclaimerPage loginAs(String username, String password, String accountSource) {
		loginField.sendKeys(username);
		passwordField.sendKeys(password);
		setDropDown(accountSourceDropDown, accountSource);
		loginButton.click();
		return new DisclaimerPage(webDriver);
	}
	
	private void setDropDown(WebElement webElement, String keysToSend) {
		Select select = new Select(webElement);

		// Get a list of the options
		List<WebElement> options = select.getOptions();

		// For each option in the list, verify if it's the one you want and then
		// click it
		for (WebElement we : options) {
			if (we.getText().equals(keysToSend)) {
				we.click();
				break;
			}
		}
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"NCI CTRP Viewer".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Login page");
		}
	}

	@Override
	protected void load() {
		HomePage homePage = new HomePage(webDriver).get();
		homePage.clickLogInLink();
	}

}
