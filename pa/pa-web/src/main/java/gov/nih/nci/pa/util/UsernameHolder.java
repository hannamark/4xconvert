package gov.nih.nci.pa.util;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Holds the name of the currently-logged in user in a ThreadLocal.  If the
 * value is unset, return the username for the 'anonymous' user.
 */
public final class UsernameHolder {

    private static final Logger LOG = Logger.getLogger(UsernameHolder.class);

    private static ThreadLocal<String> usernameThreadLocal = new ThreadLocal<String>();

    /**
     * The username returned for anonymous access, or when setUser has not been called.
     */
    public static final String ANONYMOUS_USERNAME = "__anonymous__";

    /**
     * @param user the user to set for the current thread
     */
    public static void setUser(String user) {
        if (ANONYMOUS_USERNAME.equals(user)) {
            LOG.warn("explicitly setting user to the ANONYMOUS_USERNAME");
        }
        usernameThreadLocal.set((user == null) ? null : user.toLowerCase(Locale.US));
    }

    /**
     * @return the currently logged in user for this thread, or the anonymous user
     *         if no user is logged in
     */
    public static String getUser() {
        String val = usernameThreadLocal.get();
        if (StringUtils.isBlank(val)) {
            return ANONYMOUS_USERNAME;
        }
        return val;
    }
}
