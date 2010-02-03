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
public class BooleanConverterTest {

    @Test
    public void testConvertToBl() {
        Boolean value = null;
        Bl result = BooleanConverter.convertToBl(value);
        assertEquals(NullFlavor.NI, result.getNullFlavor());
        assertEquals(value, result.getValue());


        value = Boolean.FALSE;
        result = BooleanConverter.convertToBl(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());


        value = Boolean.TRUE;
        result = BooleanConverter.convertToBl(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());
    }

}