package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.URL;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class IiConverterTest {

   @Test (expected = UnsupportedOperationException.class)
    public void testConvert() {
        Class<URL> returnClass = URL.class;
        IiConverter instance = new IiConverter();
        instance.convert(returnClass, new Ii());
    }

    @Test
    public void testConvertToLong() {
        Ii value = null;
        IiConverter instance = new IiConverter();
        Long expResult = null;
        Long result = instance.convertToLong(value);
        assertEquals(expResult, result);

        value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        result = instance.convertToLong(value);
        assertEquals(expResult, result);

        expResult = 2L;
        value.setNullFlavor(null);
        value.setExtension(expResult.toString());

        try {
            instance.convertToLong(value);
            fail();
        } catch(IllegalArgumentException x){
            // "root must be set"
        }

        value.setRoot("123.456.789");
        result = instance.convertToLong(value);
        assertEquals(expResult, result);
    }

}