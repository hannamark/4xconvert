package gov.nih.nci.coppa.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestUtils {

    public static Properties properties = new Properties();
    public static final String DEFAULT_UTF8_STRING = "живлёнымΕλάδαविप्रकुर्युर्समाचारažībs學而時習之";

    public static String getUTF8TestString() {
        try {
            InputStream stream = TestUtils.class.getClassLoader()
                    .getResourceAsStream("utf8chars.properties");
            properties.load(stream);

            String utfString = properties.getProperty("utf8string",
                    DEFAULT_UTF8_STRING);
            return utfString;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
