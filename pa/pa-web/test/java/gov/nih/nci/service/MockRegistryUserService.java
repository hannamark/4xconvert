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
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#createUser(gov.nih.nci.pa.domain.RegistryUser)
     */
    public RegistryUser createUser(RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#getUser(java.lang.String)
     */
    public RegistryUser getUser(String loginName) throws PAException {
        if(loginName != null && loginName.equals("exceptionName")){
            throw new PAException("test");
        }
        RegistryUser regUser = new RegistryUser();
        regUser.setCity("city");
        regUser.setCountry("country");
        regUser.setCsmUserId(1L);
        User userLastCreated = new User();
        userLastCreated.setLoginName("userLastCreated");
        regUser.setUserLastCreated(userLastCreated);
        User userLastUpdated = new User();
        userLastUpdated.setLoginName("userLastUpdated");
        regUser.setUserLastUpdated(userLastUpdated);
        return regUser;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#updateUser(gov.nih.nci.pa.domain.RegistryUser)
     */
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#getUserByUserOrgType(gov.nih.nci.pa.enums.UserOrgType)
     */
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType)
            throws PAException {
        List<RegistryUser> userTypeList = new ArrayList<RegistryUser>();
        for (RegistryUser regUser: usrList) {
            if(regUser.getAffiliatedOrgUserType().equals(userType)) {
                userTypeList.add(regUser);
            }
        }
        return userTypeList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#getUserById(java.lang.Long)
     */
    public RegistryUser getUserById(Long userId) throws PAException {
        for (RegistryUser regUser: usrList) {
            if (userId != null && userId ==4) {
                throw new PAException("test");
            }
            if(regUser.getId().equals(userId)) {
                return regUser;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#search(gov.nih.nci.pa.domain.RegistryUser)
     */
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        // TODO Auto-generated method stub
        return new ArrayList<RegistryUser>();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#hasTrialAccess(java.lang.String, java.lang.Long)
     */
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#hasTrialAccess(gov.nih.nci.pa.domain.RegistryUser, java.lang.Long)
     */
    public boolean hasTrialAccess(RegistryUser user, Long studyProtocolId)
            throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#assignOwnership(java.lang.Long, java.lang.Long)
     */
    public void assignOwnership(Long userId, Long studyProtocolId)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#removeOwnership(java.lang.Long, java.lang.Long)
     */
    public void removeOwnership(Long userId, Long studyProtocolId)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#isTrialOwner(java.lang.Long, java.lang.Long)
     */
    public boolean isTrialOwner(Long userId, Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#searchTrialOwnership(gov.nih.nci.pa.util.DisplayTrialOwnershipInformation, java.lang.Long)
     */
    public List<DisplayTrialOwnershipInformation> searchTrialOwnership(
            DisplayTrialOwnershipInformation trialOwnershipInfo, Long affiliatedOrgId) throws PAException {
        // TODO Auto-generated method stub
        return new ArrayList<DisplayTrialOwnershipInformation>();
    }

    /**
     * {@inheritDoc}
     */
    public boolean doesRegistryUserExist(String loginName) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getTrialOwnerNames(Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Set<RegistryUser> getAllTrialOwners(Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
