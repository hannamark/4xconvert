package gov.nih.nci.coppa.test;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Environment properties passed in to tests.
 */
public final class TstProperties {

    public static final String SERVER_HOSTNAME_KEY = "server.hostname";
    public static final String SERVER_PORT_KEY = "server.port";
    public static final String SERVER_JNDI_PORT_KEY = "server.jndi.port";

    public static final String SERVER_HOSTNAME_DEFAULT = "localhost";
    public static final String SERVER_PORT_DEFAULT = "8080";
    public static final String SERVER_JNDI_PORT_DEFAULT = "1099";

    public static final String SELENIUM_SERVER_PORT_KEY = "selenium.server.port";
    public static final String SELENIUM_SERVER_PORT_DEFAULT = "4444";

    public static final String SELENIUM_BROWSER_KEY = "selenium.browser";
    public static final String SELENIUM_BROWSER_DEFAULT = "*chrome";


    public static Properties properties = new Properties();
    static {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties");
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getServerHostname() {
        return properties.getProperty(SERVER_HOSTNAME_KEY, SERVER_HOSTNAME_DEFAULT);
    }

    public static int getServerPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT_KEY, SERVER_PORT_DEFAULT));
    }

    public static int getServerJndiPort() {
        return Integer.parseInt(properties.getProperty(SERVER_JNDI_PORT_KEY, SERVER_JNDI_PORT_DEFAULT));
    }

    public static int getSeleniumServerPort() {
        return Integer.parseInt(properties.getProperty(SELENIUM_SERVER_PORT_KEY, SELENIUM_SERVER_PORT_DEFAULT));
    }

    public static String getSeleniumBrowser() {
        return properties.getProperty(SELENIUM_BROWSER_KEY, SELENIUM_BROWSER_DEFAULT);
    }
}
