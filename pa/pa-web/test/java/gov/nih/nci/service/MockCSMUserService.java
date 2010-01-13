/**
 * 
 */
package gov.nih.nci.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * @author Vrushali
 *
 */
public class MockCSMUserService extends CSMUserService {
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
    public User createCSMUser(RegistryUser user, String loginName,
            String password) throws PAException {
        return new User();
    }

    public User updateCSMUser(RegistryUser user, String loginName,
            String password) throws PAException {
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
    
    @SuppressWarnings("unchecked")
    public Set<User> getCSMUsers() throws PAException {
        Set <User> userSet = new HashSet<User>();
        userSet.addAll(users);
        return userSet;
    }

}
