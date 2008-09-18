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
        verifyTrue(selenium.isElementPresent("link=Organization"));
        verifyTrue(selenium.isElementPresent("link=Person"));
    }

    public void loginAsCurator() {
        login("curator", "pass");
    }

}
