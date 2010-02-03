package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class StConverterTest {

    @Test
    public void testConvert() {

        St value = null;

        String expResult = null;
        String result = StConverter.convertToString(value);
        assertEquals(expResult, result);

        value = new St();
        value.setNullFlavor(NullFlavor.NI);
        result = StConverter.convertToString(value);
        assertNull(result);

        expResult = "foo";
        value.setNullFlavor(null);
        value.setValue(expResult);
        result = StConverter.convertToString(value);
        assertEquals(expResult, result);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testConvertBad() {
        St value = new St();
        assertNull(value.getNullFlavor());
        assertNull(value.getValue());

        StConverter.convertToString(value);
    }
}