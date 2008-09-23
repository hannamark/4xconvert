package gov.nih.nci.pa.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public class TestPropertiesPaEar {
    public static final String JAVA_NAMING_PROVIDER_URL 
         = "test.java.naming.provider";

    private static Properties properties = new Properties();
    static {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties");;
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getJavaNamingProvider() {
        return properties.getProperty(JAVA_NAMING_PROVIDER_URL, "localhost");
    }

}
