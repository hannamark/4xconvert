package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

public class PAUtilTest {
    @Test
    public void isNotNullOrNotEmptyTest() {
        assertFalse(PAUtil.isNotNullOrNotEmpty(null));
        assertFalse(PAUtil.isNotNullOrNotEmpty(" "));
        assertTrue(PAUtil.isNotNullOrNotEmpty(" 1 "));
        assertTrue(PAUtil.isNotNullOrNotEmpty(new Object()));
    }
    
    @Test
    public void normalizeDateStringTest() {
        assertEquals("01/31/2001", PAUtil.normalizeDateString("1/31/2001abcdefg"));
        assertEquals("01/31/2001", PAUtil.normalizeDateString("2001-01-31abcdefg"));
        assertNull(PAUtil.normalizeDateString("Tuesday"));
    }
    
    @Test
    public void dateStringToTimestampTest() {
        Timestamp now = new Timestamp(new Date().getTime());
        assertTrue(now.after(PAUtil.dateStringToTimestamp(now.toString())));
        assertEquals (new Timestamp(101,0,31,0,0,0,0)
                      , PAUtil.dateStringToTimestamp("01/31/2001"));
    }
    
 
}
