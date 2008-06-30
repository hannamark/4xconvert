package gov.nih.nci.coppa.test.integration.test;



public class LoginTest extends AbstractSeleneseTestCase {

    public void testNew() throws Exception {
        selenium.open("/po-web");
        verifyTrue(selenium.isTextPresent("Login"));
        verifyTrue(selenium.isTextPresent("CONTACT US"));
        clickAndWait("link=Login");
        selenium.type("j_username", "curator");
        selenium.type("j_password", "pass");
        clickAndWait("id=enableEnterSubmit");
        verifyTrue(selenium.isElementPresent("link=Logout"));
        verifyTrue(selenium.isElementPresent("link=Inbox"));
    }
}
