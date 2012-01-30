package gov.nih.nci.commons.ctrp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author bpickeral
 *
 */
public class NCICommonsUtilsTest {
    /**
     * test for filter.
     *
     * @throws Exception on error
     */
    @Test
    public void testFilter() throws Exception {
        StringBuilder sb = new StringBuilder("<");
        // characters 0-31 are all the ASCII control chars and white space (carriage returns, tabs, etc); the control
        // characters should get stripped out but not the white space
        // 32 is a space, so that should also not get stripped out
        for (char c = 0; c <= 32; c++) {
            sb.append(c);
        }

        StringBuilder whitespaceChars = new StringBuilder("");
        // get a string of just the whitespace chars
        for (char c = 0; c <= 32; c++) {
            if (Character.isWhitespace(c)) {
                whitespaceChars.append(c);
            }
        }
        String whitespace = whitespaceChars.toString();

        sb.append("script>");
        // replace both symbols and control chars.
        String s = NCICommonsUtils.performXSSFilter(sb.toString(), true, true, false);
        assertEquals("&lt;" + whitespace + "script&gt;", s);

        // replace only the control chars.
        s = NCICommonsUtils.performXSSFilter(sb.toString(), false, true, false);
        assertEquals("<" + whitespace + "script>", s);

        // do not replace symbols/control chars.
        s = NCICommonsUtils.performXSSFilter(sb.toString(), false, false, false);
        assertFalse("< script>".equals(s));
        assertFalse("&lt; script&gt;".equals(s));
        assertTrue(s.equals(sb.toString()));

        // substitute symbols only. retain control chars.
        s = NCICommonsUtils.performXSSFilter(sb.toString(), true, false, false);
        StringBuilder sb1 = new StringBuilder("&lt;");
        for (char c = 0; c <= 32; c++) {
            sb1.append(c);
        }
        sb1.append("script&gt;");
        assertFalse("< script>".equals(s));
        assertFalse("&lt; script&gt;".equals(s));
        assertFalse(s.equals(sb.toString()));
        assertTrue(s.equals(sb1.toString()));
    }

    @Test
    public void testFilterScriptTag() {
        String testString = "security<script>alert(34880)</script>";
        String fixedString = "security script alert(34880) /script ";
        // lowercase
        assertEquals(fixedString, NCICommonsUtils.performXSSFilter(testString, false, true, true));
        assertEquals(fixedString, NCICommonsUtils.performXSSFilter(testString, false, false, true));
        // uppercase
        assertEquals(fixedString.toUpperCase(),
                NCICommonsUtils.performXSSFilter(testString.toUpperCase(), false, false, true));
        assertEquals(fixedString.toUpperCase(),
                NCICommonsUtils.performXSSFilter(testString.toUpperCase(), false, true, true));
        // no tag
        assertEquals("<>", NCICommonsUtils.performXSSFilter("<>", false, false, true));
        assertEquals("<>", NCICommonsUtils.performXSSFilter("<>", false, true, true));
    }
}
