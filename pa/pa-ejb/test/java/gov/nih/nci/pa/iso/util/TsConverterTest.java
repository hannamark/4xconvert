/**
 * 
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class TsConverterTest {
    @Test
    public void timestampTsTimestamp () {
        Timestamp xxx = new Timestamp(new Date().getTime());
        Timestamp yyy = TsConverter.convertToTimestamp(TsConverter.convertToTs(xxx));
        assertEquals(xxx,yyy);
        Timestamp zzz = TsConverter.convertToTimestamp(TsConverter.convertToTs(null));
        assertNull(zzz);
    }
}
