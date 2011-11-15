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

public class Biomarkers {

	@FindBy(how = How.ID, using = "bioMarkers")
	WebElement biomarkerMultiSelect;

	private final WebDriver webDriver;

	public Biomarkers(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setBiomarkers(List<String> selectList) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setBiomarkers");
		PageUtil.setMultiSelect(biomarkerMultiSelect, selectList);
		split.stop();
	}

}
