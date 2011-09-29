package gov.nih.nci.qa.selenium.PageComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Summary4TypeReportTable {
	
	@FindBy(how=How.LINK_TEXT, using="Agent/Device")
	WebElement agentDeviceTab;
	
	@FindBy(how=How.LINK_TEXT, using="Epidemiologic/Other/Outcome")
	WebElement otherInterventionTab;
	
	@FindBy(how=How.LINK_TEXT, using="Epidemiologic/Other/Outcome")
	WebElement epidemiologicOutcomeTab;
	
	@FindBy(how=How.LINK_TEXT, using="Ancillary/Correlative")
	WebElement ancillaryCorrelativeTab;
	
	private final WebDriver webDriver;
	
	public Summary4TypeReportTable(WebDriver webDriver){
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

}
