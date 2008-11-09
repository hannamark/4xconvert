package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;

import javax.ejb.Remote;



/**
 * 
 * @author Bala Nair
 *
 */
@Remote
public interface RegistryUserServiceRemote {
    
    /**
     * Create a new Registry user.
     * @param user user
     * @return user
     * @throws PAException PAException
     */
    RegistryUser createUser(RegistryUser user) throws PAException;
    
    /**
     * Update an existing Registry user.
     * @param user user
     * @return user
     * @throws PAException PAException
     */
    RegistryUser updateUser(RegistryUser user) throws PAException;
    
    /**
     * Retrieves user by login name.
     * @param loginName loginName
     * @return user
     * @throws PAException PAException
     */
    RegistryUser getUser(String loginName) throws PAException;
    
    

}
