package gov.nih.nci.pa.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PAUtilTest {
    @Test
    public void isNotNullOrNotEmptyTest() {
        assertFalse(PAUtil.isNotNullOrNotEmpty(null));
        assertFalse(PAUtil.isNotNullOrNotEmpty(" "));
        assertTrue(PAUtil.isNotNullOrNotEmpty(" 1 "));
        assertTrue(PAUtil.isNotNullOrNotEmpty(new Object()));
    }

}
