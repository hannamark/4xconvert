package gov.nih.nci.pa.test.integration;

import org.junit.Test;

/**
 * 
 * @author dkrylov
 */
public class DashboardTest extends AbstractTrialStatusTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testAdminCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin) Successful"));
        assertEquals("My Dashboard - Administrative Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("admin-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testScientificCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsScientificAbstractor();
        clickAndWait("link=Dashboard");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Scientific) Successful"));
        assertEquals("My Dashboard - Scientific Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("scientific-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSuperAbstractorCheckOut() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        clickAndWait("link=Dashboard");
        s.type("submittedOnOrAfter", "01/01/1990");
        clickAndWait("xpath=//a//span[text()='Search']");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
        clickAndWait("link=Admin/Scientific Check Out");
        assertTrue(s
                .isTextPresent("Message. Trial Check-Out (Admin and Scientific) Successful"));
        assertEquals("My Dashboard - Super Abstractor", s.getTitle()
                .replaceAll("[^\\p{ASCII}]", "-"));
        clickAndWait("link=Check out History");
        assertEquals("Administrative",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]")
                        .trim());
        assertEquals("Scientific",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[1]")
                        .trim());
        assertEquals("ctrpsubstractor",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[2]/td[3]")
                        .trim());

    }
}
