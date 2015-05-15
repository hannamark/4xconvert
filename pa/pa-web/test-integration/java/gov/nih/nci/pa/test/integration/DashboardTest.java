package gov.nih.nci.pa.test.integration;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

/**
 * 
 * @author dkrylov
 */
public class DashboardTest extends AbstractTrialStatusTest {

    private static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 10000
            : 2000;

    @SuppressWarnings({ "deprecation", "unused", "unchecked" })
    @Test
    public void testCsvExport() throws Exception {
        logoutUser();
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        clickAndWait("link=Dashboard");

        // Export banner must be at top and at bottom.
        assertTrue(s
                .isElementPresent("xpath=//div[@id='results']/div[@class='exportlinks'][1]"));
        assertTrue(s
                .isElementPresent("xpath=//div[@id='results']/div[@class='exportlinks'][2]/preceding-sibling::table[@id='results']"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "dashboardSearchResults.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();

            List<String> lines = FileUtils.readLines(csv);
            String content = FileUtils.readFileToString(csv);
            assertEquals(
                    "NCI Trial Identifier,Lead Organization,Lead Org PO ID,ClinicalTrials.gov Identifier,CTEP ID,DCP ID,CDR ID,Amendment #,Trial Category,Summary 4 Funding,On Hold Date,Off Hold Date,On Hold Reason,On Hold Description,Trial Type,NCI Sponsored,Processing Status,Processing Status Date,Admin Check out Name,Admin Check out Date,Scientific Check out Name,Scientific Check out Date,Submission Type,Trial Category,CTEP/DCP,Submitting Organization,Submission Date,Last Milestone,Last Milestone Date,Submission Source,Processing Priority,Comments,This Trial is,Submission Received Date,Added By,Added On,Submission Acceptance Date,Added By,Added On,Submission Rejection Date,Added By,Added On,Submission Terminated Date,Added By,Added On,Submission Reactivated Date,Added By,Added On,Administrative Processing Completed Date,Added By,Added On,Administrative QC Completed Date,Added By,Added On,Scientific Processing Completed Date,Added By,Added On,Scientific QC Completed Date,Added By,Added On,Trial Summary Report Date,Added By,Added On,Submitter Trial Summary Report Feedback Date,Added By,Added On,Initial Abstraction Verified Date,Added By,Added On,On-going Abstraction Verified Date,Added By,Added On,Late Rejection Date,Added By,Added On",
                    lines.get(0));
            assertTrue(content
                    .contains(trial.nciID.replaceFirst("NCI-", "")
                            + ",ClinicalTrials.gov,1,,,,,,Complete,,,,,,Interventional,No,Accepted,"
                            + today
                            + ",,,,,Original,Complete,,ClinicalTrials.gov,04/16/2014,Submission Acceptance Date,"
                            + today + ",Other,2,,Ready for Admin Processing,"
                            + today + ",," + today + "," + today + ",," + today
                            + ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"));

            csv.delete();
        }

    }

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
