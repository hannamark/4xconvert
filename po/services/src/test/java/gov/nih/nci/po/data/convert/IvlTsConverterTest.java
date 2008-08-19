package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.services.PoIsoConstraintException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 *
 * @author gax
 */
public class IvlTsConverterTest {

    @Test
    public void testConvert() {
        Class returnClass = java.net.URI.class;
        Ivl<Ts> value = null;
        IvlTsConverter instance = new IvlTsConverter();
        try{
            instance.convert(returnClass, value);
            fail();
        } catch (UnsupportedOperationException x) {
        }
        
        value = new Ivl<Ts>();
        Ts ts = new Ts();
        Date expResult = make(2008, 8, 19, 15, 17, 0, 0, "UTC");//Zulu
        ts.setValue(expResult);
        value.setLow(ts);
        Date result = instance.convert(Date.class, value);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToIvlTs() {
        Ivl<Ts> iso = null;
        Date result = IvlTsConverter.convertToIvlTs(iso);
        assertNull(result);

        iso = new Ivl<Ts>();
        iso.setNullFlavor(NullFlavor.NINF);
        result = IvlTsConverter.convertToIvlTs(iso);
        assertNull(result);
        
        iso = new Ivl<Ts>();
        Ts ts = new Ts();
        Date expResult = make(1999, 12, 31, 23, 59, 59, 999, "EST");
        ts.setValue(expResult);
        iso.setLow(ts);
        result = IvlTsConverter.convertToIvlTs(iso);
        assertNotSame(expResult, result);
        assertEquals(expResult, result);
        
        iso.setFlavorId("flava");
        try {
            IvlTsConverter.convertToIvlTs(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }
        
        iso.setFlavorId(null);
        ts.setFlavorId("flava");
        try {
            IvlTsConverter.convertToIvlTs(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }
    }

    public static Date make(int year, int month, int date, int hour24, int min, int sec, int millis, String zone) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(zone));
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, date);
        c.set(Calendar.HOUR_OF_DAY, hour24);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, sec);
        c.set(Calendar.MILLISECOND, millis);
        return c.getTime();
    }

}