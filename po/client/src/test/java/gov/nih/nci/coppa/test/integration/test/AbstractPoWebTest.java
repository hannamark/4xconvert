package gov.nih.nci.coppa.test.integration.test;


public abstract class AbstractPoWebTest extends AbstractSeleneseTestCase {

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
    
}
