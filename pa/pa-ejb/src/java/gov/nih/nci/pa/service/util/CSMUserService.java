package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

/**
 * Service for managing csm users (create, retrieve and update).
 * @author Bala Nair
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
public class CSMUserService {
    
    private static final Logger LOG  = Logger.getLogger(PAPersonServiceBean.class);
    private static CSMUserService registryUserService = new CSMUserService();
    
    /**
     * Create a new CSM user.
     * @param user user
     * @param loginName loginName
     * @param password  password
     * @return user
     * @throws PAException PAException
     */
    public User createCSMUser(RegistryUser user, String loginName, 
                                String password) throws PAException {
        
        User createdCSMUser = null;
        try {            
            // create the csm user
            User csmUser = new User();
            // get values from Registry User object and set in CSM User object
            csmUser.setLoginName(loginName);
            csmUser.setPassword(password);
            csmUser.setFirstName(user.getFirstName());
            csmUser.setLastName(user.getLastName());
            csmUser.setOrganization(user.getAffiliateOrg());
            csmUser.setPhoneNumber(user.getPhone());
            ///create new user in CSM table           
            UserProvisioningManager upManager = SecurityServiceProvider.
                                            getUserProvisioningManager("pa");
            upManager.createUser(csmUser);
            // assign the created user to the appropriate group
            // read the CSM group name from the properties
            String submitterGroup = PaEarPropertyReader.getCSMSubmitterGroup();
            upManager.assignUserToGroup(loginName, submitterGroup);
            createdCSMUser = upManager.getUser(loginName);

        } catch (HibernateException hbe) {
            LOG.error(" Hibernate Exception while creating CSM user : " 
                                      + loginName, hbe);
            throw new PAException(" Hibernate exception while "
                    + "creating CSM user :" + loginName, hbe);
        } catch (CSException cse) {
            LOG.error(" CSM Exception while creating CSM user : "
                                     + loginName, cse);
            throw new PAException(" CSM exception while "
                    + "creating CSM user :" + loginName, cse);
        }
        
        return createdCSMUser;
        
    }
    
    /**
     * Update an existing CSM user.
     * @param user user
     * @param loginName loginName
     * @param password  password
     * @return user
     * @throws PAException PAException
     */
    public User updateCSMUser(RegistryUser user, String loginName, 
                                    String password) throws PAException {
        
        User createdCSMUser = null;
        try {            
            // create the csm user
            User csmUser = new User();
            // get values from Registry User object and set in CSM User object
            csmUser.setUserId(user.getCsmUserId());
            csmUser.setLoginName(loginName);
            csmUser.setPassword(password);
            csmUser.setFirstName(user.getFirstName());
            csmUser.setLastName(user.getLastName());
            csmUser.setOrganization(user.getAffiliateOrg());
            csmUser.setPhoneNumber(user.getPhone());
            ///update the user info in CSM table           
            UserProvisioningManager upManager = SecurityServiceProvider.
                                            getUserProvisioningManager("pa");
            upManager.modifyUser(csmUser);
            // assign the updated user to the appropriate group
            // read the CSM group name from the properties
            String submitterGroup = PaEarPropertyReader.getCSMSubmitterGroup();
            upManager.assignUserToGroup(loginName, submitterGroup);
            createdCSMUser = upManager.getUser(loginName);

        } catch (HibernateException hbe) {
            LOG.error(" Hibernate Exception while updating CSM user : "
                                    + loginName, hbe);
            throw new PAException(" Hibernate exception while "
                    + "updating CSM user :" + loginName, hbe);
        } catch (CSException cse) {
            LOG.error(" CSM Exception while updating CSM user : " 
                                    + loginName, cse);
            throw new PAException(" CSM exception while "
                    + "updating CSM user :" + loginName, cse);
        }
        
        return createdCSMUser;
        
    }
    
    /**
     * Retrieve an existing CSM user.
     * @param loginName loginName
     * @return user
     * @throws PAException PAException
     */
    public User getCSMUser(String loginName) 
                                    throws PAException {
        User csmUser = null;
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.
                                                   getUserProvisioningManager("pa");
            csmUser = upManager.getUser(loginName);
        } catch (CSConfigurationException csce) {
            LOG.error(" CSM Exception while retrieving CSM user : " 
                                        + loginName, csce);
            throw new PAException(" CSM exception while "
                                        + "retrieving CSM user :" + loginName, csce);
        } catch (CSException cse) {
            LOG.error(" CSM Exception while retrieving CSM user : " 
                                                + loginName, cse);
            throw new PAException(" CSM exception while "
                    + "retrieving CSM user :" + loginName, cse);
        }        
   
        return csmUser;
        
    }
    
    /**
     * 
     * @return RegistryUserService
     */
    public static CSMUserService getInstance() {
        return  registryUserService;
    }

}
