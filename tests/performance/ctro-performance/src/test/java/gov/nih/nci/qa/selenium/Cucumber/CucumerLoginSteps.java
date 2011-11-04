package gov.nih.nci.qa.selenium.Cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.qa.selenium.PageObjects.DisclaimerPage;
import gov.nih.nci.qa.selenium.PageObjects.HomePage;
import gov.nih.nci.qa.selenium.PageObjects.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class CucumerLoginSteps {
	private WebDriver webDriver;

	private HomePage homePage;
	private LoginPage loginPage;
	private DisclaimerPage disclaimerPage;

	@Before
	public void prepare() {
		webDriver = new FirefoxDriver();
		homePage = new HomePage(webDriver).get();
		loginPage = homePage.clickLogInLink();
	}

	@After
	public void cleanUp() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@Given("^I enter a Login Id of (.*)$")
	public void setLoginField(String username) {
		loginPage.setLoginField(username);
	}

	@Given("^I enter a blank Login Id")
	public void setLoginBlankField() {
		loginPage.setLoginField("");
	}

	@And("^I enter a blank password")
	public void setBlankPassword() {
		loginPage.setPasswordField("");
	}

	@And("^I enter a password of (.*)$")
	public void setPassword(String password) {
		loginPage.setPasswordField(password);
	}

	@And("^I enter the Account Source of (.*)$")
	public void setAccountSource(String accountSource) {
		loginPage.setAccountSource(accountSource);
	}

	@When("^I click the login button")
	public void clickLoginButton() {
		try {
			disclaimerPage = loginPage.clickLoginButton();
		} catch (Error e) {
			// Swallow this exception... leave it for the assertions.
		}
	}

	@Then("^I should be logged in")
	public void assertLoginSuccess() {
		assertFalse("Login Error should not be displayed!",
				loginPage.isLoginErrorPresent());

		assertTrue("Log Out link should be displayed!",
				webDriver.findElement(By.linkText("Log Out")).isDisplayed());
	}

	@Then("^I should not be logged in")
	public void assertLoginFailure() {
		assertTrue("Login Error should be displayed!",
				loginPage.isLoginErrorPresent());

		WebElement webElement = webDriver.findElement(By.id("loginButton"));
		assertTrue("Log In button should be displayed!",
				webElement.isDisplayed());
	}
}
