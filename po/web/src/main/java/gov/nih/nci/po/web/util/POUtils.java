/**
 * 
 */
package gov.nih.nci.po.web.util;

import org.apache.commons.lang.StringUtils;

/**
 * Multi-purpose utilities.
 * @author Denis G. Krylov
 *
 */
public final class POUtils {
    
    
    
    /**
     * PO-5934: attempts to automatically convert the format of the phone number
     * to the one accepted by the system. For US/Canada-based phone numbers,
     * attempts to normalize to ###-###-####(x#*). For others, simply removes
     * parenthesis as requested.
     * 
     * @param phone
     *            phone
     * @return reformatted or untouched phone
     */
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public static String adjustPhoneNumberFormat(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            phone = phone.replaceFirst("^\\s*\\((\\d+)\\)\\s*-\\s*", "$1-")
                    .replaceFirst("^\\s*\\((\\d+)\\)\\s*(\\d)", "$1-$2")
                    .replaceAll("[\\(\\)]", "");
        }
        return phone;
    }

}
