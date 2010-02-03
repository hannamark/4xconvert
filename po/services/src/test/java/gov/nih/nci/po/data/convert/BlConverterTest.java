package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.NullFlavor;

import org.junit.Test;

/**
 *
 * @author smatyas
 */
public class BlConverterTest {

    @Test
    public void testConvert() {

        Bl value = null;

        Boolean expResult = null;
        Boolean result = BlConverter.convertToBoolean(value);
        assertEquals(expResult, result);

        value = new Bl();
        value.setNullFlavor(NullFlavor.NI);
        result = BlConverter.convertToBoolean(value);
        assertNull(result);

        expResult = Boolean.FALSE;
        value.setNullFlavor(null);
        value.setValue(expResult);
        result = BlConverter.convertToBoolean(value);
        assertEquals(expResult, result);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testConvertBad() {
        Bl value = new Bl();
        assertNull(value.getNullFlavor());
        assertNull(value.getValue());

        BlConverter.convertToBoolean(value);
    }
}