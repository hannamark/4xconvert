package gov.nih.nci.coppa.test.integration.test;

import org.apache.log4j.Logger;

public abstract class AbstractPoWebTest extends AbstractSeleneseTestCase {
    protected static final String DEFAULT_URL = "http://default.example.com";

    protected static final String DEFAULT_EMAIL = "default@example.com";
    private static int PAGE_SIZE = 20;
    private static final Logger LOG = Logger.getLogger(AbstractPoWebTest.class);

    protected void login(String username, String password) {
        selenium.open("/po-web");
        verifyTrue(selenium.isTextPresent("Login"));
        verifyTrue(selenium.isTextPresent("CONTACT US"));
        clickAndWait("link=Login");
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        clickAndWait("id=enableEnterSubmit");
        verifyTrue(selenium.isElementPresent("link=Logout"));
        verifyTrue(selenium.isElementPresent("id=EntityInboxOrganization"));
        verifyTrue(selenium.isElementPresent("id=SearchOrganization"));
        verifyTrue(selenium.isElementPresent("id=CreateOrganization"));
        verifyTrue(selenium.isElementPresent("id=EntityInboxPerson"));
        verifyTrue(selenium.isElementPresent("id=SearchPerson"));
        verifyTrue(selenium.isElementPresent("id=CreatePerson"));
    }

    public void loginAsCurator() {
        login("curator", "pass");
    }

    protected boolean isLoggedIn() {
        return selenium.isElementPresent("link=Logout") && !selenium.isElementPresent("link=Login");
    }

    protected void clickAndWaitSaveButton() {
        clickAndWaitButton("save_button");
    }

    protected void clickAndWaitButton(String buttonId) {
        clickAndWait("//a[@id='" + buttonId + "']/span/span");
    }

    protected void openCreateOrganization() {
        goHome();
        selenium.click("CreateOrganization");
        waitForPageToLoad();
    }

    private void goHome() {
        if (!isLoggedIn()) {
            loginAsCurator();
        }
        selenium.open("/po-web/protected/home.action");
        waitForPageToLoad();
    }

    protected void openCreatePerson() {
        goHome();
        selenium.click("CreatePerson");
        waitForPageToLoad();
    }

    protected void openSearchPerson() {
        goHome();
        clickAndWait("SearchPerson");
    }

    protected void openSearchOrganization() {
        goHome();
        clickAndWait("SearchOrganization");
    }

    /**
     * Searches in the given column for the given text
     * 
     * @param text - string to search for
     * @param column - table column to search in
     * @return the row number where the text was found. -1 if not found
     */
    protected int getRow(String text, int column) {
        String tblValue = null;
        for (int row = 1;; row++) {
            tblValue = null;
            try {
                tblValue = selenium.getTable("row." + row + "." + column);
            } catch (RuntimeException e) {
                if (tblValue == null) {
                    // there are no rows on the page
                    return -1;
                }
                LOG.info("problem looking for " + text + " at (" + row + "," + column + ") Stopped at : " + tblValue);
                throw e;
            }
            if (text.equalsIgnoreCase(tblValue)) {
                return row;
            }

            if (row % PAGE_SIZE == 0) {
                // Moving to next page
                // this will fail once there are no more pages and the text parameter is not found
                try {
                    clickAndWait("link=Next");
                } catch (Exception e1) {
                    return -1;
                }
                pause(2000);
                row = 0;
            }
        }
    }

    /**
     * Verify the Telecom fields behave properly
     */
    protected void verifyTelecom() {
        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();
    }

    private void verifyEmail() {
        waitForElementById("email-remove-0", 5);
        assertEquals(DEFAULT_EMAIL + " | Remove", selenium.getText("email-entry-0"));

        waitForElementById("emailEntry_value", 5);
        clear("emailEntry_value");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address must be set"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc@example.com");
        selenium.click("email-add");
        waitForElementById("email-remove-1", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("email-entry-1"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc.com");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address is not a well-formed email address"));

        clear("emailEntry_value");
    }

    private void clear(String locator) {
        selenium.type(locator, "");
    }

    private void verifyPhone() {
        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone must be set"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "123-456-7890");
        selenium.click("phone-add");
        waitForElementById("phone-remove-0", 5);
        assertEquals("123-456-7890 | Remove", selenium.getText("phone-entry-0"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "1234567890123456789012345678901");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone length must be between 0 and 30"));

        clear("phoneEntry_value");
    }

    private void verifyFax() {
        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax must be set"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "234-567-8901");
        selenium.click("fax-add");
        waitForElementById("fax-remove-0", 5);
        assertEquals("234-567-8901 | Remove", selenium.getText("fax-entry-0"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "1234567890123456789012345678901");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax length must be between 0 and 30"));

        clear("faxEntry_value");
    }

    private void verifyTty() {
        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY must be set"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "345-678-9012");
        selenium.click("tty-add");
        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", selenium.getText("tty-entry-0"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "1234567890123456789012345678901");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY length must be between 0 and 30"));

        clear("ttyEntry_value");
    }

    private void verifyUrl() {
        waitForElementById("url-remove-0", 5);
        assertEquals(DEFAULT_URL + " | Remove | Visit...", selenium.getText("url-entry-0"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL must be set"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "http://www.example.com");
        selenium.click("url-add");
        waitForElementById("url-remove-1", 5);
        assertEquals("http://www.example.com | Remove | Visit...", selenium.getText("url-entry-1"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "abc.com");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL is not well formed"));

        clear("urlEntry_value");
    }

    protected void verifyPostalAddress(ENTITYTYPE type) {
        assertEquals("123 abc ave.", selenium.getValue("curateEntityForm_" + type.name()
                + "_postalAddress_streetAddressLine"));
        assertEquals("", selenium.getValue("curateEntityForm_" + type.name()
                + "_postalAddress_deliveryAddressLine"));
        assertEquals("mycity", selenium.getValue("curateEntityForm_" + type.name()
                + "_postalAddress_cityOrMunicipality"));
        assertEquals("VA", selenium.getValue("curateEntityForm." + type.name()
                + ".postalAddress.stateOrProvince"));
        assertEquals("12345", selenium.getValue("curateEntityForm_" + type.name()
                + "_postalAddress_postalCode"));
    }

    public enum ENTITYTYPE {
        person, organization;
    }
}
