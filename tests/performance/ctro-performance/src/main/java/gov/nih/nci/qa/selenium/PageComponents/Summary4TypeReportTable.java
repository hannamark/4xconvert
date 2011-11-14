package gov.nih.nci.qa.selenium.PageComponents;

import gov.nih.nci.qa.selenium.util.TableUtil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Summary4TypeReportTable {

	@FindBy(how = How.LINK_TEXT, using = "Agent/Device")
	WebElement agentDeviceTab;

	@FindBy(how = How.ID, using = "agentDeviceEPRRow")
	WebElement agentDeviceEPRTable;

	@FindBy(how = How.ID, using = "agentDeviceInstRow")
	WebElement agentDeviceInstitutionalTable;

	@FindBy(how = How.LINK_TEXT, using = "Epidemiologic/Other/Outcome")
	WebElement otherInterventionTab;

	@FindBy(how = How.LINK_TEXT, using = "Epidemiologic/Other/Outcome")
	WebElement epidemiologicOutcomeTab;

	@FindBy(how = How.LINK_TEXT, using = "Ancillary/Correlative")
	WebElement ancillaryCorrelativeTab;

	private final WebDriver webDriver;
	TableUtil agentDeviceEPRResultsTableHelper;
	TableUtil agentDeviceInstitutionalTableHelper;

	public Summary4TypeReportTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
		agentDeviceEPRResultsTableHelper = new TableUtil(agentDeviceEPRTable);
		agentDeviceInstitutionalTableHelper = new TableUtil(
				agentDeviceInstitutionalTable);
	}

	public int getAgentDeviceExternallyPeerReviewedResultCount() {
		agentDeviceTab.click();
		return agentDeviceEPRResultsTableHelper.getRowCount();
	}

	public int getAgentDeviceInstitutionalResultCount() {
		agentDeviceTab.click();
		return agentDeviceInstitutionalTableHelper.getRowCount();
	}

}
