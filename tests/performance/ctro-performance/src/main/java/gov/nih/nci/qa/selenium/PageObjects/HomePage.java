package gov.nih.nci.qa.selenium.PageObjects;

import gov.nih.nci.qa.selenium.util.SplitUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class HomePage extends LoadableComponent<HomePage> {

	@FindBy(how = How.LINK_TEXT, using = "Log In")
	WebElement loginLink;

	private final WebDriver webDriver;

	public HomePage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public LoginPage clickLogInLink() {
		Split split = SplitUtil.getPageElementSplit("HomePage",
				"clickLogInLink");
		loginLink.click();
		split.stop();
		return new LoginPage(webDriver);
	}

	@Override
	protected void load() {
		Split split = SplitUtil.getTestSplit(SplitUtil.PAGE_LOAD_CATEGORY,
				"HomePage");
		// TODO this probably belongs somewhere nicer.
		String filename = "config.properties";
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(filename));

		} catch (IOException e) {
			System.out.println("Couldn't read the file [" + filename + "].");
			e.printStackTrace();
		}

		String protocol = properties.getProperty("protocol");
		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String url = protocol + "://" + host + ":" + port + "/viewer/";
		// TODO sanity check the url.
		webDriver.get(url);
		split.stop();
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
