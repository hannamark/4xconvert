/**
 * 
 */
package gov.nih.nci.po.web.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 *
 */
public class POUtilsTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link gov.nih.nci.po.web.util.POUtils#adjustPhoneNumberFormat(java.lang.String)}.
     */
    @Test
    public final void testAdjustPhoneNumberFormat() {
        assertNull(POUtils.adjustPhoneNumberFormat(null));
        assertEquals("", POUtils.adjustPhoneNumberFormat(""));
        assertEquals("`006433640371", POUtils.adjustPhoneNumberFormat("`006433640371"));
        assertEquals("433-0421", POUtils.adjustPhoneNumberFormat("()433-0421"));
        assertEquals("+35314096651", POUtils.adjustPhoneNumberFormat("(+353)14096651"));
        assertEquals("262-928-7537", POUtils.adjustPhoneNumberFormat(" (262)-928-7537"));
        assertEquals("263-11-866-919", POUtils.adjustPhoneNumberFormat(" (263) -11-866-919"));
        assertEquals("269-556-7169", POUtils.adjustPhoneNumberFormat("(269) 556-7169"));
        assertEquals("301-251-1161-X-2895", POUtils.adjustPhoneNumberFormat("(301)-251-1161-X-2895"));
        assertEquals("301-295-4430", POUtils.adjustPhoneNumberFormat("(301)295-4430"));         
    }

}
