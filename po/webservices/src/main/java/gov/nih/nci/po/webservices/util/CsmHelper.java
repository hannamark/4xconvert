package gov.nih.nci.po.webservices.util;

import org.apache.commons.lang.StringUtils;

/**
 * A class that provides access to CSMUser fields such as
 * first and last name.
 * 
 * @author ludetc
 *
 */
public class CsmHelper {

    private final String username;

    /**
     * Instantiate class with csm username.
     * @param username the csm username
     */
    public CsmHelper(String username) {
        this.username = username;
    }

    /**
     * returns the CSM username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Given a username string, determines if its in the grid format, returns the CN.
     * @return the username or the grid identity.
     */
    public String getDisplayname() {
        String splitString = "CN=";
        if (StringUtils.contains(getUsername(), splitString)) {
            return getUsername().split(splitString)[1];
        }
        return getUsername();
    }
}
