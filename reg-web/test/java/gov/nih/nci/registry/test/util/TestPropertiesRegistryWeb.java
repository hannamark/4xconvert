package gov.nih.nci.registry.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestPropertiesRegistryWeb {
    public static final String TEST_LOGIN_CONFIG = "test.java.security.login.config";
    @SuppressWarnings("AvoidThrowingRawExceptionTypes")
    private static Properties properties = new Properties();
    static {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties");;
            properties.load(stream);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    public static String getLoginConfig() {
        return properties.getProperty(TEST_LOGIN_CONFIG
            , "Error:  test.java.security.login.config not set in build.properties.");
    }

}
