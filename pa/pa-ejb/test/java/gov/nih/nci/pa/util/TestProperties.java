package gov.nih.nci.pa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Environment properties passed in to tests.
 */
public class TestProperties {
    public static final String TEST_SCHEMA_SHOWSQL = "test.schema.showsql";

    private static Properties properties = new Properties();
    static {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties");;
            properties.load(stream);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }
    
    public static String getShowSQL() {
        return properties.getProperty(TEST_SCHEMA_SHOWSQL, "false");
    }
}
