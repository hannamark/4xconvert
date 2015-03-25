/**
 * 
 */
package gov.nih.nci.registry.test.performance;

import gov.nih.nci.registry.test.integration.AbstractRegistrySeleniumTest;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author dkrylov
 * 
 */
public class AmendPerformanceTest extends AbstractRegistrySeleniumTest {

    /**
     * Tests logging in as abstractor.
     * 
     * @throws Exception
     *             on error
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testAmendPerformanceOnTrialsWithManySites() throws Exception {
        amendAndCheckPerformance("NCI-2009-00702");
        amendAndCheckPerformance("NCI-2011-02050");

    }

    /**
     * @param nciID
     * @throws URISyntaxException
     */
    @SuppressWarnings("deprecation")
    private void amendAndCheckPerformance(final String nciID)
            throws URISyntaxException {
        logoutUser();
        loginAsSubmitter();
        handleDisclaimer(true);

        searchForTrialByNciID(nciID);
        selectAction("Amend");

        populateAmendmentNumber();
        populateDocuments();
        clickAndWait("xpath=//button[text()='Review Trial']");

        try {
            waitForElementToBecomeVisible(By.id("dialog-opensites"), 15);
            s.click("//div[@aria-labelledby='ui-dialog-title-dialog-opensites']//span[text()='Proceed']");
        } catch (Exception e) {
            System.out.println("dialog-opensites did not show up.");
        }

        waitForElementById("reviewTrialForm", 30);

        final Date start = new Date();
        System.out.println("Timestamp prior to submitting an amendment of "
                + nciID + ": " + start);
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        selenium.waitForPageToLoad(toMillisecondsString(60 * 10));
        assertTrue(selenium
                .isTextPresent("The amendment to trial with the NCI Identifier "
                        + nciID + " was successfully submitted."));
        final Date end = new Date();
        System.out.println("Timestamp after submitting an amendment of "
                + nciID + ": " + end);

        long diff = end.getTime() - start.getTime();
        final String durationString = "Duration: " + (diff / 1000L)
                + " seconds or " + (diff / 1000D / 60D) + " minutes.";
        System.out.println(durationString);

        assertTrue(
                "Amendment operation for "
                        + nciID
                        + " did not complete within 5 minutes, which is the timeout hard-coded in JBoss. "
                        + durationString, diff <= 1000 * 60 * 5L);
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    protected void populateAmendmentNumber() {
        s.type("trialDTO.localAmendmentNumber", "1");
        s.type("trialDTO.amendmentDate", today);
    }

    /**
     * @throws URISyntaxException
     */
    @SuppressWarnings("deprecation")
    protected void populateDocuments() throws URISyntaxException {
        // Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);
        selenium.type("protocolHighlightDocument", protocolDocPath);
    }
}
