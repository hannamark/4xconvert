package gov.nih.nci.po.web.util;

import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

/**
 * A class that provides access to CSMUser fields such as
 * first and last name.
 * 
 * @author ludetc
 *
 */
public class CsmHelper {

    private final String username;
    private String firstName;
    private String lastName;
    
    /**
     * Instantiate class with csm username.
     * @param username the csm username
     */
    public CsmHelper(String username) {
        this.username = username;
    }
    
    /**
     * returns the CSM username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * get the user's first name as recorded in CSM.
     * @return the first name
     */
    public String getFirstName() {
        if (firstName == null) {
            init();
        }
        return firstName;
    }
    
    /**
     * get the user's last name as recorded in CSM.
     * @return the last name
     */
    public String getLastName() {
        if (lastName == null) {
            init();
        }
        return lastName;
    }    
    
    private void init() {
        try {
            User csmUser = SecurityServiceProvider.getUserProvisioningManager("po").getUser(username);
            firstName = csmUser.getFirstName();
            lastName = csmUser.getLastName();
        } catch (CSException cse) {
            throw new SecurityException("CSM exception while retrieving CSM user :" + username, cse);
        }
    }
    
}
