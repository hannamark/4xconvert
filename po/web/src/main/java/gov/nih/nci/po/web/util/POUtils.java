/**
 * 
 */
package gov.nih.nci.po.web.util;

import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * Multi-purpose utilities.
 * 
 * @author Denis G. Krylov
 * @author Rohit Gupta
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

    /**
     * This method is used to return the logged-in user.
     * 
     * @return User
     * @throws CSException
     *             CSException
     */
    public static User getLoggedInUser() throws CSException {
        return SecurityServiceProvider.getUserProvisioningManager("po")
                .getUser(ServletActionContext.getRequest().getRemoteUser());
    }
    
}
