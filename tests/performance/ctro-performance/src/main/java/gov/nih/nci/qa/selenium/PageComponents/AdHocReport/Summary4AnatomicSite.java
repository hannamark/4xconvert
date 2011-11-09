package gov.nih.nci.qa.selenium.PageComponents.AdHocReport;

import gov.nih.nci.qa.selenium.util.PageUtil;

import java.util.List;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Summary4AnatomicSite {

	@FindBy(how = How.ID, using = "summ4Sponsor")
	WebElement summary4SponsorDropDown;

	@FindBy(how = How.ID, using = "anatomicSites")
	WebElement summary4AnatomicSitesMultiSelect;

	@FindBy(how = How.ID, using = "summ4FundingSourceTypeCode")
	WebElement summary4FundingCategoryDropDown;

	private final WebDriver webDriver;

	public Summary4AnatomicSite(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setSummary4AnatomicSite(String sponsor, List<String> sites,
			String fundingCategory) {
		if (sites != null) {
			setSummary4AnatomicSites(sites);
		}
		if (fundingCategory != null) {
			setSummary4FundingCategory(fundingCategory);
		}
		if (sponsor != null) {
			setSummary4Sponsor(sponsor);
		}
	}

	private void setSummary4Sponsor(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER + "setSummary4Sponsor")
				.start();
		PageUtil.setDropDown(summary4SponsorDropDown, keysToSend);
		split.stop();
	}

	private void setSummary4AnatomicSites(List<String> selectList) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setSummary4AnatomicSites").start();
		PageUtil.setMultiSelect(summary4AnatomicSitesMultiSelect, selectList);
		split.stop();
	}

	private void setSummary4FundingCategory(String keysToSend) {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "AdHocReportPage"
						+ Manager.HIERARCHY_DELIMITER
						+ "setSummary4FundingCategory").start();
		PageUtil.setDropDown(summary4FundingCategoryDropDown, keysToSend);
		split.stop();
	}

}
