package gov.nih.nci.qa.selenium.Parameterized;

import gov.nih.nci.qa.selenium.PageObjects.AdHocReportPage;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsBuilder;
import gov.nih.nci.qa.selenium.Parameters.ClinicalTrialRegistrationDetailsParam;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

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
public class ProcessingStatusesTest {

	public ProcessingStatusesTest(String currentOption) {
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
		List<String> primaryPurposes = adHocReportPage.getProcessingStatuses();

		// Turn the options we collected into test parameters.
		List<String[]> options = new LinkedList<String[]>();
		for (String primaryPurpose : primaryPurposes) {
			options.add(new String[] { primaryPurpose });
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
	public void runReportUsingProcessingStatuses() {
		// The @Before method navigates us to the page.
		ClinicalTrialRegistrationDetailsBuilder builder = new ClinicalTrialRegistrationDetailsBuilder();
		builder.setProcessingStatus(currentOption);
		ClinicalTrialRegistrationDetailsParam parameters = builder
				.getParameters();
		adHocReportPage.setClinicalTrialsRegistrationDetails(parameters);

		// Start the timer for the currently executing test.
		Split split = SplitUtil.getParameterizedTestSplit(
				testName.getMethodName(), currentOption);
		adHocReportPage.clickRunReportButton();
		split.stop();
	}
}
