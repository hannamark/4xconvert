package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class StConverterTest {

    @Test
    public void testConvert() {
    
        St value = null;
        StConverter instance = new StConverter();
        String expResult = null;
        String result = instance.convertToString(value);
        assertEquals(expResult, result);

        value = new St();
        value.setNullFlavor(NullFlavor.NI);
        result = instance.convertToString(value);
        assertNull(result);

        expResult = "foo";
        value.setNullFlavor(null);
        value.setValue(expResult);
        result = instance.convertToString(value);
        assertEquals(expResult, result);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testConvertBad() {
        St value = new St();
        assertNull(value.getNullFlavor());
        assertNull(value.getValue());

        StConverter instance = new StConverter();
        instance.convertToString(value);
    }

}