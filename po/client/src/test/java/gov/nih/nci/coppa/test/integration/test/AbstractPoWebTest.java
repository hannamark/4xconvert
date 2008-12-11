package gov.nih.nci.coppa.test.integration.test;

import org.apache.log4j.Logger;


public abstract class AbstractPoWebTest extends AbstractSeleneseTestCase {

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
     * @param text - string to search for
     * @param column - table column to search in
     * @return the row number where the text was found.  -1 if not found
     */
    protected int getRow(String text, int column) {
        String tblValue = null;
        for (int row = 1;; row++) {
            tblValue = null;
            try {
                tblValue = selenium.getTable("row." + row + "." + column);
             } catch (RuntimeException e) {
                 if (tblValue == null){
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

}
