package gov.nih.nci.accrual.outweb.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;

import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockPaRegistryUserServiceBean implements RegistryUserServiceRemote {

    /**
     * {@inheritDoc}
     */
    public RegistryUser createUser(RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUser getUser(String loginName) throws PAException {
        if (loginName != null && loginName.equals("exceptionName")) {
            throw new PAException("test");
        }
        RegistryUser regUser = new RegistryUser();
        regUser.setCity("city");
        regUser.setCountry("country");
        regUser.setCsmUserId(1L);
        regUser.setUserLastCreated("userLastCreated");
        regUser.setUserLastUpdated("userLastUpdated");
        return regUser;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
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

}
