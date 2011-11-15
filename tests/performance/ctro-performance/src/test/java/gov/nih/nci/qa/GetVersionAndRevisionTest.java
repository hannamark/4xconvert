package gov.nih.nci.qa;

import gov.nih.nci.qa.selenium.PageObjects.HomePage;
import gov.nih.nci.qa.selenium.util.SplitUtil;
import gov.nih.nci.qa.selenium.util.StopwatchUtil;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javasimon.Split;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GetVersionAndRevisionTest {

	private WebDriver webDriver;

	@Before
	public void setUp() {
		// Start the browser.
		webDriver = new FirefoxDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		// Close the browser.
		if (webDriver != null) {
			webDriver.quit();
		}

		// Output to a report.
		StopwatchUtil.printCompleteReport();
	}

	@Test
	public void getViewerRevision() {
		HomePage homePage = new HomePage(webDriver).get();
		String pageSource = webDriver.getPageSource();

		Pattern pattern = Pattern
				.compile("\\<![ \\r\\n\\t]*(--([^\\-]|[\\r\\n]|-[^\\-])*--[ \\r\\n\\t]*)\\>");
		Matcher matcher = pattern.matcher(pageSource);
		String revision = null;
		while (matcher.find()) {
			String s = matcher.group();
			if (s.contains("Version: ") && s.contains("revision")) {
				String[] split = s.split(",");
				revision = split[1];
			}
		}
		int start = revision.indexOf("revision:");
		int end = revision.indexOf("-->");
		//
		String substring = revision.substring(start, end);

		// TODO This is just a hacky way to get the build revision into the
		// report.
		Split split = SplitUtil.getRevisionSplit(substring);
		split.stop();
	}

}
