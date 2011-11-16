package gov.nih.nci.qa.selenium.Parameterized;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.javasimon.Split;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Parameterized.class)
public class StatesTest {

	public StatesTest(String currentOption) {
		this.currentOption = currentOption;
	}

	@Rule
	public TestName testName = new TestName();

	@Parameters
	public static Collection<String[]> getOptions() {
		// Start the browser.
		WebDriver webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Navigate to the adhoc report page and get the options.
		AdHocReportPage adHocReportPage = new AdHocReportPage(webDriver).get();
		List<String> states = adHocReportPage.getStates();

		// Turn the options we collected into test parameters.
		List<String[]> options = new LinkedList<String[]>();
		for (String state : states) {
			options.add(new String[] { state });
		}

		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}

		return options;
	}

	@Before
	public void setUp() {
		// Start the browser.
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		adHocReportPage = new AdHocReportPage(webDriver).get();
	}

	@After
	public void tearDown() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@AfterClass
	public static void printReport() {
		// Output to a report.
		StopwatchUtil.printCompleteReport();
	}

	private String currentOption;
	private AdHocReportPage adHocReportPage;
	private WebDriver webDriver;

	@Test
	public void runReportUsingStates() {
		// The @Before method navigates us to the page.
		List<String> states = new ArrayList<String>();
		states.add(currentOption);
		adHocReportPage.setTrialGeographicArea("", states, "");

		// Start the timer for the currently executing test.
		Split split = SplitUtil.getParameterizedTestSplit(
				testName.getMethodName(), currentOption);
		adHocReportPage.clickRunReportButton();
		split.stop();
	}
}
