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
        if(loginName != null && loginName.equals("throwException")) {
            throw new PAException("testing");
        }
        for(RegistryUser usrDto :userList){
            if(usrDto.getFirstName().equals(loginName)){
                return usrDto;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#updateUser(gov.nih.nci.pa.domain.RegistryUser)
     */
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     *
     */
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     *
     */
	public RegistryUser getUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RegistryUser> search(RegistryUser regUser) throws PAException {
       List<RegistryUser> returnList = new ArrayList<RegistryUser>();
       for(RegistryUser usrDto :userList){
           if (regUser.getAffiliatedOrgUserType() != null) {
               if(usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())
                       && usrDto.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)){
                       returnList.add(usrDto);
                  }
           } else {
               if(usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())) {
                   returnList.add(usrDto);
               }
           }

        }
		return returnList;
	}

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserService#hasTrialAccess(java.lang.String, java.lang.Long)
     */
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        if (loginName != null && loginName.equalsIgnoreCase("userLastCreated")
              && studyProtocolId != null && studyProtocolId == 1) {
              return true;
        }
        return false;
    }
    /**
     * {@inheritDoc}
     */
    public boolean hasTrialAccess(RegistryUser user, Long studyProtocolId) throws PAException {
        return false;
    }
	/**
     * {@inheritDoc}
     */
    public void assignOwnership(Long userId, Long studyProtocolId)
			throws PAException {
		// TODO Auto-generated method stub

	}
    /**
     * {@inheritDoc}
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
