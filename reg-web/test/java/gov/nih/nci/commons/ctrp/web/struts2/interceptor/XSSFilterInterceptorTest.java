package gov.nih.nci.commons.ctrp.web.struts2.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class XSSFilterInterceptorTest extends AbstractInterceptorBaseTest {
    private final String KEY1 = "KEY1";
    private final String KEY2 = "KEY2";

    @Test
    public void testXSSFiterInterceptor() throws Exception {
        String testString = getTestString();
        String whitespace = getWhitespaceCharString();

        getParams().put(KEY1, testString);
        getParams().put(KEY2, getArrayOfStrings(testString));
        XSSFilterInterceptor interceptor = new XSSFilterInterceptor();
        interceptor.intercept(getActionInvocation());
        // By default, both symbols and control chars are replaced.
        assertEquals("&lt;" + whitespace + "script&gt;", getParams().get(KEY1));
        assertEquals("&lt;" + whitespace + "script&gt;", ((String[]) getParams().get(KEY2))[0]);
        assertEquals("some other value", ((String[]) getParams().get(KEY2))[1]);

        getParams().put(KEY1, testString);
        getParams().put(KEY2, getArrayOfStrings(testString));
        // retain both symbols and control chars.
        interceptor.setFilterControlChars(false);
        interceptor.setFilterSymbols(false);
        interceptor.intercept(getActionInvocation());
        assertFalse("< script>".equals(getParams().get(KEY1).toString()));
        assertFalse("&lt; script&gt;".equals(getParams().get(KEY1).toString()));
        assertTrue(getParams().get(KEY1).toString().equals(testString));
        assertTrue(((String[]) getParams().get(KEY2))[0].equals(testString));
    }

    private String[] getArrayOfStrings(String val) {
        String[] arr = new String[2];
        arr[0] = val;
        arr[1] = "some other value";
        return arr;
    }

    private String getTestString() {
        StringBuilder sb = new StringBuilder("<");
        // characters 0-31 are all the ASCII control chars and white space (carriage returns, tabs, etc); the control
        // characters should get stripped out but not the white space
        // 32 is a space, so that should also not get stripped out
        for (char c = 0; c <= 32; c++) {
            sb.append(c);
        }
        sb.append("script>");

        return sb.toString();
    }

    private String getWhitespaceCharString() {
        StringBuilder whitespaceChars = new StringBuilder("");
        // get a string of just the whitespace chars
        for (char c = 0; c <= 32; c++) {
            if (Character.isWhitespace(c)) {
                whitespaceChars.append(c);
            }
        }
        return whitespaceChars.toString();
    }
}
