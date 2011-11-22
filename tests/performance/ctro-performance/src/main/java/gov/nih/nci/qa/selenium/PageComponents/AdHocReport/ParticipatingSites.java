package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.util.PageUtil;
import gov.nih.nci.qa.selenium.util.SplitUtil;

import java.util.List;

import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ParticipatingSites {

	@FindBy(how = How.ID, using = "participatingSiteIds")
	WebElement partipatingSiteDropDown;

	private final WebDriver webDriver;

	public ParticipatingSites(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setParticipatingSites(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setParticipatingSites");
		PageUtil.setDropDown(partipatingSiteDropDown, keysToSend);
		split.stop();
	}

	public List<String> getParticipatingSites() {
		return PageUtil.getOptions(partipatingSiteDropDown);
	}

}
