package gov.nih.nci.commons.ctrp.util;

import org.apache.commons.lang.StringUtils;

/**
 * Utils class for nci-commons. Branched from com.fiveamsolutions.nci.commons so that it can be edited for CTRP.
 * 
 * @author bpickeral
 */
public final class NCICommonsUtils {

    private NCICommonsUtils() {
    }

    /**
     * 
     * Filters user input to protect against XSS attacks. Replaces the <code><</code> symbol with <code>&lt;</code> and
     * replaces the <code>></code> symbol with <code>&gt;</code> Follows the guidance given at:
     * http://www.stripesframework.org/display/stripes/XSS+filter
     * 
     * Also strips out ASCII control characters, per Character.isISOControl(char) and
     * http://www.w3schools.com/TAGS/ref_urlencode.asp.
     * 
     * @param s The String to filter.
     * @param filterSymbols Substitute the < and > chars or not.
     * @param filterControlChars Filter control characters or not.
     * @param filterScriptTag Filter out url encoded less than.
     * @return The filtered String.
     */
    public static String performXSSFilter(String s, boolean filterSymbols, boolean filterControlChars,
            boolean filterScriptTag) {
        boolean forceFilterSymbols = filterSymbols || filterScriptTag && StringUtils.containsIgnoreCase(s, "script");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (isSymbol(ch)) {
                handleSymbol(sb, ch, forceFilterSymbols, filterScriptTag);
            } else if (Character.isISOControl(ch) && !Character.isWhitespace(ch)) {
                handleControlChar(sb, ch, filterControlChars);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private static void handleSymbol(StringBuffer sb, char ch, boolean filter, boolean filterScriptTag) {
        if (filter) {
            if (filterScriptTag) {
                sb.append(' ');
            } else if (ch == '<') {
                sb.append("&lt;");
            } else {
                sb.append("&gt;");
            }
        } else {
            sb.append(ch);
        }
    }

    private static void handleControlChar(StringBuffer sb, char ch, boolean filterControlChar) {
        if (!filterControlChar) {
            sb.append(ch);
        }
    }

    private static boolean isSymbol(char ch) {
        return ch == '<' || ch == '>';
    }
}
