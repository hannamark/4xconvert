/**
 *
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * @author Vrushali
 *
 */
public class MockPaRegistryUserServiceBean implements RegistryUserServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#createUser(gov.nih.nci.pa.domain.RegistryUser)
     */
    public RegistryUser createUser(RegistryUser user) throws PAException {
        RegistryUser regUser = null;
        for (User userBean : MockCsmUtil.users) {
            if (user.getCsmUserId().equals(userBean.getUserId())) {
                regUser = new RegistryUser();
                regUser.setState("Texas");
                regUser.setCsmUserId(userBean.getUserId());
                regUser.setFirstName("firstName");
                regUser.setMiddleName("middleName");
                regUser.setLastName("lastName");
                regUser.setAddressLine("address");
                regUser.setAffiliateOrg("testOrg");
                regUser.setCity("Dallas");
                regUser.setCountry("USA");
                regUser.setPhone("phone");
                regUser.setPoOrganizationId(1L);
                regUser.setPoPersonId(1L);
                regUser.setPostalCode("postAdd");
                regUser.setPrsOrgName("prsOrg");
                regUser.setId(1L);
                regUser.setUserLastCreated("userLastCreated");
                regUser.setUserLastUpdated("userLastUpdated");
            }
        }
        return regUser;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#getUser(java.lang.String)
     */
    public RegistryUser getUser(String loginName) throws PAException {
        if(loginName != null && loginName.equals("exceptionName")){
            throw new PAException("test");
        }
        RegistryUser regUser = null;
        for (User user : MockCsmUtil.users) {
            if (loginName.equals(user.getLoginName())) {
                regUser = new RegistryUser();
                regUser.setCity("city");
                regUser.setCountry("USA");
                regUser.setState("Texas");
                regUser.setCsmUserId(user.getUserId());
                regUser.setUserLastCreated("userLastCreated");
                regUser.setUserLastUpdated("userLastUpdated");
            }
        }
        return regUser;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegistryUserServiceRemote#updateUser(gov.nih.nci.pa.domain.RegistryUser)
     */
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        RegistryUser regUser = null;
        for (User userBean : MockCsmUtil.users) {
            if (user.getCsmUserId().equals(userBean.getUserId())) {
                regUser = new RegistryUser();
                regUser.setState("Texas");
                regUser.setCsmUserId(userBean.getUserId());
                regUser.setFirstName("firstName");
                regUser.setMiddleName("middleName");
                regUser.setLastName("lastName");
                regUser.setAddressLine("address");
                regUser.setAffiliateOrg("testOrg");
                regUser.setCity("Dallas");
                regUser.setCountry("USA");
                regUser.setPhone("phone");
                regUser.setPoOrganizationId(1L);
                regUser.setPoPersonId(1L);
                regUser.setPostalCode("postAdd");
                regUser.setPrsOrgName("prsOrg");
                regUser.setId(1L);
                regUser.setUserLastCreated("userLastCreated");
                regUser.setUserLastUpdated("userLastUpdated");
            }
        }
        return regUser;
    }

}
