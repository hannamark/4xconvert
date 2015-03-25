/**
 * 
 */
package gov.nih.nci.registry.test.performance;

import java.io.File;
import java.net.URISyntaxException;

import gov.nih.nci.registry.test.integration.AbstractRegistrySeleniumTest;

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
        loginAsSubmitter();
        handleDisclaimer(true);
        final String nciID = "NCI-2011-02050";
        searchForTrialByNciID(nciID);
        selectAction("Amend");

        populateAmendmentNumber();
        populateDocuments();
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("reviewTrialForm", 10);

        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The amendment to trial with the NCI Identifier "
                        + nciID + " was successfully submitted."));
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    protected void populateAmendmentNumber() {
        s.type("trialDTO.localAmendmentNumber", "1");
        s.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='trialDTO.amendmentDate']]");
        clickOnFirstVisible(By.xpath("//td[@class='day active']"));
        clickOnFirstVisible(By
                .xpath("//div[@class='datepicker']/button[@class='close']"));
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
