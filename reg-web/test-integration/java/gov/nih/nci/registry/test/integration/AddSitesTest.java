package gov.nih.nci.registry.test.integration;

import java.sql.SQLException;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;

import org.junit.Test;

/**
 * @author Denis G. Krylov
 */
public class AddSitesTest extends AbstractRegistrySeleniumTest {

    @Test
    public void testEmptyCriteriaError() throws SQLException {
        goToAddSitesScreen();
        clickAndWait("id=runSearchBtn");
        assertTrue(selenium
                .isTextPresent("Please provide a search criteria; "
                        + "otherwise the number of trials returned may be unmanageable"));
    }

    @Test
    public void testTooManyResults() throws SQLException {
        goToAddSitesScreen();
        for (int i = 0; i < 101; i++) {
            createAcceptedTrial(true);
        }
        selenium.type("id=officialTitle", "-");
        clickAndWait("id=runSearchBtn");
        assertTrue(selenium
                .isTextPresent("Error Message: Search criteria you provided has produced"));
        assertTrue(selenium
                .isTextPresent("(!) matching trials, which is too many to manage at once on this page."
                        + " The limit is 100. If you could, please revise the criteria to produce a smaller number of trials"
                        + " that will be more manageable"));
    }

    /**
     * 
     */
    private void goToAddSitesScreen() {
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        clickAndWait("link=Add Sites");
    }

    @Test
    public void testResetButton() throws SQLException {
        goToAddSitesScreen();
        selenium.type("id=identifier", "NCI-2014-00001");
        selenium.type("id=officialTitle", "Trial Title");
        clickAndWait("id=resetBtn");
        assertEquals("", selenium.getValue("id=identifier"));
        assertEquals("", selenium.getValue("id=officialTitle"));
    }

}
