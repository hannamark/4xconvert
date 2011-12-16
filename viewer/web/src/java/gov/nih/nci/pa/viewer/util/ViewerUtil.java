/**
 *
 */
package gov.nih.nci.pa.viewer.util;

import org.joda.time.DateTime;

/**
 * @author Hugh Reinhart
 * @since 6/17/2009
 */
public class ViewerUtil {

    private static final int ASSIGNED_IDENTIFIER_SN_LENGTH = 5;

    /**
     * @param value user input
     * @return a value conforming to NCI identifier format
     */
    public static String assignedIdentifierSetter(String value) {
        if (value == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(value.trim().toUpperCase());
        if (result.length() <= ASSIGNED_IDENTIFIER_SN_LENGTH) {
            try {
                result = new StringBuffer(String.format("%05d", Integer.parseInt(result.toString())));
            } catch (NumberFormatException e) {
                return value;
            }
        }
        if (result.length() == ASSIGNED_IDENTIFIER_SN_LENGTH) {
            StringBuffer sb = new StringBuffer("NCI-");
            sb.append(String.format("%04d", new DateTime().getYear()));
            sb.append('-');
            result = new StringBuffer(sb.append(result).toString());
        }
        return result.toString();
    }
}
