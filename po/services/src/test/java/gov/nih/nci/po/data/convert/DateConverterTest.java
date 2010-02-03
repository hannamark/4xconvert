package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Ts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class DateConverterTest {

    @Test
    public void testConvert() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        Ts value = null;

        Date expResult = null;
        Date result = TsConverter.convertToDate(value);
        assertEquals(expResult, result);

        value = new Ts();
        value.setNullFlavor(NullFlavor.NI);
        result = TsConverter.convertToDate(value);
        assertNull(result);

        expResult = sdf.parse("09/28/1980");
        value.setNullFlavor(null);
        value.setValue(expResult);
        result = TsConverter.convertToDate(value);
        assertEquals(expResult, result);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testConvertBad() {
        Ts value = new Ts();
        assertNull(value.getNullFlavor());
        assertNull(value.getValue());

        TsConverter.convertToDate(value);
    }
}