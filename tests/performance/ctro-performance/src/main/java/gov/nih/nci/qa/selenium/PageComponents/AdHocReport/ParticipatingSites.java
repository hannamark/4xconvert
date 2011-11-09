package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.util.PageUtil;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ParticipatingSites {

	@FindBy(how = How.ID, using = "participatingSiteId")
	WebElement partipatingSiteDropDown;

	private final WebDriver webDriver;

	public ParticipatingSites(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setParticipatingSites(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setParticipatingSite")
				.start();
		PageUtil.setDropDown(partipatingSiteDropDown, keysToSend);
		split.stop();
	}

}
