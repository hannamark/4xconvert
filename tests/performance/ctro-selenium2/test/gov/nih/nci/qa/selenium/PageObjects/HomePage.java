package gov.nih.nci.qa.selenium.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class HomePage extends LoadableComponent<HomePage>{
	
	@FindBy(how = How.LINK_TEXT, using = "Log In")
	@CacheLookup
	WebElement loginLink;

	private final WebDriver webDriver;
	
	public HomePage(WebDriver webDriver){
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}
	
	public LoginPage clickLogInLink(){
		loginLink.click();
		return new LoginPage(webDriver);
	}
	
	@Override
	protected void load() {
		// TODO Read from a properties file.
		webDriver.get("http://192.168.184.190:39480/viewer");
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Welcome to NCI's CTRP Viewer".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Home Page");
		}
	}

}
