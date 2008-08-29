package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Ts;

import java.util.Date;

import org.junit.Test;

/**
 *
 * @author gax
 */
@SuppressWarnings("unchecked")
public class StatusDateConverterTest {

    @Test
    public void testConvert() {
        Class returnClass = java.net.URI.class;
        Date value = null;
        StatusDateConverter instance = new StatusDateConverter();
        try{
            instance.convert(returnClass, value);
            fail();
        } catch (UnsupportedOperationException x) {
        }

        value = IvlTsConverterTest.make(2008, 8, 20, 1, 1, 0, 0, "UTC");
        Ivl<Ts> result = instance.convert(Ivl.class, value);
        Date d = result.getLow().getValue();
        assertEquals(value, d);
        assertNotSame(value, d);
    }

    @Test
    public void testConvertToDate() {
        Date d = null;
        Ivl<Ts> result = StatusDateConverter.convertToDate(d);
        assertNotNull(result.getNullFlavor());

        d = new Date();
        result = StatusDateConverter.convertToDate(d);
        Date rd = result.getLow().getValue();
        assertEquals(d, rd);
        assertNotSame(d, rd);
        assertTrue(result.getLowClosed());
        assertEquals(NullFlavor.PINF, result.getHigh().getNullFlavor());
        assertFalse(result.getHighClosed());
    }

    @Test
    public void testRoundTrip() {
        Date d = null;
        Ivl<Ts> result = StatusDateConverter.convertToDate(d);
        assertNotNull(result.getNullFlavor());

        d = new Date();
        result = StatusDateConverter.convertToDate(d);
        Date rd = result.getLow().getValue();
        assertEquals(d, rd);
        assertNotSame(d, rd);

        Date result2 = IvlTsConverter.convertToIvlTs(result);
        assertEquals(d, result2);
        assertNotSame(d, result2);
    }

}