/**
 * 
 */
package gov.nih.nci.registry.test.performance;

import gov.nih.nci.registry.test.integration.AbstractRegistrySeleniumTest;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author dkrylov
 * 
 */
public class TrialSearchPerformanceTest extends AbstractRegistrySeleniumTest {

    private final File report = new File("trial_search_performance_report.txt");
    {
        try {
            report.createNewFile();
            report(new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByParticipatingSite() throws Exception {
        loginAsSubmitter();
        handleDisclaimer(true);

        testBySiteSearchPerformance("M D Anderson Cancer Center (MDA)", 90);
        testBySiteSearchPerformance("Duke Cancer Institute", 20);
        testBySiteSearchPerformance("Wake Forest University Health Sciences",
                30);
        testBySiteSearchPerformance("OHSU Knight Cancer Institute", 25);
        testBySiteSearchPerformance("Mayo Clinic Cancer Center", 25);
        testBySiteSearchPerformance("Mayo Clinic", 60);
        testBySiteSearchPerformance("Mayo Clinic in Arizona", 45);

    }

    private void testBySiteSearchPerformance(String orgName, int timeoutSecond) {
        long totalTime = 0;
        int tries = 3;

        // Initial search can take longer since Postgres needs to cache stuff
        // etc, so ignore it from consideration.
        runBySiteSearchWithTimeout(orgName, 300);

        for (int i = 0; i < tries; i++) {
            totalTime += runBySiteSearchWithTimeout(orgName, timeoutSecond);
        }

        report(("Average time to search by site " + orgName + " is "
                + (totalTime / tries) + " seconds.").toUpperCase());
    }

    @SuppressWarnings("deprecation")
    private long runBySiteSearchWithTimeout(String orgName, int timeoutSeconds) {
        accessTrialSearchScreen();
        s.select("organizationType", "Participating Site");
        waitForElementToBecomeVisible(By.id("participatingSiteName"), 5);
        driver.findElement(By.id("participatingSiteName")).sendKeys(
                orgName.replaceFirst(" \\(\\w+\\)$", ""));
        waitForElementToBecomeAvailable(
                By.xpath("//ul[@role='listbox']/li/a[text()='" + orgName + "']"),
                60);
        s.click("//ul[@role='listbox']/li/a[text()='" + orgName + "']");
        pause(500);
        selenium.click("runSearchBtn");

        final Date start = new Date();
        report("Timestamp prior to searching by site " + orgName + ": " + start);

        driver.findElement(By.linkText("All Trials")).click();
        WebDriverWait waiting = new WebDriverWait(driver, timeoutSeconds);
        waiting.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//h2[text()='Clinical Trials Search Results']")));

        final Date end = new Date();
        report("Timestamp after searching by site " + orgName + ": " + end);

        long diff = end.getTime() - start.getTime();
        final long diffSeconds = diff / 1000L;
        final String durationString = "Duration: " + diffSeconds
                + " seconds or " + (diff / 1000D / 60D) + " minutes.";
        report(durationString);

        assertTrue(s.isTextPresent("Showing 1 to"));
        assertTrue(s.isElementPresent("row"));
        assertTrue("Wait time exceeded the given timeout of " + timeoutSeconds,
                diffSeconds <= timeoutSeconds);

        return diffSeconds;
    }

    private void report(String str) {
        System.out.println(str);
        try {
            FileUtils.writeStringToFile(report,
                    FileUtils.readFileToString(report)
                            + SystemUtils.LINE_SEPARATOR + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
