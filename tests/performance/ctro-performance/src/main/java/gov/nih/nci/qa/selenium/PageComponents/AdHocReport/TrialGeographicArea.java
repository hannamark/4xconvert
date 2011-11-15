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

public class TrialGeographicArea {

	@FindBy(how = How.ID, using = "country")
	WebElement countryDropDown;

	@FindBy(how = How.ID, using = "states")
	WebElement statesMultiSelect;

	@FindBy(how = How.ID, using = "city")
	WebElement cityTextBox;

	private final WebDriver webDriver;

	public TrialGeographicArea(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setTrialGeographicArea(String country, List<String> states,
			String city) {
		setCountry(country);
		setState(states);
		setCity(city);
	}

	private void setCountry(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setCountry");
		PageUtil.setDropDown(countryDropDown, keysToSend);
		split.stop();
	}

	private void setState(List<String> selectList) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setState");
		PageUtil.setMultiSelect(statesMultiSelect, selectList);
		split.stop();
	}

	private void setCity(String keysToSend) {
		Split split = SplitUtil.getPageElementSplit(webDriver,
				"AdHocReportPage", "setCity");
		cityTextBox.sendKeys(keysToSend);
		split.stop();
	}

}
