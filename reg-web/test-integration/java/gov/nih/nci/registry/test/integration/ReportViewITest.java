package gov.nih.nci.registry.test.integration;

import gov.nih.nci.registry.service.MockRestClientNCITServer;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ReportViewITest extends AbstractRegistrySeleniumTest {

    MockRestClientNCITServer mockRestClientNCITServer = new MockRestClientNCITServer();

    public static int REST_SERVER_PORT;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        REST_SERVER_PORT = randomPort();
        mockRestClientNCITServer.startServer(REST_SERVER_PORT);
        setupProperties();
    }

    private void setupProperties() throws SQLException {

        String url = "http://localhost:" + REST_SERVER_PORT
                + "/reports/rest/user";
        setPaProperty("jasper.base.user.rest.url", url);
        setPaProperty("jasper.admin.username", "jasperadmin");
        setPaProperty("jasper.admin.password", "jasperadmin");
        setPaProperty("reg.web.admin.showReportsMenu", "true");
        setPaProperty("regweb.jasper.allow.allssl", "true");
        setPaProperty("regweb.reportview.availableReports",
                "Data Table 4:ROLE_DT4_CC_PSUSER|organization_1");
        setPaProperty("jasper.rest.http.timeout.millis", "10000");

    }

    @Test
    public void testSetDT4Report() throws Exception {

        loginAndAcceptDisclaimer();

        Number csmUserId = createCSMUser();
        assertNotNull(csmUserId);

        Number registryUserId = createRegistryUser(csmUserId);
        assertNotNull(registryUserId);

        navigateToReportViewerPage();

        WebElement selectElement = driver.findElement(By
                .id("drop-report-select-" + registryUserId.intValue()));
        if (selectElement != null) {

            Select reportSelect = new Select(selectElement);
            reportSelect.selectByIndex(0);

            driver.findElement(By.id("savereportuser")).click();
            assert s.isTextPresent("Successfully Updated Report Viewers.");

            checkReportViewerColumnUpdated(registryUserId);

        }

        // waitForEmailsToArrive(1);
        logoutUser();

        int count = removeCSMUser(csmUserId);
        assert (count > 0);
        count = removeRegistryUser(registryUserId);
        assert (count > 0);
    }

    @Test
    public void testSearchUser() throws Exception {

        loginAndAcceptDisclaimer();
        Number csmUserId = createCSMUser();
        Number registryUserId = createRegistryUser(csmUserId);
        CharSequence userName = "test" + registryUserId;

        navigateToReportViewerPage();

        WebElement inputElement = driver.findElement(By.id("firstName"));
        if (inputElement != null) {

            inputElement.sendKeys(userName);
            driver.findElement(By.id("searchreportuser")).click();
            String text = driver.findElement(By.id("row")).getText();
            assert (text.contains(userName));
        }

        logoutUser();

        int count = removeCSMUser(csmUserId);
        assert (count > 0);
        count = removeRegistryUser(registryUserId);
        assert (count > 0);
    }

    @Test
    public void testInterceptor() throws Exception {

        setPaProperty("reg.web.admin.showReportsMenu", "false");
        loginAndAcceptDisclaimer();

        driver.findElement(By.linkText("Administration")).click();

        WebElement element = null;
        try {
            element = driver.findElement(By.id("viewReportViewersMenuOption"));
        } catch (Exception e) {
        }

        assertNull(element);

    }

    /*
     * driver.get(
     * "http://localhost:39480/registry/siteadmin/viewReportViewerssearch.action"
     * ); assertEquals(
     * "You do not have access to this page. Please contact System Admin.",
     * driver.findElement(By.cssSelector("h2")).getText());
     */

    @Test
    public void testInterceptorNoAccess() throws Exception {

        setPaProperty("reg.web.admin.showReportsMenu", "false");
        loginAndAcceptDisclaimer();

        openAndWait("/registry/siteadmin/viewReportViewerssearch.action");
        // driver.get("http://localhost:39480/registry/siteadmin/viewReportViewerssearch.action");
        assertEquals(
                "You do not have access to this page. Please contact System Admin.",
                driver.findElement(By.cssSelector("h2")).getText());

    }

    private void navigateToReportViewerPage() {
        driver.findElement(By.linkText("Administration")).click();
        driver.findElement(By.id("viewReportViewersMenuOption")).click();
        assertEquals("Report Viewers",
                driver.findElement(By.cssSelector("h1.heading")).getText());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        mockRestClientNCITServer.stopServer();
    }

}
