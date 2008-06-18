package gov.nih.nci;
import com.thoughtworks.selenium.*;


public class QuerySeleniumTest extends SeleneseTestCase  {
    public void testQuery() throws Exception {
        super.setUp("http://localhost:8280" , "*chrome");
        selenium.open("/pa/index.action");
        selenium.type("userName", "admin");
        selenium.type("password", "admin");
        selenium.click("authenticate_0");
        selenium.waitForPageToLoad("30000");
        selenium.click("submit");
        selenium.waitForPageToLoad("30000");
    }
}
