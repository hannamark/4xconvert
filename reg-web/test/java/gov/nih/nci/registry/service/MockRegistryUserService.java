/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;

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
        dto.setCsmUserId(1L);
        dto.setId(1L);
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

}
