/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockRegistryUserService implements RegistryUserServiceLocal {
    static List<RegistryUser> userList;
    static {
        userList = new ArrayList<RegistryUser>();
        RegistryUser dto = new RegistryUser();
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setId(1L);
        dto.setAffiliatedOrganizationId(1L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        userList.add(dto);
        dto = new RegistryUser();
        dto.setFirstName("affiliated Org");
        dto.setLastName("lastName");
        dto.setId(2L);
        dto.setAffiliatedOrganizationId(2L);
        dto.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        userList.add(dto);

        // add three members.
        dto = new RegistryUser();
        dto.setFirstName("RegUser");
        dto.setLastName("lastName");
        dto.setId(3L);
        dto.setAffiliatedOrganizationId(3L);
        dto.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        userList.add(dto);

        dto = new RegistryUser();
        dto.setFirstName("reguser2");
        dto.setLastName("lastName Two");
        dto.setId(4L);
        dto.setAffiliatedOrganizationId(3L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        userList.add(dto);

        dto = new RegistryUser();
        dto.setFirstName("reguser3");
        dto.setLastName("lastName Three");
        dto.setId(5L);
        dto.setAffiliatedOrganizationId(3L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        userList.add(dto);
        dto = new RegistryUser();
        dto.setFirstName("userLastCreated");
        dto.setLastName("lastName");
        dto.setId(1L);
        dto.setAffiliatedOrganizationId(1L);
        dto.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        userList.add(dto);
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
        if (loginName != null && loginName.equals("throwException")) {
            throw new PAException("testing");
        }
        for (RegistryUser usrDto : userList) {
            if (usrDto.getFirstName().equals(loginName)) {
                return usrDto;
            }
        }
        return null;
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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUser getUserById(Long userId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        List<RegistryUser> returnList = new ArrayList<RegistryUser>();
        for (RegistryUser usrDto : userList) {
            if (regUser.getAffiliatedOrgUserType() != null) {
                if (usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())
                        && usrDto.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)) {
                    returnList.add(usrDto);
                }
            } else {
                if (usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())) {
                    returnList.add(usrDto);
                }
            }

        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        if (loginName != null && loginName.equalsIgnoreCase("userLastCreated") && studyProtocolId != null
                && studyProtocolId == 1) {
            return true;
        }
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
    public void activateAccount(String email, String username) throws PAException {
    }

    public List<RegistryUser> getLoginNamesByEmailAddress(String emailAddress) {
        return null;
    }

    @Override
    public void assignSiteOwnership(Long userId, Long studySiteId)
            throws PAException {  
    }

    @Override
    public void removeSiteOwnership(Long userId, Long studySiteId)
            throws PAException {
       
        
    }

    
    public Long getUserId(String loginName) throws PAException {     
        return 0L;
    }

    
    public RegistryUser getPartialUserById(Long userId) throws PAException {      
        return getUserById(userId);
    }

    
    public boolean isEmailNotificationsEnabled(Long userId, Long trialId)
            throws PAException {
        return false;
    }

    
    public void setEmailNotificationsPreference(Long userId, Long trialId,
            boolean enableEmails) throws PAException {
      
        
    }

    @Override
    public boolean isEmailNotificationsEnabledOnTrialLevel(Long userId,
            Long trialId) throws PAException {
      
        return false;
    }

}
