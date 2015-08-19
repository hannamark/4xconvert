package gov.nih.nci.coppa.test.integration.test;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class ManageUserGroupsTest extends AbstractPoWebTest {

    private static final int WAIT_TIME = 10;

    @Test
    public void testSecurityAdmin() throws Exception {
        String loginName = "security_" + System.currentTimeMillis();
        Number userID = createCSMUser(loginName);
        assignUserToGroup(userID, "SecurityAdmin");
        performLoginAndAcceptDisclaimer(loginName, "pass");

        verifyCuratorMenuNotAvailable();
        s.click("link=Manage User Groups");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        verifyManageUserGroups(loginName, userID);
    }

    @Test
    public void testUserWithNoRolesCantDoAnything() throws Exception {
        String loginName = "security_" + System.currentTimeMillis();
        createCSMUser(loginName);
        attemptLogin(loginName, "pass");
        verifyCuratorMenuNotAvailable();
        assertTrue(s
                .isTextPresent("Invalid username and/or password, please try again."));
    }

    @Test
    public void testCuratorsCantAccessSecurityScreens() throws Exception {
        String loginName = "security_" + System.currentTimeMillis();
        Number userID = createCSMUser(loginName);
        assignUserToGroup(userID, "Curator");
        assignUserToGroup(userID, "gridClient");
        login(loginName, "pass");
        verifySecurityAdminMenuNotAvailable();

        // Must be unable to access the screen directly.
        openAndWait("/po-web/security/manage/groups/execute.action");
        assertFalse(s.isTextPresent("Manage User Groups"));
        assertFalse(s.isElementPresent("users"));

    }

    private void verifySecurityAdminMenuNotAvailable() {
        assertFalse(selenium.isElementPresent("link=Manage User Groups"));

    }

    private void verifyManageUserGroups(String loginName, Number userID)
            throws SQLException {
        s.select("//select[@name='users_length']", "100");
        driver.findElement(By.xpath("//input[@aria-controls='users']"))
                .sendKeys(loginName);
        assertTrue(s.isTextPresent("Showing 1 to 1 of 1 entries"));
        assertTrue(s.isElementPresent("//tr/td[text()='" + loginName + "']"));
        assertOptionSelected("SecurityAdmin");
        assertOptionNotSelected("Curator");
        assertOptionNotSelected("gridClient");

        // Remove the only security group.
        useSelect2ToUnselectOption("SecurityAdmin");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertFalse(isUserInGroup(userID, "Curator"));
        assertFalse(isUserInGroup(userID, "gridClient"));

        // Now start adding back.
        useSelect2ToPickAnOption("groups_of_user_" + userID, "grid",
                "gridClient");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertFalse(isUserInGroup(userID, "Curator"));
        assertTrue(isUserInGroup(userID, "gridClient"));

        useSelect2ToPickAnOption("groups_of_user_" + userID, "Cur", "Curator");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertTrue(isUserInGroup(userID, "Curator"));
        assertTrue(isUserInGroup(userID, "gridClient"));

        useSelect2ToPickAnOption("groups_of_user_" + userID, "Secu",
                "SecurityAdmin");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertTrue(isUserInGroup(userID, "SecurityAdmin"));
        assertTrue(isUserInGroup(userID, "Curator"));
        assertTrue(isUserInGroup(userID, "gridClient"));

        // Now remove all groups again
        useSelect2ToUnselectOption("SecurityAdmin");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        useSelect2ToUnselectOption("Curator");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        useSelect2ToUnselectOption("gridClient");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertFalse(isUserInGroup(userID, "Curator"));
        assertFalse(isUserInGroup(userID, "gridClient"));

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
            xpath = By.xpath("//li[@role='treeitem']//b[text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 15);
        }

        driver.findElement(xpath).click();
        assertOptionSelected(option);
    }

    @SuppressWarnings("deprecation")
    private void useSelect2ToUnselectOption(String option) {
        s.click("//li[@class='select2-selection__choice' and @title='" + option
                + "']/span[@class='select2-selection__choice__remove']");
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));

    }

    private void verifyCuratorMenuNotAvailable() {
        assertFalse(selenium.isElementPresent("id=EntityInboxOrganization"));
        assertFalse(selenium.isElementPresent("id=SearchOrganization"));
        assertFalse(selenium.isElementPresent("id=CreateOrganization"));
        assertFalse(selenium.isElementPresent("id=ImportCtepOrgs"));
        assertFalse(selenium.isElementPresent("id=EntityInboxPerson"));
        assertFalse(selenium.isElementPresent("id=SearchPerson"));
        assertFalse(selenium.isElementPresent("id=CreatePerson"));
        assertFalse(selenium.isElementPresent("id=ImportCtepPeople"));
        assertFalse(selenium.isElementPresent("id=ListFamily"));
        assertFalse(selenium.isElementPresent("id=CreateFamily"));

    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    private void assertOptionSelected(String option) {
        assertTrue(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param option
     * @return
     */
    private String getXPathForSelectedOption(String option) {
        return "//li[@class='select2-selection__choice' and @title='" + option
                + "']";
    }

    @SuppressWarnings("deprecation")
    private void assertOptionNotSelected(String option) {
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));
    }

}
