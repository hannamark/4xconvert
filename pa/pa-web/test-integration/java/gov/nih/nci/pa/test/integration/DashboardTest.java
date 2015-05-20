package gov.nih.nci.pa.test.integration;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

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
        findAndSelectTrialInDashboard(trial);
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

    @SuppressWarnings("deprecation")
    @Test
    public void testSiteInterventionAndDiseaseCombinedSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Adding site along is not sufficient to find.
        findAndSelectTrialInDashboard(trial);
        addAnatomicSite("Kidney");
        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Adding site and intervention still not enough.
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        runCombinedSearch();
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Only when all 3 criterions present, trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        runCombinedSearch();
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

    }

    /**
     * 
     */
    private void runCombinedSearch() {
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        clickAndWait("link=Search");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDiseaseSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // trichothiodystrophy not found.
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add trichothiodystrophy to disease list, and now trial found.
        findAndSelectTrialInDashboard(trial);
        addDisease("trichothiodystrophy");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Trial found using both diseases, too
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // verify disease option removal.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addDisease("xerostomia");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        useSelect2ToUnselectOption("xerostomia");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("diseases", "trichothiodystrophy",
                "trichothiodystrophy");
        useSelect2ToPickAnOption("diseases", "xerostomia", "xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("trichothiodystrophy");
        assertOptionSelected("xerostomia");
        clickAndWait("link=Reset");
        assertOptionNotSelected("trichothiodystrophy");
        assertOptionNotSelected("xerostomia");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));
    }

    @SuppressWarnings("deprecation")
    private void addDisease(String disease) {
        clickAndWait("link=Disease/Condition");
        clickAndWait("link=Add");
        driver.switchTo().frame("popupFrame");
        s.type("disease", disease);
        clickAndWait("css=.search_inner_button");
        clickAndWait("//span[@class='breadcrumbHighlight' and text()='"
                + disease + "']");
        clickAndWait("//span[@class='add' and text()='Add']");
        driver.switchTo().defaultContent();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInterventionSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // tarenflurbil not found.
        findAndSelectTrialInDashboard(trial);
        addInterevention("pyroxamide");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // now found.
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // both found.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        addInterevention("pyroxamide");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("interventions", "pyroxamide", "pyroxamide");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // add both, remove one, not found.
        trial = createAcceptedTrial();
        findAndSelectTrialInDashboard(trial);
        addInterevention("tarenflurbil");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        useSelect2ToPickAnOption("interventions", "pyroxamide", "pyroxamide");
        useSelect2ToUnselectOption("tarenflurbil");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("interventions", "tarenflurbil",
                "tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("tarenflurbil");
        clickAndWait("link=Reset");
        assertOptionNotSelected("tarenflurbil");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

    }

    @SuppressWarnings("deprecation")
    private void addInterevention(String in) {
        clickAndWait("link=Interventions");
        clickAndWait("link=Add");
        clickAndWait("link=Look Up");
        driver.switchTo().frame("popupFrame");
        s.type("searchName", in);
        clickAndWait("link=Search");
        clickAndWait("//td[@class='action']//span[text()='Select']");
        driver.switchTo().defaultContent();
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Created."));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAnatomicSiteSearch() throws Exception {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();

        // Kidney not found.
        findAndSelectTrialInDashboard(trial);
        addAnatomicSite("Colon");
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Colon now found.
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Colon & Kidney now found.
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Add Colon & Kidney, then remove Colon. Not found.
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("anatomicSites", "kidney", "Kidney");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        useSelect2ToUnselectOption("Colon");
        clickAndWait("link=Search");
        assertFalse(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));

        // Verify selected values persist in search criteria and resets
        // properly.
        clickAndWait("link=Dashboard");
        useSelect2ToPickAnOption("anatomicSites", "colo", "Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        clickAndWait("//input[@type='button' and @value='Refresh']");
        assertTrue(s.isTextPresent(trial.nciID.replaceFirst("NCI-", "")));
        s.click("searchid");
        assertOptionSelected("Colon");
        clickAndWait("link=Reset");
        assertOptionNotSelected("Colon");
        clickAndWait("link=Search");
        assertTrue(s.isTextPresent("At least one criteria is required."));

    }

    @SuppressWarnings("deprecation")
    private void useSelect2ToUnselectOption(String option) {
        s.click("//li[@class='select2-selection__choice' and @title='" + option
                + "']/span[@class='select2-selection__choice__remove']");
        assertFalse(s
                .isElementPresent("//li[@class='select2-selection__choice' and @title='"
                        + option + "']"));

    }

    @SuppressWarnings("deprecation")
    private void useSelect2ToPickAnOption(String id, String sendKeys,
            String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-" + id + "-results"));
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        try {
            xpath = By.xpath("//li[@role='treeitem' and text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 3);
        } catch (TimeoutException e) {
            xpath = By.xpath("//li[@role='treeitem']/b[text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 10);
        }

        driver.findElement(xpath).click();
        assertOptionSelected(option);
    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    private void assertOptionSelected(String option) {
        assertTrue(s
                .isElementPresent("//li[@class='select2-selection__choice' and @title='"
                        + option + "']"));
    }

    @SuppressWarnings("deprecation")
    private void assertOptionNotSelected(String option) {
        assertFalse(s
                .isElementPresent("//li[@class='select2-selection__choice' and @title='"
                        + option + "']"));
    }

    /**
     * @param site
     */
    private void addAnatomicSite(String site) {
        clickAndWait("link=Summary 4 Anatomic Site");
        clickAndWait("link=Add");
        s.select("anatomicSite_code", site);
        clickAndWait("link=Save");
        assertTrue(s.isTextPresent("Message. Record Created."));
    }

    /**
     * @param trial
     */
    private void findAndSelectTrialInDashboard(TrialInfo trial) {
        clickAndWait("link=Dashboard");
        s.type("submittedOnOrAfter", "01/01/1990");
        clickAndWait("xpath=//a//span[text()='Search']");
        clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
    }
}
