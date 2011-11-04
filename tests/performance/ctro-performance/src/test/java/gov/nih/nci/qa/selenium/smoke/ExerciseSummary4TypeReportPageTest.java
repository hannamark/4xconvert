package gov.nih.nci.qa.selenium.smoke;

import gov.nih.nci.qa.selenium.PageObjects.Summary4TypeReportPage;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExerciseSummary4TypeReportPageTest {

	private WebDriver webDriver;

	@Before
	public void setUp() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "startup").start();
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		split.stop();
	}

	@After
	public void tearDown() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "shutdown").start();
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
		split.stop();
		StopwatchUtil.printReport("parent");
	}

	@Test
	public void exercisePageObjects() {
		Summary4TypeReportPage summary4TypeReportPage = new Summary4TypeReportPage(
				webDriver).get();

		summary4TypeReportPage.setDateRange("1/1/2009", "9/7/2011");

		summary4TypeReportPage.clickFindByFamilyRadio();
		// Just grab something from the list to exercise the page object.
		List<String> availableOptions = summary4TypeReportPage.getFamilyList();
		Iterator<String> it = availableOptions.iterator();
		String currentOption = null;
		while (it.hasNext()) {
			currentOption = it.next();
			if (!currentOption.contains("Select")) {
				break;
			}
		}
		summary4TypeReportPage.setFindByFamily(currentOption);

		summary4TypeReportPage.clickFindByOrgNameRadio();
		summary4TypeReportPage.setFindByOrgName("Lorem Ipsum");
		summary4TypeReportPage.clickRunReportButton();

		summary4TypeReportPage.clickResetButton();

	}

}
