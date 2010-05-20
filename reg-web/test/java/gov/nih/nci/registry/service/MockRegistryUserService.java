/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockRegistryUserService implements RegistryUserServiceRemote {
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
            if(usrDto.getAffiliatedOrganizationId().equals(regUser.getAffiliatedOrganizationId())
                 && usrDto.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)){
                 returnList.add(usrDto);
            }
        }
		return returnList;
	}

}
