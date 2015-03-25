/**
 * 
 */
package gov.nih.nci.registry.test.performance;

import gov.nih.nci.registry.test.integration.AbstractRegistrySeleniumTest;

import org.junit.Test;

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
    @Test
    public void testAmendPerformanceOnTrialsWithManySites() throws Exception {
        loginAsSubmitter();
        handleDisclaimer(true);
        searchForTrialByNciID("NCI-2011-02050");
        selectAction("Amend");
    }

}
