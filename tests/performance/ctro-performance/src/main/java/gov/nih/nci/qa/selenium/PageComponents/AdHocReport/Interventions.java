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

public class Interventions {

	@FindBy(how = How.ID, using = "interventionType")
	WebElement interventionTypeDropDown;

	private final WebDriver webDriver;

	public Interventions(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setInterventionType(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setInterventionType")
				.start();
		PageUtil.setDropDown(interventionTypeDropDown, keysToSend);
		split.stop();
	}

}
