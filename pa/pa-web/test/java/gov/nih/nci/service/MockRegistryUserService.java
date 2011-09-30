/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Vrushali
 *
 */
public class MockRegistryUserService implements RegistryUserServiceLocal {
    static List<RegistryUser> usrList;
    static {
        usrList = new ArrayList<RegistryUser>();
        RegistryUser usr = new RegistryUser();
        usr.setId(1L);
        usr.setFirstName("firstName");
        usr.setLastName("lastName");
        usr.setAffiliatedOrganizationId(1L);
        usr.setAffiliatedOrgUserType(UserOrgType.PENDING_ADMIN);
        usrList.add(usr);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser createUser(RegistryUser user) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUser(String loginName) throws PAException {
        if (StringUtils.equals(loginName, "exceptionName")) {
            throw new PAException("test");
        }
        if (StringUtils.equals(loginName, "nullRegistryUser")) {
            return null;
        }

        RegistryUser regUser = new RegistryUser();
        regUser.setFirstName("Test");
        regUser.setLastName("User");
        regUser.setCity("city");
        regUser.setCountry("country");
        User csmUser = new User();
        csmUser.setUserId(1L);
        regUser.setCsmUser(csmUser);
        User userLastCreated = new User();
        userLastCreated.setLoginName("userLastCreated");
        regUser.setUserLastCreated(userLastCreated);
        User userLastUpdated = new User();
        userLastUpdated.setLoginName("userLastUpdated");
        regUser.setUserLastUpdated(userLastUpdated);
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        List<RegistryUser> userTypeList = new ArrayList<RegistryUser>();
        for (RegistryUser regUser : usrList) {
            if (regUser.getAffiliatedOrgUserType().equals(userType)) {
                userTypeList.add(regUser);
            }
        }
        return userTypeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUserById(Long userId) throws PAException {
        for (RegistryUser regUser : usrList) {
            if (userId != null && userId == 4) {
                throw new PAException("test");
            }
            if (regUser.getId().equals(userId)) {
                return regUser;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        return new ArrayList<RegistryUser>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(RegistryUser user, Long studyProtocolId) throws PAException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignOwnership(Long userId, Long studyProtocolId) throws PAException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeOwnership(Long userId, Long studyProtocolId) throws PAException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrialOwner(Long userId, Long studyProtocolId) throws PAException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DisplayTrialOwnershipInformation> searchTrialOwnership(
            DisplayTrialOwnershipInformation trialOwnershipInfo, Long affiliatedOrgId) throws PAException {
        return new ArrayList<DisplayTrialOwnershipInformation>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doesRegistryUserExist(String loginName) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTrialOwnerNames(Long studyProtocolId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<RegistryUser> getAllTrialOwners(Long studyProtocolId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress) {
        return null;
    }
}
