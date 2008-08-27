/**
 * 
 */
package gov.nih.nci.pa.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;

import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class IiConverterTest {
    @Test
    public void testConvertToIi() {
        Ii z;
        Long y;
        for (Long x = 1L; x < 1000L; x = x * 100) {
            z = IiConverter.convertToIi(x);
            y = IiConverter.convertToLong(z);
            assertEquals(x, y);
        }
    }

    @Test
    public void testConvertToLong() {
        Ii value = null;
        Long expResult = null;
        Long result = IiConverter.convertToLong(value);
        assertEquals(expResult, result);

        value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        result = IiConverter.convertToLong(value);
        assertEquals(expResult, result);

        expResult = 2L;
        value.setNullFlavor(null);
        value.setExtension(expResult.toString());

        try {
            IiConverter.convertToLong(value);
            fail();
        } catch (IllegalArgumentException x) {
            // "root must be set"
        }

        value.setRoot("123.456.789");
        result = IiConverter.convertToLong(value);
        assertEquals(expResult, result);
    }

}
