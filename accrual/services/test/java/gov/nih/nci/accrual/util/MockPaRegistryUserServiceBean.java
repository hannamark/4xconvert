/**
 * 
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockPaRegistryUserServiceBean implements RegistryUserServiceRemote {

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
        RegistryUser regUser = null;
        if(loginName != null && loginName.equals("test@scenpro.com")){    
            regUser = new RegistryUser();
            regUser.setCity("city");
            regUser.setCountry("USA");
            regUser.setState("Texas");
            regUser.setCsmUserId(1L);
            regUser.setUserLastCreated("userLastCreated");
            regUser.setUserLastUpdated("userLastUpdated");
        }
        return regUser;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#updateUser(gov.nih.nci.pa.domain.RegistryUser)
     */
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
