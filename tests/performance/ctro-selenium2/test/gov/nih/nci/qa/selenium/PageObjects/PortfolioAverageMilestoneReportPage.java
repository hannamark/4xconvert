package gov.nih.nci.qa.selenium.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class PortfolioAverageMilestoneReportPage extends
LoadableComponent<PortfolioAverageMilestoneReportPage>{
	
	private final WebDriver webDriver;
	
	public PortfolioAverageMilestoneReportPage(WebDriver webDriver){
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		if (!"Portfolio Average Milestone Report".equals(webDriver.getTitle())) {
			// TODO Do something nicer than just accepting the default title as
			// proof that we're at the right place.
			throw new Error("This is not the Portfolio Average Milestone report page.");
		}
	}

	@Override
	protected void load() {
		CtroReportSelectionPage ctroReportSelectionPage = new CtroReportSelectionPage(webDriver).get();
		ctroReportSelectionPage.clickPortfolioAverageMilestoneLink();
	}

}
