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
        String value = null;
        StConverter instance = new StConverter();
        St result = instance.convert(value);
        assertEquals(NullFlavor.NI, result.getNullFlavor());
       
        
        value = "foo";
        result = instance.convert(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());
        
        
        value = "";
        result = instance.convert(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());
    }

}