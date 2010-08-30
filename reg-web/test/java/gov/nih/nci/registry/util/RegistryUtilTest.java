/**
 *
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.registry.action.AbstractRegWebTest;

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

}
