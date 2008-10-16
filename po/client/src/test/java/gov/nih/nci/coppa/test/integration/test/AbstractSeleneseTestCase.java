package gov.nih.nci.coppa.test.integration.test;


import gov.nih.nci.coppa.test.TstProperties;

import org.apache.log4j.Logger;

import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * todo Move this class to commons-test. caArray has a similar one.
 */
public abstract class AbstractSeleneseTestCase extends SeleneseTestCase {
    private static final int PAGE_TIMEOUT_SECONDS = 180;

    private static final Logger LOG = Logger.getLogger(AbstractSeleneseTestCase.class);

    @Override
    public void setUp() throws Exception {
        System.setProperty("selenium.port", "" + TstProperties.getSeleniumServerPort());
        String hostname = TstProperties.getServerHostname();
        int port = TstProperties.getServerPort();
        String browser = TstProperties.getSeleniumBrowser();
        if (port == 0) {
            super.setUp("http://" + hostname, browser);
        } else {
            super.setUp("http://" + hostname + ":" + port, browser);

        }
        selenium.setTimeout(toMillisecondsString(PAGE_TIMEOUT_SECONDS));
    }

    protected static String toMillisecondsString(long seconds) {
        return String.valueOf(seconds * 1000);
    }

    protected void waitForPageToLoad() {
        selenium.waitForPageToLoad(toMillisecondsString(PAGE_TIMEOUT_SECONDS));
    }

    protected void waitForElementById(String id, int timeoutSeconds) {
        selenium.waitForCondition("selenium.browserbot.getCurrentWindow().document.getElementById('"
                + id + "');", toMillisecondsString(timeoutSeconds));
    }

    protected void waitForElementByXPath(String xpathExpression, int timeoutSeconds) {
        String cond = "var doc = selenium.browserbot.getCurrentWindow().document; "
                + "doc.evaluate("
                + toJSString(xpathExpression) + ", doc, null, XPathResult.BOOLEAN_TYPE, null).booleanValue;";
        LOG.info(cond);
        selenium.waitForCondition(cond, toMillisecondsString(timeoutSeconds));
    }

    protected static String toJSString(String str) {
        if (str == null) { return "null"; }
        StringBuffer sb = new StringBuffer().append('\'');
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (c == '\'' || c == '\\') {
                sb.append('\\');
            }
            sb.append(c);
        }

        return sb.append('\'').toString();
    }

    protected void clickAndWait(String locator) {
        selenium.click(locator);
        waitForPageToLoad();
    }
}
