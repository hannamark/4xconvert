/**
 *
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;
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
    public void testGenerateMail() {
        RegistryUtil.generateMail(Constants.PROCESSED, "firstName", "1", "0", "1", "fileName.doc", "");
        RegistryUtil.generateMail(Constants.ERROR_PROCESSING, "firstName", "0", "1", "1", "", "error");
    }
    @Test
    public void testRemoveExceptionFromErrMsg() {
        String errMsg = "gov.nih.nci.pa.service.PAException: Duplicates grants are not allowed.";
        assertEquals(RegistryUtil.removeExceptionFromErrMsg(errMsg),"Duplicates grants are not allowed.");
        assertEquals(RegistryUtil.removeExceptionFromErrMsg("Duplicates grants are not allowed."),
             "Duplicates grants are not allowed.");
        assertEquals(RegistryUtil.removeExceptionFromErrMsg("gov.nih.nci.pa.service.PAException: " +
            "gov.nih.nci.pa.service.PAException: Validation Exception Please enter valid value for " +
            "Summary 4 Sponsor Category."),"Please enter valid value for Summary 4 Sponsor Category.");
    }
}
