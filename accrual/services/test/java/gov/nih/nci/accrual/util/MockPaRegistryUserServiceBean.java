/**
 *
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockPaRegistryUserServiceBean implements RegistryUserServiceLocal {

    /**
     * {@inheritDoc}
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
                regUser.setUserLastCreated(TestSchema.createUser());
                regUser.setUserLastUpdated(TestSchema.createUser());
            }
        }
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUser getUser(String loginName) throws PAException {
        if (loginName != null && loginName.equals("exceptionName")) {
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
                regUser.setUserLastCreated(TestSchema.createUser());
                regUser.setUserLastUpdated(TestSchema.createUser());
            }
        }
        return regUser;
    }

    /**
     * {@inheritDoc}
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
                regUser.setUserLastCreated(TestSchema.createUser());
                regUser.setUserLastUpdated(TestSchema.createUser());
            }
        }
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUser getUserById(Long userId) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        return null;
    }
     /**
     * {@inheritDoc}
     */
    public List<RegistryUser> search(RegistryUser user) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {

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

    /**
     * {@inheritDoc}
     */
    public boolean isTrialOwner(Long userId, Long studyProtocolId) throws PAException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public List<DisplayTrialOwnershipInformation> searchTrialOwnership(
            DisplayTrialOwnershipInformation trialOwnershipInfo, Long affiliatedOrgId) throws PAException {
        return null;
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
