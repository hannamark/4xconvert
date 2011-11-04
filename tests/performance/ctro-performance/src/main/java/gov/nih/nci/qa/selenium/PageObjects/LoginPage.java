package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.util.PageUtil;

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

public class LoginPage extends LoadableComponent<LoginPage> {

	@FindBy(how = How.NAME, using = "j_username")
	private WebElement loginField;

	@FindBy(how = How.NAME, using = "j_password")
	private WebElement passwordField;

	@FindBy(how = How.ID, using = "loginButton")
	private WebElement loginButton;

	@FindBy(how = How.NAME, using = "authenticationServiceURL")
	private WebElement accountSourceDropDown;

	private final WebDriver webDriver;

	private static String LOGIN_ERROR_TEXT = "Invalid username and/or password, please try again.";

	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setLoginField(String username) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "LoginPage"
						+ Manager.HIERARCHY_DELIMITER + "setLoginField")
				.start();
		loginField.sendKeys(username);
		split.stop();
	}

	public void setPasswordField(String password) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "LoginPage"
						+ Manager.HIERARCHY_DELIMITER + "setPasswordField")
				.start();
		passwordField.sendKeys(password);
		split.stop();
	}

	public void setAccountSource(String accountSource) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "LoginPage"
						+ Manager.HIERARCHY_DELIMITER + "setAccountSource")
				.start();
		PageUtil.setDropDown(accountSourceDropDown, accountSource);
		split.stop();
	}

	public DisclaimerPage clickLoginButton() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "LoginPage"
						+ Manager.HIERARCHY_DELIMITER + "clickLoginButton")
				.start();
		loginButton.click();
		split.stop();
		return new DisclaimerPage(webDriver);
	}

	public boolean isLoginErrorPresent() {
		WebElement webElement;
		try {
			webElement = webDriver.findElement(By.className("directions"));
		} catch (NoSuchElementException e) {
			return false;
		}
		if (webElement.getText().contains(LOGIN_ERROR_TEXT)) {
			return true;
		}
		return false;
	}

	public DisclaimerPage loginAs(String username, String password,
			String accountSource) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "LoginPage"
						+ Manager.HIERARCHY_DELIMITER + "loginAs").start();
		setLoginField(username);
		setPasswordField(password);
		PageUtil.setDropDown(accountSourceDropDown, accountSource);
		clickLoginButton();
		split.stop();
		return new DisclaimerPage(webDriver);
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
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "LoginPage"
						+ Manager.HIERARCHY_DELIMITER + "load").start();
		HomePage homePage = new HomePage(webDriver).get();
		homePage.clickLogInLink();
		split.stop();
	}

}
