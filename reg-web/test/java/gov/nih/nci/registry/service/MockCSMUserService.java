/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vrushali
 *
 */
public class MockCSMUserService implements CSMUserUtil {
    public static List<User> users;
    static {
        users = new ArrayList<User>();
        User user = new User();
        user.setLoginName("user1@mail.nih.gov");
        user.setUserId(1L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        users.add(user);
        user = new User();
        user.setLoginName("user2@mail.nih.gov");
        user.setUserId(2L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        users.add(user);
        user = new User();
        user.setLoginName("user3@mail.nih.gov");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setUserId(3L);
        users.add(user);
    }

    public User createCSMUser(RegistryUser user, String loginName, String password) throws PAException {
        return new User();
    }

    public User updateCSMUser(RegistryUser user, String loginName, String password) throws PAException {
        return new User();
    }

    public User getCSMUser(String loginName) throws PAException {
        for (User user : users) {
            if (user.getLoginName().equals(loginName)) {
                return user;
            }
        }
        return new User();
    }

    public Set<User> getCSMUsers() throws PAException {
        Set<User> userSet = new HashSet<User>();
        userSet.addAll(users);
        return userSet;
    }

    /**
     * Assigns the given user to the given group.
     * @param loginName the user to add to the group
     * @param groupName the group to add the user to
     * @throws PAException on error
     */
    public void assignUserToGroup(String loginName, String groupName) throws PAException {
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            upManager.assignUserToGroup(loginName, groupName);
        } catch (Exception e) {
            throw new PAException("CSM exception while adding " + loginName + " to " + groupName + " group.", e);
        }
    }

    /**
     * Retrieves an existing CSM user by id.
     * @param id the id of the CSM user to retrieve
     * @return The CSM user with the give id
     * @throws PAException on error
     */
    public User getCSMUserById(Long id) throws PAException {
        User csmUser = null;
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            csmUser = upManager.getUserById(id.toString());
        } catch (CSConfigurationException csce) {
            throw new PAException(" CSM exception while retrieving CSM user :" + id, csce);
        } catch (CSException cse) {
            throw new PAException(" CSM exception while retrieving CSM user :" + id, cse);
        }
        return csmUser;
    }
}
