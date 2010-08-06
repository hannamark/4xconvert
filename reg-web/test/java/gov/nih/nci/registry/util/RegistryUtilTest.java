/**
 *
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.registry.action.AbstractRegWebTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class RegistryUtilTest extends AbstractRegWebTest {
    @Test
    public void testIsValidPhoneNumber() {
        assertTrue(RegistryUtil.isValidPhoneNumber("phoneNumber"));
        assertFalse(RegistryUtil.isValidPhoneNumber("phone!*Number"));
        assertFalse(RegistryUtil.isValidPhoneNumber(null));
        assertFalse(RegistryUtil.isValidPhoneNumber("min"));
    }
    @Test
    public void testIsValidFileType() {
        assertTrue(RegistryUtil.isValidFileType("fileName.doc","doc"));
        assertFalse(RegistryUtil.isValidFileType("fileName.doc","txt"));
        assertFalse(RegistryUtil.isValidFileType("fileName.doc",""));
    }

    @Test
    public void testGenerateMail() {
        RegistryUtil.generateMail(Constants.PROCESSED, "firstName", "1", "0", "1", "fileName.doc", "");
        RegistryUtil.generateMail(Constants.ERROR_PROCESSING, "firstName", "0", "1", "1", "", "error");
    }

    @Test
    public void testGetRegistryUserWebDto() {
        List<RegistryUser> registryUsers = new ArrayList<RegistryUser>();
        RegistryUser reguser = new RegistryUser();
        reguser.setFirstName("firstName");
        reguser.setLastName("lastName");
        reguser.setId(1L);
        reguser.setAffiliatedOrganizationId(1L);
        reguser.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        registryUsers.add(reguser);
        RegistryUtil.getRegistryUserWebDto(registryUsers);

    }

}
